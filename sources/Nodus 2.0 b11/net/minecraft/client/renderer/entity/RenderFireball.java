/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.client.renderer.texture.TextureMap;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.entity.projectile.EntityFireball;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.util.IIcon;
/* 10:   */ import net.minecraft.util.ResourceLocation;
/* 11:   */ import org.lwjgl.opengl.GL11;
/* 12:   */ 
/* 13:   */ public class RenderFireball
/* 14:   */   extends Render
/* 15:   */ {
/* 16:   */   private float field_77002_a;
/* 17:   */   private static final String __OBFID = "CL_00000995";
/* 18:   */   
/* 19:   */   public RenderFireball(float par1)
/* 20:   */   {
/* 21:20 */     this.field_77002_a = par1;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void doRender(EntityFireball par1EntityFireball, double par2, double par4, double par6, float par8, float par9)
/* 25:   */   {
/* 26:31 */     GL11.glPushMatrix();
/* 27:32 */     bindEntityTexture(par1EntityFireball);
/* 28:33 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/* 29:34 */     GL11.glEnable(32826);
/* 30:35 */     float var10 = this.field_77002_a;
/* 31:36 */     GL11.glScalef(var10 / 1.0F, var10 / 1.0F, var10 / 1.0F);
/* 32:37 */     IIcon var11 = Items.fire_charge.getIconFromDamage(0);
/* 33:38 */     Tessellator var12 = Tessellator.instance;
/* 34:39 */     float var13 = var11.getMinU();
/* 35:40 */     float var14 = var11.getMaxU();
/* 36:41 */     float var15 = var11.getMinV();
/* 37:42 */     float var16 = var11.getMaxV();
/* 38:43 */     float var17 = 1.0F;
/* 39:44 */     float var18 = 0.5F;
/* 40:45 */     float var19 = 0.25F;
/* 41:46 */     GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 42:47 */     GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 43:48 */     var12.startDrawingQuads();
/* 44:49 */     var12.setNormal(0.0F, 1.0F, 0.0F);
/* 45:50 */     var12.addVertexWithUV(0.0F - var18, 0.0F - var19, 0.0D, var13, var16);
/* 46:51 */     var12.addVertexWithUV(var17 - var18, 0.0F - var19, 0.0D, var14, var16);
/* 47:52 */     var12.addVertexWithUV(var17 - var18, 1.0F - var19, 0.0D, var14, var15);
/* 48:53 */     var12.addVertexWithUV(0.0F - var18, 1.0F - var19, 0.0D, var13, var15);
/* 49:54 */     var12.draw();
/* 50:55 */     GL11.glDisable(32826);
/* 51:56 */     GL11.glPopMatrix();
/* 52:   */   }
/* 53:   */   
/* 54:   */   protected ResourceLocation getEntityTexture(EntityFireball par1EntityFireball)
/* 55:   */   {
/* 56:64 */     return TextureMap.locationItemsTexture;
/* 57:   */   }
/* 58:   */   
/* 59:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 60:   */   {
/* 61:72 */     return getEntityTexture((EntityFireball)par1Entity);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 65:   */   {
/* 66:83 */     doRender((EntityFireball)par1Entity, par2, par4, par6, par8, par9);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderFireball
 * JD-Core Version:    0.7.0.1
 */