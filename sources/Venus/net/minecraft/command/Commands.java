/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.impl.AdvancementCommand;
import net.minecraft.command.impl.AttributeCommand;
import net.minecraft.command.impl.BanCommand;
import net.minecraft.command.impl.BanIpCommand;
import net.minecraft.command.impl.BanListCommand;
import net.minecraft.command.impl.BossBarCommand;
import net.minecraft.command.impl.ClearCommand;
import net.minecraft.command.impl.CloneCommand;
import net.minecraft.command.impl.DataPackCommand;
import net.minecraft.command.impl.DeOpCommand;
import net.minecraft.command.impl.DebugCommand;
import net.minecraft.command.impl.DefaultGameModeCommand;
import net.minecraft.command.impl.DifficultyCommand;
import net.minecraft.command.impl.EffectCommand;
import net.minecraft.command.impl.EnchantCommand;
import net.minecraft.command.impl.ExecuteCommand;
import net.minecraft.command.impl.ExperienceCommand;
import net.minecraft.command.impl.FillCommand;
import net.minecraft.command.impl.ForceLoadCommand;
import net.minecraft.command.impl.FunctionCommand;
import net.minecraft.command.impl.GameModeCommand;
import net.minecraft.command.impl.GameRuleCommand;
import net.minecraft.command.impl.GiveCommand;
import net.minecraft.command.impl.HelpCommand;
import net.minecraft.command.impl.KickCommand;
import net.minecraft.command.impl.KillCommand;
import net.minecraft.command.impl.ListCommand;
import net.minecraft.command.impl.LocateBiomeCommand;
import net.minecraft.command.impl.LocateCommand;
import net.minecraft.command.impl.LootCommand;
import net.minecraft.command.impl.MeCommand;
import net.minecraft.command.impl.MessageCommand;
import net.minecraft.command.impl.OpCommand;
import net.minecraft.command.impl.PardonCommand;
import net.minecraft.command.impl.PardonIpCommand;
import net.minecraft.command.impl.ParticleCommand;
import net.minecraft.command.impl.PlaySoundCommand;
import net.minecraft.command.impl.PublishCommand;
import net.minecraft.command.impl.RecipeCommand;
import net.minecraft.command.impl.ReloadCommand;
import net.minecraft.command.impl.ReplaceItemCommand;
import net.minecraft.command.impl.SaveAllCommand;
import net.minecraft.command.impl.SaveOffCommand;
import net.minecraft.command.impl.SaveOnCommand;
import net.minecraft.command.impl.SayCommand;
import net.minecraft.command.impl.ScheduleCommand;
import net.minecraft.command.impl.ScoreboardCommand;
import net.minecraft.command.impl.SeedCommand;
import net.minecraft.command.impl.SetBlockCommand;
import net.minecraft.command.impl.SetIdleTimeoutCommand;
import net.minecraft.command.impl.SetWorldSpawnCommand;
import net.minecraft.command.impl.SpawnPointCommand;
import net.minecraft.command.impl.SpectateCommand;
import net.minecraft.command.impl.SpreadPlayersCommand;
import net.minecraft.command.impl.StopCommand;
import net.minecraft.command.impl.StopSoundCommand;
import net.minecraft.command.impl.SummonCommand;
import net.minecraft.command.impl.TagCommand;
import net.minecraft.command.impl.TeamCommand;
import net.minecraft.command.impl.TeamMsgCommand;
import net.minecraft.command.impl.TeleportCommand;
import net.minecraft.command.impl.TellRawCommand;
import net.minecraft.command.impl.TimeCommand;
import net.minecraft.command.impl.TitleCommand;
import net.minecraft.command.impl.TriggerCommand;
import net.minecraft.command.impl.WeatherCommand;
import net.minecraft.command.impl.WhitelistCommand;
import net.minecraft.command.impl.WorldBorderCommand;
import net.minecraft.command.impl.data.DataCommand;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SCommandListPacket;
import net.minecraft.test.TestCommand;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Commands {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher();

    public Commands(EnvironmentType environmentType) {
        AdvancementCommand.register(this.dispatcher);
        AttributeCommand.register(this.dispatcher);
        ExecuteCommand.register(this.dispatcher);
        BossBarCommand.register(this.dispatcher);
        ClearCommand.register(this.dispatcher);
        CloneCommand.register(this.dispatcher);
        DataCommand.register(this.dispatcher);
        DataPackCommand.register(this.dispatcher);
        DebugCommand.register(this.dispatcher);
        DefaultGameModeCommand.register(this.dispatcher);
        DifficultyCommand.register(this.dispatcher);
        EffectCommand.register(this.dispatcher);
        MeCommand.register(this.dispatcher);
        EnchantCommand.register(this.dispatcher);
        ExperienceCommand.register(this.dispatcher);
        FillCommand.register(this.dispatcher);
        ForceLoadCommand.register(this.dispatcher);
        FunctionCommand.register(this.dispatcher);
        GameModeCommand.register(this.dispatcher);
        GameRuleCommand.register(this.dispatcher);
        GiveCommand.register(this.dispatcher);
        HelpCommand.register(this.dispatcher);
        KickCommand.register(this.dispatcher);
        KillCommand.register(this.dispatcher);
        ListCommand.register(this.dispatcher);
        LocateCommand.register(this.dispatcher);
        LocateBiomeCommand.register(this.dispatcher);
        LootCommand.register(this.dispatcher);
        MessageCommand.register(this.dispatcher);
        ParticleCommand.register(this.dispatcher);
        PlaySoundCommand.register(this.dispatcher);
        ReloadCommand.register(this.dispatcher);
        RecipeCommand.register(this.dispatcher);
        ReplaceItemCommand.register(this.dispatcher);
        SayCommand.register(this.dispatcher);
        ScheduleCommand.register(this.dispatcher);
        ScoreboardCommand.register(this.dispatcher);
        SeedCommand.register(this.dispatcher, environmentType != EnvironmentType.INTEGRATED);
        SetBlockCommand.register(this.dispatcher);
        SpawnPointCommand.register(this.dispatcher);
        SetWorldSpawnCommand.register(this.dispatcher);
        SpectateCommand.register(this.dispatcher);
        SpreadPlayersCommand.register(this.dispatcher);
        StopSoundCommand.register(this.dispatcher);
        SummonCommand.register(this.dispatcher);
        TagCommand.register(this.dispatcher);
        TeamCommand.register(this.dispatcher);
        TeamMsgCommand.register(this.dispatcher);
        TeleportCommand.register(this.dispatcher);
        TellRawCommand.register(this.dispatcher);
        TimeCommand.register(this.dispatcher);
        TitleCommand.register(this.dispatcher);
        TriggerCommand.register(this.dispatcher);
        WeatherCommand.register(this.dispatcher);
        WorldBorderCommand.register(this.dispatcher);
        if (SharedConstants.developmentMode) {
            TestCommand.register(this.dispatcher);
        }
        if (environmentType.field_237220_e_) {
            BanIpCommand.register(this.dispatcher);
            BanListCommand.register(this.dispatcher);
            BanCommand.register(this.dispatcher);
            DeOpCommand.register(this.dispatcher);
            OpCommand.register(this.dispatcher);
            PardonCommand.register(this.dispatcher);
            PardonIpCommand.register(this.dispatcher);
            SaveAllCommand.register(this.dispatcher);
            SaveOffCommand.register(this.dispatcher);
            SaveOnCommand.register(this.dispatcher);
            SetIdleTimeoutCommand.register(this.dispatcher);
            StopCommand.register(this.dispatcher);
            WhitelistCommand.register(this.dispatcher);
        }
        if (environmentType.field_237219_d_) {
            PublishCommand.register(this.dispatcher);
        }
        this.dispatcher.findAmbiguities(this::lambda$new$0);
        this.dispatcher.setConsumer(Commands::lambda$new$1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int handleCommand(CommandSource commandSource, String string) {
        int n;
        StringReader stringReader = new StringReader(string);
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }
        commandSource.getServer().getProfiler().startSection(string);
        try {
            int n2 = this.dispatcher.execute(stringReader, commandSource);
            return n2;
        } catch (CommandException commandException) {
            commandSource.sendErrorMessage(commandException.getComponent());
            int n3 = 0;
            return n3;
        } catch (CommandSyntaxException commandSyntaxException) {
            commandSource.sendErrorMessage(TextComponentUtils.toTextComponent(commandSyntaxException.getRawMessage()));
            if (commandSyntaxException.getInput() != null && commandSyntaxException.getCursor() >= 0) {
                int n4 = Math.min(commandSyntaxException.getInput().length(), commandSyntaxException.getCursor());
                IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("").mergeStyle(TextFormatting.GRAY).modifyStyle(arg_0 -> Commands.lambda$handleCommand$2(string, arg_0));
                if (n4 > 10) {
                    iFormattableTextComponent.appendString("...");
                }
                iFormattableTextComponent.appendString(commandSyntaxException.getInput().substring(Math.max(0, n4 - 10), n4));
                if (n4 < commandSyntaxException.getInput().length()) {
                    IFormattableTextComponent iFormattableTextComponent2 = new StringTextComponent(commandSyntaxException.getInput().substring(n4)).mergeStyle(TextFormatting.RED, TextFormatting.UNDERLINE);
                    iFormattableTextComponent.append(iFormattableTextComponent2);
                }
                iFormattableTextComponent.append(new TranslationTextComponent("command.context.here").mergeStyle(TextFormatting.RED, TextFormatting.ITALIC));
                commandSource.sendErrorMessage(iFormattableTextComponent);
            }
        } catch (Exception exception) {
            StringTextComponent stringTextComponent = new StringTextComponent(exception.getMessage() == null ? exception.getClass().getName() : exception.getMessage());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("Command exception: {}", (Object)string, (Object)exception);
                StackTraceElement[] stackTraceElementArray = exception.getStackTrace();
                for (int i = 0; i < Math.min(stackTraceElementArray.length, 3); ++i) {
                    stringTextComponent.appendString("\n\n").appendString(stackTraceElementArray[i].getMethodName()).appendString("\n ").appendString(stackTraceElementArray[i].getFileName()).appendString(":").appendString(String.valueOf(stackTraceElementArray[i].getLineNumber()));
                }
            }
            commandSource.sendErrorMessage(new TranslationTextComponent("command.failed").modifyStyle(arg_0 -> Commands.lambda$handleCommand$3(stringTextComponent, arg_0)));
            if (SharedConstants.developmentMode) {
                commandSource.sendErrorMessage(new StringTextComponent(Util.getMessage(exception)));
                LOGGER.error("'" + string + "' threw an exception", (Throwable)exception);
            }
            int n5 = 0;
            return n5;
        }
        {
            n = 0;
        }
        return n;
        finally {
            commandSource.getServer().getProfiler().endSection();
        }
    }

    public void send(ServerPlayerEntity serverPlayerEntity) {
        HashMap<CommandNode<CommandSource>, CommandNode<ISuggestionProvider>> hashMap = Maps.newHashMap();
        RootCommandNode<ISuggestionProvider> rootCommandNode = new RootCommandNode<ISuggestionProvider>();
        hashMap.put(this.dispatcher.getRoot(), rootCommandNode);
        this.commandSourceNodesToSuggestionNodes(this.dispatcher.getRoot(), rootCommandNode, serverPlayerEntity.getCommandSource(), hashMap);
        serverPlayerEntity.connection.sendPacket(new SCommandListPacket(rootCommandNode));
    }

    private void commandSourceNodesToSuggestionNodes(CommandNode<CommandSource> commandNode, CommandNode<ISuggestionProvider> commandNode2, CommandSource commandSource, Map<CommandNode<CommandSource>, CommandNode<ISuggestionProvider>> map) {
        for (CommandNode<CommandSource> commandNode3 : commandNode.getChildren()) {
            Object object;
            if (!commandNode3.canUse(commandSource)) continue;
            ArgumentBuilder<CommandSource, ?> argumentBuilder = commandNode3.createBuilder();
            argumentBuilder.requires(Commands::lambda$commandSourceNodesToSuggestionNodes$4);
            if (argumentBuilder.getCommand() != null) {
                argumentBuilder.executes(Commands::lambda$commandSourceNodesToSuggestionNodes$5);
            }
            if (argumentBuilder instanceof RequiredArgumentBuilder && ((RequiredArgumentBuilder)(object = (RequiredArgumentBuilder)argumentBuilder)).getSuggestionsProvider() != null) {
                ((RequiredArgumentBuilder)object).suggests(SuggestionProviders.ensureKnown(((RequiredArgumentBuilder)object).getSuggestionsProvider()));
            }
            if (argumentBuilder.getRedirect() != null) {
                argumentBuilder.redirect(map.get(argumentBuilder.getRedirect()));
            }
            object = argumentBuilder.build();
            map.put(commandNode3, (CommandNode<ISuggestionProvider>)object);
            commandNode2.addChild((CommandNode<ISuggestionProvider>)object);
            if (commandNode3.getChildren().isEmpty()) continue;
            this.commandSourceNodesToSuggestionNodes(commandNode3, (CommandNode<ISuggestionProvider>)object, commandSource, map);
        }
    }

    public static LiteralArgumentBuilder<CommandSource> literal(String string) {
        return LiteralArgumentBuilder.literal(string);
    }

    public static <T> RequiredArgumentBuilder<CommandSource, T> argument(String string, ArgumentType<T> argumentType) {
        return RequiredArgumentBuilder.argument(string, argumentType);
    }

    public static Predicate<String> predicate(IParser iParser) {
        return arg_0 -> Commands.lambda$predicate$6(iParser, arg_0);
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        return this.dispatcher;
    }

    @Nullable
    public static <S> CommandSyntaxException func_227481_a_(ParseResults<S> parseResults) {
        if (!parseResults.getReader().canRead()) {
            return null;
        }
        if (parseResults.getExceptions().size() == 1) {
            return parseResults.getExceptions().values().iterator().next();
        }
        return parseResults.getContext().getRange().isEmpty() ? CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(parseResults.getReader()) : CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(parseResults.getReader());
    }

    public static void func_242986_b() {
        RootCommandNode<CommandSource> rootCommandNode = new Commands(EnvironmentType.ALL).getDispatcher().getRoot();
        Set<ArgumentType<?>> set = ArgumentTypes.func_243511_a(rootCommandNode);
        Set set2 = set.stream().filter(Commands::lambda$func_242986_b$7).collect(Collectors.toSet());
        if (!set2.isEmpty()) {
            LOGGER.warn("Missing type registration for following arguments:\n {}", (Object)set2.stream().map(Commands::lambda$func_242986_b$8).collect(Collectors.joining(",\n")));
            throw new IllegalStateException("Unregistered argument types");
        }
    }

    private static String lambda$func_242986_b$8(ArgumentType argumentType) {
        return "\t" + argumentType;
    }

    private static boolean lambda$func_242986_b$7(ArgumentType argumentType) {
        return !ArgumentTypes.func_243510_a(argumentType);
    }

    private static boolean lambda$predicate$6(IParser iParser, String string) {
        try {
            iParser.parse(new StringReader(string));
            return true;
        } catch (CommandSyntaxException commandSyntaxException) {
            return true;
        }
    }

    private static int lambda$commandSourceNodesToSuggestionNodes$5(CommandContext commandContext) throws CommandSyntaxException {
        return 1;
    }

    private static boolean lambda$commandSourceNodesToSuggestionNodes$4(ISuggestionProvider iSuggestionProvider) {
        return false;
    }

    private static Style lambda$handleCommand$3(IFormattableTextComponent iFormattableTextComponent, Style style) {
        return style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, iFormattableTextComponent));
    }

    private static Style lambda$handleCommand$2(String string, Style style) {
        return style.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, string));
    }

    private static void lambda$new$1(CommandContext commandContext, boolean bl, int n) {
        ((CommandSource)commandContext.getSource()).onCommandComplete(commandContext, bl, n);
    }

    private void lambda$new$0(CommandNode commandNode, CommandNode commandNode2, CommandNode commandNode3, Collection collection) {
        LOGGER.warn("Ambiguity between arguments {} and {} with inputs: {}", (Object)this.dispatcher.getPath(commandNode2), (Object)this.dispatcher.getPath(commandNode3), (Object)collection);
    }

    public static enum EnvironmentType {
        ALL(true, true),
        DEDICATED(false, true),
        INTEGRATED(true, false);

        private final boolean field_237219_d_;
        private final boolean field_237220_e_;

        private EnvironmentType(boolean bl, boolean bl2) {
            this.field_237219_d_ = bl;
            this.field_237220_e_ = bl2;
        }
    }

    @FunctionalInterface
    public static interface IParser {
        public void parse(StringReader var1) throws CommandSyntaxException;
    }
}

