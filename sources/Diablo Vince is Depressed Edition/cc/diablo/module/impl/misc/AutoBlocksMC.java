/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoBlocksMC
extends Module {
    public NumberSetting distance = new NumberSetting("Distance", 3.0, 1.0, 6.0, 1.0);

    public AutoBlocksMC() {
        super("AutoBlocksMC", "Automatically phases out of cages", 0, Category.Misc);
        this.addSettings(this.distance);
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        S02PacketChat packetChat;
        if (e.isIncoming() && e.getPacket() instanceof S02PacketChat && (packetChat = (S02PacketChat)e.getPacket()).getChatComponent().getUnformattedText().contains("Cages open in") && packetChat.getChatComponent().getUnformattedText().contains("10")) {
            AutoBlocksMC.mc.thePlayer.setPosition(AutoBlocksMC.mc.thePlayer.posX, AutoBlocksMC.mc.thePlayer.posY - this.distance.getVal(), AutoBlocksMC.mc.thePlayer.posZ);
            ChatHelper.addChat("Automatically phased out!");
        }
    }
}

