package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;

public class RenderManager
{
    private Map £á;
    private Map Å;
    private RenderPlayer £à;
    private FontRenderer µà;
    public static double HorizonCode_Horizon_È;
    public static double Â;
    public static double Ý;
    public TextureManager Ø­áŒŠá;
    public World Âµá€;
    public Entity Ó;
    public Entity à;
    public static float Ø;
    public static float áŒŠÆ;
    public GameSettings áˆºÑ¢Õ;
    public double ÂµÈ;
    public double á;
    public double ˆÏ­;
    private boolean ˆà;
    private boolean ¥Æ;
    private boolean Ø­à;
    private static final String µÕ = "CL_00000991";
    
    public RenderManager(final TextureManager p_i46180_1_, final RenderItem p_i46180_2_) {
        this.£á = Maps.newHashMap();
        this.Å = Maps.newHashMap();
        this.ˆà = false;
        this.¥Æ = true;
        this.Ø­à = false;
        this.Ø­áŒŠá = p_i46180_1_;
        this.£á.put(EntityCaveSpider.class, new RenderCaveSpider(this));
        this.£á.put(EntitySpider.class, new RenderSpider(this));
        this.£á.put(EntityPig.class, new RenderPig(this, new ModelPig(), 0.7f));
        this.£á.put(EntitySheep.class, new RenderSheep(this, new ModelSheep2(), 0.7f));
        this.£á.put(EntityCow.class, new RenderCow(this, new ModelCow(), 0.7f));
        this.£á.put(EntityMooshroom.class, new RenderMooshroom(this, new ModelCow(), 0.7f));
        this.£á.put(EntityWolf.class, new RenderWolf(this, new ModelWolf(), 0.5f));
        this.£á.put(EntityChicken.class, new RenderChicken(this, new ModelChicken(), 0.3f));
        this.£á.put(EntityOcelot.class, new RenderOcelot(this, new ModelOcelot(), 0.4f));
        this.£á.put(EntityRabbit.class, new RenderRabbit(this, new ModelRabbit(), 0.3f));
        this.£á.put(EntitySilverfish.class, new RenderSilverfish(this));
        this.£á.put(EntityEndermite.class, new RenderEndermite(this));
        this.£á.put(EntityCreeper.class, new RenderCreeper(this));
        this.£á.put(EntityEnderman.class, new RenderEnderman(this));
        this.£á.put(EntitySnowman.class, new RenderSnowMan(this));
        this.£á.put(EntitySkeleton.class, new RenderSkeleton(this));
        this.£á.put(EntityWitch.class, new RenderWitch(this));
        this.£á.put(EntityBlaze.class, new RenderBlaze(this));
        this.£á.put(EntityPigZombie.class, new RenderPigZombie(this));
        this.£á.put(EntityZombie.class, new RenderZombie(this));
        this.£á.put(EntitySlime.class, new RenderSlime(this, new ModelSlime(16), 0.25f));
        this.£á.put(EntityMagmaCube.class, new RenderMagmaCube(this));
        this.£á.put(EntityGiantZombie.class, new RenderGiantZombie(this, new ModelZombie(), 0.5f, 6.0f));
        this.£á.put(EntityGhast.class, new RenderGhast(this));
        this.£á.put(EntitySquid.class, new RenderSquid(this, new ModelSquid(), 0.7f));
        this.£á.put(EntityVillager.class, new RenderVillager(this));
        this.£á.put(EntityIronGolem.class, new RenderIronGolem(this));
        this.£á.put(EntityBat.class, new RenderBat(this));
        this.£á.put(EntityGuardian.class, new RenderGuardian(this));
        this.£á.put(EntityDragon.class, new RenderDragon(this));
        this.£á.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
        this.£á.put(EntityWither.class, new RenderWither(this));
        this.£á.put(Entity.class, new RenderEntity(this));
        this.£á.put(EntityPainting.class, new RenderPainting(this));
        this.£á.put(EntityItemFrame.class, new RenderItemFrame(this, p_i46180_2_));
        this.£á.put(EntityLeashKnot.class, new RenderLeashKnot(this));
        this.£á.put(EntityArrow.class, new RenderArrow(this));
        this.£á.put(EntitySnowball.class, new RenderSnowball(this, Items.Ñ¢à, p_i46180_2_));
        this.£á.put(EntityEnderPearl.class, new RenderSnowball(this, Items.ˆÐƒØ, p_i46180_2_));
        this.£á.put(EntityEnderEye.class, new RenderSnowball(this, Items.¥áŠ, p_i46180_2_));
        this.£á.put(EntityEgg.class, new RenderSnowball(this, Items.¥É, p_i46180_2_));
        this.£á.put(EntityPotion.class, new RenderPotion(this, p_i46180_2_));
        this.£á.put(EntityExpBottle.class, new RenderSnowball(this, Items.áŒŠÉ, p_i46180_2_));
        this.£á.put(EntityFireworkRocket.class, new RenderSnowball(this, Items.ŠáŒŠà¢, p_i46180_2_));
        this.£á.put(EntityLargeFireball.class, new RenderFireball(this, 2.0f));
        this.£á.put(EntitySmallFireball.class, new RenderFireball(this, 0.5f));
        this.£á.put(EntityWitherSkull.class, new RenderWitherSkull(this));
        this.£á.put(EntityItem.class, new RenderEntityItem(this, p_i46180_2_));
        this.£á.put(EntityXPOrb.class, new RenderXPOrb(this));
        this.£á.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
        this.£á.put(EntityFallingBlock.class, new RenderFallingBlock(this));
        this.£á.put(EntityArmorStand.class, new ArmorStandRenderer(this));
        this.£á.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
        this.£á.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
        this.£á.put(EntityMinecart.class, new RenderMinecart(this));
        this.£á.put(EntityBoat.class, new RenderBoat(this));
        this.£á.put(EntityFishHook.class, new RenderFish(this));
        this.£á.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75f));
        this.£á.put(EntityLightningBolt.class, new RenderLightningBolt(this));
        this.£à = new RenderPlayer(this);
        this.Å.put("default", this.£à);
        this.Å.put("slim", new RenderPlayer(this, true));
    }
    
    public void HorizonCode_Horizon_È(final double p_178628_1_, final double p_178628_3_, final double p_178628_5_) {
        RenderManager.HorizonCode_Horizon_È = p_178628_1_;
        RenderManager.Â = p_178628_3_;
        RenderManager.Ý = p_178628_5_;
    }
    
    public Render HorizonCode_Horizon_È(final Class p_78715_1_) {
        Render var2 = (Render)this.£á.get(p_78715_1_);
        if (var2 == null && p_78715_1_ != Entity.class) {
            var2 = this.HorizonCode_Horizon_È(p_78715_1_.getSuperclass());
            this.£á.put(p_78715_1_, var2);
        }
        return var2;
    }
    
    public Render HorizonCode_Horizon_È(final Entity p_78713_1_) {
        if (p_78713_1_ instanceof AbstractClientPlayer) {
            final String var2 = ((AbstractClientPlayer)p_78713_1_).e_();
            final RenderPlayer var3 = this.Å.get(var2);
            return (var3 != null) ? var3 : this.£à;
        }
        return this.HorizonCode_Horizon_È(p_78713_1_.getClass());
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final FontRenderer p_180597_2_, final Entity p_180597_3_, final Entity p_180597_4_, final GameSettings p_180597_5_, final float p_180597_6_) {
        this.Âµá€ = worldIn;
        this.áˆºÑ¢Õ = p_180597_5_;
        this.Ó = p_180597_3_;
        this.à = p_180597_4_;
        this.µà = p_180597_2_;
        if (p_180597_3_ instanceof EntityLivingBase && ((EntityLivingBase)p_180597_3_).Ï­Ó()) {
            final IBlockState var7 = worldIn.Â(new BlockPos(p_180597_3_));
            final Block var8 = var7.Ý();
            if (var8 == Blocks.Ê) {
                final int var9 = ((EnumFacing)var7.HorizonCode_Horizon_È(BlockBed.ŠÂµà)).Ý();
                RenderManager.Ø = var9 * 90 + 180;
                RenderManager.áŒŠÆ = 0.0f;
            }
        }
        else {
            RenderManager.Ø = p_180597_3_.á€ + (p_180597_3_.É - p_180597_3_.á€) * p_180597_6_;
            RenderManager.áŒŠÆ = p_180597_3_.Õ + (p_180597_3_.áƒ - p_180597_3_.Õ) * p_180597_6_;
        }
        if (p_180597_5_.µÏ == 2) {
            RenderManager.Ø += 180.0f;
        }
        this.ÂµÈ = p_180597_3_.áˆºáˆºÈ + (p_180597_3_.ŒÏ - p_180597_3_.áˆºáˆºÈ) * p_180597_6_;
        this.á = p_180597_3_.ÇŽá€ + (p_180597_3_.Çªà¢ - p_180597_3_.ÇŽá€) * p_180597_6_;
        this.ˆÏ­ = p_180597_3_.Ï + (p_180597_3_.Ê - p_180597_3_.Ï) * p_180597_6_;
    }
    
    public void HorizonCode_Horizon_È(final float p_178631_1_) {
        RenderManager.Ø = p_178631_1_;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.¥Æ;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_178633_1_) {
        this.¥Æ = p_178633_1_;
    }
    
    public void Â(final boolean p_178629_1_) {
        this.Ø­à = p_178629_1_;
    }
    
    public boolean Â() {
        return this.Ø­à;
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_147937_1_, final float p_147937_2_) {
        return this.HorizonCode_Horizon_È(p_147937_1_, p_147937_2_, false);
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_178635_1_, final ICamera p_178635_2_, final double p_178635_3_, final double p_178635_5_, final double p_178635_7_) {
        final Render var9 = this.HorizonCode_Horizon_È(p_178635_1_);
        return var9 != null && var9.HorizonCode_Horizon_È(p_178635_1_, p_178635_2_, p_178635_3_, p_178635_5_, p_178635_7_);
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_147936_1_, final float p_147936_2_, final boolean p_147936_3_) {
        if (p_147936_1_.Œ == 0) {
            p_147936_1_.áˆºáˆºÈ = p_147936_1_.ŒÏ;
            p_147936_1_.ÇŽá€ = p_147936_1_.Çªà¢;
            p_147936_1_.Ï = p_147936_1_.Ê;
        }
        final double var4 = p_147936_1_.áˆºáˆºÈ + (p_147936_1_.ŒÏ - p_147936_1_.áˆºáˆºÈ) * p_147936_2_;
        final double var5 = p_147936_1_.ÇŽá€ + (p_147936_1_.Çªà¢ - p_147936_1_.ÇŽá€) * p_147936_2_;
        final double var6 = p_147936_1_.Ï + (p_147936_1_.Ê - p_147936_1_.Ï) * p_147936_2_;
        final float var7 = p_147936_1_.á€ + (p_147936_1_.É - p_147936_1_.á€) * p_147936_2_;
        int var8 = p_147936_1_.HorizonCode_Horizon_È(p_147936_2_);
        if (p_147936_1_.ˆÏ()) {
            var8 = 15728880;
        }
        final int var9 = var8 % 65536;
        final int var10 = var8 / 65536;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var9 / 1.0f, var10 / 1.0f);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        return this.HorizonCode_Horizon_È(p_147936_1_, var4 - RenderManager.HorizonCode_Horizon_È, var5 - RenderManager.Â, var6 - RenderManager.Ý, var7, p_147936_2_, p_147936_3_);
    }
    
    public void Â(final Entity p_178630_1_, final float p_178630_2_) {
        final double var3 = p_178630_1_.áˆºáˆºÈ + (p_178630_1_.ŒÏ - p_178630_1_.áˆºáˆºÈ) * p_178630_2_;
        final double var4 = p_178630_1_.ÇŽá€ + (p_178630_1_.Çªà¢ - p_178630_1_.ÇŽá€) * p_178630_2_;
        final double var5 = p_178630_1_.Ï + (p_178630_1_.Ê - p_178630_1_.Ï) * p_178630_2_;
        final Render var6 = this.HorizonCode_Horizon_È(p_178630_1_);
        if (var6 != null && this.Ø­áŒŠá != null) {
            final int var7 = p_178630_1_.HorizonCode_Horizon_È(p_178630_2_);
            final int var8 = var7 % 65536;
            final int var9 = var7 / 65536;
            OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var8 / 1.0f, var9 / 1.0f);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            var6.HorizonCode_Horizon_È(p_178630_1_, var3 - RenderManager.HorizonCode_Horizon_È, var4 - RenderManager.Â, var5 - RenderManager.Ý);
        }
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_147940_1_, final double p_147940_2_, final double p_147940_4_, final double p_147940_6_, final float p_147940_8_, final float p_147940_9_) {
        return this.HorizonCode_Horizon_È(p_147940_1_, p_147940_2_, p_147940_4_, p_147940_6_, p_147940_8_, p_147940_9_, false);
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_147939_1_, final double p_147939_2_, final double p_147939_4_, final double p_147939_6_, final float p_147939_8_, final float p_147939_9_, final boolean p_147939_10_) {
        Render var11 = null;
        try {
            var11 = this.HorizonCode_Horizon_È(p_147939_1_);
            if (var11 != null && this.Ø­áŒŠá != null) {
                try {
                    if (var11 instanceof RendererLivingEntity) {
                        ((RendererLivingEntity)var11).HorizonCode_Horizon_È(this.ˆà);
                    }
                    var11.HorizonCode_Horizon_È(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                }
                catch (Throwable var12) {
                    throw new ReportedException(CrashReport.HorizonCode_Horizon_È(var12, "Rendering entity in world"));
                }
                try {
                    if (!this.ˆà) {
                        var11.Â(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                    }
                }
                catch (Throwable var13) {
                    throw new ReportedException(CrashReport.HorizonCode_Horizon_È(var13, "Post-rendering entity in world"));
                }
                if (!this.Ø­à || p_147939_1_.áŒŠÏ() || p_147939_10_) {
                    return true;
                }
                try {
                    this.Â(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                    return true;
                }
                catch (Throwable var14) {
                    throw new ReportedException(CrashReport.HorizonCode_Horizon_È(var14, "Rendering entity hitbox in world"));
                }
            }
            if (this.Ø­áŒŠá != null) {
                return false;
            }
            return true;
        }
        catch (Throwable var16) {
            final CrashReport var15 = CrashReport.HorizonCode_Horizon_È(var16, "Rendering entity in world");
            final CrashReportCategory var17 = var15.HorizonCode_Horizon_È("Entity being rendered");
            p_147939_1_.HorizonCode_Horizon_È(var17);
            final CrashReportCategory var18 = var15.HorizonCode_Horizon_È("Renderer details");
            var18.HorizonCode_Horizon_È("Assigned renderer", var11);
            var18.HorizonCode_Horizon_È("Location", CrashReportCategory.HorizonCode_Horizon_È(p_147939_2_, p_147939_4_, p_147939_6_));
            var18.HorizonCode_Horizon_È("Rotation", p_147939_8_);
            var18.HorizonCode_Horizon_È("Delta", p_147939_9_);
            throw new ReportedException(var15);
        }
    }
    
    private void Â(final Entity p_85094_1_, final double p_85094_2_, final double p_85094_4_, final double p_85094_6_, final float p_85094_8_, final float p_85094_9_) {
        GlStateManager.HorizonCode_Horizon_È(false);
        GlStateManager.Æ();
        GlStateManager.Ó();
        GlStateManager.£à();
        GlStateManager.ÂµÈ();
        final float var10 = p_85094_1_.áŒŠ / 2.0f;
        final AxisAlignedBB var11 = p_85094_1_.£É();
        final AxisAlignedBB var12 = new AxisAlignedBB(var11.HorizonCode_Horizon_È - p_85094_1_.ŒÏ + p_85094_2_, var11.Â - p_85094_1_.Çªà¢ + p_85094_4_, var11.Ý - p_85094_1_.Ê + p_85094_6_, var11.Ø­áŒŠá - p_85094_1_.ŒÏ + p_85094_2_, var11.Âµá€ - p_85094_1_.Çªà¢ + p_85094_4_, var11.Ó - p_85094_1_.Ê + p_85094_6_);
        RenderGlobal.HorizonCode_Horizon_È(var12, 16777215);
        if (p_85094_1_ instanceof EntityLivingBase) {
            final float var13 = 0.01f;
            RenderGlobal.HorizonCode_Horizon_È(new AxisAlignedBB(p_85094_2_ - var10, p_85094_4_ + p_85094_1_.Ðƒáƒ() - 0.009999999776482582, p_85094_6_ - var10, p_85094_2_ + var10, p_85094_4_ + p_85094_1_.Ðƒáƒ() + 0.009999999776482582, p_85094_6_ + var10), 16711680);
        }
        final Tessellator var14 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var15 = var14.Ý();
        final Vec3 var16 = p_85094_1_.Ó(p_85094_9_);
        var15.HorizonCode_Horizon_È(3);
        var15.Ý(255);
        var15.Â(p_85094_2_, p_85094_4_ + p_85094_1_.Ðƒáƒ(), p_85094_6_);
        var15.Â(p_85094_2_ + var16.HorizonCode_Horizon_È * 2.0, p_85094_4_ + p_85094_1_.Ðƒáƒ() + var16.Â * 2.0, p_85094_6_ + var16.Ý * 2.0);
        var14.Â();
        GlStateManager.µÕ();
        GlStateManager.Âµá€();
        GlStateManager.Å();
        GlStateManager.ÂµÈ();
        GlStateManager.HorizonCode_Horizon_È(true);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn) {
        this.Âµá€ = worldIn;
    }
    
    public double Â(final double p_78714_1_, final double p_78714_3_, final double p_78714_5_) {
        final double var7 = p_78714_1_ - this.ÂµÈ;
        final double var8 = p_78714_3_ - this.á;
        final double var9 = p_78714_5_ - this.ˆÏ­;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public FontRenderer Ý() {
        return this.µà;
    }
    
    public void Ý(final boolean p_178632_1_) {
        this.ˆà = p_178632_1_;
    }
}
