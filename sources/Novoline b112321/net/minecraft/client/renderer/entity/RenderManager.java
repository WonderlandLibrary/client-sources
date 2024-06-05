package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import net.asJ;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderBat;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderCaveSpider;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderEndermite;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderGiantZombie;
import net.minecraft.client.renderer.entity.RenderGuardian;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderLeashKnot;
import net.minecraft.client.renderer.entity.RenderLightningBolt;
import net.minecraft.client.renderer.entity.RenderMagmaCube;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.entity.RenderMinecartMobSpawner;
import net.minecraft.client.renderer.entity.RenderMooshroom;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.client.renderer.entity.RenderPainting;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.client.renderer.entity.RenderPigZombie;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderPotion;
import net.minecraft.client.renderer.entity.RenderRabbit;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.RenderSilverfish;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.client.renderer.entity.RenderTntMinecart;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.optifine.PlayerItemsLayer;
import net.optifine.Reflector;

public class RenderManager {
   public Map entityRenderMap = Maps.newHashMap();
   private Map skinMap = Maps.newHashMap();
   public RenderPlayer playerRenderer;
   private FontRenderer textRenderer;
   public double renderPosX;
   public double renderPosY;
   public double renderPosZ;
   public TextureManager renderEngine;
   public World worldObj;
   public Entity livingPlayer;
   public Entity pointedEntity;
   public float playerViewY;
   public float playerViewX;
   public GameSettings options;
   public double viewerPosX;
   public double viewerPosY;
   public double viewerPosZ;
   private boolean renderOutlines = false;
   private boolean renderShadow = true;
   private boolean debugBoundingBox = false;
   private static final String t = "CL_00000991";

