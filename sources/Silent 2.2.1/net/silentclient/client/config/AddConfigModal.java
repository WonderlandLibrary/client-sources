package net.silentclient.client.config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.Checkbox;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.NotificationUtils;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class AddConfigModal extends SilentScreen {
    private final GuiScreen parentScreen;
    private int modalWidth;
    private int modalHeight;
    private boolean cloneConfig;

    public AddConfigModal(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        this.modalWidth = 200;
        this.modalHeight = 90;
    }

    @Override
    public void initGui() {
        MenuBlurUtils.loadBlur();
        defaultCursor = false;
        this.cloneConfig = false;
        int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);
        this.buttonList.add(new IconButton(1, x + this.modalWidth - 14 - 3, y + 3, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));
        this.buttonList.add(new Button(2, x + 3, y + this.modalHeight - 23, this.modalWidth - 6, 20, "Done"));
        this.silentInputs.add(new Input("Config Name"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(parentScreen);
                break;
            case 2:
                if(this.silentInputs.get(0).getValue().length() != 0) {
                    String result = Client.getInstance().configManager.newConfig(this.silentInputs.get(0).getValue() + ".txt", cloneConfig);
				    if(!result.equals("success")) {
					    NotificationUtils.showNotification("Error", result);
				    } else {
                        mc.displayGuiScreen(parentScreen);
                    }
                } else {
                    NotificationUtils.showNotification("Error", "Please enter a Config Name");
                }
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MenuBlurUtils.renderBackground(this);
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        GlStateManager.pushMatrix();
        int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);

        // Header
        RenderUtils.drawRect(x, y, this.modalWidth, this.modalHeight, Theme.backgroundColor().getRGB());
        Client.getInstance().getSilentFontRenderer().drawString("New Config", x + 3, y + 3, 14, SilentFontRenderer.FontType.TITLE);

        // Content
        this.silentInputs.get(0).render(mouseX, mouseY, x + 3, y + 23, this.modalWidth - 6);
        Checkbox.render(mouseX, mouseY, x + 3, y + 50, "Clone Current Config", cloneConfig);
        if(Checkbox.isHovered(mouseX, mouseY, x + 3, y + 50)) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
        }

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

        super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);
        this.silentInputs.get(0).onClick(mouseX, mouseY, x + 3, y + 23, this.modalWidth - 6);
        if(Checkbox.isHovered(mouseX, mouseY, x + 3, y + 50)) {
            this.cloneConfig = !this.cloneConfig;
        }
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
