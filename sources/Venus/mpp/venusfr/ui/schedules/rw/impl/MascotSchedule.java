/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.schedules.rw.impl;

import mpp.venusfr.ui.schedules.rw.Schedule;
import mpp.venusfr.ui.schedules.rw.TimeType;

public class MascotSchedule
extends Schedule {
    @Override
    public String getName() {
        return "\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d";
    }

    @Override
    public TimeType[] getTimes() {
        return new TimeType[]{TimeType.NINETEEN_HALF};
    }
}

