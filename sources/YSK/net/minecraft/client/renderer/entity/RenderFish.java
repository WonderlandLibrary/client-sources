package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderFish extends Render<EntityFishHook>
{
    private static final ResourceLocation FISH_PARTICLES;
    private static final String[] I;
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityFishHook entityFishHook) {
        return RenderFish.FISH_PARTICLES;
    }
    
    @Override
    public void doRender(final EntityFishHook entityFishHook, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        this.bindEntityTexture(entityFishHook);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        " ".length();
        "  ".length();
        GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        worldRenderer.begin(0x4A ^ 0x4D, DefaultVertexFormats.POSITION_TEX_NORMAL);
        worldRenderer.pos(-0.5, -0.5, 0.0).tex(0.0625, 0.1875).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(0.5, -0.5, 0.0).tex(0.125, 0.1875).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(0.5, 0.5, 0.0).tex(0.125, 0.125).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(-0.5, 0.5, 0.0).tex(0.0625, 0.125).normal(0.0f, 1.0f, 0.0f).endVertex();
        instance.draw();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        if (entityFishHook.angler != null) {
            final float sin = MathHelper.sin(MathHelper.sqrt_float(entityFishHook.angler.getSwingProgress(n5)) * 3.1415927f);
            final Vec3 rotatePitch = new Vec3(-0.36, 0.03, 0.35).rotatePitch(-(entityFishHook.angler.prevRotationPitch + (entityFishHook.angler.rotationPitch - entityFishHook.angler.prevRotationPitch) * n5) * 3.1415927f / 180.0f).rotateYaw(-(entityFishHook.angler.prevRotationYaw + (entityFishHook.angler.rotationYaw - entityFishHook.angler.prevRotationYaw) * n5) * 3.1415927f / 180.0f).rotateYaw(sin * 0.5f).rotatePitch(-sin * 0.7f);
            double n6 = entityFishHook.angler.prevPosX + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * n5 + rotatePitch.xCoord;
            double n7 = entityFishHook.angler.prevPosY + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * n5 + rotatePitch.yCoord;
            double n8 = entityFishHook.angler.prevPosZ + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * n5 + rotatePitch.zCoord;
            double n9 = entityFishHook.angler.getEyeHeight();
            if ((this.renderManager.options != null && this.renderManager.options.thirdPersonView > 0) || entityFishHook.angler != Minecraft.getMinecraft().thePlayer) {
                final float n10 = (entityFishHook.angler.prevRenderYawOffset + (entityFishHook.angler.renderYawOffset - entityFishHook.angler.prevRenderYawOffset) * n5) * 3.1415927f / 180.0f;
                final double n11 = MathHelper.sin(n10);
                final double n12 = MathHelper.cos(n10);
                n6 = entityFishHook.angler.prevPosX + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * n5 - n12 * 0.35 - n11 * 0.8;
                n7 = entityFishHook.angler.prevPosY + n9 + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * n5 - 0.45;
                n8 = entityFishHook.angler.prevPosZ + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * n5 - n11 * 0.35 + n12 * 0.8;
                double n13;
                if (entityFishHook.angler.isSneaking()) {
                    n13 = -0.1875;
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    n13 = 0.0;
                }
                n9 = n13;
            }
            final double n14 = entityFishHook.prevPosX + (entityFishHook.posX - entityFishHook.prevPosX) * n5;
            final double n15 = entityFishHook.prevPosY + (entityFishHook.posY - entityFishHook.prevPosY) * n5 + 0.25;
            final double n16 = entityFishHook.prevPosZ + (entityFishHook.posZ - entityFishHook.prevPosZ) * n5;
            final double n17 = (float)(n6 - n14);
            final double n18 = (float)(n7 - n15) + n9;
            final double n19 = (float)(n8 - n16);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            worldRenderer.begin("   ".length(), DefaultVertexFormats.POSITION_COLOR);
            int i = "".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
            while (i <= (0xD0 ^ 0xC0)) {
                final float n20 = i / 16.0f;
                worldRenderer.pos(n + n17 * n20, n2 + n18 * (n20 * n20 + n20) * 0.5 + 0.25, n3 + n19 * n20).color("".length(), "".length(), "".length(), 38 + 49 + 57 + 111).endVertex();
                ++i;
            }
            instance.draw();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            super.doRender(entityFishHook, n, n2, n3, n4, n5);
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityFishHook)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityFishHook)entity, n, n2, n3, n4, n5);
    }
    
    static {
        I();
        FISH_PARTICLES = new ResourceLocation(RenderFish.I["".length()]);
    }
    
    public RenderFish(final RenderManager renderManager) {
        super(renderManager);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0007\u0013)8;\u0001\u0013\"c>\u0012\u0004%%-\u001f\u0013~</\u0001\u00028/\"\u0016\u0005\u007f< \u0014", "svQLN");
    }
}
