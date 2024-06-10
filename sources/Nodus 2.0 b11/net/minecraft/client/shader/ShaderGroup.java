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
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.vecmath.Matrix4f;
/*  16:    */ import net.minecraft.client.resources.IResource;
/*  17:    */ import net.minecraft.client.resources.IResourceManager;
/*  18:    */ import net.minecraft.client.util.JsonException;
/*  19:    */ import net.minecraft.util.JsonUtils;
/*  20:    */ import net.minecraft.util.ResourceLocation;
/*  21:    */ import org.apache.commons.io.IOUtils;
/*  22:    */ 
/*  23:    */ public class ShaderGroup
/*  24:    */ {
/*  25:    */   private final Framebuffer mainFramebuffer;
/*  26:    */   private final IResourceManager resourceManager;
/*  27:    */   private final String shaderGroupName;
/*  28: 27 */   private final List listShaders = Lists.newArrayList();
/*  29: 28 */   private final Map mapFramebuffers = Maps.newHashMap();
/*  30: 29 */   private final List listFramebuffers = Lists.newArrayList();
/*  31:    */   private Matrix4f projectionMatrix;
/*  32:    */   private int mainFramebufferWidth;
/*  33:    */   private int mainFramebufferHeight;
/*  34:    */   private float field_148036_j;
/*  35:    */   private float field_148037_k;
/*  36:    */   private static final String __OBFID = "CL_00001041";
/*  37:    */   
/*  38:    */   public ShaderGroup(IResourceManager p_i45088_1_, Framebuffer p_i45088_2_, ResourceLocation p_i45088_3_)
/*  39:    */     throws JsonException
/*  40:    */   {
/*  41: 39 */     this.resourceManager = p_i45088_1_;
/*  42: 40 */     this.mainFramebuffer = p_i45088_2_;
/*  43: 41 */     this.field_148036_j = 0.0F;
/*  44: 42 */     this.field_148037_k = 0.0F;
/*  45: 43 */     this.mainFramebufferWidth = p_i45088_2_.framebufferWidth;
/*  46: 44 */     this.mainFramebufferHeight = p_i45088_2_.framebufferHeight;
/*  47: 45 */     this.shaderGroupName = p_i45088_3_.toString();
/*  48: 46 */     resetProjectionMatrix();
/*  49: 47 */     initFromLocation(p_i45088_3_);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void initFromLocation(ResourceLocation p_148025_1_)
/*  53:    */     throws JsonException
/*  54:    */   {
/*  55: 52 */     JsonParser var2 = new JsonParser();
/*  56: 53 */     InputStream var3 = null;
/*  57:    */     try
/*  58:    */     {
/*  59: 57 */       IResource var4 = this.resourceManager.getResource(p_148025_1_);
/*  60: 58 */       var3 = var4.getInputStream();
/*  61: 59 */       JsonObject var21 = var2.parse(IOUtils.toString(var3, Charsets.UTF_8)).getAsJsonObject();
/*  62: 66 */       if (JsonUtils.jsonObjectFieldTypeIsArray(var21, "targets"))
/*  63:    */       {
/*  64: 68 */         JsonArray var6 = var21.getAsJsonArray("targets");
/*  65: 69 */         int var7 = 0;
/*  66: 71 */         for (Iterator var8 = var6.iterator(); var8.hasNext(); var7++)
/*  67:    */         {
/*  68: 73 */           JsonElement var9 = (JsonElement)var8.next();
/*  69:    */           try
/*  70:    */           {
/*  71: 77 */             initTarget(var9);
/*  72:    */           }
/*  73:    */           catch (Exception var18)
/*  74:    */           {
/*  75: 81 */             JsonException var11 = JsonException.func_151379_a(var18);
/*  76: 82 */             var11.func_151380_a("targets[" + var7 + "]");
/*  77: 83 */             throw var11;
/*  78:    */           }
/*  79:    */         }
/*  80:    */       }
/*  81: 88 */       if (JsonUtils.jsonObjectFieldTypeIsArray(var21, "passes"))
/*  82:    */       {
/*  83: 90 */         JsonArray var6 = var21.getAsJsonArray("passes");
/*  84: 91 */         int var7 = 0;
/*  85: 93 */         for (Iterator var8 = var6.iterator(); var8.hasNext(); var7++)
/*  86:    */         {
/*  87: 95 */           JsonElement var9 = (JsonElement)var8.next();
/*  88:    */           try
/*  89:    */           {
/*  90: 99 */             initPass(var9);
/*  91:    */           }
/*  92:    */           catch (Exception var17)
/*  93:    */           {
/*  94:103 */             JsonException var11 = JsonException.func_151379_a(var17);
/*  95:104 */             var11.func_151380_a("passes[" + var7 + "]");
/*  96:105 */             throw var11;
/*  97:    */           }
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:    */     catch (Exception var19)
/* 102:    */     {
/* 103:112 */       JsonException var5 = JsonException.func_151379_a(var19);
/* 104:113 */       var5.func_151381_b(p_148025_1_.getResourcePath());
/* 105:114 */       throw var5;
/* 106:    */     }
/* 107:    */     finally
/* 108:    */     {
/* 109:118 */       IOUtils.closeQuietly(var3);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   private void initTarget(JsonElement p_148027_1_)
/* 114:    */     throws JsonException
/* 115:    */   {
/* 116:124 */     if (JsonUtils.jsonElementTypeIsString(p_148027_1_))
/* 117:    */     {
/* 118:126 */       addFramebuffer(p_148027_1_.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
/* 119:    */     }
/* 120:    */     else
/* 121:    */     {
/* 122:130 */       JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_148027_1_, "target");
/* 123:131 */       String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
/* 124:132 */       int var4 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var2, "width", this.mainFramebufferWidth);
/* 125:133 */       int var5 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var2, "height", this.mainFramebufferHeight);
/* 126:135 */       if (this.mapFramebuffers.containsKey(var3)) {
/* 127:137 */         throw new JsonException(var3 + " is already defined");
/* 128:    */       }
/* 129:140 */       addFramebuffer(var3, var4, var5);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   private void initPass(JsonElement p_148019_1_)
/* 134:    */     throws JsonException
/* 135:    */   {
/* 136:146 */     JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_148019_1_, "pass");
/* 137:147 */     String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
/* 138:148 */     String var4 = JsonUtils.getJsonObjectStringFieldValue(var2, "intarget");
/* 139:149 */     String var5 = JsonUtils.getJsonObjectStringFieldValue(var2, "outtarget");
/* 140:150 */     Framebuffer var6 = getFramebuffer(var4);
/* 141:151 */     Framebuffer var7 = getFramebuffer(var5);
/* 142:153 */     if (var6 == null) {
/* 143:155 */       throw new JsonException("Input target '" + var4 + "' does not exist");
/* 144:    */     }
/* 145:157 */     if (var7 == null) {
/* 146:159 */       throw new JsonException("Output target '" + var5 + "' does not exist");
/* 147:    */     }
/* 148:163 */     Shader var8 = addShader(var3, var6, var7);
/* 149:164 */     JsonArray var9 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var2, "auxtargets", null);
/* 150:166 */     if (var9 != null)
/* 151:    */     {
/* 152:168 */       int var10 = 0;
/* 153:170 */       for (Iterator var11 = var9.iterator(); var11.hasNext(); var10++)
/* 154:    */       {
/* 155:172 */         JsonElement var12 = (JsonElement)var11.next();
/* 156:    */         try
/* 157:    */         {
/* 158:176 */           JsonObject var13 = JsonUtils.getJsonElementAsJsonObject(var12, "auxtarget");
/* 159:177 */           String var24 = JsonUtils.getJsonObjectStringFieldValue(var13, "name");
/* 160:178 */           String var15 = JsonUtils.getJsonObjectStringFieldValue(var13, "id");
/* 161:179 */           Framebuffer var16 = getFramebuffer(var15);
/* 162:181 */           if (var16 == null) {
/* 163:183 */             throw new JsonException("Render target '" + var15 + "' does not exist");
/* 164:    */           }
/* 165:186 */           var8.addAuxFramebuffer(var24, var16, var16.framebufferTextureWidth, var16.framebufferTextureHeight);
/* 166:    */         }
/* 167:    */         catch (Exception var18)
/* 168:    */         {
/* 169:190 */           JsonException var14 = JsonException.func_151379_a(var18);
/* 170:191 */           var14.func_151380_a("auxtargets[" + var10 + "]");
/* 171:192 */           throw var14;
/* 172:    */         }
/* 173:    */       }
/* 174:    */     }
/* 175:197 */     JsonArray var20 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var2, "uniforms", null);
/* 176:199 */     if (var20 != null)
/* 177:    */     {
/* 178:201 */       int var19 = 0;
/* 179:203 */       for (Iterator var21 = var20.iterator(); var21.hasNext(); var19++)
/* 180:    */       {
/* 181:205 */         JsonElement var22 = (JsonElement)var21.next();
/* 182:    */         try
/* 183:    */         {
/* 184:209 */           initUniform(var22);
/* 185:    */         }
/* 186:    */         catch (Exception var17)
/* 187:    */         {
/* 188:213 */           JsonException var23 = JsonException.func_151379_a(var17);
/* 189:214 */           var23.func_151380_a("uniforms[" + var19 + "]");
/* 190:215 */           throw var23;
/* 191:    */         }
/* 192:    */       }
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   private void initUniform(JsonElement p_148028_1_)
/* 197:    */     throws JsonException
/* 198:    */   {
/* 199:224 */     JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_148028_1_, "uniform");
/* 200:225 */     String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
/* 201:226 */     ShaderUniform var4 = ((Shader)this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().func_147991_a(var3);
/* 202:228 */     if (var4 == null) {
/* 203:230 */       throw new JsonException("Uniform '" + var3 + "' does not exist");
/* 204:    */     }
/* 205:234 */     float[] var5 = new float[4];
/* 206:235 */     int var6 = 0;
/* 207:236 */     JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var2, "values");
/* 208:238 */     for (Iterator var8 = var7.iterator(); var8.hasNext(); var6++)
/* 209:    */     {
/* 210:240 */       JsonElement var9 = (JsonElement)var8.next();
/* 211:    */       try
/* 212:    */       {
/* 213:244 */         var5[var6] = JsonUtils.getJsonElementFloatValue(var9, "value");
/* 214:    */       }
/* 215:    */       catch (Exception var12)
/* 216:    */       {
/* 217:248 */         JsonException var11 = JsonException.func_151379_a(var12);
/* 218:249 */         var11.func_151380_a("values[" + var6 + "]");
/* 219:250 */         throw var11;
/* 220:    */       }
/* 221:    */     }
/* 222:254 */     switch (var6)
/* 223:    */     {
/* 224:    */     case 0: 
/* 225:    */     default: 
/* 226:    */       break;
/* 227:    */     case 1: 
/* 228:261 */       var4.func_148090_a(var5[0]);
/* 229:262 */       break;
/* 230:    */     case 2: 
/* 231:265 */       var4.func_148087_a(var5[0], var5[1]);
/* 232:266 */       break;
/* 233:    */     case 3: 
/* 234:269 */       var4.func_148095_a(var5[0], var5[1], var5[2]);
/* 235:270 */       break;
/* 236:    */     case 4: 
/* 237:273 */       var4.func_148081_a(var5[0], var5[1], var5[2], var5[3]);
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void addFramebuffer(String p_148020_1_, int p_148020_2_, int p_148020_3_)
/* 242:    */   {
/* 243:280 */     Framebuffer var4 = new Framebuffer(p_148020_2_, p_148020_3_, true);
/* 244:281 */     var4.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 245:282 */     this.mapFramebuffers.put(p_148020_1_, var4);
/* 246:284 */     if ((p_148020_2_ == this.mainFramebufferWidth) && (p_148020_3_ == this.mainFramebufferHeight)) {
/* 247:286 */       this.listFramebuffers.add(var4);
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void deleteShaderGroup()
/* 252:    */   {
/* 253:292 */     Iterator var1 = this.mapFramebuffers.values().iterator();
/* 254:294 */     while (var1.hasNext())
/* 255:    */     {
/* 256:296 */       Framebuffer var2 = (Framebuffer)var1.next();
/* 257:297 */       var2.deleteFramebuffer();
/* 258:    */     }
/* 259:300 */     var1 = this.listShaders.iterator();
/* 260:302 */     while (var1.hasNext())
/* 261:    */     {
/* 262:304 */       Shader var3 = (Shader)var1.next();
/* 263:305 */       var3.deleteShader();
/* 264:    */     }
/* 265:308 */     this.listShaders.clear();
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Shader addShader(String p_148023_1_, Framebuffer p_148023_2_, Framebuffer p_148023_3_)
/* 269:    */     throws JsonException
/* 270:    */   {
/* 271:313 */     Shader var4 = new Shader(this.resourceManager, p_148023_1_, p_148023_2_, p_148023_3_);
/* 272:314 */     this.listShaders.add(this.listShaders.size(), var4);
/* 273:315 */     return var4;
/* 274:    */   }
/* 275:    */   
/* 276:    */   private void resetProjectionMatrix()
/* 277:    */   {
/* 278:320 */     this.projectionMatrix = new Matrix4f();
/* 279:321 */     this.projectionMatrix.setIdentity();
/* 280:322 */     this.projectionMatrix.m00 = (2.0F / this.mainFramebuffer.framebufferTextureWidth);
/* 281:323 */     this.projectionMatrix.m11 = (2.0F / -this.mainFramebuffer.framebufferTextureHeight);
/* 282:324 */     this.projectionMatrix.m22 = -0.0020002F;
/* 283:325 */     this.projectionMatrix.m33 = 1.0F;
/* 284:326 */     this.projectionMatrix.m03 = -1.0F;
/* 285:327 */     this.projectionMatrix.m13 = 1.0F;
/* 286:328 */     this.projectionMatrix.m23 = -1.0002F;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void createBindFramebuffers(int p_148026_1_, int p_148026_2_)
/* 290:    */   {
/* 291:333 */     this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
/* 292:334 */     this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
/* 293:335 */     resetProjectionMatrix();
/* 294:336 */     Iterator var3 = this.listShaders.iterator();
/* 295:338 */     while (var3.hasNext())
/* 296:    */     {
/* 297:340 */       Shader var4 = (Shader)var3.next();
/* 298:341 */       var4.setProjectionMatrix(this.projectionMatrix);
/* 299:    */     }
/* 300:344 */     var3 = this.listFramebuffers.iterator();
/* 301:346 */     while (var3.hasNext())
/* 302:    */     {
/* 303:348 */       Framebuffer var5 = (Framebuffer)var3.next();
/* 304:349 */       var5.createBindFramebuffer(p_148026_1_, p_148026_2_);
/* 305:    */     }
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void loadShaderGroup(float p_148018_1_)
/* 309:    */   {
/* 310:355 */     if (p_148018_1_ < this.field_148037_k)
/* 311:    */     {
/* 312:357 */       this.field_148036_j += 1.0F - this.field_148037_k;
/* 313:358 */       this.field_148036_j += p_148018_1_;
/* 314:    */     }
/* 315:    */     else
/* 316:    */     {
/* 317:362 */       this.field_148036_j += p_148018_1_ - this.field_148037_k;
/* 318:    */     }
/* 319:365 */     for (this.field_148037_k = p_148018_1_; this.field_148036_j > 20.0F; this.field_148036_j -= 20.0F) {}
/* 320:370 */     Iterator var2 = this.listShaders.iterator();
/* 321:372 */     while (var2.hasNext())
/* 322:    */     {
/* 323:374 */       Shader var3 = (Shader)var2.next();
/* 324:375 */       var3.loadShader(this.field_148036_j / 20.0F);
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   public final String getShaderGroupName()
/* 329:    */   {
/* 330:381 */     return this.shaderGroupName;
/* 331:    */   }
/* 332:    */   
/* 333:    */   private Framebuffer getFramebuffer(String p_148017_1_)
/* 334:    */   {
/* 335:386 */     return p_148017_1_.equals("minecraft:main") ? this.mainFramebuffer : p_148017_1_ == null ? null : (Framebuffer)this.mapFramebuffers.get(p_148017_1_);
/* 336:    */   }
/* 337:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.shader.ShaderGroup
 * JD-Core Version:    0.7.0.1
 */