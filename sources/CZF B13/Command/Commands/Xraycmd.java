package gq.vapu.czfclient.Command.Commands;

import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Modules.Render.Xray;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Math.MathUtil;

import java.util.Arrays;

public class Xraycmd extends Command {
    public Xraycmd() {
        super("xray", new String[]{"oreesp"}, "", "nigga");
    }

    @Override
    public String execute(String[] args) {
        Xray xray = (Xray) ModuleManager.getModuleByClass(Xray.class);
        if (args.length == 2) {
            if (MathUtil.parsable(args[1], (byte) 4)) {
                int id = Integer.parseInt(args[1]);
                if (args[0].equalsIgnoreCase("add")) {
                    xray.blocks.add(id);
                    Helper.sendMessage("Added Block ID " + id);
                } else if (args[0].equalsIgnoreCase("remove")) {
                    xray.blocks.remove(id);
                    Helper.sendMessage("Removed Block ID " + id);
                } else {
                    Helper.sendMessage("Invalid syntax");
                }
            } else {
                Helper.sendMessage("Invalid block ID");
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            Arrays.toString(xray.blocks.toArray());
        }
        return null;
    }
}
