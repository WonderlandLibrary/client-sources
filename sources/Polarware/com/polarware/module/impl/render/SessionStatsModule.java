package com.polarware.module.impl.render;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.ServerJoinEvent;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.util.font.FontManager;
import com.polarware.util.localization.Localization;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.render.ColorUtil;
import com.polarware.util.render.RenderUtil;
import com.polarware.util.vector.Vector2d;
import com.polarware.value.impl.*;
import lombok.AllArgsConstructor;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.StringUtils;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Hazsi
 * @since 10/13/2022
 */
@ModuleInfo(name = "module.render.sessionstats.name", description = "module.render.sessionstats.description", category = Category.RENDER)
public class SessionStatsModule extends Module {

    private final ModeValue glowMode = new ModeValue("Glow Mode", this) {{
        add(new SubMode("Colored"));
        add(new SubMode("Shadow"));
        add(new SubMode("None"));
        setDefault("Colored");
    }};

    private final BooleanValue fade = new BooleanValue("Fade", this, true);
    private final NumberValue opacity = new NumberValue("Background Opacity", this, 100, 64, 128, 1);
    private final DragValue position = new DragValue("", this, new Vector2d(200, 200), true);

    private Session session = new Session(0, 0, 0, 0, 0, 0);
    private String time = "0 seconds";

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (MoveUtil.isMoving() && MoveUtil.speed() < 0.5 && !mc.thePlayer.inWater &&
                !Client.INSTANCE.getModuleManager().get(FlightModule.class).isEnabled()) {

            double deltaX = mc.thePlayer.lastTickPosX - mc.thePlayer.posX;
            double deltaZ = mc.thePlayer.lastTickPosZ - mc.thePlayer.posZ;
            double distance = Math.hypot(deltaX, deltaZ);

            if (distance < 5) {
                this.session.distanceWalked += distance;
            }
        } else if (MoveUtil.isMoving() && Client.INSTANCE.getModuleManager().get(FlightModule.class).isEnabled()) {
            double deltaX = mc.thePlayer.lastTickPosX - mc.thePlayer.posX;
            double deltaZ = mc.thePlayer.lastTickPosZ - mc.thePlayer.posZ;
            double distance = Math.hypot(deltaX, deltaZ);

            this.session.distanceFlown += distance;
        }

        // Don't do this awful shit every frame
        if (mc.thePlayer.ticksExisted % 10 == 0) {
            long elapsed = System.currentTimeMillis() - this.session.startTime;
            long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) % 60;
            long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60;

