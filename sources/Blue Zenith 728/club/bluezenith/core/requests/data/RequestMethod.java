package club.bluezenith.core.requests.data;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

public enum RequestMethod {
    GET,
    POST,
    PUT;

    public HttpRequestBase getAppropriateEntity() {
        switch (this) {
            case GET:
                return new HttpGet();
            case POST:
                return new HttpPost();
            case PUT:
                return new HttpPut();
            default: throw new IllegalStateException();
        }
    }

}
