package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityHeartFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.world.World;

public class EntityHeartFX$AngryVillagerFactory implements IParticleFactory {
   public EntityFX getEntityFX(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      EntityHeartFX var16 = new EntityHeartFX(var2, var3, var5 + 0.5D, var7, var9, var11, var13);
      var16.setParticleTextureIndex(81);
      var16.setRBGColorF(1.0F, 1.0F, 1.0F);
      return var16;
   }
}
