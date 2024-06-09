package com.leafclient.leaf.mixin.render;

import com.leafclient.leaf.extension.ExtensionRenderManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager implements ExtensionRenderManager {

    @Shadow private boolean renderOutlines;

    @Override
    public boolean isRenderOutline() {
        return renderOutlines;
    }
}
