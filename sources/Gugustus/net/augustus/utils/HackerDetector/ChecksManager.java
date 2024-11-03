package net.augustus.utils.HackerDetector;

import java.util.ArrayList;

import net.augustus.utils.HackerDetector.impl.AutoBlockCheck;
import net.augustus.utils.HackerDetector.impl.MovementCheck;
import net.augustus.utils.HackerDetector.impl.NoSlowCheck;
import net.augustus.utils.HackerDetector.impl.ReachCheck;
import net.augustus.utils.HackerDetector.impl.TeleportCheck;
import net.augustus.utils.HackerDetector.impl.VelocityCheck;

public class ChecksManager {
	
	public ArrayList<Check> checks = new ArrayList<Check>();
	
	public ChecksManager() {
		this.addChecks();
	}
	
	private void addChecks() {
		checks.add(new MovementCheck());
		checks.add(new AutoBlockCheck());
		checks.add(new TeleportCheck());
		checks.add(new ReachCheck());
		checks.add(new VelocityCheck());
		checks.add(new NoSlowCheck());
	}
	
	public Check getCheckByName(String name) {
		Check c = null;
		for(Check chk : checks) {
			if(c.name == name) {
				c = chk;
			}
		}
		if(c == null) {
			System.out.println(name + " not found, returnning null.");
			return null;
		}
		return c;
	}

}
