package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializer;

public abstract interface IMetadataSectionSerializer
  extends JsonDeserializer
{
  public abstract String getSectionName();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.IMetadataSectionSerializer
 * JD-Core Version:    0.7.0.1
 */