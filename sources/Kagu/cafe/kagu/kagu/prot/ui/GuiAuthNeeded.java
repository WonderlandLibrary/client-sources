	/**
 * 
 */
package cafe.kagu.kagu.prot.ui;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.font.FontRenderer;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.prot.Note;
import cafe.kagu.kagu.ui.gui.MainMenuHandler;
import cafe.kagu.kagu.ui.widgets.WidgetColorPicker;
import cafe.kagu.kagu.utils.UiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;

/**
 * @author DistastefulBannock
 *
 */
public class GuiAuthNeeded extends GuiScreen {
	
	public GuiAuthNeeded() {
		if (!FileManager.SAVED_CREDENTIALS.exists())
			return;
		try {
			JSONObject json = new JSONObject(FileManager.readStringFromFile(FileManager.SAVED_CREDENTIALS));
			textFields[0] = json.optString("user");
			textFields[1] = json.optString("pass");
			saveCredentials = json.optBoolean("save", false);
		} catch (Exception e) {
			FileManager.SAVED_CREDENTIALS.delete();
		}
	}
	
	private boolean leftClicked = false;
	
	private String[] textFields = new String[] {"", ""};
	private int selectedTextField = -1;
	private long lastTypedMillis = 0;
	private boolean saveCredentials = false;
	private String errorText = "";
	private boolean loggingIn = false;
	private boolean startCheat = false;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		FontRenderer titleFr = FontUtils.STRATUM2_MEDIUM_18_AA;
		FontRenderer buttonFr = FontUtils.STRATUM2_REGULAR_13_AA;
		FontRenderer authorFr = FontUtils.STRATUM2_REGULAR_13_AA;
		
		// Background
		drawRect(0, 0, width, height, 0xff1c161a);
		
		// Main window
		UiUtils.drawRoundedRect(width * 0.4, height * 0.3, width * 0.6, height * 0.7, 0xff292425, 5);
		
		// Title
		titleFr.drawCenteredString(Kagu.getName() + " Auth", width * 0.5, height * 0.3, -1);
		
		GlStateManager.pushMatrix();
		int margin = 5;
		float buttonHeight = buttonFr.getFontHeight() + 4;
		GlStateManager.translate(0, titleFr.getFontHeight() + margin, 0);
		
		for (int i = 0; i < 5; i++) {
			
			switch (i) {
				case 0:
				case 1:{
					UiUtils.drawRoundedRect(width * 0.4 + margin, height * 0.3, width * 0.6 - margin, height * 0.3 + buttonHeight, 0xff393435, 3);
					if (leftClicked && UiUtils.isMouseInsideRoundedRect(mouseX, mouseY - ((buttonHeight + margin) * i) - titleFr.getFontHeight() - margin, width * 0.4 + margin, height * 0.3, width * 0.6 - margin, height * 0.3 + buttonHeight, 3)) {
						selectedTextField = i;
						leftClicked = false;
					}
					String text = textFields[i];
					int textColor = -1;
					if (text.isEmpty() && selectedTextField != i) {
						text = i == 0 ? "Username" : "Password";
						textColor = 0xff9d9d9d;
					}
					if (i == 1 && textColor == -1)
						text = text.replaceAll("(.)", "*");
					if (selectedTextField == i && (System.currentTimeMillis() - lastTypedMillis) % 1000 <= 500)
						text += "_";
					buttonFr.drawString(text, width * 0.4 + margin * 2, height * 0.3 + 1.5, textColor);
				}break;
				case 2:
				case 3 :{
					GL11.glLineWidth(2);
					UiUtils.enableWireframe();
					UiUtils.drawRoundedRect(width * 0.4 + margin, height * 0.3, width * 0.6 - margin, height * 0.3 + buttonHeight, 0xff393435, 3);
					UiUtils.disableWireframe();
					if (leftClicked && UiUtils.isMouseInsideRoundedRect(mouseX, mouseY - ((buttonHeight + margin) * i) - titleFr.getFontHeight() - margin, width * 0.4 + margin, height * 0.3, width * 0.6 - margin, height * 0.3 + buttonHeight, 3)) {
						if (i == 2) {
							saveCredentials ^= true;
						}else if (!loggingIn) {
							
							errorText = "Logging in...";
							loggingIn = true;
							new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									Kagu.getKeyAuth().login(textFields[0], textFields[1], msg -> {
										errorText = msg;
									}, msg -> {
										errorText = "Error 0x" + Integer.toHexString(Note.WINAUTH_RESPONSE_TAMPERED);
										while (true);
									}, msg -> {
										errorText = msg;
									}, msg -> {
										JSONObject json = new JSONObject();
										if (saveCredentials) {
											json.put("user", textFields[0]);
											json.put("pass", textFields[1]);
										}
										json.put("save", saveCredentials);
										FileManager.writeStringToFile(FileManager.SAVED_CREDENTIALS, json.toString());
										Kagu.setLoggedInUser(textFields[0]);
										textFields[0] = "";
										textFields[1] = "";
										System.gc();
										startCheat = true;
									});
									loggingIn = false;
								}
							}, 0);
						}
						leftClicked = false;
					}
					
