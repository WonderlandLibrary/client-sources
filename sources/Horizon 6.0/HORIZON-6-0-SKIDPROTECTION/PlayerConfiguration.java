package HORIZON-6-0-SKIDPROTECTION;

public class PlayerConfiguration
{
    private PlayerItemModel[] HorizonCode_Horizon_È;
    private boolean Â;
    
    public PlayerConfiguration() {
        this.HorizonCode_Horizon_È = new PlayerItemModel[0];
        this.Â = false;
    }
    
    public void HorizonCode_Horizon_È(final ModelBiped modelBiped, final AbstractClientPlayer player, final float scale, final float partialTicks) {
        if (this.Â) {
            for (int i = 0; i < this.HorizonCode_Horizon_È.length; ++i) {
                final PlayerItemModel model = this.HorizonCode_Horizon_È[i];
                model.HorizonCode_Horizon_È(modelBiped, player, scale, partialTicks);
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final boolean initialized) {
        this.Â = initialized;
    }
    
    public PlayerItemModel[] Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final PlayerItemModel playerItemModel) {
        this.HorizonCode_Horizon_È = (PlayerItemModel[])Config.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, playerItemModel);
    }
}
