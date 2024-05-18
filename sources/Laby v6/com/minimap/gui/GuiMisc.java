package com.minimap.gui;

import java.text.*;
import net.minecraft.client.gui.*;

public class GuiMisc
{
    public static final DecimalFormat simpleFormat;
    
    public static void checkField(final GuiTextField field) {
        String text = field.getText();
        final char[] charArray = text.toCharArray();
        final char[] allowed = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-' };
        text = "";
        for (int i = 0; i < charArray.length; ++i) {
            boolean found = false;
            for (int j = 0; j < allowed.length; ++j) {
                if (charArray[i] == allowed[j]) {
                    found = true;
                    break;
                }
            }
            if (!found || (charArray[i] == '-' && i != 0)) {
                charArray[i] = '_';
            }
        }
        for (final char c : charArray) {
            text += ((c != '_') ? c : "");
        }
        field.setText(text);
    }
    
    static {
        simpleFormat = new DecimalFormat("0.0");
    }
}
