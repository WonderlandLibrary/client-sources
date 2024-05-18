/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.slot;

import me.thekirkayt.client.gui.click.component.Component;
import me.thekirkayt.client.gui.click.component.window.OptionWindow;

public abstract class SlotComponent<T>
extends Component<T> {
    protected static final int FILL_COLOR_DARK = -16777216;
    protected static final int OUTLINE_COLOR = -16777216;
    protected static final int FILL_COLOR_DEFAULT = -16777216;
    private OptionWindow optionWindow;

    public SlotComponent(T parent, double x, double y, double width, double height) {
        super(parent, x, y, width, height);
    }

    public OptionWindow getOptionWindow() {
        return this.optionWindow;
    }

    public void setOptionWindow(OptionWindow optionWindow) {
        this.optionWindow = optionWindow;
    }
}

