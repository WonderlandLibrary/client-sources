// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.clickgui.element.elements;

import me.kaktuswasser.client.clickgui.element.Element;
import me.kaktuswasser.client.utilities.NahrFont;
import me.kaktuswasser.client.utilities.RenderHelper;
import me.kaktuswasser.client.values.ModeValue;
import net.minecraft.client.Minecraft;

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
        RenderHelper.drawBorderedCorneredRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + this.getHeight(), 1.0f, 0x9971f442, this.open ? 0x9971f442 : -2145049307);
        if (this.isHovering(mouseX, mouseY)) {
            RenderHelper.drawRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0x8071f442);
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
