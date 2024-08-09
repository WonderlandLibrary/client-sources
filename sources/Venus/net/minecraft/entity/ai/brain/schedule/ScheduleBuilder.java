/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.schedule;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.schedule.ScheduleDuties;

public class ScheduleBuilder {
    private final Schedule schedule;
    private final List<ActivityEntry> entries = Lists.newArrayList();

    public ScheduleBuilder(Schedule schedule) {
        this.schedule = schedule;
    }

    public ScheduleBuilder add(int n, Activity activity) {
        this.entries.add(new ActivityEntry(n, activity));
        return this;
    }

    public Schedule build() {
        this.entries.stream().map(ActivityEntry::getActivity).collect(Collectors.toSet()).forEach(this.schedule::createDutiesFor);
        this.entries.forEach(this::lambda$build$1);
        return this.schedule;
    }

    private void lambda$build$1(ActivityEntry activityEntry) {
        Activity activity = activityEntry.getActivity();
        this.schedule.getAllDutiesExcept(activity).forEach(arg_0 -> ScheduleBuilder.lambda$build$0(activityEntry, arg_0));
        this.schedule.getDutiesFor(activity).addDutyTime(activityEntry.getDuration(), 1.0f);
    }

    private static void lambda$build$0(ActivityEntry activityEntry, ScheduleDuties scheduleDuties) {
        scheduleDuties.addDutyTime(activityEntry.getDuration(), 0.0f);
    }

    static class ActivityEntry {
        private final int duration;
        private final Activity activity;

        public ActivityEntry(int n, Activity activity) {
            this.duration = n;
            this.activity = activity;
        }

        public int getDuration() {
            return this.duration;
        }

        public Activity getActivity() {
            return this.activity;
        }
    }
}

