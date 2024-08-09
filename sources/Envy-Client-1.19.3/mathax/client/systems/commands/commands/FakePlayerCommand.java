package mathax.client.systems.commands.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.systems.commands.Command;
import mathax.client.systems.modules.Modules;
import mathax.client.utils.entity.fakeplayer.FakePlayerManager;
import mathax.client.systems.modules.client.FakePlayer;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class FakePlayerCommand extends Command {
    public FakePlayerCommand(){
        super("fake-player", "Manages fake players that you can use for testing.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("spawn").executes(context -> {
            if (active()) FakePlayerManager.add("Envy", 36, true);
            return SINGLE_SUCCESS;
        })
                 .then(argument("name", StringArgumentType.word())
                         .executes(context -> {
                    if (active()) FakePlayerManager.add(StringArgumentType.getString(context, "name"), 36, true);
                    return SINGLE_SUCCESS;
                })
                .then(argument("health", FloatArgumentType.floatArg(0))
                        .executes(context -> {
                    if (active()) FakePlayerManager.add(StringArgumentType.getString(context, "name"), FloatArgumentType.getFloat(context, "health"), true);
                    return SINGLE_SUCCESS;
                })
                .then(argument("copy-inv", BoolArgumentType.bool())
                        .executes(context -> {
                            if (active()) FakePlayerManager.add(StringArgumentType.getString(context, "name"), FloatArgumentType.getFloat(context, "health"), BoolArgumentType.getBool(context, "copy-inv"));
                            return SINGLE_SUCCESS;
                        })
                )))
        );

        builder.then(literal("clear").executes(context -> {
            if (active()) FakePlayerManager.clear();
            return SINGLE_SUCCESS;
        }));
    }

    private boolean active() {
        if (!Modules.get().isActive(FakePlayer.class)) {
            error("The FakePlayer module must be enabled.");
            return false;
        } else return true;
    }
}
