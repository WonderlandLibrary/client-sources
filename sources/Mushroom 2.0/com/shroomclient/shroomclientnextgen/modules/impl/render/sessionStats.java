package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.annotations.AlwaysPost;
import com.shroomclient.shroomclientnextgen.config.ConfigHide;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderScreenEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScreenClickedEvent;
import com.shroomclient.shroomclientnextgen.mixin.PlayerInteractEntityC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

@RegisterModule(
    name = "Session Stats",
    uniqueId = "sessionstats",
    description = "Shows Your Current Session Stats",
    category = ModuleCategory.Render
)
public class sessionStats extends Module {

    @ConfigMode
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Swig;

    @ConfigHide
    @ConfigOption(
        name = "X Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 3
    )
    public static Integer xPos = 8;

    @ConfigHide
    @ConfigOption(
        name = "Y Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 4
    )
    public static Integer yPos = 35;

    private final Map<Integer, Boolean> previouslyAlive = new HashMap<>();

    @ConfigOption(
        name = "Glow",
        description = "Renders A Glow Around The GUI",
        order = 2
    )
    public Boolean glow = true;

    public String killText = " Kills";
    public String winText = " Wins";
    int kills = 0;
    int wins = 0;
    long pt;
    boolean dragging = false;
    double mouseXold = 0;
    double mouseYold = 0;
    double oldHudX = 0;
    double oldHudY = 0;
    private @Nullable Entity lastHitEntity = null;

    @Override
    protected void onEnable() {
        kills = 0;
        wins = 0;
        pt = System.currentTimeMillis();
        previouslyAlive.clear();
    }

    @Override
    protected void onDisable() {
        kills = 0;
        wins = 0;
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        PlayerEntity target = (PlayerEntity) lastHitEntity;

        //if (ModuleManager.isEnabled(KillAura.class)) target = (PlayerEntity) KillAura.lastTargetEnt;
        //if (ModuleManager.isEnabled(KillAura2.class)) target = KillAura2.target;

        if (target != null) {
            int targetId = target.getId();
            boolean isCurrentlyDead = target.isDead();
            boolean wasPreviouslyAlive = previouslyAlive.getOrDefault(
                targetId,
                !isCurrentlyDead
            );
            if (wasPreviouslyAlive && isCurrentlyDead) {
                kills++;
            }

            previouslyAlive.put(targetId, !isCurrentlyDead);
        }

        String titleText = "§lSession Stats";
        float widest = RenderUtil.getFontWidth(titleText) + 3;
        float height = 2;

        String playTimeText = getFormattedPlayTime();
        if (RenderUtil.getFontWidth(playTimeText) > widest) {
            widest = RenderUtil.getFontWidth(playTimeText);
        }

        RenderUtil.drawRoundedRect2(
            xPos,
            yPos,
            widest + 10,
            height * 15 + 20,
            5,
            new Color(23, 23, 23, 100),
            false,
            false,
            false,
            false
        );
        if (glow) {
            RenderUtil.drawRoundedGlow(
                xPos,
                yPos,
                widest + 10,
                height * 15 + 20,
                5,
                5,
                ThemeUtil.themeColors()[0],
                false,
                false,
                false,
                false
            );
        }
        if (kills == 1) {
            killText = " Kill";
        } else {
            killText = " Kills";
        }
        if (wins == 1) {
            winText = " Win";
        } else {
            winText = " Wins";
        }
        RenderUtil.drawTextShadow(
            "§lSession Stats",
            (int) (xPos +
                5 +
                (widest / 2f -
                    RenderUtil.getFontWidth("§lSession Stats") / 2f)),
            yPos + 4,
            ThemeUtil.themeColors()[0]
        );
        RenderUtil.drawTextShadow(
            playTimeText,
            (int) (xPos +
                5 +
                (widest / 2f - RenderUtil.getFontWidth(playTimeText) / 2f)),
            yPos + 20,
            ThemeUtil.themeColors()[0]
        );
        RenderUtil.drawTextShadow(
            kills + killText + " " + wins + winText,
            (int) (xPos +
                5 +
                (widest / 2f -
                    RenderUtil.getFontWidth(
                            kills + killText + " " + wins + winText
                        ) /
                        2f)),
            yPos + 35,
            ThemeUtil.themeColors()[0]
        );
    }

    private String getFormattedPlayTime() {
        long milli = System.currentTimeMillis() - pt;
        long days = TimeUnit.MILLISECONDS.toDays(milli);
        milli -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(milli);
        milli -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milli);
        milli -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milli);

        StringBuilder formattedTime = new StringBuilder();
        if (days > 0) {
            formattedTime.append(days).append(days == 1 ? " Day " : " Days ");
        }
        if (hours > 0 || days > 0) {
            formattedTime
                .append(hours)
                .append(hours == 1 ? " Hour " : " Hours ");
        }
        if (minutes > 0 || hours > 0 || days > 0) {
            formattedTime
                .append(minutes)
                .append(minutes == 1 ? " Minute " : " Minutes ");
        }
        formattedTime
            .append(seconds)
            .append(seconds == 1 ? " Second" : " Seconds");

        return formattedTime.toString();
    }

    @SubscribeEvent
    public void ChatEvent(ScreenClickedEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            float[] size = statsSquare();

            if (
                e.mouseX >= xPos &&
                e.mouseX <= xPos + size[2] &&
                e.mouseY >= yPos &&
                e.mouseY <= yPos + size[3]
            ) {
                dragging = e.button == 0;
                mouseXold = e.mouseX;
                mouseYold = e.mouseY;

                oldHudX = xPos;
                oldHudY = yPos;
            }

            if (!e.down && e.button == 0) {
                dragging = false;
            }
        }
    }

    @SubscribeEvent
    public void onDrawScreen(RenderScreenEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            float[] square = statsSquare();

            double mouseMovedX = e.mouseX - mouseXold;
            double mouseMovedY = e.mouseY - mouseYold;

            if (dragging) {
                xPos = (int) (oldHudX + mouseMovedX);
                yPos = (int) (oldHudY + mouseMovedY);

                RenderUtil.drawRoundedGlow(
                    square[0],
                    square[1],
                    square[2],
                    square[3],
                    5,
                    5,
                    new Color(255, 255, 255),
                    20,
                    false,
                    false,
                    false,
                    false
                );
            }
        }
    }

    public float[] statsSquare() {
        String titleText = "§lSession Stats";
        float widest = RenderUtil.getFontWidth(titleText) + 3;
        int height = 2;

        String playTimeText = getFormattedPlayTime();
        if (RenderUtil.getFontWidth(playTimeText) > widest) {
            widest = RenderUtil.getFontWidth(playTimeText);
        }
        if (
            RenderUtil.getFontWidth(kills + killText + " " + wins + winText) >
            widest
        ) {
            widest = RenderUtil.getFontWidth(
                kills + killText + " " + wins + winText
            );
        }

        height = height * 15 + 20;
        return new float[] { xPos, yPos, widest + 10, height };
    }

    @AlwaysPost
    @SubscribeEvent
    public void packetEvent(PacketEvent.Send.Post e) {
        if (e.getPacket() instanceof PlayerInteractEntityC2SPacket p) {
            int eId = ((PlayerInteractEntityC2SPacketAccessor) p).getEntityId();
            if (
                C.w().getEntityById(eId) instanceof PlayerEntity ent &&
                !TargetUtil.isBot(ent)
            ) {
                lastHitEntity = C.w().getEntityById(eId);
            }
        }
    }

    public enum Mode {
        Swig,
    }
}
