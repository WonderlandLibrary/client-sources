package net.silentclient.client.gui.hud;

public interface IRenderConfig {
	public void Save(ScreenPosition pos);
	
	public ScreenPosition load();
}
