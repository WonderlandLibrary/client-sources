package com.polarware.ui.click.components.value.impl;

import com.polarware.ui.click.components.value.ValueComponent;
import com.polarware.util.vector.Vector2d;
import com.polarware.value.Value;
import net.minecraft.util.ResourceLocation;

public class PositionValueComponent extends ValueComponent {

    private final ResourceLocation image = new ResourceLocation("rise/icons/click.png");

    public PositionValueComponent(final Value<?> value) {
        super(value);
    }

    @Override
    public void draw(final Vector2d position, final int mouseX, final int mouseY, final float partialTicks) {
        this.height = 0;
    }

    @Override
    public void click(final int mouseX, final int mouseY, final int mouseButton) {
    }

    @Override
    public void released() {
    }

    @Override
    public void bloom() {
    }

    @Override
    public void key(final char typedChar, final int keyCode) {

    }
}
