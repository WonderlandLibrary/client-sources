// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.clickgui.element.elements;

import net.andrewsnetwork.icarus.utilities.NahrFont;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.minecraft.client.Minecraft;
import net.andrewsnetwork.icarus.clickgui.Panel;
import net.andrewsnetwork.icarus.clickgui.element.Element;

public class ElementStartMenuButton extends Element
{
    protected float limit;
    protected Panel panel;
    
    public ElementStartMenuButton(final Panel panel) {
        this.panel = panel;
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.25f, 0.75f);
            this.panel.setVisible(!this.panel.isVisible());
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float button) {
        if (!this.panel.getToggled() || this.limit > this.getY()) {
            return;
        }
        RenderHelper.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight() - 4, this.panel.isVisible() ? -2137603890 : -2145049307);
        if (this.isHovering(mouseX, mouseY)) {
            RenderHelper.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight() - 4, 1627389951);
        }
        RenderHelper.getNahrFont().drawString(this.panel.getTitle(), this.getX() + this.getWidth() / 2 - RenderHelper.getNahrFont().getStringWidth(this.panel.getTitle()) / 2.0f, this.getY() + this.getHeight() / 4 - 4, NahrFont.FontType.SHADOW_THIN, -1, -16777216);
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() + 4 && mouseY <= this.getY() + this.getHeight();
    }
    
    public float getLimit() {
        return this.limit;
    }
    
    public void setLimit(final float limit) {
        this.limit = limit;
    }
}
