package best.actinium.util.render;

import best.actinium.util.IAccess;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.nanovg.NVGColor;
import org.lwjglx.opengl.Display;

import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.system.MemoryUtil.memUTF8;

public class RenderEngine2D implements IAccess {

    private static long ctx;

    public static void init() {
        ctx = nvgCreate(NVG_ANTIALIAS);
    }

    public static void begin() {
        nvgBeginFrame(
                ctx,
                Display.getWidth(),
                Display.getHeight(),
                1.0F
        );

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Minecraft renders everything scaled 2x down, so we need to do this:
        nvgScale(ctx, 2.0F, 2.0F);
    }

    public static void end() {
        nvgEndFrame(ctx);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private static final NVGColor RED = NVGColor.calloc()
            .a(1).r(1).g(0).b(0);

    public static void drawString() {
       // nvgText(ctx, x, y, test, co)
        ByteBuffer text = memUTF8("Following text has trailing spaces:", false);
        nvgCreateFont(ctx, "sans", "actinium/fonts/product-sans-regular.ttf");
        drawText(3, 3, text, 18, 0,"sans", RED, NVG_ALIGN_CENTER);
    }

    private static void drawText(int posX, int posY, ByteBuffer asciiBuffer,
                                 float fontSize, float fontBlur, String font, NVGColor color, int alignment) {
        nvgFontSize(ctx, fontSize);
        nvgFontFace(ctx, font);
        nvgFontBlur(ctx, fontBlur);
        nvgFillColor(ctx, color);
        nvgTextAlign(ctx, alignment);
        nvgText(ctx, (float)posX, (float)posY, asciiBuffer);
    }

    public static void rectangle(float x, float y, float width, float height, Color color) {
        NVGColor nvgColor = getColor(color);

        nvgBeginPath(ctx);
        nvgRect(ctx, x, y, width, height);
        nvgFillColor(ctx, nvgColor);
        nvgFill(ctx);
        nvgClosePath(ctx);

        nvgColor.free();
    }

    public static void rounded(float x, float y, float width, float height, float radius, Color color) {
        NVGColor nvgColor = getColor(color);

        nvgBeginPath(ctx);
        nvgRoundedRect(ctx, x, y, width, height, radius);
        nvgFillColor(ctx, nvgColor);
        nvgFill(ctx);
        nvgClosePath(ctx);

        nvgColor.free();
    }

    public static void rounded(float x, float y, float width, float height, float[] radius, Color color) {
        if (radius.length != 4)
            return;

        NVGColor nvgColor = getColor(color);

        nvgBeginPath(ctx);
        nvgRoundedRectVarying(ctx, x, y, width, height, radius[0], radius[1], radius[2], radius[3]);
        nvgFillColor(ctx, nvgColor);
        nvgFill(ctx);
        nvgClosePath(ctx);

        nvgColor.free();
    }

    public static void circle(float x, float y, float radius, Color color) {
        NVGColor nvgColor = getColor(color);

        nvgBeginPath(ctx);
        nvgCircle(ctx, x, y, radius);
        nvgFillColor(ctx, nvgColor);
        nvgFill(ctx);
        nvgClosePath(ctx);

        nvgColor.free();
    }

    private static NVGColor getColor(Color color) {
        return NVGColor.calloc()
                .r(color.getRed() / 255F)
                .g(color.getGreen() / 255F)
                .b(color.getBlue() / 255F)
                .a(color.getAlpha() / 255F);
    }

}
