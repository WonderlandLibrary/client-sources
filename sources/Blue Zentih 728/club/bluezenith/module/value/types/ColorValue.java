package club.bluezenith.module.value.types;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.awt.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ColorValue extends Value<Color> {

    @Entry("hue") public float h;
    @Entry("saturation") public float s;
    @Entry("brightness") public float b;

    @Entry("hue-x") public float xH;
    @Entry("saturation-x") public float xS;
    @Entry("brightness-x") public float xB;

    public int sliderState;

    public ColorValue(String valueName) {
        super(valueName, new Color(30, 30, 250, 255), true, null, null);
        this.sliderState = 1;
        this.h = 0.5f;
        this.s = 0.8f;
        this.b = 0.9f;
    }

    @Override
    public ColorValue setIndex(int index) {
        this.valIndex = index;
        return this;
    }

    @Override
    public Color get() {
        return new Color(getRGB());
    }

    @Override
    public void set(Color newValue) {

    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public ColorValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public ColorValue setValueChangeListener(ValueConsumer<Color> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public Value<Color> setDefaultVisibility(boolean state) {
        return null;
    }

    @Override
    public JsonElement getPrimitive() {
        final JsonObject object = new JsonObject();

        try {
            object.add("WARNING", new JsonPrimitive("Do not edit the values that end with -x."));
            for (Field field : this.getClass().getFields()) {
                final Entry entry = field.getAnnotation(Entry.class);

                if(entry == null || field.getType() != float.class) continue;
                field.setAccessible(true);

                object.add(entry.value(), new JsonPrimitive((float) field.get(this)));
            }
        } catch (Exception exception) {
            object.add("error", new JsonPrimitive("Couldn't save the color value."));
            exception.printStackTrace();
        }

        return object;
    }

    @Override
    public void fromElement(JsonElement primitive) {
        if(primitive.isJsonObject()) {
            final JsonObject object = primitive.getAsJsonObject();

            try {
                for (Field field : this.getClass().getFields()) {
                    final Entry entry = field.getAnnotation(Entry.class);

                    if (entry == null || field.getType() != float.class) continue;
                    field.setAccessible(true);

                    if(object.has(entry.value()))
                        field.set(this, object.get(entry.value()).getAsFloat());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if(primitive.isJsonPrimitive()) {
            final Color c = new Color(primitive.getAsInt());
            float[] hsb = new float[3];
            hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
            this.h = hsb[0];
            this.s = hsb[1];
            this.b = hsb[2];
            if(listener != null)
                listener.check(null, null);
        }
    }

    public void setHue(float h) {
        if(listener != null)
            listener.check(null, null);
        this.h = h;
    }
    public void setSaturation(float s) {
        if(listener != null)
            listener.check(null, null);
        this.s = s;
    }
    public void setBrightness(float b) {
        if(listener != null)
            listener.check(null, null);
        this.b = b;
    }
    public int getRGB() {
        return Color.HSBtoRGB(h, s, b);
    }

    @Override
    public ColorValue setID(String id) {
        this.id = "id-" + id;
        return this;
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface Entry {
        String value();
    }
}
