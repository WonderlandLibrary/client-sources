package me.satisfactory.base.gui;

import org.lwjgl.input.*;
import java.net.*;
import me.satisfactory.base.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class GuiPortscan extends GuiScreen
{
    private GuiScreen before;
    private GuiTextField emailField;
    private GuiTextField SPORT;
    private GuiTextField EPORT;
    private String status;
    private String serverIP;
    
    public GuiPortscan(final GuiScreen before) {
        this.status = "Waiting";
        this.before = before;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        final GuiButton crackBtn = new GuiButton(0, GuiPortscan.width / 2 - 100, GuiPortscan.height / 2 + 22, 200, 20, "Scan!");
        this.buttonList.add(crackBtn);
        this.buttonList.add(new GuiButton(1, GuiPortscan.width / 2 - 100, GuiPortscan.height / 2 + 44, 200, 20, "Back"));
        (this.emailField = new GuiTextField(2, this.fontRendererObj, GuiPortscan.width / 2 - 100, GuiPortscan.height / 2 - 70, 200, 20)).setMaxStringLength(254);
        this.emailField.setFocused(true);
        (this.SPORT = new GuiTextField(33, this.fontRendererObj, GuiPortscan.width / 2 - 100, GuiPortscan.height / 2 - 70 + 52, 98, 20)).setMaxStringLength(5);
        this.SPORT.setText("25500");
        (this.EPORT = new GuiTextField(44, this.fontRendererObj, GuiPortscan.width / 2 - 100 + 102, GuiPortscan.height / 2 - 70 + 52, 98, 20)).setMaxStringLength(5);
        this.EPORT.setText("26000");
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        final int id = button.id;
        if (button.enabled) {
            if (id == 0) {
                this.status = "Scanning";
                PortscanManager.ports.clear();
                this.serverIP = this.emailField.getText();
                final String ip2 = this.serverIP.split(":")[0];
                try {
                    final ServerData p_i1181_3_ = new ServerData("", ip2);
                    final InetAddress inetaddress = InetAddress.getByName(ip2);
                    final ServerAddress serveraddress = ServerAddress.func_78860_a(p_i1181_3_.serverIP);
                    Base.INSTANCE.getPortscanManager().Portscan(serveraddress.getIP(), Integer.parseInt(this.SPORT.getText()), Integer.parseInt(this.EPORT.getText()), 500, 250);
                    if (PortscanManager.ports != null && !PortscanManager.ports.isEmpty() && GuiPortscan.mc.currentScreen == this) {
                        for (int i = 0; i < PortscanManager.ports.size(); ++i) {
                            final Minecraft mc = GuiPortscan.mc;
                            final Minecraft mc2 = GuiPortscan.mc;
                            final String hostAddress = inetaddress.getHostAddress();
                            Base.INSTANCE.getPortscanManager();
                            mc.displayGuiScreen(new GuiConnecting(this, mc2, hostAddress, PortscanManager.ports.get(i)));
                        }
                    }
                }
                catch (Exception e) {
                    System.err.print(e);
                }
                this.status = "Done Scanning!";
                super.actionPerformed(button);
                return;
            }
            if (id == 1) {
                GuiPortscan.mc.displayGuiScreen(this.before);
            }
            if (id == 2) {
                Base.INSTANCE.getPortscanManager().onStop();
                Base.INSTANCE.getPortscanManager().isChecking = false;
                super.actionPerformed(button);
                return;
            }
        }
        super.actionPerformed(button);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        GlStateManager.scale(4.0f, 4.0f, 1.0f);
        GlStateManager.scale(0.5, 0.5, 1.0);
        Gui.drawCenteredString(this.fontRendererObj, "PortScan", GuiPortscan.width / 2 / 2, (GuiPortscan.height / 2 - 100 - 25) / 2, -1);
        GlStateManager.scale(0.5, 0.5, 1.0);
        this.drawString(this.fontRendererObj, "Status: " + this.status, GuiPortscan.width - this.fontRendererObj.getStringWidth("Status: " + this.status) - 10, GuiPortscan.height / 100 + 10, -1);
        Gui.drawCenteredString(this.fontRendererObj, "§cTries every Port of the a Server IP.", GuiPortscan.width / 2 / 1, (GuiPortscan.height / 2 - 100) / 1, -1);
        this.drawString(this.fontRendererObj, "§7Server IP:", GuiPortscan.width / 2 - 100, GuiPortscan.height / 2 - 11 - 73, -1);
        this.drawString(this.fontRendererObj, "§7Port range:", GuiPortscan.width / 2 - 100, GuiPortscan.height / 2 - 11 - 20, -1);
        this.emailField.drawTextBox();
        this.SPORT.drawTextBox();
        this.EPORT.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.emailField.textboxKeyTyped(typedChar, keyCode);
        this.SPORT.textboxKeyTyped(typedChar, keyCode);
        this.EPORT.textboxKeyTyped(typedChar, keyCode);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.emailField.mouseClicked(mouseX, mouseY, mouseButton);
        this.SPORT.mouseClicked(mouseX, mouseY, mouseButton);
        this.EPORT.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }
}
