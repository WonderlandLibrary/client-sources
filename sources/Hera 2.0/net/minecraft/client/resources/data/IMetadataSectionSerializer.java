package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializer;

public interface IMetadataSectionSerializer<T extends IMetadataSection> extends JsonDeserializer<T> {
  String getSectionName();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\data\IMetadataSectionSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */