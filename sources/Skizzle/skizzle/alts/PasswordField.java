/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.alts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.opengl.GL11;

public class PasswordField
extends Gui {
    public int height;
    public int enabledColor = 0xE0E0E0;
    public int selectionEnd = 0;
    public boolean isEnabled = true;
    public int i = 0;
    public int cursorPosition = 0;
    public boolean enableBackgroundDrawing = true;
    public FontRenderer fontRenderer;
    public String text = "";
    public int maxStringLength = 50;
    public int xPos;
    public int cursorCounter;
    public int yPos;
    public int disabledColor = 0x707070;
    public int width;
    public boolean canLoseFocus = true;
    public boolean isFocused = false;
    public boolean b = true;

    public boolean getEnableBackgroundDrawing() {
        PasswordField Nigga;
        return Nigga.enableBackgroundDrawing;
    }

    public int getCursorPosition() {
        PasswordField Nigga;
        return Nigga.cursorPosition;
    }

    public void setText(String Nigga) {
        PasswordField Nigga2;
        Nigga2.text = Nigga.length() > Nigga2.maxStringLength ? Nigga.substring(0, Nigga2.maxStringLength) : Nigga;
        Nigga2.setCursorPositionEnd();
    }

    public void setCanLoseFocus(boolean Nigga) {
        Nigga.canLoseFocus = Nigga;
    }

    public PasswordField(FontRenderer Nigga, int Nigga2, int Nigga3, int Nigga4, int Nigga5) {
        PasswordField Nigga6;
        Nigga6.fontRenderer = Nigga;
        Nigga6.xPos = Nigga2;
        Nigga6.yPos = Nigga3;
        Nigga6.width = Nigga4;
        Nigga6.height = Nigga5;
    }

    public void setCursorPositionZero() {
        PasswordField Nigga;
        Nigga.setCursorPosition(0);
    }

    public void drawTextBox() {
        PasswordField Nigga;
        if (Nigga.func_73778_q()) {
            if (Nigga.getEnableBackgroundDrawing()) {
                Gui.drawRect(Nigga.xPos - 1, Nigga.yPos - 1, Nigga.xPos + Nigga.width + 1, Nigga.yPos + Nigga.height + 1, -6250336);
                Gui.drawRect(Nigga.xPos, Nigga.yPos, Nigga.xPos + Nigga.width, Nigga.yPos + Nigga.height, -16777216);
            }
            int Nigga2 = Nigga.isEnabled ? Nigga.enabledColor : Nigga.disabledColor;
            int Nigga3 = Nigga.cursorPosition - Nigga.i;
            int Nigga4 = Nigga.selectionEnd - Nigga.i;
            String Nigga5 = Nigga.fontRenderer.trimStringToWidth(Nigga.text.substring(Nigga.i), Nigga.getWidth());
            boolean Nigga6 = Nigga3 >= 0 && Nigga3 <= Nigga5.length();
            boolean Nigga7 = Nigga.isFocused && Nigga.cursorCounter / 6 % 2 == 0 && Nigga6;
            int Nigga8 = Nigga.enableBackgroundDrawing ? Nigga.xPos + 4 : Nigga.xPos;
            int Nigga9 = Nigga.enableBackgroundDrawing ? Nigga.yPos + (Nigga.height - 8) / 2 : Nigga.yPos;
            int Nigga10 = Nigga8;
            if (Nigga4 > Nigga5.length()) {
                Nigga4 = Nigga5.length();
            }
            if (Nigga5.length() > 0) {
                if (Nigga6) {
                    Nigga5.substring(0, Nigga3);
                }
                Nigga10 = Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(Nigga.text.replaceAll(Qprot0.0("\u39f7\u7194\u02e0\u6ea5\ud80a"), "*"), Nigga8, Nigga9, Nigga2);
            }
            boolean Nigga11 = Nigga.cursorPosition < Nigga.text.length() || Nigga.text.length() >= Nigga.getMaxStringLength();
            int Nigga12 = Nigga10;
            if (!Nigga6) {
                Nigga12 = Nigga3 > 0 ? Nigga8 + Nigga.width : Nigga8;
            } else if (Nigga11) {
                Nigga12 = Nigga10 - 1;
                --Nigga10;
            }
            if (Nigga5.length() > 0 && Nigga6 && Nigga3 < Nigga5.length()) {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(Nigga5.substring(Nigga3), Nigga10, Nigga9, Nigga2);
            }
            if (Nigga7) {
                if (Nigga11) {
                    Gui.drawRect(Nigga12, Nigga9 - 1, Nigga12 + 1, Nigga9 + 1 + Nigga.fontRenderer.FONT_HEIGHT, -3092272);
                } else {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("_", Nigga12, Nigga9, Nigga2);
                }
            }
            if (Nigga4 != Nigga3) {
                int Nigga13 = Nigga8 + Nigga.fontRenderer.getStringWidth(Nigga5.substring(0, Nigga4));
                Nigga.drawCursorVertical(Nigga12, Nigga9 - 1, Nigga13 - 1, Nigga9 + 1 + Nigga.fontRenderer.FONT_HEIGHT);
            }
        }
    }

    public void func_73800_i(int Nigga) {
        PasswordField Nigga2;
        int Nigga3 = Nigga2.text.length();
        if (Nigga > Nigga3) {
            Nigga = Nigga3;
        }
        if (Nigga < 0) {
            Nigga = 0;
        }
        Nigga2.selectionEnd = Nigga;
        if (Nigga2.fontRenderer != null) {
            if (Nigga2.i > Nigga3) {
                Nigga2.i = Nigga3;
            }
            int Nigga4 = Nigga2.getWidth();
            String Nigga5 = Nigga2.fontRenderer.trimStringToWidth(Nigga2.text.substring(Nigga2.i), Nigga4);
            int Nigga6 = Nigga5.length() + Nigga2.i;
            if (Nigga == Nigga2.i) {
                Nigga2.i -= Nigga2.fontRenderer.trimStringToWidth(Nigga2.text, Nigga4, true).length();
            }
            if (Nigga > Nigga6) {
                Nigga2.i += Nigga - Nigga6;
            } else if (Nigga <= Nigga2.i) {
                Nigga2.i -= Nigga2.i - Nigga;
            }
            if (Nigga2.i < 0) {
                Nigga2.i = 0;
            }
            if (Nigga2.i > Nigga3) {
                Nigga2.i = Nigga3;
            }
        }
    }

    public int getNthWordFromPos(int Nigga, int Nigga2) {
        PasswordField Nigga3;
        return Nigga3.type(Nigga, Nigga3.getCursorPosition(), true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int type(int Nigga, int Nigga, boolean Nigga) {
        Nigga = Nigga;
        Nigga = Nigga < 0;
        Nigga = Math.abs(Nigga);
        block0: for (Nigga = 0; Nigga < Nigga; ++Nigga) {
            if (Nigga) ** GOTO lbl17
            Nigga = Nigga.text.length();
            if ((Nigga = Nigga.text.indexOf(32, Nigga)) != -1) ** GOTO lbl12
            Nigga = Nigga;
            continue;
            while (Nigga < Nigga && Nigga.text.charAt(Nigga) == ' ') {
                ++Nigga;
lbl12:
                // 2 sources

                if (Nigga) continue;
                continue block0;
            }
            continue;
            while (Nigga > 0 && Nigga.text.charAt(Nigga - 1) == ' ') {
                --Nigga;
lbl17:
                // 2 sources

                if (Nigga) continue;
            }
            while (Nigga > 0 && Nigga.text.charAt(Nigga - 1) != ' ') {
                --Nigga;
            }
        }
        return Nigga;
    }

    public void drawCursorVertical(int Nigga, int Nigga2, int Nigga3, int Nigga4) {
        int Nigga5;
        if (Nigga < Nigga3) {
            Nigga5 = Nigga;
            Nigga = Nigga3;
            Nigga3 = Nigga5;
        }
        if (Nigga2 < Nigga4) {
            Nigga5 = Nigga2;
            Nigga2 = Nigga4;
            Nigga4 = Nigga5;
        }
        Tessellator Nigga6 = Tessellator.getInstance();
        Nigga6.getWorldRenderer();
        GL11.glColor4f((float)Float.intBitsToFloat(2.13111309E9f ^ 0x7F063470), (float)Float.intBitsToFloat(2.12915405E9f ^ 0x7EE85007), (float)Float.intBitsToFloat(1.03331878E9f ^ 0x7EE8317B), (float)Float.intBitsToFloat(1.01368077E9f ^ 0x7F148A68));
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3058);
        GL11.glLogicOp((int)5387);
        GL11.glDisable((int)3058);
        GL11.glEnable((int)3553);
    }

    public int getWidth() {
        PasswordField Nigga;
        return Nigga.getEnableBackgroundDrawing() ? Nigga.width - 8 : Nigga.width;
    }

    public void func_73794_g(int Nigga) {
        Nigga.enabledColor = Nigga;
    }

    public void func_73779_a(int Nigga) {
        PasswordField Nigga2;
        if (Nigga2.text.length() != 0) {
            if (Nigga2.selectionEnd != Nigga2.cursorPosition) {
                Nigga2.writeText("");
            } else {
                Nigga2.deleteFromCursor(Nigga2.getNthWordFromCursor(Nigga) - Nigga2.cursorPosition);
            }
        }
    }

    public void deleteFromCursor(int Nigga) {
        PasswordField Nigga2;
        if (Nigga2.text.length() != 0) {
            if (Nigga2.selectionEnd != Nigga2.cursorPosition) {
                Nigga2.writeText("");
            } else {
                boolean Nigga3 = Nigga < 0;
                int Nigga4 = Nigga3 ? Nigga2.cursorPosition + Nigga : Nigga2.cursorPosition;
                int Nigga5 = Nigga3 ? Nigga2.cursorPosition : Nigga2.cursorPosition + Nigga;
                String Nigga6 = "";
                if (Nigga4 >= 0) {
                    Nigga6 = Nigga2.text.substring(0, Nigga4);
                }
                if (Nigga5 < Nigga2.text.length()) {
                    Nigga6 = String.valueOf(String.valueOf(String.valueOf(Nigga6))) + Nigga2.text.substring(Nigga5);
                }
                Nigga2.text = Nigga6;
                if (Nigga3) {
                    Nigga2.cursorPos(Nigga);
                }
            }
        }
    }

    public void mouseClicked(int Nigga, int Nigga2, int Nigga3) {
        PasswordField Nigga4;
        boolean Nigga5;
        boolean bl = Nigga5 = Nigga >= Nigga4.xPos && Nigga < Nigga4.xPos + Nigga4.width && Nigga2 >= Nigga4.yPos && Nigga2 < Nigga4.yPos + Nigga4.height;
        if (Nigga4.canLoseFocus) {
            Nigga4.setFocused(Nigga4.isEnabled && Nigga5);
        }
        if (Nigga4.isFocused && Nigga3 == 0) {
            int Nigga6 = Nigga - Nigga4.xPos;
            if (Nigga4.enableBackgroundDrawing) {
                Nigga6 -= 4;
            }
            String Nigga7 = Nigga4.fontRenderer.trimStringToWidth(Nigga4.text.substring(Nigga4.i), Nigga4.getWidth());
            Nigga4.setCursorPosition(Nigga4.fontRenderer.trimStringToWidth(Nigga7, Nigga6).length() + Nigga4.i);
        }
    }

    public void setEnableBackgroundDrawing(boolean Nigga) {
        Nigga.enableBackgroundDrawing = Nigga;
    }

    public static {
        throw throwable;
    }

    public boolean textboxKeyTyped(char Nigga, int Nigga2) {
        PasswordField Nigga3;
        if (!Nigga3.isEnabled || !Nigga3.isFocused) {
            return false;
        }
        switch (Nigga) {
            case '\u0001': {
                Nigga3.setCursorPositionEnd();
                Nigga3.func_73800_i(0);
                return true;
            }
            case '\u0003': {
                GuiScreen.setClipboardString(Nigga3.getSelectedtext());
                return true;
            }
            case '\u0016': {
                Nigga3.writeText(GuiScreen.getClipboardString());
                return true;
            }
            case '\u0018': {
                GuiScreen.setClipboardString(Nigga3.getSelectedtext());
                Nigga3.writeText("");
                return true;
            }
        }
        switch (Nigga2) {
            case 14: {
                if (GuiScreen.isCtrlKeyDown()) {
                    Nigga3.func_73779_a(-1);
                } else {
                    Nigga3.deleteFromCursor(-1);
                }
                return true;
            }
            case 199: {
                if (GuiScreen.isShiftKeyDown()) {
                    Nigga3.func_73800_i(0);
                } else {
                    Nigga3.setCursorPositionZero();
                }
                return true;
            }
            case 203: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        Nigga3.func_73800_i(Nigga3.getNthWordFromPos(-1, Nigga3.getSelectionEnd()));
                    } else {
                        Nigga3.func_73800_i(Nigga3.getSelectionEnd() - 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    Nigga3.setCursorPosition(Nigga3.getNthWordFromCursor(-1));
                } else {
                    Nigga3.cursorPos(-1);
                }
                return true;
            }
            case 205: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        Nigga3.func_73800_i(Nigga3.getNthWordFromPos(1, Nigga3.getSelectionEnd()));
                    } else {
                        Nigga3.func_73800_i(Nigga3.getSelectionEnd() + 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    Nigga3.setCursorPosition(Nigga3.getNthWordFromCursor(1));
                } else {
                    Nigga3.cursorPos(1);
                }
                return true;
            }
            case 207: {
                if (GuiScreen.isShiftKeyDown()) {
                    Nigga3.func_73800_i(Nigga3.text.length());
                } else {
                    Nigga3.setCursorPositionEnd();
                }
                return true;
            }
            case 211: {
                if (GuiScreen.isCtrlKeyDown()) {
                    Nigga3.func_73779_a(1);
                } else {
                    Nigga3.deleteFromCursor(1);
                }
                return true;
            }
        }
        if (ChatAllowedCharacters.isAllowedCharacter(Nigga)) {
            Nigga3.writeText(Character.toString(Nigga));
            return true;
        }
        return false;
    }

    public void setMaxStringLength(int Nigga) {
        PasswordField Nigga2;
        Nigga2.maxStringLength = Nigga;
        if (Nigga2.text.length() > Nigga) {
            Nigga2.text = Nigga2.text.substring(0, Nigga);
        }
    }

    public String getSelectedtext() {
        PasswordField Nigga;
        int Nigga2 = Nigga.cursorPosition < Nigga.selectionEnd ? Nigga.cursorPosition : Nigga.selectionEnd;
        int Nigga3 = Nigga.cursorPosition < Nigga.selectionEnd ? Nigga.selectionEnd : Nigga.cursorPosition;
        return Nigga.text.substring(Nigga2, Nigga3);
    }

    public boolean func_73778_q() {
        PasswordField Nigga;
        return Nigga.b;
    }

    public void setCursorPosition(int Nigga) {
        PasswordField Nigga2;
        Nigga2.cursorPosition = Nigga;
        int Nigga3 = Nigga2.text.length();
        if (Nigga2.cursorPosition < 0) {
            Nigga2.cursorPosition = 0;
        }
        if (Nigga2.cursorPosition > Nigga3) {
            Nigga2.cursorPosition = Nigga3;
        }
        Nigga2.func_73800_i(Nigga2.cursorPosition);
    }

    public void updateCursorCounter() {
        PasswordField Nigga;
        ++Nigga.cursorCounter;
    }

    public int getSelectionEnd() {
        PasswordField Nigga;
        return Nigga.selectionEnd;
    }

    public void cursorPos(int Nigga) {
        PasswordField Nigga2;
        Nigga2.setCursorPosition(Nigga2.selectionEnd + Nigga);
    }

    public int getNthWordFromCursor(int Nigga) {
        PasswordField Nigga2;
        return Nigga2.getNthWordFromPos(Nigga, Nigga2.getCursorPosition());
    }

    public void setFocused(boolean Nigga) {
        PasswordField Nigga2;
        if (Nigga && !Nigga2.isFocused) {
            Nigga2.cursorCounter = 0;
        }
        Nigga2.isFocused = Nigga;
    }

    public String getText() {
        PasswordField Nigga;
        String Nigga2 = Nigga.text.replaceAll(" ", "");
        return Nigga2;
    }

    public int getMaxStringLength() {
        PasswordField Nigga;
        return Nigga.maxStringLength;
    }

    public void func_73790_e(boolean Nigga) {
        Nigga.b = Nigga;
    }

    public boolean isFocused() {
        PasswordField Nigga;
        return Nigga.isFocused;
    }

    public void writeText(String Nigga) {
        int Nigga2;
        PasswordField Nigga3;
        String Nigga4 = "";
        String Nigga5 = ChatAllowedCharacters.filterAllowedCharacters(Nigga);
        int Nigga6 = Nigga3.cursorPosition < Nigga3.selectionEnd ? Nigga3.cursorPosition : Nigga3.selectionEnd;
        int Nigga7 = Nigga3.cursorPosition < Nigga3.selectionEnd ? Nigga3.selectionEnd : Nigga3.cursorPosition;
        int Nigga8 = Nigga3.maxStringLength - Nigga3.text.length() - (Nigga6 - Nigga3.selectionEnd);
        if (Nigga3.text.length() > 0) {
            Nigga4 = String.valueOf(String.valueOf(String.valueOf(Nigga4))) + Nigga3.text.substring(0, Nigga6);
        }
        if (Nigga8 < Nigga5.length()) {
            Nigga4 = String.valueOf(String.valueOf(String.valueOf(Nigga4))) + Nigga5.substring(0, Nigga8);
            Nigga2 = Nigga8;
        } else {
            Nigga4 = String.valueOf(String.valueOf(String.valueOf(Nigga4))) + Nigga5;
            Nigga2 = Nigga5.length();
        }
        if (Nigga3.text.length() > 0 && Nigga7 < Nigga3.text.length()) {
            Nigga4 = String.valueOf(String.valueOf(String.valueOf(Nigga4))) + Nigga3.text.substring(Nigga7);
        }
        Nigga3.text = Nigga4.replaceAll(" ", "");
        Nigga3.cursorPos(Nigga6 - Nigga3.selectionEnd + Nigga2);
    }

    public void setCursorPositionEnd() {
        PasswordField Nigga;
        Nigga.setCursorPosition(Nigga.text.length());
    }
}

