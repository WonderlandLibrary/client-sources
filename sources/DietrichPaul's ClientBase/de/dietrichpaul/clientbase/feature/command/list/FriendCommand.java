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

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.feature.command.argument.FriendArgumentType;
import de.dietrichpaul.clientbase.feature.command.suggestor.PlayerSuggestor;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class FriendCommand extends Command {

    private final static int friendsPerPage = 5;

    public FriendCommand() {
        super("friend");
    }

    private int add(CommandContext<CommandSource> ctx) {
        String friend = StringArgumentType.getString(ctx, "name");
        ClientBase.INSTANCE.getFriendList().add(friend);
        ChatUtil.sendI18n("command.friend.add", friend);
        return 1;
    }

    private int remove(CommandContext<CommandSource> ctx) {
        String friend = FriendArgumentType.getFriend(ctx, "name");
        ClientBase.INSTANCE.getFriendList().remove(friend);
        ChatUtil.sendI18n("command.friend.remove", friend);
        return 1;
    }

    private int list() {
        ChatUtil.sendI18n("command.friend.list");
        for (String friend : ClientBase.INSTANCE.getFriendList().getFriends()) {
            ChatUtil.sendI18n("bullet_point", friend);
        }
        return 1;
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root
                .then(literal("list")
                        .executes(ctx -> list()))
                .then(literal("add")
                        .then(argument("name", StringArgumentType.word())
                                .suggests(new PlayerSuggestor())
                                .executes(this::add)))
                .then(literal("remove")
                        .then(argument("name", FriendArgumentType.friend())
                                .executes(this::remove)))
        ;
    }
}
