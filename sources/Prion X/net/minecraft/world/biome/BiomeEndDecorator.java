package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.BlockPos;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeEndDecorator extends BiomeDecorator
{
  protected WorldGenerator spikeGen;
  private static final String __OBFID = "CL_00000188";
  
  public BiomeEndDecorator()
  {
    spikeGen = new net.minecraft.world.gen.feature.WorldGenSpikes(net.minecraft.init.Blocks.end_stone);
  }
  
  protected void genDecorations(BiomeGenBase p_150513_1_)
  {
    generateOres();
    
    if (randomGenerator.nextInt(5) == 0)
    {
      int var2 = randomGenerator.nextInt(16) + 8;
      int var3 = randomGenerator.nextInt(16) + 8;
      spikeGen.generate(currentWorld, randomGenerator, currentWorld.func_175672_r(field_180294_c.add(var2, 0, var3)));
    }
    
    if ((field_180294_c.getX() == 0) && (field_180294_c.getZ() == 0))
    {
      EntityDragon var4 = new EntityDragon(currentWorld);
      var4.setLocationAndAngles(0.0D, 128.0D, 0.0D, randomGenerator.nextFloat() * 360.0F, 0.0F);
      currentWorld.spawnEntityInWorld(var4);
    }
  }
}
