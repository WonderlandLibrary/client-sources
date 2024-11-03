package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.silentclient.client.hooks.NameTagRenderingHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Render.class)
public abstract class RenderMixin<T extends Entity> {
    /**
     * @author kirillsaint
     * @reason Custom Nametag
     */
    @Overwrite
    protected void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance) {
        NameTagRenderingHooks.renderNametag(entityIn, str, x, y, z, maxDistance, false);
    }

    /**
     * @author kirillsaint
     * @reason Custom Nametag
     */
    @Overwrite
    protected void renderOffsetLivingLabel(T entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_)
    {
        NameTagRenderingHooks.renderNametag(entityIn, str, x, y, z, 64, true);
    }
}
