/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Map;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.gen.feature.structure.Structure;

public class LocateCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.locate.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)Commands.literal("locate").requires(LocateCommand::lambda$register$0);
        for (Map.Entry entry : Structure.field_236365_a_.entrySet()) {
            literalArgumentBuilder = (LiteralArgumentBuilder)literalArgumentBuilder.then(Commands.literal((String)entry.getKey()).executes(arg_0 -> LocateCommand.lambda$register$1(entry, arg_0)));
        }
        commandDispatcher.register(literalArgumentBuilder);
    }

    private static int func_241053_a_(CommandSource commandSource, Structure<?> structure) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(commandSource.getPos());
        BlockPos blockPos2 = commandSource.getWorld().func_241117_a_(structure, blockPos, 100, true);
        if (blockPos2 == null) {
            throw FAILED_EXCEPTION.create();
        }
        return LocateCommand.func_241054_a_(commandSource, structure.getStructureName(), blockPos, blockPos2, "commands.locate.success");
    }

    public static int func_241054_a_(CommandSource commandSource, String string, BlockPos blockPos, BlockPos blockPos2, String string2) {
        int n = MathHelper.floor(LocateCommand.getDistance(blockPos.getX(), blockPos.getZ(), blockPos2.getX(), blockPos2.getZ()));
        IFormattableTextComponent iFormattableTextComponent = TextComponentUtils.wrapWithSquareBrackets(new TranslationTextComponent("chat.coordinates", blockPos2.getX(), "~", blockPos2.getZ())).modifyStyle(arg_0 -> LocateCommand.lambda$func_241054_a_$2(blockPos2, arg_0));
        commandSource.sendFeedback(new TranslationTextComponent(string2, string, iFormattableTextComponent, n), true);
        return n;
    }

    private static float getDistance(int n, int n2, int n3, int n4) {
        int n5 = n3 - n;
        int n6 = n4 - n2;
        return MathHelper.sqrt(n5 * n5 + n6 * n6);
    }

    private static Style lambda$func_241054_a_$2(BlockPos blockPos, Style style) {
        return style.setFormatting(TextFormatting.GREEN).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + blockPos.getX() + " ~ " + blockPos.getZ())).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.coordinates.tooltip")));
    }

    private static int lambda$register$1(Map.Entry entry, CommandContext commandContext) throws CommandSyntaxException {
        return LocateCommand.func_241053_a_((CommandSource)commandContext.getSource(), (Structure)entry.getValue());
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

