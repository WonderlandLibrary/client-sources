// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.alt;

import net.minecraft.client.gui.GuiScreen;
import gg.childtrafficking.smokex.gui.element.VAlignment;
import gg.childtrafficking.smokex.gui.element.elements.ButtonElement;
import gg.childtrafficking.smokex.gui.element.HAlignment;
import gg.childtrafficking.smokex.gui.element.elements.TextElement;
import gg.childtrafficking.smokex.gui.element.Element;
import gg.childtrafficking.smokex.gui.element.elements.RectElement;
import gg.childtrafficking.smokex.gui.BetterGuiScreen;

public class GuiAltManager extends BetterGuiScreen
{
    @Override
    public void initGui() {
        this.addElement(new RectElement("background", 0.0f, 0.0f, this.mc.displayWidth / 2.0f, this.mc.displayHeight / 2.0f, -15921377));
        this.addElement(new TextElement("header", 0.0f, 15.0f, "Alt Manager", -1)).setHAlignment(HAlignment.CENTER);
        this.addElement(new ButtonElement("login", 0.0f, 20.0f, "Login", -1)).setHAlignment(HAlignment.CENTER).setVAlignment(VAlignment.BOTTOM);
        this.addElement(new ButtonElement("addAlt", 120.0f, 20.0f, "Add Alt", -1)).setHAlignment(HAlignment.CENTER).setVAlignment(VAlignment.BOTTOM);
        this.addElement(new ButtonElement("directLogin", -120.0f, 20.0f, "Direct Login", -1)).setHAlignment(HAlignment.CENTER).setVAlignment(VAlignment.BOTTOM);
        this.addElement(new ButtonElement("back", 20.0f, 20.0f, "Back", -1)).setHAlignment(HAlignment.LEFT).setVAlignment(VAlignment.BOTTOM);
    }
    
    @Override
    public void elementClicked(final String identifier) {
        System.out.println("bruh bruh bruh");
        switch (identifier) {
            case "back": {
                this.mc.displayGuiScreen(null);
                break;
            }
        }
    }
}
