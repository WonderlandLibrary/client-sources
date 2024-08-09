package xyz.mathax.mathaxclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import xyz.mathax.mathaxclient.systems.commands.Command;
import xyz.mathax.mathaxclient.systems.commands.Commands;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.utils.text.ChatUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class CommandsCommand extends Command {
    public CommandsCommand() {
        super("Commands", "List of all commands.", "help");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            info("--- Commands ((highlight)%d(default)) ---", Commands.get().getCount());

            MutableText commands = Text.literal("");
            Commands.get().getAll().forEach(command -> commands.append(getCommandText(command)));

            ChatUtils.sendMessage(commands);

            return SINGLE_SUCCESS;
        });
    }

    private MutableText getCommandText(Command command) {
        MutableText tooltip = Text.literal("");
        tooltip.append(Text.literal(command.getName()).formatted(Formatting.BLUE, Formatting.BOLD)).append("\n");

        MutableText aliases = Text.literal(Config.get().prefixSetting.get() + command.getCommand());
        if (command.getAliases().size() > 0) {
            aliases.append(", ");

            for (String alias : command.getAliases()) {
                if (alias.isEmpty()) {
                    continue;
                }

                aliases.append(Config.get().prefixSetting.get() + alias);

                if (!alias.equals(command.getAliases().get(command.getAliases().size() - 1))) {
                    aliases.append(", ");
                }
            }
        }

        tooltip.append(aliases.formatted(Formatting.GRAY)).append("\n\n");

        tooltip.append(Text.literal(command.getDescription()).formatted(Formatting.WHITE));

        // Text
        MutableText text = Text.literal(command.getName());
        if (command != Commands.get().getAll().get(Commands.get().getAll().size() - 1)) {
            text.append(Text.literal(", ").formatted(Formatting.GRAY));
        }

        text.setStyle(text.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip)).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, Config.get().prefixSetting.get() + command.getCommand())));

        return text;
    }
}
