package win.sightclient.module;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import win.sightclient.Sight;
import win.sightclient.event.Event;

public class Module {

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	private String name;
	private String displayName;
	private Category category;
	private int key = Keyboard.KEY_NONE;
	
	private boolean toggled = false;
	private boolean hidden = false;
	
	public float renderSlide = 0F;
	public boolean showInClickGUI = true;
	
	public Module(String name, Category category) {
		super();
		this.name = name;
		this.category = category;
		this.displayName = this.name;
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return category;
	}
	
	public boolean isToggled() {
		return this.toggled;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public boolean isHidden() {
		return this.hidden;
	}
	
	public void setSuffix(String suffix) {
		StringBuilder sb = new StringBuilder(this.getName());
		sb.append(EnumChatFormatting.GRAY);
		sb.append(" ");
		sb.append(suffix);
		this.displayName = sb.toString();
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setKey(int key) {
		if (this.key != key) {
			this.key = key;
			
			if (Sight.instance.fileManager != null) {
				Sight.instance.fileManager.getBindsFile().save();
			}
		}
	}
	
	public int getKey() {
		return this.key;
	}
	
	public void setToggled(boolean t) {
		if (t != this.toggled) {
			this.toggle();
		}
	}
	
	public void setToggledNoSave(boolean t) {
		if (t != this.toggled) {
			this.toggleNoSave();
		}
	}
	
	private void update() {
		Sight.instance.getRichPresence().update();
	}
	
	public void toggle() {
		this.toggled = !this.toggled;
		if (this.toggled) {
			this.onEnable();
		} else {
			this.onDisable();
		}
		if (Sight.instance.fileManager != null) {
			Sight.instance.fileManager.saveDefaultConfig();
		}
		update();
	}
	
	public void toggleNoSave() {
		this.toggled = !this.toggled;
		if (this.toggled) {
			this.onEnable();
		} else {
			this.onDisable();
		}
		update();
	}
	
	public void onEnable() {}
	public void onDisable() {}
	public void updateSettings() {}
 
	public void onEvent(Event e) {}
}
