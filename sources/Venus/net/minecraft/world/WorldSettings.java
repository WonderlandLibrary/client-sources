/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class WorldSettings {
    private final String worldName;
    private final GameType gameType;
    private final boolean hardcoreEnabled;
    private final Difficulty difficulty;
    private final boolean commandsAllowed;
    private final GameRules gameRules;
    private final DatapackCodec datapackCodec;

    public WorldSettings(String string, GameType gameType, boolean bl, Difficulty difficulty, boolean bl2, GameRules gameRules, DatapackCodec datapackCodec) {
        this.worldName = string;
        this.gameType = gameType;
        this.hardcoreEnabled = bl;
        this.difficulty = difficulty;
        this.commandsAllowed = bl2;
        this.gameRules = gameRules;
        this.datapackCodec = datapackCodec;
    }

    public static WorldSettings decodeWorldSettings(Dynamic<?> dynamic, DatapackCodec datapackCodec) {
        GameType gameType = GameType.getByID(dynamic.get("GameType").asInt(0));
        return new WorldSettings(dynamic.get("LevelName").asString(""), gameType, dynamic.get("hardcore").asBoolean(true), dynamic.get("Difficulty").asNumber().map(WorldSettings::lambda$decodeWorldSettings$0).result().orElse(Difficulty.NORMAL), dynamic.get("allowCommands").asBoolean(gameType == GameType.CREATIVE), new GameRules(dynamic.get("GameRules")), datapackCodec);
    }

    public String getWorldName() {
        return this.worldName;
    }

    public GameType getGameType() {
        return this.gameType;
    }

    public boolean isHardcoreEnabled() {
        return this.hardcoreEnabled;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public boolean isCommandsAllowed() {
        return this.commandsAllowed;
    }

    public GameRules getGameRules() {
        return this.gameRules;
    }

    public DatapackCodec getDatapackCodec() {
        return this.datapackCodec;
    }

    public WorldSettings setGameType(GameType gameType) {
        return new WorldSettings(this.worldName, gameType, this.hardcoreEnabled, this.difficulty, this.commandsAllowed, this.gameRules, this.datapackCodec);
    }

    public WorldSettings setDifficulty(Difficulty difficulty) {
        return new WorldSettings(this.worldName, this.gameType, this.hardcoreEnabled, difficulty, this.commandsAllowed, this.gameRules, this.datapackCodec);
    }

    public WorldSettings setDatapackCodec(DatapackCodec datapackCodec) {
        return new WorldSettings(this.worldName, this.gameType, this.hardcoreEnabled, this.difficulty, this.commandsAllowed, this.gameRules, datapackCodec);
    }

    public WorldSettings clone() {
        return new WorldSettings(this.worldName, this.gameType, this.hardcoreEnabled, this.difficulty, this.commandsAllowed, this.gameRules.clone(), this.datapackCodec);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private static Difficulty lambda$decodeWorldSettings$0(Number number) {
        return Difficulty.byId(number.byteValue());
    }
}

