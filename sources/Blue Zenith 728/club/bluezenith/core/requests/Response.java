package club.bluezenith.core.requests;

import org.apache.http.HttpEntity;

public class Response {
    public final String route;
    public final Request parent;
    public final String textResponse;
    public final HttpEntity responseEntity;
    public int code;

    public Response(String route, Request parent, String textResponse, HttpEntity responseEntity) {
        this.route = route;
        this.parent = parent;
        this.textResponse = textResponse;
        this.responseEntity = responseEntity;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }
}
