package org.jsoup.helper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.jsoup.Connection;
import org.jsoup.internal.StringUtil;

class CookieUtil {
   private static final Map<String, List<String>> EmptyRequestHeaders = Collections.unmodifiableMap(new HashMap<>());
   private static final String Sep = "; ";
   private static final String CookieName = "Cookie";
   private static final String Cookie2Name = "Cookie2";

   static void applyCookiesToRequest(HttpConnection.Request req, HttpURLConnection con) throws IOException {
      Set<String> cookieSet = requestCookieSet(req);
      Set<String> cookies2 = null;
      Map<String, List<String>> storedCookies = req.cookieManager().get(asUri(req.url), EmptyRequestHeaders);

      for (Entry<String, List<String>> entry : storedCookies.entrySet()) {
         List<String> cookies = entry.getValue();
         if (cookies != null && cookies.size() != 0) {
            String key = entry.getKey();
            Set<String> set;
            if ("Cookie".equals(key)) {
               set = cookieSet;
            } else {
               if (!"Cookie2".equals(key)) {
                  continue;
               }

               set = new HashSet<>();
               cookies2 = set;
            }

            set.addAll(cookies);
         }
      }

      if (cookieSet.size() > 0) {
         con.addRequestProperty("Cookie", StringUtil.join(cookieSet, "; "));
      }

      if (cookies2 != null && cookies2.size() > 0) {
         con.addRequestProperty("Cookie2", StringUtil.join(cookies2, "; "));
      }
   }

   private static LinkedHashSet<String> requestCookieSet(Connection.Request req) {
      LinkedHashSet<String> set = new LinkedHashSet<>();

      for (Entry<String, String> cookie : req.cookies().entrySet()) {
         set.add(cookie.getKey() + "=" + cookie.getValue());
      }

      return set;
   }

   static URI asUri(URL url) throws IOException {
      try {
         return url.toURI();
      } catch (URISyntaxException var3) {
         MalformedURLException ue = new MalformedURLException(var3.getMessage());
         ue.initCause(var3);
         throw ue;
      }
   }

   static void storeCookies(HttpConnection.Request req, URL url, Map<String, List<String>> resHeaders) throws IOException {
      req.cookieManager().put(asUri(url), resHeaders);
   }
}
