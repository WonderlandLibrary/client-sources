package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;

public class PackTweaksMod extends Mod {
    public PackTweaksMod() {
        super("Pack Tweaks", ModCategory.MODS, "silentclient/icons/mods/packtweaks.png");
    }

    @Override
    public void setup() {
    	this.addSliderSetting("Fire Height", this, 0, -0.8, 0.4, false);
        this.addSliderSetting("Fire Opacity", this, 0.9F, 0, 1, false);
        this.addBooleanSetting("Hide Pumpkin Overlay", this, false);
        this.addBooleanSetting("Water Fog", this, true);
    }
    
    @Override
    public void onChangeSettingValue(Setting setting) {
    	if(isEnabled()) {
    		mc.renderGlobal.loadRenderers();
    	}
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.renderGlobal.loadRenderers();
    }
}
