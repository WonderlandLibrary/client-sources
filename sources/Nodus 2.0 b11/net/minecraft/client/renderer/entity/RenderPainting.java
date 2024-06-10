/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.item.EntityPainting;
/*   7:    */ import net.minecraft.entity.item.EntityPainting.EnumArt;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ import net.minecraft.util.ResourceLocation;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ import org.lwjgl.opengl.GL11;
/*  12:    */ 
/*  13:    */ public class RenderPainting
/*  14:    */   extends Render
/*  15:    */ {
/*  16: 14 */   private static final ResourceLocation field_110807_a = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
/*  17:    */   private static final String __OBFID = "CL_00001018";
/*  18:    */   
/*  19:    */   public void doRender(EntityPainting par1EntityPainting, double par2, double par4, double par6, float par8, float par9)
/*  20:    */   {
/*  21: 25 */     GL11.glPushMatrix();
/*  22: 26 */     GL11.glTranslated(par2, par4, par6);
/*  23: 27 */     GL11.glRotatef(par8, 0.0F, 1.0F, 0.0F);
/*  24: 28 */     GL11.glEnable(32826);
/*  25: 29 */     bindEntityTexture(par1EntityPainting);
/*  26: 30 */     EntityPainting.EnumArt var10 = par1EntityPainting.art;
/*  27: 31 */     float var11 = 0.0625F;
/*  28: 32 */     GL11.glScalef(var11, var11, var11);
/*  29: 33 */     func_77010_a(par1EntityPainting, var10.sizeX, var10.sizeY, var10.offsetX, var10.offsetY);
/*  30: 34 */     GL11.glDisable(32826);
/*  31: 35 */     GL11.glPopMatrix();
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected ResourceLocation getEntityTexture(EntityPainting par1EntityPainting)
/*  35:    */   {
/*  36: 43 */     return field_110807_a;
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void func_77010_a(EntityPainting par1EntityPainting, int par2, int par3, int par4, int par5)
/*  40:    */   {
/*  41: 48 */     float var6 = -par2 / 2.0F;
/*  42: 49 */     float var7 = -par3 / 2.0F;
/*  43: 50 */     float var8 = 0.5F;
/*  44: 51 */     float var9 = 0.75F;
/*  45: 52 */     float var10 = 0.8125F;
/*  46: 53 */     float var11 = 0.0F;
/*  47: 54 */     float var12 = 0.0625F;
/*  48: 55 */     float var13 = 0.75F;
/*  49: 56 */     float var14 = 0.8125F;
/*  50: 57 */     float var15 = 0.00195313F;
/*  51: 58 */     float var16 = 0.00195313F;
/*  52: 59 */     float var17 = 0.7519531F;
/*  53: 60 */     float var18 = 0.7519531F;
/*  54: 61 */     float var19 = 0.0F;
/*  55: 62 */     float var20 = 0.0625F;
/*  56: 64 */     for (int var21 = 0; var21 < par2 / 16; var21++) {
/*  57: 66 */       for (int var22 = 0; var22 < par3 / 16; var22++)
/*  58:    */       {
/*  59: 68 */         float var23 = var6 + (var21 + 1) * 16;
/*  60: 69 */         float var24 = var6 + var21 * 16;
/*  61: 70 */         float var25 = var7 + (var22 + 1) * 16;
/*  62: 71 */         float var26 = var7 + var22 * 16;
/*  63: 72 */         func_77008_a(par1EntityPainting, (var23 + var24) / 2.0F, (var25 + var26) / 2.0F);
/*  64: 73 */         float var27 = (par4 + par2 - var21 * 16) / 256.0F;
/*  65: 74 */         float var28 = (par4 + par2 - (var21 + 1) * 16) / 256.0F;
/*  66: 75 */         float var29 = (par5 + par3 - var22 * 16) / 256.0F;
/*  67: 76 */         float var30 = (par5 + par3 - (var22 + 1) * 16) / 256.0F;
/*  68: 77 */         Tessellator var31 = Tessellator.instance;
/*  69: 78 */         var31.startDrawingQuads();
/*  70: 79 */         var31.setNormal(0.0F, 0.0F, -1.0F);
/*  71: 80 */         var31.addVertexWithUV(var23, var26, -var8, var28, var29);
/*  72: 81 */         var31.addVertexWithUV(var24, var26, -var8, var27, var29);
/*  73: 82 */         var31.addVertexWithUV(var24, var25, -var8, var27, var30);
/*  74: 83 */         var31.addVertexWithUV(var23, var25, -var8, var28, var30);
/*  75: 84 */         var31.setNormal(0.0F, 0.0F, 1.0F);
/*  76: 85 */         var31.addVertexWithUV(var23, var25, var8, var9, var11);
/*  77: 86 */         var31.addVertexWithUV(var24, var25, var8, var10, var11);
/*  78: 87 */         var31.addVertexWithUV(var24, var26, var8, var10, var12);
/*  79: 88 */         var31.addVertexWithUV(var23, var26, var8, var9, var12);
/*  80: 89 */         var31.setNormal(0.0F, 1.0F, 0.0F);
/*  81: 90 */         var31.addVertexWithUV(var23, var25, -var8, var13, var15);
/*  82: 91 */         var31.addVertexWithUV(var24, var25, -var8, var14, var15);
/*  83: 92 */         var31.addVertexWithUV(var24, var25, var8, var14, var16);
/*  84: 93 */         var31.addVertexWithUV(var23, var25, var8, var13, var16);
/*  85: 94 */         var31.setNormal(0.0F, -1.0F, 0.0F);
/*  86: 95 */         var31.addVertexWithUV(var23, var26, var8, var13, var15);
/*  87: 96 */         var31.addVertexWithUV(var24, var26, var8, var14, var15);
/*  88: 97 */         var31.addVertexWithUV(var24, var26, -var8, var14, var16);
/*  89: 98 */         var31.addVertexWithUV(var23, var26, -var8, var13, var16);
/*  90: 99 */         var31.setNormal(-1.0F, 0.0F, 0.0F);
/*  91:100 */         var31.addVertexWithUV(var23, var25, var8, var18, var19);
/*  92:101 */         var31.addVertexWithUV(var23, var26, var8, var18, var20);
/*  93:102 */         var31.addVertexWithUV(var23, var26, -var8, var17, var20);
/*  94:103 */         var31.addVertexWithUV(var23, var25, -var8, var17, var19);
/*  95:104 */         var31.setNormal(1.0F, 0.0F, 0.0F);
/*  96:105 */         var31.addVertexWithUV(var24, var25, -var8, var18, var19);
/*  97:106 */         var31.addVertexWithUV(var24, var26, -var8, var18, var20);
/*  98:107 */         var31.addVertexWithUV(var24, var26, var8, var17, var20);
/*  99:108 */         var31.addVertexWithUV(var24, var25, var8, var17, var19);
/* 100:109 */         var31.draw();
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void func_77008_a(EntityPainting par1EntityPainting, float par2, float par3)
/* 106:    */   {
/* 107:116 */     int var4 = MathHelper.floor_double(par1EntityPainting.posX);
/* 108:117 */     int var5 = MathHelper.floor_double(par1EntityPainting.posY + par3 / 16.0F);
/* 109:118 */     int var6 = MathHelper.floor_double(par1EntityPainting.posZ);
/* 110:120 */     if (par1EntityPainting.hangingDirection == 2) {
/* 111:122 */       var4 = MathHelper.floor_double(par1EntityPainting.posX + par2 / 16.0F);
/* 112:    */     }
/* 113:125 */     if (par1EntityPainting.hangingDirection == 1) {
/* 114:127 */       var6 = MathHelper.floor_double(par1EntityPainting.posZ - par2 / 16.0F);
/* 115:    */     }
/* 116:130 */     if (par1EntityPainting.hangingDirection == 0) {
/* 117:132 */       var4 = MathHelper.floor_double(par1EntityPainting.posX - par2 / 16.0F);
/* 118:    */     }
/* 119:135 */     if (par1EntityPainting.hangingDirection == 3) {
/* 120:137 */       var6 = MathHelper.floor_double(par1EntityPainting.posZ + par2 / 16.0F);
/* 121:    */     }
/* 122:140 */     int var7 = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(var4, var5, var6, 0);
/* 123:141 */     int var8 = var7 % 65536;
/* 124:142 */     int var9 = var7 / 65536;
/* 125:143 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var8, var9);
/* 126:144 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 130:    */   {
/* 131:152 */     return getEntityTexture((EntityPainting)par1Entity);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 135:    */   {
/* 136:163 */     doRender((EntityPainting)par1Entity, par2, par4, par6, par8, par9);
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderPainting
 * JD-Core Version:    0.7.0.1
 */