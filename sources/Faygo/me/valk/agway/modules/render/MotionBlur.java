package me.valk.agway.modules.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.valk.event.EventListener;
import me.valk.event.events.other.EventTick;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.client.renderer.EntityRenderer;

public class MotionBlur extends Module{

	public MotionBlur() {
		super(new ModData("MotionBlur", 0, new Color(255, 255, 255)), ModType.RENDER);
	}
	
	@Override
	public void onDisable() {
		mc.entityRenderer.field_175083_ad = true;
		if(mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
		}
	}
	
	@Override
	public void onEnable() {
		EntityRenderer er = mc.entityRenderer;
		er.activateNextShader();
	}
	
	@EventListener
	public void onTick(EventTick event) {
		EntityRenderer er = mc.entityRenderer;
		mc.entityRenderer.field_175083_ad = true;
		if(mc.theWorld != null && (mc.entityRenderer.theShaderGroup == null || !mc.entityRenderer.theShaderGroup.getShaderGroupName().contains("phosphor"))) {
			if(er.theShaderGroup != null) {
				er.theShaderGroup.deleteShaderGroup();
			}
			er.activateNextShader();
		}
	}

}
