package com.example.editme.util.converters;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanConverter extends Converter {
   protected JsonElement doForward(Boolean var1) {
      return new JsonPrimitive(var1);
   }

   protected Object doForward(Object var1) {
      return this.doForward((Boolean)var1);
   }

   protected Boolean doBackward(JsonElement var1) {
      return var1.getAsBoolean();
   }

   protected Object doBackward(Object var1) {
      return this.doBackward((JsonElement)var1);
   }
}
