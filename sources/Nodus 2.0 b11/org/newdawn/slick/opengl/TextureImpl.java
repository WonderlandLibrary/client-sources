/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import java.nio.IntBuffer;
/*   6:    */ import org.lwjgl.BufferUtils;
/*   7:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*   8:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*   9:    */ import org.newdawn.slick.util.Log;
/*  10:    */ 
/*  11:    */ public class TextureImpl
/*  12:    */   implements Texture
/*  13:    */ {
/*  14: 27 */   protected static SGL GL = ;
/*  15:    */   static Texture lastBind;
/*  16:    */   private int target;
/*  17:    */   private int textureID;
/*  18:    */   private int height;
/*  19:    */   private int width;
/*  20:    */   private int texWidth;
/*  21:    */   private int texHeight;
/*  22:    */   private float widthRatio;
/*  23:    */   private float heightRatio;
/*  24:    */   private boolean alpha;
/*  25:    */   private String ref;
/*  26:    */   private String cacheName;
/*  27:    */   private ReloadData reloadData;
/*  28:    */   
/*  29:    */   public static Texture getLastBind()
/*  30:    */   {
/*  31: 38 */     return lastBind;
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected TextureImpl() {}
/*  35:    */   
/*  36:    */   public TextureImpl(String ref, int target, int textureID)
/*  37:    */   {
/*  38: 81 */     this.target = target;
/*  39: 82 */     this.ref = ref;
/*  40: 83 */     this.textureID = textureID;
/*  41: 84 */     lastBind = this;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setCacheName(String cacheName)
/*  45:    */   {
/*  46: 93 */     this.cacheName = cacheName;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hasAlpha()
/*  50:    */   {
/*  51:100 */     return this.alpha;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getTextureRef()
/*  55:    */   {
/*  56:107 */     return this.ref;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setAlpha(boolean alpha)
/*  60:    */   {
/*  61:116 */     this.alpha = alpha;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static void bindNone()
/*  65:    */   {
/*  66:123 */     lastBind = null;
/*  67:124 */     GL.glDisable(3553);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static void unbind()
/*  71:    */   {
/*  72:133 */     lastBind = null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void bind()
/*  76:    */   {
/*  77:140 */     if (lastBind != this)
/*  78:    */     {
/*  79:141 */       lastBind = this;
/*  80:142 */       GL.glEnable(3553);
/*  81:143 */       GL.glBindTexture(this.target, this.textureID);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setHeight(int height)
/*  86:    */   {
/*  87:153 */     this.height = height;
/*  88:154 */     setHeight();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setWidth(int width)
/*  92:    */   {
/*  93:163 */     this.width = width;
/*  94:164 */     setWidth();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getImageHeight()
/*  98:    */   {
/*  99:171 */     return this.height;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getImageWidth()
/* 103:    */   {
/* 104:178 */     return this.width;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public float getHeight()
/* 108:    */   {
/* 109:185 */     return this.heightRatio;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public float getWidth()
/* 113:    */   {
/* 114:192 */     return this.widthRatio;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getTextureHeight()
/* 118:    */   {
/* 119:199 */     return this.texHeight;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getTextureWidth()
/* 123:    */   {
/* 124:206 */     return this.texWidth;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setTextureHeight(int texHeight)
/* 128:    */   {
/* 129:215 */     this.texHeight = texHeight;
/* 130:216 */     setHeight();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setTextureWidth(int texWidth)
/* 134:    */   {
/* 135:225 */     this.texWidth = texWidth;
/* 136:226 */     setWidth();
/* 137:    */   }
/* 138:    */   
/* 139:    */   private void setHeight()
/* 140:    */   {
/* 141:234 */     if (this.texHeight != 0) {
/* 142:235 */       this.heightRatio = (this.height / this.texHeight);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void setWidth()
/* 147:    */   {
/* 148:244 */     if (this.texWidth != 0) {
/* 149:245 */       this.widthRatio = (this.width / this.texWidth);
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void release()
/* 154:    */   {
/* 155:253 */     IntBuffer texBuf = createIntBuffer(1);
/* 156:254 */     texBuf.put(this.textureID);
/* 157:255 */     texBuf.flip();
/* 158:    */     
/* 159:257 */     GL.glDeleteTextures(texBuf);
/* 160:259 */     if (lastBind == this) {
/* 161:260 */       bindNone();
/* 162:    */     }
/* 163:263 */     if (this.cacheName != null) {
/* 164:264 */       InternalTextureLoader.get().clear(this.cacheName);
/* 165:    */     } else {
/* 166:266 */       InternalTextureLoader.get().clear(this.ref);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int getTextureID()
/* 171:    */   {
/* 172:274 */     return this.textureID;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setTextureID(int textureID)
/* 176:    */   {
/* 177:283 */     this.textureID = textureID;
/* 178:    */   }
/* 179:    */   
/* 180:    */   protected IntBuffer createIntBuffer(int size)
/* 181:    */   {
/* 182:294 */     ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
/* 183:295 */     temp.order(ByteOrder.nativeOrder());
/* 184:    */     
/* 185:297 */     return temp.asIntBuffer();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public byte[] getTextureData()
/* 189:    */   {
/* 190:304 */     ByteBuffer buffer = BufferUtils.createByteBuffer((hasAlpha() ? 4 : 3) * this.texWidth * this.texHeight);
/* 191:305 */     bind();
/* 192:306 */     GL.glGetTexImage(3553, 0, hasAlpha() ? 6408 : 6407, 5121, 
/* 193:307 */       buffer);
/* 194:308 */     byte[] data = new byte[buffer.limit()];
/* 195:309 */     buffer.get(data);
/* 196:310 */     buffer.clear();
/* 197:    */     
/* 198:312 */     return data;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setTextureFilter(int textureFilter)
/* 202:    */   {
/* 203:319 */     bind();
/* 204:320 */     GL.glTexParameteri(this.target, 10241, textureFilter);
/* 205:321 */     GL.glTexParameteri(this.target, 10240, textureFilter);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setTextureData(int srcPixelFormat, int componentCount, int minFilter, int magFilter, ByteBuffer textureBuffer)
/* 209:    */   {
/* 210:335 */     this.reloadData = new ReloadData(null);
/* 211:336 */     this.reloadData.srcPixelFormat = srcPixelFormat;
/* 212:337 */     this.reloadData.componentCount = componentCount;
/* 213:338 */     this.reloadData.minFilter = minFilter;
/* 214:339 */     this.reloadData.magFilter = magFilter;
/* 215:340 */     this.reloadData.textureBuffer = textureBuffer;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void reload()
/* 219:    */   {
/* 220:347 */     if (this.reloadData != null) {
/* 221:348 */       this.textureID = this.reloadData.reload();
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   private class ReloadData
/* 226:    */   {
/* 227:    */     private int srcPixelFormat;
/* 228:    */     private int componentCount;
/* 229:    */     private int minFilter;
/* 230:    */     private int magFilter;
/* 231:    */     private ByteBuffer textureBuffer;
/* 232:    */     
/* 233:    */     private ReloadData() {}
/* 234:    */     
/* 235:    */     public int reload()
/* 236:    */     {
/* 237:373 */       Log.error("Reloading texture: " + TextureImpl.this.ref);
/* 238:374 */       return InternalTextureLoader.get().reload(TextureImpl.this, this.srcPixelFormat, this.componentCount, this.minFilter, this.magFilter, this.textureBuffer);
/* 239:    */     }
/* 240:    */   }
/* 241:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.TextureImpl
 * JD-Core Version:    0.7.0.1
 */