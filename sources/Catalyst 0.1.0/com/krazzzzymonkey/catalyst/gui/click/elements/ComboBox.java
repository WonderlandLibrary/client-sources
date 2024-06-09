// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.elements;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.listener.ComboBoxListener;
import java.util.ArrayList;
import com.krazzzzymonkey.catalyst.gui.click.base.Container;

public class ComboBox extends Container
{
    private /* synthetic */ boolean selected;
    private static final /* synthetic */ int[] lIlIIllI;
    public /* synthetic */ ArrayList<ComboBoxListener> listeners;
    private /* synthetic */ int selectedIndex;
    private /* synthetic */ String[] elements;
    
    static {
        lIIIIIIlll();
    }
    
    public String[] getElements() {
        return this.elements;
    }
    
    private static boolean lIIIIIlIll(final int llIIIlIIlllllll) {
        return llIIIlIIlllllll == 0;
    }
    
    public ArrayList<ComboBoxListener> getListeners() {
        return this.listeners;
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public int getSelectedIndex() {
        return this.selectedIndex;
    }
    
    public void setSelectedIndex(final int llIIIlIlIlIllll) {
        this.selectedIndex = llIIIlIlIlIllll;
        final short llIIIlIlIlIlllI = (short)this.listeners.iterator();
        while (lIIIIIlIII(((Iterator)llIIIlIlIlIlllI).hasNext() ? 1 : 0)) {
            final ComboBoxListener llIIIlIlIllIIll = ((Iterator<ComboBoxListener>)llIIIlIlIlIlllI).next();
            llIIIlIlIllIIll.onComboBoxSelectionChange(this);
            "".length();
            if (-"  ".length() >= 0) {
                return;
            }
        }
    }
    
    public ComboBox(final int llIIIllIIIIIIlI, final int llIIIlIlllllIIl, final int llIIIllIIIIIIII, final int llIIIlIllllIlll, final Component llIIIlIlllllllI, final String llIIIlIllllIlIl, final String... llIIIlIllllIIll) {
        super(llIIIllIIIIIIlI, llIIIlIlllllIIl, llIIIllIIIIIIII, llIIIlIllllIlll, ComponentType.COMBO_BOX, llIIIlIlllllllI, llIIIlIllllIlIl);
        this.listeners = new ArrayList<ComboBoxListener>();
        this.elements = llIIIlIllllIIll;
    }
    
    public void setElements(final String[] llIIIlIlIllllll) {
        this.elements = llIIIlIlIllllll;
        this.selectedIndex = ComboBox.lIlIIllI[1];
    }
    
    private static boolean lIIIIIlIII(final int llIIIlIlIIIIIIl) {
        return llIIIlIlIIIIIIl != 0;
    }
    
    public void addListeners(final ComboBoxListener llIIIlIlIIlIIll) {
        this.listeners.add(llIIIlIlIIlIIll);
        "".length();
    }
    
    private static void lIIIIIIlll() {
        (lIlIIllI = new int[3])[0] = " ".length();
        ComboBox.lIlIIllI[1] = ((96 + 101 - 147 + 80 ^ 46 + 52 + 16 + 46) & (0x1D ^ 0x26 ^ (0x7B ^ 0x62) ^ -" ".length()));
        ComboBox.lIlIIllI[2] = "  ".length();
    }
    
    private static boolean lIIIIIlIIl(final int llIIIlIlIIlIIII, final int llIIIlIlIIIllll) {
        return llIIIlIlIIlIIII == llIIIlIlIIIllll;
    }
    
    @Override
    public void onMousePress(final int llIIIlIllIlIIll, final int llIIIlIllIlIllI, final int llIIIlIllIlIIIl) {
        if (lIIIIIlIII(this.isMouseOver(llIIIlIllIlIIll, llIIIlIllIlIllI) ? 1 : 0)) {
            if (lIIIIIlIIl(llIIIlIllIlIIIl, ComboBox.lIlIIllI[0])) {
                int selected;
                if (lIIIIIlIll(this.selected ? 1 : 0)) {
                    selected = ComboBox.lIlIIllI[0];
                    "".length();
                    if ((("   ".length() ^ (0x7A ^ 0x71)) & (((0xDE ^ 0x93) & ~(0x5C ^ 0x11)) ^ (0xCF ^ 0xC7) ^ -" ".length())) != 0x0) {
                        return;
                    }
                }
                else {
                    selected = ComboBox.lIlIIllI[1];
                }
                this.selected = (selected != 0);
            }
            if (lIIIIIlIll(llIIIlIllIlIIIl)) {
                int llIIIlIllIllIll = this.getDimension().height + ComboBox.lIlIIllI[2];
                final String[] llIIIlIllIllIlI = this.getElements();
                int llIIIlIllIlllII = ComboBox.lIlIIllI[1];
                while (lIIIIIllII(llIIIlIllIlllII, llIIIlIllIllIlI.length)) {
                    if (lIIIIIlIIl(llIIIlIllIlllII, this.getSelectedIndex())) {
                        "".length();
                        if ("   ".length() != "   ".length()) {
                            return;
                        }
                    }
                    else if (lIIIIIllll(llIIIlIllIlIllI, llIIIlIllIllIll) && lIIIIlIIII(llIIIlIllIlIllI, llIIIlIllIllIll + Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT)) {
                        this.setSelectedIndex(llIIIlIllIlllII);
                        this.setSelected((boolean)(ComboBox.lIlIIllI[1] != 0));
                        "".length();
                        if (" ".length() >= "  ".length()) {
                            return;
                        }
                        break;
                    }
                    else {
                        llIIIlIllIllIll += Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT + ComboBox.lIlIIllI[2];
                    }
                    ++llIIIlIllIlllII;
                    "".length();
                    if (" ".length() >= "   ".length()) {
                        return;
                    }
                }
            }
        }
    }
    
    private static boolean lIIIIIllll(final int llIIIlIlIIIllII, final int llIIIlIlIIIlIll) {
        return llIIIlIlIIIllII >= llIIIlIlIIIlIll;
    }
    
    private static boolean lIIIIIllII(final int llIIIlIlIIIlIII, final int llIIIlIlIIIIlll) {
        return llIIIlIlIIIlIII < llIIIlIlIIIIlll;
    }
    
    public void setSelected(final boolean llIIIlIlIlIIIII) {
        this.selected = llIIIlIlIlIIIII;
        final long llIIIlIlIIlllIl = (long)this.listeners.iterator();
        while (lIIIIIlIII(((Iterator)llIIIlIlIIlllIl).hasNext() ? 1 : 0)) {
            final ComboBoxListener llIIIlIlIlIIIlI = ((Iterator<ComboBoxListener>)llIIIlIlIIlllIl).next();
            llIIIlIlIlIIIlI.onComboBoxSelectionChange(this);
            "".length();
            if (null != null) {
                return;
            }
        }
    }
    
    private static boolean lIIIIlIIII(final int llIIIlIlIIIIlII, final int llIIIlIlIIIIIll) {
        return llIIIlIlIIIIlII <= llIIIlIlIIIIIll;
    }
    
    public String getSelectedElement() {
        return this.elements[this.selectedIndex];
    }
}
