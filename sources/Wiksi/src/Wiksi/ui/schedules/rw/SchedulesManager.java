package src.Wiksi.ui.schedules.rw;

import src.Wiksi.ui.schedules.rw.impl.*;
import src.Wiksi.utils.client.IMinecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchedulesManager
        implements IMinecraft {
    private final List<Schedule> schedules = new ArrayList<>();

    public SchedulesManager() {
        this.schedules.addAll(Arrays.asList(new AirDropSchedule(), new ScroogeSchedule(), new SecretMerchantSchedule(), new MascotSchedule(), new CompetitionSchedule()));
    }

    public List<Schedule> getSchedules() {
        return this.schedules;
    }
}