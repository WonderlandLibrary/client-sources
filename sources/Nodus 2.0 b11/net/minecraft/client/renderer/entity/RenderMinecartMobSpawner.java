/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
/*  5:   */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*  6:   */ import net.minecraft.entity.item.EntityMinecart;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ 
/*  9:   */ public class RenderMinecartMobSpawner
/* 10:   */   extends RenderMinecart
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00001014";
/* 13:   */   
/* 14:   */   protected void func_147910_a(EntityMinecartMobSpawner p_147911_1_, float p_147911_2_, Block p_147911_3_, int p_147911_4_)
/* 15:   */   {
/* 16:15 */     super.func_147910_a(p_147911_1_, p_147911_2_, p_147911_3_, p_147911_4_);
/* 17:17 */     if (p_147911_3_ == Blocks.mob_spawner) {
/* 18:19 */       TileEntityMobSpawnerRenderer.func_147517_a(p_147911_1_.func_98039_d(), p_147911_1_.posX, p_147911_1_.posY, p_147911_1_.posZ, p_147911_2_);
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected void func_147910_a(EntityMinecart p_147910_1_, float p_147910_2_, Block p_147910_3_, int p_147910_4_)
/* 23:   */   {
/* 24:25 */     func_147910_a((EntityMinecartMobSpawner)p_147910_1_, p_147910_2_, p_147910_3_, p_147910_4_);
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderMinecartMobSpawner
 * JD-Core Version:    0.7.0.1
 */