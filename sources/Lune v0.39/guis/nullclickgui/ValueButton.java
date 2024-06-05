package me.superskidder.lune.guis.nullclickgui;

import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.utils.render.RenderUtils;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.Gui;

import java.awt.Color;

public class ValueButton {
    public Value value;
    public String name;
    public boolean custom;
    public boolean change;
    public int x;
    public int y;
    public double opacity;

    public ValueButton(final Value value, final int x, final int y) {
        this.custom = false;
        this.opacity = 0.0;
        this.value = value;
        this.x = x;
        this.y = y;
        this.name = "";
        if (this.value instanceof Bool) {
            this.change = (boolean) ((Bool) this.value).getValue();
        } else if (this.value instanceof Mode) {
            this.name = new StringBuilder().append(((Mode) this.value).getValue()).toString();
        } else if (value instanceof Num) {
            final Num v = (Num) value;
            this.name = this.name + ((Number) v.getValue()).doubleValue();
        }
        this.opacity = 0.0;
    }

    public void render(final int mouseX, final int mouseY) {
        if (!this.custom) {
            if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.F14.getStringHeight(this.value.getName()) + 5) {
                if (this.opacity + 10.0 < 200.0) {
                    this.opacity += 10.0;
                } else {
                    this.opacity = 200.0;
                }
            } else if (this.opacity - 6.0 > 0.0) {
                this.opacity -= 6.0;
            } else {
                this.opacity = 0.0;
            }
            final Color color = new Color(new Color(180, 180, 180).getRGB());
//            Gui.drawRect(this.x - 10, this.y - 4, this.x + 80, this.y + FontLoaders.F14.getStringHeight(this.value.getName()) + 5, new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (this.opacity/2)).getRGB());
            //Gui.drawRect(this.x - 9, this.y - 4, this.x - 7, this.y + FontLoaders.F14.getStringHeight(this.value.getName()) + 5, new Color(255, 255, 255, (int)(200 - this.opacity)).getRGB());
            if (this.change) {
                RenderUtil.drawBordered(this.x + 70, this.y, 4, 4,1, new Color(205, 205, 205).getRGB(),new Color(95, 95, 95).getRGB());
//                Gui.drawRect(this.x - 9, this.y - 4, this.x - 9 + 88, this.y + FontLoaders.F14.getStringHeight(this.value.getName()) + 5, new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(this.opacity)).getRGB());
            }else if(!this.change && this.value instanceof Bool){
                RenderUtil.drawBordered(this.x + 70, this.y, 4, 4,1, new Color(115, 115, 115).getRGB(),new Color(95, 95, 95).getRGB());
            }
            if (this.value instanceof Bool) {
                this.change = (boolean) ((Bool) this.value).getValue();
            } else if (this.value instanceof Mode) {
                this.name = new StringBuilder().append(((Mode) this.value).getValue()).toString();
            } else if (this.value instanceof Num) {
                final Num v = (Num) this.value;
                this.name = new StringBuilder().append(((Number) v.getValue()).doubleValue()).toString();
                if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.F14.getStringHeight(this.value.getName()) + 5 && Mouse.isButtonDown(0)) {
                    final double min = v.getMin().doubleValue();
                    final double max = v.getMax().doubleValue();
                    final double inc = 0.1;
                    final double valAbs = mouseX - (this.x + 1.0);
                    double perc = valAbs / 68.0;
                    perc = Math.min(Math.max(0.0, perc), 1.0);
                    final double valRel = (max - min) * perc;
                    double val = min + valRel;
                    val = Math.round(val * (1.0 / inc)) / (1.0 / inc);
                    v.setValue(val);
                }
            }
            if (this.value instanceof Num) {
                final Num v = (Num) this.value;
                final double render = 68.0f * (((Number) v.getValue()).floatValue() - v.getMin().floatValue()) / (v.getMax().floatValue() - v.getMin().floatValue());
                RenderUtils.drawRect(this.x, this.y + FontLoaders.F14.getStringHeight(this.value.getName()) + 3, (float) (this.x + render + 6.5), this.y + FontLoaders.F14.getStringHeight(this.value.getName()) + 4, new Color(255, 255, 255).getRGB());
            }
            Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
            FontLoaders.F14.drawStringWithShadow(this.value.getName(), this.x, this.y, (change || !(this.value instanceof Bool)) ? -1 : new Color(150, 150, 150).getRGB());
            FontLoaders.F14.drawStringWithShadow(this.name, this.x + 75 - FontLoaders.F14.getStringWidth(this.name), this.y, (change || !(this.value instanceof Bool)) ? -1 : new Color(150, 150, 150).getRGB());
        }
    }

    public void key(final char typedChar, final int keyCode) {
    }

    public void click(final int mouseX, final int mouseY, final int button) {
        if (!this.custom && mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.F14.getStringHeight(this.value.getName()) + 5) {
            if (this.value instanceof Bool) {
                final Bool v = (Bool) this.value;
                v.setValue(!(boolean) v.getValue());
                return;
            }
            if (this.value instanceof Mode) {
                final Mode m = (Mode) this.value;
                final Enum current = (Enum) m.getValue();
                final int next = (current.ordinal() + 1 >= m.getModes().length) ? 0 : (current.ordinal() + 1);
                this.value.setValue(m.getModes()[next]);
            }
        }
    }
}
