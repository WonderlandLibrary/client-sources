/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import optifine.PlayerItemsLayer;
import optifine.Reflector;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.friends.Friend;
import skizzle.friends.FriendManager;
import skizzle.modules.ModuleManager;
import skizzle.util.Colors;

public class RenderManager {
    private Map entityRenderMap = Maps.newHashMap();
    private Map field_178636_l = Maps.newHashMap();
    private RenderPlayer field_178637_m;
    private FontRenderer textRenderer;
    public double renderPosX;
    public double renderPosY;
    public double renderPosZ;
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
    public boolean debugBoundingBox = false;
    private static final String __OBFID = "CL_00000991";

    public RenderManager(TextureManager p_i46180_1_, RenderItem p_i46180_2_) {
        this.renderEngine = p_i46180_1_;
        this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
        this.entityRenderMap.put(EntitySpider.class, new RenderSpider(this));
        this.entityRenderMap.put(EntityPig.class, new RenderPig(this, new ModelPig(), 0.7f));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, new ModelSheep2(), 0.7f));
        this.entityRenderMap.put(EntityCow.class, new RenderCow(this, new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, new ModelWolf(), 0.5f));
        this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this, new ModelChicken(), 0.3f));
        this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, new ModelOcelot(), 0.4f));
        this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, new ModelRabbit(), 0.3f));
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
        this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this, new ModelSlime(16), 0.25f));
        this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
        this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, new ModelZombie(), 0.5f, 6.0f));
        this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
        this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this, new ModelSquid(), 0.7f));
        this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
        this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
        this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
        this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
        this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
        this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
        this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
        this.entityRenderMap.put(Entity.class, new RenderEntity(this));
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
        this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, p_i46180_2_));
        this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
        this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
        this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(this, Items.snowball, p_i46180_2_));
        this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(this, Items.ender_pearl, p_i46180_2_));
        this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(this, Items.ender_eye, p_i46180_2_));
        this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(this, Items.egg, p_i46180_2_));
        this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, p_i46180_2_));
        this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(this, Items.experience_bottle, p_i46180_2_));
        this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(this, Items.fireworks, p_i46180_2_));
        this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0f));
        this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5f));
        this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
        this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, p_i46180_2_));
        this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
        this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
        this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
        this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
        this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart(this));
        this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
        this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
        this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75f));
        this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
        this.field_178637_m = new RenderPlayer(this);
        this.field_178636_l.put("default", this.field_178637_m);
        this.field_178636_l.put("slim", new RenderPlayer(this, true));
        PlayerItemsLayer.register(this.field_178636_l);
        if (Reflector.RenderingRegistry_loadEntityRenderers.exists()) {
            Reflector.call(Reflector.RenderingRegistry_loadEntityRenderers, this, this.entityRenderMap);
        }
    }

    public void setRenderPosition(double x, double y, double z) {
        this.renderPosX = x;
        this.renderPosY = y;
        this.renderPosZ = z;
    }

    public Render getEntityClassRenderObject(Class p_78715_1_) {
        Render var2 = (Render)this.entityRenderMap.get(p_78715_1_);
        if (var2 == null && p_78715_1_ != Entity.class) {
            var2 = this.getEntityClassRenderObject(p_78715_1_.getSuperclass());
            this.entityRenderMap.put(p_78715_1_, var2);
        }
        return var2;
    }

    public Render getEntityRenderObject(Entity p_78713_1_) {
        if (p_78713_1_ instanceof AbstractClientPlayer) {
            String var2 = ((AbstractClientPlayer)p_78713_1_).func_175154_l();
            RenderPlayer var3 = (RenderPlayer)this.field_178636_l.get(var2);
            return var3 != null ? var3 : this.field_178637_m;
        }
        return this.getEntityClassRenderObject(p_78713_1_.getClass());
    }

    public void func_180597_a(World worldIn, FontRenderer p_180597_2_, Entity p_180597_3_, Entity p_180597_4_, GameSettings p_180597_5_, float p_180597_6_) {
        this.worldObj = worldIn;
        this.options = p_180597_5_;
        this.livingPlayer = p_180597_3_;
        this.field_147941_i = p_180597_4_;
        this.textRenderer = p_180597_2_;
        if (p_180597_3_ instanceof EntityLivingBase && ((EntityLivingBase)p_180597_3_).isPlayerSleeping()) {
            IBlockState var7 = worldIn.getBlockState(new BlockPos(p_180597_3_));
            Block var8 = var7.getBlock();
            if (Reflector.callBoolean(Reflector.ForgeBlock_isBed, worldIn, new BlockPos(p_180597_3_), (EntityLivingBase)p_180597_3_)) {
                EnumFacing var9 = (EnumFacing)Reflector.call(var8, Reflector.ForgeBlock_getBedDirection, worldIn, new BlockPos(p_180597_3_));
                int var91 = var9.getHorizontalIndex();
                this.playerViewY = var91 * 90 + 180;
                this.playerViewX = 0.0f;
            } else if (var8 == Blocks.bed) {
                int var92 = ((EnumFacing)((Object)var7.getValue(BlockBed.AGE))).getHorizontalIndex();
                this.playerViewY = var92 * 90 + 180;
                this.playerViewX = 0.0f;
            }
        } else {
            this.playerViewY = p_180597_3_.prevRotationYaw + (p_180597_3_.rotationYaw - p_180597_3_.prevRotationYaw) * p_180597_6_;
            this.playerViewX = p_180597_3_.prevRotationPitch + (p_180597_3_.rotationPitch - p_180597_3_.prevRotationPitch) * p_180597_6_;
        }
        if (p_180597_5_.thirdPersonView == 2) {
            this.playerViewY += 180.0f;
        }
        this.viewerPosX = p_180597_3_.lastTickPosX + (p_180597_3_.posX - p_180597_3_.lastTickPosX) * (double)p_180597_6_;
        this.viewerPosY = p_180597_3_.lastTickPosY + (p_180597_3_.posY - p_180597_3_.lastTickPosY) * (double)p_180597_6_;
        this.viewerPosZ = p_180597_3_.lastTickPosZ + (p_180597_3_.posZ - p_180597_3_.lastTickPosZ) * (double)p_180597_6_;
    }

    public void func_178631_a(float p_178631_1_) {
        this.playerViewY = p_178631_1_;
    }

    public boolean func_178627_a() {
        return this.field_178638_s;
    }

    public void func_178633_a(boolean p_178633_1_) {
        this.field_178638_s = p_178633_1_;
    }

    public void func_178629_b(boolean p_178629_1_) {
        this.debugBoundingBox = p_178629_1_;
    }

    public boolean func_178634_b() {
        return this.debugBoundingBox;
    }

    public boolean renderEntitySimple(Entity p_147937_1_, float p_147937_2_) {
        return this.renderEntityStatic(p_147937_1_, p_147937_2_, false);
    }

    public boolean func_178635_a(Entity p_178635_1_, ICamera p_178635_2_, double p_178635_3_, double p_178635_5_, double p_178635_7_) {
        Render var9 = this.getEntityRenderObject(p_178635_1_);
        return var9 != null && var9.func_177071_a(p_178635_1_, p_178635_2_, p_178635_3_, p_178635_5_, p_178635_7_);
    }

    public boolean renderEntityStatic(Entity p_147936_1_, float p_147936_2_, boolean p_147936_3_) {
        if (p_147936_1_.ticksExisted == 0) {
            p_147936_1_.lastTickPosX = p_147936_1_.posX;
            p_147936_1_.lastTickPosY = p_147936_1_.posY;
            p_147936_1_.lastTickPosZ = p_147936_1_.posZ;
        }
        double var4 = p_147936_1_.lastTickPosX + (p_147936_1_.posX - p_147936_1_.lastTickPosX) * (double)p_147936_2_;
        double var6 = p_147936_1_.lastTickPosY + (p_147936_1_.posY - p_147936_1_.lastTickPosY) * (double)p_147936_2_;
        double var8 = p_147936_1_.lastTickPosZ + (p_147936_1_.posZ - p_147936_1_.lastTickPosZ) * (double)p_147936_2_;
        float var10 = p_147936_1_.prevRotationYaw + (p_147936_1_.rotationYaw - p_147936_1_.prevRotationYaw) * p_147936_2_;
        int var11 = p_147936_1_.getBrightnessForRender(p_147936_2_);
        if (p_147936_1_.isBurning()) {
            var11 = 0xF000F0;
        }
        int var12 = var11 % 65536;
        int var13 = var11 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var12 / 1.0f, (float)var13 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return this.doRenderEntity(p_147936_1_, var4 - this.renderPosX, var6 - this.renderPosY, var8 - this.renderPosZ, var10, p_147936_2_, p_147936_3_);
    }

    public void func_178630_b(Entity p_178630_1_, float p_178630_2_) {
        double var3 = p_178630_1_.lastTickPosX + (p_178630_1_.posX - p_178630_1_.lastTickPosX) * (double)p_178630_2_;
        double var5 = p_178630_1_.lastTickPosY + (p_178630_1_.posY - p_178630_1_.lastTickPosY) * (double)p_178630_2_;
        double var7 = p_178630_1_.lastTickPosZ + (p_178630_1_.posZ - p_178630_1_.lastTickPosZ) * (double)p_178630_2_;
        Render var9 = this.getEntityRenderObject(p_178630_1_);
        if (var9 != null && this.renderEngine != null) {
            int var10 = p_178630_1_.getBrightnessForRender(p_178630_2_);
            int var11 = var10 % 65536;
            int var12 = var10 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var11 / 1.0f, (float)var12 / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            var9.func_177067_a(p_178630_1_, var3 - this.renderPosX, var5 - this.renderPosY, var7 - this.renderPosZ);
        }
    }

    public boolean renderEntityWithPosYaw(Entity p_147940_1_, double p_147940_2_, double p_147940_4_, double p_147940_6_, float p_147940_8_, float p_147940_9_, boolean fuck) {
        return this.doRenderEntity(p_147940_1_, p_147940_2_, p_147940_4_, p_147940_6_, p_147940_8_, p_147940_9_, false);
    }

    public boolean renderEntityWithPosYawNoLabel(Entity p_147940_1_, double p_147940_2_, double p_147940_4_, double p_147940_6_, float p_147940_8_, float p_147940_9_, boolean fuck) {
        return this.doRenderEntity(p_147940_1_, p_147940_2_, p_147940_4_, p_147940_6_, p_147940_8_, p_147940_9_, false);
    }

    /*
     * Exception decompiling
     */
    public boolean doRenderEntityNoLabel(Entity entityIn, double p_147939_2_, double p_147939_4_, double p_147939_6_, float p_147939_8_, float p_147939_9_, boolean p_147939_10_) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl97 : GETSTATIC - null : trying to set 0 previously set to 1
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:203)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1542)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:400)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:258)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:192)
         * org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         * org.benf.cfr.reader.entities.Method.analyse(Method.java:521)
         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
         * org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:922)
         * org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:253)
         * org.benf.cfr.reader.Driver.doJar(Driver.java:135)
         * org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
         * org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException(Decompilation failed);
    }

    /*
     * Exception decompiling
     */
    public boolean doRenderEntity(Entity entityIn, double p_147939_2_, double p_147939_4_, double p_147939_6_, float p_147939_8_, float p_147939_9_, boolean p_147939_10_) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl110 : GETSTATIC - null : trying to set 0 previously set to 1
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:203)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1542)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:400)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:258)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:192)
         * org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         * org.benf.cfr.reader.entities.Method.analyse(Method.java:521)
         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
         * org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:922)
         * org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:253)
         * org.benf.cfr.reader.Driver.doJar(Driver.java:135)
         * org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
         * org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException(Decompilation failed);
    }

    private void renderESPBox(Entity entityIn, double p_85094_2_, double p_85094_4_, double p_85094_6_, float p_85094_8_, float p_85094_9_) {
        GL11.glPopMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GL11.glEnable((int)2848);
        AxisAlignedBB var11 = entityIn.getEntityBoundingBox();
        int color = Colors.getColor((int)(ModuleManager.esp.getColors()[0] * 255.0f), (int)(ModuleManager.esp.getColors()[1] * 255.0f), (int)(ModuleManager.esp.getColors()[2] * 255.0f));
        for (Friend f : FriendManager.friends) {
            if (!entityIn.getName().equals(f.getName())) continue;
            color = -16561668;
        }
        float addY = 0.0f;
        float addYTop = 0.0f;
        if (entityIn instanceof EntityItem) {
            addY = 0.2f;
            addYTop = 0.1f;
        }
        if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).isSneaking()) {
            addYTop = -0.225f;
        }
        AxisAlignedBB var12 = new AxisAlignedBB(var11.minX - entityIn.posX + p_85094_2_, var11.minY - entityIn.posY + p_85094_4_ + (double)addY, var11.minZ - entityIn.posZ + p_85094_6_, var11.maxX - entityIn.posX + p_85094_2_, var11.maxY - entityIn.posY + 0.09 + p_85094_4_ + (double)addYTop, var11.maxZ - entityIn.posZ + p_85094_6_);
        GL11.glDisable((int)2929);
        GL11.glLineWidth((float)1.0f);
        RenderGlobal.drawOutlinedBoundingBox(var12, color);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(3);
        GL11.glEnable((int)2929);
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GL11.glDisable((int)2848);
        GlStateManager.disableBlend();
        GL11.glPushMatrix();
    }

    public static void renderESPBlockBox(BlockPos pos) {
        GL11.glPopMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        AxisAlignedBB var12 = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
        GL11.glDisable((int)2929);
        RenderGlobal.drawOutlinedBoundingBox(var12.offset(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ), Client.RGBColor);
        GL11.glEnable((int)2929);
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GL11.glPushMatrix();
    }

    private void renderDebugBoundingBox(Entity p_85094_1_, double p_85094_2_, double p_85094_4_, double p_85094_6_, float p_85094_8_, float p_85094_9_) {
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        float var10 = p_85094_1_.width / 2.0f;
        AxisAlignedBB var11 = p_85094_1_.getEntityBoundingBox();
        AxisAlignedBB var12 = new AxisAlignedBB(var11.minX - p_85094_1_.posX + p_85094_2_, var11.minY - p_85094_1_.posY + p_85094_4_, var11.minZ - p_85094_1_.posZ + p_85094_6_, var11.maxX - p_85094_1_.posX + p_85094_2_, var11.maxY - p_85094_1_.posY + p_85094_4_, var11.maxZ - p_85094_1_.posZ + p_85094_6_);
        RenderGlobal.drawOutlinedBoundingBox(var12, 0xFFFFFF);
        if (p_85094_1_ instanceof EntityLivingBase) {
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(p_85094_2_ - (double)var10, p_85094_4_ + (double)p_85094_1_.getEyeHeight() - (double)0.01f, p_85094_6_ - (double)var10, p_85094_2_ + (double)var10, p_85094_4_ + (double)p_85094_1_.getEyeHeight() + (double)0.01f, p_85094_6_ + (double)var10), 0xFF0000);
        }
        Tessellator var161 = Tessellator.getInstance();
        WorldRenderer var14 = var161.getWorldRenderer();
        Vec3 var15 = p_85094_1_.getLook(p_85094_9_);
        var14.startDrawing(3);
        var14.func_178991_c(255);
        var14.addVertex(p_85094_2_, p_85094_4_ + (double)p_85094_1_.getEyeHeight(), p_85094_6_);
        var14.addVertex(p_85094_2_ + var15.xCoord * 2.0, p_85094_4_ + (double)p_85094_1_.getEyeHeight() + var15.yCoord * 2.0, p_85094_6_ + var15.zCoord * 2.0);
        var161.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
    }

    public void set(World worldIn) {
        this.worldObj = worldIn;
    }

    public double getDistanceToCamera(double p_78714_1_, double p_78714_3_, double p_78714_5_) {
        double var7 = p_78714_1_ - this.viewerPosX;
        double var9 = p_78714_3_ - this.viewerPosY;
        double var11 = p_78714_5_ - this.viewerPosZ;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    public FontRenderer getFontRenderer() {
        return this.textRenderer;
    }

    public void func_178632_c(boolean p_178632_1_) {
        this.field_178639_r = p_178632_1_;
    }

    public Map getEntityRenderMap() {
        return this.entityRenderMap;
    }

    public void setEntityRenderMap(Map entityRenderMap) {
        this.entityRenderMap = entityRenderMap;
    }

    public Map<String, RenderPlayer> getSkinMap() {
        return Collections.unmodifiableMap(this.field_178636_l);
    }
}

