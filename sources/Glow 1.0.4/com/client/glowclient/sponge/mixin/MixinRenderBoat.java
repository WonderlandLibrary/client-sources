package com.client.glowclient.sponge.mixin;

import net.minecraft.entity.item.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;

@Mixin({ RenderBoat.class })
public abstract class MixinRenderBoat extends Render<EntityBoat>
{
    public MixinRenderBoat() {
        super((RenderManager)null);
    }
}
