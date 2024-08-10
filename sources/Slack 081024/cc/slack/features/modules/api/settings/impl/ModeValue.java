// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.api.settings.impl;

import cc.slack.features.modules.api.settings.Value;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ModeValue<T> extends Value<T> {

    private final T[] modes;

    public ModeValue(String name, T[] modes) {
        super(name, modes[0], null);
        this.modes = modes;
    }

    public ModeValue(T[] modes) {
        super("Mode: ", modes[0], null);
        this.modes = modes;
    }

    public T increment() {
        List<T> choices = Arrays.asList(this.modes);
        int currentIndex = choices.indexOf(getValue());
        currentIndex++;
        if (currentIndex >= choices.size()) currentIndex = 0;
        setValue(choices.get(currentIndex));
        return choices.get(currentIndex);
    }

    public T decrement() {
        List<T> choices = Arrays.asList(this.modes);
        int currentIndex = choices.indexOf(getValue());
        currentIndex--;
        if (currentIndex < 0) currentIndex = choices.size() - 1;
        setValue(choices.get(currentIndex));
        return choices.get(currentIndex);
    }

    public int getIndex() {
        List<T> choices = Arrays.asList(this.modes);
        return choices.indexOf(getValue());
    }

    public void setIndex(Integer index) {
        List<T> choices = Arrays.asList(this.modes);
        setValue(choices.get(index));
    }

    public boolean is(T mode) {
        for (T m : modes) {
            if (m instanceof String) {
                if (((String) m).toLowerCase() == mode)
                    return true;
            }
            if (m == mode)
                return true;
        }
        return false;
    }

    public void setValueFromString(String value) {
        for (T choice : modes) {
            if (choice.toString().equalsIgnoreCase(value)) {
                setValue(choice);
            }
        }
    }
}
