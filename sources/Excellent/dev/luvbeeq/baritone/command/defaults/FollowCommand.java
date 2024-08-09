package dev.luvbeeq.baritone.command.defaults;

import dev.luvbeeq.baritone.KeepName;
import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.command.Command;
import dev.luvbeeq.baritone.api.command.argument.IArgConsumer;
import dev.luvbeeq.baritone.api.command.datatypes.EntityClassById;
import dev.luvbeeq.baritone.api.command.datatypes.IDatatypeFor;
import dev.luvbeeq.baritone.api.command.datatypes.NearbyPlayer;
import dev.luvbeeq.baritone.api.command.exception.CommandErrorMessageException;
import dev.luvbeeq.baritone.api.command.exception.CommandException;
import dev.luvbeeq.baritone.api.command.helpers.TabCompleteHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FollowCommand extends Command {

    public FollowCommand(IBaritone baritone) {
        super(baritone, "follow");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        args.requireMin(1);
        FollowGroup group;
        FollowList list;
        List<Entity> entities = new ArrayList<>();
        List<EntityType> classes = new ArrayList<>();
        if (args.hasExactlyOne()) {
            baritone.getFollowProcess().follow((group = args.getEnum(FollowGroup.class)).filter);
        } else {
            args.requireMin(2);
            group = null;
            list = args.getEnum(FollowList.class);
            while (args.hasAny()) {
                Object gotten = args.getDatatypeFor(list.datatype);
                if (gotten instanceof EntityType) {
                    //noinspection unchecked
                    classes.add((EntityType) gotten);
                } else if (gotten != null) {
                    entities.add((Entity) gotten);
                }
            }

            baritone.getFollowProcess().follow(
                    classes.isEmpty()
                            ? entities::contains
                            : e -> classes.stream().anyMatch(c -> e.getType().equals(c))
            );
        }
        if (group != null) {
            logDirect(String.format("Following all %s", group.name().toLowerCase(Locale.US)));
        } else {
            if (classes.isEmpty()) {
                if (entities.isEmpty()) throw new NoEntitiesException();
                logDirect("Following these entities:");
                entities.stream()
                        .map(Entity::toString)
                        .forEach(this::logDirect);
            } else {
                logDirect("Following these types of entities:");
                classes.stream()
                        .map(Registry.ENTITY_TYPE::getKey)
                        .map(Objects::requireNonNull)
                        .map(ResourceLocation::toString)
                        .forEach(this::logDirect);
            }
        }
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) throws CommandException {
        if (args.hasExactlyOne()) {
            return new TabCompleteHelper()
                    .append(FollowGroup.class)
                    .append(FollowList.class)
                    .filterPrefix(args.getString())
                    .stream();
        } else {
            IDatatypeFor followType;
            try {
                followType = args.getEnum(FollowList.class).datatype;
            } catch(NullPointerException e) {
                return Stream.empty();
            }
            while (args.has(2)) {
                if (args.peekDatatypeOrNull(followType) == null) {
                    return Stream.empty();
                }
                args.get();
            }
            return args.tabCompleteDatatype(followType);
        }
    }

    @Override
    public String getShortDesc() {
        return "Follow entity things";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "The follow command tells Baritone to follow certain kinds of entities.",
                "",
                "Usage:",
                "> follow entities - Follows all entities.",
                "> follow entity <entity1> <entity2> <...> - Follow certain entities (for example 'skeleton', 'horse' etc.)",
                "> follow players - Follow players",
                "> follow player <username1> <username2> <...> - Follow certain players"
        );
    }

    @KeepName
    private enum FollowGroup {
        ENTITIES(LivingEntity.class::isInstance),
        PLAYERS(PlayerEntity.class::isInstance); /* ,
        FRIENDLY(entity -> entity.getAttackTarget() != HELPER.mc.player),
        HOSTILE(FRIENDLY.filter.negate()); */
        final Predicate<Entity> filter;

        FollowGroup(Predicate<Entity> filter) {
            this.filter = filter;
        }
    }

    @KeepName
    private enum FollowList {
        ENTITY(EntityClassById.INSTANCE),
        PLAYER(NearbyPlayer.INSTANCE);

        final IDatatypeFor datatype;

        FollowList(IDatatypeFor datatype) {
            this.datatype = datatype;
        }
    }

    public static class NoEntitiesException extends CommandErrorMessageException {

        protected NoEntitiesException() {
            super("No valid entities in range!");
        }

    }
}
