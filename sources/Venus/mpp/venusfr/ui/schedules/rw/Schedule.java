/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.schedules.rw;

import mpp.venusfr.ui.schedules.rw.TimeType;

public abstract class Schedule {
    public abstract String getName();

    public abstract TimeType[] getTimes();
}

