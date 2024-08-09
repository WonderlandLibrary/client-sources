/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;

public class GiveCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("give").requires(GiveCommand::lambda$register$0)).then(Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("item", ItemArgument.item()).executes(GiveCommand::lambda$register$1)).then(Commands.argument("count", IntegerArgumentType.integer(1)).executes(GiveCommand::lambda$register$2)))));
    }

    private static int giveItem(CommandSource commandSource, ItemInput itemInput, Collection<ServerPlayerEntity> collection, int n) throws CommandSyntaxException {
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            int n2 = n;
            while (n2 > 0) {
                ItemEntity itemEntity;
                int n3 = Math.min(itemInput.getItem().getMaxStackSize(), n2);
                n2 -= n3;
                ItemStack itemStack = itemInput.createStack(n3, true);
                boolean bl = serverPlayerEntity.inventory.addItemStackToInventory(itemStack);
                if (bl && itemStack.isEmpty()) {
                    itemStack.setCount(1);
                    itemEntity = serverPlayerEntity.dropItem(itemStack, true);
                    if (itemEntity != null) {
                        itemEntity.makeFakeItem();
                    }
                    serverPlayerEntity.world.playSound(null, serverPlayerEntity.getPosX(), serverPlayerEntity.getPosY(), serverPlayerEntity.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((serverPlayerEntity.getRNG().nextFloat() - serverPlayerEntity.getRNG().nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    serverPlayerEntity.container.detectAndSendChanges();
                    continue;
                }
                itemEntity = serverPlayerEntity.dropItem(itemStack, true);
                if (itemEntity == null) continue;
                itemEntity.setNoPickupDelay();
                itemEntity.setOwnerId(serverPlayerEntity.getUniqueID());
            }
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.give.success.single", n, itemInput.createStack(n, true).getTextComponent(), collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.give.success.single", n, itemInput.createStack(n, true).getTextComponent(), collection.size()), false);
        }
        return collection.size();
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return GiveCommand.giveItem((CommandSource)commandContext.getSource(), ItemArgument.getItem(commandContext, "item"), EntityArgument.getPlayers(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "count"));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return GiveCommand.giveItem((CommandSource)commandContext.getSource(), ItemArgument.getItem(commandContext, "item"), EntityArgument.getPlayers(commandContext, "targets"), 1);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

