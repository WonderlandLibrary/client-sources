package me.gishreload.yukon.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import me.gishreload.yukon.Category;
import me.gishreload.yukon.CategorySett;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.events.PacketEvent;
public class Module {
	
	protected Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private int key;
	private boolean toggled;
	private boolean toggledKa = Meanings.killaura10;
	private Category category;
	private CategorySett categorysett;
	
	public Module(String nm, int i, Category c){
		name = nm;
		key = i;
		category = c;
		toggled = false;
	}
	
	public void toggle(){
		toggled = !toggled;
		if(toggled){
			onEnable();
		}else{
			onDisable();
		}
	}
	
	public void toggleKa10(){
		toggledKa = !toggledKa;
		if(toggledKa){
			onEnable();
		}else{
			onDisable();
		}
	}
	
	public void onEnable(){}
	public void onDisable(){}
	public void onUpdate(){}
	public void onRender(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Category getCategory(){
		return category;
	}
	
	public CategorySett getCategorySett(){
		return categorysett;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public boolean isToggled() {
		return toggled;
	}
	
	public boolean isToggledKa10() {
		return toggledKa;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}
	
	public boolean onSendChatMessage(String s){
		return true;
	}
	
	public boolean onRecieveChatMessage(SPacketChat packet){
		return true;
	}

	public boolean isPressed()
    {
        if (this.key == 0)
        {
            return false;
        }
        else
        {
            --this.key;
            return true;
        }
    }

}
