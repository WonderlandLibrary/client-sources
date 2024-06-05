package net.minecraft.client.particle;

import net.minecraft.client.particle.Barrier;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Barrier$Factory implements IParticleFactory {
   public EntityFX getEntityFX(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      return new Barrier(var2, var3, var5, var7, Item.getItemFromBlock(Blocks.barrier));
   }
}
