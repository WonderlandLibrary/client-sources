package ac.sapphire.client.mixin.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderManager.class)
public interface IRenderManagerAccessor {
    @Accessor
    double getRenderPosX();

    @Accessor
    double getRenderPosY();

    @Accessor
    double getRenderPosZ();
}
