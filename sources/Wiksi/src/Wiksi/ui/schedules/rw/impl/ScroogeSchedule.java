package src.Wiksi.ui.schedules.rw.impl;

import src.Wiksi.ui.schedules.rw.Schedule;
import src.Wiksi.ui.schedules.rw.TimeType;

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
