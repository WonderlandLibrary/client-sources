package wtf.diablo.client.module.impl.player.scaffoldrecode.modes;

import wtf.diablo.client.setting.api.IMode;

public enum EnumSwingMode implements IMode {
    CLIENT("Client"),
    SERVER("Server")
    ;
    private final String name;


    EnumSwingMode(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
