/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.AngleArgument;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

public class SetWorldSpawnCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("setworldspawn").requires(SetWorldSpawnCommand::lambda$register$0)).executes(SetWorldSpawnCommand::lambda$register$1)).then(((RequiredArgumentBuilder)Commands.argument("pos", BlockPosArgument.blockPos()).executes(SetWorldSpawnCommand::lambda$register$2)).then(Commands.argument("angle", AngleArgument.func_242991_a()).executes(SetWorldSpawnCommand::lambda$register$3))));
    }

    private static int setSpawn(CommandSource commandSource, BlockPos blockPos, float f) {
        commandSource.getWorld().func_241124_a__(blockPos, f);
        commandSource.sendFeedback(new TranslationTextComponent("commands.setworldspawn.success", blockPos.getX(), blockPos.getY(), blockPos.getZ(), Float.valueOf(f)), false);
        return 0;
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return SetWorldSpawnCommand.setSpawn((CommandSource)commandContext.getSource(), BlockPosArgument.getBlockPos(commandContext, "pos"), AngleArgument.func_242992_a(commandContext, "angle"));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return SetWorldSpawnCommand.setSpawn((CommandSource)commandContext.getSource(), BlockPosArgument.getBlockPos(commandContext, "pos"), 0.0f);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return SetWorldSpawnCommand.setSpawn((CommandSource)commandContext.getSource(), new BlockPos(((CommandSource)commandContext.getSource()).getPos()), 0.0f);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

