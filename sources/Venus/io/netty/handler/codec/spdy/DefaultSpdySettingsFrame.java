/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.SpdySettingsFrame;
import io.netty.util.internal.StringUtil;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DefaultSpdySettingsFrame
implements SpdySettingsFrame {
    private boolean clear;
    private final Map<Integer, Setting> settingsMap = new TreeMap<Integer, Setting>();

    @Override
    public Set<Integer> ids() {
        return this.settingsMap.keySet();
    }

    @Override
    public boolean isSet(int n) {
        return this.settingsMap.containsKey(n);
    }

    @Override
    public int getValue(int n) {
        Setting setting = this.settingsMap.get(n);
        return setting != null ? setting.getValue() : -1;
    }

    @Override
    public SpdySettingsFrame setValue(int n, int n2) {
        return this.setValue(n, n2, false, true);
    }

    @Override
    public SpdySettingsFrame setValue(int n, int n2, boolean bl, boolean bl2) {
        if (n < 0 || n > 0xFFFFFF) {
            throw new IllegalArgumentException("Setting ID is not valid: " + n);
        }
        Integer n3 = n;
        Setting setting = this.settingsMap.get(n3);
        if (setting != null) {
            setting.setValue(n2);
            setting.setPersist(bl);
            setting.setPersisted(bl2);
        } else {
            this.settingsMap.put(n3, new Setting(n2, bl, bl2));
        }
        return this;
    }

    @Override
    public SpdySettingsFrame removeValue(int n) {
        this.settingsMap.remove(n);
        return this;
    }

    @Override
    public boolean isPersistValue(int n) {
        Setting setting = this.settingsMap.get(n);
        return setting != null && setting.isPersist();
    }

    @Override
    public SpdySettingsFrame setPersistValue(int n, boolean bl) {
        Setting setting = this.settingsMap.get(n);
        if (setting != null) {
            setting.setPersist(bl);
        }
        return this;
    }

    @Override
    public boolean isPersisted(int n) {
        Setting setting = this.settingsMap.get(n);
        return setting != null && setting.isPersisted();
    }

    @Override
    public SpdySettingsFrame setPersisted(int n, boolean bl) {
        Setting setting = this.settingsMap.get(n);
        if (setting != null) {
            setting.setPersisted(bl);
        }
        return this;
    }

    @Override
    public boolean clearPreviouslyPersistedSettings() {
        return this.clear;
    }

    @Override
    public SpdySettingsFrame setClearPreviouslyPersistedSettings(boolean bl) {
        this.clear = bl;
        return this;
    }

    private Set<Map.Entry<Integer, Setting>> getSettings() {
        return this.settingsMap.entrySet();
    }

    private void appendSettings(StringBuilder stringBuilder) {
        for (Map.Entry<Integer, Setting> entry : this.getSettings()) {
            Setting setting = entry.getValue();
            stringBuilder.append("--> ");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(':');
            stringBuilder.append(setting.getValue());
            stringBuilder.append(" (persist value: ");
            stringBuilder.append(setting.isPersist());
            stringBuilder.append("; persisted: ");
            stringBuilder.append(setting.isPersisted());
            stringBuilder.append(')');
            stringBuilder.append(StringUtil.NEWLINE);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(StringUtil.simpleClassName(this)).append(StringUtil.NEWLINE);
        this.appendSettings(stringBuilder);
        stringBuilder.setLength(stringBuilder.length() - StringUtil.NEWLINE.length());
        return stringBuilder.toString();
    }

    private static final class Setting {
        private int value;
        private boolean persist;
        private boolean persisted;

        Setting(int n, boolean bl, boolean bl2) {
            this.value = n;
            this.persist = bl;
            this.persisted = bl2;
        }

        int getValue() {
            return this.value;
        }

        void setValue(int n) {
            this.value = n;
        }

        boolean isPersist() {
            return this.persist;
        }

        void setPersist(boolean bl) {
            this.persist = bl;
        }

        boolean isPersisted() {
            return this.persisted;
        }

        void setPersisted(boolean bl) {
            this.persisted = bl;
        }
    }
}

