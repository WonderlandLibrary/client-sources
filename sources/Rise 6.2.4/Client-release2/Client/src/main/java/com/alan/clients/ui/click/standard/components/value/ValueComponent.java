package com.alan.clients.ui.click.standard.components.value;

import com.alan.clients.util.Accessor;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Value;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class ValueComponent implements Accessor {

    public double height = 14;
    public Vector2d position;
    public Value<?> value;
    @Setter
    public int opacity = 255;

    public ValueComponent(final Value<?> value) {
        this.value = value;
    }

    public abstract void draw(Vector2d position, int mouseX, int mouseY, float partialTicks);

    public abstract boolean click(int mouseX, int mouseY, int mouseButton);

    public abstract void released();

    public abstract void bloom();

    public abstract void key(final char typedChar, final int keyCode);
}
