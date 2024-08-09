package dev.luvbeeq.baritone.api.cache;

import dev.luvbeeq.baritone.api.utils.BetterBlockPos;

import java.util.*;

/**
 * A marker for a position in the world.
 *
 * @author Brady
 * @since 9/24/2018
 */
public interface IWaypoint {

    /**
     * @return The label for this waypoint
     */
    String getName();

    /**
     * Returns the tag for this waypoint. The tag is a category
     * for the waypoint in a sense, it describes the source of
     * the waypoint.
     *
     * @return The waypoint tag
     */
    Tag getTag();

    /**
     * Returns the unix epoch time in milliseconds that this waypoint
     * was created. This value should only be set once, when the waypoint
     * is initially created, and not when it is being loaded from file.
     *
     * @return The unix epoch milliseconds that this waypoint was created
     */
    long getCreationTimestamp();

    /**
     * Returns the actual block position of this waypoint.
     *
     * @return The block position of this waypoint
     */
    BetterBlockPos getLocation();

    enum Tag {

        /**
         * Tag indicating a position explictly marked as a home base
         */
        HOME("home", "base"),

        /**
         * Tag indicating a position that the local player has died at
         */
        DEATH("death"),

        /**
         * Tag indicating a bed position
         */
        BED("bed", "spawn"),

        /**
         * Tag indicating that the waypoint was user-created
         */
        USER("user");

        /**
         * A list of all of the
         */
        private static final List<Tag> TAG_LIST = Collections.unmodifiableList(Arrays.asList(Tag.values()));

        /**
         * The names for the tag, anything that the tag can be referred to as.
         */
        public final String[] names;

        Tag(String... names) {
            this.names = names;
        }

        /**
         * @return A name that can be passed to {@link #getByName(String)} to retrieve this tag
         */
        public String getName() {
            return names[0];
        }

        /**
         * Gets a tag by one of its names.
         *
         * @param name The name to search for.
         * @return The tag, if found, or null.
         */
        public static Tag getByName(String name) {
            for (Tag action : Tag.values()) {
                for (String alias : action.names) {
                    if (alias.equalsIgnoreCase(name)) {
                        return action;
                    }
                }
            }

            return null;
        }

        /**
         * @return All tag names.
         */
        public static String[] getAllNames() {
            Set<String> names = new HashSet<>();

            for (Tag tag : Tag.values()) {
                names.addAll(Arrays.asList(tag.names));
            }

            return names.toArray(new String[0]);
        }
    }
}
