package wtf.diablo.client.setting.impl;

import wtf.diablo.client.setting.api.AbstractSetting;

public final class TextSetting extends AbstractSetting<String> {
    public TextSetting(final String name, final String value) {
        super(name, value);
    }

    @Override
    public String parse(String value) {
        return value;
    }
}