package lu.arthurmj.cnfpc_spring_boot_project_assignment.exception;

public class CategoryExistsException extends RuntimeException {
    public CategoryExistsException(String message) {
        super(message);
    }
}
