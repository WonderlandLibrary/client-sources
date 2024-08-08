package lol.point.returnclient.ui;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import lol.point.Return;
import lol.point.returnclient.managers.SessionManager;
import lol.point.returnclient.module.impl.client.Theme;
import lol.point.returnclient.ui.components.UIButton2;
import lol.point.returnclient.ui.components.UITextInput;
import lol.point.returnclient.ui.utils.MicrosoftAuth;
import lol.point.returnclient.util.render.FastFontRenderer;
import lol.point.returnclient.util.render.shaders.ShaderUtil;
import lol.point.returnclient.util.system.StringUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AltMenu extends GuiScreen {
    private final GuiScreen previousScreen;

    private String status = null;
    private String cause = null;
    private ExecutorService executor = null;
    private CompletableFuture<Void> task = null;

    private final List<UIButton2> buttons = new ArrayList<>();
    private final List<UITextInput> inputs = new ArrayList<>();

    private final FastFontRenderer big = Return.INSTANCE.fontManager.getFont("Nano-Regular 200");
    private final FastFontRenderer small = Return.INSTANCE.fontManager.getFont("Nano-Regular 20");
    private final FastFontRenderer text = Return.INSTANCE.fontManager.getFont("ProductSans-Regular 18");
    private final FastFontRenderer copyRight = Return.INSTANCE.fontManager.getFont("ProductSans-Bold 18");
    private final FastFontRenderer icons = Return.INSTANCE.fontManager.getFont("ReturnNew-Icons 18");

    public AltMenu(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    public void initGui() {
        buttons.clear();
        buttons.add(new UIButton2("Login", "m", 200, 20));
        buttons.add(new UIButton2("Login with browser", "A", 200, 20));
        buttons.add(new UIButton2("Random cracked", "n", 200, 20));
        buttons.add(new UIButton2("Back", "P", 200, 20));
        inputs.clear();
        inputs.add(new UITextInput("Email:Password", "b", 200, 20));
    }

    public void onGuiClosed() {
        if (task != null && !task.isDone()) {
            task.cancel(true);
            executor.shutdownNow();
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        Color gradient1, gradient2;
        gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
        gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;

        RenderUtil.rectangle(0, 0, width, height, new Color(22, 22, 22));
        ShaderUtil.renderGlow(() -> {
            text.drawStringWithShadow(mc.getSession().getUsername(), 5, 5, -1);
            big.drawStringWithShadow("R", (sr.getScaledWidth() / 2f) - (59f / 2f), (sr.getScaledHeight() / 2f) - 105, gradient1);
            small.drawStringWithShadow("eturn", (sr.getScaledWidth() / 2f) - (59f / 2f) + 14, (sr.getScaledHeight() / 2f) - 105 + 67, -1);
        }, 2, 2, 0.86f);

        ShaderUtil.renderGlow(() -> {
            if (status != null) {
                text.drawCenteredString(status, (float) width / 2, (float) height / 2 - text.getHeight() / 2 - text.getHeight() * 2, -1);
            }

            if (cause != null) {
                text.drawCenteredString(cause, (float) width / 2, (float) height / 2 - text.getHeight() / 2 - text.getHeight() * 2 + 10, -1);
            }
        }, 2, 2, 0.86f);

        if (status != null) {
            text.drawCenteredString(status, (float) width / 2, (float) height / 2 - text.getHeight() / 2 - text.getHeight() * 2, -1);
        }

        if (cause != null) {
            text.drawCenteredString(cause, (float) width / 2, (float) height / 2 - text.getHeight() / 2 - text.getHeight() * 2 + 10, -1);
        }

        text.drawStringWithShadow(mc.getSession().getUsername(), 5, 5, -1);
        big.drawStringWithShadow("R", (sr.getScaledWidth() / 2f) - (59f / 2f), (sr.getScaledHeight() / 2f) - 105, gradient1);
        small.drawStringWithShadow("eturn", (sr.getScaledWidth() / 2f) - (59f / 2f) + 14, (sr.getScaledHeight() / 2f) - 105 + 67, -1);
        copyRight.drawString("  2024-2025 Return Client. All Rights Reserved", ((float) sr.getScaledWidth() / 2f) - (copyRight.getWidth("  2024-2025 Return Client. All Rights Reserved") / 2), sr.getScaledHeight() - 12, new Color(60, 60, 60));
        icons.drawString("o", ((float) sr.getScaledWidth() / 2f) - (copyRight.getWidth("@@ 2024-2025 Return Client. All Rights Reserved") / 2) + 2, sr.getScaledHeight() - 11, new Color(60, 60, 60));

        float yOffset1 = cause != null ? 10 : 0;
        for (UITextInput input : inputs) {
            float iconX = ((float) sr.getScaledWidth() / 2f) - (200 / 2f), iconY = ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset1;
            if (input.placeHolderText.equals("Email:Password")) {
                iconX += 5;
                iconY += 7;
            }
            input.drawButton(mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset1, iconX, iconY);
            yOffset1 += 21;
        }

        float yOffset = yOffset1;
        for (UIButton2 button : buttons) {
            float iconX = ((float) sr.getScaledWidth() / 2f) - (200 / 2f), iconY = ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset;
            switch (button.buttonText) {
                case "Login", "Login with browser" -> {
                    iconX += 4;
                    iconY += 6.5f;
                }
                case "Random cracked" -> {
                    iconX += 4.5f;
                    iconY += 7;
                }
                case "Back" -> {
                    iconX += 5;
                    iconY += 6.5f;
                }
            }
            button.drawButton(mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset, iconX, iconY);
            yOffset += 21;
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);

        float yOffset1 = cause != null ? 10 : 0;
        for (UITextInput input : inputs) {
            input.mouseClicked(mouseButton, mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset1);
            yOffset1 += 21;
        }

        float yOffset = yOffset1;
        for (UIButton2 button : buttons) {
            if (button.mouseClicked(mouseButton, mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset)) {
                switch (button.buttonText) {
                    case "Login" -> {
                        String inputText = inputs.getFirst().inputText;
                        String[] parts = inputText.split(":", 2);
                        String username;
                        String password;

                        if (parts.length == 2) {
                            username = parts[0].trim();
                            password = parts[1].trim();
                        } else {
                            username = inputText.trim();
                            password = "";

                            mc.session = new Session(username, "", "", "legacy");
                            status = String.format("§aLogged in as §e%s §c(cracked)§a!", username);
                        }

                        if (!password.isEmpty()) {
                            new Thread(() -> {
                                MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                                try {
                                    MicrosoftAuthResult result = authenticator.loginWithCredentials(username, password);
                                    status = String.format("§aLogged in as §e%s §b(microsoft)§a!", result.getProfile().getName());
                                    mc.session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");
                                } catch (MicrosoftAuthenticationException e) {
                                    status = String.format("§c%s§r", e.getMessage());
                                    cause = String.format("§c§o%s§r", e.getCause().getMessage());
                                }
                            }).start();
                        }
                    }
                    case "Random cracked" -> {
                        String randomUsername = StringUtil.generateRandomString("Return_", 11);
                        status = String.format("§aLogged in as §e%s §c(cracked)§a!", randomUsername);
                        mc.session = new Session(randomUsername, "", "", "legacy");
                    }
                    case "Login with browser" -> {
                        if (task == null) {
                            if (executor == null) {
                                executor = Executors.newSingleThreadExecutor();
                            }
                            status = "§rCheck your browser to continue...";
                            task = MicrosoftAuth.acquireMSAuthCode(executor)
                                    .thenComposeAsync(msAuthCode -> {
                                        status = "Acquiring Microsoft access token";
                                        return MicrosoftAuth.acquireMSAccessToken(msAuthCode, executor);
                                    })
                                    .thenComposeAsync(msAccessToken -> {
                                        status = "§rAcquiring Xbox access token";
                                        return MicrosoftAuth.acquireXboxAccessToken(msAccessToken, executor);
                                    })
                                    .thenComposeAsync(xboxAccessToken -> {
                                        status = "§rAcquiring Xbox XSTS token";
                                        return MicrosoftAuth.acquireXboxXstsToken(xboxAccessToken, executor);
                                    })
                                    .thenComposeAsync(xboxXstsData -> {
                                        status = "§rAcquiring Minecraft access token";
                                        return MicrosoftAuth.acquireMCAccessToken(
                                                xboxXstsData.get("Token"), xboxXstsData.get("uhs"), executor
                                        );
                                    })
                                    .thenComposeAsync(mcToken -> {
                                        status = "§rFetching your Minecraft profile";
                                        return MicrosoftAuth.login(mcToken, executor);
                                    })
                                    .thenAccept(session -> {
                                        SessionManager.setSession(session);
                                        status = String.format("§aLogged in as §e%s §b(microsoft)§a!", session.getUsername());
                                        task = null;
                                        executor = null;
                                    })
                                    .exceptionally(error -> {
                                        status = String.format("§c%s§r", error.getMessage());
                                        cause = String.format("§c§o%s§r", error.getCause().getMessage());
                                        return null;
                                    });
                        }
                    }
                    case "Back" -> mc.displayGuiScreen(previousScreen);
                }
            }
            yOffset += 21;
        }
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(previousScreen);
        }

        for (UITextInput input : inputs) {
            input.keyTyped(keyCode, typedChar);
        }
    }
}
