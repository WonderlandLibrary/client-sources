package mathax.client.systems.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.systems.commands.Command;
import mathax.client.systems.config.Config;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("prefix", "Lets you customize the command prefix.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            info("The command prefix is currently (highlight)%s%s%s(default)!", Formatting.BOLD, Config.get().prefix, Formatting.RESET);
            return SINGLE_SUCCESS;
        });

        builder.then(literal("set")
            .then(argument("new-prefix", StringArgumentType.string())
                .executes(context -> {
                    Config.get().prefix.set(StringArgumentType.getString(context, "new-prefix"));
                    info("Command prefix set to (highlight)(bold)%s(reset)(default)!", Config.get().prefix);
                    return SINGLE_SUCCESS;
                })
            )
        );
    }
}
