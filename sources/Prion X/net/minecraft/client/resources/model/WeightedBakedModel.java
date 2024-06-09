package net.minecraft.client.resources.model;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandom.Item;

public class WeightedBakedModel implements IBakedModel
{
  private final int totalWeight;
  private final List models;
  private final IBakedModel baseModel;
  private static final String __OBFID = "CL_00002384";
  
  public WeightedBakedModel(List p_i46073_1_)
  {
    models = p_i46073_1_;
    totalWeight = WeightedRandom.getTotalWeight(p_i46073_1_);
    baseModel = get0model;
  }
  
  public List func_177551_a(EnumFacing p_177551_1_)
  {
    return baseModel.func_177551_a(p_177551_1_);
  }
  
  public List func_177550_a()
  {
    return baseModel.func_177550_a();
  }
  
  public boolean isGui3d()
  {
    return baseModel.isGui3d();
  }
  
  public boolean isAmbientOcclusionEnabled()
  {
    return baseModel.isAmbientOcclusionEnabled();
  }
  
  public boolean isBuiltInRenderer()
  {
    return baseModel.isBuiltInRenderer();
  }
  
  public net.minecraft.client.renderer.texture.TextureAtlasSprite getTexture()
  {
    return baseModel.getTexture();
  }
  
  public ItemCameraTransforms getItemCameraTransforms()
  {
    return baseModel.getItemCameraTransforms();
  }
  
  public IBakedModel func_177564_a(long p_177564_1_)
  {
    return func_180166_amodels, Math.abs((int)p_177564_1_ >> 16) % totalWeight)).model;
  }
  
  public static class Builder
  {
    private List field_177678_a = Lists.newArrayList();
    private static final String __OBFID = "CL_00002383";
    
    public Builder() {}
    
    public Builder add(IBakedModel p_177677_1_, int p_177677_2_) { field_177678_a.add(new WeightedBakedModel.MyWeighedRandomItem(p_177677_1_, p_177677_2_));
      return this;
    }
    
    public WeightedBakedModel build()
    {
      Collections.sort(field_177678_a);
      return new WeightedBakedModel(field_177678_a);
    }
    
    public IBakedModel first()
    {
      return field_177678_a.get(0)).model;
    }
  }
  
  static class MyWeighedRandomItem extends WeightedRandom.Item implements Comparable
  {
    protected final IBakedModel model;
    private static final String __OBFID = "CL_00002382";
    
    public MyWeighedRandomItem(IBakedModel p_i46072_1_, int p_i46072_2_)
    {
      super();
      model = p_i46072_1_;
    }
    
    public int func_177634_a(MyWeighedRandomItem p_177634_1_)
    {
      return ComparisonChain.start().compare(itemWeight, itemWeight).compare(func_177635_a(), p_177634_1_.func_177635_a()).result();
    }
    
    protected int func_177635_a()
    {
      int var1 = model.func_177550_a().size();
      EnumFacing[] var2 = EnumFacing.values();
      int var3 = var2.length;
      
      for (int var4 = 0; var4 < var3; var4++)
      {
        EnumFacing var5 = var2[var4];
        var1 += model.func_177551_a(var5).size();
      }
      
      return var1;
    }
    
    public String toString()
    {
      return "MyWeighedRandomItem{weight=" + itemWeight + ", model=" + model + '}';
    }
    
    public int compareTo(Object p_compareTo_1_)
    {
      return func_177634_a((MyWeighedRandomItem)p_compareTo_1_);
    }
  }
}
