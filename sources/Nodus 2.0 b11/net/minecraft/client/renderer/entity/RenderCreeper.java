/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelBase;
/*   4:    */ import net.minecraft.client.model.ModelCreeper;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.monster.EntityCreeper;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ import net.minecraft.util.ResourceLocation;
/*  10:    */ import org.lwjgl.opengl.GL11;
/*  11:    */ 
/*  12:    */ public class RenderCreeper
/*  13:    */   extends RenderLiving
/*  14:    */ {
/*  15: 14 */   private static final ResourceLocation armoredCreeperTextures = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
/*  16: 15 */   private static final ResourceLocation creeperTextures = new ResourceLocation("textures/entity/creeper/creeper.png");
/*  17: 18 */   private ModelBase creeperModel = new ModelCreeper(2.0F);
/*  18:    */   private static final String __OBFID = "CL_00000985";
/*  19:    */   
/*  20:    */   public RenderCreeper()
/*  21:    */   {
/*  22: 23 */     super(new ModelCreeper(), 0.5F);
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected void preRenderCallback(EntityCreeper par1EntityCreeper, float par2)
/*  26:    */   {
/*  27: 32 */     float var3 = par1EntityCreeper.getCreeperFlashIntensity(par2);
/*  28: 33 */     float var4 = 1.0F + MathHelper.sin(var3 * 100.0F) * var3 * 0.01F;
/*  29: 35 */     if (var3 < 0.0F) {
/*  30: 37 */       var3 = 0.0F;
/*  31:    */     }
/*  32: 40 */     if (var3 > 1.0F) {
/*  33: 42 */       var3 = 1.0F;
/*  34:    */     }
/*  35: 45 */     var3 *= var3;
/*  36: 46 */     var3 *= var3;
/*  37: 47 */     float var5 = (1.0F + var3 * 0.4F) * var4;
/*  38: 48 */     float var6 = (1.0F + var3 * 0.1F) / var4;
/*  39: 49 */     GL11.glScalef(var5, var6, var5);
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected int getColorMultiplier(EntityCreeper par1EntityCreeper, float par2, float par3)
/*  43:    */   {
/*  44: 57 */     float var4 = par1EntityCreeper.getCreeperFlashIntensity(par3);
/*  45: 59 */     if ((int)(var4 * 10.0F) % 2 == 0) {
/*  46: 61 */       return 0;
/*  47:    */     }
/*  48: 65 */     int var5 = (int)(var4 * 0.2F * 255.0F);
/*  49: 67 */     if (var5 < 0) {
/*  50: 69 */       var5 = 0;
/*  51:    */     }
/*  52: 72 */     if (var5 > 255) {
/*  53: 74 */       var5 = 255;
/*  54:    */     }
/*  55: 77 */     short var6 = 255;
/*  56: 78 */     short var7 = 255;
/*  57: 79 */     short var8 = 255;
/*  58: 80 */     return var5 << 24 | var6 << 16 | var7 << 8 | var8;
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected int shouldRenderPass(EntityCreeper par1EntityCreeper, int par2, float par3)
/*  62:    */   {
/*  63: 89 */     if (par1EntityCreeper.getPowered())
/*  64:    */     {
/*  65: 91 */       if (par1EntityCreeper.isInvisible()) {
/*  66: 93 */         GL11.glDepthMask(false);
/*  67:    */       } else {
/*  68: 97 */         GL11.glDepthMask(true);
/*  69:    */       }
/*  70:100 */       if (par2 == 1)
/*  71:    */       {
/*  72:102 */         float var4 = par1EntityCreeper.ticksExisted + par3;
/*  73:103 */         bindTexture(armoredCreeperTextures);
/*  74:104 */         GL11.glMatrixMode(5890);
/*  75:105 */         GL11.glLoadIdentity();
/*  76:106 */         float var5 = var4 * 0.01F;
/*  77:107 */         float var6 = var4 * 0.01F;
/*  78:108 */         GL11.glTranslatef(var5, var6, 0.0F);
/*  79:109 */         setRenderPassModel(this.creeperModel);
/*  80:110 */         GL11.glMatrixMode(5888);
/*  81:111 */         GL11.glEnable(3042);
/*  82:112 */         float var7 = 0.5F;
/*  83:113 */         GL11.glColor4f(var7, var7, var7, 1.0F);
/*  84:114 */         GL11.glDisable(2896);
/*  85:115 */         GL11.glBlendFunc(1, 1);
/*  86:116 */         return 1;
/*  87:    */       }
/*  88:119 */       if (par2 == 2)
/*  89:    */       {
/*  90:121 */         GL11.glMatrixMode(5890);
/*  91:122 */         GL11.glLoadIdentity();
/*  92:123 */         GL11.glMatrixMode(5888);
/*  93:124 */         GL11.glEnable(2896);
/*  94:125 */         GL11.glDisable(3042);
/*  95:    */       }
/*  96:    */     }
/*  97:129 */     return -1;
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected int inheritRenderPass(EntityCreeper par1EntityCreeper, int par2, float par3)
/* 101:    */   {
/* 102:134 */     return -1;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected ResourceLocation getEntityTexture(EntityCreeper par1EntityCreeper)
/* 106:    */   {
/* 107:142 */     return creeperTextures;
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 111:    */   {
/* 112:151 */     preRenderCallback((EntityCreeper)par1EntityLivingBase, par2);
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase, float par2, float par3)
/* 116:    */   {
/* 117:159 */     return getColorMultiplier((EntityCreeper)par1EntityLivingBase, par2, par3);
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 121:    */   {
/* 122:167 */     return shouldRenderPass((EntityCreeper)par1EntityLivingBase, par2, par3);
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected int inheritRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 126:    */   {
/* 127:172 */     return inheritRenderPass((EntityCreeper)par1EntityLivingBase, par2, par3);
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 131:    */   {
/* 132:180 */     return getEntityTexture((EntityCreeper)par1Entity);
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderCreeper
 * JD-Core Version:    0.7.0.1
 */