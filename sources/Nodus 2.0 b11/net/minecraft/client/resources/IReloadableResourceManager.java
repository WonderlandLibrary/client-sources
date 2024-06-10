package net.minecraft.client.resources;

import java.util.List;

public abstract interface IReloadableResourceManager
  extends IResourceManager
{
  public abstract void reloadResources(List paramList);
  
  public abstract void registerReloadListener(IResourceManagerReloadListener paramIResourceManagerReloadListener);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.IReloadableResourceManager
 * JD-Core Version:    0.7.0.1
 */