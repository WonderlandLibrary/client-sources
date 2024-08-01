package wtf.diablo.client.module.impl.player.antifall;

import wtf.diablo.client.setting.api.IMode;

public enum EnumAntiFallMode implements IMode {
    BLINK("Blink"),
    ;

    private final String name;

    EnumAntiFallMode(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
