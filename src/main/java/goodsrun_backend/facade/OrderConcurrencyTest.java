package goodsrun_backend.facade;

import goodsrun_backend.domain.Product;
import goodsrun_backend.domain.User;
import goodsrun_backend.repository.ProductRepository;
import goodsrun_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderConcurrencyTest {

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private Long savedProductId;
    private Long savedUserId;

    @BeforeEach
    void setUp() {
        // 기존 데이터 초기화 (필요시)
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        // Product는 기본 생성자가 protected이므로 테스트용 데이터를 넣을 수 있는 메서드나 빌더가 필요합니다.
        // 또는 Product 클래스에 테스트용 패키지-private 생성자를 열어줄 수 있습니다.
    }
    
    // ... 테스트 로직 계속
}