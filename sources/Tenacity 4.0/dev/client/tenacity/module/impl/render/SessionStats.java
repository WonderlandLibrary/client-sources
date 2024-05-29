package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.objects.Dragging;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import dev.event.EventListener;
import dev.event.impl.render.Render2DEvent;
import dev.event.impl.render.ShaderEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ColorSetting;
import dev.settings.impl.ModeSetting;
import dev.utils.font.FontUtil;
import dev.utils.render.StencilUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class SessionStats extends Module {

    public static int gamesPlayed, killCount;
    public static long startTime = System.currentTimeMillis(), endTime = -1;
    public static final String[] KILL_TRIGGERS = {"by *", "para *", "fue destrozado a manos de *"};
    private final List<String> linesLeft = Arrays.asList("Play time", "Games played", "Kills");

    private final Dragging dragging = Tenacity.INSTANCE.createDrag(this, "sessionstats", 40, 100);


    private final BooleanSetting animatedPlaytime = new BooleanSetting("Animated counter", true);
    public final ModeSetting colorMode = new ModeSetting("Color", "Sync", "Sync", "Analogous", "Tenacity", "Gradient", "Modern");
    public final ModeSetting degree = new ModeSetting("Degree", "30", "30", "-30");
    private final ColorSetting color1 = new ColorSetting("Color 1", Tenacity.INSTANCE.getClientColor());
    private final ColorSetting color2 = new ColorSetting("Color 2", Tenacity.INSTANCE.getAlternateClientColor());

    public SessionStats() {
        super("SessionStats", Category.RENDER, "Displays session stats");
        color1.addParent(colorMode, modeSetting -> modeSetting.is("Gradient") || modeSetting.is("Analogous"));
        color2.addParent(colorMode, modeSetting -> modeSetting.is("Gradient") && !modeSetting.is("Analogous"));
        degree.addParent(colorMode, modeSetting -> modeSetting.is("Analogous"));
        addSettings(animatedPlaytime, colorMode, degree, color1, color2);
    }


    private final EventListener<ShaderEvent> shaderEvent = e -> {
        float x = this.dragging.getX(), y = this.dragging.getY();
        float height = linesLeft.size() * (FontUtil.tenacityBoldFont18.getHeight() + 6) + 24;
        float width = 140;
        if(colorMode.is("Modern")){
            RoundedUtil.drawRoundOutline(x, y, width, height, 6, .5f,ColorUtil.applyOpacity(Color.WHITE, .85f),ColorUtil.applyOpacity(Color.WHITE, .85f));
        }else {
            RoundedUtil.drawRound(x, y, width, height, 6, ColorUtil.applyOpacity(Color.WHITE, .85f));
        }
    };

    float playtimeWidth = 20.5f;

    private Color gradientColor1 = Color.WHITE, gradientColor2 = Color.WHITE, gradientColor3 = Color.WHITE, gradientColor4 = Color.WHITE;
    private final EventListener<Render2DEvent> eventCall = e -> {
        float x = this.dragging.getX(), y = this.dragging.getY();
        float height = linesLeft.size() * (FontUtil.tenacityBoldFont18.getHeight() + 6) + 24;
        float width = 140;
        dragging.setHeight(height);
        dragging.setWidth(width);

        switch (colorMode.getMode()) {
            case "Sync":
                HudMod hudMod = (HudMod) Tenacity.INSTANCE.getModuleCollection().get(HudMod.class);
                Color[] colors = hudMod.getClientColors();
                gradientColor1 = ColorUtil.interpolateColorsBackAndForth(15, 0, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                gradientColor2 = ColorUtil.interpolateColorsBackAndForth(15, 90, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                gradientColor3 = ColorUtil.interpolateColorsBackAndForth(15, 180, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                gradientColor4 = ColorUtil.interpolateColorsBackAndForth(15, 270, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                break;
            case "Tenacity":
                gradientColor1 = ColorUtil.interpolateColorsBackAndForth(15, 0, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor2 = ColorUtil.interpolateColorsBackAndForth(15, 90, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor3 = ColorUtil.interpolateColorsBackAndForth(15, 180, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor4 = ColorUtil.interpolateColorsBackAndForth(15, 270, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                break;
            case "Gradient":
                gradientColor1 = ColorUtil.interpolateColorsBackAndForth(15, 0, color1.getColor(), color2.getColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor2 = ColorUtil.interpolateColorsBackAndForth(15, 90, color1.getColor(), color2.getColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor3 = ColorUtil.interpolateColorsBackAndForth(15, 180, color1.getColor(), color2.getColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor4 = ColorUtil.interpolateColorsBackAndForth(15, 270, color1.getColor(), color2.getColor(), HudMod.hueInterpolation.isEnabled());
                break;
            case "Analogous":
                int val = degree.is("30") ? 0 : 1;
                Color analogous = ColorUtil.getAnalogousColor(color1.getColor())[val];
                gradientColor1 = ColorUtil.interpolateColorsBackAndForth(15, 0, color1.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                gradientColor2 = ColorUtil.interpolateColorsBackAndForth(15, 90, color1.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                gradientColor3 = ColorUtil.interpolateColorsBackAndForth(15, 180, color1.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                gradientColor4 = ColorUtil.interpolateColorsBackAndForth(15, 270, color1.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                break;
            case "Modern":
                RoundedUtil.drawRoundOutline(x, y, width, height, 6, .5f, new Color(10, 10, 10, 80), new Color(-2));
                break;
        }
        boolean outlinedRadar = !(colorMode.is("Modern"));
        RenderUtil.setAlphaLimit(0);
        if (outlinedRadar) {
            RoundedUtil.drawGradientRound(x, y, width, height, 6, ColorUtil.applyOpacity(gradientColor4, .85f), gradientColor1, gradientColor3, gradientColor2);
            Gui.drawGradientRect2(x - 1, y + 15, width + 2, 5, ColorUtil.applyOpacity(Color.BLACK, .2f).getRGB(), ColorUtil.applyOpacity(Color.BLACK, 0).getRGB());
        }else {
            Gui.drawGradientRect2(x +1, y + 15, width - 2, 5, ColorUtil.applyOpacity(Color.BLACK, .2f).getRGB(), ColorUtil.applyOpacity(Color.BLACK, 0).getRGB());
        }


        FontUtil.tenacityBoldFont22.drawCenteredString("Statistics", x + width / 2, y + (colorMode.is("Modern") ? 3 : 2), -1);




        for (int i = 0; i < linesLeft.size(); i++) {
            int offset = i * (FontUtil.tenacityBoldFont18.getHeight() + 6);
            FontUtil.tenacityBoldFont18.drawString(linesLeft.get(i), x + 5, y + offset + (i == 0 ? 23.5 : 25), -1);
        }
        int[] playTime = getPlayTime();

        playtimeWidth = (float) RenderUtil.animate(20.5 + (playTime[1] > 0 ? 20 : 0) + (playTime[0] > 0 ? 14 : 0), playtimeWidth, 0.008);

        float playtimeX = x + width - (playtimeWidth + 5);
        if (animatedPlaytime.isEnabled()) {
            drawAnimatedPlaytime(playtimeX, y, width);
        } else {
            String playtimeString = playTime[0] + "h " + playTime[1] + "m " + playTime[2] + "s";
            FontUtil.tenacityFont18.drawString(playtimeString, playtimeX + playtimeWidth - FontUtil.tenacityFont18.getStringWidth(playtimeString), y + 24, -1);
        }

        List<String> linesRight = Arrays.asList(String.valueOf(gamesPlayed), String.valueOf(killCount));

        for (int i = 0; i < linesRight.size(); i++) {
            int offset = (i + 1) * (FontUtil.tenacityBoldFont18.getHeight() + 6);
            FontUtil.tenacityFont18.drawString(linesRight.get(i), x + width - (FontUtil.tenacityFont18.getStringWidth(linesRight.get(i)) + 5), y + offset + 25, -1);
        }

    };


    //Animation values for going up and down with the time
    float hourYAnimation;
    float minuteYAnimation1;
    float minuteYAnimation2;
    float secondYAnimation2;
    float secondYAnimation1;

    //Animation values for going left or right based on the width of the other charchter
    float secondsSeperateWidthAnim1;
    float secondsSeperateWidthAnim2;
    float minuteSeperateWidthAnim1;
    float minuteSeperateWidthAnim2;

    private void drawAnimatedPlaytime(float playtimeX, float y, float width) {
        int[] playTime = getPlayTime();
        RoundedUtil.drawRoundOutline(playtimeX, y + 21, playtimeWidth, 13, 6, .5f, ColorUtil.applyOpacity(Color.WHITE, 0), Color.WHITE);
        //RoundedUtil.drawRound(playtimeX, y + 22, playtimeWidth, 11, 6, new Color(30, 30, 30));
        StencilUtil.initStencilToWrite();
        RoundedUtil.drawRound(playtimeX, y + 22, playtimeWidth, 11, 6, new Color(30, 30, 30));
        StencilUtil.readStencilBuffer(1);


        float secondX = playtimeX + playtimeWidth - 7;
        FontUtil.tenacityFont18.drawString("s", secondX, y + 24, -1);

        int secondsFirstPlace = (playTime[2] % 10);

        secondYAnimation2 = (float) RenderUtil.animate(20 * secondsFirstPlace, secondYAnimation2, .02);

        secondsSeperateWidthAnim1 = (float) RenderUtil.animate(FontUtil.tenacityFont18.getStringWidth(String.valueOf(secondsFirstPlace)), secondsSeperateWidthAnim1, .05);

        secondX -= secondsSeperateWidthAnim1 + .5;

        for (int i = 0; i < 10; i++) {
            FontUtil.tenacityFont18.drawString(String.valueOf(i), secondX, y + 24 + (i * 20) - secondYAnimation2, -1);
        }

        int secondsSecondPlace = Math.floorDiv(playTime[2], 10);

        secondYAnimation1 = (float) RenderUtil.animate(20 * (secondsSecondPlace), secondYAnimation1, .02);

        secondsSeperateWidthAnim2 = (float) RenderUtil.animate(FontUtil.tenacityFont18.getStringWidth(String.valueOf(secondsSecondPlace)), secondsSeperateWidthAnim2, .05);


        secondX -= secondsSeperateWidthAnim2 + .5;

        for (int i = 0; i < 10; i++) {
            FontUtil.tenacityFont18.drawString(String.valueOf(i), secondX, y + 24 + (i * 20) - secondYAnimation1, -1);
        }

        if (playTime[1] > 0) {

            float minuteX = playtimeX + playtimeWidth - 27;

            FontUtil.tenacityFont18.drawString("m", minuteX, y + 24, -1);

            int minuteFirstPlace = (playTime[1] % 10);

            minuteYAnimation1 = (float) RenderUtil.animate(20 * minuteFirstPlace, minuteYAnimation1, .02);

            minuteSeperateWidthAnim1 = (float) RenderUtil.animate(FontUtil.tenacityFont18.getStringWidth(String.valueOf(minuteFirstPlace)), minuteSeperateWidthAnim1, .05);

            minuteX -= minuteSeperateWidthAnim1 + .5;

            for (int i = 0; i < 10; i++) {
                FontUtil.tenacityFont18.drawString(String.valueOf(i), minuteX, y + 24 + (i * 20) - minuteYAnimation1, -1);
            }

            int minuteSecondPlace = Math.floorDiv(playTime[1], 10);

            minuteYAnimation2 = (float) RenderUtil.animate(20 * (minuteSecondPlace), minuteYAnimation2, .02);

            minuteSeperateWidthAnim2 = (float) RenderUtil.animate(FontUtil.tenacityFont18.getStringWidth(String.valueOf(minuteSecondPlace)), minuteSeperateWidthAnim2, .05);

            minuteX -= minuteSeperateWidthAnim2 + .5;

            for (int i = 0; i < 10; i++) {
                FontUtil.tenacityFont18.drawString(String.valueOf(i), minuteX, y + 24 + (i * 20) - minuteYAnimation2, -1);
            }

            if (playTime[0] > 0) {
                hourYAnimation = (float) RenderUtil.animate(20 * (playTime[0] % 10), hourYAnimation, .02);

                FontUtil.tenacityFont18.drawString("h", playtimeX + playtimeWidth - 44, y + 24, -1);
                for (int i = 0; i < 10; i++) {
                    FontUtil.tenacityFont18.drawString(String.valueOf(i), playtimeX + playtimeWidth - 49, y + 24 + (i * 20) - hourYAnimation, -1);
                }

            }

        }


        StencilUtil.uninitStencilBuffer();
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


}
