package fr.dog.module.impl.combat;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketReceiveEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.module.impl.player.BreakerModule;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.player.MoveUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {

    private final ModeProperty m = ModeProperty.newInstance("Mode", new String[]{"Set", "Addition", "AirVelo", "Watchdog", "Reduce", "Old AGC"}, "Addition");
    private final BooleanProperty velo = BooleanProperty.newInstance("0/0 While Breaking", false);
    private final NumberProperty h = NumberProperty.newInstance("Horizontal", 0F, 0F, 100F, 0.1F,  () -> m.is("Set") || m.is("Addition"));
    private final NumberProperty v = NumberProperty.newInstance("Vertical", 0F, 100F, 100F, 0.1F, () -> m.is("Set") || m.is("Addition"));
    private final NumberProperty mitiga = NumberProperty.newInstance("Mitigations Fix", 0f, 3f, 8f, 1f, () -> m.is("Watchdog"));
    private final BooleanProperty noExplosion = BooleanProperty.newInstance("No Explosion", true, () -> !m.is("Strafe"));
    private final BooleanProperty dmgstrafetest = BooleanProperty.newInstance("Damage Boost", false,  () -> m.is("Set") || m.is("Addition"));
    private final BooleanProperty rando = BooleanProperty.newInstance("Dynamic", false, dmgstrafetest::getValue);
    private final NumberProperty Strafe = NumberProperty.newInstance("Damage Boost", 0.1f, 0.5f, 1f, 0.05f, dmgstrafetest::getValue);

    private int hurtCount = 0;
    private int tickCount = 0;

    public Velocity() {
        super("Velocity", ModuleCategory.COMBAT);
        this.registerProperties(m, h, v, noExplosion, dmgstrafetest, Strafe, rando, mitiga, velo);
    }

    @SubscribeEvent
    private void onPacketReceiveEvent(PacketReceiveEvent event) {
        if (m.is("Set") || m.is("Addition")) {
            this.setSuffix(String.format("%d/%d", h.getValue().intValue(), v.getValue().intValue()));
        } else {
            this.setSuffix(m.getValue());
        }

        if (event.getPacket() instanceof S12PacketEntityVelocity s12PacketEntityVelocity) {
            if (s12PacketEntityVelocity.getEntityID() == mc.thePlayer.getEntityId() && !dmgstrafetest.getValue()) {
                if (velo.getValue() && Dog.getInstance().getModuleManager().getModule(BreakerModule.class).breakPos != null) {
                    return;
                }

                switch (m.getValue()) {
                    case "Set" -> {
                        mc.thePlayer.motionX = (s12PacketEntityVelocity.getMotionX() / 8000D) * (h.getValue() / 100);
                        mc.thePlayer.motionZ = (s12PacketEntityVelocity.getMotionZ() / 8000D) * (h.getValue() / 100);
                        mc.thePlayer.motionY = (s12PacketEntityVelocity.getMotionY() / 8000D) * (v.getValue() / 100);
                    }
                    case "Addition" -> {
                        mc.thePlayer.motionX += (s12PacketEntityVelocity.getMotionX() / 8000D) * (h.getValue() / 100);
                        mc.thePlayer.motionZ += (s12PacketEntityVelocity.getMotionZ() / 8000D) * (h.getValue() / 100);
                        mc.thePlayer.motionY = (s12PacketEntityVelocity.getMotionY() / 8000D) * (v.getValue() / 100);
                    }
                    case "AirVelo" -> {
                        mc.thePlayer.motionX += (s12PacketEntityVelocity.getMotionX() / 8000D) * (h.getValue() / 100);
                        mc.thePlayer.motionZ += (s12PacketEntityVelocity.getMotionZ() / 8000D) * (h.getValue() / 100);
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = (s12PacketEntityVelocity.getMotionY() / 8000D) * (v.getValue() / 100);
                        }
                    }
                    case "Watchdog" -> {
                        hurtCount++;
                        if (hurtCount >= mitiga.getValue()) {
                            mc.thePlayer.motionY = (s12PacketEntityVelocity.getMotionY() / 8000D);
                            hurtCount = 0;
                        }
                    }
                    case "Reduce" -> {
                        mc.thePlayer.motionX *= 0.6;
                        mc.thePlayer.motionZ *= 0.6;
                    }
                    case "Old AGC" -> {
                        tickCount++;
                        if (tickCount % 2 == 0) {
                            event.setCancelled(true);
                        } else {
                            mc.thePlayer.motionX *= 0.5;
                            mc.thePlayer.motionZ *= 0.5;
                        }
                    }
                }
            }
        }

        if (event.getPacket() instanceof S27PacketExplosion && noExplosion.getValue()) {
            event.setCancelled(true);
        }
    }

    @SubscribeEvent
    private void onPublic(PlayerTickEvent event) {
        if (dmgstrafetest.getValue() && mc.thePlayer.hurtTime == 9) {
            double strafeSpeed = Strafe.getValue();

            if (rando.getValue()) {
                double randomStrafeSpeed = Math.random() * 2 + 1;
                strafeSpeed *= randomStrafeSpeed;
            }
            MoveUtil.strafe(MoveUtil.speed() * strafeSpeed);
        }
    }
}
