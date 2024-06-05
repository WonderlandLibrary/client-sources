// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui;

import java.util.Collection;
import net.minecraft.client.Minecraft;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import java.util.ArrayList;
import gg.childtrafficking.smokex.gui.element.Element;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class BetterGuiScreen extends GuiScreen
{
    private final List<Element> elements;
    
    public BetterGuiScreen() {
        this.elements = new ArrayList<Element>();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        for (final Element element : this.elements) {
            if (element.clickable && mouseX <= element.getRenderX() + element.getWidth() && mouseX >= element.getRenderX() && mouseY >= element.getRenderY() + element.getHeight() && mouseY <= element.getRenderY()) {
                element.hovered(partialTicks);
            }
        }
        double scale = 2.0f / RenderingUtils.getScaledFactor();
        GlStateManager.scale(scale, scale, scale);
        for (final Element element2 : this.elements) {
            element2.render(partialTicks);
        }
        scale = RenderingUtils.getScaledFactor() / 2.0f;
        GlStateManager.scale(scale, scale, scale);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            for (final Element element : this.elements) {
                if (element.clickable && mouseX <= element.getRenderX() + element.getWidth() && mouseX >= element.getRenderX() && mouseY >= element.getRenderY() && mouseY <= element.getRenderY() + element.getHeight()) {
                    this.elementClicked(element.getIdentifier());
                }
            }
        }
    }
    
    public void elementClicked(final String identifier) {
    }
    
    @Override
    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        this.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.width = width;
        this.height = height;
        this.clearElements();
        this.initGui();
    }
    
    public List<Element> getElements() {
        return this.elements;
    }
    
    public Element addElement(final Element element) {
        this.elements.add(element);
        return element;
    }
    
    public void addElements(final Collection<Element> e) {
        this.elements.addAll(e);
    }
    
    public Element getElement(final String identifier) {
        for (final Element element : this.elements) {
            if (element.getIdentifier().equalsIgnoreCase(identifier)) {
                return element;
            }
        }
        return null;
    }
    
    public void removeElement(final String identifier) {
        for (int i = 0; i < this.elements.size(); ++i) {
            if (this.elements.get(i).getIdentifier().equalsIgnoreCase(identifier)) {
                this.elements.remove(this.elements.get(i));
                --i;
            }
        }
    }
    
    public void clearElements() {
        this.elements.clear();
    }
}
