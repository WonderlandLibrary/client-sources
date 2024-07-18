package com.alan.clients.ui.click.standard.components.value.impl;

import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.click.standard.components.value.ValueComponent;
import com.alan.clients.ui.click.standard.screen.Colors;
import com.alan.clients.util.gui.GUIUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.Value;
import com.alan.clients.value.impl.ListValue;
import com.alan.clients.value.impl.ModeValue;
import lombok.Getter;

@Getter
public class ListValueComponent extends ValueComponent {

    @Override
    public void draw(final Vector2d position, final int mouseX, final int mouseY, final float partialTicks) {
        final ListValue<?> listValue = (ListValue<?>) value;
        this.position = position;

        final String prefix = this.value.getName() + ":";

        Fonts.MAIN.get(16, Weight.REGULAR).draw(prefix, this.position.x, this.position.y, Colors.SECONDARY_TEXT.getRGBWithAlpha(opacity));
        Fonts.MAIN.get(16, Weight.REGULAR).draw(listValue instanceof ModeValue ? ((ModeValue) listValue).getValue().getName() : listValue.getValue().toString(), this.position.x + Fonts.MAIN.get(16, Weight.REGULAR).width(prefix) + 2, this.position.y, Colors.SECONDARY_TEXT.getRGBWithAlpha(opacity));
    }

    public ListValueComponent(final Value<?> value) {
        super(value);
    }

    @Override
    public boolean click(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.position == null) {
            return false;
        }

        final ListValue<?> listValue = (ListValue<?>) value;

        final boolean left = mouseButton == 0;
        final boolean right = mouseButton == 1;

        if (GUIUtil.mouseOver(this.position.x, this.position.y - 3.5f, getClickGUI().width - 70, this.height, mouseX, mouseY)) {
            final int currentIndex = listValue.getModes().indexOf(listValue.getValue());

            Object value = null;
            if (left) {
                if (listValue.getModes().size() <= currentIndex + 1) {
                    value = listValue.getModes().get(0);
                } else {
                    value = listValue.getModes().get(currentIndex + 1);
                }
            } else if (right) {
                if (0 > currentIndex - 1) {
                    value = listValue.getModes().get(listValue.getModes().size() - 1);
                } else {
                    value = listValue.getModes().get(currentIndex - 1);
                }
            }

            if (value != null) {
                if (this.getValue() instanceof ModeValue) {
                    ((ModeValue)listValue).update((Mode<?>) value);
                } else {
                    listValue.setValueAsObject(value);
                }
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

