package net.silentclient.client.gui.quickplay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.theme.input.DefaultInputTheme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.mods.hypixel.QuickPlayMod;
import net.silentclient.client.mods.util.Server;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class QuickplayModeGui extends SilentScreen {
    private final QuickPlayMod.QuickplayModeType quickplayMode;
    private SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
    private float scrollY = 0;

    public QuickplayModeGui(QuickPlayMod.QuickplayModeType quickplayMode) {
        this.quickplayMode = quickplayMode;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.scrollY = 0;
        MenuBlurUtils.loadBlur();
        defaultCursor = false;
        this.buttonList.clear();
        this.silentInputs.clear();
        this.buttonList.add(new IconButton(0, this.width - 14 - 3,  3, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));
        for(QuickPlayMod.QuickplayCommandType quickplayMode : quickplayMode.modes) {
            this.silentInputs.add(new Input(quickplayMode.name, Client.getInstance().getSettingsManager().getSettingByClass(QuickPlayMod.class, String.format("Quickplay Mode&%s&%s", Server.isRuHypixel() ? "RuHypixel" : "Hypixel", quickplayMode.command)).getKeybind()));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        MenuBlurUtils.renderBackground(this);

        Client.getInstance().getSilentFontRenderer().drawString("Quickplay",3, 3, 14, SilentFontRenderer.FontType.TITLE);

        super.drawScreen(mouseX, mouseY, partialTicks);

        int modeBaseX = this.width / 2;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
        int s = r.getScaleFactor();
        int listHeight = height - 24;
        int translatedY = r.getScaledHeight() - 24 - listHeight;
        GL11.glScissor(0 * s, translatedY * s, this.width * s, listHeight * s);

        RenderUtil.drawRoundedOutline(modeBaseX - 205, 24 - scrollAnimation.getValue(), 410, 30, 3, 1, Theme.borderColor().getRGB());
        RenderUtil.drawImage(new ResourceLocation("silentclient/mods/quickplay/"+quickplayMode.icon+".png"), modeBaseX - 205 + 2, 24 + 2 - scrollAnimation.getValue(), 26, 26);
        Client.getInstance().getSilentFontRenderer().drawString(quickplayMode.name, modeBaseX - 205 + 32, 24 + 8 - scrollAnimation.getValue(), 14, SilentFontRenderer.FontType.TITLE);

        int column = 1;
        float modeY = 24 + 40 - scrollAnimation.getValue();
        int inputIndex = 0;

        for(QuickPlayMod.QuickplayCommandType quickplayMode : quickplayMode.modes) {
            int modeX = column == 1 ? modeBaseX - 205 : modeBaseX + 5;
            boolean isCommandHovered = MouseUtils.isInside(mouseX, mouseY, modeX, modeY, 140, 20);
            if(isCommandHovered) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
                RenderUtil.drawRoundedRect(modeX, modeY, 140, 20, 3, new Color(255, 255, 255,  30).getRGB());
            }
            RenderUtil.drawRoundedOutline(modeX, modeY, 140, 20, 3, 1, Theme.borderColor().getRGB());
            Client.getInstance().getSilentFontRenderer().drawString(quickplayMode.name, modeX + 3, modeY + 3, 14, SilentFontRenderer.FontType.TITLE, 140);

            this.silentInputs.get(inputIndex).render(mouseX, mouseY, modeX + 145, modeY, 55, false, new DefaultInputTheme(), true);

            if(column == 1) {
                column = 2;
            } else {
                column = 1;
                modeY += 30;
            }
            inputIndex++;
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
        scrollAnimation.setAnimation(scrollY, 12);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dw = Mouse.getEventDWheel();
        double newScrollY = this.scrollY;
        int height = this.height;
        if(dw != 0) {
            if (dw > 0) {
                dw = -1;
            } else {
                dw = 1;
            }
            float amountScrolled = (float) (dw * 10);
            if (newScrollY + amountScrolled > 0)
                newScrollY += amountScrolled;
            else
                newScrollY = 0;
            if((newScrollY < (((Math.ceil(quickplayMode.modes.size() / 2)) + 1) * 30) && ((((Math.ceil(quickplayMode.modes.size() / 2)) + 1) * 30) + 24 + 40) > height) || amountScrolled < 0) {
                this.scrollY = (float) newScrollY;
                if(this.scrollY < 0) {
                    this.scrollY = 0;
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int modeBaseX = this.width / 2;

        RenderUtil.drawRoundedOutline(modeBaseX - 205, 24, 410, 30, 3, 1, Theme.borderColor().getRGB());
        RenderUtil.drawImage(new ResourceLocation("silentclient/mods/quickplay/"+quickplayMode.icon+".png"), modeBaseX - 205 + 2, 24 + 2, 26, 26);
        Client.getInstance().getSilentFontRenderer().drawString(quickplayMode.name, modeBaseX - 205 + 32, 24 + 8, 14, SilentFontRenderer.FontType.TITLE);

        int column = 1;
        float modeY = 24 + 40 - scrollAnimation.getValue();
        int inputIndex = 0;

        for(QuickPlayMod.QuickplayCommandType quickplayMode : quickplayMode.modes) {
            int modeX = column == 1 ? modeBaseX - 205 : modeBaseX + 5;
            boolean isCommandHovered = MouseUtils.isInside(mouseX, mouseY, modeX, modeY, 140, 20);
            if(isCommandHovered) {
                QuickPlayMod.runCommand(quickplayMode.command);
                mc.displayGuiScreen(null);
            }

            this.silentInputs.get(inputIndex).onClick(mouseX, mouseY, modeX + 145, (int) modeY, 55, false);

            if(column == 1) {
                column = 2;
            } else {
                column = 1;
                modeY += 30;
            }
            inputIndex++;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new QuickplayGui());
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        int inputIndex = 0;
        boolean neededKeyCheck = true;

        for(QuickPlayMod.QuickplayCommandType quickplayMode : quickplayMode.modes) {
            if(silentInputs.get(inputIndex).isFocused()) {
                this.silentInputs.get(inputIndex).onKeyTyped(typedChar, keyCode);
                Setting setting = Client.getInstance().getSettingsManager().getSettingByClass(QuickPlayMod.class, String.format("Quickplay Mode&%s&%s", Server.isRuHypixel() ? "RuHypixel" : "Hypixel", quickplayMode.command));
                setting.setKeybind(this.silentInputs.get(inputIndex).getKey());
                Client.getInstance().getModInstances().getModByClass(QuickPlayMod.class).onChangeSettingValue(setting);
                if(keyCode == Keyboard.KEY_ESCAPE) {
                    neededKeyCheck = false;
                    break;
                }
            }
            inputIndex++;
        }

        if (neededKeyCheck && keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        };
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Client.getInstance().getConfigManager().save();
        MenuBlurUtils.unloadBlur();
    }
}
