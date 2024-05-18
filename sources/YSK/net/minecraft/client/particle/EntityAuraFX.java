package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityAuraFX extends EntityFX
{
    protected EntityAuraFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        final float particleBlue = this.rand.nextFloat() * 0.1f + 0.2f;
        this.particleRed = particleBlue;
        this.particleGreen = particleBlue;
        this.particleBlue = particleBlue;
        this.setParticleTextureIndex("".length());
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.5f;
        this.motionX *= 0.019999999552965164;
        this.motionY *= 0.019999999552965164;
        this.motionZ *= 0.019999999552965164;
        this.particleMaxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
        this.noClip = (" ".length() != 0);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.99;
        this.motionY *= 0.99;
        this.motionZ *= 0.99;
        final int particleMaxAge = this.particleMaxAge;
        this.particleMaxAge = particleMaxAge - " ".length();
        if (particleMaxAge <= 0) {
            this.setDead();
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
    
    public static class HappyVillagerFactory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntityAuraFX entityAuraFX = new EntityAuraFX(world, n2, n3, n4, n5, n6, n7);
            entityAuraFX.setParticleTextureIndex(0x92 ^ 0xC0);
            entityAuraFX.setRBGColorF(1.0f, 1.0f, 1.0f);
            return entityAuraFX;
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
                if (3 < 1) {
                    throw null;
                }
            }
            return sb.toString();
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
                if (-1 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityAuraFX(world, n2, n3, n4, n5, n6, n7);
        }
    }
}
