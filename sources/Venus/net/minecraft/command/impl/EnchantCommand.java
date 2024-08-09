/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

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
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EnchantmentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class EnchantCommand {
    private static final DynamicCommandExceptionType NONLIVING_ENTITY_EXCEPTION = new DynamicCommandExceptionType(EnchantCommand::lambda$static$0);
    private static final DynamicCommandExceptionType ITEMLESS_EXCEPTION = new DynamicCommandExceptionType(EnchantCommand::lambda$static$1);
    private static final DynamicCommandExceptionType INCOMPATIBLE_ENCHANTS_EXCEPTION = new DynamicCommandExceptionType(EnchantCommand::lambda$static$2);
    private static final Dynamic2CommandExceptionType INVALID_LEVEL = new Dynamic2CommandExceptionType(EnchantCommand::lambda$static$3);
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.enchant.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("enchant").requires(EnchantCommand::lambda$register$4)).then(Commands.argument("targets", EntityArgument.entities()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("enchantment", EnchantmentArgument.enchantment()).executes(EnchantCommand::lambda$register$5)).then(Commands.argument("level", IntegerArgumentType.integer(0)).executes(EnchantCommand::lambda$register$6)))));
    }

    private static int enchant(CommandSource commandSource, Collection<? extends Entity> collection, Enchantment enchantment, int n) throws CommandSyntaxException {
        if (n > enchantment.getMaxLevel()) {
            throw INVALID_LEVEL.create(n, enchantment.getMaxLevel());
        }
        int n2 = 0;
        for (Entity entity2 : collection) {
            if (entity2 instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity2;
                ItemStack itemStack = livingEntity.getHeldItemMainhand();
                if (!itemStack.isEmpty()) {
                    if (enchantment.canApply(itemStack) && EnchantmentHelper.areAllCompatibleWith(EnchantmentHelper.getEnchantments(itemStack).keySet(), enchantment)) {
                        itemStack.addEnchantment(enchantment, n);
                        ++n2;
                        continue;
                    }
                    if (collection.size() != 1) continue;
                    throw INCOMPATIBLE_ENCHANTS_EXCEPTION.create(itemStack.getItem().getDisplayName(itemStack).getString());
                }
                if (collection.size() != 1) continue;
                throw ITEMLESS_EXCEPTION.create(livingEntity.getName().getString());
            }
            if (collection.size() != 1) continue;
            throw NONLIVING_ENTITY_EXCEPTION.create(entity2.getName().getString());
        }
        if (n2 == 0) {
            throw FAILED_EXCEPTION.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.enchant.success.single", enchantment.getDisplayName(n), collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.enchant.success.multiple", enchantment.getDisplayName(n), collection.size()), false);
        }
        return n2;
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return EnchantCommand.enchant((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), EnchantmentArgument.getEnchantment(commandContext, "enchantment"), IntegerArgumentType.getInteger(commandContext, "level"));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return EnchantCommand.enchant((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), EnchantmentArgument.getEnchantment(commandContext, "enchantment"), 1);
    }

    private static boolean lambda$register$4(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$3(Object object, Object object2) {
        return new TranslationTextComponent("commands.enchant.failed.level", object, object2);
    }

    private static Message lambda$static$2(Object object) {
        return new TranslationTextComponent("commands.enchant.failed.incompatible", object);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("commands.enchant.failed.itemless", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.enchant.failed.entity", object);
    }
}

