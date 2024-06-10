/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.client.renderer.RenderBlocks;
/*  5:   */ import net.minecraft.entity.item.EntityMinecart;
/*  6:   */ import net.minecraft.entity.item.EntityMinecartTNT;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class RenderTntMinecart
/* 11:   */   extends RenderMinecart
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00001029";
/* 14:   */   
/* 15:   */   protected void func_147910_a(EntityMinecartTNT p_147912_1_, float p_147912_2_, Block p_147912_3_, int p_147912_4_)
/* 16:   */   {
/* 17:15 */     int var5 = p_147912_1_.func_94104_d();
/* 18:17 */     if ((var5 > -1) && (var5 - p_147912_2_ + 1.0F < 10.0F))
/* 19:   */     {
/* 20:19 */       float var6 = 1.0F - (var5 - p_147912_2_ + 1.0F) / 10.0F;
/* 21:21 */       if (var6 < 0.0F) {
/* 22:23 */         var6 = 0.0F;
/* 23:   */       }
/* 24:26 */       if (var6 > 1.0F) {
/* 25:28 */         var6 = 1.0F;
/* 26:   */       }
/* 27:31 */       var6 *= var6;
/* 28:32 */       var6 *= var6;
/* 29:33 */       float var7 = 1.0F + var6 * 0.3F;
/* 30:34 */       GL11.glScalef(var7, var7, var7);
/* 31:   */     }
/* 32:37 */     super.func_147910_a(p_147912_1_, p_147912_2_, p_147912_3_, p_147912_4_);
/* 33:39 */     if ((var5 > -1) && (var5 / 5 % 2 == 0))
/* 34:   */     {
/* 35:41 */       GL11.glDisable(3553);
/* 36:42 */       GL11.glDisable(2896);
/* 37:43 */       GL11.glEnable(3042);
/* 38:44 */       GL11.glBlendFunc(770, 772);
/* 39:45 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, (1.0F - (var5 - p_147912_2_ + 1.0F) / 100.0F) * 0.8F);
/* 40:46 */       GL11.glPushMatrix();
/* 41:47 */       this.field_94145_f.renderBlockAsItem(Blocks.tnt, 0, 1.0F);
/* 42:48 */       GL11.glPopMatrix();
/* 43:49 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 44:50 */       GL11.glDisable(3042);
/* 45:51 */       GL11.glEnable(2896);
/* 46:52 */       GL11.glEnable(3553);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected void func_147910_a(EntityMinecart p_147910_1_, float p_147910_2_, Block p_147910_3_, int p_147910_4_)
/* 51:   */   {
/* 52:58 */     func_147910_a((EntityMinecartTNT)p_147910_1_, p_147910_2_, p_147910_3_, p_147910_4_);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderTntMinecart
 * JD-Core Version:    0.7.0.1
 */