package com.alan.clients.ui.menu.impl.account.impl;

import com.alan.clients.Client;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.menu.component.button.MenuButton;
import com.alan.clients.ui.menu.component.button.impl.MenuTextButton;
import com.alan.clients.ui.menu.impl.account.AccountManagerScreen;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.account.auth.MicrosoftLogin;
import com.alan.clients.util.account.impl.MicrosoftAccount;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.gui.textbox.TextAlign;
import com.alan.clients.util.gui.textbox.TextBox;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.shader.RiseShaders;
import com.alan.clients.util.shader.base.ShaderRenderType;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.web.Browser;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.alan.clients.layer.Layers.BLUR;
import static com.alan.clients.layer.Layers.REGULAR;

public class AddCookieScreen extends GuiScreen implements Accessor {
    private static final Font FONT_RENDERER = Fonts.MAIN.get(36, Weight.BOLD);
    private final MenuButton[] menuButtons = new MenuButton[4];
    private static TextBox usernameBox;
    private static GuiScreen reference;
    private static String text_to_render = "Select file";
    private static String[] cookie_string;
    private Animation animation;
    private static final Gson gson = new Gson();

    private static final Runnable TEXT_BOX_RUNNABLE = () -> usernameBox.setSelected(true);
    private static final Runnable CANCEL_RUNNABLE = () -> {

        mc.displayGuiScreen(new AccountManagerScreen(reference));
    };

