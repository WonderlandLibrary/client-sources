package wtf.diablo.client.module.impl.movement.velocity;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

@ModuleMetaData(
        name = "Velocity",
        description = "Reduces knockback",
        category = ModuleCategoryEnum.MOVEMENT
)
public final class VelocityModule extends AbstractModule {

    private final ModeSetting<EnumVelocityMode> enumVelocityModeModeSetting = new ModeSetting<>("Velocity Mode", EnumVelocityMode.WATCHDOG);

    private final NumberSetting<Double> horizontal = new NumberSetting<>("Horizontal", 100.0, 0.0, 600.0, 1.0);
    private final NumberSetting<Double> vertical = new NumberSetting<>("Vertical", 100.0, 0.0, 600.0, 1.0);


    public VelocityModule()
    {
        this.registerSettings(
                this.enumVelocityModeModeSetting,
                this.horizontal,
                this.vertical
        );
    }

    @EventHandler
    private final Listener<RecievePacketEvent> eventRecievePacketListener = event -> {
        this.setSuffix(enumVelocityModeModeSetting.getValue().getName());

        switch (enumVelocityModeModeSetting.getValue()) {
            case WATCHDOG:
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) event.getPacket();
                    if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                        boolean shouldBypass = mc.thePlayer.onGround;

                        if (mc.thePlayer.isCollided) {
                            shouldBypass = true;
                        }

                        if (shouldBypass) {
                            mc.thePlayer.motionX = 0;
                            mc.thePlayer.motionY = 0;
                            mc.thePlayer.motionZ = 0;
                            event.setCancelled(true);
                        } else {
                            if (horizontal.getValue() != 100.0 || vertical.getValue() != 100.0) {
                                wrapper.setMotionX(wrapper.getMotionX() * horizontal.getValue().intValue() / 100);
                                wrapper.setMotionZ(wrapper.getMotionZ() * horizontal.getValue().intValue() / 100);
                                wrapper.setMotionY(wrapper.getMotionY() * vertical.getValue().intValue() / 100);
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    }

                }
                break;
            case PERCENT:
                this.setSuffix(horizontal.getValue() + "% " + vertical.getValue() + "%");
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) event.getPacket();
                    if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                        if (horizontal.getValue() != 100.0 || vertical.getValue() != 100.0) {
                            wrapper.setMotionX(wrapper.getMotionX() * horizontal.getValue().intValue() / 100);
                            wrapper.setMotionZ(wrapper.getMotionZ() * horizontal.getValue().intValue() / 100);
                            wrapper.setMotionY(wrapper.getMotionY() * vertical.getValue().intValue() / 100);
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
                break;
        }
    };
}