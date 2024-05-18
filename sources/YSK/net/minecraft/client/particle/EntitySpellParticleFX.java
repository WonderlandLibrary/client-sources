package net.minecraft.client.particle;

import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class EntitySpellParticleFX extends EntityFX
{
    private static final Random RANDOM;
    private int baseSpellTextureIndex;
    
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        RANDOM = new Random();
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge * 32.0f, 0.0f, 1.0f);
        super.renderParticle(worldRenderer, entity, n, n2, n3, n4, n5, n6);
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
        this.setParticleTextureIndex(this.baseSpellTextureIndex + ((0x5E ^ 0x59) - this.particleAge * (0xA4 ^ 0xAC) / this.particleMaxAge));
        this.motionY += 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    protected EntitySpellParticleFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, 0.5 - EntitySpellParticleFX.RANDOM.nextDouble(), n5, 0.5 - EntitySpellParticleFX.RANDOM.nextDouble());
        this.baseSpellTextureIndex = 36 + 81 - 29 + 40;
        this.motionY *= 0.20000000298023224;
        if (n4 == 0.0 && n6 == 0.0) {
            this.motionX *= 0.10000000149011612;
            this.motionZ *= 0.10000000149011612;
        }
        this.particleScale *= 0.75f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.noClip = ("".length() != 0);
    }
    
    public void setBaseSpellTextureIndex(final int baseSpellTextureIndex) {
        this.baseSpellTextureIndex = baseSpellTextureIndex;
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
                if (1 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
        }
    }
    
    public static class AmbientMobFactory implements IParticleFactory
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
                if (4 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntitySpellParticleFX entitySpellParticleFX = new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
            entitySpellParticleFX.setAlphaF(0.15f);
            entitySpellParticleFX.setRBGColorF((float)n5, (float)n6, (float)n7);
            return entitySpellParticleFX;
        }
    }
    
    public static class WitchFactory implements IParticleFactory
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
                if (4 < 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntitySpellParticleFX entitySpellParticleFX = new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
            entitySpellParticleFX.setBaseSpellTextureIndex(36 + 59 - 10 + 59);
            final float n8 = world.rand.nextFloat() * 0.5f + 0.35f;
            entitySpellParticleFX.setRBGColorF(1.0f * n8, 0.0f * n8, 1.0f * n8);
            return entitySpellParticleFX;
        }
    }
    
    public static class MobFactory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntitySpellParticleFX entitySpellParticleFX = new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
            entitySpellParticleFX.setRBGColorF((float)n5, (float)n6, (float)n7);
            return entitySpellParticleFX;
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
                if (-1 >= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class InstantFactory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntitySpellParticleFX entitySpellParticleFX = new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
            entitySpellParticleFX.setBaseSpellTextureIndex(117 + 82 - 111 + 56);
            return entitySpellParticleFX;
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
                if (4 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
