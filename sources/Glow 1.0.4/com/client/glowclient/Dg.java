package com.client.glowclient;

import com.client.glowclient.modules.*;

public class Dg
{
    public static qe f;
    public static qe M;
    public static qe G;
    public static qe d;
    public static qe L;
    public static qe A;
    public static Yf B;
    public static qe b;
    
    public Dg() {
        super();
    }
    
    static {
        Dg.f = new qe(614, 2, "Jewish Tricks", Category.JEWISH TRICKS, false);
        Dg.L = new qe(512, 2, "Other", Category.OTHER, true);
        Dg.G = new qe(410, 2, "Server", Category.SERVER, true);
        Dg.b = new qe(308, 2, "Render", Category.RENDER, true);
        Dg.A = new qe(206, 2, "Movement", Category.MOVEMENT, true);
        Dg.d = new qe(104, 2, "Player", Category.PLAYER, true);
        final int n = 2;
        Dg.M = new qe(n, n, "Combat", Category.COMBAT, true);
        final int n2 = 512;
        final int n3 = 54;
        final String s = "Inventory";
        final boolean b = false;
        Dg.B = new Yf(n2, n3, s, b, b);
    }
}
