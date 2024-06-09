// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils;

import java.util.Random;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;

public class Utils
{
    protected static Minecraft mc;
    public static ScaledResolution sr;
    
    public static float range(final float min, final float max) {
        return min + new Random().nextFloat() * (max - min);
    }
    
    public static double range(final double min, final double max) {
        return min + new Random().nextDouble() * (max - min);
    }
    
    public static int range(final int min, final int max) {
        return min + new Random().nextInt() * (max - min);
    }
    
    public static boolean isInThirdPerson() {
        return Utils.mc.gameSettings.thirdPersonView != 0;
    }
    
    static {
        Utils.mc = Minecraft.getMinecraft();
        Utils.sr = new ScaledResolution(Utils.mc);
    }
}
