package win.sightclient.module;

public enum Category {
	COMBAT("Combat"), 
	MOVEMENT("Movement"),
	RENDER("Render"),
	PLAYER("Player"),
	OTHER("Other"),
	TARGETS("Targets"),
	SCRIPTS("Scripts");

	private String name;
	Category(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
    public String toString() {
        return this.name;
    }
}
