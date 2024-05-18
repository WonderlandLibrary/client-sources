package pw.latematt.xiv.mod.mods.movement;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

import java.util.Objects;

/**
 * @author Matthew
 */
public class Sneak extends Mod implements Listener<MotionUpdateEvent> {
    public Sneak() {
        super("Sneak", ModType.MOVEMENT, Keyboard.KEY_Z, 0xFF33C452);
    }

    @Override
    public void onEventCalled(MotionUpdateEvent event) {
        boolean sneaking = mc.thePlayer.isSneaking();
        boolean moving = mc.thePlayer.movementInput.moveForward != 0;
        boolean strafing = mc.thePlayer.movementInput.moveStrafe != 0;
        boolean movingCheck = moving || strafing;
        if (Objects.equals(event.getCurrentState(), MotionUpdateEvent.State.PRE)) {
            if (movingCheck && !sneaking) {
                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
            } else {
                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            }
        } else if (Objects.equals(event.getCurrentState(), MotionUpdateEvent.State.POST)) {
            if (movingCheck && !sneaking) {
                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            }
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
        if (mc.thePlayer != null && !mc.thePlayer.isSneaking()) {
            mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
}
