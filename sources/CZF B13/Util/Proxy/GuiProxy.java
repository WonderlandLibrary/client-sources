package gq.vapu.czfclient.Util.Proxy;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiProxy extends GuiScreen {
    public static boolean connected = false;
    private final GuiMultiplayer prevMenu;
    private GuiTextField proxyBox;
    private String error = "";

    public GuiProxy(GuiMultiplayer guiMultiplayer) {
        this.prevMenu = guiMultiplayer;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public void updateScreen() {
        this.proxyBox.updateCursorCounter();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, "Back"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 72 + 12, "Connect"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 96 + 12, "Disconnect"));
        this.proxyBox = new GuiTextField(0, fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.proxyBox.setFocused(true);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton clickedButton) {
        if (clickedButton.enabled) {
            if (clickedButton.id == 0) {
                this.mc.displayGuiScreen(this.prevMenu);
            } else if (clickedButton.id == 1) {
                if (!this.proxyBox.getText().contains(":") || this.proxyBox.getText().split(":").length != 2) {
                    this.error = "Not a proxy!";
                    return;
                }
                String[] parts = this.proxyBox.getText().split(":");
                if (GuiProxy.isInteger(parts[1]) && Integer.parseInt(parts[1]) <= 65536) {
                    Integer.parseInt(parts[1]);
                }
                try {
                    ProxyConfig.proxyAddr = new ConnectionInfo();
                    ProxyConfig.proxyAddr.ip = parts[0];
                    ProxyConfig.proxyAddr.port = Integer.parseInt(parts[1]);
                    connected = true;
                } catch (Exception e) {
                    this.error = e.toString();
                    return;
                }
                if (!this.error.isEmpty()) {
                    return;
                }
            } else if (clickedButton.id == 2) {
                ProxyConfig.stop();
                connected = false;
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.proxyBox.textboxKeyTyped(par1, par2);
        if (par2 == 28 || par2 == 156) {
            this.actionPerformed(this.buttonList.get(1));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        this.proxyBox.mouseClicked(par1, par2, par3);
        if (this.proxyBox.isFocused()) {
            this.error = "";
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.mc.fontRendererObj.drawCenteredString("Proxies Reloaded", width / 2, 20, 16777215);
        this.mc.fontRendererObj.drawCenteredString("(SOCKS5 Proxies only)", width / 2, 30, 16777215);
        this.mc.fontRendererObj.drawStringWithShadow("IP:Port", width / 2 - 100, 47.0f, 10526880);
        this.mc.fontRendererObj.drawCenteredString(this.error, width / 2, 95, 16711680);
        String currentProxy = "";
        if (connected) {
            currentProxy = "\u00a7a" + ProxyConfig.proxyAddr.ip + ":" + ProxyConfig.proxyAddr.port;
        }
        if (!connected) {
            currentProxy = "\u00a7cN/A";
        }
        this.mc.fontRendererObj.drawStringWithShadow("Current proxy: " + currentProxy, 1.0f, 3.0f, -1);
        this.proxyBox.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
