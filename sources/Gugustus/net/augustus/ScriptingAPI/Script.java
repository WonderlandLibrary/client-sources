package net.augustus.ScriptingAPI;

public class Script {
	
	public String name = "";
	public String author = "";
	public double version = 1.0;
	public String script = "";
	
	public Script(String name, double version, String script) {
		this.name = name;
		this.version = version;
		this.script = script;
	}

	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
}
