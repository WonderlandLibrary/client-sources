/*
 * Decompiled with CFR 0.150.
 */
package skizzle.ui.elements;

import skizzle.util.AnimationHelper;

public class Element
extends AnimationHelper {
    public boolean hovering;
    public int height;
    public int y;
    public int id;
    public boolean enabled;
    public int x;
    public int mid;
    public int width;

    public boolean hovering(int Nigga, int Nigga2, int Nigga3, int Nigga4, int Nigga5, int Nigga6) {
        return Nigga5 > Nigga && Nigga6 > Nigga2 && Nigga5 < Nigga3 && Nigga6 < Nigga4;
    }

    public void click() {
    }

    public Element(int Nigga, int Nigga2, int Nigga3, int Nigga4, int Nigga5) {
        Element Nigga6;
        Nigga6.id = Nigga;
        Nigga6.x = Nigga2;
        Nigga6.y = Nigga3;
        Nigga6.width = Nigga4;
        Nigga6.height = Nigga5;
        Nigga6.mid = Nigga2 + Nigga4 / 2;
        Nigga6.enabled = true;
    }

    public void onKeyPress() {
    }

    public void draw() {
    }

    public boolean isHovering(int Nigga, int Nigga2) {
        Element Nigga3;
        return Nigga3.hovering(Nigga3.x, Nigga3.y, Nigga3.x + Nigga3.width, Nigga3.y + Nigga3.width, Nigga, Nigga2);
    }

    public void onMouseClick(int Nigga, int Nigga2, int Nigga3, int Nigga4) {
        Element Nigga5;
        if (Nigga == 0 && Nigga2 == 1 && Nigga5.isHovering(Nigga3, Nigga4)) {
            Nigga5.click();
        }
    }

    public static {
        throw throwable;
    }
}

