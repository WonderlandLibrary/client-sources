package net.silentclient.client.mods;

public interface IMod {
	default void onDisable() {}
	
	default void onEnable() {}
	
	default void setup() {}
}
