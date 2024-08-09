package dev.excellent.client.command.impl;

import dev.excellent.Excellent;
import dev.excellent.client.command.Command;
import dev.excellent.impl.util.chat.ChatUtil;

public final class ReloadCommand extends Command {

    public ReloadCommand() {
        super("", "reload");
    }

    @Override
    public void execute(final String[] args) {
//        if (DeadCodeConstants.IDE) {
//            Excellent.getInst().init();
//            ChatUtil.addText("Reloaded " + Excellent.getInst().getInfo().getName());
//        } else {
        ChatUtil.addText("Эта команда только для разработчиков " + Excellent.getInst().getInfo().getName());
//        }
    }
}