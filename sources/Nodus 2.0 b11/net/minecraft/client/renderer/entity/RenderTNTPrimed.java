/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.RenderBlocks;
/*  4:   */ import net.minecraft.client.renderer.texture.TextureMap;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.entity.item.EntityTNTPrimed;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import net.minecraft.util.ResourceLocation;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class RenderTNTPrimed
/* 12:   */   extends Render
/* 13:   */ {
/* 14:13 */   private RenderBlocks blockRenderer = new RenderBlocks();
/* 15:   */   private static final String __OBFID = "CL_00001030";
/* 16:   */   
/* 17:   */   public RenderTNTPrimed()
/* 18:   */   {
/* 19:18 */     this.shadowSize = 0.5F;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void doRender(EntityTNTPrimed par1EntityTNTPrimed, double par2, double par4, double par6, float par8, float par9)
/* 23:   */   {
/* 24:29 */     GL11.glPushMatrix();
/* 25:30 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/* 26:33 */     if (par1EntityTNTPrimed.fuse - par9 + 1.0F < 10.0F)
/* 27:   */     {
/* 28:35 */       float var10 = 1.0F - (par1EntityTNTPrimed.fuse - par9 + 1.0F) / 10.0F;
/* 29:37 */       if (var10 < 0.0F) {
/* 30:39 */         var10 = 0.0F;
/* 31:   */       }
/* 32:42 */       if (var10 > 1.0F) {
/* 33:44 */         var10 = 1.0F;
/* 34:   */       }
/* 35:47 */       var10 *= var10;
/* 36:48 */       var10 *= var10;
/* 37:49 */       float var11 = 1.0F + var10 * 0.3F;
/* 38:50 */       GL11.glScalef(var11, var11, var11);
/* 39:   */     }
/* 40:53 */     float var10 = (1.0F - (par1EntityTNTPrimed.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
/* 41:54 */     bindEntityTexture(par1EntityTNTPrimed);
/* 42:55 */     this.blockRenderer.renderBlockAsItem(Blocks.tnt, 0, par1EntityTNTPrimed.getBrightness(par9));
/* 43:57 */     if (par1EntityTNTPrimed.fuse / 5 % 2 == 0)
/* 44:   */     {
/* 45:59 */       GL11.glDisable(3553);
/* 46:60 */       GL11.glDisable(2896);
/* 47:61 */       GL11.glEnable(3042);
/* 48:62 */       GL11.glBlendFunc(770, 772);
/* 49:63 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, var10);
/* 50:64 */       this.blockRenderer.renderBlockAsItem(Blocks.tnt, 0, 1.0F);
/* 51:65 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 52:66 */       GL11.glDisable(3042);
/* 53:67 */       GL11.glEnable(2896);
/* 54:68 */       GL11.glEnable(3553);
/* 55:   */     }
/* 56:71 */     GL11.glPopMatrix();
/* 57:   */   }
/* 58:   */   
/* 59:   */   protected ResourceLocation getEntityTexture(EntityTNTPrimed par1EntityTNTPrimed)
/* 60:   */   {
/* 61:79 */     return TextureMap.locationBlocksTexture;
/* 62:   */   }
/* 63:   */   
/* 64:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 65:   */   {
/* 66:87 */     return getEntityTexture((EntityTNTPrimed)par1Entity);
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 70:   */   {
/* 71:98 */     doRender((EntityTNTPrimed)par1Entity, par2, par4, par6, par8, par9);
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderTNTPrimed
 * JD-Core Version:    0.7.0.1
 */