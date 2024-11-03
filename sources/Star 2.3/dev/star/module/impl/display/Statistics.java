package dev.star.module.impl.display;

import dev.star.Client;
import dev.star.event.impl.player.MotionEvent;
import dev.star.event.impl.render.Render2DEvent;
import dev.star.event.impl.render.ShaderEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.utils.objects.Dragging;
import dev.star.utils.objects.GradientColorWheel;
import dev.star.utils.render.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Statistics extends Module {
    private final Dragging motionDragging = Client.INSTANCE.createDrag(this, "motionGraph", 5, 200);
    private final Dragging dragging = Client.INSTANCE.createDrag(this, "sessionstats", 5, 150);
    public static final String[] KILL_TRIGGERS = {"by *", "para *", "fue destrozado a manos de *"};
    public static long startTime = System.currentTimeMillis(), endTime = -1;
    private final GradientColorWheel colorWheel = new GradientColorWheel();
    public static int gamesPlayed, killCount, deathCount;
    private float width, height;

    public Statistics() {
        super("Statistics", Category.DISPLAY, "Displays statistics about your session");
        addSettings(colorWheel.createModeSetting("Color Mode"), colorWheel.getColorSetting());
    }

    @Override
    public void onShaderEvent(ShaderEvent e) {
        float x = this.dragging.getX(), y = this.dragging.getY();
            RoundedUtil.drawRound(x, y, width, height, 6, Color.BLACK);
            RoundedUtil.drawRound(motionDragging.getX(), motionDragging.getY(),
                    motionDragging.getWidth(), motionDragging.getHeight(), 6, Color.BLACK);
    }

    @Override
    public void onRender2DEvent(Render2DEvent e) {
        float x = this.dragging.getX(), y = this.dragging.getY();

        motionDragging.setWidth(0);
        motionDragging.setHeight(0);

        width = 145;
        height = 55;

        dragging.setHeight(55);
        dragging.setWidth(width);

        colorWheel.setColorsForMode("Dark", ColorUtil.brighter(new Color(30, 30, 30), .65f));
        colorWheel.setColors();

        float alpha = colorWheel.getColorMode().is("Dark") ? 1 : .85f;
        RoundedUtil.drawRoundOutline(x, y, width, 55, 6, 0.5f, new Color(38, 35, 35, 200),
                ColorUtil.applyOpacity(colorWheel.getColor2(), alpha));
//                ColorUtil.applyOpacity(colorWheel.getColor3(), alpha));
        int[] playTimeActual = getPlayTime();

        // Centered "Session Info" text
        String sessionInfoText = "Session Info ";
        float sessionInfoWidth =BoldFont20.getStringWidth(sessionInfoText);
       BoldFont20.drawString(sessionInfoText, x + (width - sessionInfoWidth) / 2, y + 4, -1);

        int hours = playTimeActual[0];
        int minutes = playTimeActual[1];
        int seconds = playTimeActual[2];

        String formattedTimeElapsed = String.format("%02d:%02d:%02d", hours, minutes, seconds);

// Display "Time Elapsed" above "Games Played"
        String timeElapsedText = "Time Elapsed: " + formattedTimeElapsed;
        BoldFont18.drawString(timeElapsedText, x + 5, y + 4 + BoldFont22.getHeight() + 2, -1);        BoldFont18.drawString(timeElapsedText, x + 5, y + 4 + BoldFont22.getHeight() + 2, -1);

// Display "Games Played" below "Time Elapsed"
        String gamesPlayedText = "Games Played: " + gamesPlayed;
        BoldFont18.drawString(gamesPlayedText, x + 5, y + 4 + BoldFont22.getHeight() + 2 + BoldFont18.getHeight() + 7, -1);

// Display "Kills" below "Games Played"
        String killsText = "Kills: " + killCount;
        BoldFont18.drawString(killsText, x + 5, y + 4 + BoldFont22.getHeight() + 2 + 2 * (BoldFont18.getHeight() + 7), -1);
    }

    private final List<Float> speeds = new ArrayList<>();

    @Override
    public void onMotionEvent(MotionEvent event) {
        if (event.isPre()) {
            if ((speeds.size() - 1) >= 100) {
                speeds.remove(0);
            }
            speeds.add(getPlayerSpeed());

        }
    }

    public static int[] getPlayTime() {
        long diff = getTimeDiff();
        long diffSeconds = 0, diffMinutes = 0, diffHours = 0;
        if (diff > 0) {
            diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000) % 24;
        }
       /* String str = (int) diffSeconds + "s";
        if (diffMinutes > 0) str = (int) diffMinutes + "m " + str;
        if (diffHours > 0) str = (int) diffHours + "h " + str;*/
        return new int[]{(int) diffHours, (int) diffMinutes, (int) diffSeconds};
    }

    public static long getTimeDiff() {
        return (endTime == -1 ? System.currentTimeMillis() : endTime) - startTime;
    }

    public static void reset() {
        startTime = System.currentTimeMillis();
        endTime = -1;
        gamesPlayed = 0;
        killCount = 0;
    }

    private float getPlayerSpeed() {
        double bps = (Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * mc.timer.timerSpeed) * 20;
        return (float) bps / 50;
    }

    @Override
    public void onEnable() {
        speeds.clear();
        super.onEnable();
    }
}
