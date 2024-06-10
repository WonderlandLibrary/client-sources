/*   1:    */ package org.newdawn.slick.opengl.pbuffer;
/*   2:    */ 
/*   3:    */ import java.nio.IntBuffer;
/*   4:    */ import org.lwjgl.BufferUtils;
/*   5:    */ import org.lwjgl.opengl.ContextCapabilities;
/*   6:    */ import org.lwjgl.opengl.EXTFramebufferObject;
/*   7:    */ import org.lwjgl.opengl.GL11;
/*   8:    */ import org.lwjgl.opengl.GLContext;
/*   9:    */ import org.newdawn.slick.Graphics;
/*  10:    */ import org.newdawn.slick.Image;
/*  11:    */ import org.newdawn.slick.SlickException;
/*  12:    */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*  13:    */ import org.newdawn.slick.opengl.SlickCallable;
/*  14:    */ import org.newdawn.slick.opengl.Texture;
/*  15:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  16:    */ import org.newdawn.slick.util.Log;
/*  17:    */ 
/*  18:    */ public class FBOGraphics
/*  19:    */   extends Graphics
/*  20:    */ {
/*  21:    */   private Image image;
/*  22:    */   private int FBO;
/*  23: 28 */   private boolean valid = true;
/*  24:    */   
/*  25:    */   public FBOGraphics(Image image)
/*  26:    */     throws SlickException
/*  27:    */   {
/*  28: 37 */     super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
/*  29: 38 */     this.image = image;
/*  30:    */     
/*  31: 40 */     Log.debug("Creating FBO " + image.getWidth() + "x" + image.getHeight());
/*  32:    */     
/*  33: 42 */     boolean FBOEnabled = GLContext.getCapabilities().GL_EXT_framebuffer_object;
/*  34: 43 */     if (!FBOEnabled) {
/*  35: 44 */       throw new SlickException("Your OpenGL card does not support FBO and hence can't handle the dynamic images required for this application.");
/*  36:    */     }
/*  37: 47 */     init();
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void completeCheck()
/*  41:    */     throws SlickException
/*  42:    */   {
/*  43: 56 */     int framebuffer = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*  44: 57 */     switch (framebuffer)
/*  45:    */     {
/*  46:    */     case 36053: 
/*  47:    */       break;
/*  48:    */     case 36054: 
/*  49: 61 */       throw new SlickException("FrameBuffer: " + this.FBO + 
/*  50: 62 */         ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception");
/*  51:    */     case 36055: 
/*  52: 64 */       throw new SlickException("FrameBuffer: " + this.FBO + 
/*  53: 65 */         ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception");
/*  54:    */     case 36057: 
/*  55: 67 */       throw new SlickException("FrameBuffer: " + this.FBO + 
/*  56: 68 */         ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception");
/*  57:    */     case 36059: 
/*  58: 70 */       throw new SlickException("FrameBuffer: " + this.FBO + 
/*  59: 71 */         ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception");
/*  60:    */     case 36058: 
/*  61: 73 */       throw new SlickException("FrameBuffer: " + this.FBO + 
/*  62: 74 */         ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception");
/*  63:    */     case 36060: 
/*  64: 76 */       throw new SlickException("FrameBuffer: " + this.FBO + 
/*  65: 77 */         ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception");
/*  66:    */     case 36056: 
/*  67:    */     default: 
/*  68: 79 */       throw new SlickException("Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   private void init()
/*  73:    */     throws SlickException
/*  74:    */   {
/*  75: 89 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/*  76: 90 */     EXTFramebufferObject.glGenFramebuffersEXT(buffer);
/*  77: 91 */     this.FBO = buffer.get();
/*  78:    */     try
/*  79:    */     {
/*  80: 96 */       Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
/*  81:    */       
/*  82: 98 */       EXTFramebufferObject.glBindFramebufferEXT(36160, this.FBO);
/*  83: 99 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 
/*  84:100 */         36064, 
/*  85:101 */         3553, tex.getTextureID(), 0);
/*  86:    */       
/*  87:103 */       completeCheck();
/*  88:104 */       unbind();
/*  89:    */       
/*  90:    */ 
/*  91:107 */       clear();
/*  92:108 */       flush();
/*  93:    */       
/*  94:    */ 
/*  95:111 */       drawImage(this.image, 0.0F, 0.0F);
/*  96:112 */       this.image.setTexture(tex);
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:115 */       throw new SlickException("Failed to create new texture for FBO");
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void bind()
/* 105:    */   {
/* 106:123 */     EXTFramebufferObject.glBindFramebufferEXT(36160, this.FBO);
/* 107:124 */     GL11.glReadBuffer(36064);
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void unbind()
/* 111:    */   {
/* 112:131 */     EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
/* 113:132 */     GL11.glReadBuffer(1029);
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected void disable()
/* 117:    */   {
/* 118:139 */     GL.flush();
/* 119:    */     
/* 120:141 */     unbind();
/* 121:142 */     GL11.glPopClientAttrib();
/* 122:143 */     GL11.glPopAttrib();
/* 123:144 */     GL11.glMatrixMode(5888);
/* 124:145 */     GL11.glPopMatrix();
/* 125:146 */     GL11.glMatrixMode(5889);
/* 126:147 */     GL11.glPopMatrix();
/* 127:148 */     GL11.glMatrixMode(5888);
/* 128:    */     
/* 129:150 */     SlickCallable.leaveSafeBlock();
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected void enable()
/* 133:    */   {
/* 134:157 */     if (!this.valid) {
/* 135:158 */       throw new RuntimeException("Attempt to use a destroy()ed offscreen graphics context.");
/* 136:    */     }
/* 137:160 */     SlickCallable.enterSafeBlock();
/* 138:    */     
/* 139:162 */     GL11.glPushAttrib(1048575);
/* 140:163 */     GL11.glPushClientAttrib(-1);
/* 141:164 */     GL11.glMatrixMode(5889);
/* 142:165 */     GL11.glPushMatrix();
/* 143:166 */     GL11.glMatrixMode(5888);
/* 144:167 */     GL11.glPushMatrix();
/* 145:    */     
/* 146:169 */     bind();
/* 147:170 */     initGL();
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected void initGL()
/* 151:    */   {
/* 152:177 */     GL11.glEnable(3553);
/* 153:178 */     GL11.glShadeModel(7425);
/* 154:179 */     GL11.glDisable(2929);
/* 155:180 */     GL11.glDisable(2896);
/* 156:    */     
/* 157:182 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 158:183 */     GL11.glClearDepth(1.0D);
/* 159:    */     
/* 160:185 */     GL11.glEnable(3042);
/* 161:186 */     GL11.glBlendFunc(770, 771);
/* 162:    */     
/* 163:188 */     GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
/* 164:189 */     GL11.glMatrixMode(5888);
/* 165:190 */     GL11.glLoadIdentity();
/* 166:    */     
/* 167:192 */     enterOrtho();
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected void enterOrtho()
/* 171:    */   {
/* 172:199 */     GL11.glMatrixMode(5889);
/* 173:200 */     GL11.glLoadIdentity();
/* 174:201 */     GL11.glOrtho(0.0D, this.screenWidth, 0.0D, this.screenHeight, 1.0D, -1.0D);
/* 175:202 */     GL11.glMatrixMode(5888);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void destroy()
/* 179:    */   {
/* 180:209 */     super.destroy();
/* 181:    */     
/* 182:211 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/* 183:212 */     buffer.put(this.FBO);
/* 184:213 */     buffer.flip();
/* 185:    */     
/* 186:215 */     EXTFramebufferObject.glDeleteFramebuffersEXT(buffer);
/* 187:216 */     this.valid = false;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void flush()
/* 191:    */   {
/* 192:223 */     super.flush();
/* 193:    */     
/* 194:225 */     this.image.flushPixelData();
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.pbuffer.FBOGraphics
 * JD-Core Version:    0.7.0.1
 */