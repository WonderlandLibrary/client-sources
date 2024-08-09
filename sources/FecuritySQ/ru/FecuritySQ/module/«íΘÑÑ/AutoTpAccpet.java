package ru.FecuritySQ.module.общее;

import net.minecraft.network.play.server.SChatPacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.utils.Counter;
import ru.FecuritySQ.utils.Friend;

public class AutoTpAccpet extends Module {
    private OptionBoolean onlyfriends = new OptionBoolean("Только друзья", true);

    private Counter counter = new Counter();

    public AutoTpAccpet() {
        super(Category.Общее, GLFW.GLFW_KEY_0);
        addOption(onlyfriends);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventPacket eventPacket && isEnabled()){
            if(eventPacket.packet instanceof SChatPacket chatPacket){
                String m = chatPacket.getChatComponent().getString();

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
                    if (onlyfriends.get()) {
                        for (Friend friends : FecuritySQ.get().getFriendManager().getFriends()) {
                            if (!builder.toString().contains(friends.getName()) || !counter.hasTimeElapsed(500))
                                continue;
                            mc.player.sendChatMessage("/tpaccept");
                            counter.reset();
                        }
                    } else if (counter.hasTimeElapsed(300)) {
                        mc.player.sendChatMessage("/tpaccept");
                        counter.reset();
                    }
                }
            }
        }
    }
}
