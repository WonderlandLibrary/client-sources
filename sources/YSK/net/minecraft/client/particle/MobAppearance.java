package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;

public class MobAppearance extends EntityFX
{
    private EntityLivingBase entity;
    
    protected MobAppearance(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.particleGravity = 0.0f;
        this.particleMaxAge = (0x44 ^ 0x5A);
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        if (this.entity != null) {
            final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
            renderManager.setRenderPosition(EntityFX.interpPosX, EntityFX.interpPosY, EntityFX.interpPosZ);
            final float n7 = 0.42553192f;
            final float n8 = (this.particleAge + n) / this.particleMaxAge;
            GlStateManager.depthMask(" ".length() != 0);
            GlStateManager.enableBlend();
            GlStateManager.enableDepth();
            GlStateManager.blendFunc(608 + 91 - 69 + 140, 737 + 132 - 459 + 361);
            final float n9 = 240.0f;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, n9, n9);
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 0.05f + 0.5f * MathHelper.sin(n8 * 3.1415927f));
            GlStateManager.translate(0.0f, 1.8f, 0.0f);
            GlStateManager.rotate(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(60.0f - 150.0f * n8 - entity.rotationPitch, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.4f, -1.5f);
            GlStateManager.scale(n7, n7, n7);
            final EntityLivingBase entity2 = this.entity;
            final EntityLivingBase entity3 = this.entity;
            final float n10 = 0.0f;
            entity3.prevRotationYaw = n10;
            entity2.rotationYaw = n10;
            final EntityLivingBase entity4 = this.entity;
            final EntityLivingBase entity5 = this.entity;
            final float n11 = 0.0f;
            entity5.prevRotationYawHead = n11;
            entity4.rotationYawHead = n11;
            renderManager.renderEntityWithPosYaw(this.entity, 0.0, 0.0, 0.0, 0.0f, n);
            GlStateManager.popMatrix();
            GlStateManager.enableDepth();
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.entity == null) {
            final EntityGuardian entity = new EntityGuardian(this.worldObj);
            entity.setElder();
            this.entity = entity;
        }
    }
    
    @Override
    public int getFXLayer() {
        return "   ".length();
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new MobAppearance(world, n2, n3, n4);
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
    }
}
