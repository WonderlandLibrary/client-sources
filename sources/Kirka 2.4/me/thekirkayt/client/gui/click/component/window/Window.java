/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.window;

import java.util.ArrayList;
import java.util.List;
import me.thekirkayt.client.gui.click.component.Component;
import me.thekirkayt.client.gui.click.component.slot.SlotComponent;
import me.thekirkayt.client.gui.click.component.window.Handle;

public abstract class Window<T>
extends Component {
    private List<SlotComponent> slotList = new ArrayList<SlotComponent>();
    private Handle handle;
    private double[] startOffset;
    private boolean extended;
    private boolean dragging;

    public Window(T parent, double x, double y, double width, double height, Handle handle) {
        super(parent, x, y, width, height);
        this.handle = handle;
    }

    @Override
    public abstract void draw(int var1, int var2);

    public List<SlotComponent> getSlotList() {
        return this.slotList;
    }

    @Override
    public T getParent() {
        return super.getParent();
    }

    public Handle getHandle() {
        return this.handle;
    }

    public double[] getStartOffset() {
        return this.startOffset;
    }

    public boolean isExtended() {
        return this.extended;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public void setStartOffset(double[] is) {
        this.startOffset = is;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
}

