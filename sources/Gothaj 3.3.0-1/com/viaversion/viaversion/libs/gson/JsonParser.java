package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
   public static JsonElement parseString(String json) throws JsonSyntaxException {
      return parseReader(new StringReader(json));
   }

   public static JsonElement parseReader(Reader reader) throws JsonIOException, JsonSyntaxException {
      try {
         JsonReader jsonReader = new JsonReader(reader);
         JsonElement element = parseReader(jsonReader);
         if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
            throw new JsonSyntaxException("Did not consume the entire document.");
         } else {
            return element;
         }
      } catch (MalformedJsonException var3) {
         throw new JsonSyntaxException(var3);
      } catch (IOException var4) {
         throw new JsonIOException(var4);
      } catch (NumberFormatException var5) {
         throw new JsonSyntaxException(var5);
      }
   }

   public static JsonElement parseReader(JsonReader reader) throws JsonIOException, JsonSyntaxException {
      boolean lenient = reader.isLenient();
      reader.setLenient(true);

      JsonElement e;
      try {
         e = Streams.parse(reader);
      } catch (StackOverflowError var7) {
         throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", var7);
      } catch (OutOfMemoryError var8) {
         throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", var8);
      } finally {
         reader.setLenient(lenient);
      }

      return e;
   }

   @Deprecated
   public JsonElement parse(String json) throws JsonSyntaxException {
      return parseString(json);
   }

   @Deprecated
   public JsonElement parse(Reader json) throws JsonIOException, JsonSyntaxException {
      return parseReader(json);
   }

   @Deprecated
   public JsonElement parse(JsonReader json) throws JsonIOException, JsonSyntaxException {
      return parseReader(json);
   }
}
