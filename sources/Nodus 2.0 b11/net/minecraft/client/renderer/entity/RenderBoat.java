/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBase;
/*  4:   */ import net.minecraft.client.model.ModelBoat;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.entity.item.EntityBoat;
/*  7:   */ import net.minecraft.util.MathHelper;
/*  8:   */ import net.minecraft.util.ResourceLocation;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class RenderBoat
/* 12:   */   extends Render
/* 13:   */ {
/* 14:13 */   private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
/* 15:   */   protected ModelBase modelBoat;
/* 16:   */   private static final String __OBFID = "CL_00000981";
/* 17:   */   
/* 18:   */   public RenderBoat()
/* 19:   */   {
/* 20:21 */     this.shadowSize = 0.5F;
/* 21:22 */     this.modelBoat = new ModelBoat();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void doRender(EntityBoat par1EntityBoat, double par2, double par4, double par6, float par8, float par9)
/* 25:   */   {
/* 26:33 */     GL11.glPushMatrix();
/* 27:34 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/* 28:35 */     GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
/* 29:36 */     float var10 = par1EntityBoat.getTimeSinceHit() - par9;
/* 30:37 */     float var11 = par1EntityBoat.getDamageTaken() - par9;
/* 31:39 */     if (var11 < 0.0F) {
/* 32:41 */       var11 = 0.0F;
/* 33:   */     }
/* 34:44 */     if (var10 > 0.0F) {
/* 35:46 */       GL11.glRotatef(MathHelper.sin(var10) * var10 * var11 / 10.0F * par1EntityBoat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
/* 36:   */     }
/* 37:49 */     float var12 = 0.75F;
/* 38:50 */     GL11.glScalef(var12, var12, var12);
/* 39:51 */     GL11.glScalef(1.0F / var12, 1.0F / var12, 1.0F / var12);
/* 40:52 */     bindEntityTexture(par1EntityBoat);
/* 41:53 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/* 42:54 */     this.modelBoat.render(par1EntityBoat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 43:55 */     GL11.glPopMatrix();
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected ResourceLocation getEntityTexture(EntityBoat par1EntityBoat)
/* 47:   */   {
/* 48:63 */     return boatTextures;
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 52:   */   {
/* 53:71 */     return getEntityTexture((EntityBoat)par1Entity);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 57:   */   {
/* 58:82 */     doRender((EntityBoat)par1Entity, par2, par4, par6, par8, par9);
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderBoat
 * JD-Core Version:    0.7.0.1
 */