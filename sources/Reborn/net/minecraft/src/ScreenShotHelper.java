package net.minecraft.src;

import java.nio.*;
import java.text.*;
import java.io.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.*;

public class ScreenShotHelper
{
    private static final DateFormat dateFormat;
    private static IntBuffer field_74293_b;
    private static int[] field_74294_c;
    
    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }
    
    public static String saveScreenshot(final File par0File, final int par1, final int par2) {
        return func_74292_a(par0File, null, par1, par2);
    }
    
    public static String func_74292_a(final File par0File, final String par1Str, final int par2, final int par3) {
        try {
            final File var4 = new File(par0File, "screenshots");
            var4.mkdir();
            final int var5 = par2 * par3;
            if (ScreenShotHelper.field_74293_b == null || ScreenShotHelper.field_74293_b.capacity() < var5) {
                ScreenShotHelper.field_74293_b = BufferUtils.createIntBuffer(var5);
                ScreenShotHelper.field_74294_c = new int[var5];
            }
            GL11.glPixelStorei(3333, 1);
            GL11.glPixelStorei(3317, 1);
            ScreenShotHelper.field_74293_b.clear();
            GL11.glReadPixels(0, 0, par2, par3, 32993, 33639, ScreenShotHelper.field_74293_b);
            ScreenShotHelper.field_74293_b.get(ScreenShotHelper.field_74294_c);
            func_74289_a(ScreenShotHelper.field_74294_c, par2, par3);
            final BufferedImage var6 = new BufferedImage(par2, par3, 1);
            var6.setRGB(0, 0, par2, par3, ScreenShotHelper.field_74294_c, 0, par2);
            File var7;
            if (par1Str == null) {
                var7 = func_74290_a(var4);
            }
            else {
                var7 = new File(var4, par1Str);
            }
            ImageIO.write(var6, "png", var7);
            return "Saved screenshot as " + var7.getName();
        }
        catch (Exception var8) {
            var8.printStackTrace();
            return "Failed to save: " + var8;
        }
    }
    
    private static File func_74290_a(final File par0File) {
        final String var1 = ScreenShotHelper.dateFormat.format(new Date()).toString();
        int var2 = 1;
        File var3;
        while (true) {
            var3 = new File(par0File, String.valueOf(var1) + ((var2 == 1) ? "" : ("_" + var2)) + ".png");
            if (!var3.exists()) {
                break;
            }
            ++var2;
        }
        return var3;
    }
    
    private static void func_74289_a(final int[] par0ArrayOfInteger, final int par1, final int par2) {
        final int[] var3 = new int[par1];
        for (int var4 = par2 / 2, var5 = 0; var5 < var4; ++var5) {
            System.arraycopy(par0ArrayOfInteger, var5 * par1, var3, 0, par1);
            System.arraycopy(par0ArrayOfInteger, (par2 - 1 - var5) * par1, par0ArrayOfInteger, var5 * par1, par1);
            System.arraycopy(var3, 0, par0ArrayOfInteger, (par2 - 1 - var5) * par1, par1);
        }
    }
}
