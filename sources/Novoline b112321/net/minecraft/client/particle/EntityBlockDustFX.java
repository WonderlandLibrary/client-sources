package net.minecraft.client.particle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.world.World;

public class EntityBlockDustFX extends EntityDiggingFX {
   protected EntityBlockDustFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, IBlockState var14) {
      super(var1, var2, var4, var6, var8, var10, var12, var14);
      this.motionX = var8;
      this.motionY = var10;
      this.motionZ = var12;
   }
}
