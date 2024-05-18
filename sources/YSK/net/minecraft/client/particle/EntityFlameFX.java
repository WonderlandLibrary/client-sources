package net.minecraft.client.particle;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class EntityFlameFX extends EntityFX
{
    private float flameScale;
    
    @Override
    public int getBrightnessForRender(final float n) {
        final float clamp_float = MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge, 0.0f, 1.0f);
        final int brightnessForRender = super.getBrightnessForRender(n);
        final int n2 = brightnessForRender & 87 + 63 + 58 + 47;
        final int n3 = brightnessForRender >> (0xB9 ^ 0xA9) & 182 + 3 - 168 + 238;
        int n4 = n2 + (int)(clamp_float * 15.0f * 16.0f);
        if (n4 > 19 + 116 - 101 + 206) {
            n4 = 227 + 141 - 340 + 212;
        }
        return n4 | n3 << (0xD3 ^ 0xC3);
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected EntityFlameFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.motionX = this.motionX * 0.009999999776482582 + n4;
        this.motionY = this.motionY * 0.009999999776482582 + n5;
        this.motionZ = this.motionZ * 0.009999999776482582 + n6;
        this.posX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        this.posY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        this.posZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        this.flameScale = this.particleScale;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + (0x2 ^ 0x6);
        this.noClip = (" ".length() != 0);
        this.setParticleTextureIndex(0x69 ^ 0x59);
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = (this.particleAge + n) / this.particleMaxAge;
        this.particleScale = this.flameScale * (1.0f - n7 * n7 * 0.5f);
        super.renderParticle(worldRenderer, entity, n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public float getBrightness(final float n) {
        final float clamp_float = MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge, 0.0f, 1.0f);
        return super.getBrightness(n) * clamp_float + (1.0f - clamp_float);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final int particleAge = this.particleAge;
        this.particleAge = particleAge + " ".length();
        if (particleAge >= this.particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    public static class Factory implements IParticleFactory
    {
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
                if (3 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityFlameFX(world, n2, n3, n4, n5, n6, n7);
        }
    }
}
