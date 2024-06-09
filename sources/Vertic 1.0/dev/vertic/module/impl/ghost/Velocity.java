package dev.vertic.module.impl.ghost;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.packet.PacketReceiveEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.ModeSetting;
import dev.vertic.setting.impl.NumberSetting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Velocity extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Simple", "Reverse", "Simple");
    private final NumberSetting horizontal = new NumberSetting("Horizontal", 0, 0, 100, 2);
    private final NumberSetting vertical = new NumberSetting("Vertical", 100, 0, 100, 2);

    public Velocity() {
        super("Velocity", "Reduces your knockback.", Category.GHOST);
        this.addSettings(mode, horizontal, vertical);
    }

    @EventLink
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                switch (mode.getMode().toLowerCase()) {
                    case "simple":
                        double horizontalMult = horizontal.getValue() / 100.0;
                        double verticalMult = vertical.getValue() / 100.0;

                        if(horizontalMult == 0) {
                            event.setCancelled(true);

                            if(verticalMult > 0) {
                                mc.thePlayer.motionY = (packet.getMotionY() * verticalMult) / 8000.0;
                            }
                        } else {
                            packet.setMotionX((int) (packet.getMotionX() * horizontalMult));
                            packet.setMotionZ((int) (packet.getMotionZ() * horizontalMult));
                            packet.setMotionY((int) (packet.getMotionY() * verticalMult));
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
