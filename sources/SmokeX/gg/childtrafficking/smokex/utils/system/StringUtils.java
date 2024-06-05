// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.system;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.security.MessageDigest;
import java.text.DecimalFormat;

public final class StringUtils
{
    private static DecimalFormat formatter;
    
    public static String upperSnakeCaseToPascal(final String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 1) {
            return Character.toString(s.charAt(0));
        }
        return s.charAt(0) + s.substring(1).toLowerCase();
    }
    
    public static String getHWID() {
        try {
            final String toEncrypt = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            final StringBuffer hexString = new StringBuffer();
            final byte[] digest;
            final byte[] byteData = digest = md.digest();
            for (final byte aByteData : digest) {
                final String hex = Integer.toHexString(0xFF & aByteData);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (final Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
    
    public static void copyStringToClipboard(final String lol) {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Clipboard clipboard = toolkit.getSystemClipboard();
        final StringSelection strSel = new StringSelection(lol);
        clipboard.setContents(strSel, null);
    }
    
    public static String getTrimmedClipboardContents() {
        String data = GuiScreen.getClipboardString();
        data = data.trim();
        if (data.indexOf(10) != -1) {
            data = data.replace("\n", "");
        }
        return data;
    }
    
    public static String[] wrapStringToWidth(final String s, final int width) {
        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        final StringBuilder builder = new StringBuilder();
        for (final char c : s.toCharArray()) {
            final String[] lines = builder.toString().split("\n");
            if (fontRenderer.getStringWidth(lines[lines.length - 1]) > width && c == ' ') {
                final int index = builder.lastIndexOf(" ");
                builder.replace(index, index + 1, "\n");
            }
            builder.append(c);
        }
        final String[] lines2 = builder.toString().split("\n");
        if (fontRenderer.getStringWidth(lines2[lines2.length - 1]) > width) {
            final int index2 = builder.lastIndexOf(" ");
            builder.replace(index2, index2 + 1, "\n");
        }
        return builder.toString().split("\n");
    }
    
    public static String formatNumber(final Number number) {
        return StringUtils.formatter.format(number);
    }
    
    static {
        StringUtils.formatter = new DecimalFormat("#,###");
    }
}
