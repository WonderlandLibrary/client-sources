package fun.ellant.ui.schedules.rw.impl;

import fun.ellant.ui.schedules.rw.Schedule;
import fun.ellant.ui.schedules.rw.TimeType;

public class MascotSchedule
        extends Schedule {
    @Override
    public String getName() {
        return "Талисман";
    }

    @Override
    public TimeType[] getTimes() {
        return new TimeType[]{TimeType.NINETEEN_HALF};
    }
}
