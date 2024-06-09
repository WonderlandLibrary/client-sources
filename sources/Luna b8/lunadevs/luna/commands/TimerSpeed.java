package lunadevs.luna.commands;

import lunadevs.luna.command.Command;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.movement.TimerBoost;

public class TimerSpeed extends Command{

	public static double value = 5;
	
	@Override
	public String getAlias() {
		return "Timer";
	}

	@Override
	public String getDescription() {
		return "sets timer speed";
	}

	@Override
	public String getSyntax() {
		return "-Timer";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0].equalsIgnoreCase("help")){
			Luna.addChatMessage("speed");
		}else if (args[0].equalsIgnoreCase("speed")){
		    String timer = args[1];
		    TimerBoost.speed = Double.parseDouble(timer);
		    value = TimerBoost.speed;
		    Luna.addChatMessage("Timer speed: " + value);
		}
	}

}
