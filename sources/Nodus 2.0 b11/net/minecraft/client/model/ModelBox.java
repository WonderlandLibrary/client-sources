/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.renderer.Tessellator;
/*   4:    */ 
/*   5:    */ public class ModelBox
/*   6:    */ {
/*   7:    */   private PositionTextureVertex[] vertexPositions;
/*   8:    */   private TexturedQuad[] quadList;
/*   9:    */   public final float posX1;
/*  10:    */   public final float posY1;
/*  11:    */   public final float posZ1;
/*  12:    */   public final float posX2;
/*  13:    */   public final float posY2;
/*  14:    */   public final float posZ2;
/*  15:    */   public String field_78247_g;
/*  16:    */   private static final String __OBFID = "CL_00000872";
/*  17:    */   
/*  18:    */   public ModelBox(ModelRenderer par1ModelRenderer, int par2, int par3, float par4, float par5, float par6, int par7, int par8, int par9, float par10)
/*  19:    */   {
/*  20: 37 */     this.posX1 = par4;
/*  21: 38 */     this.posY1 = par5;
/*  22: 39 */     this.posZ1 = par6;
/*  23: 40 */     this.posX2 = (par4 + par7);
/*  24: 41 */     this.posY2 = (par5 + par8);
/*  25: 42 */     this.posZ2 = (par6 + par9);
/*  26: 43 */     this.vertexPositions = new PositionTextureVertex[8];
/*  27: 44 */     this.quadList = new TexturedQuad[6];
/*  28: 45 */     float var11 = par4 + par7;
/*  29: 46 */     float var12 = par5 + par8;
/*  30: 47 */     float var13 = par6 + par9;
/*  31: 48 */     par4 -= par10;
/*  32: 49 */     par5 -= par10;
/*  33: 50 */     par6 -= par10;
/*  34: 51 */     var11 += par10;
/*  35: 52 */     var12 += par10;
/*  36: 53 */     var13 += par10;
/*  37: 55 */     if (par1ModelRenderer.mirror)
/*  38:    */     {
/*  39: 57 */       float var14 = var11;
/*  40: 58 */       var11 = par4;
/*  41: 59 */       par4 = var14;
/*  42:    */     }
/*  43: 62 */     PositionTextureVertex var23 = new PositionTextureVertex(par4, par5, par6, 0.0F, 0.0F);
/*  44: 63 */     PositionTextureVertex var15 = new PositionTextureVertex(var11, par5, par6, 0.0F, 8.0F);
/*  45: 64 */     PositionTextureVertex var16 = new PositionTextureVertex(var11, var12, par6, 8.0F, 8.0F);
/*  46: 65 */     PositionTextureVertex var17 = new PositionTextureVertex(par4, var12, par6, 8.0F, 0.0F);
/*  47: 66 */     PositionTextureVertex var18 = new PositionTextureVertex(par4, par5, var13, 0.0F, 0.0F);
/*  48: 67 */     PositionTextureVertex var19 = new PositionTextureVertex(var11, par5, var13, 0.0F, 8.0F);
/*  49: 68 */     PositionTextureVertex var20 = new PositionTextureVertex(var11, var12, var13, 8.0F, 8.0F);
/*  50: 69 */     PositionTextureVertex var21 = new PositionTextureVertex(par4, var12, var13, 8.0F, 0.0F);
/*  51: 70 */     this.vertexPositions[0] = var23;
/*  52: 71 */     this.vertexPositions[1] = var15;
/*  53: 72 */     this.vertexPositions[2] = var16;
/*  54: 73 */     this.vertexPositions[3] = var17;
/*  55: 74 */     this.vertexPositions[4] = var18;
/*  56: 75 */     this.vertexPositions[5] = var19;
/*  57: 76 */     this.vertexPositions[6] = var20;
/*  58: 77 */     this.vertexPositions[7] = var21;
/*  59: 78 */     this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] { var19, var15, var16, var20 }, par2 + par9 + par7, par3 + par9, par2 + par9 + par7 + par9, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
/*  60: 79 */     this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] { var23, var18, var21, var17 }, par2, par3 + par9, par2 + par9, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
/*  61: 80 */     this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] { var19, var18, var23, var15 }, par2 + par9, par3, par2 + par9 + par7, par3 + par9, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
/*  62: 81 */     this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] { var16, var17, var21, var20 }, par2 + par9 + par7, par3 + par9, par2 + par9 + par7 + par7, par3, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
/*  63: 82 */     this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] { var15, var23, var17, var16 }, par2 + par9, par3 + par9, par2 + par9 + par7, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
/*  64: 83 */     this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] { var18, var19, var20, var21 }, par2 + par9 + par7 + par9, par3 + par9, par2 + par9 + par7 + par9 + par7, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
/*  65: 85 */     if (par1ModelRenderer.mirror) {
/*  66: 87 */       for (int var22 = 0; var22 < this.quadList.length; var22++) {
/*  67: 89 */         this.quadList[var22].flipFace();
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void render(Tessellator par1Tessellator, float par2)
/*  73:    */   {
/*  74: 99 */     for (int var3 = 0; var3 < this.quadList.length; var3++) {
/*  75:101 */       this.quadList[var3].draw(par1Tessellator, par2);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public ModelBox func_78244_a(String par1Str)
/*  80:    */   {
/*  81:107 */     this.field_78247_g = par1Str;
/*  82:108 */     return this;
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelBox
 * JD-Core Version:    0.7.0.1
 */