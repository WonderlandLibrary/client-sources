package club.strifeclient.module.implementations.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.networking.PacketInboundEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

import java.util.function.Supplier;

@ModuleInfo(name = "Velocity", description = "Change how much knockback you take.", category = Category.PLAYER)
public final class Velocity extends Module {
    private final ModeSetting<VelocityMode> modeSetting = new ModeSetting<>("Mode", VelocityMode.WATCHDOG);
    private final DoubleSetting horizontalSetting = new DoubleSetting("Horizontal", 100, 0, 100, 1, () -> modeSetting.getValue() == VelocityMode.WATCHDOG);
    private final DoubleSetting verticalSetting = new DoubleSetting("Vertical", 100, 0, 100, 1, () -> modeSetting.getValue() == VelocityMode.WATCHDOG);

    @Override
    public Supplier<Object> getSuffix() {
        return () -> horizontalSetting.getInt() + "% " + verticalSetting.getInt() + "%";
    }

    @EventHandler
    private final Listener<PacketInboundEvent> packetInboundEventListener = e -> {
        switch (modeSetting.getValue()) {
            case WATCHDOG: {
                if (e.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity velocity = e.getPacket();
                    if (mc.theWorld != null && mc.thePlayer != null && velocity.getEntityID() == mc.thePlayer.getEntityId()) {
                        if (horizontalSetting.getInt() == 0 && verticalSetting.getInt() == 0)
                            e.setCancelled(true);
                        velocity.setMotionX(velocity.getMotionX() * (horizontalSetting.getInt() / 100));
                        velocity.setMotionY(velocity.getMotionY() * (verticalSetting.getInt() / 100));
                        velocity.setMotionZ(velocity.getMotionZ() * (horizontalSetting.getInt() / 100));
                    }
                }
                if (e.getPacket() instanceof S27PacketExplosion) {
                    S27PacketExplosion explosion = e.getPacket();
                    if (mc.theWorld != null && mc.thePlayer != null) {
                        if (horizontalSetting.getInt() == 0 && verticalSetting.getInt() == 0)
                            e.setCancelled(true);
                        explosion.setX(explosion.getX() * (horizontalSetting.getInt() / 100f));
                        explosion.setY(explosion.getY() * (verticalSetting.getInt() / 100f));
                        explosion.setZ(explosion.getZ() * (horizontalSetting.getInt()  / 100f));
                    }
                }
                break;
            }
        }
    };

    public enum VelocityMode implements SerializableEnum {
        WATCHDOG("Watchdog");
        private final String name;

        VelocityMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
