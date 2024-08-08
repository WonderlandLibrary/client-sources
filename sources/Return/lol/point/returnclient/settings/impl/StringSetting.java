package lol.point.returnclient.settings.impl;

import lol.point.Return;
import lol.point.returnclient.settings.Setting;
import lol.point.returnclient.theme.Theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StringSetting extends Setting {
    public final List<String> modes;
    public int index;
    public String value;

    public StringSetting(String name, String... modes) {
        this.name = name;
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(modes[0]);
        if (value == null) value = modes[0];
    }

    public StringSetting(String name, String defaultMode, String... modes) {
        this.name = name;
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
        if (this.index == -1) {
            this.index = 0;
        }
        this.value = this.modes.get(this.index);
    }

    public void cycleForwards() {
        index++;
        if (index > modes.size() - 1) index = 0;
        value = modes.get(index);
    }

    public void cycleBackwards() {
        index--;
        if (index < 0) index = modes.size() - 1;
        value = modes.get(index);
    }

    public static StringSetting fontSetting(String name, String mode) {
        final ArrayList<String> modes = new ArrayList<>();
        for (Map.Entry<String, List<String>> fonts : Return.INSTANCE.fontManager.availableFonts.entrySet()) {
            for (String type : fonts.getValue()) {
                String font = fonts.getKey() + "-" + type;
                if (!font.equals("return-Icons") || !font.equals("returnnew-Icons")) {
                    modes.add(font);
                }
            }
        }

        return new StringSetting(name, mode, modes.toArray(new String[0]));
    }


    public static StringSetting themeSetting(String name, String mode) {
        final ArrayList<String> modes = new ArrayList<>();
        for (Theme theme : Return.INSTANCE.themeManager.themes) {
            modes.add(theme.name);
        }

        return new StringSetting(name, mode, modes.toArray(new String[0]));
    }

    public boolean is(String value) {
        return this.value.equals(value);
    }
}
