package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class BaseSpectatorGroup implements ISpectatorMenuView
{
    private final List HorizonCode_Horizon_È;
    private static final String Â = "CL_00001928";
    
    public BaseSpectatorGroup() {
        (this.HorizonCode_Horizon_È = Lists.newArrayList()).add(new TeleportToPlayer());
        this.HorizonCode_Horizon_È.add(new TeleportToTeam());
    }
    
    @Override
    public List HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public IChatComponent Â() {
        return new ChatComponentText("Press a key to select a command, and again to use it.");
    }
}
