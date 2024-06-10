/*   1:    */ package net.minecraft.client.renderer.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.BlockChest;
/*   6:    */ import net.minecraft.client.model.ModelChest;
/*   7:    */ import net.minecraft.client.model.ModelLargeChest;
/*   8:    */ import net.minecraft.tileentity.TileEntity;
/*   9:    */ import net.minecraft.tileentity.TileEntityChest;
/*  10:    */ import net.minecraft.util.ResourceLocation;
/*  11:    */ import org.lwjgl.opengl.GL11;
/*  12:    */ 
/*  13:    */ public class TileEntityChestRenderer
/*  14:    */   extends TileEntitySpecialRenderer
/*  15:    */ {
/*  16: 16 */   private static final ResourceLocation field_147507_b = new ResourceLocation("textures/entity/chest/trapped_double.png");
/*  17: 17 */   private static final ResourceLocation field_147508_c = new ResourceLocation("textures/entity/chest/christmas_double.png");
/*  18: 18 */   private static final ResourceLocation field_147505_d = new ResourceLocation("textures/entity/chest/normal_double.png");
/*  19: 19 */   private static final ResourceLocation field_147506_e = new ResourceLocation("textures/entity/chest/trapped.png");
/*  20: 20 */   private static final ResourceLocation field_147503_f = new ResourceLocation("textures/entity/chest/christmas.png");
/*  21: 21 */   private static final ResourceLocation field_147504_g = new ResourceLocation("textures/entity/chest/normal.png");
/*  22: 22 */   private ModelChest field_147510_h = new ModelChest();
/*  23: 23 */   private ModelChest field_147511_i = new ModelLargeChest();
/*  24:    */   private boolean field_147509_j;
/*  25:    */   private static final String __OBFID = "CL_00000965";
/*  26:    */   
/*  27:    */   public TileEntityChestRenderer()
/*  28:    */   {
/*  29: 29 */     Calendar var1 = Calendar.getInstance();
/*  30: 31 */     if ((var1.get(2) + 1 == 12) && (var1.get(5) >= 24) && (var1.get(5) <= 26)) {
/*  31: 33 */       this.field_147509_j = true;
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void renderTileEntityAt(TileEntityChest p_147502_1_, double p_147502_2_, double p_147502_4_, double p_147502_6_, float p_147502_8_)
/*  36:    */   {
/*  37:    */     int var9;
/*  38:    */     int var9;
/*  39: 41 */     if (!p_147502_1_.hasWorldObj())
/*  40:    */     {
/*  41: 43 */       var9 = 0;
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45: 47 */       Block var10 = p_147502_1_.getBlockType();
/*  46: 48 */       var9 = p_147502_1_.getBlockMetadata();
/*  47: 50 */       if (((var10 instanceof BlockChest)) && (var9 == 0))
/*  48:    */       {
/*  49: 52 */         ((BlockChest)var10).func_149954_e(p_147502_1_.getWorldObj(), p_147502_1_.field_145851_c, p_147502_1_.field_145848_d, p_147502_1_.field_145849_e);
/*  50: 53 */         var9 = p_147502_1_.getBlockMetadata();
/*  51:    */       }
/*  52: 56 */       p_147502_1_.func_145979_i();
/*  53:    */     }
/*  54: 59 */     if ((p_147502_1_.field_145992_i == null) && (p_147502_1_.field_145991_k == null))
/*  55:    */     {
/*  56:    */       ModelChest var14;
/*  57: 63 */       if ((p_147502_1_.field_145990_j == null) && (p_147502_1_.field_145988_l == null))
/*  58:    */       {
/*  59: 65 */         ModelChest var14 = this.field_147510_h;
/*  60: 67 */         if (p_147502_1_.func_145980_j() == 1) {
/*  61: 69 */           bindTexture(field_147506_e);
/*  62: 71 */         } else if (this.field_147509_j) {
/*  63: 73 */           bindTexture(field_147503_f);
/*  64:    */         } else {
/*  65: 77 */           bindTexture(field_147504_g);
/*  66:    */         }
/*  67:    */       }
/*  68:    */       else
/*  69:    */       {
/*  70: 82 */         var14 = this.field_147511_i;
/*  71: 84 */         if (p_147502_1_.func_145980_j() == 1) {
/*  72: 86 */           bindTexture(field_147507_b);
/*  73: 88 */         } else if (this.field_147509_j) {
/*  74: 90 */           bindTexture(field_147508_c);
/*  75:    */         } else {
/*  76: 94 */           bindTexture(field_147505_d);
/*  77:    */         }
/*  78:    */       }
/*  79: 98 */       GL11.glPushMatrix();
/*  80: 99 */       GL11.glEnable(32826);
/*  81:100 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  82:101 */       GL11.glTranslatef((float)p_147502_2_, (float)p_147502_4_ + 1.0F, (float)p_147502_6_ + 1.0F);
/*  83:102 */       GL11.glScalef(1.0F, -1.0F, -1.0F);
/*  84:103 */       GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*  85:104 */       short var11 = 0;
/*  86:106 */       if (var9 == 2) {
/*  87:108 */         var11 = 180;
/*  88:    */       }
/*  89:111 */       if (var9 == 3) {
/*  90:113 */         var11 = 0;
/*  91:    */       }
/*  92:116 */       if (var9 == 4) {
/*  93:118 */         var11 = 90;
/*  94:    */       }
/*  95:121 */       if (var9 == 5) {
/*  96:123 */         var11 = -90;
/*  97:    */       }
/*  98:126 */       if ((var9 == 2) && (p_147502_1_.field_145990_j != null)) {
/*  99:128 */         GL11.glTranslatef(1.0F, 0.0F, 0.0F);
/* 100:    */       }
/* 101:131 */       if ((var9 == 5) && (p_147502_1_.field_145988_l != null)) {
/* 102:133 */         GL11.glTranslatef(0.0F, 0.0F, -1.0F);
/* 103:    */       }
/* 104:136 */       GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
/* 105:137 */       GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 106:138 */       float var12 = p_147502_1_.field_145986_n + (p_147502_1_.field_145989_m - p_147502_1_.field_145986_n) * p_147502_8_;
/* 107:141 */       if (p_147502_1_.field_145992_i != null)
/* 108:    */       {
/* 109:143 */         float var13 = p_147502_1_.field_145992_i.field_145986_n + (p_147502_1_.field_145992_i.field_145989_m - p_147502_1_.field_145992_i.field_145986_n) * p_147502_8_;
/* 110:145 */         if (var13 > var12) {
/* 111:147 */           var12 = var13;
/* 112:    */         }
/* 113:    */       }
/* 114:151 */       if (p_147502_1_.field_145991_k != null)
/* 115:    */       {
/* 116:153 */         float var13 = p_147502_1_.field_145991_k.field_145986_n + (p_147502_1_.field_145991_k.field_145989_m - p_147502_1_.field_145991_k.field_145986_n) * p_147502_8_;
/* 117:155 */         if (var13 > var12) {
/* 118:157 */           var12 = var13;
/* 119:    */         }
/* 120:    */       }
/* 121:161 */       var12 = 1.0F - var12;
/* 122:162 */       var12 = 1.0F - var12 * var12 * var12;
/* 123:163 */       var14.chestLid.rotateAngleX = (-(var12 * 3.141593F / 2.0F));
/* 124:164 */       var14.renderAll();
/* 125:165 */       GL11.glDisable(32826);
/* 126:166 */       GL11.glPopMatrix();
/* 127:167 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
/* 132:    */   {
/* 133:173 */     renderTileEntityAt((TileEntityChest)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntityChestRenderer
 * JD-Core Version:    0.7.0.1
 */