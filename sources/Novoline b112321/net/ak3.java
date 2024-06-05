package net;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager$TexGen;

public class ak3 {
   private static boolean b;

   public static void z() {
      GlStateManager.resetColor();
   }

   public static void n() {
      GlStateManager.pushMatrix();
   }

   public static void a(float var0, float var1, float var2) {
      GlStateManager.translate(var0, var1, var2);
   }

   public static void l(int var0) {
      GlStateManager.callList(var0);
   }

   public static void L() {
      GlStateManager.popMatrix();
   }

   public static void a(float var0, float var1, float var2, float var3) {
      GlStateManager.rotate(var0, var1, var2, var3);
   }

   public static void b(float var0, float var1, float var2, float var3) {
      GlStateManager.color(var0, var1, var2, var3);
   }

   public static void b(float var0, float var1, float var2) {
      GlStateManager.scale(var0, var1, var2);
   }

   public static void B() {
      GlStateManager.loadIdentity();
   }

   public static void a(int var0, FloatBuffer var1) {
      GlStateManager.getFloat(var0, var1);
   }

   public static void a(FloatBuffer var0) {
      GlStateManager.multMatrix(var0);
   }

   public static void d() {
      GlStateManager.bindCurrentTexture();
   }

   public static void v() {
      GlStateManager.enableRescaleNormal();
   }

   public static void d(boolean var0) {
      GlStateManager.depthMask(var0);
   }

   public static void c(int var0) {
      GlStateManager.depthFunc(var0);
   }

   public static void s() {
      GlStateManager.disableLighting();
   }

   public static void a(int var0, int var1) {
      GlStateManager.blendFunc(var0, var1);
   }

   public static void j(int var0) {
      GlStateManager.matrixMode(var0);
   }

   public static void o() {
      GlStateManager.enableLighting();
   }

   public static void a(int var0, float var1) {
      GlStateManager.alphaFunc(var0, var1);
   }

   public static void E() {
      GlStateManager.enableBlend();
   }

   public static void a(int var0, int var1, int var2, int var3) {
      GlStateManager.tryBlendFuncSeparate(var0, var1, var2, var3);
   }

   public static void k(int var0) {
      GlStateManager.cullFace(var0);
   }

   public static void h() {
      GlStateManager.disableRescaleNormal();
   }

   public static void g() {
      GlStateManager.disableBlend();
   }

   public static void l() {
      GlStateManager.enableAlpha();
   }

   public static void i() {
      GlStateManager.disableAlpha();
   }

   public static void j() {
      GlStateManager.disableDepth();
   }

   public static void D() {
      GlStateManager.enableDepth();
   }

   public static void I() {
      GlStateManager.disableTexture2D();
   }

   public static void J() {
      GlStateManager.enableTexture2D();
   }

   public static void h(int var0) {
      GlStateManager.disableLight(var0);
   }

   public static void x() {
      GlStateManager.disableColorMaterial();
   }

   public static void d(int var0) {
      GlStateManager.enableLight(var0);
   }

   public static void H() {
      GlStateManager.enableColorMaterial();
   }

   public static void b(int var0, int var1) {
      GlStateManager.colorMaterial(var0, var1);
   }

   public static void e(int var0) {
      GlStateManager.shadeModel(var0);
   }

   public static void a(boolean var0, boolean var1, boolean var2, boolean var3) {
      GlStateManager.colorMask(var0, var1, var2, var3);
   }

   public static void A() {
      GlStateManager.disableCull();
   }

   public static void p() {
      GlStateManager.pushAttrib();
   }

   public static void K() {
      GlStateManager.popAttrib();
   }

   public static void f(int var0) {
      GlStateManager.bindTexture(var0);
   }

   public static void a(double var0) {
      GlStateManager.clearDepth(var0);
   }

   public static void b(int var0, int var1, int var2, int var3) {
      GlStateManager.viewport(var0, var1, var2, var3);
   }

   public static void a(double var0, double var2, double var4, double var6, double var8, double var10) {
      GlStateManager.ortho(var0, var2, var4, var6, var8, var10);
   }

   public static void C() {
      GlStateManager.disableFog();
   }

   public static void a(int var0) {
      GlStateManager.clear(var0);
   }

   public static void b(double var0, double var2, double var4) {
      GlStateManager.scale(var0, var2, var4);
   }

   public static void a() {
      GlStateManager.enableCull();
   }

   public static void m(int var0) {
      GlStateManager.setActiveTexture(var0);
   }

   public static void a(IntBuffer var0) {
      GlStateManager.deleteTextures(var0);
   }

   public static void c(float var0, float var1, float var2, float var3) {
      GlStateManager.clearColor(var0, var1, var2, var3);
   }

   public static void c(float var0, float var1, float var2) {
      GlStateManager.color(var0, var1, var2);
   }

   public static void a(double var0, double var2, double var4) {
      GlStateManager.translate(var0, var2, var4);
   }

   public static int f() {
      return GlStateManager.getBoundTexture();
   }

   public static void a(float var0) {
      GlStateManager.setFogDensity(var0);
   }

   public static void g(int var0) {
      GlStateManager.setFog(var0);
   }

   public static void b(float var0) {
      GlStateManager.setFogStart(var0);
   }

   public static void c(float var0) {
      GlStateManager.setFogEnd(var0);
   }

   public static void b() {
      GlStateManager.enableFog();
   }

   public static void i(int var0) {
      GlStateManager.deleteTexture(var0);
   }

   public static void a(GlStateManager$TexGen var0, int var1) {
      GlStateManager.texGen(var0, var1);
   }

   public static void a(GlStateManager$TexGen var0, int var1, FloatBuffer var2) {
      GlStateManager.func_179105_a(var0, var1, var2);
   }

   public static void b(GlStateManager$TexGen var0) {
      GlStateManager.enableTexGenCoord(var0);
   }

   public static void a(GlStateManager$TexGen var0) {
      GlStateManager.disableTexGenCoord(var0);
   }

   public static int y() {
      return GlStateManager.getActiveTextureUnit();
   }

   public static void t() {
      GlStateManager.disableTextures();
   }

   public static void F() {
      GlStateManager.enableTextures();
   }

   public static void b(boolean var0) {
      GlStateManager.b(var0);
   }

   public static void a(boolean var0) {
      GlStateManager.a(var0);
   }

   public static void a(float var0, float var1) {
      GlStateManager.doPolygonOffset(var0, var1);
   }

   public static void k() {
      GlStateManager.enablePolygonOffset();
   }

   public static void e() {
      GlStateManager.disablePolygonOffset();
   }

   public static void w() {
      GlStateManager.enableColorLogic();
   }

   public static void b(int var0) {
      GlStateManager.colorLogicOp(var0);
   }

   public static void G() {
      GlStateManager.disableColorLogic();
   }

   public static int q() {
      return GlStateManager.generateTexture();
   }

   public static void c() {
      GlStateManager.o();
   }

   public static void m() {
      GlStateManager.v();
   }

   public static void c(boolean var0) {
      b = var0;
   }

   public static boolean u() {
      return b;
   }

   public static boolean r() {
      boolean var0 = u();
      return true;
   }

   static {
      if(u()) {
         c(true);
      }

   }
}
