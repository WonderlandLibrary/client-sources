package me.rich.module.render;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventCrosshair;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class Crosshair extends Feature {

	@Override
    public void setup() { 
		Main.settingsManager.rSetting(new Setting("Dot", this, true));
		Main.settingsManager.rSetting(new Setting("Dynamic", this, false));
        Main.settingsManager.rSetting(new Setting("Gap", this, 4, 0, 20, true));
        Main.settingsManager.rSetting(new Setting("Lenght", this, 10, 0.5, 50, true));
        Main.settingsManager.rSetting(new Setting("Thickness", this, 1, 0.5, 4, false));
        Main.settingsManager.rSetting(new Setting("LineThickness", this, 0.5, 0.5, 4, false));
        Main.settingsManager.rSetting(new Setting("ColorRed", this, 255, 0, 255, false));
        Main.settingsManager.rSetting(new Setting("ColorGreen", this, 255, 0, 255, false));
        Main.settingsManager.rSetting(new Setting("ColorBlue", this, 255, 0, 255, false));      
	}
	
	public Crosshair() {
		super("Crosshair", 0, Category.RENDER);
	}
	
	@EventTarget
    public void onRenderCrosshair(EventCrosshair event) {
        GL11.glPushMatrix();
        event.setCancelled(true);
        ScaledResolution sr = event.getScaledRes();
        double thickness = Main.settingsManager.getSettingByName("Thickness").getValDouble() / 2.0D;
        double linethickness = Main.settingsManager.getSettingByName("LineThickness").getValDouble();
        int red = (int) Main.settingsManager.getSettingByName("ColorRed").getValDouble();
        int green = (int) Main.settingsManager.getSettingByName("ColorGreen").getValDouble();
        int blue = (int) Main.settingsManager.getSettingByName("ColorBlue").getValDouble();
        double gap = Main.settingsManager.getSettingByName("Gap").getValDouble();
        double lenght = Main.settingsManager.getSettingByName("Lenght").getValDouble();
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        float middleX = width / 2.0F;
        float middleY = height / 2.0F;
        if (Main.settingsManager.getSettingByName("Dot").getValBoolean()) {
        	RenderHelper.drawRect(middleX - thickness - linethickness, middleY - thickness - linethickness, middleX + thickness + linethickness, middleY + thickness + linethickness, Color.BLACK.getRGB());
        	RenderHelper.drawRect(middleX - thickness, middleY - thickness, middleX + thickness, middleY + thickness, new Color(red, green, blue).getRGB());
        
        }

        // 1
        RenderHelper.drawRect(middleX - thickness - linethickness, middleY - gap - lenght - linethickness - (isMoving() ? 2 : 0), middleX + thickness + linethickness, middleY - gap + linethickness - (isMoving() ? 2 : 0), Color.BLACK.getRGB());
        RenderHelper.drawRect(middleX - thickness, middleY - gap - lenght - (isMoving() ? 2 : 0), middleX + thickness, middleY - gap - (isMoving() ? 2 : 0), new Color(red, green, blue).getRGB());

        // 2
        RenderHelper.drawRect(middleX - gap - lenght - linethickness - (isMoving() ? 2 : 0), middleY - thickness - linethickness, middleX - gap + linethickness - (isMoving() ? 2 : 0), middleY + thickness + linethickness, Color.BLACK.getRGB());
        RenderHelper.drawRect(middleX - gap - lenght - (isMoving() ? 2 : 0), middleY - thickness, middleX - gap - (isMoving() ? 2 : 0), middleY + thickness, new Color(red, green, blue).getRGB());

        // 3
        RenderHelper.drawRect(middleX - thickness - linethickness, middleY + gap - linethickness + (isMoving() ? 2 : 0), middleX + thickness + linethickness, middleY + gap + lenght + linethickness + (isMoving() ? 2 : 0), Color.BLACK.getRGB());
        RenderHelper.drawRect(middleX - thickness, middleY + gap + (isMoving() ? 2 : 0), middleX + thickness, middleY + gap + lenght + (isMoving() ? 2 : 0), new Color(red, green, blue).getRGB());

        // 4
        RenderHelper.drawRect(middleX + gap - linethickness + (isMoving() ? 2 : 0), middleY - thickness - linethickness, middleX + gap + lenght + linethickness + (isMoving() ? 2 : 0), middleY + thickness + linethickness, Color.BLACK.getRGB());
        RenderHelper.drawRect(middleX + gap + (isMoving() ? 2 : 0), middleY - thickness, middleX + gap + lenght + (isMoving() ? 2 : 0), middleY + thickness, new Color(red, green, blue).getRGB());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GL11.glPopMatrix();
    }

    public boolean isMoving() {
        return Main.settingsManager.getSettingByName("Dynamic").getValBoolean() && (!mc.player.isCollidedHorizontally) && (!mc.player.isSneaking()) && ((mc.player.movementInput.moveForward != 0.0F) || (mc.player.movementInput.moveStrafe != 0.0F) || mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindAttack.pressed || mc.gameSettings.keyBindBack.pressed);
    }
}