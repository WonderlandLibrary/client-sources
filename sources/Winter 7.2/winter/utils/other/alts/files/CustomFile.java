/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other.alts.files;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;

public class CustomFile {
    private final File file;
    private final String name;
    private final File directory;

    public CustomFile(String name) {
        this.name = name;
        this.loadFile();
        this.directory = Util.getOSType() == Util.EnumOS.LINUX ? new File(Minecraft.getMinecraft().mcDataDir, "/Winter") : new File(Minecraft.getMinecraft().mcDataDir + "\\Winter");
        this.file = new File(this.directory, String.valueOf(String.valueOf(String.valueOf(name))) + ".txt");
        if (!this.file.exists()) {
            this.saveFile();
        }
    }

    public final File getFile() {
        return this.file;
    }

    public final String getName() {
        return this.name;
    }

    public void loadFile() {
    }

    public void saveFile() {
    }
}

