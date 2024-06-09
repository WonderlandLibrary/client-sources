package com.kilo.mod;

import java.util.List;

import com.kilo.mod.toolbar.dropdown.Interactable;

public class ModuleOption {

	public String name, description;
	public Interactable.TYPE type;
	public Object value;
	public float[] limit;
	public boolean isFloat;
	public List<ModuleSubOption> subOptions;

	public ModuleOption(String name, String description, Interactable.TYPE type, Object value, float[] limit, boolean isFloat, List<ModuleSubOption> subOptions) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.value = value;
		this.limit = limit;
		this.isFloat = isFloat;
		this.subOptions = subOptions;
	}
}
