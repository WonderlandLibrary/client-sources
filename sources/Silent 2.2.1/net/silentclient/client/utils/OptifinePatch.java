package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;

import java.io.File;
import java.io.FileInputStream;

public class OptifinePatch {
    public static final File optifineConfig = new File(Minecraft.getMinecraft().mcDataDir, "optionsof.txt");
    public static boolean fastRenderEnabled = false;
    public static boolean fastRenderPatched = false;

    public static void init() {
        try {
            if(optifineConfig.exists()) {
                String ofConfig = FileUtils.readInputStream(new FileInputStream(optifineConfig));

                if(ofConfig.contains("ofFastRender:true")) {
                    fastRenderEnabled = true;
                }
            } else {
                fastRenderEnabled = true;
            }
        } catch (Exception err) {
            Client.logger.catching(err);
        }
    }

    public static boolean needPatch() {
        return fastRenderEnabled && !fastRenderPatched;
    }

    public static void patch() {
        Minecraft.getMinecraft().toggleFullscreen();
        Minecraft.getMinecraft().toggleFullscreen();
        fastRenderPatched = true;
    }
}
