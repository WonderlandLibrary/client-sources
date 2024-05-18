package baritone.api;

import baritone.api.utils.*;

public final class Settings$Setting<T>
{
    public T value;
    public final T defaultValue;
    private String name;
    private final Class<T> klass;
    final Settings this$0;
    
    private Settings$Setting(final Settings this$0, final T t) {
        this.this$0 = this$0;
        super();
        if (t == null) {
            throw new IllegalArgumentException("Cannot determine value type class from null");
        }
        this.value = t;
        this.defaultValue = t;
        this.klass = (Class<T>)t.getClass();
    }
    
    @Deprecated
    public final T get() {
        return this.value;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final Class<T> getValueClass() {
        return this.klass;
    }
    
    @Override
    public final String toString() {
        return SettingsUtil.settingToString(this);
    }
    
    public final void reset() {
        this.value = this.defaultValue;
    }
    
    Settings$Setting(final Settings settings, final Object o, final Settings$1 settings$1) {
        this(o);
    }
    
    static String access$102(final Settings$Setting settings$Setting, final String name) {
        return settings$Setting.name = name;
    }
}
