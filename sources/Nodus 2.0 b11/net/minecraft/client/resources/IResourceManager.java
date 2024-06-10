package net.minecraft.client.resources;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

public abstract interface IResourceManager
{
  public abstract Set getResourceDomains();
  
  public abstract IResource getResource(ResourceLocation paramResourceLocation)
    throws IOException;
  
  public abstract List getAllResources(ResourceLocation paramResourceLocation)
    throws IOException;
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.IResourceManager
 * JD-Core Version:    0.7.0.1
 */