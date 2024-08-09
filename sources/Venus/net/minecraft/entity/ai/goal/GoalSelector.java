/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import com.google.common.collect.Sets;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.profiler.IProfiler;
import net.optifine.util.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoalSelector {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final PrioritizedGoal DUMMY = new PrioritizedGoal(Integer.MAX_VALUE, new Goal(){

        @Override
        public boolean shouldExecute() {
            return true;
        }
    }){

        @Override
        public boolean isRunning() {
            return true;
        }
    };
    private final Map<Goal.Flag, PrioritizedGoal> flagGoals = new EnumMap<Goal.Flag, PrioritizedGoal>(Goal.Flag.class);
    private final Set<PrioritizedGoal> goals = Sets.newLinkedHashSet();
    private final Supplier<IProfiler> profiler;
    private final EnumSet<Goal.Flag> disabledFlags = EnumSet.noneOf(Goal.Flag.class);
    private int tickRate = 3;

    public GoalSelector(Supplier<IProfiler> supplier) {
        this.profiler = supplier;
    }

    public void addGoal(int n, Goal goal) {
        this.goals.add(new PrioritizedGoal(n, goal));
    }

    public void removeGoal(Goal goal) {
        this.goals.stream().filter(arg_0 -> GoalSelector.lambda$removeGoal$0(goal, arg_0)).filter(PrioritizedGoal::isRunning).forEach(PrioritizedGoal::resetTask);
        this.goals.removeIf(arg_0 -> GoalSelector.lambda$removeGoal$1(goal, arg_0));
    }

    public void tick() {
        IProfiler iProfiler = this.profiler.get();
        iProfiler.startSection("goalCleanup");
        if (this.goals.size() > 0) {
            for (PrioritizedGoal prioritizedGoal : this.goals) {
                if (!prioritizedGoal.isRunning() || prioritizedGoal.isRunning() && !CollectionUtils.anyMatch(prioritizedGoal.getMutexFlags(), this.disabledFlags) && prioritizedGoal.shouldContinueExecuting()) continue;
                prioritizedGoal.resetTask();
            }
        }
        if (this.flagGoals.size() > 0) {
            this.flagGoals.forEach(this::lambda$tick$2);
        }
        iProfiler.endSection();
        iProfiler.startSection("goalUpdate");
        if (this.goals.size() > 0) {
            for (PrioritizedGoal prioritizedGoal : this.goals) {
                if (prioritizedGoal.isRunning() || !CollectionUtils.noneMatch(prioritizedGoal.getMutexFlags(), this.disabledFlags) || !GoalSelector.allPreemptedBy(prioritizedGoal, prioritizedGoal.getMutexFlags(), this.flagGoals) || !prioritizedGoal.shouldExecute()) continue;
                GoalSelector.resetTasks(prioritizedGoal, prioritizedGoal.getMutexFlags(), this.flagGoals);
                prioritizedGoal.startExecuting();
            }
        }
        iProfiler.endSection();
        iProfiler.startSection("goalTick");
        if (this.goals.size() > 0) {
            for (PrioritizedGoal prioritizedGoal : this.goals) {
                if (!prioritizedGoal.isRunning()) continue;
                prioritizedGoal.tick();
            }
        }
        iProfiler.endSection();
    }

    private static boolean allPreemptedBy(PrioritizedGoal prioritizedGoal, EnumSet<Goal.Flag> enumSet, Map<Goal.Flag, PrioritizedGoal> map) {
        if (enumSet.isEmpty()) {
            return false;
        }
        for (Goal.Flag flag : enumSet) {
            PrioritizedGoal prioritizedGoal2 = map.getOrDefault((Object)flag, DUMMY);
            if (prioritizedGoal2.isPreemptedBy(prioritizedGoal)) continue;
            return true;
        }
        return false;
    }

    private static void resetTasks(PrioritizedGoal prioritizedGoal, EnumSet<Goal.Flag> enumSet, Map<Goal.Flag, PrioritizedGoal> map) {
        if (!enumSet.isEmpty()) {
            for (Goal.Flag flag : enumSet) {
                PrioritizedGoal prioritizedGoal2 = map.getOrDefault((Object)flag, DUMMY);
                prioritizedGoal2.resetTask();
                map.put(flag, prioritizedGoal);
            }
        }
    }

    public Stream<PrioritizedGoal> getRunningGoals() {
        return this.goals.stream().filter(PrioritizedGoal::isRunning);
    }

    public void disableFlag(Goal.Flag flag) {
        this.disabledFlags.add(flag);
    }

    public void enableFlag(Goal.Flag flag) {
        this.disabledFlags.remove((Object)flag);
    }

    public void setFlag(Goal.Flag flag, boolean bl) {
        if (bl) {
            this.enableFlag(flag);
        } else {
            this.disableFlag(flag);
        }
    }

    private void lambda$tick$2(Goal.Flag flag, PrioritizedGoal prioritizedGoal) {
        if (!prioritizedGoal.isRunning()) {
            this.flagGoals.remove((Object)flag);
        }
    }

    private static boolean lambda$removeGoal$1(Goal goal, PrioritizedGoal prioritizedGoal) {
        return prioritizedGoal.getGoal() == goal;
    }

    private static boolean lambda$removeGoal$0(Goal goal, PrioritizedGoal prioritizedGoal) {
        return prioritizedGoal.getGoal() == goal;
    }
}

