// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.ui.click.component.slot;

import net.minecraft.client.triton.ui.click.component.Component;
import net.minecraft.client.triton.ui.click.component.window.OptionWindow;

public abstract class SlotComponent<T> extends Component<T>
{
    protected static final int FILL_COLOR_DARK = -14540254;
    protected static final int OUTLINE_COLOR = -15658735;
    protected static final int FILL_COLOR_DEFAULT = -13421773;
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
