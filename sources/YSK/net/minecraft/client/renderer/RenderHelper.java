package net.minecraft.client.renderer;

import java.nio.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;

public class RenderHelper
{
    private static FloatBuffer colorBuffer;
    private static final Vec3 LIGHT0_POS;
    private static final Vec3 LIGHT1_POS;
    
    public static void disableStandardItemLighting() {
        GlStateManager.disableLighting();
        GlStateManager.disableLight("".length());
        GlStateManager.disableLight(" ".length());
        GlStateManager.disableColorMaterial();
    }
    
    public static void enableGUIStandardItemLighting() {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(165.0f, 1.0f, 0.0f, 0.0f);
        enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static FloatBuffer setColorBuffer(final float n, final float n2, final float n3, final float n4) {
        RenderHelper.colorBuffer.clear();
        RenderHelper.colorBuffer.put(n).put(n2).put(n3).put(n4);
        RenderHelper.colorBuffer.flip();
        return RenderHelper.colorBuffer;
    }
    
    private static FloatBuffer setColorBuffer(final double n, final double n2, final double n3, final double n4) {
        return setColorBuffer((float)n, (float)n2, (float)n3, (float)n4);
    }
    
    static {
        RenderHelper.colorBuffer = GLAllocation.createDirectFloatBuffer(0x48 ^ 0x58);
        LIGHT0_POS = new Vec3(0.20000000298023224, 1.0, -0.699999988079071).normalize();
        LIGHT1_POS = new Vec3(-0.20000000298023224, 1.0, 0.699999988079071).normalize();
    }
    
    public static void enableStandardItemLighting() {
        GlStateManager.enableLighting();
        GlStateManager.enableLight("".length());
        GlStateManager.enableLight(" ".length());
        GlStateManager.enableColorMaterial();
        GlStateManager.colorMaterial(28 + 229 + 234 + 541, 798 + 4068 - 2298 + 3066);
        final float n = 0.4f;
        final float n2 = 0.6f;
        final float n3 = 0.0f;
        GL11.glLight(14047 + 11479 - 19938 + 10796, 1023 + 4165 - 3141 + 2564, setColorBuffer(RenderHelper.LIGHT0_POS.xCoord, RenderHelper.LIGHT0_POS.yCoord, RenderHelper.LIGHT0_POS.zCoord, 0.0));
        GL11.glLight(10104 + 10918 - 15903 + 11265, 3912 + 3830 - 3335 + 202, setColorBuffer(n2, n2, n2, 1.0f));
        GL11.glLight(14641 + 9594 - 17536 + 9685, 305 + 10 + 925 + 3368, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(104 + 4051 + 6149 + 6080, 3481 + 3987 - 4543 + 1685, setColorBuffer(n3, n3, n3, 1.0f));
        GL11.glLight(15754 + 10062 - 24440 + 15009, 344 + 110 + 2945 + 1212, setColorBuffer(RenderHelper.LIGHT1_POS.xCoord, RenderHelper.LIGHT1_POS.yCoord, RenderHelper.LIGHT1_POS.zCoord, 0.0));
        GL11.glLight(6410 + 8817 - 5771 + 6929, 2521 + 3905 - 6317 + 4500, setColorBuffer(n2, n2, n2, 1.0f));
        GL11.glLight(4329 + 13156 - 13464 + 12364, 2916 + 1676 - 3683 + 3699, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(2015 + 5363 - 3856 + 12863, 2719 + 1592 - 1887 + 2186, setColorBuffer(n3, n3, n3, 1.0f));
        GlStateManager.shadeModel(5925 + 5143 - 7253 + 3609);
        GL11.glLightModel(857 + 465 + 1056 + 521, setColorBuffer(n, n, n, 1.0f));
    }
}
