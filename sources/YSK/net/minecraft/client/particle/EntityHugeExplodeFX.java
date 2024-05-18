package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityHugeExplodeFX extends EntityFX
{
    private int maximumTime;
    private int timeSinceStart;
    
    protected EntityHugeExplodeFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.maximumTime = (0x6 ^ 0xE);
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
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
    
    @Override
    public void onUpdate() {
        int i = "".length();
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (i < (0xBC ^ 0xBA)) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0, this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0, this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0, this.timeSinceStart / this.maximumTime, 0.0, 0.0, new int["".length()]);
            ++i;
        }
        this.timeSinceStart += " ".length();
        if (this.timeSinceStart == this.maximumTime) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return " ".length();
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityHugeExplodeFX(world, n2, n3, n4, n5, n6, n7);
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
                if (1 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
