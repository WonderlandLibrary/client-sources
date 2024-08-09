/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class BeaconTileEntityRenderer
extends TileEntityRenderer<BeaconTileEntity> {
    public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("textures/entity/beacon_beam.png");

    public BeaconTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(BeaconTileEntity beaconTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        long l = beaconTileEntity.getWorld().getGameTime();
        List<BeaconTileEntity.BeamSegment> list = beaconTileEntity.getBeamSegments();
        int n3 = 0;
        for (int i = 0; i < list.size(); ++i) {
            BeaconTileEntity.BeamSegment beamSegment = list.get(i);
            BeaconTileEntityRenderer.renderBeamSegment(matrixStack, iRenderTypeBuffer, f, l, n3, i == list.size() - 1 ? 1024 : beamSegment.getHeight(), beamSegment.getColors());
            n3 += beamSegment.getHeight();
        }
    }

    private static void renderBeamSegment(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, float f, long l, int n, int n2, float[] fArray) {
        BeaconTileEntityRenderer.renderBeamSegment(matrixStack, iRenderTypeBuffer, TEXTURE_BEACON_BEAM, f, 1.0f, l, n, n2, fArray, 0.2f, 0.25f);
    }

    public static void renderBeamSegment(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, ResourceLocation resourceLocation, float f, float f2, long l, int n, int n2, float[] fArray, float f3, float f4) {
        int n3 = n + n2;
        matrixStack.push();
        matrixStack.translate(0.5, 0.0, 0.5);
        float f5 = (float)Math.floorMod(l, 40L) + f;
        float f6 = n2 < 0 ? f5 : -f5;
        float f7 = MathHelper.frac(f6 * 0.2f - (float)MathHelper.floor(f6 * 0.1f));
        float f8 = fArray[0];
        float f9 = fArray[1];
        float f10 = fArray[2];
        matrixStack.push();
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f5 * 2.25f - 45.0f));
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = -f3;
        float f14 = 0.0f;
        float f15 = 0.0f;
        float f16 = -f3;
        float f17 = 0.0f;
        float f18 = 1.0f;
        float f19 = -1.0f + f7;
        float f20 = (float)n2 * f2 * (0.5f / f3) + f19;
        BeaconTileEntityRenderer.renderPart(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.getBeaconBeam(resourceLocation, false)), f8, f9, f10, 1.0f, n, n3, 0.0f, f3, f3, 0.0f, f13, 0.0f, 0.0f, f16, 0.0f, 1.0f, f20, f19);
        matrixStack.pop();
        f11 = -f4;
        float f21 = -f4;
        f12 = -f4;
        f13 = -f4;
        f17 = 0.0f;
        f18 = 1.0f;
        f19 = -1.0f + f7;
        f20 = (float)n2 * f2 + f19;
        BeaconTileEntityRenderer.renderPart(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.getBeaconBeam(resourceLocation, true)), f8, f9, f10, 0.125f, n, n3, f11, f21, f4, f12, f13, f4, f4, f4, 0.0f, 1.0f, f20, f19);
        matrixStack.pop();
    }

    private static void renderPart(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, int n, int n2, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) {
        MatrixStack.Entry entry = matrixStack.getLast();
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        BeaconTileEntityRenderer.addQuad(matrix4f, matrix3f, iVertexBuilder, f, f2, f3, f4, n, n2, f5, f6, f7, f8, f13, f14, f15, f16);
        BeaconTileEntityRenderer.addQuad(matrix4f, matrix3f, iVertexBuilder, f, f2, f3, f4, n, n2, f11, f12, f9, f10, f13, f14, f15, f16);
        BeaconTileEntityRenderer.addQuad(matrix4f, matrix3f, iVertexBuilder, f, f2, f3, f4, n, n2, f7, f8, f11, f12, f13, f14, f15, f16);
        BeaconTileEntityRenderer.addQuad(matrix4f, matrix3f, iVertexBuilder, f, f2, f3, f4, n, n2, f9, f10, f5, f6, f13, f14, f15, f16);
    }

    private static void addQuad(Matrix4f matrix4f, Matrix3f matrix3f, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, int n, int n2, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        BeaconTileEntityRenderer.addVertex(matrix4f, matrix3f, iVertexBuilder, f, f2, f3, f4, n2, f5, f6, f10, f11);
        BeaconTileEntityRenderer.addVertex(matrix4f, matrix3f, iVertexBuilder, f, f2, f3, f4, n, f5, f6, f10, f12);
        BeaconTileEntityRenderer.addVertex(matrix4f, matrix3f, iVertexBuilder, f, f2, f3, f4, n, f7, f8, f9, f12);
        BeaconTileEntityRenderer.addVertex(matrix4f, matrix3f, iVertexBuilder, f, f2, f3, f4, n2, f7, f8, f9, f11);
    }

    private static void addVertex(Matrix4f matrix4f, Matrix3f matrix3f, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, int n, float f5, float f6, float f7, float f8) {
        iVertexBuilder.pos(matrix4f, f5, n, f6).color(f, f2, f3, f4).tex(f7, f8).overlay(OverlayTexture.NO_OVERLAY).lightmap(0xF000F0).normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
    }

    @Override
    public boolean isGlobalRenderer(BeaconTileEntity beaconTileEntity) {
        return false;
    }

    @Override
    public boolean isGlobalRenderer(TileEntity tileEntity) {
        return this.isGlobalRenderer((BeaconTileEntity)tileEntity);
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((BeaconTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

