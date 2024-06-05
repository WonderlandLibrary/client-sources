package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.world.World;

public class EntityAuraFX$HappyVillagerFactory implements IParticleFactory {
   public EntityFX getEntityFX(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      EntityAuraFX var16 = new EntityAuraFX(var2, var3, var5, var7, var9, var11, var13);
      var16.setParticleTextureIndex(82);
      var16.setRBGColorF(1.0F, 1.0F, 1.0F);
      return var16;
   }
}
