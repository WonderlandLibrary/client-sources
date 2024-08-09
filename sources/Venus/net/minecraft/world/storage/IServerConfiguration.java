/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import com.mojang.serialization.Lifecycle;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.IServerWorldInfo;

public interface IServerConfiguration {
    public DatapackCodec getDatapackCodec();

    public void setDatapackCodec(DatapackCodec var1);

    public boolean isModded();

    public Set<String> getServerBranding();

    public void addServerBranding(String var1, boolean var2);

    default public void addToCrashReport(CrashReportCategory crashReportCategory) {
        crashReportCategory.addDetail("Known server brands", this::lambda$addToCrashReport$0);
        crashReportCategory.addDetail("Level was modded", this::lambda$addToCrashReport$1);
        crashReportCategory.addDetail("Level storage version", this::lambda$addToCrashReport$2);
    }

    default public String getStorageVersionName(int n) {
        switch (n) {
            case 19132: {
                return "McRegion";
            }
            case 19133: {
                return "Anvil";
            }
        }
        return "Unknown?";
    }

    @Nullable
    public CompoundNBT getCustomBossEventData();

    public void setCustomBossEventData(@Nullable CompoundNBT var1);

    public IServerWorldInfo getServerWorldInfo();

    public WorldSettings getWorldSettings();

    public CompoundNBT serialize(DynamicRegistries var1, @Nullable CompoundNBT var2);

    public boolean isHardcore();

    public int getStorageVersionId();

    public String getWorldName();

    public GameType getGameType();

    public void setGameType(GameType var1);

    public boolean areCommandsAllowed();

    public Difficulty getDifficulty();

    public void setDifficulty(Difficulty var1);

    public boolean isDifficultyLocked();

    public void setDifficultyLocked(boolean var1);

    public GameRules getGameRulesInstance();

    public CompoundNBT getHostPlayerNBT();

    public CompoundNBT getDragonFightData();

    public void setDragonFightData(CompoundNBT var1);

    public DimensionGeneratorSettings getDimensionGeneratorSettings();

    public Lifecycle getLifecycle();

    private String lambda$addToCrashReport$2() throws Exception {
        int n = this.getStorageVersionId();
        return String.format("0x%05X - %s", n, this.getStorageVersionName(n));
    }

    private String lambda$addToCrashReport$1() throws Exception {
        return Boolean.toString(this.isModded());
    }

    private String lambda$addToCrashReport$0() throws Exception {
        return String.join((CharSequence)", ", this.getServerBranding());
    }
}

