// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import xyz.niggfaclient.Client;
import net.minecraft.util.StringUtils;
import java.lang.reflect.Field;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.property.impl.DoubleProperty;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import xyz.niggfaclient.config.Serializable;
import xyz.niggfaclient.keybind.Bindable;
import xyz.niggfaclient.property.Property;

public class Module extends Manager<Property<?>> implements Bindable, Toggleable, Serializable
{
    protected Minecraft mc;
    private String name;
    private String description;
    private int key;
    public Category category;
    private double verticalTransition;
    private double transition;
    private String displayName;
    private boolean beingEnabled;
    private boolean visible;
    private boolean enabled;
    
    public Module() {
        super(new ArrayList());
        this.mc = Minecraft.getMinecraft();
        this.name = this.getClass().getAnnotation(ModuleInfo.class).name();
        this.description = this.getClass().getAnnotation(ModuleInfo.class).description();
        this.key = this.getClass().getAnnotation(ModuleInfo.class).key();
        this.category = this.getClass().getAnnotation(ModuleInfo.class).cat();
        this.beingEnabled = false;
        this.visible = true;
    }
    
    public void resetPropertyValues() {
        for (final Property<?> property : this.getElements()) {
            property.callFirstTime();
        }
    }
    
    public void reflectProperties() {
        for (final Field field : this.getClass().getDeclaredFields()) {
            final Class<?> type = field.getType();
            if (type.isAssignableFrom(Property.class) || type.isAssignableFrom(DoubleProperty.class) || type.isAssignableFrom(EnumProperty.class)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    this.elements.add((T)field.get(this));
                }
                catch (IllegalAccessException ex) {}
            }
        }
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            if (!this.enabled) {
                this.onEnable();
            }
        }
        else if (this.enabled) {
            this.onDisable();
        }
        this.enabled = enabled;
    }
    
    @Override
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    @Override
    public void onPress() {
        this.toggle();
    }
    
    @Override
    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    @Override
    public void onEnable() {
        this.beingEnabled = true;
        this.verticalTransition = 0.0;
        this.transition = this.mc.fontRendererObj.getStringWidth(StringUtils.stripControlCodes(this.getDisplayName()));
        Client.getInstance().getEventBus().subscribe(this);
    }
    
    @Override
    public void onDisable() {
        this.beingEnabled = false;
        Client.getInstance().getEventBus().unsubscribe(this);
    }
    
    public double getVerticalTransition() {
        return this.verticalTransition;
    }
    
    public void setVerticalTransition(final double verticalTransition) {
        this.verticalTransition = verticalTransition;
    }
    
    public boolean isBeingEnabled() {
        return this.beingEnabled;
    }
    
    public void setBeingEnabled(final boolean beingEnabled) {
        this.beingEnabled = beingEnabled;
    }
    
    public double getTransition() {
        return this.transition;
    }
    
    public void setTransition(final double transition) {
        this.transition = transition;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(final Category category) {
        this.category = category;
    }
    
    public String getDisplayName() {
        return (this.displayName == null) ? this.name : this.displayName;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public JsonObject save() {
        final JsonObject object = new JsonObject();
        object.addProperty("toggled", this.isEnabled());
        object.addProperty("key", this.getKey());
        object.addProperty("hidden", this.isVisible());
        final List<Property<?>> properties = this.getElements();
        if (!properties.isEmpty()) {
            final JsonObject propertiesObject = new JsonObject();
            for (final Property<?> property : properties) {
                if (property instanceof DoubleProperty) {
                    propertiesObject.addProperty(property.getName(), ((Property<Number>)property).getValue());
                }
                else if (property instanceof EnumProperty) {
                    final EnumProperty<?> enumProperty = (EnumProperty<?>)(EnumProperty)property;
                    propertiesObject.add(property.getName(), new JsonPrimitive(enumProperty.getValue().name()));
                }
                else if (property.getType() == Boolean.class) {
                    propertiesObject.addProperty(property.getName(), (Boolean)property.getValue());
                }
                else if (property.getType() == Integer.class) {
                    propertiesObject.addProperty(property.getName(), Integer.toHexString((int)property.getValue()));
                }
                else {
                    if (property.getType() != String.class) {
                        continue;
                    }
                    propertiesObject.addProperty(property.getName(), (String)property.getValue());
                }
            }
            object.add("Properties", propertiesObject);
        }
        return object;
    }
    
    @Override
    public void load(final JsonObject object) {
        if (object.has("toggled")) {
            this.setEnabled(object.get("toggled").getAsBoolean());
        }
        if (object.has("key")) {
            this.setKey(object.get("key").getAsInt());
        }
        if (object.has("hidden")) {
            this.setVisible(object.get("hidden").getAsBoolean());
        }
        if (object.has("Properties") && !this.getElements().isEmpty()) {
            final JsonObject propertiesObject = object.getAsJsonObject("Properties");
            for (final Property<?> property : this.getElements()) {
                if (propertiesObject.has(property.getName())) {
                    if (property instanceof DoubleProperty) {
                        ((DoubleProperty)property).setValue(propertiesObject.get(property.getName()).getAsDouble());
                    }
                    else if (property instanceof EnumProperty) {
                        this.findEnumValue(property, propertiesObject);
                    }
                    else if (property.getValue() instanceof Boolean) {
                        property.setValue((Object)propertiesObject.get(property.getName()).getAsBoolean());
                    }
                    else {
                        if (!(property.getValue() instanceof Integer)) {
                            continue;
                        }
                        property.setValue((Object)(int)Long.parseLong(propertiesObject.get(property.getName()).getAsString(), 16));
                    }
                }
            }
        }
    }
    
    private <T extends Enum<T>> void findEnumValue(final Property<?> property, final JsonObject propertiesObject) {
        final EnumProperty<T> enumProperty = (EnumProperty<T>)(EnumProperty)property;
        final String value = propertiesObject.getAsJsonPrimitive(property.getName()).getAsString();
        for (final T possibleValue : enumProperty.getValues()) {
            if (possibleValue.name().equalsIgnoreCase(value)) {
                enumProperty.setValue(possibleValue);
                break;
            }
        }
    }
}
