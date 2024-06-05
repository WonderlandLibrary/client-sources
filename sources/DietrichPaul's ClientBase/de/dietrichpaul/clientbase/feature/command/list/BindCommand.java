/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.command.list;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.feature.command.argument.KeyArgumentType;
import de.dietrichpaul.clientbase.feature.command.argument.KeyBindingArgumentType;
import de.dietrichpaul.clientbase.feature.command.suggestor.HackSuggestor;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BindCommand extends Command {

    public BindCommand() {
        super("bind");
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                literal("add").then(
                        argument("key", KeyArgumentType.key())
                                .then(argument("message", StringArgumentType.greedyString())
                                        .suggests(new HackSuggestor())
                                        .executes(this::add)))
        ).then(
                literal("remove").then(
                        argument("key", KeyBindingArgumentType.boundKey())
                                .executes(this::remove)
                )
        ).then(
                literal("list").then(
                        argument("key", KeyBindingArgumentType.boundKey())
                                .executes(this::list)
                )
        );
    }

    private int list(CommandContext<CommandSource> ctx) {
        InputUtil.Key key = KeyArgumentType.getKey(ctx, "key");
        ChatUtil.sendI18n("command.bind.list", key.getLocalizedText());

        for (String binding : ClientBase.INSTANCE.getKeybindingList().getBindings(key)) {
            ChatUtil.sendI18n("bullet_point", binding);
        }
        return 1;
    }

    private int remove(CommandContext<CommandSource> ctx) {
        InputUtil.Key key = KeyArgumentType.getKey(ctx, "key");
        ClientBase.INSTANCE.getKeybindingList().unbind(key);
        ChatUtil.sendI18n("command.bind.remove", key.getLocalizedText());
        return 1;
    }

    private int add(CommandContext<CommandSource> ctx) {
        InputUtil.Key key = KeyArgumentType.getKey(ctx, "key");
        String message = StringArgumentType.getString(ctx, "message");
        ClientBase.INSTANCE.getKeybindingList().bind(key, message);
        ChatUtil.sendI18n("command.bind.add", message, key.getLocalizedText());
        return 1;
    }
}
