package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;


public class IceSpeed extends Module{
	public IceSpeed() {
		super("IceSpeed", 0, Category.PLAYER);
	}
	
	 @Override
	    public void onEnable() {
	        Blocks.ICE.slipperiness = 0.4f;
	        Blocks.PACKED_ICE.slipperiness = 0.4f;
	        Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eIceSpeed \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	    }
	    
	    @Override
	    public void onDisable() {
	        Blocks.ICE.slipperiness = 0.85f;
	        Blocks.PACKED_ICE.slipperiness = 0.85f;
	        Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eIceSpeed \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	    }
	}


