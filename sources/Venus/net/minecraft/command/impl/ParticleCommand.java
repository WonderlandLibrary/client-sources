/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ParticleArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class ParticleCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.particle.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("particle").requires(ParticleCommand::lambda$register$0)).then(((RequiredArgumentBuilder)Commands.argument("name", ParticleArgument.particle()).executes(ParticleCommand::lambda$register$1)).then(((RequiredArgumentBuilder)Commands.argument("pos", Vec3Argument.vec3()).executes(ParticleCommand::lambda$register$2)).then(Commands.argument("delta", Vec3Argument.vec3(false)).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("speed", FloatArgumentType.floatArg(0.0f)).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("count", IntegerArgumentType.integer(0)).executes(ParticleCommand::lambda$register$3)).then(((LiteralArgumentBuilder)Commands.literal("force").executes(ParticleCommand::lambda$register$4)).then(Commands.argument("viewers", EntityArgument.players()).executes(ParticleCommand::lambda$register$5)))).then(((LiteralArgumentBuilder)Commands.literal("normal").executes(ParticleCommand::lambda$register$6)).then(Commands.argument("viewers", EntityArgument.players()).executes(ParticleCommand::lambda$register$7)))))))));
    }

    private static int spawnParticle(CommandSource commandSource, IParticleData iParticleData, Vector3d vector3d, Vector3d vector3d2, float f, int n, boolean bl, Collection<ServerPlayerEntity> collection) throws CommandSyntaxException {
        int n2 = 0;
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            if (!commandSource.getWorld().spawnParticle(serverPlayerEntity, iParticleData, bl, vector3d.x, vector3d.y, vector3d.z, n, vector3d2.x, vector3d2.y, vector3d2.z, f)) continue;
            ++n2;
        }
        if (n2 == 0) {
            throw FAILED_EXCEPTION.create();
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.particle.success", Registry.PARTICLE_TYPE.getKey(iParticleData.getType()).toString()), false);
        return n2;
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return ParticleCommand.spawnParticle((CommandSource)commandContext.getSource(), ParticleArgument.getParticle(commandContext, "name"), Vec3Argument.getVec3(commandContext, "pos"), Vec3Argument.getVec3(commandContext, "delta"), FloatArgumentType.getFloat(commandContext, "speed"), IntegerArgumentType.getInteger(commandContext, "count"), false, EntityArgument.getPlayers(commandContext, "viewers"));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return ParticleCommand.spawnParticle((CommandSource)commandContext.getSource(), ParticleArgument.getParticle(commandContext, "name"), Vec3Argument.getVec3(commandContext, "pos"), Vec3Argument.getVec3(commandContext, "delta"), FloatArgumentType.getFloat(commandContext, "speed"), IntegerArgumentType.getInteger(commandContext, "count"), false, ((CommandSource)commandContext.getSource()).getServer().getPlayerList().getPlayers());
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return ParticleCommand.spawnParticle((CommandSource)commandContext.getSource(), ParticleArgument.getParticle(commandContext, "name"), Vec3Argument.getVec3(commandContext, "pos"), Vec3Argument.getVec3(commandContext, "delta"), FloatArgumentType.getFloat(commandContext, "speed"), IntegerArgumentType.getInteger(commandContext, "count"), true, EntityArgument.getPlayers(commandContext, "viewers"));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return ParticleCommand.spawnParticle((CommandSource)commandContext.getSource(), ParticleArgument.getParticle(commandContext, "name"), Vec3Argument.getVec3(commandContext, "pos"), Vec3Argument.getVec3(commandContext, "delta"), FloatArgumentType.getFloat(commandContext, "speed"), IntegerArgumentType.getInteger(commandContext, "count"), true, ((CommandSource)commandContext.getSource()).getServer().getPlayerList().getPlayers());
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return ParticleCommand.spawnParticle((CommandSource)commandContext.getSource(), ParticleArgument.getParticle(commandContext, "name"), Vec3Argument.getVec3(commandContext, "pos"), Vec3Argument.getVec3(commandContext, "delta"), FloatArgumentType.getFloat(commandContext, "speed"), IntegerArgumentType.getInteger(commandContext, "count"), false, ((CommandSource)commandContext.getSource()).getServer().getPlayerList().getPlayers());
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return ParticleCommand.spawnParticle((CommandSource)commandContext.getSource(), ParticleArgument.getParticle(commandContext, "name"), Vec3Argument.getVec3(commandContext, "pos"), Vector3d.ZERO, 0.0f, 0, false, ((CommandSource)commandContext.getSource()).getServer().getPlayerList().getPlayers());
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return ParticleCommand.spawnParticle((CommandSource)commandContext.getSource(), ParticleArgument.getParticle(commandContext, "name"), ((CommandSource)commandContext.getSource()).getPos(), Vector3d.ZERO, 0.0f, 0, false, ((CommandSource)commandContext.getSource()).getServer().getPlayerList().getPlayers());
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

