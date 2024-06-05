package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import de.tired.api.extension.Extension;
import de.tired.api.util.misc.Trie;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.interfaces.FHook;
import de.tired.module.Module;
import de.tired.notification.notificationcenter.NotificationCenter;
import de.tired.tired.Tired;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiChat extends GuiScreen {
	private static final Logger logger = LogManager.getLogger();
	private String historyBuffer = "";

	private float animationY;
	/**
	 * keeps position of which chat message you will select when you press up, (does not increase for duplicated
	 * messages sent immediately after each other)
	 */
	private int sentHistoryCursor = -1;
	private boolean playerNamesFound;
	private boolean waitingOnAutocomplete;
	private int autocompleteIndex;
	private List<String> foundPlayerNames = Lists.<String>newArrayList();
	public NotificationCenter notificationCenter = new NotificationCenter();
	/**
	 * Chat entry field
	 */
	public final ArrayList<String> names = new ArrayList<>();

	protected GuiTextField inputField;

	/**
	 * is the text that appears when you press the chat key and the input box appears pre-filled
	 */
	private String defaultInputFieldText = "";

	public GuiChat() {
	}

	public GuiChat(String defaultText) {
		this.defaultInputFieldText = defaultText;
		for (Module m : Tired.INSTANCE.moduleManager.getModuleList()) {
			names.add(m.name.toLowerCase());
		}

	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		animationY = 0;
		Keyboard.enableRepeatEvents(true);
		this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
		this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
		this.inputField.setMaxStringLength(100);
		this.inputField.setEnableBackgroundDrawing(false);
		this.inputField.setFocused(true);
		this.inputField.setText(this.defaultInputFieldText);
		this.inputField.setCanLoseFocus(false);
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		this.mc.ingameGUI.getChatGUI().resetScroll();
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		this.inputField.updateCursorCounter();
	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		this.waitingOnAutocomplete = false;

		if (keyCode == 15) {
			this.autocompletePlayerNames();
		} else {
			this.playerNamesFound = false;
		}

		if (keyCode == 1) {
			this.mc.displayGuiScreen((GuiScreen) null);
		} else if (keyCode != 28 && keyCode != 156) {
			if (keyCode == 200) {
				this.getSentHistory(-1);
			} else if (keyCode == 208) {
				this.getSentHistory(1);
			} else if (keyCode == 201) {
				this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
			} else if (keyCode == 209) {
				this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
			} else {
				this.inputField.textboxKeyTyped(typedChar, keyCode);
			}
		} else {
			String s = this.inputField.getText().trim();

			if (s.length() > 0) {
				this.sendChatMessage(s);
			}

			this.mc.displayGuiScreen((GuiScreen) null);
		}
	}

	/**
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();

		if (i != 0) {
			if (i > 1) {
				i = 1;
			}

			if (i < -1) {
				i = -1;
			}

			if (!isShiftKeyDown()) {
				i *= 7;
			}

			this.mc.ingameGUI.getChatGUI().scroll(i);
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		notificationCenter.mouseClicked(mouseX, mouseY, mouseButton);
		if (mouseButton == 0) {
			IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

			if (this.handleComponentClick(ichatcomponent)) {
				return;
			}
		}

		this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Sets the text of the chat
	 */
	protected void setText(String newChatText, boolean shouldOverwrite) {
		if (shouldOverwrite) {
			this.inputField.setText(newChatText);
		} else {
			this.inputField.writeText(newChatText);
		}
	}

	public void autocompletePlayerNames() {
		if (this.playerNamesFound) {
			this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());

			if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
				this.autocompleteIndex = 0;
			}
		} else {
			int i = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
			this.foundPlayerNames.clear();
			this.autocompleteIndex = 0;
			String s = this.inputField.getText().substring(i).toLowerCase();
			String s1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
			this.sendAutocompleteRequest(s1, s);

			if (this.foundPlayerNames.isEmpty()) {
				return;
			}

			this.playerNamesFound = true;
			this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
		}

		if (this.foundPlayerNames.size() > 1) {
			StringBuilder stringbuilder = new StringBuilder();

			for (String s2 : this.foundPlayerNames) {
				if (stringbuilder.length() > 0) {
					stringbuilder.append(", ");
				}

				stringbuilder.append(s2);
			}

			this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(stringbuilder.toString()), 1);
		}

		this.inputField.writeText((String) this.foundPlayerNames.get(this.autocompleteIndex++));
	}

	private void sendAutocompleteRequest(String p_146405_1_, String p_146405_2_) {
		if (p_146405_1_.length() >= 1) {
			BlockPos blockpos = null;

			if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				blockpos = this.mc.objectMouseOver.getBlockPos();
			}

			this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_, blockpos));
			this.waitingOnAutocomplete = true;
		}
	}

	/**
	 * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
	 * message from the current cursor position
	 */
	public void getSentHistory(int msgPos) {
		int i = this.sentHistoryCursor + msgPos;
		int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
		i = MathHelper.clamp_int(i, 0, j);

		if (i != this.sentHistoryCursor) {
			if (i == j) {
				this.sentHistoryCursor = j;
				this.inputField.setText(this.historyBuffer);
			} else {
				if (this.sentHistoryCursor == j) {
					this.historyBuffer = this.inputField.getText();
				}

				this.inputField.setText((String) this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
				this.sentHistoryCursor = i;
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		animationY = (float) AnimationUtil.getAnimationState(animationY, +13, 40);
		drawRect(2, this.height - animationY, FHook.fontRenderer.getStringWidth(inputField.getText()) + 10, this.height - 2, Integer.MIN_VALUE);
		Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawPlayerHead(mc.thePlayer, FHook.fontRenderer.getStringWidth(inputField.getText()) + 11, (int) (this.height - animationY), 11, 11);

		final Trie trie = new Trie(names);
		int yAxis = 0;
		final String[] split = inputField.getText().split(" ");
		if (split.length > 1) {
			final String splittedString = split[1];
			final List<String> suggestedSort = trie.suggest(splittedString.toLowerCase());
			suggestedSort.sort((m1, m2) -> FHook.fontRenderer.getStringWidth(m2) - FHook.fontRenderer.getStringWidth(m1));

			if (Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
				if (suggestedSort.size() > 0) {
					if (inputField.getText().toLowerCase().contains(".bind")) {
						inputField.setText(".bind " + suggestedSort.get(0) + " ");
					}
					if (inputField.getText().toLowerCase().contains(".t") || inputField.getText().toLowerCase().contains(".toggle")) {
						inputField.setText(".t " + suggestedSort.get(0) + " ");
					}
				}
			}
			for (String suggested : suggestedSort) {
				if (inputField.getText().toLowerCase().contains(".bind") || inputField.getText().toLowerCase().contains(".t") || inputField.getText().toLowerCase().contains(".toggle")) {
					GlStateManager.resetColor();
					Gui.drawRect(FHook.fontRenderer.getStringWidth(inputField.getText()) - 2, height - 32 - yAxis, FHook.fontRenderer.getStringWidth(inputField.getText()) + FHook.fontRenderer.getStringWidth(suggested) + 2, height - 32 - yAxis + 11, new Color(20, 20, 20).getRGB());
					FHook.fontRenderer.drawStringWithShadow(suggested, FHook.fontRenderer.getStringWidth(inputField.getText()), height - 32 - yAxis, -1);
					yAxis += FHook.fontRenderer.FONT_HEIGHT;
				}
			}
		}

		this.inputField.drawTextBox();

		IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

		if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null) {
			this.handleComponentHover(ichatcomponent, mouseX, mouseY);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void onAutocompleteResponse(String[] p_146406_1_) {
		if (this.waitingOnAutocomplete) {
			this.playerNamesFound = false;
			this.foundPlayerNames.clear();

			for (String s : p_146406_1_) {
				if (s.length() > 0) {
					this.foundPlayerNames.add(s);
				}
			}

			String s1 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
			String s2 = StringUtils.getCommonPrefix(p_146406_1_);

			if (s2.length() > 0 && !s1.equalsIgnoreCase(s2)) {
				this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
				this.inputField.writeText(s2);
			} else if (this.foundPlayerNames.size() > 0) {
				this.playerNamesFound = true;
				this.autocompletePlayerNames();
			}
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}
}
