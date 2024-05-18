package me.valk.agway.modules.movement;

import java.awt.Color;

import me.valk.event.EventListener;
import me.valk.event.events.entity.EventMoveRaw;
import me.valk.event.events.other.EventPacket;
import me.valk.event.events.player.EventMotion;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.value.BooleanValue;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Fly extends Module {

	public Fly() {
		super(new ModData("Fly", 22, new Color(120, 10, 193)), ModType.MOVEMENT);
	}

	@Override
	public void onEnable() {
		p.motionY = 0.1;
		p.capabilities.isFlying = true;
	}

	@Override
	public void onDisable() {
		p.capabilities.isFlying = false;
	}

	@EventListener
	public void onMotion(EventMotion e) {
        p.capabilities.setFlySpeed(0.2f);
        if(p.onGround) {
        	p.capabilities.isFlying = false;
        	this.toggle();
        }
	}

}