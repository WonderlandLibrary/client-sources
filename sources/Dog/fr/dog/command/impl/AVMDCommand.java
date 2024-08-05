package fr.dog.command.impl;

import fr.dog.command.Command;

public class AVMDCommand extends Command {

    public AVMDCommand(){
        super("avmd");
    }


    public static boolean advancedMode = false;


    @Override
    public void execute(String[] args, String message) {
        advancedMode = !advancedMode;
    }
}
