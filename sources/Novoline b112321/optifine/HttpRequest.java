package optifine;

import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {
   private String host = null;
   private int port = 0;
   private Proxy proxy = Proxy.NO_PROXY;
   private String method = null;
   private String file = null;
   private String http = null;
   private Map headers = new LinkedHashMap();
   private byte[] body = null;
   private int redirects = 0;
   public static final String d = "GET";
   public static final String g = "HEAD";
   public static final String k = "POST";
   public static final String h = "HTTP/1.0";
   public static final String f = "HTTP/1.1";

   public HttpRequest(String var1, int var2, Proxy var3, String var4, String var5, String var6, Map var7, byte[] var8) {
      this.host = var1;
      this.port = var2;
      this.proxy = var3;
      this.method = var4;
      this.file = var5;
      this.http = var6;
      this.headers = var7;
      this.body = var8;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public String getMethod() {
      return this.method;
   }

   public String getFile() {
      return this.file;
   }

   public String getHttp() {
      return this.http;
   }

   public Map getHeaders() {
      return this.headers;
   }

   public byte[] getBody() {
      return this.body;
   }

   public int getRedirects() {
      return this.redirects;
   }

   public void setRedirects(int var1) {
      this.redirects = var1;
   }

   public Proxy getProxy() {
      return this.proxy;
   }
}
