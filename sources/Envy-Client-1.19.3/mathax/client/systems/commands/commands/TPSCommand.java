package mathax.client.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.systems.commands.Command;
import mathax.client.utils.world.TickRate;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class TPSCommand extends Command {
    public TPSCommand() {
        super("tps", "Prints server TPS.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            float tps = TickRate.INSTANCE.getTickRate();
            Formatting color;
            if (tps > 16.0f) color = Formatting.GREEN;
            else if (tps > 12.0f) color = Formatting.YELLOW;
            else color = Formatting.RED;
            info("Current TPS: %s%.2f(default).", color, tps);
            return SINGLE_SUCCESS;
        });
    }
}
