// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.clickgui.element.elements;

import net.andrewsnetwork.icarus.utilities.NahrFont;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.minecraft.client.Minecraft;
import net.andrewsnetwork.icarus.clickgui.element.Element;

public class ElementActionButton extends Element
{
    protected ActionType type;
    protected String title;
    
    public ElementActionButton(final String title, final ActionType type) {
        this.title = title;
        this.type = type;
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.onAction();
        }
    }
    
    public void onAction() {
        if (this.type == ActionType.SHUTDOWN) {
            Minecraft.getMinecraft().shutdown();
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float button) {
        RenderHelper.drawBorderedCorneredRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight() - 4, 1.0f, Integer.MIN_VALUE, -2145049307);
        if (this.isHovering(mouseX, mouseY)) {
            RenderHelper.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight() - 4, -2130706433);
        }
        RenderHelper.getNahrFont().drawString(this.title, this.getX() + this.getWidth() / 2 - RenderHelper.getNahrFont().getStringWidth(this.title) / 2.0f + 6.0f, this.getY() + this.getHeight() / 4 - 4, NahrFont.FontType.SHADOW_THIN, -1, -16777216);
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() + 4 && mouseY <= this.getY() + this.getHeight();
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public ActionType getType() {
        return this.type;
    }
    
    public enum ActionType
    {
        SHUTDOWN("SHUTDOWN", 0);
        
        private ActionType(final String s, final int n) {
        }
    }
}
