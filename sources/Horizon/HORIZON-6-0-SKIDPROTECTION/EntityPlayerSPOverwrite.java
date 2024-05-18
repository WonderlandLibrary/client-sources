package HORIZON-6-0-SKIDPROTECTION;

public class EntityPlayerSPOverwrite extends EntityPlayerSP
{
    public EntityPlayerSPOverwrite(final Minecraft minecraft, final World world, final NetHandlerPlayClient networkManager, final StatFileWriter stats) {
        super(minecraft, world, networkManager, stats);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double x, final double y, final double z) {
        final EventMovementSpeed event = new EventMovementSpeed();
        event.HorizonCode_Horizon_È(x, y, z);
        super.HorizonCode_Horizon_È(event.Ý(), event.Ø­áŒŠá(), event.Âµá€());
    }
    
    @Override
    protected boolean Â(final double x, final double y, final double z) {
        final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
        final boolean phase = ModuleManager.HorizonCode_Horizon_È(Phase.class).áˆºÑ¢Õ() || this.ÇªÓ;
        return !phase && super.Â(x, y, z);
    }
}
