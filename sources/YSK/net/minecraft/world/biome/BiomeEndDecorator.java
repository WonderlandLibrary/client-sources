package net.minecraft.world.biome;

import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;

public class BiomeEndDecorator extends BiomeDecorator
{
    protected WorldGenerator spikeGen;
    
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
    protected void genDecorations(final BiomeGenBase biomeGenBase) {
        this.generateOres();
        if (this.randomGenerator.nextInt(0x7C ^ 0x79) == 0) {
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(this.randomGenerator.nextInt(0x95 ^ 0x85) + (0x4 ^ 0xC), "".length(), this.randomGenerator.nextInt(0x86 ^ 0x96) + (0x72 ^ 0x7A))));
        }
        if (this.field_180294_c.getX() == 0 && this.field_180294_c.getZ() == 0) {
            final EntityDragon entityDragon = new EntityDragon(this.currentWorld);
            entityDragon.setLocationAndAngles(0.0, 128.0, 0.0, this.randomGenerator.nextFloat() * 360.0f, 0.0f);
            this.currentWorld.spawnEntityInWorld(entityDragon);
        }
    }
    
    public BiomeEndDecorator() {
        this.spikeGen = new WorldGenSpikes(Blocks.end_stone);
    }
}
