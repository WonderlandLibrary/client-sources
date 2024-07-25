package club.bluezenith.core.requests;

import club.bluezenith.core.requests.data.ContentType;
import club.bluezenith.core.requests.data.RequestMethod;
import club.bluezenith.core.requests.data.RequestOption;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Request {
    static final int MAX_CALLBACKS = 8;
    final String route;
    final RequestExecutor executor;
    final RequestMethod method;
    final ContentType contentType;
    final RequestOption[] options;
    Consumer<Response>[] callbacks;
    int addedCallbacks = 1;
    boolean done;
    boolean running;

    private CompletableFuture<Response> future = null;
    private Response response = null;

    @SuppressWarnings("unchecked")
    public Request(String route, RequestExecutor executor, RequestMethod method, ContentType contentType, Consumer<Response> callback, RequestOption... options) {
        this.route = route;
        this.executor = executor;
        this.method = method;
        this.contentType = contentType;
        this.options = options;
        final Consumer<Response>[] callbacks = new Consumer[MAX_CALLBACKS];
        callbacks[0] = callback;
        callbacks[1] = ((response) -> {
            this.response = response;
            this.done = true;
        });
        this.callbacks = callbacks;
    }

    public Request run() {
        if(!this.done && !this.running) {
            this.future = executor.queue(this, callbacks);
            this.running = true;
        }
        return this;
    }

    public Response getResponse() {
        return this.response;
    }

    public boolean isDone() {
        return this.done;
    }

    public Request appendCallback(Consumer<Response> callback) {
        if(++addedCallbacks < MAX_CALLBACKS)
        this.callbacks[addedCallbacks] = callback;
        return this;
    }

    public Request blockThread() {
        if(future == null) return this;
        while (!this.done && !future.isDone() && !future.isCancelled() && !future.isCompletedExceptionally());
        return this;
    }

    public static Request get(String route, RequestExecutor executor, ContentType contentType, RequestOption... options) {
        return new Request(route, executor, RequestMethod.GET, contentType, null, options);
    }

    public static Request post(String route, RequestExecutor executor, ContentType contentType, RequestOption... options) {
        return new Request(route, executor, RequestMethod.POST, contentType, null, options);
    }

    public static Request put(String route, RequestExecutor executor, ContentType contentType, RequestOption... options) {
        return new Request(route, executor, RequestMethod.PUT, contentType, null, options);
    }
}
