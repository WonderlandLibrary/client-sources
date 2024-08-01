package wtf.diablo.client.module.impl.combat.criticals;

import wtf.diablo.client.setting.api.IMode;

public enum EnumCriticalsMode implements IMode {
    OFF_GROUND("Off Ground"),
    WATCHDOG("Watchdog"),
    ;

    private final String name;

    EnumCriticalsMode(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
