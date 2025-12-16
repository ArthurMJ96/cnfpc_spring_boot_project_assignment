package lu.arthurmj.cnfpc_spring_boot_project_assignment.exception;

/**
 * Thrown when a requested quantity exceeds available inventory.
 */
public class OutOfStockException extends RuntimeException {

    private final Long productId;
    private final int requested;
    private final int available;

    public OutOfStockException(Long productId, int requested, int available) {
        super("Out of stock for product " + productId + ": requested=" + requested + ", available=" + available);
        this.productId = productId;
        this.requested = requested;
        this.available = available;
    }

    public Long getProductId() {
        return productId;
    }

    public int getRequested() {
        return requested;
    }

    public int getAvailable() {
        return available;
    }
}
