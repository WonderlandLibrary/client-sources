/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import java.util.concurrent.Executor;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.filter.IChatFilter;

public class MeCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)Commands.literal("me").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("action", StringArgumentType.greedyString()).executes(MeCommand::lambda$register$2)));
    }

    private static ITextComponent func_244711_a(CommandContext<CommandSource> commandContext, String string) {
        return new TranslationTextComponent("chat.type.emote", commandContext.getSource().getDisplayName(), string);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        String string = StringArgumentType.getString(commandContext, "action");
        Entity entity2 = ((CommandSource)commandContext.getSource()).getEntity();
        MinecraftServer minecraftServer = ((CommandSource)commandContext.getSource()).getServer();
        if (entity2 != null) {
            IChatFilter iChatFilter;
            if (entity2 instanceof ServerPlayerEntity && (iChatFilter = ((ServerPlayerEntity)entity2).func_244529_Q()) != null) {
                iChatFilter.func_244432_a(string).thenAcceptAsync(arg_0 -> MeCommand.lambda$register$1(minecraftServer, commandContext, entity2, arg_0), (Executor)minecraftServer);
                return 0;
            }
            minecraftServer.getPlayerList().func_232641_a_(MeCommand.func_244711_a(commandContext, string), ChatType.CHAT, entity2.getUniqueID());
        } else {
            minecraftServer.getPlayerList().func_232641_a_(MeCommand.func_244711_a(commandContext, string), ChatType.SYSTEM, Util.DUMMY_UUID);
        }
        return 0;
    }

    private static void lambda$register$1(MinecraftServer minecraftServer, CommandContext commandContext, Entity entity2, Optional optional) {
        optional.ifPresent(arg_0 -> MeCommand.lambda$register$0(minecraftServer, commandContext, entity2, arg_0));
    }

    private static void lambda$register$0(MinecraftServer minecraftServer, CommandContext commandContext, Entity entity2, String string) {
        minecraftServer.getPlayerList().func_232641_a_(MeCommand.func_244711_a(commandContext, string), ChatType.CHAT, entity2.getUniqueID());
    }
}

