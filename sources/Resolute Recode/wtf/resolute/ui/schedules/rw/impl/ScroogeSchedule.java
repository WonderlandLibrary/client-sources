package wtf.resolute.ui.schedules.rw.impl;

import wtf.resolute.ui.schedules.rw.Schedule;
import wtf.resolute.ui.schedules.rw.TimeType;

public class ScroogeSchedule
        extends Schedule {
    @Override
    public String getName() {
        return "Скрудж";
    }

    @Override
    public TimeType[] getTimes() {
        return new TimeType[]{TimeType.FIFTEEN_HALF};
    }
}
