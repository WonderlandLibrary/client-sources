package net.silentclient.client.mixin.accessors.skins;

import net.silentclient.client.mods.render.skins.render.CustomizableModelPart;

public interface PlayerSettings {

	public CustomizableModelPart client$getHeadLayers();
	
	public void client$setupHeadLayers(CustomizableModelPart box);
	
	public CustomizableModelPart[] client$getSkinLayers();
	
	public void client$setupSkinLayers(CustomizableModelPart[] box);

}
