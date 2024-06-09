/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.lwjgl.input.Keyboard
 */
package me.imfr0zen.guiapi;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public final class KeyUtils {
    public static boolean isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? Keyboard.isKeyDown((int)219) || Keyboard.isKeyDown((int)220) : Keyboard.isKeyDown((int)29) || Keyboard.isKeyDown((int)157);
    }

    public static boolean isShiftKeyDown() {
        if (!Keyboard.isKeyDown((int)42) && !Keyboard.isKeyDown((int)54)) {
            return false;
        }
        return true;
    }

    public static boolean isAltKeyDown() {
        if (!Keyboard.isKeyDown((int)56) && !Keyboard.isKeyDown((int)184)) {
            return false;
        }
        return true;
    }

    public static boolean isKeyComboCtrlX(int p_175277_0_) {
        if (p_175277_0_ == 45 && KeyUtils.isCtrlKeyDown() && !KeyUtils.isShiftKeyDown() && !KeyUtils.isAltKeyDown()) {
            return true;
        }
        return false;
    }

    public static boolean isKeyComboCtrlV(int p_175279_0_) {
        if (p_175279_0_ == 47 && KeyUtils.isCtrlKeyDown() && !KeyUtils.isShiftKeyDown() && !KeyUtils.isAltKeyDown()) {
            return true;
        }
        return false;
    }

    public static boolean isKeyComboCtrlC(int p_175280_0_) {
        if (p_175280_0_ == 46 && KeyUtils.isCtrlKeyDown() && !KeyUtils.isShiftKeyDown() && !KeyUtils.isAltKeyDown()) {
            return true;
        }
        return false;
    }

    public static boolean isKeyComboCtrlA(int p_175278_0_) {
        if (p_175278_0_ == 30 && KeyUtils.isCtrlKeyDown() && !KeyUtils.isShiftKeyDown() && !KeyUtils.isAltKeyDown()) {
            return true;
        }
        return false;
    }

    public static void setClipboardString(String text) {
        if (!StringUtils.isEmpty((CharSequence)text)) {
            try {
                StringSelection stringselection = new StringSelection(text);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
            }
            catch (Exception stringselection) {
                // empty catch block
            }
        }
    }

    public static String getClipboardString() {
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (Exception transferable) {
            // empty catch block
        }
        return "";
    }
}

