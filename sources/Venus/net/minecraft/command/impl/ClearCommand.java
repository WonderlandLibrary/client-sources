/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemPredicateArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class ClearCommand {
    private static final DynamicCommandExceptionType SINGLE_FAILED_EXCEPTION = new DynamicCommandExceptionType(ClearCommand::lambda$static$0);
    private static final DynamicCommandExceptionType MULTIPLE_FAILED_EXCEPTION = new DynamicCommandExceptionType(ClearCommand::lambda$static$1);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("clear").requires(ClearCommand::lambda$register$2)).executes(ClearCommand::lambda$register$4)).then(((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).executes(ClearCommand::lambda$register$6)).then(((RequiredArgumentBuilder)Commands.argument("item", ItemPredicateArgument.itemPredicate()).executes(ClearCommand::lambda$register$7)).then(Commands.argument("maxCount", IntegerArgumentType.integer(0)).executes(ClearCommand::lambda$register$8)))));
    }

    private static int clearInventory(CommandSource commandSource, Collection<ServerPlayerEntity> collection, Predicate<ItemStack> predicate, int n) throws CommandSyntaxException {
        int n2 = 0;
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            n2 += serverPlayerEntity.inventory.func_234564_a_(predicate, n, serverPlayerEntity.container.func_234641_j_());
            serverPlayerEntity.openContainer.detectAndSendChanges();
            serverPlayerEntity.container.onCraftMatrixChanged(serverPlayerEntity.inventory);
            serverPlayerEntity.updateHeldItem();
        }
        if (n2 == 0) {
            if (collection.size() == 1) {
                throw SINGLE_FAILED_EXCEPTION.create(collection.iterator().next().getName());
            }
            throw MULTIPLE_FAILED_EXCEPTION.create(collection.size());
        }
        if (n == 0) {
            if (collection.size() == 1) {
                commandSource.sendFeedback(new TranslationTextComponent("commands.clear.test.single", n2, collection.iterator().next().getDisplayName()), false);
            } else {
                commandSource.sendFeedback(new TranslationTextComponent("commands.clear.test.multiple", n2, collection.size()), false);
            }
        } else if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.clear.success.single", n2, collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.clear.success.multiple", n2, collection.size()), false);
        }
        return n2;
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return ClearCommand.clearInventory((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ItemPredicateArgument.getItemPredicate(commandContext, "item"), IntegerArgumentType.getInteger(commandContext, "maxCount"));
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return ClearCommand.clearInventory((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ItemPredicateArgument.getItemPredicate(commandContext, "item"), -1);
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return ClearCommand.clearInventory((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ClearCommand::lambda$register$5, -1);
    }

    private static boolean lambda$register$5(ItemStack itemStack) {
        return false;
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return ClearCommand.clearInventory((CommandSource)commandContext.getSource(), Collections.singleton(((CommandSource)commandContext.getSource()).asPlayer()), ClearCommand::lambda$register$3, -1);
    }

    private static boolean lambda$register$3(ItemStack itemStack) {
        return false;
    }

    private static boolean lambda$register$2(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("clear.failed.multiple", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("clear.failed.single", object);
    }
}

