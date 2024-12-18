package net.minecraft.world.gen.layer;

public class GenLayerZoom
  extends GenLayer
{
  private static final String __OBFID = "CL_00000572";
  
  public GenLayerZoom(long p_i2134_1_, GenLayer p_i2134_3_)
  {
    super(p_i2134_1_);
    this.parent = p_i2134_3_;
  }
  
  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
  {
    int var5 = areaX >> 1;
    int var6 = areaY >> 1;
    int var7 = (areaWidth >> 1) + 2;
    int var8 = (areaHeight >> 1) + 2;
    int[] var9 = this.parent.getInts(var5, var6, var7, var8);
    int var10 = var7 - 1 << 1;
    int var11 = var8 - 1 << 1;
    int[] var12 = IntCache.getIntCache(var10 * var11);
    for (int var13 = 0; var13 < var8 - 1; var13++)
    {
      int var14 = (var13 << 1) * var10;
      int var15 = 0;
      int var16 = var9[(var15 + 0 + (var13 + 0) * var7)];
      for (int var17 = var9[(var15 + 0 + (var13 + 1) * var7)]; var15 < var7 - 1; var15++)
      {
        initChunkSeed(var15 + var5 << 1, var13 + var6 << 1);
        int var18 = var9[(var15 + 1 + (var13 + 0) * var7)];
        int var19 = var9[(var15 + 1 + (var13 + 1) * var7)];
        var12[var14] = var16;
        var12[(var14++ + var10)] = selectRandom(new int[] { var16, var17 });
        var12[var14] = selectRandom(new int[] { var16, var18 });
        var12[(var14++ + var10)] = selectModeOrRandom(var16, var18, var17, var19);
        var16 = var18;
        var17 = var19;
      }
    }
    int[] var20 = IntCache.getIntCache(areaWidth * areaHeight);
    for (int var14 = 0; var14 < areaHeight; var14++) {
      System.arraycopy(var12, (var14 + (areaY & 0x1)) * var10 + (areaX & 0x1), var20, var14 * areaWidth, areaWidth);
    }
    return var20;
  }
  
  public static GenLayer magnify(long p_75915_0_, GenLayer p_75915_2_, int p_75915_3_)
  {
    Object var4 = p_75915_2_;
    for (int var5 = 0; var5 < p_75915_3_; var5++) {
      var4 = new GenLayerZoom(p_75915_0_ + var5, (GenLayer)var4);
    }
    return (GenLayer)var4;
  }
}
