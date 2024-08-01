package wtf.diablo.client.module.impl.player.scaffoldrecode.modes;

import wtf.diablo.client.setting.api.IMode;

public enum EnumSprintMode implements IMode {
    VANILLA("Vanilla"),
    WATCHDOG("Watchdog")
    ;

    private final String name;

    EnumSprintMode(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
