/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import mpp.venusfr.events.CancelEvent;

public class EventCancelOverlay
extends CancelEvent {
    public final Overlays overlayType;

    public EventCancelOverlay(Overlays overlays) {
        this.overlayType = overlays;
    }

    public static enum Overlays {
        FIRE_OVERLAY,
        BOSS_LINE,
        SCOREBOARD,
        TITLES,
        TOTEM,
        FOG,
        HURT;

    }
}

