package net.silentclient.client.mixin.accessors.skins;

import net.silentclient.client.mods.render.skins.renderlayers.BodyLayerFeatureRenderer;
import net.silentclient.client.mods.render.skins.renderlayers.HeadLayerFeatureRenderer;

/**
 * Used to expose the thinArms setting of the player model
 *
 */
public interface PlayerEntityModelAccessor {
	public boolean client$hasThinArms();
	public HeadLayerFeatureRenderer client$getHeadLayer();
	public BodyLayerFeatureRenderer client$getBodyLayer();
}