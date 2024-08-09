/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SayCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("say").requires(SayCommand::lambda$register$0)).then(Commands.argument("message", MessageArgument.message()).executes(SayCommand::lambda$register$1)));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        ITextComponent iTextComponent = MessageArgument.getMessage(commandContext, "message");
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("chat.type.announcement", ((CommandSource)commandContext.getSource()).getDisplayName(), iTextComponent);
        Entity entity2 = ((CommandSource)commandContext.getSource()).getEntity();
        if (entity2 != null) {
            ((CommandSource)commandContext.getSource()).getServer().getPlayerList().func_232641_a_(translationTextComponent, ChatType.CHAT, entity2.getUniqueID());
        } else {
            ((CommandSource)commandContext.getSource()).getServer().getPlayerList().func_232641_a_(translationTextComponent, ChatType.SYSTEM, Util.DUMMY_UUID);
        }
        return 0;
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

