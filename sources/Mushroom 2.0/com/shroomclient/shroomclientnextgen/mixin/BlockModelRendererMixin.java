package com.shroomclient.shroomclientnextgen.mixin;

import com.google.common.collect.Queues;
import com.shroomclient.shroomclientnextgen.hooks.BlockRenderHook;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockModelRenderer.class, priority = 2000)
public class BlockModelRendererMixin {

    @Inject(
        method = {
            "render(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/block/BlockState;Lnet/minecraft/client/render/model/BakedModel;FFFII)V",
        },
        at = { @At("HEAD") }
    )
    private void render(
        MatrixStack.Entry entry,
        VertexConsumer vertexConsumer,
        BlockState blockState,
        BakedModel bakedModel,
        float red,
        float green,
        float blue,
        int light,
        int overlay,
        CallbackInfo ci
    ) {
        System.out.println("a");
        MatrixStack matrixStack = new MatrixStack();
        ((MatrixStackAccessor) matrixStack).setStack(
                Util.make(Queues.newArrayDeque(), stack -> stack.add(entry))
            );
        BlockRenderHook.onRenderBlock(blockState, matrixStack);
    }
}
