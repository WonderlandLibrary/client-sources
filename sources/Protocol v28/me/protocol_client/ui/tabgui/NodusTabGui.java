package me.protocol_client.ui.tabgui;

import java.util.ArrayList;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.utils.RenderUtils2D;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class NodusTabGui {
	private ArrayList<String> category = new ArrayList();
	private FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
	private int selectedTab;
	private int selectedMod;
	private boolean mainMenu = true;
	public int linesColor = 0x50999999;
	public String topString = Protocol.primColor + Protocol.name + Protocol.secColor + " b" + Protocol.version + " (X: " + (int) Wrapper.getPlayer().posX + " Z: " + (int) Wrapper.getPlayer().posZ + ")";

	public NodusTabGui() {
		Category[] arrayOfModCategory;
		int j = (arrayOfModCategory = Category.values()).length;
		for (int i = 0; i < j; i++) {
			Category mc = arrayOfModCategory[i];
			if (mc.name().equalsIgnoreCase("Other") || mc.name().equalsIgnoreCase("ProtocolButtons") || mc.name().equalsIgnoreCase("Theme"))
				continue;
			this.category.add(mc.toString().substring(0, 1) + mc.toString().substring(1, mc.toString().length()).toLowerCase());
		}
	}

	public void drawTabGui() {
		linesColor = 553648127;
		int x = (int) Wrapper.getPlayer().posX;
		int z = (int) Wrapper.getPlayer().posZ;
		topString = Protocol.primColor + Protocol.name + Protocol.secColor + " b" + Protocol.version + " (X: " + (int) Wrapper.getPlayer().posX + " Z: " + (int) Wrapper.getPlayer().posZ + ")";
		Wrapper.drawBorderRect(3, 3, Wrapper.fr().getStringWidth(topString) + 10, 18, linesColor, 0xcc151515, 2);
		Gui.drawString(Wrapper.fr(), topString, 7, 7, 0xFF00FF00);
		Gui.drawRect(3, 18, 5, 18 + (67), linesColor);
		Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 8, 18, Wrapper.fr().getStringWidth(topString) + 10, 18 + (67), linesColor);
		int categoryCount = 0;
		for (String s : this.category) {
			if (s.equalsIgnoreCase("Other") || s.equalsIgnoreCase("ProtocolButtons") || s.equalsIgnoreCase("Theme"))
				continue;
			Wrapper.drawBorderRect(5, 18 + (categoryCount * 11), Wrapper.fr().getStringWidth(topString) + 8, 28 + (categoryCount * 11), -1728053248, -1728053248, 0.7f);
			Gui.drawRect(5, 28 + ((categoryCount) * 11), Wrapper.fr().getStringWidth(topString) + 8, 30 + ((categoryCount) * 11), linesColor);
			
			if (categoryCount == this.selectedTab) {
				Gui.drawString(Wrapper.fr(), Protocol.primColor + "> " + s, 6, 19 + categoryCount * 11, 0xFF00FF00);
			} else {
				Gui.drawString(Wrapper.fr(), Protocol.secColor + s, 6, 19+ categoryCount * 11, -1);
			}
			if (mainMenu) {
				if (categoryCount == this.selectedTab) {
					Gui.drawString(Wrapper.fr(), Protocol.primColor + ">>", Wrapper.fr().getStringWidth(topString) - 3, 19 + categoryCount * 11, 0xFF00FF00);
				} else {
					Gui.drawString(Wrapper.fr(), Protocol.secColor + ">>", Wrapper.fr().getStringWidth(topString) - 3, 19 + categoryCount * 11, -1);
				}
			} else {
				if (categoryCount == this.selectedTab) {
					Gui.drawString(Wrapper.fr(), Protocol.primColor + "<<", Wrapper.fr().getStringWidth(topString) - 3, 19 + categoryCount * 11, 0xFF00FF00);
				} else {
					Gui.drawString(Wrapper.fr(), Protocol.secColor + ">>", Wrapper.fr().getStringWidth(topString) - 3, 19 + categoryCount * 11, -1);
				}
			}
			categoryCount++;
		}
		if (!this.mainMenu) {
			int modCount = 0;
			for (Module mod : getModsForCategory()) {
				if (selectedTab == 0) {
					int top = 18;
					int bottom = 28;
					Wrapper.drawBorderRect(Wrapper.fr().getStringWidth(topString) + 16, top + (modCount * 11), Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), bottom + (modCount * 11), -1728053248, -1728053248, 1);
					Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), (top - 1) + (modCount + 1) * 11, linesColor);
				}
				if (selectedTab == 1) {
					int top = 29;
					int bottom = 39;
					Wrapper.drawBorderRect(Wrapper.fr().getStringWidth(topString) + 16, top + (modCount * 11), Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), bottom + (modCount * 11), -1728053248, -1728053248, 1);
					Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), (top - 1) + (modCount) * 11, linesColor);
				}
				if (selectedTab == 2) {
					int top = 40;
					int bottom = 50;
					Wrapper.drawBorderRect(Wrapper.fr().getStringWidth(topString) + 16, top + (modCount * 11), Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), bottom + (modCount * 11), -1728053248, -1728053248, 1);
					Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), (top - 1) + (modCount) * 11, linesColor);
				}
				if (selectedTab == 3) {
					int top = 51;
					int bottom = 61;
					Wrapper.drawBorderRect(Wrapper.fr().getStringWidth(topString) + 16, top + (modCount * 11), Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), bottom + (modCount * 11), -1728053248, -1728053248, 1);
					Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), (top - 1) + (modCount) * 11, linesColor);
				}
				if (selectedTab == 4) {
					int top = 62;
					int bottom = 72;
					Wrapper.drawBorderRect(Wrapper.fr().getStringWidth(topString) + 16, top + (modCount * 11), Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), bottom + (modCount * 11), -1728053248, -1728053248, 1);
					Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), (top - 1) + (modCount) * 11, linesColor);
				}
				if (selectedTab == 5) {
					int top = 73;
					int bottom = 83;
					Wrapper.drawBorderRect(Wrapper.fr().getStringWidth(topString) + 16, top + (modCount * 11), Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), bottom + (modCount * 11), -1728053248, -1728053248, 1);
					Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), (top - 1) + (modCount) * 11, linesColor);
				}
				if (selectedTab == 6) {
					int top = 84;
					int bottom = 94;
					Wrapper.drawBorderRect(Wrapper.fr().getStringWidth(topString) + 16, top + (modCount * 11), Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), bottom + (modCount * 11), -1728053248, -1728053248, 1);
					Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), (top - 1) + (modCount) * 11, linesColor);
				}
				String color;
				if (!mod.isToggled()) {
					color = Protocol.secColor;
				} else
					color = Protocol.primColor;
				String name = modCount == this.selectedMod ? Protocol.primColor + "> " + color + mod.getName() + "§f" : Protocol.secColor + color + mod.getName();
				if (selectedTab == 0) {
					Gui.drawString(Wrapper.fr(), name, Wrapper.fr().getStringWidth(topString) + 18, 19 + modCount * 11, 0xFF00FF00);
				}
				if (selectedTab == 1) {
					Gui.drawString(Wrapper.fr(), name, Wrapper.fr().getStringWidth(topString) + 18, 30 + modCount * 11, 0xFF00FF00);
				}
				if (selectedTab == 2) {
					Gui.drawString(Wrapper.fr(), name, Wrapper.fr().getStringWidth(topString) + 18, 41 + modCount * 11, 0xFF00FF00);
				}
				if (selectedTab == 3) {
					Gui.drawString(Wrapper.fr(), name, Wrapper.fr().getStringWidth(topString) + 18, 52 + modCount * 11, 0xFF00FF00);
				}
				if (selectedTab == 4) {
					Gui.drawString(Wrapper.fr(), name, Wrapper.fr().getStringWidth(topString) + 18, 63 + modCount * 11, 0xFF00FF00);
				}
				if (selectedTab == 5) {
					Gui.drawString(Wrapper.fr(), name, Wrapper.fr().getStringWidth(topString) + 18, 74 + modCount * 11, 0xFF00FF00);
				}
				if (selectedTab == 6) {
					Gui.drawString(Wrapper.fr(), name, Wrapper.fr().getStringWidth(topString) + 18, 85 + modCount * 11, 0xFF00FF00);
				}
				modCount++;
			}
			if(selectedTab == 0){
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 16, 16, Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), 18, linesColor);
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 14, 16, Wrapper.fr().getStringWidth(topString) + 16, 19 + modCount * 11, linesColor);
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), 16, Wrapper.fr().getStringWidth(topString) + 14 + getLongestModWidth(), 19 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 18 + modCount * 11, linesColor);
			}
			if(selectedTab == 1){
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 14, 28, Wrapper.fr().getStringWidth(topString) + 16, 30 + modCount * 11, linesColor);
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), 28, Wrapper.fr().getStringWidth(topString) + 14 + getLongestModWidth(), 30 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 29 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 28 + modCount * 11, linesColor);
			}
			if(selectedTab == 2){
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 14, 39, Wrapper.fr().getStringWidth(topString) + 16, 41 + modCount * 11, linesColor);
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), 39, Wrapper.fr().getStringWidth(topString) + 14 + getLongestModWidth(), 41 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 39 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 40 + modCount * 11, linesColor);
			}
			if(selectedTab == 3){
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 14, 50, Wrapper.fr().getStringWidth(topString) + 16, 52 + modCount * 11, linesColor);
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), 50, Wrapper.fr().getStringWidth(topString) + 14 + getLongestModWidth(), 52 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 51 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 50 + modCount * 11, linesColor);
			}
			if(selectedTab == 4){
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 14, 61, Wrapper.fr().getStringWidth(topString) + 16, 63 + modCount * 11, linesColor);
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), 61, Wrapper.fr().getStringWidth(topString) + 14 + getLongestModWidth(), 63 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 61 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 62 + modCount * 11, linesColor);
			}
			if(selectedTab == 5){
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 14, 72, Wrapper.fr().getStringWidth(topString) + 16, 74 + modCount * 11, linesColor);
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), 72, Wrapper.fr().getStringWidth(topString) + 14 + getLongestModWidth(), 74 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 73 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 72 + modCount * 11, linesColor);
			}
			if(selectedTab == 6){
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 14, 83, Wrapper.fr().getStringWidth(topString) + 16, 85 + modCount * 11, linesColor);
				Gui.drawRect(Wrapper.fr().getStringWidth(topString) + 12 + getLongestModWidth(), 83, Wrapper.fr().getStringWidth(topString) + 14 + getLongestModWidth(), 85 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 83 + modCount * 11, linesColor);
				Gui.drawHorizontalLine(Wrapper.fr().getStringWidth(topString) + 16, Wrapper.fr().getStringWidth(topString) + 11 + getLongestModWidth(), 84 + modCount * 11, linesColor);
			}
		}
	}

	public void down() {
		if (this.mainMenu) {
			if (this.selectedTab >= this.category.size() - 1) {
				this.selectedTab = -1;
			}
			this.selectedTab += 1;
		} else {
			if (this.selectedMod >= getModsForCategory().size() - 1) {
				this.selectedMod = -1;
			}
			this.selectedMod += 1;
		}
	}

	public void up() {
		if (this.mainMenu) {
			if (this.selectedTab <= 0) {
				this.selectedTab = this.category.size();
			}
			this.selectedTab -= 1;
		} else {
			if (this.selectedMod <= 0) {
				this.selectedMod = getModsForCategory().size();
			}
			this.selectedMod -= 1;
		}
	}

	public void left() {
		this.mainMenu = true;
	}

	public void right() {
		if (!this.mainMenu) {
			enter();
			((Module) getModsForCategory().get(this.selectedMod)).onToggle();
		} else {
			this.selectedMod = 0;
			this.mainMenu = false;
		}
	}

	public void enter() {
		if (!this.mainMenu) {
			((Module) getModsForCategory().get(this.selectedMod)).toggle();
		}
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
			if (7 + this.fr.getStringWidth(mod.getName()) >= longest) {
				longest = 7 + this.fr.getStringWidth(((Module) getModsForCategory().get(this.selectedMod)) == mod ? "> " + mod.getName() : mod.getName());
			}
		}
		return longest;
	}
}
