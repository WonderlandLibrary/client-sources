package win.sightclient.script;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.script.events.EventScriptDisable;
import win.sightclient.script.events.EventScriptEnable;
import win.sightclient.script.events.EventScriptMove;
import win.sightclient.script.events.EventScriptRender;
import win.sightclient.script.events.EventScriptUpdate;
import win.sightclient.script.events.ScriptEvent;
import win.sightclient.script.values.ScriptMC;
import win.sightclient.script.values.classes.TimerUtilsMC;
import win.sightclient.utils.minecraft.ChatUtils;

public class Script extends Module {

    private ScriptEngineManager manager;
    private ScriptEngine engine;
    private Invocable inv;

    private String SCRIPT_NAME;
    private String SCRIPT_VERSION;
    
    public Script(File f) throws ScriptException, IOException, Exception {
        super(f.getName().split(".js")[0], Category.SCRIPTS);
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("JavaScript");
        manager.put("mc", ScriptMC.instance);
        manager.put("script", this);
        manager.put("TimerUtils", TimerUtilsMC.class);
        manager.put("EventUpdate", EventScriptUpdate.class);
        engine.eval(Files.newBufferedReader(Paths.get(f.getAbsolutePath()), StandardCharsets.UTF_8));
        inv = (Invocable) engine;
        
		this.SCRIPT_NAME = inv.invokeFunction("getName").toString();
		this.SCRIPT_VERSION = inv.invokeFunction("getVersion").toString();
    }
    
    public void disable() {
    	this.setToggled(false);
    }
    
    @Override
    public void onEnable() {
    	ScriptMC.instance.preRun();
    	this.sendFunction(new EventScriptEnable());
    	ScriptMC.instance.postRun();
    }
    
    @Override
    public void onDisable() {
    	ScriptMC.instance.preRun();
    	this.sendFunction(new EventScriptDisable());
    	ScriptMC.instance.postRun();
    }
    
    public String getName() {
    	return this.SCRIPT_NAME;
    }
    
    public String getDisplayName() {
    	return this.SCRIPT_NAME;
    }
    
    public String getVersion() {
    	return this.SCRIPT_VERSION;
    }

    public void sendFunction(ScriptEvent event) {
    	try {
    		if (event instanceof EventScriptUpdate) {
    			inv.invokeFunction("onUpdate", event);
    		} else if (event instanceof EventScriptMove) { 
    			inv.invokeFunction("onMove", event);
    		}  else if (event instanceof EventScriptRender) { 
    			inv.invokeFunction("onRender", event);
    		} else {
    			inv.invokeFunction("onEvent", event);
    		}
		} catch (ScriptException e) {
			ChatUtils.sendMessage(e.getMessage());
		} catch (NoSuchMethodException e) {}
    }
}