            String base = "";
            if (hours > 0) base += hours + (hours == 1 ? Localization.get("ui.sessionstats.hour") : Localization.get("ui.sessionstats.hours")) + ((minutes == 0 ? "" : ", "));
            if (minutes > 0)
                base += minutes + (minutes == 1 ? Localization.get("ui.sessionstats.minute") : Localization.get("ui.sessionstats.minutes")) + (seconds == 0 || hours > 0 ? "" : ", ");
            if (seconds > 0 && hours == 0) base += seconds + (seconds == 1 ? Localization.get("ui.sessionstats.second") : Localization.get("ui.sessionstats.seconds"));
            this.time = base;
        }
    };

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        double padding = 15;
        position.scale = new Vector2d(200, 100);

        // Don't draw if the F3 menu is open
        if (mc.gameSettings.showDebugInfo) return;

        // Draw the background
        // Draw background with animated fade
        if (this.fade.getValue()) {
            NORMAL_POST_RENDER_RUNNABLES.add(() -> {
                Color color1 = ColorUtil.mixColors(getTheme().getFirstColor(), getTheme().getSecondColor(), getTheme().getBlendFactor(new Vector2d(0, position.position.y)));
                Color color2 = ColorUtil.mixColors(getTheme().getFirstColor(), getTheme().getSecondColor(), getTheme().getBlendFactor(new Vector2d(0, position.position.y + position.scale.y * 5)));

                RenderUtil.drawRoundedGradientRect(position.position.x, position.position.y, position.scale.x - 25, position.scale.y - 25,
                        11, ColorUtil.withAlpha(color1, opacity.getValue().intValue()),
                        ColorUtil.withAlpha(color2, opacity.getValue().intValue()), true);

                RenderUtil.circle(position.position.x + 108, position.position.y + 7, 60, 360, false, new Color(255, 255, 255, 60));
                RenderUtil.circle(position.position.x + 108, position.position.y + 7, 60, 360, true, new Color(255, 255, 255, 40));
                RenderUtil.circle(position.position.x + 108, position.position.y + 7, 60, (System.currentTimeMillis() - this.session.startTime) * 0.0001, false, Color.white);
            });
        }

        // Draw static gradient background
        else {
            NORMAL_POST_RENDER_RUNNABLES.add(() -> {
                RenderUtil.drawRoundedGradientRect(position.position.x, position.position.y, position.scale.x - 25, position.scale.y - 25,
                        11, ColorUtil.withAlpha(getTheme().getFirstColor(), opacity.getValue().intValue()),
                        ColorUtil.withAlpha(getTheme().getSecondColor(), opacity.getValue().intValue()), true);
            });
        }

        // Blur the area behind the background
        NORMAL_BLUR_RUNNABLES.add(() -> {
            RenderUtil.roundedRectangle(position.position.x, position.position.y, position.scale.x - 25, position.scale.y - 25, 11, Color.BLACK);
        });

        // Draw the glow
        NORMAL_POST_BLOOM_RUNNABLES.add(() -> {

            // Colored glow
            if (this.glowMode.getValue().getName().equals("Colored")) {

                Color color1 = ColorUtil.mixColors(getTheme().getFirstColor(), getTheme().getSecondColor(), getTheme().getBlendFactor(new Vector2d(0, position.position.y)));
                Color color2 = ColorUtil.mixColors(getTheme().getFirstColor(), getTheme().getSecondColor(), getTheme().getBlendFactor(new Vector2d(0, position.position.y + position.scale.y * 5)));
                boolean fade = this.fade.getValue();

                RenderUtil.drawRoundedGradientRect(position.position.x, position.position.y, position.scale.x - 25, position.scale.y - 25,
                        12, ColorUtil.withAlpha(fade ? color1 : getTheme().getFirstColor(), opacity.getValue().intValue() + 100),
                        ColorUtil.withAlpha(fade ? color2 : getTheme().getSecondColor(), opacity.getValue().intValue() + 100), true);
            }

            // Shadow glow
            else if (this.glowMode.getValue().getName().equals("Shadow")) {
                RenderUtil.roundedRectangle(position.position.x, position.position.y, position.scale.x, position.scale.y,
                        12, getTheme().getDropShadow());
            }
        });

        // Draw all the text itself
        NORMAL_POST_RENDER_RUNNABLES.add(() -> {

            // Format the walking/flying distance in meters/km

            FontManager.getProductSansBold(24).drawStringWithShadow(Localization.get("ui.sessionstats.name"), position.position.x + padding, position.position.y + padding, -1);
            FontManager.getProductSansBold(18).drawStringWithShadow(time, position.position.x + padding,
                    position.position.y + padding + 15, new Color(255, 255, 255, 200).getRGB());

            FontManager.getProductSansMedium(18).drawStringWithShadow(Localization.get("ui.sessionstats.kills") + " " + session.kills, position.position.x + padding,
                    position.position.y + padding + 35, new Color(255, 255, 255, 200).getRGB());
            FontManager.getProductSansMedium(18).drawStringWithShadow(Localization.get("ui.sessionstats.wins") + " " + session.wins, position.position.x + padding + 40,
                    position.position.y + padding + 35, new Color(255, 255, 255, 200).getRGB());
        });


//        if (true) return;
        // Add the glow for the "Session Stats" text and the elapsed time
        NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
            FontManager.getProductSansBold(24).drawStringWithShadow(Localization.get("ui.sessionstats.name"), position.position.x + padding, position.position.y + padding, 0xFFFFFFFF);
            FontManager.getProductSansBold(18).drawStringWithShadow(time, position.position.x + padding, position.position.y + padding + 15, 0xFFFFFFFF);
        });
    };

    @EventLink()
    public final Listener<KillEffectModule> onKill = event -> {
        this.session.kills++;
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (event.getPacket() instanceof S45PacketTitle) {
            S45PacketTitle s45 = (S45PacketTitle) event.getPacket();
            if (s45.getMessage() == null) return;

            if (StringUtils.stripControlCodes(s45.getMessage().getUnformattedText()).equals("VICTORY!")) {
                this.session.wins++;
            }
        }
    };

    @EventLink()
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