package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAITasks
{
  private static final Logger logger = ;
  

  private List taskEntries = Lists.newArrayList();
  

  private List executingTaskEntries = Lists.newArrayList();
  
  private final Profiler theProfiler;
  
  private int tickCount;
  private int tickRate = 3;
  private static final String __OBFID = "CL_00001588";
  
  public EntityAITasks(Profiler p_i1628_1_)
  {
    theProfiler = p_i1628_1_;
  }
  



  public void addTask(int p_75776_1_, EntityAIBase p_75776_2_)
  {
    taskEntries.add(new EntityAITaskEntry(p_75776_1_, p_75776_2_));
  }
  



  public void removeTask(EntityAIBase p_85156_1_)
  {
    Iterator var2 = taskEntries.iterator();
    
    while (var2.hasNext())
    {
      EntityAITaskEntry var3 = (EntityAITaskEntry)var2.next();
      EntityAIBase var4 = action;
      
      if (var4 == p_85156_1_)
      {
        if (executingTaskEntries.contains(var3))
        {
          var4.resetTask();
          executingTaskEntries.remove(var3);
        }
        
        var2.remove();
      }
    }
  }
  
  public void onUpdateTasks()
  {
    theProfiler.startSection("goalSetup");
    


    if (tickCount++ % tickRate == 0)
    {
      Iterator var1 = taskEntries.iterator();
      
      while (var1.hasNext())
      {
        EntityAITaskEntry var2 = (EntityAITaskEntry)var1.next();
        boolean var3 = executingTaskEntries.contains(var2);
        
        if (var3)
        {
          if ((!canUse(var2)) || (!canContinue(var2)))
          {



            action.resetTask();
            executingTaskEntries.remove(var2);
          }
        }
        else if ((canUse(var2)) && (action.shouldExecute()))
        {
          action.startExecuting();
          executingTaskEntries.add(var2);
        }
      }
    }
    else
    {
      var1 = executingTaskEntries.iterator();
      
      while (var1.hasNext())
      {
        EntityAITaskEntry var2 = (EntityAITaskEntry)var1.next();
        
        if (!canContinue(var2))
        {
          action.resetTask();
          var1.remove();
        }
      }
    }
    
    theProfiler.endSection();
    theProfiler.startSection("goalTick");
    Iterator var1 = executingTaskEntries.iterator();
    
    while (var1.hasNext())
    {
      EntityAITaskEntry var2 = (EntityAITaskEntry)var1.next();
      action.updateTask();
    }
    
    theProfiler.endSection();
  }
  



  private boolean canContinue(EntityAITaskEntry p_75773_1_)
  {
    boolean var2 = action.continueExecuting();
    return var2;
  }
  




  private boolean canUse(EntityAITaskEntry p_75775_1_)
  {
    Iterator var2 = taskEntries.iterator();
    
    while (var2.hasNext())
    {
      EntityAITaskEntry var3 = (EntityAITaskEntry)var2.next();
      
      if (var3 != p_75775_1_)
      {
        if (priority >= priority)
        {
          if ((!areTasksCompatible(p_75775_1_, var3)) && (executingTaskEntries.contains(var3)))
          {
            return false;
          }
        }
        else if ((!action.isInterruptible()) && (executingTaskEntries.contains(var3)))
        {
          return false;
        }
      }
    }
    
    return true;
  }
  



  private boolean areTasksCompatible(EntityAITaskEntry p_75777_1_, EntityAITaskEntry p_75777_2_)
  {
    return (action.getMutexBits() & action.getMutexBits()) == 0;
  }
  
  class EntityAITaskEntry
  {
    public EntityAIBase action;
    public int priority;
    private static final String __OBFID = "CL_00001589";
    
    public EntityAITaskEntry(int p_i1627_2_, EntityAIBase p_i1627_3_)
    {
      priority = p_i1627_2_;
      action = p_i1627_3_;
    }
  }
}
