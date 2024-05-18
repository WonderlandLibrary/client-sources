// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.base;

import java.util.Iterator;
import java.util.ArrayList;

public class Container extends Component
{
    private /* synthetic */ ArrayList<Component> components;
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    private static boolean lIIlIlII(final int lllIlIIlIlIIlll) {
        return lllIlIIlIlIIlll != 0;
    }
    
    public void addComponent(final Component lllIlIIllIIIIlI) {
        this.components.add(lllIlIIllIIIIlI);
        "".length();
    }
    
    public Container(final int lllIlIIllIlIlII, final int lllIlIIllIIlIll, final int lllIlIIllIlIIlI, final int lllIlIIllIlIIIl, final ComponentType lllIlIIllIIlIII, final Component lllIlIIllIIIlll, final String lllIlIIllIIlllI) {
        super(lllIlIIllIlIlII, lllIlIIllIIlIll, lllIlIIllIlIIlI, lllIlIIllIlIIIl, lllIlIIllIIlIII, lllIlIIllIIIlll, lllIlIIllIIlllI);
        this.components = new ArrayList<Component>();
    }
    
    public void renderChildren(final int lllIlIIlIllIIlI, final int lllIlIIlIlIlllI) {
        final short lllIlIIlIlIllIl = (short)this.getComponents().iterator();
        while (lIIlIlII(((Iterator)lllIlIIlIlIllIl).hasNext() ? 1 : 0)) {
            final Component lllIlIIlIllIlII = ((Iterator<Component>)lllIlIIlIlIllIl).next();
            lllIlIIlIllIlII.render(lllIlIIlIllIIlI, lllIlIIlIlIlllI);
            "".length();
            if (null != null) {
                return;
            }
        }
    }
    
    public void removeCompoent(final Component lllIlIIlIlllIlI) {
        this.components.remove(lllIlIIlIlllIlI);
        "".length();
    }
}
