package com.alan.clients.ui.click.standard.components.value.impl;

import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.click.standard.components.value.ValueComponent;
import com.alan.clients.ui.click.standard.screen.Colors;
import com.alan.clients.util.gui.GUIUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.Value;
import com.alan.clients.value.impl.ModeValue;
import lombok.Getter;

@Getter
public class ModeValueComponent extends ValueComponent {

    public ModeValueComponent(final Value<?> value) {
        super(value);
    }

    @Override
    public void draw(final Vector2d position, final int mouseX, final int mouseY, final float partialTicks) {
        final ModeValue modeValue = (ModeValue) value;
        this.position = position;

        final String prefix = this.value.getName() + ":";

        Fonts.MAIN.get(16, Weight.REGULAR).draw(prefix, this.position.x, this.position.y, Colors.SECONDARY_TEXT.getRGBWithAlpha(Math.min(opacity, Colors.SECONDARY_TEXT.get().getAlpha())));
        Fonts.MAIN.get(16, Weight.REGULAR).draw(modeValue.getValue().getName(), this.position.x + Fonts.MAIN.get(16, Weight.REGULAR).width(prefix) + 2, this.position.y, Colors.SECONDARY_TEXT.getRGBWithAlpha(Math.min(opacity, Colors.SECONDARY_TEXT.get().getAlpha())));
    }

    @Override
    public boolean click(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.position == null) {
            return false;
        }

        final ModeValue modeValue = (ModeValue) value;

        final boolean left = mouseButton == 0;
        final boolean right = mouseButton == 1;

        if (GUIUtil.mouseOver(this.position.x, this.position.y - 3.5f, getClickGUI().width - 70, this.height, mouseX, mouseY)) {
            final int currentIndex = modeValue.getModes().indexOf(modeValue.getValue());

            Mode<?> mode = null;
            if (left) {
                if (modeValue.getModes().size() <= currentIndex + 1) {
                    mode = modeValue.getModes().get(0);
                } else {
                    mode = modeValue.getModes().get(currentIndex + 1);
                }
            } else if (right) {
                if (0 > currentIndex - 1) {
                    mode = modeValue.getModes().get(modeValue.getModes().size() - 1);
                } else {
                    mode = modeValue.getModes().get(currentIndex - 1);
                }
            }

            if (mode != null) {
                modeValue.update(mode);
            }

            return true;
        }

        return false;
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

