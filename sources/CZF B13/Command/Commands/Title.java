package gq.vapu.czfclient.Command.Commands;

import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Util.Helper;
import org.lwjgl.opengl.Display;

public class Title extends Command {
    public Title() {
        super("title", new String[]{"ti", "t"}, "title", "set a title");
    }

    @Override
    public String execute(String[] args) {
        String title = args[1];
        Display.setTitle((String)title);
        Display.setTitle(Display.getTitle());
        Helper.sendMessage("> " + "Client Title was set to " + Display.getTitle());
        return null;
    }
}

