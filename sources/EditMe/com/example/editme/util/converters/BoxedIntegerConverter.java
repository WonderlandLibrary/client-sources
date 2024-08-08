package com.example.editme.util.converters;

import com.google.gson.JsonElement;

public class BoxedIntegerConverter extends AbstractBoxedNumberConverter {
   protected Object doBackward(Object var1) {
      return this.doBackward((JsonElement)var1);
   }

   protected Integer doBackward(JsonElement var1) {
      return var1.getAsInt();
   }
}
