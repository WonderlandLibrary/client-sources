package wtf.resolute.ui.schedules.rw.impl;

import wtf.resolute.ui.schedules.rw.Schedule;
import wtf.resolute.ui.schedules.rw.TimeType;

public class AirDropSchedule
        extends Schedule {
    @Override
    public String getName() {
        return "јир дроп";
    }

    @Override
    public TimeType[] getTimes() {
        return new TimeType[]{TimeType.NINE, TimeType.ELEVEN, TimeType.THIRTEEN, TimeType.FIFTEEN, TimeType.SEVENTEEN, TimeType.NINETEEN, TimeType.TWENTY_ONE, TimeType.TWENTY_THREE, TimeType.ONE};
    }
}