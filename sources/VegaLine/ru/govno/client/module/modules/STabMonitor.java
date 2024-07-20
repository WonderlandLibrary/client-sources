/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.module.Module;
import ru.govno.client.utils.Math.TimerHelper;

public class STabMonitor
extends Module {
    TimerHelper timer = new TimerHelper();

    public STabMonitor() {
        super("STabMonitor", 0, Module.Category.MISC, false, () -> false);
    }

    String decoloredString(String toDecolor) {
        List<String> CHAR_SAMPLES = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "c", "e", "a", "b", "d", "f", "r", "l", "k", "o", "m", "n");
        String formatChar = "\u00a7";
        for (String sample : CHAR_SAMPLES) {
            toDecolor = toDecolor.replace("\u00a7" + sample, "");
        }
        return toDecolor;
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived) {
            mc.getConnection().sendPacket(new CPacketTabComplete("/", BlockPos.ORIGIN, false));
            this.timer.reset();
        }
        super.onToggled(actived);
    }

    String getStringAsMassive(String[] massiveString) {
        Object string = "";
        for (String str : massiveString) {
            string = (String)string + str;
        }
        return string;
    }

    @EventTarget
    public void onReceived(EventReceivePacket received) {
        Packet packet = received.getPacket();
        if (packet instanceof SPacketTabComplete) {
            SPacketTabComplete completed = (SPacketTabComplete)packet;
            this.toggleSilent(false);
            if (completed.getMatches().length != 0) {
                List outPut = Arrays.asList(this.getStringAsMassive(completed.getMatches()).split("/")).stream().filter(str -> str.length() > 0).map(str -> str.replace(":", "").replace(";", "").replace(" ", "")).toList();
                ArrayList<String> outPutFiltered = new ArrayList<String>();
                for (String out : outPut) {
                    if (outPutFiltered.stream().anyMatch(str -> str.contains(out))) continue;
                    outPutFiltered.add(out);
                }
                outPutFiltered.sort(String.CASE_INSENSITIVE_ORDER);
                if (outPutFiltered.isEmpty()) {
                    Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77\u043d\u0435\u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0440\u0435\u0437\u0443\u043b\u044c\u0442\u0430\u0442.", false);
                    return;
                }
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77\u0440\u0435\u0437\u0443\u043b\u044c\u0442\u0430\u0442\u043e\u0432 \u043d\u0430\u0439\u0434\u0435\u043d\u043e -> " + outPutFiltered.size() + ":", false);
                int number = 0;
                for (String result : outPutFiltered) {
                    Client.msg("\u00a77\u2116" + number + "\u00a7r -> " + this.decoloredString(result) + (++number == outPutFiltered.size() ? "." : ";"), this.actived);
                }
                return;
            }
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7l" + this.getName() + "\u00a7r\u00a77]: \u043d\u0435\u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0440\u0435\u0437\u0443\u043b\u044c\u0442\u0430\u0442.", false);
        }
    }

    @Override
    public void onUpdate() {
        if (this.timer.hasReached((long)mc.getConnection().getPlayerInfo(Minecraft.player.getUniqueID()).getResponseTime() + 280L)) {
            this.toggle(false);
        }
    }
}

