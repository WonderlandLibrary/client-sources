package net.minecraft.world.gen.layer;

public class GenLayerAddIsland
  extends GenLayer
{
  private static final String __OBFID = "CL_00000551";
  
  public GenLayerAddIsland(long p_i2119_1_, GenLayer p_i2119_3_)
  {
    super(p_i2119_1_);
    this.parent = p_i2119_3_;
  }
  
  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
  {
    int var5 = areaX - 1;
    int var6 = areaY - 1;
    int var7 = areaWidth + 2;
    int var8 = areaHeight + 2;
    int[] var9 = this.parent.getInts(var5, var6, var7, var8);
    int[] var10 = IntCache.getIntCache(areaWidth * areaHeight);
    for (int var11 = 0; var11 < areaHeight; var11++) {
      for (int var12 = 0; var12 < areaWidth; var12++)
      {
        int var13 = var9[(var12 + 0 + (var11 + 0) * var7)];
        int var14 = var9[(var12 + 2 + (var11 + 0) * var7)];
        int var15 = var9[(var12 + 0 + (var11 + 2) * var7)];
        int var16 = var9[(var12 + 2 + (var11 + 2) * var7)];
        int var17 = var9[(var12 + 1 + (var11 + 1) * var7)];
        initChunkSeed(var12 + areaX, var11 + areaY);
        if ((var17 == 0) && ((var13 != 0) || (var14 != 0) || (var15 != 0) || (var16 != 0)))
        {
          int var18 = 1;
          int var19 = 1;
          if ((var13 != 0) && (nextInt(var18++) == 0)) {
            var19 = var13;
          }
          if ((var14 != 0) && (nextInt(var18++) == 0)) {
            var19 = var14;
          }
          if ((var15 != 0) && (nextInt(var18++) == 0)) {
            var19 = var15;
          }
          if ((var16 != 0) && (nextInt(var18++) == 0)) {
            var19 = var16;
          }
          if (nextInt(3) == 0) {
            var10[(var12 + var11 * areaWidth)] = var19;
          } else if (var19 == 4) {
            var10[(var12 + var11 * areaWidth)] = 4;
          } else {
            var10[(var12 + var11 * areaWidth)] = 0;
          }
        }
        else if ((var17 > 0) && ((var13 == 0) || (var14 == 0) || (var15 == 0) || (var16 == 0)))
        {
          if (nextInt(5) == 0)
          {
            if (var17 == 4) {
              var10[(var12 + var11 * areaWidth)] = 4;
            } else {
              var10[(var12 + var11 * areaWidth)] = 0;
            }
          }
          else {
            var10[(var12 + var11 * areaWidth)] = var17;
          }
        }
        else
        {
          var10[(var12 + var11 * areaWidth)] = var17;
        }
      }
    }
    return var10;
  }
}
