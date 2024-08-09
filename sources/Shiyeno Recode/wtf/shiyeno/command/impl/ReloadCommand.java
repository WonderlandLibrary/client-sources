package wtf.shiyeno.command.impl;

import wtf.shiyeno.command.Command;
import wtf.shiyeno.command.CommandInfo;
import wtf.shiyeno.managment.Managment;

@CommandInfo(name = "reload", description = "Перезагрузка плагина")
public class ReloadCommand extends Command {
    @Override
    public void run(String[] args) throws Exception {
        Managment.SCRIPT_MANAGER.reload();
        sendMessage("Все скрипты перезагружены.");
    }

    @Override
    public void error() {

    }
}