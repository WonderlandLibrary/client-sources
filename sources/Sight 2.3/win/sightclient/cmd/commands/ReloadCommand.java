package win.sightclient.cmd.commands;

import win.sightclient.Sight;
import win.sightclient.cmd.Command;
import win.sightclient.module.render.ClickGUIMod;
import win.sightclient.module.render.TabGUIMod;

public class ReloadCommand extends Command {

	public ReloadCommand() {
		super(new String[] {"Reload"});
	}

	@Override
	public void onCommand(String message) {
		Sight.instance.scriptManager.reload();
		try {
			ClickGUIMod cg = (ClickGUIMod)Sight.instance.mm.getModuleByName("ClickGUI");
			cg.click.reload();
		} catch (Exception e) {}
		
		try {
			TabGUIMod tb = (TabGUIMod)Sight.instance.mm.getModuleByName("TabGUI");
			tb.open();
		} catch (Exception e) {}
	}
}
