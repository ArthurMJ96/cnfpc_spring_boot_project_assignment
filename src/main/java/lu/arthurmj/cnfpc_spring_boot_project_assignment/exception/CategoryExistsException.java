package lu.arthurmj.cnfpc_spring_boot_project_assignment.exception;

/**
 * Thrown when attempting to create a category that already exists.
 */
public class CategoryExistsException extends RuntimeException {
    public CategoryExistsException(String message) {
        super(message);
    }
}
