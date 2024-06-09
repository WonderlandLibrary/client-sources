// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.clickgui.element.elements;

import net.andrewsnetwork.icarus.utilities.NahrFont;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.minecraft.client.Minecraft;
import net.andrewsnetwork.icarus.values.ModeValue;
import net.andrewsnetwork.icarus.clickgui.element.Element;

public class ElementDropdownMenu extends Element
{
    protected ModeValue value;
    protected boolean open;
    protected float textWidth;
    
    public ElementDropdownMenu(final ModeValue value) {
        this.value = value;
        this.open = false;
    }
    
    public ModeValue getValue() {
        return this.value;
    }
    
    public void setValue(final ModeValue value) {
        this.value = value;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.25f, 0.75f);
            this.open = !this.open;
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float button) {
        RenderHelper.drawBorderedCorneredRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + this.getHeight(), 1.0f, Integer.MIN_VALUE, this.open ? -2137603890 : -2145049307);
        if (this.isHovering(mouseX, mouseY)) {
            RenderHelper.drawRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + this.getHeight(), 1627389951);
        }
        final String text = RenderHelper.getPrettyName(this.value.getName().replace(this.value.getModule().getName().toLowerCase(), "").replace("_", ""), " ");
        this.textWidth = RenderHelper.getNahrFont().getStringWidth(text);
        RenderHelper.getNahrFont().drawString(text, this.getX() + this.getWidth() / 2 - RenderHelper.getNahrFont().getStringWidth(text) / 2.0f, this.getY() + this.getHeight() / 4 - 2, NahrFont.FontType.SHADOW_THIN, -1, -16777216);
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() + 4 && mouseY <= this.getY() + this.getHeight();
    }
    
    public float getTextWidth() {
        return this.textWidth;
    }
    
    public void setTextWidth(final float textWidth) {
        this.textWidth = textWidth;
    }
}
