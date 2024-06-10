/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelBase;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.EntityHanging;
/*   7:    */ import net.minecraft.entity.EntityLiving;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import org.lwjgl.opengl.GL11;
/*  10:    */ 
/*  11:    */ public abstract class RenderLiving
/*  12:    */   extends RendererLivingEntity
/*  13:    */ {
/*  14:    */   private static final String __OBFID = "CL_00001015";
/*  15:    */   
/*  16:    */   public RenderLiving(ModelBase par1ModelBase, float par2)
/*  17:    */   {
/*  18: 17 */     super(par1ModelBase, par2);
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected boolean func_110813_b(EntityLiving par1EntityLiving)
/*  22:    */   {
/*  23: 22 */     return (super.func_110813_b(par1EntityLiving)) && ((par1EntityLiving.getAlwaysRenderNameTagForRender()) || ((par1EntityLiving.hasCustomNameTag()) && (par1EntityLiving == this.renderManager.field_147941_i)));
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*  27:    */   {
/*  28: 33 */     super.doRender(par1EntityLiving, par2, par4, par6, par8, par9);
/*  29: 34 */     func_110827_b(par1EntityLiving, par2, par4, par6, par8, par9);
/*  30:    */   }
/*  31:    */   
/*  32:    */   private double func_110828_a(double par1, double par3, double par5)
/*  33:    */   {
/*  34: 39 */     return par1 + (par3 - par1) * par5;
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void func_110827_b(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*  38:    */   {
/*  39: 44 */     Entity var10 = par1EntityLiving.getLeashedToEntity();
/*  40: 46 */     if (var10 != null)
/*  41:    */     {
/*  42: 48 */       par4 -= (1.6D - par1EntityLiving.height) * 0.5D;
/*  43: 49 */       Tessellator var11 = Tessellator.instance;
/*  44: 50 */       double var12 = func_110828_a(var10.prevRotationYaw, var10.rotationYaw, par9 * 0.5F) * 0.0174532923847437D;
/*  45: 51 */       double var14 = func_110828_a(var10.prevRotationPitch, var10.rotationPitch, par9 * 0.5F) * 0.0174532923847437D;
/*  46: 52 */       double var16 = Math.cos(var12);
/*  47: 53 */       double var18 = Math.sin(var12);
/*  48: 54 */       double var20 = Math.sin(var14);
/*  49: 56 */       if ((var10 instanceof EntityHanging))
/*  50:    */       {
/*  51: 58 */         var16 = 0.0D;
/*  52: 59 */         var18 = 0.0D;
/*  53: 60 */         var20 = -1.0D;
/*  54:    */       }
/*  55: 63 */       double var22 = Math.cos(var14);
/*  56: 64 */       double var24 = func_110828_a(var10.prevPosX, var10.posX, par9) - var16 * 0.7D - var18 * 0.5D * var22;
/*  57: 65 */       double var26 = func_110828_a(var10.prevPosY + var10.getEyeHeight() * 0.7D, var10.posY + var10.getEyeHeight() * 0.7D, par9) - var20 * 0.5D - 0.25D;
/*  58: 66 */       double var28 = func_110828_a(var10.prevPosZ, var10.posZ, par9) - var18 * 0.7D + var16 * 0.5D * var22;
/*  59: 67 */       double var30 = func_110828_a(par1EntityLiving.prevRenderYawOffset, par1EntityLiving.renderYawOffset, par9) * 0.0174532923847437D + 1.570796326794897D;
/*  60: 68 */       var16 = Math.cos(var30) * par1EntityLiving.width * 0.4D;
/*  61: 69 */       var18 = Math.sin(var30) * par1EntityLiving.width * 0.4D;
/*  62: 70 */       double var32 = func_110828_a(par1EntityLiving.prevPosX, par1EntityLiving.posX, par9) + var16;
/*  63: 71 */       double var34 = func_110828_a(par1EntityLiving.prevPosY, par1EntityLiving.posY, par9);
/*  64: 72 */       double var36 = func_110828_a(par1EntityLiving.prevPosZ, par1EntityLiving.posZ, par9) + var18;
/*  65: 73 */       par2 += var16;
/*  66: 74 */       par6 += var18;
/*  67: 75 */       double var38 = (float)(var24 - var32);
/*  68: 76 */       double var40 = (float)(var26 - var34);
/*  69: 77 */       double var42 = (float)(var28 - var36);
/*  70: 78 */       GL11.glDisable(3553);
/*  71: 79 */       GL11.glDisable(2896);
/*  72: 80 */       GL11.glDisable(2884);
/*  73: 81 */       boolean var44 = true;
/*  74: 82 */       double var45 = 0.025D;
/*  75: 83 */       var11.startDrawing(5);
/*  76: 87 */       for (int var47 = 0; var47 <= 24; var47++)
/*  77:    */       {
/*  78: 89 */         if (var47 % 2 == 0) {
/*  79: 91 */           var11.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
/*  80:    */         } else {
/*  81: 95 */           var11.setColorRGBA_F(0.35F, 0.28F, 0.21F, 1.0F);
/*  82:    */         }
/*  83: 98 */         float var48 = var47 / 24.0F;
/*  84: 99 */         var11.addVertex(par2 + var38 * var48 + 0.0D, par4 + var40 * (var48 * var48 + var48) * 0.5D + ((24.0F - var47) / 18.0F + 0.125F), par6 + var42 * var48);
/*  85:100 */         var11.addVertex(par2 + var38 * var48 + 0.025D, par4 + var40 * (var48 * var48 + var48) * 0.5D + ((24.0F - var47) / 18.0F + 0.125F) + 0.025D, par6 + var42 * var48);
/*  86:    */       }
/*  87:103 */       var11.draw();
/*  88:104 */       var11.startDrawing(5);
/*  89:106 */       for (var47 = 0; var47 <= 24; var47++)
/*  90:    */       {
/*  91:108 */         if (var47 % 2 == 0) {
/*  92:110 */           var11.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
/*  93:    */         } else {
/*  94:114 */           var11.setColorRGBA_F(0.35F, 0.28F, 0.21F, 1.0F);
/*  95:    */         }
/*  96:117 */         float var48 = var47 / 24.0F;
/*  97:118 */         var11.addVertex(par2 + var38 * var48 + 0.0D, par4 + var40 * (var48 * var48 + var48) * 0.5D + ((24.0F - var47) / 18.0F + 0.125F) + 0.025D, par6 + var42 * var48);
/*  98:119 */         var11.addVertex(par2 + var38 * var48 + 0.025D, par4 + var40 * (var48 * var48 + var48) * 0.5D + ((24.0F - var47) / 18.0F + 0.125F), par6 + var42 * var48 + 0.025D);
/*  99:    */       }
/* 100:122 */       var11.draw();
/* 101:123 */       GL11.glEnable(2896);
/* 102:124 */       GL11.glEnable(3553);
/* 103:125 */       GL11.glEnable(2884);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected boolean func_110813_b(EntityLivingBase par1EntityLivingBase)
/* 108:    */   {
/* 109:131 */     return func_110813_b((EntityLiving)par1EntityLivingBase);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 113:    */   {
/* 114:142 */     doRender((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 118:    */   {
/* 119:153 */     doRender((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderLiving
 * JD-Core Version:    0.7.0.1
 */