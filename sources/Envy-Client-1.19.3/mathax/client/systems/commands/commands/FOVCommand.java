package mathax.client.systems.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.systems.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class FOVCommand extends Command {
    public FOVCommand() {
        super("fov", "Changes your FOV.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            info("Currently using (highlight)%s(default).", mc.options.getFov().getValue());
            return SINGLE_SUCCESS;
        });

        builder.then(literal("set")
            .then(argument("new-fov", IntegerArgumentType.integer(0))
                .executes(context -> {
                    int newFov = context.getArgument("new-fov", Integer.class);
                    mc.options.getFov().setValue(context.getArgument("new-fov", Integer.class));
                    info("Set to (highlight)%s(default).", newFov);
                    return SINGLE_SUCCESS;
                })
            )
        );
    }
}
