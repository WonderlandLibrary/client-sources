package com.kilo.mod;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.toolbar.dropdown.Interactable;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Module {

	protected final Minecraft mc = Minecraft.getMinecraft();
	
	public Category category;
	public String finder, name, description, warning;
	public boolean active;
	public List<ModuleOption> options = new CopyOnWriteArrayList<ModuleOption>();
	
	public Module(String finder, Category category, String name, String description, String warning) {
		this.finder = finder;
		this.category = category;
		this.name = name;
		this.description = description;
		this.warning = warning;
		
		addOption("Keybind", name+"'s keybind", Interactable.TYPE.KEYBINDER, Keyboard.KEY_NONE, null, false);
	}
	
	public void addOption(String name, String description, Interactable.TYPE type, Object value, float[] limit, boolean isFloat, List<ModuleSubOption> subOptions) {
		options.add(new ModuleOption(name, description, type, value, limit, isFloat, subOptions));
	}
	
	public void addOption(String name, String description, Interactable.TYPE type, Object value, float[] limit, boolean isFloat) {
		options.add(new ModuleOption(name, description, type, value, limit, isFloat, null));
	}
	
	public void onEnable() {
		this.active = true;
		ModuleManager.enable(this);
	}
	
	public void onDisable() {
		this.active = false;
		ModuleManager.disable(this);
	}
	
	public void toggle() {
		ModuleManager.toggle(this);
	}
	
	public void update(){}
	
	public void render2D(){}
	public void render3D(){}
	public void render3DPost(){}
	
	public Packet onPacketSend(Packet packet) { return packet; }
	public Packet onPacketReceive(Packet packet) { return packet;}

	public void onPlayerPreUpdate(){}
	public void onPlayerUpdate(){}
	public void onPlayerPostUpdate(){}

	public void onPlayerMovePreUpdate(){}
	public void onPlayerMovePostUpdate(){}

	public void onPlayerPreMotion(){}
	public void onPlayerPostMotion(){}

	public void onPlayerAttack(){}
	public void onPlayerPick(){}
	public void onPlayerUse(){}
	
	public void onPlayerBlockPlace(ItemStack stack, BlockPos pos, EnumFacing face, Vec3 hitVec){}
	public void onPlayerBlockBreaking(BlockPos pos, EnumFacing face){}
	public void onPlayerBlockBreak(){}

	public void onPlayerHurt(){}
	
	public void onChatSend(String s){}
	public void onChatReceive(String s){}
	
	public void onCameraTransform(){}
	
	public int getOption(String s) {
		for(ModuleOption mo : options) {
			if (mo.name.equalsIgnoreCase(s)) {
				return options.indexOf(mo);
			}
		}
		return -1;
	}
	
	public int getSubOption(String main, String sub) {
		for(ModuleOption mo : options) {
			if (mo.name.equalsIgnoreCase(main)) {
				if (mo.subOptions != null) {
					for(ModuleSubOption mso : mo.subOptions) {
						if (String.valueOf(mso.name).equalsIgnoreCase(sub)) {
							return mo.subOptions.indexOf(mso);
						}
					}
				}
			}
		}
		return -1;
	}
	
	public Object getOptionValue(String s) {
		for(ModuleOption mo : options) {
			if (mo.name.equalsIgnoreCase(s)) {
				return mo.value;
			}
		}
		return null;
	}
	
	public Object getSubOptionValue(String main, String sub) {
		for(ModuleOption mo : options) {
			if (mo.name.equalsIgnoreCase(main)) {
				if (mo.subOptions != null) {
					for(ModuleSubOption mso : mo.subOptions) {
						if (String.valueOf(mso.name).equalsIgnoreCase(sub)) {
							return mso.value;
						}
					}
				}
			}
		}
		return null;
	}
}
