package net.minecraft.util;


public class IntHashMap
{
  private transient Entry[] slots = new Entry[16];
  

  private transient int count;
  

  private int threshold = 12;
  

  private final float growFactor = 0.75F;
  
  private static final String __OBFID = "CL_00001490";
  
  public IntHashMap() {}
  
  private static int computeHash(int p_76044_0_)
  {
    p_76044_0_ ^= p_76044_0_ >>> 20 ^ p_76044_0_ >>> 12;
    return p_76044_0_ ^ p_76044_0_ >>> 7 ^ p_76044_0_ >>> 4;
  }
  



  private static int getSlotIndex(int p_76043_0_, int p_76043_1_)
  {
    return p_76043_0_ & p_76043_1_ - 1;
  }
  



  public Object lookup(int p_76041_1_)
  {
    int var2 = computeHash(p_76041_1_);
    
    for (Entry var3 = slots[getSlotIndex(var2, slots.length)]; var3 != null; var3 = nextEntry)
    {
      if (hashEntry == p_76041_1_)
      {
        return valueEntry;
      }
    }
    
    return null;
  }
  



  public boolean containsItem(int p_76037_1_)
  {
    return lookupEntry(p_76037_1_) != null;
  }
  



  final Entry lookupEntry(int p_76045_1_)
  {
    int var2 = computeHash(p_76045_1_);
    
    for (Entry var3 = slots[getSlotIndex(var2, slots.length)]; var3 != null; var3 = nextEntry)
    {
      if (hashEntry == p_76045_1_)
      {
        return var3;
      }
    }
    
    return null;
  }
  



  public void addKey(int p_76038_1_, Object p_76038_2_)
  {
    int var3 = computeHash(p_76038_1_);
    int var4 = getSlotIndex(var3, slots.length);
    
    for (Entry var5 = slots[var4]; var5 != null; var5 = nextEntry)
    {
      if (hashEntry == p_76038_1_)
      {
        valueEntry = p_76038_2_;
        return;
      }
    }
    
    insert(var3, p_76038_1_, p_76038_2_, var4);
  }
  



  private void grow(int p_76047_1_)
  {
    Entry[] var2 = slots;
    int var3 = var2.length;
    
    if (var3 == 1073741824)
    {
      threshold = Integer.MAX_VALUE;
    }
    else
    {
      Entry[] var4 = new Entry[p_76047_1_];
      copyTo(var4);
      slots = var4;
      threshold = ((int)(p_76047_1_ * 0.75F));
    }
  }
  



  private void copyTo(Entry[] p_76048_1_)
  {
    Entry[] var2 = slots;
    int var3 = p_76048_1_.length;
    
    for (int var4 = 0; var4 < var2.length; var4++)
    {
      Entry var5 = var2[var4];
      
      if (var5 != null)
      {
        var2[var4] = null;
        
        Entry var6;
        do
        {
          var6 = nextEntry;
          int var7 = getSlotIndex(slotHash, var3);
          nextEntry = p_76048_1_[var7];
          p_76048_1_[var7] = var5;
          var5 = var6;
        }
        while (var6 != null);
      }
    }
  }
  



  public Object removeObject(int p_76049_1_)
  {
    Entry var2 = removeEntry(p_76049_1_);
    return var2 == null ? null : valueEntry;
  }
  



  final Entry removeEntry(int p_76036_1_)
  {
    int var2 = computeHash(p_76036_1_);
    int var3 = getSlotIndex(var2, slots.length);
    Entry var4 = slots[var3];
    
    Entry var6;
    
    for (Entry var5 = var4; var5 != null; var5 = var6)
    {
      var6 = nextEntry;
      
      if (hashEntry == p_76036_1_)
      {
        count -= 1;
        
        if (var4 == var5)
        {
          slots[var3] = var6;
        }
        else
        {
          nextEntry = var6;
        }
        
        return var5;
      }
      
      var4 = var5;
    }
    
    return var5;
  }
  



  public void clearMap()
  {
    Entry[] var1 = slots;
    
    for (int var2 = 0; var2 < var1.length; var2++)
    {
      var1[var2] = null;
    }
    
    count = 0;
  }
  



  private void insert(int p_76040_1_, int p_76040_2_, Object p_76040_3_, int p_76040_4_)
  {
    Entry var5 = slots[p_76040_4_];
    slots[p_76040_4_] = new Entry(p_76040_1_, p_76040_2_, p_76040_3_, var5);
    
    if (count++ >= threshold)
    {
      grow(2 * slots.length);
    }
  }
  
  static class Entry
  {
    final int hashEntry;
    Object valueEntry;
    Entry nextEntry;
    final int slotHash;
    private static final String __OBFID = "CL_00001491";
    
    Entry(int p_i1552_1_, int p_i1552_2_, Object p_i1552_3_, Entry p_i1552_4_)
    {
      valueEntry = p_i1552_3_;
      nextEntry = p_i1552_4_;
      hashEntry = p_i1552_2_;
      slotHash = p_i1552_1_;
    }
    
    public final int getHash()
    {
      return hashEntry;
    }
    
    public final Object getValue()
    {
      return valueEntry;
    }
    
    public final boolean equals(Object p_equals_1_)
    {
      if (!(p_equals_1_ instanceof Entry))
      {
        return false;
      }
      

      Entry var2 = (Entry)p_equals_1_;
      Integer var3 = Integer.valueOf(getHash());
      Integer var4 = Integer.valueOf(var2.getHash());
      
      if ((var3 == var4) || ((var3 != null) && (var3.equals(var4))))
      {
        Object var5 = getValue();
        Object var6 = var2.getValue();
        
        if ((var5 == var6) || ((var5 != null) && (var5.equals(var6))))
        {
          return true;
        }
      }
      
      return false;
    }
    

    public final int hashCode()
    {
      return IntHashMap.computeHash(hashEntry);
    }
    
    public final String toString()
    {
      return getHash() + "=" + getValue();
    }
  }
}
