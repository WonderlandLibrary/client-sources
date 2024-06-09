package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Constructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBridge extends RealmsScreen
{
    private static final Logger £á;
    private GuiScreen Å;
    private static final String £à = "CL_00001869";
    
    static {
        £á = LogManager.getLogger();
    }
    
    public void HorizonCode_Horizon_È(final GuiScreen p_switchToRealms_1_) {
        this.Å = p_switchToRealms_1_;
        try {
            final Class var2 = Class.forName("com.mojang.realmsclient.RealmsMainScreen");
            final Constructor var3 = var2.getDeclaredConstructor(RealmsScreen.class);
            var3.setAccessible(true);
            final Object var4 = var3.newInstance(this);
            Minecraft.áŒŠà().HorizonCode_Horizon_È(((RealmsScreen)var4).HorizonCode_Horizon_È());
        }
        catch (Exception var5) {
            RealmsBridge.£á.error("Realms module missing", (Throwable)var5);
        }
    }
    
    @Override
    public void init() {
        Minecraft.áŒŠà().HorizonCode_Horizon_È(this.Å);
    }
}
