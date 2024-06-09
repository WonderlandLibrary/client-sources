package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class WorldManager implements IWorldAccess
{
    private MinecraftServer HorizonCode_Horizon_È;
    private WorldServer Â;
    private static final String Ý = "CL_00001433";
    
    public WorldManager(final MinecraftServer p_i1517_1_, final WorldServer p_i1517_2_) {
        this.HorizonCode_Horizon_È = p_i1517_1_;
        this.Â = p_i1517_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_180442_1_, final boolean p_180442_2_, final double p_180442_3_, final double p_180442_5_, final double p_180442_7_, final double p_180442_9_, final double p_180442_11_, final double p_180442_13_, final int... p_180442_15_) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity entityIn) {
        this.Â.ÇŽá€().HorizonCode_Horizon_È(entityIn);
    }
    
    @Override
    public void Â(final Entity entityIn) {
        this.Â.ÇŽá€().Â(entityIn);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String soundName, final double x, final double y, final double z, final float volume, final float pitch) {
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(x, y, z, (volume > 1.0f) ? ((double)(16.0f * volume)) : 16.0, this.Â.£à.µà(), new S29PacketSoundEffect(soundName, x, y, z, volume, pitch));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer except, final String soundName, final double x, final double y, final double z, final float volume, final float pitch) {
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(except, x, y, z, (volume > 1.0f) ? ((double)(16.0f * volume)) : 16.0, this.Â.£à.µà(), new S29PacketSoundEffect(soundName, x, y, z, volume, pitch));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BlockPos pos) {
        this.Â.Ô().HorizonCode_Horizon_È(pos);
    }
    
    @Override
    public void Â(final BlockPos pos) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_174961_1_, final BlockPos p_174961_2_) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer p_180439_1_, final int p_180439_2_, final BlockPos p_180439_3_, final int p_180439_4_) {
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(p_180439_1_, p_180439_3_.HorizonCode_Horizon_È(), p_180439_3_.Â(), p_180439_3_.Ý(), 64.0, this.Â.£à.µà(), new S28PacketEffect(p_180439_2_, p_180439_3_, p_180439_4_, false));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_180440_1_, final BlockPos p_180440_2_, final int p_180440_3_) {
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S28PacketEffect(p_180440_1_, p_180440_2_, p_180440_3_, true));
    }
    
    @Override
    public void Â(final int breakerId, final BlockPos pos, final int progress) {
        for (final EntityPlayerMP var5 : this.HorizonCode_Horizon_È.Œ().Âµá€) {
            if (var5 != null && var5.Ï­Ðƒà == this.Â && var5.ˆá() != breakerId) {
                final double var6 = pos.HorizonCode_Horizon_È() - var5.ŒÏ;
                final double var7 = pos.Â() - var5.Çªà¢;
                final double var8 = pos.Ý() - var5.Ê;
                if (var6 * var6 + var7 * var7 + var8 * var8 >= 1024.0) {
                    continue;
                }
                var5.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S25PacketBlockBreakAnim(breakerId, pos, progress));
            }
        }
    }
}
