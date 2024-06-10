/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelWither;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.EntityLiving;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.boss.BossStatus;
/*   8:    */ import net.minecraft.entity.boss.EntityWither;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.util.ResourceLocation;
/*  11:    */ import org.lwjgl.opengl.GL11;
/*  12:    */ 
/*  13:    */ public class RenderWither
/*  14:    */   extends RenderLiving
/*  15:    */ {
/*  16: 15 */   private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
/*  17: 16 */   private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
/*  18:    */   private int field_82419_a;
/*  19:    */   private static final String __OBFID = "CL_00001034";
/*  20:    */   
/*  21:    */   public RenderWither()
/*  22:    */   {
/*  23: 22 */     super(new ModelWither(), 1.0F);
/*  24: 23 */     this.field_82419_a = ((ModelWither)this.mainModel).func_82903_a();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void doRender(EntityWither par1EntityWither, double par2, double par4, double par6, float par8, float par9)
/*  28:    */   {
/*  29: 34 */     BossStatus.setBossStatus(par1EntityWither, true);
/*  30: 35 */     int var10 = ((ModelWither)this.mainModel).func_82903_a();
/*  31: 37 */     if (var10 != this.field_82419_a)
/*  32:    */     {
/*  33: 39 */       this.field_82419_a = var10;
/*  34: 40 */       this.mainModel = new ModelWither();
/*  35:    */     }
/*  36: 43 */     super.doRender(par1EntityWither, par2, par4, par6, par8, par9);
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected ResourceLocation getEntityTexture(EntityWither par1EntityWither)
/*  40:    */   {
/*  41: 51 */     int var2 = par1EntityWither.func_82212_n();
/*  42: 52 */     return (var2 > 0) && ((var2 > 80) || (var2 / 5 % 2 != 1)) ? invulnerableWitherTextures : witherTextures;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void preRenderCallback(EntityWither par1EntityWither, float par2)
/*  46:    */   {
/*  47: 61 */     int var3 = par1EntityWither.func_82212_n();
/*  48: 63 */     if (var3 > 0)
/*  49:    */     {
/*  50: 65 */       float var4 = 2.0F - (var3 - par2) / 220.0F * 0.5F;
/*  51: 66 */       GL11.glScalef(var4, var4, var4);
/*  52:    */     }
/*  53:    */     else
/*  54:    */     {
/*  55: 70 */       GL11.glScalef(2.0F, 2.0F, 2.0F);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected int shouldRenderPass(EntityWither par1EntityWither, int par2, float par3)
/*  60:    */   {
/*  61: 79 */     if (par1EntityWither.isArmored())
/*  62:    */     {
/*  63: 81 */       if (par1EntityWither.isInvisible()) {
/*  64: 83 */         GL11.glDepthMask(false);
/*  65:    */       } else {
/*  66: 87 */         GL11.glDepthMask(true);
/*  67:    */       }
/*  68: 90 */       if (par2 == 1)
/*  69:    */       {
/*  70: 92 */         float var4 = par1EntityWither.ticksExisted + par3;
/*  71: 93 */         bindTexture(invulnerableWitherTextures);
/*  72: 94 */         GL11.glMatrixMode(5890);
/*  73: 95 */         GL11.glLoadIdentity();
/*  74: 96 */         float var5 = MathHelper.cos(var4 * 0.02F) * 3.0F;
/*  75: 97 */         float var6 = var4 * 0.01F;
/*  76: 98 */         GL11.glTranslatef(var5, var6, 0.0F);
/*  77: 99 */         setRenderPassModel(this.mainModel);
/*  78:100 */         GL11.glMatrixMode(5888);
/*  79:101 */         GL11.glEnable(3042);
/*  80:102 */         float var7 = 0.5F;
/*  81:103 */         GL11.glColor4f(var7, var7, var7, 1.0F);
/*  82:104 */         GL11.glDisable(2896);
/*  83:105 */         GL11.glBlendFunc(1, 1);
/*  84:106 */         GL11.glTranslatef(0.0F, -0.01F, 0.0F);
/*  85:107 */         GL11.glScalef(1.1F, 1.1F, 1.1F);
/*  86:108 */         return 1;
/*  87:    */       }
/*  88:111 */       if (par2 == 2)
/*  89:    */       {
/*  90:113 */         GL11.glMatrixMode(5890);
/*  91:114 */         GL11.glLoadIdentity();
/*  92:115 */         GL11.glMatrixMode(5888);
/*  93:116 */         GL11.glEnable(2896);
/*  94:117 */         GL11.glDisable(3042);
/*  95:    */       }
/*  96:    */     }
/*  97:121 */     return -1;
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected int inheritRenderPass(EntityWither par1EntityWither, int par2, float par3)
/* 101:    */   {
/* 102:126 */     return -1;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/* 106:    */   {
/* 107:137 */     doRender((EntityWither)par1EntityLiving, par2, par4, par6, par8, par9);
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 111:    */   {
/* 112:146 */     preRenderCallback((EntityWither)par1EntityLivingBase, par2);
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 116:    */   {
/* 117:154 */     return shouldRenderPass((EntityWither)par1EntityLivingBase, par2, par3);
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected int inheritRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 121:    */   {
/* 122:159 */     return inheritRenderPass((EntityWither)par1EntityLivingBase, par2, par3);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 126:    */   {
/* 127:170 */     doRender((EntityWither)par1Entity, par2, par4, par6, par8, par9);
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 131:    */   {
/* 132:178 */     return getEntityTexture((EntityWither)par1Entity);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 136:    */   {
/* 137:189 */     doRender((EntityWither)par1Entity, par2, par4, par6, par8, par9);
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderWither
 * JD-Core Version:    0.7.0.1
 */