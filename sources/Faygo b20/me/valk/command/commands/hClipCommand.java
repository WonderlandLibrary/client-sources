package me.valk.command.commands;

import java.util.List;

import me.valk.command.Command;
import me.valk.utils.Wrapper;

public class hClipCommand extends Command {

	public hClipCommand() {
		super("HClip", new String[]{"hc"}, "Teleport horazontally");
	}

	@Override
	public void onCommand(List<String> args) {
		if(args.size() >= 1){
			if(args.size() == 1){
				try{
					double ammount = Double.parseDouble(args.get(0));
					
					double x = -Math.sin(Math.toRadians(Wrapper.getPlayer().rotationYaw)) * ammount;
					double z = Math.cos(Math.toRadians(Wrapper.getPlayer().rotationYaw)) * ammount;
					
					Wrapper.getPlayer().setLocation(Wrapper.getPlayer().getLocation().add(x, 0, z));
				}catch(Exception e){
					error("Invalid args! Usage : 'HClip [ammount]'");
				}
			}else if(args.size() == 2){
				try{
					double x = Double.parseDouble(args.get(0));
                    double z = Double.parseDouble(args.get(1));
					
					Wrapper.getPlayer().setLocation(Wrapper.getPlayer().getLocation().add(x, 0, z));
				}catch(Exception e){
					error("Invalid args! Usage : 'HClip [x] [z]'");
				}
			}else{
				error("Invalid args! Usage : 'HClip [x] [z]' or 'Hclip [ammount]'");
			}
		}else{
			error("Invalid args! Usage : 'HClip [x] [z]' or 'Hclip [ammount]'");
		}
	}
	
}
