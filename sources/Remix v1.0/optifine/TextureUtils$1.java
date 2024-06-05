package optifine;

import net.minecraft.client.resources.*;

static final class TextureUtils$1 implements IResourceManagerReloadListener {
    @Override
    public void onResourceManagerReload(final IResourceManager var1) {
        TextureUtils.resourcesReloaded(var1);
    }
}