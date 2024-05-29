package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.utils.objects.Dragging;
import dev.event.EventListener;
import dev.event.impl.render.Render2DEvent;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.impl.render.ShaderEvent;
import dev.settings.ParentAttribute;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ColorSetting;
import dev.settings.impl.ModeSetting;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.GradientUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.utils.font.MinecraftFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.*;
import java.util.List;

public class HudMod extends Module {

    public static final ModeSetting colorMode = new ModeSetting("Hud Mode", "Tenacity", "Tenacity", "Light Rainbow", "Rainbow", "Static", "Fade", "Double Color", "Analogous");
    public static final ModeSetting degree = new ModeSetting("Degree", "30", "30", "-30");
    public static final ColorSetting color = new ColorSetting("Main Color", Tenacity.INSTANCE.getClientColor());
    public static final ColorSetting colorAlt = new ColorSetting("Alt Color", Tenacity.INSTANCE.getAlternateClientColor());
    public static final BooleanSetting movingColors = new BooleanSetting("Moving Colors", false);
    public static final BooleanSetting hueInterpolation = new BooleanSetting("Hue Interpolate", false);
    private final ModeSetting infoMode = new ModeSetting("Info Mode", "Semi Bold", "Semi Bold", "Bold", "Normal");
    private final BooleanSetting whiteInfo = new BooleanSetting("White Info", true);

    public HudMod() {
        super("Hud", Category.RENDER, "Settings that change how the client looks");
        movingColors.addParent(colorMode, m -> m.is("Tenacity") || m.is("Fade") || m.is("Double Color") || m.is("Analogous"));
        color.addParent(colorMode, m -> m.is("Static") || m.is("Fade") || m.is("Double Color") || m.is("Analogous"));
        colorAlt.addParent(colorMode, m -> m.is("Double Color"));
        degree.addParent(colorMode, m -> m.is("Analogous"));
        this.addSettings(colorMode, degree, color, colorAlt, movingColors, hueInterpolation, infoMode, whiteInfo);
        if (!toggled) this.toggleSilent();
    }

    private final EventListener<Render2DEvent> onRender2D = e -> {

        Color[] clientColors = getClientColors();

        GradientUtil.applyGradientHorizontal(5, 5, (float) FontUtil.tenacityBoldFont40.getStringWidth(Tenacity.NAME.toLowerCase()), 20, 1, clientColors[0], clientColors[1], () -> {
            RenderUtil.setAlphaLimit(0);
            FontUtil.tenacityBoldFont40.drawString(Tenacity.NAME.toLowerCase(), 5, 5, new Color(0,0,0,0).getRGB());
        });
        RenderUtil.resetColor();
        FontUtil.tenacityFont20.drawString(Tenacity.VERSION, FontUtil.tenacityBoldFont40.getStringWidth(Tenacity.NAME.toLowerCase()) + 6, 5, clientColors[1].getRGB());

        drawInfo(clientColors);
    };

    private final Map<String, String> bottomLeftText = new LinkedHashMap<>();

