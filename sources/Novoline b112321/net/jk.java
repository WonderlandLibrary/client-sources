package net;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

public class jk {
   public static CloseableHttpResponse a(CloseableHttpClient var0, HttpUriRequest var1) {
      return var0.execute(var1);
   }
}
