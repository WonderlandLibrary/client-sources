package lol.point.returnclient.module.impl.misc;

import lol.point.returnclient.events.impl.packet.EventPacket;
import lol.point.returnclient.events.impl.player.EventMotion;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.StringSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

@ModuleInfo(
        name = "Disabler",
        description = "disables anticheats or parts of them",
        category = Category.MISC
)
public class Disabler extends Module {

    private final StringSetting mode = new StringSetting("Mode", new String[]{"Custom", "Vulcan"});

    private final BooleanSetting c0f = new BooleanSetting("Cancel C0F", false).hideSetting(() -> !mode.is("Custom"));
    private final BooleanSetting c00 = new BooleanSetting("Cancel C00", false).hideSetting(() -> !mode.is("Custom"));
    private final BooleanSetting c0b = new BooleanSetting("Cancel C0B", false).hideSetting(() -> !mode.is("Custom"));
    private final BooleanSetting c0c = new BooleanSetting("Cancel C0C", false).hideSetting(() -> !mode.is("Custom"));
    private final BooleanSetting vulcanss = new BooleanSetting("Semi-Scaffold", false).hideSetting(() -> !mode.is("Vulcan"));

    public Disabler() {
        addSettings(mode, c0f, c00, c0b, c0c, vulcanss);
    }

    public String getSuffix() {
        return mode.value;
    }

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(eventPacket -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (eventPacket.isInbound()) {
            if (c0f.value && eventPacket.packet instanceof C0FPacketConfirmTransaction) {
                eventPacket.setCancelled(true);
            }

            if (c00.value && eventPacket.packet instanceof C00PacketKeepAlive) {
                eventPacket.setCancelled(true);
            }

            if (c0b.value && eventPacket.packet instanceof C0BPacketEntityAction) {
                eventPacket.setCancelled(true);
            }

            if (c0c.value && eventPacket.packet instanceof C0CPacketInput) {
                eventPacket.setCancelled(true);
            }
        }
    });
    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(event -> {
        if (vulcanss.value && event.isPre()) {
            event.Sprinting = false;
            if (mc.thePlayer.ticksExisted % 20 == 0) {
                mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
        }
    });
}
