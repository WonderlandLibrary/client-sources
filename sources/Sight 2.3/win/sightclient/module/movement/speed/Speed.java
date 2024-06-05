package win.sightclient.module.movement.speed;

import win.sightclient.event.Event;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;

public class Speed extends Module {

	private ModeSetting mode = new ModeSetting("Mode", this, new String[] {"Hypixel", "Vanilla", "OnGround"});
	private NumberSetting speed = new NumberSetting("Speed", this, 0.6, 0.05, 2, false);
	
	private HypixelSpeed hypixel;
	private VanillaSpeed vanilla;
	private CubecraftSpeed cubecraft;
	private MineplexSpeed mineplex;
	private OnGroundSpeed ground;
	
	public Speed() {
		super("Speed", Category.MOVEMENT);
		hypixel = new HypixelSpeed(this);
		vanilla = new VanillaSpeed(this, speed);
		mineplex = new MineplexSpeed(this);
		cubecraft = new CubecraftSpeed(this);
		ground = new OnGroundSpeed(this);
	}
	
	@Override
	public void updateSettings() {
		this.speed.setVisible(this.mode.getValue().equalsIgnoreCase("Vanilla"));
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
		if (this.mode.getValue().equalsIgnoreCase("Cubecraft")) {
			this.cubecraft.onEvent(e);
		}
		if (this.mode.getValue().equalsIgnoreCase("Mineplex")) {
			this.mineplex.onEvent(e);
		}
		if (this.mode.getValue().equalsIgnoreCase("OnGround")) {
			this.ground.onEvent(e);
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		if (this.mode.getValue().equalsIgnoreCase("Hypixel")) {
			this.hypixel.onEnable();
		}
		if (this.mode.getValue().equalsIgnoreCase("Mineplex")) {
			this.mineplex.onEnable();
		}
		if (this.mode.getValue().equalsIgnoreCase("OnGround")) {
			this.ground.onEnable();
		}
	}
	
	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1F;
		if (this.mode.getValue().equalsIgnoreCase("Cubecraft")) {
			this.cubecraft.onDisable();
		}
		if (this.mode.getValue().equalsIgnoreCase("Hypixel")) {
			this.hypixel.onDisable();
		}
	}
}
