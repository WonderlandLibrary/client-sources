package host.kix.uzi.ui.click.impl.component;

import host.kix.uzi.ui.click.api.component.impl.Button;
import host.kix.uzi.ui.click.impl.GuiClick;
import host.kix.uzi.utilities.value.Value;

/**
 * @author Marc
 * @since 7/20/2016
 */
public class ValueButton extends Button {
    /**
     * An instance of the value.
     */
    private Value value;

    public ValueButton(GuiClick parent, Value value, int x, int y, int width, int height) {
        super(parent, value.getName(), (boolean) value.getValue(), x, y, width, height);
        this.value = value;
    }

    @Override
    public void draw(int x, int y) {
        parent.getTheme().drawComponent(this, x, y);
    }

    @Override
    public boolean isEnabled() {
        return (boolean) this.value.getValue();
    }

    @Override
    public void toggle() {
        value.setValue(!(boolean) value.getValue());
    }
}
