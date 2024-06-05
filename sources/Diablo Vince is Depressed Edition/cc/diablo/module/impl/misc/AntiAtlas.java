/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.helpers.Stopwatch;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;

public class AntiAtlas
extends Module {
    private final List<UUID> reported = new ArrayList<UUID>();
    private final Stopwatch timer = new Stopwatch();
    int total = 0;

    public AntiAtlas() {
        super("AntiAtlas", "Automatically reports all players in your lobby on Hypixel", 0, Category.Misc);
    }

    @Override
    public void onEnable() {
        this.timer.reset();
        this.reported.clear();
        super.onEnable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        for (NetworkPlayerInfo playerInfo : mc.getNetHandler().getPlayerInfoMap()) {
            this.setDisplayName(this.getName() + "\u00a77 " + this.total);
            UUID uuid = playerInfo.getGameProfile().getId();
            String name = playerInfo.getGameProfile().getName();
            if (!this.timer.hasReached(5000L) || this.reported.contains(uuid) || uuid.equals(AntiAtlas.mc.thePlayer.getUniqueID())) continue;
            PacketHelper.sendPacketNoEvent(new C01PacketChatMessage("/report " + name + " killaura fly speed"));
            this.reported.add(uuid);
            ++this.total;
            this.timer.reset();
        }
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        S02PacketChat packet;
        if (e.getPacket() instanceof S02PacketChat && (packet = (S02PacketChat)e.getPacket()).getChatComponent().getFormattedText().startsWith("\u00a7cThere is no player named")) {
            e.setCancelled(true);
        }
    }
}

