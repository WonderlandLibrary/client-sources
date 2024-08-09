/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.DyeColor;
import net.minecraft.state.StateHolder;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class BannerTileEntityRenderer
extends TileEntityRenderer<BannerTileEntity> {
    private final ModelRenderer field_228833_a_ = BannerTileEntityRenderer.getModelRender();
    private final ModelRenderer field_228834_c_ = new ModelRenderer(64, 64, 44, 0);
    private final ModelRenderer field_228835_d_;

    public BannerTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
        this.field_228834_c_.addBox(-1.0f, -30.0f, -1.0f, 2.0f, 42.0f, 2.0f, 0.0f);
        this.field_228835_d_ = new ModelRenderer(64, 64, 0, 42);
        this.field_228835_d_.addBox(-10.0f, -32.0f, -1.0f, 20.0f, 2.0f, 2.0f, 0.0f);
    }

    public static ModelRenderer getModelRender() {
        ModelRenderer modelRenderer = new ModelRenderer(64, 64, 0, 0);
        modelRenderer.addBox(-10.0f, 0.0f, -2.0f, 20.0f, 40.0f, 1.0f, 0.0f);
        return modelRenderer;
    }

    @Override
    public void render(BannerTileEntity bannerTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        List<Pair<BannerPattern, DyeColor>> list = bannerTileEntity.getPatternList();
        if (list != null) {
            Object object;
            long l;
            float f2 = 0.6666667f;
            boolean bl = bannerTileEntity.getWorld() == null;
            matrixStack.push();
            if (bl) {
                l = 0L;
                matrixStack.translate(0.5, 0.5, 0.5);
                this.field_228834_c_.showModel = true;
            } else {
                l = bannerTileEntity.getWorld().getGameTime();
                object = bannerTileEntity.getBlockState();
                if (((AbstractBlock.AbstractBlockState)object).getBlock() instanceof BannerBlock) {
                    matrixStack.translate(0.5, 0.5, 0.5);
                    var13_12 = (float)(-((StateHolder)object).get(BannerBlock.ROTATION).intValue() * 360) / 16.0f;
                    matrixStack.rotate(Vector3f.YP.rotationDegrees(var13_12));
                    this.field_228834_c_.showModel = true;
                } else {
                    matrixStack.translate(0.5, -0.1666666716337204, 0.5);
                    var13_12 = -((StateHolder)object).get(WallBannerBlock.HORIZONTAL_FACING).getHorizontalAngle();
                    matrixStack.rotate(Vector3f.YP.rotationDegrees(var13_12));
                    matrixStack.translate(0.0, -0.3125, -0.4375);
                    this.field_228834_c_.showModel = false;
                }
            }
            matrixStack.push();
            matrixStack.scale(0.6666667f, -0.6666667f, -0.6666667f);
            object = ModelBakery.LOCATION_BANNER_BASE.getBuffer(iRenderTypeBuffer, RenderType::getEntitySolid);
            this.field_228834_c_.render(matrixStack, (IVertexBuilder)object, n, n2);
            this.field_228835_d_.render(matrixStack, (IVertexBuilder)object, n, n2);
            BlockPos blockPos = bannerTileEntity.getPos();
            float f3 = ((float)Math.floorMod((long)(blockPos.getX() * 7 + blockPos.getY() * 9 + blockPos.getZ() * 13) + l, 100L) + f) / 100.0f;
            this.field_228833_a_.rotateAngleX = (-0.0125f + 0.01f * MathHelper.cos((float)Math.PI * 2 * f3)) * (float)Math.PI;
            this.field_228833_a_.rotationPointY = -32.0f;
            BannerTileEntityRenderer.func_230180_a_(matrixStack, iRenderTypeBuffer, n, n2, this.field_228833_a_, ModelBakery.LOCATION_BANNER_BASE, true, list);
            matrixStack.pop();
            matrixStack.pop();
        }
    }

    public static void func_230180_a_(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2, ModelRenderer modelRenderer, RenderMaterial renderMaterial, boolean bl, List<Pair<BannerPattern, DyeColor>> list) {
        BannerTileEntityRenderer.func_241717_a_(matrixStack, iRenderTypeBuffer, n, n2, modelRenderer, renderMaterial, bl, list, false);
    }

    public static void func_241717_a_(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2, ModelRenderer modelRenderer, RenderMaterial renderMaterial, boolean bl, List<Pair<BannerPattern, DyeColor>> list, boolean bl2) {
        modelRenderer.render(matrixStack, renderMaterial.getItemRendererBuffer(iRenderTypeBuffer, RenderType::getEntitySolid, bl2), n, n2);
        for (int i = 0; i < 17 && i < list.size(); ++i) {
            Pair<BannerPattern, DyeColor> pair = list.get(i);
            float[] fArray = pair.getSecond().getColorComponentValues();
            RenderMaterial renderMaterial2 = new RenderMaterial(bl ? Atlases.BANNER_ATLAS : Atlases.SHIELD_ATLAS, pair.getFirst().getTextureLocation(bl));
            modelRenderer.render(matrixStack, renderMaterial2.getBuffer(iRenderTypeBuffer, RenderType::getEntityNoOutline), n, n2, fArray[0], fArray[1], fArray[2], 1.0f);
        }
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((BannerTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

