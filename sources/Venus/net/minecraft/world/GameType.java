/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum GameType {
    NOT_SET(-1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure"),
    SPECTATOR(3, "spectator");

    private final int id;
    private final String name;

    private GameType(int n2, String string2) {
        this.id = n2;
        this.name = string2;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gameMode." + this.name);
    }

    public void configurePlayerCapabilities(PlayerAbilities playerAbilities) {
        if (this == CREATIVE) {
            playerAbilities.allowFlying = true;
            playerAbilities.isCreativeMode = true;
            playerAbilities.disableDamage = true;
        } else if (this == SPECTATOR) {
            playerAbilities.allowFlying = true;
            playerAbilities.isCreativeMode = false;
            playerAbilities.disableDamage = true;
            playerAbilities.isFlying = true;
        } else {
            playerAbilities.allowFlying = false;
            playerAbilities.isCreativeMode = false;
            playerAbilities.disableDamage = false;
            playerAbilities.isFlying = false;
        }
        playerAbilities.allowEdit = !this.hasLimitedInteractions();
    }

    public boolean hasLimitedInteractions() {
        return this == ADVENTURE || this == SPECTATOR;
    }

    public boolean isCreative() {
        return this == CREATIVE;
    }

    public boolean isSurvivalOrAdventure() {
        return this == SURVIVAL || this == ADVENTURE;
    }

    public static GameType getByID(int n) {
        return GameType.parseGameTypeWithDefault(n, SURVIVAL);
    }

    public static GameType parseGameTypeWithDefault(int n, GameType gameType) {
        for (GameType gameType2 : GameType.values()) {
            if (gameType2.id != n) continue;
            return gameType2;
        }
        return gameType;
    }

    public static GameType getByName(String string) {
        return GameType.parseGameTypeWithDefault(string, SURVIVAL);
    }

    public static GameType parseGameTypeWithDefault(String string, GameType gameType) {
        for (GameType gameType2 : GameType.values()) {
            if (!gameType2.name.equals(string)) continue;
            return gameType2;
        }
        return gameType;
    }
}

