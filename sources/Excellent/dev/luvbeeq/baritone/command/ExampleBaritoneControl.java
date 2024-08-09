package dev.luvbeeq.baritone.command;

import dev.luvbeeq.baritone.Baritone;
import dev.luvbeeq.baritone.api.BaritoneAPI;
import dev.luvbeeq.baritone.api.Settings;
import dev.luvbeeq.baritone.api.command.argument.ICommandArgument;
import dev.luvbeeq.baritone.api.command.exception.CommandNotEnoughArgumentsException;
import dev.luvbeeq.baritone.api.command.exception.CommandNotFoundException;
import dev.luvbeeq.baritone.api.command.helpers.TabCompleteHelper;
import dev.luvbeeq.baritone.api.command.manager.ICommandManager;
import dev.luvbeeq.baritone.api.event.events.ChatEvent;
import dev.luvbeeq.baritone.api.event.events.TabCompleteEvent;
import dev.luvbeeq.baritone.api.utils.Helper;
import dev.luvbeeq.baritone.api.utils.SettingsUtil;
import dev.luvbeeq.baritone.behavior.Behavior;
import dev.luvbeeq.baritone.command.argument.ArgConsumer;
import dev.luvbeeq.baritone.command.argument.CommandArguments;
import dev.luvbeeq.baritone.command.manager.CommandManager;
import dev.luvbeeq.baritone.utils.accessor.IGuiScreen;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static dev.luvbeeq.baritone.api.command.IBaritoneChatControl.FORCE_COMMAND_PREFIX;

public class ExampleBaritoneControl extends Behavior implements Helper {

    private static final Settings settings = BaritoneAPI.getSettings();
    private final ICommandManager manager;

    public ExampleBaritoneControl(Baritone baritone) {
        super(baritone);
        this.manager = baritone.getCommandManager();
    }

    @Override
    public void onSendChatMessage(ChatEvent event) {
        String msg = event.getMessage();
        String prefix = settings.prefix.value;
        boolean forceRun = msg.startsWith(FORCE_COMMAND_PREFIX);
        if ((settings.prefixControl.value && msg.startsWith(prefix)) || forceRun) {
            event.cancel();
            String commandStr = msg.substring(forceRun ? FORCE_COMMAND_PREFIX.length() : prefix.length());
            if (!runCommand(commandStr) && !commandStr.trim().isEmpty()) {
                new CommandNotFoundException(CommandManager.expand(commandStr).getA()).handle(null, null);
            }
        } else if ((settings.chatControl.value || settings.chatControlAnyway.value) && runCommand(msg)) {
            event.cancel();
        }
    }

    private void logRanCommand(String command, String rest) {
        if (settings.echoCommands.value) {
            String msg = command + rest;
            String toDisplay = settings.censorRanCommands.value ? command + " ..." : msg;
            TextComponent component = new StringTextComponent(String.format("> %s", toDisplay));
            component.setStyle(component.getStyle()
                    .setFormatting(TextFormatting.WHITE)
                    .setHoverEvent(new HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            new StringTextComponent("Click to rerun command")
                    ))
                    .setClickEvent(new ClickEvent(
                            ClickEvent.Action.RUN_COMMAND,
                            FORCE_COMMAND_PREFIX + msg
                    )));
            logDirect(component);
        }
    }

    public boolean runCommand(String msg) {
        if (msg.trim().equalsIgnoreCase("damn")) {
            logDirect("daniel");
            return false;
        } else if (msg.trim().equalsIgnoreCase("orderpizza")) {
            try {
                ((IGuiScreen) ctx.minecraft().currentScreen).openLinkInvoker(new URI("https://www.dominos.com/en/pages/order/"));
            } catch(NullPointerException | URISyntaxException ignored) {
            }
            return false;
        }
        if (msg.isEmpty()) {
            return this.runCommand("help");
        }
        Tuple<String, List<ICommandArgument>> pair = CommandManager.expand(msg);
        String command = pair.getA();
        String rest = msg.substring(pair.getA().length());
        ArgConsumer argc = new ArgConsumer(this.manager, pair.getB());
        if (!argc.hasAny()) {
            Settings.Setting<?> setting = settings.byLowerName.get(command.toLowerCase(Locale.US));
            if (setting != null) {
                logRanCommand(command, rest);
                if (setting.getValueClass() == Boolean.class) {
                    this.manager.execute(String.format("set toggle %s", setting.getName()));
                } else {
                    this.manager.execute(String.format("set %s", setting.getName()));
                }
                return true;
            }
        } else if (argc.hasExactlyOne()) {
            for (Settings.Setting<?> setting : settings.allSettings) {
                if (setting.isJavaOnly()) {
                    continue;
                }
                if (setting.getName().equalsIgnoreCase(pair.getA())) {
                    logRanCommand(command, rest);
                    try {
                        this.manager.execute(String.format("set %s %s", setting.getName(), argc.getString()));
                    } catch(CommandNotEnoughArgumentsException ignored) {
                    } // The operation is safe
                    return true;
                }
            }
        }

        // If the command exists, then handle echoing the input
        if (this.manager.getCommand(pair.getA()) != null) {
            logRanCommand(command, rest);
        }

        return this.manager.execute(pair);
    }

    @Override
    public void onPreTabComplete(TabCompleteEvent event) {
        if (!settings.prefixControl.value) {
            return;
        }
        String prefix = event.prefix;
        String commandPrefix = settings.prefix.value;
        if (!prefix.startsWith(commandPrefix)) {
            return;
        }
        String msg = prefix.substring(commandPrefix.length());
        List<ICommandArgument> args = CommandArguments.from(msg, true);
        Stream<String> stream = tabComplete(msg);
        if (args.size() == 1) {
            stream = stream.map(x -> commandPrefix + x);
        }
        event.completions = stream.toArray(String[]::new);
    }

    public Stream<String> tabComplete(String msg) {
        try {
            List<ICommandArgument> args = CommandArguments.from(msg, true);
            ArgConsumer argc = new ArgConsumer(this.manager, args);
            if (argc.hasAtMost(2)) {
                if (argc.hasExactly(1)) {
                    return new TabCompleteHelper()
                            .addCommands(this.manager)
                            .addSettings()
                            .filterPrefix(argc.getString())
                            .stream();
                }
                Settings.Setting<?> setting = settings.byLowerName.get(argc.getString().toLowerCase(Locale.US));
                if (setting != null && !setting.isJavaOnly()) {
                    if (setting.getValueClass() == Boolean.class) {
                        TabCompleteHelper helper = new TabCompleteHelper();
                        if ((Boolean) setting.value) {
                            helper.append("true", "false");
                        } else {
                            helper.append("false", "true");
                        }
                        return helper.filterPrefix(argc.getString()).stream();
                    } else {
                        return Stream.of(SettingsUtil.settingValueToString(setting));
                    }
                }
            }
            return this.manager.tabComplete(msg);
        } catch(CommandNotEnoughArgumentsException ignored) { // Shouldn't happen, the operation is safe
            return Stream.empty();
        }
    }
}
