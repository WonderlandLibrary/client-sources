package wtf.shiyeno.modules.settings.imp;

import lombok.Getter;
import lombok.Setter;
import wtf.shiyeno.modules.settings.Setting;

import java.util.function.Supplier;

@Getter
@Setter
public class BindSetting extends Setting {

    private int key;

    public BindSetting(String name, int defaultKey) {
        super(name);
        key = defaultKey;
    }
    public BindSetting setVisible(Supplier<Boolean> bool) {
        visible = bool;
        return this;
    }

    @Override
    public SettingType getType() {
        return SettingType.BIND_SETTING;
    }
}
