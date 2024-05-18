package net.minecraft.world.demo;

import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.world.storage.*;
import net.minecraft.profiler.*;

public class DemoWorldServer extends WorldServer
{
    private static final String[] I;
    private static final long demoWorldSeed;
    public static final WorldSettings demoWorldSettings;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0005<\u0005\"*k\u0010\u0016$-':\u00197", "KSwVB");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        demoWorldSeed = DemoWorldServer.I["".length()].hashCode();
        demoWorldSettings = new WorldSettings(DemoWorldServer.demoWorldSeed, WorldSettings.GameType.SURVIVAL, " ".length() != 0, "".length() != 0, WorldType.DEFAULT).enableBonusChest();
    }
    
    public DemoWorldServer(final MinecraftServer minecraftServer, final ISaveHandler saveHandler, final WorldInfo worldInfo, final int n, final Profiler profiler) {
        super(minecraftServer, saveHandler, worldInfo, n, profiler);
        this.worldInfo.populateFromWorldSettings(DemoWorldServer.demoWorldSettings);
    }
}
