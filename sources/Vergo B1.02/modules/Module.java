package xyz.vergoclient.modules;

import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.text.WordUtils;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.modules.impl.movement.scaffold.NewScaffold;
import xyz.vergoclient.settings.Setting;
import xyz.vergoclient.ui.notifications.ingame.NotificationManager;
import xyz.vergoclient.ui.notifications.ingame.NotificationType;
import xyz.vergoclient.util.animations.Direction;
import xyz.vergoclient.util.anticheat.Player;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Module {
	
	public Module(String name, String description, Category category) {
		this.name = name;
		this.description = description;
		this.category = category;
		canSubscribeToEvents = this instanceof OnEventInterface;
	}
	
	public Module(String name, Category category) {
		this.name = name;
		this.description = "";
		this.category = category;
		canSubscribeToEvents = this instanceof OnEventInterface;
	}
	
	private String name, info = "";
	private final String description;
	private Category category;
	private boolean enabled = false;
	
	public transient CopyOnWriteArrayList<Setting> settings = new CopyOnWriteArrayList<>();
	
	// For the clickgui
	public transient boolean clickguiExtended = false;
	public transient float clickguiFlip = 0, clickguiExpand = 0;
	
	// For the arraylist
	public transient double arrayListAnimation = 0;
	
	public static Minecraft mc = Minecraft.getMinecraft();
	public static transient Player player = Vergo.getPlayer();
	
	public String getName() {
		return name;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void loadSettings() {
		
	}
	
	protected void addSettings(Setting... settings) {
		this.settings.clear();
		this.settings.addAll(Arrays.asList(settings));
	}
	
	public void silentToggle() {
		
		enabled = !enabled;
		
		if (canSubscribeToEvents && isEnabled() && !ModuleManager.eventListeners.contains((OnEventInterface) this))
			ModuleManager.eventListeners.add((OnEventInterface) this);
		else if (canSubscribeToEvents && isDisabled() && ModuleManager.eventListeners.contains((OnEventInterface) this))
			ModuleManager.eventListeners.remove((OnEventInterface) this);
	}
	
	public void toggle() {
		
		enabled = !enabled;
		
		if (mc.thePlayer == null || mc.theWorld == null) {
			
		}else {
			if (enabled) {
				if(Vergo.config.modNotifications.isDisabled()) {

				} else {
					String message = WordUtils.wrap("Toggled on " + getName(), 120);
					NotificationManager.post(NotificationType.SUCCESS, getName() + " Enabled!", message);
				}
				onEnable();
			}else {
				if(Vergo.config.modNotifications.isDisabled()) {

				} else {
					String message = WordUtils.wrap("Toggled off " + getName(), 120);
					NotificationManager.post(NotificationType.DISABLE, getName() + " Disabled!", message);
				}

				if(Vergo.config.modScaffold.isEnabled()) {
					NewScaffold.openingAnimation.setDirection(Direction.BACKWARDS);
					if(NewScaffold.openingAnimation.isDone() && NewScaffold.openingAnimation.getDirection() == Direction.BACKWARDS) {
						onDisable();
					}
				} else {
					onDisable();
				}
			}
		}
		
		if (canSubscribeToEvents && isEnabled() && !ModuleManager.eventListeners.contains((OnEventInterface) this))
			ModuleManager.eventListeners.add((OnEventInterface) this);
		else if (canSubscribeToEvents && isDisabled() && ModuleManager.eventListeners.contains((OnEventInterface) this))
			ModuleManager.eventListeners.remove((OnEventInterface) this);
	}
	
	public boolean isEnabled() {

		return enabled;
	}
	
	public boolean isDisabled() {
		return !enabled;
	}
	
	public void onEnable() {};
	public void onDisable() {};
	
	public transient boolean canSubscribeToEvents = false;
	
	public static enum Category{
		
		COMBAT("Combat"),
		VISUAL("Visual"),
		PLAYER("Player"),
		MOVEMENT("Movement"),
		MEMES("Memes"),
		MISCELLANEOUS("Miscellaneous");
		
		public final String displayName;
		//public final Icons icon;
		
		private Category(String displayName/*, Icons icon*/) {
			this.displayName = displayName;
			//this.icon = icon;
		}
		
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
