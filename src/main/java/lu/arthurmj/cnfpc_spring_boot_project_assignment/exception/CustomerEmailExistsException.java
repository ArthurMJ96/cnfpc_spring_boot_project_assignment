package lu.arthurmj.cnfpc_spring_boot_project_assignment.exception;

public class CustomerEmailExistsException extends RuntimeException {
    public CustomerEmailExistsException(String message) {
        super(message);
    }
}
