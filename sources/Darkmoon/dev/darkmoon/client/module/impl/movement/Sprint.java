package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.event.packet.EventSendPacket;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.network.play.client.CPacketEntityAction;

import java.util.Arrays;

@ModuleAnnotation(name = "Sprint", category = Category.MOVEMENT)
public class Sprint extends Module {
    public static MultiBooleanSetting sprintModes = new MultiBooleanSetting("Sprint-Mode", Arrays.asList("Auto", "Keep"));
    public static BooleanSetting omnidirectional = new BooleanSetting("All-Direction", false);

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (sprintModes.get(0)) {
            if (mc.player == null) return;
            if (mc.player.getFoodStats().getFoodLevel() > 6 && !mc.player.isSneaking() && !mc.player.collidedHorizontally)
                mc.player.setSprinting(omnidirectional.get() ? MovementUtility.isMoving() : mc.player.movementInput.forwardKeyDown);
        }
    }
    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (omnidirectional.get() && sprintModes.get(0)) {
            if (event.getPacket() instanceof CPacketEntityAction) {
                CPacketEntityAction packet = (CPacketEntityAction) event.getPacket();
                if (packet.getAction() == CPacketEntityAction.Action.START_SPRINTING) {
                    event.setCancelled(true);
                }
                if (packet.getAction() == CPacketEntityAction.Action.STOP_SPRINTING) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.player == null) return;
        mc.player.setSprinting(false);
    }
}
