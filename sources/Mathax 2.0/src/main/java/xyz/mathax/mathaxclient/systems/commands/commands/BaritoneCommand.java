package xyz.mathax.mathaxclient.systems.commands.commands;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import xyz.mathax.mathaxclient.systems.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class BaritoneCommand extends Command {
    public BaritoneCommand() {
        super("Baritone", "Executes baritone commands.", "b");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
                    BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("help");

                    return SINGLE_SUCCESS;
                }).then(argument("command", StringArgumentType.greedyString()).executes(context -> {
                            String command = context.getArgument("command", String.class);
                            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute(command);

                            return SINGLE_SUCCESS;
                        })
                );
    }
}
