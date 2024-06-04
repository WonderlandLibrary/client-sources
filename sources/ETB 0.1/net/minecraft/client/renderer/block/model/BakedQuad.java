package net.minecraft.client.renderer.block.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import optifine.Config;
import optifine.Reflector;

public class BakedQuad implements net.minecraftforge.client.model.pipeline.IVertexProducer
{
  protected int[] field_178215_a;
  protected final int field_178213_b;
  protected final EnumFacing face;
  private static final String __OBFID = "CL_00002512";
  private TextureAtlasSprite sprite = null;
  private int[] vertexDataSingle = null;
  
  public BakedQuad(int[] p_i46232_1_, int p_i46232_2_, EnumFacing p_i46232_3_, TextureAtlasSprite sprite)
  {
    field_178215_a = p_i46232_1_;
    field_178213_b = p_i46232_2_;
    face = p_i46232_3_;
    this.sprite = sprite;
    fixVertexData();
  }
  
  public TextureAtlasSprite getSprite()
  {
    if (sprite == null)
    {
      sprite = getSpriteByUv(func_178209_a());
    }
    
    return sprite;
  }
  
  public String toString()
  {
    return "vertex: " + field_178215_a.length / 7 + ", tint: " + field_178213_b + ", facing: " + face + ", sprite: " + sprite;
  }
  
  public BakedQuad(int[] p_i46232_1_, int p_i46232_2_, EnumFacing p_i46232_3_)
  {
    field_178215_a = p_i46232_1_;
    field_178213_b = p_i46232_2_;
    face = p_i46232_3_;
    fixVertexData();
  }
  
  public int[] func_178209_a()
  {
    fixVertexData();
    return field_178215_a;
  }
  
  public boolean func_178212_b()
  {
    return field_178213_b != -1;
  }
  
  public int func_178211_c()
  {
    return field_178213_b;
  }
  
  public EnumFacing getFace()
  {
    return face;
  }
  
  public int[] getVertexDataSingle()
  {
    if (vertexDataSingle == null)
    {
      vertexDataSingle = makeVertexDataSingle(func_178209_a(), getSprite());
    }
    
    return vertexDataSingle;
  }
  
  private static int[] makeVertexDataSingle(int[] vd, TextureAtlasSprite sprite)
  {
    int[] vdSingle = (int[])vd.clone();
    int ku = sheetWidth / sprite.getIconWidth();
    int kv = sheetHeight / sprite.getIconHeight();
    int step = vdSingle.length / 4;
    
    for (int i = 0; i < 4; i++)
    {
      int pos = i * step;
      float tu = Float.intBitsToFloat(vdSingle[(pos + 4)]);
      float tv = Float.intBitsToFloat(vdSingle[(pos + 4 + 1)]);
      float u = sprite.toSingleU(tu);
      float v = sprite.toSingleV(tv);
      vdSingle[(pos + 4)] = Float.floatToRawIntBits(u);
      vdSingle[(pos + 4 + 1)] = Float.floatToRawIntBits(v);
    }
    
    return vdSingle;
  }
  
  public void pipe(IVertexConsumer consumer)
  {
    Reflector.callVoid(Reflector.LightUtil_putBakedQuad, new Object[] { consumer, this });
  }
  
  private static TextureAtlasSprite getSpriteByUv(int[] vertexData)
  {
    float uMin = 1.0F;
    float vMin = 1.0F;
    float uMax = 0.0F;
    float vMax = 0.0F;
    int step = vertexData.length / 4;
    
    for (int uMid = 0; uMid < 4; uMid++)
    {
      int vMid = uMid * step;
      float spriteUv = Float.intBitsToFloat(vertexData[(vMid + 4)]);
      float tv = Float.intBitsToFloat(vertexData[(vMid + 4 + 1)]);
      uMin = Math.min(uMin, spriteUv);
      vMin = Math.min(vMin, tv);
      uMax = Math.max(uMax, spriteUv);
      vMax = Math.max(vMax, tv);
    }
    
    float var10 = (uMin + uMax) / 2.0F;
    float var11 = (vMin + vMax) / 2.0F;
    TextureAtlasSprite var12 = Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV(var10, var11);
    return var12;
  }
  
  private void fixVertexData()
  {
    if (Config.isShaders())
    {
      if (field_178215_a.length == 28)
      {
        field_178215_a = expandVertexData(field_178215_a);
      }
    }
    else if (field_178215_a.length == 56)
    {
      field_178215_a = compactVertexData(field_178215_a);
    }
  }
  
  private static int[] expandVertexData(int[] vd)
  {
    int step = vd.length / 4;
    int stepNew = step * 2;
    int[] vdNew = new int[stepNew * 4];
    
    for (int i = 0; i < 4; i++)
    {
      System.arraycopy(vd, i * step, vdNew, i * stepNew, step);
    }
    
    return vdNew;
  }
  
  private static int[] compactVertexData(int[] vd)
  {
    int step = vd.length / 4;
    int stepNew = step / 2;
    int[] vdNew = new int[stepNew * 4];
    
    for (int i = 0; i < 4; i++)
    {
      System.arraycopy(vd, i * step, vdNew, i * stepNew, stepNew);
    }
    
    return vdNew;
  }
}
