package net.shoreline.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

/**
 * Fabric {@link ModInitializer}.
 *
 * @author linus
 * @since 1.0
 */
public class ShorelineMod implements ClientModInitializer
{
    // Mod identifier
    public static final String MOD_ID = "shoreline";
    // Mod name
    public static final String MOD_NAME = "Shoreline";
    // Mod version - should comply with https://semver.org/
    // UPDATE BEFORE RELEASE
    public static final String MOD_VER = "1.0";
    // Build number - updated every time a major change happens
    // This is more of debug information and could be formatted as:
    // Name 1.0.{BUILD_NUMBER}-{GIT_HASH}
    public static final String MOD_BUILD_NUMBER = "b2";
    // Mod mc version
    public static final String MOD_MC_VER = "1.19.4";

    /**
     * This code runs as soon as Minecraft is in a mod-load-ready state.
     * However, some things (like resources) may still be uninitialized.
     * Proceed with mild caution.
     */
    @Override
    public void onInitializeClient()
    {
        Shoreline.preInit();
        Shoreline.init();
        Shoreline.postInit();
    }
}
