package wwe;

import java.awt.*;
import java.io.*;

public class bozE
{
    public static giQz SPev;
    public static giQz xISY;
    public static giQz dRQq;
    public static giQz TCnN;
    public static giQz SpUn;
    public static giQz VuaG;
    public static giQz gHdT;
    public static giQz Jfgf;
    
    public bozE() {
        super();
    }
    
    public static void JduO() {
        bozE.xISY = new giQz(odSO("/assets/minecraft/textures/wwe/Comfortaa-Regular.ttf", 0, 44), true, 8);
        bozE.SPev = new giQz(odSO("/assets/minecraft/textures/wwe/Comfortaa-Regular.ttf", 0, 50), true, 8);
        bozE.dRQq = new giQz(odSO("/assets/minecraft/textures/wwe/Comfortaa-Regular.ttf", 0, 36), true, 8);
        bozE.TCnN = new giQz(odSO("/assets/minecraft/textures/wwe/Comfortaa-Regular.ttf", 0, 30), true, 8);
        bozE.SpUn = new giQz(new Font("Roboto", 0, 36), true, 8);
        bozE.VuaG = new giQz(odSO("/assets/minecraft/textures/wwe/Comic-Sans.ttf", 0, 36), true, 8);
        bozE.gHdT = new giQz(new Font("Verdana", 0, 36), true, 8);
    }
    
    public static Font odSO(final String s, final int n, final int n2) {
        final InputStream resourceAsStream = bozE.class.getResourceAsStream(s);
        try {
            return Font.createFont(n, resourceAsStream).deriveFont(0, (float)n2);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new Font("Roboto", n, n2);
        }
    }
}
