package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.Ev;
import net.MK;
import net.OW;
import net.QW;
import net.U9;
import net.Z3;
import net._a;
import net.a8c;
import net.aNl;
import net.aYD;
import net.aYx;
import net.aaU;
import net.ail;
import net.ajc;
import net.iC;
import net.l2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager$GlStateManager$1;
import net.minecraft.client.renderer.GlStateManager$TexGen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.optifine.Config;
import org.lwjgl.opengl.GL11;

public class GlStateManager {
   private static Ev i = new Ev((GlStateManager$GlStateManager$1)null);
   private static aYx q = new aYx(2896);
   private static aYx[] l = new aYx[8];
   private static MK t = new MK((GlStateManager$GlStateManager$1)null);
   private static aNl j = new aNl((GlStateManager$GlStateManager$1)null);
   private static OW g = new OW((GlStateManager$GlStateManager$1)null);
   private static QW f = new QW((GlStateManager$GlStateManager$1)null);
   private static a8c u = new a8c((GlStateManager$GlStateManager$1)null);
   private static aYD o = new aYD((GlStateManager$GlStateManager$1)null);
   private static Z3 b = new Z3((GlStateManager$GlStateManager$1)null);
   private static aaU a = new aaU((GlStateManager$GlStateManager$1)null);
   private static _a h = new _a((GlStateManager$GlStateManager$1)null);
   private static iC r = new iC((GlStateManager$GlStateManager$1)null);
   private static aYx e = new aYx(2977);
   private static int activeTextureUnit = 0;
   private static U9[] d = new U9[32];
   private static int activeShadeModel = 7425;
   private static aYx p = new aYx('耺');
   private static ail m = new ail((GlStateManager$GlStateManager$1)null);
   private static l2 s = new l2();
   public static boolean clearEnabled = true;

   public static void pushAttrib() {
      GL11.glPushAttrib(8256);
   }

   public static void popAttrib() {
      GL11.glPopAttrib();
   }

   public static void disableAlpha() {
      i.a.a();
   }

   public static void enableAlpha() {
      i.a.b();
   }

   public static void alphaFunc(int var0, float var1) {
      if(var0 != i.b || var1 != i.d) {
         i.b = var0;
         i.d = var1;
         GL11.glAlphaFunc(var0, var1);
      }

   }

   public static void enableLighting() {
      q.b();
   }

   public static void disableLighting() {
      q.a();
   }

   public static void a(boolean var0) {
      q.b();
   }

   public static void b(boolean var0) {
      q.a();
   }

   public static void enableLight(int var0) {
      l[var0].b();
   }

   public static void disableLight(int var0) {
      l[var0].a();
   }

   public static void enableColorMaterial() {
      t.b.b();
   }

   public static void disableColorMaterial() {
      t.b.a();
   }

   public static void colorMaterial(int var0, int var1) {
      if(var0 != t.c || var1 != t.d) {
         t.c = var0;
         t.d = var1;
         GL11.glColorMaterial(var0, var1);
      }

   }

   public static void disableDepth() {
      g.a.a();
   }

   public static void enableDepth() {
      g.a.b();
   }

   public static void depthFunc(int var0) {
      if(var0 != g.d) {
         g.d = var0;
         GL11.glDepthFunc(var0);
      }

   }

   public static void depthMask(boolean var0) {
      if(var0 != g.c) {
         g.c = var0;
         GL11.glDepthMask(var0);
      }

   }

   public static void disableBlend() {
      j.a.a();
   }

   public static void enableBlend() {
      j.a.b();
   }

   public static void blendFunc(int var0, int var1) {
      if(var0 != j.e || var1 != j.d) {
         j.e = var0;
         j.d = var1;
         GL11.glBlendFunc(var0, var1);
      }

   }

   public static void tryBlendFuncSeparate(int var0, int var1, int var2, int var3) {
      if(var0 != j.e || var1 != j.d || var2 != j.f || var3 != j.b) {
         j.e = var0;
         j.d = var1;
         j.f = var2;
         j.b = var3;
         OpenGlHelper.glBlendFunc(var0, var1, var2, var3);
      }

   }

   public static void enableFog() {
      f.f.b();
   }

   public static void disableFog() {
      f.f.a();
   }

