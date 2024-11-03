package net.silentclient.client.mixin.accessors;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderItem.class)
public interface RenderItemAccessor {
//    @Invoker("renderModel")
//    void renderModel(IBakedModel model, int color);
}
