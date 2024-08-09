/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;

public interface IWorldInfo {
    public int getSpawnX();

    public int getSpawnY();

    public int getSpawnZ();

    public float getSpawnAngle();

    public long getGameTime();

    public long getDayTime();

    public boolean isThundering();

    public boolean isRaining();

    public void setRaining(boolean var1);

    public boolean isHardcore();

    public GameRules getGameRulesInstance();

    public Difficulty getDifficulty();

    public boolean isDifficultyLocked();

    default public void addToCrashReport(CrashReportCategory crashReportCategory) {
        crashReportCategory.addDetail("Level spawn location", this::lambda$addToCrashReport$0);
        crashReportCategory.addDetail("Level time", this::lambda$addToCrashReport$1);
    }

    private String lambda$addToCrashReport$1() throws Exception {
        return String.format("%d game time, %d day time", this.getGameTime(), this.getDayTime());
    }

    private String lambda$addToCrashReport$0() throws Exception {
        return CrashReportCategory.getCoordinateInfo(this.getSpawnX(), this.getSpawnY(), this.getSpawnZ());
    }
}

