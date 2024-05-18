package net.minecraft.client.resources;

import java.util.List;

public interface IReloadableResourceManager extends IResourceManager {
  void reloadResources(List<IResourcePack> paramList);
  
  void registerReloadListener(IResourceManagerReloadListener paramIResourceManagerReloadListener);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\IReloadableResourceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */