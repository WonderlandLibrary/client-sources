/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.UUIDArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class AttributeCommand {
    private static final SuggestionProvider<CommandSource> field_240996_a_ = AttributeCommand::lambda$static$0;
    private static final DynamicCommandExceptionType field_240997_b_ = new DynamicCommandExceptionType(AttributeCommand::lambda$static$1);
    private static final Dynamic2CommandExceptionType field_240998_c_ = new Dynamic2CommandExceptionType(AttributeCommand::lambda$static$2);
    private static final Dynamic3CommandExceptionType field_240999_d_ = new Dynamic3CommandExceptionType(AttributeCommand::lambda$static$3);
    private static final Dynamic3CommandExceptionType field_241000_e_ = new Dynamic3CommandExceptionType(AttributeCommand::lambda$static$4);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("attribute").requires(AttributeCommand::lambda$register$5)).then(Commands.argument("target", EntityArgument.entity()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("attribute", ResourceLocationArgument.resourceLocation()).suggests(field_240996_a_).then((ArgumentBuilder<CommandSource, ?>)((LiteralArgumentBuilder)Commands.literal("get").executes(AttributeCommand::lambda$register$6)).then(Commands.argument("scale", DoubleArgumentType.doubleArg()).executes(AttributeCommand::lambda$register$7)))).then(((LiteralArgumentBuilder)Commands.literal("base").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("set").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("value", DoubleArgumentType.doubleArg()).executes(AttributeCommand::lambda$register$8)))).then(((LiteralArgumentBuilder)Commands.literal("get").executes(AttributeCommand::lambda$register$9)).then(Commands.argument("scale", DoubleArgumentType.doubleArg()).executes(AttributeCommand::lambda$register$10))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("modifier").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("uuid", UUIDArgument.func_239194_a_()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("name", StringArgumentType.string()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("value", DoubleArgumentType.doubleArg()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("add").executes(AttributeCommand::lambda$register$11))).then(Commands.literal("multiply").executes(AttributeCommand::lambda$register$12))).then(Commands.literal("multiply_base").executes(AttributeCommand::lambda$register$13))))))).then(Commands.literal("remove").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("uuid", UUIDArgument.func_239194_a_()).executes(AttributeCommand::lambda$register$14)))).then(Commands.literal("value").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("get").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("uuid", UUIDArgument.func_239194_a_()).executes(AttributeCommand::lambda$register$15)).then(Commands.argument("scale", DoubleArgumentType.doubleArg()).executes(AttributeCommand::lambda$register$16)))))))));
    }

    private static ModifiableAttributeInstance func_241002_a_(Entity entity2, Attribute attribute) throws CommandSyntaxException {
        ModifiableAttributeInstance modifiableAttributeInstance = AttributeCommand.func_241001_a_(entity2).getAttributeManager().createInstanceIfAbsent(attribute);
        if (modifiableAttributeInstance == null) {
            throw field_240998_c_.create(entity2.getName(), new TranslationTextComponent(attribute.getAttributeName()));
        }
        return modifiableAttributeInstance;
    }

    private static LivingEntity func_241001_a_(Entity entity2) throws CommandSyntaxException {
        if (!(entity2 instanceof LivingEntity)) {
            throw field_240997_b_.create(entity2.getName());
        }
        return (LivingEntity)entity2;
    }

    private static LivingEntity func_241014_b_(Entity entity2, Attribute attribute) throws CommandSyntaxException {
        LivingEntity livingEntity = AttributeCommand.func_241001_a_(entity2);
        if (!livingEntity.getAttributeManager().hasAttributeInstance(attribute)) {
            throw field_240998_c_.create(entity2.getName(), new TranslationTextComponent(attribute.getAttributeName()));
        }
        return livingEntity;
    }

    private static int func_241007_a_(CommandSource commandSource, Entity entity2, Attribute attribute, double d) throws CommandSyntaxException {
        LivingEntity livingEntity = AttributeCommand.func_241014_b_(entity2, attribute);
        double d2 = livingEntity.getAttributeValue(attribute);
        commandSource.sendFeedback(new TranslationTextComponent("commands.attribute.value.get.success", new TranslationTextComponent(attribute.getAttributeName()), entity2.getName(), d2), true);
        return (int)(d2 * d);
    }

    private static int func_241016_b_(CommandSource commandSource, Entity entity2, Attribute attribute, double d) throws CommandSyntaxException {
        LivingEntity livingEntity = AttributeCommand.func_241014_b_(entity2, attribute);
        double d2 = livingEntity.getBaseAttributeValue(attribute);
        commandSource.sendFeedback(new TranslationTextComponent("commands.attribute.base_value.get.success", new TranslationTextComponent(attribute.getAttributeName()), entity2.getName(), d2), true);
        return (int)(d2 * d);
    }

    private static int func_241009_a_(CommandSource commandSource, Entity entity2, Attribute attribute, UUID uUID, double d) throws CommandSyntaxException {
        LivingEntity livingEntity = AttributeCommand.func_241014_b_(entity2, attribute);
        AttributeModifierManager attributeModifierManager = livingEntity.getAttributeManager();
        if (!attributeModifierManager.hasModifier(attribute, uUID)) {
            throw field_240999_d_.create(entity2.getName(), new TranslationTextComponent(attribute.getAttributeName()), uUID);
        }
        double d2 = attributeModifierManager.getModifierValue(attribute, uUID);
        commandSource.sendFeedback(new TranslationTextComponent("commands.attribute.modifier.value.get.success", uUID, new TranslationTextComponent(attribute.getAttributeName()), entity2.getName(), d2), true);
        return (int)(d2 * d);
    }

    private static int func_241019_c_(CommandSource commandSource, Entity entity2, Attribute attribute, double d) throws CommandSyntaxException {
        AttributeCommand.func_241002_a_(entity2, attribute).setBaseValue(d);
        commandSource.sendFeedback(new TranslationTextComponent("commands.attribute.base_value.set.success", new TranslationTextComponent(attribute.getAttributeName()), entity2.getName(), d), true);
        return 0;
    }

    private static int func_241010_a_(CommandSource commandSource, Entity entity2, Attribute attribute, UUID uUID, String string, double d, AttributeModifier.Operation operation) throws CommandSyntaxException {
        AttributeModifier attributeModifier;
        ModifiableAttributeInstance modifiableAttributeInstance = AttributeCommand.func_241002_a_(entity2, attribute);
        if (modifiableAttributeInstance.hasModifier(attributeModifier = new AttributeModifier(uUID, string, d, operation))) {
            throw field_241000_e_.create(entity2.getName(), new TranslationTextComponent(attribute.getAttributeName()), uUID);
        }
        modifiableAttributeInstance.applyPersistentModifier(attributeModifier);
        commandSource.sendFeedback(new TranslationTextComponent("commands.attribute.modifier.add.success", uUID, new TranslationTextComponent(attribute.getAttributeName()), entity2.getName()), true);
        return 0;
    }

    private static int func_241008_a_(CommandSource commandSource, Entity entity2, Attribute attribute, UUID uUID) throws CommandSyntaxException {
        ModifiableAttributeInstance modifiableAttributeInstance = AttributeCommand.func_241002_a_(entity2, attribute);
        if (modifiableAttributeInstance.removePersistentModifier(uUID)) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.attribute.modifier.remove.success", uUID, new TranslationTextComponent(attribute.getAttributeName()), entity2.getName()), true);
            return 0;
        }
        throw field_240999_d_.create(entity2.getName(), new TranslationTextComponent(attribute.getAttributeName()), uUID);
    }

    private static int lambda$register$16(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241009_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), UUIDArgument.func_239195_a_(commandContext, "uuid"), DoubleArgumentType.getDouble(commandContext, "scale"));
    }

    private static int lambda$register$15(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241009_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), UUIDArgument.func_239195_a_(commandContext, "uuid"), 1.0);
    }

    private static int lambda$register$14(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241008_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), UUIDArgument.func_239195_a_(commandContext, "uuid"));
    }

    private static int lambda$register$13(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241010_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), UUIDArgument.func_239195_a_(commandContext, "uuid"), StringArgumentType.getString(commandContext, "name"), DoubleArgumentType.getDouble(commandContext, "value"), AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private static int lambda$register$12(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241010_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), UUIDArgument.func_239195_a_(commandContext, "uuid"), StringArgumentType.getString(commandContext, "name"), DoubleArgumentType.getDouble(commandContext, "value"), AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    private static int lambda$register$11(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241010_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), UUIDArgument.func_239195_a_(commandContext, "uuid"), StringArgumentType.getString(commandContext, "name"), DoubleArgumentType.getDouble(commandContext, "value"), AttributeModifier.Operation.ADDITION);
    }

    private static int lambda$register$10(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241016_b_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), DoubleArgumentType.getDouble(commandContext, "scale"));
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241016_b_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), 1.0);
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241019_c_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), DoubleArgumentType.getDouble(commandContext, "value"));
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241007_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), DoubleArgumentType.getDouble(commandContext, "scale"));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return AttributeCommand.func_241007_a_((CommandSource)commandContext.getSource(), EntityArgument.getEntity(commandContext, "target"), ResourceLocationArgument.func_239094_d_(commandContext, "attribute"), 1.0);
    }

    private static boolean lambda$register$5(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$4(Object object, Object object2, Object object3) {
        return new TranslationTextComponent("commands.attribute.failed.modifier_already_present", object3, object2, object);
    }

    private static Message lambda$static$3(Object object, Object object2, Object object3) {
        return new TranslationTextComponent("commands.attribute.failed.no_modifier", object2, object, object3);
    }

    private static Message lambda$static$2(Object object, Object object2) {
        return new TranslationTextComponent("commands.attribute.failed.no_attribute", object, object2);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("commands.attribute.failed.entity", object);
    }

    private static CompletableFuture lambda$static$0(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggestIterable(Registry.ATTRIBUTE.keySet(), suggestionsBuilder);
    }
}

