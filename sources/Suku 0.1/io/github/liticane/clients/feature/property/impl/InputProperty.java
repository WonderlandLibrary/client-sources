package io.github.liticane.clients.feature.property.impl;

import imgui.type.ImString;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.Property;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

@Getter
@Setter
public class InputProperty extends Property {
    private String string = "";
    private final ImString imString;

    public InputProperty(String name, Module module, String defaultValue) {
        this.name = name;
        this.string = defaultValue;
        this.imString = new ImString(string, 512);
        module.getProperties().add(this);
    }

    public InputProperty(String name, Module module, String defaultValue, Supplier<Boolean> visible) {
        this.name = name;
        this.string = defaultValue;
        this.imString = new ImString(string, 512);

        this.visible = visible;
        module.getProperties().add(this);
    }

    public String getString() {
        return string
                .replaceAll("%fps%", "" + Minecraft.getDebugFPS());
    }

}
