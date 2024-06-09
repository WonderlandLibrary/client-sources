/**
 * 
 */
package cafe.kagu.kagu.ui.clickgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.Event.EventPosition;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.impl.EventCheatRenderTick;
import cafe.kagu.kagu.eventBus.impl.EventKeyUpdate;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.font.FontRenderer;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.Module.Category;
import cafe.kagu.kagu.mods.impl.visual.ModClickGui;
import cafe.kagu.kagu.settings.Setting;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.KeybindSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.settings.impl.SlotSetting;
import cafe.kagu.kagu.ui.Colors;
import cafe.kagu.kagu.utils.SoundUtils;
import cafe.kagu.kagu.utils.UiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

/**
 * @author lavaflowglow
 *
 */
public class GuiCsgoClickgui extends GuiScreen {
	
	private GuiCsgoClickgui() {
		
	}
	
	private static GuiCsgoClickgui instance;
	/**
	 * @return The instance of the clickgui
	 */
	public static GuiCsgoClickgui getInstance() {
		if (instance == null) {
			instance = new GuiCsgoClickgui();
		}
		return instance;
	}
	
	/**
	 * Called when the client starts
	 */
	public void start() {
		Kagu.getEventBus().subscribe(this);
		
		// So we can calculate mouse presses
		degreesPerCategory = 360d / (double)Module.Category.values().length;
		{
			double degrees = 0;
			for (Category category : Category.values()) {
				Vector2d vector2d = new Vector2d(degrees, degrees + degreesPerCategory);
				categoryDegreesMap.put(category, vector2d);
				degrees += degreesPerCategory;
			}
		}
		
		// Assign modules to their category
		{
			for (Category category : Category.values()) {
				List<Module> modules = new ArrayList<>();
				for (Module module : Kagu.getModuleManager().getModules()) {
					if (module.getCategory() == category) {
						modules.add(module);
					}
				}
				Module[] categoryModules = new Module[0];
				categoryModules = modules.toArray(categoryModules);
				this.categoryModules.put(category, categoryModules);
			}
		}
		
	}
	
	// Colors
	private Vector4d circleInnerSelectionColor = new Vector4d(1, 1, 1, 0.65),
			circleOuterSelectionColor = new Vector4d(1, 1, 1, 0.005),
			circleInnerBackgroundColor = new Vector4d(0.0784313725, 0.0784313725, 0.0784313725, 0.5),
			circleOuterBackgroundColor = circleInnerBackgroundColor,
			circleOutlineColor = new Vector4d(0.549019608, 0.549019608, 0.549019608, 1),
			idleColor = circleOutlineColor,
			accentColor = new Vector4d(1, 1, 1, 1),
			textColor = new Vector4d(1, 1, 1, 1);
	
	// Other stuff	
	private double targetPosX = 0, targetPosY = 0, posX = 0, posY = 0, targetInnerCircleRadius = 0,
			innerCircleRadius = 0, targetOuterCircleRadius = 0, outerCircleRadius = 0, degreesPerCategory = 0,
			selectedScale = 1.075, boxSize = 0;
	private Map<Category, Vector2d> categoryDegreesMap = new HashMap<>();
	private Map<Category, Module[]> categoryModules = new HashMap<>();
	private Category selectedCategory = null, lastHoveredCategory = null; // I am lazy so I'll just reuse the calculations from the render for the mouse click with this variable
	private boolean closeCategoryOnClick = false; // I am still lazy
	private Map<Category, Double> categorySliceScale = new HashMap<>();
	private ResourceLocation closeIcon = new ResourceLocation("Kagu/clickgui/close.png"), toggleBackground = new ResourceLocation("Kagu/clickgui/toggle_background.png"), 
			toggleCircle = new ResourceLocation("Kagu/clickgui/toggle_circle.png"), settingsCog = new ResourceLocation("Kagu/clickgui/settings.png"), 
			modeDropdown = new ResourceLocation("Kagu/clickgui/modeDropdown.png");
	private boolean isLeftMouseDown = false, isLeftMouseClick = false;
	private double scrollOffset = 0, scrollOffsetTarget = 0;
	private Setting selectedSetting = null;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		allowUserInput = true;
		
		FontRenderer boxTitleFr = FontUtils.STRATUM2_MEDIUM_18_AA;
		FontRenderer moduleAndSettingsFr = FontUtils.STRATUM2_MEDIUM_13_AA;
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		
		// Scroll wheel
		scrollOffsetTarget += (Mouse.getDWheel() * 0.15) * (Minecraft.isRunningOnMac ? -1 : 1);
		
		// Draw all the category slices
		for (Category category : Category.values()) {
			
			if (!categorySliceScale.containsKey(category)) {
				categorySliceScale.put(category, Double.valueOf(1));
			}
			
			Vector2d angles = categoryDegreesMap.get(category);
			
			// Fill the white background of the slice
			drawSlice(category, mouseX, mouseY, angles, circleInnerBackgroundColor, circleOuterBackgroundColor);
			
			// Draw an outline around the slice
			drawSlice(category, mouseX, mouseY, angles, circleOutlineColor, circleOutlineColor, true);
			
			// Draw the category name
			FontRenderer categoryFr = FontUtils.STRATUM2_MEDIUM_13_AA;
			{
				double rot = (angles.getX() + angles.getY()) * 0.5;
				double textSpacing = 0.65;
				double textPosX = posX + Math.cos(Math.toRadians(rot)) * ((outerCircleRadius * categorySliceScale.get(category)) * textSpacing);
				double textPosY = posY + Math.sin(Math.toRadians(rot)) * ((outerCircleRadius * categorySliceScale.get(category)) * textSpacing);
				categoryFr.drawString(category.getName().toUpperCase(), textPosX - (categoryFr.getStringWidth(category.getName().toUpperCase()) * 0.5), textPosY, UiUtils.getColorFromVector(textColor));
			}
			
		}
		
