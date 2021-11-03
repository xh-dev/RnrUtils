package dev.xethh.webtool.dto.base.response;

public class FailResponse implements Response {

    private String message;

    protected FailResponse(String message) {
        this.message = message;
    }

    public static FailResponse error(String message) {
        return new FailResponse(message);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    public String getMessage() {
        return message;
    }
}
