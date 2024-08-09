/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import java.util.Locale;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChatPacket;

@FunctionRegister(name="AutoAccept", type=Category.Misc)
public class AutoAccept
extends Function {
    private final BooleanSetting onlyFriend = new BooleanSetting("\u0422\u043e\u043b\u044c\u043a\u043e \u0434\u0440\u0443\u0437\u044c\u044f", true);

    public AutoAccept() {
        this.addSettings(this.onlyFriend);
    }

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        SChatPacket sChatPacket;
        if (AutoAccept.mc.player == null || AutoAccept.mc.world == null) {
            return;
        }
        IPacket<?> iPacket = eventPacket.getPacket();
        if (iPacket instanceof SChatPacket && (((String)((Object)(iPacket = (sChatPacket = (SChatPacket)iPacket).getChatComponent().getString().toLowerCase(Locale.ROOT)))).contains("\u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f") || ((String)((Object)iPacket)).contains("has requested teleport") || ((String)((Object)iPacket)).contains("\u043f\u0440\u043e\u0441\u0438\u0442 \u043a \u0432\u0430\u043c \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f"))) {
            if (((Boolean)this.onlyFriend.get()).booleanValue()) {
                boolean bl = false;
                for (String string : FriendStorage.getFriends()) {
                    if (!((String)((Object)iPacket)).contains(string.toLowerCase(Locale.ROOT))) continue;
                    bl = true;
                    break;
                }
                if (!bl) {
                    return;
                }
            }
            AutoAccept.mc.player.sendChatMessage("/tpaccept");
        }
    }
}