   public static void setFog(int var0) {
      if(var0 != f.b) {
         f.b = var0;
         GL11.glFogi(2917, var0);
      }

   }

   public static void setFogDensity(float var0) {
      if(var0 != f.a) {
         f.a = var0;
         GL11.glFogf(2914, var0);
      }

   }

   public static void setFogStart(float var0) {
      if(var0 != f.c) {
         f.c = var0;
         GL11.glFogf(2915, var0);
      }

   }

   public static void setFogEnd(float var0) {
      if(var0 != f.d) {
         f.d = var0;
         GL11.glFogf(2916, var0);
      }

   }

   public static void enableCull() {
      u.b.b();
   }

   public static void disableCull() {
      u.b.a();
   }

   public static void cullFace(int var0) {
      if(var0 != u.a) {
         u.a = var0;
         GL11.glCullFace(var0);
      }

   }

   public static void enablePolygonOffset() {
      o.e.b();
   }

   public static void disablePolygonOffset() {
      o.e.a();
   }

   public static void doPolygonOffset(float var0, float var1) {
      if(var0 != o.b || var1 != o.a) {
         o.b = var0;
         o.a = var1;
         GL11.glPolygonOffset(var0, var1);
      }

   }

   public static void enableColorLogic() {
      b.a.b();
   }

   public static void disableColorLogic() {
      b.a.a();
   }

   public static void colorLogicOp(int var0) {
      if(var0 != b.b) {
         b.b = var0;
         GL11.glLogicOp(var0);
      }

   }

   public static void enableTexGenCoord(GlStateManager$TexGen var0) {
      c(var0).b.b();
   }

   public static void disableTexGenCoord(GlStateManager$TexGen var0) {
      c(var0).b.a();
   }

   public static void texGen(GlStateManager$TexGen var0, int var1) {
      ajc var2 = c(var0);
      if(var1 != var2.d) {
         var2.d = var1;
         GL11.glTexGeni(var2.c, 9472, var1);
      }

   }

   public static void func_179105_a(GlStateManager$TexGen var0, int var1, FloatBuffer var2) {
      GL11.glTexGen(c(var0).c, var1, var2);
   }

   private static ajc c(GlStateManager$TexGen var0) {
      switch(GlStateManager$GlStateManager$1.field_179175_a[var0.ordinal()]) {
      case 1:
         return a.a;
      case 2:
         return a.b;
      case 3:
         return a.d;
      case 4:
         return a.e;
      default:
         return a.a;
      }
   }

   public static void setActiveTexture(int var0) {
      if(activeTextureUnit != var0 - OpenGlHelper.defaultTexUnit) {
         activeTextureUnit = var0 - OpenGlHelper.defaultTexUnit;
         OpenGlHelper.setActiveTexture(var0);
      }

   }

   public static void enableTexture2D() {
      d[activeTextureUnit].b.b();
   }

   public static void disableTexture2D() {
      d[activeTextureUnit].b.a();
   }

   public static int generateTexture() {
      return GL11.glGenTextures();
   }

   public static void deleteTexture(int var0) {
      GL11.glDeleteTextures(var0);

      for(U9 var4 : d) {
         if(var4.c == var0) {
            var4.c = 0;
         }
      }

   }

   public static void bindTexture(int var0) {
      if(var0 != d[activeTextureUnit].c) {
         d[activeTextureUnit].c = var0;
         GL11.glBindTexture(3553, var0);
      }

   }

   public static void bindCurrentTexture() {
      GL11.glBindTexture(3553, d[activeTextureUnit].c);
   }

   public static void o() {
      e.b();
   }

   public static void v() {
      e.a();
   }

   public static void shadeModel(int var0) {
      if(var0 != activeShadeModel) {
         activeShadeModel = var0;
         GL11.glShadeModel(var0);
      }

   }

   public static void enableRescaleNormal() {
      p.b();
   }

   public static void disableRescaleNormal() {
      p.a();
   }

   public static void viewport(int var0, int var1, int var2, int var3) {
      GL11.glViewport(var0, var1, var2, var3);
   }

   public static void colorMask(boolean var0, boolean var1, boolean var2, boolean var3) {
      if(var0 != m.c || var1 != m.a || var2 != m.d || var3 != m.e) {
         m.c = var0;
         m.a = var1;
         m.d = var2;
         m.e = var3;
         GL11.glColorMask(var0, var1, var2, var3);
      }

   }

