package exhibition.util.security;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Connection {
   private String url;
   private String json;
   private Map parameters = Maps.newHashMap();
   private Map headers = Maps.newHashMap();

   public Connection(String url) {
      this.setUrl(url);
      this.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.13) Gecko/20080311 Firefox/2.0.0.13");
   }

   private void setUrl(String url) {
      this.url = url;
   }

   public Connection setJson(String json) {
      this.json = json;
      return this;
   }

   public Connection setUserAgent(String userAgent) {
      this.headers.put("User-Agent", userAgent);
      return this;
   }

   public Connection setAccept(String accept) {
      this.headers.put("Accept", accept);
      return this;
   }

   public Connection setContentType(String contentType) {
      this.headers.put("Content-Type", contentType);
      return this;
   }

   public Connection setParameters(String... parameters) {
      for(int i = 0; i < parameters.length; i += 2) {
         this.parameters.put(parameters[i], parameters[i + 1]);
      }

      return this;
   }

   public Connection setHeaders(String... headers) {
      for(int i = 0; i < headers.length; i += 2) {
         this.headers.put(headers[i], headers[i + 1]);
      }

      return this;
   }

   public String getUrl() {
      return this.url;
   }

   public String getJson() {
      return this.json;
   }

   private Map getParameters() {
      return this.parameters;
   }

   public Map getHeaders() {
      return this.headers;
   }

   public String getPayload() {
      if (this.getJson() != null) {
         return this.getJson();
      } else {
         StringBuilder payload = new StringBuilder();
         Iterator var2 = this.parameters.entrySet().iterator();

         while(var2.hasNext()) {
            Entry parameter = (Entry)var2.next();
            payload.append(String.format("%s=%s", parameter.getKey(), parameter.getValue()));
         }

         return payload.toString();
      }
   }
}
