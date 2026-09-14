package goodsrun_backend.exception;

public class LockAcquisitionFailException extends RuntimeException {
    public LockAcquisitionFailException(String message) {
        super(message);
    }
}