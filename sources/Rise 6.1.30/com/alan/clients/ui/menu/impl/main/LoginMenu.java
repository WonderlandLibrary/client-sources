package com.alan.clients.ui.menu.impl.main;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.BackendPacketEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.menu.Menu;
import com.alan.clients.ui.menu.component.button.MenuButton;
import com.alan.clients.ui.menu.component.button.impl.MenuTextButton;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.NetworkUtil;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.gui.textbox.TextAlign;
import com.alan.clients.util.gui.textbox.TextBox;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.shader.RiseShaders;
import com.alan.clients.util.shader.base.ShaderRenderType;
import com.alan.clients.util.vantage.HWIDUtil;
import com.alan.clients.util.vector.Vector2d;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import rip.vantage.commons.packet.impl.server.protection.S2CPacketAuthenticationFinish;
import rip.vantage.commons.util.time.StopWatch;
import rip.vantage.network.core.Network;
import rip.vantage.network.handler.WebSocketClient;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.alan.clients.layer.Layers.BLUR;
import static com.alan.clients.layer.Layers.REGULAR;

public final class LoginMenu extends Menu {

    private final Font fontRenderer = Fonts.MAIN.get(64, Weight.LIGHT);
    private Animation animation = new Animation(Easing.EASE_OUT_QUINT, 600);
    private final Animation fadeAnimation = new Animation(Easing.EASE_IN_OUT_CUBIC, 3000);

    private MenuTextButton emailButton;
    private MenuTextButton loginButton;
    private TextBox emailBox;

    private MenuButton[] menuButtons;

    private boolean attemptedLogin;
    private StopWatch stopWatch = new StopWatch();
    private String latestVersion, message;

    public void updateLatestVersion() {
        latestVersion = NetworkUtil.request("https://raw.githubusercontent.com/risellc/LatestRiseVersion/main/Version");
    }

    public LoginMenu() {
        Client.INSTANCE.getEventBus().register(this);
    }

    @Override
    public void onGuiClosed() {
        Client.INSTANCE.getEventBus().unregister(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Renders the background
        if (this.fadeAnimation.getValue() < 255) {
            RiseShaders.MAIN_MENU_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, null);
        }

        ScaledResolution scaledResolution = mc.scaledResolution;

        getLayer(BLUR).add(() -> RenderUtil.rectangle(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Color.BLACK));

        // Renders the buttons
        this.emailButton.draw(mouseX, mouseY, partialTicks);
        this.loginButton.draw(mouseX, mouseY, partialTicks);

        getLayer(REGULAR).add(() -> {
            this.emailBox.draw();

            // Update the animation
            final double destination = this.emailButton.getY() - this.fontRenderer.height();
            this.animation.run(destination);

            // Render the rise "logo"
            final double value = this.animation.getValue();
            final Color color = ColorUtil.withAlpha(Color.WHITE, (int) (value / destination * 200));
            this.fontRenderer.drawCentered("Welcome", width / 2.0F, value - 10, color.getRGB());

            // Draws failed login info
            if (stopWatch.finished(5000)) {
                if (this.attemptedLogin) {
                    this.reportFailedLogin("Login is taking longer than expected");
                }

                this.attemptedLogin = false;
            } else if (message != null) {
                Fonts.MAIN.get(18, Weight.LIGHT).drawCentered(message, width / 2.0F, value + 26, Color.RED.getRGB());
            }

            // Draw bottom right text
            Fonts.MAIN.get(18, Weight.REGULAR).drawRight(Client.CREDITS,
                    scaledResolution.getScaledWidth() - 5, scaledResolution.getScaledHeight() - 20,
                    ColorUtil.withAlpha(TEXT_SUBTEXT, 100).getRGB());

            Fonts.MAIN.get(12, Weight.REGULAR).drawRight(Client.COPYRIGHT,
                    scaledResolution.getScaledWidth() - 5, scaledResolution.getScaledHeight() - 10,
                    ColorUtil.withAlpha(TEXT_SUBTEXT, 100).getRGB());

            this.fadeAnimation.run(0);
            RenderUtil.rectangle(0, 0, mc.displayWidth, mc.displayHeight, new Color(0, 0, 0, (int) fadeAnimation.getValue()));
        });
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.menuButtons == null) return;

