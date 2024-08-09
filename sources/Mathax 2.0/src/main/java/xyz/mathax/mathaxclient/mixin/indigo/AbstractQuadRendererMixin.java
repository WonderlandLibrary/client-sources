package xyz.mathax.mathaxclient.mixin.indigo;

import xyz.mathax.mathaxclient.systems.modules.render.Xray;
import net.fabricmc.fabric.impl.client.indigo.renderer.mesh.MutableQuadViewImpl;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractQuadRenderer;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(value = AbstractQuadRenderer.class, remap = false)
public abstract class AbstractQuadRendererMixin {
    @Final
    @Shadow
    protected BlockRenderInfo blockInfo;

    @Final
    @Shadow
    protected Function<RenderLayer, VertexConsumer> bufferFunc;

    @Final
    @Shadow
    protected Vector3f normalVec;

    @Shadow
    protected abstract Matrix3f normalMatrix();

    @Shadow
    protected abstract Matrix4f matrix();

    @Shadow
    protected abstract int overlay();

    @Inject(method = "bufferQuad(Lnet/fabricmc/fabric/impl/client/indigo/renderer/mesh/MutableQuadViewImpl;Lnet/minecraft/client/render/RenderLayer;)V", at = @At("HEAD"), cancellable = true, remap = true)
    private void onBufferQuad(MutableQuadViewImpl quad, RenderLayer renderLayer, CallbackInfo info) {
        int alpha = Xray.getAlpha(blockInfo.blockState, blockInfo.blockPos);
        if (alpha == 0) {
            info.cancel();
        } else if (alpha != -1) {
            whBufferQuad(bufferFunc.apply(renderLayer), quad, matrix(), overlay(), normalMatrix(), normalVec, alpha);
            info.cancel();
        }
    }

    @Unique
    private void whBufferQuad(VertexConsumer buff, MutableQuadViewImpl quad, Matrix4f matrix, int overlay, Matrix3f normalMatrix, Vector3f normalVec, int alpha) {
        boolean useNormals = quad.hasVertexNormals();
        if (useNormals) {
            quad.populateMissingNormals();
        } else {
            Vector3f faceNormal = quad.faceNormal();
            normalVec.set(faceNormal.x, faceNormal.y, faceNormal.z);
            normalVec.mul(normalMatrix);
        }

        for (int i = 0; i < 4; i++) {
            buff.vertex(matrix, quad.x(i), quad.y(i), quad.z(i));
            int color = quad.spriteColor(i, 0);
            buff.color(color & 0xFF, (color >> 8) & 0xFF, (color >> 16) & 0xFF, alpha);
            buff.texture(quad.spriteU(i, 0), quad.spriteV(i, 0));
            buff.overlay(overlay);
            buff.light(quad.lightmap(i));

            if (useNormals) {
                normalVec.set(quad.normalX(i), quad.normalY(i), quad.normalZ(i));
                normalVec.mul(normalMatrix);
            }

            buff.normal(normalVec.x, normalVec.y, normalVec.z);
            buff.next();
        }
    }
}
