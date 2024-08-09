package dev.luvbeeq.baritone.api.cache;

import java.util.Set;

/**
 * @author Brady
 * @since 9/24/2018
 */
public interface IWaypointCollection {

    /**
     * Adds a waypoint to this collection
     *
     * @param waypoint The waypoint
     */
    void addWaypoint(IWaypoint waypoint);

    /**
     * Removes a waypoint from this collection
     *
     * @param waypoint The waypoint
     */
    void removeWaypoint(IWaypoint waypoint);

    /**
     * Gets the most recently created waypoint by the specified {@link IWaypoint.Tag}
     *
     * @param tag The tag
     * @return The most recently created waypoint with the specified tag
     */
    IWaypoint getMostRecentByTag(IWaypoint.Tag tag);

    /**
     * Gets all of the waypoints that have the specified tag
     *
     * @param tag The tag
     * @return All of the waypoints with the specified tag
     * @see IWaypointCollection#getAllWaypoints()
     */
    Set<IWaypoint> getByTag(IWaypoint.Tag tag);

    /**
     * Gets all of the waypoints in this collection, regardless of the tag.
     *
     * @return All of the waypoints in this collection
     * @see IWaypointCollection#getByTag(IWaypoint.Tag)
     */
    Set<IWaypoint> getAllWaypoints();
}
