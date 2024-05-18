package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Callable;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import java.util.Map;
import java.util.Random;
import java.util.List;

public class EffectRenderer
{
    private static final ResourceLocation_1975012498 Â;
    protected World HorizonCode_Horizon_È;
    private List[][] Ý;
    private List Ø­áŒŠá;
    private TextureManager Âµá€;
    private Random Ó;
    private Map à;
    private static final String Ø = "CL_00000915";
    
    static {
        Â = new ResourceLocation_1975012498("textures/particle/particles.png");
    }
    
    public EffectRenderer(final World worldIn, final TextureManager p_i1220_2_) {
        this.Ý = new List[4][];
        this.Ø­áŒŠá = Lists.newArrayList();
        this.Ó = new Random();
        this.à = Maps.newHashMap();
        this.HorizonCode_Horizon_È = worldIn;
        this.Âµá€ = p_i1220_2_;
        for (int var3 = 0; var3 < 4; ++var3) {
            this.Ý[var3] = new List[2];
            for (int var4 = 0; var4 < 2; ++var4) {
                this.Ý[var3][var4] = Lists.newArrayList();
            }
        }
        this.Ý();
    }
    
    private void Ý() {
        this.HorizonCode_Horizon_È(EnumParticleTypes.HorizonCode_Horizon_È.Ý(), new EntityExplodeFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€.Ý(), new EntityBubbleFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Ó.Ý(), new EntitySplashFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.à.Ý(), new EntityFishWakeFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.¥à.Ý(), new EntityRainFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Ø.Ý(), new EntitySuspendFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠÆ.Ý(), new EntityAuraFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.áˆºÑ¢Õ.Ý(), new EntityCrit2FX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.ÂµÈ.Ý(), new EntityCrit2FX.Â());
        this.HorizonCode_Horizon_È(EnumParticleTypes.á.Ý(), new EntitySmokeFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­.Ý(), new EntityCritFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.£á.Ý(), new EntitySpellParticleFX.Â());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Å.Ý(), new EntitySpellParticleFX.Ý());
        this.HorizonCode_Horizon_È(EnumParticleTypes.£à.Ý(), new EntitySpellParticleFX.Ø­áŒŠá());
        this.HorizonCode_Horizon_È(EnumParticleTypes.µà.Ý(), new EntitySpellParticleFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.ˆà.Ý(), new EntitySpellParticleFX.Âµá€());
        this.HorizonCode_Horizon_È(EnumParticleTypes.¥Æ.Ý(), new EntityDropParticleFX.Â());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Ø­à.Ý(), new EntityDropParticleFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.µÕ.Ý(), new EntityHeartFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Æ.Ý(), new EntityAuraFX.Â());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Šáƒ.Ý(), new EntityAuraFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Ï­Ðƒà.Ý(), new EntityNoteFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà.Ý(), new EntityPortalFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.ŠÄ.Ý(), new EntityEnchantmentTableParticleFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Ñ¢á.Ý(), new EntityFlameFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.ŒÏ.Ý(), new EntityLavaFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Çªà¢.Ý(), new EntityFootStepFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Ê.Ý(), new EntityCloudFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.ÇŽÉ.Ý(), new EntityReddustFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.ˆá.Ý(), new EntityBreakingFX.Ý());
        this.HorizonCode_Horizon_È(EnumParticleTypes.ÇŽÕ.Ý(), new EntitySnowShovelFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.É.Ý(), new EntityBreakingFX.Â());
        this.HorizonCode_Horizon_È(EnumParticleTypes.áƒ.Ý(), new EntityHeartFX.Â());
        this.HorizonCode_Horizon_È(EnumParticleTypes.á€.Ý(), new Barrier.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Õ.Ý(), new EntityBreakingFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.à¢.Ý(), new EntityDiggingFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.ŠÂµà.Ý(), new EntityBlockDustFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Ý.Ý(), new EntityHugeExplodeFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Â.Ý(), new EntityLargeExplodeFX.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Ø­áŒŠá.Ý(), new EntityFireworkStarterFX_Factory());
        this.HorizonCode_Horizon_È(EnumParticleTypes.Ç.Ý(), new MobAppearance.HorizonCode_Horizon_È());
    }
    
    public void HorizonCode_Horizon_È(final int p_178929_1_, final IParticleFactory p_178929_2_) {
        this.à.put(p_178929_1_, p_178929_2_);
    }
    
    public void HorizonCode_Horizon_È(final Entity p_178926_1_, final EnumParticleTypes p_178926_2_) {
        this.Ø­áŒŠá.add(new EntityParticleEmitter(this.HorizonCode_Horizon_È, p_178926_1_, p_178926_2_));
    }
    
    public EntityFX HorizonCode_Horizon_È(final int p_178927_1_, final double p_178927_2_, final double p_178927_4_, final double p_178927_6_, final double p_178927_8_, final double p_178927_10_, final double p_178927_12_, final int... p_178927_14_) {
        final IParticleFactory var15 = this.à.get(p_178927_1_);
        if (var15 != null) {
            final EntityFX var16 = var15.HorizonCode_Horizon_È(p_178927_1_, this.HorizonCode_Horizon_È, p_178927_2_, p_178927_4_, p_178927_6_, p_178927_8_, p_178927_10_, p_178927_12_, p_178927_14_);
            if (var16 != null) {
                this.HorizonCode_Horizon_È(var16);
                return var16;
            }
        }
        return null;
    }
    
    public void HorizonCode_Horizon_È(final EntityFX p_78873_1_) {
        final int var2 = p_78873_1_.Ø­áŒŠá();
        final int var3 = (p_78873_1_.áŒŠÆ() == 1.0f) ? 1 : 0;
        if (this.Ý[var2][var3].size() >= 4000) {
            this.Ý[var2][var3].remove(0);
        }
        this.Ý[var2][var3].add(p_78873_1_);
    }
    
    public void HorizonCode_Horizon_È() {
        for (int var1 = 0; var1 < 4; ++var1) {
            this.HorizonCode_Horizon_È(var1);
        }
        final ArrayList var2 = Lists.newArrayList();
        for (final EntityParticleEmitter var4 : this.Ø­áŒŠá) {
            var4.á();
            if (var4.ˆáŠ) {
                var2.add(var4);
            }
        }
        this.Ø­áŒŠá.removeAll(var2);
    }
    
    private void HorizonCode_Horizon_È(final int p_178922_1_) {
        for (int var2 = 0; var2 < 2; ++var2) {
            this.HorizonCode_Horizon_È(this.Ý[p_178922_1_][var2]);
        }
    }
    
    private void HorizonCode_Horizon_È(final List p_178925_1_) {
        final ArrayList var2 = Lists.newArrayList();
        for (int var3 = 0; var3 < p_178925_1_.size(); ++var3) {
            final EntityFX var4 = p_178925_1_.get(var3);
            this.Ø­áŒŠá(var4);
            if (var4.ˆáŠ) {
                var2.add(var4);
            }
        }
        p_178925_1_.removeAll(var2);
    }
    
    private void Ø­áŒŠá(final EntityFX p_178923_1_) {
        try {
            p_178923_1_.á();
        }
        catch (Throwable var4) {
            final CrashReport var3 = CrashReport.HorizonCode_Horizon_È(var4, "Ticking Particle");
            final CrashReportCategory var5 = var3.HorizonCode_Horizon_È("Particle being ticked");
            final int var6 = p_178923_1_.Ø­áŒŠá();
            var5.HorizonCode_Horizon_È("Particle", new Callable() {
                private static final String Â = "CL_00000916";
                
                public String HorizonCode_Horizon_È() {
                    return p_178923_1_.toString();
                }
            });
            var5.HorizonCode_Horizon_È("Particle Type", new Callable() {
                private static final String Â = "CL_00000917";
                
                public String HorizonCode_Horizon_È() {
                    return (var6 == 0) ? "MISC_TEXTURE" : ((var6 == 1) ? "TERRAIN_TEXTURE" : ((var6 == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + var6)));
                }
            });
            throw new ReportedException(var3);
        }
    }
    
    public void HorizonCode_Horizon_È(final Entity p_78874_1_, final float p_78874_2_) {
        final float var3 = ActiveRenderInfo.Â();
        final float var4 = ActiveRenderInfo.Ø­áŒŠá();
        final float var5 = ActiveRenderInfo.Âµá€();
        final float var6 = ActiveRenderInfo.Ó();
        final float var7 = ActiveRenderInfo.Ý();
        EntityFX.Å = p_78874_1_.áˆºáˆºÈ + (p_78874_1_.ŒÏ - p_78874_1_.áˆºáˆºÈ) * p_78874_2_;
        EntityFX.£à = p_78874_1_.ÇŽá€ + (p_78874_1_.Çªà¢ - p_78874_1_.ÇŽá€) * p_78874_2_;
        EntityFX.µà = p_78874_1_.Ï + (p_78874_1_.Ê - p_78874_1_.Ï) * p_78874_2_;
        GlStateManager.á();
        GlStateManager.Â(770, 771);
        GlStateManager.HorizonCode_Horizon_È(516, 0.003921569f);
        for (int var8_nf = 0; var8_nf < 3; ++var8_nf) {
            final int var8 = var8_nf;
            for (int var9 = 0; var9 < 2; ++var9) {
                if (!this.Ý[var8][var9].isEmpty()) {
                    switch (var9) {
                        case 0: {
                            GlStateManager.HorizonCode_Horizon_È(false);
                            break;
                        }
                        case 1: {
                            GlStateManager.HorizonCode_Horizon_È(true);
                            break;
                        }
                    }
                    switch (var8) {
                        default: {
                            this.Âµá€.HorizonCode_Horizon_È(EffectRenderer.Â);
                            break;
                        }
                        case 1: {
                            this.Âµá€.HorizonCode_Horizon_È(TextureMap.à);
                            break;
                        }
                    }
                    GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                    final Tessellator var10 = Tessellator.HorizonCode_Horizon_È();
                    final WorldRenderer var11 = var10.Ý();
                    var11.Â();
                    for (int var12 = 0; var12 < this.Ý[var8][var9].size(); ++var12) {
                        final EntityFX var13 = this.Ý[var8][var9].get(var12);
                        var11.Â(var13.HorizonCode_Horizon_È(p_78874_2_));
                        try {
                            var13.HorizonCode_Horizon_È(var11, p_78874_1_, p_78874_2_, var3, var7, var4, var5, var6);
                        }
                        catch (Throwable var15) {
                            final CrashReport var14 = CrashReport.HorizonCode_Horizon_È(var15, "Rendering Particle");
                            final CrashReportCategory var16 = var14.HorizonCode_Horizon_È("Particle being rendered");
                            var16.HorizonCode_Horizon_È("Particle", new Callable() {
                                private static final String Â = "CL_00000918";
                                
                                public String HorizonCode_Horizon_È() {
                                    return var13.toString();
                                }
                            });
                            var16.HorizonCode_Horizon_È("Particle Type", new Callable() {
                                private static final String Â = "CL_00000919";
                                
                                public String HorizonCode_Horizon_È() {
                                    return (var8 == 0) ? "MISC_TEXTURE" : ((var8 == 1) ? "TERRAIN_TEXTURE" : ((var8 == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + var8)));
                                }
                            });
                            throw new ReportedException(var14);
                        }
                    }
                    var10.Â();
                }
            }
        }
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.ÂµÈ();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
    }
    
    public void Â(final Entity p_78872_1_, final float p_78872_2_) {
        final float var3 = 0.017453292f;
        final float var4 = MathHelper.Â(p_78872_1_.É * 0.017453292f);
        final float var5 = MathHelper.HorizonCode_Horizon_È(p_78872_1_.É * 0.017453292f);
        final float var6 = -var5 * MathHelper.HorizonCode_Horizon_È(p_78872_1_.áƒ * 0.017453292f);
        final float var7 = var4 * MathHelper.HorizonCode_Horizon_È(p_78872_1_.áƒ * 0.017453292f);
        final float var8 = MathHelper.Â(p_78872_1_.áƒ * 0.017453292f);
        for (int var9 = 0; var9 < 2; ++var9) {
            final List var10 = this.Ý[3][var9];
            if (!var10.isEmpty()) {
                final Tessellator var11 = Tessellator.HorizonCode_Horizon_È();
                final WorldRenderer var12 = var11.Ý();
                for (int var13 = 0; var13 < var10.size(); ++var13) {
                    final EntityFX var14 = var10.get(var13);
                    var12.Â(var14.HorizonCode_Horizon_È(p_78872_2_));
                    var14.HorizonCode_Horizon_È(var12, p_78872_1_, p_78872_2_, var4, var8, var5, var6, var7);
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final World worldIn) {
        this.HorizonCode_Horizon_È = worldIn;
        for (int var2 = 0; var2 < 4; ++var2) {
            for (int var3 = 0; var3 < 2; ++var3) {
                this.Ý[var2][var3].clear();
            }
        }
        this.Ø­áŒŠá.clear();
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_180533_1_, IBlockState p_180533_2_) {
        if (p_180533_2_.Ý().Ó() != Material.HorizonCode_Horizon_È) {
            p_180533_2_ = p_180533_2_.Ý().HorizonCode_Horizon_È(p_180533_2_, this.HorizonCode_Horizon_È, p_180533_1_);
            final byte var3 = 4;
            for (int var4 = 0; var4 < var3; ++var4) {
                for (int var5 = 0; var5 < var3; ++var5) {
                    for (int var6 = 0; var6 < var3; ++var6) {
                        final double var7 = p_180533_1_.HorizonCode_Horizon_È() + (var4 + 0.5) / var3;
                        final double var8 = p_180533_1_.Â() + (var5 + 0.5) / var3;
                        final double var9 = p_180533_1_.Ý() + (var6 + 0.5) / var3;
                        this.HorizonCode_Horizon_È(new EntityDiggingFX(this.HorizonCode_Horizon_È, var7, var8, var9, var7 - p_180533_1_.HorizonCode_Horizon_È() - 0.5, var8 - p_180533_1_.Â() - 0.5, var9 - p_180533_1_.Ý() - 0.5, p_180533_2_).HorizonCode_Horizon_È(p_180533_1_));
                    }
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_180532_1_, final EnumFacing p_180532_2_) {
        final IBlockState var3 = this.HorizonCode_Horizon_È.Â(p_180532_1_);
        final Block var4 = var3.Ý();
        if (var4.ÂµÈ() != -1) {
            final int var5 = p_180532_1_.HorizonCode_Horizon_È();
            final int var6 = p_180532_1_.Â();
            final int var7 = p_180532_1_.Ý();
            final float var8 = 0.1f;
            double var9 = var5 + this.Ó.nextDouble() * (var4.¥Æ() - var4.ˆà() - var8 * 2.0f) + var8 + var4.ˆà();
            double var10 = var6 + this.Ó.nextDouble() * (var4.µÕ() - var4.Ø­à() - var8 * 2.0f) + var8 + var4.Ø­à();
            double var11 = var7 + this.Ó.nextDouble() * (var4.Šáƒ() - var4.Æ() - var8 * 2.0f) + var8 + var4.Æ();
            if (p_180532_2_ == EnumFacing.HorizonCode_Horizon_È) {
                var10 = var6 + var4.Ø­à() - var8;
            }
            if (p_180532_2_ == EnumFacing.Â) {
                var10 = var6 + var4.µÕ() + var8;
            }
            if (p_180532_2_ == EnumFacing.Ý) {
                var11 = var7 + var4.Æ() - var8;
            }
            if (p_180532_2_ == EnumFacing.Ø­áŒŠá) {
                var11 = var7 + var4.Šáƒ() + var8;
            }
            if (p_180532_2_ == EnumFacing.Âµá€) {
                var9 = var5 + var4.ˆà() - var8;
            }
            if (p_180532_2_ == EnumFacing.Ó) {
                var9 = var5 + var4.¥Æ() + var8;
            }
            this.HorizonCode_Horizon_È(new EntityDiggingFX(this.HorizonCode_Horizon_È, var9, var10, var11, 0.0, 0.0, 0.0, var3).HorizonCode_Horizon_È(p_180532_1_).Ý(0.2f).Ø­áŒŠá(0.6f));
        }
    }
    
    public void Â(final EntityFX p_178928_1_) {
        this.HorizonCode_Horizon_È(p_178928_1_, 1, 0);
    }
    
    public void Ý(final EntityFX p_178931_1_) {
        this.HorizonCode_Horizon_È(p_178931_1_, 0, 1);
    }
    
    private void HorizonCode_Horizon_È(final EntityFX p_178924_1_, final int p_178924_2_, final int p_178924_3_) {
        for (int var4 = 0; var4 < 4; ++var4) {
            if (this.Ý[var4][p_178924_2_].contains(p_178924_1_)) {
                this.Ý[var4][p_178924_2_].remove(p_178924_1_);
                this.Ý[var4][p_178924_3_].add(p_178924_1_);
            }
        }
    }
    
    public String Â() {
        int var1 = 0;
        for (int var2 = 0; var2 < 4; ++var2) {
            for (int var3 = 0; var3 < 2; ++var3) {
                var1 += this.Ý[var2][var3].size();
            }
        }
        return new StringBuilder().append(var1).toString();
    }
}
