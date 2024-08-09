/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ILocationArgument;
import net.minecraft.command.arguments.LocationInput;
import net.minecraft.command.arguments.RotationArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

public class TeleportCommand {
    private static final SimpleCommandExceptionType field_241077_a_ = new SimpleCommandExceptionType(new TranslationTextComponent("commands.teleport.invalidPosition"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralCommandNode<CommandSource> literalCommandNode = commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("teleport").requires(TeleportCommand::lambda$register$0)).then(((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.entities()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("location", Vec3Argument.vec3()).executes(TeleportCommand::lambda$register$1)).then(Commands.argument("rotation", RotationArgument.rotation()).executes(TeleportCommand::lambda$register$2))).then(((LiteralArgumentBuilder)Commands.literal("facing").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("entity").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("facingEntity", EntityArgument.entity()).executes(TeleportCommand::lambda$register$3)).then(Commands.argument("facingAnchor", EntityAnchorArgument.entityAnchor()).executes(TeleportCommand::lambda$register$4))))).then(Commands.argument("facingLocation", Vec3Argument.vec3()).executes(TeleportCommand::lambda$register$5))))).then(Commands.argument("destination", EntityArgument.entity()).executes(TeleportCommand::lambda$register$6)))).then(Commands.argument("location", Vec3Argument.vec3()).executes(TeleportCommand::lambda$register$7))).then(Commands.argument("destination", EntityArgument.entity()).executes(TeleportCommand::lambda$register$8)));
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tp").requires(TeleportCommand::lambda$register$9)).redirect(literalCommandNode));
    }

    private static int teleportToEntity(CommandSource commandSource, Collection<? extends Entity> collection, Entity entity2) throws CommandSyntaxException {
        for (Entity entity3 : collection) {
            TeleportCommand.teleport(commandSource, entity3, (ServerWorld)entity2.world, entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), EnumSet.noneOf(SPlayerPositionLookPacket.Flags.class), entity2.rotationYaw, entity2.rotationPitch, null);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.teleport.success.entity.single", collection.iterator().next().getDisplayName(), entity2.getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.teleport.success.entity.multiple", collection.size(), entity2.getDisplayName()), false);
        }
        return collection.size();
    }

    private static int teleportToPos(CommandSource commandSource, Collection<? extends Entity> collection, ServerWorld serverWorld, ILocationArgument iLocationArgument, @Nullable ILocationArgument iLocationArgument2, @Nullable Facing facing) throws CommandSyntaxException {
        Vector3d vector3d = iLocationArgument.getPosition(commandSource);
        Vector2f vector2f = iLocationArgument2 == null ? null : iLocationArgument2.getRotation(commandSource);
        EnumSet<SPlayerPositionLookPacket.Flags> enumSet = EnumSet.noneOf(SPlayerPositionLookPacket.Flags.class);
        if (iLocationArgument.isXRelative()) {
            enumSet.add(SPlayerPositionLookPacket.Flags.X);
        }
        if (iLocationArgument.isYRelative()) {
            enumSet.add(SPlayerPositionLookPacket.Flags.Y);
        }
        if (iLocationArgument.isZRelative()) {
            enumSet.add(SPlayerPositionLookPacket.Flags.Z);
        }
        if (iLocationArgument2 == null) {
            enumSet.add(SPlayerPositionLookPacket.Flags.X_ROT);
            enumSet.add(SPlayerPositionLookPacket.Flags.Y_ROT);
        } else {
            if (iLocationArgument2.isXRelative()) {
                enumSet.add(SPlayerPositionLookPacket.Flags.X_ROT);
            }
            if (iLocationArgument2.isYRelative()) {
                enumSet.add(SPlayerPositionLookPacket.Flags.Y_ROT);
            }
        }
        for (Entity entity2 : collection) {
            if (iLocationArgument2 == null) {
                TeleportCommand.teleport(commandSource, entity2, serverWorld, vector3d.x, vector3d.y, vector3d.z, enumSet, entity2.rotationYaw, entity2.rotationPitch, facing);
                continue;
            }
            TeleportCommand.teleport(commandSource, entity2, serverWorld, vector3d.x, vector3d.y, vector3d.z, enumSet, vector2f.y, vector2f.x, facing);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.teleport.success.location.single", collection.iterator().next().getDisplayName(), vector3d.x, vector3d.y, vector3d.z), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.teleport.success.location.multiple", collection.size(), vector3d.x, vector3d.y, vector3d.z), false);
        }
        return collection.size();
    }

    private static void teleport(CommandSource commandSource, Entity entity2, ServerWorld serverWorld, double d, double d2, double d3, Set<SPlayerPositionLookPacket.Flags> set, float f, float f2, @Nullable Facing facing) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(d, d2, d3);
        if (!World.isInvalidPosition(blockPos)) {
            throw field_241077_a_.create();
        }
        if (entity2 instanceof ServerPlayerEntity) {
            ChunkPos chunkPos = new ChunkPos(new BlockPos(d, d2, d3));
            serverWorld.getChunkProvider().registerTicket(TicketType.POST_TELEPORT, chunkPos, 1, entity2.getEntityId());
            entity2.stopRiding();
            if (((ServerPlayerEntity)entity2).isSleeping()) {
                ((ServerPlayerEntity)entity2).stopSleepInBed(true, false);
            }
            if (serverWorld == entity2.world) {
                ((ServerPlayerEntity)entity2).connection.setPlayerLocation(d, d2, d3, f, f2, set);
            } else {
                ((ServerPlayerEntity)entity2).teleport(serverWorld, d, d2, d3, f, f2);
            }
            entity2.setRotationYawHead(f);
        } else {
            float f3 = MathHelper.wrapDegrees(f);
            float f4 = MathHelper.wrapDegrees(f2);
            f4 = MathHelper.clamp(f4, -90.0f, 90.0f);
            if (serverWorld == entity2.world) {
                entity2.setLocationAndAngles(d, d2, d3, f3, f4);
                entity2.setRotationYawHead(f3);
            } else {
                entity2.detach();
                Entity entity3 = entity2;
                entity2 = entity2.getType().create(serverWorld);
                if (entity2 == null) {
                    return;
                }
                entity2.copyDataFromOld(entity3);
                entity2.setLocationAndAngles(d, d2, d3, f3, f4);
                entity2.setRotationYawHead(f3);
                serverWorld.addFromAnotherDimension(entity2);
                entity3.removed = true;
            }
        }
        if (facing != null) {
            facing.updateLook(commandSource, entity2);
        }
        if (!(entity2 instanceof LivingEntity) || !((LivingEntity)entity2).isElytraFlying()) {
            entity2.setMotion(entity2.getMotion().mul(1.0, 0.0, 1.0));
            entity2.setOnGround(false);
        }
        if (entity2 instanceof CreatureEntity) {
            ((CreatureEntity)entity2).getNavigator().clearPath();
        }
    }

    private static boolean lambda$register$9(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return TeleportCommand.teleportToEntity((CommandSource)commandContext.getSource(), Collections.singleton(((CommandSource)commandContext.getSource()).assertIsEntity()), EntityArgument.getEntity(commandContext, "destination"));
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return TeleportCommand.teleportToPos((CommandSource)commandContext.getSource(), Collections.singleton(((CommandSource)commandContext.getSource()).assertIsEntity()), ((CommandSource)commandContext.getSource()).getWorld(), Vec3Argument.getLocation(commandContext, "location"), LocationInput.current(), null);
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return TeleportCommand.teleportToEntity((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), EntityArgument.getEntity(commandContext, "destination"));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return TeleportCommand.teleportToPos((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), ((CommandSource)commandContext.getSource()).getWorld(), Vec3Argument.getLocation(commandContext, "location"), null, new Facing(Vec3Argument.getVec3(commandContext, "facingLocation")));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return TeleportCommand.teleportToPos((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), ((CommandSource)commandContext.getSource()).getWorld(), Vec3Argument.getLocation(commandContext, "location"), null, new Facing(EntityArgument.getEntity(commandContext, "facingEntity"), EntityAnchorArgument.getEntityAnchor(commandContext, "facingAnchor")));
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return TeleportCommand.teleportToPos((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), ((CommandSource)commandContext.getSource()).getWorld(), Vec3Argument.getLocation(commandContext, "location"), null, new Facing(EntityArgument.getEntity(commandContext, "facingEntity"), EntityAnchorArgument.Type.FEET));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return TeleportCommand.teleportToPos((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), ((CommandSource)commandContext.getSource()).getWorld(), Vec3Argument.getLocation(commandContext, "location"), RotationArgument.getRotation(commandContext, "rotation"), null);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return TeleportCommand.teleportToPos((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), ((CommandSource)commandContext.getSource()).getWorld(), Vec3Argument.getLocation(commandContext, "location"), null, null);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    static class Facing {
        private final Vector3d position;
        private final Entity entity;
        private final EntityAnchorArgument.Type anchor;

        public Facing(Entity entity2, EntityAnchorArgument.Type type) {
            this.entity = entity2;
            this.anchor = type;
            this.position = type.apply(entity2);
        }

        public Facing(Vector3d vector3d) {
            this.entity = null;
            this.position = vector3d;
            this.anchor = null;
        }

        public void updateLook(CommandSource commandSource, Entity entity2) {
            if (this.entity != null) {
                if (entity2 instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity)entity2).lookAt(commandSource.getEntityAnchorType(), this.entity, this.anchor);
                } else {
                    entity2.lookAt(commandSource.getEntityAnchorType(), this.position);
                }
            } else {
                entity2.lookAt(commandSource.getEntityAnchorType(), this.position);
            }
        }
    }
}

