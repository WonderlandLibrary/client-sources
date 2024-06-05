package cn.Hanabi.injection.mixins;

import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;

@Mixin({ Render.class })
public abstract class MixinRender
{
    @Shadow
    protected RenderManager renderManager;
    
    @Shadow
    public <T extends Entity> void doRender(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
    }
}
