package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.world.World;

public class EntityDiggingFX$Factory implements IParticleFactory {
   public EntityFX getEntityFX(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      return (new EntityDiggingFX(var2, var3, var5, var7, var9, var11, var13, Block.getStateById(var15[0]))).func_174845_l();
   }
}
