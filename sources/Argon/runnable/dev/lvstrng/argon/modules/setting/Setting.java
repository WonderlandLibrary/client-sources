// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.modules.setting;

public abstract class Setting<T> {
    public CharSequence desc;
    private CharSequence name;

    public Setting(final CharSequence name) {
        this.name = name;
    }

    public T setDescription(final CharSequence desc) {
        this.desc = desc;
        return (T) this;
    }

    public CharSequence getName() {
        return this.name;
    }

    public void setName(final CharSequence name) {
        this.name = name;
    }

    public CharSequence getDesc() {
        return this.desc;
    }
}
