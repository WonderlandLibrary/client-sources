import java.io.File;
import java.util.Arrays;

import net.minecraft.client.main.Main;
import net.minecraft.util.Util;
import oshi.SystemInfo;
import wtf.diablo.hwid.HwidUtil;

public final class Start
{
    public static void main(final String[] args)
    {
        final SystemInfo systemInfo = new SystemInfo();
        HwidUtil.generateHwid();
        final File minecraft_loc = getMinecraftDir();
        final File assets_loc = new File(minecraft_loc, "assets");

        Main.main(concat(new String[] {
                "--version", "mcp",
                "--accessToken", "0",
                "--gameDir", minecraft_loc.exists() ? minecraft_loc.toString() : "",
                "--assetsDir", assets_loc.exists() ? assets_loc.toString() : "assets",
                "--assetIndex", "1.8",
                "--userProperties", "{}"}, args));
    }

    private static File getMinecraftDir() {
        final String fileLocation;
        final Util.EnumOS operatingSystem = Util.getOSType();
        switch (operatingSystem) {
            case WINDOWS:
                fileLocation = (System.getenv("APPDATA") + "/.minecraft");
                break;
            case OSX:
                fileLocation = (System.getProperty("user.home") + "/Library/Application Support/minecraft");
                break;
            default: // for linux & others
                fileLocation = (System.getProperty("user.home") + "/.minecraft");
                break;
        }
        return new File(fileLocation);
    }

    public static <T> T[] concat(final T[] first, final T[] second)
    {
        final T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
