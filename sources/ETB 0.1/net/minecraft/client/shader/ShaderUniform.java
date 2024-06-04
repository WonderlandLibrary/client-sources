package net.minecraft.client.shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.vecmath.Matrix4f;
import net.minecraft.client.renderer.OpenGlHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

public class ShaderUniform
{
  private static final Logger logger = ;
  private int uniformLocation;
  private final int uniformCount;
  private final int uniformType;
  private final IntBuffer uniformIntBuffer;
  private final FloatBuffer uniformFloatBuffer;
  private final String shaderName;
  private boolean field_148105_h;
  private final ShaderManager shaderManager;
  private static final String __OBFID = "CL_00001046";
  
  public ShaderUniform(String name, int type, int count, ShaderManager manager)
  {
    shaderName = name;
    uniformCount = count;
    uniformType = type;
    shaderManager = manager;
    
    if (type <= 3)
    {
      uniformIntBuffer = BufferUtils.createIntBuffer(count);
      uniformFloatBuffer = null;
    }
    else
    {
      uniformIntBuffer = null;
      uniformFloatBuffer = BufferUtils.createFloatBuffer(count);
    }
    
    uniformLocation = -1;
    markDirty();
  }
  
  private void markDirty()
  {
    field_148105_h = true;
    
    if (shaderManager != null)
    {
      shaderManager.markDirty();
    }
  }
  
  public static int parseType(String p_148085_0_)
  {
    byte var1 = -1;
    
    if (p_148085_0_.equals("int"))
    {
      var1 = 0;
    }
    else if (p_148085_0_.equals("float"))
    {
      var1 = 4;
    }
    else if (p_148085_0_.startsWith("matrix"))
    {
      if (p_148085_0_.endsWith("2x2"))
      {
        var1 = 8;
      }
      else if (p_148085_0_.endsWith("3x3"))
      {
        var1 = 9;
      }
      else if (p_148085_0_.endsWith("4x4"))
      {
        var1 = 10;
      }
    }
    
    return var1;
  }
  
  public void setUniformLocation(int p_148084_1_)
  {
    uniformLocation = p_148084_1_;
  }
  
  public String getShaderName()
  {
    return shaderName;
  }
  
  public void set(float p_148090_1_)
  {
    uniformFloatBuffer.position(0);
    uniformFloatBuffer.put(0, p_148090_1_);
    markDirty();
  }
  
  public void set(float p_148087_1_, float p_148087_2_)
  {
    uniformFloatBuffer.position(0);
    uniformFloatBuffer.put(0, p_148087_1_);
    uniformFloatBuffer.put(1, p_148087_2_);
    markDirty();
  }
  
  public void set(float p_148095_1_, float p_148095_2_, float p_148095_3_)
  {
    uniformFloatBuffer.position(0);
    uniformFloatBuffer.put(0, p_148095_1_);
    uniformFloatBuffer.put(1, p_148095_2_);
    uniformFloatBuffer.put(2, p_148095_3_);
    markDirty();
  }
  
  public void set(float p_148081_1_, float p_148081_2_, float p_148081_3_, float p_148081_4_)
  {
    uniformFloatBuffer.position(0);
    uniformFloatBuffer.put(p_148081_1_);
    uniformFloatBuffer.put(p_148081_2_);
    uniformFloatBuffer.put(p_148081_3_);
    uniformFloatBuffer.put(p_148081_4_);
    uniformFloatBuffer.flip();
    markDirty();
  }
  
  public void func_148092_b(float p_148092_1_, float p_148092_2_, float p_148092_3_, float p_148092_4_)
  {
    uniformFloatBuffer.position(0);
    
    if (uniformType >= 4)
    {
      uniformFloatBuffer.put(0, p_148092_1_);
    }
    
    if (uniformType >= 5)
    {
      uniformFloatBuffer.put(1, p_148092_2_);
    }
    
    if (uniformType >= 6)
    {
      uniformFloatBuffer.put(2, p_148092_3_);
    }
    
    if (uniformType >= 7)
    {
      uniformFloatBuffer.put(3, p_148092_4_);
    }
    
    markDirty();
  }
  
  public void set(int p_148083_1_, int p_148083_2_, int p_148083_3_, int p_148083_4_)
  {
    uniformIntBuffer.position(0);
    
    if (uniformType >= 0)
    {
      uniformIntBuffer.put(0, p_148083_1_);
    }
    
    if (uniformType >= 1)
    {
      uniformIntBuffer.put(1, p_148083_2_);
    }
    
    if (uniformType >= 2)
    {
      uniformIntBuffer.put(2, p_148083_3_);
    }
    
    if (uniformType >= 3)
    {
      uniformIntBuffer.put(3, p_148083_4_);
    }
    
    markDirty();
  }
  
