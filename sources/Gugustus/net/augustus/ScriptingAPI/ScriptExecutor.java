package net.augustus.ScriptingAPI;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import net.augustus.Augustus;
import net.augustus.utils.interfaces.MC;

public class ScriptExecutor {
	
	public ScriptingAPI api;
	
	public ScriptExecutor(ScriptingAPI api) {
		this.api = api;
	}
	
	public void execute(String script) {
		Binding bind = new Binding();
		bind.setVariable(Augustus.getInstance().getApiBinding(), api);
		GroovyShell shell = new GroovyShell(bind);
		try {
			shell.evaluate(script);
		}catch(Exception e) {
			//usually script error
			System.err.println("Script error: " + e.getMessage());
		}
	}

}
