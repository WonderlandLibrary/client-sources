/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import java.text.DecimalFormat;

import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventCheatRenderTick;
import cafe.kagu.kagu.eventBus.impl.EventRender2D;
import cafe.kagu.kagu.eventBus.impl.EventSettingUpdate;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.font.FontRenderer;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.combat.ModKillAura;
import cafe.kagu.kagu.mods.impl.ghost.ModAutoRod;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.LabelSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.ui.clickgui.GuiCsgoClickgui;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.ClickGuiUtils;
import cafe.kagu.kagu.utils.StencilUtil;
import cafe.kagu.kagu.utils.UiUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

/**
 * @author DistastefulBannock
 *
 */
public class ModTargetHud extends Module {
	
	public ModTargetHud() {
		super("TargetHud", Category.VISUAL);
		setSettings(movementLabel, mode, positionPresets, kaguHealthRed, kaguHealthGreen, kaguHealthBlue, kaguSmoothHealthRed, kaguSmoothHealthGreen, kaguSmoothHealthBlue, offsetX, offsetY);
	}
	
	private LabelSetting movementLabel = new LabelSetting("You can move the targethud by opening chat");
	private ModeSetting mode = new ModeSetting("Mode", "Simple", "Simple", "Kagu");
	private ModeSetting positionPresets = new ModeSetting("Position Presets", "None", "None", "Below Reticle", "Above Reticle", "Left of Reticle", "Right of Reticle");
	
	private IntegerSetting kaguHealthRed = new IntegerSetting("Health Bar Red", 165, 0, 255, 1).setDependency(() -> mode.is("Kagu"));
	private IntegerSetting kaguHealthGreen = new IntegerSetting("Health Bar Green", 224, 0, 255, 1).setDependency(() -> mode.is("Kagu"));
	private IntegerSetting kaguHealthBlue = new IntegerSetting("Health Bar Blue", 254, 0, 255, 1).setDependency(() -> mode.is("Kagu"));
	private IntegerSetting kaguSmoothHealthRed = new IntegerSetting("Smooth Health Bar Red", 213, 0, 255, 1).setDependency(() -> mode.is("Kagu"));
	private IntegerSetting kaguSmoothHealthGreen = new IntegerSetting("Smooth Health Bar Green", 140, 0, 255, 1).setDependency(() -> mode.is("Kagu"));
	private IntegerSetting kaguSmoothHealthBlue = new IntegerSetting("Smooth Health Bar Blue", 255, 0, 255, 1).setDependency(() -> mode.is("Kagu"));
	
	// Hidden settings, just here so the values save in the config
	private DoubleSetting offsetX = new DoubleSetting("Offset X", 0.5, 0, 1, 0.00000001).setDependency(() -> false);
	private DoubleSetting offsetY = new DoubleSetting("Offset Y", 0.7, 0, 1, 0.00000001).setDependency(() -> false);
	
	private double chatUiAnimation = 0;
	private double[] animation1 = new double[2], animation2 = new double[2];
	
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0");
	
