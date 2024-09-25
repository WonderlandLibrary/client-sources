/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package skizzle.ui.elements;

import java.awt.Color;
import org.lwjgl.input.Keyboard;
import skizzle.newFont.FontUtil;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.ui.elements.Element;
import skizzle.util.Colors;
import skizzle.util.RenderUtil;

public class Textbox
extends Element {
    public boolean cursor;
    public boolean selected;
    public String storedText;
    public String defaultText;

    public void hover() {
    }

    @Override
    public void click() {
        Textbox Nigga;
        if (!Nigga.selected) {
            Nigga.cursor = true;
            Nigga.reset();
        }
        Nigga.selected = true;
    }

    @Override
    public boolean isHovering(int Nigga, int Nigga2) {
        Textbox Nigga3;
        return Nigga > Nigga3.x && Nigga2 > Nigga3.y && Nigga < Nigga3.x + Nigga3.width && Nigga2 < Nigga3.y + Nigga3.height;
    }

    public void animate() {
    }

    public void draw(int Nigga, int Nigga2) {
        Textbox Nigga3;
        Nigga3.hovering = Nigga3.isHovering(Nigga, Nigga2);
        Nigga3.animate();
        MinecraftFontRenderer Nigga4 = FontUtil.cleanSmall;
        RenderUtil.drawRoundedRect(Nigga3.x, Nigga3.y, Nigga3.x + Nigga3.width, Nigga3.y + Nigga3.height, 5.0, -12566464);
        if (Nigga3.hasTimeElapsed((long)-159087744 ^ 0xFFFFFFFFF6848274L, true)) {
            boolean bl = Nigga3.cursor = !Nigga3.cursor;
        }
        if (Nigga3.cursor && Nigga3.selected) {
            RenderUtil.drawRoundedRect(Nigga3.x + Nigga4.getStringWidth(Nigga3.storedText) + 5, (double)Nigga3.y + (double)Nigga3.height / 4.0 - (double)Nigga4.getHeight() / 2.0 + 2.0, Nigga3.x + Nigga4.getStringWidth(Nigga3.storedText) + 6, (double)Nigga3.y + (double)Nigga3.height / 4.0 + (double)(Nigga4.getHeight() * 2) - 2.0, 1.0, -1);
        }
        if (!Nigga3.selected && Nigga3.storedText.equals("")) {
            Nigga4.drawString(Nigga3.defaultText, Nigga3.x + 4, (double)Nigga3.y + (double)Nigga3.height / 4.0, 0x909090);
        } else {
            Nigga4.drawString(Nigga3.storedText, Nigga3.x + 4, (double)Nigga3.y + (double)Nigga3.height / 4.0, -1);
        }
    }

    public Textbox(String Nigga, int Nigga2, int Nigga3, int Nigga4, int Nigga5, int Nigga6) {
        super(Nigga2, Nigga3, Nigga4, Nigga5, Nigga6);
        Textbox Nigga7;
        Nigga7.defaultText = Nigga;
        Nigga7.storedText = "";
    }

    public void onKeyPress(int Nigga) {
        Textbox Nigga2;
        if (Nigga == 56 || Nigga == 200 || Nigga == 208 || Nigga == 205 || Nigga == 203 || Nigga == 15 || Nigga == 41 || Nigga == 219 || Nigga == 42) {
            return;
        }
        if (Nigga == 1) {
            Nigga2.selected = false;
            Nigga2.storedText = "";
        } else if (Nigga2.selected) {
            if (Nigga == 14 && Nigga2.storedText.length() > 0) {
                Nigga2.storedText = Nigga2.storedText.substring(0, Nigga2.storedText.length() - 1);
            } else if (Nigga != 14) {
                Nigga2.storedText = String.valueOf(Nigga2.storedText) + Keyboard.getKeyName((int)Nigga).toLowerCase();
            }
        }
    }

    public int getHoverColor() {
        Textbox Nigga;
        double Nigga2 = Nigga.stage / (double)Nigga.width;
        int Nigga3 = (int)(32.0 * Nigga2);
        new Color(0x70000000);
        return Nigga.enabled ? Colors.getColor(Nigga3, Nigga3, Nigga3, 130) : 0x70202020;
    }

    public static {
        throw throwable;
    }
}

