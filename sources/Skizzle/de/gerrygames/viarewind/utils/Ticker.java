/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.utils;

import de.gerrygames.viarewind.utils.Tickable;
import us.myles.ViaVersion.api.Via;

public class Ticker {
    private static boolean init = false;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void init() {
        if (init) {
            return;
        }
        Class<Ticker> class_ = Ticker.class;
        synchronized (Ticker.class) {
            if (init) {
                // ** MonitorExit[var0] (shouldn't be in output)
                return;
            }
            init = true;
            // ** MonitorExit[var0] (shouldn't be in output)
            Via.getPlatform().runRepeatingSync(() -> Via.getManager().getPortedPlayers().values().forEach(user -> user.getStoredObjects().values().stream().filter(Tickable.class::isInstance).map(Tickable.class::cast).forEach(Tickable::tick)), 1L);
            return;
        }
    }
}

