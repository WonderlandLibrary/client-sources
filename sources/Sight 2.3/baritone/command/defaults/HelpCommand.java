/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.command.defaults;

import static baritone.api.command.IBaritoneChatControl.FORCE_COMMAND_PREFIX;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.ICommand;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandException;
import baritone.api.command.exception.CommandNotFoundException;
import baritone.api.command.helpers.Paginator;
import baritone.api.command.helpers.TabCompleteHelper;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class HelpCommand extends Command {

    public HelpCommand(IBaritone baritone) {
        super(baritone, "help", "?");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        args.requireMax(1);
        if (!args.hasAny() || args.is(Integer.class)) {
            Paginator.paginate(
                    args, new Paginator<>(
                            this.baritone.getCommandManager().getRegistry().descendingStream()
                                    .filter(command -> !command.hiddenFromHelp())
                                    .collect(Collectors.toList())
                    ),
                    () -> logDirect("All Baritone commands (clickable):"),
                    command -> {
                        String names = String.join("/", command.getNames());
                        String name = command.getNames().get(0);
                        IChatComponent shortDescComponent = new ChatComponentText(" - " + command.getShortDesc());
                        shortDescComponent.getChatStyle().setColor(EnumChatFormatting.DARK_GRAY);
                        IChatComponent namesComponent = new ChatComponentText(names);
                        namesComponent.getChatStyle().setColor(EnumChatFormatting.WHITE);
                        IChatComponent hoverComponent = new ChatComponentText("");
                        hoverComponent.getChatStyle().setColor(EnumChatFormatting.GRAY);
                        hoverComponent.appendSibling(namesComponent);
                        hoverComponent.appendText("\n" + command.getShortDesc());
                        hoverComponent.appendText("\n\nClick to view full help");
                        String clickCommand = FORCE_COMMAND_PREFIX + String.format("%s %s", label, command.getNames().get(0));
                        IChatComponent component = new ChatComponentText(name);
                        component.getChatStyle().setColor(EnumChatFormatting.GRAY);
                        component.appendSibling(shortDescComponent);
                        component.getChatStyle()
                                .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent))
                                .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickCommand));
                        return component;
                    },
                    FORCE_COMMAND_PREFIX + label
            );
        } else {
            String commandName = args.getString().toLowerCase();
            ICommand command = this.baritone.getCommandManager().getCommand(commandName);
            if (command == null) {
                throw new CommandNotFoundException(commandName);
            }
            logDirect(String.format("%s - %s", String.join(" / ", command.getNames()), command.getShortDesc()));
            logDirect("");
            command.getLongDesc().forEach(this::logDirect);
            logDirect("");
            IChatComponent returnComponent = new ChatComponentText("Click to return to the help menu");
            returnComponent.getChatStyle().setChatClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    FORCE_COMMAND_PREFIX + label
            ));
            logDirect(returnComponent);
        }
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) throws CommandException {
        if (args.hasExactlyOne()) {
            return new TabCompleteHelper()
                    .addCommands(this.baritone.getCommandManager())
                    .filterPrefix(args.getString())
                    .stream();
        }
        return Stream.empty();
    }

    @Override
    public String getShortDesc() {
        return "View all commands or help on specific ones";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Using this command, you can view detailed help information on how to use certain commands of Baritone.",
                "",
                "Usage:",
                "> help - Lists all commands and their short descriptions.",
                "> help <command> - Displays help information on a specific command."
        );
    }
}
