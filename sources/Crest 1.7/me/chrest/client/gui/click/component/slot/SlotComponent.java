// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.gui.click.component.slot;

import me.chrest.client.gui.click.component.window.OptionWindow;
import me.chrest.client.gui.click.component.Component;

public abstract class SlotComponent<T> extends Component<T>
{
    protected static final int FILL_COLOR_DARK = -40865;
    protected static final int OUTLINE_COLOR = -1710619;
    protected static final int FILL_COLOR_DEFAULT = -1710619;
    private OptionWindow optionWindow;
    
    public SlotComponent(final T parent, final double x, final double y, final double width, final double height) {
        super(parent, x, y, width, height);
    }
    
    public OptionWindow getOptionWindow() {
        return this.optionWindow;
    }
    
    public void setOptionWindow(final OptionWindow optionWindow) {
        this.optionWindow = optionWindow;
    }
}
