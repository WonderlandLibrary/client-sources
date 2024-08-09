package wtf.resolute.ui.schedules.rw.impl;

import wtf.resolute.ui.schedules.rw.Schedule;
import wtf.resolute.ui.schedules.rw.TimeType;

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
