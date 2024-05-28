package arsenic.module.impl.combat;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventPacket;
import arsenic.injection.accessor.IMixinS12PacketEntityVelocity;
import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.impl.movement.Flight;
import arsenic.module.property.PropertyInfo;
import arsenic.module.property.impl.EnumProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.utils.minecraft.PlayerUtils;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

@ModuleInfo(name = "Velocity", category = ModuleCategory.Combat)
public class Velocity extends Module {
    public final EnumProperty<vMode> veloMode = new EnumProperty<>("Mode:", vMode.Reduce);
    @PropertyInfo(reliesOn = "Mode:", value = "Reduce")
    public final DoubleProperty horizontalVelo = new DoubleProperty("Horizontal", new DoubleValue(0, 100, 80, 1));
    @PropertyInfo(reliesOn = "Mode:", value = "Reduce")
    public final DoubleProperty verticalVelo = new DoubleProperty("Vertical", new DoubleValue(0, 100, 80, 1));
    @EventLink
    public final Listener<EventPacket.Incoming.Pre> packetEvent = event -> {
        if (!PlayerUtils.isPlayerInGame()) {
            return;
        }
        if (flyDisabler()) {
            return;
        }
        switch (veloMode.getValue()) {
            case Reduce:
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    if (mc.thePlayer != null && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
                        S12PacketEntityVelocity velocityPacket = (S12PacketEntityVelocity) event.getPacket();
                        IMixinS12PacketEntityVelocity iVelocityPacket = (IMixinS12PacketEntityVelocity) velocityPacket;
                        iVelocityPacket.setMotionX((int) (velocityPacket.getMotionX() * (horizontalVelo.getValue().getInput() / 100f)));
                        iVelocityPacket.setMotionY((int) (velocityPacket.getMotionY() * (verticalVelo.getValue().getInput() / 100f)));
                        iVelocityPacket.setMotionZ((int) (velocityPacket.getMotionZ() * (horizontalVelo.getValue().getInput() / 100f)));
                    }
                }
                break;
            case Reverse:
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    if (mc.thePlayer != null && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
                        S12PacketEntityVelocity velocityPacket = (S12PacketEntityVelocity) event.getPacket();
                        IMixinS12PacketEntityVelocity iVelocityPacket = (IMixinS12PacketEntityVelocity) velocityPacket;
                        iVelocityPacket.setMotionX(velocityPacket.getMotionX() * -1);
                        iVelocityPacket.setMotionZ(velocityPacket.getMotionZ() * -1);
                    }
                }
                break;
            case Cancel:
            case Vulcan:
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    if (mc.thePlayer != null && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
                        event.cancel();
                    }
                }
                break;
            case Legit:
                if (mc.thePlayer.hurtTime == 9) {
                    if (mc.thePlayer.isBurning() && mc.thePlayer.isInWater() && mc.thePlayer.isInLava()) {
                        return;
                    }
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
                } else if (mc.thePlayer.hurtTime == 8) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), GameSettings.isKeyDown(mc.gameSettings.keyBindJump));
                }
                break;
            case Hypixel:
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    if (mc.thePlayer != null && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
                        S12PacketEntityVelocity velocityPacket = (S12PacketEntityVelocity) event.getPacket();
                        IMixinS12PacketEntityVelocity iVelocityPacket = (IMixinS12PacketEntityVelocity) velocityPacket;
                        if (mc.thePlayer.onGround) {
                            iVelocityPacket.setMotionX(0);
                            iVelocityPacket.setMotionY(velocityPacket.getMotionY());
                            iVelocityPacket.setMotionZ(0);
                        } else {
                            event.cancel();
                        }
                    }
                }
                break;
        }
    };

    @EventLink
    public final Listener<EventPacket.OutGoing> outGoingListener = event -> {
        if (veloMode.getValue() == vMode.Vulcan) {
            if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
                if (mc.thePlayer.hurtTime > 0 && event.getPacket() instanceof C0FPacketConfirmTransaction) {
                    event.setCancelled(true);
                }
            }
        }
    };

    private boolean flyDisabler() {
        return Nexus.getNexus().getModuleManager().getModuleByClass(Flight.class).isEnabled();
    }

    public enum vMode {
        Reduce,
        Cancel,
        Vulcan,
        Hypixel,
        Reverse,
        Legit
    }
}