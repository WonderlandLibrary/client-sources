/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.schedule;

import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.ScheduleBuilder;
import net.minecraft.entity.ai.brain.schedule.ScheduleDuties;
import net.minecraft.util.registry.Registry;

public class Schedule {
    public static final Schedule EMPTY = Schedule.register("empty").add(0, Activity.IDLE).build();
    public static final Schedule SIMPLE = Schedule.register("simple").add(5000, Activity.WORK).add(11000, Activity.REST).build();
    public static final Schedule VILLAGER_BABY = Schedule.register("villager_baby").add(10, Activity.IDLE).add(3000, Activity.PLAY).add(6000, Activity.IDLE).add(10000, Activity.PLAY).add(12000, Activity.REST).build();
    public static final Schedule VILLAGER_DEFAULT = Schedule.register("villager_default").add(10, Activity.IDLE).add(2000, Activity.WORK).add(9000, Activity.MEET).add(11000, Activity.IDLE).add(12000, Activity.REST).build();
    private final Map<Activity, ScheduleDuties> activityToDutiesMap = Maps.newHashMap();

    protected static ScheduleBuilder register(String string) {
        Schedule schedule = Registry.register(Registry.SCHEDULE, string, new Schedule());
        return new ScheduleBuilder(schedule);
    }

    protected void createDutiesFor(Activity activity) {
        if (!this.activityToDutiesMap.containsKey(activity)) {
            this.activityToDutiesMap.put(activity, new ScheduleDuties());
        }
    }

    protected ScheduleDuties getDutiesFor(Activity activity) {
        return this.activityToDutiesMap.get(activity);
    }

    protected List<ScheduleDuties> getAllDutiesExcept(Activity activity) {
        return this.activityToDutiesMap.entrySet().stream().filter(arg_0 -> Schedule.lambda$getAllDutiesExcept$0(activity, arg_0)).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public Activity getScheduledActivity(int n) {
        return this.activityToDutiesMap.entrySet().stream().max(Comparator.comparingDouble(arg_0 -> Schedule.lambda$getScheduledActivity$1(n, arg_0))).map(Map.Entry::getKey).orElse(Activity.IDLE);
    }

    private static double lambda$getScheduledActivity$1(int n, Map.Entry entry) {
        return ((ScheduleDuties)entry.getValue()).updateActiveDutyTime(n);
    }

    private static boolean lambda$getAllDutiesExcept$0(Activity activity, Map.Entry entry) {
        return entry.getKey() != activity;
    }
}

