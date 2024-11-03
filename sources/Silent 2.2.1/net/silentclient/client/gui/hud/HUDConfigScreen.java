package net.silentclient.client.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.font.SilentFontRenderer.FontType;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.CustomFontRenderer.RenderMode;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModDraggable;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.Sounds;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class HUDConfigScreen extends GuiScreen {

	private final GuiScreen screen;
	private CustomFontRenderer font;
	private Mod mod = null;
	private SimpleAnimation xLineAnimation;
	private SimpleAnimation yLineAnimation;
	
	@Override
	public void initGui() {
		MenuBlurUtils.loadBlur();
		super.initGui();

		this.mod = null;
		ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        this.buttonList.add(new IconButton(0, scaledresolution.getScaledWidth() / 2 - 15, scaledresolution.getScaledHeight() - 80, 30, 30, 18, 18, new ResourceLocation("silentclient/icons/back.png")));
        this.font = new CustomFontRenderer();
        font.setRenderMode(RenderMode.CUSTOM);

		for(Mod m : Client.getInstance().getModInstances().getMods()) {
			if(m instanceof ModDraggable) {
				((ModDraggable) m).setDragging(false);
			}
		}

		xLineAnimation = new SimpleAnimation(0);
		yLineAnimation = new SimpleAnimation(0);
	}
	
	public HUDConfigScreen(GuiScreen screen) {		
		this.screen = screen;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		MenuBlurUtils.renderBackground(this);
		final float zBackup = this.zLevel;
		this.zLevel = 200;
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
		boolean debug = Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Gui Debug").getValBoolean();
		if(debug) {
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(0, 0, "mX: " + mouseX, 6, FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(0, 6, "mY: " + mouseY, 6, FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(0, 12, "width: " + scaledresolution.getScaledWidth() * Client.getInstance().getScaleFactor(), 6, FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(0, 18, "height: " + scaledresolution.getScaledHeight() * Client.getInstance().getScaleFactor(), 6, FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(0, 24, "scale: " + Client.getInstance().getScaleFactor(), 6, FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(0, 30, "mouseDown: " + Mouse.isButtonDown(0), 6, FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(0, 36, "selectedMod: " + (this.mod != null ? this.mod.getClass().getName() : "null"), 6, FontType.TITLE);
		}

		MouseCursorHandler.CursorType cursorType = SilentScreen.getCursor(new ArrayList<>(), buttonList);

		for(Mod m : Client.getInstance().getModInstances().getMods()) {
			if(m.isEnabled() && m instanceof ModDraggable) {
				if(((ModDraggable) m).isHovered(mouseX, mouseY, scaledresolution) || ((ModDraggable) m).isDragging()) {
					cursorType = MouseCursorHandler.CursorType.MOVE;
				}
				if(((ModDraggable) m).getCornerScalingType() == CornerScalingType.BOTTOM_RIGHT || ((ModDraggable) m).cornerDotHovered(mouseX, mouseY, CornerScalingType.BOTTOM_RIGHT)) {
					cursorType = MouseCursorHandler.CursorType.NESW_RESIZE;
				}

				if(((ModDraggable) m).isDragging()) {
					this.mod = m;
					((ModDraggable) m).setPos(new ScreenPosition(mouseX + ((ModDraggable) m).getDraggingX(), mouseY + ((ModDraggable) m).getDraggingY()));
				}

				if(((ModDraggable) m).getCornerScalingType() != null && ((ModDraggable) m).getCornerScalingType() != CornerScalingType.NONE) {
					Setting setting = Client.getInstance().getSettingsManager().getSettingByName(m, "Scale");
					float scale;
					scale = mouseY / (((ModDraggable) m).getPos().getAbsoluteY() + (((ModDraggable) m).getCornerY(((ModDraggable) m).getCornerScalingType()) * ((ModDraggable) m).getScale()));
					scale = scale - 1;
					scale = setting.getValFloat() + scale;
					if(scale <= setting.getMax() && scale >= setting.getMin()) {
						setting.setValDouble(scale);
					}
				}

				((ModDraggable) m).renderEditing(mouseX, mouseY, scaledresolution, debug, this);
			}
		}

		if(mod != null && ((ModDraggable) mod).isDragging() && Mouse.isButtonDown(0)) {
			if(MouseUtils.isInside(mouseX, mouseY, this.width / 2 - ((30 + ((ModDraggable) mod).getScaledWidth()) / 2), 0, (30 + ((ModDraggable) mod).getScaledWidth()), height)) {
				yLineAnimation.setAnimation(255, 12);
			} else {
				yLineAnimation.setAnimation(0, 12);
			}

			if(MouseUtils.isInside(mouseX, mouseY, 0, this.height / 2 - ((30 + ((ModDraggable) mod).getScaledHeight()) / 2), width, (30 + ((ModDraggable) mod).getScaledHeight()))) {
				xLineAnimation.setAnimation(255, 12);
			} else {
				xLineAnimation.setAnimation(0, 12);
			}
		} else {
			yLineAnimation.setAnimation(0, 12);
			xLineAnimation.setAnimation(0, 12);
		}

		RenderUtils.drawRect(0, this.height / 2, this.width, 1, new Color(255, 255, 255, (int) xLineAnimation.getValue()).getRGB());
		RenderUtils.drawRect(this.width / 2, 0, 1, this.height, new Color(255, 255, 255, (int) yLineAnimation.getValue()).getRGB());

		if(mod != null && !((ModDraggable) mod).isDragging()) {
			this.mod = null;
		}

		Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.zLevel = zBackup;
		GlStateManager.disableAlpha();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		// TODO Auto-generated method stub
		super.actionPerformed(button);
		switch(button.id) {
		case 0:
			mc.displayGuiScreen(screen);
			break;
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			Sounds.playButtonSound();
	        mc.displayGuiScreen(null);
		}
	}
	
	@Override
	public void onGuiClosed() {
		MenuBlurUtils.unloadBlur();
		Client.getInstance().configManager.save();
		for(Mod m : Client.getInstance().getModInstances().getMods()) {
			if(m instanceof ModDraggable) {
				((ModDraggable) m).setDragging(false);
			}
		}
		Client.getInstance().getMouseCursorHandler().disableCursor();
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		for(Mod m : Client.getInstance().getModInstances().getMods()) {
			if(m instanceof ModDraggable) {
				((ModDraggable) m).setDragging(false);
				((ModDraggable) m).setCornerScalingType(CornerScalingType.NONE);
			}
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		for(Mod m : Client.getInstance().getModInstances().getMods()) {
			if(m.isEnabled() && m instanceof ModDraggable) {
				if(((ModDraggable) m).cornerDotHovered(mouseX, mouseY, CornerScalingType.BOTTOM_RIGHT)) {
					((ModDraggable) m).setCornerScalingType(CornerScalingType.BOTTOM_RIGHT);
					break;
				}
				boolean hovered = ((ModDraggable) m).isHovered(mouseX, mouseY, scaledResolution);

				if(hovered && mouseButton == 0) {
					((ModDraggable) m).setDragging(true);
					((ModDraggable) m).setDraggingX(((ModDraggable) m).getPos().getAbsoluteX() - mouseX);
					((ModDraggable) m).setDraggingY(((ModDraggable) m).getPos().getAbsoluteY() - mouseY);
					break;
				}
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	public enum CornerScalingType {
		NONE,
		TOP_LEFT,
		TOP_RIGHT,
		BOTTOM_LEFT,
		BOTTOM_RIGHT
	}
}