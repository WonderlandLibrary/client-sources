package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.Util.EnumOS;
import net.minecraft.util.text.TextComponentString;
import zamorozka.main.Zamorozka;

import java.io.File;

public class Wrapper {

    public static FileManager fileManager;
    private static FriendManager friendManager;


    public static final Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static final EntityPlayerSP getPlayer() {
        return Minecraft.player;
    }

    public static final WorldClient getWorld() {
        return getMinecraft().world;
    }


    public static final File getMinecraftDir() {
        return getMinecraft().mcDataDir;
    }

    public static FileManager getFileManager() {
        if (fileManager == null) fileManager = new FileManager();

        return fileManager;
    }

    public static FriendManager getFriends() {
        if (friendManager == null) friendManager = new FriendManager();

        return friendManager;
    }

    public static float getCooldown() {
        return getPlayer().getCooledAttackStrength(0);
    }

    public static void addChatMessage(String s) {
        getPlayer().addChatMessage(new TextComponentString(Zamorozka.wrap(String.format("[%s%s%s] %s", "\247e", Zamorozka.instance.getClientName(), "\247f", s), 100)));
    }


    /**
     * gets the working dir (OS specific) for the specific application (which is always minecraft)
     */
    public static File getAppDir(String par0Str) {
        String var1 = System.getProperty("user.home", ".");
        File var2;

        switch (getOs()) {
            case LINUX:
            case SOLARIS:
                var2 = new File(var1, '.' + par0Str + '/');
                break;

            case WINDOWS:
                String var3 = System.getenv("APPDATA");

                if (var3 != null) {
                    var2 = new File(var3, "." + par0Str + '/');
                } else {
                    var2 = new File(var1, '.' + par0Str + '/');
                }

                break;

            case OSX:
                var2 = new File(var1, "Library/Application Support/" + par0Str);
                break;

            default:
                var2 = new File(var1, par0Str + '/');
        }

        if (!var2.exists() && !var2.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + var2);
        } else {
            return var2;
        }
    }

    public static EnumOS getOs() {
        String var0 = System.getProperty("os.name").toLowerCase();
        return var0.contains("win") ? EnumOS.WINDOWS : (var0.contains("mac") ? EnumOS.OSX : (var0.contains("solaris") ? EnumOS.SOLARIS : (var0.contains("sunos") ? EnumOS.SOLARIS : (var0.contains("linux") ? EnumOS.LINUX : (var0.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }


}
