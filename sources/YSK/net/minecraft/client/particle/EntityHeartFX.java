package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityHeartFX extends EntityFX
{
    float particleScaleOverTime;
    
    protected EntityHeartFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final float n7) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.motionX *= 0.009999999776482582;
        this.motionY *= 0.009999999776482582;
        this.motionZ *= 0.009999999776482582;
        this.motionY += 0.1;
        this.particleScale *= 0.75f;
        this.particleScale *= n7;
        this.particleScaleOverTime = this.particleScale;
        this.particleMaxAge = (0x72 ^ 0x62);
        this.noClip = ("".length() != 0);
        this.setParticleTextureIndex(0x45 ^ 0x15);
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
            if (1 <= -1) {
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
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= 0.8600000143051147;
        this.motionY *= 0.8600000143051147;
        this.motionZ *= 0.8600000143051147;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    protected EntityHeartFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this(world, n, n2, n3, n4, n5, n6, 2.0f);
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.particleScale = this.particleScaleOverTime * MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge * 32.0f, 0.0f, 1.0f);
        super.renderParticle(worldRenderer, entity, n, n2, n3, n4, n5, n6);
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityHeartFX(world, n2, n3, n4, n5, n6, n7);
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
                if (3 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class AngryVillagerFactory implements IParticleFactory
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntityHeartFX entityHeartFX = new EntityHeartFX(world, n2, n3 + 0.5, n4, n5, n6, n7);
            entityHeartFX.setParticleTextureIndex(0x97 ^ 0xC6);
            entityHeartFX.setRBGColorF(1.0f, 1.0f, 1.0f);
            return entityHeartFX;
        }
    }
}
