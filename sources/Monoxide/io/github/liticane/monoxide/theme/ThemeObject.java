package io.github.liticane.monoxide.theme;

import com.google.gson.JsonObject;
import io.github.liticane.monoxide.util.interfaces.ColorPalette;
import io.github.liticane.monoxide.util.interfaces.Methods;
import net.minecraft.client.gui.ScaledResolution;
import io.github.liticane.monoxide.theme.data.ThemeObjectInfo;
import io.github.liticane.monoxide.theme.data.enums.ElementType;
import io.github.liticane.monoxide.theme.data.enums.ScreenType;
import io.github.liticane.monoxide.theme.data.enums.ThemeObjectType;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;

import java.util.List;

public abstract class ThemeObject implements Methods, ColorPalette {

    private final String name;
    private final ThemeObjectType themeObjectType;
    private final ElementType elementType;
    private final ScreenType screenType;

    public ThemeObject() {
        ThemeObjectInfo themeObjectInfo = this.getClass().getAnnotation(ThemeObjectInfo.class);
        if(themeObjectInfo == null)
            throw new RuntimeException();
        this.name = themeObjectInfo.name();
        this.themeObjectType = themeObjectInfo.themeObjectType();
        this.elementType = themeObjectInfo.elementType();
        this.screenType = themeObjectInfo.screenType();
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, Object[] params) {
        if(this.themeObjectType == ThemeObjectType.SCREEN) {
            throw new RuntimeException("Gui Screen objects should not be executed on drawing.");
        }
    }

    public JsonObject save() {
        JsonObject object = new JsonObject();
        List<Value> values = ValueManager.getInstance().getValues(this);
        if (values != null && !values.isEmpty()) {
            JsonObject propertiesObject = new JsonObject();
            for (Value property : values) {
                propertiesObject.addProperty(property.getIdName(), property.getValueAsString());
            }
            object.add("Values", propertiesObject);
        }
        return object;
    }

    public void load(JsonObject object) {
        List<Value> values = ValueManager.getInstance().getValues(this);

        if (object.has("Values") && values != null && !values.isEmpty()) {
            JsonObject propertiesObject = object.getAsJsonObject("Values");
            for (Value property : values) {
                if (propertiesObject.has(property.getIdName())) {
                    property.setValue(propertiesObject.get(property.getIdName()).getAsString());
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public ThemeObjectType getThemeObjectType() {
        return themeObjectType;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public ScreenType getScreenType() {
        return screenType;
    }
}
