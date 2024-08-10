// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.api.settings.impl;

import cc.slack.features.modules.api.settings.Value;
import lombok.Getter;

@Getter
public class NumberValue<T extends Number> extends Value<T> {
    private final T minimum;
    private final T maximum;
    private final T increment;

    public NumberValue(String name, T defaultValue, T minimum, T maximum, T increment) {
        super(name, defaultValue, null);
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
    }
}
