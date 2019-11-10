package dev.jfuture.task.exception;

public class WebParsingException extends Exception {

    public WebParsingException() {

    }

    public WebParsingException(String message) {
        super(message);
    }

    public WebParsingException(Throwable cause) {
        super(cause);
    }

    public WebParsingException(String message, Throwable cause) {
        super(message, cause);
    }

}
