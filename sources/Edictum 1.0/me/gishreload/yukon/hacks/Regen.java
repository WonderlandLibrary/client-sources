package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.events.Event;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.command.Command;
import me.gishreload.yukon.command.CommandManager;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.Timer;
import me.gishreload.yukon.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.Potion;


public class Regen extends Module{
    
	public Regen() {
		super("Regen", 0, Category.COMBAT);
	}
	
	public void onEnable(){
		Meanings.Regen = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eRegen \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Meanings.Regen = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eRegen \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
}
