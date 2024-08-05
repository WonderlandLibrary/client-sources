package studio.dreamys.mixin.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import studio.dreamys.entityculling.access.EntityRendererInter;

@Mixin(Render.class)
public abstract class MixinRender<T extends Entity> implements EntityRendererInter<T> {

    @Override
    public boolean shadowShouldShowName(T entity) {
        return canRenderName(entity);
    }

    @Override
    public void shadowRenderNameTag(T p_renderName_1_, double p_renderName_2_, double d1, double d2) {
        renderName(p_renderName_1_, p_renderName_2_, d1, d2);
    }

    @Shadow
    protected abstract void renderName(T p_renderName_1_, double p_renderName_2_, double d1, double d2);
    
    @Shadow
    protected abstract boolean canRenderName(T p_canRenderName_1_);
}
