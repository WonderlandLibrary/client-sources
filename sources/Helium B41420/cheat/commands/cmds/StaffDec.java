package rip.helium.cheat.commands.cmds;

import rip.helium.ClientBase;
import rip.helium.cheat.commands.Command;
import rip.helium.cheat.impl.misc.FlagDetector;

public class StaffDec extends Command {

    public StaffDec() {
        super("onlinestaff", "staff", "staffdetect", "detect", "s");
    }

    @Override
    public void run(String[] args) {
        try {
            ClientBase.chat("Starting Staff Detector Command.\nLooping thru players.");
            FlagDetector.TabListCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
