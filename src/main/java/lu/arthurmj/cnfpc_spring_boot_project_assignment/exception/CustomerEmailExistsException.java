package lu.arthurmj.cnfpc_spring_boot_project_assignment.exception;

/**
 * Thrown when attempting to create a customer with an email that already
 * exists.
 */
public class CustomerEmailExistsException extends RuntimeException {
    public CustomerEmailExistsException(String message) {
        super(message);
    }
}
