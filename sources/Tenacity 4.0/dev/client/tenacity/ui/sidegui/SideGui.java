package dev.client.tenacity.ui.sidegui;

import dev.client.tenacity.module.impl.render.ClickGuiMod;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.utils.misc.MathUtils;
import dev.utils.objects.Drag;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import dev.utils.time.TimerUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.HashMap;

public class SideGui extends GuiPanel {

    private final ConfigPanel configPanel = new ConfigPanel();
  //  private final ScriptPanel scriptPanel = new ScriptPanel();
    private final String[] categories = {"Scripts", "Configs"};
    public boolean focused;
    public Animation clickAnimation;
    private Animation hoverAnimation;
    private Animation textAnimation;
    private Animation moveOverGradientAnimation;
    private HashMap<String, Animation[]> categoryAnimation = new HashMap<>();
    private Drag drag;
    private String currentCategory = "Configs";

    private TimerUtil timerUtil;


    @Override
    public void initGui() {
        focused = false;
        timerUtil = new TimerUtil();
        rectWidth = 550;
        rectHeight = 350;
        ScaledResolution sr = new ScaledResolution(mc);
        drag = new Drag(sr.getScaledWidth() - 30, sr.getScaledHeight() / 2f - rectHeight / 2f);
        textAnimation = new DecelerateAnimation(500, 1);
        textAnimation.setDirection(Direction.BACKWARDS);
        clickAnimation = new DecelerateAnimation(325, 1);
        clickAnimation.setDirection(Direction.BACKWARDS);
        categoryAnimation = new HashMap<>();
        for (String category : categories) {
            categoryAnimation.put(category, new Animation[]{new DecelerateAnimation(250, 1), new DecelerateAnimation(250, 1)});
        }

        moveOverGradientAnimation = new DecelerateAnimation(250, 1);
        moveOverGradientAnimation.setDirection(Direction.BACKWARDS);

        hoverAnimation = new DecelerateAnimation(250, 1);
        hoverAnimation.setDirection(Direction.BACKWARDS);
        configPanel.initGui();
     //   scriptPanel.initGui();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        switch (currentCategory) {
            case "Configs":
                configPanel.keyTyped(typedChar, keyCode);
                break;
            case "Scripts":
                //scriptPanel.keyTyped(typedChar, keyCode);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, int alpha) {
        if (configPanel.reInit) {
            configPanel.initGui();
            configPanel.reInit = false;
        }
        //if (scriptPanel.reInit) {
          //  scriptPanel.initGui();
       // }

        clickAnimation.setDirection(focused ? Direction.FORWARDS : Direction.BACKWARDS);
        boolean hovering = HoveringUtil.isHovering(drag.getX(), drag.getY(), rectWidth, rectHeight, mouseX, mouseY);
        hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);
        ScaledResolution sr = new ScaledResolution(mc);

        boolean setDirection = !focused && (!timerUtil.hasTimeElapsed(6000) || (!hoverAnimation.isDone() || hoverAnimation.isDone() && hoverAnimation.getDirection().equals(Direction.FORWARDS)));
        textAnimation.setDirection(setDirection ? Direction.FORWARDS : Direction.BACKWARDS);


        if(!textAnimation.isDone() || textAnimation.getDirection().equals(Direction.FORWARDS) && textAnimation.isDone()) {
            FontUtil.iconFont26.drawString(FontUtil.PLAY, drag.getX() -
                            ((FontUtil.iconFont26.getStringWidth(FontUtil.PLAY) + 10) * textAnimation.getOutput()),
                    drag.getY() + FontUtil.iconFont26.getMiddleOfBox(rectHeight),
                    ColorUtil.applyOpacity(-1, (float) textAnimation.getOutput() * 0.5F));
        }


        if (!clickAnimation.isDone()) {
            drag.setX(MathUtils.interpolateFloat(sr.getScaledWidth() - 30, focused ? sr.getScaledWidth() / 2f - rectWidth / 2f : drag.getX(), (float) clickAnimation.getOutput()));
            drag.setY(MathUtils.interpolateFloat(sr.getScaledHeight() / 2f - rectHeight / 2f, drag.getY(), (float) clickAnimation.getOutput()));
        }

        boolean gradient = drag.getX() + rectWidth > sr.getScaledWidth() && focused && (clickAnimation.isDone() && clickAnimation.getDirection().equals(Direction.FORWARDS));
        moveOverGradientAnimation.setDirection(gradient ? Direction.FORWARDS : Direction.BACKWARDS);


        float rectAlpha = (float) Math.min((float) ((185 + (30 * hoverAnimation.getOutput()) + (70 * clickAnimation.getOutput()))) - (70 * moveOverGradientAnimation.getOutput()), 255);
        rectAlpha *= alpha / 255f;

        Color mainRectColor = new Color(30, 30, 30, (int) rectAlpha);

