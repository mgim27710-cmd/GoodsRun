package goodsrun_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 필수 추가!
@SpringBootApplication
public class GoodsrunBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsrunBackendApplication.class, args);
    }
}