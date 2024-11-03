package com.minus.utils;

import net.fabricmc.fabric.mixin.networking.client.accessor.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;

public interface MinecraftInterface {
    public MinecraftClient mc = MinecraftClient.getInstance();
}
