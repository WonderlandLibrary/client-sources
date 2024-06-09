package me.valk.agway.modules.render;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.valk.event.EventListener;
import me.valk.event.events.chams.EventPostRenderEntity;
import me.valk.event.events.chams.EventPostRenderPlayer;
import me.valk.event.events.chams.EventPreRenderEntity;
import me.valk.event.events.chams.EventPreRenderPlayer;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class Chams extends Module {

	public Chams() {
		super(new ModData("Chams", Keyboard.KEY_NONE, new Color(37, 83, 118)), ModType.RENDER);

	}

	@EventListener
	public void preRenderEntity(EventPreRenderEntity e) {

		GL11.glEnable(32823);
		GL11.glPolygonOffset(1.0f, -1100000.0f);
		
	}

	@EventListener
	public void postRenderEntity(EventPostRenderEntity e) {
		GL11.glDisable(32823);
		GL11.glPolygonOffset(1.0f, 1100000.0f);

	}

	@EventListener
	public void preRenderPlayer(EventPreRenderPlayer e) {
			GL11.glEnable(32823);
			GL11.glPolygonOffset(1.0f, -1100000.0f);
		
	}

	@EventListener
	public void postRenderPlayer(EventPostRenderPlayer e) {
			GL11.glDisable(32823);
			GL11.glPolygonOffset(1.0f, 1100000.0f);
		
	}

}