package org.jsoup;

import java.io.IOException;

public class HttpStatusException extends IOException {
   private final int statusCode;
   private final String url;

   public HttpStatusException(String message, int statusCode, String url) {
      super(message + ". Status=" + statusCode + ", URL=[" + url + "]");
      this.statusCode = statusCode;
      this.url = url;
   }

   public int getStatusCode() {
      return this.statusCode;
   }

   public String getUrl() {
      return this.url;
   }
}
