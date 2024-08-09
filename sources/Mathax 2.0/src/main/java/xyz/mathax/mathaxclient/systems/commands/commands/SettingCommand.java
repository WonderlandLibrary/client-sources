package xyz.mathax.mathaxclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.commands.Command;
import xyz.mathax.mathaxclient.systems.commands.arguments.ModuleArgumentType;
import xyz.mathax.mathaxclient.systems.commands.arguments.SettingArgumentType;
import xyz.mathax.mathaxclient.systems.commands.arguments.SettingValueArgumentType;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.utils.Utils;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class SettingCommand extends Command {
    public SettingCommand() {
        super("Settings", "Allows you to view and change module settings.", "s");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(
                argument("module", ModuleArgumentType.create()).executes(context -> {
                    Module module = context.getArgument("module", Module.class);
                    WidgetScreen screen = Systems.get(Themes.class).getTheme().moduleScreen(module);
                    screen.parent = null;

                    Utils.screenToOpen = screen;
                    return SINGLE_SUCCESS;
                }).then(argument("setting", SettingArgumentType.create()).executes(context -> {
                    Setting<?> setting = SettingArgumentType.get(context);
                    ModuleArgumentType.get(context).info("Setting (highlight)%s(default) is (highlight)%s(default).", setting.name, setting.get());

                    return SINGLE_SUCCESS;
                }).then(argument("value", SettingValueArgumentType.create()).executes(context -> {
                    Setting<?> setting = SettingArgumentType.get(context);
                    String value = context.getArgument("value", String.class);
                    if (setting.parse(value)) {
                        ModuleArgumentType.get(context).info("Setting (highlight)%s(default) changed to (highlight)%s(default).", setting.name, value);
                    }

                    return SINGLE_SUCCESS;
                })))
        );
    }
}
