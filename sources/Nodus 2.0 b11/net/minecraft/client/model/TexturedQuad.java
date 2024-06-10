/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.util.Vec3;
/*  5:   */ 
/*  6:   */ public class TexturedQuad
/*  7:   */ {
/*  8:   */   public PositionTextureVertex[] vertexPositions;
/*  9:   */   public int nVertices;
/* 10:   */   private boolean invertNormal;
/* 11:   */   private static final String __OBFID = "CL_00000850";
/* 12:   */   
/* 13:   */   public TexturedQuad(PositionTextureVertex[] par1ArrayOfPositionTextureVertex)
/* 14:   */   {
/* 15:15 */     this.vertexPositions = par1ArrayOfPositionTextureVertex;
/* 16:16 */     this.nVertices = par1ArrayOfPositionTextureVertex.length;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public TexturedQuad(PositionTextureVertex[] par1ArrayOfPositionTextureVertex, int par2, int par3, int par4, int par5, float par6, float par7)
/* 20:   */   {
/* 21:21 */     this(par1ArrayOfPositionTextureVertex);
/* 22:22 */     float var8 = 0.0F / par6;
/* 23:23 */     float var9 = 0.0F / par7;
/* 24:24 */     par1ArrayOfPositionTextureVertex[0] = par1ArrayOfPositionTextureVertex[0].setTexturePosition(par4 / par6 - var8, par3 / par7 + var9);
/* 25:25 */     par1ArrayOfPositionTextureVertex[1] = par1ArrayOfPositionTextureVertex[1].setTexturePosition(par2 / par6 + var8, par3 / par7 + var9);
/* 26:26 */     par1ArrayOfPositionTextureVertex[2] = par1ArrayOfPositionTextureVertex[2].setTexturePosition(par2 / par6 + var8, par5 / par7 - var9);
/* 27:27 */     par1ArrayOfPositionTextureVertex[3] = par1ArrayOfPositionTextureVertex[3].setTexturePosition(par4 / par6 - var8, par5 / par7 - var9);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void flipFace()
/* 31:   */   {
/* 32:32 */     PositionTextureVertex[] var1 = new PositionTextureVertex[this.vertexPositions.length];
/* 33:34 */     for (int var2 = 0; var2 < this.vertexPositions.length; var2++) {
/* 34:36 */       var1[var2] = this.vertexPositions[(this.vertexPositions.length - var2 - 1)];
/* 35:   */     }
/* 36:39 */     this.vertexPositions = var1;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void draw(Tessellator par1Tessellator, float par2)
/* 40:   */   {
/* 41:44 */     Vec3 var3 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
/* 42:45 */     Vec3 var4 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
/* 43:46 */     Vec3 var5 = var4.crossProduct(var3).normalize();
/* 44:47 */     par1Tessellator.startDrawingQuads();
/* 45:49 */     if (this.invertNormal) {
/* 46:51 */       par1Tessellator.setNormal(-(float)var5.xCoord, -(float)var5.yCoord, -(float)var5.zCoord);
/* 47:   */     } else {
/* 48:55 */       par1Tessellator.setNormal((float)var5.xCoord, (float)var5.yCoord, (float)var5.zCoord);
/* 49:   */     }
/* 50:58 */     for (int var6 = 0; var6 < 4; var6++)
/* 51:   */     {
/* 52:60 */       PositionTextureVertex var7 = this.vertexPositions[var6];
/* 53:61 */       par1Tessellator.addVertexWithUV((float)var7.vector3D.xCoord * par2, (float)var7.vector3D.yCoord * par2, (float)var7.vector3D.zCoord * par2, var7.texturePositionX, var7.texturePositionY);
/* 54:   */     }
/* 55:64 */     par1Tessellator.draw();
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.TexturedQuad
 * JD-Core Version:    0.7.0.1
 */