/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.ConduitTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class ConduitTileEntityRenderer
extends TileEntityRenderer<ConduitTileEntity> {
    public static final RenderMaterial BASE_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/base"));
    public static final RenderMaterial CAGE_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/cage"));
    public static final RenderMaterial WIND_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/wind"));
    public static final RenderMaterial VERTICAL_WIND_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/wind_vertical"));
    public static final RenderMaterial OPEN_EYE_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/open_eye"));
    public static final RenderMaterial CLOSED_EYE_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/closed_eye"));
    private final ModelRenderer field_228872_h_ = new ModelRenderer(16, 16, 0, 0);
    private final ModelRenderer field_228873_i_;
    private final ModelRenderer field_228874_j_;
    private final ModelRenderer field_228875_k_;

    public ConduitTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
        this.field_228872_h_.addBox(-4.0f, -4.0f, 0.0f, 8.0f, 8.0f, 0.0f, 0.01f);
        this.field_228873_i_ = new ModelRenderer(64, 32, 0, 0);
        this.field_228873_i_.addBox(-8.0f, -8.0f, -8.0f, 16.0f, 16.0f, 16.0f);
        this.field_228874_j_ = new ModelRenderer(32, 16, 0, 0);
        this.field_228874_j_.addBox(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f);
        this.field_228875_k_ = new ModelRenderer(32, 16, 0, 0);
        this.field_228875_k_.addBox(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
    }

    @Override
    public void render(ConduitTileEntity conduitTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        float f2 = (float)conduitTileEntity.ticksExisted + f;
        if (!conduitTileEntity.isActive()) {
            float f3 = conduitTileEntity.getActiveRotation(0.0f);
            IVertexBuilder iVertexBuilder = BASE_TEXTURE.getBuffer(iRenderTypeBuffer, RenderType::getEntitySolid);
            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(f3));
            this.field_228874_j_.render(matrixStack, iVertexBuilder, n, n2);
            matrixStack.pop();
        } else {
            float f4 = conduitTileEntity.getActiveRotation(f) * 57.295776f;
            float f5 = MathHelper.sin(f2 * 0.1f) / 2.0f + 0.5f;
            f5 = f5 * f5 + f5;
            matrixStack.push();
            matrixStack.translate(0.5, 0.3f + f5 * 0.2f, 0.5);
            Vector3f vector3f = new Vector3f(0.5f, 1.0f, 0.5f);
            vector3f.normalize();
            matrixStack.rotate(new Quaternion(vector3f, f4, true));
            this.field_228875_k_.render(matrixStack, CAGE_TEXTURE.getBuffer(iRenderTypeBuffer, RenderType::getEntityCutoutNoCull), n, n2);
            matrixStack.pop();
            int n3 = conduitTileEntity.ticksExisted / 66 % 3;
            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            if (n3 == 1) {
                matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
            } else if (n3 == 2) {
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0f));
            }
            IVertexBuilder iVertexBuilder = (n3 == 1 ? VERTICAL_WIND_TEXTURE : WIND_TEXTURE).getBuffer(iRenderTypeBuffer, RenderType::getEntityCutoutNoCull);
            this.field_228873_i_.render(matrixStack, iVertexBuilder, n, n2);
            matrixStack.pop();
            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.scale(0.875f, 0.875f, 0.875f);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(180.0f));
            this.field_228873_i_.render(matrixStack, iVertexBuilder, n, n2);
            matrixStack.pop();
            ActiveRenderInfo activeRenderInfo = this.renderDispatcher.renderInfo;
            matrixStack.push();
            matrixStack.translate(0.5, 0.3f + f5 * 0.2f, 0.5);
            matrixStack.scale(0.5f, 0.5f, 0.5f);
            float f6 = -activeRenderInfo.getYaw();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(f6));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(activeRenderInfo.getPitch()));
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(180.0f));
            float f7 = 1.3333334f;
            matrixStack.scale(1.3333334f, 1.3333334f, 1.3333334f);
            this.field_228872_h_.render(matrixStack, (conduitTileEntity.isEyeOpen() ? OPEN_EYE_TEXTURE : CLOSED_EYE_TEXTURE).getBuffer(iRenderTypeBuffer, RenderType::getEntityCutoutNoCull), n, n2);
            matrixStack.pop();
        }
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((ConduitTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

