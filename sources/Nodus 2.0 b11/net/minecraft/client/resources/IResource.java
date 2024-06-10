package net.minecraft.client.resources;

import java.io.InputStream;
import net.minecraft.client.resources.data.IMetadataSection;

public abstract interface IResource
{
  public abstract InputStream getInputStream();
  
  public abstract boolean hasMetadata();
  
  public abstract IMetadataSection getMetadata(String paramString);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.IResource
 * JD-Core Version:    0.7.0.1
 */