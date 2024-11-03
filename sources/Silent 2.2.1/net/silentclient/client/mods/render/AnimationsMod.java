package net.silentclient.client.mods.render;

import net.silentclient.client.Client;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class AnimationsMod extends Mod {
	
	public AnimationsMod() {
		super("Animations", ModCategory.MODS, "silentclient/icons/mods/oldanimations.png", true);
	}
	
	@Override
	public void setup() {
		this.addBooleanSetting("1.7 Item Positions", this, true);
		this.addBooleanSetting("1.7 Bow Pullback", this, true);
		this.addBooleanSetting("1.7 Block Animation", this, true);
		this.addBooleanSetting("1.7 Rod Position", this, true);
		this.addBooleanSetting("1.7 3rd Person Block Animation", this, true);
		this.addBooleanSetting("1.7 Throwing", this, true);
		this.addBooleanSetting("1.7 Enchant Glint", this, true);
		this.addBooleanSetting("1.7 Skins", this, false);
		this.addBooleanSetting("No Shaking", this, true);
		this.addSliderSetting("Shaking Intensity", this, 14, 0, 100, true);
		this.addBooleanSetting("Smooth Sneaking", this, true);
		this.addBooleanSetting("Consume Animation", this, true);
		this.addBooleanSetting("Block-Hitting Animation", this, true);
		this.addBooleanSetting("Red Armor", this, true);
		this.addBooleanSetting("Punching During Usage", this, true);
		this.addBooleanSetting("Item Switching Animation", this, true);
		this.addBooleanSetting("Remove Health Bar Flashing", this, true);
	}

	public static boolean getSettingBoolean(String name) {
		return Client.getInstance().getModInstances().getOldAnimationsMod().isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(AnimationsMod.class, name).getValBoolean();
	}
}
