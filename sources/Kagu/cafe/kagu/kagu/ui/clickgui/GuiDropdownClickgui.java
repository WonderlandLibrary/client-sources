/**
 * 
 */
package cafe.kagu.kagu.ui.clickgui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import cafe.kagu.kagu.eventBus.Event.EventPosition;
import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventCheatRenderTick;
import cafe.kagu.kagu.eventBus.impl.EventKeyUpdate;
import cafe.kagu.kagu.font.FontRenderer;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.mods.Module.Category;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.visual.ModClickGui;
import cafe.kagu.kagu.settings.Setting;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.ColorSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.KeybindSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.MathUtils;
import cafe.kagu.kagu.utils.MiscUtils;
import cafe.kagu.kagu.utils.Shader;
import cafe.kagu.kagu.utils.StencilUtil;
import cafe.kagu.kagu.utils.UiUtils;
import cafe.kagu.kagu.utils.Shader.ShaderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author lavaflowglow
 *
 */
public class GuiDropdownClickgui extends GuiScreen {
	
	private GuiDropdownClickgui() {
		
	}
	
	private static GuiDropdownClickgui instance;
	/**
	 * @return The instance of the clickgui
	 */
	public static GuiDropdownClickgui getInstance() {
		if (instance == null) {
			instance = new GuiDropdownClickgui();
		}
		return instance;
	}
	
	private HashMap<String, BackgroundImage> bgImages = new HashMap<>();
	private boolean isLeftClick = false, isRightClick = false;
	private Setting<?> selectedSetting = null;
	private long antiFuckupShittyMcCodeIsDogShitAndCantDoAnythingWithoutBreakingNinetyPercentOfMyShit = System.currentTimeMillis();
	private BackgroundImage backgroundImage;
	private double bgImageAnimation = 0;
	private final Tab[] TABS = new Tab[Category.values().length];
	private int[] mouseOffsets = new int[2];
	private Tab draggedTab = null;
	private Map<Category, Module[]> categoryModules = new HashMap<>();
	private Object hoveredText = null;
	private double textScrollAnimation = 0, textScrollWidth = 1;
	private ResourceLocation expand = new ResourceLocation("Kagu/dropdownClickgui/expand.png");
	private int[] colorPickerPosition = new int[2];
	
	private static final int TAB_CORNER_SIZE = 5;
	private static final FontRenderer TAB_TITLE_FR = FontUtils.STRATUM2_MEDIUM_15_AA;
	private static final FontRenderer TAB_MODULE_FR = FontUtils.STRATUM2_REGULAR_10_AA;
	
	/**
	 * Called when the client starts
	 */
	public void start() {
		Kagu.getEventBus().subscribe(this);
		
		// Populate the background image resource location data map
		String dropdownImageFolder = "Kagu/dropdownClickgui/bgImage/";
		
		// Furries
		bgImages.put("Fleur 1", new BackgroundImage(dropdownImageFolder + "fleur1.png", "Fleur 1"));
		bgImages.put("Fleur 2", new BackgroundImage(dropdownImageFolder + "fleur2.png", "Fleur 2"));
		bgImages.put("Distasteful", new BackgroundImage(dropdownImageFolder + "dark.png", "Distasteful"));
		bgImages.put("Cheddar 1", new BackgroundImage(dropdownImageFolder + "cheddar1.png", "Cheddar 1"));
		bgImages.put("Cheddar 2", new BackgroundImage(dropdownImageFolder + "cheddar2.png", "Cheddar 2"));
		bgImages.put("Cheddar 3", new BackgroundImage(dropdownImageFolder + "cheddar3.png", "Cheddar 3"));
		bgImages.put("Sylveon 1", new BackgroundImage(dropdownImageFolder + "sylveon1.png", "Sylveon 1"));
		bgImages.put("Vaporeon 1", new BackgroundImage(dropdownImageFolder + "vaporeon1.png", "Vaporeon 1"));
		bgImages.put("Wolf O'Donnell", new BackgroundImage(dropdownImageFolder + "wolf_odonnell.png", "Wolf O'Donnell"));
		bgImages.put("Protogen 1", new BackgroundImage(dropdownImageFolder + "protogen1.png", "Protogen 1")); // https://e621.net/posts/3330093
		bgImages.put("Protogen 2", new BackgroundImage(dropdownImageFolder + "protogen2.png", "Protogen 2")); // https://e621.net/posts/2983700
		bgImages.put("Presto 1", new BackgroundImage(dropdownImageFolder + "presswer1.png", "Presto 1")); // https://twitter.com/presswer/status/1626277757218099201/photo/2
		bgImages.put("Presto 2", new BackgroundImage(dropdownImageFolder + "presswer2.png", "Presto 2")); // https://twitter.com/presswer/status/1621499567652093953/photo/3
		bgImages.put("Presto 3", new BackgroundImage(dropdownImageFolder + "presswer3.png", "Presto 3")); // https://e621.net/posts/3530284?q=presto_%28character%29g
		bgImages.put("Neko Maid 1", new BackgroundImage(dropdownImageFolder + "pawmaid1.png", "Neko Maid 1")); // https://twitter.com/DreamSama/status/1074948080573779968/photo/3
		
		// Anime (mostly femboys)
		bgImages.put("Astolfo 1", new BackgroundImage(dropdownImageFolder + "astolfo1.png", "Astolfo 1"));
		bgImages.put("Astolfo 2", new BackgroundImage(dropdownImageFolder + "astolfo2.png", "Astolfo 2"));
		bgImages.put("Astolfo 3", new BackgroundImage(dropdownImageFolder + "astolfo3.png", "Astolfo 3"));
		bgImages.put("Felix 1", new BackgroundImage(dropdownImageFolder + "felix1.png", "Felix 1"));
		bgImages.put("Felix 2", new BackgroundImage(dropdownImageFolder + "felix2.png", "Felix 2"));
		bgImages.put("Felix 3", new BackgroundImage(dropdownImageFolder + "felix3.png", "Felix 3"));
		bgImages.put("Miku 1", new BackgroundImage(dropdownImageFolder + "miku1.png", "Miku 1"));
		bgImages.put("Miku 2", new BackgroundImage(dropdownImageFolder + "miku2.png", "Miku 2"));
		
		// Other
		bgImages.put("Peter Griffin 1", new BackgroundImage(dropdownImageFolder + "peter1.png", "Peter Griffin 1"));
		bgImages.put("Peter Griffin 2", new BackgroundImage(dropdownImageFolder + "peter2.png", "Peter Griffin 2"));
		bgImages.put("Yoshi 1", new BackgroundImage(dropdownImageFolder + "yoshi1.png", "Yoshi 1"));
		bgImages.put("Yoshi 2", new BackgroundImage(dropdownImageFolder + "yoshi2.png", "Yoshi 2"));
		bgImages.put("Crazy Frog 1", new BackgroundImage(dropdownImageFolder + "crazyfrog1.png", "Crazy Frog 1"));
		bgImages.put("Jeremy Clarkson", new BackgroundImage(dropdownImageFolder + "jeremy_clarkson.png", "Jeremy Clarkson"));
		bgImages.put("Niko 1", new BackgroundImage(dropdownImageFolder + "niko1.png", "Niko 1"));
		
		backgroundImage = bgImages.get(Kagu.getModuleManager().getModule(ModClickGui.class).getMode().getMode());
		resetBackgroundImage();
		
		// Assign modules to their categories
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
		
		// Create all the tabs
		int index = 0;
		for (Category category : Category.values()) {
			TABS[index++] = new Tab(category);
		}
		
		// Load clickgui data
		if (FileManager.CLICKGUI_OPTIONS.exists()) {
			JSONObject json = new JSONObject(FileManager.readStringFromFile(FileManager.CLICKGUI_OPTIONS));
			try {
				for (Tab tab : TABS) {
					JSONObject tabObj = json.getJSONObject(tab.getCategory().getName());
					if (tabObj == null)
						continue; // Tab doesn't have any save data
					tab.setPosX(tabObj.getInt("x"));
					tab.setPosX(tabObj.getInt("y"));
					tab.setExpanded(tabObj.getBoolean("expanded"));
				}
			} catch (Exception e) {
				
			}
			saveClickguiOptions();
		}
		
	}
	
