package zamorozka.modules.ZAMOROZKA;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventCrosshair;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RenderUtils;

public class Crosshair extends Module {

	@Override
    public void setup() { 
		Zamorozka.settingsManager.rSetting(new Setting("Dot", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("Dynamic", this, false));
        Zamorozka.settingsManager.rSetting(new Setting("Gap", this, 4, 0, 20, true));
        Zamorozka.settingsManager.rSetting(new Setting("Lenght", this, 10, 0.5, 50, true));
        Zamorozka.settingsManager.rSetting(new Setting("Thickness", this, 1, 0.5, 4, false));
        Zamorozka.settingsManager.rSetting(new Setting("LineThickness", this, 0.5, 0.5, 4, false));
        Zamorozka.settingsManager.rSetting(new Setting("ColorRed", this, 255, 0, 255, false));
        Zamorozka.settingsManager.rSetting(new Setting("ColorGreen", this, 255, 0, 255, false));
        Zamorozka.settingsManager.rSetting(new Setting("ColorBlue", this, 255, 0, 255, false));      
	}
	
	public Crosshair() {
		super("Crosshair", 0, Category.Zamorozka);
	}
	
	@EventTarget
    public void onRenderCrosshair(EventCrosshair event) {
        GL11.glPushMatrix();
        event.setCancelled(true);
        ScaledResolution sr = event.getScaledRes();
        double thickness = Zamorozka.settingsManager.getSettingByName("Thickness").getValDouble() / 2.0D;
        double linethickness = Zamorozka.settingsManager.getSettingByName("LineThickness").getValDouble();
        int red = (int) Zamorozka.settingsManager.getSettingByName("ColorRed").getValDouble();
        int green = (int) Zamorozka.settingsManager.getSettingByName("ColorGreen").getValDouble();
        int blue = (int) Zamorozka.settingsManager.getSettingByName("ColorBlue").getValDouble();
        double gap = Zamorozka.settingsManager.getSettingByName("Gap").getValDouble();
        double lenght = Zamorozka.settingsManager.getSettingByName("Lenght").getValDouble();
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        float middleX = width / 2.0F;
        float middleY = height / 2.0F;
        if (Zamorozka.settingsManager.getSettingByName("Dot").getValBoolean()) {
        	RenderUtils.drawRect(middleX - thickness - linethickness, middleY - thickness - linethickness, middleX + thickness + linethickness, middleY + thickness + linethickness, Color.BLACK.getRGB());
            RenderUtils.drawRect(middleX - thickness, middleY - thickness, middleX + thickness, middleY + thickness, new Color(red, green, blue).getRGB());
        
        }

        // 1
        RenderUtils.drawRect(middleX - thickness - linethickness, middleY - gap - lenght - linethickness - (isMoving() ? 2 : 0), middleX + thickness + linethickness, middleY - gap + linethickness - (isMoving() ? 2 : 0), Color.BLACK.getRGB());
        RenderUtils.drawRect(middleX - thickness, middleY - gap - lenght - (isMoving() ? 2 : 0), middleX + thickness, middleY - gap - (isMoving() ? 2 : 0), new Color(red, green, blue).getRGB());

        // 2
        RenderUtils.drawRect(middleX - gap - lenght - linethickness - (isMoving() ? 2 : 0), middleY - thickness - linethickness, middleX - gap + linethickness - (isMoving() ? 2 : 0), middleY + thickness + linethickness, Color.BLACK.getRGB());
        RenderUtils.drawRect(middleX - gap - lenght - (isMoving() ? 2 : 0), middleY - thickness, middleX - gap - (isMoving() ? 2 : 0), middleY + thickness, new Color(red, green, blue).getRGB());

        // 3
        RenderUtils.drawRect(middleX - thickness - linethickness, middleY + gap - linethickness + (isMoving() ? 2 : 0), middleX + thickness + linethickness, middleY + gap + lenght + linethickness + (isMoving() ? 2 : 0), Color.BLACK.getRGB());
        RenderUtils.drawRect(middleX - thickness, middleY + gap + (isMoving() ? 2 : 0), middleX + thickness, middleY + gap + lenght + (isMoving() ? 2 : 0), new Color(red, green, blue).getRGB());

        // 4
        RenderUtils.drawRect(middleX + gap - linethickness + (isMoving() ? 2 : 0), middleY - thickness - linethickness, middleX + gap + lenght + linethickness + (isMoving() ? 2 : 0), middleY + thickness + linethickness, Color.BLACK.getRGB());
        RenderUtils.drawRect(middleX + gap + (isMoving() ? 2 : 0), middleY - thickness, middleX + gap + lenght + (isMoving() ? 2 : 0), middleY + thickness, new Color(red, green, blue).getRGB());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GL11.glPopMatrix();
    }

    public boolean isMoving() {
        return Zamorozka.settingsManager.getSettingByName("Dynamic").getValBoolean() && (!mc.player.isCollidedHorizontally) && (!mc.player.isSneaking()) && ((mc.player.movementInput.moveForward != 0.0F) || (mc.player.movementInput.moveStrafe != 0.0F) || mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindAttack.pressed || mc.gameSettings.keyBindBack.pressed);
    }
}