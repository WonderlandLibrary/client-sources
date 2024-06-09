package com.client.glowclient.modules.player;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import com.client.glowclient.utils.*;

public class AntiAFK extends ModuleContainer
{
    private Timer L;
    public boolean A;
    public static final NumberValue hitdelay;
    public final boolean b = false;
    
    public AntiAFK() {
        final boolean b = false;
        super(Category.PLAYER, "AntiAFK", false, -1, "Swing arm to prevent being afk kicked");
        this.L = new Timer();
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final Timer timer = new Timer();
        if (!this.L.hasBeenSet()) {
            this.L.reset();
            return;
        }
        if (this.L.delay(AntiAFK.hitdelay.k() - 500.0) && this.A) {
            Wrapper.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (this.L.delay(AntiAFK.hitdelay.k())) {
            final Timer timer2 = timer;
            this.L.reset();
            this.d();
            Wrapper.mc.player.swingArm(EnumHand.MAIN_HAND);
            timer2.reset();
        }
    }
    
    @SubscribeEvent
    public void M(final InputEvent$KeyInputEvent inputEvent$KeyInputEvent) {
        if (this.L.hasBeenSet()) {
            this.L.reset();
        }
    }
    
    public void d() {
        if (this.A) {
            this.A = false;
            return;
        }
        this.A = true;
    }
    
    static {
        hitdelay = ValueFactory.M("AntiAFK", "Hitdelay", "Delay between swings in ms", 30000.0, 1.0, 0.0, 60000.0);
    }
}
