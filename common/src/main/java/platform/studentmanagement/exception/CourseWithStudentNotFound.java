package platform.studentmanagement.exception;

public class CourseWithStudentNotFound extends RuntimeException {
    public CourseWithStudentNotFound(String message) {
        super(message);
    }
}
