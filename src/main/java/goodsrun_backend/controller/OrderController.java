package goodsrun_backend.controller;

import goodsrun_backend.dto.OrderCreateRequest;
import goodsrun_backend.dto.OrderCreateResponse;
import goodsrun_backend.facade.OrderFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        Long orderId = orderFacade.order(
                request.getProductId(),
                request.getUserId(),
                request.getCount()
        );

        URI location = URI.create("/api/orders/" + orderId);
        return ResponseEntity.created(location).body(new OrderCreateResponse(orderId));
    }
}