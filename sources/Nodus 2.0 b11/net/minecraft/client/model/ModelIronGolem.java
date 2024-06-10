/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*   6:    */ 
/*   7:    */ public class ModelIronGolem
/*   8:    */   extends ModelBase
/*   9:    */ {
/*  10:    */   public ModelRenderer ironGolemHead;
/*  11:    */   public ModelRenderer ironGolemBody;
/*  12:    */   public ModelRenderer ironGolemRightArm;
/*  13:    */   public ModelRenderer ironGolemLeftArm;
/*  14:    */   public ModelRenderer ironGolemLeftLeg;
/*  15:    */   public ModelRenderer ironGolemRightLeg;
/*  16:    */   private static final String __OBFID = "CL_00000863";
/*  17:    */   
/*  18:    */   public ModelIronGolem()
/*  19:    */   {
/*  20: 30 */     this(0.0F);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ModelIronGolem(float par1)
/*  24:    */   {
/*  25: 35 */     this(par1, -7.0F);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ModelIronGolem(float par1, float par2)
/*  29:    */   {
/*  30: 40 */     short var3 = 128;
/*  31: 41 */     short var4 = 128;
/*  32: 42 */     this.ironGolemHead = new ModelRenderer(this).setTextureSize(var3, var4);
/*  33: 43 */     this.ironGolemHead.setRotationPoint(0.0F, 0.0F + par2, -2.0F);
/*  34: 44 */     this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, par1);
/*  35: 45 */     this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, par1);
/*  36: 46 */     this.ironGolemBody = new ModelRenderer(this).setTextureSize(var3, var4);
/*  37: 47 */     this.ironGolemBody.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
/*  38: 48 */     this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, par1);
/*  39: 49 */     this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, par1 + 0.5F);
/*  40: 50 */     this.ironGolemRightArm = new ModelRenderer(this).setTextureSize(var3, var4);
/*  41: 51 */     this.ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
/*  42: 52 */     this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, par1);
/*  43: 53 */     this.ironGolemLeftArm = new ModelRenderer(this).setTextureSize(var3, var4);
/*  44: 54 */     this.ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
/*  45: 55 */     this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, par1);
/*  46: 56 */     this.ironGolemLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(var3, var4);
/*  47: 57 */     this.ironGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + par2, 0.0F);
/*  48: 58 */     this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, par1);
/*  49: 59 */     this.ironGolemRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(var3, var4);
/*  50: 60 */     this.ironGolemRightLeg.mirror = true;
/*  51: 61 */     this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + par2, 0.0F);
/*  52: 62 */     this.ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, par1);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*  56:    */   {
/*  57: 70 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/*  58: 71 */     this.ironGolemHead.render(par7);
/*  59: 72 */     this.ironGolemBody.render(par7);
/*  60: 73 */     this.ironGolemLeftLeg.render(par7);
/*  61: 74 */     this.ironGolemRightLeg.render(par7);
/*  62: 75 */     this.ironGolemRightArm.render(par7);
/*  63: 76 */     this.ironGolemLeftArm.render(par7);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/*  67:    */   {
/*  68: 86 */     this.ironGolemHead.rotateAngleY = (par4 / 57.295776F);
/*  69: 87 */     this.ironGolemHead.rotateAngleX = (par5 / 57.295776F);
/*  70: 88 */     this.ironGolemLeftLeg.rotateAngleX = (-1.5F * func_78172_a(par1, 13.0F) * par2);
/*  71: 89 */     this.ironGolemRightLeg.rotateAngleX = (1.5F * func_78172_a(par1, 13.0F) * par2);
/*  72: 90 */     this.ironGolemLeftLeg.rotateAngleY = 0.0F;
/*  73: 91 */     this.ironGolemRightLeg.rotateAngleY = 0.0F;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/*  77:    */   {
/*  78:100 */     EntityIronGolem var5 = (EntityIronGolem)par1EntityLivingBase;
/*  79:101 */     int var6 = var5.getAttackTimer();
/*  80:103 */     if (var6 > 0)
/*  81:    */     {
/*  82:105 */       this.ironGolemRightArm.rotateAngleX = (-2.0F + 1.5F * func_78172_a(var6 - par4, 10.0F));
/*  83:106 */       this.ironGolemLeftArm.rotateAngleX = (-2.0F + 1.5F * func_78172_a(var6 - par4, 10.0F));
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87:110 */       int var7 = var5.getHoldRoseTick();
/*  88:112 */       if (var7 > 0)
/*  89:    */       {
/*  90:114 */         this.ironGolemRightArm.rotateAngleX = (-0.8F + 0.025F * func_78172_a(var7, 70.0F));
/*  91:115 */         this.ironGolemLeftArm.rotateAngleX = 0.0F;
/*  92:    */       }
/*  93:    */       else
/*  94:    */       {
/*  95:119 */         this.ironGolemRightArm.rotateAngleX = ((-0.2F + 1.5F * func_78172_a(par2, 13.0F)) * par3);
/*  96:120 */         this.ironGolemLeftArm.rotateAngleX = ((-0.2F - 1.5F * func_78172_a(par2, 13.0F)) * par3);
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   private float func_78172_a(float par1, float par2)
/* 102:    */   {
/* 103:127 */     return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F) / (par2 * 0.25F);
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelIronGolem
 * JD-Core Version:    0.7.0.1
 */