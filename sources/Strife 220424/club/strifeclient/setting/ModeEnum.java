package club.strifeclient.setting;

import club.strifeclient.module.Module;

public interface ModeEnum<Parent extends Module> extends SerializableEnum {
    Mode<? extends ModeEnum<Parent>> getMode();
}