	@Override
	public void onEnable() {
		isLeftMouseDown = false;
		isTargetHudBeingDragged = false;
		mouseOffsets = new double[4];
	}
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		setInfo(mode.getMode());
	};
	
	@EventHandler
	private Handler<EventRender2D> onRender2D = e -> {
		if (e.isPost())
			return;
		EntityLivingBase target = Kagu.getModuleManager().getModule(ModKillAura.class).getTarget();
		if (target == null)
			if (mc.getCurrentScreen() != null && (mc.getCurrentScreen() instanceof GuiChat || ClickGuiUtils.isInClickGui()))
				target = mc.thePlayer;
			else {
				animation1[0] = 0;
				animation1[1] = 0;
				return;
			}
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GlStateManager.color(1, 1, 1, 1);
		ScaledResolution sr = new ScaledResolution(mc);
		double x = offsetX.getValue() * sr.getScaledWidth_double();
		double y = offsetY.getValue() * sr.getScaledHeight_double();
		GlStateManager.translate(x, y, 0);
		
		switch (mode.getMode()) {
			case "Simple":{
				FontRenderer fr = FontUtils.SAN_FRANCISCO_THIN_10_AA;
				double halfWidth = (fr.getStringWidth(target.getName()) + 20) / 2;
				halfWidth = Math.max(halfWidth, 40);
				double halfHeight = fr.getFontHeight() * 2;
				animation1[1] = target.getHealth() / target.getMaxHealth();
				double healthScissorScale = animation1[0] * (halfWidth * 2) - halfWidth;
				
				UiUtils.drawRoundedRect(-halfWidth, -halfHeight, halfWidth, halfHeight, 0x50000000, 5);
				UiUtils.enableScissor(-halfWidth + x, -halfHeight + y, healthScissorScale + x, halfHeight + y);
				UiUtils.drawRoundedRect(-halfWidth, -halfHeight, halfWidth, halfHeight, 0x50000000, 5);
				UiUtils.disableScissor();
				fr.drawString(target.getName(), -halfWidth + 5, -halfHeight + 5, -1);
				fr.drawString(DECIMAL_FORMAT.format(target.getHealth()) + " HP", -halfWidth + 5, halfHeight - 5 - fr.getFontHeight(), -1);
				if (mc.getCurrentScreen() != null && mc.getCurrentScreen() instanceof GuiChat) {
					StencilUtil.enableStencilTest();
					StencilUtil.enableWrite();
					StencilUtil.clearStencil();
					StencilUtil.setTestOutcome(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
					double padding = 2;
					UiUtils.drawRoundedRect(-halfWidth - padding, -halfHeight - padding, halfWidth + padding, halfHeight + padding, 0x00000000, 5);
					StencilUtil.disableWrite();
					StencilUtil.setTestOutcome(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
					StencilUtil.glStencilFunc(GL11.GL_NOTEQUAL, 1);
					double spacing = 5;
					double hSize = (halfWidth + spacing) * chatUiAnimation;
					double vSize = (halfHeight + spacing) * chatUiAnimation;
					UiUtils.drawRoundedRect(-hSize, -vSize, hSize, vSize, 0x90ffffff, 5);
					StencilUtil.disableStencilTest();
				}
			}break;
			case "Kagu":{
				
			}break;
		}
		
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
		
	};
	
	@EventHandler
	private Handler<EventCheatRenderTick> onCheatRenderTick = e -> {
		if (e.isPost())
			return;
		
		// Animations
		double animationSpeed = 0.3;
		double chatUiAnimation = this.chatUiAnimation;
		
		if (mc.getCurrentScreen() != null && mc.getCurrentScreen() instanceof GuiChat) {
			chatUiAnimation += (1 - chatUiAnimation) * animationSpeed;
		}else {
			chatUiAnimation -= chatUiAnimation * animationSpeed;
		}
		this.chatUiAnimation = chatUiAnimation;
		
		if (animation1[0] < animation1[1]) {
			animation1[0] += (animation1[1] - animation1[0]) * animationSpeed;
		}else {
			animation1[0] -= (animation1[0] - animation1[1]) * animationSpeed;
		}
		
		if (animation2[0] < animation2[1]) {
			animation2[0] += (animation2[1] - animation2[0]) * animationSpeed;
		}else {
			animation2[0] -= (animation2[0] - animation2[1]) * animationSpeed;
		}
		
	};
	
	@EventHandler
	private Handler<EventSettingUpdate> onSettingUpdate = e -> {
		if (e.getSetting() != positionPresets || positionPresets.is("None"))
			return;
		
		switch (positionPresets.getMode()) {
			case "Below Reticle":{
				offsetX.setValue(0.5);
				offsetY.setValue(0.7);
			}break;
			case "Above Reticle":{
				offsetX.setValue(0.5);
				offsetY.setValue(0.3);
			}break;
			case "Left of Reticle":{
				offsetX.setValue(0.35);
				offsetY.setValue(0.5);
			}break;
			case "Right of Reticle":{
				offsetX.setValue(0.65);
				offsetY.setValue(0.5);
			}break;
		}
		
		positionPresets.setMode("None");
	};
	
	private boolean isLeftMouseDown = false;
	private boolean isTargetHudBeingDragged = false;
	private double[] mouseOffsets = new double[4];
	
	public void mouseMove(double mouseX, double mouseY) {
		ScaledResolution sr = new ScaledResolution(mc);
		mouseOffsets[0] = mouseX / sr.getScaledWidth_double();
		mouseOffsets[1] = mouseY / sr.getScaledHeight_double();
		if (isTargetHudBeingDragged) {
			offsetX.setValue(mouseOffsets[0] - mouseOffsets[2]);
			offsetY.setValue(mouseOffsets[1] - mouseOffsets[3]);
		}
	}
	
	/**
	 * @param isLeftMouseDown the isLeftMouseDown to set
	 */
	public void setLeftMouseDown(boolean isLeftMouseDown) {
		if (isLeftMouseDown) {
			ScaledResolution sr = new ScaledResolution(mc);
			EntityLivingBase target = Kagu.getModuleManager().getModule(ModKillAura.class).getTarget();
			if (Kagu.getModuleManager().getModule(ModKillAura.class).isDisabled() || target == null)
				if (mc.getCurrentScreen() != null && mc.getCurrentScreen() instanceof GuiChat)
					target = mc.thePlayer;
			
			switch (mode.getMode()) {
				case "Simple":{
					FontRenderer fr = FontUtils.SAN_FRANCISCO_THIN_10_AA;
					double halfWidth = (fr.getStringWidth(target.getName()) + 20) / 2;
					halfWidth = Math.max(halfWidth, 40);
					double halfHeight = fr.getFontHeight() * 2;
					double spacing = 5;
					double hSize = (halfWidth + spacing) * chatUiAnimation;
					double vSize = (halfHeight + spacing) * chatUiAnimation;
					double[] currentOffsets = new double[] {offsetX.getValue() * sr.getScaledWidth_double(), offsetY.getValue() * sr.getScaledHeight_double()};
					if (UiUtils.isMouseInsideRoundedRect(mouseOffsets[0] * sr.getScaledWidth_double(),
							mouseOffsets[1] * sr.getScaledHeight_double(), -hSize + currentOffsets[0],
							-vSize + currentOffsets[1], hSize + currentOffsets[0], vSize + currentOffsets[1], 5)) {
						mouseOffsets[2] = mouseOffsets[0] - offsetX.getValue();
						mouseOffsets[3] = mouseOffsets[1] - offsetY.getValue();
						isTargetHudBeingDragged = true;
					}
				}break;
			}
		}else {
			isTargetHudBeingDragged = false;
		}
		this.isLeftMouseDown = isLeftMouseDown;
	}
	
	/**
	 * @return the offsetX
	 */
	public DoubleSetting getOffsetX() {
		return offsetX;
	}
	
	/**
	 * @return the offsetY
	 */
	public DoubleSetting getOffsetY() {
		return offsetY;
	}
	
}
