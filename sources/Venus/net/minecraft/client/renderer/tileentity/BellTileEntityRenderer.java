/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.BellTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BellTileEntityRenderer
extends TileEntityRenderer<BellTileEntity> {
    public static final RenderMaterial BELL_BODY_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/bell/bell_body"));
    private final ModelRenderer modelRenderer = new ModelRenderer(32, 32, 0, 0);

    public BellTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
        this.modelRenderer.addBox(-3.0f, -6.0f, -3.0f, 6.0f, 7.0f, 6.0f);
        this.modelRenderer.setRotationPoint(8.0f, 12.0f, 8.0f);
        ModelRenderer modelRenderer = new ModelRenderer(32, 32, 0, 13);
        modelRenderer.addBox(4.0f, 4.0f, 4.0f, 8.0f, 2.0f, 8.0f);
        modelRenderer.setRotationPoint(-8.0f, -12.0f, -8.0f);
        this.modelRenderer.addChild(modelRenderer);
    }

    @Override
    public void render(BellTileEntity bellTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        float f2 = (float)bellTileEntity.ringingTicks + f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        if (bellTileEntity.isRinging) {
            float f5 = MathHelper.sin(f2 / (float)Math.PI) / (4.0f + f2 / 3.0f);
            if (bellTileEntity.ringDirection == Direction.NORTH) {
                f3 = -f5;
            } else if (bellTileEntity.ringDirection == Direction.SOUTH) {
                f3 = f5;
            } else if (bellTileEntity.ringDirection == Direction.EAST) {
                f4 = -f5;
            } else if (bellTileEntity.ringDirection == Direction.WEST) {
                f4 = f5;
            }
        }
        this.modelRenderer.rotateAngleX = f3;
        this.modelRenderer.rotateAngleZ = f4;
        IVertexBuilder iVertexBuilder = BELL_BODY_TEXTURE.getBuffer(iRenderTypeBuffer, RenderType::getEntitySolid);
        this.modelRenderer.render(matrixStack, iVertexBuilder, n, n2);
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((BellTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