   public static void clearDepth(double var0) {
      if(var0 != h.e) {
         if(Minecraft.getInstance().getCurrentServerData() == null && var0 != (double)h.c && Minecraft.getInstance().player != null || var0 == (double)h.c) {
            return;
         }

         h.e = var0;
         GL11.glClearDepth(var0);
      }

   }

   public static void clearColor(float var0, float var1, float var2, float var3) {
      if(var0 != h.a.a || var1 != h.a.c || var2 != h.a.b || var3 != h.a.e) {
         h.a.a = var0;
         h.a.c = var1;
         h.a.b = var2;
         h.a.e = var3;
         GL11.glClearColor(var0, var1, var2, var3);
      }

   }

   public static void clear(int var0) {
      if(clearEnabled) {
         GL11.glClear(var0);
      }

   }

   public static void matrixMode(int var0) {
      GL11.glMatrixMode(var0);
   }

   public static void loadIdentity() {
      GL11.glLoadIdentity();
   }

   public static void pushMatrix() {
      GL11.glPushMatrix();
   }

   public static void popMatrix() {
      GL11.glPopMatrix();
   }

   public static void getFloat(int var0, FloatBuffer var1) {
      GL11.glGetFloat(var0, var1);
   }

   public static void ortho(double var0, double var2, double var4, double var6, double var8, double var10) {
      GL11.glOrtho(var0, var2, var4, var6, var8, var10);
   }

   public static void rotate(float var0, float var1, float var2, float var3) {
      GL11.glRotatef(var0, var1, var2, var3);
   }

   public static void scale(float var0, float var1, float var2) {
      GL11.glScalef(var0, var1, var2);
   }

   public static void scale(double var0, double var2, double var4) {
      GL11.glScaled(var0, var2, var4);
   }

   public static void translate(float var0, float var1, float var2) {
      GL11.glTranslatef(var0, var1, var2);
   }

   public static void translate(double var0, double var2, double var4) {
      GL11.glTranslated(var0, var2, var4);
   }

   public static void multMatrix(FloatBuffer var0) {
      GL11.glMultMatrix(var0);
   }

   public static void color(float var0, float var1, float var2, float var3) {
      if(var0 != s.a || var1 != s.c || var2 != s.b || var3 != s.e) {
         s.a = var0;
         s.c = var1;
         s.b = var2;
         s.e = var3;
         GL11.glColor4f(var0, var1, var2, var3);
      }

   }

   public static void color(float var0, float var1, float var2) {
      color(var0, var1, var2, 1.0F);
   }

   public static void resetColor() {
      s.a = s.c = s.b = s.e = -1.0F;
   }

   public static void callList(int var0) {
      GL11.glCallList(var0);
   }

   public static int getActiveTextureUnit() {
      return OpenGlHelper.defaultTexUnit + activeTextureUnit;
   }

   public static int getBoundTexture() {
      return d[activeTextureUnit].c;
   }

   public static void checkBoundTexture() {
      if(Config.isMinecraftThread()) {
         int var0 = GL11.glGetInteger('蓠');
         int var1 = GL11.glGetInteger('聩');
         int var2 = getActiveTextureUnit();
         int var3 = getBoundTexture();
         if(var0 != var2 || var1 != var3) {
            Config.dbg("checkTexture: act: " + var2 + ", glAct: " + var0 + ", tex: " + var3 + ", glTex: " + var1);
         }
      }

   }

   public static void deleteTextures(IntBuffer var0) {
      var0.rewind();

      while(var0.position() < var0.limit()) {
         int var1 = var0.get();
         deleteTexture(var1);
      }

      var0.rewind();
   }

   public static void disableTextures() {
      d[activeTextureUnit].b.a();
   }

   public static void enableTextures() {
      d[activeTextureUnit].b.b();
   }

   public static void a(int var0) {
      l[var0].b();
   }

   public static void b(int var0) {
      l[var0].a();
   }

   static {
      for(int var7 = 0; var7 < 8; ++var7) {
         l[var7] = new aYx(16384 + var7);
      }

      for(int var8 = 0; var8 < d.length; ++var8) {
         d[var8] = new U9((GlStateManager$GlStateManager$1)null);
      }

   }
}
