package goodsrun_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int orderPrice;
    private int count;

    public static OrderItem createOrderItem(Product product, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.product = product;
        orderItem.orderPrice = orderPrice;
        orderItem.count = count;
        product.removeStock(count);
        return orderItem;
    }

    void setOrder(Order order) {
        this.order = order;
    }
}