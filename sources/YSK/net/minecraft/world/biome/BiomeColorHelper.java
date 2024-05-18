package net.minecraft.world.biome;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public class BiomeColorHelper
{
    private static final ColorResolver field_180291_a;
    private static final ColorResolver field_180290_c;
    private static final ColorResolver field_180289_b;
    
    static {
        field_180291_a = new ColorResolver() {
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
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public int getColorAtPos(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
                return biomeGenBase.getGrassColorAtPos(blockPos);
            }
        };
        field_180289_b = new ColorResolver() {
            @Override
            public int getColorAtPos(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
                return biomeGenBase.getFoliageColorAtPos(blockPos);
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
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        field_180290_c = new ColorResolver() {
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
            public int getColorAtPos(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
                return biomeGenBase.waterColorMultiplier;
            }
        };
    }
    
    public static int getFoliageColorAtPos(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_180285_a(blockAccess, blockPos, BiomeColorHelper.field_180289_b);
    }
    
    public static int getGrassColorAtPos(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_180285_a(blockAccess, blockPos, BiomeColorHelper.field_180291_a);
    }
    
    private static int func_180285_a(final IBlockAccess blockAccess, final BlockPos blockPos, final ColorResolver colorResolver) {
        int length = "".length();
        int length2 = "".length();
        int length3 = "".length();
        final Iterator<BlockPos.MutableBlockPos> iterator = BlockPos.getAllInBoxMutable(blockPos.add(-" ".length(), "".length(), -" ".length()), blockPos.add(" ".length(), "".length(), " ".length())).iterator();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos.MutableBlockPos mutableBlockPos = iterator.next();
            final int colorAtPos = colorResolver.getColorAtPos(blockAccess.getBiomeGenForCoords(mutableBlockPos), mutableBlockPos);
            length += (colorAtPos & 1198746 + 5415701 + 1619561 + 8477672) >> (0x44 ^ 0x54);
            length2 += (colorAtPos & 21509 + 28697 - 11175 + 26249) >> (0xB ^ 0x3);
            length3 += (colorAtPos & 60 + 30 + 153 + 12);
        }
        return (length / (0xC ^ 0x5) & 77 + 162 - 175 + 191) << (0x57 ^ 0x47) | (length2 / (0xBE ^ 0xB7) & 250 + 214 - 353 + 144) << (0xA5 ^ 0xAD) | (length3 / (0xB4 ^ 0xBD) & 91 + 48 + 114 + 2);
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static int getWaterColorAtPos(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_180285_a(blockAccess, blockPos, BiomeColorHelper.field_180290_c);
    }
    
    interface ColorResolver
    {
        int getColorAtPos(final BiomeGenBase p0, final BlockPos p1);
    }
}