        if (focused) {
            drag.onDraw(mouseX, mouseY);
        }

        float x = drag.getX(), y = drag.getY();
        RoundedUtil.drawRound(x, y, rectWidth, rectHeight, 9, mainRectColor);
        if (!focused) return;
        int textColor = ColorUtil.applyOpacity(-1, alpha / 255f);
        int seperation = 0;
        for (String category : categories) {
            float xVal = x + rectWidth / 2f - 50 + seperation;
            float yVal = y + 15;

            boolean hovered = HoveringUtil.isHovering(xVal - 30, yVal - 5, 60, FontUtil.tenacityBoldFont26.getHeight() + 10, mouseX, mouseY);
            Animation hoverAnimation = categoryAnimation.get(category)[0];
            Animation enableAnimation = categoryAnimation.get(category)[1];

            hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
            enableAnimation.setDirection(currentCategory.equals(category) ? Direction.FORWARDS : Direction.BACKWARDS);


            Color categoryColor = new Color(45, 45, 45, alpha);
            Color hoverColor = ColorUtil.interpolateColorC(categoryColor, ColorUtil.brighter(categoryColor, .8f), (float) hoverAnimation.getOutput());
            Color finalColor = ColorUtil.interpolateColorC(hoverColor, ColorUtil.applyOpacity(ClickGuiMod.color.getColor(), alpha / 255f), (float) enableAnimation.getOutput());

            RoundedUtil.drawRound(xVal - 30, yVal - 5, 60, FontUtil.tenacityBoldFont26.getHeight() + 10, 6, finalColor);

            RenderUtil.resetColor();
            FontUtil.tenacityBoldFont26.drawCenteredString(category, xVal, y + 15, textColor);
            seperation += 100;
        }

        Gui.drawRect2(x + 20, y + 50, rectWidth - 40, 1, new Color(45, 45, 45, alpha).getRGB());


        if (currentCategory.equals("Scripts")) {
           /* scriptPanel.x = x;
            scriptPanel.rawY = y;
            scriptPanel.rectWidth = rectWidth;
            scriptPanel.rectHeight = rectHeight;
            scriptPanel.drawScreen(mouseX, mouseY, partialTicks, (int) rectAlpha);*/
        } else {
            configPanel.x = x;
            configPanel.rawY = y;
            configPanel.rectWidth = rectWidth;
            configPanel.rectHeight = rectHeight;
            configPanel.drawScreen(mouseX, mouseY, partialTicks, (int) rectAlpha);
        }
        RenderUtil.setAlphaLimit(0);
        Gui.drawGradientRect2(x + 20, y + 51, rectWidth - 40, 8, new Color(0, 0, 0, (int) (60 * (alpha / 255f))).getRGB(), new Color(0, 0, 0, 0).getRGB());

        RenderUtil.setAlphaLimit(0);
        Gui.drawGradientRectSideways2(sr.getScaledWidth() - 40, 0, 40, sr.getScaledHeight(),
                ColorUtil.applyOpacity(ClickGuiMod.color.getColor().getRGB(), 0),
                ColorUtil.applyOpacity(ClickGuiMod.color.getColor().getRGB(), (float) (.4 * moveOverGradientAnimation.getOutput())));

        RenderUtil.setAlphaLimit(1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        boolean hovering = HoveringUtil.isHovering(drag.getX(), drag.getY(), rectWidth, rectHeight, mouseX, mouseY);
        if (hovering && button == 0 && !focused) {
            focused = true;
            return;
        }

        if (focused) {
            boolean canDrag = HoveringUtil.isHovering(drag.getX(), drag.getY(), rectWidth, 50, mouseX, mouseY)
                    || HoveringUtil.isHovering(drag.getX(), drag.getY(), 20, rectHeight, mouseX, mouseY);
            drag.onClick(mouseX, mouseY, button, canDrag);

            float x = drag.getX(), y = drag.getY();
            int seperation = 0;
            for (String category : categories) {
                float xVal = x + rectWidth / 2f - 50 + seperation;
                float yVal = y + 15;

                boolean hovered = HoveringUtil.isHovering(xVal - 30, yVal - 5, 60, FontUtil.tenacityBoldFont26.getHeight() + 10, mouseX, mouseY);

                if (hovered) {
                    currentCategory = category;
                    return;
                }
                seperation += 100;
            }

            if (currentCategory.equals("Configs")) {
                configPanel.mouseClicked(mouseX, mouseY, button);
            } else {
               // scriptPanel.mouseClicked(mouseX, mouseY, button);
            }

        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        if (focused) {
            drag.onRelease(button);
            ScaledResolution sr = new ScaledResolution(mc);
            if (drag.getX() + rectWidth > sr.getScaledWidth() && clickAnimation.isDone()) {
                focused = false;
            }
            if (currentCategory.equals("Configs")) {
                configPanel.mouseReleased(mouseX, mouseY, button);
            }
        }
    }
}
