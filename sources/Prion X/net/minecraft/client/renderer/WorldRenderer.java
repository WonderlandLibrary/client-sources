package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.PriorityQueue;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUseage;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldRenderer
{
  private ByteBuffer byteBuffer;
  private IntBuffer rawIntBuffer;
  private FloatBuffer rawFloatBuffer;
  private int vertexCount;
  private double field_178998_e;
  private double field_178995_f;
  private int field_178996_g;
  private int field_179007_h;
  private int rawBufferIndex;
  private boolean needsUpdate;
  private int drawMode;
  private double xOffset;
  private double yOffset;
  private double zOffset;
  private int field_179003_o;
  private int field_179012_p;
  private VertexFormat vertexFormat;
  private boolean isDrawing;
  private int bufferSize;
  private static final String __OBFID = "CL_00000942";
  
  public WorldRenderer(int p_i46275_1_)
  {
    bufferSize = p_i46275_1_;
    byteBuffer = GLAllocation.createDirectByteBuffer(p_i46275_1_ * 4);
    rawIntBuffer = byteBuffer.asIntBuffer();
    rawFloatBuffer = byteBuffer.asFloatBuffer();
    vertexFormat = new VertexFormat();
    vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
  }
  
  private void growBuffer(int p_178983_1_)
  {
    LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + bufferSize * 4 + " bytes, new size " + (bufferSize * 4 + p_178983_1_) + " bytes.");
    bufferSize += p_178983_1_ / 4;
    ByteBuffer var2 = GLAllocation.createDirectByteBuffer(bufferSize * 4);
    rawIntBuffer.position(0);
    var2.asIntBuffer().put(rawIntBuffer);
    byteBuffer = var2;
    rawIntBuffer = byteBuffer.asIntBuffer();
    rawFloatBuffer = byteBuffer.asFloatBuffer();
  }
  
  public State getVertexState(float p_178971_1_, float p_178971_2_, float p_178971_3_)
  {
    int[] var4 = new int[rawBufferIndex];
    PriorityQueue var5 = new PriorityQueue(rawBufferIndex, new net.minecraft.client.util.QuadComparator(rawFloatBuffer, (float)(p_178971_1_ + xOffset), (float)(p_178971_2_ + yOffset), (float)(p_178971_3_ + zOffset), vertexFormat.func_177338_f() / 4));
    int var6 = vertexFormat.func_177338_f();
    

    for (int var7 = 0; var7 < rawBufferIndex; var7 += var6)
    {
      var5.add(Integer.valueOf(var7));
    }
    
    for (var7 = 0; !var5.isEmpty(); var7 += var6)
    {
      int var8 = ((Integer)var5.remove()).intValue();
      
      for (int var9 = 0; var9 < var6; var9++)
      {
        var4[(var7 + var9)] = rawIntBuffer.get(var8 + var9);
      }
    }
    
    rawIntBuffer.clear();
    rawIntBuffer.put(var4);
    return new State(var4, rawBufferIndex, vertexCount, new VertexFormat(vertexFormat));
  }
  
  public void setVertexState(State p_178993_1_)
  {
    if (p_178993_1_.func_179013_a().length > rawIntBuffer.capacity())
    {
      growBuffer(2097152);
    }
    
    rawIntBuffer.clear();
    rawIntBuffer.put(p_178993_1_.func_179013_a());
    rawBufferIndex = p_178993_1_.getRawBufferIndex();
    vertexCount = p_178993_1_.getVertexCount();
    vertexFormat = new VertexFormat(p_178993_1_.func_179016_d());
  }
  
  public void reset()
  {
    vertexCount = 0;
    rawBufferIndex = 0;
    vertexFormat.clear();
    vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
  }
  
  public void startDrawingQuads()
  {
    startDrawing(7);
  }
  
  public void startDrawing(int p_178964_1_)
  {
    if (isDrawing)
    {
      throw new IllegalStateException("Already building!");
    }
    

    isDrawing = true;
    reset();
    drawMode = p_178964_1_;
    needsUpdate = false;
  }
  

  public void setTextureUV(double p_178992_1_, double p_178992_3_)
  {
    if ((!vertexFormat.func_177347_a(0)) && (!vertexFormat.func_177347_a(1)))
    {
      VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2);
      vertexFormat.func_177349_a(var5);
    }
    
    field_178998_e = p_178992_1_;
    field_178995_f = p_178992_3_;
  }
  
  public void func_178963_b(int p_178963_1_)
  {
    if (!vertexFormat.func_177347_a(1))
    {
      if (!vertexFormat.func_177347_a(0))
      {
        vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
      }
      
      VertexFormatElement var2 = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.UV, 2);
      vertexFormat.func_177349_a(var2);
    }
    
    field_178996_g = p_178963_1_;
  }
  
  public void func_178986_b(float p_178986_1_, float p_178986_2_, float p_178986_3_)
  {
    setPosition((int)(p_178986_1_ * 255.0F), (int)(p_178986_2_ * 255.0F), (int)(p_178986_3_ * 255.0F));
  }
  
  public void func_178960_a(float p_178960_1_, float p_178960_2_, float p_178960_3_, float p_178960_4_)
  {
    func_178961_b((int)(p_178960_1_ * 255.0F), (int)(p_178960_2_ * 255.0F), (int)(p_178960_3_ * 255.0F), (int)(p_178960_4_ * 255.0F));
  }
  



  public void setPosition(int p_78913_1_, int p_78913_2_, int p_78913_3_)
  {
    func_178961_b(p_78913_1_, p_78913_2_, p_78913_3_, 255);
  }
  
  public void func_178962_a(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_)
  {
    int var5 = (vertexCount - 4) * (vertexFormat.func_177338_f() / 4) + vertexFormat.func_177344_b(1) / 4;
    int var6 = vertexFormat.func_177338_f() >> 2;
    rawIntBuffer.put(var5, p_178962_1_);
    rawIntBuffer.put(var5 + var6, p_178962_2_);
    rawIntBuffer.put(var5 + var6 * 2, p_178962_3_);
    rawIntBuffer.put(var5 + var6 * 3, p_178962_4_);
  }
  
  public void func_178987_a(double p_178987_1_, double p_178987_3_, double p_178987_5_)
  {
    if (rawBufferIndex >= bufferSize - vertexFormat.func_177338_f())
    {
      growBuffer(2097152);
    }
    
    int var7 = vertexFormat.func_177338_f() / 4;
    int var8 = (vertexCount - 4) * var7;
    
    for (int var9 = 0; var9 < 4; var9++)
    {
      int var10 = var8 + var9 * var7;
      int var11 = var10 + 1;
      int var12 = var11 + 1;
      rawIntBuffer.put(var10, Float.floatToRawIntBits((float)(p_178987_1_ + xOffset) + Float.intBitsToFloat(rawIntBuffer.get(var10))));
      rawIntBuffer.put(var11, Float.floatToRawIntBits((float)(p_178987_3_ + yOffset) + Float.intBitsToFloat(rawIntBuffer.get(var11))));
      rawIntBuffer.put(var12, Float.floatToRawIntBits((float)(p_178987_5_ + zOffset) + Float.intBitsToFloat(rawIntBuffer.get(var12))));
    }
  }
  



  private int getGLCallListForPass(int p_78909_1_)
  {
    return ((vertexCount - p_78909_1_) * vertexFormat.func_177338_f() + vertexFormat.func_177340_e()) / 4;
  }
  
  public void func_178978_a(float p_178978_1_, float p_178978_2_, float p_178978_3_, int p_178978_4_)
  {
    int var5 = getGLCallListForPass(p_178978_4_);
    int var6 = rawIntBuffer.get(var5);
    



    if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
    {
      int var7 = (int)((var6 & 0xFF) * p_178978_1_);
      int var8 = (int)((var6 >> 8 & 0xFF) * p_178978_2_);
      int var9 = (int)((var6 >> 16 & 0xFF) * p_178978_3_);
      var6 &= 0xFF000000;
      var6 |= var9 << 16 | var8 << 8 | var7;
    }
    else
    {
      int var7 = (int)((field_179007_h >> 24 & 0xFF) * p_178978_1_);
      int var8 = (int)((field_179007_h >> 16 & 0xFF) * p_178978_2_);
      int var9 = (int)((field_179007_h >> 8 & 0xFF) * p_178978_3_);
      var6 &= 0xFF;
      var6 |= var7 << 24 | var8 << 16 | var9 << 8;
    }
    
    if (needsUpdate)
    {
      var6 = -1;
    }
    
    rawIntBuffer.put(var5, var6);
  }
  
  private void func_178988_b(int p_178988_1_, int p_178988_2_)
  {
    int var3 = getGLCallListForPass(p_178988_2_);
    int var4 = p_178988_1_ >> 16 & 0xFF;
    int var5 = p_178988_1_ >> 8 & 0xFF;
    int var6 = p_178988_1_ & 0xFF;
    int var7 = p_178988_1_ >> 24 & 0xFF;
    func_178972_a(var3, var4, var5, var6, var7);
  }
  
  public void func_178994_b(float p_178994_1_, float p_178994_2_, float p_178994_3_, int p_178994_4_)
  {
    int var5 = getGLCallListForPass(p_178994_4_);
    int var6 = MathHelper.clamp_int((int)(p_178994_1_ * 255.0F), 0, 255);
    int var7 = MathHelper.clamp_int((int)(p_178994_2_ * 255.0F), 0, 255);
    int var8 = MathHelper.clamp_int((int)(p_178994_3_ * 255.0F), 0, 255);
    func_178972_a(var5, var6, var7, var8, 255);
  }
  
  private void func_178972_a(int p_178972_1_, int p_178972_2_, int p_178972_3_, int p_178972_4_, int p_178972_5_)
  {
    if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
    {
      rawIntBuffer.put(p_178972_1_, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | p_178972_2_);
    }
    else
    {
      rawIntBuffer.put(p_178972_1_, p_178972_2_ << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
    }
  }
  
  public void func_178961_b(int p_178961_1_, int p_178961_2_, int p_178961_3_, int p_178961_4_)
  {
    if (!needsUpdate)
    {
      if (p_178961_1_ > 255)
      {
        p_178961_1_ = 255;
      }
      
      if (p_178961_2_ > 255)
      {
        p_178961_2_ = 255;
      }
      
      if (p_178961_3_ > 255)
      {
        p_178961_3_ = 255;
      }
      
      if (p_178961_4_ > 255)
      {
        p_178961_4_ = 255;
      }
      
      if (p_178961_1_ < 0)
      {
        p_178961_1_ = 0;
      }
      
      if (p_178961_2_ < 0)
      {
        p_178961_2_ = 0;
      }
      
      if (p_178961_3_ < 0)
      {
        p_178961_3_ = 0;
      }
      
      if (p_178961_4_ < 0)
      {
        p_178961_4_ = 0;
      }
      
      if (!vertexFormat.func_177346_d())
      {
        VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4);
        vertexFormat.func_177349_a(var5);
      }
      
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
      {
        field_179007_h = (p_178961_4_ << 24 | p_178961_3_ << 16 | p_178961_2_ << 8 | p_178961_1_);
      }
      else
      {
        field_179007_h = (p_178961_1_ << 24 | p_178961_2_ << 16 | p_178961_3_ << 8 | p_178961_4_);
      }
    }
  }
  
  public void func_178982_a(byte p_178982_1_, byte p_178982_2_, byte p_178982_3_)
  {
    setPosition(p_178982_1_ & 0xFF, p_178982_2_ & 0xFF, p_178982_3_ & 0xFF);
  }
  
  public void addVertexWithUV(double p_178985_1_, double p_178985_3_, double p_178985_5_, double p_178985_7_, double p_178985_9_)
  {
    setTextureUV(p_178985_7_, p_178985_9_);
    addVertex(p_178985_1_, p_178985_3_, p_178985_5_);
  }
  
  public void setVertexFormat(VertexFormat p_178967_1_)
  {
    vertexFormat = new VertexFormat(p_178967_1_);
  }
  
  public void func_178981_a(int[] p_178981_1_)
  {
    int var2 = vertexFormat.func_177338_f() / 4;
    vertexCount += p_178981_1_.length / var2;
    rawIntBuffer.position(rawBufferIndex);
    rawIntBuffer.put(p_178981_1_);
    rawBufferIndex += p_178981_1_.length;
  }
  
  public void addVertex(double p_178984_1_, double p_178984_3_, double p_178984_5_)
  {
    if (rawBufferIndex >= bufferSize - vertexFormat.func_177338_f())
    {
      growBuffer(2097152);
    }
    
    List var7 = vertexFormat.func_177343_g();
    int listSize = var7.size();
    
    for (int i = 0; i < listSize; i++)
    {
      VertexFormatElement var9 = (VertexFormatElement)var7.get(i);
      int var10 = var9.func_177373_a() >> 2;
      int var11 = rawBufferIndex + var10;
      
      switch (SwitchEnumUseage.field_178959_a[var9.func_177375_c().ordinal()])
      {
      case 1: 
        rawIntBuffer.put(var11, Float.floatToRawIntBits((float)(p_178984_1_ + xOffset)));
        rawIntBuffer.put(var11 + 1, Float.floatToRawIntBits((float)(p_178984_3_ + yOffset)));
        rawIntBuffer.put(var11 + 2, Float.floatToRawIntBits((float)(p_178984_5_ + zOffset)));
        break;
      
      case 2: 
        rawIntBuffer.put(var11, field_179007_h);
        break;
      
      case 3: 
        if (var9.func_177369_e() == 0)
        {
          rawIntBuffer.put(var11, Float.floatToRawIntBits((float)field_178998_e));
          rawIntBuffer.put(var11 + 1, Float.floatToRawIntBits((float)field_178995_f));
        }
        else
        {
          rawIntBuffer.put(var11, field_178996_g);
        }
        
        break;
      
      case 4: 
        rawIntBuffer.put(var11, field_179003_o);
      }
      
    }
    rawBufferIndex += (vertexFormat.func_177338_f() >> 2);
    vertexCount += 1;
  }
  
  public void func_178991_c(int p_178991_1_)
  {
    int var2 = p_178991_1_ >> 16 & 0xFF;
    int var3 = p_178991_1_ >> 8 & 0xFF;
    int var4 = p_178991_1_ & 0xFF;
    setPosition(var2, var3, var4);
  }
  
  public void func_178974_a(int p_178974_1_, int p_178974_2_)
  {
    int var3 = p_178974_1_ >> 16 & 0xFF;
    int var4 = p_178974_1_ >> 8 & 0xFF;
    int var5 = p_178974_1_ & 0xFF;
    func_178961_b(var3, var4, var5, p_178974_2_);
  }
  



  public void markDirty()
  {
    needsUpdate = true;
  }
  
  public void func_178980_d(float p_178980_1_, float p_178980_2_, float p_178980_3_)
  {
    if (!vertexFormat.func_177350_b())
    {
      VertexFormatElement var7 = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3);
      vertexFormat.func_177349_a(var7);
      vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.PADDING, 1));
    }
    
    byte var71 = (byte)(int)(p_178980_1_ * 127.0F);
    byte var5 = (byte)(int)(p_178980_2_ * 127.0F);
    byte var6 = (byte)(int)(p_178980_3_ * 127.0F);
    field_179003_o = (var71 & 0xFF | (var5 & 0xFF) << 8 | (var6 & 0xFF) << 16);
  }
  
  public void func_178975_e(float p_178975_1_, float p_178975_2_, float p_178975_3_)
  {
    byte var4 = (byte)(int)(p_178975_1_ * 127.0F);
    byte var5 = (byte)(int)(p_178975_2_ * 127.0F);
    byte var6 = (byte)(int)(p_178975_3_ * 127.0F);
    int var7 = vertexFormat.func_177338_f() >> 2;
    int var8 = (vertexCount - 4) * var7 + vertexFormat.func_177342_c() / 4;
    field_179003_o = (var4 & 0xFF | (var5 & 0xFF) << 8 | (var6 & 0xFF) << 16);
    rawIntBuffer.put(var8, field_179003_o);
    rawIntBuffer.put(var8 + var7, field_179003_o);
    rawIntBuffer.put(var8 + var7 * 2, field_179003_o);
    rawIntBuffer.put(var8 + var7 * 3, field_179003_o);
  }
  
  public void setTranslation(double p_178969_1_, double p_178969_3_, double p_178969_5_)
  {
    xOffset = p_178969_1_;
    yOffset = p_178969_3_;
    zOffset = p_178969_5_;
  }
  
  public int draw()
  {
    if (!isDrawing)
    {
      throw new IllegalStateException("Not building!");
    }
    

    isDrawing = false;
    
    if (vertexCount > 0)
    {
      byteBuffer.position(0);
      byteBuffer.limit(rawBufferIndex * 4);
    }
    
    field_179012_p = (rawBufferIndex * 4);
    return field_179012_p;
  }
  

  public int func_178976_e()
  {
    return field_179012_p;
  }
  
  public ByteBuffer func_178966_f()
  {
    return byteBuffer;
  }
  
  public VertexFormat func_178973_g()
  {
    return vertexFormat;
  }
  
  public int func_178989_h()
  {
    return vertexCount;
  }
  
  public int getDrawMode()
  {
    return drawMode;
  }
  
  public void func_178968_d(int p_178968_1_)
  {
    for (int var2 = 0; var2 < 4; var2++)
    {
      func_178988_b(p_178968_1_, var2 + 1);
    }
  }
  
  public void func_178990_f(float p_178990_1_, float p_178990_2_, float p_178990_3_)
  {
    for (int var4 = 0; var4 < 4; var4++)
    {
      func_178994_b(p_178990_1_, p_178990_2_, p_178990_3_, var4 + 1);
    }
  }
  
  public class State
  {
    private final int[] field_179019_b;
    private final int field_179020_c;
    private final int field_179017_d;
    private final VertexFormat field_179018_e;
    private static final String __OBFID = "CL_00002568";
    
    public State(int[] p_i46274_2_, int p_i46274_3_, int p_i46274_4_, VertexFormat p_i46274_5_)
    {
      field_179019_b = p_i46274_2_;
      field_179020_c = p_i46274_3_;
      field_179017_d = p_i46274_4_;
      field_179018_e = p_i46274_5_;
    }
    
    public int[] func_179013_a()
    {
      return field_179019_b;
    }
    
    public int getRawBufferIndex()
    {
      return field_179020_c;
    }
    
    public int getVertexCount()
    {
      return field_179017_d;
    }
    
    public VertexFormat func_179016_d()
    {
      return field_179018_e;
    }
  }
  
  static final class SwitchEnumUseage
  {
    static final int[] field_178959_a = new int[VertexFormatElement.EnumUseage.values().length];
    private static final String __OBFID = "CL_00002569";
    
    static
    {
      try
      {
        field_178959_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        field_178959_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      



      try
      {
        field_178959_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      



      try
      {
        field_178959_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
    }
    
    SwitchEnumUseage() {}
  }
}
