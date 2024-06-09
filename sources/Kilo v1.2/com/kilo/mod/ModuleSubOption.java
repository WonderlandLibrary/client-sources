package com.kilo.mod;

import com.kilo.mod.toolbar.dropdown.Interactable;

public class ModuleSubOption {

	public String name, description;
	public Interactable.TYPE type;
	public Object value;
	public float[] limit;
	public boolean isFloat;

	public ModuleSubOption(String name, String description, Interactable.TYPE type, Object value, float[] limit, boolean isFloat) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.value = value;
		this.limit = limit;
		this.isFloat = isFloat;
	}
}
