package xyz.vergoclient.modules.impl.visual;

import xyz.vergoclient.modules.Module;

public class Xray extends Module {

	public Xray() {
		super("Xray", Category.VISUAL);
	}

	@Override
	public void onEnable() {
		try {
			mc.renderGlobal.loadRenderers();
		} catch (Exception e2) {

		}
	}

	@Override
	public void onDisable() {
		try {
			mc.renderGlobal.loadRenderers();
		} catch (Exception e2) {

		}
	}

}
