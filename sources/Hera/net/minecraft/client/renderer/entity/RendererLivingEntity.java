/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public abstract class RendererLivingEntity<T extends EntityLivingBase> extends Render<T> {
/*  31 */   private static final Logger logger = LogManager.getLogger();
/*  32 */   private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);
/*     */   protected ModelBase mainModel;
/*  34 */   protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
/*  35 */   protected List<LayerRenderer<T>> layerRenderers = Lists.newArrayList();
/*     */   
/*     */   protected boolean renderOutlines = false;
/*     */   
/*     */   public RendererLivingEntity(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/*  40 */     super(renderManagerIn);
/*  41 */     this.mainModel = modelBaseIn;
/*  42 */     this.shadowSize = shadowSizeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layer) {
/*  47 */     return this.layerRenderers.add((LayerRenderer<T>)layer);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean removeLayer(U layer) {
/*  52 */     return this.layerRenderers.remove(layer);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBase getMainModel() {
/*  57 */     return this.mainModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float interpolateRotation(float par1, float par2, float par3) {
/*     */     float f;
/*  69 */     for (f = par2 - par1; f < -180.0F; f += 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     while (f >= 180.0F)
/*     */     {
/*  76 */       f -= 360.0F;
/*     */     }
/*     */     
/*  79 */     return par1 + par3 * f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformHeldFull3DItemLayer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  94 */     GlStateManager.pushMatrix();
/*  95 */     GlStateManager.disableCull();
/*  96 */     this.mainModel.swingProgress = getSwingProgress(entity, partialTicks);
/*  97 */     this.mainModel.isRiding = entity.isRiding();
/*  98 */     this.mainModel.isChild = entity.isChild();
/*     */ 
/*     */     
/*     */     try {
/* 102 */       float f = interpolateRotation(((EntityLivingBase)entity).prevRenderYawOffset, ((EntityLivingBase)entity).renderYawOffset, partialTicks);
/* 103 */       float f1 = interpolateRotation(((EntityLivingBase)entity).prevRotationYawHead, ((EntityLivingBase)entity).rotationYawHead, partialTicks);
/* 104 */       float f2 = f1 - f;
/*     */       
/* 106 */       if (entity.isRiding() && ((EntityLivingBase)entity).ridingEntity instanceof EntityLivingBase) {
/*     */         
/* 108 */         EntityLivingBase entitylivingbase = (EntityLivingBase)((EntityLivingBase)entity).ridingEntity;
/* 109 */         f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
/* 110 */         f2 = f1 - f;
/* 111 */         float f3 = MathHelper.wrapAngleTo180_float(f2);
/*     */         
/* 113 */         if (f3 < -85.0F)
/*     */         {
/* 115 */           f3 = -85.0F;
/*     */         }
/*     */         
/* 118 */         if (f3 >= 85.0F)
/*     */         {
/* 120 */           f3 = 85.0F;
/*     */         }
/*     */         
/* 123 */         f = f1 - f3;
/*     */         
/* 125 */         if (f3 * f3 > 2500.0F)
/*     */         {
/* 127 */           f += f3 * 0.2F;
/*     */         }
/*     */       } 
/*     */       
/* 131 */       float f7 = ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
/* 132 */       renderLivingAt(entity, x, y, z);
/* 133 */       float f8 = handleRotationFloat(entity, partialTicks);
/* 134 */       rotateCorpse(entity, f8, f, partialTicks);
/* 135 */       GlStateManager.enableRescaleNormal();
/* 136 */       GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 137 */       preRenderCallback(entity, partialTicks);
/* 138 */       float f4 = 0.0625F;
/* 139 */       GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
/* 140 */       float f5 = ((EntityLivingBase)entity).prevLimbSwingAmount + (((EntityLivingBase)entity).limbSwingAmount - ((EntityLivingBase)entity).prevLimbSwingAmount) * partialTicks;
/* 141 */       float f6 = ((EntityLivingBase)entity).limbSwing - ((EntityLivingBase)entity).limbSwingAmount * (1.0F - partialTicks);
/*     */       
/* 143 */       if (entity.isChild())
/*     */       {
/* 145 */         f6 *= 3.0F;
/*     */       }
/*     */       
/* 148 */       if (f5 > 1.0F)
/*     */       {
/* 150 */         f5 = 1.0F;
/*     */       }
/*     */       
/* 153 */       GlStateManager.enableAlpha();
/* 154 */       this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
/* 155 */       this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, 0.0625F, (Entity)entity);
/*     */       
/* 157 */       if (this.renderOutlines) {
/*     */         
/* 159 */         boolean flag1 = setScoreTeamColor(entity);
/*     */ 
/*     */ 
/*     */         
/* 163 */         renderModel(entity, f6, f5, f8, f2, f7, 0.0625F);
/*     */         
/* 165 */         if (flag1)
/*     */         {
/* 167 */           unsetScoreTeamColor();
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 172 */         boolean flag = setDoRenderBrightness(entity, partialTicks);
/* 173 */         renderModel(entity, f6, f5, f8, f2, f7, 0.0625F);
/*     */         
/* 175 */         if (flag)
/*     */         {
/* 177 */           unsetBrightness();
/*     */         }
/*     */         
/* 180 */         GlStateManager.depthMask(true);
/*     */         
/* 182 */         if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())
/*     */         {
/* 184 */           renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, 0.0625F);
/*     */         }
/*     */       } 
/*     */       
/* 188 */       GlStateManager.disableRescaleNormal();
/*     */     }
/* 190 */     catch (Exception exception) {
/*     */       
/* 192 */       logger.error("Couldn't render entity", exception);
/*     */     } 
/*     */     
/* 195 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 196 */     GlStateManager.enableTexture2D();
/* 197 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 198 */     GlStateManager.enableCull();
/* 199 */     GlStateManager.popMatrix();
/*     */     
/* 201 */     if (!this.renderOutlines)
/*     */     {
/* 203 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setScoreTeamColor(T entityLivingBaseIn) {
/* 209 */     int i = 16777215;
/*     */     
/* 211 */     if (entityLivingBaseIn instanceof EntityPlayer) {
/*     */       
/* 213 */       ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)entityLivingBaseIn.getTeam();
/*     */       
/* 215 */       if (scoreplayerteam != null) {
/*     */         
/* 217 */         String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());
/*     */         
/* 219 */         if (s.length() >= 2)
/*     */         {
/* 221 */           i = getFontRendererFromRenderManager().getColorCode(s.charAt(1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     float f1 = (i >> 16 & 0xFF) / 255.0F;
/* 227 */     float f2 = (i >> 8 & 0xFF) / 255.0F;
/* 228 */     float f = (i & 0xFF) / 255.0F;
/* 229 */     GlStateManager.disableLighting();
/* 230 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 231 */     GlStateManager.color(f1, f2, f, 1.0F);
/* 232 */     GlStateManager.disableTexture2D();
/* 233 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 234 */     GlStateManager.disableTexture2D();
/* 235 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 236 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void unsetScoreTeamColor() {
/* 241 */     GlStateManager.enableLighting();
/* 242 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 243 */     GlStateManager.enableTexture2D();
/* 244 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 245 */     GlStateManager.enableTexture2D();
/* 246 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
/* 254 */     boolean flag = !entitylivingbaseIn.isInvisible();
/* 255 */     boolean flag1 = (!flag && !entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/*     */     
/* 257 */     if (flag || flag1) {
/*     */       
/* 259 */       if (!bindEntityTexture(entitylivingbaseIn)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 264 */       if (flag1) {
/*     */         
/* 266 */         GlStateManager.pushMatrix();
/* 267 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
/* 268 */         GlStateManager.depthMask(false);
/* 269 */         GlStateManager.enableBlend();
/* 270 */         GlStateManager.blendFunc(770, 771);
/* 271 */         GlStateManager.alphaFunc(516, 0.003921569F);
/*     */       } 
/*     */       
/* 274 */       this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
/*     */       
/* 276 */       if (flag1) {
/*     */         
/* 278 */         GlStateManager.disableBlend();
/* 279 */         GlStateManager.alphaFunc(516, 0.1F);
/* 280 */         GlStateManager.popMatrix();
/* 281 */         GlStateManager.depthMask(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks) {
/* 288 */     return setBrightness(entityLivingBaseIn, partialTicks, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures) {
/* 293 */     float f = entitylivingbaseIn.getBrightness(partialTicks);
/* 294 */     int i = getColorMultiplier(entitylivingbaseIn, f, partialTicks);
/* 295 */     boolean flag = ((i >> 24 & 0xFF) > 0);
/* 296 */     boolean flag1 = !(((EntityLivingBase)entitylivingbaseIn).hurtTime <= 0 && ((EntityLivingBase)entitylivingbaseIn).deathTime <= 0);
/*     */     
/* 298 */     if (!flag && !flag1)
/*     */     {
/* 300 */       return false;
/*     */     }
/* 302 */     if (!flag && !combineTextures)
/*     */     {
/* 304 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 308 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 309 */     GlStateManager.enableTexture2D();
/* 310 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 311 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 312 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 313 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 314 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 315 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 316 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 317 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 318 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 319 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 320 */     GlStateManager.enableTexture2D();
/* 321 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 322 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
/* 323 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
/* 324 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 325 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
/* 326 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 327 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 328 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
/* 329 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 330 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 331 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 332 */     this.brightnessBuffer.position(0);
/*     */     
/* 334 */     if (flag1) {
/*     */       
/* 336 */       this.brightnessBuffer.put(1.0F);
/* 337 */       this.brightnessBuffer.put(0.0F);
/* 338 */       this.brightnessBuffer.put(0.0F);
/* 339 */       this.brightnessBuffer.put(0.3F);
/*     */     }
/*     */     else {
/*     */       
/* 343 */       float f1 = (i >> 24 & 0xFF) / 255.0F;
/* 344 */       float f2 = (i >> 16 & 0xFF) / 255.0F;
/* 345 */       float f3 = (i >> 8 & 0xFF) / 255.0F;
/* 346 */       float f4 = (i & 0xFF) / 255.0F;
/* 347 */       this.brightnessBuffer.put(f2);
/* 348 */       this.brightnessBuffer.put(f3);
/* 349 */       this.brightnessBuffer.put(f4);
/* 350 */       this.brightnessBuffer.put(1.0F - f1);
/*     */     } 
/*     */     
/* 353 */     this.brightnessBuffer.flip();
/* 354 */     GL11.glTexEnv(8960, 8705, this.brightnessBuffer);
/* 355 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 356 */     GlStateManager.enableTexture2D();
/* 357 */     GlStateManager.bindTexture(field_177096_e.getGlTextureId());
/* 358 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 359 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 360 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
/* 361 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
/* 362 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 363 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 364 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 365 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 366 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 367 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 368 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unsetBrightness() {
/* 374 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 375 */     GlStateManager.enableTexture2D();
/* 376 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 377 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 378 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 379 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 380 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 381 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 382 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 383 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 384 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
/* 385 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 386 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
/* 387 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 388 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 389 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 390 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 391 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 392 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 393 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 394 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 395 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 396 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 397 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 398 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 399 */     GlStateManager.disableTexture2D();
/* 400 */     GlStateManager.bindTexture(0);
/* 401 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 402 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 403 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 404 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 405 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 406 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 407 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 408 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 409 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 410 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z) {
/* 418 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 423 */     GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
/*     */     
/* 425 */     if (((EntityLivingBase)bat).deathTime > 0) {
/*     */       
/* 427 */       float f = (((EntityLivingBase)bat).deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
/* 428 */       f = MathHelper.sqrt_float(f);
/*     */       
/* 430 */       if (f > 1.0F)
/*     */       {
/* 432 */         f = 1.0F;
/*     */       }
/*     */       
/* 435 */       GlStateManager.rotate(f * getDeathMaxRotation(bat), 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 439 */       String s = EnumChatFormatting.getTextWithoutFormattingCodes(bat.getName());
/*     */       
/* 441 */       if (s != null && (s.equals("Dinnerbone") || s.equals("Grumm")) && (!(bat instanceof EntityPlayer) || ((EntityPlayer)bat).isWearing(EnumPlayerModelParts.CAPE))) {
/*     */         
/* 443 */         GlStateManager.translate(0.0F, ((EntityLivingBase)bat).height + 0.1F, 0.0F);
/* 444 */         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSwingProgress(T livingBase, float partialTickTime) {
/* 454 */     return livingBase.getSwingProgress(partialTickTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float handleRotationFloat(T livingBase, float partialTicks) {
/* 462 */     return ((EntityLivingBase)livingBase).ticksExisted + partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLayers(T entitylivingbaseIn, float p_177093_2_, float p_177093_3_, float partialTicks, float p_177093_5_, float p_177093_6_, float p_177093_7_, float p_177093_8_) {
/* 467 */     for (LayerRenderer<T> layerrenderer : this.layerRenderers) {
/*     */       
/* 469 */       boolean flag = setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
/* 470 */       layerrenderer.doRenderLayer((EntityLivingBase)entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
/*     */       
/* 472 */       if (flag)
/*     */       {
/* 474 */         unsetBrightness();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getDeathMaxRotation(T entityLivingBaseIn) {
/* 481 */     return 90.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getColorMultiplier(T entitylivingbaseIn, float lightBrightness, float partialTickTime) {
/* 489 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderName(T entity, double x, double y, double z) {
/* 502 */     if (canRenderName(entity)) {
/*     */       
/* 504 */       double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 505 */       float f = entity.isSneaking() ? 32.0F : 64.0F;
/*     */       
/* 507 */       if (d0 < (f * f)) {
/*     */         
/* 509 */         String s = entity.getDisplayName().getFormattedText();
/* 510 */         float f1 = 0.02666667F;
/* 511 */         GlStateManager.alphaFunc(516, 0.1F);
/*     */         
/* 513 */         if (entity.isSneaking()) {
/*     */           
/* 515 */           FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 516 */           GlStateManager.pushMatrix();
/* 517 */           GlStateManager.translate((float)x, (float)y + ((EntityLivingBase)entity).height + 0.5F - (entity.isChild() ? (((EntityLivingBase)entity).height / 2.0F) : 0.0F), (float)z);
/* 518 */           GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 519 */           GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 520 */           GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 521 */           GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
/* 522 */           GlStateManager.translate(0.0F, 9.374999F, 0.0F);
/* 523 */           GlStateManager.disableLighting();
/* 524 */           GlStateManager.depthMask(false);
/* 525 */           GlStateManager.enableBlend();
/* 526 */           GlStateManager.disableTexture2D();
/* 527 */           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 528 */           int i = fontrenderer.getStringWidth(s) / 2;
/* 529 */           Tessellator tessellator = Tessellator.getInstance();
/* 530 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 531 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 532 */           worldrenderer.pos((-i - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 533 */           worldrenderer.pos((-i - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 534 */           worldrenderer.pos((i + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 535 */           worldrenderer.pos((i + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 536 */           tessellator.draw();
/* 537 */           GlStateManager.enableTexture2D();
/* 538 */           GlStateManager.depthMask(true);
/* 539 */           fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
/* 540 */           GlStateManager.enableLighting();
/* 541 */           GlStateManager.disableBlend();
/* 542 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 543 */           GlStateManager.popMatrix();
/*     */         }
/*     */         else {
/*     */           
/* 547 */           renderOffsetLivingLabel(entity, x, y - (entity.isChild() ? (((EntityLivingBase)entity).height / 2.0F) : 0.0D), z, s, 0.02666667F, d0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/* 555 */     EntityPlayerSP entityplayersp = (Minecraft.getMinecraft()).thePlayer;
/*     */     
/* 557 */     if (entity instanceof EntityPlayer && entity != entityplayersp) {
/*     */       
/* 559 */       Team team = entity.getTeam();
/* 560 */       Team team1 = entityplayersp.getTeam();
/*     */       
/* 562 */       if (team != null) {
/*     */         
/* 564 */         Team.EnumVisible team$enumvisible = team.getNameTagVisibility();
/*     */         
/* 566 */         switch (team$enumvisible) {
/*     */           
/*     */           case null:
/* 569 */             return true;
/*     */           
/*     */           case NEVER:
/* 572 */             return false;
/*     */           
/*     */           case HIDE_FOR_OTHER_TEAMS:
/* 575 */             return !(team1 != null && !team.isSameTeam(team1));
/*     */           
/*     */           case HIDE_FOR_OWN_TEAM:
/* 578 */             return !(team1 != null && team.isSameTeam(team1));
/*     */         } 
/*     */         
/* 581 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 586 */     return (Minecraft.isGuiEnabled() && entity != this.renderManager.livingPlayer && !entity.isInvisibleToPlayer((EntityPlayer)entityplayersp) && ((EntityLivingBase)entity).riddenByEntity == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderOutlines(boolean renderOutlinesIn) {
/* 591 */     this.renderOutlines = renderOutlinesIn;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 596 */     int[] aint = field_177096_e.getTextureData();
/*     */     
/* 598 */     for (int i = 0; i < 256; i++)
/*     */     {
/* 600 */       aint[i] = -1;
/*     */     }
/*     */     
/* 603 */     field_177096_e.updateDynamicTexture();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RendererLivingEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */