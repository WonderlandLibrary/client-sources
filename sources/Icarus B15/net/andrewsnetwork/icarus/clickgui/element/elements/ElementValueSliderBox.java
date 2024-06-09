// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.clickgui.element.elements;

import net.andrewsnetwork.icarus.utilities.NahrFont;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.minecraft.client.Minecraft;
import java.util.Iterator;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.clickgui.element.Element;

public class ElementValueSliderBox extends Element
{
    protected boolean decompilerFucker;
    protected Value value;
    protected String text;
    protected boolean drag;
    protected boolean set;
    protected float textWidth;
    protected float maxWidth;
    
    public ElementValueSliderBox(final Value value) {
        this.decompilerFucker = true;
        this.set = true;
        this.value = value;
        for (final Module module : Icarus.getModuleManager().getModules()) {
            if (value.getName().startsWith(String.valueOf(module.getName().toLowerCase()) + "_")) {
                this.text = value.getName().substring(module.getName().length() + 1);
            }
        }
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.x + 1 && mouseX <= this.x + this.width - 1 && mouseY >= this.y + 5 && mouseY <= this.y + this.height - 1;
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY) && this.value.getValue() instanceof Boolean) {
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 0.75f);
            this.value.setValue(this.value.getValue());
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            this.drag = false;
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float button) {
        if (this.decompilerFucker) {
            OutputStreamWriter request = new OutputStreamWriter(System.out);
            Label_0046: {
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0046;
                }
                finally {
                    request = null;
                }
                request = null;
            }
            this.decompilerFucker = false;
        }
        this.setTextWidth(RenderHelper.getNahrFont().getStringWidth(this.text));
        if (this.value.getValue() instanceof Boolean) {
            RenderHelper.drawRect(this.x + 1, this.y + 5, this.x + this.width - 1, this.y + this.height - 1, ((boolean)this.value.getValue()) ? -862535474 : -869980891);
            RenderHelper.drawRect(this.x, this.y + 4, this.x + 1, this.y + this.height, -872415232);
            RenderHelper.drawRect(this.x, this.y + 4, this.x + this.width, this.y + 5, -872415232);
            RenderHelper.drawRect(this.x + this.width - 1, this.y + 4, this.x + this.width, this.y + this.height, -872415232);
            RenderHelper.drawRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, -872415232);
            if (this.isHovering(mouseX, mouseY)) {
                RenderHelper.drawRect(this.x + 1, this.y + 5, this.x + this.width - 1, this.y + this.height - 1, -2130706433);
            }
            RenderHelper.getNahrFont().drawString(this.text, this.x + 18, this.y + 1, NahrFont.FontType.SHADOW_THIN, -1, -16777216);
        }
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public Value getValue() {
        return this.value;
    }
    
    public void setValue(final Value value) {
        this.value = value;
    }
    
    public boolean isDrag() {
        return this.drag;
    }
    
    public void setDrag(final boolean drag) {
        this.drag = drag;
    }
    
    public float getTextWidth() {
        return this.textWidth;
    }
    
    public void setTextWidth(final float textWidth) {
        this.textWidth = textWidth;
    }
    
    public float getMaxWidth() {
        return this.maxWidth;
    }
    
    public void setMaxWidth(final float maxWidth) {
        this.maxWidth = maxWidth;
    }
}
