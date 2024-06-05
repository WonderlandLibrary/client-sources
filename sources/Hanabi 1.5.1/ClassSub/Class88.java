package ClassSub;

import cn.Hanabi.value.*;
import cn.Hanabi.*;
import java.awt.*;
import cn.Hanabi.utils.fontmanager.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;

public class Class88
{
    Value<Double> value;
    public float x;
    public float y;
    private boolean isDraging;
    private boolean clickNotDraging;
    public int tX;
    public int tY;
    public int dragX;
    public int dragY;
    double ani;
    
    
    public Class88(final Value value) {
        this.value = (Value<Double>)value;
    }
    
    public void draw(final float x, final float y) {
        this.x = x;
        this.y = y;
        final UnicodeFontRenderer comfortaa16 = Hanabi.INSTANCE.fontManager.comfortaa16;
        final double n = (this.value.getValueState() - this.value.getValueMin()) / (this.value.getValueMax() - this.value.getValueMin());
        comfortaa16.drawString(this.value.getValueName().split("_")[1], x + 120.0f, y, new Color(212, 212, 212).getRGB());
        comfortaa16.drawString(this.value.getValueState() + "", x - 165.0f - comfortaa16.getStringWidth(this.value.getValueState() + ""), y, Class135.theme.isCurrentMode("Light") ? Class15.GREY.c : new Color(212, 212, 212).getRGB());
        if (this.ani == 0.0 || Class77.isDraging) {
            this.ani = (float)(x - 15.0f - (140.0 - 140.0 * n));
        }
        this.ani = Class246.getAnimationState(this.ani, (float)(x - 15.0f - (140.0 - 140.0 * n)), 600.0);
        Class246.drawRoundedRect(x - 155.0f, y + 3.0f, x - 15.0f, y + 6.0f, 1.0f, Class135.theme.isCurrentMode("Light") ? Class15.GREY.c : new Color(27, 27, 27).getRGB());
        Class246.drawRoundedRect(x - 155.0f, y + 3.0f, (float)this.ani, y + 6.0f, 1.0f, Class135.theme.isCurrentMode("Light") ? new Color(-14848033).brighter().getRGB() : new Color(16, 72, 182).getRGB());
        Class246.circle((float)this.ani, y + 4.5f, 3.0f, Class135.theme.isCurrentMode("Light") ? new Color(-14848033).brighter().getRGB() : new Color(29, 111, 223).getRGB());
    }
    
    public void onPress(final int tx, final int ty) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (Mouse.isButtonDown(0)) {
            if (this.isHovering(tx, ty, this.x - 155.0f, this.y - 3.0f, this.x - 15.0f, this.y + 10.0f) || this.isDraging) {
                this.isDraging = true;
            }
            else {
                this.clickNotDraging = true;
            }
            if (this.isDraging && !this.clickNotDraging) {
                double n = (tx - this.x + 155.0f) / 140.0f;
                if (n < 0.0) {
                    n = 0.0;
                }
                if (n > 1.0) {
                    n = 1.0;
                }
                this.value.setValueState(Math.round(((this.value.getValueMax() - this.value.getValueMin()) * n + this.value.getValueMin()) * (1.0 / this.value.getSteps())) / (1.0 / this.value.getSteps()));
            }
        }
        else {
            this.isDraging = false;
            this.clickNotDraging = false;
        }
        this.tX = tx;
        this.tY = ty;
    }
    
    private boolean isHovering(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
}
