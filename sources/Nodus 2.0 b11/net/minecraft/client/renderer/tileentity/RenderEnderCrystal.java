/*  1:   */ package net.minecraft.client.renderer.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBase;
/*  4:   */ import net.minecraft.client.model.ModelEnderCrystal;
/*  5:   */ import net.minecraft.client.renderer.entity.Render;
/*  6:   */ import net.minecraft.entity.Entity;
/*  7:   */ import net.minecraft.entity.item.EntityEnderCrystal;
/*  8:   */ import net.minecraft.util.MathHelper;
/*  9:   */ import net.minecraft.util.ResourceLocation;
/* 10:   */ import org.lwjgl.opengl.GL11;
/* 11:   */ 
/* 12:   */ public class RenderEnderCrystal
/* 13:   */   extends Render
/* 14:   */ {
/* 15:14 */   private static final ResourceLocation enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
/* 16:   */   private ModelBase field_76995_b;
/* 17:   */   private static final String __OBFID = "CL_00000987";
/* 18:   */   
/* 19:   */   public RenderEnderCrystal()
/* 20:   */   {
/* 21:20 */     this.shadowSize = 0.5F;
/* 22:21 */     this.field_76995_b = new ModelEnderCrystal(0.0F, true);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void doRender(EntityEnderCrystal par1EntityEnderCrystal, double par2, double par4, double par6, float par8, float par9)
/* 26:   */   {
/* 27:32 */     float var10 = par1EntityEnderCrystal.innerRotation + par9;
/* 28:33 */     GL11.glPushMatrix();
/* 29:34 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/* 30:35 */     bindTexture(enderCrystalTextures);
/* 31:36 */     float var11 = MathHelper.sin(var10 * 0.2F) / 2.0F + 0.5F;
/* 32:37 */     var11 += var11 * var11;
/* 33:38 */     this.field_76995_b.render(par1EntityEnderCrystal, 0.0F, var10 * 3.0F, var11 * 0.2F, 0.0F, 0.0F, 0.0625F);
/* 34:39 */     GL11.glPopMatrix();
/* 35:   */   }
/* 36:   */   
/* 37:   */   protected ResourceLocation getEntityTexture(EntityEnderCrystal par1EntityEnderCrystal)
/* 38:   */   {
/* 39:47 */     return enderCrystalTextures;
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 43:   */   {
/* 44:55 */     return getEntityTexture((EntityEnderCrystal)par1Entity);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 48:   */   {
/* 49:66 */     doRender((EntityEnderCrystal)par1Entity, par2, par4, par6, par8, par9);
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.RenderEnderCrystal
 * JD-Core Version:    0.7.0.1
 */