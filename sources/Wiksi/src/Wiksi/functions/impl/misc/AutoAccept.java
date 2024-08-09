package src.Wiksi.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.command.friends.FriendStorage;
import src.Wiksi.events.EventPacket;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import net.minecraft.network.play.server.SChatPacket;

import java.util.Locale;

@FunctionRegister(name = "AutoAccept", type = Category.Misc)
public class AutoAccept extends Function {

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
