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
import mathax.client.systems.friends.Friend;
import mathax.client.systems.friends.Friends;
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

public class FriendsCommand extends Command {
    public FriendsCommand() {
        super("friends", "Manages friends.", "friend");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {

        builder.then(literal("add").then(argument("friend", FriendArgumentType.friend())
                        .executes(context -> {
                            Friend friend = FriendArgumentType.getFriend(context, "friend");

                            if (Friends.get().add(friend)) {
                                if (friend.name.equals(mc.getSession().getUsername())) return SINGLE_SUCCESS;
                                if (Config.get().chatFeedback.get()) info("Added (highlight)%s(default) to friends.", friend.name);
                                if (Config.get().toastFeedback.get()) mc.getToastManager().add(new ToastSystem(Items.EMERALD_BLOCK, Friends.get().color.getPacked(), "Friends " + Formatting.GRAY + "[" + Formatting.WHITE + friend.name + Formatting.GRAY + "]", null, Formatting.GRAY + "Added to friends.", Config.get().toastDuration.get()));
                            } else {
                                if (friend.name.equals(mc.getSession().getUsername())) return SINGLE_SUCCESS;
                                if (Config.get().chatFeedback.get()) error("(highlight)%s(default) is already your friend.", friend.name);
                                if (Config.get().toastFeedback.get()) mc.getToastManager().add(new ToastSystem(Items.EMERALD_BLOCK, Friends.get().color.getPacked(), "Friends " + Formatting.GRAY + "[" + Formatting.WHITE + friend.name + Formatting.GRAY + "]", null, Formatting.RED + "Already your friend.", Config.get().toastDuration.get()));
                            }

                            return SINGLE_SUCCESS;
                        })
                )
        );

        builder.then(literal("remove").then(argument("friend", FriendArgumentType.friend())
                        .executes(context -> {
                            Friend friend = FriendArgumentType.getFriend(context, "friend");

                            if (Friends.get().remove(friend)) {
                                if (friend.name.equals(mc.getSession().getUsername())) return SINGLE_SUCCESS;
                                if (Config.get().chatFeedback.get()) info("Removed (highlight)%s(default) from friends.", friend.name);
                                if (Config.get().toastFeedback.get()) mc.getToastManager().add(new ToastSystem(Items.EMERALD_BLOCK, Friends.get().color.getPacked(), "Friends " + Formatting.GRAY + "[" + Formatting.WHITE + friend.name + Formatting.GRAY + "]", null, Formatting.GRAY + "Removed from friends.", Config.get().toastDuration.get()));
                            } else {
                                if (friend.name.equals(mc.getSession().getUsername())) return SINGLE_SUCCESS;
                                if (Config.get().chatFeedback.get()) error("(highlight)%s(default) is not your friend.", friend.name);
                                if (Config.get().toastFeedback.get()) mc.getToastManager().add(new ToastSystem(Items.EMERALD_BLOCK, Friends.get().color.getPacked(), "Friends " + Formatting.GRAY + "[" + Formatting.WHITE + friend.name + Formatting.GRAY + "]", null, Formatting.RED + "Not your friend.", Config.get().toastDuration.get()));
                            }

                            return SINGLE_SUCCESS;
                        })
                )
        );

        builder.then(literal("list").executes(context -> {
                    info("--- Friends ((highlight)%s(default)) ---", Friends.get().count());
                    Friends.get().forEach(friend-> info("(highlight)" + friend.name));
                    return SINGLE_SUCCESS;
                })
        );
    }

    private static class FriendArgumentType implements ArgumentType<Friend> {

        public static FriendArgumentType friend() {
            return new FriendArgumentType();
        }

        @Override
        public Friend parse(StringReader reader) throws CommandSyntaxException {
            return new Friend(reader.readString());
        }

        public static Friend getFriend(CommandContext<?> context, String name) {
            return context.getArgument(name, Friend.class);
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
