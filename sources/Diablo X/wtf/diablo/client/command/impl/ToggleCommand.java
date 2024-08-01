package wtf.diablo.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.module.api.management.IModule;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

@Command(
        name = "toggle",
        description = "Toggle a module",
        aliases = {"t"},
        usage = "toggle <module>"
)
public final class ToggleCommand extends AbstractCommand {

    @Override
    public void execute(final String[] args) {
        final String message = args[0].replaceAll("_", " ");

        if (args.length < 1) {
            ChatUtil.addChatMessage("Usage: " + ChatFormatting.GRAY + ".toggle <module>");
            return;
        }

        final IModule module = Diablo.getInstance().getModuleRepository().getModuleByName(message);
        if (module != null) {
            module.toggle();
            ChatUtil.addChatMessage("Toggled " + ChatFormatting.GRAY + module.getName() + ChatFormatting.WHITE + " to " + (module.isEnabled() ? ChatFormatting.GREEN + "enabled" : ChatFormatting.RED + "disabled") + ChatFormatting.WHITE + "!");
            return;
        }

        ChatUtil.addChatMessage(ChatFormatting.DARK_RED + "Module not found!");
    }
}
