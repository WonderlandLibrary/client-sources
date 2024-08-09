/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.schedules.rw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mpp.venusfr.ui.schedules.rw.Schedule;
import mpp.venusfr.ui.schedules.rw.impl.AirDropSchedule;
import mpp.venusfr.ui.schedules.rw.impl.CompetitionSchedule;
import mpp.venusfr.ui.schedules.rw.impl.MascotSchedule;
import mpp.venusfr.ui.schedules.rw.impl.ScroogeSchedule;
import mpp.venusfr.ui.schedules.rw.impl.SecretMerchantSchedule;
import mpp.venusfr.utils.client.IMinecraft;

public class SchedulesManager
implements IMinecraft {
    private final List<Schedule> schedules = new ArrayList<Schedule>();

    public SchedulesManager() {
        this.schedules.addAll(Arrays.asList(new AirDropSchedule(), new ScroogeSchedule(), new SecretMerchantSchedule(), new MascotSchedule(), new CompetitionSchedule()));
    }

    public List<Schedule> getSchedules() {
        return this.schedules;
    }
}

