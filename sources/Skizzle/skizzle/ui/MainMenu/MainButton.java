/*
 * Decompiled with CFR 0.150.
 */
package skizzle.ui.MainMenu;

import skizzle.util.Timer;

public class MainButton {
    public Timer animationTimer = new Timer();
    public int y;
    public int animationStage;
    public String name;
    public int x;

    public boolean hovered(int Nigga, int Nigga2) {
        MainButton Nigga3;
        float Nigga4 = Nigga3.x;
        float Nigga5 = Nigga3.y - 6;
        return (float)Nigga >= Nigga4 && (float)Nigga2 >= Nigga5 && (float)Nigga < Nigga4 + Float.intBitsToFloat(1.0084391E9f ^ 0x7F3B8F24) && (float)Nigga2 < Nigga5 + Float.intBitsToFloat(1.01718829E9f ^ 0x7D010FDF);
    }

    public MainButton(String Nigga, int Nigga2, int Nigga3) {
        MainButton Nigga4;
        Nigga4.name = Nigga;
        Nigga4.x = Nigga2;
        Nigga4.y = Nigga3;
        Nigga4.animationStage = 0;
    }

    public static {
        throw throwable;
    }
}

