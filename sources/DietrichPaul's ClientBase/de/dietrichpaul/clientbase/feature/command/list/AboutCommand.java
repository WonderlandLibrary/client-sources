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

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

public class AboutCommand extends Command {

    public AboutCommand() {
        super("about");
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.executes(ctx -> {
            ChatUtil.sendChatMessage(Text.of(ClientBase.NAME + " v" + ClientBase.VERSION));
            ChatUtil.sendChatMessage(Text.of(ClientBase.METADATA.getDescription()));
            ChatUtil.sendChatMessage(Text.of("Made by " + ClientBase.AUTHORS));
            return 1;
        });
    }
}
