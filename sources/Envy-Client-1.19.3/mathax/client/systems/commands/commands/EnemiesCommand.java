package mathax.client.systems.commands.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import mathax.client.systems.commands.Command;
import mathax.client.systems.config.Config;
import mathax.client.systems.enemies.Enemies;
import mathax.client.systems.enemies.Enemy;
import mathax.client.utils.render.ToastSystem;
import net.minecraft.command.CommandSource;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static net.minecraft.command.CommandSource.suggestMatching;

public class EnemiesCommand extends Command {
    public EnemiesCommand() {
        super("enemies", "Manages enemies.", "enemy");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {

        builder.then(literal("add").then(argument("enemy", EnemyArgumentType.enemy())
                        .executes(context -> {
                            Enemy enemy = EnemyArgumentType.getEnemy(context, "enemy");

                            if (Enemies.get().add(enemy)) {
                                if (enemy.name.equals(mc.getSession().getUsername())) return SINGLE_SUCCESS;
                                if (Config.get().chatFeedback.get()) info("Added (highlight)%s(default) to enemies.", enemy.name);
                                if (Config.get().toastFeedback.get()) mc.getToastManager().add(new ToastSystem(Items.REDSTONE_BLOCK, Enemies.get().color.getPacked(), "Enemies " + Formatting.GRAY + "[" + Formatting.WHITE + enemy.name + Formatting.GRAY + "]", null, Formatting.GRAY + "Added to enemies.", Config.get().toastDuration.get()));
                            } else {
                                if (enemy.name.equals(mc.getSession().getUsername())) return SINGLE_SUCCESS;
                                if (Config.get().chatFeedback.get()) error("(highlight)%s(default) is already your enemy.", enemy.name);
                                if (Config.get().toastFeedback.get()) mc.getToastManager().add(new ToastSystem(Items.REDSTONE_BLOCK, Enemies.get().color.getPacked(), "Enemies " + Formatting.GRAY + "[" + Formatting.WHITE + enemy.name + Formatting.GRAY + "]", null, Formatting.RED + "Already your enemy.", Config.get().toastDuration.get()));
                            }

                            return SINGLE_SUCCESS;
                        })
                )
        );

        builder.then(literal("remove").then(argument("enemy", EnemyArgumentType.enemy())
                        .executes(context -> {
                            Enemy enemy = EnemyArgumentType.getEnemy(context, "enemy");

                            if (Enemies.get().remove(enemy)) {
                                if (enemy.name.equals(mc.getSession().getUsername())) return SINGLE_SUCCESS;
                                if (Config.get().chatFeedback.get()) info("Removed (highlight)%s(default) from enemies.", enemy.name);
                                if (Config.get().toastFeedback.get()) mc.getToastManager().add(new ToastSystem(Items.REDSTONE_BLOCK, Enemies.get().color.getPacked(), "Enemies " + Formatting.GRAY + "[" + Formatting.WHITE + enemy.name + Formatting.GRAY + "]", null, Formatting.GRAY + "Removed from enemies.", Config.get().toastDuration.get()));
                            } else {
                                if (enemy.name.equals(mc.getSession().getUsername())) return SINGLE_SUCCESS;
                                if (Config.get().chatFeedback.get()) error("(highlight)%s(default) is not your enemy.", enemy.name);
                                if (Config.get().toastFeedback.get()) mc.getToastManager().add(new ToastSystem(Items.REDSTONE_BLOCK, Enemies.get().color.getPacked(), "Enemies " + Formatting.GRAY + "[" + Formatting.WHITE + enemy.name + Formatting.GRAY + "]", null, Formatting.RED + "Not your enemy.", Config.get().toastDuration.get()));
                            }

                            return SINGLE_SUCCESS;
                        })
                )
        );

        builder.then(literal("list").executes(context -> {
                    info("--- Enemies ((highlight)%s(default)) ---", Enemies.get().count());
                    Enemies.get().forEach(enemy-> info("(highlight)" + enemy.name));
                    return SINGLE_SUCCESS;
                })
        );
    }

    private static class EnemyArgumentType implements ArgumentType<Enemy> {

        public static EnemyArgumentType enemy() {
            return new EnemyArgumentType();
        }

        @Override
        public Enemy parse(StringReader reader) throws CommandSyntaxException {
            return new Enemy(reader.readString());
        }

        public static Enemy getEnemy(CommandContext<?> context, String name) {
            return context.getArgument(name, Enemy.class);
        }

        @Override
        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
            return suggestMatching(mc.getNetworkHandler().getPlayerList().stream().map(entry -> entry.getProfile().getName()).collect(Collectors.toList()), builder);
        }

        @Override
        public Collection<String> getExamples() {
            return List.of("Matejko06");
        }
    }
}
