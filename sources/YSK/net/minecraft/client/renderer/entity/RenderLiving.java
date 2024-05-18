package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;

public abstract class RenderLiving<T extends EntityLiving> extends RendererLivingEntity<T>
{
    protected void renderLeash(final T t, double n, double n2, double n3, final float n4, final float n5) {
        final Entity leashedToEntity = t.getLeashedToEntity();
        if (leashedToEntity != null) {
            n2 -= (1.6 - t.height) * 0.5;
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final double n6 = this.interpolateValue(leashedToEntity.prevRotationYaw, leashedToEntity.rotationYaw, n5 * 0.5f) * 0.01745329238474369;
            final double n7 = this.interpolateValue(leashedToEntity.prevRotationPitch, leashedToEntity.rotationPitch, n5 * 0.5f) * 0.01745329238474369;
            double cos = Math.cos(n6);
            double sin = Math.sin(n6);
            double sin2 = Math.sin(n7);
            if (leashedToEntity instanceof EntityHanging) {
                cos = 0.0;
                sin = 0.0;
                sin2 = -1.0;
            }
            final double cos2 = Math.cos(n7);
            final double n8 = this.interpolateValue(leashedToEntity.prevPosX, leashedToEntity.posX, n5) - cos * 0.7 - sin * 0.5 * cos2;
            final double n9 = this.interpolateValue(leashedToEntity.prevPosY + leashedToEntity.getEyeHeight() * 0.7, leashedToEntity.posY + leashedToEntity.getEyeHeight() * 0.7, n5) - sin2 * 0.5 - 0.25;
            final double n10 = this.interpolateValue(leashedToEntity.prevPosZ, leashedToEntity.posZ, n5) - sin * 0.7 + cos * 0.5 * cos2;
            final double n11 = this.interpolateValue(t.prevRenderYawOffset, t.renderYawOffset, n5) * 0.01745329238474369 + 1.5707963267948966;
            final double n12 = Math.cos(n11) * t.width * 0.4;
            final double n13 = Math.sin(n11) * t.width * 0.4;
            final double n14 = this.interpolateValue(t.prevPosX, t.posX, n5) + n12;
            final double interpolateValue = this.interpolateValue(t.prevPosY, t.posY, n5);
            final double n15 = this.interpolateValue(t.prevPosZ, t.posZ, n5) + n13;
            n += n12;
            n3 += n13;
            final double n16 = (float)(n8 - n14);
            final double n17 = (float)(n9 - interpolateValue);
            final double n18 = (float)(n10 - n15);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            worldRenderer.begin(0xAF ^ 0xAA, DefaultVertexFormats.POSITION_COLOR);
            int i = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (i <= (0x4B ^ 0x53)) {
                float n19 = 0.5f;
                float n20 = 0.4f;
                float n21 = 0.3f;
                if (i % "  ".length() == 0) {
                    n19 *= 0.7f;
                    n20 *= 0.7f;
                    n21 *= 0.7f;
                }
                final float n22 = i / 24.0f;
                worldRenderer.pos(n + n16 * n22 + 0.0, n2 + n17 * (n22 * n22 + n22) * 0.5 + ((24.0f - i) / 18.0f + 0.125f), n3 + n18 * n22).color(n19, n20, n21, 1.0f).endVertex();
                worldRenderer.pos(n + n16 * n22 + 0.025, n2 + n17 * (n22 * n22 + n22) * 0.5 + ((24.0f - i) / 18.0f + 0.125f) + 0.025, n3 + n18 * n22).color(n19, n20, n21, 1.0f).endVertex();
                ++i;
            }
            instance.draw();
            worldRenderer.begin(0x5C ^ 0x59, DefaultVertexFormats.POSITION_COLOR);
            int j = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
            while (j <= (0x5B ^ 0x43)) {
                float n23 = 0.5f;
                float n24 = 0.4f;
                float n25 = 0.3f;
                if (j % "  ".length() == 0) {
                    n23 *= 0.7f;
                    n24 *= 0.7f;
                    n25 *= 0.7f;
                }
                final float n26 = j / 24.0f;
                worldRenderer.pos(n + n16 * n26 + 0.0, n2 + n17 * (n26 * n26 + n26) * 0.5 + ((24.0f - j) / 18.0f + 0.125f) + 0.025, n3 + n18 * n26).color(n23, n24, n25, 1.0f).endVertex();
                worldRenderer.pos(n + n16 * n26 + 0.025, n2 + n17 * (n26 * n26 + n26) * 0.5 + ((24.0f - j) / 18.0f + 0.125f), n3 + n18 * n26 + 0.025).color(n23, n24, n25, 1.0f).endVertex();
                ++j;
            }
            instance.draw();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.enableCull();
        }
    }
    
    private double interpolateValue(final double n, final double n2, final double n3) {
        return n + (n2 - n) * n3;
    }
    
    @Override
    protected boolean canRenderName(final T t) {
        if (super.canRenderName(t) && (t.getAlwaysRenderNameTagForRender() || (t.hasCustomName() && t == this.renderManager.pointedEntity))) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void doRender(final T t, final double n, final double n2, final double n3, final float n4, final float n5) {
        super.doRender(t, n, n2, n3, n4, n5);
        this.renderLeash(t, n, n2, n3, n4, n5);
    }
    
    @Override
    protected boolean canRenderName(final EntityLivingBase entityLivingBase) {
        return this.canRenderName((EntityLiving)entityLivingBase);
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
            if (1 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldRender(final Entity entity, final ICamera camera, final double n, final double n2, final double n3) {
        return this.shouldRender((EntityLiving)entity, camera, n, n2, n3);
    }
    
    public void func_177105_a(final T t, final float n) {
        final int brightnessForRender = t.getBrightnessForRender(n);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % (2613 + 57769 - 19265 + 24419) / 1.0f, brightnessForRender / (60724 + 1317 - 52816 + 56311) / 1.0f);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLiving)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    public RenderLiving(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    @Override
    public boolean shouldRender(final T t, final ICamera camera, final double n, final double n2, final double n3) {
        if (super.shouldRender(t, camera, n, n2, n3)) {
            return " ".length() != 0;
        }
        if (t.getLeashed() && t.getLeashedToEntity() != null) {
            return camera.isBoundingBoxInFrustum(t.getLeashedToEntity().getEntityBoundingBox());
        }
        return "".length() != 0;
    }
}
