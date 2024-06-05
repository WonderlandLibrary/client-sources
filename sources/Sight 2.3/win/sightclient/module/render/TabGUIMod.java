package win.sightclient.module.render;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventKey;
import win.sightclient.event.events.render.EventRender2D;
import win.sightclient.fonts.TTFFontRenderer;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.ui.clickgui.ClickGui;

public class TabGUIMod extends Module {

	private int selectedTab;
	private int selectedModule;
	private boolean opened;
	private ArrayList<Module> categoryMods = new ArrayList<Module>();
	
	public TabGUIMod() {
		super("TabGUI", Category.RENDER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventRender2D) {
			final int yOffset = 42;
			final int xOffset = 6;
			final int categoryWidth = 50;
			final float categoryHeight = this.getFont().getHeight("ABCDEFGHIJKLMNOPQRSTUVWXYZ") + 1F;
			final int moduleWidth = 60;
			final float moduleHeight = categoryHeight;
			if (this.selectedModule < 0) {
				this.selectedModule = this.categoryMods.size() - 1;
			} else if (this.selectedModule > this.categoryMods.size() - 1) {
				this.selectedModule = 0;
			}
			int count = 1;
			for (Category c : Category.values()) {
				Gui.drawRect(xOffset, yOffset + (categoryHeight * (count - 1)), xOffset + categoryWidth, yOffset + (categoryHeight * count), new Color(0, 0, 0, count - 1 == this.selectedTab ? 200 : 125).getRGB());
				GL11.glColor3f(1F, 1F, 1F);
				this.getFont().drawString(c.getName(), xOffset + 2, yOffset + (categoryHeight * (count - 1)) + 1, count - 1 == this.selectedTab && this.opened ? ClickGui.getPrimaryColor().getRGB() : new Color(255, 255, 255, 255).getRGB());
				count++;
			}
			
			if (this.opened) {
				count = 1;
				for (Module m : this.categoryMods) {
					Gui.drawRect(xOffset + categoryWidth, yOffset + (moduleHeight * (count - 1)), xOffset + categoryWidth + moduleWidth, yOffset + (moduleHeight * count) + (count == this.categoryMods.size() ? 1 : 0), new Color(0, 0, 0, count - 1 == this.selectedModule ? 200 : 125).getRGB());
					GL11.glColor3f(1F, 1F, 1F);
					this.getFont().drawString(m.getName(), xOffset + categoryWidth + 2, yOffset + (moduleHeight * (count - 1)) + 1, this.categoryMods.get(count - 1).isToggled() ? ClickGui.getPrimaryColor().getRGB() : new Color(255, 255, 255, 255).getRGB());
					count++;
				}
			}
		} else if (e instanceof EventKey) {
			int key = ((EventKey)e).getKey();
			
			if (!opened) {
				if (key == Keyboard.KEY_UP) {
					this.selectedTab--;
					Keyboard.enableRepeatEvents(true);
				} else if (key == Keyboard.KEY_DOWN) {
					this.selectedTab++;
					Keyboard.enableRepeatEvents(true);
				} else if (key == Keyboard.KEY_RIGHT) {
					this.opened = true;
					Keyboard.enableRepeatEvents(true);
					this.open();
				} else {
					Keyboard.enableRepeatEvents(false);
				}
				if (this.selectedTab < 0) {
					this.selectedTab = Category.values().length - 1;
				} else if (this.selectedTab > Category.values().length - 1) {
					this.selectedTab = 0;
				}
			} else {
				if (key == Keyboard.KEY_UP) {
					this.selectedModule--;
				} else if (key == Keyboard.KEY_DOWN) {
					Keyboard.enableRepeatEvents(true);
					this.selectedModule++;
				} else if (key == Keyboard.KEY_LEFT) {
					Keyboard.enableRepeatEvents(true);
					this.opened = false;
				} else if (key == Keyboard.KEY_RETURN) {
					this.categoryMods.get(this.selectedModule).toggle();
					Keyboard.enableRepeatEvents(true);
				} else {
					Keyboard.enableRepeatEvents(false);
				}
				if (this.selectedModule < 0) {
					this.selectedModule = this.categoryMods.size() - 1;
				} else if (this.selectedModule > this.categoryMods.size() - 1) {
					this.selectedModule = 0;
				}
			}
		}
	}
	
	public void open() {
		if (!this.opened) {
			return;
		}
		categoryMods = Sight.instance.mm.getModInCategory(Category.values()[this.selectedTab]);
		categoryMods.removeIf(m -> (!m.showInClickGUI));
	}
	
	private TTFFontRenderer font;
	
	private TTFFontRenderer getFont() {
		if (font == null) {
			font = Sight.instance.fm.getFont("SFUI 18");
		}
		return font;
	}
}
