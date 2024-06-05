package net;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class O1 {
   public static Decoder b() {
      return Base64.getDecoder();
   }

   public static Encoder a() {
      return Base64.getEncoder();
   }
}
