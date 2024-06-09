// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.auth;

import java.util.Iterator;
import xyz.niggfaclient.font.Fonts;
import net.minecraft.client.gui.Gui;
import xyz.niggfaclient.utils.render.RenderUtils;
import java.io.IOException;
import xyz.niggfaclient.gui.GuiCustomMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import xyz.niggfaclient.Client;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import xyz.niggfaclient.utils.other.Security;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiAuth extends GuiScreen
{
    public static String status;
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 73, this.height / 2, 150, 20, "Authenticate"));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 0) {
            try {
                final String hwid = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getProperty("os.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
                final String encryptedHWID = Security.hashMD5(Security.encryptAES(hwid, hwid));
                final URL url = new URL("https://github.com/andrewtateofficial/whitelisted-users/blob/main/users.txt");
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    if (!inputLine.contains("//")) {
                        if (inputLine.split(":")[0].contains(encryptedHWID)) {
                            Client.getInstance().setAuthorized(true);
                            GuiAuth.status = EnumChatFormatting.GREEN + "Authorized!";
                            Minecraft.getMinecraft().displayGuiScreen(new GuiCustomMainMenu());
                        }
                        else {
                            if (inputLine.contains(encryptedHWID)) {
                                continue;
                            }
                            Client.getInstance().setAuthorized(false);
                            GuiAuth.status = EnumChatFormatting.RED + "Failed Authentication!";
                        }
                    }
                }
                System.out.println("closed");
                bufferedReader.close();
            }
            catch (Exception e) {
                System.out.println("error");
                GuiAuth.status = EnumChatFormatting.RED + "Error";
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtils.drawGradient((float)this.width, (float)this.height);
        GuiAuth.status = EnumChatFormatting.GRAY + "Waiting for Authentication..";
        Gui.drawRect(this.width / 2.0f + 100.0f, this.height / 2.0f + 35.0f, this.width / 2.0f - 100.0f, this.height / 2.0f - 55.0f, -1895825408);
        for (final GuiButton g : this.buttonList) {
            g.drawButton(this.mc, mouseX, mouseY);
        }
        Fonts.sf23.drawCenteredStringWithShadow(GuiAuth.status, this.width / 2.0f, this.height / 2.0f - 45.0f, -1);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
    public void updateScreen() {
        this.mc.displayGuiScreen(new GuiAuth());
        super.updateScreen();
    }
    
    static {
        GuiAuth.status = "";
    }
}
