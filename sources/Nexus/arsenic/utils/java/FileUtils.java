package arsenic.utils.java;

import net.minecraft.client.Minecraft;

import java.io.File;

public class FileUtils extends UtilityClass {

    public static String getNexusFolderDirAsString() {
        return Minecraft.getMinecraft().mcDataDir + File.separator + "Arsenic";
    }

    public static File getNexusFolderDirAsFile() {
        return new File(Minecraft.getMinecraft().mcDataDir + File.separator + "Arsenic");
    }
}
