package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import optifine.Config;
import org.lwjgl.opengl.GL11;

public class GlStateManager {
   private static GlStateManager.BooleanState rescaleNormalState = new GlStateManager.BooleanState(32826);
   private static GlStateManager.ColorLogicState colorLogicState = new GlStateManager.ColorLogicState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.StencilState stencilState = new GlStateManager.StencilState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.Color colorState = new GlStateManager.Color();
   private static GlStateManager.ClearState clearState = new GlStateManager.ClearState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.PolygonOffsetState polygonOffsetState = new GlStateManager.PolygonOffsetState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.ColorMaterialState colorMaterialState = new GlStateManager.ColorMaterialState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.BooleanState normalizeState = new GlStateManager.BooleanState(2977);
   private static GlStateManager.BooleanState lightingState = new GlStateManager.BooleanState(2896);
   private static final String __OBFID = "CL_00002558";
   public static boolean clearEnabled = true;
   private static GlStateManager.FogState fogState = new GlStateManager.FogState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.ColorMask colorMaskState = new GlStateManager.ColorMask((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.DepthState depthState = new GlStateManager.DepthState((GlStateManager.SwitchTexGen)null);
   private static int field_179173_q = 7425;
   private static GlStateManager.TextureState[] field_179174_p = new GlStateManager.TextureState[32];
   private static GlStateManager.BlendState blendState = new GlStateManager.BlendState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.Viewport field_179169_u = new GlStateManager.Viewport((GlStateManager.SwitchTexGen)null);
   private static int field_179162_o = 0;
   private static GlStateManager.CullState cullState = new GlStateManager.CullState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.TexGenState texGenState = new GlStateManager.TexGenState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.AlphaState alphaState = new GlStateManager.AlphaState((GlStateManager.SwitchTexGen)null);
   private static GlStateManager.BooleanState[] field_179159_c = new GlStateManager.BooleanState[8];

   public static void shadeModel(int var0) {
      if (var0 != field_179173_q) {
         field_179173_q = var0;
         GL11.glShadeModel(var0);
      }

   }

   public static int getActiveTextureUnit() {
      return OpenGlHelper.defaultTexUnit + field_179162_o;
   }

   public static void enableAlpha() {
      alphaState.field_179208_a.setEnabled();
   }

   public static void ortho(double var0, double var2, double var4, double var6, double var8, double var10) {
      GL11.glOrtho(var0, var2, var4, var6, var8, var10);
   }

   public static void enableFog() {
      fogState.field_179049_a.setEnabled();
   }

   public static void clearDepth(double var0) {
      if (var0 != clearState.field_179205_a) {
         clearState.field_179205_a = var0;
         GL11.glClearDepth(var0);
      }

   }

   public static void disableDepth() {
      depthState.field_179052_a.setDisabled();
   }

   public static void viewport(int var0, int var1, int var2, int var3) {
      if (var0 != field_179169_u.field_179058_a || var1 != field_179169_u.field_179056_b || var2 != field_179169_u.field_179057_c || var3 != field_179169_u.field_179055_d) {
         field_179169_u.field_179058_a = var0;
         field_179169_u.field_179056_b = var1;
         field_179169_u.field_179057_c = var2;
         field_179169_u.field_179055_d = var3;
         GL11.glViewport(var0, var1, var2, var3);
      }

   }

   public static void translate(float var0, float var1, float var2) {
      GL11.glTranslatef(var0, var1, var2);
   }

   public static void disableColorLogic() {
      colorLogicState.field_179197_a.setDisabled();
   }

   public static void pushAttrib() {
      GL11.glPushAttrib(8256);
   }

   public static void clear(int var0) {
      if (clearEnabled) {
         GL11.glClear(var0);
      }

   }

   public static void enableRescaleNormal() {
      rescaleNormalState.setEnabled();
   }

   public static void scale(float var0, float var1, float var2) {
      GL11.glScalef(var0, var1, var2);
   }

   public static void enableColorLogic() {
      colorLogicState.field_179197_a.setEnabled();
   }

   public static void translate(double var0, double var2, double var4) {
      GL11.glTranslated(var0, var2, var4);
   }

   public static void callList(int var0) {
      GL11.glCallList(var0);
   }

   public static void setFogDensity(float var0) {
      if (var0 != fogState.field_179048_c) {
         fogState.field_179048_c = var0;
         GL11.glFogf(2914, var0);
      }

   }

   public static void disableLighting() {
      lightingState.setDisabled();
   }

   public static void disableBooleanStateAt(int var0) {
      field_179159_c[var0].setDisabled();
   }

   public static void func_179098_w() {
      field_179174_p[field_179162_o].field_179060_a.setEnabled();
   }

   public static void texGen(GlStateManager.TexGen var0, int var1) {
      GlStateManager.TexGenCoord var2 = texGenCoord(var0);
      if (var1 != var2.field_179066_c) {
         var2.field_179066_c = var1;
         GL11.glTexGeni(var2.field_179065_b, 9472, var1);
      }

   }

   public static void color(float var0, float var1, float var2, float var3) {
      if (var0 != colorState.field_179195_a || var1 != colorState.green || var2 != colorState.blue || var3 != colorState.alpha) {
         colorState.field_179195_a = var0;
         colorState.green = var1;
         colorState.blue = var2;
         colorState.alpha = var3;
         GL11.glColor4f(var0, var1, var2, var3);
      }

   }

   public static void func_179105_a(GlStateManager.TexGen var0, int var1, FloatBuffer var2) {
      GL11.glTexGen(texGenCoord(var0).field_179065_b, var1, var2);
   }

   public static void disableRescaleNormal() {
      rescaleNormalState.setDisabled();
   }

   public static void deleteTextures(IntBuffer var0) {
      var0.rewind();

      while(var0.position() < var0.limit()) {
         int var1 = var0.get();
         func_179150_h(var1);
      }

      var0.rewind();
   }

   public static void disableTexGenCoord(GlStateManager.TexGen var0) {
      texGenCoord(var0).field_179067_a.setDisabled();
   }

   public static void enableDepth() {
      depthState.field_179052_a.setEnabled();
   }

   public static void enablePolygonOffset() {
      polygonOffsetState.field_179044_a.setEnabled();
   }

   public static void loadIdentity() {
      GL11.glLoadIdentity();
   }

   public static void disableColorMaterial() {
      colorMaterialState.field_179191_a.setDisabled();
   }

   public static void bindCurrentTexture() {
      GL11.glBindTexture(3553, field_179174_p[field_179162_o].field_179059_b);
   }

   public static void enableColorMaterial() {
      colorMaterialState.field_179191_a.setEnabled();
   }

   private static GlStateManager.TexGenCoord texGenCoord(GlStateManager.TexGen var0) {
      switch(var0) {
      case S:
         return texGenState.field_179064_a;
      case T:
         return texGenState.field_179062_b;
      case R:
         return texGenState.field_179063_c;
      case Q:
         return texGenState.field_179061_d;
      default:
         return texGenState.field_179064_a;
      }
   }

   public static void disableAlpha() {
      alphaState.field_179208_a.setDisabled();
   }

   static {
      int var0;
      for(var0 = 0; var0 < 8; ++var0) {
         field_179159_c[var0] = new GlStateManager.BooleanState(16384 + var0);
      }

      for(var0 = 0; var0 < field_179174_p.length; ++var0) {
         field_179174_p[var0] = new GlStateManager.TextureState((GlStateManager.SwitchTexGen)null);
      }

   }

   public static void func_179144_i(int var0) {
      if (var0 != field_179174_p[field_179162_o].field_179059_b) {
         field_179174_p[field_179162_o].field_179059_b = var0;
         GL11.glBindTexture(3553, var0);
      }

   }

   public static void colorMaterial(int var0, int var1) {
      if (var0 != colorMaterialState.field_179189_b || var1 != colorMaterialState.field_179190_c) {
         colorMaterialState.field_179189_b = var0;
         colorMaterialState.field_179190_c = var1;
         GL11.glColorMaterial(var0, var1);
      }

   }

   public static void tryBlendFuncSeparate(int var0, int var1, int var2, int var3) {
      if (var0 != blendState.field_179211_b || var1 != blendState.field_179212_c || var2 != blendState.field_179209_d || var3 != blendState.field_179210_e) {
         blendState.field_179211_b = var0;
         blendState.field_179212_c = var1;
         blendState.field_179209_d = var2;
         blendState.field_179210_e = var3;
         OpenGlHelper.glBlendFunc(var0, var1, var2, var3);
      }

   }

   public static void setFogEnd(float var0) {
      if (var0 != fogState.field_179046_e) {
         fogState.field_179046_e = var0;
         GL11.glFogf(2916, var0);
      }

   }

   public static void depthFunc(int var0) {
      if (var0 != depthState.field_179051_c) {
         depthState.field_179051_c = var0;
         GL11.glDepthFunc(var0);
      }

   }

   public static int func_179146_y() {
      return GL11.glGenTextures();
   }

   public static void matrixMode(int var0) {
      GL11.glMatrixMode(var0);
   }

   public static void enableBlend() {
      blendState.field_179213_a.setEnabled();
   }

   public static void enableNormalize() {
      normalizeState.setEnabled();
   }

   public static void pushMatrix() {
      GL11.glPushMatrix();
   }

   public static void disableCull() {
      cullState.field_179054_a.setDisabled();
   }

   public static void disablePolygonOffset() {
      polygonOffsetState.field_179044_a.setDisabled();
   }

   public static void disableBlend() {
      blendState.field_179213_a.setDisabled();
   }

   public static void rotate(float var0, float var1, float var2, float var3) {
      GL11.glRotatef(var0, var1, var2, var3);
   }

   public static void setActiveTexture(int var0) {
      if (field_179162_o != var0 - OpenGlHelper.defaultTexUnit) {
         field_179162_o = var0 - OpenGlHelper.defaultTexUnit;
         OpenGlHelper.setActiveTexture(var0);
      }

   }

   public static void color(float var0, float var1, float var2) {
      color(var0, var1, var2, 1.0F);
   }

   public static void enableTexGenCoord(GlStateManager.TexGen var0) {
      texGenCoord(var0).field_179067_a.setEnabled();
   }

   public static void doPolygonOffset(float var0, float var1) {
      if (var0 != polygonOffsetState.field_179043_c || var1 != polygonOffsetState.field_179041_d) {
         polygonOffsetState.field_179043_c = var0;
         polygonOffsetState.field_179041_d = var1;
         GL11.glPolygonOffset(var0, var1);
      }

   }

   public static void disableFog() {
      fogState.field_179049_a.setDisabled();
   }

   public static void colorMask(boolean var0, boolean var1, boolean var2, boolean var3) {
      if (var0 != colorMaskState.field_179188_a || var1 != colorMaskState.field_179186_b || var2 != colorMaskState.field_179187_c || var3 != colorMaskState.field_179185_d) {
         colorMaskState.field_179188_a = var0;
         colorMaskState.field_179186_b = var1;
         colorMaskState.field_179187_c = var2;
         colorMaskState.field_179185_d = var3;
         GL11.glColorMask(var0, var1, var2, var3);
      }

   }

   public static int getBoundTexture() {
      return field_179174_p[field_179162_o].field_179059_b;
   }

   public static void popAttrib() {
      GL11.glPopAttrib();
   }

   public static void enableBooleanStateAt(int var0) {
      field_179159_c[var0].setEnabled();
   }

   public static void popMatrix() {
      GL11.glPopMatrix();
   }

   public static void alphaFunc(int var0, float var1) {
      if (var0 != alphaState.field_179206_b || var1 != alphaState.field_179207_c) {
         alphaState.field_179206_b = var0;
         alphaState.field_179207_c = var1;
         GL11.glAlphaFunc(var0, var1);
      }

   }

   public static void checkBoundTexture() {
      if (Config.isMinecraftThread()) {
         int var0 = GL11.glGetInteger(34016);
         int var1 = GL11.glGetInteger(32873);
         int var2 = getActiveTextureUnit();
         int var3 = getBoundTexture();
         if (var3 > 0 && (var0 != var2 || var1 != var3)) {
            Config.dbg(String.valueOf((new StringBuilder("checkTexture: act: ")).append(var2).append(", glAct: ").append(var0).append(", tex: ").append(var3).append(", glTex: ").append(var1)));
         }
      }

   }

   public static void setFog(int var0) {
      if (var0 != fogState.field_179047_b) {
         fogState.field_179047_b = var0;
         GL11.glFogi(2917, var0);
      }

   }

   public static void multMatrix(FloatBuffer var0) {
      GL11.glMultMatrix(var0);
   }

   public static void enableLighting() {
      lightingState.setEnabled();
   }

   public static void scale(double var0, double var2, double var4) {
      GL11.glScaled(var0, var2, var4);
   }

   public static void func_179117_G() {
      colorState.field_179195_a = colorState.green = colorState.blue = colorState.alpha = -1.0F;
   }

   public static void func_179090_x() {
      field_179174_p[field_179162_o].field_179060_a.setDisabled();
   }

   public static void disableNormalize() {
      normalizeState.setDisabled();
   }

   public static void getFloat(int var0, FloatBuffer var1) {
      GL11.glGetFloat(var0, var1);
   }

   public static void blendFunc(int var0, int var1) {
      if (var0 != blendState.field_179211_b || var1 != blendState.field_179212_c) {
         blendState.field_179211_b = var0;
         blendState.field_179212_c = var1;
         GL11.glBlendFunc(var0, var1);
      }

   }

   public static void depthMask(boolean var0) {
      if (var0 != depthState.field_179050_b) {
         depthState.field_179050_b = var0;
         GL11.glDepthMask(var0);
      }

   }

   public static void colorLogicOp(int var0) {
      if (var0 != colorLogicState.field_179196_b) {
         colorLogicState.field_179196_b = var0;
         GL11.glLogicOp(var0);
      }

   }

   public static void setFogStart(float var0) {
      if (var0 != fogState.field_179045_d) {
         fogState.field_179045_d = var0;
         GL11.glFogf(2915, var0);
      }

   }

   public static void cullFace(int var0) {
      if (var0 != cullState.field_179053_b) {
         cullState.field_179053_b = var0;
         GL11.glCullFace(var0);
      }

   }

   public static void clearColor(float var0, float var1, float var2, float var3) {
      if (var0 != clearState.field_179203_b.field_179195_a || var1 != clearState.field_179203_b.green || var2 != clearState.field_179203_b.blue || var3 != clearState.field_179203_b.alpha) {
         clearState.field_179203_b.field_179195_a = var0;
         clearState.field_179203_b.green = var1;
         clearState.field_179203_b.blue = var2;
         clearState.field_179203_b.alpha = var3;
         GL11.glClearColor(var0, var1, var2, var3);
      }

   }

   public static void func_179150_h(int var0) {
      if (var0 != 0) {
         GL11.glDeleteTextures(var0);
         GlStateManager.TextureState[] var1 = field_179174_p;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            GlStateManager.TextureState var4 = var1[var3];
            if (var4.field_179059_b == var0) {
               var4.field_179059_b = 0;
            }
         }
      }

   }

   public static void enableCull() {
      cullState.field_179054_a.setEnabled();
   }

   static class PolygonOffsetState {
      public float field_179041_d;
      public float field_179043_c;
      public GlStateManager.BooleanState field_179044_a;
      public GlStateManager.BooleanState field_179042_b;

      PolygonOffsetState(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private PolygonOffsetState() {
         this.field_179044_a = new GlStateManager.BooleanState(32823);
         this.field_179042_b = new GlStateManager.BooleanState(10754);
         this.field_179043_c = 0.0F;
         this.field_179041_d = 0.0F;
      }
   }

   static class DepthState {
      public int field_179051_c;
      public boolean field_179050_b;
      public GlStateManager.BooleanState field_179052_a;

      DepthState(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private DepthState() {
         this.field_179052_a = new GlStateManager.BooleanState(2929);
         this.field_179050_b = true;
         this.field_179051_c = 513;
      }
   }

   static class AlphaState {
      private static final String __OBFID = "CL_00002556";
      public float field_179207_c;
      public GlStateManager.BooleanState field_179208_a;
      public int field_179206_b;

      AlphaState(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private AlphaState() {
         this.field_179208_a = new GlStateManager.BooleanState(3008);
         this.field_179206_b = 519;
         this.field_179207_c = -1.0F;
      }
   }

   static class ColorMaterialState {
      public GlStateManager.BooleanState field_179191_a;
      public int field_179189_b;
      public int field_179190_c;

      ColorMaterialState(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private ColorMaterialState() {
         this.field_179191_a = new GlStateManager.BooleanState(2903);
         this.field_179189_b = 1032;
         this.field_179190_c = 5634;
      }
   }

   static final class SwitchTexGen {
      static final int[] field_179175_a = new int[GlStateManager.TexGen.values().length];

      static {
         try {
            field_179175_a[GlStateManager.TexGen.S.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_179175_a[GlStateManager.TexGen.T.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_179175_a[GlStateManager.TexGen.R.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_179175_a[GlStateManager.TexGen.Q.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   static class ColorLogicState {
      public int field_179196_b;
      public GlStateManager.BooleanState field_179197_a;

      ColorLogicState(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private ColorLogicState() {
         this.field_179197_a = new GlStateManager.BooleanState(3058);
         this.field_179196_b = 5379;
      }
   }

   static class TexGenState {
      public GlStateManager.TexGenCoord field_179063_c;
      public GlStateManager.TexGenCoord field_179061_d;
      public GlStateManager.TexGenCoord field_179062_b;
      public GlStateManager.TexGenCoord field_179064_a;

      private TexGenState() {
         this.field_179064_a = new GlStateManager.TexGenCoord(8192, 3168);
         this.field_179062_b = new GlStateManager.TexGenCoord(8193, 3169);
         this.field_179063_c = new GlStateManager.TexGenCoord(8194, 3170);
         this.field_179061_d = new GlStateManager.TexGenCoord(8195, 3171);
      }

      TexGenState(GlStateManager.SwitchTexGen var1) {
         this();
      }
   }

   static class TextureState {
      public GlStateManager.BooleanState field_179060_a;
      public int field_179059_b;

      TextureState(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private TextureState() {
         this.field_179060_a = new GlStateManager.BooleanState(3553);
         this.field_179059_b = 0;
      }
   }

   static class BlendState {
      public int field_179212_c;
      public int field_179211_b;
      public int field_179210_e;
      public int field_179209_d;
      public GlStateManager.BooleanState field_179213_a;

      BlendState(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private BlendState() {
         this.field_179213_a = new GlStateManager.BooleanState(3042);
         this.field_179211_b = 1;
         this.field_179212_c = 0;
         this.field_179209_d = 1;
         this.field_179210_e = 0;
      }
   }

   static class ColorMask {
      public boolean field_179186_b;
      public boolean field_179188_a;
      public boolean field_179187_c;
      public boolean field_179185_d;

      ColorMask(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private ColorMask() {
         this.field_179188_a = true;
         this.field_179186_b = true;
         this.field_179187_c = true;
         this.field_179185_d = true;
      }
   }

   static class BooleanState {
      private final int capability;
      private boolean currentState = false;

      public void setDisabled() {
         this.setState(false);
      }

      public void setEnabled() {
         this.setState(true);
      }

      public BooleanState(int var1) {
         this.capability = var1;
      }

      public void setState(boolean var1) {
         if (var1 != this.currentState) {
            this.currentState = var1;
            if (var1) {
               GL11.glEnable(this.capability);
            } else {
               GL11.glDisable(this.capability);
            }
         }

      }
   }

   public static enum TexGen {
      private static final GlStateManager.TexGen[] $VALUES = new GlStateManager.TexGen[]{S, T, R, Q};
      private static final String __OBFID = "CL_00002542";
      S("S", 0, "S", 0);

      private static final GlStateManager.TexGen[] ENUM$VALUES = new GlStateManager.TexGen[]{S, T, R, Q};
      R("R", 2, "R", 2),
      T("T", 1, "T", 1),
      Q("Q", 3, "Q", 3);

      private TexGen(String var3, int var4, String var5, int var6) {
      }
   }

   static class Viewport {
      public int field_179055_d;
      public int field_179056_b;
      public int field_179058_a;
      public int field_179057_c;

      Viewport(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private Viewport() {
         this.field_179058_a = 0;
         this.field_179056_b = 0;
         this.field_179057_c = 0;
         this.field_179055_d = 0;
      }
   }

   static class Color {
      public float green = 1.0F;
      public float field_179195_a = 1.0F;
      public float alpha = 1.0F;
      public float blue = 1.0F;

      public Color(float var1, float var2, float var3, float var4) {
         this.field_179195_a = var1;
         this.green = var2;
         this.blue = var3;
         this.alpha = var4;
      }

      public Color() {
      }
   }

   static class StencilFunc {
      public int field_179079_b;
      public int field_179080_c;
      public int field_179081_a;

      private StencilFunc() {
         this.field_179081_a = 519;
         this.field_179079_b = 0;
         this.field_179080_c = -1;
      }

      StencilFunc(GlStateManager.SwitchTexGen var1) {
         this();
      }
   }

   static class StencilState {
      public int field_179075_e;
      public int field_179074_d;
      public int field_179077_c;
      public int field_179076_b;
      public GlStateManager.StencilFunc field_179078_a;

      StencilState(GlStateManager.SwitchTexGen var1) {
         this();
      }

      private StencilState() {
         this.field_179078_a = new GlStateManager.StencilFunc((GlStateManager.SwitchTexGen)null);
         this.field_179076_b = -1;
         this.field_179077_c = 7680;
         this.field_179074_d = 7680;
         this.field_179075_e = 7680;
      }
   }

   static class TexGenCoord {
      public int field_179066_c = -1;
      public int field_179065_b;
      public GlStateManager.BooleanState field_179067_a;

      public TexGenCoord(int var1, int var2) {
         this.field_179065_b = var1;
         this.field_179067_a = new GlStateManager.BooleanState(var2);
      }
   }

   static class FogState {
      public GlStateManager.BooleanState field_179049_a;
      public float field_179046_e;
      public float field_179045_d;
      public int field_179047_b;
      public float field_179048_c;

      private FogState() {
         this.field_179049_a = new GlStateManager.BooleanState(2912);
         this.field_179047_b = 2048;
         this.field_179048_c = 1.0F;
         this.field_179045_d = 0.0F;
         this.field_179046_e = 1.0F;
      }

      FogState(GlStateManager.SwitchTexGen var1) {
         this();
      }
   }

   static class CullState {
      public GlStateManager.BooleanState field_179054_a;
      public int field_179053_b;

      private CullState() {
         this.field_179054_a = new GlStateManager.BooleanState(2884);
         this.field_179053_b = 1029;
      }

      CullState(GlStateManager.SwitchTexGen var1) {
         this();
      }
   }

   static class ClearState {
      public int field_179204_c;
      public GlStateManager.Color field_179203_b;
      public double field_179205_a;

      private ClearState() {
         this.field_179205_a = 1.0D;
         this.field_179203_b = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
         this.field_179204_c = 0;
      }

      ClearState(GlStateManager.SwitchTexGen var1) {
         this();
      }
   }
}
