package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Map;

public class TileEntityRendererDispatcher
{
    private Map ˆÏ­;
    public static TileEntityRendererDispatcher HorizonCode_Horizon_È;
    private FontRenderer £á;
    public static double Â;
    public static double Ý;
    public static double Ø­áŒŠá;
    public TextureManager Âµá€;
    public World Ó;
    public Entity à;
    public float Ø;
    public float áŒŠÆ;
    public double áˆºÑ¢Õ;
    public double ÂµÈ;
    public double á;
    private static final String Å = "CL_00000963";
    
    static {
        TileEntityRendererDispatcher.HorizonCode_Horizon_È = new TileEntityRendererDispatcher();
    }
    
    private TileEntityRendererDispatcher() {
        (this.ˆÏ­ = Maps.newHashMap()).put(TileEntitySign.class, new TileEntitySignRenderer());
        this.ˆÏ­.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        this.ˆÏ­.put(TileEntityPiston.class, new TileEntityPistonRenderer());
        this.ˆÏ­.put(TileEntityChest.class, new TileEntityChestRenderer());
        this.ˆÏ­.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
        this.ˆÏ­.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
        this.ˆÏ­.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
        this.ˆÏ­.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
        this.ˆÏ­.put(TileEntitySkull.class, new TileEntitySkullRenderer());
        this.ˆÏ­.put(TileEntityBanner.class, new TileEntityBannerRenderer());
        for (final TileEntitySpecialRenderer var2 : this.ˆÏ­.values()) {
            var2.HorizonCode_Horizon_È(this);
        }
    }
    
    public TileEntitySpecialRenderer HorizonCode_Horizon_È(final Class p_147546_1_) {
        TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)this.ˆÏ­.get(p_147546_1_);
        if (var2 == null && p_147546_1_ != TileEntity.class) {
            var2 = this.HorizonCode_Horizon_È(p_147546_1_.getSuperclass());
            this.ˆÏ­.put(p_147546_1_, var2);
        }
        return var2;
    }
    
    public boolean HorizonCode_Horizon_È(final TileEntity p_147545_1_) {
        return this.Â(p_147545_1_) != null;
    }
    
    public TileEntitySpecialRenderer Â(final TileEntity p_147547_1_) {
        return (p_147547_1_ == null) ? null : this.HorizonCode_Horizon_È(p_147547_1_.getClass());
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final TextureManager p_178470_2_, final FontRenderer p_178470_3_, final Entity p_178470_4_, final float p_178470_5_) {
        if (this.Ó != worldIn) {
            this.HorizonCode_Horizon_È(worldIn);
        }
        this.Âµá€ = p_178470_2_;
        this.à = p_178470_4_;
        this.£á = p_178470_3_;
        this.Ø = p_178470_4_.á€ + (p_178470_4_.É - p_178470_4_.á€) * p_178470_5_;
        this.áŒŠÆ = p_178470_4_.Õ + (p_178470_4_.áƒ - p_178470_4_.Õ) * p_178470_5_;
        this.áˆºÑ¢Õ = p_178470_4_.áˆºáˆºÈ + (p_178470_4_.ŒÏ - p_178470_4_.áˆºáˆºÈ) * p_178470_5_;
        this.ÂµÈ = p_178470_4_.ÇŽá€ + (p_178470_4_.Çªà¢ - p_178470_4_.ÇŽá€) * p_178470_5_;
        this.á = p_178470_4_.Ï + (p_178470_4_.Ê - p_178470_4_.Ï) * p_178470_5_;
    }
    
    public void HorizonCode_Horizon_È(final TileEntity p_180546_1_, final float p_180546_2_, final int p_180546_3_) {
        if (p_180546_1_.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, this.ÂµÈ, this.á) < p_180546_1_.ÂµÈ()) {
            final int var4 = this.Ó.HorizonCode_Horizon_È(p_180546_1_.á(), 0);
            final int var5 = var4 % 65536;
            final int var6 = var4 / 65536;
            OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var5 / 1.0f, var6 / 1.0f);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            final BlockPos var7 = p_180546_1_.á();
            this.HorizonCode_Horizon_È(p_180546_1_, var7.HorizonCode_Horizon_È() - TileEntityRendererDispatcher.Â, var7.Â() - TileEntityRendererDispatcher.Ý, var7.Ý() - TileEntityRendererDispatcher.Ø­áŒŠá, p_180546_2_, p_180546_3_);
        }
    }
    
    public void HorizonCode_Horizon_È(final TileEntity p_147549_1_, final double p_147549_2_, final double p_147549_4_, final double p_147549_6_, final float p_147549_8_) {
        this.HorizonCode_Horizon_È(p_147549_1_, p_147549_2_, p_147549_4_, p_147549_6_, p_147549_8_, -1);
    }
    
    public void HorizonCode_Horizon_È(final TileEntity p_178469_1_, final double p_178469_2_, final double p_178469_4_, final double p_178469_6_, final float p_178469_8_, final int p_178469_9_) {
        final TileEntitySpecialRenderer var10 = this.Â(p_178469_1_);
        if (var10 != null) {
            try {
                var10.HorizonCode_Horizon_È(p_178469_1_, p_178469_2_, p_178469_4_, p_178469_6_, p_178469_8_, p_178469_9_);
            }
            catch (Throwable var12) {
                final CrashReport var11 = CrashReport.HorizonCode_Horizon_È(var12, "Rendering Block Entity");
                final CrashReportCategory var13 = var11.HorizonCode_Horizon_È("Block Entity Details");
                p_178469_1_.HorizonCode_Horizon_È(var13);
                throw new ReportedException(var11);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final World worldIn) {
        this.Ó = worldIn;
    }
    
    public FontRenderer HorizonCode_Horizon_È() {
        return this.£á;
    }
}
