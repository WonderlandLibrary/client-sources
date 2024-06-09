package net.minecraft.client.main.neptune.Mod;

import java.util.ArrayList;
import java.util.Comparator;

import net.minecraft.client.main.neptune.Mod.Collection.Combat.Aboat;
import net.minecraft.client.main.neptune.Mod.Collection.Combat.Tigrboat;
import net.minecraft.client.main.neptune.Mod.Collection.Movement.Glide;
import net.minecraft.client.main.neptune.Mod.Collection.Movement.Jesus;
import net.minecraft.client.main.neptune.Mod.Collection.Movement.Sprint;
import net.minecraft.client.main.neptune.Mod.Collection.Player.ChestStealer;
import net.minecraft.client.main.neptune.Mod.Collection.Render.Gui;
import net.minecraft.client.main.neptune.Mod.Collection.Render.StorageESP;
import net.minecraft.client.main.neptune.Mod.Collection.Render.Tags;
import net.minecraft.client.main.neptune.Mod.Collection.Render.Tracers;


public class Mods {
	private ArrayList<Mod> mods;

	public Mods() {
		this.mods = new ArrayList<Mod>();
		this.add(new Aboat());//
		this.add(new Tags());
		this.add(new ChestStealer());
		this.add(new Tracers());
		this.add(new Glide());
		this.add(new Tigrboat());
		this.add(new Jesus());
		this.add(new Gui());
		this.add(new Sprint());
		this.add(new StorageESP());
		// Sort
		this.mods.sort(new Comparator<Mod>() {
			@Override
			public int compare(Mod o1, Mod o2) {
				return o1.getModName().length() - o2.getModName().length();
			}
		});
		// Optimize?
		this.mods.trimToSize();
	}

	private void add(Mod mod) {
		this.mods.add(mod);
	}

	public ArrayList<Mod> getMods() {
		// Sort
		this.mods.sort(new Comparator<Mod>() {
			@Override
			public int compare(Mod o1, Mod o2) {
				return o1.getModName().length() - o2.getModName().length();
			}
		});
		// Optimize?
		this.mods.trimToSize();
		return this.mods;
	}

	public Mod getMod(Class<? extends Mod> theMod) {
		for (Mod mod : this.getMods()) {
			if (mod.getClass() == theMod) {
				return mod;
			}
		}
		return null;
	}

	public Mod getMod(String theMod) {
		for (Mod mod : this.getMods()) {
			if (mod.getName().equalsIgnoreCase(theMod)) {
				return mod;
			}
		}
		return null;
	}
}
