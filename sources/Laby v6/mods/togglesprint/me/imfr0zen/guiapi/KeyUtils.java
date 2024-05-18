package mods.togglesprint.me.imfr0zen.guiapi;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public final class KeyUtils {

	/**
	 * @return true if either windows ctrl key is down or if either mac meta key
	 *         is down
	 */
	public static boolean isCtrlKeyDown() {
		return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
	}

	/**
	 * @return true if either shift key is down
	 */
	public static boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
	}

	/**
	 * @return true if either alt key is down
	 */
	public static boolean isAltKeyDown() {
		return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
	}

	public static boolean isKeyComboCtrlX(int p_175277_0_) {
		return p_175277_0_ == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
	}

	public static boolean isKeyComboCtrlV(int p_175279_0_) {
		return p_175279_0_ == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
	}

	public static boolean isKeyComboCtrlC(int p_175280_0_) {
		return p_175280_0_ == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
	}

	public static boolean isKeyComboCtrlA(int p_175278_0_) {
		return p_175278_0_ == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
	}

	/**
	 * Stores the given string in the system clipboard
	 */
	public static void setClipboardString(String text) {
		if (!StringUtils.isEmpty(text)) {
			try {
				StringSelection stringselection = new StringSelection(text);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
			} catch (Exception e) {}
		}
	}

	public static String getClipboardString() {
		try {
			Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

			if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return (String) transferable.getTransferData(DataFlavor.stringFlavor);
			}
		} catch (Exception e) {}

		return "";
	}
}
