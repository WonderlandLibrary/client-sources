package net.silentclient.client.gui.multiplayer;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
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
import java.util.regex.Pattern;

public class AddServerGui extends SilentScreen {
    private final SilentMultiplayerGui parentScreen;
    private int modalWidth;
    private int modalHeight;
    private Button serverResourcePacks;
    private final ServerData serverData;
    private final boolean edit;
    private final int index;

    public AddServerGui(SilentMultiplayerGui p_i1033_1_, ServerData p_i1033_2_, boolean edit, int index)
    {
        this.parentScreen = p_i1033_1_;
        this.serverData = p_i1033_2_;
        this.modalWidth = 200;
        this.modalHeight = 120;
        this.edit = edit;
        this.index = index;
    }

    public AddServerGui(SilentMultiplayerGui p_i1033_1_, ServerData p_i1033_2_, boolean edit)
    {
        this(p_i1033_1_, p_i1033_2_, edit, -1);
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
        this.buttonList.add(this.serverResourcePacks = new Button(2, x + 3, y + 23 + 25 + 25, this.modalWidth - 6, 20, "Server Resource Packs: " + this.serverData.getResourceMode().getMotd().getFormattedText()));
        this.buttonList.add(new Button(3, x + 3, y + this.modalHeight - 23, this.modalWidth - 6, 20, "Done"));
        this.silentInputs.add(new Input("Server Name", this.serverData.serverName));
        this.silentInputs.add(new Input("Server IP", this.serverData.serverIP, -1, Pattern
                .compile("^[~`!@#$%^&*()_+=[\\\\]\\\\\\\\\\\\{\\\\}|;':\\\",.\\\\/<>?a-zA-Z0-9-\\s]+$"), 60, false));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(parentScreen);
                break;
            case 2:
                this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % ServerData.ServerResourceMode.values().length]);
                this.serverResourcePacks.displayString = "Server Resource Packs: " + this.serverData.getResourceMode().getMotd().getFormattedText();
                break;
            case 3:
                if(this.silentInputs.get(0).getValue().trim().length() == 0) {
                    NotificationUtils.showNotification("Error", "Please enter a Server Name");
                    break;
                }
                if(this.silentInputs.get(1).getValue().trim().length() == 0) {
                    NotificationUtils.showNotification("Error", "Please enter a Server IP");
                    break;
                }
                this.serverData.serverName = this.silentInputs.get(0).getValue().trim();
                this.serverData.serverIP = this.silentInputs.get(1).getValue().trim();
                if(edit) {
                    parentScreen.editServer(serverData, index);
                } else {
                    parentScreen.getServerList().addServerData(serverData);
                    parentScreen.getServerList().saveServerList();
                }
                parentScreen.refreshServerList();
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
        Client.getInstance().getSilentFontRenderer().drawString("Add Server", x + 3, y + 3, 14, SilentFontRenderer.FontType.TITLE);

        // Content
        this.silentInputs.get(0).render(mouseX, mouseY, x + 3, y + 23, this.modalWidth - 6);
        this.silentInputs.get(1).render(mouseX, mouseY, x + 3, y + 23 + 25, this.modalWidth - 6);

        super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);
        this.silentInputs.get(0).onClick(mouseX, mouseY, x + 3, y + 23, this.modalWidth - 6);
        this.silentInputs.get(1).onClick(mouseX, mouseY, x + 3, y + 23 + 25, this.modalWidth - 6);
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
        this.silentInputs.get(1).onKeyTyped(typedChar, keyCode);
    }
}
