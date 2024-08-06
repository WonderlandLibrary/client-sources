package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigHide;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderScreenEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScoreboardEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScreenClickedEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.util.Comparator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.number.NumberFormat;
import net.minecraft.scoreboard.number.StyledNumberFormat;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

@RegisterModule(
    name = "Scoreboard",
    uniqueId = "scoreboard",
    description = "Changes How The Scoreboard Looks",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class ScoreBoard extends Module {

    public static float[] square = new float[] { 0, 0, 0, 0 };

    @ConfigOption(
        name = "Underline Title",
        description = "Underlines The Title Of The Scoreboard",
        order = 1
    )
    public Boolean titleUnderline = false;

    @ConfigOption(
        name = "Glow",
        description = "Renders A Glow Around The Scoreboard",
        order = 2
    )
    public Boolean glow = false;

    @ConfigOption(
        name = "Shadow",
        description = "Renders A Shadow Around The Scoreboard",
        order = 2.1
    )
    public Boolean shadow = true;

    @ConfigOption(name = "Radius", description = "", max = 20, order = 3)
    public static Integer radius = 0;

    @ConfigOption(
        name = "Background Opacity",
        description = "",
        max = 255,
        order = 3.1
    )
    public static Integer opacityBackround = 100;

    @ConfigHide
    @ConfigOption(
        name = "X Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 4
    )
    public static Integer xPos = 200;

    @ConfigHide
    @ConfigOption(
        name = "Y Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 5
    )
    public static Integer yPos = 100;

    SidebarEntry[] sidebarEntrys = null;
    ScoreboardObjective objective;
    boolean dragging = false;
    double mouseXold = 0;
    double mouseYold = 0;
    double oldHudX = 0;
    double oldHudY = 0;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    // drawing the custom font in a scoreboard event makes everything go IGFUEUSGUeghshSesgEIUOGHEUehHUOIhehehHO3t08egeh3£)£&8004ITHUEHU
    @SubscribeEvent
    public void drawScoreboard(ScoreboardEvent event) {
        // code from minecrafts inGameHud.class
        Scoreboard scoreboard = event.objective.getScoreboard();
        NumberFormat numberFormat = event.objective.getNumberFormatOr(
            StyledNumberFormat.RED
        );

        sidebarEntrys = scoreboard
            .getScoreboardEntries(event.objective)
            .stream()
            .filter(score -> !score.hidden())
            .sorted(
                Comparator.comparing(ScoreboardEntry::value)
                    .reversed()
                    .thenComparing(
                        ScoreboardEntry::owner,
                        String.CASE_INSENSITIVE_ORDER
                    )
            )
            .limit(15L)
            .map(scoreboardEntry -> {
                Team team = scoreboard.getScoreHolderTeam(
                    scoreboardEntry.owner()
                );
                Text text = scoreboardEntry.name();
                MutableText text2 = Team.decorateName(team, text);
                MutableText text3 = scoreboardEntry.formatted(numberFormat);

                if (
                    text2.toString().contains("hypixel.ne") ||
                    text2.toString().contains("minemen.c")
                ) return new SidebarEntry(
                    Text.of("mushroomer.top"),
                    text3,
                    (int) RenderUtil.getFontWidth("mushroomer.top")
                );

                return new SidebarEntry(text2, text3, 0);
            })
            .toArray(SidebarEntry[]::new);

        objective = event.objective;
    }

    @SubscribeEvent
    public void render2dEvent(RenderTickEvent event) {
        if (
            sidebarEntrys != null &&
            objective != null &&
            !C.mc.isInSingleplayer()
        ) {
            RenderUtil.setContext(event.drawContext);

            int highestWidth = 0;
            for (SidebarEntry sidebarEntry : sidebarEntrys) {
                if (
                    RenderUtil.getFontWidth(sidebarEntry.name.getString()) >
                    highestWidth
                ) highestWidth = (int) RenderUtil.getFontWidth(
                    sidebarEntry.name.getString()
                );
            }
            if (
                C.mc.textRenderer.getWidth(objective.getDisplayName()) >
                highestWidth
            ) highestWidth = (int) C.mc.textRenderer.getWidth(
                objective.getDisplayName()
            );

            int gapWidth = 10;
            int height = gapWidth * (sidebarEntrys.length + 1);
            int startHeight = yPos;

            int startX = xPos;

            square = new float[] {
                startX - 4 - (highestWidth + 8),
                startHeight - 8,
                highestWidth + 8,
                height + 12,
            };

            RenderUtil.drawRoundedRect2(
                square[0],
                square[1],
                square[2],
                square[3],
                radius,
                new Color(23, 23, 23, opacityBackround),
                false,
                false,
                false,
                false
            );
            if (glow) RenderUtil.drawRoundedGlow(
                square[0],
                square[1],
                square[2],
                square[3],
                radius,
                5,
                ThemeUtil.themeColors()[0],
                false,
                false,
                false,
                false
            );
            if (shadow) RenderUtil.drawRoundedGlow(
                square[0],
                square[1],
                square[2],
                square[3],
                radius,
                5,
                new Color(23, 23, 23, opacityBackround),
                opacityBackround,
                false,
                false,
                false,
                false
            );

            //RenderUtil.drawOutlineRoundedFade(startX - 4, startHeight - 8, highestWidth+8, height+12, 6, ThemeUtil.themeColors()[0], ThemeUtil.themeColors()[1], false, true, false, true);

            int h = 0;
            /*
            for (Text text : objective.getDisplayName().getWithStyle(objective.getDisplayName().getStyle())) {
                if (text.getStyle().getColor() != null)
                    RenderUtil.drawCenteredTextShadow(C.mc.textRenderer, objective.getDisplayName().getString(), (int) (square[0] + (highestWidth / 2)) + 4 + h, startHeight - 4, new Color(text.getStyle().getColor().getRgb()));
                else
                    RenderUtil.drawCenteredTextShadow(C.mc.textRenderer, objective.getDisplayName().getString(), (int) (square[0] + (highestWidth / 2)) + 4 + h, startHeight - 4, new Color(255, 255, 255));

                h += (int) RenderUtil.getFontWidth(text.getString());
            }
             */

            RenderUtil.drawTextShadow(
                objective.getDisplayName(),
                (int) ((int) (square[0] + (highestWidth / 2)) +
                    4 -
                    (RenderUtil.getFontWidth(
                            "§l" + objective.getDisplayName().getString()
                        ) /
                        2f)),
                startHeight - 4,
                new Color(255, 255, 255)
            );

            if (titleUnderline) RenderUtil.drawFade(
                square[0],
                square[1] + RenderUtil.fontSize + 7,
                square[2],
                1,
                ThemeUtil.themeColors()[0],
                ThemeUtil.themeColors()[1]
            );

            for (int i = 0; i < sidebarEntrys.length; i++) {
                int prevTextLen = 0;
                if (sidebarEntrys[i].name.equals(Text.of("mushroomer.top"))) {
                    RenderUtil.drawCenteredTextShadow(
                        C.mc.textRenderer,
                        sidebarEntrys[i].name.getString(),
                        (int) square[0] + (highestWidth / 2) + 4,
                        (int) square[1] + ((i + 1) * gapWidth) + 10,
                        ThemeUtil.themeColors()[0]
                    );
                } else {
                    RenderUtil.drawTextShadow(
                        sidebarEntrys[i].name,
                        (int) square[0] + 4,
                        (int) square[1] + ((i + 1) * gapWidth) + 10,
                        new Color(255, 255, 255)
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public void ChatEvent(ScreenClickedEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            if (
                e.mouseX >= square[0] &&
                e.mouseX <= square[0] + square[2] &&
                e.mouseY >= square[1] &&
                e.mouseY <= square[1] + square[3]
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
                    radius,
                    5,
                    new Color(255, 255, 255),
                    200,
                    false,
                    false,
                    false,
                    false
                );
            }
        }
    }

    @Environment(value = EnvType.CLIENT)
    record SidebarEntry(Text name, Text score, int scoreWidth) {}
}
