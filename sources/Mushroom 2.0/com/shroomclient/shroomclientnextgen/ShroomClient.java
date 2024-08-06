package com.shroomclient.shroomclientnextgen;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.client.ClickGUI;
import com.shroomclient.shroomclientnextgen.protection.JnicInclude;
import net.fabricmc.api.ModInitializer;

public class ShroomClient implements ModInitializer {

    public static String version = "2.0";
    public static String commandKey = ".";

    public static String selectedConfig = "config.mushroom";

    @JnicInclude
    @Override
    public void onInitialize() {
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // $$$INSERT$$$___LICENSE-CHECK-3___$$$INSERT$$$

        ModuleManager.init();
        if (!ModuleManager.didFinishInit) {
            System.out.println("Failed to launch due to an unknown error");
            System.exit(1);
        }
        //new AuthServer();

        ClickGUI.fontType = ClickGUI.font.Minecraft;
    }
}
