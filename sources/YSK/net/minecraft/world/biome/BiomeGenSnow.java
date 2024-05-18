package net.minecraft.world.biome;

import net.minecraft.init.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BiomeGenSnow extends BiomeGenBase
{
    private static final String[] I;
    private boolean field_150615_aC;
    private WorldGenIceSpike field_150616_aD;
    private WorldGenIcePath field_150617_aE;
    
    public BiomeGenSnow(final int n, final boolean field_150615_aC) {
        super(n);
        this.field_150616_aD = new WorldGenIceSpike();
        this.field_150617_aE = new WorldGenIcePath(0x19 ^ 0x1D);
        this.field_150615_aC = field_150615_aC;
        if (field_150615_aC) {
            this.topBlock = Blocks.snow.getDefaultState();
        }
        this.spawnableCreatureList.clear();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("w&4+/2\u0006", "WuDBD");
    }
    
    static {
        I();
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final BiomeGenBase setHeight = new BiomeGenSnow(n, " ".length() != 0).func_150557_a(575033 + 8704925 - 1523780 + 6071917, " ".length() != 0).setBiomeName(String.valueOf(this.biomeName) + BiomeGenSnow.I["".length()]).setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).setHeight(new Height(this.minHeight + 0.1f, this.maxHeight + 0.1f));
        setHeight.minHeight = this.minHeight + 0.3f;
        setHeight.maxHeight = this.maxHeight + 0.4f;
        return setHeight;
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return new WorldGenTaiga2("".length() != 0);
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
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        if (this.field_150615_aC) {
            int i = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
            while (i < "   ".length()) {
                this.field_150616_aD.generate(world, random, world.getHeight(blockPos.add(random.nextInt(0x8F ^ 0x9F) + (0xBD ^ 0xB5), "".length(), random.nextInt(0xBD ^ 0xAD) + (0x3A ^ 0x32))));
                ++i;
            }
            int j = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (j < "  ".length()) {
                this.field_150617_aE.generate(world, random, world.getHeight(blockPos.add(random.nextInt(0x97 ^ 0x87) + (0x57 ^ 0x5F), "".length(), random.nextInt(0x29 ^ 0x39) + (0x65 ^ 0x6D))));
                ++j;
            }
        }
        super.decorate(world, random, blockPos);
    }
}
