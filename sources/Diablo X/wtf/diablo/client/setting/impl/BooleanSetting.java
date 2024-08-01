package wtf.diablo.client.setting.impl;

import wtf.diablo.client.setting.api.AbstractSetting;

public final class BooleanSetting extends AbstractSetting<Boolean> {
    public BooleanSetting(final String name, final Boolean value) {
        super(name, value);
    }

    @Override
    public Boolean parse(final String value) {
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return this.getValue();
        }
    }
}
