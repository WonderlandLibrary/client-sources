package dev.darkmoon.client.ui.menu.proxy;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.manager.proxy.Proxy;
import dev.darkmoon.client.manager.proxy.ProxyManager;
import dev.darkmoon.client.manager.proxy.TestPing;
import dev.darkmoon.client.ui.menu.main.GuiMainMenuElement;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class GuiProxy extends GuiScreen {
    private boolean isSocks4 = false;
    private boolean isEnabled = false;

    private GuiTextField ipPort;
    private GuiTextField username;
    private GuiTextField password;

    private final GuiScreen parentScreen;

    private String msg = "";

    private int[] positionY;
    private int positionX;

    private TestPing testPing = new TestPing();

    public GuiProxy(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    private static boolean isValidIpPort(String ipP) {
        String[] split = ipP.split(":");
        if (split.length > 1) {
            if (!StringUtils.isNumeric(split[1])) return false;
            int port = Integer.parseInt(split[1]);
            if (port < 0 || port > 0xFFFF) return false;
            return true;
        } else {
            return false;
        }
    }

    private boolean checkProxy() {
        if (!isValidIpPort(ipPort.getText())) {
            msg = ChatFormatting.RED + "Неправильный IP:PORT";
            this.ipPort.setFocused(true);
            return false;
        }
        return true;
    }

    private void centerButtons(int amount, int buttonLength, int gap) {
        positionX = (this.width / 2) - (buttonLength / 2);
        positionY = new int[amount];
        int center = (this.height + amount * gap) / 2;
        int buttonStarts = center - (amount * gap);
        for (int i = 0; i != amount; i++) {
            positionY[i] = buttonStarts + (gap * i);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.ipPort.textboxKeyTyped(typedChar, keyCode);
        this.username.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
        msg = "";
        testPing.state = "";
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void updateScreen() {
        testPing.pingPendingNetworks();

        this.ipPort.updateCursorCounter();
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();

        super.updateScreen();
    }

    @Override
    public void initGui() {
        int buttonLength = 160;
        centerButtons(10, buttonLength, 26);

        isSocks4 = ProxyManager.proxy.type == Proxy.ProxyType.SOCKS4;

        GuiButton proxyType = new GuiButton(0, positionX, positionY[1], buttonLength, 20, isSocks4 ? "Socks 4" : "Socks 5");
        this.addButton(proxyType);

        this.ipPort = new GuiTextField(this.eventButton, mc.fontRenderer, positionX, positionY[2], buttonLength, 20);
        this.ipPort.setText(ProxyManager.proxy.ipPort);
        this.ipPort.setMaxStringLength(1024);
        this.ipPort.setFocused(true);

        this.username = new GuiTextField(this.eventButton, mc.fontRenderer, positionX, positionY[4], buttonLength, 20);
        this.username.setMaxStringLength(255);
        this.username.setText(ProxyManager.proxy.username);

        this.password = new GuiTextField(this.eventButton, mc.fontRenderer, positionX, positionY[5], buttonLength, 20);
        this.password.setMaxStringLength(255);
        this.password.setText(ProxyManager.proxy.password);

        int posXButtons = (this.width / 2) - (((buttonLength / 2) * 3) / 2);

        this.addButton(new GuiButton(1, posXButtons, positionY[8], buttonLength / 2 - 3, 20, "Применить"));
        this.addButton(new GuiButton(2, posXButtons + buttonLength / 2 + 3, positionY[8], buttonLength / 2 - 3, 20, "Проверить"));
        this.addButton(new GuiButton(3, positionX, positionY[7], buttonLength, 20, isEnabled ? "Включено" : "Выключено"));
        this.addButton(new GuiButton(4, posXButtons + (buttonLength / 2 + 3) * 2, positionY[8], buttonLength / 2 - 3, 20, "Вернуться"));

        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                isSocks4 = !isSocks4;
                button.displayString = isSocks4 ? "Socks 4" : "Socks 5";
                break;
            case 1:
                if (checkProxy()) {
                    ProxyManager.proxy = new Proxy(isSocks4, ipPort.getText(), username.getText(), password.getText());
                    ProxyManager.proxyEnabled = isEnabled;
                    mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenuElement()));
                }
                break;
            case 2:
                if (ipPort.getText().isEmpty() || ipPort.getText().equalsIgnoreCase("none")) {
                    msg = TextFormatting.RED + "Укажите прокси для тестирования";
                    return;
                }
                if (checkProxy()) {
                    testPing = new TestPing();
                    testPing.run("mc.reallyworld.ru", 25565, new Proxy(isSocks4, ipPort.getText(), username.getText(), password.getText()));
                }
                break;
            case 3:
                if (isValidIpPort(ipPort.getText())) {
                    isEnabled = !isEnabled;
                    button.displayString = isEnabled ? "Включено" : "Выключено";
                }
                break;
            case 4:
                mc.displayGuiScreen(parentScreen);
                break;
        }
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        drawString(mc.fontRenderer, "Тип:", this.width / 2 - 150, positionY[1] + 5, 10526880);
        drawCenteredString(mc.fontRenderer, "Аутентификация (необязательно)", this.width / 2, positionY[3] + 8, -1);
        drawString(mc.fontRenderer, "IP:PORT: ", this.width / 2 - 150, positionY[2] + 5, 10526880);
        drawString(mc.fontRenderer, "Статус: ", this.width / 2 - 150, positionY[7] + 5, 10526880);

        this.ipPort.drawTextBox();
        if (isSocks4) {
            drawString(mc.fontRenderer, "User ID: ", this.width / 2 - 150, positionY[4] + 5, 10526880);
            this.username.drawTextBox();
        } else {
            drawString(mc.fontRenderer, "Имя: ", this.width / 2 - 150, positionY[4] + 5, 10526880);
            drawString(mc.fontRenderer, "Пароль: ", this.width / 2 - 150, positionY[5] + 5, 10526880);
            this.username.drawTextBox();
            this.password.drawTextBox();
        }

        drawCenteredString(mc.fontRenderer, !msg.isEmpty() ? msg : testPing.state, this.width / 2, positionY[6] + 5, 10526880);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.ipPort.mouseClicked(mouseX, mouseY, mouseButton);
        this.username.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
