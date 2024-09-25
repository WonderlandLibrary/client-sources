/*
 * Decompiled with CFR 0.150.
 */
package skizzle.alts;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import skizzle.alts.GuiAltManager;
import skizzle.alts.PasswordField;

public class GuiRenameAlt
extends GuiScreen {
    public GuiAltManager manager;
    public GuiTextField nameField;
    public String status = (Object)((Object)EnumChatFormatting.GRAY) + Qprot0.0("\u0c3f\u71ca\u3775\ua7f0\u2fd6\u89bc\u8c28\u600b\u574c\u84db");
    public PasswordField pwField;

    @Override
    public void keyTyped(char Nigga, int Nigga2) {
        GuiRenameAlt Nigga3;
        Nigga3.nameField.textboxKeyTyped(Nigga, Nigga2);
        Nigga3.pwField.textboxKeyTyped(Nigga, Nigga2);
        if (Nigga == '\t' && (Nigga3.nameField.isFocused() || Nigga3.pwField.isFocused())) {
            Nigga3.nameField.setFocused(!Nigga3.nameField.isFocused());
            Nigga3.pwField.setFocused(!Nigga3.pwField.isFocused());
        }
        if (Nigga == '\r') {
            Nigga3.actionPerformed((GuiButton)Nigga3.buttonList.get(0));
        }
    }

    @Override
    public void initGui() {
        GuiRenameAlt Nigga;
        Nigga.buttonList.add(new GuiButton(0, Nigga.width / 2 - 100, Nigga.height / 4 + 92 + 12, Qprot0.0("\u0c2d\u71cf\u3775\u7f89")));
        Nigga.buttonList.add(new GuiButton(1, Nigga.width / 2 - 100, Nigga.height / 4 + 116 + 12, Qprot0.0("\u0c2b\u71ca\u3772\u7f9e\uc643\u89be")));
        Nigga.nameField = new GuiTextField(Nigga.eventButton, Nigga.mc.fontRendererObj, Nigga.width / 2 - 100, 60, 200, 20);
        Nigga.pwField = new PasswordField(Nigga.mc.fontRendererObj, Nigga.width / 2 - 100, 100, 200, 20);
    }

    public GuiRenameAlt(GuiAltManager Nigga) {
        GuiRenameAlt Nigga2;
        Nigga2.manager = Nigga;
    }

    @Override
    public void drawScreen(int Nigga, int Nigga2, float Nigga3) {
        GuiRenameAlt Nigga4;
        Nigga4.drawDefaultBackground();
        Nigga4.drawCenteredString(Nigga4.fontRendererObj, Qprot0.0("\u0c2d\u71cf\u3775\u7352\ufbfd\u8993\u8c23\u6051"), Nigga4.width / 2, Float.intBitsToFloat(1.04576582E9f ^ 0x7F751EA8), -1);
        Nigga4.drawCenteredString(Nigga4.fontRendererObj, Nigga4.status, Nigga4.width / 2, Float.intBitsToFloat(1.06499878E9f ^ 0x7EDA9783), -1);
        Nigga4.nameField.drawTextBox();
        Nigga4.pwField.drawTextBox();
        if (Nigga4.nameField.getText().isEmpty()) {
            Nigga4.drawString(Nigga4.mc.fontRendererObj, Qprot0.0("\u0c26\u71ce\u376b\u7306\ufbb3\u89b3\u8c22\u6040"), Nigga4.width / 2 - 96, 66, -7829368);
        }
        if (Nigga4.pwField.getText().isEmpty()) {
            Nigga4.drawString(Nigga4.mc.fontRendererObj, Qprot0.0("\u0c26\u71ce\u376b\u7306\ufbad\u89b3\u8c3c\u6056\u83b7\u50f8\u038d\uaf08"), Nigga4.width / 2 - 96, 106, -7829368);
        }
        super.drawScreen(Nigga, Nigga2, Nigga3);
    }

    public static {
        throw throwable;
    }

    @Override
    public void actionPerformed(GuiButton Nigga) {
        switch (Nigga.id) {
            case 1: {
                GuiRenameAlt Nigga2;
                Nigga2.mc.displayGuiScreen(Nigga2.manager);
                break;
            }
            case 0: {
                GuiRenameAlt Nigga2;
                Nigga2.manager.selectedAlt.setMask(Nigga2.nameField.getText());
                Nigga2.manager.selectedAlt.setPassword(Nigga2.pwField.getText());
                Nigga2.status = Qprot0.0("\u0c2d\u71cf\u3775\u7ba8\uc262\u89b6\u8c6e");
            }
        }
    }

    @Override
    public void mouseClicked(int Nigga, int Nigga2, int Nigga3) {
        GuiRenameAlt Nigga4;
        try {
            super.mouseClicked(Nigga, Nigga2, Nigga3);
        }
        catch (IOException Nigga5) {
            Nigga5.printStackTrace();
        }
        Nigga4.nameField.mouseClicked(Nigga, Nigga2, Nigga3);
        Nigga4.pwField.mouseClicked(Nigga, Nigga2, Nigga3);
    }
}

