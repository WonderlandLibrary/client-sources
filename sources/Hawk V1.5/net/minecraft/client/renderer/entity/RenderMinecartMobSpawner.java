package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;

public class RenderMinecartMobSpawner extends RenderMinecart {
   private static final String __OBFID = "CL_00001014";

   public RenderMinecartMobSpawner(RenderManager var1) {
      super(var1);
   }

   protected void func_177081_a(EntityMinecartMobSpawner var1, float var2, IBlockState var3) {
      super.func_180560_a(var1, var2, var3);
      if (var3.getBlock() == Blocks.mob_spawner) {
         TileEntityMobSpawnerRenderer.func_147517_a(var1.func_98039_d(), var1.posX, var1.posY, var1.posZ, var2);
      }

   }

   protected void func_180560_a(EntityMinecart var1, float var2, IBlockState var3) {
      this.func_177081_a((EntityMinecartMobSpawner)var1, var2, var3);
   }
}
