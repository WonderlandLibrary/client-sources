package com.example.editme.util.converters;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public abstract class AbstractBoxedNumberConverter extends Converter {
   protected Object doForward(Object var1) {
      return this.doForward((Number)var1);
   }

   protected JsonElement doForward(Number var1) {
      return new JsonPrimitive(var1);
   }
}
