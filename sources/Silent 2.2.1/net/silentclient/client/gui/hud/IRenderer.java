package net.silentclient.client.gui.hud;

public interface IRenderer extends IRenderConfig {
	int getWidth();
	int getHeight();
	
	boolean render(ScreenPosition pos);
	
	default void renderDummy(ScreenPosition pos) {
		render(pos);
	}
	
	public default boolean isEnabled() {
		return true;
	}
	
	default boolean getRender() {
		return true;
	}
}
