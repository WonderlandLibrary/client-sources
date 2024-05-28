package arsenic.module.property.impl;

import arsenic.gui.click.impl.ButtonComponent;
import arsenic.utils.render.PosInfo;
import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;

import arsenic.gui.click.impl.PropertyComponent;
import arsenic.module.property.IReliable;
import arsenic.module.property.SerializableProperty;
import arsenic.utils.render.RenderInfo;

import java.util.function.Supplier;

public class BooleanProperty extends SerializableProperty<Boolean> implements IReliable {

    public BooleanProperty(String name, Boolean value) {
        super(name, value);
    }

    @Override
    public JsonObject saveInfoToJson(@NotNull JsonObject obj) {
        obj.addProperty("enabled", value);
        return obj;
    }

    @Override
    public void loadFromJson(@NotNull JsonObject obj) {
        setValueSilently(obj.get("enabled").getAsBoolean());
    }

    @Override
    public Supplier<Boolean> valueCheck(String value) {
        return () -> Boolean.parseBoolean(value) == this.value && isVisible();
    }

    @Override
    public PropertyComponent<BooleanProperty> createComponent() {
        return new PropertyComponent<BooleanProperty>(this) {
            private final ButtonComponent buttonComponent = new ButtonComponent(this) {
                @Override
                protected boolean isEnabled() {
                    return getValue();
                }

                @Override
                protected void setEnabled(boolean enabled) {
                    setValueSilently(enabled);
                }
            };

            @Override
            public float updateComponent(PosInfo pi, RenderInfo ri) {
                if(isVisible())
                    buttonComponent.updateComponent(pi, ri);
                return super.updateComponent(pi, ri);
            }

            @Override
            protected float draw(RenderInfo ri) {
                return height;
            }

            @Override
            protected void click(int mouseX, int mouseY, int mouseButton) {
                buttonComponent.handleClick(mouseX, mouseY, mouseButton);
            }
        };
    }
}
