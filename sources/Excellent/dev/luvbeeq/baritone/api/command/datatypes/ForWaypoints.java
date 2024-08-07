package dev.luvbeeq.baritone.api.command.datatypes;

import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.cache.IWaypoint;
import dev.luvbeeq.baritone.api.cache.IWaypointCollection;
import dev.luvbeeq.baritone.api.command.exception.CommandException;
import dev.luvbeeq.baritone.api.command.helpers.TabCompleteHelper;

import java.util.Comparator;
import java.util.stream.Stream;

public enum ForWaypoints implements IDatatypeFor<IWaypoint[]> {
    INSTANCE;

    @Override
    public IWaypoint[] get(IDatatypeContext ctx) throws CommandException {
        final String input = ctx.getConsumer().getString();
        final IWaypoint.Tag tag = IWaypoint.Tag.getByName(input);

        // If the input doesn't resolve to a valid tag, resolve by name
        return tag == null
                ? getWaypointsByName(ctx.getBaritone(), input)
                : getWaypointsByTag(ctx.getBaritone(), tag);
    }

    @Override
    public Stream<String> tabComplete(IDatatypeContext ctx) throws CommandException {
        return new TabCompleteHelper()
                .append(getWaypointNames(ctx.getBaritone()))
                .sortAlphabetically()
                .prepend(IWaypoint.Tag.getAllNames())
                .filterPrefix(ctx.getConsumer().getString())
                .stream();
    }

    public static IWaypointCollection waypoints(IBaritone baritone) {
        return baritone.getWorldProvider().getCurrentWorld().getWaypoints();
    }

    public static IWaypoint[] getWaypoints(IBaritone baritone) {
        return waypoints(baritone).getAllWaypoints().stream()
                .sorted(Comparator.comparingLong(IWaypoint::getCreationTimestamp).reversed())
                .toArray(IWaypoint[]::new);
    }

    public static String[] getWaypointNames(IBaritone baritone) {
        return Stream.of(getWaypoints(baritone))
                .map(IWaypoint::getName)
                .filter(name -> !name.isEmpty())
                .toArray(String[]::new);
    }

    public static IWaypoint[] getWaypointsByTag(IBaritone baritone, IWaypoint.Tag tag) {
        return waypoints(baritone).getByTag(tag).stream()
                .sorted(Comparator.comparingLong(IWaypoint::getCreationTimestamp).reversed())
                .toArray(IWaypoint[]::new);
    }

    public static IWaypoint[] getWaypointsByName(IBaritone baritone, String name) {
        return Stream.of(getWaypoints(baritone))
                .filter(waypoint -> waypoint.getName().equalsIgnoreCase(name))
                .toArray(IWaypoint[]::new);
    }
}
