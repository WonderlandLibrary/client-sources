package me.swezedcode.client.gui.cgui.parts.vals;

import java.awt.Color;
import java.awt.Point;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.opengl.GL11;

import me.swezedcode.client.gui.cgui.component.Component;
import me.swezedcode.client.utils.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class RestrictedValuePart extends Component
{
    private NumberValue value;
    private boolean dragging;
    private double renderWidth;
    
    public RestrictedValuePart(final String title, final NumberValue value) {
        this.dragging = false;
        this.title = title;
        this.value = value;
    }
    
    @Override
    public void onUpdate(final int mouseX, final int mouseY, final float partialTicks) {
        double diff = Math.min(this.width, Math.max(0, mouseX - this.x));
        double min = this.value.getMin().doubleValue();
        double max = this.value.getMax().doubleValue();

        this.renderWidth = (this.width - 16) * (this.value.getValue().doubleValue() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0) {
                this.value.setValue(this.value.getMin());
            }
            else {
                double newValue = roundToPlace(diff / this.width * (max - min) + min, 2);
                this.value.setValue(newValue);
            }
        }
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY, final float partialTicks) {
        GL11.glPushMatrix();
        GL11.glScalef(1.1f, 1.1f, 1.1f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.value.getName() + " " + this.value.getValue(), (int)(this.x / 1.1 + 25.0) + 2, (int)(this.y / 1.1 + 3.8), -1);
        GL11.glPopMatrix();
        Gui.drawRect(this.x + 25  , this.y + 13, this.x + 170, this.y + 1 + this.height, new Color(0, 0, 0, 150).getRGB());
        Gui.drawRect(this.x + 25, this.y + 14, this.x + (int)this.renderWidth + 25, this.y + this.height, new Color(255, 128, 0).getRGB());

       
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final Point mouse = new Point(mouseX, mouseY);
        if (this.MouseIsInside().contains(mouse)) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.dragging = false;
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    private static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
