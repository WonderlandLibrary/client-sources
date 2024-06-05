package de.tired.api.guis;

import de.tired.api.extension.Extension;
import de.tired.api.util.render.Scissoring;
import de.tired.api.util.font.CustomFont;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.config.Config;
import de.tired.interfaces.IHook;
import de.tired.module.impl.list.visual.ClickGUI;
import de.tired.tired.Tired;
import de.tired.shaderloader.ShaderRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class ConfigGui extends GuiScreen implements IHook {

    public boolean isOver = false;
    private int x, y, dragX, dragY;
    private boolean amongi;
    private static final double widthRect = 410;
    private int scrollAmount = 4;
    public boolean dragging;
    public float animationSussy;
    public float animationY;
    public float susAmongus;
    public boolean sussy = false;

    @Override
    public void initGui() {
        susAmongus = 0;
        for (Config c : Tired.INSTANCE.configManager.configs()) {
            if (Tired.INSTANCE.configManager.configs() != null) {
                Tired.INSTANCE.configManager.loadNoSet(c);
            }
        }
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (dragging) {
            this.x = mouseX + dragX;
            this.y = mouseY + dragY;
        }
        int yJude = 0;

        if (Tired.INSTANCE.configManager.configs().isEmpty()) {
            dragging = false;
            mouseX = 0;
            mouseY = 0;
        }

        ShaderRenderer.stopBlur();

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(widthRect + 20, yJude + 215, susAmongus, new Color(ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getRed(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getGreen(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getBlue(), 122).getRGB());
        if (Tired.INSTANCE.configManager.activeConfig() != null) {
            FontManager.bebasFBig.drawStringWithShadow("Current Config: " + Tired.INSTANCE.configManager.activeConfig().name() + " ", x + widthRect, y + 90, -1);
        }

        final double sussybaka = sussy ? 880 : 0;
        this.animationSussy = (int) AnimationUtil.getAnimationState(animationSussy, sussybaka, Math.max(.2D, Math.abs((double) animationSussy - sussybaka) / 2));

        if (animationSussy > 43) {
            sussy = false;
        }


        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        Scissoring.SCISSORING.scissorOtherWay(x + widthRect - 1, y + 117, 482, 198);

        Gui.drawRect(x + widthRect, y + 115, x + width / 2f + widthRect, y + 315, new Color(20, 20, 20).getRGB());
        this.susAmongus = (int) AnimationUtil.getAnimationState(susAmongus, 1000, Math.max(3.6D, Math.abs((double) susAmongus - 1000)) * 2);

        for (Config c : Tired.INSTANCE.configManager.configs()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                if (yJude + 300 > -7 + yJude) scrollAmount -= 16;
            } else if (wheel > 0) {
                scrollAmount += 34;
                if (scrollAmount > 0)
                    scrollAmount = 0;
            }
            if (yJude * 4 != 0) {
                if (Math.abs(scrollAmount) > yJude * yJude + scrollAmount) {
                    scrollAmount += 16;
                }
            }
            this.animationY = (float) AnimationUtil.getAnimationState((double) this.animationY, scrollAmount, Math.max(0.6D, Math.abs((double) this.animationY - animationY)) * 282);
            isOver = isOver(x + (int) widthRect, y + (int) (yJude + 115 + animationY), 180, (int) (19), mouseX, mouseY);
            Gui.drawRect(x + widthRect, y + yJude + 135 + animationY, x + widthRect + 180, y + yJude + 115 + animationY, Integer.MIN_VALUE);
            if (isOver) {
                Gui.drawRect(x + widthRect, y + yJude + 135 + animationY, x + widthRect + 180, y + yJude + 115 + animationY, Integer.MIN_VALUE);
            }

            String text = c.name().replace("Online", "");

            FontManager.SFPRO.drawStringWithShadow(text.substring(0, 1).toUpperCase() + text.substring(1), x + widthRect + 3, y + 119 + yJude + animationY, -1);

            if (c.name().contains("Online")) {
                double strWidth = FontManager.SFPRO.getStringWidth(c.name().replace("Online", ""));
                FontManager.SFPRO.drawStringWithShadow("[Online]", x + widthRect + 3 + strWidth + 5, y + 119 + yJude + animationY, new Color(0, 220, 140));
            }
            if (!c.name().contains("Online")) {
                double strWidth = FontManager.SFPRO.getStringWidth(c.name().replace("Online", ""));
                FontManager.SFPRO.drawStringWithShadow("[OFFLINE]", x + widthRect + 3 + strWidth + 5, y + 119 + yJude + animationY, new Color(208, 38, 38));
            }


            yJude += 30;
        }
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(mouseX, mouseY, animationSussy, new Color(ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getRed(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getGreen(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getBlue(), 162).getRGB());
        if (sussy) {
            float susLol = (int) animationSussy;
            for (int i = 0; i < 7; i++) {
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(mouseX, mouseY, susLol, new Color(ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getRed(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getGreen(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getBlue(), 162).getRGB());
                susLol -= 10;
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);


        if (Tired.INSTANCE.configManager.configs().isEmpty()) {
            ShaderRenderer.stopBlur();
            FontManager.SFPRO.drawCenteredStringWithShadow("You got no configs!", width / 2, height / 2, -1);
            FontManager.SFPRO.drawCenteredStringWithShadow("You can create configs with .c save \"Name\"", width / 2, height / 2 + 30, -1);
        }


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int yAxis = 0;
        boolean isOverHQ = isOver((int) x, y, (int) (width / 2f + widthRect ), 315, mouseX, mouseY);
        if (isOverHQ) {
            if (mouseButton == 0) {
                this.dragX = x - mouseX;
                this.dragY = y - mouseY;
                this.dragging = true;
            }
        }


        for (Config c : Tired.INSTANCE.configManager.configs()) {

            int wheel = Mouse.getDWheel();

            if (wheel < 0) {
                if (yAxis + 300 > -7 + yAxis) scrollAmount -= 16;
            } else if (wheel > 0) {
                scrollAmount += 34;
                if (scrollAmount > 0)
                    scrollAmount = 0;
            }
            if (yAxis * 4 != 0) {
                if (Math.abs(scrollAmount) > yAxis * yAxis + scrollAmount) {
                    scrollAmount += 16;
                }
            }


            isOver = isOver((int) ((int) x + widthRect), y + yAxis + 115 + scrollAmount, 200, 19, mouseX, mouseY);
            if (isOver) {
                Tired.INSTANCE.configManager.load(c.name());
            }
            yAxis += 30;
        }
        sussy = true;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public int calculateMiddle(String text, CustomFont fontRenderer, int x, int widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    private enum currentTap {
        PRIVATE,
        ONLINE
    }

}
