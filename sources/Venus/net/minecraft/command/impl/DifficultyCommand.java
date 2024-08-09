/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;

public class DifficultyCommand {
    private static final DynamicCommandExceptionType FAILED_EXCEPTION = new DynamicCommandExceptionType(DifficultyCommand::lambda$static$0);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralArgumentBuilder<CommandSource> literalArgumentBuilder = Commands.literal("difficulty");
        for (Difficulty difficulty : Difficulty.values()) {
            literalArgumentBuilder.then((ArgumentBuilder<CommandSource, ?>)Commands.literal(difficulty.getTranslationKey()).executes(arg_0 -> DifficultyCommand.lambda$register$1(difficulty, arg_0)));
        }
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.requires(DifficultyCommand::lambda$register$2)).executes(DifficultyCommand::lambda$register$3));
    }

    public static int setDifficulty(CommandSource commandSource, Difficulty difficulty) throws CommandSyntaxException {
        MinecraftServer minecraftServer = commandSource.getServer();
        if (minecraftServer.func_240793_aU_().getDifficulty() == difficulty) {
            throw FAILED_EXCEPTION.create(difficulty.getTranslationKey());
        }
        minecraftServer.setDifficultyForAllWorlds(difficulty, false);
        commandSource.sendFeedback(new TranslationTextComponent("commands.difficulty.success", difficulty.getDisplayName()), false);
        return 1;
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        Difficulty difficulty = ((CommandSource)commandContext.getSource()).getWorld().getDifficulty();
        ((CommandSource)commandContext.getSource()).sendFeedback(new TranslationTextComponent("commands.difficulty.query", difficulty.getDisplayName()), true);
        return difficulty.getId();
    }

    private static boolean lambda$register$2(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static int lambda$register$1(Difficulty difficulty, CommandContext commandContext) throws CommandSyntaxException {
        return DifficultyCommand.setDifficulty((CommandSource)commandContext.getSource(), difficulty);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.difficulty.failure", object);
    }
}

