package me.valk.command.commands;

import java.util.List;

import me.valk.Vital;
import me.valk.command.Command;
import me.valk.module.Module;
import me.valk.utils.TimerUtils;
import me.valk.utils.Wrapper;

public class SpamCommand extends Command {

	public TimerUtils timer;
	
    public SpamCommand(){
        super("Spam", new String[]{"s"}, "spam a specific player.");
    }

    @Override
    public void onCommand(List<String> args){
        if(args.size() != 1){
            error("Invalid args! Usage : 'Spam name msg'");
            return;
        }

        
        String name = args.get(0);
                
        if(timer.hasReached(100L) && name != null) {
        	Wrapper.getPlayer().sendChatMessage("TEST" + name);
        	timer.reset();
        }

        
        
    }
}
