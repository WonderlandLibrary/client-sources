/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBase;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.monster.EntitySlime;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class RenderSlime
/* 11:   */   extends RenderLiving
/* 12:   */ {
/* 13:12 */   private static final ResourceLocation slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");
/* 14:   */   private ModelBase scaleAmount;
/* 15:   */   private static final String __OBFID = "CL_00001024";
/* 16:   */   
/* 17:   */   public RenderSlime(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
/* 18:   */   {
/* 19:18 */     super(par1ModelBase, par3);
/* 20:19 */     this.scaleAmount = par2ModelBase;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected int shouldRenderPass(EntitySlime par1EntitySlime, int par2, float par3)
/* 24:   */   {
/* 25:27 */     if (par1EntitySlime.isInvisible()) {
/* 26:29 */       return 0;
/* 27:   */     }
/* 28:31 */     if (par2 == 0)
/* 29:   */     {
/* 30:33 */       setRenderPassModel(this.scaleAmount);
/* 31:34 */       GL11.glEnable(2977);
/* 32:35 */       GL11.glEnable(3042);
/* 33:36 */       GL11.glBlendFunc(770, 771);
/* 34:37 */       return 1;
/* 35:   */     }
/* 36:41 */     if (par2 == 1)
/* 37:   */     {
/* 38:43 */       GL11.glDisable(3042);
/* 39:44 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 40:   */     }
/* 41:47 */     return -1;
/* 42:   */   }
/* 43:   */   
/* 44:   */   protected void preRenderCallback(EntitySlime par1EntitySlime, float par2)
/* 45:   */   {
/* 46:57 */     float var3 = par1EntitySlime.getSlimeSize();
/* 47:58 */     float var4 = (par1EntitySlime.prevSquishFactor + (par1EntitySlime.squishFactor - par1EntitySlime.prevSquishFactor) * par2) / (var3 * 0.5F + 1.0F);
/* 48:59 */     float var5 = 1.0F / (var4 + 1.0F);
/* 49:60 */     GL11.glScalef(var5 * var3, 1.0F / var5 * var3, var5 * var3);
/* 50:   */   }
/* 51:   */   
/* 52:   */   protected ResourceLocation getEntityTexture(EntitySlime par1EntitySlime)
/* 53:   */   {
/* 54:68 */     return slimeTextures;
/* 55:   */   }
/* 56:   */   
/* 57:   */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 58:   */   {
/* 59:77 */     preRenderCallback((EntitySlime)par1EntityLivingBase, par2);
/* 60:   */   }
/* 61:   */   
/* 62:   */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 63:   */   {
/* 64:85 */     return shouldRenderPass((EntitySlime)par1EntityLivingBase, par2, par3);
/* 65:   */   }
/* 66:   */   
/* 67:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 68:   */   {
/* 69:93 */     return getEntityTexture((EntitySlime)par1Entity);
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderSlime
 * JD-Core Version:    0.7.0.1
 */