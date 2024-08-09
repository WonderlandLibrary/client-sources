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
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SStopSoundPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TranslationTextComponent;

public class StopSoundCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        RequiredArgumentBuilder requiredArgumentBuilder = (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).executes(StopSoundCommand::lambda$register$0)).then(Commands.literal("*").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("sound", ResourceLocationArgument.resourceLocation()).suggests(SuggestionProviders.AVAILABLE_SOUNDS).executes(StopSoundCommand::lambda$register$1)));
        for (SoundCategory soundCategory : SoundCategory.values()) {
            requiredArgumentBuilder.then(((LiteralArgumentBuilder)Commands.literal(soundCategory.getName()).executes(arg_0 -> StopSoundCommand.lambda$register$2(soundCategory, arg_0))).then(Commands.argument("sound", ResourceLocationArgument.resourceLocation()).suggests(SuggestionProviders.AVAILABLE_SOUNDS).executes(arg_0 -> StopSoundCommand.lambda$register$3(soundCategory, arg_0))));
        }
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("stopsound").requires(StopSoundCommand::lambda$register$4)).then(requiredArgumentBuilder));
    }

    private static int stopSound(CommandSource commandSource, Collection<ServerPlayerEntity> collection, @Nullable SoundCategory soundCategory, @Nullable ResourceLocation resourceLocation) {
        SStopSoundPacket sStopSoundPacket = new SStopSoundPacket(resourceLocation, soundCategory);
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            serverPlayerEntity.connection.sendPacket(sStopSoundPacket);
        }
        if (soundCategory != null) {
            if (resourceLocation != null) {
                commandSource.sendFeedback(new TranslationTextComponent("commands.stopsound.success.source.sound", resourceLocation, soundCategory.getName()), false);
            } else {
                commandSource.sendFeedback(new TranslationTextComponent("commands.stopsound.success.source.any", soundCategory.getName()), false);
            }
        } else if (resourceLocation != null) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.stopsound.success.sourceless.sound", resourceLocation), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.stopsound.success.sourceless.any"), false);
        }
        return collection.size();
    }

    private static boolean lambda$register$4(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static int lambda$register$3(SoundCategory soundCategory, CommandContext commandContext) throws CommandSyntaxException {
        return StopSoundCommand.stopSound((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), soundCategory, ResourceLocationArgument.getResourceLocation(commandContext, "sound"));
    }

    private static int lambda$register$2(SoundCategory soundCategory, CommandContext commandContext) throws CommandSyntaxException {
        return StopSoundCommand.stopSound((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), soundCategory, null);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return StopSoundCommand.stopSound((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), null, ResourceLocationArgument.getResourceLocation(commandContext, "sound"));
    }

    private static int lambda$register$0(CommandContext commandContext) throws CommandSyntaxException {
        return StopSoundCommand.stopSound((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), null, null);
    }
}

