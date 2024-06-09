package net.minecraft.client.main.neptune.Mod;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.neptune.memes.Memeager;

public class Mod {
	private String name, renderName, realName;
	private int bind;
	private Category cat;
	private Color color;
	private boolean isEnabled;
	protected Minecraft mc;

	public Mod(String name, Category cat) {
		this.mc = Minecraft.getMinecraft();
		this.name = name;
		this.setBind(Keyboard.KEY_NONE);
		this.cat = cat;
		this.setRenderName(this.getModName());
		this.setName(name);
	}

	public void setName(String name) {
		this.realName = name;
	}

	public String getName() {
		return this.realName;
	}

	public int getBind() {
		return this.bind;
	}

	public void setBind(int keyBind) {
		this.bind = keyBind;
	}

	public String getModName() {
		return this.name;
	}

	public Category getCategory() {
		return this.cat;
	}

	public Color getRandColor() {
		return this.color;
	}

	public String getRenderName() {
		return this.renderName;
	}

	public void setRenderName(String renderName) {
		this.renderName = renderName;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void toggle() {
		this.setEnabled(!this.isEnabled);
	}

	public void onEnable() {
		//OMG FUCKING DUMBASS
		Memeager.register(this);
	}

	public void onDisable() {
		Memeager.unregister(this); //no no look
	}

	public void onLeftClick() {
	}

	public void onRightClick() {
	}

	public void setEnabled(boolean enabled) {
		this.color = new Color((float) (Math.random() * 1.0), (float) (Math.random() * 1.0),
				(float) (Math.random() * 1.0), 1.0f);
		if (enabled) {
			this.isEnabled = true;
			this.onEnable();
		} else {
			this.isEnabled = false;
			this.onDisable();
		}
	}

	public boolean isCategory(Category category) {
		return this.cat == category;
	}
}
