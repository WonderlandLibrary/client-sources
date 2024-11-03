package net.silentclient.client.gui.friends;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
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
import net.silentclient.client.utils.Requests;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class AddFriendModal extends SilentScreen {
    private final GuiScreen parentScreen;
    private int modalWidth;
    private int modalHeight;

    public AddFriendModal(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        this.modalWidth = 200;
        this.modalHeight = 70;
    }

    @Override
    public void initGui() {
        MenuBlurUtils.loadBlur();
        int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);
        this.buttonList.add(new IconButton(1, x + this.modalWidth - 14 - 3, y + 3, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));
        this.buttonList.add(new Button(2, x + 3, y + this.modalHeight - 23, this.modalWidth - 6, 20, "Add Friend"));

        this.silentInputs.add(new Input("Username"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(parentScreen);
                break;
            case 2:
                if(this.silentInputs.get(0).getValue().trim().length() != 0) {
                    String content = Requests.post("https://api.silentclient.net/friends/send_request", new JSONObject().put("username", this.silentInputs.get(0).getValue().trim()).toString());
                    if(content != null) {
                        Client.getInstance().updateFriendsList();
                        mc.displayGuiScreen(parentScreen);
                    } else {
                        NotificationUtils.showNotification("Error", "User not found or you have already added him as a friend");
                    }
                } else {
                    NotificationUtils.showNotification("Error", "Please enter a username");
                }
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MenuBlurUtils.renderBackground(this);
        GlStateManager.pushMatrix();
        int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);

        // Header
        RenderUtils.drawRect(x, y, this.modalWidth, this.modalHeight, Theme.backgroundColor().getRGB());
        Client.getInstance().getSilentFontRenderer().drawString("Add Friend", x + 3, y + 3, 14, SilentFontRenderer.FontType.TITLE);

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
        super.onGuiClosed();
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
