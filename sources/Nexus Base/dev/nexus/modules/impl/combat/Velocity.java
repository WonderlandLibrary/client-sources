package dev.nexus.modules.impl.combat;

import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventLiving;
import dev.nexus.events.impl.EventPacketReceive;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.setting.ModeSetting;
import dev.nexus.modules.setting.NumberSetting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Velocity extends Module {
    public final ModeSetting veloMode = new ModeSetting("Mode", "Packet", "Packet", "Watchdog");
    public final NumberSetting horizontal = new NumberSetting("Horizontal", 0, 100, 85, 1);
    public final NumberSetting vertical = new NumberSetting("Vertical", 0, 100, 85, 1);

    public Velocity() {
        super("Velocity", 0, ModuleCategory.COMBAT);
        this.addSettings(veloMode, horizontal, vertical);
        horizontal.setDependency(veloMode, "Packet");
        vertical.setDependency(veloMode, "Packet");
    }

    @EventLink
    public final Listener<EventLiving> eventLivingListener = event -> {
        this.setSuffix(veloMode.getMode());
    };

    @EventLink
    public final Listener<EventPacketReceive> eventPacketReceiveListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getPacket() instanceof S12PacketEntityVelocity s12) {
            if (s12.getEntityID() == mc.thePlayer.getEntityId()) {
                switch (veloMode.getMode()) {
                    case "Packet":
                        s12.motionY = (int) (s12.getMotionY() * (vertical.getValue() / 100));
                        s12.motionX = (int) (s12.getMotionX() * (horizontal.getValue() / 100));
                        s12.motionZ = (int) (s12.getMotionZ() * (horizontal.getValue() / 100));
                        break;
                    case "Watchdog":
                        if (mc.thePlayer.offGroundTicks <= 5) {
                            mc.thePlayer.motionY = s12.getMotionY() / 8000D;
                        }
                        event.cancel();
                        break;
                }
            }
        }
    };
}
