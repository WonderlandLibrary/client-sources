package me.valk.agway.modules.movement;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.valk.event.EventSystem;
import me.valk.event.EventType;
import me.valk.event.Listener;
import me.valk.event.events.player.EventMotion;
import me.valk.event.events.player.EventPlayerSlowdown;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowMod extends Module {

	public NoSlowMod(){
		super(new ModData("NoSlowdown", Keyboard.KEY_NONE, new Color(40, 255, 10)),
				ModType.MOVEMENT);
		EventSystem.register(new Listener<EventMotion>(){

			@Override
			public void onEvent(EventMotion event) {
				if(!getState()) return;
				
				boolean wasBlocking = p.isBlocking();
				if(wasBlocking){
					if(event.getType() == EventType.PRE){
						if(wasBlocking)
							p.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
									C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
									new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0D)));
					}else{
						p.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(
								p.getLocation().toBlockPos(), 255, p.inventory.getCurrentItem(),
								0.0F, 0.0F, 0.0F));
					}
				}
			}
			
		});
		
		EventSystem.register(new Listener<EventPlayerSlowdown>(){

			@Override
			public void onEvent(EventPlayerSlowdown event) {
				if(!getState()) return;
				
				event.setCancelled(true);
			}
			
		});
	}
	
	@Override
	public void onDisable(){
		if(p != null)
		p.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
				C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0,
						0, 0), EnumFacing.fromAngle(-255.0D)));
	}
	
}
