/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.optifine.Config;
import net.optifine.shaders.ShadersRender;

public class EndPortalTileEntityRenderer<T extends EndPortalTileEntity>
extends TileEntityRenderer<T> {
    public static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
    public static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
    private static final Random RANDOM = new Random(31100L);
    private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj(EndPortalTileEntityRenderer::lambda$static$0).collect(ImmutableList.toImmutableList());

    public EndPortalTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(T t, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        if (!Config.isShaders() || !ShadersRender.renderEndPortal(t, f, this.getOffset(), matrixStack, iRenderTypeBuffer, n, n2)) {
            RANDOM.setSeed(31100L);
            double d = ((TileEntity)t).getPos().distanceSq(this.renderDispatcher.renderInfo.getProjectedView(), false);
            int n3 = this.getPasses(d);
            float f2 = this.getOffset();
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            this.renderCube(t, f2, 0.15f, matrix4f, iRenderTypeBuffer.getBuffer(RENDER_TYPES.get(0)));
            for (int i = 1; i < n3; ++i) {
                this.renderCube(t, f2, 2.0f / (float)(18 - i), matrix4f, iRenderTypeBuffer.getBuffer(RENDER_TYPES.get(i)));
            }
        }
    }

    private void renderCube(T t, float f, float f2, Matrix4f matrix4f, IVertexBuilder iVertexBuilder) {
        float f3 = (RANDOM.nextFloat() * 0.5f + 0.1f) * f2;
        float f4 = (RANDOM.nextFloat() * 0.5f + 0.4f) * f2;
        float f5 = (RANDOM.nextFloat() * 0.5f + 0.5f) * f2;
        this.renderFace(t, matrix4f, iVertexBuilder, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, f3, f4, f5, Direction.SOUTH);
        this.renderFace(t, matrix4f, iVertexBuilder, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f3, f4, f5, Direction.NORTH);
        this.renderFace(t, matrix4f, iVertexBuilder, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, f3, f4, f5, Direction.EAST);
        this.renderFace(t, matrix4f, iVertexBuilder, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, f3, f4, f5, Direction.WEST);
        this.renderFace(t, matrix4f, iVertexBuilder, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f3, f4, f5, Direction.DOWN);
        this.renderFace(t, matrix4f, iVertexBuilder, 0.0f, 1.0f, f, f, 1.0f, 1.0f, 0.0f, 0.0f, f3, f4, f5, Direction.UP);
    }

    private void renderFace(T t, Matrix4f matrix4f, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, Direction direction) {
        if (((EndPortalTileEntity)t).shouldRenderFace(direction)) {
            iVertexBuilder.pos(matrix4f, f, f3, f5).color(f9, f10, f11, 1.0f).endVertex();
            iVertexBuilder.pos(matrix4f, f2, f3, f6).color(f9, f10, f11, 1.0f).endVertex();
            iVertexBuilder.pos(matrix4f, f2, f4, f7).color(f9, f10, f11, 1.0f).endVertex();
            iVertexBuilder.pos(matrix4f, f, f4, f8).color(f9, f10, f11, 1.0f).endVertex();
        }
    }

    protected int getPasses(double d) {
        if (d > 36864.0) {
            return 0;
        }
        if (d > 25600.0) {
            return 0;
        }
        if (d > 16384.0) {
            return 0;
        }
        if (d > 9216.0) {
            return 0;
        }
        if (d > 4096.0) {
            return 0;
        }
        if (d > 1024.0) {
            return 0;
        }
        if (d > 576.0) {
            return 0;
        }
        return d > 256.0 ? 14 : 15;
    }

    protected float getOffset() {
        return 0.75f;
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((T)((EndPortalTileEntity)tileEntity), f, matrixStack, iRenderTypeBuffer, n, n2);
    }

    private static RenderType lambda$static$0(int n) {
        return RenderType.getEndPortal(n + 1);
    }
}

