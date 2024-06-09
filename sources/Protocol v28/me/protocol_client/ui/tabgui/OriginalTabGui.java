package me.protocol_client.ui.tabgui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import org.lwjgl.opengl.GL11;

public class OriginalTabGui {
	private ArrayList<String>	category	= new ArrayList();
	private ArrayList<String>	modez		= new ArrayList();
	private FontRenderer		fr			= Minecraft.getMinecraft().fontRendererObj;
	private int					selectedTab;
	private int					selectedMod;
	private int					selectedOption;
	public int					start		= 17;
	public int					start1		= start + 14;
	public int					otherstart	= 17;
	public int					otherstart1	= otherstart + 14;
	public int					end;
	public static int			screen		= 0;
	public static long			lastUpdateTime;

	public OriginalTabGui() {
		Category[] arrayOfModCategory;
		int j = (arrayOfModCategory = Category.values()).length;
		for (int i = 0; i < j; i++) {
			Category mc = arrayOfModCategory[i];
			if (mc.name().equalsIgnoreCase("Other") || mc.name().equalsIgnoreCase("Theme"))
				continue;
			this.category.add(mc.toString().substring(0, 1) + mc.toString().substring(1, mc.toString().length()).toLowerCase());
		}
	}

	public void drawTabGui() {
		int top = 11;
		int startx = 1;
		int endX = 65;
		int otherstartx = endX + 2;
		int sliderColor = 0xff206080;
		int backgroundColor = 0xff373737;
		int toggledColor = 0xffaaaaaa;
		int normalColor = 0xff505050;

		start = selectedTab * 12 + top;
		start1 = start + 13;
		otherstart = selectedMod * 12 + top;
		otherstart1 = otherstart + 13;
		int count = 0;
		Wrapper.drawBorderRect(startx, top, endX, top + 1 + this.category.size() * 11 + 6, backgroundColor, backgroundColor, 1);
		Wrapper.drawBorderRect(startx, start, endX, start1, 0xcb, sliderColor, 1);
		drawTopString();
		SimpleDateFormat date = new SimpleDateFormat("h:mm a");
		Gui.drawString(Wrapper.fr(), "FPS: " + Wrapper.mc().func_175610_ah(), startx, 94, toggledColor);
		Gui.drawString(Wrapper.bf(), date.format(new Date()), startx, 85, toggledColor);

		int categoryCount = 0;
		for (String s : this.category) {
			if (s.equalsIgnoreCase("Other") || s.equalsIgnoreCase("ProtocolButtons"))
				continue;
			Gui.drawString(Wrapper.bf(), s, 4, (top + 3) + categoryCount * 12, toggledColor);
			categoryCount++;
		}
		if (screen == 1) {
			Wrapper.drawBorderRect(otherstartx, top, otherstartx + getLongestModWidth() + 1, getModsForCategory().size() * 12 + top + 1, backgroundColor, backgroundColor, 1);
			Wrapper.drawBorderRect(otherstartx, otherstart, otherstartx + getLongestModWidth() + 1, otherstart1, 0xcb, sliderColor, 1);
			int modCount = 0;
			for (Module mod : getModsForCategory()) {
				String name = mod.getName();
				Gui.drawString(Wrapper.bf(), name, otherstartx + 2, (top + 3) + modCount * 12, mod.isToggled() ? toggledColor : normalColor);
				modCount++;
			}
		}
	}

	public void drawTopString() {
		int sliderColor = 0xff007080;
		GL11.glPushMatrix();
		GL11.glScalef(1f, 1f, 1f);
		Gui.drawString(Wrapper.fr(), Protocol.name + " b" + Protocol.version, 1, 2, sliderColor);
		GL11.glPopMatrix();
	}

	public void down() {
		if (screen == 0) {
			if (this.selectedTab >= this.category.size() - 1) {
				this.selectedTab = -1;
			}
			this.selectedTab += 1;
		} else if (screen == 1) {
			if (this.selectedMod >= getModsForCategory().size() - 1) {
				this.selectedMod = -1;
			}
			this.selectedMod += 1;
		}
		if (screen == 2) {
		}
	}

	public void up() {
		if (screen == 0) {
			if (this.selectedTab <= 0) {
				this.selectedTab = this.category.size();
			}
			this.selectedTab -= 1;
		} else if (screen == 1) {
			if (this.selectedMod <= 0) {
				this.selectedMod = getModsForCategory().size();
			}
			this.selectedMod -= 1;
		}
		if (screen == 2) {
		}
	}

	public void left() {
		if (screen == 1) {
			screen = 0;
		}
		if (screen == 2) {
			this.selectedOption = 0;
			screen = 1;
		}
	}

	public void right() {
		if (screen == 1) {
			enter();
		} else {
			if (screen == 0) {
				screen = 1;
				this.selectedMod = 0;
			}
			if (screen == 2) {
			}
		}
	}

	public void enter() {
		if (screen == 1) {
			((Module) getModsForCategory().get(this.selectedMod)).toggle();
		}
		if (screen == 2) {
		}
	}

	public Module getSelectedMod() {
		return (Module) this.getModsForCategory().get(this.selectedMod);
	}

	private ArrayList<Module> getModsForCategory() {
		ArrayList<Module> mods = new ArrayList();
		for (Module mod : Protocol.getModules()) {
			if (mod.getCategory() == Category.valueOf(((String) this.category.get(this.selectedTab)).toUpperCase())) {
				mods.add(mod);
			}
		}
		return mods;
	}

	private int getLongestModWidth() {
		int longest = 0;
		for (Module mod : getModsForCategory()) {
			if (this.fr.getStringWidth(mod.getName()) > longest) {
				longest = this.fr.getStringWidth(" " + mod.getName());
			}
		}
		return longest;
	}
}