	@Override
	public void initGui() {
		antiFuckupShittyMcCodeIsDogShitAndCantDoAnythingWithoutBreakingNinetyPercentOfMyShit = System.currentTimeMillis();
		selectedSetting = null;
		hoveredText = null;
		textScrollAnimation = 0;
		isLeftClick = false;
		isRightClick = false;
		draggedTab = null;
		Mouse.getDWheel();
		
		// Position the tabs in a semi neat order if they're unset
		boolean setTabPositions = true;
		for (Tab tab : TABS) {
			if ((tab.getPosX() != 0 && tab.getPosX() != 25) || tab.getPosY() != 0)
				setTabPositions = false;
		}
		if (setTabPositions) {
			resetTabs();
		}
		
	}
	
	@Override
	public void onGuiClosed() {
		selectedSetting = null;
		hoveredText = null;
		textScrollAnimation = 0;
		isLeftClick = false;
		isRightClick = false;
		draggedTab = null;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		
		BackgroundImage backgroundImage = this.backgroundImage;
		ModClickGui modClickGui = Kagu.getModuleManager().getModule(ModClickGui.class);
		
		// Draw background color
		drawRect(0, 0, width, height, backgroundImage.getSampledColor());
		
		// Draw background image
		boolean isFlipBgImage = modClickGui.getBgImageFlip().isEnabled();
		double bgImageScale = (height * 0.5 / backgroundImage.getHeight()) * modClickGui.getBgImageScale().getValue();
		mc.getTextureManager().bindTexture(backgroundImage.getResourceLocation());
		double imageWidth = backgroundImage.getWidth() * bgImageScale;
		double imageHeight = backgroundImage.getHeight() * bgImageScale;
		double animationX = 0;
		double animationY = 0;
		switch(modClickGui.getBgImageAnimation().getMode()) {
			case "From Bottom":{
				animationY = imageHeight * (1 - bgImageAnimation);
			}break;
			case "From Side":{
				animationX = imageWidth * (1 - bgImageAnimation);
			}break;
			case "From Corner":{
				animationX = imageWidth * (1 - bgImageAnimation);
				animationY = imageHeight * (1 - bgImageAnimation);
			}break;
		}
		if (isFlipBgImage) {
//			GlStateManager.pushMatrix();
//			GlStateManager.disableCull();
//			GL11.glDisable(GL11.GL_CULL_FACE);
//			GlStateManager.translate(imageWidth - (imageWidth - animationX) + imageWidth / 2, 0, 0);
//			GlStateManager.scale(-1, 1, 1);
//			GlStateManager.translate(-(imageWidth - (imageWidth - animationX) + imageWidth / 2), 0, 0);
			drawTexture(-(animationX), height - imageHeight + animationY, imageWidth, imageHeight, true);
//			GlStateManager.popMatrix();
		}else {
			drawTexture(width - imageWidth + animationX, height - imageHeight + animationY, imageWidth, imageHeight, true);
		}
		
		// Draw all the tabs
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
		int offsetUnits = TABS.length;
		final int tabTitleColor = 0xff0a0611;
		final int textColor = -1;
		FontRenderer tabTitleFontRenderer = TAB_TITLE_FR;
		FontRenderer tabModuleFontRenderer = TAB_MODULE_FR;
		double yOffset = 0;
		int coolColor = backgroundImage.getSampledSolidColor();
		Map<Category, Module[]> categoryModules = this.categoryModules;
		Object hoveredText = null, oldHoveredText = this.hoveredText;
		double textScrollAnimation = this.textScrollAnimation;
		boolean hasHovered = false;
		for (Tab tab : TABS) {
			
			if (draggedTab == tab) {
				tab.setPosX(mouseX - mouseOffsets[0]);
				tab.setPosY(mouseY - mouseOffsets[1]);
				saveClickguiOptions();
			}
			
			GlStateManager.pushMatrix();
			GlStateManager.pushAttrib();
			GlStateManager.translate(tab.getPosX(), tab.getPosY(), 0);
			yOffset = 0;
			double tabWidth = tab.getWidth();
			GL11.glPolygonOffset(1f, -(offsetUnits--)); // Makes the tabs render in the correct order using polygon offsets
			
			// Title
			UiUtils.drawRoundedRect(0, yOffset, tabWidth, yOffset += (tabTitleFontRenderer.getFontHeight() + TAB_CORNER_SIZE * 2), tabTitleColor, TAB_CORNER_SIZE, TAB_CORNER_SIZE, 0d, 0d);
//			tabTitleFontRenderer.drawCenteredString(tab.getCategory().getName(), tab.getWidth() / 2, TAB_CORNER_SIZE, -1);
			tabTitleFontRenderer.drawString(tab.getCategory().getName(), TAB_CORNER_SIZE, TAB_CORNER_SIZE, textColor);
			if (UiUtils.isMouseInsideRoundedRect(mouseX - tab.getPosX(), mouseY - tab.getPosY(), 0, 0, tab.getWidth(), tabTitleFontRenderer.getFontHeight() + TAB_CORNER_SIZE * 2, TAB_CORNER_SIZE, 0)) {
				if (isLeftClick) {
					mouseOffsets[0] = mouseX - tab.getPosX();
					mouseOffsets[1] = mouseY - tab.getPosY();
					draggedTab = tab;
					isLeftClick = false;
				}else if (isRightClick) {
					tab.setExpanded(!tab.isExpanded());
					saveClickguiOptions();
					isRightClick = false;
				}
				hasHovered = true;
			}
			
			// Modules and settings
			Module[] modules = categoryModules.get(tab.getCategory());
			
			// Get total height of tab fully expanded
			double heightOffset = 0;
			for (Module module : modules) {
				
				// Module button height
				heightOffset += 1 + tabModuleFontRenderer.getFontHeight() + 4;
				
			}
			double scissor1 = tab.getPosY() + yOffset;
			yOffset -= heightOffset * tab.getExpandAnimation();
//			GlStateManager.translate(0, -heightOffset * tab.getExpandAnimation(), 0);
			
			// Draw the modules
			for (Module module : modules) {
				UiUtils.enableScissor(tab.getPosX(), scissor1, tab.getPosX() + tabWidth, height);
				
				// Draw module button
				drawRect(0, yOffset, tabWidth, yOffset += 1, coolColor);
				drawRect(0, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, module.isEnabled() ? coolColor : tabTitleColor);
				tabModuleFontRenderer.drawString(module.getName(), TAB_CORNER_SIZE, yOffset + 2, textColor);
				
				if (UiUtils.isMouseInsideRoundedRect(mouseX - tab.getPosX(), mouseY - tab.getPosY() - -heightOffset * tab.getExpandAnimation(), 0, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, 0)) {
					if (isLeftClick) {
						module.toggle();
						isLeftClick = false;
					}else if (isRightClick) {
						module.setClickguiExtended(!module.isClickguiExtended());
						isRightClick = false;
					}
					hasHovered = true;
				}
				
				// Expand icon
				if (module.getSettings().length > 0) {
					int expandIconBorder = 2;
					int size = (int) (tabModuleFontRenderer.getFontHeight() + 4 - expandIconBorder * 2);
					size -= size % 2;
					mc.getTextureManager().bindTexture(expand);
					UiUtils.glColorWithInt(coolColor);
					GlStateManager.pushMatrix();
					GlStateManager.translate(tabWidth - size - expandIconBorder + size / 2, yOffset + expandIconBorder + size / 2, 0);
					GlStateManager.scale(1, (1 - module.getClickguiExtension()) * 2 - 1, 0);
					GlStateManager.rotate(90, 0, 0, 1);
					GlStateManager.translate(-(tabWidth - size - expandIconBorder + size / 2), -(yOffset + expandIconBorder + size / 2), 0);
					GL11.glDisable(GL11.GL_CULL_FACE);
					drawTextureNoColorOverride(tabWidth - size - expandIconBorder, yOffset + expandIconBorder, size, size, true);
					GL11.glEnable(GL11.GL_CULL_FACE);
					GlStateManager.popMatrix();
					GlStateManager.color(1, 1, 1, 1);
				}
				
				yOffset += tabModuleFontRenderer.getFontHeight() + 4;
				
				// Offset for setting height
				double moduleExpand = module.getClickguiExtension();
				double settingOffset = 0;
				for (Setting<?> setting : module.getSettings()) {
					if (setting.isHidden())
						continue;
					if (moduleExpand <= 0.001)
						break;
					settingOffset += tabModuleFontRenderer.getFontHeight() + 4;
				}
				settingOffset *= 1 - moduleExpand;
				double scissor2 = Math.max(tab.getPosY() + yOffset, scissor1);
				yOffset -= settingOffset;
				
				// Draw module settings
				for (Setting<?> setting : module.getSettings()) {
					if (setting.isHidden())
						continue;
					if (moduleExpand <= 0.001)
						break;
					UiUtils.enableScissor(tab.getPosX(), scissor2, tab.getPosX() + tabWidth, height);
					drawRect(0, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor);
					
					// Text scroll animation
					boolean isInsideBasicSettingsRect = false;
					if (!hasHovered && UiUtils.isMouseInsideRoundedRect(mouseX - tab.getPosX(), mouseY - tab.getPosY() - -heightOffset * tab.getExpandAnimation(), 0, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, 0)) {
						hoveredText = setting;
						if (oldHoveredText != hoveredText) {
							textScrollAnimation = 0;
							this.textScrollAnimation = 0;
						}
						hasHovered = true;
						isInsideBasicSettingsRect = true;
					}
					
					double scroll = setting == hoveredText ? -textScrollAnimation : 0;
					final double textIndent = TAB_CORNER_SIZE * 2;
					switch (MiscUtils.getSettingType(setting)) {
						case "bool":{
							String text = setting.getName() + "        ";
							double textWidth = tabModuleFontRenderer.getStringWidth(text);
							UiUtils.enableScissor(tab.getPosX() + textIndent, scissor2, tab.getPosX() + Math.min(textIndent + textWidth, tabWidth), height);
							if (textWidth + textIndent + TAB_CORNER_SIZE < tabWidth)
								scroll = 0;
							if (scroll > 0) {
								textScrollWidth = textWidth;
							}
							tabModuleFontRenderer.drawString(text, textIndent + scroll * textWidth, yOffset + 2, textColor);
							if (textIndent + textWidth > tabWidth) {
								tabModuleFontRenderer.drawString(text, textIndent + textWidth + scroll * textWidth, yOffset + 2, textColor);
								drawGradientRectH(tabWidth - (tabModuleFontRenderer.getFontHeight() + 4) * 2, yOffset, tabWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor, 0x00000000);
							}else if (hoveredText == setting) {
								hoveredText = null;
							}
							if (isLeftClick && isInsideBasicSettingsRect) {
								((BooleanSetting)setting).toggle();
								isLeftClick = false;
							}
							UiUtils.enableScissor(tab.getPosX(), scissor2, tab.getPosX() + tabWidth, height);
							drawRect(tabWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor);
							if (((BooleanSetting)setting).isEnabled()) {
								drawRect(tabWidth - (tabModuleFontRenderer.getFontHeight() + 2), yOffset + 2, tabWidth - 2, yOffset + tabModuleFontRenderer.getFontHeight() + 2, coolColor);
							}
							else {
								drawHorizontalLine(tabWidth - (tabModuleFontRenderer.getFontHeight() + 2), tabWidth - 3, yOffset + 2, coolColor);
								drawHorizontalLine(tabWidth - (tabModuleFontRenderer.getFontHeight() + 2), tabWidth - 3, yOffset + tabModuleFontRenderer.getFontHeight() + 1, coolColor);
								drawVerticalLine(tabWidth - (tabModuleFontRenderer.getFontHeight() + 2), yOffset + 2, yOffset + tabModuleFontRenderer.getFontHeight() + 1, coolColor);
								drawVerticalLine(tabWidth - 3, yOffset + 2, yOffset + tabModuleFontRenderer.getFontHeight() + 1, coolColor);
							}
						}break;
						case "dec":{
							DoubleSetting doubleSetting = (DoubleSetting)setting;
							String text = setting.getName() + ":        ";
							double textWidth = tabModuleFontRenderer.getStringWidth(text);
							
							{ // Slider percent
								double value = doubleSetting.getValue();
								double range = doubleSetting.getMax() - doubleSetting.getMin();
								
								// Drag slider
								if (selectedSetting == setting){
									double newPercent = Math.max(Math.min((mouseX - tab.getPosX()) / tabWidth, 100), 0);
									doubleSetting.setValue((newPercent * range) + doubleSetting.getMin());
								}
								
								// Draw slider
								StencilUtil.enableStencilTest();
								StencilUtil.enableWrite();
								StencilUtil.clearStencil();
								StencilUtil.setTestOutcome(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
								double sliderValue = value - doubleSetting.getMin();
								double sliderPercent = sliderValue / range;
								drawRect(0, yOffset, selectedSetting == setting ? mouseX - tab.getPosX() : tabWidth * sliderPercent, yOffset + tabModuleFontRenderer.getFontHeight() + 4, coolColor);
								StencilUtil.disableWrite();
								StencilUtil.glStencilFunc(GL11.GL_ALWAYS, 0xff);
								StencilUtil.setTestOutcome(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
							}
							
							UiUtils.enableScissor(tab.getPosX() + textIndent, scissor2, tab.getPosX() + Math.min(textIndent + textWidth, tabWidth), height);
							String value = doubleSetting.getValue() + "";
							double valueWidth = tabModuleFontRenderer.getStringWidth(value);
							if (textWidth + textIndent + TAB_CORNER_SIZE < tabWidth - valueWidth)
								scroll = 0;
							if (scroll > 0) {
								textScrollWidth = textWidth;
							}
							tabModuleFontRenderer.drawString(text, textIndent + scroll * textWidth, yOffset + 2, textColor);
							if (textIndent + textWidth > tabWidth - valueWidth) {
								tabModuleFontRenderer.drawString(text, textIndent + textWidth + scroll * textWidth, yOffset + 2, textColor);
								StencilUtil.glStencilFunc(GL11.GL_EQUAL, 0x00);
								StencilUtil.setTestOutcome(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE);
								drawGradientRectH(tabWidth - 4 - valueWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset, tabWidth - 4 - valueWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor, 0x00000000);
								StencilUtil.glStencilFunc(GL11.GL_NOTEQUAL, 0x00);
								float[] coolColorArrayShit = UiUtils.getFloatArrayFromColor(coolColor);
								drawGradientRectH(tabWidth - 4 - valueWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset, tabWidth - 4 - valueWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, coolColor, new Color(coolColorArrayShit[0], coolColorArrayShit[1], coolColorArrayShit[2], 0).getRGB());
								StencilUtil.glStencilFunc(GL11.GL_ALWAYS, 0xff);
								StencilUtil.setTestOutcome(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
							}else if (hoveredText == setting) {
								hoveredText = null;
							}
							if (isLeftClick && isInsideBasicSettingsRect) {
								selectedSetting = setting;
								isLeftClick = false;
							}
							UiUtils.enableScissor(tab.getPosX(), scissor2, tab.getPosX() + tabWidth, height);
							StencilUtil.glStencilFunc(GL11.GL_EQUAL, 0x00);
							StencilUtil.setTestOutcome(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE);
							drawRect(tabWidth - valueWidth - 4, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor);
							StencilUtil.glStencilFunc(GL11.GL_NOTEQUAL, 0x00);
							drawRect(tabWidth - valueWidth - 4, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, coolColor);
							StencilUtil.glStencilFunc(GL11.GL_ALWAYS, 0xff);
							StencilUtil.setTestOutcome(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
							tabModuleFontRenderer.drawString(value, tabWidth - 2 - valueWidth, yOffset + 2, textColor);
							StencilUtil.disableStencilTest();
						}break;
						case "int":{
							IntegerSetting integerSetting = (IntegerSetting)setting;
							String text = setting.getName() + ":        ";
							double textWidth = tabModuleFontRenderer.getStringWidth(text);
							
							{ // Slider percent
								double value = integerSetting.getValue();
								double range = integerSetting.getMax() - integerSetting.getMin();
								
								// Drag slider
								if (selectedSetting == setting){
									double newPercent = Math.max(Math.min((mouseX - tab.getPosX()) / tabWidth, 100), 0);
									integerSetting.setValue((int) (Math.round((newPercent * range) + integerSetting.getMin())));
								}
								
								// Draw slider
								StencilUtil.enableStencilTest();
								StencilUtil.enableWrite();
								StencilUtil.clearStencil();
								StencilUtil.setTestOutcome(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
								double sliderValue = value - integerSetting.getMin();
								double sliderPercent = sliderValue / range;
								drawRect(0, yOffset, selectedSetting == setting ? mouseX - tab.getPosX() : tabWidth * sliderPercent, yOffset + tabModuleFontRenderer.getFontHeight() + 4, coolColor);
								StencilUtil.disableWrite();
								StencilUtil.glStencilFunc(GL11.GL_ALWAYS, 0xff);
								StencilUtil.setTestOutcome(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
							}
							
							UiUtils.enableScissor(tab.getPosX() + textIndent, scissor2, tab.getPosX() + Math.min(textIndent + textWidth, tabWidth), height);
							String value = integerSetting.getValue() + "";
							double valueWidth = tabModuleFontRenderer.getStringWidth(value);
							if (textWidth + textIndent + TAB_CORNER_SIZE < tabWidth - valueWidth)
								scroll = 0;
							if (scroll > 0) {
								textScrollWidth = textWidth;
							}
							tabModuleFontRenderer.drawString(text, textIndent + scroll * textWidth, yOffset + 2, textColor);
							if (textIndent + textWidth > tabWidth - valueWidth) {
								tabModuleFontRenderer.drawString(text, textIndent + textWidth + scroll * textWidth, yOffset + 2, textColor);
								StencilUtil.glStencilFunc(GL11.GL_EQUAL, 0x00);
								StencilUtil.setTestOutcome(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE);
								drawGradientRectH(tabWidth - 4 - valueWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset, tabWidth - 4 - valueWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor, 0x00000000);
								StencilUtil.glStencilFunc(GL11.GL_NOTEQUAL, 0x00);
								float[] coolColorArrayShit = UiUtils.getFloatArrayFromColor(coolColor);
								drawGradientRectH(tabWidth - 4 - valueWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset, tabWidth - 4 - valueWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, coolColor, new Color(coolColorArrayShit[0], coolColorArrayShit[1], coolColorArrayShit[2], 0).getRGB());
								StencilUtil.glStencilFunc(GL11.GL_ALWAYS, 0xff);
								StencilUtil.setTestOutcome(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
							}else if (hoveredText == setting) {
								hoveredText = null;
							}
							if (isLeftClick && isInsideBasicSettingsRect) {
								selectedSetting = setting;
								isLeftClick = false;
							}
							UiUtils.enableScissor(tab.getPosX(), scissor2, tab.getPosX() + tabWidth, height);
							StencilUtil.glStencilFunc(GL11.GL_EQUAL, 0x00);
							StencilUtil.setTestOutcome(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE);
							drawRect(tabWidth - valueWidth - 4, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor);
							StencilUtil.glStencilFunc(GL11.GL_NOTEQUAL, 0x00);
							drawRect(tabWidth - valueWidth - 4, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, coolColor);
							StencilUtil.glStencilFunc(GL11.GL_ALWAYS, 0xff);
							StencilUtil.setTestOutcome(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
							tabModuleFontRenderer.drawString(value, tabWidth - 2 - valueWidth, yOffset + 2, textColor);
							StencilUtil.disableStencilTest();
						}break;
						case "mode":{
							ModeSetting modeSetting = (ModeSetting)setting;
							String text = setting.getName() + ":        ";
							double textWidth = tabModuleFontRenderer.getStringWidth(text);
							
							if ((isLeftClick || isRightClick) && isInsideBasicSettingsRect) {
								modeSetting.setClickguiExtended(!modeSetting.isClickguiExtended());
								isLeftClick = false;
								isRightClick = false;
							}
							
							UiUtils.enableScissor(tab.getPosX() + textIndent, scissor2, tab.getPosX() + Math.min(textIndent + textWidth, tabWidth), height);
							String value = modeSetting.isClickguiExtended() ? "..." : modeSetting.getMode();
							double valueWidth = tabModuleFontRenderer.getStringWidth(value);
							if (textWidth + textIndent + TAB_CORNER_SIZE < tabWidth - valueWidth)
								scroll = 0;
							if (scroll > 0) {
								textScrollWidth = textWidth;
							}
							tabModuleFontRenderer.drawString(text, textIndent + scroll * textWidth, yOffset + 2, textColor);
							if (textIndent + textWidth > tabWidth - valueWidth) {
								tabModuleFontRenderer.drawString(text, textIndent + textWidth + scroll * textWidth, yOffset + 2, textColor);
								drawGradientRectH(tabWidth - 4 - valueWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset, tabWidth - 4 - valueWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor, 0x00000000);
							}else if (hoveredText == setting) {
								hoveredText = null;
							}
							UiUtils.enableScissor(tab.getPosX(), scissor2, tab.getPosX() + tabWidth, height);
							drawRect(tabWidth - valueWidth - 4, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor);
							tabModuleFontRenderer.drawString(value, tabWidth - 2 - valueWidth, yOffset + 2, textColor);
							
							if (modeSetting.getClickguiToggleStatus() <= 0.001)
								break;
							
							// Get height of modes fully expanded
							double scissor3 = Math.max(tab.getPosY() + yOffset + tabModuleFontRenderer.getFontHeight() + 4, scissor2);
							double modesHeightOffset = 0;
							for (String mode : modeSetting.getModes()) {
								modesHeightOffset += tabModuleFontRenderer.getFontHeight() + 4;
							}
							yOffset -= modesHeightOffset * (1 - modeSetting.getClickguiToggleStatus());
							
							// Display modes
							yOffset += tabModuleFontRenderer.getFontHeight() + 4;
							for (String mode : modeSetting.getModes()) {
								UiUtils.enableScissor(tab.getPosX(), scissor3, tab.getPosX() + tabWidth, height);
								
								boolean isOverMode = UiUtils.isMouseInsideRoundedRect(mouseX - tab.getPosX(), mouseY - tab.getPosY() - -heightOffset * tab.getExpandAnimation(), 0, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, 0);
								
								drawRect(0, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor);
								drawRect(textIndent * 2 - 5, yOffset, textIndent * 2 - (isOverMode ? 3 : 4), yOffset + tabModuleFontRenderer.getFontHeight() + 4, coolColor);
								tabModuleFontRenderer.drawString(mode, textIndent * 2, yOffset + 2, textColor);
								
								if (isLeftClick && isOverMode) {
									modeSetting.setMode(mode);
									modeSetting.setClickguiExtended(false);
									isLeftClick = false;
								}
								
								yOffset += tabModuleFontRenderer.getFontHeight() + 4;
							}
							yOffset -= tabModuleFontRenderer.getFontHeight() + 4;
							
						}break;
						case "bind":{
							KeybindSetting keybindSetting = (KeybindSetting)setting;
							String text = setting.getName() + ":        ";
							double textWidth = tabModuleFontRenderer.getStringWidth(text);
							
							if (isLeftClick && isInsideBasicSettingsRect) {
								selectedSetting = setting;
								isLeftClick = false;
							}
							
							UiUtils.enableScissor(tab.getPosX() + textIndent, scissor2, tab.getPosX() + Math.min(textIndent + textWidth, tabWidth), height);
							String value = selectedSetting == setting ? "..." : Keyboard.getKeyName(keybindSetting.getKeybind());
							double valueWidth = tabModuleFontRenderer.getStringWidth(value);
							if (textWidth + textIndent + TAB_CORNER_SIZE < tabWidth - valueWidth)
								scroll = 0;
							if (scroll > 0) {
								textScrollWidth = textWidth;
							}
							tabModuleFontRenderer.drawString(text, textIndent + scroll * textWidth, yOffset + 2, textColor);
							if (textIndent + textWidth > tabWidth - valueWidth) {
								tabModuleFontRenderer.drawString(text, textIndent + textWidth + scroll * textWidth, yOffset + 2, textColor);
								drawGradientRectH(tabWidth - 4 - valueWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset, tabWidth - 4 - valueWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor, 0x00000000);
							}else if (hoveredText == setting) {
								hoveredText = null;
							}
							if (isLeftClick && isInsideBasicSettingsRect) {
								selectedSetting = setting;
								isLeftClick = false;
							}
							UiUtils.enableScissor(tab.getPosX(), scissor2, tab.getPosX() + tabWidth, height);
							drawRect(tabWidth - valueWidth - 4, yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor);
							tabModuleFontRenderer.drawString(value, tabWidth - 2 - valueWidth, yOffset + 2, textColor);
						}break;
						case "color":{
							String text = setting.getName() + ": #" + Integer.toHexString(UiUtils.convertMcColor(setting.get(ColorSetting.class).getColor())).toUpperCase() + "        ";
							double textWidth = tabModuleFontRenderer.getStringWidth(text);
							UiUtils.enableScissor(tab.getPosX() + textIndent, scissor2, tab.getPosX() + Math.min(textIndent + textWidth, tabWidth), height);
							if (textWidth + textIndent + TAB_CORNER_SIZE < tabWidth)
								scroll = 0;
							if (scroll > 0) {
								textScrollWidth = textWidth;
							}
							tabModuleFontRenderer.drawString(text, textIndent + scroll * textWidth, yOffset + 2, textColor);
							if (textIndent + textWidth > tabWidth) {
								tabModuleFontRenderer.drawString(text, textIndent + textWidth + scroll * textWidth, yOffset + 2, textColor);
								drawGradientRectH(tabWidth - (tabModuleFontRenderer.getFontHeight() + 4) * 2, yOffset, tabWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor, 0x00000000);
							}else if (hoveredText == setting) {
								hoveredText = null;
							}
							if (isLeftClick && isInsideBasicSettingsRect) {
								// TODO add click logic
								isLeftClick = false;
							}
							UiUtils.enableScissor(tab.getPosX(), scissor2, tab.getPosX() + tabWidth, height);
							drawRect(tabWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor);
							drawRect(tabWidth - (tabModuleFontRenderer.getFontHeight() + 2), yOffset + 2, tabWidth - 2, yOffset + tabModuleFontRenderer.getFontHeight() + 2, setting.get(ColorSetting.class).getColor());
						}break;
						default:{
							String text = setting.getName() + "        ";
							double textWidth = tabModuleFontRenderer.getStringWidth(text);
							UiUtils.enableScissor(tab.getPosX() + textIndent, scissor2, tab.getPosX() + Math.min(textIndent + textWidth, tabWidth), height);
							if (textWidth + textIndent < tabWidth)
								scroll = 0;
							if (scroll > 0) {
								textScrollWidth = textWidth;
							}
							tabModuleFontRenderer.drawString(text, textIndent + scroll * textWidth, yOffset + 2, textColor);
							if (textIndent + textWidth > tabWidth) {
								tabModuleFontRenderer.drawString(text, textIndent + textWidth + scroll * textWidth, yOffset + 2, textColor);
								drawGradientRectH(tabWidth - (tabModuleFontRenderer.getFontHeight() + 4), yOffset, tabWidth, yOffset + tabModuleFontRenderer.getFontHeight() + 4, tabTitleColor, 0x00000000);
							}
						}break;
					}
					yOffset += tabModuleFontRenderer.getFontHeight() + 4;
				}
				
			}
			UiUtils.disableScissor();
			yOffset--;
			
			// Footer
			drawRect(0, yOffset, tabWidth, yOffset + 1, coolColor);
			if ((isLeftClick || isRightClick) && UiUtils.isMouseInsideRoundedRect(mouseX - tab.getPosX(), mouseY - tab.getPosY(), 0, yOffset + 1 ,tabWidth, yOffset + TAB_CORNER_SIZE * 2, 0, TAB_CORNER_SIZE)) {
				if (isLeftClick) {
					mouseOffsets[0] = mouseX - tab.getPosX();
					mouseOffsets[1] = mouseY - tab.getPosY();
					draggedTab = tab;
					isLeftClick = false;
				}
			}
			UiUtils.drawRoundedRect(0, yOffset + 1 ,tabWidth, yOffset += (TAB_CORNER_SIZE * 2), tabTitleColor, 0d, 0d, TAB_CORNER_SIZE, TAB_CORNER_SIZE);
			
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
			
		}
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
		
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
		
		isLeftClick = false;
		isRightClick = false;
		this.hoveredText = hoveredText;
	}
	
	@EventHandler
	private Handler<EventCheatRenderTick> onCheatRenderTick = e -> {
		if (e.isPost())
			return;
		
		double animationSpeed = 0.1;
		Minecraft mc = Minecraft.getMinecraft();
		
		// bg image animation
		if (mc.getCurrentScreen() instanceof GuiDropdownClickgui) {
			bgImageAnimation += (1 - bgImageAnimation) * animationSpeed;
		}else {
			bgImageAnimation -= bgImageAnimation * animationSpeed;
			return;
		}
		
		// Tab and module animations
		for (Tab tab : TABS) {
			
			// Tab expansion animation
			if (!tab.isExpanded()) {
				tab.setExpandAnimation(tab.getExpandAnimation() + (1 - tab.getExpandAnimation()) * animationSpeed);
			}else {
				tab.setExpandAnimation(tab.getExpandAnimation() - tab.getExpandAnimation() * animationSpeed);
			}
			
			// Module expansion animation
			boolean tabExpanded = tab.isExpanded();
			double tabExpand = tab.getExpandAnimation();
			for (Module module : categoryModules.get(tab.getCategory())) {
				if (module.isClickguiExtended() && tabExpanded) {
					module.setClickguiExtension(module.getClickguiExtension() + (1 - module.getClickguiExtension()) * animationSpeed);
				}else {
					module.setClickguiExtension(module.getClickguiExtension() - module.getClickguiExtension() * animationSpeed);
				}
				
				// ModeSetting expansion animation
				for (Setting<?> setting : module.getSettings()) {
					if (!(setting instanceof ModeSetting))
						continue;
					ModeSetting modeSetting = (ModeSetting)setting;
					if (module.isClickguiExtended() && tabExpanded && modeSetting.isClickguiExtended()) {
						modeSetting.setClickguiToggleStatus(modeSetting.getClickguiToggleStatus() + (1 - modeSetting.getClickguiToggleStatus()) * animationSpeed);
					}else {
						modeSetting.setClickguiToggleStatus(modeSetting.getClickguiToggleStatus() - modeSetting.getClickguiToggleStatus() * animationSpeed);
					}
				}
				
			}
			
		}
		
		// Text scroll animation
		if (hoveredText != null) {
			textScrollAnimation += (0.4 / textScrollWidth) / 100;
			if (textScrollAnimation > 1)
				textScrollAnimation = 0;
		}else {
			textScrollAnimation = 0;
		}
		
	};
	
	/**
	 * Rechecks and sets the background image
	 */
	public void resetBackgroundImage() {
		backgroundImage = bgImages.get(Kagu.getModuleManager().getModule(ModClickGui.class).getBgImage().getMode());
	}
	
	private class Tab {
		
		/**
		 * The category for the tab
		 */
		public Tab(Category category) {
			this.category = category;
			width = (int)Math.ceil(TAB_TITLE_FR.getStringWidth("HHHHHHHH") * 1.5);
			if (width < (int)Math.ceil(TAB_TITLE_FR.getStringWidth(category.getName()) * 1.5))
				width = (int)Math.ceil(TAB_TITLE_FR.getStringWidth(category.getName()) * 1.5);
			
			// Expand the size of the tab if a module name is too big for it
			for (Module module : categoryModules.get(category)) {
				if (Math.ceil(TAB_MODULE_FR.getStringWidth(module.getName())) + TAB_CORNER_SIZE > width)
					width = (int) Math.ceil(TAB_MODULE_FR.getStringWidth(module.getName())) + TAB_CORNER_SIZE * 3;
			}
			
			// Make room for the module extend icons
			width += 5;
			
		}
		
		private Category category;
		private int posX = 0, posY = 0, width = 0;
		private double expandAnimation = 0;
		private boolean expanded = false;

		/**
		 * @return the posX
		 */
		public int getPosX() {
			return posX;
		}

		/**
		 * @param posX the posX to set
		 */
		public void setPosX(int posX) {
			this.posX = posX;
		}

		/**
		 * @return the posY
		 */
		public int getPosY() {
			return posY;
		}

		/**
		 * @param posY the posY to set
		 */
		public void setPosY(int posY) {
			this.posY = posY;
		}

		/**
		 * @return the expandAnimation
		 */
		public double getExpandAnimation() {
			return expandAnimation;
		}

		/**
		 * @param expandAnimation the expandAnimation to set
		 */
		public void setExpandAnimation(double expandAnimation) {
			this.expandAnimation = expandAnimation;
		}

		/**
		 * @return the expanded
		 */
		public boolean isExpanded() {
			return expanded;
		}

		/**
		 * @param expanded the expanded to set
		 */
		public void setExpanded(boolean expanded) {
			this.expanded = expanded;
		}

		/**
		 * @return the category
		 */
		public Category getCategory() {
			return category;
		}

		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}

	}
	
	/**
	 * @author DistastefulBannock
	 *
	 */
	private class BackgroundImage {
		
		/**
		 * @param resourceLocation The resourcelocation for the image
		 * @param name The name of the mode, used for saving config data
		 */
		public BackgroundImage(String resourceLocation, String name) {
			this.name = name;
			this.resourceLocation = new ResourceLocation(resourceLocation);
			
			// Load the image, sample it for the average color, save color and cleanup and streams used
			InputStream in = GuiDropdownClickgui.class.getClassLoader().getResourceAsStream("assets/minecraft/" + resourceLocation);
			if (in == null) {
				throw new IllegalArgumentException("The resource location provided does not point to any real resource, please double check the resource path");
			}
			
			// Load the image from the stream
			BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO.read(in);
			} catch (IOException e1) {
				e1.printStackTrace();
				try {
					in.close();
				} catch (Exception e) {
					
				}
				return;
			}
			
			// Close the stream
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Sample the image for the average color
			long[] data = new long[4];
			int width = bufferedImage.getWidth(), height = bufferedImage.getHeight();
			this.width = width; this.height = height;
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					Color pixel = new Color(bufferedImage.getRGB(x, y));
					if (pixel.getAlpha() <= 10)
						continue;
					data[0] += pixel.getRed();
					data[1] += pixel.getGreen();
					data[2] += pixel.getBlue();
					data[3]++;
				}
			}
			
			// Calculate and save the sampled color
			this.sampledColor = new Color((data[0] / data[3]) / 255f, (data[1] / data[3]) / 255f, (data[2] / data[3]) / 255f, 
					((data[0] / data[3]) / 255f + (data[1] / data[3]) / 255f + (data[2] / data[3]) / 255f) > 2.2 ? WHITE_ALPHA : ALPHA).getRGB();
			
			if (((data[0] / data[3]) / 255f + (data[1] / data[3]) / 255f + (data[2] / data[3]) / 255f) < 1) {
				int brighten = ((data[0] / data[3]) / 255f + (data[1] / data[3]) / 255f + (data[2] / data[3]) / 255f) < 0.5 ? 40 : 25;
				this.sampledSolidColor = new Color(Math.min((data[0] / data[3]) + brighten, 255) / 255f,
						Math.min((data[1] / data[3]) + brighten, 255) / 255f,
						Math.min((data[2] / data[3]) + brighten, 255) / 255f, 1).getRGB();
			}
			else if (((data[0] / data[3]) / 255f + (data[1] / data[3]) / 255f + (data[2] / data[3]) / 255f) > 2) {
				int darken = ((data[0] / data[3]) / 255f + (data[1] / data[3]) / 255f + (data[2] / data[3]) / 255f) > 2.5 ? 40 : 25;
				this.sampledSolidColor = new Color(Math.max((data[0] / data[3]) - darken, 0) / 255f,
						Math.max((data[1] / data[3]) - darken, 0) / 255f,
						Math.max((data[2] / data[3]) - darken, 0) / 255f, 1).getRGB();
			}
			else {
				this.sampledSolidColor = new Color((data[0] / data[3]) / 255f, (data[1] / data[3]) / 255f, (data[2] / data[3]) / 255f, 1).getRGB();
			}
			
		}
		
		private final float ALPHA = 75 / 255f;
		private final float WHITE_ALPHA = 35 / 255f;
		
		private ResourceLocation resourceLocation;
		private int width, height, sampledColor, sampledSolidColor;
		private String name;
		
		/**
		 * @return the resourceLocation
		 */
		public ResourceLocation getResourceLocation() {
			return resourceLocation;
		}

		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}

		/**
		 * @return the height
		 */
		public int getHeight() {
			return height;
		}

		/**
		 * @return the sampledColor
		 */
		public int getSampledColor() {
			return sampledColor;
		}
		
		/**
		 * @return the sampleSolidColor
		 */
		public int getSampledSolidColor() {
			return sampledSolidColor;
		}
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		switch (mouseButton) {
			case 0:{
				isLeftClick = true;
			}break;
			case 1:{
				isRightClick = true;
			}break;
		}
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		switch (state) {
			case 0:{
				draggedTab = null;
				if (!(selectedSetting instanceof KeybindSetting)) {
					if (selectedSetting == Kagu.getModuleManager().getModule(ModClickGui.class).getBgImageScale()) {
						saveClickguiOptions();
					}
					selectedSetting = null;
				}
			}break;
		}
	}
	
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
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	/**
	 * Resets the tab positioning
	 */
	public void resetTabs() {
		if (Kagu.getModuleManager().getModule(ModClickGui.class).getBgImageFlip().isEnabled()) {
			int spaceShift = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() - 25;
			ArrayList<Tab> tempTabs = new ArrayList<>(Arrays.asList(TABS));
			Collections.reverse(tempTabs);
			for (Tab tab : tempTabs) {
				tab.setPosX(spaceShift - tab.getWidth());
				tab.setPosY(25);
				spaceShift -= (tab.getWidth() * 1.1);
			}
		}else {
			int spaceShift = 25;
			for (Tab tab : TABS) {
				tab.setPosX(spaceShift);
				tab.setPosY(25);
				spaceShift += (tab.getWidth() * 1.1);
			}
		}
		saveClickguiOptions();
	}
	
	/**
	 * Saves the clickgui mode and tab positions
	 */
	public void saveClickguiOptions() {
		try {
			JSONObject json = new JSONObject();
			json.put("mode", Kagu.getModuleManager().getModule(ModClickGui.class).getMode().getMode());
			json.put("bgAnimation", Kagu.getModuleManager().getModule(ModClickGui.class).getBgImageAnimation().getMode());
			json.put("bgScale", Kagu.getModuleManager().getModule(ModClickGui.class).getBgImageScale().getValue());
			json.put("bgFlip", Kagu.getModuleManager().getModule(ModClickGui.class).getBgImageFlip().isEnabled());
			json.put("image", backgroundImage.getName());
			for (Tab tab : TABS) {
				JSONObject tabObj = new JSONObject();
				tabObj.put("x", tab.getPosX());
				tabObj.put("y", tab.getPosY());
				tabObj.put("expanded", tab.isExpanded());
				json.put(tab.getCategory().getName(), tabObj);
			}
			FileManager.writeStringToFile(FileManager.CLICKGUI_OPTIONS, json.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
