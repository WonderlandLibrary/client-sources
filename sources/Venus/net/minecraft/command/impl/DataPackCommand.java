/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
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
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.impl.ReloadCommand;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

public class DataPackCommand {
    private static final DynamicCommandExceptionType UNKNOWN_DATA_PACK_EXCEPTION = new DynamicCommandExceptionType(DataPackCommand::lambda$static$0);
    private static final DynamicCommandExceptionType ENABLE_FAILED_EXCEPTION = new DynamicCommandExceptionType(DataPackCommand::lambda$static$1);
    private static final DynamicCommandExceptionType DISABLE_FAILED_EXCEPTION = new DynamicCommandExceptionType(DataPackCommand::lambda$static$2);
    private static final SuggestionProvider<CommandSource> SUGGEST_ENABLED_PACK = DataPackCommand::lambda$static$3;
    private static final SuggestionProvider<CommandSource> field_241028_e_ = DataPackCommand::lambda$static$5;

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("datapack").requires(DataPackCommand::lambda$register$6)).then(Commands.literal("enable").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("name", StringArgumentType.string()).suggests(field_241028_e_).executes(DataPackCommand::lambda$register$9)).then(Commands.literal("after").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("existing", StringArgumentType.string()).suggests(SUGGEST_ENABLED_PACK).executes(DataPackCommand::lambda$register$11)))).then(Commands.literal("before").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("existing", StringArgumentType.string()).suggests(SUGGEST_ENABLED_PACK).executes(DataPackCommand::lambda$register$13)))).then(Commands.literal("last").executes(DataPackCommand::lambda$register$14))).then(Commands.literal("first").executes(DataPackCommand::lambda$register$16))))).then(Commands.literal("disable").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("name", StringArgumentType.string()).suggests(SUGGEST_ENABLED_PACK).executes(DataPackCommand::lambda$register$17)))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("list").executes(DataPackCommand::lambda$register$18)).then(Commands.literal("available").executes(DataPackCommand::lambda$register$19))).then(Commands.literal("enabled").executes(DataPackCommand::lambda$register$20))));
    }

    private static int enablePack(CommandSource commandSource, ResourcePackInfo resourcePackInfo, IHandler iHandler) throws CommandSyntaxException {
        ResourcePackList resourcePackList = commandSource.getServer().getResourcePacks();
        ArrayList<ResourcePackInfo> arrayList = Lists.newArrayList(resourcePackList.getEnabledPacks());
        iHandler.apply(arrayList, resourcePackInfo);
        commandSource.sendFeedback(new TranslationTextComponent("commands.datapack.modify.enable", resourcePackInfo.getChatLink(false)), false);
        ReloadCommand.func_241062_a_(arrayList.stream().map(ResourcePackInfo::getName).collect(Collectors.toList()), commandSource);
        return arrayList.size();
    }

    private static int disablePack(CommandSource commandSource, ResourcePackInfo resourcePackInfo) {
        ResourcePackList resourcePackList = commandSource.getServer().getResourcePacks();
        ArrayList<ResourcePackInfo> arrayList = Lists.newArrayList(resourcePackList.getEnabledPacks());
        arrayList.remove(resourcePackInfo);
        commandSource.sendFeedback(new TranslationTextComponent("commands.datapack.modify.disable", resourcePackInfo.getChatLink(false)), false);
        ReloadCommand.func_241062_a_(arrayList.stream().map(ResourcePackInfo::getName).collect(Collectors.toList()), commandSource);
        return arrayList.size();
    }

    private static int listAllPacks(CommandSource commandSource) {
        return DataPackCommand.listEnabledPacks(commandSource) + DataPackCommand.listAvailablePacks(commandSource);
    }

    private static int listAvailablePacks(CommandSource commandSource) {
        ResourcePackList resourcePackList = commandSource.getServer().getResourcePacks();
        resourcePackList.reloadPacksFromFinders();
        Collection<ResourcePackInfo> collection = resourcePackList.getEnabledPacks();
        Collection<ResourcePackInfo> collection2 = resourcePackList.getAllPacks();
        List list = collection2.stream().filter(arg_0 -> DataPackCommand.lambda$listAvailablePacks$21(collection, arg_0)).collect(Collectors.toList());
        if (list.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.datapack.list.available.none"), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.datapack.list.available.success", list.size(), TextComponentUtils.func_240649_b_(list, DataPackCommand::lambda$listAvailablePacks$22)), true);
        }
        return list.size();
    }

    private static int listEnabledPacks(CommandSource commandSource) {
        ResourcePackList resourcePackList = commandSource.getServer().getResourcePacks();
        resourcePackList.reloadPacksFromFinders();
        Collection<ResourcePackInfo> collection = resourcePackList.getEnabledPacks();
        if (collection.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.datapack.list.enabled.none"), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.datapack.list.enabled.success", collection.size(), TextComponentUtils.func_240649_b_(collection, DataPackCommand::lambda$listEnabledPacks$23)), true);
        }
        return collection.size();
    }

    private static ResourcePackInfo parsePackInfo(CommandContext<CommandSource> commandContext, String string, boolean bl) throws CommandSyntaxException {
        String string2 = StringArgumentType.getString(commandContext, string);
        ResourcePackList resourcePackList = commandContext.getSource().getServer().getResourcePacks();
        ResourcePackInfo resourcePackInfo = resourcePackList.getPackInfo(string2);
        if (resourcePackInfo == null) {
            throw UNKNOWN_DATA_PACK_EXCEPTION.create(string2);
        }
        boolean bl2 = resourcePackList.getEnabledPacks().contains(resourcePackInfo);
        if (bl && bl2) {
            throw ENABLE_FAILED_EXCEPTION.create(string2);
        }
        if (!bl && !bl2) {
            throw DISABLE_FAILED_EXCEPTION.create(string2);
        }
        return resourcePackInfo;
    }

    private static ITextComponent lambda$listEnabledPacks$23(ResourcePackInfo resourcePackInfo) {
        return resourcePackInfo.getChatLink(false);
    }

    private static ITextComponent lambda$listAvailablePacks$22(ResourcePackInfo resourcePackInfo) {
        return resourcePackInfo.getChatLink(true);
    }

    private static boolean lambda$listAvailablePacks$21(Collection collection, ResourcePackInfo resourcePackInfo) {
        return !collection.contains(resourcePackInfo);
    }

    private static int lambda$register$20(CommandContext commandContext) throws CommandSyntaxException {
        return DataPackCommand.listEnabledPacks((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$19(CommandContext commandContext) throws CommandSyntaxException {
        return DataPackCommand.listAvailablePacks((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$18(CommandContext commandContext) throws CommandSyntaxException {
        return DataPackCommand.listAllPacks((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$17(CommandContext commandContext) throws CommandSyntaxException {
        return DataPackCommand.disablePack((CommandSource)commandContext.getSource(), DataPackCommand.parsePackInfo(commandContext, "name", false));
    }

    private static int lambda$register$16(CommandContext commandContext) throws CommandSyntaxException {
        return DataPackCommand.enablePack((CommandSource)commandContext.getSource(), DataPackCommand.parsePackInfo(commandContext, "name", true), DataPackCommand::lambda$register$15);
    }

    private static void lambda$register$15(List list, ResourcePackInfo resourcePackInfo) throws CommandSyntaxException {
        list.add(0, resourcePackInfo);
    }

    private static int lambda$register$14(CommandContext commandContext) throws CommandSyntaxException {
        return DataPackCommand.enablePack((CommandSource)commandContext.getSource(), DataPackCommand.parsePackInfo(commandContext, "name", true), List::add);
    }

    private static int lambda$register$13(CommandContext commandContext) throws CommandSyntaxException {
        return DataPackCommand.enablePack((CommandSource)commandContext.getSource(), DataPackCommand.parsePackInfo(commandContext, "name", true), (arg_0, arg_1) -> DataPackCommand.lambda$register$12(commandContext, arg_0, arg_1));
    }

    private static void lambda$register$12(CommandContext commandContext, List list, ResourcePackInfo resourcePackInfo) throws CommandSyntaxException {
        list.add(list.indexOf(DataPackCommand.parsePackInfo(commandContext, "existing", false)), resourcePackInfo);
    }

    private static int lambda$register$11(CommandContext commandContext) throws CommandSyntaxException {
        return DataPackCommand.enablePack((CommandSource)commandContext.getSource(), DataPackCommand.parsePackInfo(commandContext, "name", true), (arg_0, arg_1) -> DataPackCommand.lambda$register$10(commandContext, arg_0, arg_1));
    }

    private static void lambda$register$10(CommandContext commandContext, List list, ResourcePackInfo resourcePackInfo) throws CommandSyntaxException {
        list.add(list.indexOf(DataPackCommand.parsePackInfo(commandContext, "existing", false)) + 1, resourcePackInfo);
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return DataPackCommand.enablePack((CommandSource)commandContext.getSource(), DataPackCommand.parsePackInfo(commandContext, "name", true), DataPackCommand::lambda$register$8);
    }

    private static void lambda$register$8(List list, ResourcePackInfo resourcePackInfo) throws CommandSyntaxException {
        resourcePackInfo.getPriority().insert(list, resourcePackInfo, DataPackCommand::lambda$register$7, true);
    }

    private static ResourcePackInfo lambda$register$7(ResourcePackInfo resourcePackInfo) {
        return resourcePackInfo;
    }

    private static boolean lambda$register$6(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static CompletableFuture lambda$static$5(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        ResourcePackList resourcePackList = ((CommandSource)commandContext.getSource()).getServer().getResourcePacks();
        Collection<String> collection = resourcePackList.func_232621_d_();
        return ISuggestionProvider.suggest(resourcePackList.func_232616_b_().stream().filter(arg_0 -> DataPackCommand.lambda$static$4(collection, arg_0)).map(StringArgumentType::escapeIfRequired), suggestionsBuilder);
    }

    private static boolean lambda$static$4(Collection collection, String string) {
        return !collection.contains(string);
    }

    private static CompletableFuture lambda$static$3(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggest(((CommandSource)commandContext.getSource()).getServer().getResourcePacks().func_232621_d_().stream().map(StringArgumentType::escapeIfRequired), suggestionsBuilder);
    }

    private static Message lambda$static$2(Object object) {
        return new TranslationTextComponent("commands.datapack.disable.failed", object);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("commands.datapack.enable.failed", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.datapack.unknown", object);
    }

    static interface IHandler {
        public void apply(List<ResourcePackInfo> var1, ResourcePackInfo var2) throws CommandSyntaxException;
    }
}

