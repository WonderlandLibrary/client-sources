/*   1:    */ package org.newdawn.slick.opengl.pbuffer;
/*   2:    */ 
/*   3:    */ import org.lwjgl.LWJGLException;
/*   4:    */ import org.lwjgl.opengl.Display;
/*   5:    */ import org.lwjgl.opengl.GL11;
/*   6:    */ import org.lwjgl.opengl.Pbuffer;
/*   7:    */ import org.lwjgl.opengl.PixelFormat;
/*   8:    */ import org.lwjgl.opengl.RenderTexture;
/*   9:    */ import org.newdawn.slick.Graphics;
/*  10:    */ import org.newdawn.slick.Image;
/*  11:    */ import org.newdawn.slick.SlickException;
/*  12:    */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*  13:    */ import org.newdawn.slick.opengl.SlickCallable;
/*  14:    */ import org.newdawn.slick.opengl.Texture;
/*  15:    */ import org.newdawn.slick.opengl.TextureImpl;
/*  16:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  17:    */ import org.newdawn.slick.util.Log;
/*  18:    */ 
/*  19:    */ public class PBufferGraphics
/*  20:    */   extends Graphics
/*  21:    */ {
/*  22:    */   private Pbuffer pbuffer;
/*  23:    */   private Image image;
/*  24:    */   
/*  25:    */   public PBufferGraphics(Image image)
/*  26:    */     throws SlickException
/*  27:    */   {
/*  28: 37 */     super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
/*  29: 38 */     this.image = image;
/*  30:    */     
/*  31: 40 */     Log.debug("Creating pbuffer(rtt) " + image.getWidth() + "x" + image.getHeight());
/*  32: 41 */     if ((Pbuffer.getCapabilities() & 0x1) == 0) {
/*  33: 42 */       throw new SlickException("Your OpenGL card does not support PBuffers and hence can't handle the dynamic images required for this application.");
/*  34:    */     }
/*  35: 44 */     if ((Pbuffer.getCapabilities() & 0x2) == 0) {
/*  36: 45 */       throw new SlickException("Your OpenGL card does not support Render-To-Texture and hence can't handle the dynamic images required for this application.");
/*  37:    */     }
/*  38: 48 */     init();
/*  39:    */   }
/*  40:    */   
/*  41:    */   private void init()
/*  42:    */     throws SlickException
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46: 58 */       Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
/*  47:    */       
/*  48: 60 */       RenderTexture rt = new RenderTexture(false, true, false, false, 8314, 0);
/*  49: 61 */       this.pbuffer = new Pbuffer(this.screenWidth, this.screenHeight, new PixelFormat(8, 0, 0), rt, null);
/*  50:    */       
/*  51:    */ 
/*  52: 64 */       this.pbuffer.makeCurrent();
/*  53:    */       
/*  54: 66 */       initGL();
/*  55: 67 */       GL.glBindTexture(3553, tex.getTextureID());
/*  56: 68 */       this.pbuffer.releaseTexImage(8323);
/*  57: 69 */       this.image.draw(0.0F, 0.0F);
/*  58: 70 */       this.image.setTexture(tex);
/*  59:    */       
/*  60: 72 */       Display.makeCurrent();
/*  61:    */     }
/*  62:    */     catch (Exception e)
/*  63:    */     {
/*  64: 74 */       Log.error(e);
/*  65: 75 */       throw new SlickException("Failed to create PBuffer for dynamic image. OpenGL driver failure?");
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void disable()
/*  70:    */   {
/*  71: 83 */     GL.flush();
/*  72:    */     
/*  73:    */ 
/*  74: 86 */     GL.glBindTexture(3553, this.image.getTexture().getTextureID());
/*  75: 87 */     this.pbuffer.bindTexImage(8323);
/*  76:    */     try
/*  77:    */     {
/*  78: 90 */       Display.makeCurrent();
/*  79:    */     }
/*  80:    */     catch (LWJGLException e)
/*  81:    */     {
/*  82: 92 */       Log.error(e);
/*  83:    */     }
/*  84: 95 */     SlickCallable.leaveSafeBlock();
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void enable()
/*  88:    */   {
/*  89:    */     
/*  90:    */     try
/*  91:    */     {
/*  92:105 */       if (this.pbuffer.isBufferLost())
/*  93:    */       {
/*  94:106 */         this.pbuffer.destroy();
/*  95:107 */         init();
/*  96:    */       }
/*  97:110 */       this.pbuffer.makeCurrent();
/*  98:    */     }
/*  99:    */     catch (Exception e)
/* 100:    */     {
/* 101:112 */       Log.error("Failed to recreate the PBuffer");
/* 102:113 */       throw new RuntimeException(e);
/* 103:    */     }
/* 104:117 */     GL.glBindTexture(3553, this.image.getTexture().getTextureID());
/* 105:118 */     this.pbuffer.releaseTexImage(8323);
/* 106:119 */     TextureImpl.unbind();
/* 107:120 */     initGL();
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void initGL()
/* 111:    */   {
/* 112:127 */     GL11.glEnable(3553);
/* 113:128 */     GL11.glShadeModel(7425);
/* 114:129 */     GL11.glDisable(2929);
/* 115:130 */     GL11.glDisable(2896);
/* 116:    */     
/* 117:132 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 118:133 */     GL11.glClearDepth(1.0D);
/* 119:    */     
/* 120:135 */     GL11.glEnable(3042);
/* 121:136 */     GL11.glBlendFunc(770, 771);
/* 122:    */     
/* 123:138 */     GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
/* 124:139 */     GL11.glMatrixMode(5888);
/* 125:140 */     GL11.glLoadIdentity();
/* 126:    */     
/* 127:142 */     enterOrtho();
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected void enterOrtho()
/* 131:    */   {
/* 132:149 */     GL11.glMatrixMode(5889);
/* 133:150 */     GL11.glLoadIdentity();
/* 134:151 */     GL11.glOrtho(0.0D, this.screenWidth, 0.0D, this.screenHeight, 1.0D, -1.0D);
/* 135:152 */     GL11.glMatrixMode(5888);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void destroy()
/* 139:    */   {
/* 140:159 */     super.destroy();
/* 141:    */     
/* 142:161 */     this.pbuffer.destroy();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void flush()
/* 146:    */   {
/* 147:168 */     super.flush();
/* 148:    */     
/* 149:170 */     this.image.flushPixelData();
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.pbuffer.PBufferGraphics
 * JD-Core Version:    0.7.0.1
 */