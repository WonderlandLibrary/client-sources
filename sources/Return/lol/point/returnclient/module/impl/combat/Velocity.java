package lol.point.returnclient.module.impl.combat;

import lol.point.Return;
import lol.point.returnclient.events.impl.packet.EventPacket;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.module.impl.movement.LongJump;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.NumberSetting;
import lol.point.returnclient.settings.impl.StringSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

@ModuleInfo(
        name = "Velocity",
        description = "alter received knockback",
        category = Category.COMBAT

)
public class Velocity extends Module {

    private final StringSetting mode = new StringSetting("Mode", new String[]{"Simple", "Reverse", "Jump reset", "Hypixel"});
    private final NumberSetting horizontal = new NumberSetting("Horizontal", 0, 0, 100, 0).hideSetting(() -> !mode.is("Simple") && !mode.is("Reverse"));
    private final NumberSetting vertical = new NumberSetting("Vertical", 0, 0, 100, 0).hideSetting(() -> !mode.is("Simple") && !mode.is("Reverse"));

    public Velocity() {
        addSettings(mode, horizontal, vertical);
    }

    public String getSuffix() {
        if (mode.is("Simple") || mode.is("Reverse")) {
            return horizontal.value.intValue() + "% " + vertical.value.intValue() + "%";
        } else {
            return mode.value;
        }
    }

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(eventPacket -> {
        if (mc.thePlayer == null || mc.theWorld == null || Return.INSTANCE.moduleManager.getByClass(LongJump.class).enabled)
            return;

        switch (mode.value) {
            case "Hypixel" -> {
                if (eventPacket.packet instanceof S12PacketEntityVelocity packet) {
                    if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                        packet.setMotionX((int) (packet.getMotionX() * (0D / 100D)));
                        packet.setMotionZ((int) (packet.getMotionZ() * (0D / 100D)));
                    }
                }
            }
            case "Simple" -> {
                if (eventPacket.packet instanceof S12PacketEntityVelocity packet) {
                    if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                        if (horizontal.value.doubleValue() == 0 && vertical.value.doubleValue() == 0) {
                            eventPacket.setCancelled(true);
                        }

                        packet.setMotionX((int) (packet.getMotionX() * (horizontal.value.doubleValue() / 100D)));
                        packet.setMotionY((int) (packet.getMotionY() * (vertical.value.doubleValue() / 100D)));
                        packet.setMotionZ((int) (packet.getMotionZ() * (horizontal.value.doubleValue() / 100D)));
                    }
                }
            }
            case "Reverse" -> {
                if (eventPacket.packet instanceof S12PacketEntityVelocity packet) {
                    if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                        if (horizontal.value.doubleValue() == 0 && vertical.value.doubleValue() == 0) {
                            eventPacket.setCancelled(true);
                        }

                        packet.setMotionX((int) (packet.getMotionX() * (-horizontal.value.doubleValue() / 100D)));
                        packet.setMotionY((int) (packet.getMotionY() * (-vertical.value.doubleValue() / 100D)));
                        packet.setMotionZ((int) (packet.getMotionZ() * (-horizontal.value.doubleValue() / 100D)));
                    }
                }
            }
            case "Jump reset" -> {
                if (eventPacket.packet instanceof S12PacketEntityVelocity s12) {
                    if (s12.getEntityID() == mc.thePlayer.getEntityId()) {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), mc.thePlayer.hurtTime == 9);
                    }
                }
            }
        }
    });

}
