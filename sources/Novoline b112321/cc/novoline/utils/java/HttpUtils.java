package cc.novoline.utils.java;

import cc.novoline.utils.java.Checks;
import cc.novoline.utils.java.FilteredArrayList;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

public final class HttpUtils {
   private static final JsonParser PARSER = new JsonParser();
   private static final Gson GSON = (new GsonBuilder()).disableHtmlEscaping().serializeNulls().excludeFieldsWithoutExposeAnnotation().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

   private HttpUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static JsonElement parseConnectionInput(HttpURLConnection param0, Consumer param1, Class param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static HttpURLConnection createConnection(String var0) throws IOException {
      Checks.notBlank(var0, "URL");
      HttpURLConnection var2 = (HttpURLConnection)(new URL(var0)).openConnection();
      FilteredArrayList.c();
      var2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36 OPR/65.0.3467.62");
      var2.setRequestProperty("Content-Type", "application/json");
      var2.setConnectTimeout(15000);
      var2.setRequestMethod("GET");
      var2.setDoOutput(true);
      return var2;
   }

   public static JsonParser getParser() {
      return PARSER;
   }

   public static Gson getGson() {
      return GSON;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
