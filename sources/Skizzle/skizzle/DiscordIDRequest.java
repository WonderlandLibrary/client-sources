/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package skizzle;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import skizzle.Client;
import skizzle.users.ServerManager;

public class DiscordIDRequest
extends GuiScreen {
    public String status = (Object)((Object)EnumChatFormatting.GRAY) + Qprot0.0("\u7725\u71cf\u4c6c\ua7e1\u596d\uf2f8\u8c61");
    public GuiTextField username;

    @Override
    public void initGui() {
        DiscordIDRequest Nigga;
        Keyboard.enableRepeatEvents((boolean)true);
        Nigga.buttonList.clear();
        Nigga.buttonList.add(new GuiButton(0, Nigga.width / 2 - 100, Nigga.height / 4 + 92 + 12, Qprot0.0("\u7720\u71c4\u4c67\u7f94\u7144")));
        Nigga.username = new GuiTextField(Nigga.eventButton, Nigga.mc.fontRendererObj, Nigga.width / 2 - 100, 60, 200, 20);
    }

    public static {
        throw throwable;
    }

    public static void access$0(DiscordIDRequest Nigga, String Nigga2) {
        Nigga.status = Nigga2;
    }

    public DiscordIDRequest() {
        DiscordIDRequest Nigga;
    }

    @Override
    public void drawScreen(int Nigga, int Nigga2, float Nigga3) {
        DiscordIDRequest Nigga4;
        Nigga4.drawDefaultBackground();
        Nigga4.username.drawTextBox();
        Nigga4.drawCenteredString(Nigga4.fontRendererObj, Qprot0.0("\u773c\u71c7\u4c65\u7347\u7292\uf2b3\u8c6f\u1b50\u83ae\ud9d8\u789e\uaf1e\uf596\ua6af\u06a1\ueb06\u42fc\uc169\uc3e2\u6dc4\u8ec3\u01d9\u674a\u3cc1\ub534\uca0f\u2f5e\ua227\u96e0\uf81a\u96ff\u8855\ufac2\u79ac"), Nigga4.width / 2, Float.intBitsToFloat(1.06595443E9f ^ 0x7E292C7F), -1);
        if (Nigga4.username.getText().isEmpty()) {
            Nigga4.drawString(Nigga4.mc.fontRendererObj, Qprot0.0("\u7728\u71c2\u4c73\u7345\u728e\uf2a4\u8c2b\u1b19\u8389\ud9ef"), Nigga4.width / 2 - 96, 66, -7829368);
        }
        Nigga4.drawCenteredString(Nigga4.fontRendererObj, Nigga4.status, Nigga4.width / 2, Float.intBitsToFloat(1.0633328E9f ^ 0x7E912BA9), -1);
        super.drawScreen(Nigga, Nigga2, Nigga3);
    }

    @Override
    public void keyTyped(char Nigga, int Nigga2) {
        DiscordIDRequest Nigga3;
        Nigga3.username.textboxKeyTyped(Nigga, Nigga2);
        if (Nigga == '\t' && Nigga3.username.isFocused()) {
            Nigga3.username.setFocused(!Nigga3.username.isFocused());
        }
        if (Nigga == '\r') {
            Nigga3.actionPerformed((GuiButton)Nigga3.buttonList.get(0));
        }
    }

    @Override
    public void mouseClicked(int Nigga, int Nigga2, int Nigga3) {
        DiscordIDRequest Nigga4;
        try {
            super.mouseClicked(Nigga, Nigga2, Nigga3);
        }
        catch (IOException Nigga5) {
            Nigga5.printStackTrace();
        }
        Nigga4.username.mouseClicked(Nigga, Nigga2, Nigga3);
    }

    @Override
    public void actionPerformed(GuiButton Nigga) {
        switch (Nigga.id) {
            case 0: {
                DiscordIDRequest Nigga2;
                if (Nigga2.username.getText().equals("")) break;
                try {
                    long Nigga3 = Long.parseLong(Nigga2.username.getText());
                    ServerManager.sendPacketWithResponse(Qprot0.0("\u7720\u71ca\u4c75\u7bb2\u7d68\uf2be\u8c1d\u1b5c\u8b4b\ud634\u789e\uaf1f\uf596"), String.valueOf(Client.getMotherboardSN()) + ":" + Nigga3);
                    break;
                }
                catch (Exception exception) {
                    Nigga2.status = Qprot0.0("\u77cb\u71c8\u4c49\u7bb2\u7d7d\uf2b7\u8c23\u1b50\u8b5e\ud661\u78b2\uaf28\uf5c3");
                }
            }
        }
    }
}

