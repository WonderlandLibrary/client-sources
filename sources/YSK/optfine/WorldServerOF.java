package optfine;

import net.minecraft.server.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import net.minecraft.profiler.*;

public class WorldServerOF extends WorldServer
{
    private MinecraftServer mcServer;
    
    private void fixWorldWeather() {
        if (this.worldInfo.isRaining() || this.worldInfo.isThundering()) {
            this.worldInfo.setRainTime("".length());
            this.worldInfo.setRaining("".length() != 0);
            this.setRainStrength(0.0f);
            this.worldInfo.setThunderTime("".length());
            this.worldInfo.setThundering("".length() != 0);
            this.setThunderStrength(0.0f);
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState("  ".length(), 0.0f));
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(0x66 ^ 0x61, 0.0f));
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(0x6C ^ 0x64, 0.0f));
        }
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void fixWorldTime() {
        if (this.worldInfo.getGameType().getID() == " ".length()) {
            final long worldTime = this.getWorldTime();
            final long n = worldTime % 24000L;
            if (Config.isTimeDayOnly()) {
                if (n <= 1000L) {
                    this.setWorldTime(worldTime - n + 1001L);
                }
                if (n >= 11000L) {
                    this.setWorldTime(worldTime - n + 24001L);
                }
            }
            if (Config.isTimeNightOnly()) {
                if (n <= 14000L) {
                    this.setWorldTime(worldTime - n + 14001L);
                }
                if (n >= 22000L) {
                    this.setWorldTime(worldTime - n + 24000L + 14001L);
                }
            }
        }
    }
    
    @Override
    protected void updateWeather() {
        if (!Config.isWeatherEnabled()) {
            this.fixWorldWeather();
        }
        super.updateWeather();
    }
    
    @Override
    public void tick() {
        super.tick();
        if (!Config.isTimeDefault()) {
            this.fixWorldTime();
        }
        if (Config.waterOpacityChanged) {
            Config.waterOpacityChanged = ("".length() != 0);
            ClearWater.updateWaterOpacity(Config.getGameSettings(), this);
        }
    }
    
    public WorldServerOF(final MinecraftServer mcServer, final ISaveHandler saveHandler, final WorldInfo worldInfo, final int n, final Profiler profiler) {
        super(mcServer, saveHandler, worldInfo, n, profiler);
        this.mcServer = mcServer;
    }
}