    private static final Runnable SELECT_FILE_RUNNABLE = () -> {
        new Thread(() -> {
            FileDialog dialog = new FileDialog((Frame) null, "Select Cookie File");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            dialog.dispose();
            String path = new File(dialog.getDirectory() + dialog.getFile()).getAbsolutePath();
            try {
                StringBuilder content = new StringBuilder();
                Scanner scanner = new Scanner(new FileReader(path));
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine()).append("\n");
                }
                scanner.close();
                usernameBox.setText(dialog.getFile());
                text_to_render = "Selected file!";
                cookie_string = content.toString().split("\n");
            } catch (IOException e) {
                text_to_render = "Error (read)";
            }
        }, "Select Cookie File").start();
    };

    private static final Runnable LOGIN_RUNNABLE = () -> {
        new Thread(() -> {
            try {
                if (cookie_string.length != 0) {
                    StringBuilder cookies = new StringBuilder();
                    ArrayList<String> cooki = new ArrayList<>();
                    for (String cookie : cookie_string) {
                        if (cookie.split("\t")[0].endsWith("login.live.com") && !cooki.contains(cookie.split("\t")[5])) {
                            cookies.append(cookie.split("\t")[5]).append("=").append(cookie.split("\t")[6]).append("; ");
                            cooki.add(cookie.split("\t")[5]);
                        }
                    }
                    cookies = new StringBuilder(cookies.substring(0, cookies.length() - 2));
                    HttpsURLConnection connection = (HttpsURLConnection) new URL("https://sisu.xboxlive.com/connect/XboxLive/?state=login&cobrandId=8058f65d-ce06-4c30-9559-473c9275a65d&tid=896928775&ru=https%3A%2F%2Fwww.minecraft.net%2Fen-us%2Flogin&aid=1142970254").openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                    connection.setRequestProperty("Accept-Encoding", "niggas");
                    connection.setRequestProperty("Accept-Language", "en-US;q=0.8");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
                    connection.setInstanceFollowRedirects(false);
                    connection.connect();

                    String location = connection.getHeaderField("Location").replaceAll(" ", "%20");
                    connection = (HttpsURLConnection) new URL(location).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                    connection.setRequestProperty("Accept-Encoding", "niggas");
                    connection.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7");
                    connection.setRequestProperty("Cookie", cookies.toString());
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
                    connection.setInstanceFollowRedirects(false);
                    connection.connect();

                    String location2 = connection.getHeaderField("Location");

                    connection = (HttpsURLConnection) new URL(location2).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                    connection.setRequestProperty("Accept-Encoding", "niggas");
                    connection.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7");
                    connection.setRequestProperty("Cookie", cookies.toString());
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
                    connection.setInstanceFollowRedirects(false);
                    connection.connect();

                    String location3 = connection.getHeaderField("Location");
                    String accessToken = location3.split("accessToken=")[1];

                    String decoded = new String(Base64.getDecoder().decode(accessToken), StandardCharsets.UTF_8).split("\"rp://api.minecraftservices.com/\",")[1];
                    String token = decoded.split("\"Token\":\"")[1].split("\"")[0];
                    String uhs = decoded.split(Pattern.quote("{\"DisplayClaims\":{\"xui\":[{\"uhs\":\""))[1].split("\"")[0];

                    String xbl = "XBL3.0 x=" + uhs + ";" + token;

                    final MicrosoftLogin.McResponse mcRes = gson.fromJson(
                            Browser.postExternal("https://api.minecraftservices.com/authentication/login_with_xbox",
                                    "{\"identityToken\":\"" + xbl + "\",\"ensureLegacyEnabled\":true}", true),
                            MicrosoftLogin.McResponse.class);

                    if (mcRes == null) {
                        text_to_render = "Error (Invalid Acc)";
                        return;
                    }

                    final MicrosoftLogin.ProfileResponse profileRes = gson.fromJson(
                            Browser.getBearerResponse("https://api.minecraftservices.com/minecraft/profile", mcRes.access_token),
                            MicrosoftLogin.ProfileResponse.class);

                    if (profileRes == null) {
                        text_to_render = "Error (Invalid Acc)";
                        return;
                    }
                    ;
                    MicrosoftAccount microsoftAccount = new MicrosoftAccount(profileRes.name, profileRes.id, mcRes.access_token, "");
                    microsoftAccount.login();
//                    AccountManagerScreen.addAccount(microsoftAccount);
                    CANCEL_RUNNABLE.run();
                }
            } catch (Exception e) {
                text_to_render = "Invalid Account";
            }

        }, "Login To Cookie").start();
    };

    private static final Runnable BACKGROUND_RUNNABLE = () -> {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        RenderUtil.rectangle(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Color.BLACK);
    };

    public AddCookieScreen() {
        reference = this;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Renders the background.
        animation.run(0);
        RiseShaders.MAIN_MENU_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, null);
        getLayer(BLUR).add(BACKGROUND_RUNNABLE);
        // FPS counter on dev builds
        if (Client.DEVELOPMENT_SWITCH) mc.fontRendererObj.drawWithShadow(Minecraft.getDebugFPS() + "", 0, 0, -1);

        getLayer(REGULAR).add(() -> {
            FONT_RENDERER.drawCentered(text_to_render, width / 2, height / 2 - 64 + animation.getValue(), Color.WHITE.getRGB());
            usernameBox.draw();
        });

        // Renders the buttons.
        for (MenuButton button : menuButtons) {
            button.draw(mouseX, mouseY, partialTicks);
        }

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        usernameBox.click(mouseX, mouseY, mouseButton);

        for (MenuButton button : menuButtons) {
            if (MouseUtil.isHovered(button.getX(), button.getY(), button.getWidth(), button.getHeight(), mouseX, mouseY)) {
                button.runAction();
                break;
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (usernameBox.isSelected()) {
            usernameBox.key(typedChar, keyCode);
        }
    }

    @Override
    public void initGui() {
        int boxWidth = 200;
        int boxHeight = 24;
        int padding = 4;
        float buttonWidth = (boxWidth - padding * 2) / 3.0F;

        Vector2d position = new Vector2d(this.width / 2 - boxWidth / 2, this.height / 2 - 24);
        usernameBox = new TextBox(position.offset(boxWidth / 2, 8), Fonts.MAIN.get(24, Weight.BOLD), Color.WHITE, TextAlign.CENTER, "Username", boxWidth);

        menuButtons[0] = new MenuTextButton(position.x, position.y, boxWidth, boxHeight, TEXT_BOX_RUNNABLE, "");
        menuButtons[1] = new MenuTextButton(position.x, position.y + boxHeight + padding, boxWidth, boxHeight, SELECT_FILE_RUNNABLE, "Select File");
        menuButtons[2] = new MenuTextButton(position.x, position.y + (boxHeight + padding) * 2, (boxWidth / 2) * 1.5, boxHeight, LOGIN_RUNNABLE, "Login Without Adding");
        menuButtons[3] = new MenuTextButton(position.x + (boxWidth / 2) * 1.5 + padding, position.y + (boxHeight + padding) * 2, (boxWidth / 2) * 0.5 - padding, boxHeight, CANCEL_RUNNABLE, "Cancel");

        animation = new Animation(Easing.EASE_OUT_QUINT, 600);
        animation.setStartValue(-200);
    }
}
