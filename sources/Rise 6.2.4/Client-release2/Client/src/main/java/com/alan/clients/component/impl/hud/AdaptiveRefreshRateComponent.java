package com.alan.clients.component.impl.hud;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.ui.ingame.GuiIngameCache;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import rip.vantage.commons.util.time.StopWatch;

public class AdaptiveRefreshRateComponent extends Component {

    private boolean playerList, debugInfo;
    private boolean setDirty;
    private int disabledFor;
    private float health;
    public final StopWatch stopWatch = new StopWatch();

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksExisted % 100 == 0 || setDirty ||
                mc.gameSettings.keyBindPlayerList.isKeyDown() != playerList || mc.gameSettings.showDebugInfo != debugInfo ||
                mc.thePlayer.ticksExisted <= 10 || disabledFor > 0) {
            GuiIngameCache.dirty = true;
            playerList = mc.gameSettings.keyBindPlayerList.isKeyDown();
            debugInfo = mc.gameSettings.showDebugInfo;
            setDirty = false;
        }

        if (health != mc.thePlayer.getHealth()) {
            disabledFor = 3;
            health = mc.thePlayer.getHealth();
        }

        disabledFor--;
    };

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (mc.currentScreen != null) {
            setDirty = true;
            GuiIngameCache.dirty = true;
        }
    };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        Packet packet = event.getPacket();

        if (packet instanceof S02PacketChat || packet instanceof S30PacketWindowItems || packet instanceof S2FPacketSetSlot) {
            stopWatch.reset();
            setDirty = true;
        }

        if (packet instanceof S0DPacketCollectItem) {
            disabledFor = 5;
        }
    };
}