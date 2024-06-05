package Ascii4UwUWareClient.UI.ClickUi;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.UI.Font.CFontRenderer;
import Ascii4UwUWareClient.UI.Font.FontLoaders;

public class KeyBindButton extends ValueButton {
	public Module cheat;
	public double opacity = 0.0;
	public boolean bind;

	public KeyBindButton(Module cheat, int x, int y) {
		super(null, x, y);
		this.custom = true;
		this.bind = false;
		this.cheat = cheat;
	}

	@Override
	public void render(int mouseX, int mouseY) {
		CFontRenderer font = FontLoaders.GoogleSans18;
		CFontRenderer mfont = FontLoaders.GoogleSans16;
		CFontRenderer bigfont = FontLoaders.GoogleSans28;
		Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
		Gui.drawRect(this.x - 10, this.y - 4, this.x + 80, this.y + 11, new Color(0, 0, 0, 130).getRGB());
		mfont.drawString("Bind", this.x - 5, this.y + 2, new Color(108, 108, 108).getRGB());
		mfont.drawString(String.valueOf(this.bind ? "" : "") + Keyboard.getKeyName((int) this.cheat.getKey()),
				this.x + 76 - mfont.getStringWidth(Keyboard.getKeyName((int) this.cheat.getKey())), this.y + 2,
				new Color(108, 108, 108).getRGB());
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
