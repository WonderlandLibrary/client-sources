package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.AbstractClientPlayer;
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
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
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
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class RenderManager
{
  private Map entityRenderMap = Maps.newHashMap();
  private Map field_178636_l = Maps.newHashMap();
  
  private RenderPlayer field_178637_m;
  
  private FontRenderer textRenderer;
  
  public static double renderPosX;
  
  public static double renderPosY;
  
  public static double renderPosZ;
  
  public TextureManager renderEngine;
  
  public World worldObj;
  
  public Entity livingPlayer;
  public Entity field_147941_i;
  public float playerViewY;
  public float playerViewX;
  public GameSettings options;
  public double viewerPosX;
  public double viewerPosY;
  public double viewerPosZ;
  private boolean field_178639_r = false;
  private boolean field_178638_s = true;
  

  private boolean debugBoundingBox = false;
  private static final String __OBFID = "CL_00000991";
  
  public RenderManager(TextureManager p_i46180_1_, RenderItem p_i46180_2_)
  {
    renderEngine = p_i46180_1_;
    entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
    entityRenderMap.put(EntitySpider.class, new RenderSpider(this));
    entityRenderMap.put(EntityPig.class, new RenderPig(this, new ModelPig(), 0.7F));
    entityRenderMap.put(EntitySheep.class, new RenderSheep(this, new ModelSheep2(), 0.7F));
    entityRenderMap.put(EntityCow.class, new RenderCow(this, new ModelCow(), 0.7F));
    entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, new ModelCow(), 0.7F));
    entityRenderMap.put(EntityWolf.class, new RenderWolf(this, new ModelWolf(), 0.5F));
    entityRenderMap.put(EntityChicken.class, new RenderChicken(this, new ModelChicken(), 0.3F));
    entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, new ModelOcelot(), 0.4F));
    entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, new ModelRabbit(), 0.3F));
    entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
    entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
    entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
    entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
    entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
    entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
    entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
    entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
    entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
    entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
    entityRenderMap.put(EntitySlime.class, new RenderSlime(this, new ModelSlime(16), 0.25F));
    entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
    entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, new ModelZombie(), 0.5F, 6.0F));
    entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
    entityRenderMap.put(EntitySquid.class, new RenderSquid(this, new ModelSquid(), 0.7F));
    entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
    entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
    entityRenderMap.put(EntityBat.class, new RenderBat(this));
    entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
    entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
    entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
    entityRenderMap.put(EntityWither.class, new RenderWither(this));
    entityRenderMap.put(Entity.class, new RenderEntity(this));
    entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
    entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, p_i46180_2_));
    entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
    entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
    entityRenderMap.put(EntitySnowball.class, new RenderSnowball(this, Items.snowball, p_i46180_2_));
    entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(this, Items.ender_pearl, p_i46180_2_));
    entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(this, Items.ender_eye, p_i46180_2_));
    entityRenderMap.put(EntityEgg.class, new RenderSnowball(this, Items.egg, p_i46180_2_));
    entityRenderMap.put(EntityPotion.class, new RenderPotion(this, p_i46180_2_));
    entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(this, Items.experience_bottle, p_i46180_2_));
    entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(this, Items.fireworks, p_i46180_2_));
    entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
    entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
    entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
    entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, p_i46180_2_));
    entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
    entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
    entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
    entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
    entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
    entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
    entityRenderMap.put(EntityMinecart.class, new RenderMinecart(this));
    entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
    entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
    entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75F));
    entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
    field_178637_m = new RenderPlayer(this);
    field_178636_l.put("default", field_178637_m);
    field_178636_l.put("slim", new RenderPlayer(this, true));
  }
  
  public void func_178628_a(double p_178628_1_, double p_178628_3_, double p_178628_5_)
  {
    renderPosX = p_178628_1_;
    renderPosY = p_178628_3_;
    renderPosZ = p_178628_5_;
  }
  
  public Render getEntityClassRenderObject(Class p_78715_1_)
  {
    Render var2 = (Render)entityRenderMap.get(p_78715_1_);
    
    if ((var2 == null) && (p_78715_1_ != Entity.class))
    {
      var2 = getEntityClassRenderObject(p_78715_1_.getSuperclass());
      entityRenderMap.put(p_78715_1_, var2);
    }
    
    return var2;
  }
  
  public Render getEntityRenderObject(Entity p_78713_1_)
  {
    if ((p_78713_1_ instanceof AbstractClientPlayer))
    {
      String var2 = ((AbstractClientPlayer)p_78713_1_).func_175154_l();
      RenderPlayer var3 = (RenderPlayer)field_178636_l.get(var2);
      return var3 != null ? var3 : field_178637_m;
    }
    

    return getEntityClassRenderObject(p_78713_1_.getClass());
  }
  

  public void func_180597_a(World worldIn, FontRenderer p_180597_2_, Entity p_180597_3_, Entity p_180597_4_, GameSettings p_180597_5_, float p_180597_6_)
  {
    worldObj = worldIn;
    options = p_180597_5_;
    livingPlayer = p_180597_3_;
    field_147941_i = p_180597_4_;
    textRenderer = p_180597_2_;
    
    if (((p_180597_3_ instanceof EntityLivingBase)) && (((EntityLivingBase)p_180597_3_).isPlayerSleeping()))
    {
      IBlockState var7 = worldIn.getBlockState(new BlockPos(p_180597_3_));
      Block var8 = var7.getBlock();
      
      if (var8 == Blocks.bed)
      {
        int var9 = ((EnumFacing)var7.getValue(BlockBed.AGE)).getHorizontalIndex();
        playerViewY = (var9 * 90 + 180);
        playerViewX = 0.0F;
      }
    }
    else
    {
      playerViewY = (prevRotationYaw + (rotationYaw - prevRotationYaw) * p_180597_6_);
      playerViewX = (prevRotationPitch + (rotationPitch - prevRotationPitch) * p_180597_6_);
    }
    
    if (thirdPersonView == 2)
    {
      playerViewY += 180.0F;
    }
    
    viewerPosX = (lastTickPosX + (posX - lastTickPosX) * p_180597_6_);
    viewerPosY = (lastTickPosY + (posY - lastTickPosY) * p_180597_6_);
    viewerPosZ = (lastTickPosZ + (posZ - lastTickPosZ) * p_180597_6_);
  }
  
  public void func_178631_a(float p_178631_1_)
  {
    playerViewY = p_178631_1_;
  }
  
  public boolean func_178627_a()
  {
    return field_178638_s;
  }
  
  public void func_178633_a(boolean p_178633_1_)
  {
    field_178638_s = p_178633_1_;
  }
  
  public void func_178629_b(boolean p_178629_1_)
  {
    debugBoundingBox = p_178629_1_;
  }
  
  public boolean func_178634_b()
  {
    return debugBoundingBox;
  }
  
  public boolean renderEntitySimple(Entity p_147937_1_, float p_147937_2_)
  {
    return renderEntityStatic(p_147937_1_, p_147937_2_, false);
  }
  
  public boolean func_178635_a(Entity p_178635_1_, ICamera p_178635_2_, double p_178635_3_, double p_178635_5_, double p_178635_7_)
  {
    Render var9 = getEntityRenderObject(p_178635_1_);
    return (var9 != null) && (var9.func_177071_a(p_178635_1_, p_178635_2_, p_178635_3_, p_178635_5_, p_178635_7_));
  }
  
  public boolean renderEntityStatic(Entity p_147936_1_, float p_147936_2_, boolean p_147936_3_)
  {
    if (ticksExisted == 0)
    {
      lastTickPosX = posX;
      lastTickPosY = posY;
      lastTickPosZ = posZ;
    }
    
    double var4 = lastTickPosX + (posX - lastTickPosX) * p_147936_2_;
    double var6 = lastTickPosY + (posY - lastTickPosY) * p_147936_2_;
    double var8 = lastTickPosZ + (posZ - lastTickPosZ) * p_147936_2_;
    float var10 = prevRotationYaw + (rotationYaw - prevRotationYaw) * p_147936_2_;
    int var11 = p_147936_1_.getBrightnessForRender(p_147936_2_);
    
    if (p_147936_1_.isBurning())
    {
      var11 = 15728880;
    }
    
    int var12 = var11 % 65536;
    int var13 = var11 / 65536;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var12 / 1.0F, var13 / 1.0F);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    return doRenderEntity(p_147936_1_, var4 - renderPosX, var6 - renderPosY, var8 - renderPosZ, var10, p_147936_2_, p_147936_3_);
  }
  
  public void func_178630_b(Entity p_178630_1_, float p_178630_2_)
  {
    double var3 = lastTickPosX + (posX - lastTickPosX) * p_178630_2_;
    double var5 = lastTickPosY + (posY - lastTickPosY) * p_178630_2_;
    double var7 = lastTickPosZ + (posZ - lastTickPosZ) * p_178630_2_;
    Render var9 = getEntityRenderObject(p_178630_1_);
    
    if ((var9 != null) && (renderEngine != null))
    {
      int var10 = p_178630_1_.getBrightnessForRender(p_178630_2_);
      int var11 = var10 % 65536;
      int var12 = var10 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var11 / 1.0F, var12 / 1.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      var9.func_177067_a(p_178630_1_, var3 - renderPosX, var5 - renderPosY, var7 - renderPosZ);
    }
  }
  
  public boolean renderEntityWithPosYaw(Entity p_147940_1_, double p_147940_2_, double p_147940_4_, double p_147940_6_, float p_147940_8_, float p_147940_9_)
  {
    return doRenderEntity(p_147940_1_, p_147940_2_, p_147940_4_, p_147940_6_, p_147940_8_, p_147940_9_, false);
  }
  
  public boolean doRenderEntity(Entity p_147939_1_, double p_147939_2_, double p_147939_4_, double p_147939_6_, float p_147939_8_, float p_147939_9_, boolean p_147939_10_)
  {
    Render var11 = null;
    
    try
    {
      var11 = getEntityRenderObject(p_147939_1_);
      
      if ((var11 != null) && (renderEngine != null))
      {
        try
        {
          if ((var11 instanceof RendererLivingEntity))
          {
            ((RendererLivingEntity)var11).func_177086_a(field_178639_r);
          }
          
          var11.doRender(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
        }
        catch (Throwable var18)
        {
          throw new ReportedException(CrashReport.makeCrashReport(var18, "Rendering entity in world"));
        }
        
        try
        {
          if (!field_178639_r)
          {
            var11.doRenderShadowAndFire(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
          }
        }
        catch (Throwable var17)
        {
          throw new ReportedException(CrashReport.makeCrashReport(var17, "Post-rendering entity in world"));
        }
        
        if ((debugBoundingBox) && (!p_147939_1_.isInvisible()) && (!p_147939_10_))
        {
          try
          {
            renderDebugBoundingBox(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
          }
          catch (Throwable var16)
          {
            throw new ReportedException(CrashReport.makeCrashReport(var16, "Rendering entity hitbox in world"));
          }
        }
      }
      else if (renderEngine != null)
      {
        return false;
      }
      
      return true;
    }
    catch (Throwable var19)
    {
      CrashReport var13 = CrashReport.makeCrashReport(var19, "Rendering entity in world");
      CrashReportCategory var14 = var13.makeCategory("Entity being rendered");
      p_147939_1_.addEntityCrashInfo(var14);
      CrashReportCategory var15 = var13.makeCategory("Renderer details");
      var15.addCrashSection("Assigned renderer", var11);
      var15.addCrashSection("Location", CrashReportCategory.getCoordinateInfo(p_147939_2_, p_147939_4_, p_147939_6_));
      var15.addCrashSection("Rotation", Float.valueOf(p_147939_8_));
      var15.addCrashSection("Delta", Float.valueOf(p_147939_9_));
      throw new ReportedException(var13);
    }
  }
  



  private void renderDebugBoundingBox(Entity p_85094_1_, double p_85094_2_, double p_85094_4_, double p_85094_6_, float p_85094_8_, float p_85094_9_)
  {
    GlStateManager.depthMask(false);
    GlStateManager.func_179090_x();
    GlStateManager.disableLighting();
    GlStateManager.disableCull();
    GlStateManager.disableBlend();
    float var10 = width / 2.0F;
    AxisAlignedBB var11 = p_85094_1_.getEntityBoundingBox();
    AxisAlignedBB var12 = new AxisAlignedBB(minX - posX + p_85094_2_, minY - posY + p_85094_4_, minZ - posZ + p_85094_6_, maxX - posX + p_85094_2_, maxY - posY + p_85094_4_, maxZ - posZ + p_85094_6_);
    RenderGlobal.drawOutlinedBoundingBox(var12, 16777215);
    
    if ((p_85094_1_ instanceof EntityLivingBase))
    {
      float var13 = 0.01F;
      RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(p_85094_2_ - var10, p_85094_4_ + p_85094_1_.getEyeHeight() - 0.009999999776482582D, p_85094_6_ - var10, p_85094_2_ + var10, p_85094_4_ + p_85094_1_.getEyeHeight() + 0.009999999776482582D, p_85094_6_ + var10), 16711680);
    }
    
    Tessellator var16 = Tessellator.getInstance();
    WorldRenderer var14 = var16.getWorldRenderer();
    Vec3 var15 = p_85094_1_.getLook(p_85094_9_);
    var14.startDrawing(3);
    var14.func_178991_c(255);
    var14.addVertex(p_85094_2_, p_85094_4_ + p_85094_1_.getEyeHeight(), p_85094_6_);
    var14.addVertex(p_85094_2_ + xCoord * 2.0D, p_85094_4_ + p_85094_1_.getEyeHeight() + yCoord * 2.0D, p_85094_6_ + zCoord * 2.0D);
    var16.draw();
    GlStateManager.func_179098_w();
    GlStateManager.enableLighting();
    GlStateManager.enableCull();
    GlStateManager.disableBlend();
    GlStateManager.depthMask(true);
  }
  



  public void set(World worldIn)
  {
    worldObj = worldIn;
  }
  
  public double getDistanceToCamera(double p_78714_1_, double p_78714_3_, double p_78714_5_)
  {
    double var7 = p_78714_1_ - viewerPosX;
    double var9 = p_78714_3_ - viewerPosY;
    double var11 = p_78714_5_ - viewerPosZ;
    return var7 * var7 + var9 * var9 + var11 * var11;
  }
  



  public FontRenderer getFontRenderer()
  {
    return textRenderer;
  }
  
  public void func_178632_c(boolean p_178632_1_)
  {
    field_178639_r = p_178632_1_;
  }
}
