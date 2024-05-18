// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.base;

import com.krazzzzymonkey.catalyst.gui.click.ClickGui;

public class Component extends Interactable
{
    private /* synthetic */ String text;
    private static final /* synthetic */ int[] lIllIlll;
    private /* synthetic */ ComponentType componentType;
    private /* synthetic */ Component component;
    public /* synthetic */ int height;
    
    public Component getComponent() {
        return this.component;
    }
    
    public void onUpdate() {
    }
    
    public ComponentType getComponentType() {
        return this.componentType;
    }
    
    public void render(final int llIIIIlIIIIIIlI, final int llIIIIIlllllllI) {
        ClickGui.getTheme().getRenderer().get(this.componentType).drawComponent(this, llIIIIlIIIIIIlI, llIIIIIlllllllI);
    }
    
    public void setText(final String llIIIIIlllIIlII) {
        this.text = llIIIIIlllIIlII;
    }
    
    static {
        lIIIlIIlIl();
    }
    
    public String getText() {
        return this.text;
    }
    
    private static void lIIIlIIlIl() {
        (lIllIlll = new int[1])[0] = ((0x47 ^ 0x1E) & ~(0x70 ^ 0x29));
    }
    
    public Component(final int llIIIIlIIIIllIl, final int llIIIIlIIIlIlII, final int llIIIIlIIIIlIll, final int llIIIIlIIIIlIlI, final ComponentType llIIIIlIIIlIIIl, final Component llIIIIlIIIIlIII, final String llIIIIlIIIIIlll) {
        super(llIIIIlIIIIllIl, llIIIIlIIIlIlII, llIIIIlIIIIlIll, llIIIIlIIIIlIlI);
        this.height = Component.lIllIlll[0];
        this.componentType = llIIIIlIIIlIIIl;
        this.component = llIIIIlIIIIlIII;
        this.text = llIIIIlIIIIIlll;
    }
    
    public void setComponentType(final ComponentType llIIIIIllllIlII) {
        this.componentType = llIIIIIllllIlII;
    }
    
    public void setComponent(final Component llIIIIIlllIlIll) {
        this.component = llIIIIIlllIlIll;
    }
}
