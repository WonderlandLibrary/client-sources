package win.sightclient.script;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.util.EnumChatFormatting;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.event.events.render.EventRender2D;
import win.sightclient.script.events.EventScriptMove;
import win.sightclient.script.events.EventScriptRender;
import win.sightclient.script.events.EventScriptUpdate;
import win.sightclient.script.values.ScriptMC;
import win.sightclient.utils.minecraft.ChatUtils;

public class ScriptManager {

    private File scriptDir;
    private ArrayList<Script> scripts = new ArrayList<Script>();
    public static boolean hasScript = false;
    
    public ScriptManager() {
    	scriptDir = new File(Sight.instance.fileManager.dir, "scripts");
    	if (!scriptDir.exists()) {
    		scriptDir.mkdir();
    	}
        this.reload();
    }
    
    public void onEvent(Event e) {
    	if (this.scripts.isEmpty()) {
    		return;
    	}
    	if (e instanceof EventUpdate) {
    		EventUpdate eu = (EventUpdate)e;
    		if (eu.isPre()) {
    			ScriptMC.instance.preRun();
				EventScriptUpdate esu = new EventScriptUpdate(eu.getYaw(), eu.getPitch(), eu.getY());
    			for (int i = 0; i < this.scripts.size(); i++) {
    				Script s = this.scripts.get(i);
    				if (s.isToggled()) {
        				s.sendFunction(esu);
    				}
    			}
				eu.setYaw(esu.getYaw());
				eu.setPitch(esu.getPitch());
				eu.setY(esu.getY());
				ScriptMC.instance.postRun();
    		}
    	} else if (e instanceof EventMove) {
    		EventMove em = (EventMove)e;
    		ScriptMC.instance.preRun();
    		EventScriptMove esm = new EventScriptMove(em.getX(), em.getY(), em.getZ());
			for (int i = 0; i < this.scripts.size(); i++) {
				Script s = this.scripts.get(i);
				if (s.isToggled()) {
    				s.sendFunction(esm);
				}
			}
			em.setX(esm.getX());
			em.setY(esm.getY());
			em.setZ(esm.getZ());
    		ScriptMC.instance.postRun();
    	} else if (e instanceof EventRender2D) {
			for (int i = 0; i < this.scripts.size(); i++) {
				Script s = this.scripts.get(i);
				if (s.isToggled()) {
    				s.sendFunction(new EventScriptRender());
				}
			}
    	}
    }

    public void reload() {
        Sight.instance.mm.removeScripts();
        this.scripts.clear();
        
        hasScript = false;
        for (File f : this.scriptDir.listFiles()) {
        	if (f.getName().endsWith(".js")) {
        		try {
        			Script s = new Script(f);
        			Sight.instance.mm.addScript(s);
        			scripts.add(s);
        			hasScript = true;
        			ChatUtils.sendMessage("Loaded " + EnumChatFormatting.BOLD + s.getName() + " " + EnumChatFormatting.GRAY + s.getVersion());
        		} catch (Exception e) {
        			ChatUtils.sendMessage("There was a error loading " + f.getName() + ": ");
        			ChatUtils.sendMessage(e.getMessage());
        		}
        	}
        }
    }
}
