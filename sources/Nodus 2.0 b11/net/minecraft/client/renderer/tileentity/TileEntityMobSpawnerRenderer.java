/*  1:   */ package net.minecraft.client.renderer.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.entity.RenderManager;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*  6:   */ import net.minecraft.tileentity.TileEntity;
/*  7:   */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class TileEntityMobSpawnerRenderer
/* 11:   */   extends TileEntitySpecialRenderer
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000968";
/* 14:   */   
/* 15:   */   public void renderTileEntityAt(TileEntityMobSpawner p_147518_1_, double p_147518_2_, double p_147518_4_, double p_147518_6_, float p_147518_8_)
/* 16:   */   {
/* 17:16 */     GL11.glPushMatrix();
/* 18:17 */     GL11.glTranslatef((float)p_147518_2_ + 0.5F, (float)p_147518_4_, (float)p_147518_6_ + 0.5F);
/* 19:18 */     func_147517_a(p_147518_1_.func_145881_a(), p_147518_2_, p_147518_4_, p_147518_6_, p_147518_8_);
/* 20:19 */     GL11.glPopMatrix();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static void func_147517_a(MobSpawnerBaseLogic p_147517_0_, double p_147517_1_, double p_147517_3_, double p_147517_5_, float p_147517_7_)
/* 24:   */   {
/* 25:24 */     Entity var8 = p_147517_0_.func_98281_h();
/* 26:26 */     if (var8 != null)
/* 27:   */     {
/* 28:28 */       var8.setWorld(p_147517_0_.getSpawnerWorld());
/* 29:29 */       float var9 = 0.4375F;
/* 30:30 */       GL11.glTranslatef(0.0F, 0.4F, 0.0F);
/* 31:31 */       GL11.glRotatef((float)(p_147517_0_.field_98284_d + (p_147517_0_.field_98287_c - p_147517_0_.field_98284_d) * p_147517_7_) * 10.0F, 0.0F, 1.0F, 0.0F);
/* 32:32 */       GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
/* 33:33 */       GL11.glTranslatef(0.0F, -0.4F, 0.0F);
/* 34:34 */       GL11.glScalef(var9, var9, var9);
/* 35:35 */       var8.setLocationAndAngles(p_147517_1_, p_147517_3_, p_147517_5_, 0.0F, 0.0F);
/* 36:36 */       RenderManager.instance.func_147940_a(var8, 0.0D, 0.0D, 0.0D, 0.0F, p_147517_7_);
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
/* 41:   */   {
/* 42:42 */     renderTileEntityAt((TileEntityMobSpawner)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer
 * JD-Core Version:    0.7.0.1
 */