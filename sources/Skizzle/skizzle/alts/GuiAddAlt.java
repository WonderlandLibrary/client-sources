/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  org.lwjgl.input.Keyboard
 */
package skizzle.alts;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.io.IOException;
import java.net.Proxy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import skizzle.Client;
import skizzle.alts.Alt;
import skizzle.alts.AltManager;
import skizzle.alts.GuiAltManager;
import skizzle.alts.PasswordField;

public class GuiAddAlt
extends GuiScreen {
    public GuiTextField username;
    public GuiTextField usernamePassword;
    public String status = (Object)((Object)EnumChatFormatting.GRAY) + Qprot0.0("\uf8d2\u71cf\uc383\ua7e1\ud246\u7d0f\u8c61");
    public GuiAltManager manager;
    public PasswordField password;

    public static void access$1(GuiAddAlt guiAddAlt, String string) {
        guiAddAlt.status = string;
    }

    @Override
    public void drawScreen(int Nigga, int Nigga2, float Nigga3) {
        GuiAddAlt Nigga4;
        Nigga4.drawDefaultBackground();
        Nigga4.username.drawTextBox();
        Nigga4.password.drawTextBox();
        Nigga4.usernamePassword.drawTextBox();
        Nigga4.drawCenteredString(Nigga4.fontRendererObj, Qprot0.0("\uf8da\u71cf\uc38b\u7306\uefcf\u7d4d\u8c3b"), Nigga4.width / 2, Float.intBitsToFloat(1.05610739E9f ^ 0x7F52EB84), -1);
        if (Nigga4.username.getText().isEmpty()) {
            Nigga4.drawString(Nigga4.mc.fontRendererObj, Qprot0.0("\uf8ce\u71d8\uc38a\u7354\uefe0\u7d40\u8c22\u94b3\u83e0\u44eb\uf72c\uaf29\u7a20\ua6c2\u9bd6\u64f7\u42e5"), Nigga4.width / 2 - 96, 66, -7829368);
        }
        if (Nigga4.password.getText().isEmpty()) {
            Nigga4.drawString(Nigga4.mc.fontRendererObj, Qprot0.0("\uf8cb\u71ca\uc39c\u7355\ueff9\u7d4e\u8c3d\u94b2"), Nigga4.width / 2 - 96, 106, -7829368);
        }
        if (Nigga4.usernamePassword.getText().isEmpty()) {
            Nigga4.drawString(Nigga4.mc.fontRendererObj, Qprot0.0("\uf8ce\u71d8\uc38a\u7354\uefe0\u7d40\u8c22\u94b3\u83fa\u4494\uf76d\uaf1f\u7a7e\ua6f8\u9bd8\u64ec\u42ed"), Nigga4.width / 2 - 96, 146, -7829368);
        }
        Nigga4.drawCenteredString(Nigga4.fontRendererObj, Nigga4.status, Nigga4.width / 2, Float.intBitsToFloat(1.05216691E9f ^ 0x7F46CAF7), -1);
        super.drawScreen(Nigga, Nigga2, Nigga3);
    }

    public static void access$0(GuiAddAlt Nigga, String Nigga2) {
        Nigga.status = Nigga2;
    }

    @Override
    public void mouseClicked(int Nigga, int Nigga2, int Nigga3) {
        GuiAddAlt Nigga4;
        try {
            super.mouseClicked(Nigga, Nigga2, Nigga3);
        }
        catch (IOException Nigga5) {
            Nigga5.printStackTrace();
        }
        Nigga4.username.mouseClicked(Nigga, Nigga2, Nigga3);
        Nigga4.password.mouseClicked(Nigga, Nigga2, Nigga3);
        Nigga4.usernamePassword.mouseClicked(Nigga, Nigga2, Nigga3);
    }

    public GuiAddAlt(GuiAltManager Nigga) {
        GuiAddAlt Nigga2;
        Nigga2.manager = Nigga;
    }

    public static {
        throw throwable;
    }

    @Override
    public void keyTyped(char Nigga, int Nigga2) {
        GuiAddAlt Nigga3;
        Nigga3.username.textboxKeyTyped(Nigga, Nigga2);
        Nigga3.password.textboxKeyTyped(Nigga, Nigga2);
        Nigga3.usernamePassword.textboxKeyTyped(Nigga, Nigga2);
        if (Nigga == '\t' && (Nigga3.username.isFocused() || Nigga3.password.isFocused() || Nigga3.usernamePassword.isFocused())) {
            Nigga3.username.setFocused(!Nigga3.username.isFocused());
            Nigga3.password.setFocused(!Nigga3.password.isFocused());
        }
        if (Nigga == '\r') {
            Nigga3.actionPerformed((GuiButton)Nigga3.buttonList.get(0));
        }
    }

    @Override
    public void initGui() {
        GuiAddAlt Nigga;
        Keyboard.enableRepeatEvents((boolean)true);
        Nigga.buttonList.clear();
        Nigga.buttonList.add(new GuiButton(0, Nigga.width / 2 - 100, Nigga.height / 4 + 92 + 12, Qprot0.0("\uf8d7\u71c4\uc388\u7f94\ueabd")));
        Nigga.buttonList.add(new GuiButton(1, Nigga.width / 2 - 100, Nigga.height / 4 + 116 + 12, Qprot0.0("\uf8d9\u71ca\uc38c\u7f96")));
        Nigga.usernamePassword = new GuiTextField(Nigga.eventButton, Nigga.mc.fontRendererObj, Nigga.width / 2 - 100, 140, 200, 20);
        Nigga.username = new GuiTextField(Nigga.eventButton, Nigga.mc.fontRendererObj, Nigga.width / 2 - 100, 60, 200, 20);
        Nigga.password = new PasswordField(Nigga.mc.fontRendererObj, Nigga.width / 2 - 100, 100, 200, 20);
    }

    @Override
    public void actionPerformed(GuiButton Nigga) {
        switch (Nigga.id) {
            case 0: {
                GuiAddAlt Nigga2;
                String Nigga3 = Nigga2.username.getText();
                String Nigga4 = Nigga2.password.getText();
                if (!Nigga2.usernamePassword.getText().equals("") && Nigga2.usernamePassword.getText().contains(":")) {
                    Nigga3 = Nigga2.usernamePassword.getText().split(":")[0];
                    Nigga4 = Nigga2.usernamePassword.getText().split(":")[1];
                } else if (Nigga2.username.getText().equals("")) {
                    Nigga2.status = (Object)((Object)EnumChatFormatting.RED) + Qprot0.0("\uf8cb\u71c7\uc38a\u7bbd\uf643\u7d44\u8c6f\u94bf\u8b54\u5d0a\uf779\uaf18\u7a2d\uae01\u8261\u64fb\u42a9\u4e81\ucb4b\ue914\u012f\u01c4\ue8a7\u3439\u31f2\u45b3\u2f1b\u2dd0\u9e08\u7cc7\u1916\u8855\u7505\u717c\uec99\uda85\uddaf\u9266\u96bf\ud2c6\ufbf0\u3c52\ue020\ue595\u1682");
                    break;
                }
                AddAltThread Nigga5 = new AddAltThread(Nigga2, Nigga3, Nigga4);
                Nigga5.start();
                break;
            }
            case 1: {
                GuiAddAlt Nigga2;
                Nigga2.mc.displayGuiScreen(Nigga2.manager);
            }
        }
    }

    private class AddAltThread
    extends Thread {
        public String username;
        public String password;
        public GuiAddAlt this$0;

        public void checkAndAddAlt(String Nigga, String Nigga2) throws IOException {
            AddAltThread Nigga3;
            YggdrasilAuthenticationService Nigga4 = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication Nigga5 = (YggdrasilUserAuthentication)Nigga4.createUserAuthentication(Agent.MINECRAFT);
            Nigga5.setUsername(Nigga);
            Nigga5.setPassword(Nigga2);
            try {
                Nigga5.logIn();
                for (Alt alt : AltManager.registry) {
                    if (!alt.getUsername().equals(Nigga) || !alt.getPassword().equals(Nigga2)) continue;
                    GuiAddAlt.access$1(Nigga3.this$0, (Object)((Object)EnumChatFormatting.RED) + Qprot0.0("\u289f\u71c3\u13d6\u2052\u8e97\uad10\u8c23\u44f2\ud0e7\u259c\u2730\uaf1e\uaa38\uf5e9\ufaea\ub4b7\u42a9\u9ec1\u90bd\u919f\ud17e\u01de\u38e5\u6f88"));
                    return;
                }
                AltManager.registry.add(new Alt(Nigga, Nigga2, Nigga5.getSelectedProfile().getName()));
                GuiAddAlt.access$0(Nigga3.this$0, (Object)((Object)EnumChatFormatting.GREEN) + Qprot0.0("\u288a\u71c7\u13cb\u2001\u8ed6\uad15\u8c2b\u44e3\ud0a3\u25d3\u277c\uaf44") + Nigga + ")");
                String string = Client.fileManager.readFile(Qprot0.0("\u28aa\u71c7\u13cb\u2052"));
                Client.fileManager.writeFile(String.valueOf(string == null ? "" : String.valueOf(string) + "\n") + Nigga + ":" + Nigga2, "", Qprot0.0("\u28aa\u71c7\u13cb\u2052"));
            }
            catch (AuthenticationException authenticationException) {
                GuiAddAlt.access$0(Nigga3.this$0, (Object)((Object)EnumChatFormatting.RED) + Qprot0.0("\u288a\u71c7\u13cb\u2001\u8ed1\uad10\u8c26\u44ea\ud0a2\u2599\u277d"));
                authenticationException.printStackTrace();
            }
        }

        public static {
            throw throwable;
        }

        public AddAltThread(GuiAddAlt guiAddAlt, String Nigga, String Nigga2) {
            AddAltThread Nigga3;
            Nigga3.this$0 = guiAddAlt;
            Nigga3.username = Nigga;
            Nigga3.password = Nigga2;
            GuiAddAlt.access$0(guiAddAlt, (Object)((Object)EnumChatFormatting.GRAY) + Qprot0.0("\u2882\u71cf\u13d3\ua7e1\u0236\uad5f\u8c61"));
        }

        @Override
        public void run() {
            AddAltThread Nigga;
            if (Nigga.password.equals("")) {
                AltManager.registry.add(new Alt(Nigga.username, ""));
                GuiAddAlt.access$0(Nigga.this$0, (Object)((Object)EnumChatFormatting.GREEN) + Qprot0.0("\u288a\u71c7\u13cb\u1f5d\ufa62\uad15\u8c2b\u44e3\uefff\u5167\u277c\uaf44") + Nigga.username + Qprot0.0("\u28eb\u7186\u139f\u1f12\ufa65\uad17\u8c23\u44ef\ueff5\u512c\u277c\uaf02\uaa3c\ucab9\u8e5f\ub4e7"));
                return;
            }
            GuiAddAlt.access$0(Nigga.this$0, (Object)((Object)EnumChatFormatting.YELLOW) + Qprot0.0("\u289f\u71d9\u13c6\u1f14\ufa6d\uad16\u8c6f\u44e7\ueff7\u513d\u2772\uaf42\uaa73"));
            try {
                Nigga.checkAndAddAlt(Nigga.username, Nigga.password);
            }
            catch (IOException Nigga2) {
                Nigga2.printStackTrace();
            }
        }
    }
}

