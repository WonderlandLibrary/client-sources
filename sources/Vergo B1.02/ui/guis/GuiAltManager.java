package xyz.vergoclient.ui.guis;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xyz.vergoclient.files.impl.FileAlts;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.util.main.ColorUtils;
import xyz.vergoclient.util.main.GuiUtils;
import xyz.vergoclient.util.main.RenderUtils2;
import xyz.vergoclient.util.datas.DataDouble5;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.opengl.GL11.*;

public class GuiAltManager extends GuiScreen {

	private static GuiAltManager guiAltManager = new GuiAltManager();
	public static GuiAltManager getGuiAltManager() {
		return guiAltManager;
	}

	public static FileAlts altsFile = new FileAlts();

	public static transient FileAlts.Alt selectedAlt = null;
	public static transient boolean isAddingAlt = false, isLoggingIntoAlt = false, isAltSelected = false;
	public static transient Button selectedTextBox = null, emailTextBox = null, passwordTextBox = null;
	public static transient CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>(),
			addAltButtons = new CopyOnWriteArrayList<>(),
			altButtons = new CopyOnWriteArrayList<>(),
			altOptionButtons = new CopyOnWriteArrayList<>();
	public static transient double scroll = 0, scrollTarget = 0;
	public static transient long deadAltMessage = Long.MIN_VALUE;

	public static class Button {
		public DataDouble5 posAndColor = new DataDouble5();
		public double hoverAnimation = 0;
		public String displayText = "Default text", otherText = "";
		public boolean isEnabled = true, drawTextAsPassword = false, bool1 = false;
		public FileAlts.Alt alt = null;
		public Runnable action = new Runnable() {@Override public void run() {System.out.println("");}};
	}

	public float boxResWidth = 0;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		// Draws the entire background.
		//RenderUtils2.drawRect(0, 0, width, height, new Color(20, 20, 20).getRGB());

		// Smooth Shadow Spell
		glDisable(GL_ALPHA_TEST);

		// Background
		//RenderUtils2.drawRect(0, 0, width, height, new Color(10, 10, 10).getRGB());

		// Top
		RenderUtils2.drawRect(0, 0, this.width, 45, new Color(10, 10, 10).getRGB());

		// Shadow-Top
		ColorUtils.glDrawFilledQuad(0, 45, this.width, 4, 0x96000000, 0);

		JelloFontRenderer comNorm = FontUtil.comfortaaHuge;
		JelloFontRenderer comRNorm = FontUtil.comfortaaNormal;
		JelloFontRenderer comSmall = FontUtil.comfortaaSmall;

		String altText = "Vergo Alt Manager";
		String altAmnt = String.format("You Have \2476%s\247r Alts.", altsFile.alts.size());

		// Bottom
		ColorUtils.glDrawFilledQuad(0, this.height - 58, this.width, 58, 0xFF090909);

		// Bottom shadow
		ColorUtils.glDrawFilledQuad(0, this.height - 62, this.width, 4, 0, 0x96000000);

		glEnable(GL_ALPHA_TEST);

		mc.fontRendererObj.drawStringWithShadow("\2477Current Account: \247B" + mc.getSession().getUsername(), 4, 4, 0xFFFFFFFF);

		comNorm.drawString(altText, width / 2 - (comNorm.getStringWidth(altText) / 2), 13, -1);
		comSmall.drawString(altAmnt, width / 2 - (comSmall.getStringWidth(altAmnt) / 2), 33, -1);

		scrollTarget += Mouse.getDWheel();
		scroll += (scrollTarget - scroll) / 1;

		if (scroll >= 0) {
			scrollTarget = 0;
		}
		if (scroll < (((altsFile.alts.size() - (height / ((height / 12) + 5	))) * ((height / 12) + 5)) + 5 + (height / 12)) * -1) {
			if (altsFile.alts.size() >= (height / ((height / 12) + 5))) {
				scrollTarget = (((altsFile.alts.size() - (height / ((height / 12) + 5))) * ((height / 12) + 5)) + 5 + (height / 12)) * -1;
			}else {
				scrollTarget = 0;
			}
		}

		// All the font renderers
		JelloFontRenderer fr1 = FontUtil.arialMedium;
		JelloFontRenderer fr2 = FontUtil.arialRegular;
		JelloFontRenderer fr3 = FontUtil.neurialGrotesk;


		// Creates the alt buttons
		CopyOnWriteArrayList<Button> altButtons = new CopyOnWriteArrayList<>();

