/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.schedule;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import java.util.List;
import net.minecraft.entity.ai.brain.schedule.DutyTime;

public class ScheduleDuties {
    private final List<DutyTime> dutyTimes = Lists.newArrayList();
    private int index;

    public ScheduleDuties addDutyTime(int n, float f) {
        this.dutyTimes.add(new DutyTime(n, f));
        this.sortDutyTimes();
        return this;
    }

    private void sortDutyTimes() {
        Int2ObjectAVLTreeMap int2ObjectAVLTreeMap = new Int2ObjectAVLTreeMap();
        this.dutyTimes.forEach(arg_0 -> ScheduleDuties.lambda$sortDutyTimes$0(int2ObjectAVLTreeMap, arg_0));
        this.dutyTimes.clear();
        this.dutyTimes.addAll(int2ObjectAVLTreeMap.values());
        this.index = 0;
    }

    public float updateActiveDutyTime(int n) {
        DutyTime dutyTime;
        if (this.dutyTimes.size() <= 0) {
            return 0.0f;
        }
        DutyTime dutyTime2 = this.dutyTimes.get(this.index);
        DutyTime dutyTime3 = this.dutyTimes.get(this.dutyTimes.size() - 1);
        boolean bl = n < dutyTime2.getDuration();
        int n2 = bl ? 0 : this.index;
        float f = bl ? dutyTime3.getActive() : dutyTime2.getActive();
        int n3 = n2;
        while (n3 < this.dutyTimes.size() && (dutyTime = this.dutyTimes.get(n3)).getDuration() <= n) {
            this.index = n3++;
            f = dutyTime.getActive();
        }
        return f;
    }

    private static void lambda$sortDutyTimes$0(Int2ObjectSortedMap int2ObjectSortedMap, DutyTime dutyTime) {
        DutyTime dutyTime2 = int2ObjectSortedMap.put(dutyTime.getDuration(), dutyTime);
    }
}

