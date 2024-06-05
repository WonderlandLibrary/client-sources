package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.world.World;

public class EntitySpellParticleFX$WitchFactory implements IParticleFactory {
   public EntityFX getEntityFX(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      EntitySpellParticleFX var16 = new EntitySpellParticleFX(var2, var3, var5, var7, var9, var11, var13);
      ((EntitySpellParticleFX)var16).setBaseSpellTextureIndex(144);
      float var17 = var2.rand.nextFloat() * 0.5F + 0.35F;
      var16.setRBGColorF(1.0F * var17, 0.0F * var17, 1.0F * var17);
      return var16;
   }
}
