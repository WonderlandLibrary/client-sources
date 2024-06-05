package Focus.Beta.API.GUI.clickgui.window.impl;

import Focus.Beta.UTILS.render.RenderUtil2;
import Focus.Beta.UTILS.world.Timer;
import com.google.common.collect.Lists;

import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.font.CFontRenderer;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.IMPL.set.Value;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class Button {
	public Focus.Beta.IMPL.Module.Module cheat;
	CFontRenderer font = FontLoaders.GoogleSans18;
	public Focus.Beta.API.GUI.clickgui.window.Window parent;
	public int x;
	public int y;
	public int index;
	Timer timer = new Timer();
	public int remander;
	public double opacity = 0.0;
	public ArrayList<ValueButton> buttons = Lists.newArrayList();
	public boolean expand;

	public Button(Module cheat, int x, int y) {
		CFontRenderer font = FontLoaders.GoogleSans18;
		CFontRenderer mfont = FontLoaders.GoogleSans16;
		CFontRenderer bigfont = FontLoaders.GoogleSans28;
		this.cheat = cheat;
		this.x = x;
		this.y = y;
		int y2 = y + 14;
		for (Value v : cheat.getValues()) {
			this.buttons.add(new ValueButton(v, x + 5, y2));
			y2 += 15;
		}
		this.buttons.add(new KeyBindButton(cheat, x + 5, y2));
	}

	public void render(int mouseX, int mouseY) {
		CFontRenderer font = FontLoaders.GoogleSans18;
		CFontRenderer mfont = FontLoaders.GoogleSans16;
		CFontRenderer bigfont = FontLoaders.GoogleSans28;

		if (this.index != 0) {
			Button b2 = this.parent.buttons.get(this.index - 1);
			this.y = b2.y + 14 + (b2.expand ? 15 * b2.buttons.size() : 0);
		}
		int i = 0;
		while (i < this.buttons.size()) {
			this.buttons.get((int) i).y = this.y + 14 + 15 * i;
			this.buttons.get((int) i).x = this.x + 5;
			++i;
		}
		Gui.drawRect(this.x - 7, this.y - 5, this.x + 85 + 2, this.y + font.getStringHeight(this.cheat.getName()) + 2,
				new Color(40, 40, 40).getRGB());
		if (this.cheat.isEnabled()) {
			Gui.drawRect(this.x - 7, this.y - 5, this.x + 85 + 2,
					(float) (this.y + font.getStringHeight(this.cheat.getName()) + 2),
					new Color(51, 109, 245).getRGB());
			font.drawString(this.cheat.getName(), this.x, this.y, -1);
		} else {
			font.drawString(this.cheat.getName(), this.x - 3, this.y, -1);
		}
		if (this.expand) {
			font.drawString("^", this.x + 76, this.y + 1, -1);
		}else {
			font.drawString("v", this.x + 76, this.y + 1, -1);
		}
		if (this.expand) {
			this.buttons.forEach(b -> b.render(mouseX, mouseY));
		}
		if(mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6
				&& mouseY < this.y + font.getStringHeight(this.cheat.getName()) + 4){

			if(timer.hasElapsed(900, false) && cheat.getDescription() != null && !(cheat.getDescription() == "No")){
				RenderUtil2.drawRoundedRect(mouseX, (float) (mouseY - 11.5), mouseX + 3 + FontLoaders.arial16.getStringWidth(cheat.getDescription()), mouseY + 1, 0, new Color(45, 45, 45).getRGB());
				FontLoaders.arial16.drawStringWithShadow(cheat.getDescription(),mouseX + 1, (float)(mouseY - 7.5), -1);
			}else if(cheat.getName() == "No" || cheat.getDescription() == null || cheat.getDescription() == " "){
				RenderUtil2.drawRoundedRect(mouseX, (float) (mouseY - 11.5), mouseX + 3 + FontLoaders.arial16.getStringWidth("<Theres no description for this module>"), mouseY + 1, 0, new Color(45, 45, 45).getRGB());
				FontLoaders.arial16.drawStringWithShadow("<Theres no description for this module>",mouseX + 1, (float)(mouseY - 7.5), -1);
			}
		}else{
			timer.reset();
		}
	}

	public void key(char typedChar, int keyCode) {
		this.buttons.forEach(b -> b.key(typedChar, keyCode));
	}

	public void click(int mouseX, int mouseY, int button) {
		if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6
				&& mouseY < this.y + font.getStringHeight(this.cheat.getName()) + 4) {
			if (button == 0) {
				this.cheat.setEnabled(!this.cheat.isEnabled());
			}
			if (button == 1 && !this.buttons.isEmpty()) {
				boolean bl = this.expand = !this.expand;
			}
			if(timer.hasElapsed(1500, false)){
				System.out.println("e");
			}
		}else {
			timer.reset();
		}
		if (this.expand) {
			this.buttons.forEach(b -> b.click(mouseX, mouseY, button));
		}
	}

	public void setParent(Focus.Beta.API.GUI.clickgui.window.Window parent) {
		this.parent = parent;
		int i = 0;
		while (i < this.parent.buttons.size()) {
			if (this.parent.buttons.get(i) == this) {
				this.index = i;
				this.remander = this.parent.buttons.size() - i;
				break;
			}
			++i;
		}
	}
}
