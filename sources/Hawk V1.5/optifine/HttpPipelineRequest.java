package optifine;

public class HttpPipelineRequest {
   private HttpListener httpListener = null;
   private HttpRequest httpRequest = null;
   private boolean closed = false;

   public HttpListener getHttpListener() {
      return this.httpListener;
   }

   public void setClosed(boolean var1) {
      this.closed = var1;
   }

   public HttpRequest getHttpRequest() {
      return this.httpRequest;
   }

   public boolean isClosed() {
      return this.closed;
   }

   public HttpPipelineRequest(HttpRequest var1, HttpListener var2) {
      this.httpRequest = var1;
      this.httpListener = var2;
   }
}
