package lol.point.returnclient.module.impl.misc;

import lol.point.returnclient.events.impl.packet.EventPacket;
import lol.point.returnclient.events.impl.player.EventJoinServer;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.play.client.C01PacketChatMessage;

@ModuleInfo(
        name = "AutoGG",
        description = "automatically says gg on game end",
        category = Category.MISC
)
public class AutoGG extends Module {

    private final String[] strings = new String[]{"game end", "end of game", "game finished", "finished", "you finished", "ended the game", "ended game", "winning", "win", "won", "lost", "lost the game", "you lost", "you won", "you win", "winning the game", "winner"};

    private boolean saidGG = false;

    public void onDisable() {
        saidGG = false;
    }

    @Subscribe
    private final Listener<EventJoinServer> onJoin = new Listener<>(eventJoinServer -> {
        saidGG = false;
    });

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(eventPacket -> {
        if (eventPacket.packet instanceof C01PacketChatMessage packet) {
            for (String msg : strings) {
                if (packet.getMessage().toLowerCase().contains(msg) && !saidGG && !packet.getMessage().toLowerCase().contains(mc.getSession().getUsername())) {
                    mc.thePlayer.sendChatMessage("gg");
                    saidGG = true;
                }
            }
        }
    });

}