		int altButtonOffset = 0;
		CopyOnWriteArrayList<FileAlts.Alt> alteningAlts = new CopyOnWriteArrayList<FileAlts.Alt>();
		for (FileAlts.Alt alt : altsFile.alts) {

			if (alt.email.toLowerCase().contains("@alt.com")) {
				//System.out.println("Removed " + alt.email + " because it's an altening token and not a real alt");
				alteningAlts.add(alt);
				continue;
			}

			Button altButton = new Button();
			DataDouble5 altPos = new DataDouble5();
			altPos.x1 = width / 2 - 81;
			altPos.x2 = ((width / 8) * 5) - 10;
			altPos.y1 = 45 + (altButtonOffset * ((height / 12) + 5)) + 5 + scroll;
			altPos.y2 = 50 + (altButtonOffset * ((height / 12) + 5)) + 5 + (height / 12) + scroll;
			altButton.posAndColor = altPos;
			altButton.alt = alt;
			altButton.action = new Runnable() {

				@Override
				public void run() {

					if (alt.password.isEmpty()) {
						//SessionChanger.getInstance().setUserOffline(alt.username);
					}else {
						new Thread("ALT-SWITCH-LOG") {
							@Override
							public void run() {
								isLoggingIntoAlt = true;
								String username = mc.session.getUsername();
								//SessionChanger.getInstance().setUser(alt.email, alt.password);
								if (!username.equals(mc.session.getUsername()))
									alt.username = mc.session.getUsername();
								else
									deadAltMessage = System.currentTimeMillis() + 2500;
								isLoggingIntoAlt = false;
							}
						}.start();
					}
				}
			};

			if (altPos.y2 < 0) {
				altButtonOffset++;
				continue;
			}

			if (altPos.y1 > height) {
				break;
			}

			altButtons.add(altButton);

			altButtonOffset++;

		}
		altsFile.alts.removeAll(alteningAlts);
		GuiAltManager.altButtons = altButtons;

		// Draws all the alts
		for (Button button : GuiAltManager.altButtons) {

			if(mc.displayWidth <= 1920 && mc.displayHeight <= 1080) {
				boxResWidth = (float) button.posAndColor.x1 - 210;
			} else if(mc.displayWidth >= 2560 && mc.displayHeight <= 1440) {
				boxResWidth = (float) button.posAndColor.x1 - 400;
			}

			//Gui.drawRect(button.posAndColor.x1, button.posAndColor.y1, button.posAndColor.x2, button.posAndColor.y2, Colors.ALT_MANAGER_BUTTONS.getColor());
			if (button.alt.username.equals(mc.session.getUsername())) {
				//Gui.drawRect(button.posAndColor.x2 - 10, button.posAndColor.y1, button.posAndColor.x2, button.posAndColor.y2, Colors.ALT_MANAGER_PURPLE.getColor());

				RenderUtils2.drawBorderedRect((int) button.posAndColor.x1, (int) button.posAndColor.y1, boxResWidth, 40, 1f, new Color(19, 19, 19, 255), new Color(64, 64, 64, 190));
			}
			fr3.drawString(button.alt.username, button.posAndColor.x1 + 5, (float) button.posAndColor.y1 + 5, -1);
			fr3.drawString(button.alt.email, button.posAndColor.x1 + 5, (float) button.posAndColor.y1 + 24, -1);
		}

