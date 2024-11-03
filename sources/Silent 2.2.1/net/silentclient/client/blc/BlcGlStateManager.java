package net.silentclient.client.blc;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;

public class BlcGlStateManager {
	
    public static void a() {
//        GlStateManager.get
    }
    
    
    public static void b() {
        GlStateManager.disableAlpha();
    }
    
    
    public static void a(final int n, final float n2) {
        GlStateManager.enableAlpha();
    }
    
    
    public static void c() {
        GlStateManager.enableLighting();
    }
    
    
    public static void d() {
    	GlStateManager.disableLighting();
    }
    
    
    public static void e() {
        GlStateManager.disableDepth();
    }
    
    
    public static void f() {
    	GlStateManager.enableDepth();
    }
    
    
    public static void a(final int n) {
        GlStateManager.depthFunc(n);
    }
    
    
    public static void a(final boolean b) {
        GlStateManager.depthMask(b);
    }
    
    
    public static void g() {
        GlStateManager.enableBlend();
    }
    
    
    public static void h() {
        GlStateManager.disableBlend();
    }
    
    
    public static void a(final int n, final int n2) {
        GlStateManager.blendFunc(n, n2);
    }
    
    
    public static void a(final int n, final int n2, final int n3, final int n4) {
        GlStateManager.tryBlendFuncSeparate(n, n2, n3, n4);
    }
    
    
    public static void i() {
        GlStateManager.enableFog();
    }
    
    
    public static void j() {
        GlStateManager.disableFog();
    }
    
    
    public static void b(final int n) {
        GlStateManager.setFog(n);
    }
    
    
    public static void a(final float n) {
        GlStateManager.setFogDensity(n);
    }
    
    
    public static void b(final float n) {
        GlStateManager.setFogStart(n);
    }
    
    
    public static void c(final float n) {
        GlStateManager.setFogEnd(n);
    }
    
    
    public static void k() {
        GlStateManager.enableCull();
    }
    
    
    public static void l() {
        GlStateManager.disableCull();
    }
    
    
    public static void c(final int n) {
        GlStateManager.cullFace(n);
    }
    
    
    public static void m() {
        GlStateManager.enablePolygonOffset();
    }
    
    
    public static void n() {
        GlStateManager.disablePolygonOffset();
    }
    
    
    public static void a(final float n, final float n2) {
        GlStateManager.doPolygonOffset(n, n2);
    }
    
    
    public static void o() {
        GlStateManager.enableColorLogic();
    }
    
    
    public static void p() {
        GlStateManager.disableColorLogic();
    }
    
    
    public static void d(final int n) {
        GlStateManager.colorLogicOp(n);
    }
    
    
    public static void e(final int n) {
        GlStateManager.setActiveTexture(n);
    }
    
    
    public static void q() {
        GlStateManager.enableTexture2D();
    }
    
    
    public static void r() {
        GlStateManager.disableTexture2D();
    }
    
    
    public static void a(final float n, final float n2, final float n3, final float n4) {
        GlStateManager.color(n, n2, n3, n4);
    }
    
    
    public static void a(final float n, final float n2, final float n3) {
        GlStateManager.color(n, n2, n3);
    }
    
    
    public static void s() {
        GlStateManager.resetColor();
    }
    
    
    public static void f(final int n) {
        GlStateManager.callList(n);
    }
    
    
    public static void t() {
        GlStateManager.pushMatrix();
    }
    
    
    public static void u() {
        GlStateManager.popMatrix();
    }
    
    
    public static void b(final float n, final float n2, final float n3, final float n4) {
        GlStateManager.rotate(n, n2, n3, n4);
    }
    
    
    public static void b(final float n, final float n2, final float n3) {
        GlStateManager.scale(n, n2, n3);
    }
    
    
    public static void a(final double n, final double n2, final double n3) {
        GlStateManager.scale(n, n2, n3);
    }
    
    
    public static void c(final float n, final float n2, final float n3) {
        GlStateManager.translate(n, n2, n3);
    }
    
    
    public static void b(final double n, final double n2, final double n3) {
        GlStateManager.translate(n, n2, n3);
    }
    
    
    public static void b(final int n, final int n2, final int n3, final int n4) {
        OpenGlHelper.glBlendFunc(n, n2, n3, n4);
    }
    
    
    public static boolean v() {
        return OpenGlHelper.areShadersSupported();
    }
    
    
    public static void w() {
        RenderHelper.enableStandardItemLighting();
    }
    
    
    public static void x() {
    	RenderHelper.disableStandardItemLighting();
    }
    
    
    public static void y() {
    	RenderHelper.enableGUIStandardItemLighting();
    }
    
    
    public static void g(final int n) {
        GlStateManager.shadeModel(n);
    }
    
    
    public static boolean z() {
        return GLContext.getCapabilities().OpenGL15;
    }
    
    
    public static boolean A() {
        return GLContext.getCapabilities().OpenGL21;
    }
    
    
    public static void a(final int n, final IntBuffer intBuffer) {
        GL11.glGetInteger(n, intBuffer);
    }
    
    
    public static void a(final int n, final FloatBuffer floatBuffer) {
        GL11.glGetFloat(n, floatBuffer);
    }
    
    
    public static void h(final int n) {
        GlStateManager.clear(n);
    }
}
