package com.client.glowclient.modules.combat;

import com.client.glowclient.events.*;
import com.client.glowclient.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class BowSpam extends ModuleContainer
{
    public static final NumberValue speed;
    public static final BooleanValue tpsSync;
    private boolean b;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final double k = BowSpam.speed.k();
        final double n = BowSpam.tpsSync.M() ? (1.0 / Ta.D()) : 1.0;
        if (tA.A(Wrapper.mc.player.getHeldItemMainhand())) {
            if (Wrapper.mc.gameSettings.keyBindAttack.isKeyDown()) {
                this.b = true;
                if (!this.G.hasBeenSet()) {
                    this.G.reset();
                }
                if (this.G.delay(0.0)) {
                    KeybindHelper.keyUseItem.M(true);
                }
                if (this.G.delay(k * n)) {
                    this.G.reset();
                    KeybindHelper.keyUseItem.M(false);
                }
            }
            else if (this.b) {
                KeybindHelper.keyUseItem.M(false);
                this.b = false;
            }
        }
    }
    
    public BowSpam() {
        final boolean b = false;
        super(Category.COMBAT, "BowSpam", false, -1, "Spams the bow at a select speed");
        this.b = b;
    }
    
    static {
        speed = ValueFactory.M("BowSpam", "Speed", "Speed of bow spam", 200.0, 50.0, 100.0, 1200.0);
        tpsSync = ValueFactory.M("BowSpam", "TpsSync", "Syncs bow spam to tps", false);
    }
}
