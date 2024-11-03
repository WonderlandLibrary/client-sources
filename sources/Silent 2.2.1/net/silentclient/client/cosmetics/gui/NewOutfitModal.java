package net.silentclient.client.cosmetics.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.Outfits;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.NotificationUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class NewOutfitModal extends SilentScreen {
    private final GuiScreen parentScreen;
    private int modalWidth;
    private int modalHeight;

    public NewOutfitModal(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        this.modalWidth = 200;
        this.modalHeight = 70;
    }

    @Override
    public void initGui() {
        if(mc.thePlayer == null) {
            Client.backgroundPanorama.updateWidthHeight(this.width, this.height);
        } else {
            MenuBlurUtils.loadBlur();
        }
        int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);
        this.buttonList.add(new IconButton(1, x + this.modalWidth - 14 - 3, y + 3, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));
        this.buttonList.add(new Button(2, x + 3, y + this.modalHeight - 23, this.modalWidth - 6, 20, "Done"));
        this.silentInputs.add(new Input("Outfit Name"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(parentScreen);
                break;
            case 2:
                if(this.silentInputs.get(0).getValue().trim().length() == 0) {
                    NotificationUtils.showNotification("Error", "Please enter a Outfit Name");
                    break;
                }
                if(Outfits.getOutfits().stream().filter((c) -> c.name.equals(this.silentInputs.get(0).getValue().trim())).findAny().isPresent()) {
                    NotificationUtils.showNotification("Error", "Outfit already exists.");
                    break;
                }
                Outfits.createOutfit(new Outfits.Outfit(this.silentInputs.get(0).getValue(), Client.getInstance().getAccount().selected_cape, Client.getInstance().getAccount().selected_wings, Client.getInstance().getAccount().selected_icon, Client.getInstance().getAccount().selected_bandana, Client.getInstance().getAccount().selected_hat, Client.getInstance().getAccount().selected_neck, Client.getInstance().getAccount().selected_mask, Client.getInstance().getAccount().selected_shield));
                mc.displayGuiScreen(parentScreen);
                break;
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(mc.thePlayer == null) {
            Client.backgroundPanorama.tickPanorama();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(mc.thePlayer == null) {
            GlStateManager.disableAlpha();
            Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
            GlStateManager.enableAlpha();
            if(Client.getInstance().getGlobalSettings().isLite()) {
                this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 127).getRGB(), new Color(0, 0, 0, 200).getRGB());
            } else {
                this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
            }
        } else {
            MenuBlurUtils.renderBackground(this);
        }
        GlStateManager.pushMatrix();
        int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);

        // Header
        RenderUtils.drawRect(x, y, this.modalWidth, this.modalHeight, Theme.backgroundColor().getRGB());
        Client.getInstance().getSilentFontRenderer().drawString("New Outfit", x + 3, y + 3, 14, SilentFontRenderer.FontType.TITLE);

        // Content
        this.silentInputs.get(0).render(mouseX, mouseY, x + 3, y + 23, this.modalWidth - 6);

        super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);
        this.silentInputs.get(0).onClick(mouseX, mouseY, x + 3, y + 23, this.modalWidth - 6);
    }

    @Override
    public void onGuiClosed() {
        MenuBlurUtils.unloadBlur();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parentScreen);
            return;
        };

        this.silentInputs.get(0).onKeyTyped(typedChar, keyCode);
    }
}
