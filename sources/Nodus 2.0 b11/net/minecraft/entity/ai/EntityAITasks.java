/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.profiler.Profiler;
/*   7:    */ import org.apache.logging.log4j.LogManager;
/*   8:    */ import org.apache.logging.log4j.Logger;
/*   9:    */ 
/*  10:    */ public class EntityAITasks
/*  11:    */ {
/*  12: 12 */   private static final Logger logger = ;
/*  13: 15 */   private List taskEntries = new ArrayList();
/*  14: 18 */   private List executingTaskEntries = new ArrayList();
/*  15:    */   private final Profiler theProfiler;
/*  16:    */   private int tickCount;
/*  17: 23 */   private int tickRate = 3;
/*  18:    */   private static final String __OBFID = "CL_00001588";
/*  19:    */   
/*  20:    */   public EntityAITasks(Profiler par1Profiler)
/*  21:    */   {
/*  22: 28 */     this.theProfiler = par1Profiler;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void addTask(int par1, EntityAIBase par2EntityAIBase)
/*  26:    */   {
/*  27: 33 */     this.taskEntries.add(new EntityAITaskEntry(par1, par2EntityAIBase));
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void removeTask(EntityAIBase par1EntityAIBase)
/*  31:    */   {
/*  32: 41 */     Iterator var2 = this.taskEntries.iterator();
/*  33: 43 */     while (var2.hasNext())
/*  34:    */     {
/*  35: 45 */       EntityAITaskEntry var3 = (EntityAITaskEntry)var2.next();
/*  36: 46 */       EntityAIBase var4 = var3.action;
/*  37: 48 */       if (var4 == par1EntityAIBase)
/*  38:    */       {
/*  39: 50 */         if (this.executingTaskEntries.contains(var3))
/*  40:    */         {
/*  41: 52 */           var4.resetTask();
/*  42: 53 */           this.executingTaskEntries.remove(var3);
/*  43:    */         }
/*  44: 56 */         var2.remove();
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void onUpdateTasks()
/*  50:    */   {
/*  51: 63 */     ArrayList var1 = new ArrayList();
/*  52: 67 */     if (this.tickCount++ % this.tickRate == 0)
/*  53:    */     {
/*  54: 69 */       Iterator var2 = this.taskEntries.iterator();
/*  55: 71 */       while (var2.hasNext())
/*  56:    */       {
/*  57: 73 */         EntityAITaskEntry var3 = (EntityAITaskEntry)var2.next();
/*  58: 74 */         boolean var4 = this.executingTaskEntries.contains(var3);
/*  59: 76 */         if (var4)
/*  60:    */         {
/*  61: 78 */           if ((!canUse(var3)) || (!canContinue(var3)))
/*  62:    */           {
/*  63: 83 */             var3.action.resetTask();
/*  64: 84 */             this.executingTaskEntries.remove(var3);
/*  65:    */           }
/*  66:    */         }
/*  67: 87 */         else if ((canUse(var3)) && (var3.action.shouldExecute()))
/*  68:    */         {
/*  69: 89 */           var1.add(var3);
/*  70: 90 */           this.executingTaskEntries.add(var3);
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76: 96 */       var2 = this.executingTaskEntries.iterator();
/*  77: 98 */       while (var2.hasNext())
/*  78:    */       {
/*  79:100 */         EntityAITaskEntry var3 = (EntityAITaskEntry)var2.next();
/*  80:102 */         if (!var3.action.continueExecuting())
/*  81:    */         {
/*  82:104 */           var3.action.resetTask();
/*  83:105 */           var2.remove();
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87:110 */     this.theProfiler.startSection("goalStart");
/*  88:111 */     Iterator var2 = var1.iterator();
/*  89:113 */     while (var2.hasNext())
/*  90:    */     {
/*  91:115 */       EntityAITaskEntry var3 = (EntityAITaskEntry)var2.next();
/*  92:116 */       this.theProfiler.startSection(var3.action.getClass().getSimpleName());
/*  93:117 */       var3.action.startExecuting();
/*  94:118 */       this.theProfiler.endSection();
/*  95:    */     }
/*  96:121 */     this.theProfiler.endSection();
/*  97:122 */     this.theProfiler.startSection("goalTick");
/*  98:123 */     var2 = this.executingTaskEntries.iterator();
/*  99:125 */     while (var2.hasNext())
/* 100:    */     {
/* 101:127 */       EntityAITaskEntry var3 = (EntityAITaskEntry)var2.next();
/* 102:128 */       var3.action.updateTask();
/* 103:    */     }
/* 104:131 */     this.theProfiler.endSection();
/* 105:    */   }
/* 106:    */   
/* 107:    */   private boolean canContinue(EntityAITaskEntry par1EntityAITaskEntry)
/* 108:    */   {
/* 109:139 */     this.theProfiler.startSection("canContinue");
/* 110:140 */     boolean var2 = par1EntityAITaskEntry.action.continueExecuting();
/* 111:141 */     this.theProfiler.endSection();
/* 112:142 */     return var2;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private boolean canUse(EntityAITaskEntry par1EntityAITaskEntry)
/* 116:    */   {
/* 117:151 */     this.theProfiler.startSection("canUse");
/* 118:152 */     Iterator var2 = this.taskEntries.iterator();
/* 119:154 */     while (var2.hasNext())
/* 120:    */     {
/* 121:156 */       EntityAITaskEntry var3 = (EntityAITaskEntry)var2.next();
/* 122:158 */       if (var3 != par1EntityAITaskEntry) {
/* 123:160 */         if (par1EntityAITaskEntry.priority >= var3.priority)
/* 124:    */         {
/* 125:162 */           if ((this.executingTaskEntries.contains(var3)) && (!areTasksCompatible(par1EntityAITaskEntry, var3)))
/* 126:    */           {
/* 127:164 */             this.theProfiler.endSection();
/* 128:165 */             return false;
/* 129:    */           }
/* 130:    */         }
/* 131:168 */         else if ((this.executingTaskEntries.contains(var3)) && (!var3.action.isInterruptible()))
/* 132:    */         {
/* 133:170 */           this.theProfiler.endSection();
/* 134:171 */           return false;
/* 135:    */         }
/* 136:    */       }
/* 137:    */     }
/* 138:176 */     this.theProfiler.endSection();
/* 139:177 */     return true;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private boolean areTasksCompatible(EntityAITaskEntry par1EntityAITaskEntry, EntityAITaskEntry par2EntityAITaskEntry)
/* 143:    */   {
/* 144:185 */     return (par1EntityAITaskEntry.action.getMutexBits() & par2EntityAITaskEntry.action.getMutexBits()) == 0;
/* 145:    */   }
/* 146:    */   
/* 147:    */   class EntityAITaskEntry
/* 148:    */   {
/* 149:    */     public EntityAIBase action;
/* 150:    */     public int priority;
/* 151:    */     private static final String __OBFID = "CL_00001589";
/* 152:    */     
/* 153:    */     public EntityAITaskEntry(int par2, EntityAIBase par3EntityAIBase)
/* 154:    */     {
/* 155:196 */       this.priority = par2;
/* 156:197 */       this.action = par3EntityAIBase;
/* 157:    */     }
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAITasks
 * JD-Core Version:    0.7.0.1
 */