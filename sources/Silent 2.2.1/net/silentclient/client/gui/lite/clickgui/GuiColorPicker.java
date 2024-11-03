package net.silentclient.client.gui.lite.clickgui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.normal.Direction;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.Checkbox;
import net.silentclient.client.gui.elements.HSBPicker;
import net.silentclient.client.gui.elements.Slider;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.hud.HUDConfigScreen;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.util.ColorPickerAction;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.Sounds;
import net.silentclient.client.utils.types.GlobalSettings;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiColorPicker extends SilentScreen {
    private final GuiScreen parentScreen;
    private long initTime;
    private HSBPicker hsb;
    private final Color defaultColor;
    private boolean chroma;
    private final boolean allowChangeOpacity;
    private int opacity;
    private ColorPickerAction action;
    private ArrayList<GlobalSettings.CustomColor> colors;
    private boolean customClose = false;

    public GuiColorPicker(Color defaultColor, boolean chroma, boolean allowChangeOpacity, int opacity, ColorPickerAction action, GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        this.defaultColor = defaultColor;
        this.allowChangeOpacity = allowChangeOpacity;
        this.chroma = chroma;
        this.opacity = opacity;
        this.action = action;
    }

    @Override
    public void initGui() {
        defaultCursor = false;
        this.initTime = System.currentTimeMillis();
    	MenuBlurUtils.loadBlur();
        int addX = 190;
        int addY = 110;
        int x = (width / 2) - addX;
        int y = (height / 2) - addY;
        int height = addY * 2;
        this.buttonList.add(new Button(1, x + 5, y + 25, 75, 20, "< Back"));
        this.buttonList.add(new Button(2, x + 5, (y + height) - 26, 75, 20, "Edit HUD"));
        hsb = new HSBPicker(x + 100, y + 40, 120, 70, false);
        float[] vals = Color.RGBtoHSB(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue(), null);
        hsb.color = new float[] {vals[0],vals[1],vals[2], defaultColor.getAlpha() / 255.0f};
        hsb.init();
        colors = Client.getInstance().getGlobalSettings().getLatestColors();
    }
    
    @Override
    public void onGuiClosed() {
        if(!customClose) {
            action.onClose(hsb.getSelectedColorFinal(), chroma, opacity);
            if(!chroma) {
                Client.getInstance().getGlobalSettings().addToLatestColors(new Color(hsb.getSelectedColorFinal().getRed(), hsb.getSelectedColorFinal().getGreen(), hsb.getSelectedColorFinal().getBlue(), opacity));
                Client.getInstance().getGlobalSettings().save();
            }
        }
        MenuBlurUtils.unloadBlur();
    	super.onGuiClosed();
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MenuBlurUtils.renderBackground(this);

        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);

        int addX = 190;
        int addY = 110;

        int x = (width / 2) - addX;
        int y = (height / 2) - addY;
        int width = addX * 2;
        int height = addY * 2;
        GlStateManager.pushMatrix();
		GlUtils.startScale((float) ((x) + (x) + width) / 2, (float) ((y) + (y + height)) / 2, (float) ClickGUI.introAnimation.getValue());
        RenderUtil.drawRoundedRect(x, y, width, height, 10, Theme.backgroundColor().getRGB());

		GL11.glPushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int settingY = y + 25;
        action.onChange(hsb.getSelectedColorFinal(), chroma, opacity);

        int settingHeight = 10 + 5;

        Checkbox.render(mouseX, mouseY, x + 100, settingY - 1, "Chroma", chroma);
        if(Checkbox.isHovered(mouseX, mouseY, x + 100, settingY - 1)) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
        }

        settingY += settingHeight + 90;

        hsb.render(mouseX, mouseY);

        if(allowChangeOpacity) {
            GlStateManager.color(1, 1, 1, 1);
            Slider.render(x, settingY - 1, width, "Opacity", 255, opacity);
            if (Slider.isDrag(mouseX, mouseY, x, settingY - 1, width) && (System.currentTimeMillis() - initTime) > 500) {
                double diff = 255;
                double mouse = MathHelper.clamp_double((double) (mouseX - Slider.getLeft(x, width)) / 90D, 0, 1);
                double newVal = 0 + mouse * diff;
                opacity = (int) newVal;
            }
            settingY += 15;
        }
        int colorX = x + 100;
        int colorIndex = 0;

        for(GlobalSettings.CustomColor color : colors) {
            ColorUtils.resetColor();
            RenderUtil.drawRoundedRect(colorX, settingY, 20, 20, 3, color.getRGB(!allowChangeOpacity));
            RenderUtil.drawRoundedOutline(colorX, settingY, 20, 20, 3, 2, new Color(0, 0, 0).getRGB());
            if(MouseUtils.isInside(mouseX, mouseY, colorX, settingY, 20, 20)) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
            }
            colorX += 25;
            colorIndex += 1;
            if(colorIndex == 7) {
                colorIndex = 0;
                colorX = x + 100;
                settingY += 25;
            }
        }

        GL11.glPopMatrix();
        
		RenderUtil.drawImage(new ResourceLocation("silentclient/logos/logo.png"), x + 5, y + 5, 77, 15);
		Client.getInstance().getSilentFontRenderer().drawString("Choose a color", x + 100, (int) (y + 5), 14, SilentFontRenderer.FontType.TITLE);


        super.drawScreen(mouseX, mouseY, partialTicks);
        
        if(ClickGUI.close) {
			ClickGUI.introAnimation.setDirection(Direction.BACKWARDS);
			if(ClickGUI.introAnimation.isDone(Direction.BACKWARDS)) {
				mc.displayGuiScreen(null);
			}
            Client.getInstance().getMouseCursorHandler().disableCursor();
        } else {
            Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
        }
	
		GlUtils.stopScale();
		GlStateManager.popMatrix();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 1) {
            mc.displayGuiScreen(parentScreen);
        }

        if(button.id == 2) {
            mc.displayGuiScreen(new HUDConfigScreen(this));
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    	
    	int addX = 190;
        int addY = 110;

        int x = (width / 2) - addX;
        int y = (height / 2) - addY;
        int settingY = y + 25;

        hsb.mouseClicked(mouseX, mouseY, mouseButton);

        if(Checkbox.isHovered(mouseX, mouseY, x + 100, settingY - 1)) {
            Sounds.playButtonSound();
            chroma = !chroma;
        }

        settingY += 15 + 90;
        if(allowChangeOpacity) {
            settingY += 15;
        }
        int colorX = x + 100;
        int colorIndex = 0;

        for(GlobalSettings.CustomColor customColor : colors) {
            if(MouseUtils.isInside(mouseX, mouseY, colorX, settingY, 20, 20)) {
                Color color = customColor.getColor(!allowChangeOpacity);
                action.onChange(color, false, color.getAlpha());
                customClose = true;
                action.onClose(color, false, color.getAlpha());
                mc.displayGuiScreen(parentScreen);
                break;
            }
            colorX += 25;
            colorIndex += 1;
            if(colorIndex == 7) {
                colorIndex = 0;
                colorX = x + 100;
                settingY += 25;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        hsb.mouseReleased(mouseX, mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            ClickGUI.close = true;
            Client.getInstance().configManager.save();
        }
    }
}
