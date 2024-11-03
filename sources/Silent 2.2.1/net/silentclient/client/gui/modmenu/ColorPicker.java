package net.silentclient.client.gui.modmenu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.Checkbox;
import net.silentclient.client.gui.elements.HSBPicker;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.util.ColorPickerAction;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.ScrollHelper;
import net.silentclient.client.utils.types.GlobalSettings;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ColorPicker extends SilentScreen {
    private final GuiScreen parentScreen;
    private long initTime;
    private HSBPicker hsb;
    private final Color defaultColor;
    private boolean chroma;
    private final boolean allowChangeOpacity;
    private int opacity;
    private ColorPickerAction action;
    private ArrayList<GlobalSettings.CustomColor> colors;
    private ScrollHelper scrollHelper = new ScrollHelper();
    private boolean customClose = false;

    public ColorPicker(Color defaultColor, boolean chroma, boolean allowChangeOpacity, int opacity, ColorPickerAction action, GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        this.defaultColor = defaultColor;
        this.allowChangeOpacity = allowChangeOpacity;
        this.chroma = chroma;
        this.opacity = opacity;
        this.action = action;
    }

    @Override
    public void initGui() {
        super.initGui();
        defaultCursor = false;
        this.initTime = System.currentTimeMillis();
        this.buttonList.clear();
        this.silentInputs.clear();
        hsb = new HSBPicker(3, 80, 120, 70, false);
        float[] vals = Color.RGBtoHSB(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue(), null);
        hsb.color = new float[] {vals[0],vals[1],vals[2], defaultColor.getAlpha() / 255.0f};
        hsb.init();

        MenuBlurUtils.loadBlur();

        ModMenu.initBaseButtons(this.buttonList);
        this.buttonList.add(new Button(1, 3, 26, 144, 15, "Back"));
        colors = Client.getInstance().getGlobalSettings().getLatestColors();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MenuBlurUtils.renderBackground(this);
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);

        ModMenu.drawOverlayListBase(height, "Choose a color");

        super.drawScreen(mouseX, mouseY, partialTicks);

        ModMenu.trimContentStart(width, height, true);
        scrollHelper.setStep(5);
        scrollHelper.setElementsHeight(100 + (allowChangeOpacity ? 30 : 0) + (float) Math.ceil((colors.size() + 5) / 5) * 30);
        scrollHelper.setMaxScroll(height - 20);
        scrollHelper.setSpeed(200);
        scrollHelper.setFlag(true);
        float scrollY = scrollHelper.getScroll();

        float colorY = 66 + scrollY;
        Checkbox.render(mouseX, mouseY, 3, colorY, "Chroma", chroma);
        if(Checkbox.isHovered(mouseX, mouseY, 3, colorY)) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
        }

        hsb.render(mouseX, mouseY);

        action.onChange(hsb.getSelectedColorFinal(), chroma, opacity);

        colorY += 100;

        hsb.setPickerY((int) (80 + scrollY));

        if(allowChangeOpacity) {
            GlStateManager.color(1, 1, 1, 1);
            RegularSlider.render(3, colorY, 144, "Opacity", 255, opacity);
            if (RegularSlider.isDrag(mouseX, mouseY, 3, colorY, 144) && (System.currentTimeMillis() - initTime) > 500) {
                double diff = 255;
                double mouse = MathHelper.clamp_double((mouseX - 3) / 144D, 0, 1);
                double newVal = 0 + mouse * diff;
                opacity = (int) newVal;
            }
            colorY += 30;
        }
        int colorX = 3;
        int colorIndex = 0;

        for(GlobalSettings.CustomColor color : colors) {
            ColorUtils.resetColor();
            RenderUtil.drawRoundedRect(colorX, colorY, 20, 20, 3, color.getRGB(!allowChangeOpacity));
            RenderUtil.drawRoundedOutline(colorX, colorY, 20, 20, 3, 2, new Color(0, 0, 0).getRGB());
            if(MouseUtils.isInside(mouseX, mouseY, colorX, colorY, 20, 20)) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
            }
            colorX += 25;
            colorIndex += 1;
            if(colorIndex == 5) {
                colorIndex = 0;
                colorX = 3;
                colorY += 25;
            }
        }

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

        ModMenu.trimContentEnd();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        ModMenu.clickBaseButtons(button, this);

        if (button.id == 1) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        float colorY = 66;
        if(Checkbox.isHovered(mouseX, mouseY, 3, colorY)) {
            chroma = !chroma;
        }

        colorY += 100;
        if(allowChangeOpacity) {
            colorY += 30;
        }
        int colorX = 3;
        int colorIndex = 0;
        for(GlobalSettings.CustomColor customColor : colors) {
            if(MouseUtils.isInside(mouseX, mouseY, colorX, colorY, 20, 20)) {
                Color color = customColor.getColor(!allowChangeOpacity);
                action.onChange(color, false, color.getAlpha());
                customClose = true;
                action.onClose(color, false, color.getAlpha());
                mc.displayGuiScreen(parentScreen);
                break;
            }
            colorX += 25;
            colorIndex += 1;
            if(colorIndex == 5) {
                colorIndex = 0;
                colorX = 3;
                colorY += 25;
            }
        }

        hsb.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        hsb.mouseReleased(mouseX, mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if(!customClose) {
            action.onClose(hsb.getSelectedColorFinal(), chroma, opacity);
            if(!chroma) {
                Client.getInstance().getGlobalSettings().addToLatestColors(new Color(hsb.getSelectedColorFinal().getRed(), hsb.getSelectedColorFinal().getGreen(), hsb.getSelectedColorFinal().getBlue(), opacity));
                Client.getInstance().getGlobalSettings().save();
            }
        }
        MenuBlurUtils.unloadBlur();
        Client.getInstance().configManager.save();
    }
}
