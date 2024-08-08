package com.example.editme.util.converters;

import com.google.gson.JsonElement;

public class BoxedDoubleConverter extends AbstractBoxedNumberConverter {
   protected Object doBackward(Object var1) {
      return this.doBackward((JsonElement)var1);
   }

   protected Double doBackward(JsonElement var1) {
      return var1.getAsDouble();
   }
}
