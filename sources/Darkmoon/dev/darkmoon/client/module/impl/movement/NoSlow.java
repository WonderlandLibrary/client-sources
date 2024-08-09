package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;

@ModuleAnnotation(name = "NoSlow", category = Category.MOVEMENT)
public class NoSlow extends Module {
    public final ModeSetting mode = new ModeSetting("Mode", "SunRise", "SunRise", "Matrix", "Grim");

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.isHandActive()) {
            if (mc.player.onGround) {
                if (mc.player.ticksExisted % 2 == 0) {
                    if (mode.is("Matrix")) {
                        mc.player.motionX *= mc.player.moveStrafing == 0 ? 0.55 : 0.5;
                        mc.player.motionZ *= mc.player.moveStrafing == 0 ? 0.55 : 0.5;
                    } else if (mode.is("SunRise")) {
                        mc.player.motionX *= 0.83;
                        mc.player.motionZ *= 0.83;
                    }
                }
            } else if (mode.is("SunRise")) {
                mc.player.motionX *= 0.83;
                mc.player.motionZ *= 0.83;
            } else if (mc.player.fallDistance > (mode.is("Matrix") ? 0.7 : 0.2)) {
                if (mode.is("Matrix")) {
                    mc.player.motionX *= 0.93;
                    mc.player.motionZ *= 0.93;
                }
            }
        }
    }
    public boolean isNeeded() {
        return !mode.get().equals("Grim") || mc.player.getActiveHand() == EnumHand.OFF_HAND;
    }
}
