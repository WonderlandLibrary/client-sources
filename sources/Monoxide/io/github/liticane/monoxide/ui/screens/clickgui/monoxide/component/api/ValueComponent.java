package io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api;

import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.value.Value;

@SuppressWarnings({"unused", "rawtypes"})
public class ValueComponent extends Component {

    public final Value value;

    public ValueComponent(MonoxideClickGuiScreen parent, Value value) {
        super(parent);
        this.value = value;
    }

    public boolean canShow() {
        return this.value.isVisible();
    }

}
