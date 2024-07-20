/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.net.Facility;
import org.apache.logging.log4j.core.net.Severity;

public class Priority {
    private final Facility facility;
    private final Severity severity;

    public Priority(Facility facility, Severity severity) {
        this.facility = facility;
        this.severity = severity;
    }

    public static int getPriority(Facility facility, Level level) {
        return Priority.toPriority(facility, Severity.getSeverity(level));
    }

    private static int toPriority(Facility aFacility, Severity aSeverity) {
        return (aFacility.getCode() << 3) + aSeverity.getCode();
    }

    public Facility getFacility() {
        return this.facility;
    }

    public Severity getSeverity() {
        return this.severity;
    }

    public int getValue() {
        return Priority.toPriority(this.facility, this.severity);
    }

    public String toString() {
        return Integer.toString(this.getValue());
    }
}

