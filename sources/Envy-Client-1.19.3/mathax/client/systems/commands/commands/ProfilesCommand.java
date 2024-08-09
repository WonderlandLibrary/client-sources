package mathax.client.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.systems.commands.Command;
import mathax.client.systems.commands.arguments.ProfileArgumentType;
import mathax.client.systems.profiles.Profile;
import mathax.client.systems.profiles.Profiles;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ProfilesCommand extends Command {
    public ProfilesCommand() {
        super("profiles", "Loads and saves profiles.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("load").then(argument("profile", ProfileArgumentType.profile()).executes(context -> {
            Profile profile = ProfileArgumentType.getProfile(context, "profile");

            if (profile != null) {
                profile.load();
                info("Loaded profile (highlight)%s(default).", profile.name);
            }

            return SINGLE_SUCCESS;
        })));

        builder.then(literal("save").then(argument("profile", ProfileArgumentType.profile()).executes(context -> {
            Profile profile = ProfileArgumentType.getProfile(context, "profile");

            if (profile != null) {
                profile.save();
                info("Saved profile (highlight)%s(default).", profile.name);
            }

            return SINGLE_SUCCESS;
        })));

        builder.then(literal("delete").then(argument("profile", ProfileArgumentType.profile()).executes(context -> {
            Profile profile = ProfileArgumentType.getProfile(context, "profile");

            if (profile != null) {
                Profiles.get().remove(profile);
                info("Deleted profile (highlight)%s(default).", profile.name);
            }

            return SINGLE_SUCCESS;
        })));
    }
}
