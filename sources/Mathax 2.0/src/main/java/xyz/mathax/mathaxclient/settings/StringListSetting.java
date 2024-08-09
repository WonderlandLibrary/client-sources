package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WMinus;
import xyz.mathax.mathaxclient.utils.gui.CharFilter;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class StringListSetting extends Setting<List<String>> {
    public final Class<? extends WTextBox.Renderer> renderer;

    public final CharFilter filter;

    public StringListSetting(String name, String description, List<String> defaultValue, Consumer<List<String>> onChanged, Consumer<Setting<List<String>>> onModuleEnabled, IVisible visible, Class<? extends WTextBox.Renderer> renderer, CharFilter filter) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);

        this.renderer = renderer;
        this.filter = filter;
    }

    @Override
    protected List<String> parseImpl(String string) {
        return Arrays.asList(string.split(","));
    }

    @Override
    protected boolean isValueValid(List<String> value) {
        return true;
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        value.forEach(value -> json.append("value", value));

        return json;
    }

    @Override
    public List<String> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof String value) {
                    get().add(value);
                }
            }
        }

        return get();
    }

    @Override
    public void resetImpl() {
        value = new ArrayList<>(defaultValue);
    }

    public static void fillTable(Theme theme, WTable table, StringListSetting setting) {
        table.clear();

        ArrayList<String> strings = new ArrayList<>(setting.get());
        CharFilter filter = setting.filter == null ? (text, c) -> true : setting.filter;

        for (int i = 0; i < setting.get().size(); i++) {
            int msgI = i;
            String message = setting.get().get(i);

            WTextBox textBox = table.add(theme.textBox(message, filter, setting.renderer)).expandX().widget();
            textBox.action = () -> strings.set(msgI, textBox.get());
            textBox.actionOnUnfocused = () -> setting.set(strings);

            WMinus delete = table.add(theme.minus()).widget();
            delete.action = () -> {
                strings.remove(msgI);
                setting.set(strings);

                fillTable(theme, table, setting);
            };

            table.row();
        }

        if (!setting.get().isEmpty()) {
            table.add(theme.horizontalSeparator()).expandX();
            table.row();
        }

        WButton add = table.add(theme.button("Add")).expandX().widget();
        add.action = () -> {
            strings.add("");
            setting.set(strings);

            fillTable(theme, table, setting);
        };

        WButton reset = table.add(theme.button(GuiRenderer.RESET)).widget();
        reset.action = () -> {
            setting.reset();

            fillTable(theme, table, setting);
        };
    }

    public static class Builder extends SettingBuilder<Builder, List<String>, StringListSetting> {
        private Class<? extends WTextBox.Renderer> renderer;

        private CharFilter filter;

        public Builder() {
            super(new ArrayList<>(0));
        }

        public Builder defaultValue(String... defaults) {
            return defaultValue(defaults != null ? Arrays.asList(defaults) : new ArrayList<>());
        }

        public Builder renderer(Class<? extends WTextBox.Renderer> renderer) {
            this.renderer = renderer;
            return this;
        }

        public Builder filter(CharFilter filter) {
            this.filter = filter;
            return this;
        }

        @Override
        public StringListSetting build() {
            return new StringListSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible, renderer, filter);
        }
    }
}
