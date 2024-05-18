/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Render<T extends Entity>
/*     */ {
/*  27 */   private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
/*     */ 
/*     */   
/*     */   protected final RenderManager renderManager;
/*     */   
/*     */   protected float shadowSize;
/*     */   
/*  34 */   protected float shadowOpaque = 1.0F;
/*     */ 
/*     */   
/*     */   protected Render(RenderManager renderManager) {
/*  38 */     this.renderManager = renderManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  43 */     AxisAlignedBB axisalignedbb = livingEntity.getEntityBoundingBox();
/*     */     
/*  45 */     if (axisalignedbb.func_181656_b() || axisalignedbb.getAverageEdgeLength() == 0.0D)
/*     */     {
/*  47 */       axisalignedbb = new AxisAlignedBB(((Entity)livingEntity).posX - 2.0D, ((Entity)livingEntity).posY - 2.0D, ((Entity)livingEntity).posZ - 2.0D, ((Entity)livingEntity).posX + 2.0D, ((Entity)livingEntity).posY + 2.0D, ((Entity)livingEntity).posZ + 2.0D);
/*     */     }
/*     */     
/*  50 */     return (livingEntity.isInRangeToRender3d(camX, camY, camZ) && (((Entity)livingEntity).ignoreFrustumCheck || camera.isBoundingBoxInFrustum(axisalignedbb)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  61 */     renderName(entity, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderName(T entity, double x, double y, double z) {
/*  66 */     if (canRenderName(entity))
/*     */     {
/*  68 */       renderLivingLabel(entity, entity.getDisplayName().getFormattedText(), x, y, z, 64);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/* 120 */     return (entity.getAlwaysRenderNameTagForRender() && entity.hasCustomName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderOffsetLivingLabel(T entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
/* 125 */     renderLivingLabel(entityIn, str, x, y, z, 64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ResourceLocation getEntityTexture(T paramT);
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean bindEntityTexture(T entity) {
/* 135 */     ResourceLocation resourcelocation = getEntityTexture(entity);
/*     */     
/* 137 */     if (resourcelocation == null)
/*     */     {
/* 139 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 143 */     bindTexture(resourcelocation);
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindTexture(ResourceLocation location) {
/* 150 */     this.renderManager.renderEngine.bindTexture(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks) {
/* 158 */     GlStateManager.disableLighting();
/* 159 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/* 160 */     TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_0");
/* 161 */     TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 162 */     GlStateManager.pushMatrix();
/* 163 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 164 */     float f = entity.width * 1.4F;
/* 165 */     GlStateManager.scale(f, f, f);
/* 166 */     Tessellator tessellator = Tessellator.getInstance();
/* 167 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 168 */     float f1 = 0.5F;
/* 169 */     float f2 = 0.0F;
/* 170 */     float f3 = entity.height / f;
/* 171 */     float f4 = (float)(entity.posY - (entity.getEntityBoundingBox()).minY);
/* 172 */     GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 173 */     GlStateManager.translate(0.0F, 0.0F, -0.3F + (int)f3 * 0.02F);
/* 174 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 175 */     float f5 = 0.0F;
/* 176 */     int i = 0;
/* 177 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 179 */     while (f3 > 0.0F) {
/*     */       
/* 181 */       TextureAtlasSprite textureatlassprite2 = (i % 2 == 0) ? textureatlassprite : textureatlassprite1;
/* 182 */       bindTexture(TextureMap.locationBlocksTexture);
/* 183 */       float f6 = textureatlassprite2.getMinU();
/* 184 */       float f7 = textureatlassprite2.getMinV();
/* 185 */       float f8 = textureatlassprite2.getMaxU();
/* 186 */       float f9 = textureatlassprite2.getMaxV();
/*     */       
/* 188 */       if (i / 2 % 2 == 0) {
/*     */         
/* 190 */         float f10 = f8;
/* 191 */         f8 = f6;
/* 192 */         f6 = f10;
/*     */       } 
/*     */       
/* 195 */       worldrenderer.pos((f1 - f2), (0.0F - f4), f5).tex(f8, f9).endVertex();
/* 196 */       worldrenderer.pos((-f1 - f2), (0.0F - f4), f5).tex(f6, f9).endVertex();
/* 197 */       worldrenderer.pos((-f1 - f2), (1.4F - f4), f5).tex(f6, f7).endVertex();
/* 198 */       worldrenderer.pos((f1 - f2), (1.4F - f4), f5).tex(f8, f7).endVertex();
/* 199 */       f3 -= 0.45F;
/* 200 */       f4 -= 0.45F;
/* 201 */       f1 *= 0.9F;
/* 202 */       f5 += 0.03F;
/* 203 */       i++;
/*     */     } 
/*     */     
/* 206 */     tessellator.draw();
/* 207 */     GlStateManager.popMatrix();
/* 208 */     GlStateManager.enableLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderShadow(Entity entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 217 */     GlStateManager.enableBlend();
/* 218 */     GlStateManager.blendFunc(770, 771);
/* 219 */     this.renderManager.renderEngine.bindTexture(shadowTextures);
/* 220 */     World world = getWorldFromRenderManager();
/* 221 */     GlStateManager.depthMask(false);
/* 222 */     float f = this.shadowSize;
/*     */     
/* 224 */     if (entityIn instanceof EntityLiving) {
/*     */       
/* 226 */       EntityLiving entityliving = (EntityLiving)entityIn;
/* 227 */       f *= entityliving.getRenderSizeModifier();
/*     */       
/* 229 */       if (entityliving.isChild())
/*     */       {
/* 231 */         f *= 0.5F;
/*     */       }
/*     */     } 
/*     */     
/* 235 */     double d5 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 236 */     double d0 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 237 */     double d1 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 238 */     int i = MathHelper.floor_double(d5 - f);
/* 239 */     int j = MathHelper.floor_double(d5 + f);
/* 240 */     int k = MathHelper.floor_double(d0 - f);
/* 241 */     int l = MathHelper.floor_double(d0);
/* 242 */     int i1 = MathHelper.floor_double(d1 - f);
/* 243 */     int j1 = MathHelper.floor_double(d1 + f);
/* 244 */     double d2 = x - d5;
/* 245 */     double d3 = y - d0;
/* 246 */     double d4 = z - d1;
/* 247 */     Tessellator tessellator = Tessellator.getInstance();
/* 248 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 249 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*     */     
/* 251 */     for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(i, k, i1), new BlockPos(j, l, j1))) {
/*     */       
/* 253 */       Block block = world.getBlockState(blockpos.down()).getBlock();
/*     */       
/* 255 */       if (block.getRenderType() != -1 && world.getLightFromNeighbors(blockpos) > 3)
/*     */       {
/* 257 */         func_180549_a(block, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
/*     */       }
/*     */     } 
/*     */     
/* 261 */     tessellator.draw();
/* 262 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 263 */     GlStateManager.disableBlend();
/* 264 */     GlStateManager.depthMask(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private World getWorldFromRenderManager() {
/* 272 */     return this.renderManager.worldObj;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_180549_a(Block blockIn, double p_180549_2_, double p_180549_4_, double p_180549_6_, BlockPos pos, float p_180549_9_, float p_180549_10_, double p_180549_11_, double p_180549_13_, double p_180549_15_) {
/* 277 */     if (blockIn.isFullCube()) {
/*     */       
/* 279 */       Tessellator tessellator = Tessellator.getInstance();
/* 280 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 281 */       double d0 = (p_180549_9_ - (p_180549_4_ - pos.getY() + p_180549_13_) / 2.0D) * 0.5D * getWorldFromRenderManager().getLightBrightness(pos);
/*     */       
/* 283 */       if (d0 >= 0.0D) {
/*     */         
/* 285 */         if (d0 > 1.0D)
/*     */         {
/* 287 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 290 */         double d1 = pos.getX() + blockIn.getBlockBoundsMinX() + p_180549_11_;
/* 291 */         double d2 = pos.getX() + blockIn.getBlockBoundsMaxX() + p_180549_11_;
/* 292 */         double d3 = pos.getY() + blockIn.getBlockBoundsMinY() + p_180549_13_ + 0.015625D;
/* 293 */         double d4 = pos.getZ() + blockIn.getBlockBoundsMinZ() + p_180549_15_;
/* 294 */         double d5 = pos.getZ() + blockIn.getBlockBoundsMaxZ() + p_180549_15_;
/* 295 */         float f = (float)((p_180549_2_ - d1) / 2.0D / p_180549_10_ + 0.5D);
/* 296 */         float f1 = (float)((p_180549_2_ - d2) / 2.0D / p_180549_10_ + 0.5D);
/* 297 */         float f2 = (float)((p_180549_6_ - d4) / 2.0D / p_180549_10_ + 0.5D);
/* 298 */         float f3 = (float)((p_180549_6_ - d5) / 2.0D / p_180549_10_ + 0.5D);
/* 299 */         worldrenderer.pos(d1, d3, d4).tex(f, f2).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 300 */         worldrenderer.pos(d1, d3, d5).tex(f, f3).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 301 */         worldrenderer.pos(d2, d3, d5).tex(f1, f3).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 302 */         worldrenderer.pos(d2, d3, d4).tex(f1, f2).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderOffsetAABB(AxisAlignedBB boundingBox, double x, double y, double z) {
/* 312 */     GlStateManager.disableTexture2D();
/* 313 */     Tessellator tessellator = Tessellator.getInstance();
/* 314 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 315 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 316 */     worldrenderer.setTranslation(x, y, z);
/* 317 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_NORMAL);
/* 318 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 319 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 320 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 321 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 322 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 323 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 324 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 325 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 326 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 327 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 328 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 329 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 330 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 331 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 332 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 333 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 334 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 335 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 336 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 337 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 338 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 339 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 340 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 341 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 342 */     tessellator.draw();
/* 343 */     worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 344 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 352 */     if (this.renderManager.options != null) {
/*     */       
/* 354 */       if (this.renderManager.options.field_181151_V && this.shadowSize > 0.0F && !entityIn.isInvisible() && this.renderManager.isRenderShadow()) {
/*     */         
/* 356 */         double d0 = this.renderManager.getDistanceToCamera(entityIn.posX, entityIn.posY, entityIn.posZ);
/* 357 */         float f = (float)((1.0D - d0 / 256.0D) * this.shadowOpaque);
/*     */         
/* 359 */         if (f > 0.0F)
/*     */         {
/* 361 */           renderShadow(entityIn, x, y, z, f, partialTicks);
/*     */         }
/*     */       } 
/*     */       
/* 365 */       if (entityIn.canRenderOnFire() && (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).isSpectator()))
/*     */       {
/* 367 */         renderEntityOnFire(entityIn, x, y, z, partialTicks);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRendererFromRenderManager() {
/* 377 */     return this.renderManager.getFontRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance) {
/* 385 */     double d0 = entityIn.getDistanceSqToEntity(this.renderManager.livingPlayer);
/*     */     
/* 387 */     if (d0 <= (maxDistance * maxDistance)) {
/*     */       
/* 389 */       FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 390 */       float f = 1.6F;
/* 391 */       float f1 = 0.016666668F * f;
/* 392 */       GlStateManager.pushMatrix();
/* 393 */       GlStateManager.translate((float)x + 0.0F, (float)y + ((Entity)entityIn).height + 0.5F, (float)z);
/* 394 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*     */ 
/*     */ 
/*     */       
/* 398 */       GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 399 */       GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*     */       
/* 401 */       GlStateManager.scale(-f1, -f1, f1);
/* 402 */       GlStateManager.disableLighting();
/* 403 */       GlStateManager.depthMask(false);
/*     */ 
/*     */ 
/*     */       
/* 407 */       GlStateManager.disableDepth();
/*     */       
/* 409 */       GlStateManager.enableBlend();
/* 410 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 411 */       Tessellator tessellator = Tessellator.getInstance();
/* 412 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 413 */       int i = 0;
/*     */       
/* 415 */       if (str.equals("deadmau5"))
/*     */       {
/* 417 */         i = -10;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 422 */       int j = fontrenderer.getStringWidth(str) / 2;
/* 423 */       GlStateManager.disableTexture2D();
/* 424 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 425 */       worldrenderer.pos((-j - 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 426 */       worldrenderer.pos((-j - 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 427 */       worldrenderer.pos((j + 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 428 */       worldrenderer.pos((j + 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/*     */       
/* 430 */       tessellator.draw();
/* 431 */       GlStateManager.enableTexture2D();
/* 432 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
/* 433 */       GlStateManager.enableDepth();
/* 434 */       GlStateManager.depthMask(true);
/* 435 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
/* 436 */       GlStateManager.enableLighting();
/* 437 */       GlStateManager.disableBlend();
/* 438 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 439 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderManager getRenderManager() {
/* 445 */     return this.renderManager;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\Render.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */