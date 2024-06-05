package club.mega.gui.altmanager;

import club.mega.Mega;
import club.mega.gui.altmanager.alt.Alt;
import club.mega.gui.altmanager.alt.AltType;
import club.mega.gui.altmanager.alt.LoginThread;
import club.mega.util.*;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.*;
import java.net.Proxy;
import java.util.ArrayList;

public class GuiAltManager extends GuiScreen {

    public final ArrayList<Alt> alts = new ArrayList<>();
    public Alt selectedAlt;
    public Alt loggedAccount;
    private LoginThread loginThread;

    private int selectedAltIndex;

    private final GuiScreen prevScreen;
    private final GuiAddAlt guiAddAlt = new GuiAddAlt(this);

    private GuiTextField usernameField;
    private PasswordField passwordField;

    private double current = 0, scrollOffset = 0, current2;

    private final TheAlteningAuthentication serviceSwitcher = TheAlteningAuthentication.mojang();

    public GuiAltManager(final GuiScreen prevScreen) {
        this.prevScreen = prevScreen;
        selectedAlt = null;
        loggedAccount = null;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        current2 = AnimationUtil.animate(current2, 1, 0.03);
        handleScrolling(mouseX, mouseY, width / 2D - 150,height / 8D - 20,300,400);

        RenderUtil.drawRect(0, 0, width, height, new Color(40,40,40));

        GL11.glPushMatrix();
        GL11.glScaled(current2, current2, current2);
        Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").drawCenteredString("AltManager", width / 2D + 3, height / 10D - 23, -1);
        // Rendering all the Alts
        int offset = (int) (height / 8D - 15);
        RenderUtil.drawRoundedRect(width / 2D - 150, height / 8D - 20,300,400,10, new Color(20,20,20));
        RenderUtil.drawRoundedRect(width / 4D - 15, height / 2D - 145,90,142,10, new Color(20,20,20));
        GL11.glPushMatrix();
        if (current2 == 1) RenderUtil.prepareScissorBox(width / 2D - 150, height / 8D - 20,300,400); else RenderUtil.prepareScissorBox(0, 0, width, height);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        for (Alt alt : alts)
        {
            RenderUtil.drawRoundedRect(width / 2D - 145, offset - scrollOffset,290,30, 5, selectedAlt == alt ? ColorUtil.getMainColor(180) :ColorUtil.getMainColor());
            RenderUtil.drawFullCircle(width / 2D + 135, offset + 15 - scrollOffset, 4, alt.getPassword().isEmpty() ? Color.RED.getRGB() : Color.GREEN.getRGB());
            Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").drawString(alt.getName(),width / 2D - 135,offset + 10 - scrollOffset, new Color(255,255,255,180).getRGB());
            offset += 32;
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
        usernameField.drawTextBox();
        passwordField.drawTextBox();
        if (!usernameField.isFocused() && usernameField.getText().isEmpty())
        Mega.INSTANCE.getFontManager().getFont("Roboto medium 20").drawString("email", width / 4D - 5, height / 4D - 9, new Color(255,255,255,140));
        if (!passwordField.isFocused && passwordField.getText().isEmpty())
        Mega.INSTANCE.getFontManager().getFont("Roboto medium 20").drawString("password", width / 4D - 5, height / 4D + 16, new Color(255,255,255,140));
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        usernameField.textboxKeyTyped(typedChar, keyCode);
        passwordField.textboxKeyTyped(typedChar, keyCode);
        if (typedChar == '\t') {
            if (usernameField.isFocused())
            usernameField.setFocused(!usernameField.isFocused());
            if (passwordField.isFocused())
            passwordField.setFocused(!passwordField.isFocused());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        passwordField.mouseClicked(mouseX, mouseY, mouseButton);
        usernameField.mouseClicked(mouseX, mouseY, mouseButton);
        int offset = (int) (height / 8D - 15);
        int count = 0;
        for (Alt alt : alts)
        {
            if (MouseUtil.isInside(mouseX, mouseY,width / 2D - 145, offset - scrollOffset, 290, 30) && mouseY >= (height / 8D - 20) && mouseY <= (height / 8D - 20 + 400) && (mouseButton == 0 || mouseButton == 1)) {
                selectedAlt = alt;
                selectedAltIndex = count;
                try {
                    switch (alt.getAltType())
                    {
                        case MOJANG:
                        case MICROSOFT:
                            serviceSwitcher.updateService(AlteningServiceType.MOJANG);
                            break;
                        case THEALTENING:
                            serviceSwitcher.updateService(AlteningServiceType.THEALTENING);
                            break;
                    }
                    loginThread = new LoginThread(selectedAlt.getEmail(), selectedAlt.getPassword());
                    loginThread.start();
                    loggedAccount = selectedAlt;
                } catch (Exception ignored) {
                }
            }
            offset += 32;
            count++;
        }
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
                if (usernameField.getText().isEmpty()) {
                    if (!getClipboardString().isEmpty() && (getClipboardString().contains(":") || getClipboardString().contains("@alt.com"))) {
                        if (getClipboardString().split(":")[0].toLowerCase().endsWith("@alt.com")) {
                            serviceSwitcher.updateService(AlteningServiceType.THEALTENING);
                            Session auth = this.createSession(getClipboardString().split(":")[0], "Abc12345");
                            if (auth != null) {
                                alts.add(new Alt(auth.getUsername(), getClipboardString().split(":")[0],"Abc12345", AltType.THEALTENING));
                                loginThread = new LoginThread(getClipboardString().split(":")[0], "Abc12345");
                                loginThread.start();
                            }
                        } else {
                            serviceSwitcher.updateService(AlteningServiceType.MOJANG);
                        if (getClipboardString().split(":")[0].isEmpty()) {
                            alts.add(new Alt(getClipboardString().split(":")[0], getClipboardString().split(":")[0], getClipboardString().split(":")[1], AltType.MOJANG));
                            loginThread = new LoginThread(getClipboardString().split(":")[0], getClipboardString().split(":")[1]);
                            loginThread.start();
                        } else {
                            Session auth = this.createSession(getClipboardString().split(":")[0], getClipboardString().split(":")[1]);
                            if (auth != null) {
                                alts.add(new Alt(auth.getUsername(), getClipboardString().split(":")[0], getClipboardString().split(":")[1], AltType.MOJANG));
                                loginThread = new LoginThread(getClipboardString().split(":")[0], getClipboardString().split(":")[1]);
                                loginThread.start();
                            }
                        }
                    }
                    } else {
                        serviceSwitcher.updateService(AlteningServiceType.MOJANG);
                        final String random = "MEGA" + RandomUtil.randomString(12);
                        alts.add(new Alt(random, random, passwordField.getText(), AltType.MOJANG));
                        loginThread = new LoginThread(random, passwordField.getText());
                        loginThread.start();
                    }
                } else {
                    if (usernameField.getText().toLowerCase().endsWith("@alt.com")) {
                        serviceSwitcher.updateService(AlteningServiceType.THEALTENING);
                        Session auth = this.createSession(usernameField.getText(), "Abc12345");
                        if (auth != null) {
                            alts.add(new Alt(auth.getUsername(),usernameField.getText(), passwordField.getText(), AltType.THEALTENING));
                            loginThread = new LoginThread(usernameField.getText(), passwordField.getText());
                            loginThread.start();
                        }
                    } else {
                        serviceSwitcher.updateService(AlteningServiceType.MOJANG);
                   //     Session auth = this.createSession(usernameField.getText(), passwordField.getText());
                     //   if (auth != null) {
                            alts.add(new Alt(usernameField.getText(), usernameField.getText(), passwordField.getText(), AltType.MOJANG));
                            loginThread = new LoginThread(usernameField.getText(), passwordField.getText());
                            loginThread.start();
                       // }
                    }
                }
                usernameField.setText("");
                passwordField.setText("");
                saveAlts();
                break;
            case 2:
                if (selectedAlt != null) {
                    selectedAlt = null;
                    alts.remove(selectedAltIndex);
                    saveAlts();
                }
                break;
            case 3:
                mc.displayGuiScreen(prevScreen);
                break;
            case 4:
                if (mc.session.getUsername() != null)
                    setClipboardString(mc.session.getUsername());
                else
                    setClipboardString("Failed copying your name");
                break;
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        if (alts.isEmpty())
            loadAlts();

        scrollOffset = 0;
        current = 0;
        current2 = 0.5;
        Keyboard.enableRepeatEvents(true);
        int offset = 0, x = width / 4 - 10, y = height / 3 - 20;
        buttonList.clear();
        usernameField = new GuiTextField(3, fontRendererObj, x, y - 35, 80, 20);
        passwordField = new PasswordField(fontRendererObj, x, y - 10, 80, 20);
        buttonList.add(new RoundedButton(1, x, y + (offset += 25), 80, 20, new Color(10, 240, 20, 180), new Color(10, 240, 20, 130), 10, "Add"));
        buttonList.add(new RoundedButton(2, x, y + (offset += 25), 80, 20, new Color(201, 8, 8, 180), new Color(201, 8, 8, 130), 10, "Remove"));
        buttonList.add(new RoundedButton(3, x, y + offset + 25, 80, 20, new Color(201, 8, 8, 180), new Color(201, 8, 8, 130), 10, "Cancel"));
        buttonList.add(new RoundedButton(4,0,0,55,15,0,"Copy Name"));
    }

    private void handleScrolling(final int mouseX, final int mouseY, final double x, final double y, final double width, final double height) {
        if (Mouse.hasWheel() && MouseUtil.isInside(mouseX, mouseY, x, y, width, height)) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                current += 30;
                if (this.current < 0) {
                    this.current = 0;
                }
            } else if (wheel > 0) {
                this.current -= 30;
                if (this.current < 0) {
                    this.current = 0;
                }
            }
            this.scrollOffset = AnimationUtil.animate(scrollOffset, current, 2.5);
        }
    }

    public final void saveAlts() {
        final File file = new File(mc.mcDataDir, "Mega/Accounts.mega");
        if (new File(mc.mcDataDir, "Mega/").mkdir() || !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final Alt alt : Mega.INSTANCE.getAltManager().alts) {
                bufferedWriter.write(alt.getEmail() + ":" + (alt.getPassword().isEmpty() ? "!!PW_CRACKED_EMPTY!!" : alt.getPassword()) + ":" + alt.getAltType().name() +  "\n");
                bufferedWriter.flush();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void loadAlts() {
        try {
            File file = new File(mc.mcDataDir, "Mega/Accounts.mega");
            if (new File(mc.mcDataDir, "Mega/").mkdir() || !file.exists()) {
                file.createNewFile();
                return;
            }
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                bufferedReader.lines().forEach(s -> {
                    try {
                        String password = s.split(":")[1];
                        switch (AltType.valueOf(s.split(":")[2])) {
                            case MOJANG:
                            case MICROSOFT:
                                serviceSwitcher.updateService(AlteningServiceType.MOJANG);
                                break;
                            case THEALTENING:
                                serviceSwitcher.updateService(AlteningServiceType.THEALTENING);
                                break;
                        }

                        if (password.contains("!!PW_CRACKED_EMPTY!!")) {
                            Mega.INSTANCE.getAltManager().alts.add(new Alt(s.split(":")[0], s.split(":")[0],"",AltType.valueOf(s.split(":")[2])));
                        } else {
                            Session auth = this.createSession(s.split(":")[0], password);
                            LoginThread login = new LoginThread(s.split(":")[0], password);
                            login.start();
                            Mega.INSTANCE.getAltManager().alts.add(new Alt(auth.getUsername(), s.split(":")[0],password,AltType.valueOf(s.split(":")[2])));
                        }
                    } catch (Exception ignored) {

                    }
                });
            }
        } catch (IOException ignored) {
        }
    }

    private Session createSession(final String username, final String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
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

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}
