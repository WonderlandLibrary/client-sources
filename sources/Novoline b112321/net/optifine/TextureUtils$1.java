package net.optifine;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.optifine.TextureUtils;

final class TextureUtils$1 implements IResourceManagerReloadListener {
   public void onResourceManagerReload(IResourceManager var1) {
      TextureUtils.resourcesReloaded(var1);
   }
}
