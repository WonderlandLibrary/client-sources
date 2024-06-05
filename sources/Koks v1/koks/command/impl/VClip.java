package koks.command.impl;

import koks.command.Command;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 17:25
 */
public class VClip extends Command {

    public VClip() {
        super("VClip", "Clip");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1)
            return;

        int clip = Integer.parseInt(args[0]);

        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + clip, mc.thePlayer.posZ);
    }

}