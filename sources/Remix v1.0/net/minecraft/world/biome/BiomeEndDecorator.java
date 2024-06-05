package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;

public class BiomeEndDecorator extends BiomeDecorator
{
    protected WorldGenerator spikeGen;
    
    public BiomeEndDecorator() {
        this.spikeGen = new WorldGenSpikes(Blocks.end_stone);
    }
    
    @Override
    protected void genDecorations(final BiomeGenBase p_150513_1_) {
        this.generateOres();
        if (this.randomGenerator.nextInt(5) == 0) {
            final int var2 = this.randomGenerator.nextInt(16) + 8;
            final int var3 = this.randomGenerator.nextInt(16) + 8;
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.func_175672_r(this.field_180294_c.add(var2, 0, var3)));
        }
        if (this.field_180294_c.getX() == 0 && this.field_180294_c.getZ() == 0) {
            final EntityDragon var4 = new EntityDragon(this.currentWorld);
            var4.setLocationAndAngles(0.0, 128.0, 0.0, this.randomGenerator.nextFloat() * 360.0f, 0.0f);
            this.currentWorld.spawnEntityInWorld(var4);
        }
    }
}
