package org.jsoup;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieStore;
import java.net.Proxy;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocketFactory;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public interface Connection {
   Connection newRequest();

   Connection url(URL var1);

   Connection url(String var1);

   Connection proxy(@Nullable Proxy var1);

   Connection proxy(String var1, int var2);

   Connection userAgent(String var1);

   Connection timeout(int var1);

   Connection maxBodySize(int var1);

   Connection referrer(String var1);

   Connection followRedirects(boolean var1);

   Connection method(Connection.Method var1);

   Connection ignoreHttpErrors(boolean var1);

   Connection ignoreContentType(boolean var1);

   Connection sslSocketFactory(SSLSocketFactory var1);

   Connection data(String var1, String var2);

   Connection data(String var1, String var2, InputStream var3);

   Connection data(String var1, String var2, InputStream var3, String var4);

   Connection data(Collection<Connection.KeyVal> var1);

   Connection data(Map<String, String> var1);

   Connection data(String... var1);

   @Nullable
   Connection.KeyVal data(String var1);

   Connection requestBody(String var1);

   Connection header(String var1, String var2);

   Connection headers(Map<String, String> var1);

   Connection cookie(String var1, String var2);

   Connection cookies(Map<String, String> var1);

   Connection cookieStore(CookieStore var1);

   CookieStore cookieStore();

   Connection parser(Parser var1);

   Connection postDataCharset(String var1);

   Document get() throws IOException;

   Document post() throws IOException;

   Connection.Response execute() throws IOException;

   Connection.Request request();

   Connection request(Connection.Request var1);

   Connection.Response response();

   Connection response(Connection.Response var1);

   public interface Base<T extends Connection.Base<T>> {
      URL url();

      T url(URL var1);

      Connection.Method method();

      T method(Connection.Method var1);

      @Nullable
      String header(String var1);

      List<String> headers(String var1);

      T header(String var1, String var2);

      T addHeader(String var1, String var2);

      boolean hasHeader(String var1);

      boolean hasHeaderWithValue(String var1, String var2);

      T removeHeader(String var1);

      Map<String, String> headers();

      Map<String, List<String>> multiHeaders();

      @Nullable
      String cookie(String var1);

      T cookie(String var1, String var2);

      boolean hasCookie(String var1);

      T removeCookie(String var1);

      Map<String, String> cookies();
   }

   public interface KeyVal {
      Connection.KeyVal key(String var1);

      String key();

      Connection.KeyVal value(String var1);

      String value();

      Connection.KeyVal inputStream(InputStream var1);

      @Nullable
      InputStream inputStream();

      boolean hasInputStream();

      Connection.KeyVal contentType(String var1);

      @Nullable
      String contentType();
   }

   public static enum Method {
      GET(false),
      POST(true),
      PUT(true),
      DELETE(false),
      PATCH(true),
      HEAD(false),
      OPTIONS(false),
      TRACE(false);

      private final boolean hasBody;

      private Method(boolean hasBody) {
         this.hasBody = hasBody;
      }

      public final boolean hasBody() {
         return this.hasBody;
      }
   }

   public interface Request extends Connection.Base<Connection.Request> {
      @Nullable
      Proxy proxy();

      Connection.Request proxy(@Nullable Proxy var1);

      Connection.Request proxy(String var1, int var2);

      int timeout();

      Connection.Request timeout(int var1);

      int maxBodySize();

      Connection.Request maxBodySize(int var1);

      boolean followRedirects();

      Connection.Request followRedirects(boolean var1);

      boolean ignoreHttpErrors();

      Connection.Request ignoreHttpErrors(boolean var1);

      boolean ignoreContentType();

      Connection.Request ignoreContentType(boolean var1);

      @Nullable
      SSLSocketFactory sslSocketFactory();

      void sslSocketFactory(SSLSocketFactory var1);

      Connection.Request data(Connection.KeyVal var1);

      Collection<Connection.KeyVal> data();

      Connection.Request requestBody(@Nullable String var1);

      @Nullable
      String requestBody();

      Connection.Request parser(Parser var1);

      Parser parser();

      Connection.Request postDataCharset(String var1);

      String postDataCharset();
   }

   public interface Response extends Connection.Base<Connection.Response> {
      int statusCode();

      String statusMessage();

      @Nullable
      String charset();

      Connection.Response charset(String var1);

      @Nullable
      String contentType();

      Document parse() throws IOException;

      String body();

      byte[] bodyAsBytes();

      Connection.Response bufferUp();

      BufferedInputStream bodyStream();
   }
}
