package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.EventRenderModel;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

@ModuleAnnotation(name = "Chams", category = ModuleCategory.RENDER, clickG = "See players through walls")
public class Chams extends Module {

	public ColorPickerSetting chamsColor = new ColorPickerSetting("chamsColor", this, true, new Color(0, 0, 0, 255), (new Color(0, 0, 0, 255)).getRGB(), null);

	@EventTarget
	public void onRender(EventRenderModel eventRenderModel) {

	}


	private boolean isValidType(Entity entity) {
		return (entity instanceof EntityPlayer);
	}

	@Override
	public void onState() {

	}
	@Override
	public void onUndo() {

	}
}
