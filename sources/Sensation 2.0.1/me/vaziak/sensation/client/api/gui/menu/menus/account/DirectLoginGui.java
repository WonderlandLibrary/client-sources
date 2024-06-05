package me.vaziak.sensation.client.api.gui.menu.menus.account;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.gui.menu.components.Dropbox;
import me.vaziak.sensation.client.api.gui.menu.components.FieldType;
import me.vaziak.sensation.client.api.gui.menu.components.IconButton;
import me.vaziak.sensation.client.api.gui.menu.components.TextButton;
import me.vaziak.sensation.client.api.gui.menu.components.TextField;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.main.altening.AltService;
import net.minecraft.client.main.altening.api.TheAltening;
import net.minecraft.client.main.altening.api.data.AccountData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class DirectLoginGui extends GuiScreen {
	private String token = null;
	private String lastalt;
    private GuiScreen parentScreen;
    private TextButton b1;
    private TextButton generatebutton;
    private TextButton setKeybutton;

    private TextField apiKeyFeild;
    private TextField tokenField;
    private TextField usernameField;
    private TextField passwordField;

    private Dropbox serviceDropbox;

    public DirectLoginGui(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    public void updateScreen() {
        parentScreen.updateScreen();

        Fonts.f16.drawCenteredString(Sensation.instance.accountLoginService.getStatus(), this.width / 2, this.height - 12, 0xffffffff);
        if (serviceDropbox.getSelected().equalsIgnoreCase("mojang")) {
            b1.enabled = !usernameField.getTypedContent().isEmpty();
            usernameField.updateTextField();
            passwordField.updateTextField();
            usernameField.setHidden(false);
            passwordField.setHidden(false);
            tokenField.setHidden(true);
            apiKeyFeild.setHidden(true);
            setKeybutton.visible = false;
        } else if (serviceDropbox.getSelected().equalsIgnoreCase("altening")) {
            b1.enabled = !tokenField.getTypedContent().isEmpty();
            apiKeyFeild.updateTextField();
            tokenField.updateTextField();
            tokenField.setHidden(false);
            apiKeyFeild.setHidden(token != null);
            usernameField.setHidden(true);
            passwordField.setHidden(true);

            setKeybutton.enabled = apiKeyFeild.isFocused() ||  token == null;
            setKeybutton.visible = token == null;
            
        }

        this.generatebutton.visible = serviceDropbox.getSelected().equalsIgnoreCase("altening");
    }

    public void initGui() {
        this.usernameField = new TextField(Fonts.f16,
                this.width / 2 - 75, this.height / 2 - 42,
                150, 20);

        this.passwordField = new TextField(Fonts.f16,
                this.width / 2 - 75, this.height / 2 - 20,
                150, 20);

        this.tokenField = new TextField(Fonts.f16,
                this.width / 2 - 75, this.height / 2 - 20,
                150, 20);

        this.apiKeyFeild = new TextField(Fonts.f16,
        		this.width / 2 - 81, this.height / 2 + 96,
                150, 20);
        this.usernameField.setType(FieldType.NUMBERS_LETTERS_SPECIAL);
        this.usernameField.setDefaultContent("Username/Email");

        this.passwordField.setType(FieldType.NUMBERS_LETTERS_SPECIAL);
        this.passwordField.setDefaultContent("Password");
        this.passwordField.setReplaceAll("-");

        this.tokenField.setType(FieldType.NUMBERS_LETTERS_SPECIAL);
        this.tokenField.setDefaultContent("Token");

        this.apiKeyFeild.setType(FieldType.NUMBERS_LETTERS_SPECIAL);
        this.apiKeyFeild.setDefaultContent("Put your altening API here!");
        
        serviceDropbox =
                new Dropbox(this.width / 2 - 40, this.height / 2 + 7, 80, 15, Fonts.f16, 0, "Mojang", "Altening");

        this.buttonList.add(b1 =
                new TextButton(1, this.width / 2 - 81, this.height / 2 + 24, 80, 20, "Login"));
        this.buttonList.add(
                new TextButton(2, this.width / 2 + 1, this.height / 2 + 24, 80, 20, "Clipboard"));
        this.buttonList.add(generatebutton =
                new TextButton(3, this.width / 2 - 40, this.height / 2 + 46, 80, 20, "Generate"));

        this.buttonList.add(setKeybutton = new TextButton(9, this.width / 2 - 81, this.height / 2 + 126, 80, 20, "Set API key"));
  		Sensation.instance.accountLoginService.setStatus("Waiting");
        super.initGui();
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1: {
            	loginAccount();
                break;
            }
            case 2: {
                String data;
                try {
                    data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    return;
                }
                if (data.contains(":")) {
                    String[] splitData = data.split(":");
                	if (Sensation.instance.usedAccounts.contains(splitData[0] + ":" + splitData[1])) {
                		Sensation.instance.accountLoginService.setStatus(EnumChatFormatting.RED + "WARNING: " + EnumChatFormatting.GOLD + "ACCOUNT ALREADY USED");
                	} else {
                  		Sensation.instance.accountLoginService.setStatus("Alt copied!");
                        
                	}
                    usernameField.setTypedContent(splitData[0]);
                    passwordField.setTypedContent(splitData[1]);
                } else {
                    if (data.contains("@alt")) {
                        // altening
                        usernameField.setTypedContent(data);
                    }
                }

                // NOTICE: Removed seperate method for this, made it into 1 method above ^^
                /*if (serviceDropbox.getSelected().equalsIgnoreCase("mojang")) {
                    String data;
                    try {
                        data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    } catch (Exception e) {
                        return;
                    }
                    if (data.contains(":")) {
                        String[] thepassword = data.split(":");
                        usernameField.setTypedContent(thepassword[0]);
                        passwordField.setTypedContent(thepassword[1]);
                    }
                } else if (serviceDropbox.getSelected().equalsIgnoreCase("altening")) {
                    String data;
                    try {
                        data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    } catch (Exception e) {
                        return;
                    }
                    tokenField.setTypedContent(data);
                }*/
                break;
            }
            case 3: {
                try {
                    File alteningFile = new File(Sensation.clientDir + File.separator + "altening_token.txt");

                    if (alteningFile.exists()) {
                        String token = FileUtils.readFileToString(alteningFile);
                        System.out.println(token);
                        if (!token.isEmpty()) {
                        	this.token = token;
                            AccountData accountData = new TheAltening(token).getAccountData();
                            if (accountData != null && !accountData.getToken().isEmpty()) {
                                tokenField.setTypedContent(lastalt = accountData.getToken());
                                System.out.println("Logging into: " + accountData.getToken());
                            }
                        } else if (!token.isEmpty()) {
                            AccountData accountData = new TheAltening(this.token).getAccountData();
                            if (accountData != null && !accountData.getToken().isEmpty()) {
                                tokenField.setTypedContent(lastalt = accountData.getToken());
                                System.out.println("Logging into: " + accountData.getToken());
                            }
                        }
                    } else if (!token.isEmpty()) {
                        AccountData accountData = new TheAltening(token).getAccountData();
                        if (accountData != null && !accountData.getToken().isEmpty()) {
                            tokenField.setTypedContent(lastalt = accountData.getToken());
                            System.out.println("Logging into: " + accountData.getToken());
                        }
                    }
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 9 : {
            	this.token = apiKeyFeild.getTypedContent();
            	break;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(0,0,width,height,new Color(0,0,0).getRGB());
        drawGradientRect(0, 0, width, height, new Color(0,0,0, 0).getRGB(), new Color(0,0,0).getRGB());

        GlStateManager.color(1f, 1f, 1f, 1f);
        Draw.drawImg(new ResourceLocation("client/background1.jpg"),0, 0, width, height);

        usernameField.drawTextField();
        passwordField.drawTextField();
        tokenField.drawTextField();
        apiKeyFeild.drawTextField();

        super.drawScreen(mouseX, mouseY, partialTicks);

        serviceDropbox.drawDropbox(mouseX, mouseY);

        Fonts.f16.drawCenteredString(Sensation.instance.accountLoginService.getStatus(), this.width / 2, this.height - 12, 0xffffffff);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_RETURN) {
            loginAccount();
        }


        if (keyCode == Keyboard.KEY_ESCAPE) {
            Minecraft.getMinecraft().displayGuiScreen(parentScreen);
        }
        if (serviceDropbox.getSelected().equalsIgnoreCase("mojang")) {
            if (keyCode == Keyboard.KEY_TAB) {
                if (usernameField.isFocused()) {
                    usernameField.setFocused(false);
                    passwordField.setFocused(true);
                } else if (passwordField.isFocused()) {
                    passwordField.setFocused(false);
                    usernameField.setFocused(true);
                }
            }

            usernameField.keyTyped(typedChar, keyCode);
            passwordField.keyTyped(typedChar, keyCode);
        } else if (serviceDropbox.getSelected().equalsIgnoreCase("altening")) {
            tokenField.keyTyped(typedChar, keyCode);
            apiKeyFeild.keyTyped(typedChar, keyCode);
            if (apiKeyFeild.isFocused()) {
            	apiKeyFeild.setFocused(true);
                passwordField.setFocused(false);
                usernameField.setFocused(false);
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (serviceDropbox.getSelected().equalsIgnoreCase("mojang")) {
            usernameField.mouseClicked(mouseX, mouseY, mouseButton);
            passwordField.mouseClicked(mouseX, mouseY, mouseButton);
        } else if (serviceDropbox.getSelected().equalsIgnoreCase("altening")) {
            tokenField.mouseClicked(mouseX, mouseY, mouseButton);
            apiKeyFeild.mouseClicked(mouseX, mouseY, mouseButton);
        }

        String value = serviceDropbox.getSelected();
        serviceDropbox.mouseClicked(mouseX, mouseY, mouseButton);
        if (serviceDropbox.getSelected().equals(value))
            super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void loginAccount() {
        AltService.EnumAltService service;

        if (serviceDropbox.getSelected().equals("Mojang")) {
            service = AltService.EnumAltService.MOJANG;

            Sensation.instance.accountLoginService.attemptLogin(
                    new Account(usernameField.getTypedContent(), passwordField.getTypedContent(), service));
        } else {
            service = AltService.EnumAltService.THEALTENING;

            Sensation.instance.accountLoginService.attemptLogin(
                    new Account(tokenField.getTypedContent(), "THEALTENINGBEST", service));
        }
    }
}
