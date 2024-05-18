package net.minecraft.src;

import java.util.*;

public class EntityAITasks
{
    private List taskEntries;
    private List executingTaskEntries;
    private final Profiler theProfiler;
    private int field_75778_d;
    private int field_75779_e;
    
    public EntityAITasks(final Profiler par1Profiler) {
        this.taskEntries = new ArrayList();
        this.executingTaskEntries = new ArrayList();
        this.field_75778_d = 0;
        this.field_75779_e = 3;
        this.theProfiler = par1Profiler;
    }
    
    public void addTask(final int par1, final EntityAIBase par2EntityAIBase) {
        this.taskEntries.add(new EntityAITaskEntry(this, par1, par2EntityAIBase));
    }
    
    public void removeTask(final EntityAIBase par1EntityAIBase) {
        final Iterator var2 = this.taskEntries.iterator();
        while (var2.hasNext()) {
            final EntityAITaskEntry var3 = var2.next();
            final EntityAIBase var4 = var3.action;
            if (var4 == par1EntityAIBase) {
                if (this.executingTaskEntries.contains(var3)) {
                    var4.resetTask();
                    this.executingTaskEntries.remove(var3);
                }
                var2.remove();
            }
        }
    }
    
    public void onUpdateTasks() {
        final ArrayList var1 = new ArrayList();
        if (this.field_75778_d++ % this.field_75779_e == 0) {
            for (final EntityAITaskEntry var3 : this.taskEntries) {
                final boolean var4 = this.executingTaskEntries.contains(var3);
                if (var4) {
                    if (this.canUse(var3) && this.canContinue(var3)) {
                        continue;
                    }
                    var3.action.resetTask();
                    this.executingTaskEntries.remove(var3);
                }
                if (this.canUse(var3) && var3.action.shouldExecute()) {
                    var1.add(var3);
                    this.executingTaskEntries.add(var3);
                }
            }
        }
        else {
            final Iterator var2 = this.executingTaskEntries.iterator();
            while (var2.hasNext()) {
                final EntityAITaskEntry var3 = var2.next();
                if (!var3.action.continueExecuting()) {
                    var3.action.resetTask();
                    var2.remove();
                }
            }
        }
        this.theProfiler.startSection("goalStart");
        Iterator var2 = var1.iterator();
        while (var2.hasNext()) {
            final EntityAITaskEntry var3 = var2.next();
            this.theProfiler.startSection(var3.action.getClass().getSimpleName());
            var3.action.startExecuting();
            this.theProfiler.endSection();
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("goalTick");
        var2 = this.executingTaskEntries.iterator();
        while (var2.hasNext()) {
            final EntityAITaskEntry var3 = var2.next();
            var3.action.updateTask();
        }
        this.theProfiler.endSection();
    }
    
    private boolean canContinue(final EntityAITaskEntry par1EntityAITaskEntry) {
        this.theProfiler.startSection("canContinue");
        final boolean var2 = par1EntityAITaskEntry.action.continueExecuting();
        this.theProfiler.endSection();
        return var2;
    }
    
    private boolean canUse(final EntityAITaskEntry par1EntityAITaskEntry) {
        this.theProfiler.startSection("canUse");
        for (final EntityAITaskEntry var3 : this.taskEntries) {
            if (var3 != par1EntityAITaskEntry) {
                if (par1EntityAITaskEntry.priority >= var3.priority) {
                    if (this.executingTaskEntries.contains(var3) && !this.areTasksCompatible(par1EntityAITaskEntry, var3)) {
                        this.theProfiler.endSection();
                        return false;
                    }
                    continue;
                }
                else {
                    if (this.executingTaskEntries.contains(var3) && !var3.action.isInterruptible()) {
                        this.theProfiler.endSection();
                        return false;
                    }
                    continue;
                }
            }
        }
        this.theProfiler.endSection();
        return true;
    }
    
    private boolean areTasksCompatible(final EntityAITaskEntry par1EntityAITaskEntry, final EntityAITaskEntry par2EntityAITaskEntry) {
        return (par1EntityAITaskEntry.action.getMutexBits() & par2EntityAITaskEntry.action.getMutexBits()) == 0x0;
    }
}
