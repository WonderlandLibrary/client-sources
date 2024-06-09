package me.swezedcode.client.gui.clickGui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.file.files.Keybinds;
import me.swezedcode.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

public class KeyBinder extends Component {
	
	public Module mod;
	
	public boolean binding;
	
	public static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
	
	public KeyBinder(Module mod, Component parent) {
		this.parent = parent;
		this.mod = mod;
		this.width = parent.width - 4;
		this.height = 12;
		this.renderWidth = width;
		this.renderHeight = height;
	}
	
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks, int parX, int parY, GuiScreen screen) {
		GL11.glPushMatrix();
		GL11.glTranslatef(this.parent.x, this.parent.y, 0);
		this.absx = parX + this.x;
		this.absy = parY + this.y;
		screen.drawRect(x - 1, y, x + width + 1, y + height, 0x99000000);
		String kb = binding ? "..." : Keyboard.getKeyName(mod.getKeycode());
		ClientOverlay.fontRenderer.drawStringWithShadow("Key", x + 2, y + 2, 0xFFFFFFFF);
		ClientOverlay.fontRenderer.drawStringWithShadow(kb, x + width - 6 - fr.getStringWidth(kb), y + 2, 0xFFFFFFFF);
		GL11.glPopMatrix();
	}

	

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		
		if (!this.isVisible) {
			return;
		}

		if (mouseX >= this.absx && mouseX <= this.absx + this.width) {
			if (mouseY >= this.absy && mouseY <= this.absy + this.height) {
				if (mouseButton == 0) {
					this.binding = true;
				
				}
			}
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		
	}


	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if(this.binding) {
			this.binding = false;
			if(keyCode == Keyboard.KEY_DELETE) {
				mod.setKeycode(0);
			}else{
				FileManager.saveKeys();
				mod.setKeycode(keyCode);
			}
		}
	}


	@Override
	public void keyTypedNum(int typedChar, int keyCode) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}