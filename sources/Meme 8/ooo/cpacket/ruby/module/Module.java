package ooo.cpacket.ruby.module;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import ooo.cpacket.lemongui.settings.Setting;
import ooo.cpacket.ruby.Ruby;
import ooo.cpacket.ruby.api.event.EventManager;
import ooo.cpacket.ruby.module.mode.Mode;

public abstract class Module {

	private String name;

	protected boolean enabled = false;

	private Category category;

	private int key;

	protected static Minecraft mc = Minecraft.getMinecraft();

	public Module(String name, int key, Category category) {
		this.name = name;
		this.category = category;
		this.key = key;
	}

	public String getName() {
		return this.name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Category getCategory() {
		return category;
	}

	public int getKey() {
		return key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public enum Category {
		ATTACK, SELF, MOVE, MISC, VISUAL, MEMES, HIDDEN, WORLD;
	}

	public abstract void onEnable();

	public abstract void onDisable();

	public void toggle() {
		this.enabled = !this.enabled;
		if (this.enabled) {
			EventManager.register(this);
			this.onEnable();
		}
		if (!this.enabled) {
			EventManager.unregister(this);
			this.onDisable();
		}
	}

	public String getSuffixedName() {
		if (Ruby.getRuby.setmgr.getSettingByName("Mode", this) != null) {
			return this.name + " \u00A7f[\u00A77" + Ruby.getRuby.setmgr.getSettingByName("Mode", this).getValString()
					+ "\u00A7f]";
		} else {
			return this.getName();
		}
	}

	public void addBool(String string, boolean b) {
		Ruby.getRuby.setmgr.rSetting(new Setting(string, this, b));
	}

	public void addModes(String... XDD) {
		ArrayList<String> XD = new ArrayList();
		for (String XDDD : XDD) {
			XD.add(XDDD);
		}
		Ruby.getRuby.setmgr.rSetting(new Setting("Mode", this, XDD[0], XD));
	}

	protected List<Mode> modes = new ArrayList();

	public Mode currentMode() {
		for (Mode m : this.modes) {
			if (this.isMode(m.getName()))
				return m;
		}
		return null;
	}
	
	public Mode getMode(String rolf) {
		for (Mode m : this.modes) {
			if (m.getName().equalsIgnoreCase(rolf))
				return m;
		}
		return null;
	}

	public void addModes(List<Mode> list) {
		this.modes = list;

		ArrayList<String> modes = new ArrayList();

		for (Mode m : list) {
			modes.add(m.getName());
		}

		Ruby.getRuby.setmgr.rSetting(new Setting("Mode", this, list.get(0).getName(), modes));
	}

	public void addNumberOption(String string, double d, double e, double f) {
		Ruby.getRuby.setmgr.rSetting(new Setting(string, this, d, e, f, false));
	}

	public void addNumberOption(String string, double d, double e, double f, boolean xd) {
		Ruby.getRuby.setmgr.rSetting(new Setting(string, this, d, e, f, xd));
	}

	protected boolean getBool(String string) {
		return Ruby.getRuby.setmgr.getSettingByName(string, this).getValBoolean();
	}

	protected double getDouble(String string) {
		return Ruby.getRuby.setmgr.getSettingByName(string, this).getValDouble();
	}

	protected String getString(String string) {
		return Ruby.getRuby.setmgr.getSettingByName(string, this).getValString();
	}

	protected boolean isMode(String xd) {
		return this.getString("Mode").equalsIgnoreCase(xd);
	}

}
