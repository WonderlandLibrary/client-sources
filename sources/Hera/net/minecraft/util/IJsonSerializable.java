package net.minecraft.util;

import com.google.gson.JsonElement;

public interface IJsonSerializable {
  void fromJson(JsonElement paramJsonElement);
  
  JsonElement getSerializableElement();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\IJsonSerializable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */