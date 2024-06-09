package Squad.Modules.Settings;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;
import Squad.commands.Command;

public class Gomme extends Module{

	public Gomme() {
		super("Gomme", Keyboard.KEY_NONE, 0x88, Category.Settings);
		// TODO Auto-generated constructor stub
	}
	@EventTarget
	public void onUpdate(EventUpdate e){
		Squad.Squad.setmgr.getSettingByName("Mode").setValString("AAC-Aura1");
		Squad.Squad.setmgr.getSettingByName("Mode").setValDouble(6);
		Command.msg("§f[§cSquad§f]§f Du hast die Gomme settings erfolgreich geladen!");
	}
	

}