        // If doing a left click and the mouse is hovered over a button, execute the buttons action (runnable)
        if (mouseButton == 0) {
            for (MenuButton menuButton : this.menuButtons) {
                if (MouseUtil.isHovered(menuButton.getX(), menuButton.getY(), menuButton.getWidth(), menuButton.getHeight(), mouseX, mouseY)) {
                    menuButton.runAction();
                    break;
                }
            }

            this.emailBox.click(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.emailBox.key(typedChar, keyCode);

        // Tab to go between text boxes
        if (keyCode == Keyboard.KEY_TAB) {
            this.emailBox.setSelected(!this.emailBox.isSelected());
        }

        // Enter to log in
        else if (keyCode == Keyboard.KEY_RETURN && !this.emailBox.getText().isEmpty()) {
            this.loginButton.runAction();
        }
    }

    public void initNetworkManager(String username) {
        if (this.attemptedLogin) {
            return;
        }

//        if (Arrays.stream(Category.values()).anyMatch(category -> category.getName().equalsIgnoreCase("ai")) && !Client.DEVELOPMENT_SWITCH) {
//            System.out.println("Just let alan know he knows how to fix");
//            return;
//        }

        try {
            if (latestVersion == null) updateLatestVersion();

            String[] versionFull = Client.VERSION_FULL.split("\\.");
            String[] versionLatest = latestVersion.split("\\.");

            for (int i = 0; i < 2; i++) {
                if (Float.parseFloat(versionFull[i]) < Float.parseFloat(versionLatest[i])) {
                    System.out.println("A newer version is available please update your client on https://Vantage.Rip");
                    reportFailedLogin("A newer version is available please update your client on https://Vantage.Rip");
                    return;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Network.getInstance().setUsername(username);
        Network.getInstance().reconnect();
        this.attemptedLogin = true;
        this.stopWatch.reset();
    }

    @Override
    public void initGui() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 180;
        int buttonHeight = 24;
        int buttonSpacing = 6;
        int buttonX = centerX - buttonWidth / 2;
        int buttonY = centerY - buttonHeight / 2 - buttonSpacing / 2 - buttonHeight / 2;

        // Re-creates the buttons for not having to care about the animation reset
        this.emailButton = new MenuTextButton(buttonX, buttonY, buttonWidth, buttonHeight, () -> {
        }, "");

        this.loginButton = new MenuTextButton(buttonX, buttonY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight, () -> this.initNetworkManager(this.emailBox.getText()), "Login");

        this.emailBox = new TextBox(new Vector2d(centerX, buttonY + 9), Fonts.MAIN.get(24, Weight.BOLD), Color.WHITE, TextAlign.CENTER, "Username", buttonWidth * 5);
        // Re-create the logo animation for not having to care about its reset
        this.animation = new Animation(Easing.EASE_OUT_QUINT, 600);

        // Putting all buttons in an array for handling mouse clicks
        this.menuButtons = new MenuButton[]{this.emailButton, this.loginButton};

        this.fadeAnimation.setValue(255);
        this.fadeAnimation.reset();
        this.attemptedLogin = false;
    }

    public void reportFailedLogin(String message) {
        this.message = message;
        this.stopWatch.reset();
        this.attemptedLogin = false;
    }

    @EventLink
    public final Listener<BackendPacketEvent> onBackend = event -> {
        if (event.getPacket() instanceof S2CPacketAuthenticationFinish) {
            S2CPacketAuthenticationFinish wrapper = (S2CPacketAuthenticationFinish) event.getPacket();

            System.out.println("Auth");
            WebSocketClient.keepAlive.reset();

            if (wrapper.isSuccess()) {
                mc.displayGuiScreen(new MainMenu());

                Client.INSTANCE.getConfigManager().setupLatestConfig();
            } else {
                reportFailedLogin(wrapper.getE());

                try {
                    StringSelection selection = new StringSelection(HWIDUtil.getHWID());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, new StringSelection("Rise"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/1mLN4XYZifIA6UdnsnTdN-JGMLpBz_T2e/view?usp=sharing"));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