		// Draw the module gui, used for configuration and toggling modules
		double boxOffset = ((width * 0.425) * boxSize);
		double boxWidth = width * (0.35);
		
		double left = 0;
		double top = height * 0.15;
		double right = boxWidth;
		double bottom = height * 0.85;
		
		left += width + 1;
		right += width + 1;
		left -= boxOffset;
		right -= boxOffset;
		
		// Draws the main box
		drawRect(left, top, right, bottom, UiUtils.getColorFromVector(circleInnerBackgroundColor));
		drawOutline(left, top, right, bottom, UiUtils.getColorFromVector(circleOutlineColor));
		
		// Scissor for the contents
		UiUtils.enableScissor(left, top, right + 0.5, bottom);
		
		// Draws the title of the category and the separator line under it
		double titlePadding = 2.5;
		
		// Title
		boxTitleFr.drawString(selectedCategory == null ? (Kagu.getName() + " v" + Kagu.getVersion()) : selectedCategory.getName(), left + titlePadding, top + titlePadding, UiUtils.getColorFromVector(textColor));
		
		// Lines
		drawHorizontalLine(left, right + 1, top + boxTitleFr.getFontHeight() + titlePadding, UiUtils.getColorFromVector(circleOutlineColor));
		drawVerticalLine(right - (boxTitleFr.getFontHeight() + titlePadding + 1), top - 1, bottom, UiUtils.getColorFromVector(circleOutlineColor));
		
		// Close button
		closeCategoryOnClick = mouseX > right - (boxTitleFr.getFontHeight() + titlePadding + 1) && mouseX < right && mouseY > top && mouseY < top + boxTitleFr.getFontHeight() + titlePadding;
		mc.getTextureManager().bindTexture(closeIcon);
		double imagePadding = 7;
		double imageSize = right - (right - (boxTitleFr.getFontHeight() + titlePadding + 1)) - imagePadding;
		GL11.glColor4d(idleColor.getX(), idleColor.getY(), idleColor.getZ(), idleColor.getW());
		if (closeCategoryOnClick) {
			GL11.glColor4d(accentColor.getX(), accentColor.getY(), accentColor.getZ(), accentColor.getW());
		}
		drawModalRectWithCustomSizedTexture(right - (boxTitleFr.getFontHeight() + titlePadding + 1) + (imagePadding / 2), top + (imagePadding / 2), 0, 0, imageSize, imageSize, imageSize, imageSize);
		GlStateManager.color(1, 1, 1, 1);
		
