package Focus.Beta.IMPL.Module;

public enum Type {

	COMBAT("Combat"), RENDER("Render"), MOVE("Movement"), MISC("Misc"), EXPLOIT("Exploit"), FOCUS("Focus");
	
	String name;
	
	Type(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
