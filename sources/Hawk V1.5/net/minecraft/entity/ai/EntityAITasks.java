package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAITasks {
   private List taskEntries = Lists.newArrayList();
   private final Profiler theProfiler;
   private List executingTaskEntries = Lists.newArrayList();
   private static final Logger logger = LogManager.getLogger();
   private static final String __OBFID = "CL_00001588";
   private int tickRate = 3;
   private int tickCount;

   private boolean canUse(EntityAITasks.EntityAITaskEntry var1) {
      Iterator var2 = this.taskEntries.iterator();

      while(var2.hasNext()) {
         EntityAITasks.EntityAITaskEntry var3 = (EntityAITasks.EntityAITaskEntry)var2.next();
         if (var3 != var1) {
            if (var1.priority >= var3.priority) {
               if (!this.areTasksCompatible(var1, var3) && this.executingTaskEntries.contains(var3)) {
                  return false;
               }
            } else if (!var3.action.isInterruptible() && this.executingTaskEntries.contains(var3)) {
               return false;
            }
         }
      }

      return true;
   }

   public void addTask(int var1, EntityAIBase var2) {
      this.taskEntries.add(new EntityAITasks.EntityAITaskEntry(this, var1, var2));
   }

   public void onUpdateTasks() {
      this.theProfiler.startSection("goalSetup");
      Iterator var1;
      EntityAITasks.EntityAITaskEntry var2;
      if (this.tickCount++ % this.tickRate == 0) {
         var1 = this.taskEntries.iterator();

         label57:
         while(true) {
            while(true) {
               if (!var1.hasNext()) {
                  break label57;
               }

               var2 = (EntityAITasks.EntityAITaskEntry)var1.next();
               boolean var3 = this.executingTaskEntries.contains(var2);
               if (!var3) {
                  break;
               }

               if (!this.canUse(var2) || !this.canContinue(var2)) {
                  var2.action.resetTask();
                  this.executingTaskEntries.remove(var2);
                  break;
               }
            }

            if (this.canUse(var2) && var2.action.shouldExecute()) {
               var2.action.startExecuting();
               this.executingTaskEntries.add(var2);
            }
         }
      } else {
         var1 = this.executingTaskEntries.iterator();

         while(var1.hasNext()) {
            var2 = (EntityAITasks.EntityAITaskEntry)var1.next();
            if (!this.canContinue(var2)) {
               var2.action.resetTask();
               var1.remove();
            }
         }
      }

      this.theProfiler.endSection();
      this.theProfiler.startSection("goalTick");
      var1 = this.executingTaskEntries.iterator();

      while(var1.hasNext()) {
         var2 = (EntityAITasks.EntityAITaskEntry)var1.next();
         var2.action.updateTask();
      }

      this.theProfiler.endSection();
   }

   public EntityAITasks(Profiler var1) {
      this.theProfiler = var1;
   }

   public void removeTask(EntityAIBase var1) {
      Iterator var2 = this.taskEntries.iterator();

      while(var2.hasNext()) {
         EntityAITasks.EntityAITaskEntry var3 = (EntityAITasks.EntityAITaskEntry)var2.next();
         EntityAIBase var4 = var3.action;
         if (var4 == var1) {
            if (this.executingTaskEntries.contains(var3)) {
               var4.resetTask();
               this.executingTaskEntries.remove(var3);
            }

            var2.remove();
         }
      }

   }

   private boolean areTasksCompatible(EntityAITasks.EntityAITaskEntry var1, EntityAITasks.EntityAITaskEntry var2) {
      return (var1.action.getMutexBits() & var2.action.getMutexBits()) == 0;
   }

   private boolean canContinue(EntityAITasks.EntityAITaskEntry var1) {
      boolean var2 = var1.action.continueExecuting();
      return var2;
   }

   class EntityAITaskEntry {
      private static final String __OBFID = "CL_00001589";
      final EntityAITasks this$0;
      public int priority;
      public EntityAIBase action;

      public EntityAITaskEntry(EntityAITasks var1, int var2, EntityAIBase var3) {
         this.this$0 = var1;
         this.priority = var2;
         this.action = var3;
      }
   }
}
