/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.server.ServerWorld;

public class EntitySelector {
    private final int limit;
    private final boolean includeNonPlayers;
    private final boolean currentWorldOnly;
    private final Predicate<Entity> filter;
    private final MinMaxBounds.FloatBound distance;
    private final Function<Vector3d, Vector3d> positionGetter;
    @Nullable
    private final AxisAlignedBB aabb;
    private final BiConsumer<Vector3d, List<? extends Entity>> sorter;
    private final boolean self;
    @Nullable
    private final String username;
    @Nullable
    private final UUID uuid;
    @Nullable
    private final EntityType<?> type;
    private final boolean checkPermission;

    public EntitySelector(int n, boolean bl, boolean bl2, Predicate<Entity> predicate, MinMaxBounds.FloatBound floatBound, Function<Vector3d, Vector3d> function, @Nullable AxisAlignedBB axisAlignedBB, BiConsumer<Vector3d, List<? extends Entity>> biConsumer, boolean bl3, @Nullable String string, @Nullable UUID uUID, @Nullable EntityType<?> entityType, boolean bl4) {
        this.limit = n;
        this.includeNonPlayers = bl;
        this.currentWorldOnly = bl2;
        this.filter = predicate;
        this.distance = floatBound;
        this.positionGetter = function;
        this.aabb = axisAlignedBB;
        this.sorter = biConsumer;
        this.self = bl3;
        this.username = string;
        this.uuid = uUID;
        this.type = entityType;
        this.checkPermission = bl4;
    }

    public int getLimit() {
        return this.limit;
    }

    public boolean includesEntities() {
        return this.includeNonPlayers;
    }

    public boolean isSelfSelector() {
        return this.self;
    }

    public boolean isWorldLimited() {
        return this.currentWorldOnly;
    }

    private void checkPermission(CommandSource commandSource) throws CommandSyntaxException {
        if (this.checkPermission && !commandSource.hasPermissionLevel(1)) {
            throw EntityArgument.SELECTOR_NOT_ALLOWED.create();
        }
    }

    public Entity selectOne(CommandSource commandSource) throws CommandSyntaxException {
        this.checkPermission(commandSource);
        List<? extends Entity> list = this.select(commandSource);
        if (list.isEmpty()) {
            throw EntityArgument.ENTITY_NOT_FOUND.create();
        }
        if (list.size() > 1) {
            throw EntityArgument.TOO_MANY_ENTITIES.create();
        }
        return list.get(0);
    }

    public List<? extends Entity> select(CommandSource commandSource) throws CommandSyntaxException {
        this.checkPermission(commandSource);
        if (!this.includeNonPlayers) {
            return this.selectPlayers(commandSource);
        }
        if (this.username != null) {
            ServerPlayerEntity serverPlayerEntity = commandSource.getServer().getPlayerList().getPlayerByUsername(this.username);
            return serverPlayerEntity == null ? Collections.emptyList() : Lists.newArrayList(serverPlayerEntity);
        }
        if (this.uuid != null) {
            for (ServerWorld serverWorld : commandSource.getServer().getWorlds()) {
                Entity entity2 = serverWorld.getEntityByUuid(this.uuid);
                if (entity2 == null) continue;
                return Lists.newArrayList(entity2);
            }
            return Collections.emptyList();
        }
        Vector3d vector3d = this.positionGetter.apply(commandSource.getPos());
        Predicate<Entity> predicate = this.updateFilter(vector3d);
        if (this.self) {
            return commandSource.getEntity() != null && predicate.test(commandSource.getEntity()) ? Lists.newArrayList(commandSource.getEntity()) : Collections.emptyList();
        }
        ArrayList<Entity> arrayList = Lists.newArrayList();
        if (this.isWorldLimited()) {
            this.getEntities(arrayList, commandSource.getWorld(), vector3d, predicate);
        } else {
            for (ServerWorld serverWorld : commandSource.getServer().getWorlds()) {
                this.getEntities(arrayList, serverWorld, vector3d, predicate);
            }
        }
        return this.sortAndLimit(vector3d, arrayList);
    }

