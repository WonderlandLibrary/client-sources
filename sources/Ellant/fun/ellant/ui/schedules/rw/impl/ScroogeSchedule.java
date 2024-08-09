package fun.ellant.ui.schedules.rw.impl;

import fun.ellant.ui.schedules.rw.Schedule;
import fun.ellant.ui.schedules.rw.TimeType;

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