    private void drawInfo(Color[] clientColors) {
        ScaledResolution sr = new ScaledResolution(mc);
        bottomLeftText.put("XYZ:", Math.round(mc.thePlayer.posX) + " " + Math.round(mc.thePlayer.posY) + " " + Math.round(mc.thePlayer.posZ));
        bottomLeftText.put("FPS:", String.valueOf(Minecraft.getDebugFPS()));
        bottomLeftText.put("Speed:", String.valueOf(calculateBPS()));

        //InfoStuff
        MinecraftFontRenderer fr = (infoMode.is("Semi Bold") || infoMode.is("Bold")) ? FontUtil.tenacityBoldFont20 : FontUtil.tenacityFont20;

        float yOffset = (float) (14.5 * GuiChat.openingAnimation.getOutput());
        if(whiteInfo.isEnabled()){
            float boldFontMovement = fr.getHeight() + 2 + yOffset;
            for (String line : bottomLeftText.keySet()) {
                fr.drawString(line, 2, sr.getScaledHeight() - boldFontMovement, -1);
                boldFontMovement += fr.getHeight() + 2;
            }
        }else {
            float height = (fr.getHeight() + 2) * bottomLeftText.keySet().size();
            float width = (float) fr.getStringWidth("Speed:");
            GradientUtil.applyGradientVertical(2, sr.getScaledHeight() - (height + yOffset), width, height, 1, clientColors[0], clientColors[1], () -> {
                float boldFontMovement = fr.getHeight() + 2 + yOffset;
                for (String line : bottomLeftText.keySet()) {
                    fr.drawString(line, 2, sr.getScaledHeight() - boldFontMovement, -1);
                    boldFontMovement += fr.getHeight() + 2;
                }
            });
        }

        switch (infoMode.getMode()) {
            case "Bold":
                float boldFontMovement = FontUtil.tenacityBoldFont20.getHeight() + 2 + yOffset;
                for (Map.Entry<String, String> line : bottomLeftText.entrySet()) {

                    FontUtil.tenacityBoldFont20.drawString(line.getValue(), 2 + 2 + FontUtil.tenacityBoldFont20.getStringWidth(line.getKey()),
                            sr.getScaledHeight() - boldFontMovement, -1);

                    boldFontMovement += FontUtil.tenacityBoldFont20.getHeight() + 2;
                }
                break;
            case "Semi Bold":
                float semiBoldFontMovement = FontUtil.tenacityBoldFont20.getHeight() + 2 + yOffset;
                for (Map.Entry<String, String> line : bottomLeftText.entrySet()) {
                    FontUtil.tenacityFont20.drawString(line.getValue(), 2 + 2 + FontUtil.tenacityBoldFont20.getStringWidth(line.getKey()), sr.getScaledHeight() - semiBoldFontMovement, -1);
                    semiBoldFontMovement += FontUtil.tenacityBoldFont20.getHeight() + 2;
                }
                break;

            case "Normal":
                float fontMovement = FontUtil.tenacityFont20.getHeight() + 2 + yOffset;
                for (Map.Entry<String, String> line : bottomLeftText.entrySet()) {
                    FontUtil.tenacityFont20.drawString(line.getValue(), 2 + 2 + FontUtil.tenacityFont20.getStringWidth(line.getKey()), sr.getScaledHeight() - fontMovement, -1);
                    fontMovement += FontUtil.tenacityFont20.getHeight() + 2;
                }
                break;
        }
    }

    private double calculateBPS() {
        double bps = (Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * mc.timer.timerSpeed) * 20;
        return Math.round(bps * 100.0) / 100.0;
    }

    private Color mixColors(Color color1, Color color2) {
        if (movingColors.isEnabled()) {
            return ColorUtil.interpolateColorsBackAndForth(15, 1, color1, color2, hueInterpolation.isEnabled());
        } else {
            return ColorUtil.interpolateColorC(color1, color2, 0);
        }
    }


    public Color[] getClientColors() {
        Color firstColor;
        Color secondColor;
        switch (colorMode.getMode()) {
            case "Tenacity":
                firstColor = mixColors(Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor());
                secondColor = mixColors(Tenacity.INSTANCE.getAlternateClientColor(), Tenacity.INSTANCE.getClientColor());
                break;
            case "Light Rainbow":
                firstColor = ColorUtil.rainbow(15, 1, .6f, 1, 1);
                secondColor = ColorUtil.rainbow(15, 40, .6f, 1, 1);
                break;
            case "Rainbow":
                firstColor = ColorUtil.rainbow(15, 1, 1, 1, 1);
                secondColor = ColorUtil.rainbow(15, 40, 1, 1, 1);
                break;
            case "Static":
                firstColor = color.getColor();
                secondColor = firstColor;
                break;
            case "Fade":
                firstColor = ColorUtil.fade(15, 1, color.getColor(), 1);
                secondColor = ColorUtil.fade(15, 50, color.getColor(), 1);
                break;
            case "Double Color":
                firstColor = mixColors(color.getColor(), colorAlt.getColor());
                secondColor = mixColors(colorAlt.getColor(), color.getColor());
                break;
            case "Analogous":
                int val = degree.is("30") ? 0 : 1;
                Color analogous = ColorUtil.getAnalogousColor(color.getColor())[val];
                firstColor = mixColors(color.getColor(), analogous);
                secondColor = mixColors(analogous, color.getColor());
                break;
            default:
                firstColor = new Color(-1);
                secondColor = new Color(-1);
                break;
        }
        return new Color[]{firstColor, secondColor};
    }

}
