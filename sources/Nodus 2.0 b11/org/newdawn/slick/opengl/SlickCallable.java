/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import org.lwjgl.opengl.GL11;
/*   4:    */ import org.newdawn.slick.SlickException;
/*   5:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*   6:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*   7:    */ 
/*   8:    */ public abstract class SlickCallable
/*   9:    */ {
/*  10:    */   private static Texture lastUsed;
/*  11: 47 */   private static boolean inSafe = false;
/*  12:    */   
/*  13:    */   public static void enterSafeBlock()
/*  14:    */   {
/*  15: 56 */     if (inSafe) {
/*  16: 57 */       return;
/*  17:    */     }
/*  18: 60 */     Renderer.get().flush();
/*  19: 61 */     lastUsed = TextureImpl.getLastBind();
/*  20: 62 */     TextureImpl.bindNone();
/*  21: 63 */     GL11.glPushAttrib(1048575);
/*  22: 64 */     GL11.glPushClientAttrib(-1);
/*  23: 65 */     GL11.glMatrixMode(5888);
/*  24: 66 */     GL11.glPushMatrix();
/*  25: 67 */     GL11.glMatrixMode(5889);
/*  26: 68 */     GL11.glPushMatrix();
/*  27: 69 */     GL11.glMatrixMode(5888);
/*  28:    */     
/*  29: 71 */     inSafe = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static void leaveSafeBlock()
/*  33:    */   {
/*  34: 80 */     if (!inSafe) {
/*  35: 81 */       return;
/*  36:    */     }
/*  37: 84 */     GL11.glMatrixMode(5889);
/*  38: 85 */     GL11.glPopMatrix();
/*  39: 86 */     GL11.glMatrixMode(5888);
/*  40: 87 */     GL11.glPopMatrix();
/*  41: 88 */     GL11.glPopClientAttrib();
/*  42: 89 */     GL11.glPopAttrib();
/*  43: 91 */     if (lastUsed != null) {
/*  44: 92 */       lastUsed.bind();
/*  45:    */     } else {
/*  46: 94 */       TextureImpl.bindNone();
/*  47:    */     }
/*  48: 97 */     inSafe = false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final void call()
/*  52:    */     throws SlickException
/*  53:    */   {
/*  54:108 */     enterSafeBlock();
/*  55:    */     
/*  56:110 */     performGLOperations();
/*  57:    */     
/*  58:112 */     leaveSafeBlock();
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected abstract void performGLOperations()
/*  62:    */     throws SlickException;
/*  63:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.SlickCallable
 * JD-Core Version:    0.7.0.1
 */