package com.client.glowclient.modules.movement;

import com.client.glowclient.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class AutoWalk extends ModuleContainer
{
    private static boolean L;
    public Timer A;
    private boolean B;
    public static nB mode;
    
    @Override
    public void E() {
        if (this.B) {
            KeybindHelper.keyForward.M(false);
            KeybindHelper.keyForward.M();
            this.B = false;
        }
    }
    
    static {
        AutoWalk.mode = ValueFactory.M("AutoWalk", "Mode", "Mode of AutoWalk", "Constant", "Constant", "Pulse");
        AutoWalk.L = true;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (AutoWalk.mode.e().equals("Constant")) {
            if (!this.B) {
                KeybindHelper.keyForward.D();
                this.B = true;
            }
            if (AutoWalk.B.world.getChunk(AutoWalk.B.player.getPosition()).isLoaded()) {
                if (!KeybindHelper.keyForward.M().isKeyDown()) {
                    KeybindHelper.keyForward.M(true);
                }
            }
            else {
                KeybindHelper.keyForward.M(false);
            }
        }
        if (AutoWalk.mode.e().equals("Pulse")) {
            if (!this.A.hasBeenSet()) {
                this.A.reset();
            }
            if (!this.A.hasBeenSet()) {
                this.A.reset();
            }
            if (this.A.delay(500.0)) {
                KeybindHelper.keyForward.M(true);
            }
            if (this.A.delay(2000.0)) {
                KeybindHelper.keyForward.M(false);
                this.A.reset();
            }
        }
    }
    
    public AutoWalk() {
        final boolean b = false;
        super(Category.MOVEMENT, "AutoWalk", false, -1, "Automatically walks forward");
        this.B = b;
        this.A = new Timer();
    }
    
    @Override
    public void D() {
        AutoWalk.L = true;
    }
}
