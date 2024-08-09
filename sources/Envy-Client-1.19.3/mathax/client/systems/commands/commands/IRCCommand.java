package mathax.client.systems.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.systems.commands.Command;
import mathax.client.utils.irc.Client;
import net.minecraft.command.CommandSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class IRCCommand extends Command {
    public IRCCommand() {
        super("irc", "Connects to the IRC server.", "irc");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("connect").executes(context -> {
            try {
                Client.connect();
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
            return SINGLE_SUCCESS;
        }));
        builder.then(literal("disconnect").executes(context -> {
            Client.disconnect();
            return SINGLE_SUCCESS;
        }));
        builder.then(literal("auth")
            .then(argument("username", string())
                .then(argument("password", string())
                    .executes(context -> {
                        Client.setAuth(getString(context, "username"), getString(context, "password"));
                        return SINGLE_SUCCESS;
        }))).then(literal("clear").executes(context -> {
            Client.setAuth("", "");
            return SINGLE_SUCCESS;
        })));
        builder.then(literal("send")
            .then(argument("message", StringArgumentType.greedyString())
                .executes(context -> {
                    try {
                        Client.send(context.getArgument("message", String.class));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return SINGLE_SUCCESS;
        })));
        builder.then(literal("sendDirect")
            .then(argument("user", string())
                .then(argument("message", StringArgumentType.greedyString())
                    .executes(context -> {
                        try {
                            Client.sendDirect(getString(context, "user"), context.getArgument("message", String.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return SINGLE_SUCCESS;
        }))));
    }
}
