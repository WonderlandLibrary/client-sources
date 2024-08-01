package wtf.diablo.client.command.impl;

import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.module.api.management.repository.ModuleRepository;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

@Command(name = "panic", description = "Disables all modules instantly", aliases = {"p"})
public final class PanicCommand extends AbstractCommand {

    @Override
    public void execute(String[] args) {
        final ModuleRepository moduleRepository = Diablo.getInstance().getModuleRepository();

        moduleRepository.getModules().forEach(module -> module.toggle(false));

        ChatUtil.addChatMessage("All modules have been disabled.");
    }
}
