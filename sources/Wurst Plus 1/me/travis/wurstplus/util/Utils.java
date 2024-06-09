package me.travis.wurstplus.util;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.Color;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.Minecraft;

public class Utils
{
    private static Minecraft mc;
    
    public static String vectorToString(final Vec3d vector, final boolean... includeY) {
        final boolean reallyIncludeY = includeY.length <= 0 || includeY[0];
        final StringBuilder builder = new StringBuilder();
        builder.append('(');
        builder.append((int)Math.floor(vector.x));
        builder.append(", ");
        if (reallyIncludeY) {
            builder.append((int)Math.floor(vector.y));
            builder.append(", ");
        }
        builder.append((int)Math.floor(vector.z));
        builder.append(")");
        return builder.toString();
    }
    
    public static String vectorToString(final BlockPos pos) {
        return vectorToString(new Vec3d((Vec3i)pos), new boolean[0]);
    }
    
    public static void drawRainbowString(final String string, final int x, final int y, final float brightness) {
        int thisX = x;
        for (final char c : string.toCharArray()) {
            final String s = String.valueOf(c);
            thisX += x;
        }
    }
    
    public static Color rainbowColour(final long offset, final float fade) {
        final float hue = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        final Color c = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16));
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }
    
    public static double roundDouble(final double value, final int places) {
        final double scale = Math.pow(10.0, places);
        return Math.round(value * scale) / scale;
    }
    
    public static void copyToClipboard(final String s) {
        final StringSelection selection = new StringSelection(s);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
    
    public static boolean writeFile(final String file, final String contents) {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(contents);
            writer.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // public static Plane threeToTwoDimension(final Vec3d loc) {
    //     final Entity view = Utils.mc.func_175606_aa();
    //     if (view == null) {
    //         return new Plane(0.0, 0.0, false);
    //     }
    //     final ActiveRenderInfo activeRenderInfo = new ActiveRenderInfo();
    //     final Vec3d camPos = Utils.mc.player.func_174824_e(Utils.mc.func_184121_ak());
    //     final Vec3d eyePos = ActiveRenderInfo.func_178806_a(view, (double)Utils.mc.func_184121_ak());
    //     final float vecX = (float)(camPos.field_72450_a + eyePos.field_72450_a - (float)loc.field_72450_a);
    //     final float vecY = (float)(camPos.field_72448_b + eyePos.field_72448_b - (float)loc.field_72448_b);
    //     final float vecZ = (float)(camPos.field_72449_c + eyePos.field_72449_c - (float)loc.field_72449_c);
    //     final Vector4f pos = new Vector4f(vecX, vecY, vecZ, 1.0f);
    //     final Matrix4f modelMatrix = new Matrix4f();
    //     modelMatrix.load((FloatBuffer)ObfuscationReflectionHelper.getPrivateValue((Class)ActiveRenderInfo.class, (Object)new ActiveRenderInfo(), new String[] { "MODELVIEW", "field_178812_b" }));
    //     final Matrix4f projectionMatrix = new Matrix4f();
    //     projectionMatrix.load((FloatBuffer)ObfuscationReflectionHelper.getPrivateValue((Class)ActiveRenderInfo.class, (Object)new ActiveRenderInfo(), new String[] { "PROJECTION", "field_178813_c" }));
    //     vecByMatrix(pos, modelMatrix);
    //     vecByMatrix(pos, projectionMatrix);
    //     if (pos.w > 0.0f) {
    //         final Vector4f vector4f = pos;
    //         vector4f.x *= -100000.0f;
    //         final Vector4f vector4f2 = pos;
    //         vector4f2.y *= -100000.0f;
    //     }
    //     else {
    //         final float invert = 1.0f / pos.w;
    //         final Vector4f vector4f3 = pos;
    //         vector4f3.x *= invert;
    //         final Vector4f vector4f4 = pos;
    //         vector4f4.y *= invert;
    //     }
    //     final ScaledResolution res = new ScaledResolution(Utils.mc);
    //     final float halfWidth = res.func_78326_a() / 2.0f;
    //     final float halfHeight = res.func_78328_b() / 2.0f;
    //     pos.x = halfWidth + (0.5f * pos.x * res.func_78326_a() + 0.5f);
    //     pos.y = halfHeight - (0.5f * pos.y * res.func_78328_b() + 0.5f);
    //     boolean bVisible = true;
    //     if (pos.x < 0.0f || pos.y < 0.0f || pos.x > res.func_78326_a() || pos.y > res.func_78328_b()) {
    //         bVisible = false;
    //     }
    //     return new Plane(pos.x, pos.y, bVisible);
    // }
    
    private static void vecByMatrix(final Vector4f vec, final Matrix4f matrix) {
        final float x = vec.x;
        final float y = vec.y;
        final float z = vec.z;
        vec.x = x * matrix.m00 + y * matrix.m10 + z * matrix.m20 + matrix.m30;
        vec.y = x * matrix.m01 + y * matrix.m11 + z * matrix.m21 + matrix.m31;
        vec.z = x * matrix.m02 + y * matrix.m12 + z * matrix.m22 + matrix.m32;
        vec.w = x * matrix.m03 + y * matrix.m13 + z * matrix.m23 + matrix.m33;
    }
    
    public static float getPlayerDirection() {
        float var1 = Utils.mc.player.rotationYaw;
        if (Utils.mc.player.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Utils.mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (Utils.mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Utils.mc.player.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Utils.mc.player.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        var1 *= 0.017453292f;
        return var1;
    }
    
    static {
        Utils.mc = Minecraft.getMinecraft();
        // SHULKERS = new Block[] { Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA };
    }
}
