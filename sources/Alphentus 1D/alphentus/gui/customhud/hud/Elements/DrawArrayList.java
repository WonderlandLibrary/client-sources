package alphentus.gui.customhud.hud.Elements;

import alphentus.gui.customhud.Palette;
import alphentus.gui.customhud.settings.settings.SetValues;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class DrawArrayList {

    Minecraft mc = Minecraft.getMinecraft();
    SetValues setValues = Init.getInstance().setValues;

    ScaledResolution scaledResolution;
    FontRenderer unicodeFontRenderer;

    Color backGroundColor;
    int color;
    Color colorAWT = Color.WHITE;

    boolean rectLeft;
    boolean rectRight;

    public DrawArrayList() {
        backGroundColor = new Color(0, 0, 0, 0);
    }

    public void drawArrayList() {
        final int yPlus[] = {2};
        final int[] y = {(int) setValues.listHeight.getCurrentValue()};
        setUp();

        backGroundColor = new Color((int) setValues.backgroundColor.getCurrentValue(), (int) setValues.backgroundColor.getCurrentValue(), (int) setValues.backgroundColor.getCurrentValue(), (int) setValues.backgroundAlpha.getCurrentValue());

        List sortedArrayList = sortArrayList(unicodeFontRenderer);

        int i = 0;
        for (int iMod = sortedArrayList.size(); i < iMod; i++) {
            Mod mod = (Mod) sortedArrayList.get(i);

            if (mod.animationDavid < 0.1F)
                mod.animationDavid += 0.001 * RenderUtils.deltaTime;
            if (mod.animationDavid > 0.1F)
                mod.animationDavid = 0.1F;

            int rectThickness = (int) setValues.rectThickness.getCurrentValue();

            if (setValues.chooseColor.getCurrentMode().equals("Rainbow")) {
                if (setValues.pulsate.isState()) {
                    colorAWT = getRainbowColor((int) setValues.rainbowSpeed.getCurrentValue(), y[0] * setValues.rainbowOffset.getCurrentValue());
                } else {
                    color = getRainbow((int) setValues.rainbowSpeed.getCurrentValue(), y[0] * setValues.rainbowOffset.getCurrentValue());
                }
            }

            if (setValues.pulsate.isState()) {
                color = Palette.fade(colorAWT, 100, sortedArrayList.indexOf(mod) + 30).getRGB();
            }

            String s = mod.getModuleName() + (mod.getInfoName().equals("") ? "" : " " + mod.getInfoName());

            if (rectRight)
                Gui.drawRect(scaledResolution.getScaledWidth() - rectThickness, y[0], scaledResolution.getScaledWidth(), (int) (y[0] + unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue()), color);

            if (rectLeft)
                Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 5 - rectThickness), y[0], (int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 5), (int) (y[0] + unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue()), color);

            if (setValues.background.isState())
                Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 5 + rectThickness : 5)), y[0], scaledResolution.getScaledWidth() - (rectRight ? rectThickness : 0), (int) (y[0] + unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue()), backGroundColor.getRGB());

            if (setValues.chooseAnimation.getCurrentMode().equals("Plop")) {
                GL11.glPushMatrix();
                GL11.glTranslatef(scaledResolution.getScaledWidth() - unicodeFontRenderer.getStringWidth(s) / 2, y[0] - unicodeFontRenderer.FONT_HEIGHT / 2 - 2, 0);
                GL11.glScalef(0.9F + mod.animationDavid, 0.9F + mod.animationDavid, 0);
                GL11.glTranslatef(-(scaledResolution.getScaledWidth() - unicodeFontRenderer.getStringWidth(s) / 2), -(y[0] - unicodeFontRenderer.FONT_HEIGHT / 2 - 2), 0);
            }

            if (setValues.chooseAnimation.getCurrentMode().equals("Plop")) {
                mod.getTranslate().setX(unicodeFontRenderer.getStringWidth(s));
            }

            unicodeFontRenderer.drawStringWithShadow(s, scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 3 + rectThickness : 3) + setValues.textX.getCurrentValue(), y[0] + setValues.textHeight.getCurrentValue(), color, setValues.fontShadow.isState());

            Mod nextMod = null;
            int index = sortedArrayList.indexOf(mod) + 1;

            if (sortedArrayList.size() > index) {
                nextMod = this.nextStateModule(sortedArrayList, index);
            }

            if (setValues.outline.isState()) {
                if (nextMod != null) {
                    String sNextMod = nextMod.getModuleName() + (nextMod.getInfoName().equals("") ? "" : " " + nextMod.getInfoName());
                    float differenceOfNextModule = unicodeFontRenderer.getStringWidth(s) - unicodeFontRenderer.getStringWidth(sNextMod);
                    RenderUtils.relativeRect((scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 5 + rectThickness : 5)), y[0] - 1, (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 5 + rectThickness : 5)) + 1, (int) (y[0] + unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue()), color);
                    RenderUtils.relativeRect((scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 5 + rectThickness : 5)), (int) (y[0] + unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue()) - 1, (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 5 + rectThickness : 5)) + differenceOfNextModule, (int) (y[0] + unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue()), color);
                } else {
                    Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 5 + rectThickness : 5)), (int) (y[0] + unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue()) - 1, scaledResolution.getScaledWidth() - (rectRight ? rectThickness : 0), (int) (y[0] + unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue()), color);
                    RenderUtils.relativeRect((scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 5 + rectThickness : 5)), y[0] - 1, (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 5 + rectThickness : 5)) + 1, (int) (y[0] + unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue()), color);
                }

                if (i == 0) {
                    Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - (rectRight ? 5 + rectThickness : rectLeft ? 5 + rectThickness : 5)), y[0] - 1, scaledResolution.getScaledWidth() + (rectRight ? rectThickness : 0), (int) (y[0]), color);
                }
            }

            if (setValues.chooseAnimation.getCurrentMode().equals("Plop"))
                GL11.glPopMatrix();

            mod.getTranslate().interpolate(setValues.chooseAnimation.getCurrentMode().equals("Plop") ? unicodeFontRenderer.getStringWidth(s) : unicodeFontRenderer.getStringWidth(s), unicodeFontRenderer.FONT_HEIGHT + setValues.textOffset.getCurrentValue(), 1);


            y[0] += mod.getTranslate().getY();
        }
    }


    private void setUp() {
        scaledResolution = new ScaledResolution(mc);

        if (setValues.chooseColor.getCurrentMode().equals("White")) {
            color = Color.WHITE.getRGB();
            colorAWT = Color.WHITE;
        }

        if (setValues.chooseColor.getCurrentMode().equals("Client")) {
            color = Init.getInstance().CLIENT_COLOR.getRGB();
            colorAWT = Init.getInstance().CLIENT_COLOR;
        }

        if (setValues.chooseColor.getCurrentMode().equals("Custom")) {
            color = new Color((int) setValues.colorRed.getCurrentValue(), (int) setValues.colorGreen.getCurrentValue(), (int) setValues.colorBlue.getCurrentValue(), 255).getRGB();
            colorAWT = new Color((int) setValues.colorRed.getCurrentValue(), (int) setValues.colorGreen.getCurrentValue(), (int) setValues.colorBlue.getCurrentValue(), 255);
        }

        if (setValues.chooseRect.getCurrentMode().equals("Right")) {
            rectRight = true;
            rectLeft = false;
        } else if (setValues.chooseRect.getCurrentMode().equals("Left")) {
            rectRight = false;
            rectLeft = true;
        } else if (setValues.chooseRect.getCurrentMode().equals("None")) {
            rectRight = false;
            rectLeft = false;
        }

        switch (setValues.chooseFont.getCurrentMode()) {
            case "Vanilla":
                if (unicodeFontRenderer != Minecraft.getMinecraft().fontRendererObj)
                    unicodeFontRenderer = Minecraft.getMinecraft().fontRendererObj;
                break;
            case "Thruster":
                if (unicodeFontRenderer != Init.getInstance().fontManager.getFont("Thruster-Regular", setValues.fontSize.getCurrentValue()))
                    unicodeFontRenderer = Init.getInstance().fontManager.getFont("Thruster-Regular", setValues.fontSize.getCurrentValue());
                break;
            case "BebasNeue":
                if (unicodeFontRenderer != Init.getInstance().fontManager.getFont("BebasNeue-Regular", setValues.fontSize.getCurrentValue()))
                    unicodeFontRenderer = Init.getInstance().fontManager.getFont("BebasNeue-Regular", setValues.fontSize.getCurrentValue());
                break;
            case "Tahoma":
                if (unicodeFontRenderer != Init.getInstance().fontManager.getFont("tahoma", setValues.fontSize.getCurrentValue()))
                    unicodeFontRenderer = Init.getInstance().fontManager.getFont("tahoma", setValues.fontSize.getCurrentValue());
                break;
            case "Verdana":
                if (unicodeFontRenderer != Init.getInstance().fontManager.getFont("VERDANA", setValues.fontSize.getCurrentValue()))
                    unicodeFontRenderer = Init.getInstance().fontManager.getFont("VERDANA", setValues.fontSize.getCurrentValue());
                break;
            case "Comfortaa":
                if (unicodeFontRenderer != Init.getInstance().fontManager.getFont("comfortaa", setValues.fontSize.getCurrentValue()))
                    unicodeFontRenderer = Init.getInstance().fontManager.getFont("comfortaa", setValues.fontSize.getCurrentValue());
                break;
            case "Jello":
                if (unicodeFontRenderer != Init.getInstance().fontManager.getFont("JelloLight", setValues.fontSize.getCurrentValue()))
                    unicodeFontRenderer = Init.getInstance().fontManager.getFont("JelloLight", setValues.fontSize.getCurrentValue());
                break;
            case "Arial":
                if (unicodeFontRenderer != Init.getInstance().fontManager.getFont("arial", setValues.fontSize.getCurrentValue()))
                    unicodeFontRenderer = Init.getInstance().fontManager.getFont("arial", setValues.fontSize.getCurrentValue());
                break;
            case "Arial Light":
                if (unicodeFontRenderer != Init.getInstance().fontManager.getFont("ARIALLGT", setValues.fontSize.getCurrentValue()))
                    unicodeFontRenderer = Init.getInstance().fontManager.getFont("ARIALLGT", setValues.fontSize.getCurrentValue());
                break;
        }
    }

    // OUTLINE TEST

    public List<Mod> sortArrayList(FontRenderer fontRenderer) {
        List<Mod> modList = new ArrayList<>(Init.getInstance().modManager.getModArrayList());
        Predicate<Mod> condition = mod -> !(mod.getState() && mod.isVisible());
        modList.removeIf(condition);

        Collections.sort(modList, Comparator.comparingDouble((mod) -> {
            return (double) (-fontRenderer.getStringWidth(mod.getModuleName() + (mod.getInfoName().equals("") ? "" : " " + mod.getInfoName())));
        }));

        return modList;
    }

    public Mod nextStateModule(List list, int index) {
        for (int i = 0; i < list.size(); i++) {
            if (i == index) {
                return (Mod) list.get(i);
            }
        }
        return null;
    }

    public static int getRainbow(final int speed, final double d) {
        float hue = (float) ((System.currentTimeMillis() - (d % speed) / 0.25) % speed);
        hue /= speed;
        return Color.getHSBColor(hue, (float) (0.55f), (float) (1F)).getRGB();
    }

    public static Color getRainbowColor(final int speed, final double d) {
        float hue = (float) ((System.currentTimeMillis() - (d % speed) / 0.25) % speed);
        hue /= speed;
        return Color.getHSBColor(hue, (float) (0.55f), (float) (1F));
    }

}