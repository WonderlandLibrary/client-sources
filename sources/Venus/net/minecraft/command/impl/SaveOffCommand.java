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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class SaveOffCommand {
    private static final SimpleCommandExceptionType SAVE_ALREADY_OFF_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.save.alreadyOff"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("save-off").requires(SaveOffCommand::lambda$register$0)).executes(SaveOffCommand::lambda$register$1));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        CommandSource commandSource = (CommandSource)commandContext.getSource();
        boolean bl = false;
        for (ServerWorld serverWorld : commandSource.getServer().getWorlds()) {
            if (serverWorld == null || serverWorld.disableLevelSaving) continue;
            serverWorld.disableLevelSaving = true;
            bl = true;
        }
        if (!bl) {
            throw SAVE_ALREADY_OFF_EXCEPTION.create();
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.save.disabled"), false);
        return 0;
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

