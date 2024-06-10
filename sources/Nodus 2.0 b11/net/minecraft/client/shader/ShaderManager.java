/*   1:    */ package net.minecraft.client.shader;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import com.google.common.collect.Lists;
/*   5:    */ import com.google.common.collect.Maps;
/*   6:    */ import com.google.gson.JsonArray;
/*   7:    */ import com.google.gson.JsonElement;
/*   8:    */ import com.google.gson.JsonObject;
/*   9:    */ import com.google.gson.JsonParser;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*  15:    */ import net.minecraft.client.resources.IResource;
/*  16:    */ import net.minecraft.client.resources.IResourceManager;
/*  17:    */ import net.minecraft.client.util.JsonBlendingMode;
/*  18:    */ import net.minecraft.client.util.JsonException;
/*  19:    */ import net.minecraft.util.JsonUtils;
/*  20:    */ import net.minecraft.util.ResourceLocation;
/*  21:    */ import org.apache.commons.io.IOUtils;
/*  22:    */ import org.apache.logging.log4j.LogManager;
/*  23:    */ import org.apache.logging.log4j.Logger;
/*  24:    */ import org.lwjgl.opengl.GL11;
/*  25:    */ import org.lwjgl.opengl.GL13;
/*  26:    */ import org.lwjgl.opengl.GL20;
/*  27:    */ 
/*  28:    */ public class ShaderManager
/*  29:    */ {
/*  30: 29 */   private static final Logger logger = ;
/*  31: 30 */   private static final ShaderDefault defaultShaderUniform = new ShaderDefault();
/*  32: 31 */   private static ShaderManager staticShaderManager = null;
/*  33: 32 */   private static int field_147999_d = -1;
/*  34: 33 */   private static boolean field_148000_e = true;
/*  35: 34 */   private final Map field_147997_f = Maps.newHashMap();
/*  36: 35 */   private final List field_147998_g = Lists.newArrayList();
/*  37: 36 */   private final List field_148010_h = Lists.newArrayList();
/*  38: 37 */   private final List field_148011_i = Lists.newArrayList();
/*  39: 38 */   private final List field_148008_j = Lists.newArrayList();
/*  40: 39 */   private final Map field_148009_k = Maps.newHashMap();
/*  41:    */   private final int field_148006_l;
/*  42:    */   private final String field_148007_m;
/*  43:    */   private final boolean field_148004_n;
/*  44:    */   private boolean field_148005_o;
/*  45:    */   private final JsonBlendingMode field_148016_p;
/*  46:    */   private final List field_148015_q;
/*  47:    */   private final List field_148014_r;
/*  48:    */   private final ShaderLoader field_148013_s;
/*  49:    */   private final ShaderLoader field_148012_t;
/*  50:    */   private static final String __OBFID = "CL_00001040";
/*  51:    */   
/*  52:    */   public ShaderManager(IResourceManager p_i45087_1_, String p_i45087_2_)
/*  53:    */     throws JsonException
/*  54:    */   {
/*  55: 53 */     JsonParser var3 = new JsonParser();
/*  56: 54 */     ResourceLocation var4 = new ResourceLocation("shaders/program/" + p_i45087_2_ + ".json");
/*  57: 55 */     this.field_148007_m = p_i45087_2_;
/*  58: 56 */     InputStream var5 = null;
/*  59:    */     try
/*  60:    */     {
/*  61: 60 */       var5 = p_i45087_1_.getResource(var4).getInputStream();
/*  62: 61 */       JsonObject var6 = var3.parse(IOUtils.toString(var5, Charsets.UTF_8)).getAsJsonObject();
/*  63: 62 */       String var7 = JsonUtils.getJsonObjectStringFieldValue(var6, "vertex");
/*  64: 63 */       String var28 = JsonUtils.getJsonObjectStringFieldValue(var6, "fragment");
/*  65: 64 */       JsonArray var9 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var6, "samplers", null);
/*  66: 66 */       if (var9 != null)
/*  67:    */       {
/*  68: 68 */         int var10 = 0;
/*  69: 70 */         for (Iterator var11 = var9.iterator(); var11.hasNext(); var10++)
/*  70:    */         {
/*  71: 72 */           JsonElement var12 = (JsonElement)var11.next();
/*  72:    */           try
/*  73:    */           {
/*  74: 76 */             func_147996_a(var12);
/*  75:    */           }
/*  76:    */           catch (Exception var25)
/*  77:    */           {
/*  78: 80 */             JsonException var14 = JsonException.func_151379_a(var25);
/*  79: 81 */             var14.func_151380_a("samplers[" + var10 + "]");
/*  80: 82 */             throw var14;
/*  81:    */           }
/*  82:    */         }
/*  83:    */       }
/*  84: 87 */       JsonArray var30 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var6, "attributes", null);
/*  85: 90 */       if (var30 != null)
/*  86:    */       {
/*  87: 92 */         int var29 = 0;
/*  88: 93 */         this.field_148015_q = Lists.newArrayListWithCapacity(var30.size());
/*  89: 94 */         this.field_148014_r = Lists.newArrayListWithCapacity(var30.size());
/*  90: 96 */         for (Iterator var31 = var30.iterator(); var31.hasNext(); var29++)
/*  91:    */         {
/*  92: 98 */           JsonElement var13 = (JsonElement)var31.next();
/*  93:    */           try
/*  94:    */           {
/*  95:102 */             this.field_148014_r.add(JsonUtils.getJsonElementStringValue(var13, "attribute"));
/*  96:    */           }
/*  97:    */           catch (Exception var24)
/*  98:    */           {
/*  99:106 */             JsonException var15 = JsonException.func_151379_a(var24);
/* 100:107 */             var15.func_151380_a("attributes[" + var29 + "]");
/* 101:108 */             throw var15;
/* 102:    */           }
/* 103:    */         }
/* 104:    */       }
/* 105:    */       else
/* 106:    */       {
/* 107:114 */         this.field_148015_q = null;
/* 108:115 */         this.field_148014_r = null;
/* 109:    */       }
/* 110:118 */       JsonArray var33 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var6, "uniforms", null);
/* 111:120 */       if (var33 != null)
/* 112:    */       {
/* 113:122 */         int var32 = 0;
/* 114:124 */         for (Iterator var35 = var33.iterator(); var35.hasNext(); var32++)
/* 115:    */         {
/* 116:126 */           JsonElement var36 = (JsonElement)var35.next();
/* 117:    */           try
/* 118:    */           {
/* 119:130 */             func_147987_b(var36);
/* 120:    */           }
/* 121:    */           catch (Exception var23)
/* 122:    */           {
/* 123:134 */             JsonException var16 = JsonException.func_151379_a(var23);
/* 124:135 */             var16.func_151380_a("uniforms[" + var32 + "]");
/* 125:136 */             throw var16;
/* 126:    */           }
/* 127:    */         }
/* 128:    */       }
/* 129:141 */       this.field_148016_p = JsonBlendingMode.func_148110_a(JsonUtils.getJsonObjectFieldOrDefault(var6, "blend", null));
/* 130:142 */       this.field_148004_n = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var6, "cull", true);
/* 131:143 */       this.field_148013_s = ShaderLoader.func_148057_a(p_i45087_1_, ShaderLoader.ShaderType.VERTEX, var7);
/* 132:144 */       this.field_148012_t = ShaderLoader.func_148057_a(p_i45087_1_, ShaderLoader.ShaderType.FRAGMENT, var28);
/* 133:145 */       this.field_148006_l = ShaderLinkHelper.getStaticShaderLinkHelper().func_148078_c();
/* 134:146 */       ShaderLinkHelper.getStaticShaderLinkHelper().func_148075_b(this);
/* 135:147 */       func_147990_i();
/* 136:149 */       if (this.field_148014_r != null)
/* 137:    */       {
/* 138:151 */         Iterator var31 = this.field_148014_r.iterator();
/* 139:153 */         while (var31.hasNext())
/* 140:    */         {
/* 141:155 */           String var34 = (String)var31.next();
/* 142:156 */           int var37 = GL20.glGetAttribLocation(this.field_148006_l, var34);
/* 143:157 */           this.field_148015_q.add(Integer.valueOf(var37));
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:    */     catch (Exception var26)
/* 148:    */     {
/* 149:163 */       JsonException var8 = JsonException.func_151379_a(var26);
/* 150:164 */       var8.func_151381_b(var4.getResourcePath());
/* 151:165 */       throw var8;
/* 152:    */     }
/* 153:    */     finally
/* 154:    */     {
/* 155:169 */       IOUtils.closeQuietly(var5);
/* 156:    */     }
/* 157:172 */     func_147985_d();
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void func_147988_a()
/* 161:    */   {
/* 162:177 */     ShaderLinkHelper.getStaticShaderLinkHelper().func_148077_a(this);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void func_147993_b()
/* 166:    */   {
/* 167:182 */     GL20.glUseProgram(0);
/* 168:183 */     field_147999_d = -1;
/* 169:184 */     staticShaderManager = null;
/* 170:185 */     field_148000_e = true;
/* 171:187 */     for (int var1 = 0; var1 < this.field_148010_h.size(); var1++) {
/* 172:189 */       if (this.field_147997_f.get(this.field_147998_g.get(var1)) != null)
/* 173:    */       {
/* 174:191 */         GL13.glActiveTexture(33984 + var1);
/* 175:192 */         GL11.glBindTexture(3553, 0);
/* 176:    */       }
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void func_147995_c()
/* 181:    */   {
/* 182:199 */     this.field_148005_o = false;
/* 183:200 */     staticShaderManager = this;
/* 184:201 */     this.field_148016_p.func_148109_a();
/* 185:203 */     if (this.field_148006_l != field_147999_d)
/* 186:    */     {
/* 187:205 */       GL20.glUseProgram(this.field_148006_l);
/* 188:206 */       field_147999_d = this.field_148006_l;
/* 189:    */     }
/* 190:209 */     if (field_148000_e != this.field_148004_n)
/* 191:    */     {
/* 192:211 */       field_148000_e = this.field_148004_n;
/* 193:213 */       if (this.field_148004_n) {
/* 194:215 */         GL11.glEnable(2884);
/* 195:    */       } else {
/* 196:219 */         GL11.glDisable(2884);
/* 197:    */       }
/* 198:    */     }
/* 199:223 */     for (int var1 = 0; var1 < this.field_148010_h.size(); var1++) {
/* 200:225 */       if (this.field_147997_f.get(this.field_147998_g.get(var1)) != null)
/* 201:    */       {
/* 202:227 */         GL13.glActiveTexture(33984 + var1);
/* 203:228 */         GL11.glEnable(3553);
/* 204:229 */         Object var2 = this.field_147997_f.get(this.field_147998_g.get(var1));
/* 205:230 */         int var3 = -1;
/* 206:232 */         if ((var2 instanceof Framebuffer)) {
/* 207:234 */           var3 = ((Framebuffer)var2).framebufferTexture;
/* 208:236 */         } else if ((var2 instanceof ITextureObject)) {
/* 209:238 */           var3 = ((ITextureObject)var2).getGlTextureId();
/* 210:240 */         } else if ((var2 instanceof Integer)) {
/* 211:242 */           var3 = ((Integer)var2).intValue();
/* 212:    */         }
/* 213:245 */         if (var3 != -1)
/* 214:    */         {
/* 215:247 */           GL11.glBindTexture(3553, var3);
/* 216:248 */           GL20.glUniform1i(GL20.glGetUniformLocation(this.field_148006_l, (CharSequence)this.field_147998_g.get(var1)), var1);
/* 217:    */         }
/* 218:    */       }
/* 219:    */     }
/* 220:253 */     Iterator var4 = this.field_148011_i.iterator();
/* 221:255 */     while (var4.hasNext())
/* 222:    */     {
/* 223:257 */       ShaderUniform var5 = (ShaderUniform)var4.next();
/* 224:258 */       var5.func_148093_b();
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void func_147985_d()
/* 229:    */   {
/* 230:264 */     this.field_148005_o = true;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public ShaderUniform func_147991_a(String p_147991_1_)
/* 234:    */   {
/* 235:269 */     return this.field_148009_k.containsKey(p_147991_1_) ? (ShaderUniform)this.field_148009_k.get(p_147991_1_) : null;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public ShaderUniform func_147984_b(String p_147984_1_)
/* 239:    */   {
/* 240:274 */     return this.field_148009_k.containsKey(p_147984_1_) ? (ShaderUniform)this.field_148009_k.get(p_147984_1_) : defaultShaderUniform;
/* 241:    */   }
/* 242:    */   
/* 243:    */   private void func_147990_i()
/* 244:    */   {
/* 245:279 */     int var1 = 0;
/* 246:283 */     for (int var2 = 0; var1 < this.field_147998_g.size(); var2++)
/* 247:    */     {
/* 248:285 */       String var3 = (String)this.field_147998_g.get(var1);
/* 249:286 */       int var4 = GL20.glGetUniformLocation(this.field_148006_l, var3);
/* 250:288 */       if (var4 == -1)
/* 251:    */       {
/* 252:290 */         logger.warn("Shader " + this.field_148007_m + "could not find sampler named " + var3 + " in the specified shader program.");
/* 253:291 */         this.field_147997_f.remove(var3);
/* 254:292 */         this.field_147998_g.remove(var2);
/* 255:293 */         var2--;
/* 256:    */       }
/* 257:    */       else
/* 258:    */       {
/* 259:297 */         this.field_148010_h.add(Integer.valueOf(var4));
/* 260:    */       }
/* 261:300 */       var1++;
/* 262:    */     }
/* 263:303 */     Iterator var5 = this.field_148011_i.iterator();
/* 264:305 */     while (var5.hasNext())
/* 265:    */     {
/* 266:307 */       ShaderUniform var6 = (ShaderUniform)var5.next();
/* 267:308 */       String var3 = var6.func_148086_a();
/* 268:309 */       int var4 = GL20.glGetUniformLocation(this.field_148006_l, var3);
/* 269:311 */       if (var4 == -1)
/* 270:    */       {
/* 271:313 */         logger.warn("Could not find uniform named " + var3 + " in the specified" + " shader program.");
/* 272:    */       }
/* 273:    */       else
/* 274:    */       {
/* 275:317 */         this.field_148008_j.add(Integer.valueOf(var4));
/* 276:318 */         var6.func_148084_b(var4);
/* 277:319 */         this.field_148009_k.put(var3, var6);
/* 278:    */       }
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   private void func_147996_a(JsonElement p_147996_1_)
/* 283:    */   {
/* 284:326 */     JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_147996_1_, "sampler");
/* 285:327 */     String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
/* 286:329 */     if (!JsonUtils.jsonObjectFieldTypeIsString(var2, "file"))
/* 287:    */     {
/* 288:331 */       this.field_147997_f.put(var3, null);
/* 289:332 */       this.field_147998_g.add(var3);
/* 290:    */     }
/* 291:    */     else
/* 292:    */     {
/* 293:336 */       this.field_147998_g.add(var3);
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void func_147992_a(String p_147992_1_, Object p_147992_2_)
/* 298:    */   {
/* 299:342 */     if (this.field_147997_f.containsKey(p_147992_1_)) {
/* 300:344 */       this.field_147997_f.remove(p_147992_1_);
/* 301:    */     }
/* 302:347 */     this.field_147997_f.put(p_147992_1_, p_147992_2_);
/* 303:348 */     func_147985_d();
/* 304:    */   }
/* 305:    */   
/* 306:    */   private void func_147987_b(JsonElement p_147987_1_)
/* 307:    */     throws JsonException
/* 308:    */   {
/* 309:353 */     JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_147987_1_, "uniform");
/* 310:354 */     String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
/* 311:355 */     int var4 = ShaderUniform.func_148085_a(JsonUtils.getJsonObjectStringFieldValue(var2, "type"));
/* 312:356 */     int var5 = JsonUtils.getJsonObjectIntegerFieldValue(var2, "count");
/* 313:357 */     float[] var6 = new float[Math.max(var5, 16)];
/* 314:358 */     JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var2, "values");
/* 315:360 */     if ((var7.size() != var5) && (var7.size() > 1)) {
/* 316:362 */       throw new JsonException("Invalid amount of values specified (expected " + var5 + ", found " + var7.size() + ")");
/* 317:    */     }
/* 318:366 */     int var8 = 0;
/* 319:368 */     for (Iterator var9 = var7.iterator(); var9.hasNext(); var8++)
/* 320:    */     {
/* 321:370 */       JsonElement var10 = (JsonElement)var9.next();
/* 322:    */       try
/* 323:    */       {
/* 324:374 */         var6[var8] = JsonUtils.getJsonElementFloatValue(var10, "value");
/* 325:    */       }
/* 326:    */       catch (Exception var13)
/* 327:    */       {
/* 328:378 */         JsonException var12 = JsonException.func_151379_a(var13);
/* 329:379 */         var12.func_151380_a("values[" + var8 + "]");
/* 330:380 */         throw var12;
/* 331:    */       }
/* 332:    */     }
/* 333:384 */     if ((var5 > 1) && (var7.size() == 1)) {
/* 334:386 */       while (var8 < var5)
/* 335:    */       {
/* 336:388 */         var6[var8] = var6[0];
/* 337:389 */         var8++;
/* 338:    */       }
/* 339:    */     }
/* 340:393 */     int var14 = (var5 > 1) && (var5 <= 4) && (var4 < 8) ? var5 - 1 : 0;
/* 341:394 */     ShaderUniform var15 = new ShaderUniform(var3, var4 + var14, var5, this);
/* 342:396 */     if (var4 <= 3) {
/* 343:398 */       var15.func_148083_a((int)var6[0], (int)var6[1], (int)var6[2], (int)var6[3]);
/* 344:400 */     } else if (var4 <= 7) {
/* 345:402 */       var15.func_148092_b(var6[0], var6[1], var6[2], var6[3]);
/* 346:    */     } else {
/* 347:406 */       var15.func_148097_a(var6);
/* 348:    */     }
/* 349:409 */     this.field_148011_i.add(var15);
/* 350:    */   }
/* 351:    */   
/* 352:    */   public ShaderLoader func_147989_e()
/* 353:    */   {
/* 354:415 */     return this.field_148013_s;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public ShaderLoader func_147994_f()
/* 358:    */   {
/* 359:420 */     return this.field_148012_t;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public int func_147986_h()
/* 363:    */   {
/* 364:425 */     return this.field_148006_l;
/* 365:    */   }
/* 366:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.shader.ShaderManager
 * JD-Core Version:    0.7.0.1
 */