		if (selectedCategory != null) {
			
			Module[] modules = categoryModules.get(selectedCategory);
			double padding = 5; // Padding is used on the top, and bottom
			double modsHeight = 0;
			
			// Calculate max height, needed for scrolling
			for (Module mod : modules) {
				modsHeight += moduleAndSettingsFr.getFontHeight() + (padding * 2);
				if (mod.getClickguiExtension() > 0) {
					double settingsHeight = 0;
					for (Setting setting : mod.getSettings()) {
						settingsHeight += moduleAndSettingsFr.getFontHeight() + (padding * 2);
					}
					settingsHeight *= mod.getClickguiExtension();
					modsHeight += settingsHeight;
				}
			}
			
			// So the scroll bar works correctly
			double modeSettingScrollFix = 0;
			
			// Fix scissor
			top += (boxTitleFr.getFontHeight() + titlePadding + 2);
			right -= (boxTitleFr.getFontHeight() + titlePadding + 1);
			UiUtils.enableScissor(left, top, right, bottom);
			
			// Draw all the modules and settings
			double yOffset = scrollOffset;
			double lineLength = moduleAndSettingsFr.getFontHeight() + padding;
			
			// So the user cannot click on item outside of the box
			boolean mouseOutsideOfBox = mouseY < top || mouseY > bottom || mouseX < left || mouseX > right;
			
			for (Module mod : modules) {
				
				// No need to render stuff that the scissor will cut out
				if (yOffset > bottom) {
					break;
				}
				
				// Toggle switch
				double toggleSwitchLength = lineLength * 1.6;
				if (!mouseOutsideOfBox && isLeftMouseClick && mouseX > left && mouseX < left + toggleSwitchLength && mouseY > top + yOffset && mouseY < top + lineLength + yOffset) {
					mod.toggle();
					isLeftMouseClick = false;
				}
				
//				drawRect(left, top + yOffset, left + toggleSwitchLength, top + lineLength + yOffset, -1); // Used to test bounding box of the button
				
				{
					// Switch background
					Vector4d lerpedToggleColor = UiUtils.lerpColor(idleColor, accentColor, mod.getClickguiToggle());
					GL11.glColor4d(lerpedToggleColor.getX(), lerpedToggleColor.getY(), lerpedToggleColor.getZ(), lerpedToggleColor.getW());
					Minecraft.getMinecraft().getTextureManager().bindTexture(toggleBackground);
					drawModalRectWithCustomSizedTexture(left, top + yOffset, 0, 0, toggleSwitchLength, lineLength, toggleSwitchLength, lineLength);
					
					// Switch circle
					Minecraft.getMinecraft().getTextureManager().bindTexture(toggleCircle);
					GL11.glDisable(GL11.GL_BLEND);
					GlStateManager.disableBlend();
					drawModalRectWithCustomSizedTexture(left + (toggleSwitchLength - (toggleSwitchLength * 0.62)) * (1 - mod.getClickguiToggle()), top + yOffset, 0, 0, lineLength, lineLength, lineLength, lineLength);
				}
				
				// Module name
				moduleAndSettingsFr.drawString(mod.getName(), left + toggleSwitchLength, top + yOffset + (padding / 2), UiUtils.getColorFromVector(textColor));
				
				// Settings cog
				if (mod.getSettings().length > 0) {
					
					GlStateManager.pushMatrix();
					{
						// Cog rotation
						GlStateManager.translate(left + toggleSwitchLength + moduleAndSettingsFr.getStringWidth(mod.getName()) + 1 + (lineLength * 0.5), top + yOffset + (lineLength * 0.5), 0);
						GL11.glRotated(90 * mod.getClickguiExtension(), 0, 0, 1);
						GlStateManager.translate(-(left + toggleSwitchLength + moduleAndSettingsFr.getStringWidth(mod.getName()) + 1 + (lineLength * 0.5)), -(top + yOffset + (lineLength * 0.5)), 0);
						
						Vector4d lerpedSettingsColor = UiUtils.lerpColor(idleColor, accentColor, 1 - mod.getClickguiExtension());
						GL11.glColor4d(lerpedSettingsColor.getX(), lerpedSettingsColor.getY(), lerpedSettingsColor.getZ(), lerpedSettingsColor.getW());
						Minecraft.getMinecraft().getTextureManager().bindTexture(settingsCog);
						drawModalRectWithCustomSizedTexture(left + toggleSwitchLength + moduleAndSettingsFr.getStringWidth(mod.getName()) + 1, top + yOffset, 0, 0, lineLength, lineLength, lineLength, lineLength);
						if (!mouseOutsideOfBox && isLeftMouseClick
								&& mouseX >= left + toggleSwitchLength + moduleAndSettingsFr.getStringWidth(mod.getName()) + 1 
								&& mouseX <= left + toggleSwitchLength + moduleAndSettingsFr.getStringWidth(mod.getName()) + 1 + lineLength 
								&& mouseY >= top + yOffset 
								&& mouseY <= top + yOffset + lineLength 
								) {
							mod.setClickguiExtended(!mod.isClickguiExtended());
						}
					}
					GlStateManager.popMatrix();
					
					GlStateManager.pushMatrix();
					
					// Calculate the height for the settings
					double sizeOfSettings = 0;
					double settingOffsetY = 0;
					for (Setting setting : mod.getSettings()) {
						if (setting.isHidden())
							continue;
						sizeOfSettings += lineLength;
						if (setting instanceof ModeSetting) {
							ModeSetting modeSetting = (ModeSetting)setting;
							sizeOfSettings += (lineLength * modeSetting.getModes().size()) * modeSetting.getClickguiToggleStatus();
						}
					}
					settingOffsetY = -(sizeOfSettings * (1 - mod.getClickguiExtension()));
					
					// Draw all the settings and handle mouse movements
					for (Setting setting : mod.getSettings()) {
						if (setting.isHidden())
							continue;
						
						boolean isMouseInsideSettingBox = !mouseOutsideOfBox 
														&& mouseX >= left + toggleSwitchLength
														&& mouseX <= right
														&& mouseY >= top + yOffset + (padding / 2) + lineLength
														&& mouseY < bottom;
						settingOffsetY += lineLength;
						
						// Scissor for the settings
						UiUtils.enableScissor(left + toggleSwitchLength, Math.max(top, Math.min(bottom + 1, top + yOffset + (padding / 2) + lineLength)), right, bottom);
						
						// Draw boolean setting
						if (setting instanceof BooleanSetting) {
							BooleanSetting booleanSetting = (BooleanSetting)setting;
							
							// Switch background
							Vector4d lerpedToggleColor = UiUtils.lerpColor(idleColor, accentColor, booleanSetting.getClickguiToggleStatus());
							GL11.glColor4d(lerpedToggleColor.getX(), lerpedToggleColor.getY(), lerpedToggleColor.getZ(), lerpedToggleColor.getW());
							Minecraft.getMinecraft().getTextureManager().bindTexture(toggleBackground);
							drawModalRectWithCustomSizedTexture(left + (toggleSwitchLength * 0.96), top + yOffset + settingOffsetY, 0, 0, toggleSwitchLength, lineLength, toggleSwitchLength, lineLength);
							
							// Switch circle
							Minecraft.getMinecraft().getTextureManager().bindTexture(toggleCircle);
							GL11.glDisable(GL11.GL_BLEND);
							GlStateManager.disableBlend();
							drawModalRectWithCustomSizedTexture(left + (toggleSwitchLength * 0.96) + (toggleSwitchLength - (toggleSwitchLength * 0.62)) * (1 - booleanSetting.getClickguiToggleStatus()), top + yOffset + settingOffsetY, 0, 0, lineLength, lineLength, lineLength, lineLength);
							GlStateManager.color(1, 1, 1, 1);
							
							// Toggle
							if (isMouseInsideSettingBox && mouseX < left + (toggleSwitchLength * 1.96) && mouseY > top + yOffset + settingOffsetY && mouseY < top + yOffset + settingOffsetY + lineLength 
									&& isLeftMouseClick) {
								booleanSetting.toggle();
							}
							
							// Setting name
							moduleAndSettingsFr.drawString(setting.getName(), left + (toggleSwitchLength * 1.96), top + yOffset + (padding / 2) + settingOffsetY, UiUtils.getColorFromVector(textColor));
						}
						
						// Draw decimal setting
						else if (setting instanceof DoubleSetting) {
							DoubleSetting decimalSetting = (DoubleSetting)setting;
							double value = decimalSetting.getValue();
							double range = decimalSetting.getMax() - decimalSetting.getMin();
							double sliderValue = value - decimalSetting.getMin();
							double sliderPercent = sliderValue / range;
							
							// Change value
							if (selectedSetting == setting && !isLeftMouseDown) {
								selectedSetting = null;
							}
							if (selectedSetting == null && isLeftMouseClick && isMouseInsideSettingBox && mouseY >= top + yOffset + (padding / 2) + settingOffsetY && mouseY <= top + yOffset + (padding / 2) + settingOffsetY + moduleAndSettingsFr.getFontHeight()) {
								selectedSetting = setting;
							}
							if (selectedSetting == setting) {
								double pixelRange = right - (left + toggleSwitchLength);
								double fixedMouseX = mouseX - (left + toggleSwitchLength);
								double newValuePercent = MathHelper.clamp_double(fixedMouseX / pixelRange, 0, 1);
								double newValue = decimalSetting.getMin() + (range * newValuePercent);
								decimalSetting.setValue(newValue);
								sliderPercent = newValuePercent;
							}
							
							// Render bar
							GL11.glEnable(GL11.GL_BLEND);
							GlStateManager.enableBlend();
							drawRect(left + toggleSwitchLength, top + yOffset + (padding / 2) + settingOffsetY,
									left + toggleSwitchLength + ((right - (left + toggleSwitchLength)) * sliderPercent),
									top + yOffset + (padding / 2) + settingOffsetY
											+ moduleAndSettingsFr.getFontHeight(),
									UiUtils.getColorFromVector(new Vector4d(accentColor.getX(), accentColor.getY(),
											accentColor.getZ(), 0.4)));
							
							// Setting name
							moduleAndSettingsFr.drawString(setting.getName() + ": " + value, left + toggleSwitchLength, top + yOffset + (padding / 2) + settingOffsetY, UiUtils.getColorFromVector(textColor));
							
						}
						
						// Draw integer setting, pretty much the same code as the decimal setting but uses ints instead of doubles
						else if (setting instanceof IntegerSetting) {
							IntegerSetting integerSetting = (IntegerSetting)setting;
							int value = integerSetting.getValue();
							int min = integerSetting.getMin();
							int max = integerSetting.getMax();
							int range = max - min;
							double sliderValue = value - min;
							double sliderPercent = sliderValue / range;
							
							// Change value
							if (selectedSetting == setting && !isLeftMouseDown) {
								selectedSetting = null;
							}
							if (selectedSetting == null && isLeftMouseClick && isMouseInsideSettingBox && mouseY >= top + yOffset + (padding / 2) + settingOffsetY && mouseY <= top + yOffset + (padding / 2) + settingOffsetY + moduleAndSettingsFr.getFontHeight()) {
								selectedSetting = setting;
							}
							if (selectedSetting == setting) {
								double pixelRange = right - (left + toggleSwitchLength);
								double fixedMouseX = mouseX - (left + toggleSwitchLength);
								double newValuePercent = MathHelper.clamp_double(fixedMouseX / pixelRange, 0, 1);
								int endResult = (int) Math.round((min + (range * newValuePercent)));
								((IntegerSetting)setting).setValue(endResult);
								sliderPercent = newValuePercent;
							}
							
							// Render bar
							GL11.glEnable(GL11.GL_BLEND);
							GlStateManager.enableBlend();
							drawRect(left + toggleSwitchLength, top + yOffset + (padding / 2) + settingOffsetY,
									left + toggleSwitchLength + ((right - (left + toggleSwitchLength)) * sliderPercent),
									top + yOffset + (padding / 2) + settingOffsetY
											+ moduleAndSettingsFr.getFontHeight(),
									UiUtils.getColorFromVector(new Vector4d(accentColor.getX(), accentColor.getY(),
											accentColor.getZ(), 0.4)));
							
							// Setting name
							moduleAndSettingsFr.drawString(setting.getName() + ": " + value, left + toggleSwitchLength, top + yOffset + (padding / 2) + settingOffsetY, UiUtils.getColorFromVector(textColor));
							
						}
						
						// Draw mode setting
						else if (setting instanceof ModeSetting) {
							ModeSetting modeSetting = (ModeSetting)setting;
							
							if (modeSetting.getModes().size() > 0) {
								
								// Setting name
								moduleAndSettingsFr.drawString(setting.getName() + ": " + modeSetting.getMode(),
										left + toggleSwitchLength, top + yOffset + (padding / 2) + settingOffsetY,
										UiUtils.getColorFromVector(textColor));
								
								// Dropdown icon
								GlStateManager.pushMatrix();
								{
									
									// Rotate icon
									GlStateManager.translate(left + toggleSwitchLength + 2 + moduleAndSettingsFr.getStringWidth(setting.getName() + ": " + modeSetting.getMode()) + (moduleAndSettingsFr.getFontHeight() / 2), top + yOffset + (padding / 2) + settingOffsetY + (moduleAndSettingsFr.getFontHeight() / 2), 0);
									GL11.glRotated(-180 * modeSetting.getClickguiToggleStatus(), 0, 0, 1);
									GlStateManager.translate(-(left + toggleSwitchLength + 2 + moduleAndSettingsFr.getStringWidth(setting.getName() + ": " + modeSetting.getMode()) + (moduleAndSettingsFr.getFontHeight() / 2)), -(top + yOffset + (padding / 2) + settingOffsetY + (moduleAndSettingsFr.getFontHeight() / 2)), 0);
									
									// Draw
									Vector4d lerpedModeColor = UiUtils.lerpColor(idleColor, accentColor, 1 - modeSetting.getClickguiToggleStatus());
									GL11.glColor4d(lerpedModeColor.getX(), lerpedModeColor.getY(), lerpedModeColor.getZ(), lerpedModeColor.getW());
									Minecraft.getMinecraft().getTextureManager().bindTexture(modeDropdown);
									drawModalRectWithCustomSizedTexture(left + toggleSwitchLength + 2 + moduleAndSettingsFr.getStringWidth(setting.getName() + ": " + modeSetting.getMode()),
											top + yOffset + (padding / 2) + settingOffsetY, 0, 0,
											moduleAndSettingsFr.getFontHeight(), moduleAndSettingsFr.getFontHeight(),
											moduleAndSettingsFr.getFontHeight(), moduleAndSettingsFr.getFontHeight());
									GlStateManager.color(1, 1, 1, 1);
									
								}
								GlStateManager.popMatrix();
								
								// Horizontal lines
								{
									double hLineLeft = left + toggleSwitchLength + moduleAndSettingsFr.getStringWidth(setting.getName() + ": ");
									double hLineRight = left + toggleSwitchLength + 2 + moduleAndSettingsFr.getStringWidth(setting.getName() + ": " + modeSetting.getMode()) + moduleAndSettingsFr.getFontHeight();
									hLineRight = hLineLeft + (hLineRight - hLineLeft - 1) * (1 - modeSetting.getClickguiToggleStatus());
									if (hLineRight - hLineLeft > 0.3)
										drawHorizontalLine(hLineLeft, hLineRight, top + yOffset + (padding / 2) + settingOffsetY + moduleAndSettingsFr.getFontHeight(), UiUtils.getColorFromVector(idleColor));
								}
								
								// Change mode
								if (isMouseInsideSettingBox && isLeftMouseClick) {
									if (mouseX >= left + toggleSwitchLength + 2 + moduleAndSettingsFr.getStringWidth(setting.getName() + ":") 
										&& mouseX <= left + toggleSwitchLength + 2 + moduleAndSettingsFr.getStringWidth(setting.getName() + ": " + modeSetting.getMode()) + moduleAndSettingsFr.getFontHeight() 
										&& mouseY >= top + yOffset + (padding / 2) + settingOffsetY 
										&& mouseY <= top + yOffset + (padding / 2) + settingOffsetY + moduleAndSettingsFr.getFontHeight()) {
										modeSetting.setClickguiExtended(!modeSetting.isClickguiExtended());
									}
								}
								
								// Draw modes
								double modeYOffset = -(lineLength * modeSetting.getModes().size()) * (1 - modeSetting.getClickguiToggleStatus());
								double modeScissorTop = top + yOffset + (padding / 2) + settingOffsetY + lineLength;
								double modeScissorBottom = top + yOffset + (padding / 2) + settingOffsetY + lineLength + ((lineLength * modeSetting.getModes().size() * modeSetting.getClickguiToggleStatus()));
								modeScissorBottom = Math.max(top, Math.max(top + yOffset + (padding / 2) + lineLength, Math.min(bottom, modeScissorBottom)));
								modeScissorTop = Math.max(top, Math.max(top + yOffset + (padding / 2) + lineLength, Math.min(bottom, modeScissorTop)));
								UiUtils.enableScissor(left + (toggleSwitchLength * 2) - 5, modeScissorTop, right, modeScissorBottom);
								double modeSettingRemoveAfterDraw = 0;
								
								for (String mode : modeSetting.getModes()) {
									settingOffsetY += lineLength;
									modeSettingRemoveAfterDraw += lineLength * (1 - modeSetting.getClickguiToggleStatus());
									modeSettingScrollFix += lineLength * modeSetting.getClickguiToggleStatus();
									
//									boolean isInsideModeBox = isMouseInsideSettingBox && mouseX > left + (toggleSwitchLength * 2) && mouseX < left + (toggleSwitchLength * 2) + moduleAndSettingsFr.getStringWidth(mode) + 5 
//											&& mouseY > modeScissorTop && mouseX < modeScissorBottom;
									boolean isInsideModeBox = isMouseInsideSettingBox && mouseX > left + (toggleSwitchLength * 2) - 5 && mouseX < right 
											&& mouseY > modeScissorTop && mouseY < modeScissorBottom;
									
									// Draw mode
									moduleAndSettingsFr.drawString(mode, left + (toggleSwitchLength * 2), top + yOffset + (padding / 2) + settingOffsetY + modeYOffset, UiUtils.getColorFromVector(textColor));
									
									// Click mode
									if (isInsideModeBox && mouseY >= top + yOffset + (padding / 2) + settingOffsetY + modeYOffset && mouseY <= top + yOffset + (padding / 2) + settingOffsetY + modeYOffset + lineLength) {
										if (isLeftMouseClick) {
											modeSetting.setMode(mode);
											modeSetting.setClickguiExtended(false);
										}
										drawVerticalLine(left + (toggleSwitchLength * 2) - 5, top + yOffset + (padding / 2) + settingOffsetY + modeYOffset, top + yOffset + (padding / 2) + settingOffsetY + modeYOffset + moduleAndSettingsFr.getFontHeight(), UiUtils.getColorFromVector(accentColor));
									}
									
								}
								settingOffsetY -= modeSettingRemoveAfterDraw;
								
							}
							else {
								
								// Setting name
								moduleAndSettingsFr.drawString(setting.getName(), left + toggleSwitchLength, top + yOffset + (padding / 2) + settingOffsetY, UiUtils.getColorFromVector(textColor));
								
							}
							
						}
						// Draw keybind setting
						else if (setting instanceof KeybindSetting) {
							KeybindSetting keybindSetting = (KeybindSetting)setting;
							
							String text = setting.getName() + ": " + Keyboard.getKeyName(keybindSetting.getKeybind());
							String otherText = setting.getName() + ": ";
							
							// Setting name
							double size = moduleAndSettingsFr.drawString(otherText,
									left + toggleSwitchLength, top + yOffset + (padding / 2) + settingOffsetY,
									UiUtils.getColorFromVector(textColor));
							Vector4d lerpedModeColor = UiUtils.lerpColor(idleColor, accentColor, keybindSetting.getClickguiAnimation());
							moduleAndSettingsFr.drawString(Keyboard.getKeyName(keybindSetting.getKeybind()),
									left + toggleSwitchLength + size, top + yOffset + (padding / 2) + settingOffsetY,
									UiUtils.getColorFromVector(lerpedModeColor));
							
							// Horizontal lines
							{
								double hLineLeft = left + toggleSwitchLength + moduleAndSettingsFr.getStringWidth(setting.getName() + ": ");
								double hLineRight = left + toggleSwitchLength + moduleAndSettingsFr.getStringWidth(text);
								hLineRight = hLineLeft + (hLineRight - hLineLeft - 1) * (keybindSetting.getClickguiAnimation());
								if (hLineRight - hLineLeft > 0.3)
									drawHorizontalLine(hLineLeft, hLineRight, top + yOffset + (padding / 2) + settingOffsetY + moduleAndSettingsFr.getFontHeight(), UiUtils.getColorFromVector(idleColor));
							}
							
							// Select setting
							if (isMouseInsideSettingBox && isLeftMouseClick) {
								if (mouseX >= left + toggleSwitchLength + 2 + moduleAndSettingsFr.getStringWidth(setting.getName() + ":") 
									&& mouseX <= left + toggleSwitchLength + moduleAndSettingsFr.getStringWidth(text)
									&& mouseY >= top + yOffset + (padding / 2) + settingOffsetY 
									&& mouseY <= top + yOffset + (padding / 2) + settingOffsetY + moduleAndSettingsFr.getFontHeight()) {
									selectedSetting = selectedSetting == keybindSetting ? null : keybindSetting;
								}
							}
							
						}
						// Draw slot setting
						else if (setting instanceof SlotSetting) {
							
							// Setting name
							moduleAndSettingsFr.drawString(setting.getName() + ":", left + toggleSwitchLength, top + yOffset + (padding / 2) + settingOffsetY, UiUtils.getColorFromVector(textColor));
							
							// Draw slots
							double slotSize = 0;
							for (int i = 0; i < 9; i++) {
								UiUtils.enableWireframe();
								GL11.glLineWidth(1);
								
								UiUtils.disableWireframe();
							}
						}
						else {
							
							// Setting name
							moduleAndSettingsFr.drawString(setting.getName(), left + toggleSwitchLength, top + yOffset + (padding / 2) + settingOffsetY, UiUtils.getColorFromVector(textColor));
						}
						
					}
					yOffset += sizeOfSettings * mod.getClickguiExtension();
					
					GlStateManager.popMatrix();
					
				}
				// Fix scissor for the rest of the modules
				UiUtils.enableScissor(left, top, right, bottom);
				
				yOffset += lineLength;
				
			}
			
			// Limit the scroll wheel to not go off the bottom of the box
			double trueModsHeight = modsHeight + modeSettingScrollFix - (lineLength * ((bottom - top) / lineLength));
			if (scrollOffsetTarget < -trueModsHeight)
				scrollOffsetTarget = -trueModsHeight;
			if (scrollOffsetTarget > 0)
				scrollOffsetTarget = 0;
			
		}
		
