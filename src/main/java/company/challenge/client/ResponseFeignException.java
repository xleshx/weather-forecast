package company.challenge.client;

import feign.Response;

public class ResponseFeignException extends RuntimeException {
    private final Response response;
    private final String methodKey;

    public ResponseFeignException(String methodKey, Response response) {
        super(String.format("Java method that invoked the request: %s.\nResponse from the failed request: %s", methodKey, response));
        this.response = response;
        this.methodKey = methodKey;
    }

    public ResponseFeignException(String methodKey, Response response, Throwable e) {
        super(String.format("Java method that invoked the request: %s.\nResponse from the failed request: %s", methodKey, response), e);
        this.response = response;
        this.methodKey = methodKey;
    }

    public Response getResponse() {
        return this.response;
    }

    public String getMethodKey() {
        return this.methodKey;
    }
}
