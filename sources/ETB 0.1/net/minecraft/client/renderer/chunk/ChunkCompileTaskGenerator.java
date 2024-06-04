package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;

public class ChunkCompileTaskGenerator
{
  private final RenderChunk field_178553_a;
  private final ReentrantLock field_178551_b = new ReentrantLock();
  private final List field_178552_c = Lists.newArrayList();
  private final Type field_178549_d;
  private RegionRenderCacheBuilder field_178550_e;
  private CompiledChunk field_178547_f;
  private Status field_178548_g;
  private boolean field_178554_h;
  private static final String __OBFID = "CL_00002466";
  
  public ChunkCompileTaskGenerator(RenderChunk p_i46208_1_, Type p_i46208_2_)
  {
    field_178548_g = Status.PENDING;
    field_178553_a = p_i46208_1_;
    field_178549_d = p_i46208_2_;
  }
  
  public Status func_178546_a()
  {
    return field_178548_g;
  }
  
  public RenderChunk func_178536_b()
  {
    return field_178553_a;
  }
  
  public CompiledChunk func_178544_c()
  {
    return field_178547_f;
  }
  
  public void func_178543_a(CompiledChunk p_178543_1_)
  {
    field_178547_f = p_178543_1_;
  }
  
  public RegionRenderCacheBuilder func_178545_d()
  {
    return field_178550_e;
  }
  
  public void func_178541_a(RegionRenderCacheBuilder p_178541_1_)
  {
    field_178550_e = p_178541_1_;
  }
  
  public void func_178535_a(Status p_178535_1_)
  {
    field_178551_b.lock();
    
    try
    {
      field_178548_g = p_178535_1_;
    }
    finally
    {
      field_178551_b.unlock();
    }
  }
  
  public void func_178542_e()
  {
    field_178551_b.lock();
    
    try
    {
      if ((field_178549_d == Type.REBUILD_CHUNK) && (field_178548_g != Status.DONE))
      {
        field_178553_a.func_178575_a(true);
      }
      
      field_178554_h = true;
      field_178548_g = Status.DONE;
      Iterator var1 = field_178552_c.iterator();
      
      while (var1.hasNext())
      {
        Runnable var2 = (Runnable)var1.next();
        var2.run();
      }
    }
    finally
    {
      field_178551_b.unlock();
    }
  }
  
  public void func_178539_a(Runnable p_178539_1_)
  {
    field_178551_b.lock();
    
    try
    {
      field_178552_c.add(p_178539_1_);
      
      if (field_178554_h)
      {
        p_178539_1_.run();
      }
    }
    finally
    {
      field_178551_b.unlock();
    }
  }
  
  public ReentrantLock func_178540_f()
  {
    return field_178551_b;
  }
  
  public Type func_178538_g()
  {
    return field_178549_d;
  }
  
  public boolean func_178537_h()
  {
    return field_178554_h;
  }
  
  public static enum Status
  {
    PENDING("PENDING", 0, "PENDING", 0), 
    COMPILING("COMPILING", 1, "COMPILING", 1), 
    UPLOADING("UPLOADING", 2, "UPLOADING", 2), 
    DONE("DONE", 3, "DONE", 3);
    private static final Status[] $VALUES = { PENDING, COMPILING, UPLOADING, DONE };
    
    private static final String __OBFID = "CL_00002465";
    
    private Status(String p_i46385_1_, int p_i46385_2_, String p_i46207_1_, int p_i46207_2_) {}
  }
  
  public static enum Type
  {
    REBUILD_CHUNK("REBUILD_CHUNK", 0, "REBUILD_CHUNK", 0), 
    RESORT_TRANSPARENCY("RESORT_TRANSPARENCY", 1, "RESORT_TRANSPARENCY", 1);
    private static final Type[] $VALUES = { REBUILD_CHUNK, RESORT_TRANSPARENCY };
    private static final String __OBFID = "CL_00002464";
    
    private Type(String p_i46386_1_, int p_i46386_2_, String p_i46206_1_, int p_i46206_2_) {}
  }
}
