package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;

public class GlStateManager
{
  private static AlphaState alphaState = new AlphaState(null);
  private static BooleanState lightingState = new BooleanState(2896);
  private static BooleanState[] field_179159_c = new BooleanState[8];
  private static ColorMaterialState colorMaterialState = new ColorMaterialState(null);
  private static BlendState blendState = new BlendState(null);
  private static DepthState depthState = new DepthState(null);
  private static FogState fogState = new FogState(null);
  private static CullState cullState = new CullState(null);
  private static PolygonOffsetState polygonOffsetState = new PolygonOffsetState(null);
  private static ColorLogicState colorLogicState = new ColorLogicState(null);
  private static TexGenState texGenState = new TexGenState(null);
  private static ClearState clearState = new ClearState(null);
  private static StencilState stencilState = new StencilState(null);
  private static BooleanState normalizeState = new BooleanState(2977);
  private static int field_179162_o = 0;
  private static TextureState[] field_179174_p = new TextureState[8];
  private static int field_179173_q = 7425;
  private static BooleanState rescaleNormalState = new BooleanState(32826);
  private static ColorMask colorMaskState = new ColorMask(null);
  private static Color colorState = new Color();
  private static Viewport field_179169_u = new Viewport(null);
  private static final String __OBFID = "CL_00002558";
  
  public static void pushAttrib()
  {
    GL11.glPushAttrib(8256);
  }
  





  public static void disableAlpha()
  {
    alphaStatefield_179208_a.setDisabled();
  }
  
  public static void enableAlpha()
  {
    alphaStatefield_179208_a.setEnabled();
  }
  
  public static void alphaFunc(int p_179092_0_, float p_179092_1_)
  {
    if ((p_179092_0_ != alphaStatefield_179206_b) || (p_179092_1_ != alphaStatefield_179207_c))
    {
      alphaStatefield_179206_b = p_179092_0_;
      alphaStatefield_179207_c = p_179092_1_;
      GL11.glAlphaFunc(p_179092_0_, p_179092_1_);
    }
  }
  
  public static void enableLighting()
  {
    lightingState.setEnabled();
  }
  
  public static void disableLighting()
  {
    lightingState.setDisabled();
  }
  
  public static void enableBooleanStateAt(int p_179085_0_)
  {
    field_179159_c[p_179085_0_].setEnabled();
  }
  
  public static void disableBooleanStateAt(int p_179122_0_)
  {
    field_179159_c[p_179122_0_].setDisabled();
  }
  
  public static void enableColorMaterial()
  {
    colorMaterialStatefield_179191_a.setEnabled();
  }
  
  public static void disableColorMaterial()
  {
    colorMaterialStatefield_179191_a.setDisabled();
  }
  
  public static void colorMaterial(int p_179104_0_, int p_179104_1_)
  {
    if ((p_179104_0_ != colorMaterialStatefield_179189_b) || (p_179104_1_ != colorMaterialStatefield_179190_c))
    {
      colorMaterialStatefield_179189_b = p_179104_0_;
      colorMaterialStatefield_179190_c = p_179104_1_;
      GL11.glColorMaterial(p_179104_0_, p_179104_1_);
    }
  }
  
  public static void disableDepth()
  {
    depthStatefield_179052_a.setDisabled();
  }
  
  public static void enableDepth()
  {
    depthStatefield_179052_a.setEnabled();
  }
  
  public static void depthFunc(int p_179143_0_)
  {
    if (p_179143_0_ != depthStatefield_179051_c)
    {
      depthStatefield_179051_c = p_179143_0_;
      GL11.glDepthFunc(p_179143_0_);
    }
  }
  
  public static void depthMask(boolean p_179132_0_)
  {
    if (p_179132_0_ != depthStatefield_179050_b)
    {
      depthStatefield_179050_b = p_179132_0_;
      GL11.glDepthMask(p_179132_0_);
    }
  }
  
  public static void disableBlend()
  {
    blendStatefield_179213_a.setDisabled();
  }
  
