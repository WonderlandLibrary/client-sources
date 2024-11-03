package net.silentclient.client.mixin.accessors.skins;

import net.silentclient.client.mods.render.skins.render.CustomizableModelPart;

public interface SkullSettings {

    public CustomizableModelPart getHeadLayers();
    
    public void setupHeadLayers(CustomizableModelPart box);
    
}
