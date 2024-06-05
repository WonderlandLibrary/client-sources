package net.minecraft.util;

import net.minecraft.util.IJsonSerializable;

public class TupleIntJsonSerializable {
   private int integerValue;
   private IJsonSerializable jsonSerializableValue;

   public int getIntegerValue() {
      return this.integerValue;
   }

   public void setIntegerValue(int var1) {
      this.integerValue = var1;
   }

   public IJsonSerializable getJsonSerializableValue() {
      return this.jsonSerializableValue;
   }

   public void setJsonSerializableValue(IJsonSerializable var1) {
      this.jsonSerializableValue = var1;
   }
}
