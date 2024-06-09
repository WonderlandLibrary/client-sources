/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.FoodStats;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.Player;
import winter.utils.other.Timer;

public class Sprint
extends Module {
    private Timer timer;

    public Sprint() {
        super("Sprint", Module.Category.Movement, -7271);
        this.setBind(25);
        this.timer = new Timer();
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (this.canSprint() && event.isPre()) {
            this.mc.thePlayer.setSprinting(true);
        }
    }

    private boolean canSprint() {
        if (this.mc.thePlayer.isCollidedHorizontally) {
            return false;
        }
        if (this.mc.thePlayer.getFoodStats().getFoodLevel() <= 6) {
            return false;
        }
        if (!Player.isMoving()) {
            return false;
        }
        return true;
    }
}

