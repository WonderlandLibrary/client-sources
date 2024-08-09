package dev.luvbeeq.baritone.api.cache;

import dev.luvbeeq.baritone.api.utils.BetterBlockPos;

import java.util.Date;

/**
 * Basic implementation of {@link IWaypoint}
 *
 * @author leijurv
 */
public class Waypoint implements IWaypoint {

    private final String name;
    private final Tag tag;
    private final long creationTimestamp;
    private final BetterBlockPos location;

    public Waypoint(String name, Tag tag, BetterBlockPos location) {
        this(name, tag, location, System.currentTimeMillis());
    }

    /**
     * Constructor called when a Waypoint is read from disk, adds the creationTimestamp
     * as a parameter so that it is reserved after a waypoint is wrote to the disk.
     *
     * @param name              The waypoint name
     * @param tag               The waypoint tag
     * @param location          The waypoint location
     * @param creationTimestamp When the waypoint was created
     */
    public Waypoint(String name, Tag tag, BetterBlockPos location, long creationTimestamp) {
        this.name = name;
        this.tag = tag;
        this.location = location;
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public int hashCode() {
        return name.hashCode() ^ tag.hashCode() ^ location.hashCode() ^ Long.hashCode(creationTimestamp);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Tag getTag() {
        return this.tag;
    }

    @Override
    public long getCreationTimestamp() {
        return this.creationTimestamp;
    }

    @Override
    public BetterBlockPos getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s %s",
                name,
                BetterBlockPos.from(location).toString(),
                new Date(creationTimestamp).toString()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof IWaypoint)) {
            return false;
        }
        IWaypoint w = (IWaypoint) o;
        return name.equals(w.getName()) && tag == w.getTag() && location.equals(w.getLocation());
    }
}
