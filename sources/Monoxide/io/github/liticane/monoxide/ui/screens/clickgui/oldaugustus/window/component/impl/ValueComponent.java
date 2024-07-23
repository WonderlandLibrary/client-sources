package io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.component.impl;

import io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.component.Component;
import io.github.liticane.monoxide.value.Value;

public abstract class ValueComponent extends Component {

    private final Value value;

    public ValueComponent(Value value, float posX, float posY, float height) {
        super(posX, posY, height);
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
