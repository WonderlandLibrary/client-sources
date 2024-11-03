package net.silentclient.client.mods;

import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.utils.ColorUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Setting implements Comparable<Setting> {
	private final String name;
	private final Mod parent;
	private final String mode;

	private String sval;
	public String defaultsval;
	private List<String> options;

	private boolean bval;
	public boolean defaultbval;

	private double dval;
	public double defaultdval;
	private double min;
	private double max;
	private boolean onlyint = false;

	// For keybind setting
	private int kval;
	public int defaultkval;

	// For color setting
	private Color cval;
	public Color defaultcval;

	// for cell setting
	private boolean[][] cells;
	private int key;

	// Settings category
	private String category = "";


	private int opacity = 255;
	public int defaultopacity = 255;
	private boolean chroma = false;
	private boolean canChangeOpacity = false;
	
	private boolean onlyPremiumPlus = false;
	public SimpleAnimation switchAnimation = new SimpleAnimation(0);

	public Setting(String name, Mod parent, String sval, ArrayList<String> options) {
		this.name = name;
		this.parent = parent;
		this.sval = sval;
		this.options = options;
		this.mode = "Combo";
		this.defaultsval = sval;
	}

	public Setting(String name, Mod parent, boolean bval) {
		this.name = name;
		this.parent = parent;
		this.bval = bval;
		this.defaultbval = bval;
		this.mode = "Check";
	}

	public Setting(String name, Mod parent, int kval) {
		this.name = name;
		this.parent = parent;
		this.kval = kval;
		this.defaultkval = kval;
		this.mode = "Keybind";
	}

	public Setting(String name, Mod parent, Color cval) {
		this.name = name;
		this.parent = parent;
		this.cval = cval;
		this.defaultcval = cval;
		this.mode = "Color";
		this.opacity = 255;
		this.defaultopacity = 255;
	}

	public Setting(String name, Mod parent, boolean[][] cells) {
		this.mode = "CellGrid";
		this.name = name;
		this.parent = parent;
		this.cells = new boolean[][]{
				{false, false, false, false, false, true, false, false, false, false, false},
				{false, false, false, false, false, true, false, false, false, false, false},
				{false, false, false, false, false, true, false, false, false, false, false},
				{false, false, false, false, false, true, false, false, false, false, false},
				{false, false, false, false, false, true, false, false, false, false, false},
				{false, false, true, true, false, true, false, true, true, false, false},
				{false, false, false, false, false, true, false, false, false, false, false},
				{false, false, false, false, false, true, false, false, false, false, false},
				{false, false, false, false, false, true, false, false, false, false, false},
				{false, false, false, false, false, true, false, false, false, false, false},
				{false, false, false, false, false, true, false, false, false, false, false}
		};;
	}

	public Setting(String name, Mod parent, Color cval, int opacity) {
		this.name = name;
		this.parent = parent;
		this.cval = cval;
		this.mode = "Color";
		this.opacity = opacity;
		this.defaultopacity = opacity;
		this.defaultcval = cval;
		this.canChangeOpacity = true;
	}

	public boolean isCanChangeOpacity() {
		return canChangeOpacity;
	}

	public Setting(String name, Mod parent, double dval, double min, double max, boolean onlyint) {
		this.name = name;
		this.parent = parent;
		this.dval = dval;
		this.defaultdval = dval;
		this.min = min;
		this.max = max;
		this.onlyint = onlyint;
		this.mode = "Slider";
	}

	public Setting(String name, Mod parent, String sval) {
		this.name = name;
		this.parent = parent;
		this.sval = sval;
		this.mode = "Input";
		this.defaultsval = sval;
	}
	
	public Setting(String name, Mod parent, String sval, boolean isOnlyPremium) {
		this.name = name;
		this.parent = parent;
		this.sval = sval;
		this.mode = "Input";
		this.defaultsval = sval;
		this.onlyPremiumPlus = isOnlyPremium;
	}

	public String getName() {
		return name;
	}

	public Mod getParentMod() {
		return parent;
	}

	public String getValString() {
		return this.sval;
	}

	public void setCells(boolean[][] cells) {
		this.cells = cells;
	}

	public void setValString(String in) {
		if(onlyPremiumPlus) {
			if(Client.getInstance().getAccount() != null && Client.getInstance().getAccount().isPremiumPlus()) {
				this.sval = in;
			} else {
				this.sval = defaultsval;
			}
			return;
		}
		this.sval = in;
	}

	public int getKeybind() {
		return kval;
	}

	public void setKeybind(int kval) {
		this.kval = kval;
	}

	public boolean isKeybind() {
		return this.mode.equalsIgnoreCase("Keybind");
	}

	public boolean isKeyDown() {
		if(!isKeybind() || kval == -1) {
			return false;
		}

		return Minecraft.getMinecraft().inGameHasFocus && Keyboard.isKeyDown(kval);
	}

	public boolean[][] getCells() {
		return cells;
	}

	public List<String> getOptions() {
		return this.options;
	}

	public boolean getValBoolean() {
		return this.bval;
	}

	public void setValBoolean(boolean in) {
		this.bval = in;
	}
	
	public void setValColor(Color in) {
		this.cval = in;
	}
	
	public Color getValColor() {
		return getValColor(false);
	}

	public Color getValColor(boolean ignoreChroma) {
		if(chroma && !ignoreChroma) {
			return new Color(ColorUtils.getChromaColor(0, 0, 1).getRed(), ColorUtils.getChromaColor(0, 0, 1).getGreen(), ColorUtils.getChromaColor(0, 0, 1).getBlue(), getOpacity());
		}
		return new Color(this.cval.getRed(), this.cval.getGreen(), this.cval.getBlue(), getOpacity());
	}

	public boolean isChroma() {
		return chroma;
	}

	public Color getClearColor() {
		return this.cval;
	}

	public int getOpacity() {
		return opacity;
	}

	public void setChroma(boolean chroma) {
		this.chroma = chroma;
	}

	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}

	public double getValDouble() {
		if (this.onlyint) {
			this.dval = (int) dval;
		}
		return this.dval;
	}

	public float getValFloat(){
		if(this.onlyint) {
			this.dval = (int)dval;
		}
		return (float) this.dval;
	}

	public int getValInt(){
		if(this.onlyint) {
			this.dval = (int)dval;
		}
		return (int) this.dval;
	}

	public void setValDouble(double in) {
		this.dval = in;
	}

	public double getMin() {
		return this.min;
	}

	public double getMax() {
		return this.max;
	}

	public boolean isCombo() {
		return this.mode.equalsIgnoreCase("Combo");
	}

	public boolean isCheck() {
		return this.mode.equalsIgnoreCase("Check");
	}

	public boolean isSlider() {
		return this.mode.equalsIgnoreCase("Slider");
	}
	public boolean isColor() {
		return this.mode.equalsIgnoreCase("Color");
	}
	public boolean isInput() {
		return this.mode.equalsIgnoreCase("Input");
	}

	public boolean isCellGrid() {
		return this.mode.equalsIgnoreCase("CellGrid");
	}

	public String getMode() {
        return mode;
    }

	@Override
	public int compareTo(Setting setting) {
		return this.getCategory().compareTo(setting.getCategory());
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
