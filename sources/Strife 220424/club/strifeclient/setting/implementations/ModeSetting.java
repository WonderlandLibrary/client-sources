package club.strifeclient.setting.implementations;

import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.Setting;
import club.strifeclient.util.system.StringUtil;
import imgui.type.ImInt;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Getter
public class ModeSetting<T extends SerializableEnum> extends Setting<T> {

    private final T[] values;
    private ImInt imEquivalent;

    public ModeSetting(String name, T defaultValue, Supplier<Boolean> dependency) {
        super(name, defaultValue, dependency);
        this.values = (T[]) value.getClass().getEnumConstants();
        final List<T> valueList = Arrays.asList(this.values);
        imEquivalent = new ImInt(valueList.indexOf(defaultValue));
        this.addChangeCallback((settingOld, settingNew) -> imEquivalent.set(valueList.indexOf(settingNew.getValue())));
    }

    @Override
    public void parse(Object original) {
        setValue((T) StringUtil.getEnumPrimitiveFromString((Enum<?>[]) value.getClass().getEnumConstants(), original.toString()));
    }

    public ModeSetting(String name, T value) {
        this(name, value, () -> true);
    }

    public void setValue(int index) {
        super.setValue(values[index]);
    }

    public int cycle(int index, boolean reverse) {
        if(reverse) {
            index--;
            if(index < 0)
                index = values.length - 1;
        } else {
            index++;
            if(index >= values.length)
                index = 0;
        }
        setValue(index);
        return index;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
