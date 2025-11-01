package bg.sofia.uni.fmi.mjt.fittrack.exception;

public class InvalidWorkoutException extends RuntimeException {
    public InvalidWorkoutException(String message) {
        super(message);
    }

    public InvalidWorkoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
