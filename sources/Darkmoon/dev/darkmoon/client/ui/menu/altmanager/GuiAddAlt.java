package dev.darkmoon.client.ui.menu.altmanager;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import dev.darkmoon.client.ui.menu.altmanager.alt.Alt;
import dev.darkmoon.client.ui.menu.altmanager.alt.AltManager;
import dev.darkmoon.client.ui.menu.widgets.CustomButton;
import dev.darkmoon.client.ui.menu.widgets.PasswordField;
import dev.darkmoon.client.ui.menu.widgets.TextField;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.math.Vec2i;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import dev.darkmoon.client.utility.render.particle.Particle;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;

public class GuiAddAlt extends GuiScreen {
    private dev.darkmoon.client.ui.menu.widgets.TextField nameField;
    private PasswordField passwordField;
    private final ArrayList<Particle> particles = new ArrayList<>();
    private String status;
    private final GuiAltManager guiAltManager;

    protected GuiAddAlt() {
        this.status = TextFormatting.GRAY + "Add Alt";
        this.guiAltManager = new GuiAltManager();
    }

    @Override
    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledWidth());
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledHeight());

        Keyboard.enableRepeatEvents(true);

        this.nameField = new TextField(this.eventButton, Fonts.mntsb17, scaledWidth / 2 - 100, scaledHeight / 2 - 40, 200, 20);
        this.passwordField = new PasswordField(this.eventButton, Fonts.mntsb17, scaledWidth / 2 - 100, scaledHeight / 2 + 5, 200, 20);

        this.addButton(new CustomButton(0, scaledWidth / 2 - 100, scaledHeight / 2 + 40, 98, 20, "Добавить"));
        this.addButton(new CustomButton(1, scaledWidth / 2 + 2, scaledHeight / 2 + 40, 98, 20, "Вернуться"));
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledWidth());
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledHeight());
        Vec2i mouse = DarkMoon.getInstance().getScaleMath().getMouse(mouseX, mouseY, scaledResolution);
        DarkMoon.getInstance().getScaleMath().pushScale();

        RenderUtility.drawRect(0, 0, scaledWidth, scaledHeight, new Color(20, 20, 20).getRGB());


        Fonts.mntsb17.drawStringWithShadow("Ник / E-Mail", scaledWidth / 2f - 100, scaledHeight / 2f - 52, -7829368);
        this.nameField.drawTextBox();

        Fonts.mntsb17.drawStringWithShadow("Пароль", scaledWidth / 2f - 100, scaledHeight / 2f - 7, -7829368);
        this.passwordField.drawTextBox();

        Fonts.mntsb17.drawCenteredString(this.status, scaledWidth / 2F, 10, -1);
        super.drawScreen(mouse.getX(), mouse.getY(), partialTicks);
        DarkMoon.getInstance().getScaleMath().popScale();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        Vec2i mouse = DarkMoon.getInstance().getScaleMath().getMouse(mouseX, mouseY, scaledResolution);

        this.nameField.mouseClicked(mouse.getX(), mouse.getY(), mouseButton);
        this.passwordField.mouseClicked(mouse.getX(), mouse.getY(), mouseButton);

        super.mouseClicked(mouse.getX(), mouse.getY(), mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(new GuiAltManager());
            return;
        }

        this.nameField.textboxKeyTyped(typedChar, keyCode);
        this.passwordField.textboxKeyTyped(typedChar, keyCode);
        if (typedChar == '\t' && (this.nameField.isFocused() || this.passwordField.isFocused())) {
            this.nameField.setFocused(!this.nameField.isFocused());
            this.passwordField.setFocused(!this.passwordField.isFocused());
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                AddAltThread login = new AddAltThread(this.nameField.getText(), this.passwordField.getText());
                login.start();
                break;
            case 1:
                this.mc.displayGuiScreen(guiAltManager);
                break;
        }
        super.actionPerformed(button);
    }

    private static void setStatus(GuiAddAlt guiAddAlt, String status) {
        guiAddAlt.status = status;
    }

    private class AddAltThread extends Thread {
        private final String password;
        private final String username;

        AddAltThread(String username, String password) {
            this.username = username;
            this.password = password;
            GuiAddAlt.setStatus(GuiAddAlt.this, TextFormatting.GRAY + "Add Alt");
        }

        private void checkAndAddAlt(String username, String password) {
            try {
                YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
                YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
                auth.setUsername(username);
                auth.setPassword(password);

                try {
                    auth.logIn();
                    AltManager.addAccount(new Alt(username, password, auth.getSelectedProfile().getName(), Alt.Status.Working, Alt.getCurrentDate()));
                    GuiAddAlt.setStatus(GuiAddAlt.this, TextFormatting.GREEN + "Добавлен аккаунт - " + TextFormatting.RED + (this.username) + TextFormatting.WHITE + " (Mojang)");
                } catch (AuthenticationException exception) {
                    GuiAddAlt.setStatus(GuiAddAlt.this, TextFormatting.RED + "Ошибка подключения!");
                    exception.printStackTrace();
                }
            } catch (Throwable e) {
                GuiAddAlt.setStatus(GuiAddAlt.this, TextFormatting.RED + "Ошибка!");
                e.printStackTrace();
            }
        }

        public void run() {
            if (this.password.equals("")) {
                AltManager.addAccount(new Alt(this.username, "", Alt.getCurrentDate()));
                GuiAddAlt.setStatus(GuiAddAlt.this, TextFormatting.GREEN + "Добавлен аккаунт - " + TextFormatting.RED + (this.username) + TextFormatting.WHITE + " (без лицензии)");
            } else {
                GuiAddAlt.setStatus(GuiAddAlt.this, TextFormatting.AQUA + "Подключение...");
                this.checkAndAddAlt(this.username, this.password);
            }
        }
    }
}