   public RenderManager(TextureManager var1, RenderItem var2) {
      this.renderEngine = var1;
      this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
      this.entityRenderMap.put(EntitySpider.class, new RenderSpider(this));
      this.entityRenderMap.put(EntityPig.class, new RenderPig(this, new ModelPig(), 0.7F));
      this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, new ModelSheep2(), 0.7F));
      this.entityRenderMap.put(EntityCow.class, new RenderCow(this, new ModelCow(), 0.7F));
      this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, new ModelCow(), 0.7F));
      this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, new ModelWolf(), 0.5F));
      this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this, new ModelChicken(), 0.3F));
      this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, new ModelOcelot(), 0.4F));
      this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, new ModelRabbit(), 0.3F));
      this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
      this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
      this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
      this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
      this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
      this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
      this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
      this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
      this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
      this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
      this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this, new ModelSlime(16), 0.25F));
      this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
      this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, new ModelZombie(), 0.5F, 6.0F));
      this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
      this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this, new ModelSquid(), 0.7F));
      this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
      this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
      this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
      this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
      this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
      this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
      this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
      this.entityRenderMap.put(Entity.class, new RenderEntity(this));
      this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
      this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, var2));
      this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
      this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
      this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(this, Items.snowball, var2));
      this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(this, Items.ender_pearl, var2));
      this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(this, Items.ender_eye, var2));
      this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(this, Items.egg, var2));
      this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, var2));
      this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(this, Items.experience_bottle, var2));
      this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(this, Items.fireworks, var2));
      this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
      this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
      this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
      this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, var2));
      this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
      this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
      this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
      this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
      this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
      this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
      this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart(this));
      this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
      this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
      this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75F));
      this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
      this.playerRenderer = new RenderPlayer(this);
      this.skinMap.put("default", this.playerRenderer);
      this.skinMap.put("slim", new RenderPlayer(this, true));
      PlayerItemsLayer.register(this.skinMap);
      if(Reflector.N.d()) {
         Reflector.f(Reflector.N, new Object[]{this.entityRenderMap});
      }

   }

   public void setRenderPosition(double var1, double var3, double var5) {
      this.renderPosX = var1;
      this.renderPosY = var3;
      this.renderPosZ = var5;
   }

   public Render getEntityClassRenderObject(Class var1) {
      Render var2 = (Render)this.entityRenderMap.get(var1);
      if(var1 != Entity.class) {
         var2 = this.getEntityClassRenderObject(var1.getSuperclass());
         this.entityRenderMap.put(var1, var2);
      }

      return var2;
   }

   public Render getEntityRenderObject(Entity var1) {
      if(var1 instanceof asJ) {
         String var2 = ((asJ)var1).getSkinType();
         RenderPlayer var3 = (RenderPlayer)this.skinMap.get(var2);
         return var3;
      } else {
         return this.getEntityClassRenderObject(var1.getClass());
      }
   }

   public void cacheActiveRenderInfo(World var1, FontRenderer var2, Entity var3, Entity var4, GameSettings var5, float var6) {
      this.worldObj = var1;
      this.options = var5;
      this.livingPlayer = var3;
      this.pointedEntity = var4;
      this.textRenderer = var2;
      if(var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPlayerSleeping()) {
         IBlockState var7 = var1.getBlockState(new BlockPos(var3));
         Block var8 = var7.getBlock();
         if(Reflector.b(Reflector.df, new Object[]{var1, new BlockPos(var3), var3})) {
            EnumFacing var9 = (EnumFacing)Reflector.f(var8, Reflector.t, new Object[]{var1, new BlockPos(var3)});
            int var10 = var9.getHorizontalIndex();
            this.playerViewY = (float)(var10 * 90 + 180);
            this.playerViewX = 0.0F;
         } else if(var8 == Blocks.bed) {
            int var11 = ((EnumFacing)var7.getValue(BlockBed.FACING)).getHorizontalIndex();
            this.playerViewY = (float)(var11 * 90 + 180);
            this.playerViewX = 0.0F;
         }
      } else {
         this.playerViewY = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * var6;
         this.playerViewX = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * var6;
      }

      if(var5.thirdPersonView == 2) {
         this.playerViewY += 180.0F;
      }

      this.viewerPosX = var3.lastTickPosX + (var3.posX - var3.lastTickPosX) * (double)var6;
      this.viewerPosY = var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)var6;
      this.viewerPosZ = var3.lastTickPosZ + (var3.posZ - var3.lastTickPosZ) * (double)var6;
   }

   public void setPlayerViewY(float var1) {
      this.playerViewY = var1;
   }

   public boolean isRenderShadow() {
      return this.renderShadow;
   }

   public void setRenderShadow(boolean var1) {
      this.renderShadow = var1;
   }

   public boolean isDebugBoundingBox() {
      return this.debugBoundingBox;
   }

   public void setDebugBoundingBox(boolean var1) {
      this.debugBoundingBox = var1;
   }

   public void b(Entity var1, float var2) {
      this.a(var1, var2, false);
   }

   public boolean shouldRender(Entity var1, ICamera var2, double var3, double var5, double var7) {
      Render var9 = this.getEntityRenderObject(var1);
      return var9.shouldRender(var1, var2, var3, var5, var7);
   }

   public void a(Entity var1, float var2, boolean var3) {
      if(var1.ticksExisted == 0) {
         var1.lastTickPosX = var1.posX;
         var1.lastTickPosY = var1.posY;
         var1.lastTickPosZ = var1.posZ;
      }

      double var4 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
      double var6 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
      double var8 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;
      float var10 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var2;
      int var11 = var1.getBrightnessForRender(var2);
      if(var1.isBurning()) {
         var11 = 15728880;
      }

      int var12 = var11 % 65536;
      int var13 = var11 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var12, (float)var13);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.a(var1, var4 - this.renderPosX, var6 - this.renderPosY, var8 - this.renderPosZ, var10, var2, var3);
   }

   public void renderWitherSkull(Entity var1, float var2) {
      double var3 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
      double var5 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
      double var7 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;
      Render var9 = this.getEntityRenderObject(var1);
      if(this.renderEngine != null) {
         int var10 = var1.getBrightnessForRender(var2);
         int var11 = var10 % 65536;
         int var12 = var10 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var11, (float)var12);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         var9.renderName(var1, var3 - this.renderPosX, var5 - this.renderPosY, var7 - this.renderPosZ);
      }

   }

   public void b(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.a(var1, var2, var4, var6, var8, var9, false);
   }

   public void a(Entity param1, double param2, double param4, double param6, float param8, float param9, boolean param10) {
      // $FF: Couldn't be decompiled
   }

   private void renderDebugBoundingBox(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.depthMask(false);
      GlStateManager.disableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.disableCull();
      GlStateManager.disableBlend();
      float var10 = var1.width / 2.0F;
      AxisAlignedBB var11 = var1.getEntityBoundingBox();
      AxisAlignedBB var12 = new AxisAlignedBB(var11.minX - var1.posX + var2, var11.minY - var1.posY + var4, var11.minZ - var1.posZ + var6, var11.maxX - var1.posX + var2, var11.maxY - var1.posY + var4, var11.maxZ - var1.posZ + var6);
      RenderGlobal.func_181563_a(var12, 255, 255, 255, 255);
      if(var1 instanceof EntityLivingBase) {
         float var13 = 0.01F;
         RenderGlobal.func_181563_a(new AxisAlignedBB(var2 - (double)var10, var4 + (double)var1.getEyeHeight() - 0.009999999776482582D, var6 - (double)var10, var2 + (double)var10, var4 + (double)var1.getEyeHeight() + 0.009999999776482582D, var6 + (double)var10), 255, 0, 0, 255);
      }

      Tessellator var16 = Tessellator.getInstance();
      WorldRenderer var14 = var16.getWorldRenderer();
      Vec3 var15 = var1.getLook(var9);
      var14.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var14.pos(var2, var4 + (double)var1.getEyeHeight(), var6).color(0, 0, 255, 255).endVertex();
      var14.pos(var2 + var15.xCoord * 2.0D, var4 + (double)var1.getEyeHeight() + var15.yCoord * 2.0D, var6 + var15.zCoord * 2.0D).color(0, 0, 255, 255).endVertex();
      var16.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.enableLighting();
      GlStateManager.enableCull();
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
   }

   public void set(World var1) {
      this.worldObj = var1;
   }

   public double getDistanceToCamera(double var1, double var3, double var5) {
      double var7 = var1 - this.viewerPosX;
      double var9 = var3 - this.viewerPosY;
      double var11 = var5 - this.viewerPosZ;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public FontRenderer getFontRenderer() {
      return this.textRenderer;
   }

   public void setRenderOutlines(boolean var1) {
      this.renderOutlines = var1;
   }

   public Map getEntityRenderMap() {
      return this.entityRenderMap;
   }

   public void setEntityRenderMap(Map var1) {
      this.entityRenderMap = var1;
   }

   public Map getSkinMap() {
      return Collections.unmodifiableMap(this.skinMap);
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