  public static void enableBlend()
  {
    blendStatefield_179213_a.setEnabled();
  }
  
  public static void blendFunc(int p_179112_0_, int p_179112_1_)
  {
    if ((p_179112_0_ != blendStatefield_179211_b) || (p_179112_1_ != blendStatefield_179212_c))
    {
      blendStatefield_179211_b = p_179112_0_;
      blendStatefield_179212_c = p_179112_1_;
      GL11.glBlendFunc(p_179112_0_, p_179112_1_);
    }
  }
  
  public static void tryBlendFuncSeparate(int p_179120_0_, int p_179120_1_, int p_179120_2_, int p_179120_3_)
  {
    if ((p_179120_0_ != blendStatefield_179211_b) || (p_179120_1_ != blendStatefield_179212_c) || (p_179120_2_ != blendStatefield_179209_d) || (p_179120_3_ != blendStatefield_179210_e))
    {
      blendStatefield_179211_b = p_179120_0_;
      blendStatefield_179212_c = p_179120_1_;
      blendStatefield_179209_d = p_179120_2_;
      blendStatefield_179210_e = p_179120_3_;
      OpenGlHelper.glBlendFunc(p_179120_0_, p_179120_1_, p_179120_2_, p_179120_3_);
    }
  }
  
  public static void enableFog()
  {
    fogStatefield_179049_a.setEnabled();
  }
  
  public static void disableFog()
  {
    fogStatefield_179049_a.setDisabled();
  }
  
  public static void setFog(int p_179093_0_)
  {
    if (p_179093_0_ != fogStatefield_179047_b)
    {
      fogStatefield_179047_b = p_179093_0_;
      GL11.glFogi(2917, p_179093_0_);
    }
  }
  
  public static void setFogDensity(float p_179095_0_)
  {
    if (p_179095_0_ != fogStatefield_179048_c)
    {
      fogStatefield_179048_c = p_179095_0_;
      GL11.glFogf(2914, p_179095_0_);
    }
  }
  
  public static void setFogStart(float p_179102_0_)
  {
    if (p_179102_0_ != fogStatefield_179045_d)
    {
      fogStatefield_179045_d = p_179102_0_;
      GL11.glFogf(2915, p_179102_0_);
    }
  }
  
  public static void setFogEnd(float p_179153_0_)
  {
    if (p_179153_0_ != fogStatefield_179046_e)
    {
      fogStatefield_179046_e = p_179153_0_;
      GL11.glFogf(2916, p_179153_0_);
    }
  }
  
  public static void enableCull()
  {
    cullStatefield_179054_a.setEnabled();
  }
  
  public static void disableCull()
  {
    cullStatefield_179054_a.setDisabled();
  }
  
  public static void cullFace(int p_179107_0_)
  {
    if (p_179107_0_ != cullStatefield_179053_b)
    {
      cullStatefield_179053_b = p_179107_0_;
      GL11.glCullFace(p_179107_0_);
    }
  }
  
  public static void enablePolygonOffset()
  {
    polygonOffsetStatefield_179044_a.setEnabled();
  }
  
  public static void disablePolygonOffset()
  {
    polygonOffsetStatefield_179044_a.setDisabled();
  }
  
  public static void doPolygonOffset(float p_179136_0_, float p_179136_1_)
  {
    if ((p_179136_0_ != polygonOffsetStatefield_179043_c) || (p_179136_1_ != polygonOffsetStatefield_179041_d))
    {
      polygonOffsetStatefield_179043_c = p_179136_0_;
      polygonOffsetStatefield_179041_d = p_179136_1_;
      GL11.glPolygonOffset(p_179136_0_, p_179136_1_);
    }
  }
  
  public static void enableColorLogic()
  {
    colorLogicStatefield_179197_a.setEnabled();
  }
  
  public static void disableColorLogic()
  {
    colorLogicStatefield_179197_a.setDisabled();
  }
  
