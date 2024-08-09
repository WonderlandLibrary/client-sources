/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import java.util.UUID;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.storage.ISpawnWorldInfo;

public interface IServerWorldInfo
extends ISpawnWorldInfo {
    public String getWorldName();

    public void setThundering(boolean var1);

    public int getRainTime();

    public void setRainTime(int var1);

    public void setThunderTime(int var1);

    public int getThunderTime();

    @Override
    default public void addToCrashReport(CrashReportCategory crashReportCategory) {
        ISpawnWorldInfo.super.addToCrashReport(crashReportCategory);
        crashReportCategory.addDetail("Level name", this::getWorldName);
        crashReportCategory.addDetail("Level game mode", this::lambda$addToCrashReport$0);
        crashReportCategory.addDetail("Level weather", this::lambda$addToCrashReport$1);
    }

    public int getClearWeatherTime();

    public void setClearWeatherTime(int var1);

    public int getWanderingTraderSpawnDelay();

    public void setWanderingTraderSpawnDelay(int var1);

    public int getWanderingTraderSpawnChance();

    public void setWanderingTraderSpawnChance(int var1);

    public void setWanderingTraderID(UUID var1);

    public GameType getGameType();

    public void setWorldBorderSerializer(WorldBorder.Serializer var1);

    public WorldBorder.Serializer getWorldBorderSerializer();

    public boolean isInitialized();

    public void setInitialized(boolean var1);

    public boolean areCommandsAllowed();

    public void setGameType(GameType var1);

    public TimerCallbackManager<MinecraftServer> getScheduledEvents();

    public void setGameTime(long var1);

    public void setDayTime(long var1);

    private String lambda$addToCrashReport$1() throws Exception {
        return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", this.getRainTime(), this.isRaining(), this.getThunderTime(), this.isThundering());
    }

    private String lambda$addToCrashReport$0() throws Exception {
        return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.getGameType().getName(), this.getGameType().getID(), this.isHardcore(), this.areCommandsAllowed());
    }
}

