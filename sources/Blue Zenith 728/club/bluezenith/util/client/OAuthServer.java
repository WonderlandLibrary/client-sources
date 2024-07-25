package club.bluezenith.util.client;

import club.bluezenith.BlueZenith;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.function.Function;

public class OAuthServer extends NanoHTTPD {

    private Function<IHTTPSession, Response> responseFunction;
    private boolean isWaitingForRequest;

    public OAuthServer() {
        super(8085);
        new Thread(() -> {
            try {
                start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            } catch (IOException e) {
                BlueZenith.getBlueZenith().getNotificationPublisher().postError(
                        "Microsoft Authenticator",
                        "Couldn't start local server. Check logs.",
                        3000
                );
                e.printStackTrace();
            }
        }).start();
    }

    public OAuthServer setResponseFunction(Function<IHTTPSession, Response> responseFunction) {
        this.responseFunction = responseFunction;
        return this;
    }

    @SuppressWarnings("all")
    public OAuthServer waitForRequest() {
        this.isWaitingForRequest = true;
        while (isWaitingForRequest) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public Response serve(IHTTPSession session) {
        if(session.getUri().equals("/error")) {
            if(isWaitingForRequest)
            isWaitingForRequest = false;
            return newFixedLengthResponse("Microsoft Login failed: " + session.getParms().entrySet().iterator().next().getValue());
        }
        if(session.getUri().equals("/favicon.ico")) return null;

        if(this.responseFunction != null) {
            final Response response = this.responseFunction.apply(session);
            this.responseFunction = null;
            if(this.isWaitingForRequest)
                this.isWaitingForRequest = false;
            return response;
        }
        return newFixedLengthResponse("Weird, we couldn't satisfy the request. Please report this issue.");
    }
}
