package com.alan.clients.module.impl.render;

import com.alan.clients.component.impl.player.LastConnectionComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.font.impl.minecraft.FontRenderer;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.vector.Vector2i;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.DragValue;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.alan.clients.layer.Layers.*;

@ModuleInfo(aliases = {"module.render.scoreboard.name"}, description = "module.render.scoreboard.description", category = Category.RENDER, autoEnabled = true)
public final class ScoreBoard extends Module {

    // The rounded scoreboard code was worse than anyone could imagine
    private final DragValue position = new DragValue("Position", this, new Vector2d(200, 200));
    private final BooleanValue outline = new BooleanValue("Outline", this, false, () -> true);
    private final BooleanValue blur_color = new BooleanValue("Blur color", this, false, () -> true);
    private final BooleanValue renameIP = new BooleanValue("Replace IP with Rise Website", this, true);

    private Collection<Score> collection;
    private ScoreObjective scoreObjective;
    private int maxWidth;
    private Interface interfaceModule;
    private final int padding = 3;
    private final int fontHeight = FontRenderer.FONT_HEIGHT;

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (this.scoreObjective == null) return;

        if (interfaceModule == null) interfaceModule = getModule(Interface.class);

        final Vector2i position = new Vector2i((int) this.position.position.x, (int) this.position.position.y);

        boolean rise = interfaceModule.getInformationType().getValue().getName().equals("Rise");
        int round = rise ? 5 : 1;
        getLayer(REGULAR).add(() -> {
            final int size = collection.size();
            final int height = fontHeight * size + padding;
            if (outline.getValue()) {
                RenderUtil.roundedOutlineGradientRectangle(position.x - 1, position.y - 1, (maxWidth + padding * 4) + 2, (height + fontHeight + padding) + 2, round, 1f, ColorUtil.withAlpha(getTheme().getFirstColor(), 100), ColorUtil.withAlpha(getTheme().getSecondColor(), 100));
            }
        });
        getLayer(BLUR).add(() -> this.renderScoreboard(position.x, position.y, Color.WHITE, false, round, false));

        getLayer(BLOOM).add(() -> this.renderScoreboard(position.x, position.y, rise ? getTheme().getDropShadow() : Color.BLACK, false, round + 1, true));

        getLayer(REGULAR, 1).add(() -> this.renderScoreboard(position.x, position.y, blur_color.getValue() ? new Color(getTheme().getSecondColor().getRed(), getTheme().getSecondColor().getGreen(), getTheme().getSecondColor().getBlue(), 60) : new Color(0, 0, 0, 100), true, round, false));
    };

    /**
     * Updates the scoreboard each tick.
     */
    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        this.scoreObjective = this.getScoreObjective();
        if (this.scoreObjective == null) return;

        final Collection<Score> collection = this.scoreObjective.getScoreboard().getSortedScores(this.scoreObjective);
        final List<Score> list = collection.stream()
                .filter(score -> score.getPlayerName() != null && !score.getPlayerName().startsWith("#"))
                .collect(Collectors.toList());

        if (list.size() > 15) {
            this.collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        } else {
            this.collection = list;
        }

        this.maxWidth = mc.fontRendererObj.width(scoreObjective.getDisplayName());

        for (final Score score : collection) {
            final ScorePlayerTeam scoreplayerteam = this.scoreObjective.getScoreboard().getPlayersTeam(score.getPlayerName());
            final String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName());
            this.maxWidth = Math.max(this.maxWidth, mc.fontRendererObj.width(s));

            if (renameIP.getValue() && (s.contains("riseclient.com") || Arrays.stream(LastConnectionComponent.ip.split("\\.")).anyMatch(part ->
                    Arrays.stream(s.split("\\."))
                            .anyMatch(part::equalsIgnoreCase)))) {
                String rise = getTheme().getChatAccentColor() + "riseclient.com";

                if (scoreplayerteam != null) {
                    scoreplayerteam.setNamePrefix("");
                }

                score.setScorePlayerName(rise);
            }
        }

        this.maxWidth += 2;
    };

    private ScoreObjective getScoreObjective() {
        final net.minecraft.scoreboard.Scoreboard scoreboard = mc.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        final ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(mc.thePlayer.getCommandSenderName());

        if (scoreplayerteam != null) {
            final int colorIndex = scoreplayerteam.getChatFormat().getColorIndex();

            if (colorIndex > -1) {
                scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + colorIndex);
            }
        }

        return scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
    }

    private void renderScoreboard(int x, int y, final Color backgroundColor, final boolean font, int round, boolean bloom) {
        final FontRenderer fontRenderer = mc.fontRendererObj;
        final int size = collection.size();
        final int height = fontHeight * size + padding;

        Vector2d scale = new Vector2d(maxWidth + padding * 4, height + fontHeight + padding);

        if (scale.x != position.scale.x || scale.y != position.scale.y) {
//            GuiIngameCache.dirty = true;
        }

        this.position.setScale(scale);

        if (bloom) {
            RenderUtil.roundedRectangle(x + 0.5F, y + 0.5F, (maxWidth + padding * 4) - 1, (height + fontHeight + padding) - 1,
                    round, backgroundColor);
        } else {
            RenderUtil.roundedRectangle(x, y, maxWidth + padding * 4, height + fontHeight + padding,
                    round, backgroundColor);
        }

        if (!font) {
            return;
        }

        final int fontColor = 553648127;
        x += padding * 2;
        y += padding + 1.5;

        // draws title of scoreboard
        final String objective = scoreObjective.getDisplayName();
        fontRenderer.drawWithShadow(objective, x + maxWidth / 2.0F - fontRenderer.width(objective) / 2.0F, y, fontColor);

        // draws strings
        for (final Score score1 : collection) {
            y += fontHeight;
            final ScorePlayerTeam scorePlayerTeam = this.scoreObjective.getScoreboard().getPlayersTeam(score1.getPlayerName());
            final String s1 = ScorePlayerTeam.formatPlayerName(scorePlayerTeam, score1.getPlayerName());
            fontRenderer.drawWithShadow(s1, x, y, fontColor);
        }
    }
}