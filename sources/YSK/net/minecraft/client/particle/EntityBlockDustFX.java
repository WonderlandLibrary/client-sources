package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class EntityBlockDustFX extends EntityDiggingFX
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
    
    protected EntityBlockDustFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ, final IBlockState blockState) {
        super(world, n, n2, n3, motionX, motionY, motionZ, blockState);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
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
                if (0 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final IBlockState stateById = Block.getStateById(array["".length()]);
            EntityFX func_174845_l;
            if (stateById.getBlock().getRenderType() == -" ".length()) {
                func_174845_l = null;
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                func_174845_l = new EntityBlockDustFX(world, n2, n3, n4, n5, n6, n7, stateById).func_174845_l();
            }
            return func_174845_l;
        }
    }
}
