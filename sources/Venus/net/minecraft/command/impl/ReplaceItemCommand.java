/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.SlotArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

public class ReplaceItemCommand {
    public static final SimpleCommandExceptionType BLOCK_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.replaceitem.block.failed"));
    public static final DynamicCommandExceptionType INAPPLICABLE_SLOT_EXCEPTION = new DynamicCommandExceptionType(ReplaceItemCommand::lambda$static$0);
    public static final Dynamic2CommandExceptionType ENTITY_FAILED_EXCEPTION = new Dynamic2CommandExceptionType(ReplaceItemCommand::lambda$static$1);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("replaceitem").requires(ReplaceItemCommand::lambda$register$2)).then(Commands.literal("block").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("pos", BlockPosArgument.blockPos()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("slot", SlotArgument.slot()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("item", ItemArgument.item()).executes(ReplaceItemCommand::lambda$register$3)).then(Commands.argument("count", IntegerArgumentType.integer(1, 64)).executes(ReplaceItemCommand::lambda$register$4))))))).then(Commands.literal("entity").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.entities()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("slot", SlotArgument.slot()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("item", ItemArgument.item()).executes(ReplaceItemCommand::lambda$register$5)).then(Commands.argument("count", IntegerArgumentType.integer(1, 64)).executes(ReplaceItemCommand::lambda$register$6)))))));
    }

    private static int replaceItemBlock(CommandSource commandSource, BlockPos blockPos, int n, ItemStack itemStack) throws CommandSyntaxException {
        TileEntity tileEntity = commandSource.getWorld().getTileEntity(blockPos);
        if (!(tileEntity instanceof IInventory)) {
            throw BLOCK_FAILED_EXCEPTION.create();
        }
        IInventory iInventory = (IInventory)((Object)tileEntity);
        if (n >= 0 && n < iInventory.getSizeInventory()) {
            iInventory.setInventorySlotContents(n, itemStack);
            commandSource.sendFeedback(new TranslationTextComponent("commands.replaceitem.block.success", blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack.getTextComponent()), false);
            return 0;
        }
        throw INAPPLICABLE_SLOT_EXCEPTION.create(n);
    }

    private static int replaceItemEntities(CommandSource commandSource, Collection<? extends Entity> collection, int n, ItemStack itemStack) throws CommandSyntaxException {
        ArrayList<Entity> arrayList = Lists.newArrayListWithCapacity(collection.size());
        for (Entity entity2 : collection) {
            if (entity2 instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)entity2).container.detectAndSendChanges();
            }
            if (!entity2.replaceItemInInventory(n, itemStack.copy())) continue;
            arrayList.add(entity2);
            if (!(entity2 instanceof ServerPlayerEntity)) continue;
            ((ServerPlayerEntity)entity2).container.detectAndSendChanges();
        }
        if (arrayList.isEmpty()) {
            throw ENTITY_FAILED_EXCEPTION.create(itemStack.getTextComponent(), n);
        }
        if (arrayList.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.replaceitem.entity.success.single", ((Entity)arrayList.iterator().next()).getDisplayName(), itemStack.getTextComponent()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.replaceitem.entity.success.multiple", arrayList.size(), itemStack.getTextComponent()), false);
        }
        return arrayList.size();
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return ReplaceItemCommand.replaceItemEntities((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), SlotArgument.getSlot(commandContext, "slot"), ItemArgument.getItem(commandContext, "item").createStack(IntegerArgumentType.getInteger(commandContext, "count"), false));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return ReplaceItemCommand.replaceItemEntities((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), SlotArgument.getSlot(commandContext, "slot"), ItemArgument.getItem(commandContext, "item").createStack(1, true));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return ReplaceItemCommand.replaceItemBlock((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), SlotArgument.getSlot(commandContext, "slot"), ItemArgument.getItem(commandContext, "item").createStack(IntegerArgumentType.getInteger(commandContext, "count"), false));
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return ReplaceItemCommand.replaceItemBlock((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), SlotArgument.getSlot(commandContext, "slot"), ItemArgument.getItem(commandContext, "item").createStack(1, true));
    }

    private static boolean lambda$register$2(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$1(Object object, Object object2) {
        return new TranslationTextComponent("commands.replaceitem.entity.failed", object, object2);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.replaceitem.slot.inapplicable", object);
    }
}

