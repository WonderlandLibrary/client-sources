package pw.latematt.xiv.mod.mods.misc;

import net.minecraft.network.play.server.S02PacketChat;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.ReadPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * @author Jack
 */

public class AutoAccept extends Mod implements Listener<ReadPacketEvent> {
    public AutoAccept() {
        super("AutoAccept", ModType.MISCELLANEOUS);
    }

    @Override
    public void onEventCalled(ReadPacketEvent event) {
        if (event.getPacket() instanceof S02PacketChat) {
            final S02PacketChat packetChat = (S02PacketChat) event.getPacket();
            XIV.getInstance().getFriendManager().getContents().keySet().stream()
                    .filter(friend -> packetChat.func_148915_c().getFormattedText().contains(friend))
                    .filter(friend -> isValidMessage(packetChat.func_148915_c().getFormattedText()))
                    .forEach(friend -> mc.thePlayer.sendChatMessage("/tpaccept " + friend));
        }
    }

    public boolean isValidMessage(String message) {
        return message.contains("has requested to teleport to you.") || message.contains("has requested that you teleport to them.") ||
                message.contains("te ha pedido teletransportarse hasta ti.") || message.contains("te ha pedido que te teletransportes hasta él.");
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
