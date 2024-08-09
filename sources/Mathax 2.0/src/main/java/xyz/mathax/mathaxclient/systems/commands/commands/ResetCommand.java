package xyz.mathax.mathaxclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.commands.Command;
import xyz.mathax.mathaxclient.systems.commands.arguments.ModuleArgumentType;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.utils.text.ChatUtils;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ResetCommand extends Command {
    public ResetCommand() {
        super("Reset", "Resets specified settings.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("settings").then(argument("module", ModuleArgumentType.create()).executes(context -> {
                    Module module = context.getArgument("module", Module.class);

                    module.settings.forEach(group -> group.forEach(Setting::reset));

                    module.info("All settings reset.");

                    return SINGLE_SUCCESS;
                })).then(literal("all").executes(context -> {
                    Modules.get().getAll().forEach(module -> module.settings.forEach(group -> group.forEach(Setting::reset)));

                    info("All module settings reset.");

                    return SINGLE_SUCCESS;
                }))
        ).then(literal("gui").executes(context -> {
            Systems.get(Themes.class).getTheme().clearWindowConfigs();

            info("GUI positioning has been reset.");

            return SINGLE_SUCCESS;
        }).then(literal("scale").executes(context -> {
            Systems.get(Themes.class).getTheme().resetScale();

            info("GUI scale has been reset.");

            return SINGLE_SUCCESS;
        }))).then(literal("bind").then(argument("module", ModuleArgumentType.create()).executes(context -> {
                    Module module = context.getArgument("module", Module.class);
                    module.keybind.set(true, -1);

                    module.info("Bind reset.");

                    return SINGLE_SUCCESS;
                })).then(literal("all").executes(context -> {
                    Modules.get().getAll().forEach(module -> module.keybind.set(true, -1));

                    info("All binds reset.");

                    return SINGLE_SUCCESS;
                }))
        ).then(literal("hud").executes(context -> {
            Systems.get(Hud.class).reset.run();

            ChatUtils.info("HUD", "HUD reset.");

            return SINGLE_SUCCESS;
        }));
    }
}
