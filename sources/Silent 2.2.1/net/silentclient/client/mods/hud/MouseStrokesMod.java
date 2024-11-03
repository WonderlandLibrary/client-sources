package net.silentclient.client.mods.hud;

import java.awt.Color;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.ModDraggable;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.TimerUtils;

public class MouseStrokesMod extends ModDraggable {
	private float prevX;
	private float prevY;
	private float lastX;
	private float lastY;
	
	private TimerUtils timer = new TimerUtils();
	
	private SimpleAnimation yawAnimation = new SimpleAnimation(0.0F);
	private SimpleAnimation pitchAnimation = new SimpleAnimation(0.0F);
	
	public MouseStrokesMod() {
		super("Mouse Strokes", ModCategory.MODS, "silentclient/icons/mods/mousestrokes.png");
	}
	
	@Override
	public int getWidth() {
		return 58;
	}

	@Override
	public int getHeight() {
		return 58;
	}
	
	@EventTarget
	public void onTick(ClientTickEvent event) {
		int mouseX = Mouse.getX();
		int mouseY = -Mouse.getY();
		
		if(timer.delay(150)) {
			prevX = mouseX;
			prevY = mouseY;
			timer.reset();
		}
		
		lastX = mouseX - prevX;
		lastY = mouseY - prevY;
	}

	@Override
	public boolean render(ScreenPosition pos) {
		GlStateManager.pushMatrix();
		boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "Background").getValBoolean();
		Color backgroundColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Background Color").getValColor();
		Color dotColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Dot Color").getValColor();
		if(background) {
			ColorUtils.setColor(backgroundColor.getRGB());
			RenderUtils.drawRect(0, 0, 58, 58, backgroundColor.getRGB());
		}
		
		if(lastX > 15) {
			lastX = 15;
		}
		
		if(lastX < -15) {
			lastX = -15;
		}
		
		if(lastY > 15) {
			lastY = 15;
		}
		
		if(lastY < -15) {
			lastY = -15;
		}
		
		yawAnimation.setAnimation(lastX, 100);
		pitchAnimation.setAnimation(lastY, 100);

		ColorUtils.setColor(dotColor.getRGB());
		RenderUtil.drawRoundedRect(0 + 23 + yawAnimation.getValue() + 8, 0 + 23 + pitchAnimation.getValue() + 8, 4, 4, 4, dotColor.getRGB());
		ColorUtils.setColor(-1);
		GlStateManager.popMatrix();
		return true;
	}
	
	@Override
	public void setup() {
		super.setup();
		this.addBooleanSetting("Background", this, true);
		this.addColorSetting("Background Color", this, new Color(0, 0, 0), 127);
		this.addColorSetting("Dot Color", this, new Color(255, 255, 255));
	}
}	
