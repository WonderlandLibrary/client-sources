/*   1:    */ package net.minecraft.client.shader;
/*   2:    */ 
/*   3:    */ import java.nio.FloatBuffer;
/*   4:    */ import java.nio.IntBuffer;
/*   5:    */ import javax.vecmath.Matrix4f;
/*   6:    */ import org.apache.logging.log4j.LogManager;
/*   7:    */ import org.apache.logging.log4j.Logger;
/*   8:    */ import org.lwjgl.BufferUtils;
/*   9:    */ import org.lwjgl.opengl.GL20;
/*  10:    */ import org.lwjgl.opengl.GL21;
/*  11:    */ 
/*  12:    */ public class ShaderUniform
/*  13:    */ {
/*  14: 14 */   private static final Logger logger = ;
/*  15:    */   private int field_148102_b;
/*  16:    */   private final int field_148103_c;
/*  17:    */   private final int field_148100_d;
/*  18:    */   private final IntBuffer field_148101_e;
/*  19:    */   private final FloatBuffer field_148098_f;
/*  20:    */   private final String field_148099_g;
/*  21:    */   private boolean field_148105_h;
/*  22:    */   private final ShaderManager field_148106_i;
/*  23:    */   private static final String __OBFID = "CL_00001046";
/*  24:    */   
/*  25:    */   public ShaderUniform(String p_i45092_1_, int p_i45092_2_, int p_i45092_3_, ShaderManager p_i45092_4_)
/*  26:    */   {
/*  27: 27 */     this.field_148099_g = p_i45092_1_;
/*  28: 28 */     this.field_148103_c = p_i45092_3_;
/*  29: 29 */     this.field_148100_d = p_i45092_2_;
/*  30: 30 */     this.field_148106_i = p_i45092_4_;
/*  31: 32 */     if (p_i45092_2_ <= 3)
/*  32:    */     {
/*  33: 34 */       this.field_148101_e = BufferUtils.createIntBuffer(p_i45092_3_);
/*  34: 35 */       this.field_148098_f = null;
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38: 39 */       this.field_148101_e = null;
/*  39: 40 */       this.field_148098_f = BufferUtils.createFloatBuffer(p_i45092_3_);
/*  40:    */     }
/*  41: 43 */     this.field_148102_b = -1;
/*  42: 44 */     func_148096_h();
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void func_148096_h()
/*  46:    */   {
/*  47: 49 */     this.field_148105_h = true;
/*  48: 51 */     if (this.field_148106_i != null) {
/*  49: 53 */       this.field_148106_i.func_147985_d();
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static int func_148085_a(String p_148085_0_)
/*  54:    */   {
/*  55: 59 */     byte var1 = -1;
/*  56: 61 */     if (p_148085_0_.equals("int")) {
/*  57: 63 */       var1 = 0;
/*  58: 65 */     } else if (p_148085_0_.equals("float")) {
/*  59: 67 */       var1 = 4;
/*  60: 69 */     } else if (p_148085_0_.startsWith("matrix")) {
/*  61: 71 */       if (p_148085_0_.endsWith("2x2")) {
/*  62: 73 */         var1 = 8;
/*  63: 75 */       } else if (p_148085_0_.endsWith("3x3")) {
/*  64: 77 */         var1 = 9;
/*  65: 79 */       } else if (p_148085_0_.endsWith("4x4")) {
/*  66: 81 */         var1 = 10;
/*  67: 83 */       } else if (p_148085_0_.endsWith("2x3")) {
/*  68: 85 */         var1 = 11;
/*  69: 87 */       } else if (p_148085_0_.endsWith("2x4")) {
/*  70: 89 */         var1 = 12;
/*  71: 91 */       } else if (p_148085_0_.endsWith("3x2")) {
/*  72: 93 */         var1 = 13;
/*  73: 95 */       } else if (p_148085_0_.endsWith("3x4")) {
/*  74: 97 */         var1 = 14;
/*  75: 99 */       } else if (p_148085_0_.endsWith("4x2")) {
/*  76:101 */         var1 = 15;
/*  77:103 */       } else if (p_148085_0_.endsWith("4x3")) {
/*  78:105 */         var1 = 16;
/*  79:    */       }
/*  80:    */     }
/*  81:109 */     return var1;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void func_148084_b(int p_148084_1_)
/*  85:    */   {
/*  86:114 */     this.field_148102_b = p_148084_1_;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String func_148086_a()
/*  90:    */   {
/*  91:119 */     return this.field_148099_g;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void func_148090_a(float p_148090_1_)
/*  95:    */   {
/*  96:124 */     this.field_148098_f.position(0);
/*  97:125 */     this.field_148098_f.put(0, p_148090_1_);
/*  98:126 */     func_148096_h();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void func_148087_a(float p_148087_1_, float p_148087_2_)
/* 102:    */   {
/* 103:131 */     this.field_148098_f.position(0);
/* 104:132 */     this.field_148098_f.put(0, p_148087_1_);
/* 105:133 */     this.field_148098_f.put(1, p_148087_2_);
/* 106:134 */     func_148096_h();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void func_148095_a(float p_148095_1_, float p_148095_2_, float p_148095_3_)
/* 110:    */   {
/* 111:139 */     this.field_148098_f.position(0);
/* 112:140 */     this.field_148098_f.put(0, p_148095_1_);
/* 113:141 */     this.field_148098_f.put(1, p_148095_2_);
/* 114:142 */     this.field_148098_f.put(2, p_148095_3_);
/* 115:143 */     func_148096_h();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void func_148081_a(float p_148081_1_, float p_148081_2_, float p_148081_3_, float p_148081_4_)
/* 119:    */   {
/* 120:148 */     this.field_148098_f.position(0);
/* 121:149 */     this.field_148098_f.put(p_148081_1_);
/* 122:150 */     this.field_148098_f.put(p_148081_2_);
/* 123:151 */     this.field_148098_f.put(p_148081_3_);
/* 124:152 */     this.field_148098_f.put(p_148081_4_);
/* 125:153 */     this.field_148098_f.flip();
/* 126:154 */     func_148096_h();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void func_148092_b(float p_148092_1_, float p_148092_2_, float p_148092_3_, float p_148092_4_)
/* 130:    */   {
/* 131:159 */     this.field_148098_f.position(0);
/* 132:161 */     if (this.field_148100_d >= 4) {
/* 133:163 */       this.field_148098_f.put(0, p_148092_1_);
/* 134:    */     }
/* 135:166 */     if (this.field_148100_d >= 5) {
/* 136:168 */       this.field_148098_f.put(1, p_148092_2_);
/* 137:    */     }
/* 138:171 */     if (this.field_148100_d >= 6) {
/* 139:173 */       this.field_148098_f.put(2, p_148092_3_);
/* 140:    */     }
/* 141:176 */     if (this.field_148100_d >= 7) {
/* 142:178 */       this.field_148098_f.put(3, p_148092_4_);
/* 143:    */     }
/* 144:181 */     func_148096_h();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void func_148083_a(int p_148083_1_, int p_148083_2_, int p_148083_3_, int p_148083_4_)
/* 148:    */   {
/* 149:186 */     this.field_148101_e.position(0);
/* 150:188 */     if (this.field_148100_d >= 0) {
/* 151:190 */       this.field_148101_e.put(0, p_148083_1_);
/* 152:    */     }
/* 153:193 */     if (this.field_148100_d >= 1) {
/* 154:195 */       this.field_148101_e.put(1, p_148083_2_);
/* 155:    */     }
/* 156:198 */     if (this.field_148100_d >= 2) {
/* 157:200 */       this.field_148101_e.put(2, p_148083_3_);
/* 158:    */     }
/* 159:203 */     if (this.field_148100_d >= 3) {
/* 160:205 */       this.field_148101_e.put(3, p_148083_4_);
/* 161:    */     }
/* 162:208 */     func_148096_h();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void func_148097_a(float[] p_148097_1_)
/* 166:    */   {
/* 167:213 */     if (p_148097_1_.length < this.field_148103_c)
/* 168:    */     {
/* 169:215 */       logger.warn("Uniform.set called with a too-small value array (expected " + this.field_148103_c + ", got " + p_148097_1_.length + "). Ignoring.");
/* 170:    */     }
/* 171:    */     else
/* 172:    */     {
/* 173:219 */       this.field_148098_f.position(0);
/* 174:220 */       this.field_148098_f.put(p_148097_1_);
/* 175:221 */       this.field_148098_f.position(0);
/* 176:222 */       func_148096_h();
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void func_148094_a(float p_148094_1_, float p_148094_2_, float p_148094_3_, float p_148094_4_, float p_148094_5_, float p_148094_6_, float p_148094_7_, float p_148094_8_, float p_148094_9_, float p_148094_10_, float p_148094_11_, float p_148094_12_, float p_148094_13_, float p_148094_14_, float p_148094_15_, float p_148094_16_)
/* 181:    */   {
/* 182:228 */     this.field_148098_f.position(0);
/* 183:229 */     this.field_148098_f.put(0, p_148094_1_);
/* 184:230 */     this.field_148098_f.put(1, p_148094_2_);
/* 185:231 */     this.field_148098_f.put(2, p_148094_3_);
/* 186:232 */     this.field_148098_f.put(3, p_148094_4_);
/* 187:233 */     this.field_148098_f.put(4, p_148094_5_);
/* 188:234 */     this.field_148098_f.put(5, p_148094_6_);
/* 189:235 */     this.field_148098_f.put(6, p_148094_7_);
/* 190:236 */     this.field_148098_f.put(7, p_148094_8_);
/* 191:237 */     this.field_148098_f.put(8, p_148094_9_);
/* 192:238 */     this.field_148098_f.put(9, p_148094_10_);
/* 193:239 */     this.field_148098_f.put(10, p_148094_11_);
/* 194:240 */     this.field_148098_f.put(11, p_148094_12_);
/* 195:241 */     this.field_148098_f.put(12, p_148094_13_);
/* 196:242 */     this.field_148098_f.put(13, p_148094_14_);
/* 197:243 */     this.field_148098_f.put(14, p_148094_15_);
/* 198:244 */     this.field_148098_f.put(15, p_148094_16_);
/* 199:245 */     func_148096_h();
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void func_148088_a(Matrix4f p_148088_1_)
/* 203:    */   {
/* 204:250 */     func_148094_a(p_148088_1_.m00, p_148088_1_.m01, p_148088_1_.m02, p_148088_1_.m03, p_148088_1_.m10, p_148088_1_.m11, p_148088_1_.m12, p_148088_1_.m13, p_148088_1_.m20, p_148088_1_.m21, p_148088_1_.m22, p_148088_1_.m23, p_148088_1_.m30, p_148088_1_.m31, p_148088_1_.m32, p_148088_1_.m33);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void func_148093_b()
/* 208:    */   {
/* 209:255 */     if (!this.field_148105_h) {}
/* 210:260 */     this.field_148105_h = false;
/* 211:262 */     if (this.field_148100_d <= 3)
/* 212:    */     {
/* 213:264 */       func_148091_i();
/* 214:    */     }
/* 215:266 */     else if (this.field_148100_d <= 7)
/* 216:    */     {
/* 217:268 */       func_148089_j();
/* 218:    */     }
/* 219:    */     else
/* 220:    */     {
/* 221:272 */       if (this.field_148100_d > 16)
/* 222:    */       {
/* 223:274 */         logger.warn("Uniform.upload called, but type value (" + this.field_148100_d + ") is not " + "a valid type. Ignoring.");
/* 224:275 */         return;
/* 225:    */       }
/* 226:278 */       func_148082_k();
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   private void func_148091_i()
/* 231:    */   {
/* 232:284 */     switch (this.field_148100_d)
/* 233:    */     {
/* 234:    */     case 0: 
/* 235:287 */       GL20.glUniform1(this.field_148102_b, this.field_148101_e);
/* 236:288 */       break;
/* 237:    */     case 1: 
/* 238:291 */       GL20.glUniform2(this.field_148102_b, this.field_148101_e);
/* 239:292 */       break;
/* 240:    */     case 2: 
/* 241:295 */       GL20.glUniform3(this.field_148102_b, this.field_148101_e);
/* 242:296 */       break;
/* 243:    */     case 3: 
/* 244:299 */       GL20.glUniform4(this.field_148102_b, this.field_148101_e);
/* 245:300 */       break;
/* 246:    */     default: 
/* 247:303 */       logger.warn("Uniform.upload called, but count value (" + this.field_148103_c + ") is " + " not in the range of 1 to 4. Ignoring.");
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   private void func_148089_j()
/* 252:    */   {
/* 253:309 */     switch (this.field_148100_d)
/* 254:    */     {
/* 255:    */     case 4: 
/* 256:312 */       GL20.glUniform1(this.field_148102_b, this.field_148098_f);
/* 257:313 */       break;
/* 258:    */     case 5: 
/* 259:316 */       GL20.glUniform2(this.field_148102_b, this.field_148098_f);
/* 260:317 */       break;
/* 261:    */     case 6: 
/* 262:320 */       GL20.glUniform3(this.field_148102_b, this.field_148098_f);
/* 263:321 */       break;
/* 264:    */     case 7: 
/* 265:324 */       GL20.glUniform4(this.field_148102_b, this.field_148098_f);
/* 266:325 */       break;
/* 267:    */     default: 
/* 268:328 */       logger.warn("Uniform.upload called, but count value (" + this.field_148103_c + ") is " + "not in the range of 1 to 4. Ignoring.");
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   private void func_148082_k()
/* 273:    */   {
/* 274:334 */     switch (this.field_148100_d)
/* 275:    */     {
/* 276:    */     case 8: 
/* 277:337 */       GL20.glUniformMatrix2(this.field_148102_b, true, this.field_148098_f);
/* 278:338 */       break;
/* 279:    */     case 9: 
/* 280:341 */       GL20.glUniformMatrix3(this.field_148102_b, true, this.field_148098_f);
/* 281:342 */       break;
/* 282:    */     case 10: 
/* 283:345 */       GL20.glUniformMatrix4(this.field_148102_b, true, this.field_148098_f);
/* 284:346 */       break;
/* 285:    */     case 11: 
/* 286:349 */       GL21.glUniformMatrix2x3(this.field_148102_b, true, this.field_148098_f);
/* 287:350 */       break;
/* 288:    */     case 12: 
/* 289:353 */       GL21.glUniformMatrix2x4(this.field_148102_b, true, this.field_148098_f);
/* 290:354 */       break;
/* 291:    */     case 13: 
/* 292:357 */       GL21.glUniformMatrix3x2(this.field_148102_b, true, this.field_148098_f);
/* 293:358 */       break;
/* 294:    */     case 14: 
/* 295:361 */       GL21.glUniformMatrix3x4(this.field_148102_b, true, this.field_148098_f);
/* 296:362 */       break;
/* 297:    */     case 15: 
/* 298:365 */       GL21.glUniformMatrix4x2(this.field_148102_b, true, this.field_148098_f);
/* 299:366 */       break;
/* 300:    */     case 16: 
/* 301:369 */       GL21.glUniformMatrix4x3(this.field_148102_b, true, this.field_148098_f);
/* 302:    */     }
/* 303:    */   }
/* 304:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.shader.ShaderUniform
 * JD-Core Version:    0.7.0.1
 */