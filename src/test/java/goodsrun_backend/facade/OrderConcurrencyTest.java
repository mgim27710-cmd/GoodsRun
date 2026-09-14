package goodsrun_backend.facade;

import goodsrun_backend.domain.Product;
import goodsrun_backend.domain.User;
import goodsrun_backend.repository.OrderRepository;
import goodsrun_backend.repository.ProductRepository;
import goodsrun_backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderConcurrencyTest {

    @Autowired
    private OrderFacade orderFacade;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    private Long savedProductId;
    private Long savedUserId;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        Product product = Product.createProduct("한정판 굿즈", 10000, 100);
        savedProductId = productRepository.save(product).getId();

        User user = User.createUser("test@goodsrun.com", "테스터");
        savedUserId = userRepository.save(user).getId();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("100명이 동시에 1개씩 주문하면 재고가 정확히 0이 되어야 한다 (Redisson 분산락 검증)")
    void order_concurrency_test() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderFacade.order(savedProductId, savedUserId, 1); // count=1 필수
                } catch (Exception e) {
                    // 락 획득 실패 등도 정상 시나리오이므로 여기선 무시하고 최종 재고만 검증
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Product finalProduct = productRepository.findById(savedProductId).orElseThrow();
        assertThat(finalProduct.getStockQuantity()).isEqualTo(0);
    }
}