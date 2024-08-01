package wtf.diablo.client.module.impl.movement.velocity;

import wtf.diablo.client.setting.api.IMode;

public enum EnumVelocityMode implements IMode {
    PERCENT("Percent"),
    WATCHDOG("Watchdog");

    private final String name;

    EnumVelocityMode(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
