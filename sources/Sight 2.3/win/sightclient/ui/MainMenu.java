package win.sightclient.ui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import win.sightclient.Sight;
import win.sightclient.ui.altmanager.gui.GuiAltManager;
import win.sightclient.utils.management.Changelog;

public class MainMenu extends GuiScreen {

	public MainMenu() {
		
	}
	
	@Override
	public void initGui() {
		String[] buttons = new String[] {"Single Player", "Multi Player", "Alt Manager", "Options"};
		int offset = 60;
        this.buttonList.add(new MenuButton(1, (this.width / 2) - 258 + offset, this.height / 2 - 20, 75, 75, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new MenuButton(2, (this.width / 2) - 148 + offset, this.height / 2 - 20, 75, 75, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new MenuButton(3, (this.width / 2) - 40 + offset, this.height / 2 - 20, 75, 75, I18n.format("Alt Manager", new Object[0])));
        this.buttonList.add(new MenuButton(4, (this.width / 2) + 65 + offset, this.height / 2 - 20, 75, 75, I18n.format("menu.options", new Object[0])));
		
        discord = new DiscordButton(10, 4, this.height - 55);
	}
	
	DiscordButton discord;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	float moveX = (float)mouseX / (float)this.width;
    	float moveY = (float)mouseY / (float)this.height;
    	GL11.glPushMatrix();
    	GlStateManager.color(0.5f, 0.5f, 1f);
		mc.getTextureManager().bindTexture(new ResourceLocation("sight/background.png"));
		this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
		GlStateManager.popMatrix();
		discord.drawButton(mc, mouseX, mouseY);
		int y = 5;
		for (String str : Changelog.getChangelog()) {
			this.fontRendererObj.drawStringWithShadow(str, 5, y, -1);
			y += this.fontRendererObj.FONT_HEIGHT;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		mc.getTextureManager().bindTexture(new ResourceLocation("sight/sight.png"));
		GlStateManager.color(0.05f, 0.05f, 0.05f);
		this.drawModalRectWithCustomSizedTexture((this.width / 2) - ((1280 / 3) / 2) + 2, -68 + 2, 0, 0, 1280 / 3, 720 / 3, 1280 / 3, 720 / 3);
		GlStateManager.color(1f, 1f, 1f);
		this.drawModalRectWithCustomSizedTexture((this.width / 2) - ((1280 / 3) / 2), -68, 0, 0, 1280 / 3, 720 / 3, 1280 / 3, 720 / 3);
		GlStateManager.popMatrix();

    	GL11.glPushMatrix();
    	GlStateManager.translate(moveX * 3, 1, 1);
		super.drawScreen(mouseX, mouseY, partialTicks);
		GL11.glPopMatrix();
	}
	
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if (mouseButton == 0) {
        	discord.mouseClicked(mouseX, mouseY);
    	}
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    }
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 1) {
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}
		if (button.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}
		if (button.id == 3) {
			this.mc.displayGuiScreen(new GuiAltManager());
		}
		if (button.id == 4) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}
    }
	
	@Override
	public void onGuiClosed() {

	}
}
