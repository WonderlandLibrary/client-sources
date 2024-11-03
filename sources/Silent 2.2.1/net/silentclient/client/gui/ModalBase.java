package net.silentclient.client.gui;

import java.io.IOException;

import net.silentclient.client.utils.MenuBlurUtils;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.notification.NotificationManager;
import net.silentclient.client.gui.theme.Theme;

public class ModalBase extends GuiScreen {
	private final GuiScreen parentScreen;
    private final int modalWidth;
    private final int modalHeight;
    private final String modalTitle;

    public ModalBase(GuiScreen parentScreen, String modalTitle) {
        this(parentScreen, modalTitle, 200, 90);
    }
    
    public ModalBase(GuiScreen parentScreen, String modalTitle, int modalWidth, int modalHeight) {
		this.parentScreen = parentScreen;
		this.modalWidth = modalWidth;
		this.modalHeight = modalHeight;
		this.modalTitle = modalTitle;
	}
    
    @Override
    public void initGui() {
        MenuBlurUtils.loadBlur();
    	int x = width / 2 - (this.modalWidth / 2);
        int y = height / 2 - (this.modalHeight / 2);
        this.buttonList.add(new IconButton(0, x + this.modalWidth - 14 - 3, y + 3, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
    	if(button.id == 0) {
    		mc.displayGuiScreen(parentScreen);
    	}
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MenuBlurUtils.renderBackground(this);
    	
    	RenderUtils.drawRect(this.getContentX(), this.getContentY(), this.modalWidth, this.modalHeight, Theme.backgroundColor().getRGB());
        Client.getInstance().getSilentFontRenderer().drawString(this.modalTitle, this.getContentX() + 3, this.getContentY() + 3, 14, SilentFontRenderer.FontType.TITLE);
    	super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public int getContentX() {
    	return width / 2 - (this.modalWidth / 2);
    }
    
    public int getContentY() {
    	return height / 2 - (this.modalHeight / 2);
    }
    
    @Override
    public void onGuiClosed() {
        MenuBlurUtils.unloadBlur();
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    	if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parentScreen);
        };
    }
}
