package cc.slack.ui.altmanager.gui;
import cc.slack.ui.altmanager.auth.SessionManager;
import cc.slack.utils.font.Fonts;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class GuiSessionLogin extends GuiScreen {
    private final GuiScreen previousScreen;

    private GuiButton cancelButton = null;
    private GuiButton loginButton = null;
    private GuiTextField sessionField = null;

    private String status = null;
    private String cause = null;
    private ExecutorService executor = null;
    private CompletableFuture<Void> task = null;

    public GuiSessionLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        sessionField = new GuiTextField(1, fontRendererObj, width / 2 - 100, height / 2, 200, 20);
        sessionField.setMaxStringLength(32767);
        sessionField.setFocused(true);

        buttonList.clear();

        buttonList.add(cancelButton = new GuiButton(0, width / 2 - 100, height / 2 + 100, "Cancel"));
        buttonList.add(loginButton = new GuiButton(1, width / 2 - 100, height / 2 + 25, "Log In"));

    }

    @Override
    public void onGuiClosed() {
        if (task != null && !task.isDone()) {
            task.cancel(true);
            executor.shutdownNow();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        sessionField.drawTextBox();
        if (status != null) {
            fontRendererObj.drawStringWithShadow(status, width / 2 - 100, height / 2 - 20, -1);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        sessionField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_ESCAPE) {
            actionPerformed(cancelButton);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button != null && button.id == 0) {
            mc.displayGuiScreen(new GuiAccountManager(previousScreen));
        }

        if (button != null && button.id == 1) {
            try {
                String token = sessionField.getText();
                String[] playerInfo = getProfileInfo(token);
                SessionManager.setSession(new Session(playerInfo[0], playerInfo[1], token,"mojang"));
                status = "§2Logged in as " + playerInfo[0];
            } catch (Exception e) {
                status = "§4Invalid token";
                e.printStackTrace();
            }
        }
    }

    public static String[] getProfileInfo(String token) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://api.minecraftservices.com/minecraft/profile");
        request.setHeader("Authorization", "Bearer " + token);
        CloseableHttpResponse response = client.execute(request);
        String jsonString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        String IGN = jsonObject.get("name").getAsString();
        String UUID = jsonObject.get("id").getAsString();
        return new String[]{IGN, UUID};
    }
}
