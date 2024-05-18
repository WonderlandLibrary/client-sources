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
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.command.CommandSource;

/*
Das kann möglicherweise detected werden: TabCompletion
 */
public class SayCommand extends Command {

    public SayCommand() {
        super("say");
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(argument("message", StringArgumentType.greedyString()).executes(ctx -> {
            ChatUtil.sendChatMessageToServer(StringArgumentType.getString(ctx, "message"));
            return 1;
        }));
    }
}
