// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.modules.setting.settings;

import dev.lvstrng.argon.modules.setting.Setting;

public final class KeybindSetting extends Setting<KeybindSetting> {
    private int key;
    private boolean listening;

    public KeybindSetting(final CharSequence name, final int key) {
        super(name);
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(final int key) {
        this.key = key;
    }

    public void getListening() {
        this.listening = !this.listening;
    }

    public boolean isListening() {
        return this.listening;
    }

    public void setListening(final boolean listening) {
        this.listening = listening;
    }
}
