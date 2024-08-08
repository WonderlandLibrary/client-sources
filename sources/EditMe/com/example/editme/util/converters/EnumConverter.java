package com.example.editme.util.converters;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class EnumConverter extends Converter {
   Class clazz;

   protected JsonElement doForward(Enum var1) {
      return new JsonPrimitive(var1.toString());
   }

   protected Enum doBackward(JsonElement var1) {
      return Enum.valueOf(this.clazz, var1.getAsString());
   }

   public EnumConverter(Class var1) {
      this.clazz = var1;
   }

   protected Object doBackward(Object var1) {
      return this.doBackward((JsonElement)var1);
   }

   protected Object doForward(Object var1) {
      return this.doForward((Enum)var1);
   }
}
