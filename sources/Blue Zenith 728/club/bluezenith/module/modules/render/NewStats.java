package club.bluezenith.module.modules.render;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.listeners.HypixelStatMeter;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.ui.draggables.Draggable;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.function.Supplier;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.module.value.builders.AbstractBuilder.createFloat;
import static club.bluezenith.util.render.RenderUtil.*;
import static java.lang.String.valueOf;
import static net.minecraft.client.renderer.GlStateManager.translate;
import static org.lwjgl.opengl.GL11.*;

public class NewStats extends Module implements Draggable {

    private static final String a = ((Supplier<String>)(() -> {
        if(Minecraft.getMinecraft() == null)
           return "a";
        else return "b";
    })).get();

    private static final TFontRenderer TITLE = FontUtil.createFont("Product Sans Regular", 42),
                                       STATS = FontUtil.createFont("Product Sans Regular", 35),
                                       STATS_BOLD = FontUtil.createFont("Product Sans Bold", 35);

    /* Colors used to display staff bans in the past minute. The color is chosen by the amount of bans in that minute. */
    private static final int RED = new Color(255, 40, 27).getRGB(),
                             ORANGE = new Color(253, 90, 37).getRGB(),
                             YELLOW = new Color(255, 235, 59).getRGB(),
                             GREEN = new Color(76, 220, 80).getRGB();

    private final FloatValue posX = createFloat("X")
            .index(-1)
            .increment(0.5F).max(500F).min(0F).defaultOf(0F)
            .build();

    private final FloatValue posY = createFloat("Y")
            .index(-2)
            .increment(0.5F).max(500F).min(0F).defaultOf(0F)
            .build();

    private final BooleanValue gradientLine = new BooleanValue("Gradient line", false).setIndex(-3);
    private final BooleanValue applyBlur = new BooleanValue("Blur", false).setIndex(-4);

    private final FloatValue widthSetting = createFloat("Width").index(1).increment(0.5F).max(150F).min(125F).defaultOf(140F).build();

    private float x, y, width = 150, height = 65.5F; //position

    public NewStats() {
        super("Stats", ModuleCategory.RENDER);
    }

    private boolean shouldRender;

    @Override
    public void draw(Render2DEvent event) {
        HypixelStatMeter hypixelStatMeter = getBlueZenith().getHypixelStatMeter();
        if(hypixelStatMeter == null) {
                getBlueZenith().getNotificationPublisher().postError(
                        displayName,
                        "This module only works on Hypixel. \n " +
                                "If you are on hypixel, consider reconnecting.",
                        4000

                );
                setState(false);
                shouldRender = false;
                return;
        }
        width = widthSetting.get();
        height = gradientLine.get() ? 66F : 63.5F;

        translate(x, y, 0);

        final float cornerRadius = .8f;

        if(applyBlur.get())
             blur(x, y, x + width, y + height + 1);

        rect(0, 0, width, height, new Color(0, 0, 0, applyBlur.get() ? 100 : 160));

        TITLE.drawString("Statistics", width/2F - TITLE.getStringWidthF("Statistics")/2F, 0, -1);

        final String playtime = "Playtime: ", kills = "Kills: ", games = "Games played: ", wins = "Wins: ", staffBans = "Bans: ",
                playtimeAmount = getBlueZenith().getPlaytimeMeter().timePlayedString,
                killAmount = valueOf(hypixelStatMeter.kills),
                gamesAmount = valueOf(hypixelStatMeter.gamesPlayed),
                winAmount = valueOf(hypixelStatMeter.gamesWon),
                staffBanAmount = valueOf(hypixelStatMeter.staffBansRecorded),
                staffBansPastMin = valueOf(hypixelStatMeter.staffBansCurrent);

        final int staffBansCurrent = hypixelStatMeter.staffBansCurrent;

        final int staffBansPastMinColor =
                staffBansCurrent < 5 ? GREEN
                : staffBansCurrent < 7 ? YELLOW
                : staffBansCurrent < 12 ? ORANGE
                : RED;

        float textY = TITLE.getHeight(playtime);

        if(gradientLine.get()) {
            drawGradient(textY);
        }

        textY += gradientLine.get() ? 4f : 1.5F;

        STATS_BOLD.drawString(playtime, 2, textY, -1);
        STATS.drawString(playtimeAmount, 2 + STATS_BOLD.getStringWidthF(playtime), textY, -1);

        STATS_BOLD.drawString(kills, 2, textY += STATS_BOLD.getHeight(kills) + 1, -1);
        STATS.drawString(killAmount, 2 + STATS_BOLD.getStringWidthF(kills), textY, -1);

        STATS_BOLD.drawString(games, 2, textY += STATS_BOLD.getHeight(games) + 1, -1);
        STATS.drawString(gamesAmount, 2 + STATS_BOLD.getStringWidthF(games), textY, -1);

        STATS_BOLD.drawString(wins, 2, textY += STATS_BOLD.getHeight(wins) + 1, -1);
        STATS.drawString(winAmount, 2 + STATS_BOLD.getStringWidthF(wins), textY, -1);

        float bansX;
        STATS_BOLD.drawString(staffBans, 2, textY += STATS_BOLD.getHeight(staffBans) + 1, -1);
        STATS.drawString(staffBanAmount, bansX = 2 + STATS_BOLD.getStringWidthF(staffBans), textY, -1);

        if(staffBansCurrent > 0)
            STATS.drawString("(+" + staffBansPastMin + ")", bansX + STATS_BOLD.getStringWidthF(staffBanAmount + " "), textY, staffBansPastMinColor);

        translate(-x, -y, 0);
    }

    @Override
    public void onEnable() {
        shouldRender = true;
    }

    private void drawGradient(float startY) {
        glEnable(GL_SCISSOR_TEST);
        crop(x, y, x + width, y + height);

        int lastX = -1;
        final int parts = HUD.module.colorMode.is("Pulse") ? 8 : 4;

        float width = this.width;

        if(width % parts != 0) {
            width += parts - width % parts;
        }

        for (int x = 0; x <= width; x += (width / parts)) {
            drawGradientRectHorizontal(lastX, startY + .7F, x, startY + 1.5F,  HUD.module.getColor(lastX), HUD.module.getColor(x));
            lastX = x;
        }

        glDisable(GL_SCISSOR_TEST);
    }

    @Override
    public boolean shouldBeRendered() {
        return getState() && shouldRender;
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return checkMouseBounds(mouseX, mouseY, x, y, x + width, y + height);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void moveTo(float x, float y) {
        posX.set(this.x = x);
        posY.set(this.y = y);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }
}
