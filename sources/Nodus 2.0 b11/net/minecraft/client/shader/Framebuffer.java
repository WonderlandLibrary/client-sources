/*   1:    */ package net.minecraft.client.shader;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*   6:    */ import org.lwjgl.opengl.EXTFramebufferObject;
/*   7:    */ import org.lwjgl.opengl.GL11;
/*   8:    */ 
/*   9:    */ public class Framebuffer
/*  10:    */ {
/*  11:    */   public int framebufferTextureWidth;
/*  12:    */   public int framebufferTextureHeight;
/*  13:    */   public int framebufferWidth;
/*  14:    */   public int framebufferHeight;
/*  15:    */   public boolean useDepth;
/*  16:    */   public int framebufferObject;
/*  17:    */   public int framebufferTexture;
/*  18:    */   public int depthBuffer;
/*  19:    */   public float[] framebufferColor;
/*  20:    */   public int framebufferFilter;
/*  21:    */   private static final String __OBFID = "CL_00000959";
/*  22:    */   
/*  23:    */   public Framebuffer(int p_i45078_1_, int p_i45078_2_, boolean p_i45078_3_)
/*  24:    */   {
/*  25: 26 */     this.useDepth = p_i45078_3_;
/*  26: 27 */     this.framebufferObject = -1;
/*  27: 28 */     this.framebufferTexture = -1;
/*  28: 29 */     this.depthBuffer = -1;
/*  29: 30 */     this.framebufferColor = new float[4];
/*  30: 31 */     this.framebufferColor[0] = 1.0F;
/*  31: 32 */     this.framebufferColor[1] = 1.0F;
/*  32: 33 */     this.framebufferColor[2] = 1.0F;
/*  33: 34 */     this.framebufferColor[3] = 0.0F;
/*  34: 35 */     createBindFramebuffer(p_i45078_1_, p_i45078_2_);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void createBindFramebuffer(int p_147613_1_, int p_147613_2_)
/*  38:    */   {
/*  39: 40 */     if (!OpenGlHelper.isFramebufferEnabled())
/*  40:    */     {
/*  41: 42 */       this.framebufferWidth = p_147613_1_;
/*  42: 43 */       this.framebufferHeight = p_147613_2_;
/*  43:    */     }
/*  44:    */     else
/*  45:    */     {
/*  46: 47 */       GL11.glEnable(2929);
/*  47: 49 */       if (this.framebufferObject >= 0) {
/*  48: 51 */         deleteFramebuffer();
/*  49:    */       }
/*  50: 54 */       createFramebuffer(p_147613_1_, p_147613_2_);
/*  51: 55 */       checkFramebufferComplete();
/*  52: 56 */       EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void deleteFramebuffer()
/*  57:    */   {
/*  58: 62 */     if (OpenGlHelper.isFramebufferEnabled())
/*  59:    */     {
/*  60: 64 */       unbindFramebufferTexture();
/*  61: 65 */       unbindFramebuffer();
/*  62: 67 */       if (this.depthBuffer > -1)
/*  63:    */       {
/*  64: 69 */         EXTFramebufferObject.glDeleteRenderbuffersEXT(this.depthBuffer);
/*  65: 70 */         this.depthBuffer = -1;
/*  66:    */       }
/*  67: 73 */       if (this.framebufferTexture > -1)
/*  68:    */       {
/*  69: 75 */         TextureUtil.deleteTexture(this.framebufferTexture);
/*  70: 76 */         this.framebufferTexture = -1;
/*  71:    */       }
/*  72: 79 */       if (this.framebufferObject > -1)
/*  73:    */       {
/*  74: 81 */         EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
/*  75: 82 */         EXTFramebufferObject.glDeleteFramebuffersEXT(this.framebufferObject);
/*  76: 83 */         this.framebufferObject = -1;
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void createFramebuffer(int p_147605_1_, int p_147605_2_)
/*  82:    */   {
/*  83: 90 */     this.framebufferWidth = p_147605_1_;
/*  84: 91 */     this.framebufferHeight = p_147605_2_;
/*  85: 92 */     this.framebufferTextureWidth = p_147605_1_;
/*  86: 93 */     this.framebufferTextureHeight = p_147605_2_;
/*  87: 95 */     if (!OpenGlHelper.isFramebufferEnabled())
/*  88:    */     {
/*  89: 97 */       framebufferClear();
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:101 */       this.framebufferObject = EXTFramebufferObject.glGenFramebuffersEXT();
/*  94:102 */       this.framebufferTexture = TextureUtil.glGenTextures();
/*  95:104 */       if (this.useDepth) {
/*  96:106 */         this.depthBuffer = EXTFramebufferObject.glGenRenderbuffersEXT();
/*  97:    */       }
/*  98:109 */       setFramebufferFilter(9729);
/*  99:110 */       GL11.glBindTexture(3553, this.framebufferTexture);
/* 100:111 */       GL11.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, null);
/* 101:112 */       EXTFramebufferObject.glBindFramebufferEXT(36160, this.framebufferObject);
/* 102:113 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, this.framebufferTexture, 0);
/* 103:115 */       if (this.useDepth)
/* 104:    */       {
/* 105:117 */         EXTFramebufferObject.glBindRenderbufferEXT(36161, this.depthBuffer);
/* 106:118 */         EXTFramebufferObject.glRenderbufferStorageEXT(36161, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
/* 107:119 */         EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, this.depthBuffer);
/* 108:    */       }
/* 109:122 */       framebufferClear();
/* 110:123 */       unbindFramebufferTexture();
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setFramebufferFilter(int p_147607_1_)
/* 115:    */   {
/* 116:129 */     if (OpenGlHelper.isFramebufferEnabled())
/* 117:    */     {
/* 118:131 */       this.framebufferFilter = p_147607_1_;
/* 119:132 */       GL11.glBindTexture(3553, this.framebufferTexture);
/* 120:133 */       GL11.glTexParameterf(3553, 10241, p_147607_1_);
/* 121:134 */       GL11.glTexParameterf(3553, 10240, p_147607_1_);
/* 122:135 */       GL11.glTexParameterf(3553, 10242, 10496.0F);
/* 123:136 */       GL11.glTexParameterf(3553, 10243, 10496.0F);
/* 124:137 */       GL11.glBindTexture(3553, 0);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void checkFramebufferComplete()
/* 129:    */   {
/* 130:143 */     int var1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/* 131:145 */     switch (var1)
/* 132:    */     {
/* 133:    */     case 36053: 
/* 134:148 */       return;
/* 135:    */     case 36054: 
/* 136:151 */       throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT");
/* 137:    */     case 36055: 
/* 138:154 */       throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT");
/* 139:    */     case 36056: 
/* 140:    */     default: 
/* 141:158 */       throw new RuntimeException("glCheckFramebufferStatusEXT returned unknown status:" + var1);
/* 142:    */     case 36057: 
/* 143:161 */       throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT");
/* 144:    */     case 36058: 
/* 145:164 */       throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT");
/* 146:    */     case 36059: 
/* 147:167 */       throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT");
/* 148:    */     }
/* 149:170 */     throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT");
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void bindFramebufferTexture()
/* 153:    */   {
/* 154:176 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 155:178 */       GL11.glBindTexture(3553, this.framebufferTexture);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void unbindFramebufferTexture()
/* 160:    */   {
/* 161:184 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 162:186 */       GL11.glBindTexture(3553, 0);
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void bindFramebuffer(boolean p_147610_1_)
/* 167:    */   {
/* 168:192 */     if (OpenGlHelper.isFramebufferEnabled())
/* 169:    */     {
/* 170:194 */       EXTFramebufferObject.glBindFramebufferEXT(36160, this.framebufferObject);
/* 171:196 */       if (p_147610_1_) {
/* 172:198 */         GL11.glViewport(0, 0, this.framebufferWidth, this.framebufferHeight);
/* 173:    */       }
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void unbindFramebuffer()
/* 178:    */   {
/* 179:205 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 180:207 */       EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setFramebufferColor(float p_147604_1_, float p_147604_2_, float p_147604_3_, float p_147604_4_)
/* 185:    */   {
/* 186:213 */     this.framebufferColor[0] = p_147604_1_;
/* 187:214 */     this.framebufferColor[1] = p_147604_2_;
/* 188:215 */     this.framebufferColor[2] = p_147604_3_;
/* 189:216 */     this.framebufferColor[3] = p_147604_4_;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void framebufferRender(int p_147615_1_, int p_147615_2_)
/* 193:    */   {
/* 194:221 */     if (OpenGlHelper.isFramebufferEnabled())
/* 195:    */     {
/* 196:223 */       GL11.glColorMask(true, true, true, false);
/* 197:224 */       GL11.glDisable(2929);
/* 198:225 */       GL11.glDepthMask(false);
/* 199:226 */       GL11.glMatrixMode(5889);
/* 200:227 */       GL11.glLoadIdentity();
/* 201:228 */       GL11.glOrtho(0.0D, p_147615_1_, p_147615_2_, 0.0D, 1000.0D, 3000.0D);
/* 202:229 */       GL11.glMatrixMode(5888);
/* 203:230 */       GL11.glLoadIdentity();
/* 204:231 */       GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/* 205:232 */       GL11.glViewport(0, 0, p_147615_1_, p_147615_2_);
/* 206:233 */       GL11.glEnable(3553);
/* 207:234 */       GL11.glDisable(2896);
/* 208:235 */       GL11.glDisable(3008);
/* 209:236 */       GL11.glDisable(3042);
/* 210:237 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 211:238 */       GL11.glEnable(2903);
/* 212:239 */       bindFramebufferTexture();
/* 213:240 */       float var3 = p_147615_1_;
/* 214:241 */       float var4 = p_147615_2_;
/* 215:242 */       float var5 = this.framebufferWidth / this.framebufferTextureWidth;
/* 216:243 */       float var6 = this.framebufferHeight / this.framebufferTextureHeight;
/* 217:244 */       Tessellator var7 = Tessellator.instance;
/* 218:245 */       var7.startDrawingQuads();
/* 219:246 */       var7.setColorOpaque_I(-1);
/* 220:247 */       var7.addVertexWithUV(0.0D, var4, 0.0D, 0.0D, 0.0D);
/* 221:248 */       var7.addVertexWithUV(var3, var4, 0.0D, var5, 0.0D);
/* 222:249 */       var7.addVertexWithUV(var3, 0.0D, 0.0D, var5, var6);
/* 223:250 */       var7.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, var6);
/* 224:251 */       var7.draw();
/* 225:252 */       unbindFramebufferTexture();
/* 226:253 */       GL11.glDepthMask(true);
/* 227:254 */       GL11.glColorMask(true, true, true, true);
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void framebufferClear()
/* 232:    */   {
/* 233:260 */     bindFramebuffer(true);
/* 234:261 */     GL11.glClearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
/* 235:262 */     int var1 = 16384;
/* 236:264 */     if (this.useDepth)
/* 237:    */     {
/* 238:266 */       GL11.glClearDepth(1.0D);
/* 239:267 */       var1 |= 0x100;
/* 240:    */     }
/* 241:270 */     GL11.glClear(var1);
/* 242:271 */     unbindFramebuffer();
/* 243:    */   }
/* 244:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.shader.Framebuffer
 * JD-Core Version:    0.7.0.1
 */