  public static void colorLogicOp(int p_179116_0_)
  {
    if (p_179116_0_ != colorLogicStatefield_179196_b)
    {
      colorLogicStatefield_179196_b = p_179116_0_;
      GL11.glLogicOp(p_179116_0_);
    }
  }
  
  public static void enableTexGenCoord(TexGen p_179087_0_)
  {
    texGenCoordfield_179067_a.setEnabled();
  }
  
  public static void disableTexGenCoord(TexGen p_179100_0_)
  {
    texGenCoordfield_179067_a.setDisabled();
  }
  
  public static void texGen(TexGen p_179149_0_, int p_179149_1_)
  {
    TexGenCoord var2 = texGenCoord(p_179149_0_);
    
    if (p_179149_1_ != field_179066_c)
    {
      field_179066_c = p_179149_1_;
      GL11.glTexGeni(field_179065_b, 9472, p_179149_1_);
    }
  }
  
  public static void func_179105_a(TexGen p_179105_0_, int p_179105_1_, FloatBuffer p_179105_2_)
  {
    GL11.glTexGen(texGenCoordfield_179065_b, p_179105_1_, p_179105_2_);
  }
  
  private static TexGenCoord texGenCoord(TexGen p_179125_0_)
  {
    switch (SwitchTexGen.field_179175_a[p_179125_0_.ordinal()])
    {
    case 1: 
      return texGenStatefield_179064_a;
    
    case 2: 
      return texGenStatefield_179062_b;
    
    case 3: 
      return texGenStatefield_179063_c;
    
    case 4: 
      return texGenStatefield_179061_d;
    }
    
    return texGenStatefield_179064_a;
  }
  

  public static void setActiveTexture(int p_179138_0_)
  {
    if (field_179162_o != p_179138_0_ - OpenGlHelper.defaultTexUnit)
    {
      field_179162_o = p_179138_0_ - OpenGlHelper.defaultTexUnit;
      OpenGlHelper.setActiveTexture(p_179138_0_);
    }
  }
  
  public static void func_179098_w()
  {
    field_179174_pfield_179162_ofield_179060_a.setEnabled();
  }
  
  public static void func_179090_x()
  {
    field_179174_pfield_179162_ofield_179060_a.setDisabled();
  }
  
  public static int func_179146_y()
  {
    return GL11.glGenTextures();
  }
  
  public static void func_179150_h(int p_179150_0_)
  {
    GL11.glDeleteTextures(p_179150_0_);
    TextureState[] var1 = field_179174_p;
    int var2 = var1.length;
    
    for (int var3 = 0; var3 < var2; var3++)
    {
      TextureState var4 = var1[var3];
      
      if (field_179059_b == p_179150_0_)
      {
        field_179059_b = -1;
      }
    }
  }
  
  public static void func_179144_i(int p_179144_0_)
  {
    if (p_179144_0_ != field_179174_pfield_179162_ofield_179059_b)
    {
      field_179174_pfield_179162_ofield_179059_b = p_179144_0_;
      GL11.glBindTexture(3553, p_179144_0_);
    }
  }
  
  public static void enableNormalize()
  {
    normalizeState.setEnabled();
  }
  
  public static void disableNormalize()
  {
    normalizeState.setDisabled();
  }
  
  public static void shadeModel(int p_179103_0_)
  {
    if (p_179103_0_ != field_179173_q)
    {
      field_179173_q = p_179103_0_;
      GL11.glShadeModel(p_179103_0_);
    }
  }
  
  public static void enableRescaleNormal()
  {
    rescaleNormalState.setEnabled();
  }
  
  public static void disableRescaleNormal()
  {
    rescaleNormalState.setDisabled();
  }
  
  public static void viewport(int p_179083_0_, int p_179083_1_, int p_179083_2_, int p_179083_3_)
  {
    if ((p_179083_0_ != field_179169_ufield_179058_a) || (p_179083_1_ != field_179169_ufield_179056_b) || (p_179083_2_ != field_179169_ufield_179057_c) || (p_179083_3_ != field_179169_ufield_179055_d))
    {
      field_179169_ufield_179058_a = p_179083_0_;
      field_179169_ufield_179056_b = p_179083_1_;
      field_179169_ufield_179057_c = p_179083_2_;
      field_179169_ufield_179055_d = p_179083_3_;
      GL11.glViewport(p_179083_0_, p_179083_1_, p_179083_2_, p_179083_3_);
    }
  }
  
  public static void colorMask(boolean p_179135_0_, boolean p_179135_1_, boolean p_179135_2_, boolean p_179135_3_)
  {
    if ((p_179135_0_ != colorMaskStatefield_179188_a) || (p_179135_1_ != colorMaskStatefield_179186_b) || (p_179135_2_ != colorMaskStatefield_179187_c) || (p_179135_3_ != colorMaskStatefield_179185_d))
    {
      colorMaskStatefield_179188_a = p_179135_0_;
      colorMaskStatefield_179186_b = p_179135_1_;
      colorMaskStatefield_179187_c = p_179135_2_;
      colorMaskStatefield_179185_d = p_179135_3_;
      GL11.glColorMask(p_179135_0_, p_179135_1_, p_179135_2_, p_179135_3_);
    }
  }
  
  public static void clearDepth(double p_179151_0_)
  {
    if (p_179151_0_ != clearStatefield_179205_a)
    {
      clearStatefield_179205_a = p_179151_0_;
      GL11.glClearDepth(p_179151_0_);
    }
  }
  
  public static void clearColor(float p_179082_0_, float p_179082_1_, float p_179082_2_, float p_179082_3_)
  {
    if ((p_179082_0_ != clearStatefield_179203_b.field_179195_a) || (p_179082_1_ != clearStatefield_179203_b.green) || (p_179082_2_ != clearStatefield_179203_b.blue) || (p_179082_3_ != clearStatefield_179203_b.alpha))
    {
      clearStatefield_179203_b.field_179195_a = p_179082_0_;
      clearStatefield_179203_b.green = p_179082_1_;
      clearStatefield_179203_b.blue = p_179082_2_;
      clearStatefield_179203_b.alpha = p_179082_3_;
      GL11.glClearColor(p_179082_0_, p_179082_1_, p_179082_2_, p_179082_3_);
    }
  }
  
  public static void clear(int p_179086_0_)
  {
    GL11.glClear(p_179086_0_);
  }
  
  public static void matrixMode(int p_179128_0_)
  {
    GL11.glMatrixMode(p_179128_0_);
  }
  















  public static void getFloat(int p_179111_0_, FloatBuffer p_179111_1_)
  {
    GL11.glGetFloat(p_179111_0_, p_179111_1_);
  }
  
  public static void ortho(double p_179130_0_, double p_179130_2_, double p_179130_4_, double p_179130_6_, double p_179130_8_, double p_179130_10_)
  {
    GL11.glOrtho(p_179130_0_, p_179130_2_, p_179130_4_, p_179130_6_, p_179130_8_, p_179130_10_);
  }
  
  public static void rotate(float p_179114_0_, float p_179114_1_, float p_179114_2_, float p_179114_3_)
  {
    GL11.glRotatef(p_179114_0_, p_179114_1_, p_179114_2_, p_179114_3_);
  }
  
  public static void scale(float p_179152_0_, float p_179152_1_, float p_179152_2_)
  {
    GL11.glScalef(p_179152_0_, p_179152_1_, p_179152_2_);
  }
  
  public static void scale(double p_179139_0_, double p_179139_2_, double p_179139_4_)
  {
    GL11.glScaled(p_179139_0_, p_179139_2_, p_179139_4_);
  }
  
  public static void translate(float p_179109_0_, float p_179109_1_, float p_179109_2_)
  {
    GL11.glTranslatef(p_179109_0_, p_179109_1_, p_179109_2_);
  }
  
  public static void translate(double p_179137_0_, double p_179137_2_, double p_179137_4_)
  {
    GL11.glTranslated(p_179137_0_, p_179137_2_, p_179137_4_);
  }
  
  public static void multMatrix(FloatBuffer p_179110_0_)
  {
    GL11.glMultMatrix(p_179110_0_);
  }
  
  public static void color(float p_179131_0_, float p_179131_1_, float p_179131_2_, float p_179131_3_)
  {
    if ((p_179131_0_ != colorStatefield_179195_a) || (p_179131_1_ != colorStategreen) || (p_179131_2_ != colorStateblue) || (p_179131_3_ != colorStatealpha))
    {
      colorStatefield_179195_a = p_179131_0_;
      colorStategreen = p_179131_1_;
      colorStateblue = p_179131_2_;
      colorStatealpha = p_179131_3_;
      GL11.glColor4f(p_179131_0_, p_179131_1_, p_179131_2_, p_179131_3_);
    }
  }
  
  public static void color(float p_179124_0_, float p_179124_1_, float p_179124_2_)
  {
    color(p_179124_0_, p_179124_1_, p_179124_2_, 1.0F);
  }
  
  public static void func_179117_G()
  {
    colorStatefield_179195_a = (colorState.green = colorState.blue = colorState.alpha = -1.0F);
  }
  
  public static void callList(int p_179148_0_)
  {
    GL11.glCallList(p_179148_0_);
  }
  


  static
  {
    for (int var0 = 0; var0 < 8; var0++)
    {
      field_179159_c[var0] = new BooleanState(16384 + var0);
    }
    
    for (var0 = 0; var0 < 8; var0++)
    {
      field_179174_p[var0] = new TextureState(null); }
  }
  
  public GlStateManager() {}
  
  public static void popAttrib() {}
  
  public static void loadIdentity() {}
  
  public static void pushMatrix() {}
  
  public static void popMatrix() {}
  
  static class AlphaState { private AlphaState() { field_179208_a = new GlStateManager.BooleanState(3008);
      field_179206_b = 519;
      field_179207_c = -1.0F; }
    
    public GlStateManager.BooleanState field_179208_a;
    public int field_179206_b;
    
    AlphaState(GlStateManager.SwitchTexGen p_i46269_1_) { this(); }
    
    public float field_179207_c;
    private static final String __OBFID = "CL_00002556";
  }
  
  static class BlendState {
    public GlStateManager.BooleanState field_179213_a;
    public int field_179211_b;
    public int field_179212_c;
    public int field_179209_d;
    public int field_179210_e;
    private static final String __OBFID = "CL_00002555";
    
    private BlendState() {
      field_179213_a = new GlStateManager.BooleanState(3042);
      field_179211_b = 1;
      field_179212_c = 0;
      field_179209_d = 1;
      field_179210_e = 0;
    }
    
    BlendState(GlStateManager.SwitchTexGen p_i46268_1_)
    {
      this();
    }
  }
  
  static class BooleanState
  {
    private final int capability;
    private boolean currentState = false;
    private static final String __OBFID = "CL_00002554";
    
    public BooleanState(int p_i46267_1_)
    {
      capability = p_i46267_1_;
    }
    
    public void setDisabled()
    {
      setState(false);
    }
    
    public void setEnabled()
    {
      setState(true);
    }
    
    public void setState(boolean p_179199_1_)
    {
      if (p_179199_1_ != currentState)
      {
        currentState = p_179199_1_;
        
        if (p_179199_1_)
        {
          GL11.glEnable(capability);
        }
        else
        {
          GL11.glDisable(capability);
        }
      }
    }
  }
  
  static class ClearState
  {
    public double field_179205_a;
    public GlStateManager.Color field_179203_b;
    public int field_179204_c;
    private static final String __OBFID = "CL_00002553";
    
    private ClearState()
    {
      field_179205_a = 1.0D;
      field_179203_b = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
      field_179204_c = 0;
    }
    
    ClearState(GlStateManager.SwitchTexGen p_i46266_1_)
    {
      this();
    }
  }
  
  static class Color
  {
    public float field_179195_a = 1.0F;
    public float green = 1.0F;
    public float blue = 1.0F;
    public float alpha = 1.0F;
    private static final String __OBFID = "CL_00002552";
    
    public Color() {}
    
    public Color(float p_i46265_1_, float p_i46265_2_, float p_i46265_3_, float p_i46265_4_)
    {
      field_179195_a = p_i46265_1_;
      green = p_i46265_2_;
      blue = p_i46265_3_;
      alpha = p_i46265_4_;
    }
  }
  
  static class ColorLogicState
  {
    public GlStateManager.BooleanState field_179197_a;
    public int field_179196_b;
    private static final String __OBFID = "CL_00002551";
    
    private ColorLogicState()
    {
      field_179197_a = new GlStateManager.BooleanState(3058);
      field_179196_b = 5379;
    }
    
    ColorLogicState(GlStateManager.SwitchTexGen p_i46264_1_)
    {
      this();
    }
  }
  
  static class ColorMask
  {
    public boolean field_179188_a;
    public boolean field_179186_b;
    public boolean field_179187_c;
    public boolean field_179185_d;
    private static final String __OBFID = "CL_00002550";
    
    private ColorMask()
    {
      field_179188_a = true;
      field_179186_b = true;
      field_179187_c = true;
      field_179185_d = true;
    }
    
    ColorMask(GlStateManager.SwitchTexGen p_i46263_1_)
    {
      this();
    }
  }
  
  static class ColorMaterialState
  {
    public GlStateManager.BooleanState field_179191_a;
    public int field_179189_b;
    public int field_179190_c;
    private static final String __OBFID = "CL_00002549";
    
    private ColorMaterialState()
    {
      field_179191_a = new GlStateManager.BooleanState(2903);
      field_179189_b = 1032;
      field_179190_c = 5634;
    }
    
    ColorMaterialState(GlStateManager.SwitchTexGen p_i46262_1_)
    {
      this();
    }
  }
  
  static class CullState
  {
    public GlStateManager.BooleanState field_179054_a;
    public int field_179053_b;
    private static final String __OBFID = "CL_00002548";
    
    private CullState()
    {
      field_179054_a = new GlStateManager.BooleanState(2884);
      field_179053_b = 1029;
    }
    
    CullState(GlStateManager.SwitchTexGen p_i46261_1_)
    {
      this();
    }
  }
  
  static class DepthState
  {
    public GlStateManager.BooleanState field_179052_a;
    public boolean field_179050_b;
    public int field_179051_c;
    private static final String __OBFID = "CL_00002547";
    
    private DepthState()
    {
      field_179052_a = new GlStateManager.BooleanState(2929);
      field_179050_b = true;
      field_179051_c = 513;
    }
    
    DepthState(GlStateManager.SwitchTexGen p_i46260_1_)
    {
      this();
    }
  }
  
  static class FogState
  {
    public GlStateManager.BooleanState field_179049_a;
    public int field_179047_b;
    public float field_179048_c;
    public float field_179045_d;
    public float field_179046_e;
    private static final String __OBFID = "CL_00002546";
    
    private FogState()
    {
      field_179049_a = new GlStateManager.BooleanState(2912);
      field_179047_b = 2048;
      field_179048_c = 1.0F;
      field_179045_d = 0.0F;
      field_179046_e = 1.0F;
    }
    
    FogState(GlStateManager.SwitchTexGen p_i46259_1_)
    {
      this();
    }
  }
  
  static class PolygonOffsetState
  {
    public GlStateManager.BooleanState field_179044_a;
    public GlStateManager.BooleanState field_179042_b;
    public float field_179043_c;
    public float field_179041_d;
    private static final String __OBFID = "CL_00002545";
    
    private PolygonOffsetState()
    {
      field_179044_a = new GlStateManager.BooleanState(32823);
      field_179042_b = new GlStateManager.BooleanState(10754);
      field_179043_c = 0.0F;
      field_179041_d = 0.0F;
    }
    
    PolygonOffsetState(GlStateManager.SwitchTexGen p_i46258_1_)
    {
      this();
    }
  }
  
