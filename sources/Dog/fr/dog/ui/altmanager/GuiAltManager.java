package fr.dog.ui.altmanager;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.dog.Dog;
import fr.dog.util.packet.RequestUtil;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import okhttp3.*;
import org.lwjglx.Sys;
import org.lwjglx.input.Keyboard;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GuiAltManager extends GuiScreen {
    private final GuiScreen parent;
    private GuiTextField nameField;
    private String text = "Not pressed";

    public GuiAltManager(GuiScreen parent) {
        this.parent = parent;
    }

    public void updateScreen() {
        this.nameField.updateCursorCounter();
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        this.buttonList.clear();

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("Log In")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("Browser Login")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 144 + 12, I18n.format("Random Username")));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 168 + 12, I18n.format("Hass Alts")));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 168 + 24 + 12, I18n.format("UUID:Token")));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 100, this.height / 4 + 168 + 48 + 12, I18n.format("Set Funny Skin")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 168 + 48 + 24 + 12, I18n.format("Back")));

        this.nameField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
        this.nameField.setMaxStringLength(4485);
        this.nameField.setFocused(true);

        this.buttonList.get(0).enabled = !this.nameField.getText().isEmpty() && this.nameField.getText().split(":").length > 0;
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        try{
            if (button.enabled) {
                if (button.id == 1) {
                    this.onGuiClosed();
                    this.mc.displayGuiScreen(null);
                }
                if (button.id == 2) {
                    text = "Copied link to your keyboard";
                    MicrosoftLogin.getRefreshToken(refreshToken -> {
                        if (refreshToken != null) {
                            new Thread(() -> {
                                MicrosoftLogin.LoginData loginData = loginWithRefreshToken(refreshToken);
                            }).start();
                        }
                    });
                } else {
                    text = "Not pressed";
                }
                if (button.id == 3) {
                    mc.setSession(new Session("DogClient" + +Minecraft.getSystemTime() % 1000L, "DogClient" + +Minecraft.getSystemTime() % 1000L, "0", "legacy"));
                } else if (button.id == 0) {
                    mc.setSession(new Session(nameField.getText(), "", "", "mojang"));
                }else if(button.id == 4){
                    String dc = getDecrypt(nameField.getText());
                    String name = getName(dc.split(":")[0]);
                    mc.setSession(new Session(name, dc.split(":")[0], dc.split(":")[1], "legacy"));
                    RequestUtil.requestResult.apply("/addMinecraftAccount?token=" + Dog.getInstance().getToken() + "&username=" + name + "&uuid=" + dc.split(":")[0]);
                }else if(button.id == 5){
                    String dc = nameField.getText();
                    String name = getName(dc.split(":")[0]);
                    mc.setSession(new Session(name, dc.split(":")[0], dc.split(":")[1], "legacy"));
                    RequestUtil.requestResult.apply("/addMinecraftAccount?token=" + Dog.getInstance().getToken() + "&username=" + name + "&uuid=" + dc.split(":")[0]);
                }else if(button.id == 6){
                    OkHttpClient client = new OkHttpClient();
                    String jsonPayload = String.format("{\"url\": \"%s\", \"variant\": \"classic\"}", "https://s.namemc.com/i/20731fb8c0980a5f.png");
                    RequestBody body = RequestBody.create(
                            jsonPayload, MediaType.get("application/json; charset=utf-8"));
                    Request request = new Request.Builder()
                            .url(String.format("https://api.minecraftservices.com/minecraft/profile/skins/%s", mc.getSession().getProfile().getId()))
                            .post(body)
                            .addHeader("Authorization", "Bearer " + mc.getSession().getToken())
                            .build();
                    try (Response response = client.newCall(request).execute()) {
                        if (response.isSuccessful()) {
                            System.out.println("Skin updated successfully!");
                        } else {
                            System.out.println("Failed to update skin: " + response.code() + " " + response.message());
                            System.out.println(response.body().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            text = "Shit didnt seem to work out very well";
        }

    }

    private MicrosoftLogin.LoginData loginWithRefreshToken(String refreshToken) {
        final MicrosoftLogin.LoginData loginData = MicrosoftLogin.login(refreshToken);
        mc.setSession(new Session(loginData.username, loginData.uuid, loginData.mcToken, "microsoft"));

        if (mc.getSession().getUsername() != null) {
            RequestUtil.requestResult.apply("/addMinecraftAccount?token=" + Dog.getInstance().getToken() + "&username=" + loginData.username + "&uuid=" + loginData.uuid);
        }

        return loginData;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
            this.buttonList.get(0).enabled = !this.nameField.getText().isEmpty() && this.nameField.getText().split(":").length > 0;
        } else if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }else if(keyCode == Keyboard.KEY_ESCAPE){
            mc.displayGuiScreen(parent);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        this.drawCenteredString(this.fontRendererObj, I18n.format("Log in to an alt | Status: " + text), this.width / 2, 20, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("Username / Combo"), this.width / 2, 100, 10526880);
        this.drawCenteredString(this.fontRendererObj, I18n.format("Name: " + mc.getSession().getUsername()), this.width / 2, 145, 10526880);

        this.nameField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    @SneakyThrows
    public String getName(String uuid) {
        String name = "";
        try {
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid).openStream()));
            name = (((JsonObject)new JsonParser().parse(in)).get("name")).toString().replaceAll("\"", "");
            in.close();
        } catch (Exception e) {
            System.out.println("Unable to get Name of: " + uuid + "!");
            name = "None - Issue";
        }
        return name;
    }

    @SneakyThrows
    public String getDecrypt(String bas){
        SecretKeySpec key = new SecretKeySpec("LiticaneFurryFemboy69420".getBytes(StandardCharsets.UTF_8), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(bas));
        return new String(decrypted).replaceAll("[^a-zA-Z0-9-._:]", "");
    }
}