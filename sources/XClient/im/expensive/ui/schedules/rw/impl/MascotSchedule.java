package im.expensive.ui.schedules.rw.impl;

import im.expensive.ui.schedules.rw.Schedule;
import im.expensive.ui.schedules.rw.TimeType;

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
