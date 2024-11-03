package net.silentclient.client.nanovg;

import org.lwjgl.nanovg.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class NVGWrapper {
    public static long cx;

    public static void beginFrame(float width, float height, float dpi) {
        NanoVG.nvgBeginFrame(cx, width, height, dpi);
    }

    public static void cancelFrame() {
        NanoVG.nvgCancelFrame(cx);
    }

    public static void endFrame() {
        NanoVG.nvgEndFrame(cx);
    }

    public static void globalCompositeOperation(int op) {
        NanoVG.nvgGlobalCompositeOperation(cx, op);
    }

    public static void globalCompositeBlendFunc(int sfactor, int dfactor) {
        NanoVG.nvgGlobalCompositeBlendFunc(cx, sfactor, dfactor);
    }

    public static void globalCompositeBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        NanoVG.nvgGlobalCompositeBlendFuncSeparate(cx, srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    public static NVGColor RGB(byte r, byte g, byte b, NVGColor __result) {
        return NanoVG.nvgRGB(r, g, b, __result);
    }

    public static NVGColor RGBf(float r, float g, float b, NVGColor __result) {
        return NanoVG.nvgRGBf(r, g, b, __result);
    }

    public static NVGColor RGBA(byte r, byte g, byte b, byte a, NVGColor __result) {
        return NanoVG.nvgRGBA(r, g, b, a, __result);
    }

    public static NVGColor RGBAf(float r, float g, float b, float a, NVGColor __result) {
        return NanoVG.nvgRGBAf(r, g, b, a, __result);
    }

    public static NVGColor lerpRGBA(NVGColor c0, NVGColor c1, float u, NVGColor __result) {
        return NanoVG.nvgLerpRGBA(c0, c1, u, __result);
    }

    public static NVGColor transRGBA(NVGColor c0, byte a, NVGColor __result) {
        return NanoVG.nvgTransRGBA(c0, a, __result);
    }

    public static NVGColor transRGBAf(NVGColor c0, float a, NVGColor __result) {
        return NanoVG.nvgTransRGBAf(c0, a, __result);
    }

    public static NVGColor HSL(float h, float s, float l, NVGColor __result) {
        return NanoVG.nvgHSL(h, s, l, __result);
    }

    public static NVGColor HSLA(float h, float s, float l, byte a, NVGColor __result) {
        return NanoVG.nvgHSLA(h, s, l, a, __result);
    }

    public static void save() {
        NanoVG.nvgSave(cx);
    }

    public static void restore() {
        NanoVG.nvgRestore(cx);
    }

    public static void reset() {
        NanoVG.nvgReset(cx);
    }

    public static void shapeAntiAlias(boolean enabled) {
        NanoVG.nvgShapeAntiAlias(cx, enabled);
    }

    public static void strokeColor(NVGColor color) {
        NanoVG.nvgStrokeColor(cx, color);
    }

    public static void strokePaint(NVGPaint paint) {
        NanoVG.nvgStrokePaint(cx, paint);
    }

    public static void fillColor(NVGColor color) {
        NanoVG.nvgFillColor(cx, color);
    }

    public static void fillPaint(NVGPaint paint) {
        NanoVG.nvgFillPaint(cx, paint);
    }

    public static void miterLimit(float limit) {
        NanoVG.nvgMiterLimit(cx, limit);
    }

    public static void strokeWidth(float width) {
        NanoVG.nvgStrokeWidth(cx, width);
    }

    public static void lineCap(int cap) {
        NanoVG.nvgLineCap(cx, cap);
    }

    public static void lineJoin(int join) {
        NanoVG.nvgLineJoin(cx, join);
    }

    public static void globalAlpha(float alpha) {
        NanoVG.nvgGlobalAlpha(cx, alpha);
    }

    public void resetTransformation() {
        NanoVG.nvgResetTransform(cx);
    }

    public static void transform(float a, float b, float c, float d, float e, float f) {
        NanoVG.nvgTransform(cx, a, b, c, d, e, f);
    }

    public static void translate(float x, float y) {
        NanoVG.nvgTranslate(cx, x, y);
    }

    public static void rotate(float angle) {
        NanoVG.nvgRotate(cx, angle);
    }

    public static void skewX(float angle) {
        NanoVG.nvgSkewX(cx,angle);
    }

    public static void skewY(float angle) {
        NanoVG.nvgSkewY(cx,angle);
    }

    public static void scale(float x, float y) {
        NanoVG.nvgScale(cx,x,y);
    }

    public static void currentTransform(FloatBuffer xFrom) {
        NanoVG.nvgCurrentTransform(cx,xFrom);
    }

    public static void transformIdentity(FloatBuffer dst) {
        NanoVG.nvgTransformIdentity(dst);
    }

    public static void transformTranslate(FloatBuffer dst, float x, float y) {
        NanoVG.nvgTransformTranslate(dst,x,y);
    }

    public static void transformScale(FloatBuffer dst, float x, float y) {
        NanoVG.nvgTransformScale(dst,x,y);
    }

    public static void transformRotate(FloatBuffer dst, float angle) {
        NanoVG.nvgTransformRotate(dst,angle);
    }

    public static void transformSkewX(FloatBuffer dst, float angle) {
        NanoVG.nvgTransformSkewX(dst,angle);
    }

    public static void transformSkewY(FloatBuffer dst, float angle) {
        NanoVG.nvgTransformSkewY(dst,angle);
    }

    public static void transformMultiply(FloatBuffer dst, FloatBuffer src) {
        NanoVG.nvgTransformMultiply(dst,src);
    }

    public static void transfromPremultiply(FloatBuffer dst, FloatBuffer src) {
        NanoVG.nvgTransformPremultiply(dst,src);
    }

    public static void transfromInverse(FloatBuffer dst, FloatBuffer src) {
        NanoVG.nvgTransformInverse(dst,src);
    }

    public static void transfromPoint(FloatBuffer dstx, FloatBuffer dsty, FloatBuffer xfrom, float srcX, float srcY) {
        NanoVG.nvgTransformPoint(dstx, dsty, xfrom, srcX, srcY);
    }

    public static float deg_rad(float val) {
        return NanoVG.nvgDegToRad(val);
    }

    public static float rad_deg(float val) {
        return NanoVG.nvgDegToRad(val);
    }

    public static int createImage(CharSequence filename, int imageFlags) {
        return NanoVG.nvgCreateImage(cx,filename,imageFlags);
    }

    public static int createImageMem(int imageFlags, ByteBuffer data) {
        return NanoVG.nvgCreateImageMem(cx,imageFlags,data);
    }

    public static int createImageRGBA(int w, int h, int imageFlags, ByteBuffer data) {
        return NanoVG.nvgCreateImageRGBA(cx,w, h, imageFlags, data);
    }


    public static void updateImage(int image,ByteBuffer data) {
        NanoVG.nvgUpdateImage(cx,image,data);
    }

    public static void imageSize(int image, IntBuffer w, IntBuffer h) {
        NanoVG.nvgImageSize(cx,image,w,h);
    }

    public static void deleteImage(int image) {
        NanoVG.nvgDeleteImage(cx,image);
    }

    public static NVGPaint linearGradient(float sx, float sy, float ex, float ey, NVGColor from, NVGColor to, NVGPaint __result) {
        return NanoVG.nvgLinearGradient(cx,sx,sy,ex,ey,from,to,__result);
    }

    public static NVGPaint boxGradient(float x, float y, float w, float h, float r, float f, NVGColor from, NVGColor to, NVGPaint __result) {
        return NanoVG.nvgBoxGradient(cx,x,y,w,h,r,f,from,to, __result);
    }

    public static NVGPaint imagePattern(float ox, float oy, float ex, float ey, float angle, int image, float alpha, NVGPaint __result) {
        return NanoVG.nvgImagePattern(cx,ox,oy,ex,ey,angle,image,alpha,__result);
    }

    public static void scissor(float x, float y, float w, float h) {
        NanoVG.nvgScissor(cx,x,y,w,h);
    }

    public static void intersectScissor(float x, float y, float w, float h) {
        NanoVG.nvgIntersectScissor(cx,x,y,w,h);
    }

    public static void resetScissor() {
        NanoVG.nvgResetScissor(cx);
    }

    public static void beginPath() {
        NanoVG.nvgBeginPath(cx);
    }

    public static void moveTo(float x, float y) {
        NanoVG.nvgMoveTo(cx,x,y);
    }

    public static void lineTo(float x, float y) {
        NanoVG.nvgLineTo(cx,x,y);
    }

    public static void bezierTo(float cx1, float cy1, float cx2, float cy2, float x, float y) {
        NanoVG.nvgBezierTo(cx,cx1,cy1,cx2,cy2, x,y);
    }

    public static void guadTo(float cx, float cy, float x, float y) {
        NanoVG.nvgQuadTo(NVGWrapper.cx,cx,cy,x,y);
    }

    public static void arcTo(float x1, float y1, float x2, float y2, float radius) {
        NanoVG.nvgArcTo(cx,x1,y1,x2,y2,radius);
    }

    public static void closePath() {
        NanoVG.nvgClosePath(cx);
    }

    public static void pathWinding(int op) {
        NanoVG.nvgPathWinding(cx,op);
    }

    public static void rect(float x, float y, float w, float h) {
        NanoVG.nvgRect(cx,x,y,w,h);
    }

    public static void rrect(float x, float y, float w, float h, float rad) {
        NanoVG.nvgRoundedRect(cx,x,y,w,h,rad);
    }

    public static void rrect(float x, float y, float w, float h, float rTL, float rTR, float rBL, float rBR) {
        NanoVG.nvgRoundedRectVarying(cx,x,y,w,h,rTL,rTR,rBL,rBR);
    }

    public static void ellipse(float cx, float cy, float rx, float ry) {
        NanoVG.nvgEllipse(NVGWrapper.cx,cx,cy,rx,ry);
    }

    public static void circle(float x, float y, float r) {
        NanoVG.nvgCircle(cx,x,y,r);
    }

    public static void fill() {
        NanoVG.nvgFill(cx);
    }

    public static void stroke() {
        NanoVG.nvgStroke(cx);
    }

    public static int createFont(CharSequence name, CharSequence filename) {
        return NanoVG.nvgCreateFont(cx,name,filename);
    }

    public static int createFontAtIndex(CharSequence name, CharSequence filename, int index) {
        return NanoVG.nvgCreateFontAtIndex(cx,name,filename,index);
    }

    public static int createFontMem(CharSequence name, ByteBuffer data, int freeData) {
        return NanoVG.nvgCreateFontMem(cx,name,data,freeData);
    }

    public static int createFontMemAtIndex(CharSequence name, ByteBuffer data, int freeData, int index) {
        return NanoVG.nvgCreateFontMemAtIndex(cx,name,data,freeData,index);
    }

    public static int findFont(CharSequence name) {
        return NanoVG.nvgFindFont(cx, name);
    }

    public static int addFallbackFontID(int baseFont, int fallbackFont) {
        return NanoVG.nvgAddFallbackFontId(cx,baseFont,fallbackFont);
    }

    public static int addFallbackFont(CharSequence baseFont, CharSequence fallbackFont) {
        return NanoVG.nvgAddFallbackFont(cx,baseFont, fallbackFont);
    }

    public static void resetFallbackFontsID(int baseFont) {
        NanoVG.nvgResetFallbackFontsId(cx,baseFont);
    }

    public static void resetFallbackFonts(CharSequence baseFont) {
        NanoVG.nvgResetFallbackFonts(cx,baseFont);
    }

    public static void fontSize(float size) {
        NanoVG.nvgFontSize(cx,size);
    }

    public static void fontBlur(float blur) {
        NanoVG.nvgFontBlur(cx,blur);
    }

    public static void textLetterSpacing(float spacing) {
        NanoVG.nvgTextLetterSpacing(cx,spacing);
    }

    public static void textLineHeight(float lineHeight) {
        NanoVG.nvgTextLineHeight(cx,lineHeight);
    }

    public static void textAlign(int align) {
        NanoVG.nvgTextAlign(cx, align);
    }

    public static void fontFaceID(int font) {
        NanoVG.nvgFontFaceId(cx, font);
    }

    public static void fontFace(CharSequence font) {
        NanoVG.nvgFontFace(cx, font);
    }

    public static void text(CharSequence text, float x, float y) {
        NanoVG.nvgText(cx,x,y,text);
    }

    public static void textBox(float x, float y, float breakLine, CharSequence text) {
        NanoVG.nvgTextBox(cx,x,y,breakLine,text);
    }

    public static float textBounds(float x, float y, CharSequence text, FloatBuffer bounds) {
        return NanoVG.nvgTextBounds(cx,x,y,text,bounds);
    }

    public static void textBoxBounds(float x, float y, float breakRowWidth, CharSequence text, FloatBuffer bounds) {
        NanoVG.nvgTextBoxBounds(cx,x,y,breakRowWidth,text,bounds);
    }

    public static int textGlyphPositions(float x, float y, CharSequence text, NVGGlyphPosition.Buffer positions) {
        return NanoVG.nvgTextGlyphPositions(cx,x,y,text,positions);
    }

    public static void textMetrics(FloatBuffer ascend, FloatBuffer descend, FloatBuffer lineh) {
        NanoVG.nvgTextMetrics(cx,ascend,descend,lineh);
    }

    public static int textBreakLines(CharSequence text, float breakRowWidth, NVGTextRow.Buffer rows) {
        return NanoVG.nvgTextBreakLines(cx,text,breakRowWidth,rows);
    }
}