package goodsrun_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Version
    private Long version;

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalStateException("재고가 부족합니다. (현재 재고: " + this.stockQuantity + ")");
        }
        this.stockQuantity = restStock;
    }
    // Product.java 내부에 추가할 수 있는 테스트용 생성자
public Product(String name, int price, int stockQuantity) {
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
}
public static Product createProduct(String name, int price, int stockQuantity) {
    Product product = new Product();
    product.name = name;
    product.price = price;
    product.stockQuantity = stockQuantity;
    return product;
}
}