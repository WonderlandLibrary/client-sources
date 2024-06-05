/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package drunkclient.beta.API.GUI.clickgui.window.impl;

import drunkclient.beta.IMPL.Module.impl.render.HUD;
import drunkclient.beta.IMPL.font.CFontRenderer;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.IMPL.set.Value;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class ValueButton {
    public Value value;
    public String name;
    public boolean custom = false;
    public boolean change;
    public int x;
    public int y;
    public double opacity = 0.0;

    public ValueButton(Value value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.name = "";
        if (this.value instanceof Option) {
            this.change = (Boolean)((Option)this.value).getValue();
        } else if (this.value instanceof Mode) {
            this.name = "" + ((Mode)this.value).getValue();
        } else if (value instanceof Numbers) {
            Numbers v = (Numbers)value;
            this.name = String.valueOf(this.name) + (v.isInteger() ? (double)((Number)v.getValue()).intValue() : ((Number)v.getValue()).doubleValue());
        }
        this.opacity = 0.0;
    }

    public void render(int mouseX, int mouseY) {
        Numbers v;
        CFontRenderer font = FontLoaders.GoogleSans18;
        CFontRenderer mfont = FontLoaders.GoogleSans16;
        CFontRenderer bigfont = FontLoaders.GoogleSans28;
        if (this.custom) return;
        this.opacity = mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.GoogleSans18.getStringHeight(this.value.getName()) + 5 ? (this.opacity + 10.0 < 200.0 ? (this.opacity += 10.0) : 200.0) : (this.opacity - 6.0 > 0.0 ? (this.opacity -= 6.0) : 0.0);
        if (this.value instanceof Option) {
            this.change = (Boolean)((Option)this.value).getValue();
        } else if (this.value instanceof Mode) {
            this.name = "" + ((Mode)this.value).getValue();
        } else if (this.value instanceof Numbers) {
            v = (Numbers)this.value;
            this.name = "" + (v.isInteger() ? (double)((Number)v.getValue()).intValue() : ((Number)v.getValue()).doubleValue());
            if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + font.getStringHeight(this.value.getName()) + 5 && Mouse.isButtonDown((int)0)) {
                double min = ((Number)v.getMinimum()).doubleValue();
                double max = ((Number)v.getMaximum()).doubleValue();
                double inc = ((Number)v.getIncrement()).doubleValue();
                double valAbs = (double)mouseX - ((double)this.x + 1.0);
                double perc = valAbs / 68.0;
                perc = Math.min(Math.max(0.0, perc), 1.0);
                double valRel = (max - min) * perc;
                double val = min + valRel;
                val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
                v.setValue(val);
            }
        }
        Gui.drawRect(this.x - 12, this.y - 4, this.x + 82, this.y + 11, new Color(55, 55, 55).getRGB());
        if (this.value instanceof Numbers) {
            v = (Numbers)this.value;
            double render = 68.0f * (((Number)v.getValue()).floatValue() - ((Number)v.getMinimum()).floatValue()) / (((Number)v.getMaximum()).floatValue() - ((Number)v.getMinimum()).floatValue());
            Gui.drawRect(this.x - 12, this.y + mfont.getStringHeight(this.value.getName()) - 10, (float)((double)this.x + render + 1.0), this.y + mfont.getStringHeight(this.value.getName()) + 20, HUD.color);
        }
        if (this.change) {
            Gui.drawRect(this.x - 12, this.y - 5, this.x + 82, this.y + 12, new Color(HUD.color).getRGB());
        }
        font.drawString(this.value.getName(), this.x - 8, this.y + 1, -1);
        font.drawString(this.name, this.x + 76 - mfont.getStringWidth(this.name), this.y + 2, -1);
    }

    public void key(char typedChar, int keyCode) {
    }

    public void click(int mouseX, int mouseY, int button) {
        if (this.custom) return;
        if (mouseX <= this.x - 7) return;
        if (mouseX >= this.x + 85) return;
        if (mouseY <= this.y - 6) return;
        if (mouseY >= this.y + FontLoaders.GoogleSans18.getStringHeight(this.value.getName()) + 5) return;
        if (this.value instanceof Option) {
            Option v;
            v.setValue((Boolean)(v = (Option)this.value).getValue() == false);
            return;
        }
        if (!(this.value instanceof Mode)) return;
        Mode m = (Mode)this.value;
        Enum current = (Enum)m.getValue();
        int next = current.ordinal() + 1 >= m.getModes().length ? 0 : current.ordinal() + 1;
        this.value.setValue(m.getModes()[next]);
    }
}

