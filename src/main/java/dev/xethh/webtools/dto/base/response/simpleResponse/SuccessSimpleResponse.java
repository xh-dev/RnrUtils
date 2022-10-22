package dev.xethh.webtools.dto.base.response.simpleResponse;

import dev.xethh.webtools.exception.NotSupportException;

public class SuccessSimpleResponse implements SimpleResponse {
    private static SuccessSimpleResponse _instance;

    protected SuccessSimpleResponse() {
    }
    public boolean isSuccess() {
        return true;
    }

    public static SuccessSimpleResponse instance(){
        if(_instance == null) {
            _instance = new SuccessSimpleResponse();
        }

        return _instance;
    }

    @Override
    public FailResponse asFailedResponse() {
        throw new NotSupportException();
    }
}
