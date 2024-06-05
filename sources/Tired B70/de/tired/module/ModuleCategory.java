package de.tired.module;

public enum ModuleCategory {

	COMBAT("Combat"),
	MOVEMENT("Movement"),
	PLAYER("Player"),
	RENDER("Render"),
	MISC("Misc");

	public final String displayName;

	ModuleCategory(String displayName) {

		this.displayName = displayName;
	}

}
