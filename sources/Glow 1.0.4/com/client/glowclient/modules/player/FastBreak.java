package com.client.glowclient.modules.player;

import com.client.glowclient.events.*;
import com.client.glowclient.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class FastBreak extends ModuleContainer
{
    public static BooleanValue fast;
    public static BooleanValue delayTimer;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        try {
            if (FastBreak.fast.M() && Wrapper.mc.playerController.getIsHittingBlock() && this.k() && !ModuleManager.M("Nuker").k() && ModuleManager.M("Nuker") != null) {
                HB.M(Wrapper.mc.objectMouseOver.getBlockPos());
            }
            if (FastBreak.delayTimer.M()) {
                Wrapper.mc.playerController.blockHitDelay = 0;
            }
        }
        catch (Exception ex) {}
    }
    
    static {
        FastBreak.delayTimer = ValueFactory.M("FastBreak", "DelayTimer", "Removes hit glideSpeed timer", true);
        FastBreak.fast = ValueFactory.M("FastBreak", "Fast", "Breaks blocks fast", true);
    }
    
    public FastBreak() {
        super(Category.PLAYER, "FastBreak", false, -1, "Break blocks faster");
    }
}
