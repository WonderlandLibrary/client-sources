package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntitySplashFX extends EntityRainFX
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected EntitySplashFX(final World world, final double n, final double n2, final double n3, final double motionX, final double n4, final double motionZ) {
        super(world, n, n2, n3);
        this.particleGravity = 0.04f;
        this.nextTextureIndexX();
        if (n4 == 0.0 && (motionX != 0.0 || motionZ != 0.0)) {
            this.motionX = motionX;
            this.motionY = n4 + 0.1;
            this.motionZ = motionZ;
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
                if (-1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntitySplashFX(world, n2, n3, n4, n5, n6, n7);
        }
    }
}
