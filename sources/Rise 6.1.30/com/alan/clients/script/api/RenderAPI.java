package com.alan.clients.script.api;

import com.alan.clients.Client;
import com.alan.clients.component.impl.render.SmoothCameraComponent;
import com.alan.clients.script.api.wrapper.impl.ScriptItemStack;
import com.alan.clients.script.api.wrapper.impl.ScriptMCFontRenderer;
import com.alan.clients.script.api.wrapper.impl.ScriptRiseFontRenderer;
import com.alan.clients.script.api.wrapper.impl.vector.ScriptVector3d;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.font.impl.rise.FontRenderer;
import com.alan.clients.util.render.RenderUtil;
import org.openjdk.nashorn.api.scripting.JSObject;

import javax.script.ScriptException;
import java.awt.*;
import java.util.Arrays;

import static com.alan.clients.layer.Layers.BLOOM;
import static com.alan.clients.layer.Layers.BLUR;

public class RenderAPI extends API implements Accessor {

    public ScriptVector3d getCameraPosition() {
        return new ScriptVector3d(MC.getRenderManager().renderPosX, MC.getRenderManager().renderPosY, MC.getRenderManager().renderPosZ);
    }

    public static Color intArrayToColor(int[] color) {
        int[] clamped = Arrays.stream(color).map(x -> Math.min(x, 255)).toArray();
        return new Color(clamped[0], clamped[1], clamped[2], clamped.length >= 4 ? clamped[3] : 255);
    }

    public void rectangle(final double x, final double y, final double width, final double height, final int[] rgba) throws ScriptException {
        try {
            RenderUtil.rectangle(x, y, width, height, intArrayToColor(rgba));
        } catch (Exception e) {
            throw new ScriptException("Not enough elements in the array!");
        }
    }

    public void rectangle(final double x, final double y, final double width, final double height) {
        RenderUtil.rectangle(x, y, width, height);
    }

    public void rainbowRectangle(final double x, final double y, final double width, final double height) {
        RenderUtil.rainbowRectangle(x, y, width, height);
    }

    public void roundedRectangle(final double x, final double y, final double width, final double height, final double radius, final int[] color) {
        RenderUtil.roundedRectangle(x, y, width, height, radius, intArrayToColor(color));
    }

    public void roundedOutlineRectangle(final double x, final double y, final double width, final double height, final double radius, final double borderSize, final int[] color) {
        RenderUtil.roundedOutlineRectangle(x, y, width, height, radius, borderSize, intArrayToColor(color));
    }

    public void centeredRectangle(final double x, final double y, final double width, final double height, final int[] rgba) throws ScriptException {
        try {
            RenderUtil.rectangle(x - width / 2, y - height / 2, width, height, intArrayToColor(rgba));
        } catch (Exception e) {
            throw new ScriptException("Not enough elements in the array!");
        }
    }

    public void centeredRectangle(final double x, final double y, final double width, final double height) {
        RenderUtil.rectangle(x - width / 2, y - height / 2, width, height);
    }

    public void smoothCamera() {
        SmoothCameraComponent.setY();
    }

    public void renderItemIcon(final double x, final double y, int color, final ScriptItemStack itemStack) {
        RenderUtil.renderItemIcon(x, y, color, itemStack.getWrapped());
    }

    public void drawLine3D(double x, double y, double z, double x1, double y1, double z1, final int[] color, final float width) {
        RenderUtil.drawLine(x, y, z, x1, y1, z1, intArrayToColor(color), width);
    }

    public void drawLine3D(ScriptVector3d from, ScriptVector3d to, final int[] color, final float width) {
        drawLine3D(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ(), color, width);
    }

    public ScriptMCFontRenderer getMinecraftFontRenderer() {
        return new ScriptMCFontRenderer(MC.fontRendererObj);
    }

    public ScriptRiseFontRenderer getCustomFontRenderer(String name, int size, boolean antialiasing) {
        return new ScriptRiseFontRenderer(new FontRenderer(new Font(name, Font.PLAIN, size), antialiasing));
    }

    public ScriptRiseFontRenderer getCustomFontRendererBold(String name, int size, boolean antialiasing) {
        return new ScriptRiseFontRenderer(new FontRenderer(new Font(name, Font.BOLD, size), antialiasing));
    }

    public ScriptRiseFontRenderer getCustomFontRendererItalic(String name, int size, boolean antialiasing) {
        return new ScriptRiseFontRenderer(new FontRenderer(new Font(name, Font.ITALIC, size), antialiasing));
    }

    public ScriptRiseFontRenderer getCustomFontRendererBoldItalic(String name, int size, boolean antialiasing) {
        return new ScriptRiseFontRenderer(new FontRenderer(new Font(name, Font.BOLD | Font.ITALIC, size), antialiasing));
    }

    public float getEyeHeight() {
        return MC.thePlayer.getEyeHeight();
    }

    public int[] getThemeColor() {
        int[] f = new int[4];
        Color c = Client.INSTANCE.getThemeManager().getTheme().getFirstColor();
        f[0] = c.getRed();
        f[1] = c.getGreen();
        f[2] = c.getBlue();
        f[3] = c.getAlpha();
        return f;
    }

    public void blur(JSObject function) throws ScriptException {
        if (!function.isFunction()) throw new ScriptException("Not a function!");
        getLayer(BLUR).add(() -> function.call(null));
    }

    // Stops old script from breaking, can be removed in the future
    public void postBloom(JSObject function) throws ScriptException {
        bloom(function);
    }

    public void bloom(JSObject function) throws ScriptException {
        if (!function.isFunction()) throw new ScriptException("Not a function!");
        getLayer(BLOOM).add(() -> function.call(null));
    }

    public int[] getBackgroundShade() {
        Color color = getTheme().getBackgroundShade();
        return new int[]{color.getRed(), color.getBlue(), color.getGreen(), color.getAlpha()};
    }

    public int[] getDropShadow() {
        Color color = getTheme().getDropShadow();
        return new int[]{color.getRed(), color.getBlue(), color.getGreen(), color.getAlpha()};
    }
}
