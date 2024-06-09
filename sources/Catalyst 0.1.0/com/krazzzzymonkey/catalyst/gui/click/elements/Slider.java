// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.elements;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import org.lwjgl.input.Mouse;
import com.krazzzzymonkey.catalyst.gui.click.ClickGui;
import com.krazzzzymonkey.catalyst.gui.click.listener.SliderChangeListener;
import java.util.ArrayList;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;

public class Slider extends Component
{
    public /* synthetic */ double max;
    public /* synthetic */ double percent;
    public /* synthetic */ double min;
    public /* synthetic */ boolean dragging;
    private static final /* synthetic */ int[] lIlIIlI;
    private /* synthetic */ ArrayList<SliderChangeListener> listeners;
    public /* synthetic */ double value;
    
    public double getValue() {
        return this.value;
    }
    
    public void setPercent(final double llIlllIlIllIIII) {
        this.percent = llIlllIlIllIIII;
    }
    
    public double getMin() {
        return this.min;
    }
    
    @Override
    public void onMouseRelease(final int llIlllIllllIIll, final int llIlllIllllIIlI, final int llIlllIllllIIIl) {
        this.dragging = (Slider.lIlIIlI[0] != 0);
    }
    
    public ArrayList<SliderChangeListener> getListeners() {
        return this.listeners;
    }
    
    static {
        lIIIIIlII();
    }
    
    public void setDragging(final boolean llIlllIllIlIllI) {
        this.dragging = llIlllIllIlIllI;
    }
    
    private double round(final double llIlllIlIlIlIlI, final int llIlllIlIlIIlIl) {
        final double llIlllIlIlIlIII = Math.pow(10.0, llIlllIlIlIIlIl);
        final double llIlllIlIlIIlll = llIlllIlIlIlIlI * llIlllIlIlIlIII;
        return Math.round(llIlllIlIlIIlll) / llIlllIlIlIlIII;
    }
    
    @Override
    public void onMouseDrag(int llIlllIlllIIIIl, final int llIlllIlllIIIll) {
        if (lIIIIIlIl(this.dragging ? 1 : 0)) {
            llIlllIlllIIIIl -= this.getX();
            final int llIlllIlllIIllI = (int)this.getDimension().getWidth();
            this.percent = llIlllIlllIIIIl / (double)llIlllIlllIIllI;
            this.value = this.round((this.max - this.min) * this.percent + this.min, Slider.lIlIIlI[3]);
            this.fireListeners();
        }
    }
    
    public void setMax(final double llIlllIllIIIIlI) {
        this.max = llIlllIllIIIIlI;
    }
    
    private static boolean lIIIIIllI(final int llIlllIlIIllIll) {
        return llIlllIlIIllIll == 0;
    }
    
    private static boolean lIIIIIlll(final int llIlllIlIlIIIII, final int llIlllIlIIlllll) {
        return llIlllIlIlIIIII <= llIlllIlIIlllll;
    }
    
    @Override
    public void onMousePress(int llIlllIllllllll, final int llIllllIIIIIIll, final int llIllllIIIIIIlI) {
        llIlllIllllllll -= (this.getX() != 0);
        final int llIllllIIIIIIIl = (int)this.getDimension().getWidth();
        this.percent = (double)(llIlllIllllllll ? 1 : 0) / llIllllIIIIIIIl;
        this.value = this.round((this.max - this.min) * this.percent + this.min, Slider.lIlIIlI[3]);
        this.dragging = (Slider.lIlIIlI[4] != 0);
        this.fireListeners();
    }
    
    public void setValue(final double llIlllIlIlllIIl) {
        this.value = llIlllIlIlllIIl;
    }
    
    private void fireListeners() {
        final Exception llIlllIllllIlll = (Exception)this.listeners.iterator();
        while (lIIIIIlIl(((Iterator)llIlllIllllIlll).hasNext() ? 1 : 0)) {
            final SliderChangeListener llIlllIlllllIlI = ((Iterator<SliderChangeListener>)llIlllIllllIlll).next();
            llIlllIlllllIlI.onSliderChange(this);
            "".length();
            if (-" ".length() > ((0x2E ^ 0x37) & ~(0x9F ^ 0x86))) {
                return;
            }
        }
    }
    
    private static void lIIIIIlII() {
        (lIlIIlI = new int[5])[0] = ((0x7E ^ 0x59) & ~(0xAE ^ 0x89));
        Slider.lIlIIlI[1] = (123 + 156 - 239 + 163 ^ 37 + 131 - 106 + 113);
        Slider.lIlIIlI[2] = (0xAA ^ 0xBE);
        Slider.lIlIIlI[3] = "  ".length();
        Slider.lIlIIlI[4] = " ".length();
    }
    
    public double getMax() {
        return this.max;
    }
    
    @Override
    public void onUpdate() {
        final int[] llIlllIlllIllII = ClickGui.mouse;
        this.dragging = (Slider.lIlIIlI[0] != 0);
        if (lIIIIIlIl(this.dragging ? 1 : 0) && lIIIIIllI(this.isMouseOver(llIlllIlllIllII[Slider.lIlIIlI[0]], llIlllIlllIllII[Slider.lIlIIlI[4]]) ? 1 : 0)) {
            if (lIIIIIlll(llIlllIlllIllII[Slider.lIlIIlI[0]], this.getX())) {
                this.percent = 0.0;
                this.value = this.min;
                this.fireListeners();
                "".length();
                if (null != null) {
                    return;
                }
            }
            else {
                this.percent = 1.0;
                this.value = this.max;
                this.fireListeners();
            }
        }
        if (lIIIIIlIl(Mouse.isButtonDown(Slider.lIlIIlI[0]) ? 1 : 0) && lIIIIIlIl(this.isMouseOver(llIlllIlllIllII[Slider.lIlIIlI[0]], llIlllIlllIllII[Slider.lIlIIlI[4]]) ? 1 : 0)) {
            this.dragging = (Slider.lIlIIlI[4] != 0);
        }
    }
    
    public double getPercent() {
        return this.percent;
    }
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public Slider(final double llIllllIIIllIIl, final double llIllllIIIllIII, final double llIllllIIIlIIIl, final Component llIllllIIIlIIII, final String llIllllIIIIllll) {
        super(Slider.lIlIIlI[0], Slider.lIlIIlI[0], Slider.lIlIIlI[1], Slider.lIlIIlI[2], ComponentType.SLIDER, llIllllIIIlIIII, llIllllIIIIllll);
        this.dragging = (Slider.lIlIIlI[0] != 0);
        this.percent = 0.0;
        this.listeners = new ArrayList<SliderChangeListener>();
        this.min = llIllllIIIllIIl;
        this.max = llIllllIIIllIII;
        this.value = llIllllIIIlIIIl;
        this.percent = llIllllIIIlIIIl / (llIllllIIIllIII - llIllllIIIllIIl);
    }
    
    private static boolean lIIIIIlIl(final int llIlllIlIIlllIl) {
        return llIlllIlIIlllIl != 0;
    }
    
    public void setMin(final double llIlllIllIIlIll) {
        this.min = llIlllIllIIlIll;
    }
    
    public void addListener(final SliderChangeListener llIllllIIIIlIIl) {
        this.listeners.add(llIllllIIIIlIIl);
        "".length();
    }
}
