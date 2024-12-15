package com.alan.clients.module.impl.render.sessioninfo;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.KillEvent;
import com.alan.clients.event.impl.other.ServerJoinEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.impl.render.SessionStats;
import com.alan.clients.util.localization.Localization;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.DragValue;
import lombok.AllArgsConstructor;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.StringUtils;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static com.alan.clients.layer.Layers.BLUR;
import static com.alan.clients.layer.Layers.REGULAR;

public final class RueSessionStats extends Mode<SessionStats> {
    private final DragValue position = this.getParent().getPosition();

    public RueSessionStats(String name, SessionStats parent) {
        super(name, parent);
    }

    private Session session = new Session(0, 0, 0, 0, 0, 0);
    private String time = "0 seconds";

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        // Don't do this awful shit every frame
        if (mc.thePlayer.ticksExisted % 20 == 0) {
            long elapsed = System.currentTimeMillis() - this.session.startTime;
            long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) % 60;
            long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60;

            String base = "";
            if (hours > 0)
                base += hours + " " + (hours == 1 ? Localization.get("ui.sessionstats.hour") : Localization.get("ui.sessionstats.hours")) + ((minutes == 0 ? "" : " "));
            if (minutes > 0)
                base += minutes + " " + (minutes == 1 ? Localization.get("ui.sessionstats.minute") : Localization.get("ui.sessionstats.minutes")) + (seconds == 0 || hours > 0 ? "" : " ");
            if (seconds > 0 && hours == 0)
                base += seconds + " " + (seconds == 1 ? Localization.get("ui.sessionstats.second") : Localization.get("ui.sessionstats.seconds"));

            this.time = base;
        }
    };

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        double padding = 8;
        position.scale = new Vector2d(200 - 70, 100 - 45);

        // Don't draw if the F3 menu is open
        if (mc.gameSettings.showDebugInfo) return;

        getLayer(BLUR).add(() -> {
            RenderUtil.roundedRectangle(position.position.x, position.position.y, position.scale.x, position.scale.y, 11, Color.BLACK);
        });

        // Draw all the text itself
        getLayer(REGULAR, 1).add(() -> {
            RenderUtil.roundedRectangle(position.position.x, position.position.y, position.scale.x, position.scale.y, 11, ColorUtil.withAlpha(Color.black, 100));
            RenderUtil.roundedOutlineGradientRectangle(position.position.x, position.position.y, position.scale.x, position.scale.y, 11, 0.5, ColorUtil.withAlpha(getTheme().getFirstColor(), 200), ColorUtil.withAlpha(getTheme().getSecondColor(), 200));

            // Format the walking/flying distance in meters/km

            Fonts.MAIN.get(24, Weight.REGULAR).drawCentered(Localization.get("ui.sessionstats.name"), position.position.x + position.scale.x / 2f, position.position.y + padding, getTheme().getAccentColor().getRGB());
            Fonts.MAIN.get(18, Weight.REGULAR).drawCentered(time, position.position.x + position.scale.x / 2f, position.position.y + padding + 19, new Color(255, 255, 255, 200).getRGB());
            Fonts.MAIN.get(18, Weight.REGULAR).drawCentered(Localization.get("ui.sessionstats.kills").toLowerCase() + " " + session.kills, position.position.x + 35, position.position.y + padding + 32, new Color(255, 255, 255, 200).getRGB());
            Fonts.MAIN.get(18, Weight.REGULAR).drawCentered(Localization.get("ui.sessionstats.wins").toLowerCase() + " " + session.wins, position.position.x + 95, position.position.y + padding + 32, new Color(255, 255, 255, 200).getRGB());
        });
    };

    @EventLink
    public final Listener<KillEvent> onKill = event -> {
        this.session.kills++;
    };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (event.getPacket() instanceof S45PacketTitle) {
            S45PacketTitle s45 = (S45PacketTitle) event.getPacket();
            if (s45.getMessage() == null) return;

            if (StringUtils.stripControlCodes(s45.getMessage().getUnformattedText()).equals("VICTORY!")) {
                this.session.wins++;
            }
        }
    };

    @EventLink
    public final Listener<ServerJoinEvent> onServerJoin = event -> {
        this.session = new Session(0, 0, 0, 0, 0, 0);
    };

    @AllArgsConstructor
    private static class Session {
        int kills, wins;
        int userBans, globalBans;
        double distanceWalked, distanceFlown;
        final long startTime = System.currentTimeMillis();
    }

}