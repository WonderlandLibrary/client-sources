/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelLeashKnot;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLeashKnot;
/*  6:   */ import net.minecraft.util.ResourceLocation;
/*  7:   */ import org.lwjgl.opengl.GL11;
/*  8:   */ 
/*  9:   */ public class RenderLeashKnot
/* 10:   */   extends Render
/* 11:   */ {
/* 12:12 */   private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
/* 13:13 */   private ModelLeashKnot leashKnotModel = new ModelLeashKnot();
/* 14:   */   private static final String __OBFID = "CL_00001010";
/* 15:   */   
/* 16:   */   public void doRender(EntityLeashKnot par1EntityLeashKnot, double par2, double par4, double par6, float par8, float par9)
/* 17:   */   {
/* 18:24 */     GL11.glPushMatrix();
/* 19:25 */     GL11.glDisable(2884);
/* 20:26 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/* 21:27 */     float var10 = 0.0625F;
/* 22:28 */     GL11.glEnable(32826);
/* 23:29 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/* 24:30 */     GL11.glEnable(3008);
/* 25:31 */     bindEntityTexture(par1EntityLeashKnot);
/* 26:32 */     this.leashKnotModel.render(par1EntityLeashKnot, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, var10);
/* 27:33 */     GL11.glPopMatrix();
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected ResourceLocation getEntityTexture(EntityLeashKnot par1EntityLeashKnot)
/* 31:   */   {
/* 32:41 */     return leashKnotTextures;
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 36:   */   {
/* 37:49 */     return getEntityTexture((EntityLeashKnot)par1Entity);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 41:   */   {
/* 42:60 */     doRender((EntityLeashKnot)par1Entity, par2, par4, par6, par8, par9);
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderLeashKnot
 * JD-Core Version:    0.7.0.1
 */