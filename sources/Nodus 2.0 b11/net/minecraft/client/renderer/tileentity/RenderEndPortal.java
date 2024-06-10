/*   1:    */ package net.minecraft.client.renderer.tileentity;
/*   2:    */ 
/*   3:    */ import java.nio.FloatBuffer;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*   7:    */ import net.minecraft.client.renderer.GLAllocation;
/*   8:    */ import net.minecraft.client.renderer.Tessellator;
/*   9:    */ import net.minecraft.tileentity.TileEntity;
/*  10:    */ import net.minecraft.tileentity.TileEntityEndPortal;
/*  11:    */ import net.minecraft.util.ResourceLocation;
/*  12:    */ import org.lwjgl.opengl.GL11;
/*  13:    */ 
/*  14:    */ public class RenderEndPortal
/*  15:    */   extends TileEntitySpecialRenderer
/*  16:    */ {
/*  17: 16 */   private static final ResourceLocation field_147529_c = new ResourceLocation("textures/environment/end_sky.png");
/*  18: 17 */   private static final ResourceLocation field_147526_d = new ResourceLocation("textures/entity/end_portal.png");
/*  19: 18 */   private static final Random field_147527_e = new Random(31100L);
/*  20: 19 */   FloatBuffer field_147528_b = GLAllocation.createDirectFloatBuffer(16);
/*  21:    */   private static final String __OBFID = "CL_00000972";
/*  22:    */   
/*  23:    */   public void renderTileEntityAt(TileEntityEndPortal p_147524_1_, double p_147524_2_, double p_147524_4_, double p_147524_6_, float p_147524_8_)
/*  24:    */   {
/*  25: 24 */     float var9 = (float)this.field_147501_a.field_147560_j;
/*  26: 25 */     float var10 = (float)this.field_147501_a.field_147561_k;
/*  27: 26 */     float var11 = (float)this.field_147501_a.field_147558_l;
/*  28: 27 */     GL11.glDisable(2896);
/*  29: 28 */     field_147527_e.setSeed(31100L);
/*  30: 29 */     float var12 = 0.75F;
/*  31: 31 */     for (int var13 = 0; var13 < 16; var13++)
/*  32:    */     {
/*  33: 33 */       GL11.glPushMatrix();
/*  34: 34 */       float var14 = 16 - var13;
/*  35: 35 */       float var15 = 0.0625F;
/*  36: 36 */       float var16 = 1.0F / (var14 + 1.0F);
/*  37: 38 */       if (var13 == 0)
/*  38:    */       {
/*  39: 40 */         bindTexture(field_147529_c);
/*  40: 41 */         var16 = 0.1F;
/*  41: 42 */         var14 = 65.0F;
/*  42: 43 */         var15 = 0.125F;
/*  43: 44 */         GL11.glEnable(3042);
/*  44: 45 */         GL11.glBlendFunc(770, 771);
/*  45:    */       }
/*  46: 48 */       if (var13 == 1)
/*  47:    */       {
/*  48: 50 */         bindTexture(field_147526_d);
/*  49: 51 */         GL11.glEnable(3042);
/*  50: 52 */         GL11.glBlendFunc(1, 1);
/*  51: 53 */         var15 = 0.5F;
/*  52:    */       }
/*  53: 56 */       float var17 = (float)-(p_147524_4_ + var12);
/*  54: 57 */       float var18 = var17 + ActiveRenderInfo.objectY;
/*  55: 58 */       float var19 = var17 + var14 + ActiveRenderInfo.objectY;
/*  56: 59 */       float var20 = var18 / var19;
/*  57: 60 */       var20 += (float)(p_147524_4_ + var12);
/*  58: 61 */       GL11.glTranslatef(var9, var20, var11);
/*  59: 62 */       GL11.glTexGeni(8192, 9472, 9217);
/*  60: 63 */       GL11.glTexGeni(8193, 9472, 9217);
/*  61: 64 */       GL11.glTexGeni(8194, 9472, 9217);
/*  62: 65 */       GL11.glTexGeni(8195, 9472, 9216);
/*  63: 66 */       GL11.glTexGen(8192, 9473, func_147525_a(1.0F, 0.0F, 0.0F, 0.0F));
/*  64: 67 */       GL11.glTexGen(8193, 9473, func_147525_a(0.0F, 0.0F, 1.0F, 0.0F));
/*  65: 68 */       GL11.glTexGen(8194, 9473, func_147525_a(0.0F, 0.0F, 0.0F, 1.0F));
/*  66: 69 */       GL11.glTexGen(8195, 9474, func_147525_a(0.0F, 1.0F, 0.0F, 0.0F));
/*  67: 70 */       GL11.glEnable(3168);
/*  68: 71 */       GL11.glEnable(3169);
/*  69: 72 */       GL11.glEnable(3170);
/*  70: 73 */       GL11.glEnable(3171);
/*  71: 74 */       GL11.glPopMatrix();
/*  72: 75 */       GL11.glMatrixMode(5890);
/*  73: 76 */       GL11.glPushMatrix();
/*  74: 77 */       GL11.glLoadIdentity();
/*  75: 78 */       GL11.glTranslatef(0.0F, (float)(Minecraft.getSystemTime() % 700000L) / 700000.0F, 0.0F);
/*  76: 79 */       GL11.glScalef(var15, var15, var15);
/*  77: 80 */       GL11.glTranslatef(0.5F, 0.5F, 0.0F);
/*  78: 81 */       GL11.glRotatef((var13 * var13 * 4321 + var13 * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
/*  79: 82 */       GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
/*  80: 83 */       GL11.glTranslatef(-var9, -var11, -var10);
/*  81: 84 */       var18 = var17 + ActiveRenderInfo.objectY;
/*  82: 85 */       GL11.glTranslatef(ActiveRenderInfo.objectX * var14 / var18, ActiveRenderInfo.objectZ * var14 / var18, -var10);
/*  83: 86 */       Tessellator var23 = Tessellator.instance;
/*  84: 87 */       var23.startDrawingQuads();
/*  85: 88 */       var20 = field_147527_e.nextFloat() * 0.5F + 0.1F;
/*  86: 89 */       float var21 = field_147527_e.nextFloat() * 0.5F + 0.4F;
/*  87: 90 */       float var22 = field_147527_e.nextFloat() * 0.5F + 0.5F;
/*  88: 92 */       if (var13 == 0)
/*  89:    */       {
/*  90: 94 */         var22 = 1.0F;
/*  91: 95 */         var21 = 1.0F;
/*  92: 96 */         var20 = 1.0F;
/*  93:    */       }
/*  94: 99 */       var23.setColorRGBA_F(var20 * var16, var21 * var16, var22 * var16, 1.0F);
/*  95:100 */       var23.addVertex(p_147524_2_, p_147524_4_ + var12, p_147524_6_);
/*  96:101 */       var23.addVertex(p_147524_2_, p_147524_4_ + var12, p_147524_6_ + 1.0D);
/*  97:102 */       var23.addVertex(p_147524_2_ + 1.0D, p_147524_4_ + var12, p_147524_6_ + 1.0D);
/*  98:103 */       var23.addVertex(p_147524_2_ + 1.0D, p_147524_4_ + var12, p_147524_6_);
/*  99:104 */       var23.draw();
/* 100:105 */       GL11.glPopMatrix();
/* 101:106 */       GL11.glMatrixMode(5888);
/* 102:    */     }
/* 103:109 */     GL11.glDisable(3042);
/* 104:110 */     GL11.glDisable(3168);
/* 105:111 */     GL11.glDisable(3169);
/* 106:112 */     GL11.glDisable(3170);
/* 107:113 */     GL11.glDisable(3171);
/* 108:114 */     GL11.glEnable(2896);
/* 109:    */   }
/* 110:    */   
/* 111:    */   private FloatBuffer func_147525_a(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_)
/* 112:    */   {
/* 113:119 */     this.field_147528_b.clear();
/* 114:120 */     this.field_147528_b.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
/* 115:121 */     this.field_147528_b.flip();
/* 116:122 */     return this.field_147528_b;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
/* 120:    */   {
/* 121:127 */     renderTileEntityAt((TileEntityEndPortal)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.RenderEndPortal
 * JD-Core Version:    0.7.0.1
 */