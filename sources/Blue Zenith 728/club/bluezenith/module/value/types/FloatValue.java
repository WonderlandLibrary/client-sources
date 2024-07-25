package club.bluezenith.module.value.types;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.function.Supplier;

import static club.bluezenith.util.render.RenderUtil.animate;
import static java.lang.Math.abs;

public final class FloatValue extends Value<Float> {
    public Float max;
    public Float min;
    public final float increment;

    public float animProgress;
    public boolean needAnimation;

    public FloatValue(String valueName, float value, float min, float max) {
        this(valueName, value, min, max, 0, true, null);
    }

    public FloatValue(String valueName, float value, float min, float max, float increment, boolean visible, Supplier<Boolean> modifier) {
        super(valueName, value, visible, null, modifier);
        this.max = max;
        this.min = min;
        this.increment = increment;
    }

    public FloatValue(String valueName, Float value, Float min, Float max, float increment, boolean visible, ValueConsumer<Float> consumer, Supplier<Boolean> modifier) {
        super(valueName, value, visible, consumer, modifier);
        this.max = max;
        this.min = min;
        this.increment = increment;
    }

    public FloatValue(String valueName, float value, float min, float max, float increment) {
        this(valueName, value, min, max, increment, true, null);
    }

    @Override
    public FloatValue setIndex(int index) {
        this.valIndex = index;
        return this;
    }

    @Override
    public FloatValue setID(String id) {
        this.id = "id-" + id;
        return this;
    }

    @Override
    public Float get() {
        return this.value;
    }

    @Override
    public void set(Float newValue) {
        //lmao
        if(listener != null && loaded) {
            this.value = listener.check(this.value, newValue);
        } else this.value = newValue;
        loaded = true;
    }
    public void next() {
        set(Math.min(value + increment, max));
    }

    @Override
    public void previous() {
        set(Math.max(value - increment, min));
    }

    @Override
    public FloatValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public FloatValue setValueChangeListener(ValueConsumer<Float> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public FloatValue setDefaultVisibility(boolean state) {
        this.visible = state;
        return this;
    }

    public float animateProgress(float targetX, float animationSpeed) {
        if(!this.needAnimation) return this.animProgress = targetX; //skip animation if it isn't needed

        this.animProgress = animate(targetX, this.animProgress, animationSpeed);
        if(abs(this.animProgress - targetX) < 0.15) needAnimation = false; //when the targetX is (almost) reached, disable animation to prevent it from going when you drag the dropdown panel
                                                                           //i know this a shitty way to do it but i don't want to do the maths that'd be a proper fix rn
        return this.animProgress;
    }

    @Override
    public JsonElement getPrimitive() {
        return new JsonPrimitive(this.value);
    }

    @Override
    public void fromElement(JsonElement primitive) {
         if(primitive.isJsonPrimitive() && primitive.getAsJsonPrimitive().isNumber())
         set(primitive.getAsFloat());
    }
}
