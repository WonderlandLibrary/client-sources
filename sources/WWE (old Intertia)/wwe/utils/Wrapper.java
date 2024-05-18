package wwe.utils;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import wwe.*;

public class Wrapper
{
    public static Minecraft mc;
    public static FontRenderer fontRenderer;
    private static DqTb uIkB;
    
    public Wrapper() {
        super();
    }
    
    public static DqTb ypJK() {
        if (Wrapper.uIkB == null) {
            Wrapper.uIkB = new DqTb();
        }
        return Wrapper.uIkB;
    }
    
    static {
        Wrapper.mc = Minecraft.getMinecraft();
        Wrapper.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }
}
