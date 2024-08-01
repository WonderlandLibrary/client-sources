package wtf.diablo.client.module.impl.player.nofall;

import wtf.diablo.client.setting.api.IMode;

public enum NoFallModeEnum implements IMode {
    VANILLA("Vanilla"),
    //WATCHDOG("Watchdog"),
    GROUNDSPOOF("Ground Spoof"),
    KARHU("Karhu"),
    BLOCKS_MC("Blocks MC"),
    VERUS("Verus"),
    AIR("Air"),
    GROUND("Ground"),
    NCP("NCP"),
    VULCAN("Vulcan");

    private final String name;

    NoFallModeEnum(final String name) {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
