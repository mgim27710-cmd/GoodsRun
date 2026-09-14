package goodsrun_backend.service;

import goodsrun_backend.domain.Order;
import goodsrun_backend.domain.OrderItem;
import goodsrun_backend.domain.Product;
import goodsrun_backend.domain.User;
import goodsrun_backend.exception.ProductNotFoundException;
import goodsrun_backend.exception.UserNotFoundException;
import goodsrun_backend.repository.OrderRepository;
import goodsrun_backend.repository.ProductRepository;
import goodsrun_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long processOrder(Long productId, Long userId, int count) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));
                
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));

        OrderItem orderItem = OrderItem.createOrderItem(product, product.getPrice(), count);
        Order order = Order.createOrder(user, orderItem);

        orderRepository.save(order);
        return order.getId();
    }
}