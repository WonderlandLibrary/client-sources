package com.example.editme.util.converters;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class StringConverter extends Converter {
   protected Object doBackward(Object var1) {
      return this.doBackward((JsonElement)var1);
   }

   protected String doBackward(JsonElement var1) {
      return var1.getAsString();
   }

   protected Object doForward(Object var1) {
      return this.doForward((String)var1);
   }

   protected JsonElement doForward(String var1) {
      return new JsonPrimitive(var1);
   }
}
