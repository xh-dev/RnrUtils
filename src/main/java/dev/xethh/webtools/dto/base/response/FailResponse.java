package dev.xethh.webtools.dto.base.response;

public class FailResponse implements Response {

    private String traceId;
    private String message;

    public String getTraceId() {
        return traceId;
    }

    protected FailResponse(String traceId, String message) {
        this.traceId = traceId;
        this.message = message;
    }

    public static FailResponse error(String traceId, String message) {
        return new FailResponse(traceId, message);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    public String getMessage() {
        return message;
    }
}
