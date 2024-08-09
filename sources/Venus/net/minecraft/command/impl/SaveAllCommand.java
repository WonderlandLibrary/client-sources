/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TranslationTextComponent;

public class SaveAllCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.save.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("save-all").requires(SaveAllCommand::lambda$register$0)).executes(SaveAllCommand::lambda$register$1)).then(Commands.literal("flush").executes(SaveAllCommand::lambda$register$2)));
    }

    private static int saveAll(CommandSource commandSource, boolean bl) throws CommandSyntaxException {
        commandSource.sendFeedback(new TranslationTextComponent("commands.save.saving"), true);
        MinecraftServer minecraftServer = commandSource.getServer();
        minecraftServer.getPlayerList().saveAllPlayerData();
        boolean bl2 = minecraftServer.save(true, bl, false);
        if (!bl2) {
            throw FAILED_EXCEPTION.create();
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.save.success"), false);
        return 0;
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return SaveAllCommand.saveAll((CommandSource)commandContext.getSource(), true);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return SaveAllCommand.saveAll((CommandSource)commandContext.getSource(), false);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

