package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.manager.friend.Friend;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.TimerHelper;
import net.minecraft.network.play.server.SPacketChat;

@ModuleAnnotation(name = "AutoTPAccept", category = Category.PLAYER)
public class AutoTPAccept extends Module {
    private final BooleanSetting onlyFriends = new BooleanSetting("Only Friends", true);
    private final TimerHelper timerHelper = new TimerHelper();

    @EventTarget
    public void onReceivePacket(EventReceivePacket eventPacket) {
        if (eventPacket.getPacket() instanceof SPacketChat) {
            SPacketChat packet = (SPacketChat) eventPacket.getPacket();
            String m = packet.getChatComponent().getUnformattedText();
            StringBuilder builder = new StringBuilder();
            char[] buffer = m.toCharArray();
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] == '§') {
                    i++;
                } else {
                    builder.append(buffer[i]);
                }
            }
            if (builder.toString().contains("телепортироваться")) {
                if (onlyFriends.get()) {
                    for (Friend friends : DarkMoon.getInstance().getFriendManager().getFriends()) {
                        if (!builder.toString().contains(friends.getName()) || !timerHelper.hasReached(300))
                            continue;
                        mc.player.sendChatMessage("/tpaccept");
                        timerHelper.reset();
                    }
                } else if (timerHelper.hasReached(300)) {
                    mc.player.sendChatMessage("/tpaccept");
                    timerHelper.reset();
                }
            }
        }
    }
}
