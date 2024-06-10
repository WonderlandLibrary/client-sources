// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.module.modules;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.module.ModuleManager;
import me.kaktuswasser.client.utilities.OutlineUtils;
import me.kaktuswasser.client.utilities.Stencil;
import me.kaktuswasser.client.values.ConstrainedValue;
import me.kaktuswasser.client.values.Value;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;

public class PlayerESP extends Module {
//	public final static Value red = new ConstrainedValue("esp_Red", "red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), null);
//	public final static Value green = new ConstrainedValue("esp_Green", "green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), null);
//	public final static Value blue = new ConstrainedValue("esp_Blue", "blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), null);
	
	
	public PlayerESP() {
		super("PlayerESP", -12621684, Module.Category.RENDER);
		
	}

	
	@Override
	public void onEvent(Event p0) {
		
	}

}
