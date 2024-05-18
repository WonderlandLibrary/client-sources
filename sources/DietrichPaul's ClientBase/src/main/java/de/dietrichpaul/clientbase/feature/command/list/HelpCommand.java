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

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class HelpCommand extends Command {
    private final static int commandsPerPage = 5;

    public HelpCommand() {
        super("help");
    }

    private int list(CommandContext<CommandSource> ctx, int page) {
        CommandDispatcher<CommandSource> dispatcher = ClientBase.INSTANCE.getCommandList().getDispatcher();
        String[] commands = dispatcher.getSmartUsage(dispatcher.getRoot(), ctx.getSource()).values().toArray(new String[0]);
        int pages = MathHelper.ceil(commands.length / (double) commandsPerPage);


        if (page <= 0 || page > pages) {
            ChatUtil.sendI18n("command.help.page.missing", page);
            return 1;
        }

        int pageStart = (page - 1) * commandsPerPage;
        ChatUtil.sendI18n("command.help.usages");
        for (int i = pageStart; i < Math.min(pageStart + commandsPerPage, commands.length); i++) {
            String command = commands[i];
            ChatUtil.sendI18n("bullet_point", command);
        }
        ChatUtil.sendI18n("command.help.page", page, pages);
        return 1;
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.executes(ctx -> list(ctx, 1))
                .then(
                        argument("page", IntegerArgumentType.integer())
                                .executes(ctx -> list(ctx, IntegerArgumentType.getInteger(ctx, "page")))
                );
    }
}
