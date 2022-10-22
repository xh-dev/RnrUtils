package dev.xethh.webtools.dto.base.response.simpleResponse;

public class FailedSimpleResponse implements SimpleResponse, FailResponse {

    private final String traceId;
    private final String message;

    public String getTraceId() {
        return traceId;
    }

    public FailedSimpleResponse(String traceId, String message) {
        this.traceId = traceId;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public FailResponse asFailedResponse() {
        return this;
    }
}
