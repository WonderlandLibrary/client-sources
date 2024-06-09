package com.client.glowclient.modules.server;

import com.client.glowclient.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class Timer extends ModuleContainer
{
    public static final NumberValue speed;
    public static BooleanValue tPSSync;
    
    @Override
    public void E() {
        Ta.M(1.0f);
    }
    
    @Override
    public void D() {
        if (!Timer.tPSSync.M()) {
            Ta.M((float)Timer.speed.k());
        }
    }
    
    static {
        final String s = "Timer";
        final String s2 = "Speed";
        final String s3 = "Game Speed";
        final double n = 1.0;
        final double n2 = 0.1;
        speed = ValueFactory.M(s, s2, s3, n, n2, n2, 25.0);
        Timer.tPSSync = ValueFactory.M("Timer", "TPSSync", "Makes game go speed of the server", false);
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        try {
            final net.minecraft.util.Timer timer = Wrapper.mc.timer;
            if (Timer.tPSSync.M()) {
                Ta.M((float)Ta.D());
            }
            if (Ta.M() / timer.tickLength == 0.0f) {
                Ta.M(0.1f);
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public String M() {
        return String.format("%.1f", Ta.M());
    }
    
    public Timer() {
        super(Category.SERVER, "Timer", false, -1, "Speeds up game time");
    }
}
