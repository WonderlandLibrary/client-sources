package mathax.client.systems.commands.commands;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.systems.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class BaritoneCommand extends Command {
    public BaritoneCommand() {
        super("baritone", "Executes baritone commands.", "b");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("command", StringArgumentType.greedyString())
            .executes(context -> {
                String command = context.getArgument("command", String.class);
                BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute(command);
                return SINGLE_SUCCESS;
            })
        );
    }
}
