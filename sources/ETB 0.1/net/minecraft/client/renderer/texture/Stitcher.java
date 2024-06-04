package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.util.MathHelper;

public class Stitcher
{
  private final int mipmapLevelStitcher;
  private final Set setStitchHolders = Sets.newHashSetWithExpectedSize(256);
  private final List stitchSlots = Lists.newArrayListWithCapacity(256);
  
  private int currentWidth;
  
  private int currentHeight;
  private final int maxWidth;
  private final int maxHeight;
  private final boolean forcePowerOf2;
  private final int maxTileDimension;
  private static final String __OBFID = "CL_00001054";
  
  public Stitcher(int p_i45095_1_, int p_i45095_2_, boolean p_i45095_3_, int p_i45095_4_, int p_i45095_5_)
  {
    mipmapLevelStitcher = p_i45095_5_;
    maxWidth = p_i45095_1_;
    maxHeight = p_i45095_2_;
    forcePowerOf2 = p_i45095_3_;
    maxTileDimension = p_i45095_4_;
  }
  
  public int getCurrentWidth()
  {
    return currentWidth;
  }
  
  public int getCurrentHeight()
  {
    return currentHeight;
  }
  
  public void addSprite(TextureAtlasSprite p_110934_1_)
  {
    Holder var2 = new Holder(p_110934_1_, mipmapLevelStitcher);
    
    if (maxTileDimension > 0)
    {
      var2.setNewDimension(maxTileDimension);
    }
    
    setStitchHolders.add(var2);
  }
  
  public void doStitch()
  {
    Holder[] var1 = (Holder[])setStitchHolders.toArray(new Holder[setStitchHolders.size()]);
    Arrays.sort(var1);
    Holder[] var2 = var1;
    int var3 = var1.length;
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      Holder var5 = var2[var4];
      
      if (!allocateSlot(var5))
      {
        String var6 = String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", new Object[] { var5.getAtlasSprite().getIconName(), Integer.valueOf(var5.getAtlasSprite().getIconWidth()), Integer.valueOf(var5.getAtlasSprite().getIconHeight()), Integer.valueOf(currentWidth), Integer.valueOf(currentHeight), Integer.valueOf(maxWidth), Integer.valueOf(maxHeight) });
        throw new StitcherException(var5, var6);
      }
    }
    
