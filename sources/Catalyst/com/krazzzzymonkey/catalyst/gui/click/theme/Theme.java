// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme;

import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.model.obj.OBJModel;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import java.util.HashMap;

public class Theme
{
    private /* synthetic */ HashMap<ComponentType, ComponentRenderer> rendererHashMap;
    private static final /* synthetic */ int[] llIIlllI;
    private /* synthetic */ int frameHeight;
    private /* synthetic */ String themeName;
    public /* synthetic */ FontRenderer fontRenderer;
    
    public HashMap<ComponentType, ComponentRenderer> getRenderer() {
        return this.rendererHashMap;
    }
    
    public String getThemeName() {
        return this.themeName;
    }
    
    static {
        lIIllIllII();
    }
    
    private static void lIIllIllII() {
        (llIIlllI = new int[1])[0] = (0x2D ^ 0x3F ^ (0x63 ^ 0x7E));
    }
    
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }
    
    public void addRenderer(final ComponentType lIlllIIlIllIIll, final ComponentRenderer lIlllIIlIllIIlI) {
        this.rendererHashMap.put(lIlllIIlIllIIll, lIlllIIlIllIIlI);
        "".length();
    }
    
    public Theme(final String lIlllIIlIlllIll) {
        this.rendererHashMap = new HashMap<ComponentType, ComponentRenderer>();
        this.frameHeight = Theme.llIIlllI[0];
        this.themeName = lIlllIIlIlllIll;
    }
    
    public int getFrameHeight() {
        return this.frameHeight;
    }
}
