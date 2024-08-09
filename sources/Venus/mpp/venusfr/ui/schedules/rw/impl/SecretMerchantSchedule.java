/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.schedules.rw.impl;

import mpp.venusfr.ui.schedules.rw.Schedule;
import mpp.venusfr.ui.schedules.rw.TimeType;

public class SecretMerchantSchedule
extends Schedule {
    @Override
    public String getName() {
        return "\u0422\u0430\u0439\u043d\u044b\u0439 \u0442\u043e\u0440\u0433\u043e\u0432\u0435\u0446";
    }

    @Override
    public TimeType[] getTimes() {
        return new TimeType[]{TimeType.FOUR, TimeType.FIVE, TimeType.EIGHT, TimeType.ELEVEN, TimeType.FOURTEEN, TimeType.SEVENTEEN, TimeType.TWENTY, TimeType.TWENTY_THREE};
    }
}

