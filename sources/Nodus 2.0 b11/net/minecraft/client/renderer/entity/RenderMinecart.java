/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.client.model.ModelBase;
/*   5:    */ import net.minecraft.client.model.ModelMinecart;
/*   6:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   7:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.item.EntityMinecart;
/*  10:    */ import net.minecraft.util.MathHelper;
/*  11:    */ import net.minecraft.util.ResourceLocation;
/*  12:    */ import net.minecraft.util.Vec3;
/*  13:    */ import org.lwjgl.opengl.GL11;
/*  14:    */ 
/*  15:    */ public class RenderMinecart
/*  16:    */   extends Render
/*  17:    */ {
/*  18: 17 */   private static final ResourceLocation minecartTextures = new ResourceLocation("textures/entity/minecart.png");
/*  19: 20 */   protected ModelBase modelMinecart = new ModelMinecart();
/*  20:    */   protected final RenderBlocks field_94145_f;
/*  21:    */   private static final String __OBFID = "CL_00001013";
/*  22:    */   
/*  23:    */   public RenderMinecart()
/*  24:    */   {
/*  25: 26 */     this.shadowSize = 0.5F;
/*  26: 27 */     this.field_94145_f = new RenderBlocks();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void doRender(EntityMinecart par1EntityMinecart, double par2, double par4, double par6, float par8, float par9)
/*  30:    */   {
/*  31: 38 */     GL11.glPushMatrix();
/*  32: 39 */     bindEntityTexture(par1EntityMinecart);
/*  33: 40 */     long var10 = par1EntityMinecart.getEntityId() * 493286711L;
/*  34: 41 */     var10 = var10 * var10 * 4392167121L + var10 * 98761L;
/*  35: 42 */     float var12 = (((float)(var10 >> 16 & 0x7) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  36: 43 */     float var13 = (((float)(var10 >> 20 & 0x7) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  37: 44 */     float var14 = (((float)(var10 >> 24 & 0x7) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  38: 45 */     GL11.glTranslatef(var12, var13, var14);
/*  39: 46 */     double var15 = par1EntityMinecart.lastTickPosX + (par1EntityMinecart.posX - par1EntityMinecart.lastTickPosX) * par9;
/*  40: 47 */     double var17 = par1EntityMinecart.lastTickPosY + (par1EntityMinecart.posY - par1EntityMinecart.lastTickPosY) * par9;
/*  41: 48 */     double var19 = par1EntityMinecart.lastTickPosZ + (par1EntityMinecart.posZ - par1EntityMinecart.lastTickPosZ) * par9;
/*  42: 49 */     double var21 = 0.300000011920929D;
/*  43: 50 */     Vec3 var23 = par1EntityMinecart.func_70489_a(var15, var17, var19);
/*  44: 51 */     float var24 = par1EntityMinecart.prevRotationPitch + (par1EntityMinecart.rotationPitch - par1EntityMinecart.prevRotationPitch) * par9;
/*  45: 53 */     if (var23 != null)
/*  46:    */     {
/*  47: 55 */       Vec3 var25 = par1EntityMinecart.func_70495_a(var15, var17, var19, var21);
/*  48: 56 */       Vec3 var26 = par1EntityMinecart.func_70495_a(var15, var17, var19, -var21);
/*  49: 58 */       if (var25 == null) {
/*  50: 60 */         var25 = var23;
/*  51:    */       }
/*  52: 63 */       if (var26 == null) {
/*  53: 65 */         var26 = var23;
/*  54:    */       }
/*  55: 68 */       par2 += var23.xCoord - var15;
/*  56: 69 */       par4 += (var25.yCoord + var26.yCoord) / 2.0D - var17;
/*  57: 70 */       par6 += var23.zCoord - var19;
/*  58: 71 */       Vec3 var27 = var26.addVector(-var25.xCoord, -var25.yCoord, -var25.zCoord);
/*  59: 73 */       if (var27.lengthVector() != 0.0D)
/*  60:    */       {
/*  61: 75 */         var27 = var27.normalize();
/*  62: 76 */         par8 = (float)(Math.atan2(var27.zCoord, var27.xCoord) * 180.0D / 3.141592653589793D);
/*  63: 77 */         var24 = (float)(Math.atan(var27.yCoord) * 73.0D);
/*  64:    */       }
/*  65:    */     }
/*  66: 81 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/*  67: 82 */     GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
/*  68: 83 */     GL11.glRotatef(-var24, 0.0F, 0.0F, 1.0F);
/*  69: 84 */     float var31 = par1EntityMinecart.getRollingAmplitude() - par9;
/*  70: 85 */     float var33 = par1EntityMinecart.getDamage() - par9;
/*  71: 87 */     if (var33 < 0.0F) {
/*  72: 89 */       var33 = 0.0F;
/*  73:    */     }
/*  74: 92 */     if (var31 > 0.0F) {
/*  75: 94 */       GL11.glRotatef(MathHelper.sin(var31) * var31 * var33 / 10.0F * par1EntityMinecart.getRollingDirection(), 1.0F, 0.0F, 0.0F);
/*  76:    */     }
/*  77: 97 */     int var32 = par1EntityMinecart.getDisplayTileOffset();
/*  78: 98 */     Block var28 = par1EntityMinecart.func_145820_n();
/*  79: 99 */     int var29 = par1EntityMinecart.getDisplayTileData();
/*  80:101 */     if (var28.getRenderType() != -1)
/*  81:    */     {
/*  82:103 */       GL11.glPushMatrix();
/*  83:104 */       bindTexture(TextureMap.locationBlocksTexture);
/*  84:105 */       float var30 = 0.75F;
/*  85:106 */       GL11.glScalef(var30, var30, var30);
/*  86:107 */       GL11.glTranslatef(0.0F, var32 / 16.0F, 0.0F);
/*  87:108 */       func_147910_a(par1EntityMinecart, par9, var28, var29);
/*  88:109 */       GL11.glPopMatrix();
/*  89:110 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  90:111 */       bindEntityTexture(par1EntityMinecart);
/*  91:    */     }
/*  92:114 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/*  93:115 */     this.modelMinecart.render(par1EntityMinecart, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/*  94:116 */     GL11.glPopMatrix();
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected ResourceLocation getEntityTexture(EntityMinecart par1EntityMinecart)
/*  98:    */   {
/*  99:124 */     return minecartTextures;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected void func_147910_a(EntityMinecart p_147910_1_, float p_147910_2_, Block p_147910_3_, int p_147910_4_)
/* 103:    */   {
/* 104:129 */     float var5 = p_147910_1_.getBrightness(p_147910_2_);
/* 105:130 */     GL11.glPushMatrix();
/* 106:131 */     this.field_94145_f.renderBlockAsItem(p_147910_3_, p_147910_4_, var5);
/* 107:132 */     GL11.glPopMatrix();
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 111:    */   {
/* 112:140 */     return getEntityTexture((EntityMinecart)par1Entity);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 116:    */   {
/* 117:151 */     doRender((EntityMinecart)par1Entity, par2, par4, par6, par8, par9);
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderMinecart
 * JD-Core Version:    0.7.0.1
 */