  public void set(float[] p_148097_1_)
  {
    if (p_148097_1_.length < uniformCount)
    {
      logger.warn("Uniform.set called with a too-small value array (expected " + uniformCount + ", got " + p_148097_1_.length + "). Ignoring.");
    }
    else
    {
      uniformFloatBuffer.position(0);
      uniformFloatBuffer.put(p_148097_1_);
      uniformFloatBuffer.position(0);
      markDirty();
    }
  }
  
  public void set(float p_148094_1_, float p_148094_2_, float p_148094_3_, float p_148094_4_, float p_148094_5_, float p_148094_6_, float p_148094_7_, float p_148094_8_, float p_148094_9_, float p_148094_10_, float p_148094_11_, float p_148094_12_, float p_148094_13_, float p_148094_14_, float p_148094_15_, float p_148094_16_)
  {
    uniformFloatBuffer.position(0);
    uniformFloatBuffer.put(0, p_148094_1_);
    uniformFloatBuffer.put(1, p_148094_2_);
    uniformFloatBuffer.put(2, p_148094_3_);
    uniformFloatBuffer.put(3, p_148094_4_);
    uniformFloatBuffer.put(4, p_148094_5_);
    uniformFloatBuffer.put(5, p_148094_6_);
    uniformFloatBuffer.put(6, p_148094_7_);
    uniformFloatBuffer.put(7, p_148094_8_);
    uniformFloatBuffer.put(8, p_148094_9_);
    uniformFloatBuffer.put(9, p_148094_10_);
    uniformFloatBuffer.put(10, p_148094_11_);
    uniformFloatBuffer.put(11, p_148094_12_);
    uniformFloatBuffer.put(12, p_148094_13_);
    uniformFloatBuffer.put(13, p_148094_14_);
    uniformFloatBuffer.put(14, p_148094_15_);
    uniformFloatBuffer.put(15, p_148094_16_);
    markDirty();
  }
  
  public void set(Matrix4f p_148088_1_)
  {
    set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
  }
  
  public void upload()
  {
    if (!field_148105_h) {}
    



    field_148105_h = false;
    
    if (uniformType <= 3)
    {
      uploadInt();
    }
    else if (uniformType <= 7)
    {
      uploadFloat();
    }
    else
    {
      if (uniformType > 10)
      {
        logger.warn("Uniform.upload called, but type value (" + uniformType + ") is not " + "a valid type. Ignoring.");
        return;
      }
      
      uploadFloatMatrix();
    }
  }
  
  private void uploadInt()
  {
    switch (uniformType)
    {
    case 0: 
      OpenGlHelper.glUniform1(uniformLocation, uniformIntBuffer);
      break;
    
    case 1: 
      OpenGlHelper.glUniform2(uniformLocation, uniformIntBuffer);
      break;
    
    case 2: 
      OpenGlHelper.glUniform3(uniformLocation, uniformIntBuffer);
      break;
    
    case 3: 
      OpenGlHelper.glUniform4(uniformLocation, uniformIntBuffer);
      break;
    
    default: 
      logger.warn("Uniform.upload called, but count value (" + uniformCount + ") is " + " not in the range of 1 to 4. Ignoring.");
    }
  }
  
  private void uploadFloat()
  {
    switch (uniformType)
    {
    case 4: 
      OpenGlHelper.glUniform1(uniformLocation, uniformFloatBuffer);
      break;
    
    case 5: 
      OpenGlHelper.glUniform2(uniformLocation, uniformFloatBuffer);
      break;
    
    case 6: 
      OpenGlHelper.glUniform3(uniformLocation, uniformFloatBuffer);
      break;
    
    case 7: 
      OpenGlHelper.glUniform4(uniformLocation, uniformFloatBuffer);
      break;
    
    default: 
      logger.warn("Uniform.upload called, but count value (" + uniformCount + ") is " + "not in the range of 1 to 4. Ignoring.");
    }
  }
  
  private void uploadFloatMatrix()
  {
    switch (uniformType)
    {
    case 8: 
      OpenGlHelper.glUniformMatrix2(uniformLocation, true, uniformFloatBuffer);
      break;
    
    case 9: 
      OpenGlHelper.glUniformMatrix3(uniformLocation, true, uniformFloatBuffer);
      break;
    
    case 10: 
      OpenGlHelper.glUniformMatrix4(uniformLocation, true, uniformFloatBuffer);
    }
  }
}
