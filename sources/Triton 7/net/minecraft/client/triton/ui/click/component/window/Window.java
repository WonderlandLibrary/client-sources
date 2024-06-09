// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.ui.click.component.window;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.triton.ui.click.component.Component;
import net.minecraft.client.triton.ui.click.component.slot.SlotComponent;

public abstract class Window<T> extends Component
{
    private List<SlotComponent> slotList;
    private Handle handle;
    private double[] startOffset;
    private boolean extended;
    private boolean dragging;
    
    public Window(final T parent, final double x, final double y, final double width, final double height, final Handle handle) {
        super(parent, x, y, width, height);
        this.slotList = new ArrayList<SlotComponent>();
        this.handle = handle;
    }
    
    @Override
    public abstract void draw(final int p0, final int p1);
    
    public List<SlotComponent> getSlotList() {
        return this.slotList;
    }
    
    @Override
    public T getParent() {
        return (T) super.getParent();
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
    
    public void setStartOffset(final double[] is) {
        this.startOffset = is;
    }
    
    public void setExtended(final boolean extended) {
        this.extended = extended;
    }
    
    public void setDragging(final boolean dragging) {
        this.dragging = dragging;
    }
}
