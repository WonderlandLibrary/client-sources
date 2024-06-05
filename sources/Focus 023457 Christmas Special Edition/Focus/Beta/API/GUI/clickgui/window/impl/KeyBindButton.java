package Focus.Beta.API.GUI.clickgui.window.impl;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.ChestRenderer;

import org.lwjgl.input.Keyboard;

import Focus.Beta.API.GUI.clickgui.ClickUi;
import Focus.Beta.IMPL.font.CFontRenderer;
import Focus.Beta.IMPL.font.FontLoaders;
public class KeyBindButton extends ValueButton {
	public Focus.Beta.IMPL.Module.Module cheat;
	public double opacity = 0.0;
	public boolean bind;

	public KeyBindButton(Focus.Beta.IMPL.Module.Module cheat, int x, int y) {
		super(null, x, y);
		this.custom = true;
		this.bind = false;
		this.cheat = cheat;
	}

	@Override
	public void render(int mouseX, int mouseY) {
		Focus.Beta.IMPL.font.CFontRenderer font = FontLoaders.GoogleSans18;
		CFontRenderer mfont = FontLoaders.GoogleSans16;
		CFontRenderer bigfont = FontLoaders.GoogleSans28;
		Gui.drawRect(this.x - 12, this.y - 4, this.x + 82, this.y + 11, new Color(55, 55, 55).getRGB());
		mfont.drawString("Bind", this.x - 7, this.y + 2, -1);
		mfont.drawString(String.valueOf(this.bind ? "" : "") + Keyboard.getKeyName((int) this.cheat.getKey()),
				this.x + 76 - mfont.getStringWidth(Keyboard.getKeyName((int) this.cheat.getKey())), this.y + 2,
				-1);
	}

	@Override
	public void key(char typedChar, int keyCode) {
		if (this.bind) {
			this.cheat.setKey(keyCode);
			if (keyCode == 1) {
				this.cheat.setKey(0);
			}
			ClickUi.binding = false;
			this.bind = false;
		}
		super.key(typedChar, keyCode);
	}

	@Override
	public void click(int mouseX, int mouseY, int button) {
		if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6
				&& mouseY < this.y + FontLoaders.GoogleSans18.getStringHeight(this.cheat.getName()) + 5
				&& button == 0) {
			ClickUi.binding = this.bind = !this.bind;
		}
		super.click(mouseX, mouseY, button);
	}
}
