package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_14;

import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12.StringReader_v1_12;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_13.SNbtDeserializer_v1_13;

public class SNbtDeserializer_v1_14 extends SNbtDeserializer_v1_13 {
   @Override
   protected StringReader_v1_12 makeReader(String string) {
      return new StringReader_v1_14(string);
   }

   @Override
   protected boolean isQuote(char c) {
      return c == '"' || c == '\'';
   }
}
