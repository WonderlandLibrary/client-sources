package Ascii4UwUWareClient.API.Auth;

import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.UI.Font.FontLoaders;
import Ascii4UwUWareClient.UI.Hanabi.RenderUtil;
import Ascii4UwUWareClient.Util.EmptyInputBox;
import Ascii4UwUWareClient.Util.MainMenuUtil;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class GuiAuth2 extends GuiScreen {
    EmptyInputBox username;
    EmptyInputBox password;
    private boolean loginsuccessfully = false;
    int anim = 140;
    String hwid = Hwid.getHWID();

    public GuiAuth2() throws UnsupportedEncodingException, NoSuchAlgorithmException {
    }

    @Override
    public void initGui() {
        super.initGui();
        username = new EmptyInputBox(4, mc.fontRendererObj, 20, 150, 100, 20);
        password = new EmptyInputBox(4, mc.fontRendererObj, 20, 180, 100, 20);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);


        if (loginsuccessfully) {
            Client.instance.startClient();
            mc.displayGuiScreen(new GuiMainMenu());
        }
        mc.getTextureManager().bindTexture(new ResourceLocation("ClientAssets/BACKGROUND/Meow.png"));
        drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, width, height, width, height, width, height);

        //this.drawDefaultBackground();
        username.yPosition = 100;
        password.yPosition = username.yPosition + 30;
        //RenderUtils.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(62, 66, 104).getRGB());
        RenderUtil.drawRect(0, 0, 140, sr.getScaledHeight(), -1);

        MainMenuUtil.drawString(Client.instance.name, width/2 + password.xPosition, height/2, new Color(0, 224, 255, 0).getRGB());

        FontLoaders.F23.drawString("Welcome!", 40, 45, new Color(10, 10, 10).getRGB());
        FontLoaders.F16.drawString("Click login button to continue.", 10, 65, new Color(120, 120, 120).getRGB());
        FontLoaders.F16.drawString("There no user and hwid yet.", 10, 75, new Color(241, 0, 0).getRGB());

        RenderUtil.drawRoundRect(username.xPosition, username.yPosition, username.xPosition + username.getWidth(), username.yPosition + 20, username.isFocused() ? new Color(150, 150, 150).getRGB() : new Color(200, 200, 200).getRGB());
        RenderUtil.drawRoundRect(username.xPosition + 0.5f, username.yPosition + 0.5f, username.xPosition + username.getWidth() - 0.5f, username.yPosition + 20 - 0.5f, new Color(255, 255, 255).getRGB());

        if (!username.isFocused() && username.getText().isEmpty()) {
            FontLoaders.F16.drawString("Username", username.xPosition + 4, username.yPosition + 6, new Color(180, 180, 180).getRGB());
        }

        RenderUtil.drawRoundRect(password.xPosition, password.yPosition, password.xPosition + password.getWidth(), password.yPosition + 20, password.isFocused() ? new Color(150, 150, 150).getRGB() : new Color(200, 200, 200).getRGB());
        RenderUtil.drawRoundRect(password.xPosition + 0.5f, password.yPosition + 0.5f, password.xPosition + password.getWidth() - 0.5f, password.yPosition + 20 - 0.5f, new Color(255, 255, 255).getRGB());
        if (!password.isFocused() && password.getText().isEmpty()) {
            FontLoaders.F16.drawString("Hwid", password.xPosition + 4, password.yPosition + 6, new Color(180, 180, 180).getRGB());
        } else {
            /*String xing = "";
            for (char c : password.getText().toCharArray()) {
                xing = xing + "*";

            }*/
            //FontLoaders.F20.drawString(xing, password.xPosition + 4, password.yPosition + 6, new Color(180, 180, 180).getRGB());
            }

        //TODO:SEX
        password.drawTextBox();
        username.drawTextBox();

        if (isHovered(password.xPosition, password.yPosition + 30, password.xPosition + password.getWidth(), password.yPosition + 50, mouseX, mouseY)) {
            if (Mouse.isButtonDown(0)) {
                if (Auth.authManualHWID()) {
                    loginsuccessfully = !loginsuccessfully;
                }
                //IRC.connect(username.getText(), password.getText());
            }
            RenderUtil.drawRoundRect(password.xPosition, password.yPosition + 30, password.xPosition + password.getWidth(), password.yPosition + 50, new Color(107, 141, 205).getRGB());
            FontLoaders.F16.drawCenteredString("LOGIN", password.xPosition + password.getWidth() / 2, password.yPosition + 38, new Color(255, 255, 255).getRGB());
        } else {
            RenderUtil.drawRoundRect(password.xPosition, password.yPosition + 30, password.xPosition + password.getWidth(), password.yPosition + 50, new Color(77, 111, 175).getRGB());
            FontLoaders.F16.drawCenteredString("LOGIN", password.xPosition + password.getWidth() / 2, password.yPosition + 38, new Color(255, 255, 255).getRGB());
        }
        if (isHovered(password.xPosition + password.getWidth() - FontLoaders.F14.getStringWidth("Copy hwid"), password.yPosition + 60, password.xPosition + password.getWidth(), password.yPosition + 70, mouseX, mouseY)) {
            if (Mouse.isButtonDown(0)) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                setClipboardString(hwid);
            }
            FontLoaders.F14.drawString("Copy hwid", password.xPosition + password.getWidth() - FontLoaders.F14.getStringWidth("Copy hwid"), password.yPosition + 60, new Color(77, 111, 175).getRGB());//77,111,175
        } else {
            FontLoaders.F14.drawString("Copy hwid", password.xPosition + password.getWidth() - FontLoaders.F14.getStringWidth("Copy hwid"), password.yPosition + 60, new Color(150, 150, 150).getRGB());//77,111,175
        }
        FontLoaders.F14.drawString("Made By SuperSkidder#9899", password.xPosition + password.getWidth() - FontLoaders.F14.getStringWidth("Made By SuperSkidder#9899"), password.yPosition + 90, new Color(150, 150, 150).getRGB());//77,111,175
        FontLoaders.F14.drawString(Auth.getStatus(), 3, password.yPosition + 100, new Color(150, 150, 150).getRGB());//77,111,175

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean contains_(String s, String t) {
        char[] array1 = s.toCharArray();
        char[] array2 = t.toCharArray();
        boolean status = false;

        if (array2.length < array1.length) {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] == array2[0] && i + array2.length - 1 < array1.length) {
                    int j = 0;
                    while (j < array2.length) {
                        if (array1[i + j] == array2[j]) {
                            j++;
                        } else
                            break;
                    }
                    if (j == array2.length) {
                        status = true;
                        break;
                    }
                }

            }
        }
        return status;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        switch (keyCode) {
            case Keyboard.KEY_TAB:
                if (username.isFocused()) {
                    // Tab键切换焦点
                    if(keyCode == Keyboard.KEY_TAB) {
                        password.setFocused(true);
                        username.setFocused(false);
                        return;
                    }
                }
                break;
            case Keyboard.KEY_RETURN:
                if (Auth.authManualHWID()) {
                    loginsuccessfully = !loginsuccessfully;
                }
                //IRC.connect(username.getText(), password.getText());
                break;
            default:
                if (username.isFocused()) {
                    username.textboxKeyTyped(typedChar, keyCode);
                }
                if (password.isFocused()) {
                    password.textboxKeyTyped(typedChar, keyCode);
                }
                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        username.mouseClicked(mouseX, mouseY, mouseButton);
        password.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
