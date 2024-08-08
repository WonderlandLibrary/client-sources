// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.setting;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import me.perry.mcdonalds.event.events.ClientEvent;
import me.perry.mcdonalds.features.Feature;
import java.util.function.Predicate;

public class Setting<T>
{
    private final String name;
    private final T defaultValue;
    private T value;
    private T plannedValue;
    private T min;
    private T max;
    private boolean hasRestriction;
    private Predicate<T> visibility;
    private String description;
    private Feature feature;
    
    public Setting(final String name, final T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.plannedValue = defaultValue;
        this.description = "";
    }
    
    public Setting(final String name, final T defaultValue, final String description) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.plannedValue = defaultValue;
        this.description = description;
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max, final String description) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.description = description;
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.description = "";
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max, final Predicate<T> visibility, final String description) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.visibility = visibility;
        this.description = description;
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max, final Predicate<T> visibility) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.visibility = visibility;
        this.description = "";
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValue, final Predicate<T> visibility) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.visibility = visibility;
        this.plannedValue = defaultValue;
    }
    
    public String getName() {
        return this.name;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final T value) {
        this.setPlannedValue(value);
        if (this.hasRestriction) {
            if (((Number)this.min).floatValue() > ((Number)value).floatValue()) {
                this.setPlannedValue(this.min);
            }
            if (((Number)this.max).floatValue() < ((Number)value).floatValue()) {
                this.setPlannedValue(this.max);
            }
        }
        final ClientEvent event = new ClientEvent(this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            this.value = this.plannedValue;
        }
        else {
            this.plannedValue = this.value;
        }
    }
    
    public T getPlannedValue() {
        return this.plannedValue;
    }
    
    public void setPlannedValue(final T value) {
        this.plannedValue = value;
    }
    
    public T getMin() {
        return this.min;
    }
    
    public void setMin(final T min) {
        this.min = min;
    }
    
    public T getMax() {
        return this.max;
    }
    
    public void setMax(final T max) {
        this.max = max;
    }
    
    public void setValueNoEvent(final T value) {
        this.setPlannedValue(value);
        if (this.hasRestriction) {
            if (((Number)this.min).floatValue() > ((Number)value).floatValue()) {
                this.setPlannedValue(this.min);
            }
            if (((Number)this.max).floatValue() < ((Number)value).floatValue()) {
                this.setPlannedValue(this.max);
            }
        }
        this.value = this.plannedValue;
    }
    
    public Feature getFeature() {
        return this.feature;
    }
    
    public void setFeature(final Feature feature) {
        this.feature = feature;
    }
    
    public int getEnum(final String input) {
        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            final Enum e = (Enum)this.value.getClass().getEnumConstants()[i];
            if (e.name().equalsIgnoreCase(input)) {
                return i;
            }
        }
        return -1;
    }
    
    public void setEnumValue(final String value) {
        for (final Enum e : (Enum[])((Enum)this.value).getClass().getEnumConstants()) {
            if (e.name().equalsIgnoreCase(value)) {
                this.value = (T)e;
            }
        }
    }
    
    public String currentEnumName() {
        return EnumConverter.getProperName((Enum)this.value);
    }
    
    public int currentEnum() {
        return EnumConverter.currentEnum((Enum)this.value);
    }
    
    public void increaseEnum() {
        this.plannedValue = (T)EnumConverter.increaseEnum((Enum)this.value);
        final ClientEvent event = new ClientEvent(this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            this.value = this.plannedValue;
        }
        else {
            this.plannedValue = this.value;
        }
    }
    
    public void increaseEnumNoEvent() {
        this.value = (T)EnumConverter.increaseEnum((Enum)this.value);
    }
    
    public String getType() {
        if (this.isEnumSetting()) {
            return "Enum";
        }
        return this.getClassName(this.defaultValue);
    }
    
    public <T> String getClassName(final T value) {
        return value.getClass().getSimpleName();
    }
    
    public String getDescription() {
        if (this.description == null) {
            return "";
        }
        return this.description;
    }
    
    public boolean isNumberSetting() {
        return this.value instanceof Double || this.value instanceof Integer || this.value instanceof Short || this.value instanceof Long || this.value instanceof Float;
    }
    
    public boolean isEnumSetting() {
        return !this.isNumberSetting() && !(this.value instanceof String) && !(this.value instanceof Bind) && !(this.value instanceof Character) && !(this.value instanceof Boolean);
    }
    
    public boolean isStringSetting() {
        return this.value instanceof String;
    }
    
    public T getDefaultValue() {
        return this.defaultValue;
    }
    
    public String getValueAsString() {
        return this.value.toString();
    }
    
    public boolean hasRestriction() {
        return this.hasRestriction;
    }
    
    public void setVisibility(final Predicate<T> visibility) {
        this.visibility = visibility;
    }
    
    public boolean isVisible() {
        return this.visibility == null || this.visibility.test(this.getValue());
    }
    
    public void toString(final T i) {
    }
}
