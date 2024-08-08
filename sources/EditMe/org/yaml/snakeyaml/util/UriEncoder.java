package org.yaml.snakeyaml.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.Escaper;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.PercentEscaper;

public abstract class UriEncoder {
   private static final CharsetDecoder UTF8Decoder;
   private static final String SAFE_CHARS = "-_.!~*'()@:$&,;=[]/";
   private static final Escaper escaper;

   public static String encode(String var0) {
      return escaper.escape(var0);
   }

   public static String decode(ByteBuffer var0) throws CharacterCodingException {
      CharBuffer var1 = UTF8Decoder.decode(var0);
      return var1.toString();
   }

   public static String decode(String var0) {
      try {
         return URLDecoder.decode(var0, "UTF-8");
      } catch (UnsupportedEncodingException var2) {
         throw new YAMLException(var2);
      }
   }

   static {
      UTF8Decoder = Charset.forName("UTF-8").newDecoder().onMalformedInput(CodingErrorAction.REPORT);
      escaper = new PercentEscaper("-_.!~*'()@:$&,;=[]/", false);
   }
}
