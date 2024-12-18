/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityEndGatewayRenderer
extends TileEntityEndPortalRenderer {
    private static final ResourceLocation END_GATEWAY_BEAM_TEXTURE = new ResourceLocation("textures/entity/end_gateway_beam.png");

    @Override
    public void func_192841_a(TileEntityEndPortal p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
        GlStateManager.disableFog();
        TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)p_192841_1_;
        if (tileentityendgateway.isSpawning() || tileentityendgateway.isCoolingDown()) {
            GlStateManager.alphaFunc(516, 0.1f);
            this.bindTexture(END_GATEWAY_BEAM_TEXTURE);
            float f = tileentityendgateway.isSpawning() ? tileentityendgateway.getSpawnPercent(p_192841_8_) : tileentityendgateway.getCooldownPercent(p_192841_8_);
            double d0 = tileentityendgateway.isSpawning() ? 256.0 - p_192841_4_ : 50.0;
            f = MathHelper.sin(f * (float)Math.PI);
            int i = MathHelper.floor((double)f * d0);
            float[] afloat = tileentityendgateway.isSpawning() ? EnumDyeColor.MAGENTA.func_193349_f() : EnumDyeColor.PURPLE.func_193349_f();
            TileEntityBeaconRenderer.renderBeamSegment(p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, f, tileentityendgateway.getWorld().getTotalWorldTime(), 0, i, afloat, 0.15, 0.175);
            TileEntityBeaconRenderer.renderBeamSegment(p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, f, tileentityendgateway.getWorld().getTotalWorldTime(), 0, -i, afloat, 0.15, 0.175);
        }
        super.func_192841_a(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, p_192841_9_, p_192841_10_);
        GlStateManager.enableFog();
    }

    @Override
    protected int func_191286_a(double p_191286_1_) {
        return super.func_191286_a(p_191286_1_) + 1;
    }

    @Override
    protected float func_191287_c() {
        return 1.0f;
    }
}

