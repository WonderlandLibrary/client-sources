/*   1:    */ package org.newdawn.slick.opengl.pbuffer;
/*   2:    */ 
/*   3:    */ import org.lwjgl.LWJGLException;
/*   4:    */ import org.lwjgl.opengl.Display;
/*   5:    */ import org.lwjgl.opengl.GL11;
/*   6:    */ import org.lwjgl.opengl.Pbuffer;
/*   7:    */ import org.lwjgl.opengl.PixelFormat;
/*   8:    */ import org.newdawn.slick.Graphics;
/*   9:    */ import org.newdawn.slick.Image;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*  12:    */ import org.newdawn.slick.opengl.SlickCallable;
/*  13:    */ import org.newdawn.slick.opengl.Texture;
/*  14:    */ import org.newdawn.slick.opengl.TextureImpl;
/*  15:    */ import org.newdawn.slick.util.Log;
/*  16:    */ 
/*  17:    */ public class PBufferUniqueGraphics
/*  18:    */   extends Graphics
/*  19:    */ {
/*  20:    */   private Pbuffer pbuffer;
/*  21:    */   private Image image;
/*  22:    */   
/*  23:    */   public PBufferUniqueGraphics(Image image)
/*  24:    */     throws SlickException
/*  25:    */   {
/*  26: 36 */     super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
/*  27: 37 */     this.image = image;
/*  28:    */     
/*  29: 39 */     Log.debug("Creating pbuffer(unique) " + image.getWidth() + "x" + image.getHeight());
/*  30: 40 */     if ((Pbuffer.getCapabilities() & 0x1) == 0) {
/*  31: 41 */       throw new SlickException("Your OpenGL card does not support PBuffers and hence can't handle the dynamic images required for this application.");
/*  32:    */     }
/*  33: 44 */     init();
/*  34:    */   }
/*  35:    */   
/*  36:    */   private void init()
/*  37:    */     throws SlickException
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 54 */       Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
/*  42:    */       
/*  43: 56 */       this.pbuffer = new Pbuffer(this.screenWidth, this.screenHeight, new PixelFormat(8, 0, 0), null, null);
/*  44:    */       
/*  45: 58 */       this.pbuffer.makeCurrent();
/*  46:    */       
/*  47: 60 */       initGL();
/*  48: 61 */       this.image.draw(0.0F, 0.0F);
/*  49: 62 */       GL11.glBindTexture(3553, tex.getTextureID());
/*  50: 63 */       GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, 
/*  51: 64 */         tex.getTextureWidth(), 
/*  52: 65 */         tex.getTextureHeight(), 0);
/*  53: 66 */       this.image.setTexture(tex);
/*  54:    */       
/*  55: 68 */       Display.makeCurrent();
/*  56:    */     }
/*  57:    */     catch (Exception e)
/*  58:    */     {
/*  59: 70 */       Log.error(e);
/*  60: 71 */       throw new SlickException("Failed to create PBuffer for dynamic image. OpenGL driver failure?");
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void disable()
/*  65:    */   {
/*  66: 80 */     GL11.glBindTexture(3553, this.image.getTexture().getTextureID());
/*  67: 81 */     GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, 
/*  68: 82 */       this.image.getTexture().getTextureWidth(), 
/*  69: 83 */       this.image.getTexture().getTextureHeight(), 0);
/*  70:    */     try
/*  71:    */     {
/*  72: 86 */       Display.makeCurrent();
/*  73:    */     }
/*  74:    */     catch (LWJGLException e)
/*  75:    */     {
/*  76: 88 */       Log.error(e);
/*  77:    */     }
/*  78: 91 */     SlickCallable.leaveSafeBlock();
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected void enable()
/*  82:    */   {
/*  83:    */     
/*  84:    */     try
/*  85:    */     {
/*  86:101 */       if (this.pbuffer.isBufferLost())
/*  87:    */       {
/*  88:102 */         this.pbuffer.destroy();
/*  89:103 */         init();
/*  90:    */       }
/*  91:106 */       this.pbuffer.makeCurrent();
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:108 */       Log.error("Failed to recreate the PBuffer");
/*  96:109 */       Log.error(e);
/*  97:110 */       throw new RuntimeException(e);
/*  98:    */     }
/*  99:114 */     TextureImpl.unbind();
/* 100:115 */     initGL();
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected void initGL()
/* 104:    */   {
/* 105:122 */     GL11.glEnable(3553);
/* 106:123 */     GL11.glShadeModel(7425);
/* 107:124 */     GL11.glDisable(2929);
/* 108:125 */     GL11.glDisable(2896);
/* 109:    */     
/* 110:127 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 111:128 */     GL11.glClearDepth(1.0D);
/* 112:    */     
/* 113:130 */     GL11.glEnable(3042);
/* 114:131 */     GL11.glBlendFunc(770, 771);
/* 115:    */     
/* 116:133 */     GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
/* 117:134 */     GL11.glMatrixMode(5888);
/* 118:135 */     GL11.glLoadIdentity();
/* 119:    */     
/* 120:137 */     enterOrtho();
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected void enterOrtho()
/* 124:    */   {
/* 125:144 */     GL11.glMatrixMode(5889);
/* 126:145 */     GL11.glLoadIdentity();
/* 127:146 */     GL11.glOrtho(0.0D, this.screenWidth, 0.0D, this.screenHeight, 1.0D, -1.0D);
/* 128:147 */     GL11.glMatrixMode(5888);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void destroy()
/* 132:    */   {
/* 133:154 */     super.destroy();
/* 134:    */     
/* 135:156 */     this.pbuffer.destroy();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void flush()
/* 139:    */   {
/* 140:163 */     super.flush();
/* 141:    */     
/* 142:165 */     this.image.flushPixelData();
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.pbuffer.PBufferUniqueGraphics
 * JD-Core Version:    0.7.0.1
 */