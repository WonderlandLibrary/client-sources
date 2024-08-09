/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntitySummonArgument;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SummonCommand {
    private static final SimpleCommandExceptionType SUMMON_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.failed"));
    private static final SimpleCommandExceptionType field_244378_b = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType field_241075_b_ = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.invalidPosition"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("summon").requires(SummonCommand::lambda$register$0)).then(((RequiredArgumentBuilder)Commands.argument("entity", EntitySummonArgument.entitySummon()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes(SummonCommand::lambda$register$1)).then(((RequiredArgumentBuilder)Commands.argument("pos", Vec3Argument.vec3()).executes(SummonCommand::lambda$register$2)).then(Commands.argument("nbt", NBTCompoundTagArgument.nbt()).executes(SummonCommand::lambda$register$3)))));
    }

    private static int summonEntity(CommandSource commandSource, ResourceLocation resourceLocation, Vector3d vector3d, CompoundNBT compoundNBT, boolean bl) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(vector3d);
        if (!World.isInvalidPosition(blockPos)) {
            throw field_241075_b_.create();
        }
        CompoundNBT compoundNBT2 = compoundNBT.copy();
        compoundNBT2.putString("id", resourceLocation.toString());
        ServerWorld serverWorld = commandSource.getWorld();
        Entity entity2 = EntityType.loadEntityAndExecute(compoundNBT2, serverWorld, arg_0 -> SummonCommand.lambda$summonEntity$4(vector3d, arg_0));
        if (entity2 == null) {
            throw SUMMON_FAILED.create();
        }
        if (bl && entity2 instanceof MobEntity) {
            ((MobEntity)entity2).onInitialSpawn(commandSource.getWorld(), commandSource.getWorld().getDifficultyForLocation(entity2.getPosition()), SpawnReason.COMMAND, null, null);
        }
        if (!serverWorld.func_242106_g(entity2)) {
            throw field_244378_b.create();
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.summon.success", entity2.getDisplayName()), false);
        return 0;
    }

    private static Entity lambda$summonEntity$4(Vector3d vector3d, Entity entity2) {
        entity2.setLocationAndAngles(vector3d.x, vector3d.y, vector3d.z, entity2.rotationYaw, entity2.rotationPitch);
        return entity2;
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return SummonCommand.summonEntity((CommandSource)commandContext.getSource(), EntitySummonArgument.getEntityId(commandContext, "entity"), Vec3Argument.getVec3(commandContext, "pos"), NBTCompoundTagArgument.getNbt(commandContext, "nbt"), false);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return SummonCommand.summonEntity((CommandSource)commandContext.getSource(), EntitySummonArgument.getEntityId(commandContext, "entity"), Vec3Argument.getVec3(commandContext, "pos"), new CompoundNBT(), true);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return SummonCommand.summonEntity((CommandSource)commandContext.getSource(), EntitySummonArgument.getEntityId(commandContext, "entity"), ((CommandSource)commandContext.getSource()).getPos(), new CompoundNBT(), true);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

