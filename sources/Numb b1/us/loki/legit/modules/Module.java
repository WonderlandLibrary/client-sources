package us.loki.legit.modules;

import com.darkmagician6.eventapi.EventManager;

import net.minecraft.client.Minecraft;

public class Module {

	private String name;
	private String displayname;
	public Category category;

	private boolean toggled;
	public boolean visible;
	boolean enabled;

	private int keybind;
	private int keyBind;
	public static Boolean colormode = false;
	public static Minecraft mc = Minecraft.getMinecraft();
	protected long lastUpdate;

	public Module(String name, String displayname, int keybind, Category category) {
		this.name = name;
		this.displayname = displayname;
		this.category = category;
		this.keybind = keybind;
		this.visible = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getKeybind() {
		return keybind;
	}

	public void setKeybind(int keybind) {
		this.keybind = keybind;
	}

	public boolean isEnabled() {
		return toggled;
	}

	public void onToggled() {
	}

	public void onEnabled() {
	}

	public void onDisabled() {
	}

	public void toggle() {
		if (toggled) {
			toggled = false;
			onDisable();
		} else {
			toggled = true;
			onEnable();
		}
	}

	public void onEnable() {
	}

	public void onDisable() {
	}
	public void onRender() {
	}

	public boolean isCategory(Category category) {
		return this.category == category;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void onUpdate() {
		this.lastUpdate = System.currentTimeMillis();

	}

	public void onFrameRender() {
	}

}
