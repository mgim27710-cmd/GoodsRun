package goodsrun_backend.facade;

import goodsrun_backend.exception.LockAcquisitionFailException;
import goodsrun_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final RedissonClient redissonClient;
    private final OrderService orderService;

    public Long order(Long productId, Long userId, int count) {
        RLock lock = redissonClient.getLock("product:lock:" + productId);
        boolean acquired;
        try {
            acquired = lock.tryLock(5, 3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("락 획득 중 인터럽트 발생", e);
        }
        if (!acquired) {
            throw new LockAcquisitionFailException("현재 접속이 몰려 처리할 수 없습니다. 잠시 후 다시 시도해주세요.");
        }
        try {
            return orderService.processOrder(productId, userId, count);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}