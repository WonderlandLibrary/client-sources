package me.travis.wurstplus.setting.builder.primitive;

import me.travis.wurstplus.setting.builder.SettingBuilder;
import me.travis.wurstplus.setting.impl.StringSetting;

/**
 * Created by 086 on 13/10/2018.
 */
public class StringSettingBuilder extends SettingBuilder<String> {
    @Override
    public StringSetting build() {
        return new StringSetting(initialValue, predicate(), consumer(), name, visibilityPredicate());
    }
}
