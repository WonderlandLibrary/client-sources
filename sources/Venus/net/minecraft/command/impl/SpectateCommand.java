/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class SpectateCommand {
    private static final SimpleCommandExceptionType field_229824_a_ = new SimpleCommandExceptionType(new TranslationTextComponent("commands.spectate.self"));
    private static final DynamicCommandExceptionType field_229825_b_ = new DynamicCommandExceptionType(SpectateCommand::lambda$static$0);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("spectate").requires(SpectateCommand::lambda$register$1)).executes(SpectateCommand::lambda$register$2)).then(((RequiredArgumentBuilder)Commands.argument("target", EntityArgument.entity()).executes(SpectateCommand::lambda$register$3)).then(Commands.argument("player", EntityArgument.player()).executes(SpectateCommand::lambda$register$4))));
    }

    private static int func_229829_a_(CommandSource commandSource, @Nullable Entity entity2, ServerPlayerEntity serverPlayerEntity) throws CommandSyntaxException {
        if (serverPlayerEntity == entity2) {
            throw field_229824_a_.create();
        }
        if (serverPlayerEntity.interactionManager.getGameType() != GameType.SPECTATOR) {
            throw field_229825_b_.create(serverPlayerEntity.getDisplayName());
        }
        serverPlayerEntity.setSpectatingEntity(entity2);
        if (entity2 != null) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.spectate.success.started", entity2.getDisplayName()), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.spectate.success.stopped"), true);
        }
        return 0;
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return SpectateCommand.func_229829_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), EntityArgument.getPlayer(commandContext, "player"));
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return SpectateCommand.func_229829_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ((CommandSource)commandContext.getSource()).asPlayer());
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return SpectateCommand.func_229829_a_((CommandSource)commandContext.getSource(), null, ((CommandSource)commandContext.getSource()).asPlayer());
    }

    private static boolean lambda$register$1(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.spectate.not_spectator", object);
    }
}

