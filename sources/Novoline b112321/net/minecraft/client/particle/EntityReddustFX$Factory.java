package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.world.World;

public class EntityReddustFX$Factory implements IParticleFactory {
   public EntityFX getEntityFX(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      return new EntityReddustFX(var2, var3, var5, var7, (float)var9, (float)var11, (float)var13);
   }
}
