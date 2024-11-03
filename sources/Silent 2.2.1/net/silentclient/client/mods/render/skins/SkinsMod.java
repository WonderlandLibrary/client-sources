package net.silentclient.client.mods.render.skins;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class SkinsMod extends Mod {
	public SkinsMod() {
		super("3D Skins", ModCategory.MODS, "silentclient/icons/mods/skinsmod.png");
	}

	@Override
	public void setup() {
		super.setup();

		this.addBooleanSetting("Fast Render", this, true);
		this.addSliderSetting("Level Of Detail Distance", this, 14, 5, 40, true);

		this.addBooleanSetting("Head", this, true);
		this.addBooleanSetting("Body", this, true);
		this.addBooleanSetting("Left Arm", this, true);
		this.addBooleanSetting("Right Arm", this, true);
		this.addBooleanSetting("Left Leg", this, true);
		this.addBooleanSetting("Right Leg", this, true);

		this.addSliderSetting("Voxel Size", this, 1.15F, 1F, 1.4F, false);
		this.addSliderSetting("Head Voxel Size", this, 1.18F, 1F, 1.25F, false);
		this.addSliderSetting("Body Voxel Width Size", this, 1.05F, 1F, 1.4F, false);
	}
}
