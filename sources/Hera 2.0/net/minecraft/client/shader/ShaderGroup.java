/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ 
/*     */ public class ShaderGroup
/*     */ {
/*     */   private Framebuffer mainFramebuffer;
/*     */   private IResourceManager resourceManager;
/*     */   private String shaderGroupName;
/*  32 */   private final List<Shader> listShaders = Lists.newArrayList();
/*  33 */   private final Map<String, Framebuffer> mapFramebuffers = Maps.newHashMap();
/*  34 */   private final List<Framebuffer> listFramebuffers = Lists.newArrayList();
/*     */   
/*     */   private Matrix4f projectionMatrix;
/*     */   private int mainFramebufferWidth;
/*     */   private int mainFramebufferHeight;
/*     */   private float field_148036_j;
/*     */   private float field_148037_k;
/*     */   
/*     */   public ShaderGroup(TextureManager p_i1050_1_, IResourceManager p_i1050_2_, Framebuffer p_i1050_3_, ResourceLocation p_i1050_4_) throws JsonException, IOException, JsonSyntaxException {
/*  43 */     this.resourceManager = p_i1050_2_;
/*  44 */     this.mainFramebuffer = p_i1050_3_;
/*  45 */     this.field_148036_j = 0.0F;
/*  46 */     this.field_148037_k = 0.0F;
/*  47 */     this.mainFramebufferWidth = p_i1050_3_.framebufferWidth;
/*  48 */     this.mainFramebufferHeight = p_i1050_3_.framebufferHeight;
/*  49 */     this.shaderGroupName = p_i1050_4_.toString();
/*  50 */     resetProjectionMatrix();
/*  51 */     parseGroup(p_i1050_1_, p_i1050_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseGroup(TextureManager p_152765_1_, ResourceLocation p_152765_2_) throws JsonException, IOException, JsonSyntaxException {
/*  56 */     JsonParser jsonparser = new JsonParser();
/*  57 */     InputStream inputstream = null;
/*     */ 
/*     */     
/*     */     try {
/*  61 */       IResource iresource = this.resourceManager.getResource(p_152765_2_);
/*  62 */       inputstream = iresource.getInputStream();
/*  63 */       JsonObject jsonobject = jsonparser.parse(IOUtils.toString(inputstream, Charsets.UTF_8)).getAsJsonObject();
/*     */       
/*  65 */       if (JsonUtils.isJsonArray(jsonobject, "targets")) {
/*     */         
/*  67 */         JsonArray jsonarray = jsonobject.getAsJsonArray("targets");
/*  68 */         int i = 0;
/*     */         
/*  70 */         for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */           
/*     */           try {
/*  74 */             initTarget(jsonelement);
/*     */           }
/*  76 */           catch (Exception exception1) {
/*     */             
/*  78 */             JsonException jsonexception1 = JsonException.func_151379_a(exception1);
/*  79 */             jsonexception1.func_151380_a("targets[" + i + "]");
/*  80 */             throw jsonexception1;
/*     */           } 
/*     */           
/*  83 */           i++;
/*     */         } 
/*     */       } 
/*     */       
/*  87 */       if (JsonUtils.isJsonArray(jsonobject, "passes")) {
/*     */         
/*  89 */         JsonArray jsonarray1 = jsonobject.getAsJsonArray("passes");
/*  90 */         int j = 0;
/*     */         
/*  92 */         for (JsonElement jsonelement1 : jsonarray1)
/*     */         {
/*     */           
/*     */           try {
/*  96 */             parsePass(p_152765_1_, jsonelement1);
/*     */           }
/*  98 */           catch (Exception exception) {
/*     */             
/* 100 */             JsonException jsonexception2 = JsonException.func_151379_a(exception);
/* 101 */             jsonexception2.func_151380_a("passes[" + j + "]");
/* 102 */             throw jsonexception2;
/*     */           } 
/*     */           
/* 105 */           j++;
/*     */         }
/*     */       
/*     */       } 
/* 109 */     } catch (Exception exception2) {
/*     */       
/* 111 */       JsonException jsonexception = JsonException.func_151379_a(exception2);
/* 112 */       jsonexception.func_151381_b(p_152765_2_.getResourcePath());
/* 113 */       throw jsonexception;
/*     */     }
/*     */     finally {
/*     */       
/* 117 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initTarget(JsonElement p_148027_1_) throws JsonException {
/* 123 */     if (JsonUtils.isString(p_148027_1_)) {
/*     */       
/* 125 */       addFramebuffer(p_148027_1_.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
/*     */     }
/*     */     else {
/*     */       
/* 129 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_148027_1_, "target");
/* 130 */       String s = JsonUtils.getString(jsonobject, "name");
/* 131 */       int i = JsonUtils.getInt(jsonobject, "width", this.mainFramebufferWidth);
/* 132 */       int j = JsonUtils.getInt(jsonobject, "height", this.mainFramebufferHeight);
/*     */       
/* 134 */       if (this.mapFramebuffers.containsKey(s))
/*     */       {
/* 136 */         throw new JsonException(String.valueOf(s) + " is already defined");
/*     */       }
/*     */       
/* 139 */       addFramebuffer(s, i, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parsePass(TextureManager p_152764_1_, JsonElement p_152764_2_) throws JsonException, IOException {
/* 145 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_152764_2_, "pass");
/* 146 */     String s = JsonUtils.getString(jsonobject, "name");
/* 147 */     String s1 = JsonUtils.getString(jsonobject, "intarget");
/* 148 */     String s2 = JsonUtils.getString(jsonobject, "outtarget");
/* 149 */     Framebuffer framebuffer = getFramebuffer(s1);
/* 150 */     Framebuffer framebuffer1 = getFramebuffer(s2);
/*     */     
/* 152 */     if (framebuffer == null)
/*     */     {
/* 154 */       throw new JsonException("Input target '" + s1 + "' does not exist");
/*     */     }
/* 156 */     if (framebuffer1 == null)
/*     */     {
/* 158 */       throw new JsonException("Output target '" + s2 + "' does not exist");
/*     */     }
/*     */ 
/*     */     
/* 162 */     Shader shader = addShader(s, framebuffer, framebuffer1);
/* 163 */     JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "auxtargets", null);
/*     */     
/* 165 */     if (jsonarray != null) {
/*     */       
/* 167 */       int i = 0;
/*     */       
/* 169 */       for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */         
/*     */         try {
/* 173 */           JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonelement, "auxtarget");
/* 174 */           String s4 = JsonUtils.getString(jsonobject1, "name");
/* 175 */           String s3 = JsonUtils.getString(jsonobject1, "id");
/* 176 */           Framebuffer framebuffer2 = getFramebuffer(s3);
/*     */           
/* 178 */           if (framebuffer2 == null)
/*     */           {
/* 180 */             ResourceLocation resourcelocation = new ResourceLocation("textures/effect/" + s3 + ".png");
/*     */ 
/*     */             
/*     */             try {
/* 184 */               this.resourceManager.getResource(resourcelocation);
/*     */             }
/* 186 */             catch (FileNotFoundException var24) {
/*     */               
/* 188 */               throw new JsonException("Render target or texture '" + s3 + "' does not exist");
/*     */             } 
/*     */             
/* 191 */             p_152764_1_.bindTexture(resourcelocation);
/* 192 */             ITextureObject itextureobject = p_152764_1_.getTexture(resourcelocation);
/* 193 */             int j = JsonUtils.getInt(jsonobject1, "width");
/* 194 */             int k = JsonUtils.getInt(jsonobject1, "height");
/* 195 */             boolean flag = JsonUtils.getBoolean(jsonobject1, "bilinear");
/*     */             
/* 197 */             if (flag) {
/*     */               
/* 199 */               GL11.glTexParameteri(3553, 10241, 9729);
/* 200 */               GL11.glTexParameteri(3553, 10240, 9729);
/*     */             }
/*     */             else {
/*     */               
/* 204 */               GL11.glTexParameteri(3553, 10241, 9728);
/* 205 */               GL11.glTexParameteri(3553, 10240, 9728);
/*     */             } 
/*     */             
/* 208 */             shader.addAuxFramebuffer(s4, Integer.valueOf(itextureobject.getGlTextureId()), j, k);
/*     */           }
/*     */           else
/*     */           {
/* 212 */             shader.addAuxFramebuffer(s4, framebuffer2, framebuffer2.framebufferTextureWidth, framebuffer2.framebufferTextureHeight);
/*     */           }
/*     */         
/* 215 */         } catch (Exception exception1) {
/*     */           
/* 217 */           JsonException jsonexception = JsonException.func_151379_a(exception1);
/* 218 */           jsonexception.func_151380_a("auxtargets[" + i + "]");
/* 219 */           throw jsonexception;
/*     */         } 
/*     */         
/* 222 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonobject, "uniforms", null);
/*     */     
/* 228 */     if (jsonarray1 != null) {
/*     */       
/* 230 */       int l = 0;
/*     */       
/* 232 */       for (JsonElement jsonelement1 : jsonarray1) {
/*     */ 
/*     */         
/*     */         try {
/* 236 */           initUniform(jsonelement1);
/*     */         }
/* 238 */         catch (Exception exception) {
/*     */           
/* 240 */           JsonException jsonexception1 = JsonException.func_151379_a(exception);
/* 241 */           jsonexception1.func_151380_a("uniforms[" + l + "]");
/* 242 */           throw jsonexception1;
/*     */         } 
/*     */         
/* 245 */         l++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initUniform(JsonElement p_148028_1_) throws JsonException {
/* 253 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_148028_1_, "uniform");
/* 254 */     String s = JsonUtils.getString(jsonobject, "name");
/* 255 */     ShaderUniform shaderuniform = ((Shader)this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().getShaderUniform(s);
/*     */     
/* 257 */     if (shaderuniform == null)
/*     */     {
/* 259 */       throw new JsonException("Uniform '" + s + "' does not exist");
/*     */     }
/*     */ 
/*     */     
/* 263 */     float[] afloat = new float[4];
/* 264 */     int i = 0;
/*     */     
/* 266 */     for (JsonElement jsonelement : JsonUtils.getJsonArray(jsonobject, "values")) {
/*     */ 
/*     */       
/*     */       try {
/* 270 */         afloat[i] = JsonUtils.getFloat(jsonelement, "value");
/*     */       }
/* 272 */       catch (Exception exception) {
/*     */         
/* 274 */         JsonException jsonexception = JsonException.func_151379_a(exception);
/* 275 */         jsonexception.func_151380_a("values[" + i + "]");
/* 276 */         throw jsonexception;
/*     */       } 
/*     */       
/* 279 */       i++;
/*     */     } 
/*     */     
/* 282 */     switch (i) {
/*     */       default:
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 289 */         shaderuniform.set(afloat[0]);
/*     */ 
/*     */       
/*     */       case 2:
/* 293 */         shaderuniform.set(afloat[0], afloat[1]);
/*     */ 
/*     */       
/*     */       case 3:
/* 297 */         shaderuniform.set(afloat[0], afloat[1], afloat[2]);
/*     */       case 4:
/*     */         break;
/*     */     } 
/* 301 */     shaderuniform.set(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Framebuffer getFramebufferRaw(String p_177066_1_) {
/* 308 */     return this.mapFramebuffers.get(p_177066_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFramebuffer(String p_148020_1_, int p_148020_2_, int p_148020_3_) {
/* 313 */     Framebuffer framebuffer = new Framebuffer(p_148020_2_, p_148020_3_, true);
/* 314 */     framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 315 */     this.mapFramebuffers.put(p_148020_1_, framebuffer);
/*     */     
/* 317 */     if (p_148020_2_ == this.mainFramebufferWidth && p_148020_3_ == this.mainFramebufferHeight)
/*     */     {
/* 319 */       this.listFramebuffers.add(framebuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteShaderGroup() {
/* 325 */     for (Framebuffer framebuffer : this.mapFramebuffers.values())
/*     */     {
/* 327 */       framebuffer.deleteFramebuffer();
/*     */     }
/*     */     
/* 330 */     for (Shader shader : this.listShaders)
/*     */     {
/* 332 */       shader.deleteShader();
/*     */     }
/*     */     
/* 335 */     this.listShaders.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Shader addShader(String p_148023_1_, Framebuffer p_148023_2_, Framebuffer p_148023_3_) throws JsonException, IOException {
/* 340 */     Shader shader = new Shader(this.resourceManager, p_148023_1_, p_148023_2_, p_148023_3_);
/* 341 */     this.listShaders.add(this.listShaders.size(), shader);
/* 342 */     return shader;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetProjectionMatrix() {
/* 347 */     this.projectionMatrix = new Matrix4f();
/* 348 */     this.projectionMatrix.setIdentity();
/* 349 */     this.projectionMatrix.m00 = 2.0F / this.mainFramebuffer.framebufferTextureWidth;
/* 350 */     this.projectionMatrix.m11 = 2.0F / -this.mainFramebuffer.framebufferTextureHeight;
/* 351 */     this.projectionMatrix.m22 = -0.0020001999F;
/* 352 */     this.projectionMatrix.m33 = 1.0F;
/* 353 */     this.projectionMatrix.m03 = -1.0F;
/* 354 */     this.projectionMatrix.m13 = 1.0F;
/* 355 */     this.projectionMatrix.m23 = -1.0001999F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createBindFramebuffers(int width, int height) {
/* 360 */     this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
/* 361 */     this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
/* 362 */     resetProjectionMatrix();
/*     */     
/* 364 */     for (Shader shader : this.listShaders)
/*     */     {
/* 366 */       shader.setProjectionMatrix(this.projectionMatrix);
/*     */     }
/*     */     
/* 369 */     for (Framebuffer framebuffer : this.listFramebuffers)
/*     */     {
/* 371 */       framebuffer.createBindFramebuffer(width, height);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadShaderGroup(float partialTicks) {
/* 377 */     if (partialTicks < this.field_148037_k) {
/*     */       
/* 379 */       this.field_148036_j += 1.0F - this.field_148037_k;
/* 380 */       this.field_148036_j += partialTicks;
/*     */     }
/*     */     else {
/*     */       
/* 384 */       this.field_148036_j += partialTicks - this.field_148037_k;
/*     */     } 
/*     */     
/* 387 */     for (this.field_148037_k = partialTicks; this.field_148036_j > 20.0F; this.field_148036_j -= 20.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 392 */     for (Shader shader : this.listShaders)
/*     */     {
/* 394 */       shader.loadShader(this.field_148036_j / 20.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getShaderGroupName() {
/* 400 */     return this.shaderGroupName;
/*     */   }
/*     */ 
/*     */   
/*     */   private Framebuffer getFramebuffer(String p_148017_1_) {
/* 405 */     return (p_148017_1_ == null) ? null : (p_148017_1_.equals("minecraft:main") ? this.mainFramebuffer : this.mapFramebuffers.get(p_148017_1_));
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\shader\ShaderGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */