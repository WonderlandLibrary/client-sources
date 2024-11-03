package net.silentclient.client.gui.silentmainmenu;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.gui.CosmeticsGui;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.LiteMainMenu;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.util.GlUtil;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.PromoController;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class SilentMainMenu extends SilentScreen {

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        Client.backgroundPanorama.updateWidthHeight(this.width, this.height);

        this.buttonList.add(new MenuButton(1, this.width / 2 - 130, 40, "Singleplayer"));
        this.buttonList.add(new MenuButton(2, this.width / 2 - 40, 40, "Multiplayer"));
        this.buttonList.add(new MenuButton(3, this.width / 2 + 50, 40, "Quit Game"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        PromoController.getResponse().update();
        GlStateManager.disableAlpha();
        Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 127).getRGB(), new Color(0, 0, 0, 200).getRGB());

        this.drawHeader();
        this.drawBlock(mouseX, mouseY);
        this.drawFooter();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawBlock(int mouseX, int mouseY) {
        int blockY = this.height / 2 - 75;
        int blockX = this.width / 2 - 160;
        if(blockY < 70) {
            blockY = 70;
        }
        RenderUtils.drawRect(blockX, blockY, 320, 150, new Color(20, 20, 20).getRGB());

        if(PromoController.getResponse().getCurrentPanel() != null) {
            if(PromoController.getResponse().getCurrentPanel().getImageLocation() != null) {
                RenderUtil.drawImage(PromoController.getResponse().getCurrentPanel().getImageLocation(), blockX + 78, blockY + 10, 231, 130, false);
            } else {
                PromoController.getResponse().getCurrentPanel().loadImage();
            }
        }


        int optionY = blockY + 10;
        this.drawOptionButton(mouseX, mouseY, blockX + 5, optionY, new ResourceLocation("silentclient/icons/settings/cosmetics.png"), "Cosmetics");
        optionY += 15;
        this.drawOptionButton(mouseX, mouseY, blockX + 5, optionY, new ResourceLocation("silentclient/icons/store_icon.png"), "Store");
        optionY += 15;
        this.drawOptionButton(mouseX, mouseY, blockX + 5, optionY, new ResourceLocation("silentclient/icons/settings.png"), "Options");
        optionY += 15;
        this.drawOptionButton(mouseX, mouseY, blockX + 5, optionY, new ResourceLocation("silentclient/icons/language.png"), "Language");
        optionY += 15;
        this.drawOptionButton(mouseX, mouseY, blockX + 5, optionY, new ResourceLocation("silentclient/icons/back.png"), "Lite Edition");
    }

    private void drawOptionButton(int mouseX, int mouseY, int x, int y, ResourceLocation icon, String text) {
        if(Client.getInstance().getSilentFontRenderer().getStringWidth(text, 12, SilentFontRenderer.FontType.TITLE) > 60) {
            int difference = -(60 - Client.getInstance().getSilentFontRenderer().getStringWidth(text, 12, SilentFontRenderer.FontType.TITLE));
            int oneSymbolWidth = Client.getInstance().getSilentFontRenderer().getStringWidth("a", 12, SilentFontRenderer.FontType.TITLE);
            int symbolsCount = difference / oneSymbolWidth;

            text = text.substring(0, text.length() - symbolsCount - 3);
            text += "...";
        }
        if(optionHovered(mouseX, mouseY, x - 5, y)) {
            RenderUtils.drawRect(x - 5, y, 75, 15, new Color(255, 255, 255, 70).getRGB());
        }
        RenderUtil.drawImage(icon, x, y + 3, 10, 10);
        Client.getInstance().getSilentFontRenderer().drawString(text, x + 13, y + 2, 12, SilentFontRenderer.FontType.TITLE);
    }

    private boolean optionHovered(int mouseX, int mouseY, int x, int y) {
        return MouseUtils.isInside(mouseX, mouseY, x, y, 70, 15);
    }

    private void drawHeader() {
        RenderUtils.drawRect(0, 0, this.width, 25, new Color(20, 20, 20).getRGB());
        RenderUtil.drawImage(new ResourceLocation("silentclient/logos/logo.png"), this.width / 2 - 48.8F, 3, 97.7F, 19);
    }

    private void drawFooter() {
        Client.getInstance().getSilentFontRenderer().drawString("Silent Client " + Client.getInstance().getVersion(), 5, this.height - 17, 14, SilentFontRenderer.FontType.TITLE);
        Client.getInstance().getSilentFontRenderer().drawString("Version 1.8.9", this.width - (Client.getInstance().getSilentFontRenderer().getStringWidth("Version 1.8.9", 14, SilentFontRenderer.FontType.TITLE)) - 5, this.height - 17, 14, SilentFontRenderer.FontType.TITLE);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 3:
                mc.shutdown();
                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int blockX = this.width / 2 - 160;
        int blockY = this.height / 2 - 75;
        if(blockY < 70) {
            blockY = 70;
        }
        if(MouseUtils.isInside(mouseX, mouseY, blockX + 78, blockY + 10, 231, 130) && PromoController.getResponse().getCurrentPanel() != null) {
            try {
                Class<?> oclass = Class.forName("java.awt.Desktop");
                Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(PromoController.getResponse().getCurrentPanel().getRedirectUrl())});
            } catch (Throwable err) {
                err.printStackTrace();
            }
            return;
        }

        int optionY = blockY + 10;
        if(this.optionHovered(mouseX, mouseY, blockX, optionY)) {
            mc.displayGuiScreen(new CosmeticsGui());
            return;
        }
        optionY += 15;
        if(this.optionHovered(mouseX, mouseY, blockX, optionY)) {
            try {
                Class<?> oclass = Class.forName("java.awt.Desktop");
                Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI("https://store.silentclient.net/")});
            } catch (Throwable err) {
                err.printStackTrace();
            }
            return;
        }
        optionY += 15;
        if(this.optionHovered(mouseX, mouseY, blockX, optionY)) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
            return;
        }
        optionY += 15;
        if(this.optionHovered(mouseX, mouseY, blockX, optionY)) {
            mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
            return;
        }
        optionY += 15;
        if(this.optionHovered(mouseX, mouseY, blockX, optionY)) {
            Client.getInstance().getGlobalSettings().setLite(true);
            Client.getInstance().getGlobalSettings().save();
            mc.displayGuiScreen(new LiteMainMenu());
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        Client.backgroundPanorama.tickPanorama();
    }
}
