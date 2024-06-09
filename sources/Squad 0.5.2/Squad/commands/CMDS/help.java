package Squad.commands.CMDS;

import Squad.commands.Command;

public class help extends Command{

	public help() {
		super("help", ".help");
		// TODO Auto-generated constructor stub
	}
	
	public void execute(String[] args){
		if(args.length == 0){
			msg("Bind" + Bind.usage + Bind.info);
		}else{
			msg("Command nicht gefunden. Probiere .help");
		}
	}
	
	public String getName(){
		return "help";
	}

}
