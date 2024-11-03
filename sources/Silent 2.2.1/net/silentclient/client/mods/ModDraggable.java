package net.silentclient.client.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.RenderEvent;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.hud.HUDConfigScreen;
import net.silentclient.client.gui.hud.IRenderer;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mixin.accessors.GuiAccessor;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.utils.ColorUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public abstract class ModDraggable extends Mod implements IRenderer {
	
	protected ScreenPosition pos;
	
	private ScreenPosition dragging;
	private boolean isDragging, hide;

	private int draggingX, draggingY;

	private HUDConfigScreen.CornerScalingType cornerScalingType;

	public ModDraggable(String name, ModCategory category, String icon, boolean defaultEnabled) {
		super(name, category, icon, defaultEnabled);
		this.pos = ScreenPosition.fromRelativePosition(0, 0);
	}

	public ModDraggable(String name, ModCategory category, String icon) {
		this(name, category, icon, false);
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Scale",  this, 2.0F, 1.0F, 5.0F, false);
		super.setup();
	}

	public int getDraggingX() {
		return draggingX;
	}

	public int getDraggingY() {
		return draggingY;
	}

	public void setDraggingX(int draggingX) {
		this.draggingX = draggingX;
	}

	public void setDraggingY(int draggingY) {
		this.draggingY = draggingY;
	}

	public float getScaledWidth() {
		return getWidth() * getScale();
	}
	
	public float getScaledHeight() {
		return (getHeight() - 1) * getScale();
	}
	
	public ScreenPosition getPos() {
		return pos;
	}
	
	public ScreenPosition getDragging() {
		return dragging;
	}

	public void setDragging(ScreenPosition dragging) {
		this.dragging = dragging;
	}
	
	public void setPos(ScreenPosition pos) {
		this.pos = pos;
	}
	
	@Override
	public ScreenPosition load() {
		return pos;
	}
	
	@Override
	public void Save(ScreenPosition pos) {
		this.pos = pos;
	}

	public boolean isHovered(int mouseX, int mouseY, ScaledResolution scaledresolution) {
		int x = pos.getAbsoluteX();
		int y = pos.getAbsoluteY();

		if(x < 0) {
			x = 0;
		}

		if(y < 0) {
			y = 0;
		}

		if((this.getScaledWidth() + x) > scaledresolution.getScaledWidth()) {
			x = (int) (x - ((this.getScaledWidth() + x) - scaledresolution.getScaledWidth()));
		}

		if((this.getScaledHeight() + y) > scaledresolution.getScaledHeight()) {
			y = (int) (y - ((this.getScaledHeight() + y) - scaledresolution.getScaledHeight()));
		}

		return MouseUtils.isInside(mouseX, mouseY, x, y, this.getScaledWidth(), this.getScaledHeight());
	}

	public void renderEditing(int mouseX, int mouseY, ScaledResolution scaledresolution, boolean debug, GuiScreen instance) {
		int x = pos.getAbsoluteX();
		int y = pos.getAbsoluteY();

		if(x < 0) {
			x = 0;
		}

		if(y < 0) {
			y = 0;
		}

		if((this.getScaledWidth() + x) > scaledresolution.getScaledWidth()) {
			x = (int) (x - ((this.getScaledWidth() + x) - scaledresolution.getScaledWidth()));
		}

		if((this.getScaledHeight() + y) > scaledresolution.getScaledHeight()) {
			y = (int) (y - ((this.getScaledHeight() + y) - scaledresolution.getScaledHeight()));
		}
		pos = ScreenPosition.fromAbsolute(x, y);
		if(debug) {
			GlStateManager.pushMatrix();
			int debugX = (int) (x + this.getScaledWidth()) + 1;
			int debugY = y;
			if((this.getScaledWidth() + debugX) > scaledresolution.getScaledWidth()) {
				debugX = x - Client.getInstance().getSilentFontRenderer().getStringWidth("name: " + this.getName().toLowerCase().replace(" ", ""), 6, SilentFontRenderer.FontType.TITLE) - 1;
			}
			if((this.getScaledHeight() + (debugY + 36)) > scaledresolution.getScaledHeight()) {
				debugY = (int) (y - (36 - this.getScaledHeight()));
			}
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(debugX, debugY, "name: " + this.getName().toLowerCase().replace(" ", ""), 6, SilentFontRenderer.FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(debugX, debugY + 6, "x: " + x, 6, SilentFontRenderer.FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(debugX, debugY + 12, "y: " + y, 6, SilentFontRenderer.FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(debugX, debugY + 18, "width: " + this.getScaledWidth(), 6, SilentFontRenderer.FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(debugX, debugY + 24, "height: " + this.getScaledHeight(), 6, SilentFontRenderer.FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(debugX, debugY + 30, "dragging: " + this.isDragging(), 6, SilentFontRenderer.FontType.TITLE);
			ColorUtils.setColor(-1);
			Client.getInstance().getSilentFontRenderer().drawString(debugX, debugY + 36, "cursor: " + Client.getInstance().getMouseCursorHandler().getCurrentCursor(), 6, SilentFontRenderer.FontType.TITLE);
			GlStateManager.popMatrix();
		}
		ColorUtils.setColor(-1);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0.0F);
		GlStateManager.scale(this.getScale(), this.getScale(), this.getScale());
		this.renderDummy(pos);
		boolean hovered = this.isHovered(mouseX, mouseY, scaledresolution);
		int hoverColor = new Color(245, 188, 0).getRGB();
		this.drawHollowRect(0, 0, this.getWidth(), this.getHeight(), hovered || this.isDragging() ? hoverColor : -1, instance);
		this.drawCornerDot(mouseX, mouseY, HUDConfigScreen.CornerScalingType.BOTTOM_RIGHT);
		GlStateManager.popMatrix();
	}

	private void drawCornerDot(int mouseX, int mouseY, HUDConfigScreen.CornerScalingType cornerType) {
		int color = -1;

		if(this.cornerDotHovered(mouseX, mouseY, cornerType)) {
			color = new Color(245, 188, 0).getRGB();
		}

		RenderUtil.drawRoundedRect(getCornerX(cornerType), getCornerY(cornerType), 3, 3, 3, color);
	}

	public int getCornerX(HUDConfigScreen.CornerScalingType cornerType) {
		int x = 0;

		switch (cornerType) {
			case TOP_LEFT:
			case BOTTOM_LEFT:
				x = -1;
				break;
			case TOP_RIGHT:
			case BOTTOM_RIGHT:
				x = this.getWidth() - 1;
				break;
		}

		return x;
	}

	public int getCornerY(HUDConfigScreen.CornerScalingType cornerType) {
		int y = 0;

		switch (cornerType) {
			case TOP_LEFT:
			case TOP_RIGHT:
				y = -1;
				break;
			case BOTTOM_LEFT:
			case BOTTOM_RIGHT:
				y = this.getHeight() - 1;
				break;
		}

		return y;
	}

	public boolean cornerDotHovered(int mouseX, int mouseY, HUDConfigScreen.CornerScalingType cornerType) {
		return MouseUtils.isInside(mouseX, mouseY, pos.getAbsoluteX() + (getCornerX(cornerType) * getScale()), pos.getAbsoluteY() + (getCornerY(cornerType) * getScale()), 3 * getScale(), 3 * getScale());
	}

	private void drawHollowRect(int x, int y, int w, int h, int color, Gui instance) {
		((GuiAccessor) instance).silent$drawHorizontalLine(x, x + w, y, color);
		((GuiAccessor) instance).silent$drawHorizontalLine(x, x + w, y + h, color);

		((GuiAccessor) instance).silent$drawVerticalLine(x, y + h, y, color);
		((GuiAccessor) instance).silent$drawVerticalLine(x + w, y + h, y, color);
	}

	@EventTarget
	public void renderEvent(RenderEvent event) {
		if(!this.isEnabled() || !this.getRender() || this.isForceDisabled()) {
			return;
		}
		
		ScaledResolution scaledresolution = new ScaledResolution(this.mc);
		
		ScreenPosition pos = this.load();
		
		if(pos == null) {
			pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
		}
		
		if(mc.gameSettings.showDebugInfo && Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Hide mods in F3").getValBoolean()) {
			return;
		}
		
		if(mc.currentScreen instanceof HUDConfigScreen) {
			return;
		}
		
		GL11.glColor4f(1, 1, 1, 1);

		int x = pos.getAbsoluteX();
		int y = pos.getAbsoluteY();

		if(x < 0) {
			x = 0;
		}

		if(y < 0) {
			y = 0;
		}
		
		if((getScaledWidth() + x) > scaledresolution.getScaledWidth()) {
			x = (int) (x - ((getScaledWidth() + x) - scaledresolution.getScaledWidth()));
		}
		
		if((getScaledHeight() + y) > scaledresolution.getScaledHeight()) {
			y = (int) (y - ((getScaledHeight() + y) - scaledresolution.getScaledHeight()));
		}
		
		GlStateManager.pushMatrix();
		GL11.glColor4f(1, 1, 1, 1);
		GlStateManager.translate(x, y, 0.0F);
		GlStateManager.scale(getScale(), getScale(), getScale());
		this.render(pos);
		GlStateManager.popMatrix();
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().fontRendererObj.drawString("", 0, 0, -1);
	}
	
	public float getScale() {
		float scale = Client.getInstance().getSettingsManager().getSettingByName(this, "Scale").getValFloat();
		
		return scale / Client.getInstance().getScaleFactor();
	}
	

	private int getLineOffset(int lineNum) {
		return (font.FONT_HEIGHT + 3) * lineNum;
	}

	public boolean isDragging() {
		return isDragging;
	}

	public void setDragging(boolean dragging) {
		this.isDragging = dragging;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public HUDConfigScreen.CornerScalingType getCornerScalingType() {
		return cornerScalingType;
	}

	public void setCornerScalingType(HUDConfigScreen.CornerScalingType cornerScalingType) {
		this.cornerScalingType = cornerScalingType;
	}
}
