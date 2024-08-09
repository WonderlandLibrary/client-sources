/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.CustomServerBossInfo;
import net.minecraft.server.CustomServerBossInfoManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;

public class BossBarCommand {
    private static final DynamicCommandExceptionType BOSS_BAR_ID_TAKEN = new DynamicCommandExceptionType(BossBarCommand::lambda$static$0);
    private static final DynamicCommandExceptionType NO_BOSSBAR_WITH_ID = new DynamicCommandExceptionType(BossBarCommand::lambda$static$1);
    private static final SimpleCommandExceptionType PLAYERS_ALREADY_ON_BOSSBAR = new SimpleCommandExceptionType(new TranslationTextComponent("commands.bossbar.set.players.unchanged"));
    private static final SimpleCommandExceptionType ALREADY_NAME_OF_BOSSBAR = new SimpleCommandExceptionType(new TranslationTextComponent("commands.bossbar.set.name.unchanged"));
    private static final SimpleCommandExceptionType ALREADY_COLOR_OF_BOSSBAR = new SimpleCommandExceptionType(new TranslationTextComponent("commands.bossbar.set.color.unchanged"));
    private static final SimpleCommandExceptionType ALREADY_STYLE_OF_BOSSBAR = new SimpleCommandExceptionType(new TranslationTextComponent("commands.bossbar.set.style.unchanged"));
    private static final SimpleCommandExceptionType ALREADY_VALUE_OF_BOSSBAR = new SimpleCommandExceptionType(new TranslationTextComponent("commands.bossbar.set.value.unchanged"));
    private static final SimpleCommandExceptionType ALREADY_MAX_OF_BOSSBAR = new SimpleCommandExceptionType(new TranslationTextComponent("commands.bossbar.set.max.unchanged"));
    private static final SimpleCommandExceptionType BOSSBAR_ALREADY_HIDDEN = new SimpleCommandExceptionType(new TranslationTextComponent("commands.bossbar.set.visibility.unchanged.hidden"));
    private static final SimpleCommandExceptionType BOSSBAR_ALREADY_VISIBLE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.bossbar.set.visibility.unchanged.visible"));
    public static final SuggestionProvider<CommandSource> SUGGESTIONS_PROVIDER = BossBarCommand::lambda$static$2;

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("bossbar").requires(BossBarCommand::lambda$register$3)).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("id", ResourceLocationArgument.resourceLocation()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("name", ComponentArgument.component()).executes(BossBarCommand::lambda$register$4))))).then(Commands.literal("remove").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("id", ResourceLocationArgument.resourceLocation()).suggests(SUGGESTIONS_PROVIDER).executes(BossBarCommand::lambda$register$5)))).then(Commands.literal("list").executes(BossBarCommand::lambda$register$6))).then(Commands.literal("set").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("id", ResourceLocationArgument.resourceLocation()).suggests(SUGGESTIONS_PROVIDER).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("name").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("name", ComponentArgument.component()).executes(BossBarCommand::lambda$register$7)))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("color").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("pink").executes(BossBarCommand::lambda$register$8))).then(Commands.literal("blue").executes(BossBarCommand::lambda$register$9))).then(Commands.literal("red").executes(BossBarCommand::lambda$register$10))).then(Commands.literal("green").executes(BossBarCommand::lambda$register$11))).then(Commands.literal("yellow").executes(BossBarCommand::lambda$register$12))).then(Commands.literal("purple").executes(BossBarCommand::lambda$register$13))).then(Commands.literal("white").executes(BossBarCommand::lambda$register$14)))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("style").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("progress").executes(BossBarCommand::lambda$register$15))).then(Commands.literal("notched_6").executes(BossBarCommand::lambda$register$16))).then(Commands.literal("notched_10").executes(BossBarCommand::lambda$register$17))).then(Commands.literal("notched_12").executes(BossBarCommand::lambda$register$18))).then(Commands.literal("notched_20").executes(BossBarCommand::lambda$register$19)))).then(Commands.literal("value").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("value", IntegerArgumentType.integer(0)).executes(BossBarCommand::lambda$register$20)))).then(Commands.literal("max").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("max", IntegerArgumentType.integer(1)).executes(BossBarCommand::lambda$register$21)))).then(Commands.literal("visible").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("visible", BoolArgumentType.bool()).executes(BossBarCommand::lambda$register$22)))).then(((LiteralArgumentBuilder)Commands.literal("players").executes(BossBarCommand::lambda$register$23)).then(Commands.argument("targets", EntityArgument.players()).executes(BossBarCommand::lambda$register$24)))))).then(Commands.literal("get").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("id", ResourceLocationArgument.resourceLocation()).suggests(SUGGESTIONS_PROVIDER).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("value").executes(BossBarCommand::lambda$register$25))).then(Commands.literal("max").executes(BossBarCommand::lambda$register$26))).then(Commands.literal("visible").executes(BossBarCommand::lambda$register$27))).then(Commands.literal("players").executes(BossBarCommand::lambda$register$28)))));
    }

    private static int getValue(CommandSource commandSource, CustomServerBossInfo customServerBossInfo) {
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.get.value", customServerBossInfo.getFormattedName(), customServerBossInfo.getValue()), false);
        return customServerBossInfo.getValue();
    }

    private static int getMax(CommandSource commandSource, CustomServerBossInfo customServerBossInfo) {
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.get.max", customServerBossInfo.getFormattedName(), customServerBossInfo.getMax()), false);
        return customServerBossInfo.getMax();
    }

    private static int getVisibility(CommandSource commandSource, CustomServerBossInfo customServerBossInfo) {
        if (customServerBossInfo.isVisible()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.get.visible.visible", customServerBossInfo.getFormattedName()), false);
            return 0;
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.get.visible.hidden", customServerBossInfo.getFormattedName()), false);
        return 1;
    }

    private static int getPlayers(CommandSource commandSource, CustomServerBossInfo customServerBossInfo) {
        if (customServerBossInfo.getPlayers().isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.get.players.none", customServerBossInfo.getFormattedName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.get.players.some", customServerBossInfo.getFormattedName(), customServerBossInfo.getPlayers().size(), TextComponentUtils.func_240649_b_(customServerBossInfo.getPlayers(), PlayerEntity::getDisplayName)), false);
        }
        return customServerBossInfo.getPlayers().size();
    }

    private static int setVisibility(CommandSource commandSource, CustomServerBossInfo customServerBossInfo, boolean bl) throws CommandSyntaxException {
        if (customServerBossInfo.isVisible() == bl) {
            if (bl) {
                throw BOSSBAR_ALREADY_VISIBLE.create();
            }
            throw BOSSBAR_ALREADY_HIDDEN.create();
        }
        customServerBossInfo.setVisible(bl);
        if (bl) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.set.visible.success.visible", customServerBossInfo.getFormattedName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.set.visible.success.hidden", customServerBossInfo.getFormattedName()), false);
        }
        return 1;
    }

    private static int setValue(CommandSource commandSource, CustomServerBossInfo customServerBossInfo, int n) throws CommandSyntaxException {
        if (customServerBossInfo.getValue() == n) {
            throw ALREADY_VALUE_OF_BOSSBAR.create();
        }
        customServerBossInfo.setValue(n);
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.set.value.success", customServerBossInfo.getFormattedName(), n), false);
        return n;
    }

    private static int setMax(CommandSource commandSource, CustomServerBossInfo customServerBossInfo, int n) throws CommandSyntaxException {
        if (customServerBossInfo.getMax() == n) {
            throw ALREADY_MAX_OF_BOSSBAR.create();
        }
        customServerBossInfo.setMax(n);
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.set.max.success", customServerBossInfo.getFormattedName(), n), false);
        return n;
    }

    private static int setColor(CommandSource commandSource, CustomServerBossInfo customServerBossInfo, BossInfo.Color color) throws CommandSyntaxException {
        if (customServerBossInfo.getColor().equals((Object)color)) {
            throw ALREADY_COLOR_OF_BOSSBAR.create();
        }
        customServerBossInfo.setColor(color);
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.set.color.success", customServerBossInfo.getFormattedName()), false);
        return 1;
    }

    private static int setStyle(CommandSource commandSource, CustomServerBossInfo customServerBossInfo, BossInfo.Overlay overlay) throws CommandSyntaxException {
        if (customServerBossInfo.getOverlay().equals((Object)overlay)) {
            throw ALREADY_STYLE_OF_BOSSBAR.create();
        }
        customServerBossInfo.setOverlay(overlay);
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.set.style.success", customServerBossInfo.getFormattedName()), false);
        return 1;
    }

    private static int setName(CommandSource commandSource, CustomServerBossInfo customServerBossInfo, ITextComponent iTextComponent) throws CommandSyntaxException {
        IFormattableTextComponent iFormattableTextComponent = TextComponentUtils.func_240645_a_(commandSource, iTextComponent, null, 0);
        if (customServerBossInfo.getName().equals(iFormattableTextComponent)) {
            throw ALREADY_NAME_OF_BOSSBAR.create();
        }
        customServerBossInfo.setName(iFormattableTextComponent);
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.set.name.success", customServerBossInfo.getFormattedName()), false);
        return 1;
    }

    private static int setPlayers(CommandSource commandSource, CustomServerBossInfo customServerBossInfo, Collection<ServerPlayerEntity> collection) throws CommandSyntaxException {
        boolean bl = customServerBossInfo.setPlayers(collection);
        if (!bl) {
            throw PLAYERS_ALREADY_ON_BOSSBAR.create();
        }
        if (customServerBossInfo.getPlayers().isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.set.players.success.none", customServerBossInfo.getFormattedName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.set.players.success.some", customServerBossInfo.getFormattedName(), collection.size(), TextComponentUtils.func_240649_b_(collection, PlayerEntity::getDisplayName)), false);
        }
        return customServerBossInfo.getPlayers().size();
    }

    private static int listBars(CommandSource commandSource) {
        Collection<CustomServerBossInfo> collection = commandSource.getServer().getCustomBossEvents().getBossbars();
        if (collection.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.list.bars.none"), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.list.bars.some", collection.size(), TextComponentUtils.func_240649_b_(collection, CustomServerBossInfo::getFormattedName)), true);
        }
        return collection.size();
    }

    private static int createBossbar(CommandSource commandSource, ResourceLocation resourceLocation, ITextComponent iTextComponent) throws CommandSyntaxException {
        CustomServerBossInfoManager customServerBossInfoManager = commandSource.getServer().getCustomBossEvents();
        if (customServerBossInfoManager.get(resourceLocation) != null) {
            throw BOSS_BAR_ID_TAKEN.create(resourceLocation.toString());
        }
        CustomServerBossInfo customServerBossInfo = customServerBossInfoManager.add(resourceLocation, TextComponentUtils.func_240645_a_(commandSource, iTextComponent, null, 0));
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.create.success", customServerBossInfo.getFormattedName()), false);
        return customServerBossInfoManager.getBossbars().size();
    }

    private static int removeBossbar(CommandSource commandSource, CustomServerBossInfo customServerBossInfo) {
        CustomServerBossInfoManager customServerBossInfoManager = commandSource.getServer().getCustomBossEvents();
        customServerBossInfo.removeAllPlayers();
        customServerBossInfoManager.remove(customServerBossInfo);
        commandSource.sendFeedback(new TranslationTextComponent("commands.bossbar.remove.success", customServerBossInfo.getFormattedName()), false);
        return customServerBossInfoManager.getBossbars().size();
    }

    public static CustomServerBossInfo getBossbar(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocationArgument.getResourceLocation(commandContext, "id");
        CustomServerBossInfo customServerBossInfo = commandContext.getSource().getServer().getCustomBossEvents().get(resourceLocation);
        if (customServerBossInfo == null) {
            throw NO_BOSSBAR_WITH_ID.create(resourceLocation.toString());
        }
        return customServerBossInfo;
    }

    private static int lambda$register$28(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.getPlayers((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext));
    }

    private static int lambda$register$27(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.getVisibility((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext));
    }

    private static int lambda$register$26(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.getMax((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext));
    }

    private static int lambda$register$25(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.getValue((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext));
    }

    private static int lambda$register$24(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setPlayers((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), EntityArgument.getPlayersAllowingNone(commandContext, "targets"));
    }

    private static int lambda$register$23(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setPlayers((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), Collections.emptyList());
    }

    private static int lambda$register$22(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setVisibility((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BoolArgumentType.getBool(commandContext, "visible"));
    }

    private static int lambda$register$21(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setMax((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), IntegerArgumentType.getInteger(commandContext, "max"));
    }

    private static int lambda$register$20(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setValue((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), IntegerArgumentType.getInteger(commandContext, "value"));
    }

    private static int lambda$register$19(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setStyle((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Overlay.NOTCHED_20);
    }

    private static int lambda$register$18(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setStyle((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Overlay.NOTCHED_12);
    }

    private static int lambda$register$17(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setStyle((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Overlay.NOTCHED_10);
    }

    private static int lambda$register$16(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setStyle((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Overlay.NOTCHED_6);
    }

    private static int lambda$register$15(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setStyle((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Overlay.PROGRESS);
    }

    private static int lambda$register$14(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setColor((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Color.WHITE);
    }

    private static int lambda$register$13(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setColor((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Color.PURPLE);
    }

    private static int lambda$register$12(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setColor((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Color.YELLOW);
    }

    private static int lambda$register$11(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setColor((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Color.GREEN);
    }

    private static int lambda$register$10(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setColor((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Color.RED);
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setColor((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Color.BLUE);
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setColor((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), BossInfo.Color.PINK);
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.setName((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), ComponentArgument.getComponent(commandContext, "name"));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.listBars((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.removeBossbar((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return BossBarCommand.createBossbar((CommandSource)commandContext.getSource(), ResourceLocationArgument.getResourceLocation(commandContext, "id"), ComponentArgument.getComponent(commandContext, "name"));
    }

    private static boolean lambda$register$3(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static CompletableFuture lambda$static$2(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggestIterable(((CommandSource)commandContext.getSource()).getServer().getCustomBossEvents().getIDs(), suggestionsBuilder);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("commands.bossbar.unknown", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.bossbar.create.failed", object);
    }
}

