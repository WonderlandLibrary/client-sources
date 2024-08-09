package mathax.client.settings;

import mathax.client.gui.widgets.containers.WTable;
import mathax.client.gui.widgets.input.WTextBox;
import mathax.client.gui.widgets.pressable.WButton;
import mathax.client.gui.widgets.pressable.WMinus;
import mathax.client.gui.widgets.pressable.WPlus;
import mathax.client.gui.GuiTheme;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class StringListSetting extends Setting<List<String>>{
    public String newText = "";

    public StringListSetting(String name, String description, List<String> defaultValue, Consumer<List<String>> onChanged, Consumer<Setting<List<String>>> onModuleActivated, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleActivated, visible);
    }

    @Override
    public void resetImpl() {
        value = new ArrayList<>(defaultValue);
    }

    @Override
    protected List<String> parseImpl(String str) {
        return Arrays.asList(str.split(","));
    }

    @Override
    protected boolean isValueValid(List<String> value) {
        return true;
    }

    @Override
    public NbtCompound save(NbtCompound tag) {
        NbtList valueTag = new NbtList();
        for (int i = 0; i < this.value.size(); i++) {
            valueTag.add(i, NbtString.of(get().get(i)));
        }
        tag.put("value", valueTag);

        return tag;
    }

    @Override
    public List<String> load(NbtCompound tag) {
        get().clear();

        NbtList valueTag = tag.getList("value", 8);
        for (NbtElement tagI : valueTag) {
            get().add(tagI.asString());
        }

        return get();
    }

    public static void fillTable(GuiTheme theme, WTable table, StringListSetting setting) {
        setting.get().removeIf(String::isEmpty);
        ArrayList<String> strings = new ArrayList<>(setting.get());

        for (int i = 0; i < setting.get().size(); i++) {
            int msgI = i;
            String message = setting.get().get(i);

            WTextBox textBox = table.add(theme.textBox(message)).expandX().widget();
            textBox.action = () -> strings.set(msgI, textBox.get());
            textBox.actionOnUnfocused = () -> setting.set(strings);

            WMinus delete = table.add(theme.minus()).widget();
            delete.action = () -> {
                strings.remove(msgI);
                setting.set(strings);

                table.clear();
                fillTable(theme, table, setting);
            };

            table.row();
        }

        WTextBox textBox = table.add(theme.textBox(setting.newText)).minWidth(300).expandX().widget();
        textBox.action = () -> setting.newText = textBox.get();

        WPlus add = table.add(theme.plus()).widget();
        add.action = () -> {
            strings.add(setting.newText);
            setting.set(strings);
            setting.newText = "";

            table.clear();
            fillTable(theme, table, setting);
        };

        // Reset
        table.row();
        WButton reset = table.add(theme.button("Reset")).widget();
        reset.action = () -> {
            setting.reset();
            table.clear();
            fillTable(theme, table, setting);
        };
    }

    public static class Builder extends SettingBuilder<Builder, List<String>, StringListSetting> {
        public Builder() {
            super(new ArrayList<>(0));
        }

        public Builder defaultValue(String... defaults) {
            return defaultValue(defaults != null ? Arrays.asList(defaults) : new ArrayList<>());
        }

        @Override
        public StringListSetting build() {
            return new StringListSetting(name, description, defaultValue, onChanged, onModuleActivated, visible);
        }
    }
}
