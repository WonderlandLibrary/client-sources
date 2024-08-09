/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.schedules.rw.impl;

import mpp.venusfr.ui.schedules.rw.Schedule;
import mpp.venusfr.ui.schedules.rw.TimeType;

public class CompetitionSchedule
extends Schedule {
    @Override
    public String getName() {
        return "\u0421\u043e\u0441\u0442\u044f\u0437\u0430\u043d\u0438\u0435";
    }

    @Override
    public TimeType[] getTimes() {
        return new TimeType[]{TimeType.SEVEN_THIRTY_FIVE, TimeType.FIVE, TimeType.TEN_THIRTY_FIVE, TimeType.THIRTEEN_THIRTY_FIVE, TimeType.SIXTEEN_THIRTY_FIVE, TimeType.NINETEEN_THIRTY_FIVE, TimeType.TWENTY_TWO_THIRTY_FIVE, TimeType.ONE_FORTY_FIVE};
    }
}

