package com.minus;


import com.minus.event.EventManager;
import com.minus.module.ModuleManager;
import com.minus.utils.MinecraftInterface;
import com.minus.utils.font.FontManager;
import lombok.Getter;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;


@Getter
public final class Client implements ModInitializer, MinecraftInterface {

	@Override
	public void onInitialize() {
		Minus.instance.init();
	}

	public static MinecraftClient mc;
	public static Client INSTANCE;
	private final ModuleManager moduleManager;
	private final FontManager fontManager;
	public final EventManager eventManager;

	public Client() {
		INSTANCE = this;
		eventManager = new EventManager();
		mc = MinecraftClient.getInstance();
		moduleManager = new ModuleManager();
		fontManager = new FontManager();
	}
}
