package de.tired.api.guis.clickgui.setting.impl;

import de.tired.api.guis.clickgui.setting.Setting;
import de.tired.module.Module;

import java.awt.*;
import java.util.function.Supplier;

public class ColorPickerSetting extends Setting {

	private String value;
	public float HUE;
	public Color ColorPickerC;

	public ColorPickerSetting(String name, Module parent, boolean colorPicker, Color color, int HUE, Supplier<Boolean> dependency) {
		super(name, parent, dependency, colorPicker, color, HUE);
		this.ColorPickerC = color;
		this.HUE = (float) HUE;
	}

	public Color getColorPickerColor() {
		return this.ColorPickerC;
	}

	public void setColorPickerColor(Color color) {
		ColorPickerC = color;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}