package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BakedQuad
{
  protected final int[] field_178215_a;
  protected final int field_178213_b;
  protected final EnumFacing face;
  private static final String __OBFID = "CL_00002512";
  private TextureAtlasSprite sprite = null;
  
  public BakedQuad(int[] p_i46232_1_, int p_i46232_2_, EnumFacing p_i46232_3_, TextureAtlasSprite sprite)
  {
    field_178215_a = p_i46232_1_;
    field_178213_b = p_i46232_2_;
    face = p_i46232_3_;
    this.sprite = sprite;
  }
  
  public TextureAtlasSprite getSprite()
  {
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
  }
  
  public int[] func_178209_a()
  {
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
}
