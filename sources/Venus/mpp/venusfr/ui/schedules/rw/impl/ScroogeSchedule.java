/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.schedules.rw.impl;

import mpp.venusfr.ui.schedules.rw.Schedule;
import mpp.venusfr.ui.schedules.rw.TimeType;

public class ScroogeSchedule
extends Schedule {
    @Override
    public String getName() {
        return "\u0421\u043a\u0440\u0443\u0434\u0436";
    }

    @Override
    public TimeType[] getTimes() {
        return new TimeType[]{TimeType.FIFTEEN_HALF};
    }
}

