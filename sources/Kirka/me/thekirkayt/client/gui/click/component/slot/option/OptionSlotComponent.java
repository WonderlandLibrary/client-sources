/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.slot.option;

import java.util.ArrayList;
import java.util.List;
import me.thekirkayt.client.gui.click.component.slot.SlotComponent;
import me.thekirkayt.client.gui.click.component.window.OptionWindow;
import me.thekirkayt.client.option.Option;

public abstract class OptionSlotComponent<T extends Option>
extends SlotComponent<Option> {
    private List<OptionSlotComponent> optionList = new ArrayList<OptionSlotComponent>();

    public OptionSlotComponent(T parent, double x, double y, double width, double height) {
        super(parent, x, y, width, height);
    }

    public List<OptionSlotComponent> getOptionList() {
        return this.optionList;
    }

    @Override
    public T getParent() {
        return (T)((Option)super.getParent());
    }

    public void drag(int mouseX, int mouseY, double[] startOffset) {
        double xDifference = this.getX() - ((double)mouseX - startOffset[0]);
        double yDifference = this.getY() - ((double)mouseY - startOffset[1]);
        xDifference = Math.round(xDifference / 10.0) * 10L;
        yDifference = Math.round(yDifference / 10.0) * 10L;
        this.setX(this.getX() - xDifference);
        this.setY(this.getY() - yDifference);
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().drag((double)mouseX, (double)mouseY, startOffset);
        }
    }
}

