package gq.vapu.czfclient.Command.Commands;

import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Util.Helper;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

import static gq.vapu.czfclient.Util.Helper.mc;

public class Name extends Command {
    public Name() {
        super("name", new String[]{"n","copyname"}, "", "n");
    }

    @Override
    public String execute(String[] args) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(mc.session.getUsername()), null);
        Helper.sendMessage("Copied!");
        return null;
    }
}
