package wtf.resolute.moduled.impl.misc;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.manage.friends.FriendStorage;
import wtf.resolute.evented.EventPacket;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import net.minecraft.network.play.server.SChatPacket;

import java.util.Locale;

@ModuleAnontion(name = "AutoAccept", type = Categories.Misc,server = "")
public class AutoAccept extends Module {

    private final BooleanSetting onlyFriend = new BooleanSetting("Только друзья", true);

    public AutoAccept() {
        addSettings(onlyFriend);
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player == null || mc.world == null) return;

        if (e.getPacket() instanceof SChatPacket p) {
            String raw = p.getChatComponent().getString().toLowerCase(Locale.ROOT);
            if (raw.contains("телепортироваться") || raw.contains("has requested teleport") || raw.contains("просит к вам телепортироваться")) {
                if (onlyFriend.get()) {
                    boolean yes = false;

                    for (String friend : FriendStorage.getFriends()) {
                        if (raw.contains(friend.toLowerCase(Locale.ROOT))) {
                            yes = true;
                            break;
                        }
                    }

                    if (!yes) return;
                }

                mc.player.sendChatMessage("/tpaccept");
                //print("accepted: " + raw);
            }
        }
    }
}