    if (forcePowerOf2)
    {
      currentWidth = MathHelper.roundUpToPowerOfTwo(currentWidth);
      currentHeight = MathHelper.roundUpToPowerOfTwo(currentHeight);
    }
  }
  
  public List getStichSlots()
  {
    ArrayList var1 = Lists.newArrayList();
    Iterator var2 = stitchSlots.iterator();
    
    while (var2.hasNext())
    {
      Slot var7 = (Slot)var2.next();
      var7.getAllStitchSlots(var1);
    }
    
    ArrayList var71 = Lists.newArrayList();
    Iterator var8 = var1.iterator();
    
    while (var8.hasNext())
    {
      Slot var4 = (Slot)var8.next();
      Holder var5 = var4.getStitchHolder();
      TextureAtlasSprite var6 = var5.getAtlasSprite();
      var6.initSprite(currentWidth, currentHeight, var4.getOriginX(), var4.getOriginY(), var5.isRotated());
      var71.add(var6);
    }
    
    return var71;
  }
  
  private static int getMipmapDimension(int p_147969_0_, int p_147969_1_)
  {
    return (p_147969_0_ >> p_147969_1_) + ((p_147969_0_ & (1 << p_147969_1_) - 1) == 0 ? 0 : 1) << p_147969_1_;
  }
  



  private boolean allocateSlot(Holder p_94310_1_)
  {
    for (int var2 = 0; var2 < stitchSlots.size(); var2++)
    {
      if (((Slot)stitchSlots.get(var2)).addSlot(p_94310_1_))
      {
        return true;
      }
      
      p_94310_1_.rotate();
      
      if (((Slot)stitchSlots.get(var2)).addSlot(p_94310_1_))
      {
        return true;
      }
      
      p_94310_1_.rotate();
    }
    
    return expandAndAllocateSlot(p_94310_1_);
  }
  



  private boolean expandAndAllocateSlot(Holder p_94311_1_)
  {
    int var2 = Math.min(p_94311_1_.getWidth(), p_94311_1_.getHeight());
    boolean var3 = (currentWidth == 0) && (currentHeight == 0);
    
    boolean var4;
    boolean var4;
    if (forcePowerOf2)
    {
      int var5 = MathHelper.roundUpToPowerOfTwo(currentWidth);
      int var15 = MathHelper.roundUpToPowerOfTwo(currentHeight);
      int var14 = MathHelper.roundUpToPowerOfTwo(currentWidth + var2);
      int var8 = MathHelper.roundUpToPowerOfTwo(currentHeight + var2);
      boolean var9 = var14 <= maxWidth;
      boolean var10 = var8 <= maxHeight;
      
      if ((!var9) && (!var10))
      {
        return false;
      }
      
      boolean var11 = var5 != var14;
      boolean var12 = var15 != var8;
      boolean var4;
      if ((var11 ^ var12))
      {
        var4 = !var11;
      }
      else
      {
        var4 = (var9) && (var5 <= var15);
      }
    }
    else
    {
      boolean var151 = currentWidth + var2 <= maxWidth;
      boolean var141 = currentHeight + var2 <= maxHeight;
      
      if ((!var151) && (!var141))
      {
        return false;
      }
      
      var4 = (var151) && ((var3) || (currentWidth <= currentHeight));
    }
    
    int var5 = Math.max(p_94311_1_.getWidth(), p_94311_1_.getHeight());
    
    if (MathHelper.roundUpToPowerOfTwo((!var4 ? currentHeight : currentWidth) + var5) > (!var4 ? maxHeight : maxWidth))
    {
      return false;
    }
    

    Slot var152;
    
    if (var4)
    {
      if (p_94311_1_.getWidth() > p_94311_1_.getHeight())
      {
        p_94311_1_.rotate();
      }
      
      if (currentHeight == 0)
      {
        currentHeight = p_94311_1_.getHeight();
      }
      
      Slot var152 = new Slot(currentWidth, 0, p_94311_1_.getWidth(), currentHeight);
      currentWidth += p_94311_1_.getWidth();
    }
    else
    {
      var152 = new Slot(0, currentHeight, currentWidth, p_94311_1_.getHeight());
      currentHeight += p_94311_1_.getHeight();
    }
    
    var152.addSlot(p_94311_1_);
    stitchSlots.add(var152);
    return true;
  }
  
  public static class Holder
    implements Comparable
  {
    private final TextureAtlasSprite theTexture;
    private final int width;
    private final int height;
    private final int mipmapLevelHolder;
    private boolean rotated;
    private float scaleFactor = 1.0F;
    private static final String __OBFID = "CL_00001055";
    
    public Holder(TextureAtlasSprite p_i45094_1_, int p_i45094_2_)
    {
      theTexture = p_i45094_1_;
      width = p_i45094_1_.getIconWidth();
      height = p_i45094_1_.getIconHeight();
      mipmapLevelHolder = p_i45094_2_;
      rotated = (Stitcher.getMipmapDimension(height, p_i45094_2_) > Stitcher.getMipmapDimension(width, p_i45094_2_));
    }
    
    public TextureAtlasSprite getAtlasSprite()
    {
      return theTexture;
    }
    
    public int getWidth()
    {
      return rotated ? Stitcher.getMipmapDimension((int)(height * scaleFactor), mipmapLevelHolder) : Stitcher.getMipmapDimension((int)(width * scaleFactor), mipmapLevelHolder);
    }
    
    public int getHeight()
    {
      return rotated ? Stitcher.getMipmapDimension((int)(width * scaleFactor), mipmapLevelHolder) : Stitcher.getMipmapDimension((int)(height * scaleFactor), mipmapLevelHolder);
    }
    
    public void rotate()
    {
      rotated = (!rotated);
    }
    
    public boolean isRotated()
    {
      return rotated;
    }
    
    public void setNewDimension(int p_94196_1_)
    {
      if ((width > p_94196_1_) && (height > p_94196_1_))
      {
        scaleFactor = (p_94196_1_ / Math.min(width, height));
      }
    }
    
    public String toString()
    {
      return "Holder{width=" + width + ", height=" + height + ", name=" + theTexture.getIconName() + '}';
    }
    
    public int compareTo(Holder p_compareTo_1_)
    {
      int var2;
      int var2;
      if (getHeight() == p_compareTo_1_.getHeight())
      {
        if (getWidth() == p_compareTo_1_.getWidth())
        {
          if (theTexture.getIconName() == null)
          {
            return theTexture.getIconName() == null ? 0 : -1;
          }
          
          return theTexture.getIconName().compareTo(theTexture.getIconName());
        }
        
        var2 = getWidth() < p_compareTo_1_.getWidth() ? 1 : -1;
      }
      else
      {
        var2 = getHeight() < p_compareTo_1_.getHeight() ? 1 : -1;
      }
      
      return var2;
    }
    
    public int compareTo(Object p_compareTo_1_)
    {
      return compareTo((Holder)p_compareTo_1_);
    }
  }
  
  public static class Slot
  {
    private final int originX;
    private final int originY;
    private final int width;
    private final int height;
    private List subSlots;
    private Stitcher.Holder holder;
    private static final String __OBFID = "CL_00001056";
    
    public Slot(int p_i1277_1_, int p_i1277_2_, int p_i1277_3_, int p_i1277_4_)
    {
      originX = p_i1277_1_;
      originY = p_i1277_2_;
      width = p_i1277_3_;
      height = p_i1277_4_;
    }
    
    public Stitcher.Holder getStitchHolder()
    {
      return holder;
    }
    
    public int getOriginX()
    {
      return originX;
    }
    
    public int getOriginY()
    {
      return originY;
    }
    
    public boolean addSlot(Stitcher.Holder p_94182_1_)
    {
      if (holder != null)
      {
        return false;
      }
      

      int var2 = p_94182_1_.getWidth();
      int var3 = p_94182_1_.getHeight();
      
      if ((var2 <= width) && (var3 <= height))
      {
        if ((var2 == width) && (var3 == height))
        {
          holder = p_94182_1_;
          return true;
        }
        

        if (subSlots == null)
        {
          subSlots = Lists.newArrayListWithCapacity(1);
          subSlots.add(new Slot(originX, originY, var2, var3));
          int var8 = width - var2;
          int var9 = height - var3;
          
          if ((var9 > 0) && (var8 > 0))
          {
            int var6 = Math.max(height, var8);
            int var7 = Math.max(width, var9);
            
            if (var6 >= var7)
            {
              subSlots.add(new Slot(originX, originY + var3, var2, var9));
              subSlots.add(new Slot(originX + var2, originY, var8, height));
            }
            else
            {
              subSlots.add(new Slot(originX + var2, originY, var8, var3));
              subSlots.add(new Slot(originX, originY + var3, width, var9));
            }
          }
          else if (var8 == 0)
          {
            subSlots.add(new Slot(originX, originY + var3, var2, var9));
          }
          else if (var9 == 0)
          {
            subSlots.add(new Slot(originX + var2, originY, var8, var3));
          }
        }
        
        Iterator var81 = subSlots.iterator();
        
        while (var81.hasNext())
        {
          Slot var91 = (Slot)var81.next();
          
          if (var91.addSlot(p_94182_1_))
          {
            return true;
          }
        }
        
        return false;
      }
      


      return false;
    }
    


    public void getAllStitchSlots(List p_94184_1_)
    {
      if (holder != null)
      {
        p_94184_1_.add(this);
      }
      else if (subSlots != null)
      {
        Iterator var2 = subSlots.iterator();
        
        while (var2.hasNext())
        {
          Slot var3 = (Slot)var2.next();
          var3.getAllStitchSlots(p_94184_1_);
        }
      }
    }
    
    public String toString()
    {
      return "Slot{originX=" + originX + ", originY=" + originY + ", width=" + width + ", height=" + height + ", texture=" + holder + ", subSlots=" + subSlots + '}';
    }
  }
}
