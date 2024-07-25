package club.bluezenith.module.value.types;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.function.Supplier;

import static club.bluezenith.util.render.RenderUtil.animate;
import static java.lang.Math.abs;

public final class IntegerValue extends Value<Integer> {
    public Integer max, min;
    public final int increment;

    public float animProgress;
    public boolean needAnimation;

    public IntegerValue(String valueName, int value, int min, int max, int increment, boolean visible, ValueConsumer<Integer> consumer, Supplier<Boolean> modifier) {
        super(valueName, value, visible, consumer, modifier);
        this.max = max;
        this.min = min;
        this.increment = increment;
    }

    public IntegerValue(String valueName, int value, int min, int max, int increment, boolean visible, Supplier<Boolean> modifier) {
        super(valueName, value, visible, null, modifier);
        this.max = max;
        this.min = min;
        this.increment = increment;
    }

    public IntegerValue(String valueName, int defaultValue, int min, int max, int increment) {
        this(valueName, defaultValue, min, max, increment, true, null, null);
    }

    @Override
    public IntegerValue setIndex(int index) {
        this.valIndex = index;
        return this;
    }

    @Override
    public Integer get() {
        return this.value;
    }

    @Override
    public void set(Integer newValue) {
        if(listener != null && loaded) {
            this.value = listener.check(this.value, newValue);
        } else this.value = newValue;
        loaded = true;
    }
    @Override
    public void next() {
        set(Math.min(value + increment, max));
    }

    @Override
    public void previous() {
        set(Math.max(value - increment, min));
    }

    @Override
    public IntegerValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public IntegerValue setValueChangeListener(ValueConsumer<Integer> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public IntegerValue setDefaultVisibility(boolean state) {
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
        set(primitive.getAsInt());
    }

    @Override
    public IntegerValue setID(String id) {
        this.id = "id-" + id;
        return this;
    }
}
