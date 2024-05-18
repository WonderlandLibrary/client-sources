// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.elements;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.listener.CheckButtonClickListener;
import java.util.ArrayList;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;

public class CheckButton extends Component
{
    private static final /* synthetic */ int[] lIIllIII;
    private /* synthetic */ ModeValue modeValue;
    private /* synthetic */ boolean enabled;
    public /* synthetic */ ArrayList<CheckButtonClickListener> listeners;
    
    private static void llllIlIII() {
        (lIIllIII = new int[2])[0] = ((82 + 59 - 129 + 136 ^ 33 + 132 - 154 + 147) & (0xA ^ 0x25 ^ (0x67 ^ 0x42) ^ -" ".length()));
        CheckButton.lIIllIII[1] = " ".length();
    }
    
    public CheckButton(final int llIIlIIlIIIlIlI, final int llIIlIIlIIlIIlI, final int llIIlIIlIIlIIIl, final int llIIlIIlIIIIlll, final Component llIIlIIlIIIllll, final String llIIlIIlIIIIlIl, final boolean llIIlIIlIIIIlII, final ModeValue llIIlIIlIIIllII) {
        super(llIIlIIlIIIlIlI, llIIlIIlIIlIIlI, llIIlIIlIIlIIIl, llIIlIIlIIIIlll, ComponentType.CHECK_BUTTON, llIIlIIlIIIllll, llIIlIIlIIIIlIl);
        this.listeners = new ArrayList<CheckButtonClickListener>();
        this.enabled = (CheckButton.lIIllIII[0] != 0);
        this.modeValue = null;
        this.enabled = llIIlIIlIIIIlII;
        this.modeValue = llIIlIIlIIIllII;
    }
    
    static {
        llllIlIII();
    }
    
    private static boolean llllIlIIl(final Object llIIlIIIlIllIII) {
        return llIIlIIIlIllIII != null;
    }
    
    public void addListeners(final CheckButtonClickListener llIIlIIIllIlIlI) {
        this.listeners.add(llIIlIIIllIlIlI);
        "".length();
    }
    
    private static boolean llllIlIlI(final int llIIlIIIlIllIll, final int llIIlIIIlIllIlI) {
        return llIIlIIIlIllIll < llIIlIIIlIllIlI;
    }
    
    public ModeValue getModeValue() {
        return this.modeValue;
    }
    
    @Override
    public void onMousePress(final int llIIlIIIllllIlI, final int llIIlIIIllllIIl, final int llIIlIIIllllIII) {
        if (llllIlIIl(this.modeValue)) {
            final boolean llIIlIIIlllIllI = (Object)this.modeValue.getModes();
            final int length = (llIIlIIIlllIllI ? 1 : 0).length;
            char llIIlIIIlllIlII = (char)CheckButton.lIIllIII[0];
            while (llllIlIlI(llIIlIIIlllIlII, length)) {
                final Mode llIIlIIIlllllIl = llIIlIIIlllIllI[llIIlIIIlllIlII];
                llIIlIIIlllllIl.setToggled((boolean)(CheckButton.lIIllIII[0] != 0));
                ++llIIlIIIlllIlII;
                "".length();
                if ("   ".length() <= 0) {
                    return;
                }
            }
            this.enabled = (CheckButton.lIIllIII[1] != 0);
            "".length();
            if ("   ".length() <= 0) {
                return;
            }
        }
        else {
            int enabled;
            if (llllIlIll(this.enabled ? 1 : 0)) {
                enabled = CheckButton.lIIllIII[1];
                "".length();
                if (null != null) {
                    return;
                }
            }
            else {
                enabled = CheckButton.lIIllIII[0];
            }
            this.enabled = (enabled != 0);
        }
        final boolean llIIlIIIlllIllI = (boolean)this.listeners.iterator();
        while (llllIllII(((Iterator)llIIlIIIlllIllI).hasNext() ? 1 : 0)) {
            final CheckButtonClickListener llIIlIIIlllllII = ((Iterator<CheckButtonClickListener>)llIIlIIIlllIllI).next();
            llIIlIIIlllllII.onCheckButtonClick(this);
            "".length();
            if (null != null) {
                return;
            }
        }
    }
    
    private static boolean llllIllII(final int llIIlIIIlIlIllI) {
        return llIIlIIIlIlIllI != 0;
    }
    
    public ArrayList<CheckButtonClickListener> getListeners() {
        return this.listeners;
    }
    
    private static boolean llllIlIll(final int llIIlIIIlIlIlII) {
        return llIIlIIIlIlIlII == 0;
    }
    
    public void setEnabled(final boolean llIIlIIIllIIIII) {
        this.enabled = llIIlIIIllIIIII;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
}
