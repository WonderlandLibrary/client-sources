package dev.client.tenacity.config;

import net.minecraft.client.Minecraft;

import java.io.File;

public class LocalConfig extends Configuration {

    private final File file;

    public LocalConfig(String name) {
        super(name);
        this.file = new File(Minecraft.getMinecraft().mcDataDir + "/Tenacity/Configs/" + name + ".json");
    }

    public File getFile() {
        return file;
    }

}
