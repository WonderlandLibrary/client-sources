/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SChatPacket;

@FunctionRegister(name="AutoEvent", type=Category.Misc)
public class AutoEvent
extends Function {
    private final boolean autoGPS = true;

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        Pattern pattern;
        Matcher matcher;
        if (!(eventPacket.getPacket() instanceof SChatPacket)) {
            return;
        }
        SChatPacket sChatPacket = (SChatPacket)eventPacket.getPacket();
        String string = sChatPacket.getChatComponent().getString();
        if (string.contains("\u041f\u043e\u044f\u0432\u0438\u043b\u0441\u044f \u043d\u0430 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u0430\u0445") && (matcher = (pattern = Pattern.compile("\\[(\\d+) (\\d+) (\\d+)]")).matcher(string)).find()) {
            int n = Integer.parseInt(matcher.group(1));
            int n2 = Integer.parseInt(matcher.group(3));
            this.sendCommand(".gps add Myst " + n + " 100 " + n2);
            System.out.println("\u0414\u043e\u0431\u0430\u0432\u0438\u043b \u0442\u043e\u0447\u043a\u0443 \u0441 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435\u043c Myst!");
        }
    }

    private void sendCommand(String string) {
        Minecraft.getInstance().player.sendChatMessage(string);
    }
}

