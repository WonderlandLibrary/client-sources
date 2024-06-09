package cafe.kagu.kagu.ui.gui;

import java.io.IOException;

import javax.vecmath.Vector4d;

import org.lwjgl.opengl.GL20;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.font.FontRenderer;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.managers.SessionManager;
import cafe.kagu.kagu.ui.widgets.WidgetColorPicker;
import cafe.kagu.kagu.utils.Shader;
import cafe.kagu.kagu.utils.SoundUtils;
import cafe.kagu.kagu.utils.UiUtils;
import cafe.kagu.kagu.utils.Shader.ShaderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiDefaultMainMenu extends GuiScreen {
	
	protected GuiDefaultMainMenu() {}
	
	/**
	 * Called when the client starts
	 */
	public static void start() {
		
		// Background shader
		try {
			backgroundShader = new Shader(ShaderType.FRAGMENT, FileManager.readStringFromFile(FileManager.BACKGROUND_SHADER));
			backgroundShader.create();
			backgroundShader.link();
			backgroundShader.createUniform("time");
			backgroundShader.createUniform("resolution");
		} catch (Exception e) {
			e.printStackTrace();
		}
		backgroundAnimation = System.currentTimeMillis();
		
	}
	
	private static int buttonPanelBackgroundColor = UiUtils.getColorFromVector(new Vector4d(0.0784313725, 0.0784313725, 0.0784313725, 1)), 
							buttonDefaultColor = UiUtils.getColorFromVector(new Vector4d(0.211764706, 0.211764706, 0.211764706, 1)),
							buttomHoverColor = UiUtils.getColorFromVector(new Vector4d(0.149019608, 0.149019608, 0.149019608, 1));
	private static boolean leftMouseClicked = false;
	private static Shader backgroundShader;
	private static long backgroundAnimation = 0;
	private static WidgetColorPicker colorPicker = null;
	static {
		colorPicker = new WidgetColorPicker(0, 0xffff0000, 0xff00ff00, null, () -> {
			System.out.println(Integer.toHexString(colorPicker.getColor()));
		});
	}
	
	@Override
	public void initGui() {
		leftMouseClicked = false;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!Kagu.isLoggedIn())
			return;
		
//		backgroundAnimation += 0.005f;
		
		FontRenderer titleFr = FontUtils.STRATUM2_MEDIUM_18_AA;
		FontRenderer versionFr = FontUtils.STRATUM2_REGULAR_8_AA;
		FontRenderer buttonFr = FontUtils.STRATUM2_REGULAR_10_AA;
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		
		// Draw the background
		backgroundShader.bind();
		ScaledResolution sr = new ScaledResolution(mc);
		GL20.glUniform2f(backgroundShader.getUniform("resolution"), width * sr.getScaleFactor(), height * sr.getScaleFactor());
		GL20.glUniform1f(backgroundShader.getUniform("time"), (float)(System.currentTimeMillis() - backgroundAnimation) / 1200f);
		drawRect(0, 0, width, height, -1);
		backgroundShader.unbind();
		
		colorPicker.draw(100, 100, 100, 100, mouseX, mouseY);
		
		// Draw menu background
//		UiUtils.drawRoundedRect(width * 0.4 - 1, height * 0.325 - 1, width * 0.6 + 1, height * 0.675 + 1, -1, 10);
		UiUtils.drawRoundedRect(width * 0.4, height * 0.325, width * 0.6, height * 0.675, buttonPanelBackgroundColor, 10);
		
		// Draw the title and version
		titleFr.drawCenteredString(Kagu.getName(), width / 2 - versionFr.getStringWidth("v" + Kagu.getVersion()) / 2, height * 0.33 + 2, -1);
		versionFr.drawCenteredString("v" + Kagu.getVersion(), width / 2 + titleFr.getStringWidth(Kagu.getName()) / 2, height * 0.33 + 2 + titleFr.getFontHeight() - versionFr.getFontHeight(), -1);
		
		// Draw buttons
		int buttonColor = buttonDefaultColor;
		int buttonCount = 7;
		double buttonSpacingSize = height * 0.1;
		double buttonSize = (height * 0.67 - height * 0.33 - buttonSpacingSize) / buttonCount;
		buttonSpacingSize /= buttonCount;
		
		// Singleplayer
		if (UiUtils.isMouseInsideRoundedRect(mouseX, mouseY, width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize + buttonSize, 3)) {
			buttonColor = buttomHoverColor;
			if (leftMouseClicked) {
				mc.displayGuiScreen(new GuiSelectWorld(this));
				leftMouseClicked = false;
				mc.getSoundHandler().playSound(SoundUtils.getClickSound());
			}
		}else {
			buttonColor = buttonDefaultColor;
		}
		UiUtils.drawRoundedRect(width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize + buttonSize, buttonColor, 3);
		buttonFr.drawCenteredString("Singleplayer", width / 2, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize + buttonSize / 2 - buttonFr.getFontHeight() / 2, -1);
		
		// Multiplayer
		if (UiUtils.isMouseInsideRoundedRect(mouseX, mouseY, width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 2 + buttonSize, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 2 + buttonSize * 2, 3)) {
			buttonColor = buttomHoverColor;
			if (leftMouseClicked) {
				mc.displayGuiScreen(new GuiMultiplayer(this));
				leftMouseClicked = false;
				mc.getSoundHandler().playSound(SoundUtils.getClickSound());
			}
		}else {
			buttonColor = buttonDefaultColor;
		}
		UiUtils.drawRoundedRect(width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 2 + buttonSize, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 2 + buttonSize * 2, buttonColor, 3);
		buttonFr.drawCenteredString("Multiplayer", width / 2, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 2 + buttonSize + buttonSize / 2 - buttonFr.getFontHeight() / 2, -1);
		
		// Alt manager
		if (UiUtils.isMouseInsideRoundedRect(mouseX, mouseY, width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 3 + buttonSize * 2, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 3 + buttonSize * 3, 3)) {
			buttonColor = buttomHoverColor;
			if (leftMouseClicked) {
				mc.displayGuiScreen(GuiAltManager.getInstance());
				leftMouseClicked = false;
				mc.getSoundHandler().playSound(SoundUtils.getClickSound());
			}
		}else {
			buttonColor = buttonDefaultColor;
		}
		UiUtils.drawRoundedRect(width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 3 + buttonSize * 2, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 3 + buttonSize * 3, buttonColor, 3);
		buttonFr.drawCenteredString("Alt Manager", width / 2, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 3 + buttonSize * 2 + buttonSize / 2 - buttonFr.getFontHeight() / 2, -1);
		
		// Settings
		if (UiUtils.isMouseInsideRoundedRect(mouseX, mouseY, width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 4 + buttonSize * 3, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 4 + buttonSize * 4, 3)) {
			buttonColor = buttomHoverColor;
			if (leftMouseClicked) {
				mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
				leftMouseClicked = false;
				mc.getSoundHandler().playSound(SoundUtils.getClickSound());
			}
		}else {
			buttonColor = buttonDefaultColor;
		}
		UiUtils.drawRoundedRect(width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 4 + buttonSize * 3, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 4 + buttonSize * 4, buttonColor, 3);
		buttonFr.drawCenteredString("Settings", width / 2, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 4 + buttonSize * 3 + buttonSize / 2 - buttonFr.getFontHeight() / 2, -1);
		
		// Language
		if (UiUtils.isMouseInsideRoundedRect(mouseX, mouseY, width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 5 + buttonSize * 4, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 5 + buttonSize * 5, 3)) {
			buttonColor = buttomHoverColor;
			if (leftMouseClicked) {
				mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
				leftMouseClicked = false;
				mc.getSoundHandler().playSound(SoundUtils.getClickSound());
			}
		}else {
			buttonColor = buttonDefaultColor;
		}
		UiUtils.drawRoundedRect(width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 5 + buttonSize * 4, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 5 + buttonSize * 5, buttonColor, 3);
		buttonFr.drawCenteredString("Language", width / 2, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 5 + buttonSize * 4 + buttonSize / 2 - buttonFr.getFontHeight() / 2, -1);
		
		// Themes
		if (UiUtils.isMouseInsideRoundedRect(mouseX, mouseY, width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 6 + buttonSize * 5, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 6 + buttonSize * 6, 3)) {
			buttonColor = buttomHoverColor;
			if (leftMouseClicked) {
				leftMouseClicked = false;
				mc.getSoundHandler().playSound(SoundUtils.getClickSound());
				String alt = "brandonromans31195@gmail.com:Ditmemay113@@@";
				System.out.println(SessionManager.loginPremium(alt.split(":")[0], alt.split(":")[1]));
			}
		}else {
			buttonColor = buttonDefaultColor;
		}
		UiUtils.drawRoundedRect(width * 0.41, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 6 + buttonSize * 5, width * 0.59, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 6 + buttonSize * 6, buttonColor, 3);
		buttonFr.drawCenteredString("Themes", width / 2, height * 0.33 + titleFr.getFontHeight() + buttonSpacingSize * 6 + buttonSize * 5 + buttonSize / 2 - buttonFr.getFontHeight() / 2, -1);
		
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
		
		if (leftMouseClicked) {
			leftMouseClicked = false;
		}
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		leftMouseClicked = true;
		colorPicker.mouseClick(mouseButton);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		colorPicker.mouseRelease(state);
	}
	
}
