package io.github.nevalackin.radium.utils;

import net.minecraft.entity.EntityLivingBase;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class StringUtils {

    private StringUtils() {}

    public static String upperSnakeCaseToPascal(String s) {
        return s.charAt(0) + s.substring(1).toLowerCase();
    }

    public static boolean isTeamMate(EntityLivingBase entity) {
        String entName = entity.getDisplayName().getFormattedText();
        String playerName = Wrapper.getPlayer().getDisplayName().getFormattedText();
        if (entName.length() < 2 || playerName.length() < 2) return false;
        if (!entName.startsWith("\247") || !playerName.startsWith("\247")) return false;
        return entName.charAt(1) == playerName.charAt(1);
    }


    public static String getTrimmedClipboardContents() {
        String data = null;
        try {
            data = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException ignored) {
        }

        if (data != null) {
            data = data.trim();

            if (data.indexOf('\n') != -1)
                data = data.replace("\n", "");
        }

        return data;
    }

}
