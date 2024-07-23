package io.github.liticane.monoxide.ui.screens.clickgui.astolfo.component.impl;

import io.github.liticane.monoxide.ui.screens.clickgui.astolfo.component.Component;
import io.github.liticane.monoxide.value.Value;

public abstract class ValueComponent extends Component {

    protected final Value value;

    public ValueComponent(Value value, float posX, float posY, float baseWidth, float baseHeight) {
        super(posX, posY, baseWidth, baseHeight);
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
