package win.sightclient.module.movement.flight;

import java.util.ArrayList;

import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;

public class Flight extends Module {

	private ModeSetting mode = new ModeSetting("Mode", this, new String[] {"Hypixel", "Disabler", "Vanilla"});
	private ModeSetting hmode = new ModeSetting("HypixelMode", this, new String[] {"Free", "Premium"});;
	private NumberSetting speed = new NumberSetting("Speed", this, 1, 0.3, 4, false);
	
	private VanillaFlight vanilla;
	private HypixelFlight hypixel;
	private DisablerFlight disabler;
	
	public Flight() {
		super("Flight", Category.MOVEMENT);
		vanilla = new VanillaFlight(this, speed);
		hypixel = new HypixelFlight(this, speed, this.hmode);
		disabler = new DisablerFlight(this, speed);
	}
	
	@Override
	public void updateSettings() {
		this.hmode.setVisible(this.mode.getValue().equalsIgnoreCase("Hypixel"));
	}
	
	@Override
	public void onEvent(Event e) {
		this.setSuffix(this.mode.getValue());
		if (this.mode.getValue().equalsIgnoreCase("Hypixel")) {
			this.hypixel.onEvent(e);
		}
		if (this.mode.getValue().equalsIgnoreCase("Vanilla")) {
			this.vanilla.onEvent(e);
		}
		if (this.mode.getValue().equalsIgnoreCase("Disabler")) {
			this.disabler.onEvent(e);
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		if (this.mode.getValue().equalsIgnoreCase("Hypixel")) {
			this.hypixel.onDisable();
		}
		if (this.mode.getValue().equalsIgnoreCase("Vanilla")) {
			this.vanilla.onDisable();
		}
		if (this.mode.getValue().equalsIgnoreCase("Disabler")) {
			this.disabler.onDisable();
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		if (this.mode.getValue().equalsIgnoreCase("Hypixel")) {
			this.hypixel.onEnable();
		}
		if (this.mode.getValue().equalsIgnoreCase("Vanilla")) {
			this.vanilla.onEnable();
		}
		if (this.mode.getValue().equalsIgnoreCase("Disabler")) {
			this.disabler.onEnable();
		}
	}
}
