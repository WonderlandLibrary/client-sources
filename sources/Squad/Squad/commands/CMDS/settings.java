package Squad.commands.CMDS;

import Squad.base.Module;
import Squad.base.ModuleManager;
import Squad.commands.Command;

public class settings extends Command{

	public settings() {
		super("settingsgomme", ".settings <server>");
		// TODO Auto-generated constructor stub
	}
	
	 public void execute(String[] args)
	  {
		 if(args.length == 0){
			 if(!Squad.Squad.moduleManager.getModuleByName("Aura").toggled) {
			 Squad.Squad.moduleManager.getModuleByName("Aura").toggle();
		    	Squad.Squad.setmgr.getSettingByName("Mode").setValString("AAC-Aura1");
		    	Squad.Squad.setmgr.getSettingByName("Range").setValDouble(6);
		    	Squad.Squad.setmgr.getSettingByName("Delay").setValDouble(70);
		    	if(!Squad.Squad.moduleManager.getModuleByName("Velocity").toggled){
				 Squad.Squad.moduleManager.getModuleByName("Velocity").toggle();
				 Squad.Squad.setmgr.getSettingByName("VelocityMode").setValString("AACPush");
		    	msg("This modules could autoban you: RewiTP, SlimeJump, Spider2, Highjump, WaterSpeed. Dont use it!");
		    	
		    	msg("Gomme settings loaded!");
		    	}
			 }
		 }
		 
		 }
	  
	 
	  public String getName()
	  {
	    return "settingsgomme";
	  }

}