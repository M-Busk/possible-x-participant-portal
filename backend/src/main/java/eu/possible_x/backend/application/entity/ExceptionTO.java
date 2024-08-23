package eu.possible_x.backend.application.entity;

public class ExceptionTO {
    private int httpStatusCode;

    private String message;

    public ExceptionTO(int newHttpStatusCode, String newMessage) {

        this.httpStatusCode = newHttpStatusCode;
        this.message = newMessage;
    }

    public int getHttpStatusCode() {

        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {

        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}
