/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ import java.nio.IntBuffer;
/*  4:   */ import org.lwjgl.opengl.GL11;
/*  5:   */ 
/*  6:   */ public class RenderList
/*  7:   */ {
/*  8:   */   public int renderChunkX;
/*  9:   */   public int renderChunkY;
/* 10:   */   public int renderChunkZ;
/* 11:   */   private double cameraX;
/* 12:   */   private double cameraY;
/* 13:   */   private double cameraZ;
/* 14:23 */   private IntBuffer glLists = GLAllocation.createDirectIntBuffer(65536);
/* 15:   */   private boolean valid;
/* 16:   */   private boolean bufferFlipped;
/* 17:   */   private static final String __OBFID = "CL_00000957";
/* 18:   */   
/* 19:   */   public void setupRenderList(int par1, int par2, int par3, double par4, double par6, double par8)
/* 20:   */   {
/* 21:36 */     this.valid = true;
/* 22:37 */     this.glLists.clear();
/* 23:38 */     this.renderChunkX = par1;
/* 24:39 */     this.renderChunkY = par2;
/* 25:40 */     this.renderChunkZ = par3;
/* 26:41 */     this.cameraX = par4;
/* 27:42 */     this.cameraY = par6;
/* 28:43 */     this.cameraZ = par8;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean rendersChunk(int par1, int par2, int par3)
/* 32:   */   {
/* 33:48 */     return this.valid;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void addGLRenderList(int par1)
/* 37:   */   {
/* 38:53 */     this.glLists.put(par1);
/* 39:55 */     if (this.glLists.remaining() == 0) {
/* 40:57 */       callLists();
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void callLists()
/* 45:   */   {
/* 46:63 */     if (this.valid)
/* 47:   */     {
/* 48:65 */       if (!this.bufferFlipped)
/* 49:   */       {
/* 50:67 */         this.glLists.flip();
/* 51:68 */         this.bufferFlipped = true;
/* 52:   */       }
/* 53:71 */       if (this.glLists.remaining() > 0)
/* 54:   */       {
/* 55:73 */         GL11.glPushMatrix();
/* 56:74 */         GL11.glTranslatef((float)(this.renderChunkX - this.cameraX), (float)(this.renderChunkY - this.cameraY), (float)(this.renderChunkZ - this.cameraZ));
/* 57:75 */         GL11.glCallLists(this.glLists);
/* 58:76 */         GL11.glPopMatrix();
/* 59:   */       }
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void resetList()
/* 64:   */   {
/* 65:86 */     this.valid = false;
/* 66:87 */     this.bufferFlipped = false;
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.RenderList
 * JD-Core Version:    0.7.0.1
 */