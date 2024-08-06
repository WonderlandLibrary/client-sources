package club.strifeclient.util.callback;

import club.strifeclient.setting.Setting;

public interface ChangeCallback<O, N> {
    void callback(O settingOld, Setting<N> settingNew);
}