					if (i == 2) {
						boolean outline = !saveCredentials;
						if (outline) {
							UiUtils.enableWireframe();
						}
						UiUtils.drawRoundedRect(width * 0.4 + margin * 2, height * 0.3 + buttonHeight * 0.5 - margin, width * 0.4 + margin * 4, height * 0.3 + buttonHeight * 0.5 + margin, -1, margin);
						if (outline) {
							UiUtils.disableWireframe();
						}
						buttonFr.drawString("Save Credentials", width * 0.4 + margin * 5, height * 0.3 + 1.5, -1);
					}else {
						buttonFr.drawCenteredString("Log in", width * 0.5, height * 0.3 + 1.5, -1);
					}
					
				}break;
			}
			
			GlStateManager.translate(0, buttonHeight + margin, 0);
		}
		
		GlStateManager.popMatrix();
		
		// Authors
		double centerMultiColorText = authorFr.getStringWidth("Made by DistastefulBannock") / 2;
		double authorOffset = 0;
		authorOffset += authorFr.drawString("Made by ", width * 0.5 - centerMultiColorText + authorOffset, height - authorFr.getFontHeight() - 2, -1);
		authorOffset += authorFr.drawString("DistastefulBannock", width * 0.5 - centerMultiColorText + authorOffset, height - authorFr.getFontHeight() - 2, 0xffd58cff);
		
		// Error text
		if (startCheat) {
			errorText = "Logged in";
		}
		authorFr.drawCenteredString(errorText, width * 0.5, margin, -1);
		
		if (leftClicked) {
			selectedTextField = -1;
			leftClicked = false;
		}
		
		// Start cheat on the gl thread
		if (startCheat) {
			Kagu.start();
			Minecraft.getMinecraft().displayGuiScreen(MainMenuHandler.getMainMenu());
			startCheat = false;
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 0)
			leftClicked = true;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (loggingIn)
			return;
		if (selectedTextField != -1) {
			if (keyCode == Keyboard.KEY_BACK) {
				if (textFields[selectedTextField].isEmpty())
					return;
				textFields[selectedTextField] = textFields[selectedTextField].substring(0, textFields[selectedTextField].length() - 1);
			}else {
				if (keyCode == Keyboard.KEY_TAB) {
					if (selectedTextField < 1) {
						selectedTextField++;
					}else {
						selectedTextField = 0;
					}
					lastTypedMillis = System.currentTimeMillis();
					return;
				}
				if (isCtrlKeyDown() && keyCode == Keyboard.KEY_V) {
					textFields[selectedTextField] += getClipboardString();
					return;
				}
				if (!ChatAllowedCharacters.isAllowedCharacter(typedChar))
					return;
				textFields[selectedTextField] += typedChar;
			}
			lastTypedMillis = System.currentTimeMillis();
		}
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
}
