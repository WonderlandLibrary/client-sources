package net.minecraft.entity.ai;

import net.minecraft.profiler.*;
import java.util.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;

public class EntityAITasks
{
    private final Profiler theProfiler;
    private static final Logger logger;
    private static final String[] I;
    private List<EntityAITaskEntry> executingTaskEntries;
    private int tickCount;
    private List<EntityAITaskEntry> taskEntries;
    private int tickRate;
    
    public void removeTask(final EntityAIBase entityAIBase) {
        final Iterator<EntityAITaskEntry> iterator = this.taskEntries.iterator();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityAITaskEntry entityAITaskEntry = iterator.next();
            final EntityAIBase action = entityAITaskEntry.action;
            if (action == entityAIBase) {
                if (this.executingTaskEntries.contains(entityAITaskEntry)) {
                    action.resetTask();
                    this.executingTaskEntries.remove(entityAITaskEntry);
                }
                iterator.remove();
            }
        }
    }
    
    public void addTask(final int n, final EntityAIBase entityAIBase) {
        this.taskEntries.add(new EntityAITaskEntry(n, entityAIBase));
    }
    
    public void onUpdateTasks() {
        this.theProfiler.startSection(EntityAITasks.I["".length()]);
        final int tickCount = this.tickCount;
        this.tickCount = tickCount + " ".length();
        if (tickCount % this.tickRate == 0) {
            for (final EntityAITaskEntry entityAITaskEntry : this.taskEntries) {
                if (!this.executingTaskEntries.contains(entityAITaskEntry)) {
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
                else {
                    if (this.canUse(entityAITaskEntry) && this.canContinue(entityAITaskEntry)) {
                        continue;
                    }
                    entityAITaskEntry.action.resetTask();
                    this.executingTaskEntries.remove(entityAITaskEntry);
                }
                if (this.canUse(entityAITaskEntry) && entityAITaskEntry.action.shouldExecute()) {
                    entityAITaskEntry.action.startExecuting();
                    this.executingTaskEntries.add(entityAITaskEntry);
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                    continue;
                }
            }
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            final Iterator<EntityAITaskEntry> iterator2 = this.executingTaskEntries.iterator();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final EntityAITaskEntry entityAITaskEntry2 = iterator2.next();
                if (!this.canContinue(entityAITaskEntry2)) {
                    entityAITaskEntry2.action.resetTask();
                    iterator2.remove();
                }
            }
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection(EntityAITasks.I[" ".length()]);
        final Iterator<EntityAITaskEntry> iterator3 = this.executingTaskEntries.iterator();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (iterator3.hasNext()) {
            iterator3.next().action.updateTask();
        }
        this.theProfiler.endSection();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("2\u000e+\b\u001b0\u0015?\u0014", "UaJdH");
        EntityAITasks.I[" ".length()] = I("3,\b \u001c= \u0002", "TCiLH");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAITasks(final Profiler theProfiler) {
        this.taskEntries = (List<EntityAITaskEntry>)Lists.newArrayList();
        this.executingTaskEntries = (List<EntityAITaskEntry>)Lists.newArrayList();
        this.tickRate = "   ".length();
        this.theProfiler = theProfiler;
    }
    
    private boolean canContinue(final EntityAITaskEntry entityAITaskEntry) {
        return entityAITaskEntry.action.continueExecuting();
    }
    
    private boolean areTasksCompatible(final EntityAITaskEntry entityAITaskEntry, final EntityAITaskEntry entityAITaskEntry2) {
        if ((entityAITaskEntry.action.getMutexBits() & entityAITaskEntry2.action.getMutexBits()) == 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean canUse(final EntityAITaskEntry entityAITaskEntry) {
        final Iterator<EntityAITaskEntry> iterator = this.taskEntries.iterator();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityAITaskEntry entityAITaskEntry2 = iterator.next();
            if (entityAITaskEntry2 != entityAITaskEntry) {
                if (entityAITaskEntry.priority >= entityAITaskEntry2.priority) {
                    if (!this.areTasksCompatible(entityAITaskEntry, entityAITaskEntry2) && this.executingTaskEntries.contains(entityAITaskEntry2)) {
                        return "".length() != 0;
                    }
                    continue;
                }
                else {
                    if (!entityAITaskEntry2.action.isInterruptible() && this.executingTaskEntries.contains(entityAITaskEntry2)) {
                        return "".length() != 0;
                    }
                    continue;
                }
            }
        }
        return " ".length() != 0;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    class EntityAITaskEntry
    {
        public int priority;
        public EntityAIBase action;
        final EntityAITasks this$0;
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public EntityAITaskEntry(final EntityAITasks this$0, final int priority, final EntityAIBase action) {
            this.this$0 = this$0;
            this.priority = priority;
            this.action = action;
        }
    }
}
