package wtf.diablo.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import org.lwjgl.input.Keyboard;
import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.module.api.management.IModule;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

@Command(
        name = "bind",
        description = "Bind a module",
        aliases = {""},
        usage = "bind <module> <key>"
)
public final class BindCommand extends AbstractCommand {

    @Override
    public void execute(String[] args) {
        if (args.length == 2) {
            System.out.println(args[0]);
            final String message = args[0].replaceAll("_", " ");

            final IModule module = Diablo.getInstance().getModuleRepository().getModuleInstance(message);

            if (module != null)
            {
                module.setKey(Keyboard.getKeyIndex(args[1].toUpperCase()));
                ChatUtil.addChatMessage("Bound " + module.getName() + " to " + args[1].toUpperCase());
            }
            else
            {
                ChatUtil.addChatMessage("Invalid Module!");
            }
        }
        else
        {
            ChatUtil.addChatMessage(ChatFormatting.RED + ".bind <module> <key>");
        }
    }
}
