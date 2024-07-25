package club.bluezenith.module.value.types;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.util.font.FontUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.FontRenderer;

import java.util.function.Supplier;

public class FontValue extends Value<FontRenderer> {
    private int index;

    public FontValue(String valueName, FontRenderer value, boolean visible, ValueConsumer<FontRenderer> consumer, Supplier<Boolean> modifier) {
        super(valueName, value, visible, consumer, modifier);
        index = FontUtil.fonts.indexOf(value);
        if(index == -1) index = 0;
    }

    public FontValue(String valueName, FontRenderer value) {
        this(valueName, value, true, null, null);
    }

    @Override
    public FontValue setIndex(int index) {
        if(index == -1) return this;
        this.valIndex = index;
        return this;
    }

    @Override
    public FontRenderer get() {
        return value != null ? value : FontUtil.minecraft;
    }

    @Override
    public void set(FontRenderer newValue) {
        if(listener != null) {
            this.value = listener.check(this.value, newValue);
        } else this.value = newValue;
    }

    @Override
    public void next() {
        if((index + 1) < FontUtil.fonts.size())
            index++;
         else index = 0;
        set(FontUtil.fonts.get(index));
    }

    @Override
    public void previous() {
        if((index - 1) > -1)
            index--;
        else index = FontUtil.fonts.size() - 1;
        set(FontUtil.fonts.get(index));

    }

    @Override
    public FontValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public FontValue setValueChangeListener(ValueConsumer<FontRenderer> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public FontValue setDefaultVisibility(boolean state) {
        this.visible = state;
        return this;
    }

    //TODO: Implement methods
    //what methods
    @Override
    public JsonElement getPrimitive() {
        return new JsonPrimitive(get().getName());
    }

    @Override
    public void fromElement(JsonElement primitive) {
        this.value = FontUtil.fonts.stream().filter(i -> i.getName().equals(primitive.getAsString())).findFirst().orElse(FontUtil.minecraft);
        setIndex(FontUtil.fonts.indexOf(this.value));
    }

    @Override
    public FontValue setID(String id) {
        this.id = "id-" + id;
        return this;
    }
}
