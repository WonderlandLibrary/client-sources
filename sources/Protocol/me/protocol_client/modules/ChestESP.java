package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import me.protocol_client.utils.RenderUtils;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.Render3DEvent;

public class ChestESP extends Module{
	public ChestESP() {
		super("Storage ESP", "storageesp", 0, Category.RENDER, new String[]{"chestesp", "storageesp", "chest"});
	}
	public final Value<Boolean> outline = new Value<>("storageesp_outline", false);
	public final Value<Boolean> box = new Value<>("storageesp_box", true);
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	@EventTarget
	public void onRender3D(Render3DEvent event){
		for(Object t : Wrapper.getWorld().loadedTileEntityList){
			if(t instanceof TileEntityChest){
				TileEntityChest chest = (TileEntityChest)t;
				if(box.getValue()){
				RenderUtils.drawChestESP(chest.getPos(), false);
			}
			}
			if(t instanceof TileEntityEnderChest){
				TileEntityEnderChest chest = (TileEntityEnderChest)t;
				if(box.getValue()){
				RenderUtils.drawChestESP(chest.getPos(), true);
			}
			}
		}
	}
	public void runCmd(String s){
		try{
		if(s.startsWith("mode")){
			String line2 = s.split(" ")[1];
			if(line2.equalsIgnoreCase("Box")){
				this.box.setValue(!this.box.getValue());
				Wrapper.tellPlayer("\2477ChestESP will " + (this.box.getValue() ? "now" : "no longer") + " display boxes");
				return;
			}
			if(line2.equalsIgnoreCase("Outline")){
				this.outline.setValue(!this.outline.getValue());
				Wrapper.tellPlayer("\2477ChestESP will " + (this.outline.getValue() ? "now" : "no longer") + " display using outlines");
			return;
			}
			Wrapper.tellPlayer("\2477Invalid arguments. -" + Protocol.primColor + "chestesp" + "\2477 mode <box/outline>");
			return;
		}
		Wrapper.tellPlayer("\2477Invalid arguments. -" + Protocol.primColor + "chestesp" + "\2477 mode <box/outline>");
		return;
		}catch(Exception e){
			Wrapper.tellPlayer("\2477Invalid arguments. -" + Protocol.primColor + "chestesp" + "\2477 mode <box/outline>");
		}
	}
}
