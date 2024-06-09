package com.client.glowclient.modules.server;

import net.minecraft.client.multiplayer.*;
import com.client.glowclient.modules.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;

public class AutoReconnect extends ModuleContainer
{
    public static ServerData A;
    public static final NumberValue delay;
    public static boolean b;
    
    public AutoReconnect() {
        super(Category.SERVER, "AutoReconnect", false, -1, "Automatically reconnects to the server");
    }
    
    @SubscribeEvent
    public void M(final WorldEvent$Load worldEvent$Load) {
        AutoReconnect.b = false;
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    static {
        AutoReconnect.b = false;
        delay = ValueFactory.M("AutoReconnect", "Delay", "Reconnect glideSpeed", 5.0, 0.5, 0.0, 30.0);
    }
}
