package win.sightclient.event;

import win.sightclient.Sight;
import win.sightclient.module.Module;
import win.sightclient.script.Script;

public class Event {

	private boolean isCancelled = false;
	
	private boolean waiting = false;
	
	public void call() {
		if (Sight.instance.mm == null) {
			return;
		}
		
		try {
			for (int i = 0; i < Sight.instance.mm.getModules().size(); i++) {
				Module m = Sight.instance.mm.getModules().get(i);
				if (m.isToggled() && !(m instanceof Script)) {
					m.onEvent(this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Sight.instance.scriptManager != null) {
			Sight.instance.scriptManager.onEvent(this);
		}
	}
	
	public boolean isCancelled() {
		return this.isCancelled;
	}
	
	public void setCancelled() {
		this.isCancelled = true;
	}
}
