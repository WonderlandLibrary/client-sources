package wtf.diablo.client.module.impl.player.noslow;

import wtf.diablo.client.setting.api.IMode;

public enum EnumNoSlowMode implements IMode {
    WATCHDOG("Watchdog"),
    GRIM("Grim"),
    AAC4("AAC 4"),
    AAC5("AAC 5"),
    NCP("NCP"),
    VANILLA("Vanilla"),;
    private final String name;

    EnumNoSlowMode(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
