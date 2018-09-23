package company.challenge.client;

import feign.Response;
import feign.codec.ErrorDecoder;

public class ResponseStatusFeignErrorDecoder extends ErrorDecoder.Default {

    public ResponseFeignException decode(String methodKey, Response response) {
        return new ResponseFeignException(methodKey, response);
    }
}
