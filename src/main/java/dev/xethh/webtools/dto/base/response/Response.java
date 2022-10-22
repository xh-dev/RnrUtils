package dev.xethh.webtools.dto.base.response;

public interface Response {
    boolean isSuccess();
    default boolean isFailed(){
        return !this.isSuccess();
    }
}
