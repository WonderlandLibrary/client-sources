package net.silentclient.client.nanovg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;
import java.util.function.Consumer;

import static net.silentclient.client.nanovg.NVGWrapper.*;
import static net.silentclient.client.nanovg.NVGHelper.*;

public class UI {
    private static UI instancce;
    private static boolean initialized = false;
    public static HashMap<String, Integer> imagesmap = new HashMap<>();
    private long context = -1;
    ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

    public static void init() {
        if(!initialized) {
            new UI();
            initialized = true;
        }
    }

    public static UI get() {
        return instancce;
    }

    private UI() {
        instancce = this;

        context = NanoVGGL2.nvgCreate(NanoVGGL2.NVG_ANTIALIAS | NanoVGGL2.NVG_STENCIL_STROKES);
        NVGWrapper.cx = context;

        initFont("icon", "icon.ttf");
    }


    public void render(Consumer<UI> renderer, float width, float height, float dpi) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GlStateManager.disableAlpha();
        fixDepthStencil();
        NVGWrapper.beginFrame(width,height,dpi);
        renderer.accept(this);
        NVGWrapper.endFrame();
        GlStateManager.enableAlpha();
        GL11.glPopAttrib();
    }

    private void fixDepthStencil() {
        if(Minecraft.getMinecraft().getFramebuffer().depthBuffer > -1) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT(Minecraft.getMinecraft().getFramebuffer().depthBuffer);
            int id = EXTFramebufferObject.glGenRenderbuffersEXT();
            EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, id);
            EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, id);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, id);
            Minecraft.getMinecraft().getFramebuffer().depthBuffer = -1;
        }
    }

    public void rect(float x, float y, float width, float height, Color color) {
        NVGColor col = nvgcolor(color);
        path(() -> {
            NVGWrapper.rect(x, y, width, height);
            fillColor(col);
        }, DRAW_FILL);
        col.free();
    }

    public void rrect(float x, float y, float width, float height, float radius, Color color) {
        NVGColor col = nvgcolor(color);
        path(() -> {
            NVGWrapper.rrect(x, y, width, height, radius);
            fillColor(col);
        }, DRAW_FILL);
        col.free();
    }

    public void rrect(float x, float y, float width, float height, float rTL, float rTR, float rBL, float rBR, Color color) {
        NVGColor col = nvgcolor(color);
        path(() -> {
            NVGWrapper.rrect(x, y, width, height, rTL,rTR,rBL,rBR);
            fillColor(col);
        }, DRAW_FILL);
        col.free();
    }

    public void text(float x, float y, float size, String font, String string, Color color, float blur, Alignment alignment) {
        render((vg) -> {
            NVGColor col = nvgcolor(color);
            path(() -> {
                fontBlur(blur);
                fontFace(font);
                fontSize(size*res.getScaleFactor());
                textAlign(alignment.alignment);
                fillColor(col);
                NVGWrapper.text(string,x*res.getScaleFactor(),y*res.getScaleFactor());
            }, -1);
            col.free();
        }, Display.getWidth(), Display.getHeight(), 1f);
    }

    public void text(float x, float y, float size, String font, String string, Color color, Alignment alignment) {
        text(x,y,size,font,string,color,0,alignment);
    }
    public void text(float x, float y, float size, String font, String string, Color color) {
        text(x,y,size,font,string,color,0,Alignment.LEFT_TOP);
    }

    public void image(float x, float y, float w, float h, String id, float alpha, float rad) {
        render((vg) -> {
            NVGPaint paint = NVGPaint.calloc();
            NanoVG.nvgImageSize(context, imagesmap.get(id), new int[]{(int)w},new int[]{(int)h});

            path(() -> {
                imagePattern(x,y,w,h,0,imagesmap.get(id), alpha, paint);
                NVGWrapper.rrect(x,y,w,h,rad);
                fillPaint(paint);
            }, DRAW_FILL);

            paint.free();
        }, res.getScaledWidth(), res.getScaledHeight(), res.getScaleFactor());
    }

    public void line(float sx, float sy, float ex, float ey, float w, Color c1, Color c2) {
        NVGColor nc1 = nvgcolor(c1);
        NVGColor nc2 = nvgcolor(c2);
        NVGPaint paint = NVGPaint.create();
        linearGradient(sx,sy,ex,ey,nc1,nc2,paint);
        path(sx,sy, ()->{
            strokeWidth(w);
            strokePaint(paint);
            lineTo(ex,ey);
        }, DRAW_STROKE);

        nc1.free();
        nc2.free();
    }
    public void line(float sx, float sy, float ex, float ey, float w, Color color) {
        NVGColor nc1 = nvgcolor(color);
        path(sx,sy, ()->{
            strokeWidth(w);
            strokeColor(nc1);
            lineTo(ex,ey);
        }, DRAW_STROKE);

        nc1.free();
    }

    public void dropShadow(float x, float y, float w, float h, float radius, float spread, Color color) {
        NVGColor nvg_color = nvgcolor(color);
        NVGColor nvg_transparent = nvgcolor(new Color(0, 0, 0, 0));
        NVGPaint shadowPaint = NVGPaint.calloc();

        boxGradient(x,y,w,h,radius, spread, nvg_color,nvg_transparent,shadowPaint);
        path(() -> {
            NVGWrapper.rrect(x-spread,y-spread,w+spread*2,h+spread*2,radius*2);
            NVGWrapper.rrect(x,y,w,h,radius);
            NVGWrapper.pathWinding(2);
            fillPaint(shadowPaint);
        }, DRAW_FILL);

        shadowPaint.free();
        nvg_color.free();
        nvg_transparent.free();
    }

    public void horizontalGrad(float x, float y, float w, float h, float radius, Color from, Color to) {
        gradient(x,y,w,h,x,y,x+w,y,radius,from,to);
    }
    public void verticalGrad(float x, float y, float w, float h, float radius, Color from, Color to) {
        gradient(x,y,w,h,x,y,x,y+h,radius,from,to);
    }

    public void gradient(float x, float y, float w, float h, float startX, float startY, float endX, float endY, float radius, Color from, Color to) {
        NVGColor nc1 = nvgcolor(from);
        NVGColor nc2 = nvgcolor(to);
        NVGPaint paint = NVGPaint.calloc();

        linearGradient(startX, startY, endX, endY, nc1, nc2, paint);
        path(() -> {
            NVGWrapper.rrect(x,y,w,h,radius);
            fillPaint(paint);
        }, DRAW_FILL);

        nc1.free();
        nc2.free();
        paint.free();
    }

    public float textWidth(String text, String face, float size) {
        float[] bounds = new float[4];

        float f = 0;

        save();
        fontFace(face);
        fontSize(size);
        f  = NanoVG.nvgTextBounds(cx, 0, 0, text, bounds);
        restore();

        return f;
    }

    public float textHeight(String face, float size) {
        float[] ascender = new float[1];
        float[] descender = new float[1];
        float[] lineh = new float[1];

        fontFace(face);
        fontSize(size);
        NanoVG.nvgTextMetrics(cx, ascender, descender, lineh);

        return lineh[0];
    }

    public enum Alignment {
        LEFT_TOP(NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP),
        CENTER_TOP(NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP),
        RIGHT_TOP(NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_TOP),

        LEFT_MIDDLE(NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE),
        CENTER_MIDDLE(NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE),
        RIGHT_MIDDLE(NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_MIDDLE),

        LEFT_BOTTOM(NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BOTTOM),
        CENTER_BOTTOM(NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_BOTTOM),
        RIGHT_BOTTOM(NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_BOTTOM);

        private final int alignment;

        Alignment(int alignment) {
            this.alignment = alignment;
        }
    }
}