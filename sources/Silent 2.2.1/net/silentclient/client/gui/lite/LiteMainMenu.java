package net.silentclient.client.gui.lite;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.gui.CosmeticsGui;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.TooltipIconButton;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.silentmainmenu.MainMenuConcept;
import net.silentclient.client.gui.silentmainmenu.components.AccountPicker;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.PromoController;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class LiteMainMenu extends SilentScreen
{
    private SimpleAnimation bannerAnimation;
    private AccountPicker accountPicker;

    @Override
    public void initGui() {
        super.initGui();
        defaultCursor = false;
        this.accountPicker = new LiteAccountPicker(this.width - 100 - 4, 5);
        this.bannerAnimation = new SimpleAnimation(0);
        Client.backgroundPanorama.updateWidthHeight(this.width, this.height);

        this.buttonList.clear();
        this.buttonList.add(new TooltipIconButton(1, 4, 5, 18, 18, 10, 10, new ResourceLocation("silentclient/icons/news.png"), "News"));
        this.buttonList.add(new TooltipIconButton(2, 24, 5, 18, 18, 10, 10, new ResourceLocation("silentclient/icons/settings/cosmetics.png"), "Cosmetics"));
        this.buttonList.add(new TooltipIconButton(3, 44, 5, 18, 18, 10, 10, new ResourceLocation("silentclient/icons/store_icon.png"), "Store"));
        this.buttonList.add(new TooltipIconButton(4, 64, 5, 18, 18, 10, 10, new ResourceLocation("silentclient/icons/language.png"), "Language"));
        this.buttonList.add(new TooltipIconButton(5, 84, 5, 18, 18, 10, 10, new ResourceLocation("silentclient/icons/back.png"), "Switch to SLC"));

        int buttonY = this.height / 2 - 18;

        if(buttonY - 90 > 50) {
            buttonY = 120;
        }

        this.buttonList.add(new Button(6, this.width / 2 - 90, buttonY, 180, 18, "Singleplayer"));
        this.buttonList.add(new Button(7, this.width / 2 - 90, buttonY + 18 + 5, 180, 18, "Multiplayer"));

        this.buttonList.add(new Button(8, this.width / 2 - 90, buttonY + 18 + 5 + 18 + 5, 87, 18, "Options"));
        this.buttonList.add(new Button(9, this.width / 2 + 2, buttonY + 18 + 5 + 18 + 5, 88, 18, "Quit Game"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        PromoController.getResponse().update();
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        GlStateManager.disableAlpha();
        Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        if(Client.getInstance().getGlobalSettings().isLite()) {
            this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 127).getRGB(), new Color(0, 0, 0, 200).getRGB());
        } else {
            this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        }

        RenderUtil.drawImage(new ResourceLocation("silentclient/logos/menu_logo.png"), this.width / 2 - 80, 20, 160, 70);
        ColorUtils.setColor(new Color(115, 117, 119).getRGB());
        Client.getInstance().getSilentFontRenderer().drawCenteredString("The most complete all-in-one mod library for Minecraft", this.width / 2, 87, 10, SilentFontRenderer.FontType.TITLE);

        ColorUtils.setColor(-1);
        Client.getInstance().getSilentFontRenderer().drawString(3, height - 14, "SLC Lite 1.8.9", 12, SilentFontRenderer.FontType.TITLE);

        if(PromoController.getResponse().getCurrentPanel() != null) {
            if(PromoController.getResponse().getCurrentPanel().getImageLocation() != null) {
                if(MouseUtils.isInside(mouseX, mouseY, this.width - 74, this.height - 42, 71, 40)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                    this.bannerAnimation.setAnimation(4, 20);
                } else {
                    this.bannerAnimation.setAnimation(0, 30);
                }
                RenderUtil.drawRoundTextured(this.width - 74 + (-this.bannerAnimation.getValue()), this.height - 42 + (-this.bannerAnimation.getValue()), 71, 40, 4, 1, PromoController.getResponse().getCurrentPanel().getImageLocation(), false);
            } else {
                PromoController.getResponse().getCurrentPanel().loadImage();
            }
        }

        MouseCursorHandler.CursorType accountCursor = accountPicker.draw(mc, mouseX, mouseY);

        if(accountCursor != null) {
            cursorType = accountCursor;
        }

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(new GuiNews());
                break;
            case 2:
                mc.displayGuiScreen(new CosmeticsGui());
                break;
            case 3:
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI("https://store.silentclient.net/")});
                } catch (Throwable err) {
                    err.printStackTrace();
                }
                break;
            case 4:
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                break;
            case 5:
                Client.getInstance().getGlobalSettings().setLite(false);
                Client.getInstance().getGlobalSettings().save();
                mc.displayGuiScreen(new MainMenuConcept());
                break;
            case 6:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 7:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 8:
                mc.displayGuiScreen(new GuiOptions( this, mc.gameSettings));
                break;
            case 9:
                mc.shutdown();
                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(MouseUtils.isInside(mouseX, mouseY, this.width - 74, this.height - 42, 71, 40) && PromoController.getResponse().getCurrentPanel() != null && PromoController.getResponse().getCurrentPanel().getImageLocation() != null) {
            try {
                Class<?> oclass = Class.forName("java.awt.Desktop");
                Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(PromoController.getResponse().getCurrentPanel().getRedirectUrl())});
            } catch (Throwable err) {
                err.printStackTrace();
            }
        }

        accountPicker.onClick(mc, mouseX, mouseY);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        Client.backgroundPanorama.tickPanorama();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
