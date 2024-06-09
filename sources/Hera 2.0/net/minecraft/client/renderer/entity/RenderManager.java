/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.awt.Color;
/*     */ import java.util.Map;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.module.modules.combat.Killaura;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBed;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelChicken;
/*     */ import net.minecraft.client.model.ModelCow;
/*     */ import net.minecraft.client.model.ModelHorse;
/*     */ import net.minecraft.client.model.ModelOcelot;
/*     */ import net.minecraft.client.model.ModelPig;
/*     */ import net.minecraft.client.model.ModelRabbit;
/*     */ import net.minecraft.client.model.ModelSheep2;
/*     */ import net.minecraft.client.model.ModelSlime;
/*     */ import net.minecraft.client.model.ModelSquid;
/*     */ import net.minecraft.client.model.ModelWolf;
/*     */ import net.minecraft.client.model.ModelZombie;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*     */ import net.minecraft.client.renderer.tileentity.RenderItemFrame;
/*     */ import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityMinecartTNT;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityGiantZombie;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderManager
/*     */ {
/* 116 */   private Map<Class<? extends Entity>, Render<? extends Entity>> entityRenderMap = Maps.newHashMap();
/* 117 */   private Map<String, RenderPlayer> skinMap = Maps.newHashMap();
/*     */   
/*     */   private RenderPlayer playerRenderer;
/*     */   
/*     */   private FontRenderer textRenderer;
/*     */   
/*     */   public static double renderPosX;
/*     */   
/*     */   public static double renderPosY;
/*     */   
/*     */   public static double renderPosZ;
/*     */   
/*     */   public TextureManager renderEngine;
/*     */   
/*     */   public World worldObj;
/*     */   
/*     */   public Entity livingPlayer;
/*     */   
/*     */   public Entity pointedEntity;
/*     */   
/*     */   public float playerViewY;
/*     */   public float playerViewX;
/*     */   public GameSettings options;
/*     */   public double viewerPosX;
/*     */   public double viewerPosY;
/*     */   public double viewerPosZ;
/*     */   private boolean renderOutlines = false;
/*     */   private boolean renderShadow = true;
/*     */   private boolean debugBoundingBox = false;
/*     */   
/*     */   public RenderManager(TextureManager renderEngineIn, RenderItem itemRendererIn) {
/* 148 */     this.renderEngine = renderEngineIn;
/* 149 */     this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
/* 150 */     this.entityRenderMap.put(EntitySpider.class, new RenderSpider<Entity>(this));
/* 151 */     this.entityRenderMap.put(EntityPig.class, new RenderPig(this, (ModelBase)new ModelPig(), 0.7F));
/* 152 */     this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, (ModelBase)new ModelSheep2(), 0.7F));
/* 153 */     this.entityRenderMap.put(EntityCow.class, new RenderCow(this, (ModelBase)new ModelCow(), 0.7F));
/* 154 */     this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, (ModelBase)new ModelCow(), 0.7F));
/* 155 */     this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, (ModelBase)new ModelWolf(), 0.5F));
/* 156 */     this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this, (ModelBase)new ModelChicken(), 0.3F));
/* 157 */     this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, (ModelBase)new ModelOcelot(), 0.4F));
/* 158 */     this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, (ModelBase)new ModelRabbit(), 0.3F));
/* 159 */     this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
/* 160 */     this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
/* 161 */     this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
/* 162 */     this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
/* 163 */     this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
/* 164 */     this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
/* 165 */     this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
/* 166 */     this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
/* 167 */     this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
/* 168 */     this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
/* 169 */     this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this, (ModelBase)new ModelSlime(16), 0.25F));
/* 170 */     this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
/* 171 */     this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, (ModelBase)new ModelZombie(), 0.5F, 6.0F));
/* 172 */     this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
/* 173 */     this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this, (ModelBase)new ModelSquid(), 0.7F));
/* 174 */     this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
/* 175 */     this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
/* 176 */     this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
/* 177 */     this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
/* 178 */     this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
/* 179 */     this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
/* 180 */     this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
/* 181 */     this.entityRenderMap.put(Entity.class, new RenderEntity(this));
/* 182 */     this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
/* 183 */     this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, itemRendererIn));
/* 184 */     this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
/* 185 */     this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
/* 186 */     this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball<Entity>(this, Items.snowball, itemRendererIn));
/* 187 */     this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball<Entity>(this, Items.ender_pearl, itemRendererIn));
/* 188 */     this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball<Entity>(this, Items.ender_eye, itemRendererIn));
/* 189 */     this.entityRenderMap.put(EntityEgg.class, new RenderSnowball<Entity>(this, Items.egg, itemRendererIn));
/* 190 */     this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, itemRendererIn));
/* 191 */     this.entityRenderMap.put(EntityExpBottle.class, 
/* 192 */         new RenderSnowball<Entity>(this, Items.experience_bottle, itemRendererIn));
/* 193 */     this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball<Entity>(this, Items.fireworks, itemRendererIn));
/* 194 */     this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
/* 195 */     this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
/* 196 */     this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
/* 197 */     this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, itemRendererIn));
/* 198 */     this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
/* 199 */     this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
/* 200 */     this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
/* 201 */     this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
/* 202 */     this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
/* 203 */     this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
/* 204 */     this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart<Entity>(this));
/* 205 */     this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
/* 206 */     this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
/* 207 */     this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75F));
/* 208 */     this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
/* 209 */     this.playerRenderer = new RenderPlayer(this);
/* 210 */     this.skinMap.put("default", this.playerRenderer);
/* 211 */     this.skinMap.put("slim", new RenderPlayer(this, true));
/*     */   }
/*     */   
/*     */   public void setRenderPosition(double renderPosXIn, double renderPosYIn, double renderPosZIn) {
/* 215 */     renderPosX = renderPosXIn;
/* 216 */     renderPosY = renderPosYIn;
/* 217 */     renderPosZ = renderPosZIn;
/*     */   }
/*     */   
/*     */   public <T extends Entity> Render<T> getEntityClassRenderObject(Class<? extends Entity> p_78715_1_) {
/* 221 */     Render<? extends Entity> render = this.entityRenderMap.get(p_78715_1_);
/*     */     
/* 223 */     if (render == null && p_78715_1_ != Entity.class) {
/* 224 */       render = getEntityClassRenderObject((Class)p_78715_1_.getSuperclass());
/* 225 */       this.entityRenderMap.put(p_78715_1_, render);
/*     */     } 
/*     */     
/* 228 */     return (Render)render;
/*     */   }
/*     */   
/*     */   public <T extends Entity> Render<T> getEntityRenderObject(Entity entityIn) {
/* 232 */     if (entityIn instanceof AbstractClientPlayer) {
/* 233 */       String s = ((AbstractClientPlayer)entityIn).getSkinType();
/* 234 */       RenderPlayer renderplayer = this.skinMap.get(s);
/* 235 */       return (renderplayer != null) ? renderplayer : this.playerRenderer;
/*     */     } 
/* 237 */     return getEntityClassRenderObject((Class)entityIn.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cacheActiveRenderInfo(World worldIn, FontRenderer textRendererIn, Entity livingPlayerIn, Entity pointedEntityIn, GameSettings optionsIn, float partialTicks) {
/* 243 */     this.worldObj = worldIn;
/* 244 */     this.options = optionsIn;
/* 245 */     this.livingPlayer = livingPlayerIn;
/* 246 */     this.pointedEntity = pointedEntityIn;
/* 247 */     this.textRenderer = textRendererIn;
/*     */     
/* 249 */     if (livingPlayerIn instanceof EntityLivingBase && ((EntityLivingBase)livingPlayerIn).isPlayerSleeping()) {
/* 250 */       IBlockState iblockstate = worldIn.getBlockState(new BlockPos(livingPlayerIn));
/* 251 */       Block block = iblockstate.getBlock();
/*     */       
/* 253 */       if (block == Blocks.bed) {
/* 254 */         int i = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/* 255 */         this.playerViewY = (i * 90 + 180);
/* 256 */         this.playerViewX = 0.0F;
/*     */       } 
/*     */     } else {
/* 259 */       this.playerViewY = livingPlayerIn.prevRotationYaw + (
/* 260 */         livingPlayerIn.rotationYaw - livingPlayerIn.prevRotationYaw) * partialTicks;
/* 261 */       this.playerViewX = livingPlayerIn.prevRotationPitch + (
/* 262 */         livingPlayerIn.rotationPitch - livingPlayerIn.prevRotationPitch) * partialTicks;
/*     */     } 
/*     */     
/* 265 */     if (optionsIn.thirdPersonView == 2) {
/* 266 */       this.playerViewY += 180.0F;
/*     */     }
/*     */     
/* 269 */     this.viewerPosX = livingPlayerIn.lastTickPosX + (
/* 270 */       livingPlayerIn.posX - livingPlayerIn.lastTickPosX) * partialTicks;
/* 271 */     this.viewerPosY = livingPlayerIn.lastTickPosY + (
/* 272 */       livingPlayerIn.posY - livingPlayerIn.lastTickPosY) * partialTicks;
/* 273 */     this.viewerPosZ = livingPlayerIn.lastTickPosZ + (
/* 274 */       livingPlayerIn.posZ - livingPlayerIn.lastTickPosZ) * partialTicks;
/*     */   }
/*     */   
/*     */   public void setPlayerViewY(float playerViewYIn) {
/* 278 */     this.playerViewY = playerViewYIn;
/*     */   }
/*     */   
/*     */   public boolean isRenderShadow() {
/* 282 */     return this.renderShadow;
/*     */   }
/*     */   
/*     */   public void setRenderShadow(boolean renderShadowIn) {
/* 286 */     this.renderShadow = renderShadowIn;
/*     */   }
/*     */   
/*     */   public void setDebugBoundingBox(boolean debugBoundingBoxIn) {
/* 290 */     this.debugBoundingBox = debugBoundingBoxIn;
/*     */   }
/*     */   
/*     */   public boolean isDebugBoundingBox() {
/* 294 */     return this.debugBoundingBox;
/*     */   }
/*     */   
/*     */   public boolean renderEntitySimple(Entity entityIn, float partialTicks) {
/* 298 */     return renderEntityStatic(entityIn, partialTicks, false);
/*     */   }
/*     */   
/*     */   public boolean shouldRender(Entity entityIn, ICamera camera, double camX, double camY, double camZ) {
/* 302 */     Render<Entity> render = getEntityRenderObject(entityIn);
/* 303 */     return (render != null && render.shouldRender(entityIn, camera, camX, camY, camZ));
/*     */   }
/*     */   
/*     */   public boolean renderEntityStatic(Entity entity, float partialTicks, boolean p_147936_3_) {
/* 307 */     if (entity.ticksExisted == 0) {
/* 308 */       entity.lastTickPosX = entity.posX;
/* 309 */       entity.lastTickPosY = entity.posY;
/* 310 */       entity.lastTickPosZ = entity.posZ;
/*     */     } 
/*     */     
/* 313 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 314 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 315 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 316 */     float f = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
/* 317 */     int i = entity.getBrightnessForRender(partialTicks);
/*     */     
/* 319 */     if (entity.isBurning()) {
/* 320 */       i = 15728880;
/*     */     }
/*     */     
/* 323 */     int j = i % 65536;
/* 324 */     int k = i / 65536;
/* 325 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 326 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 327 */     return doRenderEntity(entity, d0 - renderPosX, d1 - renderPosY, d2 - renderPosZ, f, 
/* 328 */         partialTicks, p_147936_3_);
/*     */   }
/*     */   
/*     */   public void renderWitherSkull(Entity entityIn, float partialTicks) {
/* 332 */     double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 333 */     double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 334 */     double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 335 */     Render<Entity> render = getEntityRenderObject(entityIn);
/*     */     
/* 337 */     if (render != null && this.renderEngine != null) {
/* 338 */       int i = entityIn.getBrightnessForRender(partialTicks);
/* 339 */       int j = i % 65536;
/* 340 */       int k = i / 65536;
/* 341 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 342 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 343 */       render.renderName(entityIn, d0 - renderPosX, d1 - renderPosY, d2 - renderPosZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderEntityWithPosYaw(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) {
/* 349 */     return doRenderEntity(entityIn, x, y, z, entityYaw, partialTicks, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doRenderEntity(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, boolean p_147939_10_) {
/* 354 */     Render<Entity> render = null;
/*     */     
/*     */     try {
/* 357 */       render = getEntityRenderObject(entity);
/*     */       
/* 359 */       if (render != null && this.renderEngine != null) {
/*     */         try {
/* 361 */           if (render instanceof RendererLivingEntity)
/*     */           {
/*     */ 
/*     */             
/* 365 */             ((RendererLivingEntity)render).setRenderOutlines(this.renderOutlines);
/*     */           }
/*     */           
/* 368 */           render.doRender(entity, x, y, z, entityYaw, partialTicks);
/* 369 */         } catch (Throwable throwable2) {
/* 370 */           throw new ReportedException(CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
/*     */         } 
/*     */         
/*     */         try {
/* 374 */           if (!this.renderOutlines) {
/* 375 */             render.doRenderShadowAndFire(entity, x, y, z, entityYaw, partialTicks);
/*     */           }
/* 377 */         } catch (Throwable throwable1) {
/* 378 */           throw new ReportedException(
/* 379 */               CrashReport.makeCrashReport(throwable1, "Post-rendering entity in world"));
/*     */         } 
/*     */         
/* 382 */         if (this.debugBoundingBox && !entity.isInvisible() && !p_147939_10_) {
/*     */           try {
/* 384 */             renderDebugBoundingBox(entity, x, y, z, entityYaw, partialTicks);
/* 385 */           } catch (Throwable throwable) {
/* 386 */             throw new ReportedException(
/* 387 */                 CrashReport.makeCrashReport(throwable, "Rendering entity hitbox in world"));
/*     */           } 
/*     */         }
/*     */         
/* 391 */         if (entity instanceof EntityPlayer)
/*     */         {
/* 393 */           if (entity != (Minecraft.getMinecraft()).thePlayer && !entity.isInvisibleToPlayer((EntityPlayer)(Minecraft.getMinecraft()).thePlayer))
/*     */           {
/* 395 */             if (Client.instance.getModuleManager().getModuleByName("PlayerESP").isEnabled()) {
/*     */               
/* 397 */               Color c = new Color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), 30);
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
/* 409 */               Client.instance.getRenderHelper().renderBoxWithOutline(x, y, z, 1.0F, 2.0F, c);
/*     */             } 
/*     */ 
/*     */             
/* 413 */             if (Client.instance.getModuleManager().getModuleByName("DMGIndicator").isEnabled()) {
/*     */               
/* 415 */               String mode = Client.instance.getSettingManager().getSettingByName("DIMode").getMode();
/*     */               
/* 417 */               if (mode.equalsIgnoreCase("Side")) {
/*     */                 
/* 419 */                 Color color = new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 50);
/*     */                 
/* 421 */                 Client.instance.getRenderHelper().renderHealthBox((EntityPlayer)entity, x, y, z, 0.3F, 2.0F, color);
/*     */               }
/* 423 */               else if (mode.equalsIgnoreCase("Center")) {
/*     */                 
/* 425 */                 Color color = new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 50);
/*     */                 
/* 427 */                 float h = 0.089999996F;
/*     */                 
/* 429 */                 Client.instance.getRenderHelper().renderBox(x, y, z, 0.8F, h * ((EntityPlayer)entity).getHealth(), color);
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 435 */             if (Client.instance.getModuleManager().getModuleByName("AuraESP").isEnabled())
/*     */             {
/* 437 */               if (Client.instance.getModuleManager().getModuleByName("Killaura").isEnabled())
/*     */               {
/* 439 */                 EntityLivingBase attackedEntity = Killaura.attackedEntity;
/*     */                 
/* 441 */                 if (attackedEntity != null)
/*     */                 {
/* 443 */                   if (entity == attackedEntity)
/*     */                   {
/* 445 */                     float width = (float)Client.instance.getSettingManager().getSettingByName("Width").getValue();
/* 446 */                     float height = (float)Client.instance.getSettingManager().getSettingByName("Height").getValue();
/*     */                     
/* 448 */                     Color c = new Color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), 30);
/*     */                     
/* 450 */                     if (attackedEntity.hurtTime >= 0.5D)
/*     */                     {
/* 452 */                       c = new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 30);
/*     */                     }
/*     */ 
/*     */                     
/* 456 */                     Client.instance.getRenderHelper().renderBoxWithOutline(x, y + 1.0D + 0.10000000149011612D + (height / 2.0F), z, width, height, c);
/*     */                   
/*     */                   }
/*     */ 
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/*     */             
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       }
/* 470 */       else if (this.renderEngine != null) {
/* 471 */         return false;
/*     */       } 
/*     */       
/* 474 */       return true;
/* 475 */     } catch (Throwable throwable3) {
/* 476 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable3, "Rendering entity in world");
/* 477 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being rendered");
/* 478 */       entity.addEntityCrashInfo(crashreportcategory);
/* 479 */       CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Renderer details");
/* 480 */       crashreportcategory1.addCrashSection("Assigned renderer", render);
/* 481 */       crashreportcategory1.addCrashSection("Location", CrashReportCategory.getCoordinateInfo(x, y, z));
/* 482 */       crashreportcategory1.addCrashSection("Rotation", Float.valueOf(entityYaw));
/* 483 */       crashreportcategory1.addCrashSection("Delta", Float.valueOf(partialTicks));
/* 484 */       throw new ReportedException(crashreport);
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
/*     */   private void renderDebugBoundingBox(Entity entityIn, double p_85094_2_, double p_85094_4_, double p_85094_6_, float p_85094_8_, float p_85094_9_) {
/* 496 */     GlStateManager.depthMask(false);
/* 497 */     GlStateManager.disableTexture2D();
/* 498 */     GlStateManager.disableLighting();
/* 499 */     GlStateManager.disableCull();
/* 500 */     GlStateManager.disableBlend();
/* 501 */     float f = entityIn.width / 2.0F;
/* 502 */     AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
/* 503 */     AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX - entityIn.posX + p_85094_2_, 
/* 504 */         axisalignedbb.minY - entityIn.posY + p_85094_4_, axisalignedbb.minZ - entityIn.posZ + p_85094_6_, 
/* 505 */         axisalignedbb.maxX - entityIn.posX + p_85094_2_, axisalignedbb.maxY - entityIn.posY + p_85094_4_, 
/* 506 */         axisalignedbb.maxZ - entityIn.posZ + p_85094_6_);
/* 507 */     RenderGlobal.func_181563_a(axisalignedbb1, 255, 255, 255, 255);
/*     */     
/* 509 */     if (entityIn instanceof EntityLivingBase) {
/* 510 */       float f1 = 0.01F;
/* 511 */       RenderGlobal.func_181563_a(new AxisAlignedBB(p_85094_2_ - f, 
/* 512 */             p_85094_4_ + entityIn.getEyeHeight() - 0.009999999776482582D, p_85094_6_ - f, 
/* 513 */             p_85094_2_ + f, p_85094_4_ + entityIn.getEyeHeight() + 0.009999999776482582D, 
/* 514 */             p_85094_6_ + f), 255, 0, 0, 255);
/*     */     } 
/*     */     
/* 517 */     Tessellator tessellator = Tessellator.getInstance();
/* 518 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 519 */     Vec3 vec3 = entityIn.getLook(p_85094_9_);
/*     */     
/* 521 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 522 */     worldrenderer.pos(p_85094_2_, p_85094_4_ + entityIn.getEyeHeight(), p_85094_6_).color(0, 0, 255, 255)
/* 523 */       .endVertex();
/* 524 */     worldrenderer.pos(p_85094_2_ + vec3.xCoord * 2.0D, 
/* 525 */         p_85094_4_ + entityIn.getEyeHeight() + vec3.yCoord * 2.0D, p_85094_6_ + vec3.zCoord * 2.0D)
/* 526 */       .color(0, 0, 255, 255).endVertex();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 531 */     tessellator.draw();
/* 532 */     GlStateManager.enableTexture2D();
/* 533 */     GlStateManager.enableLighting();
/* 534 */     GlStateManager.enableCull();
/* 535 */     GlStateManager.disableBlend();
/* 536 */     GlStateManager.depthMask(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(World worldIn) {
/* 543 */     this.worldObj = worldIn;
/*     */   }
/*     */   
/*     */   public double getDistanceToCamera(double p_78714_1_, double p_78714_3_, double p_78714_5_) {
/* 547 */     double d0 = p_78714_1_ - this.viewerPosX;
/* 548 */     double d1 = p_78714_3_ - this.viewerPosY;
/* 549 */     double d2 = p_78714_5_ - this.viewerPosZ;
/* 550 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/* 557 */     return this.textRenderer;
/*     */   }
/*     */   
/*     */   public void setRenderOutlines(boolean renderOutlinesIn) {
/* 561 */     this.renderOutlines = renderOutlinesIn;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RenderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */