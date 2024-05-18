package net.minecraft.client.renderer.culling;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;

public class ClippingHelperImpl extends ClippingHelper
{
  private static ClippingHelperImpl instance = new ClippingHelperImpl();
  private FloatBuffer projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
  private FloatBuffer modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
  private FloatBuffer field_78564_h = GLAllocation.createDirectFloatBuffer(16);
  
  private static final String __OBFID = "CL_00000975";
  
  public ClippingHelperImpl() {}
  
  public static ClippingHelper getInstance()
  {
    instance.init();
    return instance;
  }
  
  private void func_180547_a(float[] p_180547_1_)
  {
    float var2 = net.minecraft.util.MathHelper.sqrt_float(p_180547_1_[0] * p_180547_1_[0] + p_180547_1_[1] * p_180547_1_[1] + p_180547_1_[2] * p_180547_1_[2]);
    p_180547_1_[0] /= var2;
    p_180547_1_[1] /= var2;
    p_180547_1_[2] /= var2;
    p_180547_1_[3] /= var2;
  }
  
  public void init()
  {
    projectionMatrixBuffer.clear();
    modelviewMatrixBuffer.clear();
    field_78564_h.clear();
    GlStateManager.getFloat(2983, projectionMatrixBuffer);
    GlStateManager.getFloat(2982, modelviewMatrixBuffer);
    float[] var1 = field_178625_b;
    float[] var2 = field_178626_c;
    projectionMatrixBuffer.flip().limit(16);
    projectionMatrixBuffer.get(var1);
    modelviewMatrixBuffer.flip().limit(16);
    modelviewMatrixBuffer.get(var2);
    clippingMatrix[0] = (var2[0] * var1[0] + var2[1] * var1[4] + var2[2] * var1[8] + var2[3] * var1[12]);
    clippingMatrix[1] = (var2[0] * var1[1] + var2[1] * var1[5] + var2[2] * var1[9] + var2[3] * var1[13]);
    clippingMatrix[2] = (var2[0] * var1[2] + var2[1] * var1[6] + var2[2] * var1[10] + var2[3] * var1[14]);
    clippingMatrix[3] = (var2[0] * var1[3] + var2[1] * var1[7] + var2[2] * var1[11] + var2[3] * var1[15]);
    clippingMatrix[4] = (var2[4] * var1[0] + var2[5] * var1[4] + var2[6] * var1[8] + var2[7] * var1[12]);
    clippingMatrix[5] = (var2[4] * var1[1] + var2[5] * var1[5] + var2[6] * var1[9] + var2[7] * var1[13]);
    clippingMatrix[6] = (var2[4] * var1[2] + var2[5] * var1[6] + var2[6] * var1[10] + var2[7] * var1[14]);
    clippingMatrix[7] = (var2[4] * var1[3] + var2[5] * var1[7] + var2[6] * var1[11] + var2[7] * var1[15]);
    clippingMatrix[8] = (var2[8] * var1[0] + var2[9] * var1[4] + var2[10] * var1[8] + var2[11] * var1[12]);
    clippingMatrix[9] = (var2[8] * var1[1] + var2[9] * var1[5] + var2[10] * var1[9] + var2[11] * var1[13]);
    clippingMatrix[10] = (var2[8] * var1[2] + var2[9] * var1[6] + var2[10] * var1[10] + var2[11] * var1[14]);
    clippingMatrix[11] = (var2[8] * var1[3] + var2[9] * var1[7] + var2[10] * var1[11] + var2[11] * var1[15]);
    clippingMatrix[12] = (var2[12] * var1[0] + var2[13] * var1[4] + var2[14] * var1[8] + var2[15] * var1[12]);
    clippingMatrix[13] = (var2[12] * var1[1] + var2[13] * var1[5] + var2[14] * var1[9] + var2[15] * var1[13]);
    clippingMatrix[14] = (var2[12] * var1[2] + var2[13] * var1[6] + var2[14] * var1[10] + var2[15] * var1[14]);
    clippingMatrix[15] = (var2[12] * var1[3] + var2[13] * var1[7] + var2[14] * var1[11] + var2[15] * var1[15]);
    float[] var3 = frustum[0];
    var3[0] = (clippingMatrix[3] - clippingMatrix[0]);
    var3[1] = (clippingMatrix[7] - clippingMatrix[4]);
    var3[2] = (clippingMatrix[11] - clippingMatrix[8]);
    var3[3] = (clippingMatrix[15] - clippingMatrix[12]);
    func_180547_a(var3);
    float[] var4 = frustum[1];
    var4[0] = (clippingMatrix[3] + clippingMatrix[0]);
    var4[1] = (clippingMatrix[7] + clippingMatrix[4]);
    var4[2] = (clippingMatrix[11] + clippingMatrix[8]);
    var4[3] = (clippingMatrix[15] + clippingMatrix[12]);
    func_180547_a(var4);
    float[] var5 = frustum[2];
    var5[0] = (clippingMatrix[3] + clippingMatrix[1]);
    var5[1] = (clippingMatrix[7] + clippingMatrix[5]);
    var5[2] = (clippingMatrix[11] + clippingMatrix[9]);
    var5[3] = (clippingMatrix[15] + clippingMatrix[13]);
    func_180547_a(var5);
    float[] var6 = frustum[3];
    var6[0] = (clippingMatrix[3] - clippingMatrix[1]);
    var6[1] = (clippingMatrix[7] - clippingMatrix[5]);
    var6[2] = (clippingMatrix[11] - clippingMatrix[9]);
    var6[3] = (clippingMatrix[15] - clippingMatrix[13]);
    func_180547_a(var6);
    float[] var7 = frustum[4];
    var7[0] = (clippingMatrix[3] - clippingMatrix[2]);
    var7[1] = (clippingMatrix[7] - clippingMatrix[6]);
    var7[2] = (clippingMatrix[11] - clippingMatrix[10]);
    var7[3] = (clippingMatrix[15] - clippingMatrix[14]);
    func_180547_a(var7);
    float[] var8 = frustum[5];
    var8[0] = (clippingMatrix[3] + clippingMatrix[2]);
    var8[1] = (clippingMatrix[7] + clippingMatrix[6]);
    var8[2] = (clippingMatrix[11] + clippingMatrix[10]);
    var8[3] = (clippingMatrix[15] + clippingMatrix[14]);
    func_180547_a(var8);
  }
}
