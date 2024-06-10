/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.Vec3;
/*  4:   */ 
/*  5:   */ public class PositionTextureVertex
/*  6:   */ {
/*  7:   */   public Vec3 vector3D;
/*  8:   */   public float texturePositionX;
/*  9:   */   public float texturePositionY;
/* 10:   */   private static final String __OBFID = "CL_00000862";
/* 11:   */   
/* 12:   */   public PositionTextureVertex(float par1, float par2, float par3, float par4, float par5)
/* 13:   */   {
/* 14:14 */     this(Vec3.createVectorHelper(par1, par2, par3), par4, par5);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public PositionTextureVertex setTexturePosition(float par1, float par2)
/* 18:   */   {
/* 19:19 */     return new PositionTextureVertex(this, par1, par2);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public PositionTextureVertex(PositionTextureVertex par1PositionTextureVertex, float par2, float par3)
/* 23:   */   {
/* 24:24 */     this.vector3D = par1PositionTextureVertex.vector3D;
/* 25:25 */     this.texturePositionX = par2;
/* 26:26 */     this.texturePositionY = par3;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public PositionTextureVertex(Vec3 par1Vec3, float par2, float par3)
/* 30:   */   {
/* 31:31 */     this.vector3D = par1Vec3;
/* 32:32 */     this.texturePositionX = par2;
/* 33:33 */     this.texturePositionY = par3;
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.PositionTextureVertex
 * JD-Core Version:    0.7.0.1
 */