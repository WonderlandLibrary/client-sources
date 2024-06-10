package net.minecraft.client.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public abstract interface IResourcePack
{
  public abstract InputStream getInputStream(ResourceLocation paramResourceLocation)
    throws IOException;
  
  public abstract boolean resourceExists(ResourceLocation paramResourceLocation);
  
  public abstract Set getResourceDomains();
  
  public abstract IMetadataSection getPackMetadata(IMetadataSerializer paramIMetadataSerializer, String paramString)
    throws IOException;
  
  public abstract BufferedImage getPackImage()
    throws IOException;
  
  public abstract String getPackName();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.IResourcePack
 * JD-Core Version:    0.7.0.1
 */