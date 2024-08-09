/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.server;

import java.util.Objects;
import net.minecraft.world.server.TicketType;

public final class Ticket<T>
implements Comparable<Ticket<?>> {
    private final TicketType<T> type;
    private final int level;
    private final T value;
    private long timestamp;

    protected Ticket(TicketType<T> ticketType, int n, T t) {
        this.type = ticketType;
        this.level = n;
        this.value = t;
    }

    @Override
    public int compareTo(Ticket<?> ticket) {
        int n = Integer.compare(this.level, ticket.level);
        if (n != 0) {
            return n;
        }
        int n2 = Integer.compare(System.identityHashCode(this.type), System.identityHashCode(ticket.type));
        return n2 != 0 ? n2 : this.type.getComparator().compare(this.value, ticket.value);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Ticket)) {
            return true;
        }
        Ticket ticket = (Ticket)object;
        return this.level == ticket.level && Objects.equals(this.type, ticket.type) && Objects.equals(this.value, ticket.value);
    }

    public int hashCode() {
        return Objects.hash(this.type, this.level, this.value);
    }

    public String toString() {
        return "Ticket[" + this.type + " " + this.level + " (" + this.value + ")] at " + this.timestamp;
    }

    public TicketType<T> getType() {
        return this.type;
    }

    public int getLevel() {
        return this.level;
    }

    protected void setTimestamp(long l) {
        this.timestamp = l;
    }

    protected boolean isExpired(long l) {
        long l2 = this.type.getLifespan();
        return l2 != 0L && l - this.timestamp > l2;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Ticket)object);
    }
}

