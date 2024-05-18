/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dietrichpaul.clientbase.feature.command.list.*;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class CommandList {
    public final static char COMMAND_PREFIX = '#';

    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();
    private final Set<Command> commands = new TreeSet<>();

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public void registerBuiltInCommands() {
        register(new AboutCommand());
        register(new BindCommand());
        register(new FriendCommand());
        register(new HelpCommand());
        register(new PropertyCommand());
        register(new SayCommand());
        register(new ToggleCommand());
    }

    public void process(String message) {
        StringReader reader = new StringReader(message);
        if (reader.peek() == COMMAND_PREFIX) {
            reader.skip();
        }
        ClientCommandSource src = Objects.requireNonNull(mc.getNetworkHandler()).getCommandSource();
        ParseResults<CommandSource> parse = dispatcher.parse(reader, src);
        try {
            dispatcher.execute(parse);
        } catch (CommandSyntaxException e) {
            // &r -> &c (error color)
            ChatUtil.sendChatMessage(Text.literal("").append(Texts.toText(e.getRawMessage())).formatted(Formatting.RED));

            if (e.getCursor() >= 0) {
                int pos = Math.min(e.getInput().length(), e.getCursor());
                MutableText verbose = Text.empty().formatted(Formatting.GRAY).styled(style
                        -> style.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, message)));
                if (pos > 10) {
                    verbose.append(ScreenTexts.ELLIPSIS);
                }
                verbose.append(e.getInput().substring(Math.max(0, pos - 10), pos));
                if (pos < e.getInput().length()) {
                    Text text = Text.literal(e.getInput().substring(pos)).formatted(Formatting.RED, Formatting.UNDERLINE);
                    verbose.append(text);
                }
                verbose.append(Text.translatable("command.context.here").formatted(Formatting.RED, Formatting.ITALIC));
                ChatUtil.sendChatMessage(verbose);
            }
        }
    }

    public void register(Command command) {
        command.register(dispatcher);
        commands.add(command);
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        return dispatcher;
    }

    public Set<Command> getCommands() {
        return commands;
    }
}
