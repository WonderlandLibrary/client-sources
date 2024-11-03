package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.math.BigDecimal;

public enum ToNumberPolicy implements ToNumberStrategy {
   DOUBLE {
      public Double readNumber(JsonReader in) throws IOException {
         return in.nextDouble();
      }
   },
   LAZILY_PARSED_NUMBER {
      @Override
      public Number readNumber(JsonReader in) throws IOException {
         return new LazilyParsedNumber(in.nextString());
      }
   },
   LONG_OR_DOUBLE {
      @Override
      public Number readNumber(JsonReader in) throws IOException, JsonParseException {
         String value = in.nextString();

         try {
            return Long.parseLong(value);
         } catch (NumberFormatException var6) {
            try {
               Double d = Double.valueOf(value);
               if ((d.isInfinite() || d.isNaN()) && !in.isLenient()) {
                  throw new MalformedJsonException("JSON forbids NaN and infinities: " + d + "; at path " + in.getPreviousPath());
               } else {
                  return d;
               }
            } catch (NumberFormatException var5) {
               throw new JsonParseException("Cannot parse " + value + "; at path " + in.getPreviousPath(), var5);
            }
         }
      }
   },
   BIG_DECIMAL {
      public BigDecimal readNumber(JsonReader in) throws IOException {
         String value = in.nextString();

         try {
            return new BigDecimal(value);
         } catch (NumberFormatException var4) {
            throw new JsonParseException("Cannot parse " + value + "; at path " + in.getPreviousPath(), var4);
         }
      }
   };

   private ToNumberPolicy() {
   }
}
