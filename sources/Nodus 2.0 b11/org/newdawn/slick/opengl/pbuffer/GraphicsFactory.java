/*   1:    */ package org.newdawn.slick.opengl.pbuffer;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import org.lwjgl.opengl.ContextCapabilities;
/*   5:    */ import org.lwjgl.opengl.GLContext;
/*   6:    */ import org.lwjgl.opengl.Pbuffer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.util.Log;
/*  11:    */ 
/*  12:    */ public class GraphicsFactory
/*  13:    */ {
/*  14: 20 */   private static HashMap graphics = new HashMap();
/*  15: 22 */   private static boolean pbuffer = true;
/*  16: 24 */   private static boolean pbufferRT = true;
/*  17: 26 */   private static boolean fbo = true;
/*  18: 28 */   private static boolean init = false;
/*  19:    */   
/*  20:    */   private static void init()
/*  21:    */     throws SlickException
/*  22:    */   {
/*  23: 37 */     init = true;
/*  24: 39 */     if (fbo) {
/*  25: 40 */       fbo = GLContext.getCapabilities().GL_EXT_framebuffer_object;
/*  26:    */     }
/*  27: 42 */     pbuffer = (Pbuffer.getCapabilities() & 0x1) != 0;
/*  28: 43 */     pbufferRT = (Pbuffer.getCapabilities() & 0x2) != 0;
/*  29: 45 */     if ((!fbo) && (!pbuffer) && (!pbufferRT)) {
/*  30: 46 */       throw new SlickException("Your OpenGL card does not support offscreen buffers and hence can't handle the dynamic images required for this application.");
/*  31:    */     }
/*  32: 49 */     Log.info("Offscreen Buffers FBO=" + fbo + " PBUFFER=" + pbuffer + " PBUFFERRT=" + pbufferRT);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static void setUseFBO(boolean useFBO)
/*  36:    */   {
/*  37: 58 */     fbo = useFBO;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static boolean usingFBO()
/*  41:    */   {
/*  42: 67 */     return fbo;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static boolean usingPBuffer()
/*  46:    */   {
/*  47: 76 */     return (!fbo) && (pbuffer);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static Graphics getGraphicsForImage(Image image)
/*  51:    */     throws SlickException
/*  52:    */   {
/*  53: 88 */     Graphics g = (Graphics)graphics.get(image.getTexture());
/*  54: 90 */     if (g == null)
/*  55:    */     {
/*  56: 91 */       g = createGraphics(image);
/*  57: 92 */       graphics.put(image.getTexture(), g);
/*  58:    */     }
/*  59: 95 */     return g;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void releaseGraphicsForImage(Image image)
/*  63:    */     throws SlickException
/*  64:    */   {
/*  65:105 */     Graphics g = (Graphics)graphics.remove(image.getTexture());
/*  66:107 */     if (g != null) {
/*  67:108 */       g.destroy();
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   private static Graphics createGraphics(Image image)
/*  72:    */     throws SlickException
/*  73:    */   {
/*  74:    */     
/*  75:122 */     if (fbo) {
/*  76:    */       try
/*  77:    */       {
/*  78:124 */         return new FBOGraphics(image);
/*  79:    */       }
/*  80:    */       catch (Exception e)
/*  81:    */       {
/*  82:126 */         fbo = false;
/*  83:127 */         Log.warn("FBO failed in use, falling back to PBuffer");
/*  84:    */       }
/*  85:    */     }
/*  86:131 */     if (pbuffer)
/*  87:    */     {
/*  88:132 */       if (pbufferRT) {
/*  89:133 */         return new PBufferGraphics(image);
/*  90:    */       }
/*  91:135 */       return new PBufferUniqueGraphics(image);
/*  92:    */     }
/*  93:139 */     throw new SlickException("Failed to create offscreen buffer even though the card reports it's possible");
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.pbuffer.GraphicsFactory
 * JD-Core Version:    0.7.0.1
 */