		// Add the login text box
		if (isAddingAlt) {
			Gui.drawRect(0, 0, width, height, 0x90000000);
			for (Button button : addAltButtons) {
				RenderUtils2.drawBorderedRect((int) button.posAndColor.x1, (int) button.posAndColor.y1, 640, 76.5f, 1f, new Color(17, 17, 17, 255), new Color(255, 255, 255, 255));
				if (!button.isEnabled) {
					//Gui.drawRect(button.posAndColor.x1 + 5, button.posAndColor.y1 + 5, button.posAndColor.x2 - 5, button.posAndColor.y2 - 5, Colors.ALT_MANAGER_BACKGROUND.getColor());
				}else {
					//Gui.drawRect(button.posAndColor.x1, button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2), button.posAndColor.x2, (button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2)) + (((button.posAndColor.y2 - button.posAndColor.y1) / 2) * button.hoverAnimation), Colors.ALT_MANAGER_PURPLE.getColor());
					//Gui.drawRect(button.posAndColor.x1, (button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2)) - (((button.posAndColor.y2 - button.posAndColor.y1) / 2) * button.hoverAnimation), button.posAndColor.x2, button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2), Colors.ALT_MANAGER_PURPLE.getColor());
//					Gui.drawRect(button.posAndColor.x1, button.posAndColor.y2 - ((button.posAndColor.y2 - button.posAndColor.y1) * button.hoverAnimation), button.posAndColor.x2, button.posAndColor.y2, Colors.ALTMANAGERPURPLE.getColor());
				}
				if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, button.posAndColor) && Mouse.isInsideWindow() && button.isEnabled) {
					RenderUtils2.drawBorderedRect((int) button.posAndColor.x1, (int) button.posAndColor.y1, 640, 76.5f, 1f, new Color(24, 24, 24, 195), new Color(255, 255, 255, 255));
					//button.hoverAnimation += (1 - button.hoverAnimation) / 2;
				}else {
					//button.hoverAnimation += (-button.hoverAnimation) / 2;
				}
				if (button.drawTextAsPassword) {
					String displayText = "";
					for (int i = 0; i < button.displayText.length(); i++) {
						displayText += "*";
					}
					fr1.drawPassword(button.displayText, (float) (button.posAndColor.x1 + ((button.posAndColor.x2 - button.posAndColor.x1) / 2)) - (fr1.getStringWidth(displayText) / 2), (float) (button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2) - fr1.FONT_HEIGHT + 1), -1);
				}else {
					fr1.drawCenteredString(button.displayText, (float) (button.posAndColor.x1 + ((button.posAndColor.x2 - button.posAndColor.x1) / 2)), (float) (button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2) - fr1.FONT_HEIGHT + 1), -1);
				}
			}
		}
		else if (isAltSelected) {
			//Gui.drawRect(0, 0, width, height, 0x90000000);
			for (Button button : altOptionButtons) {
				//Gui.drawRect(button.posAndColor.x1, button.posAndColor.y1, button.posAndColor.x2, button.posAndColor.y2, Colors.ALT_MANAGER_BUTTONS.getColor());
				RenderUtils2.drawBorderedRect((int) button.posAndColor.x1, (int) button.posAndColor.y1, (float) button.posAndColor.x1 - 272, 30, 1f, new Color(57, 57, 57, 195), new Color(255, 255, 255, 255));
				if (!button.isEnabled) {
					//Gui.drawRect(button.posAndColor.x1, button.posAndColor.y1, button.posAndColor.x2, button.posAndColor.y2, Colors.ALT_MANAGER_BACKGROUND.getColor());
				}else {
					//Gui.drawRect(button.posAndColor.x1, button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2), button.posAndColor.x2, (button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2)) + (((button.posAndColor.y2 - button.posAndColor.y1) / 2) * button.hoverAnimation), Colors.ALT_MANAGER_PURPLE.getColor());
					//Gui.drawRect(button.posAndColor.x1, (button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2)) - (((button.posAndColor.y2 - button.posAndColor.y1) / 2) * button.hoverAnimation), button.posAndColor.x2, button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2), Colors.ALT_MANAGER_PURPLE.getColor());
//					Gui.drawRect(button.posAndColor.x1, button.posAndColor.y2 - ((button.posAndColor.y2 - button.posAndColor.y1) * button.hoverAnimation), button.posAndColor.x2, button.posAndColor.y2, Colors.ALTMANAGERPURPLE.getColor());
				}
				if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, button.posAndColor) && Mouse.isInsideWindow() && button.isEnabled) {
					button.hoverAnimation += (1 - button.hoverAnimation) / 2;
				}else {
					button.hoverAnimation += (-button.hoverAnimation) / 2;
				}
				if (button.drawTextAsPassword) {
					String displayText = "";
					for (int i = 0; i < button.displayText.length(); i++) {
						displayText += "*";
					}
					fr1.drawPassword(button.displayText, (float) (button.posAndColor.x1 + ((button.posAndColor.x2 - button.posAndColor.x1) / 2)) - (fr1.getStringWidth(displayText) / 2), (float) (button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2) - fr1.FONT_HEIGHT + 1), -1);
				}else {
					fr1.drawCenteredString(button.displayText, (float) (button.posAndColor.x1 + ((button.posAndColor.x2 - button.posAndColor.x1) / 2)), (float) (button.posAndColor.y1 + ((button.posAndColor.y2 - button.posAndColor.y1) / 2) - fr1.FONT_HEIGHT + 1), -1);
				}
			}
		}

		RenderUtils2.drawRect(0, this.height - 58, this.width, 58, 0xFF090909);

		/*for (Button button : buttons) {
			RenderUtils2.drawBorderedRect((int) button.posAndColor.x1, (int) button.posAndColor.y1, (float) button.posAndColor.x1 - 272, 30, 1f, new Color(255, 255, 255, 0), new Color(255, 255, 255, 255));

			if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, button.posAndColor) && Mouse.isInsideWindow() && button.isEnabled && !isAddingAlt && !isAltSelected) {
				RenderUtils2.drawBorderedRect((int) button.posAndColor.x1, (int) button.posAndColor.y1, (float) button.posAndColor.x1 - 272, 30, 1f, new Color(57, 57, 57, 195), new Color(255, 255, 255, 255));
			}else {
				//button.hoverAnimation += (-button.hoverAnimation) / 2;
			}
			fr1.drawCenteredString(button.displayText, (float) button.posAndColor.x1 + 34, (float) button.posAndColor.y1 + 6, -1);
		}*/

	}

	public void createComponents() {
		buttons.clear();

		// Add Alt button
		Button addAltButton = new Button();
		DataDouble5 addAltPos = new DataDouble5();
		addAltPos.x1 = width / 4 + 20;
		addAltPos.x2 = width / 3 - 20;
		addAltPos.y1 = (height / 1.06) ;
		addAltPos.y2 = (height / 1.06) + 30 ;
		addAltButton.posAndColor = addAltPos;
		addAltButton.displayText = "Add Alt";
		addAltButton.action = new Runnable() {
			@Override
			public void run() {

				addAltButtons.clear();
				selectedTextBox = null;

				Button addAltButton = new Button();
				DataDouble5 addAltPos = new DataDouble5();
				addAltPos.x1 = (width / 8) * 2;
				addAltPos.x2 = (width / 8) * 6;
				addAltPos.y1 = ((height / 9) * 5) + 5;
				addAltPos.y2 = ((height / 9) * 6) + 5;
				addAltButton.posAndColor = addAltPos;
				addAltButton.displayText = "Login To Alt";
				addAltButton.action = new Runnable() {
					@Override
					public void run() {

						FileAlts.Alt newAlt = new FileAlts.Alt();
						newAlt.email = emailTextBox.displayText;
						newAlt.password = passwordTextBox.displayText;
						newAlt.username = "Unchecked";

						if (!passwordTextBox.bool1) {
							newAlt.password = "";
							newAlt.username = newAlt.email;
						}

						altsFile.alts.add(0, newAlt);
						isAddingAlt = false;

					}
				};

				Button passwordTextBox = new Button();
				DataDouble5 passwordTextBoxPos = new DataDouble5();
				passwordTextBoxPos.x1 = (width / 8) * 2;
				passwordTextBoxPos.x2 = (width / 8) * 6;
				passwordTextBoxPos.y1 = ((height / 9) * 4);
				passwordTextBoxPos.y2 = ((height / 9) * 5);
				passwordTextBox.posAndColor = passwordTextBoxPos;
				passwordTextBox.displayText = "Password";
				passwordTextBox.isEnabled = false;
				passwordTextBox.action = new Runnable() {
					@Override
					public void run() {
						if (!passwordTextBox.bool1) {
							passwordTextBox.drawTextAsPassword = true;
							passwordTextBox.bool1 = true;
							passwordTextBox.displayText = "";
						}
						selectedTextBox = passwordTextBox;
					}
				};

				Button emailTextBox = new Button();
				DataDouble5 emailTextBoxPos = new DataDouble5();
				emailTextBoxPos.x1 = (width / 8) * 2;
				emailTextBoxPos.x2 = (width / 8) * 6;
				emailTextBoxPos.y1 = ((height / 9) * 3) - 5;
				emailTextBoxPos.y2 = ((height / 9) * 4) - 5;
				emailTextBox.posAndColor = emailTextBoxPos;
				emailTextBox.displayText = "Email";
				emailTextBox.isEnabled = false;
				emailTextBox.action = new Runnable() {
					@Override
					public void run() {
						if (!emailTextBox.bool1) {
							emailTextBox.bool1 = true;
							emailTextBox.displayText = "";
						}
						selectedTextBox = emailTextBox;
					}
				};

				GuiAltManager.passwordTextBox = passwordTextBox;
				GuiAltManager.emailTextBox = emailTextBox;
				addAltButtons.add(addAltButton);
				addAltButtons.add(passwordTextBox);
				addAltButtons.add(emailTextBox);
				isAddingAlt = true;
			}
		};

		buttons.add(addAltButton);

	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (isAddingAlt) {
			for (Button button : addAltButtons) {
				if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, button.posAndColor)) {
					button.action.run();
				}
			}
		}
		else if (isAltSelected) {
			for (Button button : altOptionButtons) {
				if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, button.posAndColor)) {
					button.action.run();
				}
			}
		}else {
			for (Button button : buttons) {
				if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, button.posAndColor)) {
					button.action.run();
				}
			}
			for (Button button : altButtons) {
				if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, button.posAndColor)) {
					if (mouseButton == 0) {
						button.action.run();
					}
					else if (mouseButton == 1) {
						altOptionButtons.clear();
						selectedAlt = button.alt;

						Button copyAltButton = new Button();
						DataDouble5 copyAltPos = new DataDouble5();
						copyAltPos.x1 = (width / 8) * 2;
						copyAltPos.x2 = (width / 8) * 6;
						copyAltPos.y1 = ((height / 9) * 3) - 5;
						copyAltPos.y2 = ((height / 9) * 4) - 5;
						copyAltButton.posAndColor = copyAltPos;
						copyAltButton.displayText = "Copy alt details";
						copyAltButton.action = new Runnable() {
							@Override
							public void run() {
								setClipboardString(selectedAlt.email + ":" + selectedAlt.password + ":" + selectedAlt.username);
								selectedAlt = null;
								isAltSelected = false;
							}
						};

						Button deleteAltButton = new Button();
						DataDouble5 deleteAltPos = new DataDouble5();
						deleteAltPos.x1 = (width / 8) * 2;
						deleteAltPos.x2 = (width / 8) * 6;
						deleteAltPos.y1 = ((height / 9) * 4);
						deleteAltPos.y2 = ((height / 9) * 5);
						deleteAltButton.posAndColor = deleteAltPos;
						deleteAltButton.displayText = "Remove Alt";
						deleteAltButton.action = new Runnable() {
							@Override
							public void run() {
								altsFile.alts.remove(selectedAlt);
								selectedAlt = null;
								isAltSelected = false;
							}
						};

						Button cancelButton = new Button();
						DataDouble5 cancelPos = new DataDouble5();
						cancelPos.x1 = (width / 8) * 2;
						cancelPos.x2 = (width / 8) * 6;
						cancelPos.y1 = ((height / 9) * 5) + 5;
						cancelPos.y2 = ((height / 9) * 6) + 5;
						cancelButton.posAndColor = cancelPos;
						cancelButton.displayText = "Cancel";
						cancelButton.action = new Runnable() {
							@Override
							public void run() {
								selectedAlt = null;
								isAltSelected = false;
							}
						};

						altOptionButtons.add(copyAltButton);
						altOptionButtons.add(deleteAltButton);
						altOptionButtons.add(cancelButton);

						isAltSelected = true;
					}
				}
			}
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		Keyboard.enableRepeatEvents(true);
		if (isAddingAlt) {
			if (keyCode == Keyboard.KEY_ESCAPE) {
				Keyboard.enableRepeatEvents(false);
				isAddingAlt = false;
			}else {
				if (selectedTextBox != null) {
					if (keyCode == Keyboard.KEY_V && isCtrlKeyDown()) {
						if (getClipboardString().contains(":") && getClipboardString().split(":").length == 2) {
							if (!emailTextBox.bool1) {
								emailTextBox.bool1 = true;
								emailTextBox.displayText = "";
							}
							if (!passwordTextBox.bool1) {
								passwordTextBox.bool1 = true;
								passwordTextBox.displayText = "";
								passwordTextBox.drawTextAsPassword = true;
							}
							emailTextBox.displayText += getClipboardString().split(":")[0];
							passwordTextBox.displayText += getClipboardString().split(":")[1];
						}else {
							if (!selectedTextBox.bool1) {
								selectedTextBox.bool1 = true;
								selectedTextBox.displayText = "";
							}
							selectedTextBox.displayText += getClipboardString();
						}
					}else {
						if (keyCode == Keyboard.KEY_BACK) {
							if (selectedTextBox.displayText.isEmpty()) {

							}
							else if (selectedTextBox.displayText.length() > 0) {
								selectedTextBox.displayText = selectedTextBox.displayText.substring(0, selectedTextBox.displayText.length() - 1);
							}
						}
						else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {

							selectedTextBox.displayText += Character.toString(typedChar);

						}
					}
				}
			}
		}
		else if (isAltSelected) {
			if (keyCode == Keyboard.KEY_ESCAPE) {
				isAltSelected = false;
			}
		}else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	public void initGui() {
		createComponents();
		if (selectedAlt == null) {
			FileAlts.Alt alt = new FileAlts.Alt();
			alt.email = "";
			alt.username = mc.session.getUsername();
			alt.password = "";
		}
	}

}