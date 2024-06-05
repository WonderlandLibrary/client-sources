package me.rich.module;

import me.rich.Main;
import me.rich.event.EventManager;
import me.rich.font.Fonts;
import me.rich.helpers.render.Translate;
import me.rich.helpers.world.TimerHelper;
import net.minecraft.client.Minecraft;

public class Feature {
	protected static Minecraft mc = Minecraft.getMinecraft();
	public static TimerHelper timerHelper = new TimerHelper();
	public static Fonts fontRenderer = new Fonts();
	private final Translate translate = new Translate(0.0F, 0.0F);
	protected String name;
	private String moduleName;
	private String suffix;
	private int key;
	private Category category;
	private boolean toggled;

	public Feature(String name, int key, Category category) {
		this.name = name;
		this.key = key;
		this.category = category;
		toggled = false;

		setup();
	}

	public void onEnable() {
		Main.instance.eventManager.register(this);
	}

	public void onDisable() {
		Main.instance.eventManager.unregister(this);
	}

    public void setEnabled(boolean enabled) {
        if (enabled) {
            EventManager.register(this);
        } else {
            EventManager.unregister(this);
        }
        this.toggled = enabled;
    }
	
	public void onToggle() {
	}

	public void toggle() {
		toggled = !toggled;
		onToggle();
		if (toggled)
			onEnable();
		else
			onDisable();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isToggled() {
		return toggled;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getModuleName() {
		return moduleName == null ? name : moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public void setup() {}

	public Translate getTranslate() {
        return translate;
    }
}
