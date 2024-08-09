package net.minecraft.command;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.impl.*;
import net.minecraft.command.impl.data.DataCommand;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SCommandListPacket;
import net.minecraft.test.TestCommand;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Commands
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

    public Commands(Commands.EnvironmentType envType)
    {
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
        SeedCommand.register(this.dispatcher, envType != Commands.EnvironmentType.INTEGRATED);
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

        if (SharedConstants.developmentMode)
        {
            TestCommand.register(this.dispatcher);
        }

        if (envType.field_237220_e_)
        {
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

        if (envType.field_237219_d_)
        {
            PublishCommand.register(this.dispatcher);
        }

        this.dispatcher.findAmbiguities((p_201302_1_, p_201302_2_, p_201302_3_, p_201302_4_) ->
        {
            LOGGER.warn("Ambiguity between arguments {} and {} with inputs: {}", this.dispatcher.getPath(p_201302_2_), this.dispatcher.getPath(p_201302_3_), p_201302_4_);
        });
        this.dispatcher.setConsumer((p_197058_0_, p_197058_1_, p_197058_2_) ->
        {
            p_197058_0_.getSource().onCommandComplete(p_197058_0_, p_197058_1_, p_197058_2_);
        });
    }

    /**
     * Runs a command.
     *  
     * @return The success value of the command, or 0 if an exception occured.
     */
    public int handleCommand(CommandSource source, String command)
    {
        StringReader stringreader = new StringReader(command);

        if (stringreader.canRead() && stringreader.peek() == '/')
        {
            stringreader.skip();
        }

        source.getServer().getProfiler().startSection(command);

        try
        {
            try
            {
                return this.dispatcher.execute(stringreader, source);
            }
            catch (CommandException commandexception)
            {
                source.sendErrorMessage(commandexception.getComponent());
                return 0;
            }
            catch (CommandSyntaxException commandsyntaxexception)
            {
                source.sendErrorMessage(TextComponentUtils.toTextComponent(commandsyntaxexception.getRawMessage()));

                if (commandsyntaxexception.getInput() != null && commandsyntaxexception.getCursor() >= 0)
                {
                    int j = Math.min(commandsyntaxexception.getInput().length(), commandsyntaxexception.getCursor());
                    IFormattableTextComponent iformattabletextcomponent1 = (new StringTextComponent("")).mergeStyle(TextFormatting.GRAY).modifyStyle((p_211705_1_) ->
                    {
                        return p_211705_1_.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
                    });

                    if (j > 10)
                    {
                        iformattabletextcomponent1.appendString("...");
                    }

                    iformattabletextcomponent1.appendString(commandsyntaxexception.getInput().substring(Math.max(0, j - 10), j));

                    if (j < commandsyntaxexception.getInput().length())
                    {
                        ITextComponent itextcomponent = (new StringTextComponent(commandsyntaxexception.getInput().substring(j))).mergeStyle(new TextFormatting[] {TextFormatting.RED, TextFormatting.UNDERLINE});
                        iformattabletextcomponent1.append(itextcomponent);
                    }

                    iformattabletextcomponent1.append((new TranslationTextComponent("command.context.here")).mergeStyle(new TextFormatting[] {TextFormatting.RED, TextFormatting.ITALIC}));
                    source.sendErrorMessage(iformattabletextcomponent1);
                }
            }
            catch (Exception exception)
            {
                IFormattableTextComponent iformattabletextcomponent = new StringTextComponent(exception.getMessage() == null ? exception.getClass().getName() : exception.getMessage());

                if (LOGGER.isDebugEnabled())
                {
                    LOGGER.error("Command exception: {}", command, exception);
                    StackTraceElement[] astacktraceelement = exception.getStackTrace();

                    for (int i = 0; i < Math.min(astacktraceelement.length, 3); ++i)
                    {
                        iformattabletextcomponent.appendString("\n\n").appendString(astacktraceelement[i].getMethodName()).appendString("\n ").appendString(astacktraceelement[i].getFileName()).appendString(":").appendString(String.valueOf(astacktraceelement[i].getLineNumber()));
                    }
                }

                source.sendErrorMessage((new TranslationTextComponent("command.failed")).modifyStyle((p_211704_1_) ->
                {
                    return p_211704_1_.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, iformattabletextcomponent));
                }));

                if (SharedConstants.developmentMode)
                {
                    source.sendErrorMessage(new StringTextComponent(Util.getMessage(exception)));
                    LOGGER.error("'" + command + "' threw an exception", (Throwable)exception);
                }

                return 0;
            }

            return 0;
        }
        finally
        {
            source.getServer().getProfiler().endSection();
        }
    }

    public void send(ServerPlayerEntity player)
    {
        Map<CommandNode<CommandSource>, CommandNode<ISuggestionProvider>> map = Maps.newHashMap();
        RootCommandNode<ISuggestionProvider> rootcommandnode = new RootCommandNode<>();
        map.put(this.dispatcher.getRoot(), rootcommandnode);
        this.commandSourceNodesToSuggestionNodes(this.dispatcher.getRoot(), rootcommandnode, player.getCommandSource(), map);
        player.connection.sendPacket(new SCommandListPacket(rootcommandnode));
    }

    private void commandSourceNodesToSuggestionNodes(CommandNode<CommandSource> rootCommandSource, CommandNode<ISuggestionProvider> rootSuggestion, CommandSource source, Map<CommandNode<CommandSource>, CommandNode<ISuggestionProvider>> commandNodeToSuggestionNode)
    {
        for (CommandNode<CommandSource> commandnode : rootCommandSource.getChildren())
        {
            if (commandnode.canUse(source))
            {
                ArgumentBuilder < ISuggestionProvider, ? > argumentbuilder = (ArgumentBuilder) commandnode.createBuilder();
                argumentbuilder.requires((p_197060_0_) ->
                {
                    return true;
                });

                if (argumentbuilder.getCommand() != null)
                {
                    argumentbuilder.executes((p_197053_0_) ->
                    {
                        return 0;
                    });
                }

                if (argumentbuilder instanceof RequiredArgumentBuilder)
                {
                    RequiredArgumentBuilder < ISuggestionProvider, ? > requiredargumentbuilder = (RequiredArgumentBuilder)argumentbuilder;

                    if (requiredargumentbuilder.getSuggestionsProvider() != null)
                    {
                        requiredargumentbuilder.suggests(SuggestionProviders.ensureKnown(requiredargumentbuilder.getSuggestionsProvider()));
                    }
                }

                if (argumentbuilder.getRedirect() != null)
                {
                    argumentbuilder.redirect(commandNodeToSuggestionNode.get(argumentbuilder.getRedirect()));
                }

                CommandNode<ISuggestionProvider> commandnode1 = argumentbuilder.build();
                commandNodeToSuggestionNode.put(commandnode, commandnode1);
                rootSuggestion.addChild(commandnode1);

                if (!commandnode.getChildren().isEmpty())
                {
                    this.commandSourceNodesToSuggestionNodes(commandnode, commandnode1, source, commandNodeToSuggestionNode);
                }
            }
        }
    }

    public static LiteralArgumentBuilder<CommandSource> literal(String name)
    {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <T> RequiredArgumentBuilder<CommandSource, T> argument(String name, ArgumentType<T> type)
    {
        return RequiredArgumentBuilder.argument(name, type);
    }

    public static Predicate<String> predicate(Commands.IParser parser)
    {
        return (p_212591_1_) ->
        {
            try {
                parser.parse(new StringReader(p_212591_1_));
                return true;
            }
            catch (CommandSyntaxException commandsyntaxexception)
            {
                return false;
            }
        };
    }

    public CommandDispatcher<CommandSource> getDispatcher()
    {
        return this.dispatcher;
    }

    @Nullable
    public static <S> CommandSyntaxException func_227481_a_(ParseResults<S> p_227481_0_)
    {
        if (!p_227481_0_.getReader().canRead())
        {
            return null;
        }
        else if (p_227481_0_.getExceptions().size() == 1)
        {
            return p_227481_0_.getExceptions().values().iterator().next();
        }
        else
        {
            return p_227481_0_.getContext().getRange().isEmpty() ? CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(p_227481_0_.getReader()) : CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(p_227481_0_.getReader());
        }
    }

    public static void func_242986_b()
    {
        RootCommandNode<CommandSource> rootcommandnode = (new Commands(Commands.EnvironmentType.ALL)).getDispatcher().getRoot();
        Set < ArgumentType<? >> set = ArgumentTypes.func_243511_a(rootcommandnode);
        Set < ArgumentType<? >> set1 = set.stream().filter((p_242987_0_) ->
        {
            return !ArgumentTypes.func_243510_a(p_242987_0_);
        }).collect(Collectors.toSet());

        if (!set1.isEmpty())
        {
            LOGGER.warn("Missing type registration for following arguments:\n {}", set1.stream().map((p_242985_0_) ->
            {
                return "\t" + p_242985_0_;
            }).collect(Collectors.joining(",\n")));
            throw new IllegalStateException("Unregistered argument types");
        }
    }

    public static enum EnvironmentType
    {
        ALL(true, true),
        DEDICATED(false, true),
        INTEGRATED(true, false);

        private final boolean field_237219_d_;
        private final boolean field_237220_e_;

        private EnvironmentType(boolean p_i232149_3_, boolean p_i232149_4_)
        {
            this.field_237219_d_ = p_i232149_3_;
            this.field_237220_e_ = p_i232149_4_;
        }
    }

    @FunctionalInterface
    public interface IParser
    {
        void parse(StringReader p_parse_1_) throws CommandSyntaxException;
    }
}
