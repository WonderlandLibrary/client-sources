package src.Wiksi.command.impl;

import src.Wiksi.command.Prefix;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrefixImpl implements Prefix {
    final String prefix = ".";

    @Override
    public void set(String prefix) {

    }

    @Override
    public String get() {
        return prefix;
    }
}
