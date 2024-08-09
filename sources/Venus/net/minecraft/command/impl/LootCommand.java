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
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SlotArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.command.impl.ReplaceItemCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class LootCommand {
    public static final SuggestionProvider<CommandSource> field_218904_a = LootCommand::lambda$static$0;
    private static final DynamicCommandExceptionType field_218905_b = new DynamicCommandExceptionType(LootCommand::lambda$static$1);
    private static final DynamicCommandExceptionType field_218906_c = new DynamicCommandExceptionType(LootCommand::lambda$static$2);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(LootCommand.func_218868_a((LiteralArgumentBuilder)Commands.literal("loot").requires(LootCommand::lambda$register$3), LootCommand::lambda$register$14));
    }

    private static <T extends ArgumentBuilder<CommandSource, T>> T func_218868_a(T t, ISourceArgumentBuilder iSourceArgumentBuilder) {
        return ((ArgumentBuilder)((ArgumentBuilder)((ArgumentBuilder)t.then(((LiteralArgumentBuilder)Commands.literal("replace").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("entity").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("entities", EntityArgument.entities()).then((ArgumentBuilder<CommandSource, ?>)iSourceArgumentBuilder.construct(Commands.argument("slot", SlotArgument.slot()), LootCommand::lambda$func_218868_a$15).then(iSourceArgumentBuilder.construct(Commands.argument("count", IntegerArgumentType.integer(0)), LootCommand::lambda$func_218868_a$16)))))).then(Commands.literal("block").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targetPos", BlockPosArgument.blockPos()).then((ArgumentBuilder<CommandSource, ?>)iSourceArgumentBuilder.construct(Commands.argument("slot", SlotArgument.slot()), LootCommand::lambda$func_218868_a$17).then(iSourceArgumentBuilder.construct(Commands.argument("count", IntegerArgumentType.integer(0)), LootCommand::lambda$func_218868_a$18))))))).then(Commands.literal("insert").then(iSourceArgumentBuilder.construct(Commands.argument("targetPos", BlockPosArgument.blockPos()), LootCommand::lambda$func_218868_a$19)))).then(Commands.literal("give").then(iSourceArgumentBuilder.construct(Commands.argument("players", EntityArgument.players()), LootCommand::lambda$func_218868_a$20)))).then(Commands.literal("spawn").then(iSourceArgumentBuilder.construct(Commands.argument("targetPos", Vec3Argument.vec3()), LootCommand::lambda$func_218868_a$21)));
    }

    private static IInventory func_218862_a(CommandSource commandSource, BlockPos blockPos) throws CommandSyntaxException {
        TileEntity tileEntity = commandSource.getWorld().getTileEntity(blockPos);
        if (!(tileEntity instanceof IInventory)) {
            throw ReplaceItemCommand.BLOCK_FAILED_EXCEPTION.create();
        }
        return (IInventory)((Object)tileEntity);
    }

    private static int func_218900_a(CommandSource commandSource, BlockPos blockPos, List<ItemStack> list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        IInventory iInventory = LootCommand.func_218862_a(commandSource, blockPos);
        ArrayList<ItemStack> arrayList = Lists.newArrayListWithCapacity(list.size());
        for (ItemStack itemStack : list) {
            if (!LootCommand.func_218890_a(iInventory, itemStack.copy())) continue;
            iInventory.markDirty();
            arrayList.add(itemStack);
        }
        iSuccessListener.accept(arrayList);
        return arrayList.size();
    }

    private static boolean func_218890_a(IInventory iInventory, ItemStack itemStack) {
        boolean bl = false;
        for (int i = 0; i < iInventory.getSizeInventory() && !itemStack.isEmpty(); ++i) {
            ItemStack itemStack2 = iInventory.getStackInSlot(i);
            if (!iInventory.isItemValidForSlot(i, itemStack)) continue;
            if (itemStack2.isEmpty()) {
                iInventory.setInventorySlotContents(i, itemStack);
                bl = true;
                break;
            }
            if (!LootCommand.func_218883_a(itemStack2, itemStack)) continue;
            int n = itemStack.getMaxStackSize() - itemStack2.getCount();
            int n2 = Math.min(itemStack.getCount(), n);
            itemStack.shrink(n2);
            itemStack2.grow(n2);
            bl = true;
        }
        return bl;
    }

    private static int func_218894_a(CommandSource commandSource, BlockPos blockPos, int n, int n2, List<ItemStack> list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        IInventory iInventory = LootCommand.func_218862_a(commandSource, blockPos);
        int n3 = iInventory.getSizeInventory();
        if (n >= 0 && n < n3) {
            ArrayList<ItemStack> arrayList = Lists.newArrayListWithCapacity(list.size());
            for (int i = 0; i < n2; ++i) {
                ItemStack itemStack;
                int n4 = n + i;
                ItemStack itemStack2 = itemStack = i < list.size() ? list.get(i) : ItemStack.EMPTY;
                if (!iInventory.isItemValidForSlot(n4, itemStack)) continue;
                iInventory.setInventorySlotContents(n4, itemStack);
                arrayList.add(itemStack);
            }
            iSuccessListener.accept(arrayList);
            return arrayList.size();
        }
        throw ReplaceItemCommand.INAPPLICABLE_SLOT_EXCEPTION.create(n);
    }

    private static boolean func_218883_a(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack.getItem() == itemStack2.getItem() && itemStack.getDamage() == itemStack2.getDamage() && itemStack.getCount() <= itemStack.getMaxStackSize() && Objects.equals(itemStack.getTag(), itemStack2.getTag());
    }

    private static int func_218859_a(Collection<ServerPlayerEntity> collection, List<ItemStack> list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        ArrayList<ItemStack> arrayList = Lists.newArrayListWithCapacity(list.size());
        for (ItemStack itemStack : list) {
            for (ServerPlayerEntity serverPlayerEntity : collection) {
                if (!serverPlayerEntity.inventory.addItemStackToInventory(itemStack.copy())) continue;
                arrayList.add(itemStack);
            }
        }
        iSuccessListener.accept(arrayList);
        return arrayList.size();
    }

    private static void func_218901_a(Entity entity2, List<ItemStack> list, int n, int n2, List<ItemStack> list2) {
        for (int i = 0; i < n2; ++i) {
            ItemStack itemStack;
            ItemStack itemStack2 = itemStack = i < list.size() ? list.get(i) : ItemStack.EMPTY;
            if (!entity2.replaceItemInInventory(n + i, itemStack.copy())) continue;
            list2.add(itemStack);
        }
    }

    private static int func_218865_a(Collection<? extends Entity> collection, int n, int n2, List<ItemStack> list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        ArrayList<ItemStack> arrayList = Lists.newArrayListWithCapacity(list.size());
        for (Entity entity2 : collection) {
            if (entity2 instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
                serverPlayerEntity.container.detectAndSendChanges();
                LootCommand.func_218901_a(entity2, list, n, n2, arrayList);
                serverPlayerEntity.container.detectAndSendChanges();
                continue;
            }
            LootCommand.func_218901_a(entity2, list, n, n2, arrayList);
        }
        iSuccessListener.accept(arrayList);
        return arrayList.size();
    }

    private static int func_218881_a(CommandSource commandSource, Vector3d vector3d, List<ItemStack> list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        ServerWorld serverWorld = commandSource.getWorld();
        list.forEach(arg_0 -> LootCommand.lambda$func_218881_a$22(serverWorld, vector3d, arg_0));
        iSuccessListener.accept(list);
        return list.size();
    }

    private static void func_218875_a(CommandSource commandSource, List<ItemStack> list) {
        if (list.size() == 1) {
            ItemStack itemStack = list.get(0);
            commandSource.sendFeedback(new TranslationTextComponent("commands.drop.success.single", itemStack.getCount(), itemStack.getTextComponent()), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.drop.success.multiple", list.size()), true);
        }
    }

    private static void func_218860_a(CommandSource commandSource, List<ItemStack> list, ResourceLocation resourceLocation) {
        if (list.size() == 1) {
            ItemStack itemStack = list.get(0);
            commandSource.sendFeedback(new TranslationTextComponent("commands.drop.success.single_with_table", itemStack.getCount(), itemStack.getTextComponent(), resourceLocation), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.drop.success.multiple_with_table", list.size(), resourceLocation), true);
        }
    }

    private static ItemStack func_218872_a(CommandSource commandSource, EquipmentSlotType equipmentSlotType) throws CommandSyntaxException {
        Entity entity2 = commandSource.assertIsEntity();
        if (entity2 instanceof LivingEntity) {
            return ((LivingEntity)entity2).getItemStackFromSlot(equipmentSlotType);
        }
        throw field_218905_b.create(entity2.getDisplayName());
    }

    private static int func_218879_a(CommandContext<CommandSource> commandContext, BlockPos blockPos, ItemStack itemStack, ITargetHandler iTargetHandler) throws CommandSyntaxException {
        CommandSource commandSource = commandContext.getSource();
        ServerWorld serverWorld = commandSource.getWorld();
        BlockState blockState = serverWorld.getBlockState(blockPos);
        TileEntity tileEntity = serverWorld.getTileEntity(blockPos);
        LootContext.Builder builder = new LootContext.Builder(serverWorld).withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(blockPos)).withParameter(LootParameters.BLOCK_STATE, blockState).withNullableParameter(LootParameters.BLOCK_ENTITY, tileEntity).withNullableParameter(LootParameters.THIS_ENTITY, commandSource.getEntity()).withParameter(LootParameters.TOOL, itemStack);
        List<ItemStack> list = blockState.getDrops(builder);
        return iTargetHandler.accept(commandContext, list, arg_0 -> LootCommand.lambda$func_218879_a$23(commandSource, blockState, arg_0));
    }

    private static int func_218869_a(CommandContext<CommandSource> commandContext, Entity entity2, ITargetHandler iTargetHandler) throws CommandSyntaxException {
        if (!(entity2 instanceof LivingEntity)) {
            throw field_218906_c.create(entity2.getDisplayName());
        }
        ResourceLocation resourceLocation = ((LivingEntity)entity2).getLootTableResourceLocation();
        CommandSource commandSource = commandContext.getSource();
        LootContext.Builder builder = new LootContext.Builder(commandSource.getWorld());
        Entity entity3 = commandSource.getEntity();
        if (entity3 instanceof PlayerEntity) {
            builder.withParameter(LootParameters.LAST_DAMAGE_PLAYER, (PlayerEntity)entity3);
        }
        builder.withParameter(LootParameters.DAMAGE_SOURCE, DamageSource.MAGIC);
        builder.withNullableParameter(LootParameters.DIRECT_KILLER_ENTITY, entity3);
        builder.withNullableParameter(LootParameters.KILLER_ENTITY, entity3);
        builder.withParameter(LootParameters.THIS_ENTITY, entity2);
        builder.withParameter(LootParameters.field_237457_g_, commandSource.getPos());
        LootTable lootTable = commandSource.getServer().getLootTableManager().getLootTableFromLocation(resourceLocation);
        List<ItemStack> list = lootTable.generate(builder.build(LootParameterSets.ENTITY));
        return iTargetHandler.accept(commandContext, list, arg_0 -> LootCommand.lambda$func_218869_a$24(commandSource, resourceLocation, arg_0));
    }

    private static int func_218887_a(CommandContext<CommandSource> commandContext, ResourceLocation resourceLocation, ITargetHandler iTargetHandler) throws CommandSyntaxException {
        CommandSource commandSource = commandContext.getSource();
        LootContext.Builder builder = new LootContext.Builder(commandSource.getWorld()).withNullableParameter(LootParameters.THIS_ENTITY, commandSource.getEntity()).withParameter(LootParameters.field_237457_g_, commandSource.getPos());
        return LootCommand.func_218871_a(commandContext, resourceLocation, builder.build(LootParameterSets.CHEST), iTargetHandler);
    }

    private static int func_218876_a(CommandContext<CommandSource> commandContext, ResourceLocation resourceLocation, BlockPos blockPos, ItemStack itemStack, ITargetHandler iTargetHandler) throws CommandSyntaxException {
        CommandSource commandSource = commandContext.getSource();
        LootContext lootContext = new LootContext.Builder(commandSource.getWorld()).withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(blockPos)).withParameter(LootParameters.TOOL, itemStack).withNullableParameter(LootParameters.THIS_ENTITY, commandSource.getEntity()).build(LootParameterSets.FISHING);
        return LootCommand.func_218871_a(commandContext, resourceLocation, lootContext, iTargetHandler);
    }

    private static int func_218871_a(CommandContext<CommandSource> commandContext, ResourceLocation resourceLocation, LootContext lootContext, ITargetHandler iTargetHandler) throws CommandSyntaxException {
        CommandSource commandSource = commandContext.getSource();
        LootTable lootTable = commandSource.getServer().getLootTableManager().getLootTableFromLocation(resourceLocation);
        List<ItemStack> list = lootTable.generate(lootContext);
        return iTargetHandler.accept(commandContext, list, arg_0 -> LootCommand.lambda$func_218871_a$25(commandSource, arg_0));
    }

    private static void lambda$func_218871_a$25(CommandSource commandSource, List list) throws CommandSyntaxException {
        LootCommand.func_218875_a(commandSource, list);
    }

    private static void lambda$func_218869_a$24(CommandSource commandSource, ResourceLocation resourceLocation, List list) throws CommandSyntaxException {
        LootCommand.func_218860_a(commandSource, list, resourceLocation);
    }

    private static void lambda$func_218879_a$23(CommandSource commandSource, BlockState blockState, List list) throws CommandSyntaxException {
        LootCommand.func_218860_a(commandSource, list, blockState.getBlock().getLootTable());
    }

    private static void lambda$func_218881_a$22(ServerWorld serverWorld, Vector3d vector3d, ItemStack itemStack) {
        ItemEntity itemEntity = new ItemEntity(serverWorld, vector3d.x, vector3d.y, vector3d.z, itemStack.copy());
        itemEntity.setDefaultPickupDelay();
        serverWorld.addEntity(itemEntity);
    }

    private static int lambda$func_218868_a$21(CommandContext commandContext, List list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        return LootCommand.func_218881_a((CommandSource)commandContext.getSource(), Vec3Argument.getVec3(commandContext, "targetPos"), list, iSuccessListener);
    }

    private static int lambda$func_218868_a$20(CommandContext commandContext, List list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        return LootCommand.func_218859_a(EntityArgument.getPlayers(commandContext, "players"), list, iSuccessListener);
    }

    private static int lambda$func_218868_a$19(CommandContext commandContext, List list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        return LootCommand.func_218900_a((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "targetPos"), list, iSuccessListener);
    }

    private static int lambda$func_218868_a$18(CommandContext commandContext, List list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        return LootCommand.func_218894_a((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "targetPos"), IntegerArgumentType.getInteger(commandContext, "slot"), IntegerArgumentType.getInteger(commandContext, "count"), list, iSuccessListener);
    }

    private static int lambda$func_218868_a$17(CommandContext commandContext, List list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        return LootCommand.func_218894_a((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "targetPos"), SlotArgument.getSlot(commandContext, "slot"), list.size(), list, iSuccessListener);
    }

    private static int lambda$func_218868_a$16(CommandContext commandContext, List list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        return LootCommand.func_218865_a(EntityArgument.getEntities(commandContext, "entities"), SlotArgument.getSlot(commandContext, "slot"), IntegerArgumentType.getInteger(commandContext, "count"), list, iSuccessListener);
    }

    private static int lambda$func_218868_a$15(CommandContext commandContext, List list, ISuccessListener iSuccessListener) throws CommandSyntaxException {
        return LootCommand.func_218865_a(EntityArgument.getEntities(commandContext, "entities"), SlotArgument.getSlot(commandContext, "slot"), list.size(), list, iSuccessListener);
    }

    private static ArgumentBuilder lambda$register$14(ArgumentBuilder argumentBuilder, ITargetHandler iTargetHandler) {
        return ((ArgumentBuilder)((ArgumentBuilder)((ArgumentBuilder)argumentBuilder.then(Commands.literal("fish").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("loot_table", ResourceLocationArgument.resourceLocation()).suggests(field_218904_a).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("pos", BlockPosArgument.blockPos()).executes(arg_0 -> LootCommand.lambda$register$4(iTargetHandler, arg_0))).then(Commands.argument("tool", ItemArgument.item()).executes(arg_0 -> LootCommand.lambda$register$5(iTargetHandler, arg_0)))).then(Commands.literal("mainhand").executes(arg_0 -> LootCommand.lambda$register$6(iTargetHandler, arg_0)))).then(Commands.literal("offhand").executes(arg_0 -> LootCommand.lambda$register$7(iTargetHandler, arg_0))))))).then(Commands.literal("loot").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("loot_table", ResourceLocationArgument.resourceLocation()).suggests(field_218904_a).executes(arg_0 -> LootCommand.lambda$register$8(iTargetHandler, arg_0))))).then(Commands.literal("kill").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("target", EntityArgument.entity()).executes(arg_0 -> LootCommand.lambda$register$9(iTargetHandler, arg_0))))).then(Commands.literal("mine").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("pos", BlockPosArgument.blockPos()).executes(arg_0 -> LootCommand.lambda$register$10(iTargetHandler, arg_0))).then(Commands.argument("tool", ItemArgument.item()).executes(arg_0 -> LootCommand.lambda$register$11(iTargetHandler, arg_0)))).then(Commands.literal("mainhand").executes(arg_0 -> LootCommand.lambda$register$12(iTargetHandler, arg_0)))).then(Commands.literal("offhand").executes(arg_0 -> LootCommand.lambda$register$13(iTargetHandler, arg_0)))));
    }

    private static int lambda$register$13(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218879_a(commandContext, BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), LootCommand.func_218872_a((CommandSource)commandContext.getSource(), EquipmentSlotType.OFFHAND), iTargetHandler);
    }

    private static int lambda$register$12(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218879_a(commandContext, BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), LootCommand.func_218872_a((CommandSource)commandContext.getSource(), EquipmentSlotType.MAINHAND), iTargetHandler);
    }

    private static int lambda$register$11(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218879_a(commandContext, BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), ItemArgument.getItem(commandContext, "tool").createStack(1, true), iTargetHandler);
    }

    private static int lambda$register$10(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218879_a(commandContext, BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), ItemStack.EMPTY, iTargetHandler);
    }

    private static int lambda$register$9(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218869_a(commandContext, EntityArgument.getEntity(commandContext, "target"), iTargetHandler);
    }

    private static int lambda$register$8(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218887_a(commandContext, ResourceLocationArgument.getResourceLocation(commandContext, "loot_table"), iTargetHandler);
    }

    private static int lambda$register$7(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218876_a(commandContext, ResourceLocationArgument.getResourceLocation(commandContext, "loot_table"), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), LootCommand.func_218872_a((CommandSource)commandContext.getSource(), EquipmentSlotType.OFFHAND), iTargetHandler);
    }

    private static int lambda$register$6(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218876_a(commandContext, ResourceLocationArgument.getResourceLocation(commandContext, "loot_table"), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), LootCommand.func_218872_a((CommandSource)commandContext.getSource(), EquipmentSlotType.MAINHAND), iTargetHandler);
    }

    private static int lambda$register$5(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218876_a(commandContext, ResourceLocationArgument.getResourceLocation(commandContext, "loot_table"), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), ItemArgument.getItem(commandContext, "tool").createStack(1, true), iTargetHandler);
    }

    private static int lambda$register$4(ITargetHandler iTargetHandler, CommandContext commandContext) throws CommandSyntaxException {
        return LootCommand.func_218876_a(commandContext, ResourceLocationArgument.getResourceLocation(commandContext, "loot_table"), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), ItemStack.EMPTY, iTargetHandler);
    }

    private static boolean lambda$register$3(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$2(Object object) {
        return new TranslationTextComponent("commands.drop.no_loot_table", object);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("commands.drop.no_held_items", object);
    }

    private static CompletableFuture lambda$static$0(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        LootTableManager lootTableManager = ((CommandSource)commandContext.getSource()).getServer().getLootTableManager();
        return ISuggestionProvider.suggestIterable(lootTableManager.getLootTableKeys(), suggestionsBuilder);
    }

    @FunctionalInterface
    static interface ISourceArgumentBuilder {
        public ArgumentBuilder<CommandSource, ?> construct(ArgumentBuilder<CommandSource, ?> var1, ITargetHandler var2);
    }

    @FunctionalInterface
    static interface ITargetHandler {
        public int accept(CommandContext<CommandSource> var1, List<ItemStack> var2, ISuccessListener var3) throws CommandSyntaxException;
    }

    @FunctionalInterface
    static interface ISuccessListener {
        public void accept(List<ItemStack> var1) throws CommandSyntaxException;
    }
}

