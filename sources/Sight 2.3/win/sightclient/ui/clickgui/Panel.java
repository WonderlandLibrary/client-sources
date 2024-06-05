package win.sightclient.ui.clickgui;

import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import win.sightclient.Sight;
import win.sightclient.fonts.TTFFontRenderer;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.utils.TimerUtils;
import win.sightclient.utils.Vec2;

public class Panel {

	private int x;
	private int y;
	private int width = 100;
	private int height = 20;
	private Category category;
	private boolean open = false;
	private boolean dragging = false;
	
	private Vec2 offset = new Vec2(0, 0);
	
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public Panel(int x, int y, Category cat) {
		this.x = x;
		this.y = y;
		this.category = cat;
		
		for (Module m : Sight.instance.mm.getModInCategory(this.category)) {
			this.buttons.add(new Button(m));
		}
	}
	
	public void reload() {
		this.buttons.clear();
		for (Module m : Sight.instance.mm.getModInCategory(this.category)) {
			this.buttons.add(new Button(m));
		}
	}
	
	private int upTo = 0;
	private TimerUtils timer = new TimerUtils();
	
	public void drawScreen(int mouseX, int mouseY) {
		if (dragging) {
			this.x = mouseX - (width / 2) + (int)offset.getX();
			this.y = mouseY - (height / 2) + (int)offset.getY();
		}
		Gui.drawRect(x, y, x + width, y + height, ClickGui.getSecondaryColor(false).getRGB());
		TTFFontRenderer font = ClickGui.getFont();
		font.drawString(this.category.getName(), x + 5, y + (height / 2) - (font.getHeight(this.category.name()) /2), ClickGui.getPrimaryColor().getRGB());
		
		if (this.open || upTo > 0) {
			int offset = height;
			if (timer.hasReached(15)) {
				if (this.open) {
					upTo++;
					if (upTo > this.buttons.size()) {
						upTo = this.buttons.size();
					}
				} else {
					upTo--;
					if (upTo < 0) {
						upTo = 0;
						for (Button b : this.buttons) {
							b.upTo = 0;
						}
					}
				}
				timer.reset();
			}
			for (int i = 0; i < this.buttons.size(); i++) {
				if (i < upTo) {
					Button b = this.buttons.get(i);
					if (b.getMod().showInClickGUI) {
						offset += b.drawScreen(mouseX, mouseY, this.x, this.y + offset, this.width);
					}
				}
			}
			//Gui.drawRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height + 1, ClickGui.getPrimaryColor().getRGB());
		}
	}
	
	public void keyTyped(char typedChar, int keyCode) {
		for (Button b : this.buttons) {
			b.keyTyped(typedChar, keyCode);
		}
	}
	
	public void mouseClicked(int x, int y, int button) {
		if (this.isHovered(x, y)) {
			if (button == 1) {
				this.open = !this.open;
			} else if (button == 0 && !ClickGui.dragging) {
				this.dragging = true;
				ClickGui.dragging = true;
				int xPos = this.x + (width / 2);
				int yPos = this.y + (height / 2);
				this.offset = new Vec2(xPos - x, yPos - y);
				
			}
		} else if (this.open) {
			for (int i = 0; i < this.buttons.size(); i++) {
				this.buttons.get(i).mouseClicked(x, y, button);
			}
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if (this.dragging && state == 0) {
			this.dragging = false;
			ClickGui.dragging = false;
		}
		
		for (Button b : this.buttons) {
			b.mouseReleased(mouseX, mouseY, state);
		}
	}
	
	private boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
