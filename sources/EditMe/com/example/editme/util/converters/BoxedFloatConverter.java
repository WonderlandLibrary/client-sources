package com.example.editme.util.converters;

import com.google.gson.JsonElement;

public class BoxedFloatConverter extends AbstractBoxedNumberConverter {
   protected Object doBackward(Object var1) {
      return this.doBackward((JsonElement)var1);
   }

   protected Float doBackward(JsonElement var1) {
      return var1.getAsFloat();
   }
}
