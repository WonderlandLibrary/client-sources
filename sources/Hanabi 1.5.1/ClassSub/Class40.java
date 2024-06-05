package ClassSub;

import cn.Hanabi.value.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.awt.*;

public class Class40 extends Class48<Value>
{
    private boolean isValueChanging;
    
    
    public Class40(final Value value) {
        super(0, 0, 0, 0, value);
        this.isValueChanging = false;
    }
    
    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)this.x, (float)this.y, 0.0f);
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        final Rectangle rectangle = new Rectangle(this.x, this.y, this.getWidth(), this.getHeight());
        GL11.glTranslatef(2.0f, 2.0f, 0.0f);
        final int FONT_HEIGHT = this.fontRenderer.FONT_HEIGHT;
        Class145.setColor(Color.white);
        this.fontRenderer.drawString(this.getValue().getValueName().split("_")[1], 0, 0, Class145.toRGBA(Color.white));
        String s = null;
        if (this.getValue().getValueState() instanceof Integer || this.getValue().getValueState() instanceof Long) {
            s = ((Class48<Value<Object>>)this).getValue().getValueState().toString();
        }
        else if (this.getValue().getValueState() instanceof Float || this.getValue().getValueState() instanceof Double) {
            s = String.format("%.2f", ((Class48<Value<Number>>)this).getValue().getValueState());
        }
        if (s != null) {
            Class145.setColor(Color.white);
            this.fontRenderer.drawString(s, this.getWidth() - this.fontRenderer.getStringWidth(s) - 3, 0, Class145.toRGBA(Color.white));
        }
        Class145.setColor(Class8.PANEL_MAIN_COLOR);
        GL11.glLineWidth(0.9f);
        final double n = (((Class48<Value<Number>>)this).getValue().getValueState().doubleValue() - ((Class48<Value<Number>>)this).getValue().getValueMin().doubleValue()) / (((Class48<Value<Number>>)this).getValue().getValueMax().doubleValue() - ((Class48<Value<Number>>)this).getValue().getValueMin().doubleValue());
        Class145.setColor(Class8.PANEL_SECONDARY_COLOR);
        Gui.drawRect(0, FONT_HEIGHT + 2, (int)(rectangle.width * n) - 2, rectangle.height - 4, Class8.PANEL_SECONDARY_COLOR.getRGB());
        Class145.drawRect(2, 0, FONT_HEIGHT + 2, (int)(rectangle.width * n) - 2, rectangle.height - 4, Class8.PANEL_MAIN_COLOR.getRGB());
        GL11.glPopMatrix();
    }
    
    @Override
    public boolean onMouseClick(final int n, final int n2, final int n3) {
        if (new Rectangle(this.getX(), this.fontRenderer.FONT_HEIGHT + 2 + this.getY(), this.getWidth(), this.getHeight() - this.fontRenderer.FONT_HEIGHT).contains(n, n2) && n3 == 0) {
            if (Mouse.isButtonDown(0) && !this.isValueChanging) {
                this.isValueChanging = true;
            }
            else if (!Mouse.isButtonDown(0) && this.isValueChanging) {
                this.isValueChanging = false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void update() {
        if (this.isValueChanging) {
            final Point calculateMouseLocation = Class170.calculateMouseLocation();
            if (!Mouse.isButtonDown(0) || !new Rectangle(this.getX(), 0, this.getWidth(), Integer.MAX_VALUE).contains(calculateMouseLocation)) {
                this.isValueChanging = false;
                return;
            }
            calculateMouseLocation.translate(-this.getX(), -this.getY());
            final double n = Math.round((((Class48<Value<Number>>)this).getValue().getValueMin().doubleValue() - calculateMouseLocation.x / this.getWidth() * (((Class48<Value<Number>>)this).getValue().getValueMin().doubleValue() - ((Class48<Value<Number>>)this).getValue().getValueMax().doubleValue())) * (1.0 / this.getValue().getSteps())) / (1.0 / this.getValue().getSteps());
            if (this.getValue().getValueState() instanceof Integer) {
                ((Class48<Value<Integer>>)this).getValue().setValueState((int)Math.round(n));
            }
            if (this.getValue().getValueState() instanceof Float) {
                ((Class48<Value<Float>>)this).getValue().setValueState((float)n);
            }
            if (this.getValue().getValueState() instanceof Double) {
                ((Class48<Value<Double>>)this).getValue().setValueState(n);
            }
            if (this.getValue().getValueState() instanceof Long) {
                ((Class48<Value<Long>>)this).getValue().setValueState((long)n);
            }
        }
        this.width = Math.max(100, this.fontRenderer.getStringWidth(((Value)this.value).getValueName().split("_")[1]) + 25);
        this.height = 12 + this.fontRenderer.FONT_HEIGHT;
    }
}
