/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package skizzle.alts;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import skizzle.alts.AltLoginThread;
import skizzle.alts.PasswordField;

public class GuiAltLogin
extends GuiScreen {
    public AltLoginThread thread;
    public GuiScreen previousScreen;
    public PasswordField password;
    public GuiTextField username;

    @Override
    public void mouseClicked(int Nigga, int Nigga2, int Nigga3) {
        GuiAltLogin Nigga4;
        try {
            super.mouseClicked(Nigga, Nigga2, Nigga3);
        }
        catch (IOException Nigga5) {
            Nigga5.printStackTrace();
        }
        Nigga4.username.mouseClicked(Nigga, Nigga2, Nigga3);
        Nigga4.password.mouseClicked(Nigga, Nigga2, Nigga3);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void drawScreen(int Nigga, int Nigga2, float Nigga3) {
        GuiAltLogin Nigga4;
        Nigga4.drawDefaultBackground();
        Nigga4.username.drawTextBox();
        Nigga4.password.drawTextBox();
        Nigga4.drawCenteredString(Nigga4.mc.fontRendererObj, Qprot0.0("\u8f22\u71c7\ub463\u7306\u7a9a\u0ab6\u8c28\ue347\u83ae"), Nigga4.width / 2, Float.intBitsToFloat(1.06528819E9f ^ 0x7EDF01E1), -1);
        Nigga4.drawCenteredString(Nigga4.mc.fontRendererObj, Nigga4.thread == null ? (Object)((Object)EnumChatFormatting.GRAY) + Qprot0.0("\u8f2a\u71cf\ub47b\u7343\u7af8\u0af7\u8c61") : Nigga4.thread.getStatus(), Nigga4.width / 2, Float.intBitsToFloat(1.04962112E9f ^ 0x7F67F27A), -1);
        if (Nigga4.username.getText().isEmpty()) {
            Nigga4.drawString(Nigga4.mc.fontRendererObj, Qprot0.0("\u8f36\u71d8\ub472\u7354\u7ab8\u0ab8\u8c22\ue34b\u83e0\ud1b3\u80d4\uaf29\u0dd8\ua6c2\u0e8e\u130f\u42e5"), Nigga4.width / 2 - 96, 66, -7829368);
        }
        if (Nigga4.password.getText().isEmpty()) {
            Nigga4.drawString(Nigga4.mc.fontRendererObj, Qprot0.0("\u8f33\u71ca\ub464\u7355\u7aa1\u0ab6\u8c3d\ue34a"), Nigga4.width / 2 - 96, 106, -7829368);
        }
        super.drawScreen(Nigga, Nigga2, Nigga3);
    }

    public static {
        throw throwable;
    }

    public GuiAltLogin(GuiScreen Nigga) {
        GuiAltLogin Nigga2;
        Nigga2.previousScreen = Nigga;
    }

    @Override
    public void actionPerformed(GuiButton Nigga) {
        switch (Nigga.id) {
            case 1: {
                GuiAltLogin Nigga2;
                Nigga2.mc.displayGuiScreen(Nigga2.previousScreen);
                break;
            }
            case 0: {
                GuiAltLogin Nigga2;
                Nigga2.thread = new AltLoginThread(Nigga2.username.getText(), Nigga2.password.getText());
                Nigga2.thread.start();
            }
        }
    }

    @Override
    public void updateScreen() {
        GuiAltLogin Nigga;
        Nigga.username.updateCursorCounter();
        Nigga.password.updateCursorCounter();
    }

    @Override
    public void keyTyped(char Nigga, int Nigga2) {
        GuiAltLogin Nigga3;
        try {
            super.keyTyped(Nigga, Nigga2);
        }
        catch (IOException Nigga4) {
            Nigga4.printStackTrace();
        }
        if (Nigga == '\t') {
            if (!Nigga3.username.isFocused() && !Nigga3.password.isFocused()) {
                Nigga3.username.setFocused(true);
            } else {
                Nigga3.username.setFocused(Nigga3.password.isFocused());
                Nigga3.password.setFocused(!Nigga3.username.isFocused());
            }
        }
        if (Nigga == '\r') {
            Nigga3.actionPerformed((GuiButton)Nigga3.buttonList.get(0));
        }
        Nigga3.username.textboxKeyTyped(Nigga, Nigga2);
        Nigga3.password.textboxKeyTyped(Nigga, Nigga2);
    }

    @Override
    public void initGui() {
        GuiAltLogin Nigga;
        int Nigga2 = Nigga.height / 4 + 24;
        Nigga.buttonList.add(new GuiButton(0, Nigga.width / 2 - 100, Nigga2 + 72 + 12, Qprot0.0("\u8f2f\u71c4\ub470\u7f94\u7975")));
        Nigga.buttonList.add(new GuiButton(1, Nigga.width / 2 - 100, Nigga2 + 72 + 12 + 24, Qprot0.0("\u8f21\u71ca\ub474\u7f96")));
        Nigga.username = new GuiTextField(Nigga2, Nigga.mc.fontRendererObj, Nigga.width / 2 - 100, 60, 200, 20);
        Nigga.password = new PasswordField(Nigga.mc.fontRendererObj, Nigga.width / 2 - 100, 100, 200, 20);
        Nigga.username.setFocused(true);
        Keyboard.enableRepeatEvents((boolean)true);
    }
}

