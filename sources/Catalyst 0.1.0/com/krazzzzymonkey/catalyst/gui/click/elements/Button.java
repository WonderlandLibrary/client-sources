// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.elements;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.listener.ComponentClickListener;
import java.util.ArrayList;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;

public class Button extends Component
{
    private /* synthetic */ Modules mod;
    private /* synthetic */ boolean enabled;
    public /* synthetic */ ArrayList<ComponentClickListener> listeners;
    private static final /* synthetic */ int[] llIlIIlI;
    
    @Override
    public void onMousePress(final int lIlllIIIIIllIII, final int lIlllIIIIIlIlll, final int lIlllIIIIIlIlII) {
        if (lIIlllIIll(lIlllIIIIIlIlII)) {
            return;
        }
        int enabled;
        if (lIIlllIlII(this.enabled ? 1 : 0)) {
            enabled = Button.llIlIIlI[1];
            "".length();
            if ("  ".length() == -" ".length()) {
                return;
            }
        }
        else {
            enabled = Button.llIlIIlI[0];
        }
        this.enabled = (enabled != 0);
        final boolean lIlllIIIIIlIIll = (boolean)this.listeners.iterator();
        while (lIIlllIIll(((Iterator)lIlllIIIIIlIIll).hasNext() ? 1 : 0)) {
            final ComponentClickListener lIlllIIIIIllIlI = ((Iterator<ComponentClickListener>)lIlllIIIIIlIIll).next();
            lIlllIIIIIllIlI.onComponenetClick(this, lIlllIIIIIlIlII);
            "".length();
            if ("   ".length() == 0) {
                return;
            }
        }
    }
    
    private static void lIIlllIIlI() {
        (llIlIIlI = new int[2])[0] = ((93 + 7 + 18 + 10 ^ 21 + 103 - 68 + 88) & (0xE ^ 0x7D ^ (0x8 ^ 0x6B) ^ -" ".length()));
        Button.llIlIIlI[1] = " ".length();
    }
    
    public Button(final int lIlllIIIlIIlIIl, final int lIlllIIIlIIIIIl, final int lIlllIIIlIIIlll, final int lIlllIIIlIIIllI, final Component lIlllIIIlIIIlIl, final String lIlllIIIlIIIlII) {
        super(lIlllIIIlIIlIIl, lIlllIIIlIIIIIl, lIlllIIIlIIIlll, lIlllIIIlIIIllI, ComponentType.BUTTON, lIlllIIIlIIIlIl, lIlllIIIlIIIlII);
        this.listeners = new ArrayList<ComponentClickListener>();
        this.enabled = (Button.llIlIIlI[0] != 0);
    }
    
    public Modules getMod() {
        return this.mod;
    }
    
    private static boolean lIIlllIIll(final int lIlllIIIIIIIIIl) {
        return lIlllIIIIIIIIIl != 0;
    }
    
    public void addListeners(final ComponentClickListener lIlllIIIIlIIIIl) {
        this.listeners.add(lIlllIIIIlIIIIl);
        "".length();
    }
    
    public ArrayList<ComponentClickListener> getListeners() {
        return this.listeners;
    }
    
    private static boolean lIIlllIlII(final int lIllIllllllllll) {
        return lIllIllllllllll == 0;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean lIlllIIIIIIlIll) {
        this.enabled = lIlllIIIIIIlIll;
    }
    
    static {
        lIIlllIIlI();
    }
    
    public Button(final int lIlllIIIIlIlIll, final int lIlllIIIIllIIlI, final int lIlllIIIIlIlIIl, final int lIlllIIIIlIlIII, final Component lIlllIIIIlIIlll, final String lIlllIIIIlIIllI, final Modules lIlllIIIIlIIlIl) {
        super(lIlllIIIIlIlIll, lIlllIIIIllIIlI, lIlllIIIIlIlIIl, lIlllIIIIlIlIII, ComponentType.BUTTON, lIlllIIIIlIIlll, lIlllIIIIlIIllI);
        this.listeners = new ArrayList<ComponentClickListener>();
        this.enabled = (Button.llIlIIlI[0] != 0);
        this.mod = lIlllIIIIlIIlIl;
    }
}