    private void getEntities(List<Entity> list, ServerWorld serverWorld, Vector3d vector3d, Predicate<Entity> predicate) {
        if (this.aabb != null) {
            list.addAll(serverWorld.getEntitiesWithinAABB(this.type, this.aabb.offset(vector3d), predicate));
        } else {
            list.addAll(serverWorld.getEntities(this.type, predicate));
        }
    }

    public ServerPlayerEntity selectOnePlayer(CommandSource commandSource) throws CommandSyntaxException {
        this.checkPermission(commandSource);
        List<ServerPlayerEntity> list = this.selectPlayers(commandSource);
        if (list.size() != 1) {
            throw EntityArgument.PLAYER_NOT_FOUND.create();
        }
        return list.get(0);
    }

    public List<ServerPlayerEntity> selectPlayers(CommandSource commandSource) throws CommandSyntaxException {
        List<Object> list;
        this.checkPermission(commandSource);
        if (this.username != null) {
            ServerPlayerEntity serverPlayerEntity = commandSource.getServer().getPlayerList().getPlayerByUsername(this.username);
            return serverPlayerEntity == null ? Collections.emptyList() : Lists.newArrayList(serverPlayerEntity);
        }
        if (this.uuid != null) {
            ServerPlayerEntity serverPlayerEntity = commandSource.getServer().getPlayerList().getPlayerByUUID(this.uuid);
            return serverPlayerEntity == null ? Collections.emptyList() : Lists.newArrayList(serverPlayerEntity);
        }
        Vector3d vector3d = this.positionGetter.apply(commandSource.getPos());
        Predicate<Entity> predicate = this.updateFilter(vector3d);
        if (this.self) {
            ServerPlayerEntity serverPlayerEntity;
            if (commandSource.getEntity() instanceof ServerPlayerEntity && predicate.test(serverPlayerEntity = (ServerPlayerEntity)commandSource.getEntity())) {
                return Lists.newArrayList(serverPlayerEntity);
            }
            return Collections.emptyList();
        }
        if (this.isWorldLimited()) {
            list = commandSource.getWorld().getPlayers(predicate::test);
        } else {
            list = Lists.newArrayList();
            for (ServerPlayerEntity serverPlayerEntity : commandSource.getServer().getPlayerList().getPlayers()) {
                if (!predicate.test(serverPlayerEntity)) continue;
                list.add(serverPlayerEntity);
            }
        }
        return this.sortAndLimit(vector3d, list);
    }

    private Predicate<Entity> updateFilter(Vector3d vector3d) {
        Predicate<Entity> predicate = this.filter;
        if (this.aabb != null) {
            AxisAlignedBB axisAlignedBB = this.aabb.offset(vector3d);
            predicate = predicate.and(arg_0 -> EntitySelector.lambda$updateFilter$0(axisAlignedBB, arg_0));
        }
        if (!this.distance.isUnbounded()) {
            predicate = predicate.and(arg_0 -> this.lambda$updateFilter$1(vector3d, arg_0));
        }
        return predicate;
    }

    private <T extends Entity> List<T> sortAndLimit(Vector3d vector3d, List<T> list) {
        if (list.size() > 1) {
            this.sorter.accept(vector3d, list);
        }
        return list.subList(0, Math.min(this.limit, list.size()));
    }

    public static IFormattableTextComponent joinNames(List<? extends Entity> list) {
        return TextComponentUtils.func_240649_b_(list, Entity::getDisplayName);
    }

    private boolean lambda$updateFilter$1(Vector3d vector3d, Entity entity2) {
        return this.distance.testSquared(entity2.getDistanceSq(vector3d));
    }

    private static boolean lambda$updateFilter$0(AxisAlignedBB axisAlignedBB, Entity entity2) {
        return axisAlignedBB.intersects(entity2.getBoundingBox());
    }
}

