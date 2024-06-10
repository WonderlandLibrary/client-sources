/*  1:   */ package net.minecraft.client.shader;
/*  2:   */ 
/*  3:   */ public class TesselatorVertexState
/*  4:   */ {
/*  5:   */   private int[] rawBuffer;
/*  6:   */   private int rawBufferIndex;
/*  7:   */   private int vertexCount;
/*  8:   */   private boolean hasTexture;
/*  9:   */   private boolean hasBrightness;
/* 10:   */   private boolean hasNormals;
/* 11:   */   private boolean hasColor;
/* 12:   */   private static final String __OBFID = "CL_00000961";
/* 13:   */   
/* 14:   */   public TesselatorVertexState(int[] p_i45079_1_, int p_i45079_2_, int p_i45079_3_, boolean p_i45079_4_, boolean p_i45079_5_, boolean p_i45079_6_, boolean p_i45079_7_)
/* 15:   */   {
/* 16:16 */     this.rawBuffer = p_i45079_1_;
/* 17:17 */     this.rawBufferIndex = p_i45079_2_;
/* 18:18 */     this.vertexCount = p_i45079_3_;
/* 19:19 */     this.hasTexture = p_i45079_4_;
/* 20:20 */     this.hasBrightness = p_i45079_5_;
/* 21:21 */     this.hasNormals = p_i45079_6_;
/* 22:22 */     this.hasColor = p_i45079_7_;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int[] getRawBuffer()
/* 26:   */   {
/* 27:27 */     return this.rawBuffer;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getRawBufferIndex()
/* 31:   */   {
/* 32:32 */     return this.rawBufferIndex;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int getVertexCount()
/* 36:   */   {
/* 37:37 */     return this.vertexCount;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean getHasTexture()
/* 41:   */   {
/* 42:42 */     return this.hasTexture;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean getHasBrightness()
/* 46:   */   {
/* 47:47 */     return this.hasBrightness;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean getHasNormals()
/* 51:   */   {
/* 52:52 */     return this.hasNormals;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean getHasColor()
/* 56:   */   {
/* 57:57 */     return this.hasColor;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void addTessellatorVertexState(TesselatorVertexState tsv)
/* 61:   */   {
/* 62:62 */     if (tsv != null) {
/* 63:64 */       if ((tsv.hasBrightness == this.hasBrightness) && (tsv.hasColor == this.hasColor) && (tsv.hasNormals == this.hasNormals) && (tsv.hasTexture == this.hasTexture))
/* 64:   */       {
/* 65:66 */         int newRawBufferIndex = this.rawBufferIndex + tsv.rawBufferIndex;
/* 66:67 */         int[] newRawBuffer = new int[newRawBufferIndex];
/* 67:68 */         System.arraycopy(this.rawBuffer, 0, newRawBuffer, 0, this.rawBufferIndex);
/* 68:69 */         System.arraycopy(tsv.rawBuffer, 0, newRawBuffer, this.rawBufferIndex, tsv.rawBufferIndex);
/* 69:70 */         this.rawBuffer = newRawBuffer;
/* 70:71 */         this.rawBufferIndex = newRawBufferIndex;
/* 71:72 */         this.vertexCount += tsv.vertexCount;
/* 72:   */       }
/* 73:   */       else
/* 74:   */       {
/* 75:76 */         throw new IllegalArgumentException("Incompatible vertex states");
/* 76:   */       }
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.shader.TesselatorVertexState
 * JD-Core Version:    0.7.0.1
 */