package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class EntityFireworkStarterFX_Factory implements IParticleFactory {
   private static final String __OBFID = "CL_00002603";

   public EntityFX func_178902_a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      EntityFireworkSparkFX var16 = new EntityFireworkSparkFX(var2, var3, var5, var7, var9, var11, var13, Minecraft.getMinecraft().effectRenderer);
      var16.setAlphaF(0.99F);
      return var16;
   }
}
