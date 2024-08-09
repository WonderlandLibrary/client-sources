/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlaySoundPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class PlaySoundCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.playsound.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        RequiredArgumentBuilder<CommandSource, ResourceLocation> requiredArgumentBuilder = Commands.argument("sound", ResourceLocationArgument.resourceLocation()).suggests(SuggestionProviders.AVAILABLE_SOUNDS);
        for (SoundCategory soundCategory : SoundCategory.values()) {
            requiredArgumentBuilder.then(PlaySoundCommand.buildCategorySubcommand(soundCategory));
        }
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("playsound").requires(PlaySoundCommand::lambda$register$0)).then(requiredArgumentBuilder));
    }

    private static LiteralArgumentBuilder<CommandSource> buildCategorySubcommand(SoundCategory soundCategory) {
        return (LiteralArgumentBuilder)Commands.literal(soundCategory.getName()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).executes(arg_0 -> PlaySoundCommand.lambda$buildCategorySubcommand$1(soundCategory, arg_0))).then(((RequiredArgumentBuilder)Commands.argument("pos", Vec3Argument.vec3()).executes(arg_0 -> PlaySoundCommand.lambda$buildCategorySubcommand$2(soundCategory, arg_0))).then(((RequiredArgumentBuilder)Commands.argument("volume", FloatArgumentType.floatArg(0.0f)).executes(arg_0 -> PlaySoundCommand.lambda$buildCategorySubcommand$3(soundCategory, arg_0))).then(((RequiredArgumentBuilder)Commands.argument("pitch", FloatArgumentType.floatArg(0.0f, 2.0f)).executes(arg_0 -> PlaySoundCommand.lambda$buildCategorySubcommand$4(soundCategory, arg_0))).then(Commands.argument("minVolume", FloatArgumentType.floatArg(0.0f, 1.0f)).executes(arg_0 -> PlaySoundCommand.lambda$buildCategorySubcommand$5(soundCategory, arg_0)))))));
    }

    private static int playSound(CommandSource commandSource, Collection<ServerPlayerEntity> collection, ResourceLocation resourceLocation, SoundCategory soundCategory, Vector3d vector3d, float f, float f2, float f3) throws CommandSyntaxException {
        double d = Math.pow(f > 1.0f ? (double)(f * 16.0f) : 16.0, 2.0);
        int n = 0;
        Iterator<ServerPlayerEntity> iterator2 = collection.iterator();
        while (true) {
            if (!iterator2.hasNext()) {
                if (n == 0) {
                    throw FAILED_EXCEPTION.create();
                }
                if (collection.size() == 1) {
                    commandSource.sendFeedback(new TranslationTextComponent("commands.playsound.success.single", resourceLocation, collection.iterator().next().getDisplayName()), false);
                } else {
                    commandSource.sendFeedback(new TranslationTextComponent("commands.playsound.success.multiple", resourceLocation, collection.size()), false);
                }
                return n;
            }
            ServerPlayerEntity serverPlayerEntity = iterator2.next();
            double d2 = vector3d.x - serverPlayerEntity.getPosX();
            double d3 = vector3d.y - serverPlayerEntity.getPosY();
            double d4 = vector3d.z - serverPlayerEntity.getPosZ();
            double d5 = d2 * d2 + d3 * d3 + d4 * d4;
            Vector3d vector3d2 = vector3d;
            float f4 = f;
            if (d5 > d) {
                if (f3 <= 0.0f) continue;
                double d6 = MathHelper.sqrt(d5);
                vector3d2 = new Vector3d(serverPlayerEntity.getPosX() + d2 / d6 * 2.0, serverPlayerEntity.getPosY() + d3 / d6 * 2.0, serverPlayerEntity.getPosZ() + d4 / d6 * 2.0);
                f4 = f3;
            }
            serverPlayerEntity.connection.sendPacket(new SPlaySoundPacket(resourceLocation, soundCategory, vector3d2, f4, f2));
            ++n;
        }
    }

    private static int lambda$buildCategorySubcommand$5(SoundCategory soundCategory, CommandContext commandContext) throws CommandSyntaxException {
        return PlaySoundCommand.playSound((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ResourceLocationArgument.getResourceLocation(commandContext, "sound"), soundCategory, Vec3Argument.getVec3(commandContext, "pos"), commandContext.getArgument("volume", Float.class).floatValue(), commandContext.getArgument("pitch", Float.class).floatValue(), commandContext.getArgument("minVolume", Float.class).floatValue());
    }

    private static int lambda$buildCategorySubcommand$4(SoundCategory soundCategory, CommandContext commandContext) throws CommandSyntaxException {
        return PlaySoundCommand.playSound((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ResourceLocationArgument.getResourceLocation(commandContext, "sound"), soundCategory, Vec3Argument.getVec3(commandContext, "pos"), commandContext.getArgument("volume", Float.class).floatValue(), commandContext.getArgument("pitch", Float.class).floatValue(), 0.0f);
    }

    private static int lambda$buildCategorySubcommand$3(SoundCategory soundCategory, CommandContext commandContext) throws CommandSyntaxException {
        return PlaySoundCommand.playSound((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ResourceLocationArgument.getResourceLocation(commandContext, "sound"), soundCategory, Vec3Argument.getVec3(commandContext, "pos"), commandContext.getArgument("volume", Float.class).floatValue(), 1.0f, 0.0f);
    }

    private static int lambda$buildCategorySubcommand$2(SoundCategory soundCategory, CommandContext commandContext) throws CommandSyntaxException {
        return PlaySoundCommand.playSound((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ResourceLocationArgument.getResourceLocation(commandContext, "sound"), soundCategory, Vec3Argument.getVec3(commandContext, "pos"), 1.0f, 1.0f, 0.0f);
    }

    private static int lambda$buildCategorySubcommand$1(SoundCategory soundCategory, CommandContext commandContext) throws CommandSyntaxException {
        return PlaySoundCommand.playSound((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ResourceLocationArgument.getResourceLocation(commandContext, "sound"), soundCategory, ((CommandSource)commandContext.getSource()).getPos(), 1.0f, 1.0f, 0.0f);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

