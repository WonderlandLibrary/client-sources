package net.minecraft.world.biome;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class BiomeGenPlains extends BiomeGenBase
{
    protected boolean field_150628_aC;
    private static final String[] I;
    
    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        if (BiomeGenPlains.GRASS_COLOR_NOISE.func_151601_a(blockPos.getX() / 200.0, blockPos.getZ() / 200.0) < -0.8) {
            switch (random.nextInt(0x7C ^ 0x78)) {
                case 0: {
                    return BlockFlower.EnumFlowerType.ORANGE_TULIP;
                }
                case 1: {
                    return BlockFlower.EnumFlowerType.RED_TULIP;
                }
                case 2: {
                    return BlockFlower.EnumFlowerType.PINK_TULIP;
                }
                default: {
                    return BlockFlower.EnumFlowerType.WHITE_TULIP;
                }
            }
        }
        else {
            if (random.nextInt("   ".length()) > 0) {
                final int nextInt = random.nextInt("   ".length());
                BlockFlower.EnumFlowerType enumFlowerType;
                if (nextInt == 0) {
                    enumFlowerType = BlockFlower.EnumFlowerType.POPPY;
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
                else if (nextInt == " ".length()) {
                    enumFlowerType = BlockFlower.EnumFlowerType.HOUSTONIA;
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
                else {
                    enumFlowerType = BlockFlower.EnumFlowerType.OXEYE_DAISY;
                }
                return enumFlowerType;
            }
            return BlockFlower.EnumFlowerType.DANDELION;
        }
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        if (BiomeGenPlains.GRASS_COLOR_NOISE.func_151601_a((blockPos.getX() + (0x1B ^ 0x13)) / 200.0, (blockPos.getZ() + (0x18 ^ 0x10)) / 200.0) < -0.8) {
            this.theBiomeDecorator.flowersPerChunk = (0x46 ^ 0x49);
            this.theBiomeDecorator.grassPerChunk = (0xB7 ^ 0xB2);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            this.theBiomeDecorator.flowersPerChunk = (0x4A ^ 0x4E);
            this.theBiomeDecorator.grassPerChunk = (0x6C ^ 0x66);
            BiomeGenPlains.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
            int i = "".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (i < (0x50 ^ 0x57)) {
                final int n = random.nextInt(0xD6 ^ 0xC6) + (0xB3 ^ 0xBB);
                final int n2 = random.nextInt(0x92 ^ 0x82) + (0x14 ^ 0x1C);
                BiomeGenPlains.DOUBLE_PLANT_GENERATOR.generate(world, random, blockPos.add(n, random.nextInt(world.getHeight(blockPos.add(n, "".length(), n2)).getY() + (0x64 ^ 0x44)), n2));
                ++i;
            }
        }
        if (this.field_150628_aC) {
            BiomeGenPlains.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
            int j = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (j < (0x14 ^ 0x1E)) {
                final int n3 = random.nextInt(0x53 ^ 0x43) + (0xF ^ 0x7);
                final int n4 = random.nextInt(0x43 ^ 0x53) + (0x4 ^ 0xC);
                BiomeGenPlains.DOUBLE_PLANT_GENERATOR.generate(world, random, blockPos.add(n3, random.nextInt(world.getHeight(blockPos.add(n3, "".length(), n4)).getY() + (0x9 ^ 0x29)), n4));
                ++j;
            }
        }
        super.decorate(world, random, blockPos);
    }
    
    static {
        I();
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0000;\u0003\u0002\u001c<9\b\u0016P\u0003\"\f\r\u001e ", "SNmdp");
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final BiomeGenPlains biomeGenPlains = new BiomeGenPlains(n);
        biomeGenPlains.setBiomeName(BiomeGenPlains.I["".length()]);
        biomeGenPlains.field_150628_aC = (" ".length() != 0);
        biomeGenPlains.setColor(3193095 + 5415434 - 2635920 + 3313887);
        biomeGenPlains.field_150609_ah = 12517603 + 878901 - 13185455 + 14062305;
        return biomeGenPlains;
    }
    
    protected BiomeGenPlains(final int n) {
        super(n);
        this.setTemperatureRainfall(0.8f, 0.4f);
        this.setHeight(BiomeGenPlains.height_LowPlains);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 0x3 ^ 0x6, "  ".length(), 0xC ^ 0xA));
        this.theBiomeDecorator.treesPerChunk = -(672 + 928 - 783 + 182);
        this.theBiomeDecorator.flowersPerChunk = (0x42 ^ 0x46);
        this.theBiomeDecorator.grassPerChunk = (0xCC ^ 0xC6);
    }
}
