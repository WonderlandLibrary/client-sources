/*   1:    */ package org.newdawn.slick.opengl.renderer;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.DoubleBuffer;
/*   5:    */ import java.nio.FloatBuffer;
/*   6:    */ import java.nio.IntBuffer;
/*   7:    */ import org.lwjgl.opengl.ContextCapabilities;
/*   8:    */ import org.lwjgl.opengl.EXTSecondaryColor;
/*   9:    */ import org.lwjgl.opengl.GL11;
/*  10:    */ import org.lwjgl.opengl.GLContext;
/*  11:    */ 
/*  12:    */ public class ImmediateModeOGLRenderer
/*  13:    */   implements SGL
/*  14:    */ {
/*  15:    */   private int width;
/*  16:    */   private int height;
/*  17: 23 */   private float[] current = { 1.0F, 1.0F, 1.0F, 1.0F };
/*  18: 25 */   protected float alphaScale = 1.0F;
/*  19:    */   
/*  20:    */   public void initDisplay(int width, int height)
/*  21:    */   {
/*  22: 31 */     this.width = width;
/*  23: 32 */     this.height = height;
/*  24:    */     
/*  25: 34 */     String extensions = GL11.glGetString(7939);
/*  26:    */     
/*  27: 36 */     GL11.glEnable(3553);
/*  28: 37 */     GL11.glShadeModel(7425);
/*  29: 38 */     GL11.glDisable(2929);
/*  30: 39 */     GL11.glDisable(2896);
/*  31:    */     
/*  32: 41 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  33: 42 */     GL11.glClearDepth(1.0D);
/*  34:    */     
/*  35: 44 */     GL11.glEnable(3042);
/*  36: 45 */     GL11.glBlendFunc(770, 771);
/*  37:    */     
/*  38: 47 */     GL11.glViewport(0, 0, width, height);
/*  39: 48 */     GL11.glMatrixMode(5888);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void enterOrtho(int xsize, int ysize)
/*  43:    */   {
/*  44: 55 */     GL11.glMatrixMode(5889);
/*  45: 56 */     GL11.glLoadIdentity();
/*  46: 57 */     GL11.glOrtho(0.0D, this.width, this.height, 0.0D, 1.0D, -1.0D);
/*  47: 58 */     GL11.glMatrixMode(5888);
/*  48:    */     
/*  49: 60 */     GL11.glTranslatef((this.width - xsize) / 2, 
/*  50: 61 */       (this.height - ysize) / 2, 0.0F);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void glBegin(int geomType)
/*  54:    */   {
/*  55: 68 */     GL11.glBegin(geomType);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void glBindTexture(int target, int id)
/*  59:    */   {
/*  60: 75 */     GL11.glBindTexture(target, id);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void glBlendFunc(int src, int dest)
/*  64:    */   {
/*  65: 82 */     GL11.glBlendFunc(src, dest);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void glCallList(int id)
/*  69:    */   {
/*  70: 89 */     GL11.glCallList(id);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void glClear(int value)
/*  74:    */   {
/*  75: 96 */     GL11.glClear(value);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void glClearColor(float red, float green, float blue, float alpha)
/*  79:    */   {
/*  80:103 */     GL11.glClearColor(red, green, blue, alpha);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void glClipPlane(int plane, DoubleBuffer buffer)
/*  84:    */   {
/*  85:110 */     GL11.glClipPlane(plane, buffer);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void glColor4f(float r, float g, float b, float a)
/*  89:    */   {
/*  90:117 */     a *= this.alphaScale;
/*  91:    */     
/*  92:119 */     this.current[0] = r;
/*  93:120 */     this.current[1] = g;
/*  94:121 */     this.current[2] = b;
/*  95:122 */     this.current[3] = a;
/*  96:    */     
/*  97:124 */     GL11.glColor4f(r, g, b, a);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha)
/* 101:    */   {
/* 102:131 */     GL11.glColorMask(red, green, blue, alpha);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border)
/* 106:    */   {
/* 107:138 */     GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void glDeleteTextures(IntBuffer buffer)
/* 111:    */   {
/* 112:145 */     GL11.glDeleteTextures(buffer);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void glDisable(int item)
/* 116:    */   {
/* 117:152 */     GL11.glDisable(item);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void glEnable(int item)
/* 121:    */   {
/* 122:159 */     GL11.glEnable(item);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void glEnd() {}
/* 126:    */   
/* 127:    */   public void glEndList() {}
/* 128:    */   
/* 129:    */   public int glGenLists(int count)
/* 130:    */   {
/* 131:180 */     return GL11.glGenLists(count);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void glGetFloat(int id, FloatBuffer ret)
/* 135:    */   {
/* 136:187 */     GL11.glGetFloat(id, ret);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void glGetInteger(int id, IntBuffer ret)
/* 140:    */   {
/* 141:194 */     GL11.glGetInteger(id, ret);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixels)
/* 145:    */   {
/* 146:201 */     GL11.glGetTexImage(target, level, format, type, pixels);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void glLineWidth(float width)
/* 150:    */   {
/* 151:208 */     GL11.glLineWidth(width);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void glLoadIdentity() {}
/* 155:    */   
/* 156:    */   public void glNewList(int id, int option)
/* 157:    */   {
/* 158:222 */     GL11.glNewList(id, option);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void glPointSize(float size)
/* 162:    */   {
/* 163:229 */     GL11.glPointSize(size);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void glPopMatrix() {}
/* 167:    */   
/* 168:    */   public void glPushMatrix() {}
/* 169:    */   
/* 170:    */   public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels)
/* 171:    */   {
/* 172:250 */     GL11.glReadPixels(x, y, width, height, format, type, pixels);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void glRotatef(float angle, float x, float y, float z)
/* 176:    */   {
/* 177:257 */     GL11.glRotatef(angle, x, y, z);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void glScalef(float x, float y, float z)
/* 181:    */   {
/* 182:264 */     GL11.glScalef(x, y, z);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void glScissor(int x, int y, int width, int height)
/* 186:    */   {
/* 187:271 */     GL11.glScissor(x, y, width, height);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void glTexCoord2f(float u, float v)
/* 191:    */   {
/* 192:278 */     GL11.glTexCoord2f(u, v);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void glTexEnvi(int target, int mode, int value)
/* 196:    */   {
/* 197:285 */     GL11.glTexEnvi(target, mode, value);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void glTranslatef(float x, float y, float z)
/* 201:    */   {
/* 202:292 */     GL11.glTranslatef(x, y, z);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void glVertex2f(float x, float y)
/* 206:    */   {
/* 207:299 */     GL11.glVertex2f(x, y);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void glVertex3f(float x, float y, float z)
/* 211:    */   {
/* 212:306 */     GL11.glVertex3f(x, y, z);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void flush() {}
/* 216:    */   
/* 217:    */   public void glTexParameteri(int target, int param, int value)
/* 218:    */   {
/* 219:319 */     GL11.glTexParameteri(target, param, value);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public float[] getCurrentColor()
/* 223:    */   {
/* 224:326 */     return this.current;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void glDeleteLists(int list, int count)
/* 228:    */   {
/* 229:333 */     GL11.glDeleteLists(list, count);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void glClearDepth(float value)
/* 233:    */   {
/* 234:340 */     GL11.glClearDepth(value);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void glDepthFunc(int func)
/* 238:    */   {
/* 239:347 */     GL11.glDepthFunc(func);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void glDepthMask(boolean mask)
/* 243:    */   {
/* 244:354 */     GL11.glDepthMask(mask);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setGlobalAlphaScale(float alphaScale)
/* 248:    */   {
/* 249:361 */     this.alphaScale = alphaScale;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void glLoadMatrix(FloatBuffer buffer)
/* 253:    */   {
/* 254:368 */     GL11.glLoadMatrix(buffer);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void glGenTextures(IntBuffer ids)
/* 258:    */   {
/* 259:376 */     GL11.glGenTextures(ids);
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void glGetError()
/* 263:    */   {
/* 264:384 */     GL11.glGetError();
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void glTexImage2D(int target, int i, int dstPixelFormat, int width, int height, int j, int srcPixelFormat, int glUnsignedByte, ByteBuffer textureBuffer)
/* 268:    */   {
/* 269:394 */     GL11.glTexImage2D(target, i, dstPixelFormat, width, height, j, srcPixelFormat, glUnsignedByte, textureBuffer);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void glTexSubImage2D(int glTexture2d, int i, int pageX, int pageY, int width, int height, int glBgra, int glUnsignedByte, ByteBuffer scratchByteBuffer)
/* 273:    */   {
/* 274:400 */     GL11.glTexSubImage2D(glTexture2d, i, pageX, pageY, width, height, glBgra, glUnsignedByte, scratchByteBuffer);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public boolean canTextureMirrorClamp()
/* 278:    */   {
/* 279:408 */     return GLContext.getCapabilities().GL_EXT_texture_mirror_clamp;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public boolean canSecondaryColor()
/* 283:    */   {
/* 284:416 */     return GLContext.getCapabilities().GL_EXT_secondary_color;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void glSecondaryColor3ubEXT(byte b, byte c, byte d)
/* 288:    */   {
/* 289:424 */     EXTSecondaryColor.glSecondaryColor3ubEXT(b, c, d);
/* 290:    */   }
/* 291:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.renderer.ImmediateModeOGLRenderer
 * JD-Core Version:    0.7.0.1
 */