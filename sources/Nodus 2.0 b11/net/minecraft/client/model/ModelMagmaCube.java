/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.monster.EntityMagmaCube;
/*  6:   */ 
/*  7:   */ public class ModelMagmaCube
/*  8:   */   extends ModelBase
/*  9:   */ {
/* 10: 9 */   ModelRenderer[] field_78109_a = new ModelRenderer[8];
/* 11:   */   ModelRenderer field_78108_b;
/* 12:   */   private static final String __OBFID = "CL_00000842";
/* 13:   */   
/* 14:   */   public ModelMagmaCube()
/* 15:   */   {
/* 16:15 */     for (int var1 = 0; var1 < this.field_78109_a.length; var1++)
/* 17:   */     {
/* 18:17 */       byte var2 = 0;
/* 19:18 */       int var3 = var1;
/* 20:20 */       if (var1 == 2)
/* 21:   */       {
/* 22:22 */         var2 = 24;
/* 23:23 */         var3 = 10;
/* 24:   */       }
/* 25:25 */       else if (var1 == 3)
/* 26:   */       {
/* 27:27 */         var2 = 24;
/* 28:28 */         var3 = 19;
/* 29:   */       }
/* 30:31 */       this.field_78109_a[var1] = new ModelRenderer(this, var2, var3);
/* 31:32 */       this.field_78109_a[var1].addBox(-4.0F, 16 + var1, -4.0F, 8, 1, 8);
/* 32:   */     }
/* 33:35 */     this.field_78108_b = new ModelRenderer(this, 0, 16);
/* 34:36 */     this.field_78108_b.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 38:   */   {
/* 39:45 */     EntityMagmaCube var5 = (EntityMagmaCube)par1EntityLivingBase;
/* 40:46 */     float var6 = var5.prevSquishFactor + (var5.squishFactor - var5.prevSquishFactor) * par4;
/* 41:48 */     if (var6 < 0.0F) {
/* 42:50 */       var6 = 0.0F;
/* 43:   */     }
/* 44:53 */     for (int var7 = 0; var7 < this.field_78109_a.length; var7++) {
/* 45:55 */       this.field_78109_a[var7].rotationPointY = (-(4 - var7) * var6 * 1.7F);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 50:   */   {
/* 51:64 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 52:65 */     this.field_78108_b.render(par7);
/* 53:67 */     for (int var8 = 0; var8 < this.field_78109_a.length; var8++) {
/* 54:69 */       this.field_78109_a[var8].render(par7);
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelMagmaCube
 * JD-Core Version:    0.7.0.1
 */