package dev.darkmoon.client.module.impl.util;

import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.ModeSetting;

@ModuleAnnotation(name = "DiscordPresence", category = Category.UTIL)
public class DiscordRPC extends Module {
    public static ModeSetting discordRPC = new ModeSetting("Discord-Mode", "Dark", "Dark", "White");
}
