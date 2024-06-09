package HORIZON-6-0-SKIDPROTECTION;

public class WorldServerOF extends WorldServer
{
    private MinecraftServer É;
    
    public WorldServerOF(final MinecraftServer par1MinecraftServer, final ISaveHandler par2iSaveHandler, final WorldInfo worldInfo, final int par4, final Profiler par6Profiler) {
        super(par1MinecraftServer, par2iSaveHandler, worldInfo, par4, par6Profiler);
        this.É = par1MinecraftServer;
    }
    
    @Override
    public void r_() {
        super.r_();
        if (!Config.ˆáƒ()) {
            this.ˆáƒ();
        }
        if (Config.áˆºÑ¢Õ) {
            Config.áˆºÑ¢Õ = false;
            ClearWater.HorizonCode_Horizon_È(Config.ÇªØ­(), this);
        }
    }
    
    @Override
    protected void Âµá€() {
        if (!Config.ÇŽá€()) {
            this.áˆºÏ();
        }
        super.Âµá€();
    }
    
    private void áˆºÏ() {
        if (this.Ø­à.Å() || this.Ø­à.ˆÏ­()) {
            this.Ø­à.Ó(0);
            this.Ø­à.Â(false);
            this.ÂµÈ(0.0f);
            this.Ø­à.Âµá€(0);
            this.Ø­à.HorizonCode_Horizon_È(false);
            this.áŒŠÆ(0.0f);
            this.É.Œ().HorizonCode_Horizon_È(new S2BPacketChangeGameState(2, 0.0f));
            this.É.Œ().HorizonCode_Horizon_È(new S2BPacketChangeGameState(7, 0.0f));
            this.É.Œ().HorizonCode_Horizon_È(new S2BPacketChangeGameState(8, 0.0f));
        }
    }
    
    private void ˆáƒ() {
        if (this.Ø­à.µà().HorizonCode_Horizon_È() == 1) {
            final long time = this.Ï­Ðƒà();
            final long timeOfDay = time % 24000L;
            if (Config.áˆºÏ()) {
                if (timeOfDay <= 1000L) {
                    this.HorizonCode_Horizon_È(time - timeOfDay + 1001L);
                }
                if (timeOfDay >= 11000L) {
                    this.HorizonCode_Horizon_È(time - timeOfDay + 24001L);
                }
            }
            if (Config.Œ()) {
                if (timeOfDay <= 14000L) {
                    this.HorizonCode_Horizon_È(time - timeOfDay + 14001L);
                }
                if (timeOfDay >= 22000L) {
                    this.HorizonCode_Horizon_È(time - timeOfDay + 24000L + 14001L);
                }
            }
        }
    }
}
