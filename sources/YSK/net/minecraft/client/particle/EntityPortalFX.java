package net.minecraft.client.particle;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class EntityPortalFX extends EntityFX
{
    private double portalPosY;
    private float portalParticleScale;
    private double portalPosX;
    private double portalPosZ;
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = 1.0f - (this.particleAge + n) / this.particleMaxAge;
        this.particleScale = this.portalParticleScale * (1.0f - n7 * n7);
        super.renderParticle(worldRenderer, entity, n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final float n = this.particleAge / this.particleMaxAge;
        final float n2 = 1.0f - (-n + n * n * 2.0f);
        this.posX = this.portalPosX + this.motionX * n2;
        this.posY = this.portalPosY + this.motionY * n2 + (1.0f - n2);
        this.posZ = this.portalPosZ + this.motionZ * n2;
        final int particleAge = this.particleAge;
        this.particleAge = particleAge + " ".length();
        if (particleAge >= this.particleMaxAge) {
            this.setDead();
        }
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        final int brightnessForRender = super.getBrightnessForRender(n);
        final float n2 = this.particleAge / this.particleMaxAge;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        final int n5 = brightnessForRender & 113 + 147 - 155 + 150;
        int n6 = (brightnessForRender >> (0x5F ^ 0x4F) & 120 + 225 - 240 + 150) + (int)(n4 * 15.0f * 16.0f);
        if (n6 > 177 + 209 - 221 + 75) {
            n6 = 50 + 158 - 79 + 111;
        }
        return n5 | n6 << (0x2C ^ 0x3C);
    }
    
    @Override
    public float getBrightness(final float n) {
        final float brightness = super.getBrightness(n);
        final float n2 = this.particleAge / this.particleMaxAge;
        final float n3 = n2 * n2 * n2 * n2;
        return brightness * (1.0f - n3) + n3;
    }
    
    protected EntityPortalFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ) {
        super(world, n, n2, n3, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.posX = n;
        this.portalPosX = n;
        this.posY = n2;
        this.portalPosY = n2;
        this.posZ = n3;
        this.portalPosZ = n3;
        final float n4 = this.rand.nextFloat() * 0.6f + 0.4f;
        final float n5 = this.rand.nextFloat() * 0.2f + 0.5f;
        this.particleScale = n5;
        this.portalParticleScale = n5;
        final float particleRed = 1.0f * n4;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGreen *= 0.3f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + (0x8D ^ 0xA5);
        this.noClip = (" ".length() != 0);
        this.setParticleTextureIndex((int)(Math.random() * 8.0));
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityPortalFX(world, n2, n3, n4, n5, n6, n7);
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