		UiUtils.disableScissor();
		
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
		
        if (isLeftMouseClick) {
        	isLeftMouseClick = false;
        }
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private double currentCategoryScale = 1;
	/**
	 * Draws a single slice of the circle
	 * @param category The category that the slice is for
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 * @param angles The start and end angles for the slice
	 * @param innerColor The color for the inner part of the circle
	 * @param outerColor The color for the outer part of the circle
	 */
	private void drawSlice(Category category, double mouseX, double mouseY, Vector2d angles, Vector4d innerColor, Vector4d outerColor) {
		drawSlice(category, mouseX, mouseY, angles, innerColor, outerColor, false);
	}
	
	/**
	 * Draws a single slice of the circle
	 * @param category The category that the slice is for
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 * @param angles The start and end angles for the slice
	 * @param innerColor The color for the inner part of the circle
	 * @param outerColor The color for the outer part of the circle
	 * @param outline Whether or not the outline is being rendered
	 */
	private void drawSlice(Category category, double mouseX, double mouseY, Vector2d angles, Vector4d innerColor, Vector4d outerColor, boolean outline) {
		
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		double incr = degreesPerCategory * 0.05;
		
		// Get the scale for the slice
		if (category != null) {
			if (!categorySliceScale.containsKey(category))
				categorySliceScale.put(category, Double.valueOf(1));
			currentCategoryScale = categorySliceScale.get(category);
		}
		double scale = currentCategoryScale;
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GL11.glColor4d(1, 1, 1, 1);
		GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
		
		// Draw slice
		worldRenderer.begin(outline ? GL11.GL_LINE_STRIP : GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
		
		if (outline) {
			
			GL11.glLineWidth(3);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			// Render the outline of the slice
			for (double rot = angles.getX(); rot <= angles.getY(); rot += incr) {
				
				// Outer
				double outerPosX = posX + Math.cos(Math.toRadians(rot)) * (outerCircleRadius * scale);
				double outerPosY = posY + Math.sin(Math.toRadians(rot)) * (outerCircleRadius * scale);
				worldRenderer.pos(outerPosX, outerPosY, 0).color(outerColor.getX(), outerColor.getY(), outerColor.getZ(), outerColor.getW()).endVertex();
				
			}
			
			for (double rot = angles.getY(); rot >= angles.getX(); rot -= incr) {
				
				// Inner
				double innerPosX = posX + Math.cos(Math.toRadians(rot)) * innerCircleRadius;
				double innerPosY = posY + Math.sin(Math.toRadians(rot)) * innerCircleRadius;
				worldRenderer.pos(innerPosX, innerPosY, 0).color(innerColor.getX(), innerColor.getY(), innerColor.getZ(), innerColor.getW()).endVertex();
				
			}
			
			// Final line to make all the lines equal size
			double outerPosX = posX + Math.cos(Math.toRadians(angles.getX())) * (outerCircleRadius * scale);
			double outerPosY = posY + Math.sin(Math.toRadians(angles.getX())) * (outerCircleRadius * scale);
			worldRenderer.pos(outerPosX, outerPosY, 0).color(outerColor.getX(), outerColor.getY(), outerColor.getZ(), outerColor.getW()).endVertex();
			
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			
		}else {
			
			// Render solid slice
			for (double rot = angles.getX(); rot <= angles.getY(); rot += incr) {
				
				// Outer
				double outerPosX = posX + Math.cos(Math.toRadians(rot)) * (outerCircleRadius * scale);
				double outerPosY = posY + Math.sin(Math.toRadians(rot)) * (outerCircleRadius * scale);
				worldRenderer.pos(outerPosX, outerPosY, 0).color(outerColor.getX(), outerColor.getY(), outerColor.getZ(), outerColor.getW()).endVertex();
				
				// Inner
				double innerPosX = posX + Math.cos(Math.toRadians(rot)) * innerCircleRadius;
				double innerPosY = posY + Math.sin(Math.toRadians(rot)) * innerCircleRadius;
				worldRenderer.pos(innerPosX, innerPosY, 0).color(innerColor.getX(), innerColor.getY(), innerColor.getZ(), innerColor.getW()).endVertex();
				
			}
			
		}
		
		tessellator.draw();
		
		// If this is not an outline render the the category is not null then do hover checks, if the mouse is hovering over the category then we draw a white gradient over the solid background
		if (category != null && !outline) {
			try {
				
				// Calculate
				double mouseAngle = Math.toDegrees(Math.atan2(posX - mouseX, posY - mouseY)) + 180; // The angle of the mouse relative to the position of the circle
				
				// Fix it to work with my numbers
				mouseAngle = (360 - mouseAngle) + 90;
				if (mouseAngle < 0)
					mouseAngle += 360;
				else if (mouseAngle > 360)
					mouseAngle -= 360;
				
				// Check if the mouse angle is in the slice angles
				if (mouseAngle >= angles.getX() && mouseAngle < angles.getY()) {
					
					// Check the distance of the mouse compared to the circles
					boolean isOutsideInner = Math.sqrt(((posY - mouseY)*(posY - mouseY)) + ((posX - mouseX)*(posX - mouseX))) >= innerCircleRadius;
					boolean isInsideOuter = Math.sqrt(((posY - mouseY)*(posY - mouseY)) + ((posX - mouseX)*(posX - mouseX))) <= (outerCircleRadius * scale);
					
					// Draw
					if (isOutsideInner && isInsideOuter) {
						drawSlice(null, mouseX, mouseY, angles, circleInnerSelectionColor, circleOuterSelectionColor);
						lastHoveredCategory = category;
					}else {
						lastHoveredCategory = null;
					}
					
				}
				
			} catch (Exception e) {
				
			}
		}
		
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableTexture2D();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		// Click
		if (closeCategoryOnClick) {
			selectedCategory = null;
			scrollOffset = 0;
			scrollOffsetTarget = 0;
		}
		else if (lastHoveredCategory != null) {
			selectedCategory = lastHoveredCategory;
			scrollOffset = 0;
			scrollOffsetTarget = 0;
		}
		
		if (mouseButton == 0) {
			isLeftMouseDown = true;
			isLeftMouseClick = true;
		}
		
	}
	
	@Override
	public void onGuiClosed() {
		selectedSetting = null;
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if (state == 0) {
			isLeftMouseDown = false;
			isLeftMouseClick = false;
		}
	}
	
	@Override
	public void initGui() {
		antiFuckupShittyMcCodeIsDogShitAndCantDoAnythingWithoutBreakingNinetyPercentOfMyShit = System.currentTimeMillis();
		if (selectedCategory == null) {
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			posX = sr.getScaledWidth_double() / 2;
			targetPosX = posX;
			posY = sr.getScaledHeight_double() / 2;
			targetPosY = posY;
			outerCircleRadius = 0;
			innerCircleRadius = 0;
		}
		selectedSetting = null;
		
		Mouse.getDWheel();
	}
	
	// For anybody trying to port this to their own client, the cheat tick loop runs at 64 ticks a second
	@EventHandler
	public Handler<EventCheatRenderTick> onPreTick = e -> {
		if (e.isPost() || (!Kagu.getModuleManager().getModule(ModClickGui.class).getMode().is("CS:GO") && Minecraft.getMinecraft().getCurrentScreen() != getInstance()))
			return;
		
		targetOuterCircleRadius = (width * 0.35) * 0.5;
//		targetInnerCircleRadius = targetOuterCircleRadius * 0.175;
		targetInnerCircleRadius = 0;
		if (selectedCategory == null) {
			targetPosX = width * 0.5; // Center the circle
		}else {
			targetPosX = (width * 0.075) + targetOuterCircleRadius; // Push it off to the side
		}
		targetPosY = height / 2;
		
		// Animations
		double animationSpeed = 0.1;
		
		posY = targetPosY;
		
		// Scroll wheel
		if (scrollOffset == scrollOffsetTarget) {}
		else if (scrollOffset > scrollOffsetTarget) {
			scrollOffset -= (scrollOffset - scrollOffsetTarget) * (animationSpeed * 3);
		}else {
			scrollOffset += (scrollOffsetTarget - scrollOffset) * (animationSpeed * 3);
		}
		
		// Pos x
		if (posX == targetPosX) {}
		else if (posX > targetPosX) {
			posX -= (posX - targetPosX) * animationSpeed;
		}else {
			posX += (targetPosX - posX) * animationSpeed;
		}
		
		// Outer circle
		if (outerCircleRadius == targetOuterCircleRadius) {}
		else if (outerCircleRadius > targetOuterCircleRadius) {
			outerCircleRadius -= (outerCircleRadius - targetOuterCircleRadius) * animationSpeed;
		}else {
			outerCircleRadius += (targetOuterCircleRadius - outerCircleRadius) * animationSpeed;
		}
		
		// Inner circle
		if (innerCircleRadius == targetInnerCircleRadius) {}
		else if (innerCircleRadius > targetInnerCircleRadius) {
			innerCircleRadius -= (innerCircleRadius - targetInnerCircleRadius) * animationSpeed;
		}else {
			innerCircleRadius += (targetInnerCircleRadius - innerCircleRadius) * animationSpeed;
		}
		
		// Category animations
		for (Category category : Category.values()) {
			
			if (!categorySliceScale.containsKey(category)) {
				categorySliceScale.put(category, Double.valueOf(1));
			}
			
			double currentExtend = categorySliceScale.get(category);
			double targetExtend = selectedCategory != null && category == selectedCategory ? selectedScale : 1;
			
			if (currentExtend == targetExtend) {}
			else if (currentExtend > targetExtend) {
				currentExtend -= (currentExtend - targetExtend) * animationSpeed;
			}else {
				currentExtend += (targetExtend - currentExtend) * animationSpeed;
			}
			
			categorySliceScale.replace(category, Double.valueOf(currentExtend));
			
		}
		
		// Box animations
		double boxTarget = selectedCategory == null ? 0 : 1;
		if (boxSize == boxTarget) {}
		else if (boxSize > boxTarget) {
			boxSize -= (boxSize - boxTarget) * animationSpeed;
		}else {
			boxSize += (boxTarget - boxSize) * animationSpeed;
		}
		
		// Module animations
		for (Module mod : Kagu.getModuleManager().getModules()) {
			
			// Toggle
			if (mod.isDisabled()) {
				mod.setClickguiToggle(mod.getClickguiToggle() + ((1 - mod.getClickguiToggle()) * animationSpeed));
			}else {
				mod.setClickguiToggle(mod.getClickguiToggle() - (mod.getClickguiToggle() * animationSpeed));
			}
			
			// Expansion
			if (mod.isClickguiExtended()) {
				mod.setClickguiExtension(mod.getClickguiExtension() + ((1 - mod.getClickguiExtension()) * animationSpeed));
			}else {
				mod.setClickguiExtension(mod.getClickguiExtension() - (mod.getClickguiExtension() * animationSpeed));
			}
			
			// Setting animations
			for (Setting setting : mod.getSettings()) {
				
				// Boolean setting animation
				if (setting instanceof BooleanSetting) {
					BooleanSetting booleanSetting = (BooleanSetting)setting;
					if (booleanSetting.isDisabled()) {
						booleanSetting.setClickguiToggleStatus(booleanSetting.getClickguiToggleStatus() + ((1 - booleanSetting.getClickguiToggleStatus()) * animationSpeed));
					}else {
						booleanSetting.setClickguiToggleStatus(booleanSetting.getClickguiToggleStatus() - (booleanSetting.getClickguiToggleStatus() * animationSpeed));
					}
				}
				
				// Mode setting animation
				else if (setting instanceof ModeSetting) {
					ModeSetting modeSetting = (ModeSetting)setting;
					if (modeSetting.isClickguiExtended()) {
						modeSetting.setClickguiToggleStatus(modeSetting.getClickguiToggleStatus() + ((1 - modeSetting.getClickguiToggleStatus()) * animationSpeed));
					}else {
						modeSetting.setClickguiToggleStatus(modeSetting.getClickguiToggleStatus() - (modeSetting.getClickguiToggleStatus() * animationSpeed));
					}
				}
				
				// Keybind setting animation
				else if (setting instanceof KeybindSetting) {
					KeybindSetting keybindSetting = (KeybindSetting)setting;
					if (keybindSetting == selectedSetting) {
						keybindSetting.setClickguiAnimation(keybindSetting.getClickguiAnimation() + ((1 - keybindSetting.getClickguiAnimation()) * animationSpeed));
					}else {
						keybindSetting.setClickguiAnimation(keybindSetting.getClickguiAnimation() - (keybindSetting.getClickguiAnimation() * animationSpeed));
					}
				}
				
			}
			
		}
		
	};
	
	private long antiFuckupShittyMcCodeIsDogShitAndCantDoAnythingWithoutBreakingNinetyPercentOfMyShit = System.currentTimeMillis();
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		if (selectedSetting instanceof KeybindSetting) {
			((KeybindSetting)selectedSetting).setKeybind(keyCode == Keyboard.KEY_ESCAPE ? Keyboard.KEY_NONE : keyCode);
			selectedSetting = null;
			return;
		}
		
		if (keyCode == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(null);
			return;
		}
		
		if (System.currentTimeMillis() - antiFuckupShittyMcCodeIsDogShitAndCantDoAnythingWithoutBreakingNinetyPercentOfMyShit < 100)
			return;
		
        // Kagu hook
        {
        	EventKeyUpdate eventKeyUpdate = new EventKeyUpdate(EventPosition.PRE, keyCode, true);
        	eventKeyUpdate.post();
        	if (eventKeyUpdate.isCanceled()) {
        		return;
        	}
        }
        
	}
	
}
