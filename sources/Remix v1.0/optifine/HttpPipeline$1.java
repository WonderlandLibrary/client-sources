package optifine;

import java.util.*;

static final class HttpPipeline$1 implements HttpListener {
    final /* synthetic */ HashMap val$map;
    
    @Override
    public void finished(final HttpRequest req, final HttpResponse resp) {
        final Map var3 = this.val$map;
        synchronized (this.val$map) {
            this.val$map.put("Response", resp);
            this.val$map.notifyAll();
        }
    }
    
    @Override
    public void failed(final HttpRequest req, final Exception e) {
        final Map var3 = this.val$map;
        synchronized (this.val$map) {
            this.val$map.put("Exception", e);
            this.val$map.notifyAll();
        }
    }
}