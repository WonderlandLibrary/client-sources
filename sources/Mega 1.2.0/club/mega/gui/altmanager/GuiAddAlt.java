package club.mega.gui.altmanager;

import club.mega.Mega;
import club.mega.gui.altmanager.alt.Alt;
import club.mega.gui.altmanager.alt.AltType;
import club.mega.gui.altmanager.alt.LoginThread;
import club.mega.util.MouseUtil;
import club.mega.util.RenderUtil;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.net.Proxy;

public class GuiAddAlt extends GuiScreen {

    private final TheAlteningAuthentication serviceSwitcher = TheAlteningAuthentication.mojang();

    private LoginThread loginThread;
    private AltType altType;
    private final GuiAltManager parent;

    private int index;

    private GuiTextField usernameField;
    private PasswordField passwordField;

    public GuiAddAlt(final GuiAltManager parent) {
        this.parent = parent;

        index = 0;
        altType = AltType.MOJANG;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(0, 0, width, height, new Color(40,40,40));
        // Rendering current AltType button
        Gui.drawRect(width / 2D - 100,height / 3D,width / 2D + 100,height / 3D + 30, new Color(1,1,1,180).getRGB());
        Mega.INSTANCE.getFontManager().getFont("Arial 30").drawCenteredString(altType.name().toLowerCase(), width / 2D,height / 3D + 7, new Color(255,255,255,150).getRGB());
        Mega.INSTANCE.getFontManager().getFont("Arial 25").drawCenteredString("Alt Type:", width / 2D,height / 3D - 17, new Color(255,255,255,150).getRGB());

        usernameField.drawTextBox();
        if (altType != AltType.THEALTENING)
            passwordField.drawTextBox();
        if (usernameField.getText().isEmpty() && !usernameField.isFocused()) {
            Mega.INSTANCE.getFontManager().getFont("Arial 30").drawString(altType == AltType.THEALTENING ? "Token" : "Email",this.width / 2D - 190, height / 2D + 7, new Color(255,255,255,180).getRGB());
        }
        if (passwordField.getText().isEmpty() && !passwordField.isFocused() && altType != AltType.THEALTENING)
            Mega.INSTANCE.getFontManager().getFont("Arial 30").drawString("Password",this.width / 2D - 190, height / 2D + 7 + 33, new Color(255,255,255,180).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (MouseUtil.isInside(mouseX, mouseY, width / 2D - 100,height / 3D,200,30) && (mouseButton == 0 || mouseButton == 1))
        {
            if (index + 1 >= AltType.values().length)
                index = 0;
            else
                index++;
            altType = AltType.values()[index];
        }
        usernameField.mouseClicked(mouseX, mouseY, mouseButton);
        if (altType != AltType.THEALTENING)
            passwordField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        usernameField.textboxKeyTyped(typedChar, keyCode);
        if (altType != AltType.THEALTENING)
            passwordField.textboxKeyTyped(typedChar, keyCode);
        if (typedChar == '\t' && usernameField.isFocused()) {
            usernameField.setFocused(!usernameField.isFocused());
        }
        if (altType != AltType.THEALTENING)
            if (typedChar == '\t' && passwordField.isFocused()) {
                passwordField.setFocused(!passwordField.isFocused());
            }
        if (keyCode == 1)
            this.mc.displayGuiScreen(parent);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id)
        {
            case 1:
                switch (altType)
                {
                    case MOJANG:
                    case MICROSOFT:
                        serviceSwitcher.updateService(AlteningServiceType.MOJANG);
                        break;
                    case THEALTENING:
                        serviceSwitcher.updateService(AlteningServiceType.THEALTENING);
                        break;
                }
                if (!usernameField.getText().isEmpty()) {
                    if (altType == AltType.THEALTENING) {
                        passwordField.setText("12345");
                    }
                    if (passwordField.getText().isEmpty()) {
                        parent.alts.add(new Alt(usernameField.getText(), usernameField.getText(), passwordField.getText(), altType));
                        loginThread = new LoginThread(usernameField.getText(), passwordField.getText());
                        loginThread.start();
                    } else {
                        Session auth = this.createSession(usernameField.getText(), passwordField.getText());
                        if (auth != null) {
                            parent.alts.add(new Alt(auth.getUsername(), usernameField.getText(), passwordField.getText(), altType));
                            loginThread = new LoginThread(usernameField.getText(), passwordField.getText());
                            loginThread.start();
                        }
                    }
                    //Mega.INSTANCE.getManager().saveAlts();
                    this.mc.displayGuiScreen(parent);
                }
                break;
            case 2:
                this.mc.displayGuiScreen(parent);
                break;
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        createButtons();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    private void createButtons() {
        int width = 400, offset = 0;
        Keyboard.enableRepeatEvents(true);

        buttonList.clear();
        usernameField = new GuiTextField(3, fontRendererObj, this.width / 2 - width / 2, height / 2, width, 30);
        passwordField = new PasswordField(fontRendererObj, this.width / 2 - width / 2, height / 2 + (offset += 33), width, 30);
        buttonList.add(new GuiButton(1, this.width / 2 - width / 2, height / 2 + (offset += 60), width, 30, "Add"));
        buttonList.add(new GuiButton(2, this.width / 2 - width / 2, height / 2 + offset + 33, width,  30, "Cancel"));
    }

    private Session createSession(final String username, final String password) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
            return null;
        }
    }

}
