package net.minecraft.client.particle;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class EntityLavaFX extends EntityFX
{
    private float lavaParticleScale;
    
    @Override
    public int getBrightnessForRender(final float n) {
        MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge, 0.0f, 1.0f);
        return 1 + 33 + 66 + 140 | (super.getBrightnessForRender(n) >> (0x50 ^ 0x40) & 98 + 139 - 82 + 100) << (0xA4 ^ 0xB4);
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
            if (3 == 1) {
                throw null;
            }
        }
        return sb.toString();
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
        if (this.rand.nextFloat() > this.particleAge / this.particleMaxAge) {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, new int["".length()]);
        }
        this.motionY -= 0.03;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9990000128746033;
        this.motionY *= 0.9990000128746033;
        this.motionZ *= 0.9990000128746033;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = (this.particleAge + n) / this.particleMaxAge;
        this.particleScale = this.lavaParticleScale * (1.0f - n7 * n7);
        super.renderParticle(worldRenderer, entity, n, n2, n3, n4, n5, n6);
    }
    
    protected EntityLavaFX(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.motionX *= 0.800000011920929;
        this.motionY *= 0.800000011920929;
        this.motionZ *= 0.800000011920929;
        this.motionY = this.rand.nextFloat() * 0.4f + 0.05f;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale *= this.rand.nextFloat() * 2.0f + 0.2f;
        this.lavaParticleScale = this.particleScale;
        this.particleMaxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.noClip = ("".length() != 0);
        this.setParticleTextureIndex(0x6B ^ 0x5A);
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityLavaFX(world, n2, n3, n4);
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
                if (2 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
