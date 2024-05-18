package me.swezedcode.client.gui.cgui.parts.vals;

import java.awt.Color;
import java.awt.Point;

import org.lwjgl.opengl.GL11;

import me.swezedcode.client.gui.cgui.component.Component;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class BooleanValuePart extends Component
{
    BooleanValue value;
    
    public BooleanValuePart(final String title, final BooleanValue value) {
        this.title = title;
        this.value = value;
        this.width = 200;
        this.height = 28;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY, final float partialTicks) {
        final Point mouse = new Point(mouseX, mouseY);
        final boolean hover = this.MouseIsInside().contains(mouse);
        Gui.drawRect(this.x + 40, this.y + 8, this.x + 25, this.y + this.height - 7, new Color(200, 200, 200, 50).getRGB());
        int e = 40;
        if(this.value.getValue() && e <= 40) {
        	e++;
        }else if(!this.value.getValue() && e >= 22) {
        	e--;
        }
        if (this.value.getValue()) {
        	 Gui.drawRect(this.x + 40, this.y + 8, this.x + 43, this.y + this.height - 7, new Color(255, 128, 0, 255).getRGB());
        } else {
        	 Gui.drawRect(this.x + 22, this.y + 8, this.x + 25, this.y + this.height - 7, new Color(255, 128, 0, 255).getRGB());
        }
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.title, this.x / 2 + 25, this.y / 2 + 4, -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void onUpdate(final int mouseX, final int mouseY, final float partialTicks) {
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
    }
}
