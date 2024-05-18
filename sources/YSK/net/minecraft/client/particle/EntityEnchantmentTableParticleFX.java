package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityEnchantmentTableParticleFX extends EntityFX
{
    private double coordX;
    private float field_70565_a;
    private double coordY;
    private double coordZ;
    
    @Override
    public float getBrightness(final float n) {
        final float brightness = super.getBrightness(n);
        final float n2 = this.particleAge / this.particleMaxAge;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        return brightness * (1.0f - n4) + n4;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        final int brightnessForRender = super.getBrightnessForRender(n);
        final float n2 = this.particleAge / this.particleMaxAge;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        final int n5 = brightnessForRender & 54 + 199 - 171 + 173;
        int n6 = (brightnessForRender >> (0x99 ^ 0x89) & 55 + 154 - 205 + 251) + (int)(n4 * 15.0f * 16.0f);
        if (n6 > 75 + 86 - 91 + 170) {
            n6 = 19 + 152 - 107 + 176;
        }
        return n5 | n6 << (0xB8 ^ 0xA8);
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
    
    protected EntityEnchantmentTableParticleFX(final World world, final double coordX, final double coordY, final double coordZ, final double motionX, final double motionY, final double motionZ) {
        super(world, coordX, coordY, coordZ, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = coordZ;
        final double n = coordX + motionX;
        this.prevPosX = n;
        this.posX = n;
        final double n2 = coordY + motionY;
        this.prevPosY = n2;
        this.posY = n2;
        final double n3 = coordZ + motionZ;
        this.prevPosZ = n3;
        this.posZ = n3;
        final float n4 = this.rand.nextFloat() * 0.6f + 0.4f;
        final float n5 = this.rand.nextFloat() * 0.5f + 0.2f;
        this.particleScale = n5;
        this.field_70565_a = n5;
        final float particleRed = 1.0f * n4;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGreen *= 0.9f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + (0x70 ^ 0x6E);
        this.noClip = (" ".length() != 0);
        this.setParticleTextureIndex((int)(Math.random() * 26.0 + 1.0 + 224.0));
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final float n = 1.0f - this.particleAge / this.particleMaxAge;
        final float n2 = 1.0f - n;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        this.posX = this.coordX + this.motionX * n;
        this.posY = this.coordY + this.motionY * n - n4 * 1.2f;
        this.posZ = this.coordZ + this.motionZ * n;
        final int particleAge = this.particleAge;
        this.particleAge = particleAge + " ".length();
        if (particleAge >= this.particleMaxAge) {
            this.setDead();
        }
    }
    
    public static class EnchantmentTable implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityEnchantmentTableParticleFX(world, n2, n3, n4, n5, n6, n7);
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
                if (1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
