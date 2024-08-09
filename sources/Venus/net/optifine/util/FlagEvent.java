/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.HashSet;
import java.util.Set;

public class FlagEvent {
    private static Set<String> setEvents = new HashSet<String>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void set(String string) {
        Set<String> set = setEvents;
        synchronized (set) {
            setEvents.add(string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean clear(String string) {
        Set<String> set = setEvents;
        synchronized (set) {
            return setEvents.remove(string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isActive(String string) {
        Set<String> set = setEvents;
        synchronized (set) {
            return setEvents.contains(string);
        }
    }

    public static boolean isActiveClear(String string) {
        return FlagEvent.clear(string);
    }
}

