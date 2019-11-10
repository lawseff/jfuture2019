package dev.jfuture.task.exception;

public class DocumentException extends Exception {

    public DocumentException() {

    }

    public DocumentException(String message) {
        super(message);
    }

    public DocumentException(Throwable cause) {
        super(cause);
    }

    public DocumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
