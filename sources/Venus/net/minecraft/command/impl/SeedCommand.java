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
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class SeedCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher, boolean bl) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("seed").requires(arg_0 -> SeedCommand.lambda$register$0(bl, arg_0))).executes(SeedCommand::lambda$register$2));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        long l = ((CommandSource)commandContext.getSource()).getWorld().getSeed();
        IFormattableTextComponent iFormattableTextComponent = TextComponentUtils.wrapWithSquareBrackets(new StringTextComponent(String.valueOf(l)).modifyStyle(arg_0 -> SeedCommand.lambda$register$1(l, arg_0)));
        ((CommandSource)commandContext.getSource()).sendFeedback(new TranslationTextComponent("commands.seed.success", iFormattableTextComponent), true);
        return (int)l;
    }

    private static Style lambda$register$1(long l, Style style) {
        return style.setFormatting(TextFormatting.GREEN).setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(l))).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.copy.click"))).setInsertion(String.valueOf(l));
    }

    private static boolean lambda$register$0(boolean bl, CommandSource commandSource) {
        return !bl || commandSource.hasPermissionLevel(1);
    }
}

