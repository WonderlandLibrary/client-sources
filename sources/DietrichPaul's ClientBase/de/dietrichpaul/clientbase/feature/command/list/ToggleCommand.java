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
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.feature.command.argument.HackArgumentType;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import net.minecraft.command.CommandSource;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("t");
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(argument("hack", HackArgumentType.hack()).executes(ctx -> {
                    Hack hack = HackArgumentType.getHack(ctx, "hack");
                    hack.toggle();
                    return 1;
        }));
    }
}
