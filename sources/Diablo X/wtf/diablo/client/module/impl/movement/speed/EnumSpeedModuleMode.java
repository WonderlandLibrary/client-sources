package wtf.diablo.client.module.impl.movement.speed;

import wtf.diablo.client.setting.api.IMode;

public enum EnumSpeedModuleMode implements IMode {
    VANILLA("Vanilla"),
    WATCHDOG("Watchdog"),
    WATCHDOG_EXPERIMENTAL("Watchdog Experimental"),
    STRAFE("Strafe"),
    VERUS("Verus");

    private final String name;

    EnumSpeedModuleMode(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