  static class StencilFunc
  {
    public int field_179081_a;
    public int field_179079_b;
    public int field_179080_c;
    private static final String __OBFID = "CL_00002544";
    
    private StencilFunc()
    {
      field_179081_a = 519;
      field_179079_b = 0;
      field_179080_c = -1;
    }
    
    StencilFunc(GlStateManager.SwitchTexGen p_i46257_1_)
    {
      this();
    }
  }
  
  static class StencilState
  {
    public GlStateManager.StencilFunc field_179078_a;
    public int field_179076_b;
    public int field_179077_c;
    public int field_179074_d;
    public int field_179075_e;
    private static final String __OBFID = "CL_00002543";
    
    private StencilState()
    {
      field_179078_a = new GlStateManager.StencilFunc(null);
      field_179076_b = -1;
      field_179077_c = 7680;
      field_179074_d = 7680;
      field_179075_e = 7680;
    }
    
    StencilState(GlStateManager.SwitchTexGen p_i46256_1_)
    {
      this();
    }
  }
  
  static final class SwitchTexGen
  {
    static final int[] field_179175_a = new int[GlStateManager.TexGen.values().length];
    private static final String __OBFID = "CL_00002557";
    
    static
    {
      try
      {
        field_179175_a[GlStateManager.TexGen.S.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        field_179175_a[GlStateManager.TexGen.T.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      



      try
      {
        field_179175_a[GlStateManager.TexGen.R.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      



      try
      {
        field_179175_a[GlStateManager.TexGen.Q.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
    }
    

    SwitchTexGen() {}
  }
  
  public static enum TexGen
  {
    S("S", 0), 
    T("T", 1), 
    R("R", 2), 
    Q("Q", 3);
    
    private static final TexGen[] $VALUES = { S, T, R, Q };
    private static final String __OBFID = "CL_00002542";
    
    private TexGen(String p_i46255_1_, int p_i46255_2_) {}
  }
  
  static class TexGenCoord
  {
    public GlStateManager.BooleanState field_179067_a;
    public int field_179065_b;
    public int field_179066_c = -1;
    private static final String __OBFID = "CL_00002541";
    
    public TexGenCoord(int p_i46254_1_, int p_i46254_2_)
    {
      field_179065_b = p_i46254_1_;
      field_179067_a = new GlStateManager.BooleanState(p_i46254_2_);
    }
  }
  
  static class TexGenState
  {
    public GlStateManager.TexGenCoord field_179064_a;
    public GlStateManager.TexGenCoord field_179062_b;
    public GlStateManager.TexGenCoord field_179063_c;
    public GlStateManager.TexGenCoord field_179061_d;
    private static final String __OBFID = "CL_00002540";
    
    private TexGenState()
    {
      field_179064_a = new GlStateManager.TexGenCoord(8192, 3168);
      field_179062_b = new GlStateManager.TexGenCoord(8193, 3169);
      field_179063_c = new GlStateManager.TexGenCoord(8194, 3170);
      field_179061_d = new GlStateManager.TexGenCoord(8195, 3171);
    }
    
    TexGenState(GlStateManager.SwitchTexGen p_i46253_1_)
    {
      this();
    }
  }
  
  static class TextureState
  {
    public GlStateManager.BooleanState field_179060_a;
    public int field_179059_b;
    private static final String __OBFID = "CL_00002539";
    
    private TextureState()
    {
      field_179060_a = new GlStateManager.BooleanState(3553);
      field_179059_b = 0;
    }
    
    TextureState(GlStateManager.SwitchTexGen p_i46252_1_)
    {
      this();
    }
  }
  
  static class Viewport
  {
    public int field_179058_a;
    public int field_179056_b;
    public int field_179057_c;
    public int field_179055_d;
    private static final String __OBFID = "CL_00002538";
    
    private Viewport()
    {
      field_179058_a = 0;
      field_179056_b = 0;
      field_179057_c = 0;
      field_179055_d = 0;
    }
    
    Viewport(GlStateManager.SwitchTexGen p_i46251_1_)
    {
      this();
    }
  }
}
