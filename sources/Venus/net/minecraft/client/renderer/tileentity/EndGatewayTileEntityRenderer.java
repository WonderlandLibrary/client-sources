/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.BeaconTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.EndPortalTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class EndGatewayTileEntityRenderer
extends EndPortalTileEntityRenderer<EndGatewayTileEntity> {
    private static final ResourceLocation END_GATEWAY_BEAM_TEXTURE = new ResourceLocation("textures/entity/end_gateway_beam.png");

    public EndGatewayTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(EndGatewayTileEntity endGatewayTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        if (endGatewayTileEntity.isSpawning() || endGatewayTileEntity.isCoolingDown()) {
            float f2 = endGatewayTileEntity.isSpawning() ? endGatewayTileEntity.getSpawnPercent(f) : endGatewayTileEntity.getCooldownPercent(f);
            double d = endGatewayTileEntity.isSpawning() ? 256.0 : 50.0;
            f2 = MathHelper.sin(f2 * (float)Math.PI);
            int n3 = MathHelper.floor((double)f2 * d);
            float[] fArray = endGatewayTileEntity.isSpawning() ? DyeColor.MAGENTA.getColorComponentValues() : DyeColor.PURPLE.getColorComponentValues();
            long l = endGatewayTileEntity.getWorld().getGameTime();
            BeaconTileEntityRenderer.renderBeamSegment(matrixStack, iRenderTypeBuffer, END_GATEWAY_BEAM_TEXTURE, f, f2, l, 0, n3, fArray, 0.15f, 0.175f);
            BeaconTileEntityRenderer.renderBeamSegment(matrixStack, iRenderTypeBuffer, END_GATEWAY_BEAM_TEXTURE, f, f2, l, 0, -n3, fArray, 0.15f, 0.175f);
        }
        super.render(endGatewayTileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }

    @Override
    protected int getPasses(double d) {
        return super.getPasses(d) + 1;
    }

    @Override
    protected float getOffset() {
        return 1.0f;
    }

    @Override
    public void render(EndPortalTileEntity endPortalTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((EndGatewayTileEntity)endPortalTileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((EndGatewayTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

