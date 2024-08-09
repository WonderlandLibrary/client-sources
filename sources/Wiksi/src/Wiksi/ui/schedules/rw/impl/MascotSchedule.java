package src.Wiksi.ui.schedules.rw.impl;

import src.Wiksi.ui.schedules.rw.Schedule;
import src.Wiksi.ui.schedules.rw.TimeType;

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
