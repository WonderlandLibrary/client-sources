package me.xatzdevelopments.xatz.client.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;

public class CosmeticController {
	
	// TODO Add database bullshit, probs getting crap from som sort of pastebin??
	public static boolean ShouldRenderTopHat(AbstractClientPlayer player) {
		return true;
	}
	
	public static float[] getTopHatColour(AbstractClientPlayer player) {
		return new float[] {1, 0, 0};
	}

}
